package br.com.herco.todoappmvp.models;

import androidx.annotation.StringRes;

import java.net.SocketTimeoutException;

import br.com.herco.todoappmvp.R;

public class ResponseError {

    @StringRes
    public int getStringRes(Throwable throwable, int stringDefault) {
        if (throwable instanceof SocketTimeoutException) {
            return R.string.failed_to_connect_to_server;
        }

        return stringDefault;
    }
}
