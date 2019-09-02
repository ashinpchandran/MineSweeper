package www.minesweeper.com.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import www.minesweeper.com.minesweeper.R;

/**
 * Created by more on 9/22/2017.
 */
public class Options extends Activity  {

    AdView mAdView;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To hide the Action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.options);

        //banner ad initialisation
        MobileAds.initialize(getApplicationContext(),
                getString(R.string.banner_ad_unit_id));

        //requesting ads
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Switch switch_sound = (Switch) findViewById(R.id.switch_sound);
        Switch switch_vibration = (Switch) findViewById(R.id.switch_vibration);

        if(SaveScorePreferences.getsound(Options.this))
            switch_sound.setChecked(true);
        else
            switch_sound.setChecked(false);

        if(SaveScorePreferences.getvibration(Options.this))
            switch_vibration.setChecked(true);
        else
            switch_vibration.setChecked(false);

        switch_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    SaveScorePreferences.savesound(Options.this,true);
                    Log.e("sound: ", Boolean.toString(SaveScorePreferences.getsound(Options.this)));
                }
                else
                {
                    SaveScorePreferences.savesound(Options.this,false);
                    Log.e("sound: ", Boolean.toString(SaveScorePreferences.getsound(Options.this)));
                }

            }
        });

        switch_vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SaveScorePreferences.savevibration(Options.this, true);
                    Log.e("vibration: ", Boolean.toString(SaveScorePreferences.getvibration(Options.this)));
                }
                else
                {
                    SaveScorePreferences.savevibration(Options.this, false);
                    Log.e("vibration: ", Boolean.toString(SaveScorePreferences.getvibration(Options.this)));
                }
            }
        });
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
