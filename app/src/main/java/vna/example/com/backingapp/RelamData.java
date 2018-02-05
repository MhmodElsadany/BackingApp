package vna.example.com.backingapp;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Google       Company on 04/02/2018.
 */

public class RelamData extends RealmObject {

    @PrimaryKey
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String IntegtatesItem;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIntegtatesItem(String IntegtatesItem) {
        this.IntegtatesItem = IntegtatesItem;
    }

    public String getIntegtatesItem() {
        return IntegtatesItem;
    }


}
