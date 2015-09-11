package antonio.iseeporto;

/**
 * Created by Antonio on 11-09-2015.
 */
public class SingletonStringId {
    private static SingletonStringId ourInstance = new SingletonStringId();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected String id;

    public static SingletonStringId getInstance() {
        return ourInstance;
    }

}
