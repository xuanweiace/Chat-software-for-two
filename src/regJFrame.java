import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//完全用JFrame的BorderLayout和Box的Component去支撑，不需要JPanel了。


public class regJFrame extends JFrame{
    JLabel welcome = new JLabel("欢迎来到注册页面",JLabel.CENTER);
    JLabel feedback = new JLabel("这里是feedback");
    JTextField id = new JTextField();
    JTextField pwd = new JTextField();
    JTextField double_pwd = new JTextField();
    JButton confirm_btn = new JButton("确认注册");
    Box hBox1,hBox2,hBox3,hBox;
    Box vBox1, vBox2;
    FileIO file_control = new FileIO();
    public static void main(String[] args) {
        new regJFrame();
    }

    public regJFrame() throws HeadlessException {
        setTitle("****欢迎注册****    ----made by zxz");
        setSize(400,400);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5,5));
        welcome.setFont(new Font("楷体",Font.BOLD,30));
        //下面这两行的区别是？第二行应该和new JLabel("欢迎来到注册页面",JLabel.CENTER);是一样的效果，那第一行是啥效果？
//        welcome.setHorizontalTextPosition(JLabel.CENTER);
//        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setForeground(Color.BLUE);
        welcome.setBackground(Color.GRAY);
        welcome.setOpaque(true);//注意必须要有这句，先设置透明度，再改变颜色才有用。
        welcome.setPreferredSize(new Dimension(400,50));
        add(welcome,BorderLayout.CENTER);

        id.addFocusListener(new JTextFieldHintListener(id,"请输入账号..."));
        pwd.addFocusListener(new JTextFieldHintListener(pwd,"请输入密码..."));
        double_pwd.addFocusListener(new JTextFieldHintListener(double_pwd,"请再次输入密码..."));
        hBox1 = Box.createHorizontalBox();
        hBox1.add(new JLabel("账号：        "));
        hBox1.add(id);

        hBox2 = Box.createHorizontalBox();
        hBox2.add(new JLabel("密码：        "));
        hBox2.add(pwd);

        hBox3 = Box.createHorizontalBox();
        hBox3.add(new JLabel("确认密码："));
        hBox3.add(double_pwd);
        hBox3.add(confirm_btn);

        vBox1 = Box.createVerticalBox();
        vBox1.add(Box.createVerticalStrut(50));
        vBox1.add(hBox1);
        vBox1.add(Box.createVerticalStrut(20));
        vBox1.add(hBox2);
        vBox1.add(Box.createVerticalStrut(20));
        vBox1.add(hBox3);
        vBox1.add(Box.createVerticalStrut(50));
        vBox1.setPreferredSize(new Dimension(250,200));
        vBox2 = Box.createVerticalBox();
        vBox2.add(confirm_btn);
        feedback.setPreferredSize(new Dimension(200,50));
        vBox2.add(feedback);
        hBox = Box.createHorizontalBox();
        hBox.add(vBox1);
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(vBox2);
        add(hBox,BorderLayout.SOUTH);
        feedback.setOpaque(true);//注意必须要有这句
        feedback.setBackground(Color.GRAY);
        feedback.setFont(new Font("宋体",Font.BOLD,15));
        confirm_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str_id = id.getText();
                String str_pwd = pwd.getText();
                String str_pwd2 = double_pwd.getText();
                if(str_id.length()<5 || str_id.length()>10) {
                    //注意处理换行，这里用html标签进行
                    feedback.setText("<html><body>ID需要是5~10位的数字！<br>注册失败!</body></html>");
                    //feedback.setText("ID需要是5~10位的数字！"+"\n"+"注册失败！");
                }
                else if(file_control.isinFile(str_id,str_pwd,true)) {
                    feedback.setText("ID已存在！注册失败！");
                }
                else if(!str_pwd.equals(str_pwd2)) {
                    feedback.setText("<html><body>两次密码输入不一致！<br>注册失败!</body></html>");
                }
                else {
                    file_control.addtoFile(str_id,str_pwd);
                    feedback.setText("满足要求！注册成功！");
//这里有个问题啊，我想让他 "满足要求！注册成功！" 这一句只显示两秒，就回到"这里是feedback"这句。这咋实现啊？
//                    feedback.repaint();
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                    feedback.setText("这里是feedback");
                }
            }
        });
        pack();
        setVisible(true);
    }

}
