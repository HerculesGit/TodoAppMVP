package br.com.herco.todoappmvp.utils.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.modules.di.TodoAppDependenciesManager;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private OnNetworkChangeListener onNetworkChange;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);
        boolean isOnline = false;
        Log.e("NetworkChangeReceiver", "Sulod sa network reciever");
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                //new ForceExitPause(context).execute();
                Log.e("STATUS_NOT_CONNECTED", "Não conectado");
                isOnline = false;
            } else {
                //new ResumeForceExitPause(context).execute();
                Log.e("STATUS_CONNECTED", "Conectado");
                isOnline = true;
            }
        }
        if (onNetworkChange != null) onNetworkChange.onNetworkChange(isOnline);
        TodoApp.getInstance().setOnline(isOnline);
    }

    public void setOnNetworkChange(OnNetworkChangeListener onNetworkChange) {
        this.onNetworkChange = onNetworkChange;
    }
}