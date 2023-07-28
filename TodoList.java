import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * やることを保存し時間制限を設けたクラス
 * 
 * @author E.Ryota
 */
public class TodoList extends Object implements ActionListener{
    JTextField textbox;
    JButton button;
    JLabel label;

    /**
     * 出力ファイル名
     */
    public static final String FILENAME = "todolist.csv";

    /**
     * CSVファイルのエンコーディング
     */
    public static final String CSV_ENCODING = "SJIS";

    public void perform() {

        // ウィンドウの準備
        JFrame mainFrame = new JFrame("TODO_List");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 200);
        mainFrame.setLayout(new GridLayout(2, 1));

        button = new JButton("やること追加");
        button.addActionListener(this);

        textbox = new JTextField(40);

        label = new JLabel();
        
        mainFrame.add(textbox);
        mainFrame.add(button);
        mainFrame.add(label);
        // ウィンドウの表示
        mainFrame.setVisible(true);

    }

     /**
     * TODOリストを記録する。
     *
     * @param 
     * @throws 
     */
    public static void recordTodolist(String todolist)
        throws IOException {

        System.out.println(todolist);

        boolean isAppending = true;   // 追記モードでファイルを開く

        try (
            BufferedWriter aWriter = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(
                        new File(TodoList.FILENAME),
                        isAppending
                    ),
                    TodoList.CSV_ENCODING
                )
            )
        ) {
            aWriter.write(todolist);   // 書き出しを依頼する
            aWriter.newLine();          // 改行を依頼する
            aWriter.flush();            // 書き出しを強制的に完了させる
                                        // close()は不要（try-with-resources文）

        } catch (IOException anException) {
            throw anException;              // 呼び出し元にそのまま投げる
        }
    }

    public void actionPerformed(ActionEvent e){
        label.setText(textbox.getText());
        try{
            recordTodolist(textbox.getText());
        } catch (IOException anException) {
            System.out.println("エラー：ファイルの入出力で問題が発生しました。");
            anException.printStackTrace();
        }
  
    }
    
}
