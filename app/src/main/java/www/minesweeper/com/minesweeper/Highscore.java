package www.minesweeper.com.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by more on 9/19/2017.
 */
public class Highscore extends Activity {

    public TextView first,second,third;
    AdView mAdView;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To hide the Action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.highscores);

        //banner ad initialisation
        MobileAds.initialize(getApplicationContext(),
                getString(R.string.banner_ad_unit_id));

        //requesting ads
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        first = (TextView)findViewById(R.id.first);
        second = (TextView)findViewById(R.id.second);
        third = (TextView)findViewById(R.id.third);

        sethighscore();

    }

    public void sethighscore() {

        int f,s,t;
        f = SaveScorePreferences.getfirst(Highscore.this);
        s = SaveScorePreferences.getsecond(Highscore.this);
        t = SaveScorePreferences.getthird(Highscore.this);

        if(f==0)
            first.setText("1. ___ Seconds");
        else
            first.setText("1. "+ Integer.toString(f) + " Seconds");
        if(s==0)
            second.setText("2. ___ Seconds");
        else
            second.setText("2. "+ Integer.toString(s) + " Seconds");
        if(s==0)
            third.setText("3. ___ Seconds");
        else
            third.setText("3. " + Integer.toString(t) + " Seconds");
    }

    //hardware back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(this,Menu.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}
