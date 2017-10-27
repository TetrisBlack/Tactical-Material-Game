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

    public ShipStat (String shipID, String shipName, int shipLive, int shipAgility,int shipPower, int shipRange){
        this.shipID = shipID;
        this.shipName = shipName;
        this.shipLive = shipLive;
        this.shipAgility = shipAgility;
        this.shipPower = shipPower;
        this.shipRange = shipRange;


    }
    public ShipStat (){

    }


}