import java.util.ArrayList;

public class Player {
    private Integer numDrawings;
    private Integer numSpots;
    private ArrayList<Integer> selectedNumbers;
    private Integer numDrawingsRemaining;
    private Integer balance;
    private boolean isGameActive;

    Player(){
        this.numDrawings = 0;
        this.numSpots = 0; // Set for testing
        this.selectedNumbers = new ArrayList<>();
        this.numDrawingsRemaining = 0;
        this.isGameActive = false;
        this.balance = 200;
    }
    Integer getNumDrawings(){
        return this.numDrawings;
    }
    void setNumDrawings(int newNum){
        this.numDrawings = newNum;
        this.numDrawingsRemaining = newNum;
    }
    Integer getBalance(){
        return this.balance;
    }
    Integer getNumSpots(){
        return this.numSpots;
    }
    void setNumSpots(int newNum){
        this.numSpots = newNum;
    }
    // Array getter : returns copy
    ArrayList<Integer> getSelectedNumbers(){
        return new ArrayList<Integer>(this.selectedNumbers);
    }
    void addNum(Integer num){
        this.selectedNumbers.add(num);
    }

    public void removeNum(Integer num) {
        this.selectedNumbers.remove(num);
    }

    Integer getNumDrawingsRemaining(){
        return this.numDrawingsRemaining;
    }
    void decNumDrawingsRemaining(){
        this.numDrawingsRemaining--;
    }
    boolean isGameActive(){
        return this.isGameActive;
    }
    void setIsGameActive(boolean isGameActive){
        this.isGameActive = isGameActive;
    }
    void clearSelections(){
        this.selectedNumbers.clear();
    }
    public void setSelectedNumbers(ArrayList<Integer> randomSelected) {
        this.selectedNumbers = randomSelected;
    }
    public void addToBalance(Integer value){
        this.balance += value;
    };
}
