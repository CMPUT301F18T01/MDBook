package com.example.mdbook;

import android.accounts.NetworkErrorException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;



public class SyncController extends BroadcastReceiver {

    private ElasticsearchController elasticsearchController;
    private DataManager dataManager;
    private UserDecomposer decomposer;

    public void SyncController(){
        if (elasticsearchController == null){
            if ( dataManager == null) {
                if (decomposer == null) {
                    elasticsearchController.getController();
                    dataManager.getDataManager();
                    decomposer = new UserDecomposer();
                }
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        elasticsearchController.getController();
        dataManager.getDataManager();


        boolean isConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
        if (isConnected){
            Toast.makeText(context,"Internet is available",Toast.LENGTH_SHORT).show();
            /*if (elasticsearchController.isConnected()){
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
            }*/

        }
        else{
            Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show();
        }
    }
}
