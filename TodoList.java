import javax.swing.JFrame;
import java.awt.GridLayout;

/**
 * やることを保存し時間制限を設けたクラス
 * 
 * @author E.Ryota
 */
public class TodoList extends Object{
    
    public void perform() {

        // ウィンドウの準備
        JFrame mainFrame = new JFrame("TODO_List");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 200);
        mainFrame.setLayout(new GridLayout(2, 1));

        // ウィンドウの表示
        mainFrame.setVisible(true);

        

    }
    
}
