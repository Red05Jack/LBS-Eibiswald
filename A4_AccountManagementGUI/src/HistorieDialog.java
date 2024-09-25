import javax.swing.*;
import java.awt.*;

public class HistorieDialog extends JDialog {
    private JTextArea historieArea;

    public HistorieDialog(JFrame parent) {
        super(parent, "Historie", true);
        setSize(300, 200);
        setLayout(new BorderLayout());

        historieArea = new JTextArea();
        historieArea.setText("Bisher keine Transaktionen");

        add(new JScrollPane(historieArea), BorderLayout.CENTER);
    }
}
