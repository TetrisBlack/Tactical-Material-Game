package hofbo.tactical_material_game;

/**
 * Created by Deniz on 14.03.2018.
 */

public class MatchClass {
    public Integer activePlayer;
    public String player1;
    public String player2;
    public String playground;
    public String round;
    public String shipsPlayer1;
    public String shipsPlayer2;

    public MatchClass(){

    }

    public MatchClass(Integer activePlayer, String player1, String player2, String playground, String round, String shipsPlayer1, String shipsPlayer2){
        this.activePlayer = activePlayer;
        this.player1 = player1;
        this.player2 = player2;
        this.playground = playground;
        this.round = round;
        this.shipsPlayer1 = shipsPlayer1;
        this.shipsPlayer2 = shipsPlayer2;

    }

}
