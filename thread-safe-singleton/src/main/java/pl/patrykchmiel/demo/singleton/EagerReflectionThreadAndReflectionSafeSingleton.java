package pl.patrykchmiel.demo.singleton;

public class EagerReflectionThreadAndReflectionSafeSingleton {

    private static final EagerReflectionThreadAndReflectionSafeSingleton INSTANCE = new EagerReflectionThreadAndReflectionSafeSingleton();

    private EagerReflectionThreadAndReflectionSafeSingleton() {
        if (INSTANCE != null)
            throw new IllegalStateException();
    }

    public static EagerReflectionThreadAndReflectionSafeSingleton getInstance() {
        return INSTANCE;
    }




}
