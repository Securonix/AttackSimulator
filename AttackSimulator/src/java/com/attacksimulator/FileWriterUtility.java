/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author securonix
 */
public class FileWriterUtility {
    private String filename;
    private BufferedWriter bufferedWriter;
    
    public FileWriterUtility(){
        filename = UUID.randomUUID().toString();
        filename +=".txt";
        //create a file
        File file = new File(filename);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileWriterUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            Logger.getLogger(FileWriterUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeString(String publishString){
        if(bufferedWriter!=null){
            try {
                bufferedWriter.write(publishString);
            } catch (IOException ex) {
                Logger.getLogger(FileWriterUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
