package antonio.iseeporto;

/**
 * Created by Antonio on 11-09-2015.
 */
public class SingletonUserId {
    private static SingletonUserId ourInstance = new SingletonUserId();

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    protected String idUser;

    public static SingletonUserId getInstance() {
        return ourInstance;
    }

}
