/*
 *  通用资源
 *  @author Wisteria
 **/
package commons;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;

public class General {
    // 数据文件根目录
    public static final String DFU = "src/data.txt";

    // 获取当前用户桌面地址
    public static String getHomeURl() {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        return desktopDir.getAbsolutePath();
    }

    // 读取数据
    public static ArrayList<Memo> readMemoFile() {
        File file = new File(DFU);
        ArrayList<Memo> memos;
        try {
            if (file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DFU))) {
                    memos = (ArrayList<Memo>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("无法识别的类", e);
                }
            } else {
                // 文件不存在或为空，创建一个空的ArrayList并写入文件
                memos = new ArrayList<>();
                // 写入空的ArrayList到文件
                saveAllMemos(memos);
            }
        } catch (IOException e) {
            throw new RuntimeException("读取文件时发生错误", e);
        }
        return memos;
    }

    // 写入单条数据
    public static void saveOneMemo(String title, String content) throws IOException {
        // 读取现有数据
        ArrayList<Memo> memos = readMemoFile();
        // 添加新数据
        memos.add(new Memo(title, content));
        // 保存所有数据
        saveAllMemos(memos);
    }

    // 写入多条数据
    public static void saveAllMemos(ArrayList<Memo> memos) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DFU));
        oos.writeObject(memos);
        oos.close();
    }

    // 删除单条数据
    public static ArrayList<Memo> deleteMemo(ArrayList<Memo> memos, int index) {
        memos.remove(index);
        try {
            // 更新文件
            saveAllMemos(memos);
        } catch (IOException e) {
            throw new RuntimeException("删除数据时发生错误", e);
        }
        return memos;
    }

    // 导出数据到本地
    public static void download() throws IOException, ClassNotFoundException {
        ArrayList<Memo> memos = readMemoFile();
        String downloadUrl = getHomeURl() + "/备忘录.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(downloadUrl));
        bw.write("                              备忘录");
        bw.newLine();
        bw.write("==============================");
        bw.newLine();
        for (Memo memo : memos) {
            bw.write(memo.download());
            bw.newLine();
        }
        bw.close();
    }
}