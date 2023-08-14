package brickBreaker;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args) {

        JFrame object = new JFrame();
        Gameplay gameplay = new Gameplay();
        object.setTitle("Brick Breaker");
        object.setBounds(10,10,700,600);
        object.setResizable(false);
        object.setVisible(true);
        object.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        object.add(gameplay);

    }
}