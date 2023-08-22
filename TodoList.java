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
import java.util.ArrayList;
import java.util.Date;

/**
 * やることを保存し時間制限を設けたクラス
 * 
 * @author E.Ryota
 */
public class TodoList extends Object implements ActionListener{

    JTextField textbox;
    JButton addButton,finishButton;
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

        addButton = new JButton("やること追加");
        addButton.addActionListener(this);

        finishButton = new JButton("<html>完了ボタン<br>(テキストボックスに消したい項目の番号を入力してください)</html>");
        finishButton.addActionListener(this);

        textbox = new JTextField(40);
        textbox.addActionListener(this);

        label = new JLabel();
        label.setText(printTodoList());

        mainFrame.add(textbox);
        mainFrame.add(addButton);
        mainFrame.add(finishButton);
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
     * todoリストの表示
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
            int number = 1;

            while ((line = aReader.readLine()) != null) {
                String[] values = line.split(",");
                labelText += number + " ";
                number++;
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

    /**
     * 終了したToDoリストの項目を消すメソッド
     * @throws IOException
     */
    public void finishTodoList(String listNum) 
        throws IOException{

        int listNumber = Integer.parseInt(listNum);
        boolean notAppending = false;   // ファイルの最後に書き出す

        try (
            BufferedReader aReader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(
                        new File(TodoList.FILENAME)
                    ),
                    TodoList.CSV_ENCODING
                )
            )
            ){
                String line = null;
                ArrayList<String> toDoList = new ArrayList<String>();
                int listindex = 1;

                while ((line = aReader.readLine()) != null) {
                    if(listindex != listNumber){
                        toDoList.add(line);
                    }
                    listindex++;
                }

                try (
                    BufferedWriter aWriter = new BufferedWriter(
                        new OutputStreamWriter(
                            new FileOutputStream(
                                new File(TodoList.FILENAME),
                                notAppending
                            ),
                            TodoList.CSV_ENCODING
                        )
                    )
                ){
                    for(String l:toDoList){
                        aWriter.write(l);// 書き出しを依頼する
                        aWriter.newLine();         // 改行を依頼する
                    }
                    aWriter.flush();           // 書き出しを強制的に完了させるaWriter.
                } catch (IOException anException) {
                    throw anException;
                }


            } catch (IOException anException) {
                throw anException;
            }
    }

    public void actionPerformed(ActionEvent e){
        if (textbox.getText() != null) {
            if(e.getSource() == addButton || e.getSource() == textbox){
                try{
                    this.recordTodolist(textbox.getText());
                    label.setText(printTodoList());
                    textbox.setText("");
                } catch (IOException anException) {
                    System.out.println("エラー：ファイルの入出力で問題が発生しました。");
                    anException.printStackTrace();
                }
            }
            if (e.getSource() == finishButton) {
                try{
                    this.finishTodoList(textbox.getText());
                    label.setText(printTodoList());
                    textbox.setText("");
                }catch (IOException anException){
                    System.out.println("エラー：ファイルの入出力で問題が発生しました。");
                    anException.printStackTrace();
                }
        } else {
            System.out.println("エラーa");
        }
    }

   
    
}
}
