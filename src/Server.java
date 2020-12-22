import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Server extends JFrame implements Runnable{
    DataInputStream inputFromClient;
    DataOutputStream outputToClient;
    // Button for send massage
    private JButton jbSend = new JButton("Send");
    // Text area for displaying contents
    private JTextArea jta = new JTextArea();
    // Text field for receiving radius
    private JTextField jtf = new JTextField();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d = new Date();
    String time = format.format(d);

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        // Place text area on the frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Message"), BorderLayout.WEST);
        p.add(jtf, BorderLayout.CENTER);
        jtf.setHorizontalAlignment(JTextField.LEFT);
        p.add(jbSend, BorderLayout.EAST);
        add(p, BorderLayout.SOUTH);
        jtf.addActionListener(new ButtonListener()); // Register listener
        jbSend.addActionListener(new ButtonListener()); // Register listener
        setTitle("Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!


    }
    public void run() {
        try {
            // Create a Server socket
            ServerSocket serverSocket = new ServerSocket(8989);
            jta.append(" ***Server started at  " + time +"***"+ '\n');

            // Listen for a connection request
            Socket socket = serverSocket.accept();

            // Create data input and output streams
            inputFromClient = new DataInputStream(socket.getInputStream());
            outputToClient = new DataOutputStream(socket.getOutputStream());

            while (true) {
                // Receive message from the Client
                String message = inputFromClient.readUTF();
                jta.append(" " + time + '\n');
                jta.append(" Message: " + message + '\n');
            }
        } catch (IOException ex) {
            jta.append(" ***端口已被占用!*** " + '\n');
        }
    }
    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                // Get the message from the text field
                String message = jtf.getText().trim();

                // Send the radius to the Server
                outputToClient.writeUTF(message);
                outputToClient.flush();

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