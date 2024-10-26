/*
 *  添加界面
 *  @author Wisteria
 **/
package uis;

import commons.General;
import views.MyJDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Add extends JFrame implements ActionListener {
    //定义标题输入框
    JTextField titleText = new JTextField();
    //定义内容的输入区域
    JTextArea contentText = new JTextArea();
    //定义保存按钮
    JButton save = new JButton("保存");
    //定义取消按钮
    JButton cancel = new JButton("取消");

    //构造方法
    public Add() {
        //初始化界面
        initFrame();
        //初始化组件
        initView();
        //使界面显示
        this.setVisible(true);
    }

    //事件控制，该方法是继承事件接口后自动重写的，管理着所有事件
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取触发事件的对象
        Object obj = e.getSource();
        if (obj == save) {
            //保存按钮
            System.out.println("保存按钮被点击了！");
            /*
             *  如果点击保存按钮，就要获取输入框里面的内容
             *  如果输入内容为空则提示必须输入内容
             **/
            String titleText = this.titleText.getText();
            System.out.println("标题：" + titleText);
            String contentText = this.contentText.getText();
            System.out.println("内容：" + contentText);
            //进行非空判断
            if (titleText.trim().isEmpty() || contentText.trim().isEmpty()) {
                //内容有空
                MyJDialog.showJDialog("标题与内容均不可以为空！", 200, 150, jDialog);
            } else {
                //内容非空
                if (showChooseJDialog() == 0) {
                    System.out.println("确认添加！");
                    //调用保存数据方法
                    try {
                        General.saveOneMemo(titleText, contentText);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    this.setVisible(false);
                    try {
                        new Main();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    System.out.println("取消添加！");
                }
            }
        } else if (obj == cancel) {
            //取消按钮
            System.out.println("取消按钮被点击了！");
            this.setVisible(false);
            try {
                new Main();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //初始化界面
    private void initFrame() {
        //设置界面的宽高
        this.setSize(600, 600);
        //设置界面的标题
        this.setTitle("备忘录(添加备忘录)");
        //设置界面的置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置，只有取消了才会按照XY轴的方式添加组件
        this.setLayout(null);
        //设置背景颜色
        this.getContentPane().setBackground(new Color(212, 212, 212));
        //设置窗体大小不可变
        this.setResizable(false);
    }

    //初始化组件
    private void initView() {
        //定义最上面的每日一记
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
        titleText.setBounds(120, 90, 426, 30);
        titleText.setFont(new Font("宋体", Font.PLAIN, 16));
        this.getContentPane().add(titleText);
        //设置内容的输入框
        contentText.setBounds(120, 140, 426, 300);
        contentText.setFont(new Font("宋体", Font.PLAIN, 16));
        this.getContentPane().add(contentText);
        //设置保存按钮
        save.setBounds(132, 466, 140, 40);
        save.setFont(new Font("宋体", Font.PLAIN, 24));
        save.addActionListener(this);
        this.getContentPane().add(save);
        //设置取消按钮
        cancel.setBounds(312, 466, 140, 40);
        cancel.setFont(new Font("宋体", Font.PLAIN, 24));
        cancel.addActionListener(this);
        this.getContentPane().add(cancel);
    }

    //只创建一个弹框对象
    MyJDialog jDialog = new MyJDialog();

    /*
     *  作用：展示一个带有确定和取消按钮的弹框
     *  方法的返回值：
     *      0--确定
     *      1--取消
     *      该弹框用于用户删除的时候，询问是否确定删除
     **/
    public int showChooseJDialog() {
        String showChooseJDialogMessage = "新增该条数据？";
        String showChooseJDialogTitle = "新增信息确认";
        return JOptionPane.showConfirmDialog(this, showChooseJDialogMessage, showChooseJDialogTitle, JOptionPane.YES_NO_OPTION);
    }
}
