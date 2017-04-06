package csc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CSC
 */
public class task extends JPanel implements MouseListener {

    JLabel name, start, stop, delete;
    JProgressBar bar;
    Thread t1, t2;
    fenhe f;
    String n, from, to;
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/pic/30030.png"));
        g.drawImage(icon.getImage(), 0, 0, getSize().width, getSize().height, this);
    }
    task(String n, String form, String to, int auto) {
        this.n = n;
        this.from = form;
        this.to = to;
        System.out.println("ren wu zheng zai " + n);
        this.setPreferredSize(new Dimension(300, 30));
        this.setLayout(null);
        this.setBackground(Color.red);
        start = new JLabel();
        start.setBounds(5, 5, 24, 24);
        start.setBorder(null);
        start.setBackground(new Color(255, 255, 255));
        start.setIcon(new ImageIcon(getClass().getResource("/pic/start.png")));
        start.addMouseListener(this);

        this.add(start);
        stop = new JLabel();
        stop.setBounds(30, 5, 24, 24);
        stop.setBorder(null);
        stop.setIcon(new ImageIcon(getClass().getResource("/pic/stop.png")));
        stop.setBackground(new Color(255, 255, 255));
        stop.addMouseListener(this);
        this.add(stop);

        delete = new JLabel();
        delete.setBounds(55, 5, 24, 24);
        delete.setBorder(null);
        delete.setBackground(new Color(255, 255, 255));
        delete.setIcon(new ImageIcon(getClass().getResource("/pic/delete.png")));
        delete.addMouseListener(this);
        this.add(delete);

        name = new JLabel();
        name.setBounds(80, 0, 120, 30);
        name.setText(n);

        this.add(name);
        bar = new JProgressBar();
        bar.setBounds(200, 0, 100, 30);
        bar.setValue(0);
        bar.setStringPainted(true);
        bar.setMaximum(100);
        this.add(bar);
        f = new fenhe();
        t1 = new Thread() {
            public void run() {
                File fen, jie;
                fen = null;
                jie = null;
                f = new fenhe();
                fen = new File(form);
                jie = new File(to);
                f.he(f.fen(fen, jie));
            }
        };

        t2 = new Thread() {
            public void run() {
                while (f.ok != 100) {
                    bar.setValue(f.ok);
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                    }
                }
                new saveTask().delTask(n, from, to);
                bar.setValue(100);
                name.getParent().setVisible(false);

            }
        };
        if (auto == 1) {
            t1.start();
            t2.start();
        }
    }

    @Override

    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(start)) {
            t1.start();
            t2.start();
        }
        if (e.getSource().equals(stop)) {
            t1.stop();
            t2.stop();
            t1 = new Thread() {
                public void run() {
                    File fen, jie;
                    fen = null;
                    jie = null;
                    f = new fenhe();
                    fen = new File(from);
                    jie = new File(to);
                    f.he(f.fen(fen, jie));

                }
            };
            t2 = new Thread() {
                public void run() {
                    while (f.ok != 100) {
                        bar.setValue(f.ok);
                        try {
                            sleep(100);
                        } catch (InterruptedException ex) {
                        }
                    }
                    new saveTask().delTask(n, from, to);
                    bar.setValue(100);
                    name.getParent().setVisible(false);
                }
            };
        }
        if (e.getSource().equals(delete)) {
            t1.stop();
            t2.stop();
            String apath = to + "\\" + n + ".temp";
            for (int i = 0; i < 10; i++) {
                File del = new File(apath + i);
                System.out.println(apath + i);
                if (del.exists()) {
                    del.setWritable(true);
                    del.delete();
                }
            }
            new saveTask().delTask(n, from, to);
            this.setVisible(false);
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
