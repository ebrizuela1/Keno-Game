import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class GameEngine {
    Player user;
    private ArrayList<Integer> winningNumbers;

    GameEngine(Player user){
        this.user = user;
        }
    // Method will return the number of correct matches
    int checkTicket(ArrayList<Integer> playerNumbers,
                     ArrayList<Integer> winningNumbers){
        HashSet<Integer> commonNumbers = new HashSet<>(winningNumbers);

        // Keep all the winning numbers that were also seen in playerNumbers
        commonNumbers.retainAll(playerNumbers);

        // Returns the number of common numbers
        return commonNumbers.size();
    }

    // Generates and returns a winningNumbers array of size numSpots
    void setWinningNumbers(Integer numSpots){
        ArrayList<Integer> winning = new ArrayList<>();
        Random randNum = new Random();

        for (int i  = 0 ; i < numSpots ; i++){
                int randWinning = randNum.nextInt(80) + 1;
                // Ensure we do not add a number that is already in list
                while (winning.contains(randWinning)){
                    randWinning = randNum.nextInt(80) + 1;
                }
                winning.add(randWinning);
        }
        this.winningNumbers = winning;
    }

    ArrayList<Integer> getWinningNumbers(){
        return this.winningNumbers;
    }

    void userList(){
        System.out.println(this.user.getSelectedNumbers());
    }

    public Player getUser() {
        return this.user;
    }
}
