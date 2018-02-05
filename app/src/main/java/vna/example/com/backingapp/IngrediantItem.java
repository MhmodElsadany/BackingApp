package vna.example.com.backingapp;

/**
 * Created by Google       Company on 05/12/2017.
 */

class IngrediantItem {
    String     quantity;
    String     measure;
    String     ingredient;

    public IngrediantItem(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
