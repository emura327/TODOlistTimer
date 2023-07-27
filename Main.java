/**
 * グラフィカルな時計を表示するアプリケーション。
 * 
 * @author M.Nogi
 */
public class Main extends Object {
    
    /**
     * エントリポイント。
     * グラフィカルな時計を生成して実行する。
     * 
     * @param args 実行時引数。特に使用しない。
     */
    public static void main(String[] args) {
        
        WidgetClock aClock = new WidgetClock();

        aClock.perform();

    }

}