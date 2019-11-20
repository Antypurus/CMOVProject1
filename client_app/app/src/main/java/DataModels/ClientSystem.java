package DataModels;

import android.app.Application;
import android.content.SharedPreferences;

public class ClientSystem extends Application {

    public String ClientUsername = "";
    private String ServerKey = "";
    public String ClientUserID = "";
    private Cart cart;
    private static ClientSystem system = null;

    public ClientSystem(String username)
    {
        this.cart = new Cart();
        this.ClientUsername = username;
        this.fetchUserData();
        this.Login();
        system = this;
    }

    private void Login()
    {
        SharedPreferences preferences = getSharedPreferences("Current_User",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Current_Username",this.ClientUsername);
        editor.apply();
    }

    public void Logout()
    {
        SharedPreferences preferences = getSharedPreferences("Current_User",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Current_Username","");
        editor.apply();
    }

    private void fetchUserData()
    {
        SharedPreferences preferences = getSharedPreferences(this.ClientUsername,MODE_PRIVATE);
        this.ServerKey = preferences.getString("Server_Key","");
        this.ClientUserID = preferences.getString("user_id","");
    }

    public Cart GetCart()
    {
        return this.cart;
    }

    public String GetServerKey() {return this.ServerKey;}

    public static ClientSystem GetSystem()
    {
        return system;
    }

}
