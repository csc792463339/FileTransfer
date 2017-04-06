/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CSC
 */
public class saveTask {

    FileWriter fw;
    FileReader fr;
    String[] tasks;
    File task;

    saveTask() {
        task = new File("task.txt");
        if (!task.exists()) {
            try {
                task.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(saveTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addTask(String name, String from, String to) {
        try {
            fw = new FileWriter(task, true);
            fw.append(name + "," + from + "," + to + ";");
            fw.flush();
        } catch (IOException ex) {
            Logger.getLogger(saveTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] getTask() {
        FileReader fr = null;
        try {
            fr = new FileReader(task);
            char[] c = new char[100000];
            fr.read(c);
            fr.close();
            tasks = new String[String.valueOf(c).split(";").length];
            tasks = String.valueOf(c).split(";");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(saveTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(saveTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tasks;
    }

    public void delTask(String name, String from, String to) {     
        char[] c = new char[100000];
        String del = name + "," + from + "," + to;
        String aa[] = this.getTask();

        try {
            fw = new FileWriter(task);
            fw.write("");
            fw.flush();
            fw.close();
            fw = new FileWriter(task, true);
        } catch (IOException ex) {
            Logger.getLogger(saveTask.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < aa.length - 1; i++) {
            if (aa[i].equalsIgnoreCase(del)) {
                continue;
            } else {
                try {
                    fw.append(aa[i] + ";");
                } catch (IOException ex) {
                    Logger.getLogger(saveTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(saveTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
