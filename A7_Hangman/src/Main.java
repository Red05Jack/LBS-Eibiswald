public class Main {
    public static void main(String[] args) {
        String[] wordList = {"rindfleischetikettierungsüberwachungsaufgabenübertragungsgesetz", "java", "computer", "spiel", "entwicklung"};
        Hangman game = new Hangman(wordList[(int)(Math.random() * wordList.length)], 6);
        new HangmanGUI(game);
    }
}
