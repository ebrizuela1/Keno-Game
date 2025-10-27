import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 Robust testing of the game engine
 * */
class GameEngineTest {

    private Player player;
    private GameEngine gameEngine;

    @BeforeEach
    void setUp() {
        player = new Player();
        gameEngine = new GameEngine(player);
    }

    @Test
    void testCheckTicketNoMatches() {
        ArrayList<Integer> playerNums = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> winningNums = new ArrayList<>(Arrays.asList(4, 5, 6));
        ArrayList<Integer> matches = gameEngine.checkTicket(playerNums, winningNums);
        assertEquals(0, matches.size());
    }

    @Test
    void testCheckTicketAllMatches(){
        ArrayList<Integer> playerNums = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> winningNums = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> matches = gameEngine.checkTicket(playerNums, winningNums);
        assertEquals(3, matches.size(), "Should have 3 matches");
        assertTrue(matches.containsAll(Arrays.asList(1, 2, 3)), "Should contain all player numbers");
    }

    @Test
    void testCheckTicketMiscMatches(){
        ArrayList<Integer> playerNums = new ArrayList<>(Arrays.asList(1, 5, 10, 20));
        ArrayList<Integer> winningNums = new ArrayList<>(Arrays.asList(1, 10, 30, 40, 50));
        ArrayList<Integer> matches = gameEngine.checkTicket(playerNums, winningNums);
        assertEquals(2, matches.size(), "Should have 2 matches");
        assertTrue(matches.containsAll(Arrays.asList(1, 10)), "Should contain 1 and 10");
    }


    @Test
    void testSetWinningNumbersInRange() {
        gameEngine.setWinningNumbers();
        ArrayList<Integer> winners = gameEngine.getWinningNumbers();

        gameEngine.setWinningNumbers();
        winners = gameEngine.getWinningNumbers();

        for (int num : winners) {
            assertTrue(num >= 1 && num <= 80, "Winning number " + num + " is not in range 1-80");
        }
    }

    @Test
    void testSetWinningNumbersNull() {
        gameEngine.setWinningNumbers();
        ArrayList<Integer> winners = gameEngine.getWinningNumbers();
        assertNotNull(winners, "Winning numbers list should not be null");
    }

    @Test
    void testSetWinningNumbersNum() {
        gameEngine.setWinningNumbers();
        ArrayList<Integer> winners = gameEngine.getWinningNumbers();
        assertEquals(20, winners.size(), "Should generate exactly 20 winning numbers");
    }

    @Test
    void testSetWinningNumbersAllUnique(){
        gameEngine.setWinningNumbers();
        ArrayList<Integer> winners = gameEngine.getWinningNumbers();
        HashSet<Integer> uniqueWinners = new HashSet<>(winners);
        assertEquals(winners.size(), uniqueWinners.size(), "All winning numbers should be unique");
    }

    @Test
    void testGetRandomNumbers() {
        player.setNumSpots(8);
        ArrayList<Integer> randomNums = gameEngine.getRandomNumbers();
        assertEquals(8, randomNums.size(), "Should return 8 random numbers for 8 spots");
    }
    @Test
    void testGetRandomNumbersZeroSpots(){
        player.setNumSpots(0);
        ArrayList<Integer> randomNums = gameEngine.getRandomNumbers();
        assertTrue(randomNums.isEmpty(), "Should return an empty list if numSpots is 0");
    }

    @Test
    void testGetRandomNumbersNegativeSpots(){
        player.setNumSpots(-1);
        ArrayList<Integer> randomNums = gameEngine.getRandomNumbers();
        assertTrue(randomNums.isEmpty(), "Should return an empty list if numSpots is 0");
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,4,8,10,80})
    void testGetRandomNumbersUnique(int numSpots){
        player.setNumSpots(numSpots);
        ArrayList<Integer> randomNums = gameEngine.getRandomNumbers();
        assertEquals(numSpots, (new HashSet<>(randomNums)).size(), "Should return " + numSpots + " unique random numbers");
    }

    @Test
    void testSubmitKenoTicket() {
        // makes sure it calls dec num drawings or keeps track correctly
        player.setNumDrawings(4);
        assertEquals(4, player.getNumDrawingsRemaining(), "Should start with 4 drawings");

        gameEngine.submitKenoTicket();
        assertEquals(3, player.getNumDrawingsRemaining(), "Should have 3 drawings remaining after submit");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 6, 10})
    void testSetNumDrawingsRemaining(int numDrawings) {
        int ans = Math.max(0, numDrawings);
        player.setNumDrawings(numDrawings);
        assertEquals(ans, player.getNumDrawingsRemaining(), "Should start with " + ans + " drawings");

        gameEngine.submitKenoTicket();
        assertEquals(Math.max(0,ans - 1), player.getNumDrawingsRemaining(), "Should have " + Math.max(0,ans - 1) + " drawings remaining after submit");
    }



    @Test
    void testResetGame() {
        player.setNumDrawings(4);
        player.setNumSpots(8);
        player.addNum(10);
        player.setIsGameActive(true);

        gameEngine.resetGame();

        assertEquals(0, player.getNumSpots(), "NumSpots should be reset to 0");
        assertTrue(player.getSelectedNumbers().isEmpty(), "Player selections should be cleared");
        assertFalse(player.isGameActive(), "GameActive flag should be reset to false");
        assertEquals(4, player.getNumDrawingsRemaining(), "Drawings remaining should reset to total drawings");
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,4,8,10})
    void testSetUserNumsZeroSpots(int numSpots){
        player.setNumSpots(numSpots);
        ArrayList<Integer> setUserNumsReturn = this.gameEngine.setUserNums();
        ArrayList<Integer> userNums = this.gameEngine.getUserList();
        int n = Math.min(setUserNumsReturn.size(), userNums.size());
        for (int i = 0; i < n; i++) {
            assertEquals(setUserNumsReturn.get(i), userNums.get(i), "UserNums should be set/equal");
        }
    }
}