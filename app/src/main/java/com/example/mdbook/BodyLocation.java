package com.example.mdbook;

import java.io.Serializable;

// class for body locations
// might be better off leaving body location as just a name for a string, we'll see
class BodyLocation implements Serializable {

    private  int Frontx;
    private int Fronty;
    private  int Backx;
    private int Backy;
    String location;

    public BodyLocation(String location) {
        Frontx = 0;
        Fronty = 0;
        Backx = 0;
        Backy = 0;
        this.location = location;
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

    public int getFrontx(){
        return this.Frontx;
    }
    public int getFronty(){
        return this.Fronty;
    }
    public int getBackx(){
        return this.Backx;
    }
    public int getBacky(){
        return this.Backy;
    }

    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }

}
