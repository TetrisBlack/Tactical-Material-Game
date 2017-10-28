package hofbo.tactical_material_game;

/**
 * Created by Deniz on 28.10.2017.
 */

public class NewsItems {
    protected String headline;
    protected String pictureUrl;
    protected String content;

    public NewsItems(){

    }

    public NewsItems(String headline, String pictureUrl,String content){
        this.headline = headline;
        this.pictureUrl = pictureUrl;
        this.content = content;
    }
}
