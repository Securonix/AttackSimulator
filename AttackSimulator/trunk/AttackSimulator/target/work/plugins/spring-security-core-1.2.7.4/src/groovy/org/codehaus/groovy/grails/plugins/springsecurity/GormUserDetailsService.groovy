/* Copyright 2006-2012 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.plugins.springsecurity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Default implementation of <code>GrailsUserDetailsService</code> that uses
 * domain classes to load users and roles.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class GormUserDetailsService implements GrailsUserDetailsService {

	private Logger _log = LoggerFactory.getLogger(getClass())

	/**
	 * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least one role, so
	 * we give a user with no granted roles this one which gets past that restriction but
	 * doesn't grant anything.
	 */
	static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]

	/** Dependency injection for the application. */
	def grailsApplication

	/**
	 * {@inheritDoc}
	 * @see org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserDetailsService#loadUserByUsername(
	 * 	java.lang.String, boolean)
	 */
	UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {
		def conf = SpringSecurityUtils.securityConfig
		String userClassName = conf.userLookup.userDomainClassName
		def dc = grailsApplication.getDomainClass(userClassName)
		if (!dc) {
			throw new RuntimeException("The specified user domain class '$userClassName' is not a domain class")
		}

		Class<?> User = dc.clazz

		User.withTransaction { status ->
			def user = User.findWhere((conf.userLookup.usernamePropertyName): username)
			if (!user) {
				log.warn "User not found: $username"
				throw new UsernameNotFoundException('User not found', username)
			}

			Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
			createUserDetails user, authorities
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(
	 * 	java.lang.String)
	 */
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		loadUserByUsername username, true
	}

	protected Collection<GrantedAuthority> loadAuthorities(user, String username, boolean loadRoles) {
		if (!loadRoles) {
			return []
		}

		def conf = SpringSecurityUtils.securityConfig

		String authoritiesPropertyName = conf.userLookup.authoritiesPropertyName
		String authorityPropertyName = conf.authority.nameField

		Collection<?> userAuthorities = user."$authoritiesPropertyName"
		def authorities = userAuthorities.collect { new GrantedAuthorityImpl(it."$authorityPropertyName") }
		authorities ?: NO_ROLES
	}

	protected UserDetails createUserDetails(user, Collection<GrantedAuthority> authorities) {

		def conf = SpringSecurityUtils.securityConfig

		String usernamePropertyName = conf.userLookup.usernamePropertyName
		String passwordPropertyName = conf.userLookup.passwordPropertyName
		String enabledPropertyName = conf.userLookup.enabledPropertyName
		String accountExpiredPropertyName = conf.userLookup.accountExpiredPropertyName
		String accountLockedPropertyName = conf.userLookup.accountLockedPropertyName
		String passwordExpiredPropertyName = conf.userLookup.passwordExpiredPropertyName

		String username = user."$usernamePropertyName"
		String password = user."$passwordPropertyName"
		boolean enabled = enabledPropertyName ? user."$enabledPropertyName" : true
		boolean accountExpired = accountExpiredPropertyName ? user."$accountExpiredPropertyName" : false
		boolean accountLocked = accountLockedPropertyName ? user."$accountLockedPropertyName" : false
		boolean passwordExpired = passwordExpiredPropertyName ? user."$passwordExpiredPropertyName" : false

		new GrailsUser(username, password, enabled, !accountExpired, !passwordExpired,
				!accountLocked, authorities, user.id)
	}

	protected Logger getLog() { _log }
}
