package DataModels;

import android.app.Application;

public class ClientSystem extends Application {

    private String ClientUsername = "";
    private static ClientSystem system = null;

    public ClientSystem(String username)
    {
        this.ClientUsername = username;
        system = this;
    }

    public ClientSystem GetSystem()
    {
        return system;
    }

}
