package io.potter.partum.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.potter.partum.Model.User;

public class Common {
    public static User currentuser;

    public static final String USER_KEY="User";
    public static final String PWD_KEY="Password";

    public static String convertCodeToStatus(String status) {
        if(status.equals("0")){
            return "Colocado";
        }
        else if(status.equals("1")){
            return "En camino";
        }
        else{
            return "Entregado";
        }
    }

    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo[] info =connectivityManager.getAllNetworkInfo();
            if(info != null){
                for (int i=0; i<info.length;i++){
                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}
