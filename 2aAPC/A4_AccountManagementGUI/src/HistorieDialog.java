
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistorieDialog extends JDialog {
    private JTextArea historieArea;

    public HistorieDialog(JFrame parent, List<String> historie) {
        super(parent, "Historie", true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        historieArea = new JTextArea();
        historieArea.setEditable(false);

        // Display transaction history
        for (String eintrag : historie) {
            historieArea.append(eintrag + "\n");
        }

        add(new JScrollPane(historieArea), BorderLayout.CENTER);
    }
}
