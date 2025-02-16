package pl.patrykchmiel.demo.singleton;

public enum EagerEnumSingleton {
    INSTANCE
    ;

    public static EagerEnumSingleton getInstance() {
        return INSTANCE;
    }
}
