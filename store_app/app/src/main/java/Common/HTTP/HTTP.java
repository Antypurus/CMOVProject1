package Common.HTTP;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HTTP {

    /**
     *
     * @param urlString
     * @param headers
     * @param handler
     * @throws IOException
     */
    public static void GetRequest(String urlString, final HashMap<String,String> headers, final HTTPResultHandler handler) throws IOException {
        if(!urlString.contains("http://") && !urlString.contains("https://"))
        {
            urlString = "http://"+urlString;
        }
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL((String) objects[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    if(headers!=null) // there are headers to include in the request
                    {
                        for(Map.Entry<String, String> entry : headers.entrySet())
                        {
                            String property = entry.getKey();
                            String value = entry.getValue();
                            connection.setRequestProperty(property,value);
                        }
                    }
                    InputStream errorStream = connection.getErrorStream();
                    if(errorStream!=null)
                    {
                        //there was an error handle it
                        return "Error";
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    reader.close();
                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    if(connection!=null) {
                        connection.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                handler.Handler(result);
            }
        };
        asyncTask.execute(new Object[]{urlString});
    }

    /**
     *
     * @param urlString
     * @param headers
     * @param body
     * @param handler
     */
    public static void PostRequest(String urlString, final HashMap<String,String>headers, final HashMap<String,String> body, final HTTPResultHandler handler)
    {
        if(!urlString.contains("http://") && !urlString.contains("https://"))
        {
            urlString = "http://"+urlString;
        }
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL((String) objects[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type","application/json");
                    if(headers!=null) // there are headers to include in the request
                    {
                        for(Map.Entry<String, String> entry : headers.entrySet())
                        {
                            String property = entry.getKey();
                            String value = entry.getValue();
                            connection.setRequestProperty(property,value);
                        }
                    }
                    if(body!=null)
                    {
                        String bodyData = "";
                        JSONObject jsonBuilder = new JSONObject();
                        for(Map.Entry<String,String> entry: body.entrySet())
                        {
                            String property = entry.getKey();
                            String value = entry.getValue();
                            jsonBuilder.put(property,value);
                        }
                        bodyData = jsonBuilder.toString();
                        connection.setDoOutput(true);
                        connection.getOutputStream().write(bodyData.getBytes());
                    }
                    InputStream errorStream = connection.getErrorStream();
                    if(errorStream!=null)
                    {
                        //there was an error handle it
                        return "Error";
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    reader.close();
                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    if(connection!=null) {
                        connection.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                handler.Handler(result);
            }
        };
        asyncTask.execute(new Object[]{urlString});
    }

    public static void PostRequest(String urlString, final HashMap<String,String>headers, final String body, final HTTPResultHandler handler)
    {
        if(!urlString.contains("http://") && !urlString.contains("https://"))
        {
            urlString = "http://"+urlString;
        }
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL((String) objects[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type","application/json");
                    if(headers!=null) // there are headers to include in the request
                    {
                        for(Map.Entry<String, String> entry : headers.entrySet())
                        {
                            String property = entry.getKey();
                            String value = entry.getValue();
                            connection.setRequestProperty(property,value);
                        }
                    }
                    if(body!=null)
                    {
                        String bodyData = body;
                        connection.setDoOutput(true);
                        connection.getOutputStream().write(bodyData.getBytes());
                    }
                    InputStream errorStream = connection.getErrorStream();
                    if(errorStream!=null)
                    {
                        //there was an error handle it
                        return "Error";
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    reader.close();
                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    if(connection!=null) {
                        connection.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                handler.Handler(result);
            }
        };
        asyncTask.execute(new Object[]{urlString});
    }

}
