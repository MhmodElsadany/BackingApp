package vna.example.com.backingapp.Models;

/**
 * Created by Google       Company on 05/12/2017.
 */

public class BackingsItemModel {
    String iIngredient_name;
    String recipe_Img;

    public BackingsItemModel(String iIngredient_name,String recipe_Img) {
        this.iIngredient_name = iIngredient_name;
        this.recipe_Img=recipe_Img;
    }

    public String getiIngredient_name() {
        return iIngredient_name;
    }

    public String getRecipe_Img() {
        return recipe_Img;
    }
}
