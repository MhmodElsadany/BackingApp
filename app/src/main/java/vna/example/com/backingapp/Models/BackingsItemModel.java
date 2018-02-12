package vna.example.com.backingapp.Models;

/**
 * Created by Google       Company on 05/12/2017.
 */

public class BackingsItemModel {
    String iIngredient_name;

    public BackingsItemModel(String iIngredient_name) {
        this.iIngredient_name = iIngredient_name;
    }

    public String getiIngredient_name() {
        return iIngredient_name;
    }
}
