package www.minesweeper.com.minesweeper.views.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.View;

import www.minesweeper.com.minesweeper.Aftersplash;
import www.minesweeper.com.minesweeper.GameEngine;
import www.minesweeper.com.minesweeper.R;
import www.minesweeper.com.minesweeper.SaveScorePreferences;

/**
 * Created by more on 9/7/2017.
 */
public class Cell extends BaseCell implements View.OnClickListener  //, View.OnLongClickListener
{
    public static  MediaPlayer mp = null;

    public Cell(Context context, int x, int y)
    {
        super(context);
        setPosition(x,y);

        setOnClickListener(this);
        //setOnLongClickListener(this);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawButton(canvas);

        if(isFlagged())
        {
            drawFlag(canvas);
        }
        else if(isRevealed() && isBomb() && !isClicked())
        {
            drawNormalBomb(canvas);
        }
        else
        {
            if(isClicked())
            {
                if(getValue() == -1)
                {
                    drawBombExploded(canvas);
                }
                else
                    drawNumber(canvas);
            }
            else
            {
                drawButton(canvas);
            }
        }
    }

    private void drawBombExploded(Canvas canvas) {

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_normal);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawFlag(Canvas canvas) {

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.flag);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawWrongFlag(Canvas canvas) {

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.wrong_flag);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawButton(Canvas canvas) {

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.yellow);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void drawNormalBomb(Canvas canvas) {

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_normal);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    private void clear(Canvas canvas)
    {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
    }

    private void drawNumber(Canvas canvas)
    {
        Drawable drawable = null;
        switch (getValue())
        {
            case 0:
                    clear(canvas);
                   drawable = ContextCompat.getDrawable(getContext(), R.drawable.white);
                   break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_one);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_8);
                break;

        }

        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    @Override
    public void onClick(View v) {
        if(Aftersplash.flagswitch==false)
        {
            if(SaveScorePreferences.getsound(Aftersplash.activity))
            {
                if (mp != null) {
                    mp.reset();
                    mp.release();
                }
                mp = MediaPlayer.create(Aftersplash.activity, R.raw.single_click);
                mp.start();
            }
            GameEngine.getInstance().click(getXPos(), getYPos());
        }
        else
        {
            if(SaveScorePreferences.getsound(Aftersplash.activity))
            {
                if (mp != null) {
                    mp.reset();
                    mp.release();
                }
                mp = MediaPlayer.create(Aftersplash.activity, R.raw.flag_click);
                mp.start();
            }
            if(SaveScorePreferences.getvibration(Aftersplash.activity))
            {
                Vibrator vib = (Vibrator) Aftersplash.activity.getSystemService(Aftersplash.activity.VIBRATOR_SERVICE);
                vib.vibrate(100);
            }
            GameEngine.getInstance().flag(getXPos(),getYPos());
        }

    }



   /* public boolean onLongClick(View v) {
        GameEngine.getInstance().flag(getXPos(),getYPos());
        return true;
    }*/
}
