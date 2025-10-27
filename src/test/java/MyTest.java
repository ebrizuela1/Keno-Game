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
    void testSetWinningNumbers() {
        gameEngine.setWinningNumbers();
        ArrayList<Integer> winners = gameEngine.getWinningNumbers();
        assertNotNull(winners, "Winning numbers list should not be null");
        assertEquals(20, winners.size(), "Should generate exactly 20 winning numbers");

        gameEngine.setWinningNumbers();
        winners = gameEngine.getWinningNumbers();

        for (int num : winners) {
            assertTrue(num >= 1 && num <= 80, "Winning number " + num + " is not in range 1-80");
        }
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

        player.setNumSpots(0);
        randomNums = gameEngine.getRandomNumbers();
        assertTrue(randomNums.isEmpty(), "Should return an empty list if numSpots is 0");
    }

    @Test
    void testSubmitKenoTicke() {
        // makes sure it calls dec num drawings or keeps track correctly
        player.setNumDrawings(4);
        assertEquals(4, player.getNumDrawingsRemaining(), "Should start with 4 drawings");

        gameEngine.submitKenoTicket();
        assertEquals(3, player.getNumDrawingsRemaining(), "Should have 3 drawings remaining after submit");
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
}