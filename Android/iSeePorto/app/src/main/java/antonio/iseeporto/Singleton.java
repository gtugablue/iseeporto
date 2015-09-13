package antonio.iseeporto;

import com.facebook.AccessToken;

/**
 * Created by Duarte on 10/09/2015.
 */
public class Singleton {
    private static Singleton ourInstance = new Singleton();

    private double latitude, longitude;

    protected AccessToken accessToken;

    protected String idToken;

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
        latitude = 0;
        longitude = 0;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

}
