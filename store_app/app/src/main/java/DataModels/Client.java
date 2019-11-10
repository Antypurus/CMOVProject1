package DataModels;

import java.util.UUID;

public class Client {

    private String username;
    private String password;
    private UUID userID;
    private String serverKey;

    private Client(){}

    public static Client Login(String username, String password)
    {
        // TODO(Tiago): Fetch client data from storage
        return null;
    }

    public static Client Register(String username, String password, String userID, String serverKey)
    {
        Client client = new Client();
        client.username = username;
        client.password = password;
        client.userID = UUID.fromString(userID);
        client.serverKey = serverKey;
        //TODO(Tiago): store the necessary user data
        return client;
    }

    public String GetUsername()
    {
        return this.username;
    }

    public String GetPassword()
    {
        return this.password;
    }

    public UUID GetUserID()
    {
        return this.userID;
    }

    public String GetServerKey()
    {
        return this.serverKey;
    }

}
