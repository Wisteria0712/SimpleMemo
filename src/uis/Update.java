/*
 *  修改界面
 *  @author Wisteria
 **/
package uis;

import commons.General;
import commons.Memo;
import views.MyJDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Update extends JFrame implements ActionListener {
    //定义标题输入框
    JTextField titleText = new JTextField();
    //定义内容区域的输入区域
    JTextArea contentText = new JTextArea();
    //定义修改按钮
    JButton update = new JButton("修改");
    //定义取消按钮
    JButton cancel = new JButton("取消");
    //定义Tip数据
    ArrayList<Memo> data = General.readMemoFile();
    //定义目标对象
    Memo ObjectTips;

    //构造方法
    public Update(int index) throws IOException, ClassNotFoundException {
        //初始化界面
        initFrame();
        //初始化组件
        initView(index);
        //使界面显示出来
        this.setVisible(true);
        //获取目标对象
        this.ObjectTips = data.get(index);
    }

    //事件控制，该方法是继承事件接口后自动重写的，管理着所有事件
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取触发事件对象
        Object obj = e.getSource();
        if (obj == update) {
            //修改操作
            System.out.println("修改按钮被点击了！");
            String title = titleText.getText();
            System.out.println("修改后的标题：" + title);
            ObjectTips.setTitle(title);
            String content = contentText.getText();
            System.out.println("修改后的内容：" + content);
            ObjectTips.setContent(content);
            //进行非空判断
            if (title.trim().isEmpty() || content.trim().isEmpty()) {
                //内容有空
                MyJDialog.showJDialog("标题与内容均不可以为空！", 200, 150, jDialog);
            } else {
                //内容非空
                if (showChooseJDialog() == 0) {
                    System.out.println("确认修改！");
                    //调用保存数据方法(保存整个集合)
                    try {
                        General.saveAllMemos(data);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    System.out.println("取消修改！");
                }
            }
        } else if (obj == cancel) {
            //取消按钮
            System.out.println("取消按钮被点击了！");
        }
        this.dispose();
        try {
            new Main();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    //初始化界面
    private void initFrame() {
        //设置界面的宽高
        this.setSize(600, 600);
        //设置界面的标题
        this.setTitle("备忘录(修改备忘录)");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置，只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);
        //设置背景颜色
        this.getContentPane().setBackground(new Color(212, 212, 212));
        //设置窗体大小不可变
        this.setResizable(false);
    }

    //初始化组件
    private void initView(int index) throws IOException {
        //获取目标记录
        Memo obj = data.get(index);
        //定义最上面的标题
        JLabel title = new JLabel("备忘录");
        title.setBounds(240, 20, 584, 50);
        title.setFont(new Font("宋体", Font.BOLD, 32));
        this.getContentPane().add(title);
        //定义文字：标题
        JLabel subject = new JLabel("标题");
        subject.setBounds(70, 90, 100, 30);
        subject.setFont(new Font("宋体", Font.PLAIN, 16));
        this.getContentPane().add(subject);
        //定义文字：内容
        JLabel content = new JLabel("内容");
        content.setBounds(70, 140, 100, 30);
        content.setFont(new Font("宋体", Font.PLAIN, 16));
        this.getContentPane().add(content);
        //设置标题的输入框
        titleText.setText(obj.getTitle());
        titleText.setBounds(120, 90, 426, 30);
        titleText.setFont(new Font("宋体", Font.PLAIN, 16));
        this.getContentPane().add(titleText);
        //设置内容的输入框
        contentText.setText(obj.getContent());
        contentText.setBounds(120, 140, 426, 300);
        contentText.setFont(new Font("宋体", Font.PLAIN, 16));
        this.getContentPane().add(contentText);
        //设置保存按钮
        update.setBounds(132, 466, 140, 40);
        update.setFont(new Font("宋体", Font.PLAIN, 24));
        update.addActionListener(this);
        this.getContentPane().add(update);
        //设置取消按钮
        cancel.setBounds(312, 466, 140, 40);
        cancel.setFont(new Font("宋体", Font.PLAIN, 24));
        cancel.addActionListener(this);
        this.getContentPane().add(cancel);
    }

    /*
     *  作用：展示一个带有确定和取消按钮的弹框
     *  方法的返回值：
     *      0--确定
     *      1--取消
     *      该弹框用于用户删除的时候，询问是否确定删除
     **/
    public int showChooseJDialog() {
        String showChooseJDialogMessage = "是否修改选中数据？";
        String showChooseJDialogTitle = "修改操作确认";
        return JOptionPane.showConfirmDialog(this, showChooseJDialogMessage, showChooseJDialogTitle, JOptionPane.YES_NO_OPTION);
    }

    //只创建一个弹框对象
    MyJDialog jDialog = new MyJDialog();
}
