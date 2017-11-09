package hofbo.tactical_material_game;

/**
 * Created by Deniz on 14.10.2017.
 */


public class ShipStat {
    protected String shipID;
    protected String shipName;
    protected int shipLive;
    protected int shipAgility;
    protected int shipPower;
    protected int shipRange;

    public ShipStat (String shipID, String shipName, double shipLive, double shipAgility,double shipPower, double shipRange){
        this.shipID = shipID;
        this.shipName = shipName;
        this.shipLive = (int)shipLive;
        this.shipAgility = (int)shipAgility;
        this.shipPower = (int)shipPower;
        this.shipRange = (int)shipRange;


    }
    public ShipStat (){

    }


}