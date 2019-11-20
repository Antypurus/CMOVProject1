package DataModels;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.example.storeapplication.Activities.Client.RegisterActivity;

public class ClientSystem extends Application {

    public String ClientUsername = "";
    private String ServerKey = "";
    public String ClientUserID = "";
    private Cart cart;
    private static ClientSystem system = null;

    public ClientSystem(String username, Activity activity)
    {
        this.cart = new Cart();
        this.ClientUsername = username;
        this.fetchUserData(activity);
        this.Login(activity);
        system = this;
    }

    private void Login(Activity activity)
    {
        SharedPreferences preferences = activity.getSharedPreferences("Current_User",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Current_Username",this.ClientUsername);
        editor.apply();
    }

    public void Logout(Activity activity)
    {
        SharedPreferences preferences = activity.getSharedPreferences("Current_User",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Current_Username","");
        editor.apply();
    }

    private void fetchUserData(Activity activity)
    {
        SharedPreferences preferences = activity.getSharedPreferences(ClientUsername,MODE_PRIVATE);
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
