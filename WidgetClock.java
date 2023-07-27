import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * ウィジェット部品を使用したグラフィカルな時計クラス。
 * 
 * @author M.Nogi
 */
public class WidgetClock extends Object {

    /**
     * ウィジェット部品（ラベルやボタンなど）を用いた時計ウィンドウを表示する。
     */
    public void perform() {
        
        // ウィンドウの準備
        JFrame mainFrame = new JFrame("WidgetClock");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 200);
        mainFrame.setLayout(new GridLayout(2, 1));

        // 時計ラベルの設定
        JLabel dateLabel = new JLabel();
        dateLabel.setFont(new Font("Consolas", Font.ITALIC, 48));
        dateLabel.setForeground(Color.BLUE);
        mainFrame.add(dateLabel);

        // 色変更ボタンの設定
        JButton aButton = new JButton("Change Color!!");
        aButton.setFont(new Font("Consolas", Font.PLAIN, 24));
        aButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent anEvent) {

                if (dateLabel.getForeground() == Color.BLUE) {
                    dateLabel.setForeground(Color.RED);
                } else {
                    dateLabel.setForeground(Color.BLUE);
                }

            }
        });
        mainFrame.add(aButton);

        // ウィンドウの表示
        mainFrame.setVisible(true);

        // 時刻更新処理の定義
        TimerTask aTimerTask = new TimerTask() {
            public void run() {
                Date aDate = new Date();
                dateLabel.setText(aDate.toString());
            }
        };

        // 時刻更新を１秒ごとに実行
        Timer aTimer = new Timer();
        aTimer.scheduleAtFixedRate(aTimerTask, 0, 1000);   // Timer#schedule() はNG
    }

}