package particle.joeypak;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.facebook.AccessToken;
//import com.jacksonandroidnetworking.JacksonParserFactory;

public class JoeyPakApplication extends Application {
    private static JoeyPakApplication instance;

    public static JoeyPakApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Set Up Networking Stack
        AndroidNetworking.initialize(getApplicationContext());
        //AndroidNetworking.setParserFactory(new JacksonParserFactory());

    }
}
