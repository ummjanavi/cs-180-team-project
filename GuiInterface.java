import java.io.IOException;
import java.net.UnknownHostException;

public interface GuiInterface {
    public static void main(String[] args) throws IOException, ClassNotFoundException, UnknownHostException {
    }
    public actionPerformed(ActionEvent e);
    public itemStateChanged(ItemEvent e);
    public mousePressed(MouseEvent e);
    keyPressed(KeyEvent e);
    focusGained(FocusEvent e);
    windowOpened(WindowEvent e);
}
