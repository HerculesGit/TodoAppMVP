package br.com.herco.todoappmvp.services.database.preferences;

import java.util.Random;

import java.util.UUID;

public class PreferencesHelper {

    @Deprecated
    static int getId() {
        final Random random = new Random();
        final int[] values = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        String key = "";

        for (int i : new int[]{1, 2, 3, 4}) {
            int selectedValue = random.nextInt(values.length);
            key += String.valueOf(selectedValue);
        }

        return Integer.valueOf(key);
    }

    public static String getUUID (){
        return UUID.randomUUID().toString();
    }
}
