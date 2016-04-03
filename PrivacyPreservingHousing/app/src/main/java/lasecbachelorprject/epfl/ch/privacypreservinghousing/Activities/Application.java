package lasecbachelorprject.epfl.ch.privacypreservinghousing.Activities;

import com.parse.Parse;

public class Application extends android.app.Application {

    private static final String APP_ID = "DEddSLIfYGyedp34DdAQaaG6Pz0pKwJ2hNPckGlg" ;
    private static final String CLIENT_KEY = "Foatujs4Hcy2cHnbYC0gr1yQjy3Zo81Zr07UdkVU" ;

    @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(this, APP_ID, CLIENT_KEY);

    }
}
