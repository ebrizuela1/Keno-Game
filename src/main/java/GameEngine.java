import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

// Model Layer
public class GameEngine {
    Player user;
    private ArrayList<Integer> winningNumbers;

    GameEngine(Player user){
        this.user = user;
        this.winningNumbers = new ArrayList<>();
        }
    // Method will return the number of correct matches
    ArrayList<Integer> checkTicket(ArrayList<Integer> playerNumbers,
                     ArrayList<Integer> winningNumbers){
        HashSet<Integer> commonNumbers = new HashSet<>(winningNumbers);

        // Keep all the winning numbers that were also seen in playerNumbers
        commonNumbers.retainAll(playerNumbers);
        ArrayList<Integer> winningMatches = new ArrayList<>(commonNumbers);
        // Returns the number of common numbers
        return winningMatches;
    }

    ArrayList<Integer> submitKenoTicket(){
        this.setWinningNumbers();
        ArrayList<Integer> winningMatches = this.checkTicket(this.user.getSelectedNumbers(), this.winningNumbers);
        this.user.decNumDrawingsRemaining();

        // testing prints
        if (winningMatches.size() != 0) {
            System.out.println("YOU WON: Matched" + winningMatches);
        } else {
            System.out.println("Loser");
        }
        System.out.println("User List : " + this.user.getSelectedNumbers());
        System.out.println("Winning List : " + this.winningNumbers);
        return winningMatches;
    }

    // Generates and returns a winningNumbers array of size numSpots
    void setWinningNumbers(){
        int numSpots = this.user.getNumSpots();
        ArrayList<Integer> winning = new ArrayList<>();
        Random randNum = new Random();

        for (int i  = 0 ; i < 20 ; i++){
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

    // Used in Quick Pick button
    public ArrayList<Integer> setUserNums() {
        // User must select num`spots > 0
        if (this.user.getNumSpots() > 0){
            ArrayList<Integer> randomSelected = this.getRandomNumbers();
            this.user.setSelectedNumbers(randomSelected);
            return randomSelected;
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Incomplete");
            alert.setHeaderText("Please enter num of spots");
            alert.showAndWait();
        }
        return null;
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

    public void setUserNumDrawings(Integer value) {
        this.user.setNumDrawings(value);
    }

    public int getDrawingsRemaining(){
        return this.user.getNumDrawingsRemaining();
    }
    public boolean isGameActive(){
        return this.user.isGameActive();
    }
    public void setGameActive(boolean active){
        this.user.setIsGameActive(active);
    }
    public void resetGame(){
        this.user.clearSelections();
        this.user.setIsGameActive(false);
        this.user.setNumSpots(0);
        this.user.setNumDrawings(this.user.getNumDrawings());
    }


    public void addMoney(Integer value) {
        this.user.addToBalance(value);
    }
    public int getBalance(){
        return this.user.getBalance();
    }
}
