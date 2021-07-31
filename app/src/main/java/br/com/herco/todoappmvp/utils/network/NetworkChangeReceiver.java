package br.com.herco.todoappmvp.utils.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);
        Log.e("NetworkChangeReceiver", "Sulod sa network reciever");
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                //new ForceExitPause(context).execute();
                Log.e("STATUS_NOT_CONNECTED", "Não conectado");
            } else {
                //new ResumeForceExitPause(context).execute();
                Log.e("STATUS_CONNECTED", "Conectado");

            }
        }
    }
}