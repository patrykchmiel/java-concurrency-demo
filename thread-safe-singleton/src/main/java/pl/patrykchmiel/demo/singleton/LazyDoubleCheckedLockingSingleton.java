package pl.patrykchmiel.demo.singleton;

public class LazyDoubleCheckedLockingSingleton {

    private static volatile LazyDoubleCheckedLockingSingleton INSTANCE = null;

    private LazyDoubleCheckedLockingSingleton() {}

    public static LazyDoubleCheckedLockingSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazyDoubleCheckedLockingSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazyDoubleCheckedLockingSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
