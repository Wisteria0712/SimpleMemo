package views;

import javax.swing.*;

public class MyJDialog extends JDialog {
    public MyJDialog() {
    }

    //展示弹框
    public static void showJDialog(String content, int width, int height, MyJDialog myJDialog) {
        if (!myJDialog.isVisible()) {
            //创建一个弹框对象
            JDialog jDialog = new JDialog();
            //给弹框设置大小
            jDialog.setSize(width, height);
            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭永远无法操作下面的界面
            jDialog.setModal(true);
            //创建JLabel对象管理文字并添加到弹框中
            JLabel warning = new JLabel(content);
            warning.setHorizontalAlignment(SwingConstants.CENTER);
            jDialog.getContentPane().add(warning);
            //设置弹窗大小不可变
            jDialog.setResizable(false);
            //让弹框显示出来
            jDialog.setVisible(true);
        }
    }
}
