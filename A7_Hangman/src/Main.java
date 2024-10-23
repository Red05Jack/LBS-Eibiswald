import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Pfad zur Datei mit den Wörtern
        String filePath = "words.txt";  // Dies ist der Dateipfad zur Textdatei mit den Wörtern

        // Lade die Wörter aus der Datei
        List<String> wordList = loadWordsFromFile(filePath);

        // Überprüfen, ob Wörter geladen wurden
        if (wordList.isEmpty()) {
            System.out.println("Keine Wörter geladen. Überprüfen Sie die Datei.");
            return;
        }

        // Erstelle das Hangman-Spiel mit dem zufälligen Wort und 11 Versuchen
        Hangman game = new Hangman(wordList);
        new HangmanGUI(game);
    }

    // Methode zum Laden der Wörter aus einer Datei
    public static List<String> loadWordsFromFile(String filePath) {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Füge jedes Wort in der Liste hinzu
                words.add(line.trim());  // trim() entfernt führende oder nachfolgende Leerzeichen
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Datei: " + e.getMessage());
        }

        return words;
    }
}
