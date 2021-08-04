package br.com.herco.todoappmvp.modules.di;

import java.util.HashMap;
import java.util.Map;

public final class TodoAppDependenciesManager {
    private static TodoAppDependenciesManager instance;

    private static Map<String, DI> mDependencies = new HashMap<>();


    private TodoAppDependenciesManager() {
        // do nothing
    }

    public static TodoAppDependenciesManager getInstance() {
        if (instance == null) {
            instance = new TodoAppDependenciesManager();
        }
        return instance;
    }


    public static void addDependency(String hash, DI dependency) {
        mDependencies.put(hash, dependency);
    }

    public static DI getDependency(String hash) {
        return mDependencies.get(hash);
    }
}


