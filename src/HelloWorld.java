import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class HelloWorld {
    public HelloWorld() {
        JFrame frame = new JFrame("HelloWorld7");

        JLabel label = new JLabel(String.valueOf(new Date()));
        label.setFont(new Font("Serif", Font.PLAIN, 36));
        frame.add(label);
        frame.setSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new HelloWorld();
    }
}