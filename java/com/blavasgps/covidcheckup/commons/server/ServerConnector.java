package com.blavasgps.covidcheckup.commons.server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created By Ravi Kant on 16/10/2019
 */
public class ServerConnector extends AsyncTask<String, String, String> {
    String urlParameters;
    boolean isget = false;
    boolean isHeaderTypeApplicationJson = false;
    private onAsyncTaskComplete mListener;

    public ServerConnector(String urlParameters) {
        this.urlParameters = urlParameters;
    }

    public static String GET(String urls) {
        URL url;
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urls);
            Log.e("URL : ",url.toString());
            urlConnection = (HttpURLConnection) url
                    .openConnection();
            InputStream in = urlConnection.getInputStream();
            //
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            result = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public void setIsget(boolean isget) {
        this.isget = isget;
    }

    public void setHeaderTypeAppJson(boolean _isHeaderTypeApplicationJson) {
        this.isHeaderTypeApplicationJson = _isHeaderTypeApplicationJson;
    }

    public String excutePost(String targetURL, String urlParameters) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            Log.e("URL : ",url.toString());
            Log.e("URL : ", "Parameter : " + urlParameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            if (isHeaderTypeApplicationJson) {
                connection.setRequestProperty("Content-Type", "application/json");
            }
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        try {
            if (isget)
                response = GET(urls[0]);
            else
                response = excutePost(urls[0], urlParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mListener.OnResponse(s);
    }

    public void setDataDownloadListner(onAsyncTaskComplete dataDowmloadListner) {
        mListener = dataDowmloadListner;
    }

    public interface onAsyncTaskComplete {
        void OnResponse(String response);

    }
}
