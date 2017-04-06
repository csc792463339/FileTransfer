/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author CSC
 */
public class showToolPane extends JPanel implements ActionListener {

    JButton copy, showSlect, selectAll, cancelAll, choosetype, deleteSelect, showTask;
    JComboBox selectType;
    JPanel showFile;
    selectFile sf;
    taskPane taskPane;

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/pic/72090.png"));
        g.drawImage(icon.getImage(), 0, 0, getSize().width, getSize().height, this);

    }

    showToolPane(JPanel sfp) {

        this.showFile = sfp;
        sf = new selectFile();
        this.setBounds(0, 400, 720, 80);
        this.setBackground(Color.ORANGE);
        this.setLayout(new FlowLayout());

        taskPane = new taskPane();
        taskPane.setVisible(false);
        taskPane.setLocationRelativeTo(null);
        taskPane.setLocation(taskPane.getLocation().x + 195, taskPane.getLocation().y + 75);
        taskPane.setBackground(Color.yellow);
        taskPane.setAlwaysOnTop(true);

        copy = new JButton("复制");
        copy.addActionListener(this);
        selectAll = new JButton("选择全部");
        selectAll.addActionListener(this);
        cancelAll = new JButton("取消全选");
        cancelAll.addActionListener(this);
        deleteSelect = new JButton("删除");
        deleteSelect.addActionListener(this);
        showTask = new JButton("查看任务");
        showTask.setBounds(550, 520, 40, 40);
        showTask.setBackground(Color.red);
        showTask.addActionListener(this);

        selectType = new JComboBox();
        selectType.setBounds(440, 500, 100, 40);
        selectType.addItem("选择类型");
        selectType.addItem("mp3");
        selectType.addItem("mp4");
        selectType.addItem("png");
        selectType.addItem("zip");
        selectType.addItem("rar");
        selectType.addItem("jpg");
        selectType.addItem("doc");
        selectType.addItem("xls");
        selectType.addItem("ppt");
        selectType.addItem("pdf");
        selectType.addItem("txt");
        selectType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Component[] cs = showFile.getComponents();
                for (int i = 1; i < cs.length; i++) {
                    showIcon s = (showIcon) cs[i];
                    String n = s.b.getName();
                    if (n.endsWith("." + selectType.getSelectedItem())) {
                        s.cb.setSelected(true);
                        s.cb.setVisible(true);
                    } else {
                        s.cb.setSelected(false);
                        s.cb.setVisible(false);
                    }
                    showFile.updateUI();
                }
            }
        });

        this.add(copy);
        this.add(deleteSelect);
        this.add(selectAll);
        this.add(cancelAll);
        this.add(selectType);
        this.add(showTask);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(copy)) {
            sf.lenth = 0;
            sf.files = new String[100];
            Component[] cs = showFile.getComponents();
            for (int i = 1; i < cs.length; i++) {
                showIcon s = (showIcon) cs[i];
                if (s.cb.isSelected()) {
                    sf.files[sf.lenth++] = s.b.getName();
                }
            }
            if (sf.lenth == 0) {
                JOptionPane.showMessageDialog(this.getParent(), "未选择任何文件");
            } else {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.setDialogTitle("选择路径");
                jfc.showDialog(this.getParent(), "选择");
                if (jfc.getSelectedFile() != null) {
                    for (int i = 0; i < sf.lenth; i++) {
                        taskPane.addTask(new File(sf.files[i]).getName(), sf.files[i], jfc.getSelectedFile().toString());
                    }
                }
            }

        } else if (e.getSource().equals(selectAll)) {
            Component[] cs = showFile.getComponents();
            for (int i = 1; i < cs.length; i++) {
                showIcon s = (showIcon) cs[i];
                s.cb.setSelected(true);
                s.cb.setVisible(true);
                showFile.updateUI();
            }

        } else if (e.getSource().equals(deleteSelect)) {
            JOptionPane j = new JOptionPane();
            int ii = JOptionPane.showConfirmDialog(this.getParent(), "你是认真的吗？", "删除文件", JOptionPane.YES_NO_OPTION);
            if (ii == 0) {
                sf.lenth = 0;
                sf.files = new String[100];
                Component[] cs = showFile.getComponents();
                for (int i = 1; i < cs.length; i++) {
                    showIcon s = (showIcon) cs[i];
                    if (s.cb.isSelected()) {
                        sf.files[sf.lenth++] = s.b.getName();
                    }
                }
                String path = new File(sf.files[0]).getParent();
                for (int i = 0; i < sf.lenth; i++) {
                    File del = new File(sf.files[i]);
                    if (del.isDirectory()) {
                        deleteDir(del);
                    } else {
                        del.delete();
                    }
                }
                showFile.removeAll();
                new showIcon("root", 0, path).filelist(showFile, path);
                showFile.updateUI();
            }
        } else if (e.getSource().equals(cancelAll)) {
            Component[] cs = showFile.getComponents();
            for (int i = 1; i < cs.length; i++) {
                showIcon s = (showIcon) cs[i];
                s.cb.setSelected(false);
                s.cb.setVisible(false);
                showFile.updateUI();
            }
        } else if (e.getSource().equals(showTask)) {
            if (taskPane.isVisible()) {
                taskPane.setVisible(false);
            } else {
                taskPane.setVisible(true);
            }
        }
    }

    class selectFile {

        int lenth;
        String[] files;
    }

    private void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                deleteDir(new File(dir, children[i]));
            }
        }
        dir.delete();
    }
}
