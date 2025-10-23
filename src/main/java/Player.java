import java.util.ArrayList;

public class Player {
    private Integer numDrawings;
    private Integer numSpots;
    private ArrayList<Integer> selectedNumbers;

    Player(){
        this.numDrawings = 0;
        this.numSpots = 5; // Set for testing
        this.selectedNumbers = new ArrayList<>();
    }
    Integer getNumDrawings(){
        return this.numDrawings;
    }
    Integer getNumSpots(){
        return this.numSpots;
    }
    void setNumSpots(int newNum){this.numSpots = newNum;}
    void setNumDrawings(int newNum){this.numDrawings = newNum;}
    // Array getter
    ArrayList<Integer> getSelectedNumbers(){
        return new ArrayList<Integer>(this.selectedNumbers);
    }

    void addNum(Integer num){
        this.selectedNumbers.add(num);
    }

    public void removeNum(Integer num) {
        this.selectedNumbers.remove(num);
    }

}
