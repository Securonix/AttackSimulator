/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author securonix
 */

public class SyslogBean {

    private String UserIpFile;
    private String ipaddressFile;
    private String transactionFile;
    private String regulartransactionFile;
    private String servicetransactionFile;
    private String operationFile;
    private String domainFile;
    private String fileListFile;
    private String recipientFile;
    private String classificationFile;
    private String subjectFile;

    private ArrayList<String> users = new ArrayList<>();
    private HashMap<String, ArrayList<String>> usersIps = new HashMap<>();
    private ArrayList<String> transactions = new ArrayList<>();
    private ArrayList<String> domains = new ArrayList<>();
    private ArrayList<String> files = new ArrayList<>();
    private ArrayList<String> operations = new ArrayList<>();
    private ArrayList<String> recipients = new ArrayList<>();
    private ArrayList<String> classifications = new ArrayList<>();
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<String> ipaddresses = new ArrayList<>();
    private ArrayList<String> servicetransactions = new ArrayList<>();
    private ArrayList<String> regulartransactions = new ArrayList<>();
    private boolean noUser = false;

    public String getIPAddressFile() {
        return ipaddressFile;
    }

    public String getRegularTransactionFile() {
        return regulartransactionFile;
    }

    public String getServiceTransactionFile() {
        return servicetransactionFile;
    }

    public String getTransactionFile() {
        return transactionFile;
    }

    public String getOperationsFile() {
        return operationFile;
    }

    public String getDomainsFile() {
        return domainFile;
    }

    public String getFileListFile() {
        return fileListFile;
    }

    public String getRecipientFile() {
        return recipientFile;
    }

    public String getClassificationFile() {
        return classificationFile;
    }

    public String getSubjectFile() {
        return subjectFile;
    }

    public ArrayList<String> getIPAddresses() {
        return ipaddresses;
    }

    public ArrayList<String> getRegularTransactions() {
        return regulartransactions;
    }

    public ArrayList<String> getServiceTransactions() {
        return servicetransactions;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public ArrayList<String> getOperations() {
        return operations;
    }

    public ArrayList<String> getDomains() {
        return domains;
    }

    public ArrayList<String> getFilenames() {
        return files;
    }

    public ArrayList<String> getRecipients() {
        return recipients;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public ArrayList<String> getClassifications() {
        return classifications;
    }

    public String getUserIpFile() {
        return UserIpFile;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public HashMap<String, ArrayList<String>> getUsersIps() {
        return usersIps;
    }

    public void setNoUser(boolean noUser) {
        this.noUser = noUser;
    }

    public boolean isNoUser() {
        return noUser;
    }

    public void setTransactionFile(String transactionFile) {
        this.transactionFile = transactionFile;
    }

    public void setRegularTransactionFile(String regulartransactionFile) {
        this.regulartransactionFile = regulartransactionFile;
    }

    public void setServiceTransactionFile(String servicetransactionFile) {
        this.servicetransactionFile = servicetransactionFile;
    }

    public void setIPAddressFile(String ipaddressFile) {
        this.ipaddressFile = ipaddressFile;
    }

    public void setOperationFile(String operationFile) {
        this.operationFile = operationFile;
    }

    public void setDomainFile(String domainFile) {
        this.domainFile = domainFile;
    }

    public void setFileListFile(String fileListFile) {
        this.fileListFile = fileListFile;
    }

    public void setRecipientFile(String recipientFile) {
        this.recipientFile = recipientFile;
    }

    public void setSubjectFile(String subjectFile) {
        this.subjectFile = subjectFile;
    }

    public void setClassificationFile(String classificationFile) {
        this.classificationFile = classificationFile;
    }

    public void setTransactions(ArrayList<String> transactions) {
        this.transactions = transactions;
    }

    public void setDomains(ArrayList<String> domains) {
        this.domains = domains;
    }

    public void setIPAddresses(ArrayList<String> ipaddresses) {
        this.ipaddresses = ipaddresses;
    }

    public void setRegularTransactions(ArrayList<String> regulartransactions) {
        this.regulartransactions = regulartransactions;
    }

    public void setServiceTransactions(ArrayList<String> servicetransactions) {
        this.servicetransactions = servicetransactions;
    }

    public void setFilenames(ArrayList<String> files) {
        this.files = files;
    }

    public void setOperations(ArrayList<String> operations) {
        this.operations = operations;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public void setClassifications(ArrayList<String> classifications) {
        this.classifications = classifications;
    }

    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }

    public void setUserIpFile(String UserIpFile) {
        this.UserIpFile = UserIpFile;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public void setUsersIps(HashMap<String, ArrayList<String>> usersIps) {
        this.usersIps = usersIps;
    }

}