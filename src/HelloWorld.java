import javax.swing.JFrame;
import java.awt.Dimension;

public class HelloWorld {
    public HelloWorld() {
        JFrame frame = new JFrame("HelloWorld7");
        frame.setSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new HelloWorld();
    }
}