import view.Frame;
import java.io.*;
import java.util.Scanner;
import java.util.logging.*;
public class Main {
    private final static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        LogManager logManager = LogManager.getLogManager();
        try {
            logManager.readConfiguration(new FileInputStream("src/main/resources/log.properties"));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot get log configuration!" + ex.getMessage());
        }

        try {
            Scanner scan = new Scanner(new File("src/main/resources/about.txt"));
            StringBuilder about = new StringBuilder();

            while (scan.hasNext()) {
                about.append(scan.nextLine()).append("\n");
            }
            new Frame(about.toString(), logger);
        } catch (FileNotFoundException ignored) {}
    }
}
