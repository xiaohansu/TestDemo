package com.example.suxiaohan.tempproject;

/**
 * Created by suxiaohan on 2018/9/9.
 */

public class Drink {
    private String name;
    private String description;
    private int imageResourceId;
    //drinks is an array of Drinks
    public static final Drink[] drinks = {
            new Drink("Latte", "A couple of espresso shots with steamed milk",android.R.drawable.ic_menu_report_image),
            new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam", android.R.drawable.ic_menu_report_image),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh", android.R.drawable.ic_menu_report_image)
    };
    //Each Drink has a name, description, and an image resource
    public Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description; this.imageResourceId = imageResourceId;
    }
    public String getDescription() { return description;
    }
    public String getName() { return name;
    }
    public int getImageResourceId() { return imageResourceId;
    }
    public String toString() { return this.name;
    }
}
