/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author CSC
 */
public class showDiskPane extends JPanel implements Runnable, ActionListener {

    JButton[] dk;
    JPanel showFile;
    int past = 0, now = 0;
    FileSystemView fileSys;

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/pic/80400.png"));
        g.drawImage(icon.getImage(), 0, 0, getSize().width, getSize().height, this);

    }

    showDiskPane(JPanel sf) {

        this.showFile = sf;
        this.setBounds(0, 0, 84, 405);
        this.setBackground(Color.green);
        this.setLayout(new FlowLayout());
        fileSys = FileSystemView.getFileSystemView();

        dk = new JButton[6];
        for (int i = 0; i < 6; i++) {
            dk[i] = new JButton();
            dk[i].setName("disk" + i);
            dk[i].setBorder(null);
            dk[i].setPreferredSize(new Dimension(48, 48));
            dk[i].addActionListener(this);
        }

        dk[0].setIcon(new ImageIcon(getClass().getResource("/pic/C.png")));
        dk[1].setIcon(new ImageIcon(getClass().getResource("/pic/D.png")));
        dk[2].setIcon(new ImageIcon(getClass().getResource("/pic/E.png")));
        dk[3].setIcon(new ImageIcon(getClass().getResource("/pic/F.png")));
        dk[4].setIcon(new ImageIcon(getClass().getResource("/pic/G.png")));
        dk[5].setIcon(new ImageIcon(getClass().getResource("/pic/H.png")));

    }

    @Override
    public void run() {

        while (true) {
            now = 0;
            fileSys = FileSystemView.getFileSystemView();
            for (File f : File.listRoots()) {
                now++;
            }
            if (past != now) {
                this.removeAll();
                FileSystemView fs = FileSystemView.getFileSystemView();
                for (File f : File.listRoots()) {
                    String disk = fs.getSystemDisplayName(f).split("\\(")[1].substring(0, 1);
                    if (disk.equalsIgnoreCase("c")) {
                        this.add(dk[0]);
                    } else if (disk.equalsIgnoreCase("d")) {
                        this.add(dk[1]);
                    } else if (disk.equalsIgnoreCase("e")) {
                        this.add(dk[2]);
                    } else if (disk.equalsIgnoreCase("f")) {
                        this.add(dk[3]);
                    } else if (disk.equalsIgnoreCase("g")) {
                        this.add(dk[4]);
                    } else if (disk.equalsIgnoreCase("h")) {
                        this.add(dk[5]);
                    }
                }
                past = now;
                this.updateUI();
            }
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(dk[0])) {
            showFile.removeAll();
            new showIcon("root", 0, "C:\\").filelist(showFile, "C:\\");
            showFile.updateUI();
        } else if (e.getSource().equals(dk[1])) {
            showFile.removeAll();
            new showIcon("root", 0, "D:\\").filelist(showFile, "D:\\");
            showFile.updateUI();
        } else if (e.getSource().equals(dk[2])) {
            showFile.removeAll();
            new showIcon("root", 0, "E:\\").filelist(showFile, "E:\\");
            showFile.updateUI();
        } else if (e.getSource().equals(dk[3])) {
            showFile.removeAll();
            new showIcon("root", 0, "F:\\").filelist(showFile, "F:\\");
            showFile.updateUI();
        } else if (e.getSource().equals(dk[4])) {
            showFile.removeAll();
            new showIcon("root", 0, "G:\\").filelist(showFile, "G:\\");
            showFile.updateUI();
        } else if (e.getSource().equals(dk[5])) {
            showFile.removeAll();
            new showIcon("root", 0, "H:\\").filelist(showFile, "H:\\");
            showFile.updateUI();
        }
    }
}
