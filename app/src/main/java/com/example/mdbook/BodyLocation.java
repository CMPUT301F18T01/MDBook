package com.example.mdbook;

import java.io.Serializable;
import java.util.ArrayList;

// class for body locations
// might be better off leaving body location as just a name for a string, we'll see
class BodyLocation implements Serializable {

    private  Integer Frontx;
    private Integer Fronty;
    private  Integer Backx;
    private Integer Backy;

    private Photo photo;

    public BodyLocation() {
        Frontx = 0;
        Fronty = 0;
        Backx = 0;
        Backy = 0;

    }

    //public void uploadPhoto(){}

    public void setFrontLoc(int x, int y){
        this.Frontx = x;
        this.Fronty = y;
    }

    public void setBackLoc(int x, int y){
        this.Backx = x;
        this.Backy = y;
    }

    public Integer getFrontx(){
        return this.Frontx;
    }
    public Integer getFronty(){
        return this.Fronty;
    }
    public Integer getBackx(){
        return this.Backx;
    }
    public Integer getBacky(){
        return this.Backy;
    }

    public void setPhoto(Photo photo){
        this.photo = photo;
    }
    public Photo getPhoto(){
        return photo;
    }
    public void deletePhoto(Photo photo){this.photo = null;}

}
