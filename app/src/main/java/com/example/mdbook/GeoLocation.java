package com.example.mdbook;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

// class for holding geographic locations
class GeoLocation implements Parcelable {
    private ArrayList<Address> addressList;

    public GeoLocation(){
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
    }

    protected GeoLocation(Parcel in) {
        addressList = in.createTypedArrayList(Address.CREATOR);
    }

    public static final Creator<GeoLocation> CREATOR = new Creator<GeoLocation>() {
        @Override
        public GeoLocation createFromParcel(Parcel in) {
            return new GeoLocation(in);
        }

        @Override
        public GeoLocation[] newArray(int size) {
            return new GeoLocation[size];
        }
    };

    public ArrayList<Address> getAddressList(){
        return this.addressList;
    }

    public void addAddress(Address address){
        addressList.add(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(addressList);
    }
}
