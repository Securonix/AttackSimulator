# AttackSimulator
AttackSimulator generates datasets for use in the testing of security products. Use this README to quickly get started with AttackSimulator.

## Table of Contents
- [Introduction to AttackSimulator](#introduction-to-attacksimulator)
- [Prerequisites](#prerequisites)
- [Installating the AttackSimulator](#installing-the-attacksimulator)
- [Simulating an Organizational IT Environment](#simulating-an-organizational-it-environment)
- [Logging in to AttackSimulator for the First Time](#logging-in-to-attacksimulator-for-the-first-time)
- [Generating Data Feeds with AttackSimulator](#generating-data-feeds-with-attacksimulator)
  + [Step 1: Generate a template](#step-1-generate-a-template)
  + [Step 2: Upload template file to folder on server hosting AttackSimulator](#step-2-upload-template-file-to-folder-on-server-hosting-attacksimulator)
  + [Step 3: Update the feedmaster table and enter a new entry](#step-3-update-the-feedmaster-table-and-enter-a-new-entry)
  + [Step 4: Order the feed delivery via Syslog](#step-4-order-the-feed-delivery-via-syslog)
  + [Step 5: Start feed generation](#step-5-start-feed-generation)
- [Creating New Variables](#creating-new-variables)  
- [Version](#version)
- [License](#license)


## Introduction to AttackSimulator
Unlike other log simulators, the AttackSimulator simulates controlled scenarios, such as sequence of events involving the same actor/IP address. Use AttackSimulator to generate event data and attack data for simulating real world datasets. At Securonix, we use AttackSimulator to mimic real world datasets for testing our content.

## Prerequisites
Operating System: Tested on CentOS 7.x
You need to have JRE 1.7, Tomcat 7.x and MySQL 5.6. 

## Installing the AttackSimulator
To deploy AttackSimulator, create the database schema and delpoy the war file on Tomcat. 
2. Update database details in config.groovy file and then compile your project.

3. Update database details in following file located in below paths:
 /WEB-INF/classes/com/attacksimulator/jdbc.properties
/WEB-INF/classes/jdbc.properties

username=xxx
password=xxx
database=<your attack simulator database schema name>
port=3306

4. Also update database details in quartz.properties file located in below path:
/WEB-INF/classes/quartz.properties

4. Create a file named "AttackSimulator.xml" and copy it in below path:
/Tomcat/conf/Catalina/localhost

Below entries to be copied in xml file:
<?xml version="1.0" encoding="UTF-8"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<Context antiResourceLocking="false" privileged="true">
    <Resource name="jdbc/AttackSimulatorDS"
            auth="Container"
            type="javax.sql.DataSource"
            username="root"
            driverClassName="com.mysql.jdbc.Driver"
            url="jdbc:mysql://localhost:3306/attacksimdev?autoReconnect=true&amp;rewriteBatchedStatements=true&amp;useUnicode=true&amp;maxReconnects=40&amp;characterEncoding=UTF-8"
            testWhileIdle="true"
            testOnBorrow="true"
            testOnReturn="true"
            validationQuery="SELECT 1"
            validationInterval="30000"
            timeBetweenEvictionRunsMillis="5000"
            maxTotal="377"
            minIdle="10"
            maxIdle="50"
            maxWaitMillis="-1"
            initialSize="10"
            removeAbandonedTimeout="60"
            removeAbandonedOnBorrow="true"
            logAbandoned="true"
            minEvictableIdleTimeMillis="300000"
            abandonWhenPercentageFull="75"
             jdbcInterceptors="ResetAbandonedTimer"
           password="<your database password>"
                        />
            </Context>

## Simulating an Organizational IT Environment
When generating data feeds, AttackSimulator uses templates to set up a fictitious organizational environment. Each template uses 
variables that get substituted at runtime. You provide values for your organization's environment, such as the number of employees,
network address class for internal network, DMZ devices and countries from which traffic is observed. Using your input, the 
AttackSimulator generates logs that match your environment.

## Logging in to AttackSimulator for the First Time
When you log in for the first time into the AttackSimulator, provide the following information:
* Internal IP address ranges: choose from Class A, Class B or Class C for the Ip address range for internal network.
* Devices in DMZ: specify the number of devices in the DMZ, and the host name of these devices.
* External IP address ranges: choose from the list of countries typically seen generating traffic, such as VPN and web server logs.

## Generating Data Feeds with AttackSimulator
To generate data feeds with AttackSimulator, follow these steps:

### Step 1: Generate a template
To generate data for a device, start with sample data and substitute text with variables. To use variables, enclose the variable name in two curly braces. For example: {{variablename}}

For a list of available variables, refer to the variablemaster table. To create new variables, enter the value directly in the database. For example: 
* Date:{{datetimeMMddyyyy}}
* IP address: {{usermaster.internalip}} or {{usermaster.desktopipaddress}}
* Accountname: {{usermaster.lanid}} or {{usermaster.userid}}
* File Name: {{demofilenames}}
* URL: {{websites}}

At the end of each line, specify the ratio in which the line occurs compared to other lines. To specify the ratio, use the less than sign <<.
To ensure that events occur sequentially, use the double pipe sign ||.

For example, to generate a scenario for the same user with multiple log failures, followed by log successful, create a template as follows:
```
{{datetimeMMddyyyy}},{{usermaster.lanid}},Logon Failure 
|| {{datetimeMMddyyyy}},{{usermaster.lanid}},Logon Failure || 
```
```
{{datetimeMMddyyyy}},{{usermaster.lanid}},Logon Failure 
|| {{datetimeMMddyyyy}},{{usermaster.lanid}},Logon Failure || 
```
```
{{datetimeMMddyyyy}},{{usermaster.lanid}},Logon Failure 
|| {{datetimeMMddyyyy}},{{usermaster.lanid}},Logon Successful << 10
```
### Step 2: Upload template file to folder on server hosting AttackSimulator
To upload the file, you need ssh access to the machine hosting the AttackSimulator application.
For example, if AttackSimulator is hosted on a machine with IP address 10.0.1.16, upload the file to a folder such as /Securonix/AttackSimulator/Feeds/.

### Step 3: Update the feedmaster table and enter a new entry
* Run query: 
```
select * from feedmaster.
```
* Add a new entry. For example: 

```
# id, feedtype, transactionfilepath, version 216, JunOS_Pulse_VPN, /Securonix/AttackSimulator/FEEDS/SAAS/JunOS_Pulse/Junos_Pulse_VPN_Template.txt, 0
```
```
INSERT INTO `attacksimdev`.`feedmaster` (`id`, `feedtype`, `transactionfilepath`, `version`) 
VALUES ('333', 'SAAS-JunOS_Pulse_VPN', '/Securonix/AttackSimulator/FEEDS/SAAS/JunOS_Pulse/Junos_Pulse_VPN_Template.txt', '0');
```

### Step 4: Order the feed delivery via Syslog
From the AttackSimulator user interface: 
  1. Go to the left navigation panel and click **My Orders**. 
  2. Select the feed and specify the frequency (number of seconds after which the event is published). 
     If you want the number of events to be 10 events/second between 9am to 1pm, enter the value as 0.1.
  3. Set up a start date and end date for the events getting published in the **Date Start** and **Date End** fields, respectively.
  4. Click **Submit**.
By default, the events are published via syslog. For example, Publish to 10.0.51.183 on port 514.

**NOTE**: The screen does not refresh automatically to show the submitted order. Click **Refresh** to view the order.

### Step 5: Start feed generation
Scroll down the **Orders** screen and click the Start Service button to begin publishing events.

## Creating New Variables
To create new variables:
```
  'tablevaluegenerator'
  'datevaluegenerator'
  'randomvaluegenerator'
  'sequentialvaluegenerator'
  'dmztablegenerator'
  'randomexternalipgenerator'
  'randomstringgenerator'
```

## Version
BETA 1

## License
This project is licensed under the Apache 2.0 License - see the LICENSE.md file for details
