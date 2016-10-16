package com.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Items")
public class Item extends Model {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // If name is omitted, then the field name is used.
    @Column(name = "Name")
    public String name;



    public Item() {
        super();
    }

    public Item(String name) {
        super();
        this.name = name;
    }

    public static Item getRandom() {
        return new Select().from(Item.class).orderBy("RANDOM()").executeSingle();
    }
}