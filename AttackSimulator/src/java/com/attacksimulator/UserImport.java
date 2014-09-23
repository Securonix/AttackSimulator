/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author securonix
 */
public class UserImport {
    private String EmployeeId;
    private String UniqueCode;
    private String NetworkId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String nameprefix;
    private String namesuffix;
    private String preferredName;
    private String department;
    private String division;
    private String orgunitnumber;
    private String companycode;
    private String companynumber;
    private String hierarchy;
    private String location;
    private String locationName;
    private String manageremployeenumber;
    private String managerfirstname;
    private String managerlastname;
    private String managermiddlename;
    private String workemail;
    private String workphone;
    private String extension;
    private String fax;
    private String mobile;
    private String pager;
    private String secondaryphone;
    private String title;
    private String employeetype;
    private String employeetypedescription;
    private String regtempin;
    private String fulltimeparttimein;
    private String status;
    private String employeestatusdescription;
    private String street;
    private String city;
    private String province;
    private String zipcode;
    private String userstate;
    private String region;
    private String country;
    private String approver;
    private String delegate;
    private String jobcode;
    private String costcentername;
    private String costcentercode;
    private String sunrisedate;
    private String sunsetdate;
    private String vacationstart;
    private String vacationend;
    private String mailcode;
    private String hiredate;
    private String terminationdate;
    private String lastdayworked;
    private String contractstartdate;
    private String contractenddate;
    private String lastperformancereviewdate;
    private String lastperformancereviewresult;
    private String standardhours;
    private String shiftcode;
    private String shiftname;
    private String dom_intl_in;
    
    public UserImport(){
        
    }

    public UserImport(String EmployeeId, String UniqueCode, String NetworkId, String firstname, String middlename, String lastname, String nameprefix, String namesuffix, String preferredName, String department, String division, String orgunitnumber, String companycode, String companynumber, String hierarchy, String location, String locationName, String manageremployeenumber, String managerfirstname, String managerlastname, String managermiddlename, String workemail, String workphone, String extension, String fax, String mobile, String pager, String secondaryphone, String title, String employeetype, String employeetypedescription, String regtempin, String fulltimeparttimein, String status, String employeestatusdescription, String street, String city, String province, String zipcode, String userstate, String region, String country, String approver, String delegate, String jobcode, String costcentername, String costcentercode, String sunrisedate, String sunsetdate, String vacationstart, String vacationend, String mailcode, String hiredate, String terminationdate, String lastdayworked, String contractstartdate, String contractenddate, String lastperformancereviewdate, String lastperformancereviewresult, String standardhours, String shiftcode, String shiftname, String dom_intl_in) {
        this.EmployeeId = EmployeeId;
        this.UniqueCode = UniqueCode;
        this.NetworkId = NetworkId;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.nameprefix = nameprefix;
        this.namesuffix = namesuffix;
        this.preferredName = preferredName;
        this.department = department;
        this.division = division;
        this.orgunitnumber = orgunitnumber;
        this.companycode = companycode;
        this.companynumber = companynumber;
        this.hierarchy = hierarchy;
        this.location = location;
        this.locationName = locationName;
        this.manageremployeenumber = manageremployeenumber;
        this.managerfirstname = managerfirstname;
        this.managerlastname = managerlastname;
        this.managermiddlename = managermiddlename;
        this.workemail = workemail;
        this.workphone = workphone;
        this.extension = extension;
        this.fax = fax;
        this.mobile = mobile;
        this.pager = pager;
        this.secondaryphone = secondaryphone;
        this.title = title;
        this.employeetype = employeetype;
        this.employeetypedescription = employeetypedescription;
        this.regtempin = regtempin;
        this.fulltimeparttimein = fulltimeparttimein;
        this.status = status;
        this.employeestatusdescription = employeestatusdescription;
        this.street = street;
        this.city = city;
        this.province = province;
        this.zipcode = zipcode;
        this.userstate = userstate;
        this.region = region;
        this.country = country;
        this.approver = approver;
        this.delegate = delegate;
        this.jobcode = jobcode;
        this.costcentername = costcentername;
        this.costcentercode = costcentercode;
        this.sunrisedate = sunrisedate;
        this.sunsetdate = sunsetdate;
        this.vacationstart = vacationstart;
        this.vacationend = vacationend;
        this.mailcode = mailcode;
        this.hiredate = hiredate;
        this.terminationdate = terminationdate;
        this.lastdayworked = lastdayworked;
        this.contractstartdate = contractstartdate;
        this.contractenddate = contractenddate;
        this.lastperformancereviewdate = lastperformancereviewdate;
        this.lastperformancereviewresult = lastperformancereviewresult;
        this.standardhours = standardhours;
        this.shiftcode = shiftcode;
        this.shiftname = shiftname;
        this.dom_intl_in = dom_intl_in;
    }
    
    
    public void saveToDatabase(){
        BufferedReader in = null;
        try {
            //get connection to the db
            MySQLDBClass db = new MySQLDBClass();
            in = new BufferedReader(new FileReader("/home/securonix/Documents/HRData665.csv"));
            
            String line;
            while((line = in.readLine())!=null){
                String [] splitLine = line.split(",");
                System.out.println("Length of the splitLineArray: "+splitLine.length);
                UserImport ui = new UserImport(splitLine[0], splitLine[1], splitLine[2], splitLine[3], splitLine[4], splitLine[5], splitLine[6], splitLine[7], splitLine[8], splitLine[9], splitLine[10], splitLine[11], splitLine[12], splitLine[13], splitLine[14], splitLine[15], splitLine[16], splitLine[17], splitLine[18], splitLine[19], splitLine[20], splitLine[21], splitLine[22], splitLine[23], splitLine[24], splitLine[25], splitLine[26], splitLine[27], splitLine[28], splitLine[29], splitLine[30], splitLine[31], splitLine[32], splitLine[33], splitLine[34], splitLine[35], splitLine[36], splitLine[37], splitLine[38], splitLine[39], splitLine[40], splitLine[41], splitLine[42], splitLine[43], splitLine[44], splitLine[45], splitLine[46], splitLine[47], splitLine[48], splitLine[49], splitLine[50], splitLine[51], splitLine[52], splitLine[53], splitLine[54], splitLine[55], splitLine[56], splitLine[57], splitLine[58], splitLine[59], splitLine[60], splitLine[61], splitLine[62]);
                
                //pass both the split line and the userimport object.. both will be helpful.. 
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserImport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserImport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(in != null)
                    in.close();
            } catch (IOException ex) {
                Logger.getLogger(UserImport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
