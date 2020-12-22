import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements Runnable {
    // Text field for receiving message
    private JTextField jtf = new JTextField();

    // Text area to display contents
    private JTextArea jta = new JTextArea();
    // Button for send massage
    private JButton jbSend = new JButton("Send");
    // IO streams
    private DataOutputStream toServer;
    private DataInputStream fromServer;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d = new Date();
    String time = format.format(d);

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        // Panel p to hold the label and text field
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Message"), BorderLayout.WEST);
        p.add(jtf, BorderLayout.CENTER);
        jtf.setHorizontalAlignment(JTextField.LEFT);
        p.add(jbSend, BorderLayout.EAST);
        setLayout(new BorderLayout());
        add(p, BorderLayout.SOUTH);
        add(jta, BorderLayout.CENTER);
        jtf.addActionListener(new ButtonListener()); // Register listener
        jbSend.addActionListener(new ButtonListener()); // Register listener
        setLocationRelativeTo(null);// 设置窗体居中
        setTitle("Client");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!


    }

    public void run() {
        try {
            // Create a socket to connect to the Server
            Socket socket = new Socket("localhost", 8989);

            // Create an input stream to receive data from the Server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the Server
            toServer = new DataOutputStream(socket.getOutputStream());

            while (true) {
                // Get message from the Server
                String message2 = fromServer.readUTF();
                jta.append(" " + time + '\n');
                jta.append(" Message: " + message2 + '\n');
            }
        } catch (IOException ex) {
            jta.append(" ***连接服务器失败!*** "+ '\n');
        }
    }

    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                // Get the message from the text field
                String message = jtf.getText().trim();

                // Send the message to the Server
                toServer.writeUTF(message);
                toServer.flush();

                // Display to the text area
                jta.append(" " + time + '\n');
                jta.append(" Me: " + message + "\n");
                jtf.setText(null);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}