import java.util.ArrayList;

public class Player {
    Integer numDrawings;
    Integer numSpots;
    private ArrayList<Integer> selectedNumbers;

    Player(){
        this.numDrawings = 0;
        this.numSpots = 0;
        this.selectedNumbers = new ArrayList<>();
    }

    // Array getter
    ArrayList<Integer> getSelectedNumbers(){
        return this.selectedNumbers;
    }

    void addNum(Integer num){
        this.selectedNumbers.add(num);
    }

    public void removeNum(Integer num) {
        this.selectedNumbers.remove(num);
    }

}
