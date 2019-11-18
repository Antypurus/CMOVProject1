package DataModels;

import android.app.Application;
import android.content.SharedPreferences;

public class ClientSystem extends Application {

    private String ClientUsername = "";
    private String ServerKey = "";
    private String ClientUserID = "";
    private static ClientSystem system = null;

    public ClientSystem(String username)
    {
        this.ClientUsername = username;
        system = this;
    }

    private void fetchUserDate()
    {
        SharedPreferences preferences = getSharedPreferences(this.ClientUsername,MODE_PRIVATE);
        this.ServerKey = preferences.getString("Server_Key","");
        this.ClientUserID = preferences.getString("user_id","");
    }


    public ClientSystem GetSystem()
    {
        return system;
    }

}
