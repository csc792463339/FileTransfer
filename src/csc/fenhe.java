package csc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author CSC
 */
public class fenhe {

    int ok = 0;

    public File[] fen(File fromFile, File toFile) {
        FileInputStream fis = null;
        File[] fl = null;
        try {
            if (!toFile.isDirectory()) {
                toFile.mkdirs();
            }
            int length = (int) fromFile.length();
            int size = length / 10;
            String newfengeFile = fromFile.getAbsolutePath();
            int fileNew = newfengeFile.lastIndexOf(".");
            String strNew = fromFile.getName();
            fis = new FileInputStream(fromFile);
            fl = new File[10];
            FileOutputStream fos;
            for (int i = 0; i < 10; i++) {
                fl[i] = new File(toFile.getAbsolutePath() + "\\" + strNew + ".temp" + i);
                if (fl[i].exists() && fl[i].length() == size) {
                    System.out.println(i + "------分 好的");
                    ok = ok + 5;
                    continue;
                }
                if (!fl[i].isFile()) {
                    fl[i].createNewFile();
                }
                fos = new FileOutputStream(fl[i]);
                byte[] bl = new byte[size];
                fis.read(bl);
                fos.write(bl);
                fos.flush();
                fos.close();
                ok = ok + 5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fl;
    }

    public void he(File[] file) {
        try {
            String[] name = file[0].getName().split("\\.");
            String nn = name[0] + "." + name[1];
            File heBingFile = new File(file[0].getParent() + "\\" + nn);
            FileOutputStream fos = null;
            int okSize = 0;
            if (!heBingFile.exists()) {
                System.out.println("不存在");
                if (!heBingFile.isFile()) {
                    heBingFile.createNewFile();
                    System.out.println("第一次创建完成");
                }
                fos = new FileOutputStream(heBingFile);
            } else {
                System.out.println("已存在");
                okSize = (int) heBingFile.length() / (int) file[0].length();
                System.out.println("okSize" + okSize);
                fos = new FileOutputStream(heBingFile, true);
            }
            for (int i = 0; i < 10; i++) {
                if (i < okSize) {
                    ok = ok + 5;
                    continue;
                }
                if (i != 0) {
                    fos = new FileOutputStream(heBingFile, true);
                }
                System.out.println(i + "------正在合并");
                FileInputStream fis = new FileInputStream(file[i]);
                int len = (int) file[i].length();
                byte[] bRead = new byte[len];
                fis.read(bRead);
                fos.write(bRead);
                fis.close();
                System.out.println(i + "------he ok");
                fos.close();
                ok = ok + 5;
                
                
            }
            for (int i = 0; i < file.length; i++) {
                file[i].delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
