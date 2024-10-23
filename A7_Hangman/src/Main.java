public class Main {
    public static void main(String[] args) {
        Hangman hangmanGame = new Hangman("elephant", 6);

        System.out.println(hangmanGame.getRemainingAttempts());

        System.out.println(hangmanGame.guessLetterOrWord(""));

        System.out.println(hangmanGame.getRemainingAttempts());

        System.out.println(hangmanGame.guessLetterOrWord("e"));
        System.out.println(hangmanGame.guessLetterOrWord("x"));

        System.out.println(hangmanGame.getCurrentWordState());

        System.out.println(hangmanGame.isGameWon());
        System.out.println(hangmanGame.isGameOver());
        System.out.println(hangmanGame.getRemainingAttempts());
        System.out.println(hangmanGame.getWordToGuess());


        System.out.println(hangmanGame.isGameWon());
        System.out.println(hangmanGame.guessLetterOrWord("elephant"));


    }

}
