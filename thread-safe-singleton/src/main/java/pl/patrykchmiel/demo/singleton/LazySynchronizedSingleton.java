package pl.patrykchmiel.demo.singleton;

public class LazySynchronizedSingleton {

    private static LazySynchronizedSingleton INSTANCE = null;

    private LazySynchronizedSingleton() {}

    public synchronized static LazySynchronizedSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazySynchronizedSingleton();
        }
        return INSTANCE;
    }
}
