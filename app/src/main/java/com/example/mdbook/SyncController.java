package com.example.mdbook;

import android.accounts.NetworkErrorException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.ArrayList;


public class SyncController extends BroadcastReceiver {

    private ElasticsearchController elasticsearchController;
    private DataManager dataManager;
    private UserDecomposer decomposer;
    private boolean isConnected = false;

    public void SyncController(){
        if (elasticsearchController == null){
            if ( dataManager == null) {
                if (decomposer == null) {
                    elasticsearchController = ElasticsearchController.getController();
                    dataManager = DataManager.getDataManager();
                    decomposer = new UserDecomposer();
                }
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        elasticsearchController = ElasticsearchController.getController();
        dataManager = DataManager.getDataManager();
        decomposer = new UserDecomposer();


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo !=null){
                for (int i = 0; i< networkInfo.length;i++){
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
                        if (!isConnected){
                            Toast.makeText(context,"Internet is available",Toast.LENGTH_SHORT).show();
                            if (elasticsearchController.isConnected()){
                                ArrayList<User> toupload = (ArrayList<User>) dataManager.getPushQueue().clone();
                                for (User user1 : toupload){

                                    UserDecomposer.Decomposition userDecomp;
                                    try {
                                        userDecomp = decomposer.decompose(user1);
                                        if (user1.getClass() == Patient.class) {
                                            if (elasticsearchController.pushPatient(userDecomp)) {
                                                dataManager.removeFromQueue(user1);
                                            }
                                        }
                                        else {
                                            if (elasticsearchController.pushCaregiver(userDecomp)) {
                                                dataManager.removeFromQueue(user1);
                                            }
                                        }

                                    } catch (NetworkErrorException e) {
                                        break;
                                    }
                                }
                            }

                            isConnected = true;
                            break;


                        }
                    }
                }
            }





        }
        else{
            Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();
            isConnected = false;
        }
    }
}
