-- 1.0 upgrade script from here
-- not there in schema attacksimdev.sql

-- If you have run attacksimdev.sql, start running scripts from Dec 10 2014

-- Dec 10 2014
-- Added additional columns to store various account names against IPaddresses for internal users

ALTER TABLE `attacksimdev`.`sysipusermapping` 
ADD COLUMN `workemail` VARCHAR(255) NULL AFTER `version`,
ADD COLUMN `lanid` VARCHAR(255) NULL AFTER `workemail`,
ADD COLUMN `account1` VARCHAR(255) NULL AFTER `lanid`;
ALTER TABLE `attacksimdev`.`sysipusermapping` 
ADD COLUMN `username` VARCHAR(255) NULL AFTER `account1`;

update attacksimdev.sysipusermapping si, attacksimdev.usermaster um set si.workemail=um.workemail, si.lanid=um.lanid,si.account1=um.account1,si.username=um.userid
where si.userid=um.id;