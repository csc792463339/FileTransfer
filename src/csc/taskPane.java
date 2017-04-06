package csc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CSC
 */
public class taskPane extends JFrame {

    JPanel pane;
    JScrollPane scrollPane;
    saveTask saveTask;

    taskPane() {
        this.setTitle("当前任务");
        this.setSize(340, 200);
        this.setResizable(false);
        this.setLayout(null);
        pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/pic/3301000.png"));
                g.drawImage(icon.getImage(), 0, 0, getSize().width, getSize().height, this);

            }
        };
        pane.setAutoscrolls(true);
        pane.setSize(300, 1000);
        pane.setBackground(Color.yellow);
        pane.setLayout(new FlowLayout());
        pane.setPreferredSize(new Dimension(100, 1000));
        scrollPane = new JScrollPane(pane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 335, 172);
        scrollPane.setVisible(true);
        this.add(scrollPane);
        this.setVisible(false);

        saveTask = new saveTask();
        String tasks[] = saveTask.getTask();
        for (int i = 0; i < tasks.length - 1; i++) {
            String[] ren = tasks[i].split(",");
            this.newadd(ren[0], ren[1], ren[2], 0);
        }
    }

    public void addTask(String name, String from, String to) {
        File f = new File(from);
        if (f.isDirectory()) {
            System.out.println("wenjianjia");
            String t = to + "\\" + f.getName();
            new File(t).mkdirs();
            File[] list = f.listFiles();

            for (int i = 0; i < list.length; i++) {
                System.out.println(list[i].getName());
                if (list[i].isDirectory()) {
                    addTask(list[i].getName(), list[i].getPath(), t + "\\");
                } else {
                    newadd(name, list[i].getPath(), t, 1);
                }
            }
        } else {
            saveTask.addTask(name, from, to);
            newadd(name, from, to, 1);
        }

    }

    public void newadd(String name, String from, String to, int auto) {

        this.pane.add(new task(name, from, to, auto));
        this.pane.updateUI();
    }

}
