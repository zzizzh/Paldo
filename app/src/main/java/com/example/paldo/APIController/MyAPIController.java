package com.example.paldo.APIController;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import static com.example.paldo.Data.Constants.*;

/**
 * Created by jm on 2017-04-23.
 */

public class MyAPIController {

    private static final String initURI     = "http://172.30.1.11:8080";

    private static MyAPIController youtubeURLController;

    private static boolean queryOK = false;

    private MyAPIController(){

    }

    public static MyAPIController getMyAPIController(){
        if(youtubeURLController == null)
            youtubeURLController = new MyAPIController();
        return youtubeURLController;
    }

    private String getCallString(int code) {

        switch(code) {
            case GET_CONTENT :
                return "/content?id=";
            case GET_TYPE:
                return "/type?id=";
            case ADD:
                return "/add";
            default:
                return "";
        }
    }

    public ArrayList<String> queryAPI(String query){
        ArrayList<String> urlList = null;

        GetItemThread thread = new GetItemThread(query);
        thread.start();

        while(thread.isAlive()){
            urlList= thread.getURLList();
        }
        return urlList;
    }
    /*
        Created by jm on 2017-04-23.
        getter
     */
/////////////////////////////////////////////////////////

    /*
        Created by jm on 2017-04-23.
        thread class to get Item using API
     */
    class GetItemThread extends Thread{
        // save code and name String list
        private ArrayList<String> URLList;

        private String query = "";
        private String call;

        public GetItemThread(String query){
            this.query = query;

            URLList = new ArrayList<String>();
        }

        @Override
        public void run() {
            String queryURL = "";
            Log.d("URL", queryURL);

            try {
                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                InputStream is = new BufferedInputStream(urlConnection.getInputStream());

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기

                String tag;

                xpp.next();
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;

                        case XmlPullParser.START_TAG:
                            tag = xpp.getName();    //테그 이름 얻어오기

                            if (tag.equals("URL")){
                                xpp.next();
                                URLList.add(xpp.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            tag = xpp.getName();    //테그 이름 얻어오기
                            break;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            queryOK = true;
        }

        public ArrayList<String> getURLList(){
            return URLList;
        }

        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
    }
}