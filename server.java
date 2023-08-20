/**
 * The server class creates a graphical user interface for a chat server and handles sending and
 * receiving messages.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class server implements ActionListener {
    JTextField text;
    JPanel panel2;
    static Box vertical = Box.createVerticalBox();
    static JFrame j = new JFrame();
    static DataOutputStream dout;

    server() {
        j.setLayout(null);

        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(99, 110, 137));
        panel.setBounds(0, 0, 450, 70);
        panel.setLayout(null);
        j.add(panel);

       
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image img2 = img.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon img3 = new ImageIcon(img2);
        JLabel back = new JLabel(img3);
        back.setBounds(10, 20, 25, 25);
        panel.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon dp1 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image dp2 = dp1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon dp3 = new ImageIcon(dp2);
        JLabel profile = new JLabel(dp3);
        profile.setBounds(55, 10, 50, 50);
        panel.add(profile);

        ImageIcon vdo1 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image vdo2 = vdo1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon vdo3 = new ImageIcon(vdo2);
        JLabel video = new JLabel(vdo3);
        video.setBounds(300, 20, 30, 30);
        panel.add(video);

        ImageIcon phn1 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image phn2 = phn1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon phn3 = new ImageIcon(phn2);
        JLabel phone = new JLabel(phn3);
        phone.setBounds(350, 20, 30, 30);
        panel.add(phone);

        ImageIcon dot1 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image dot2 = dot1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon dot3 = new ImageIcon(dot2);
        JLabel dots = new JLabel(dot3);
        dots.setBounds(390, 20, 30, 30);
        panel.add(dots);

        JLabel name = new JLabel("Server");
        name.setBounds(120, 20, 100, 20);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        panel.add(name);

        JLabel activeNow = new JLabel("Active Now");
        activeNow.setBounds(120, 35, 100, 20);
        activeNow.setForeground(Color.white);
        activeNow.setFont(new Font("SAN_SERIF", Font.PLAIN, 9));
        panel.add(activeNow);

        
        panel2 = new JPanel();
        panel2.setBounds(5, 75, 440, 570);
        j.add(panel2);

        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));
        j.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        send.addActionListener(this);
        send.setBackground(new Color(99, 110, 137));
        send.setForeground(Color.white);
        j.add(send);

        
        j.setSize(450, 700);
        j.setLocation(200, 50);
        j.setUndecorated(true);
        j.getContentPane().setBackground(Color.white);
        j.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {

            String out = text.getText();

            JPanel panel3 = formatLable(out);

            panel2.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(panel3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            panel2.add(vertical, BorderLayout.PAGE_END);
            text.setText("");

            dout.writeUTF(out);

            j.repaint();
            j.invalidate();
            j.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JPanel formatLable(String out) {
        JPanel panell = new JPanel();
        panell.setLayout(new BoxLayout(panell, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Tohoma", Font.PLAIN, 18));
        output.setForeground(new Color(255, 255, 255));
        output.setBackground(new Color(99, 110, 137));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10, 10, 10, 10));

        panell.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panell.add(time);

        return panell;
    }

    public static void main(String[] args) {
        new server();
        try {
            ServerSocket ss = new ServerSocket(6001);
            while (true) {
                Socket s = ss.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLable(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    j.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

}