package www.minesweeper.com.minesweeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import www.minesweeper.com.minesweeper.util.Generator;
import www.minesweeper.com.minesweeper.util.PrintGrid;
import www.minesweeper.com.minesweeper.views.grid.Cell;

/**
 * Created by more on 9/6/2017.
 */
public class GameEngine {

    private static GameEngine instance;
    public static  MediaPlayer mp = null;
    public static final int BOMB_NUMBER = 15;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 16;
    private Context context;
    private Cell[][] MinesweeperGrid = new Cell[WIDTH][HEIGHT];
    public static GameEngine getInstance()
    {
        if(instance == null)
            instance = new GameEngine();
       return  instance;
    }

    public GameEngine()
    {

    }

    public void createGrid(Context context)
    {
        Log.e("GameEngine" ,"CreateGrid is working");
        this.context = context;

        //create the grid and store it
        int[][] GeneratedGrid = Generator.generate(BOMB_NUMBER,WIDTH,HEIGHT);
        PrintGrid.print(GeneratedGrid,WIDTH,HEIGHT);
        setGrid(context,GeneratedGrid);
    }

    private void setGrid(final Context context, final int[][] grid)
    {
        for(int x=0; x<WIDTH; x++)
        {
            for(int y=0; y<HEIGHT; y++)
            {
                if(MinesweeperGrid[x][y]==null)
                {
                    MinesweeperGrid[x][y] = new Cell(context, x,y);
                }

                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }
    }

    public Cell getCellAt(int position)
    {
        int x = position % WIDTH;
        int y = position / WIDTH;
        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt(int x,int y)
    {
        return MinesweeperGrid[x][y];
    }

    public void click(int x, int y)
    {
        if(x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x,y).isClicked())
           {
               getCellAt(x, y).setClicked();


               if(getCellAt(x,y).getValue() == 0)
               {
                   for(int xt = -1; xt <=1; xt++)
                   {
                       for(int yt = -1; yt <=1; yt++)
                       {
                           if(xt != yt )
                           {   if(x+xt >=0 && y+yt >= 0 && x+xt <WIDTH && y+yt <HEIGHT)
                            {
                               boolean isFlagged = getCellAt(x + xt, y + yt).isFlagged();
                               boolean isRevealed = getCellAt(x + xt, y + yt).isRevealed();
                               if (!isFlagged && !isRevealed)
                                   click(x + xt, y + yt);
                            }
                           }
                       }
                   }
               }

               if(getCellAt(x,y).isBomb())
               {
                   onGameLost();
               }


           }

        checkEnd();
    }

    private boolean checkEnd()
    {
        int bombNotFound = BOMB_NUMBER;
        int notRevealed = WIDTH * HEIGHT;
        int revealed = 0;

        for(int x = 0; x<WIDTH ; x++)
        {
            for(int y = 0; y<HEIGHT ; y++)
            {
                if(getCellAt(x,y).isRevealed())
                {
                    revealed++;
                }

            }
        }

        if(revealed == (WIDTH*HEIGHT-BOMB_NUMBER))
        {
            Aftersplash.timer_on = false;
            final int scor = Aftersplash.score;
            savescore(scor);

            if(SaveScorePreferences.getsound(Aftersplash.activity))
            {
                if (mp != null) {
                    mp.reset();
                    mp.release();
                }
                mp = MediaPlayer.create(Aftersplash.activity, R.raw.game_won);
                mp.start();
            }


            //Toast.makeText(context, "Game Won", Toast.LENGTH_LONG).show();
            Aftersplash.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    //  Log.e("ifcond", Boolean.toString(gameover) + Boolean.toString(gamewin));
                    new AlertDialog.Builder(Aftersplash.activity)
                            .setTitle("You Won!")
                            .setMessage("Your Score : " + Integer.toString(scor))
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    Intent pi = new Intent(Aftersplash.activity,Menu.class);
                                    Aftersplash.activity.startActivity(pi);
                                    dialog.dismiss();
                                    Aftersplash.activity.finish();

                                }
                            }).show();
                }

            });


        }
        return false;
    }

    private void onGameLost()
    {
      //Handle lost game
       // Toast.makeText(context,"Game Over",Toast.LENGTH_LONG).show();

        if(SaveScorePreferences.getsound(Aftersplash.activity))
        {
            if (mp != null) {
                mp.reset();
                mp.release();
            }
            mp = MediaPlayer.create(Aftersplash.activity, R.raw.bomb_sound);
            mp.start();
        }

        if(SaveScorePreferences.getvibration(Aftersplash.activity))
        {
            Vibrator vib = (Vibrator) Aftersplash.activity.getSystemService(Aftersplash.activity.VIBRATOR_SERVICE);
            vib.vibrate(500);
        }

        Aftersplash.timer_on = false;
        Aftersplash.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(Aftersplash.activity)
                        .setTitle("Game Over!")
                        .setMessage("Better Luck Next Time ")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                Intent pi = new Intent(Aftersplash.activity,Menu.class);
                                Aftersplash.activity.startActivity(pi);
                                dialog.dismiss();
                                Aftersplash.activity.finish();
                            }
                        }).show();
            }

        });

        for(int x = 0; x<WIDTH ; x++)
        {
            for(int y = 0; y<HEIGHT ; y++)
            {
                if(getCellAt(x,y).isFlagged()&& !getCellAt(x,y).isBomb())
                    getCellAt(x,y).setRevealed();
                getCellAt(x,y).setRevealed();
            }
        }



    }

    public void flag(int x, int y)
    {
      boolean isFlagged = getCellAt(x,y).isFlagged();
        boolean isRevealed = getCellAt(x,y).isRevealed();
        if(!isFlagged && !isRevealed)
            bombno();
        else if (isFlagged && !isRevealed)
        {
            bombno2();

        }
        if(!isRevealed ) {
            getCellAt(x, y).setFlagged(!isFlagged);
            getCellAt(x, y).invalidate();
        }
        checkEnd();
    }

   /* public void flag(int x, int y)
    {
        boolean isFlagged = getCellAt(x,y).isFlagged();
        boolean isRevealed = getCellAt(x,y).isRevealed();

            getCellAt(x, y).setFlagged(!isFlagged);
            getCellAt(x, y).invalidate();

        checkEnd();
    }*/

    public void bombno()
    {
        String bno = (String) Aftersplash.bombno.getText();
        int bn = Integer.parseInt(bno);
        if(bn>0)
        bn--;
        else
        openflag();
        bno = Integer.toString(bn);
        Aftersplash.bombno.setText(bno);
    }
    public void bombno2()
    {
        String bno = (String) Aftersplash.bombno.getText();
        int bn = Integer.parseInt(bno);
        if(bn<15)
        bn++;
        bno = Integer.toString(bn);
        Aftersplash.bombno.setText(bno);
    }

    public void openflag()
    {
        Aftersplash.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(Aftersplash.activity)
                        .setTitle("Info!")
                        .setMessage("There is only " + BOMB_NUMBER + " bombs.")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).show();
            }

        });
    }


    public void savescore(int score)
    {
        int f,s,t;
        f = SaveScorePreferences.getfirst(Aftersplash.activity);
        s = SaveScorePreferences.getsecond(Aftersplash.activity);
        t = SaveScorePreferences.getthird(Aftersplash.activity);

        if(f==0 || s==0 || t==0)
        {
            if(f==0)
                SaveScorePreferences.savefirst(Aftersplash.activity, score);
            else if(s==0)
            {
                if(score<f)
                {
                    SaveScorePreferences.savefirst(Aftersplash.activity, score);
                    SaveScorePreferences.savesecond(Aftersplash.activity,f);
                }
                else
                    SaveScorePreferences.savesecond(Aftersplash.activity,score);
            }

            else if(t==0)
            {
                if(score<s)
                {
                    if(score<f)
                    {
                        SaveScorePreferences.savefirst(Aftersplash.activity, score);
                        SaveScorePreferences.savesecond(Aftersplash.activity, f);
                        SaveScorePreferences.savethird(Aftersplash.activity,s);
                    }
                    else
                    {
                        SaveScorePreferences.savesecond(Aftersplash.activity, score);
                        SaveScorePreferences.savethird(Aftersplash.activity,s);
                    }

                }
                else
                SaveScorePreferences.savethird(Aftersplash.activity,score);
            }
        }
        else
        {
            if(score<t)
            {
                if(score<s)
                {
                    if(score<f)
                    {
                        SaveScorePreferences.savefirst(Aftersplash.activity, score);
                        SaveScorePreferences.savesecond(Aftersplash.activity, f);
                        SaveScorePreferences.savethird(Aftersplash.activity, s);
                    }
                    else
                    {
                        SaveScorePreferences.savesecond(Aftersplash.activity, score);
                        SaveScorePreferences.savethird(Aftersplash.activity, s);
                    }
                }
                else
                    SaveScorePreferences.savethird(Aftersplash.activity,score);
            }
        }

    }

}
