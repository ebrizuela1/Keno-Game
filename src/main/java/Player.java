import java.util.ArrayList;

public class Player {
    Integer numDrawings;
    Integer numSpots;
    ArrayList<Integer> selectedNumbers;
    Player(){
        this.selectedNumbers = new ArrayList<>();
    }
    void appendNum(Integer num){
        this.selectedNumbers.add(num);
    }
}
