package csc;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main extends JFrame {

    showDiskPane showDisk;
    showFilePane showFile;
    showToolPane showTool;

    Main() {
        this.setSize(725, 510);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setTitle("文件管理器");

        showFile = new showFilePane();
        showTool = new showToolPane(showFile);
        showDisk = new showDiskPane(showFile);

        JScrollPane scrollPane = new JScrollPane(showFile);
        scrollPane.setPreferredSize(new Dimension(100, 200));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(80, 0, 640, 405);
        scrollPane.setVisible(true);

        this.add(showDisk);
        this.add(scrollPane);
        this.add(showTool);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        }
        Main MyFile = new Main();
        MyFile.setVisible(true);
        MyFile.showDisk.run();
    }

}
