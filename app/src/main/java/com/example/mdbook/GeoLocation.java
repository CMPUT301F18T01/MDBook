package com.example.mdbook;

import android.location.Address;

import java.util.ArrayList;

// class for holding geographic locations
class GeoLocation {
    private ArrayList<Address> addressList;

    public GeoLocation(){
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
    }

    public ArrayList<Address> getAddressList(){
        return this.addressList;
    }

    public void addAddress(Address address){
        addressList.add(address);
    }
}
