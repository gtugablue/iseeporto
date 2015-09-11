package antonio.iseeporto;

import com.facebook.AccessToken;

/**
 * Created by Duarte on 10/09/2015.
 */
public class Singleton {
    private static Singleton ourInstance = new Singleton();

    protected AccessToken accessToken;

    protected String idToken;

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

}
