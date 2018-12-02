package com.example.mdbook;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for implementing Geolocation
 * @author Thomas Chan
 */
class GeoLocation implements Serializable {
    private ArrayList<Address> addressList;

    /**
     * creates a Geolocation object
     */
    public GeoLocation(){
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
    }


    /**
     * Returns current address list
     * @return ArrayList<Address> addressList
     */
    public ArrayList<Address> getAddressList(){
        return this.addressList;
    }

    /**
     * adds 'address' ArrayList of addresses
     * @param address: address
     */
    public void addAddress(Address address){
        addressList.add(address);
    }


}
