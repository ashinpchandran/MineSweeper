package www.minesweeper.com.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * Created by more on 9/9/2017.
 */
public class Menu  extends Activity{

    public static  MediaPlayer mp = null;
    AdView mAdView;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To hide the Action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.menu);

        //banner ad initialisation
        MobileAds.initialize(getApplicationContext(),
                getString(R.string.banner_ad_unit_id));

        //requesting ads
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void OnClick(View v)
    {
        if(SaveScorePreferences.getsound(this))
        {
            if (mp != null) {
                mp.reset();
                mp.release();
            }
            mp = MediaPlayer.create(this, R.raw.menu_button_sound);
            mp.start();
        }

        if (v.getId() == R.id.button_newgame)
        {
            Intent pi = new Intent(this, Aftersplash.class);
            startActivity(pi);
            finish();
        }
        if (v.getId() == R.id.button_highscores)
        {
            Intent pi = new Intent(this, Highscore.class);
            startActivity(pi);
            finish();
        }
        if (v.getId() == R.id.button_options)
        {
            Intent pi = new Intent(this, Options.class);
            startActivity(pi);
            finish();
        }
        if (v.getId() == R.id.button_help)
        {
            Intent pi = new Intent(this, Welcome_and_Help.class);
            startActivity(pi);
            finish();
        }
    }



}
