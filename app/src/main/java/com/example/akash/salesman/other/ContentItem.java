package com.example.akash.salesman.other;

import android.graphics.Bitmap;

/**
 * Created by rkarthic on 18/03/18.
 */

public class ContentItem {

    private int item_id;
    private Bitmap item_image;
    private String item_name;
    private String display_content;
    private String owner;
    private String contact;
    private int bookmark;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public Bitmap getItem_image() {
        return item_image;
    }

    public void setItem_image(Bitmap item_image) {
        this.item_image = item_image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDisplay_content() {
        return display_content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDisplay_content(String display_content) {
        this.display_content = display_content;

    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }


}
