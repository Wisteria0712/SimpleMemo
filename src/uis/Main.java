/*
 *  主界面
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
import java.util.Arrays;

public class Main extends JFrame implements ActionListener {
    //创建三个按钮
    JButton add = new JButton("添加");
    JButton update = new JButton("修改");
    JButton delete = new JButton("删除");
    //创建表格组件
    JTable table;
    //创建菜单的导入导出以及作者
    JMenuItem exportItem = new JMenuItem("导出");
    JMenuItem authorItem = new JMenuItem("作者简介");
    JMenuItem projectItem = new JMenuItem("项目简介");

    //构造方法
    public Main() throws IOException, ClassNotFoundException {
        //初始化界面
        initFrame();
        //初始化菜单
        initJMenuBar();
        //初始化组件
        initView();
        //使界面显示
        this.setVisible(true);
    }

    //事件控制，该方法是继承事件接口后自动重写的，管理着所有事件
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的组件
        Object obj = e.getSource();
        //获取选中行的index
        int row = table.getSelectedRow();
        //逻辑判断
        if (obj == add) {
            //添加按钮
            System.out.println("添加按钮被点击了！");
            this.setVisible(false);
            new Add();
        } else if (obj == update) {
            //修改按钮
            System.out.println("修改按钮被点击了！");
            if (row == -1) {
                //没有选择
                System.out.println("没有选择！");
                MyJDialog.showJDialog("未选择", 200, 150, jDialog);
            } else {
                //选择了
                System.out.println("已选中行的索引为：" + row);
                this.setVisible(false);
                try {
                    new Update(row);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (obj == delete) {
            //删除按钮
            System.out.println("删除按钮被点击了！");
            if (row == -1) {
                //没有选择
                System.out.println("没有选择任何行！");
                MyJDialog.showJDialog("未选择", 200, 150, jDialog);
            } else {
                //选择了
                System.out.println("已选中行的索引为：" + row);
                if (showChooseJDialog() == 0) {
                    //确定删除
                    System.out.println("确定删除！");
                    //调用删除方法并刷新窗口
                    General.deleteMemo(General.readMemoFile(), row);
                    this.dispose();
                    try {
                        new Main();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        } else if (obj == projectItem) {
            //简介选项
            System.out.println("简介选项被调用了！");
            MyJDialog.showJDialog("在“功能”菜单中，有导出选项，该选项可以将备忘录内容导出。", 400, 250, jDialog);
        } else if (obj == exportItem) {
            //导出选项
            System.out.println("导出选项被调用了！");
            //调用导出方法
            long start = System.currentTimeMillis();
            try {
                General.download();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            long end = System.currentTimeMillis();
            String show = "文件已成功导出至您的桌面！用时 " + (end - start) + " ms";
            System.out.println(show);
            MyJDialog.showJDialog(show, 300, 200, jDialog);
        } else if (obj == authorItem) {
            //作者简介选项
            System.out.println("作者简介选项被调用了！");
            MyJDialog.showJDialog("本人是一名软件技术在读学生,这个小项目单纯用于练手", 400, 250, jDialog);
        }
    }

    //初始化界面
    private void initFrame() {
        //设置界面的宽高
        this.setSize(600, 600);
        //设置界面的标题
        this.setTitle("备忘录");
        //设置界面置顶(在最顶层显示)
        this.setAlwaysOnTop(true);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置，只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);
        //设置背景颜色
        this.getContentPane().setBackground(new Color(212, 212, 212));
        //设置窗体在屏幕上居中显示
        this.setLocationRelativeTo(null);
        //设置窗体大小不可变
        this.setResizable(false);
    }

    //初始化菜单
    private void initJMenuBar() {
        //创建整个的菜单对象
        JMenuBar JMenuBar = new JMenuBar();
        //创建菜单上面的两个选项的对象(功能 关于我们)
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        //把导入导出以及作者添加到saveJMenu中
        functionJMenu.add(exportItem);
        aboutJMenu.add(authorItem);
        aboutJMenu.add(projectItem);
        //将两个菜单选项添加到菜单栏
        JMenuBar.add(functionJMenu);
        JMenuBar.add(aboutJMenu);
        //绑定点击事件
        exportItem.addActionListener(this);
        authorItem.addActionListener(this);
        projectItem.addActionListener(this);
        //给菜单设置颜色
        JMenuBar.setBackground(new Color(230, 230, 230));
        //给整个界面设置菜单
        this.setJMenuBar(JMenuBar);
    }

    //初始化组件
    private void initView() throws IOException, ClassNotFoundException {
        //定义页面最上方标题
        JLabel title = new JLabel("备忘录");
        title.setBounds(240, 20, 584, 50);
        title.setFont(new Font("宋体", Font.BOLD, 32));
        //将标题添加到窗体中
        this.getContentPane().add(title);
        //定义表格标题
        Object[] tableTitles = new Object[]{"标题", "正文"};
        //定义表格内容(二维数组中的每一个一维数组就是一行数据)
        Object[][] tableData = exchangeData(General.readMemoFile());
        //定义表格组件(并在表格设置标题和内容)
        table = new JTable(tableData, tableTitles);
        table.setBounds(40, 70, 504, 380);
        //限定表格的列不能被拖动
        table.getTableHeader().setReorderingAllowed(false);
        //创建可滚动组件，并把表格组件放在滚动组件中间(如果表格中数据过多，可以进行上下滚动)
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 70, 504, 380);
        //将表格滚动组件添加到窗体中
        this.getContentPane().add(scrollPane);
        //给三个按钮设置宽高位置
        add.setBounds(40, 466, 140, 40);
        update.setBounds(222, 466, 140, 40);
        delete.setBounds(404, 466, 140, 40);
        //给三个按钮设置属性
        add.setFont(new Font("宋体", Font.PLAIN, 24));
        update.setFont(new Font("宋体", Font.PLAIN, 24));
        delete.setFont(new Font("宋体", Font.PLAIN, 24));
        //给三个按钮添加点击事件
        add.addActionListener(this);
        update.addActionListener(this);
        delete.addActionListener(this);
        //将三个按钮添加到窗体中
        this.getContentPane().add(add);
        this.getContentPane().add(update);
        this.getContentPane().add(delete);
    }

    /*
     *  初始化数据
     **/
    public Object[][] exchangeData(ArrayList<Memo> data) {
        int size = data.size();
        Object[][] dataArray = new Object[size][2];
        int rowIndex = 0;
        for (Memo memo : data) {
            dataArray[rowIndex][0] = memo.getTitle();
            dataArray[rowIndex][1] = memo.getContent();
            rowIndex++;
        }
        System.out.println(Arrays.deepToString(dataArray));
        return dataArray;
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
        String showChooseJDialogMessage = "是否删除选中数据？";
        String showChooseJDialogTitle = "删除信息确认";
        return JOptionPane.showConfirmDialog(this, showChooseJDialogMessage, showChooseJDialogTitle, JOptionPane.YES_NO_OPTION);
    }
}
