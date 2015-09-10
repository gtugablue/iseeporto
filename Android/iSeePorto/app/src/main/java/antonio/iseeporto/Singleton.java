package antonio.iseeporto;

/**
 * Created by Duarte on 10/09/2015.
 */
public class Singleton {
    private static Singleton ourInstance = new Singleton();


    protected String idToken;

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

}
