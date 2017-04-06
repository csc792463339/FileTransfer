package csc;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CSC
 */
public class showIcon extends JComponent {

    JButton b;
    JLabel t;
    ImageIcon ic;
    JCheckBox cb;

    showIcon(String n, int type, String p) {
        this.setPreferredSize(new Dimension(70,90));
        cb = new JCheckBox();
        cb.setBounds(5, 32, 20, 20);
        cb.setVisible(false);
        this.add(cb);
        b = new JButton();
        b.setName(p);
        b.setBorder(null);
        b.setBounds(10, 5, 40, 40);
        b.setBackground(new Color(255, 255, 255));
        if (type != 0) {
            b.setIcon(getBigIcon(new File(p)));
        } else {
            cb.setSize(0, 0);
            b.setIcon(new ImageIcon(getClass().getResource("/pic/up.png")));
        }

        b.setOpaque(false);
        b.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    File tf = new File(b.getName());
                    if (tf.isDirectory()) {
                        JPanel t = (JPanel) b.getParent().getParent();
                        t.removeAll();;
                        filelist(t, b.getName());
                        t.updateUI();
                    }
                }
                if (cb.isSelected()) {
                    cb.setSelected(false);
                } else {
                    cb.setVisible(true);
                    cb.setSelected(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b.setBorder(null);
            }
        });
        this.add(b);
        t = new JLabel(n);
        t.setBorder(null);
        t.setBounds(10, 50, 60, 40);
        this.add(t);
    }

    static void filelist(JPanel p, String path) {
        
        if(new File(path).getParent()!=null){
        p.add(new showIcon("UP",0,new File(path).getParent()));
        }
               
        File f = new File(path);
        String tn;
        File[] c = f.listFiles();
        for (int i = 0; i < c.length; i++) {
            tn = c[i].getName();
            if (tn.length() > 16) {
                tn = "<html>" + tn.substring(0, 8) + "<br>" + tn.substring(8, 16) + "..." + "</html>";
            } else if (tn.length() > 8) {
                tn = "<html>" + tn.substring(0, 8) + "<br>" + tn.substring(8, tn.length()) + "</html>";
            }
            if (c[i].isDirectory()) {
                p.add(new showIcon(tn, 1, c[i].getPath()));
            } else {
                p.add(new showIcon(tn, 2, c[i].getPath()));
            }
            tn = "";
        }
    }

    private static Icon getBigIcon(File f) {
        if (f != null && f.exists()) {
            try {
                sun.awt.shell.ShellFolder sf = sun.awt.shell.ShellFolder.getShellFolder(f);
                return (new ImageIcon(sf.getIcon(true)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return (null);
    }
}
