package pl.patrykchmiel.demo.singleton;

public class UnsafeSingleton {

    private static UnsafeSingleton INSTANCE = null;

    private UnsafeSingleton() {}

    public static UnsafeSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UnsafeSingleton();
        }
        return INSTANCE;
    }
}
