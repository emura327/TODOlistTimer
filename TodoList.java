import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    // 日付のフォーマット
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public void perform() 
        throws IOException{

        // ウィンドウの準備
        JFrame mainFrame = new JFrame("TODO_List");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 800);
        mainFrame.setLayout(new GridLayout(2, 1));

        button = new JButton("やること追加");
        button.addActionListener(this);

        textbox = new JTextField(40);
        textbox.addActionListener(addToDo);

        label = new JLabel();
        label.setText(printTodoList());

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
    public void recordTodolist(String todolist)
        throws IOException {

        todolist += "," + TodoList.dateFormat.format(new Date());

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
            aWriter.newLine();         // 改行を依頼する
            aWriter.flush();           // 書き出しを強制的に完了させる
                                       // close()は不要（try-with-resources文）

        } catch (IOException anException) {
            throw anException;              // 呼び出し元にそのまま投げる
        }
    }

    /**
     * 出退勤の記録を一覧表示する。
     *
     * @throws IOException ファイル入出力に不具合が生じた場合
     */
    public static String printTodoList() 
        throws IOException {

        try (
            BufferedReader aReader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(
                        new File(TodoList.FILENAME)
                    ),
                    TodoList.CSV_ENCODING
                )
            )
        ) {
            String line = null;
            String labelText = "<html>";

            while ((line = aReader.readLine()) != null) {
                String[] values = line.split(",");
                for(String e : values){
                    labelText += e + " ";
                }
                labelText += "<br>"; 
            }

            labelText += "</html>";
            
            return labelText;

        } catch (IOException anException) {
            throw anException;
        }
    }

    public void actionPerformed(ActionEvent e){
        try{
            this.recordTodolist(textbox.getText());
            label.setText(printTodoList());
        } catch (IOException anException) {
            System.out.println("エラー：ファイルの入出力で問題が発生しました。");
            anException.printStackTrace();
        }
  
    }

    private ActionListener addToDo = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try{
            recordTodolist(textbox.getText());
            label.setText(printTodoList());
        } catch (IOException anException) {
            System.out.println("エラー：ファイルの入出力で問題が発生しました。");
            anException.printStackTrace();
        }
        }
    };

   
    
}
