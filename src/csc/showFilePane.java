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
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author CSC
 */
public class showFilePane extends JPanel {
//
    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/pic/6001000.png"));
        g.drawImage(icon.getImage(), 0, 0, getSize().width, getSize().height, this);

    }

    showFilePane() {
        this.setLayout(new FlowLayout(3));
        this.setVisible(true);
        this.setPreferredSize(new Dimension(425, 1000));
        new showIcon("root", 0, "D:\\").filelist(this, "D:\\");
    }

}
