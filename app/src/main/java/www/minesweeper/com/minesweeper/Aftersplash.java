package www.minesweeper.com.minesweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by more on 9/9/2017.
 */
public class Aftersplash extends Activity implements RewardedVideoAdListener
{
    public static int adcount = 0;
    public static TextView bombno,timer;
    public static ImageButton imgbutton;
    public int seconds = 00;
    public int minutes = 0;
    public static int score = 0;
    public static boolean flagswitch = false;
    public Context context;
    public static Aftersplash activity;
    public static boolean timer_on = true;

    private String TAG = Aftersplash.class.getSimpleName();
    InterstitialAd mInterstitialAd;
    RewardedVideoAd mAd;
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To hide the Action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.fullscreen_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // showInterstitial();
            }
        });

        //initialise rewarded video ad
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-3441516251330872/5932708600");
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadrewardedvideoad();




        GameEngine.getInstance().createGrid(this);

        bombno = (TextView)findViewById(R.id.textbombno);
        timer = (TextView) findViewById(R.id.texttimer);
        imgbutton = (ImageButton)findViewById(R.id.imagebutton);

        context =this;
        activity = this;

        timer_on = true;
        timerclock();

    }

   public void timerclock()
   {


       //Declare the timer
       final Timer t = new Timer();
       //Set the schedule function and rate

            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        public void run() {


                            if (timer_on == true) {
                                TextView tv = (TextView) findViewById(R.id.texttimer);
                                tv.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                                seconds += 1;
                                if (seconds != 00)
                                    score = minutes * 60 + seconds - 1;
                                else
                                    score = minutes * 60;
                                Log.e("AS_score: ", Integer.toString(score) + " " + Integer.toString(minutes) + " " + Integer.toString(seconds));

                                if (seconds == 60) {
                                    tv.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                                    seconds = 00;
                                    minutes = minutes + 1;

                                }
                            } else {
                                t.cancel();
                                if(adcount%4==0)
                                {
                                    Log.e("===>", "%4");
                                    showrewardedvideoad();
                                }
                                else if(adcount%3==0)
                                {
                                    Log.e("===>","%3");
                                    showInterstitial();

                                }
                                Log.e("=================>",Integer.toString(adcount));
                                adcount++;
                            }
                        }


                    });
                }
            }, 0, 1000);


   }

  public void onclick(View view)
   {
      if(view.getId()==R.id.imagebutton)
      {
        flagswitch=!flagswitch;
        if(flagswitch==true)
            imgbutton.setBackgroundResource(R.drawable.bomb_normal);
        else
            imgbutton.setBackgroundResource(R.drawable.flag);
       }

   }

    //hardware back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            timer_on = false;
            Intent intent = new Intent(this,Menu.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }


    private  void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private  void showrewardedvideoad()
    {
        if(mAd.isLoaded())
           mAd.show();
    }

    private void loadrewardedvideoad() {

        Log.e("====**loadingvidad*====","");
        if(!mAd.isLoaded())
        {
            mAd.loadAd("ca-app-pub-3441516251330872/5932708600",new AdRequest.Builder().build());
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

        Log.e("===>","adloaded1233444");
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {


    }

    @Override
    public void onRewarded(RewardItem rewardItem) {


    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    protected void onResume() {
        mAd.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {

        mAd.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
       // mAd.destroy();
        super.onDestroy();
    }
}
