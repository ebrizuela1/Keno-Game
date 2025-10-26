import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

// Model Layer
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

    void submitKenoTicket(){
        this.setWinningNumbers();
        int totalWinningNumbers = this.checkTicket(this.user.getSelectedNumbers(), this.winningNumbers);
        if (totalWinningNumbers != 0) {
            System.out.println("YOU WON: " + totalWinningNumbers + "matches");
        } else {
            System.out.println("Loser");
        }
        System.out.println("User List : " + this.user.getSelectedNumbers());
        System.out.println("Winning List : " + this.winningNumbers);
    }

    // Generates and returns a winningNumbers array of size numSpots
    void setWinningNumbers(){
        int numSpots = this.user.getNumSpots();
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

    // Generates and returns a winningNumbers array of size numSpots
    ArrayList<Integer> getRandomNumbers(){
        int numSpots = this.user.getNumSpots();
        ArrayList<Integer> winning = new ArrayList<>();

        // Only run this if the user arlready set the numSpots
        if (numSpots > 0) {
            Random randNum = new Random();

            for (int i = 0; i < numSpots; i++) {
                int randWinning = randNum.nextInt(80) + 1;
                // Ensure we do not add a number that is already in list
                while (winning.contains(randWinning)) {
                    randWinning = randNum.nextInt(80) + 1;
                }
                winning.add(randWinning);
            }
        }else{
            System.out.println("Set the number of spots to use QuickPick");
        }
        return winning;
    }



    ArrayList<Integer> getWinningNumbers(){
        // Return a copy since ArrayList is a mutatable object
        return new ArrayList<Integer>(winningNumbers);
    }


    public ArrayList<Integer> getUserList() {
        return this.user.getSelectedNumbers();
    }

    public void removeNumFromUser(Integer buttonNum) {
        // Add the user method removeNum with buttonNum
        this.user.removeNum(buttonNum);
    }

    public void addNumFromUser(Integer buttonNum) {
        this.user.addNum(buttonNum);
    }

    public void setUserNumSpots(Integer value) {
        this.user.setNumSpots(value);
    }

    public int getUserNumSpots(){
        return this.user.getNumSpots();
    }

    public void setUserNums() {
        // User must select num`spots > 0
        if (this.user.getNumSpots() != 0){
            ArrayList<Integer> randomSelected = this.getRandomNumbers();
            this.user.setSelectedNumbers(randomSelected);
        }
    }
}
