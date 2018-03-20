package com.example.akash.salesman.other;

import android.graphics.Bitmap;

/**
 * Created by rkarthic on 17/03/18.
 */

public class CategoryPageItem {

    private String category_name;

    private String display_name;

    private Bitmap categoryImage;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Bitmap getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(Bitmap categoryImage) {
        this.categoryImage = categoryImage;
    }
}
