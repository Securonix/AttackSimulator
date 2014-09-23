package com.grailsrocks.blueprint

class BlueprintTagLib {
    static namespace = "blueprint"

    static RESOURCE_MAPPINGS = [
        css:[type:"text/css", rel:'stylesheet'],
        rss:[type:'application/rss+xml', rel:'alternate'], 
        atom:[type:'application/atom+xml', rel:'alternate'], 
        favicon:[type:'image/x-icon', rel:'shortcut icon'],
        appleicon:[type:'image/x-icon', rel:'apple-touch-icon']
    ]

    def resources = { attrs ->
        out << resourceLink(plugin:'blueprint', type:'css', dir:'css/blueprint', file:'screen.css', media:'screen, projection')
        out << resourceLink(plugin:'blueprint', type:'css', dir:'css/blueprint', file:'print.css', media:'print')
        out << """
<!--[if lt IE 8]>${resourceLink(plugin:'blueprint', type:'css', dir:'css/blueprint', file:'ie.css', media:'screen, projection')}<![endif]-->
"""
        def plugins = attrs.plugins?.tokenize(',')
        if (plugins) {
            plugins.each { pl ->
                out << resourceLink(plugin:'blueprint', type:'css', dir:'css/blueprint/plugins/'+pl.trim(), file:'screen.css', media:'screen, projection')
            }
        }
    }
    
    def resourceLink = { attrs ->
        def t = attrs.remove('type')
        def typeInfo = RESOURCE_MAPPINGS[t]
        if (!typeInfo) {
            throwTagError "Unknown resourceLink type: ${t}"
        }
        def o = new StringBuilder()
        def url = g.resource(plugin:attrs.remove('plugin'), dir:attrs.remove('dir'), file:attrs.remove('file'))
        o << "<link href=\"${url.encodeAsHTML()}\" "
        // Output info from the mappings
        typeInfo.each { k, v ->
           o << k
           o << '="'
           o << v
           o << '" '    
        }

        // Output any remaining user-specified attributes
        attrs.each { k, v ->
           o << k
           o << '="'
           o << v.encodeAsHTML()
           o << '" '    
        }
        
        o << '/>'
        out << o
    }
}
