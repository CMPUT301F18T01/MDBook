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

    private static SyncController syncController;
    private ElasticsearchController elasticsearchController;
    private DataManager dataManager;
    private UserDecomposer decomposer;
    private boolean isConnected = false;


    public void SyncController(){
        if (elasticsearchController == null){
            elasticsearchController = ElasticsearchController.getController();
        }
        if(dataManager == null){
            dataManager = DataManager.getDataManager();
        }
        if (decomposer == null){
            decomposer = new UserDecomposer();
        }

    }

    public static SyncController getController() {
        if (syncController == null) {
            throw new RuntimeException("SyncController has not been initialized!");
        }
        return syncController;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        elasticsearchController = ElasticsearchController.getController();
        dataManager = DataManager.getDataManager();
        decomposer = new UserDecomposer();

        boolean isConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if(isConnected){
            Toast.makeText(context,"Internet Connection Lost \n Profile Not Synced",Toast.LENGTH_SHORT).show();

        }
        else{
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
                        Toast.makeText(context,"Profile Synced Up",Toast.LENGTH_SHORT).show();

                    } catch (NetworkErrorException e) {
                        break;
                    }
                }
            }
        }
    }
}
