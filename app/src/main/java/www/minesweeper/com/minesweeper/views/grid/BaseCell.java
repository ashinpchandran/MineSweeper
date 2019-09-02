package www.minesweeper.com.minesweeper.views.grid;

import android.content.Context;
import android.view.View;

import www.minesweeper.com.minesweeper.GameEngine;

/**
 * Created by more on 9/7/2017.
 */
public abstract class BaseCell extends View
{
    private int value;
    private boolean isBomb;
    private boolean isRevealed;
    private boolean isClicked;
    private boolean isFlagged;


    private int x,y;
    private int position;

    public BaseCell(Context context)
    {
        super(context);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {

        isBomb = false;
        isRevealed = false;
        isClicked = false;
        isFlagged = false;

        if(value == -1)
        {
            isBomb =true;

        }

        this.value = value;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setIsBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed() {
       isRevealed = true;
        invalidate();
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked() {
       this.isClicked = true;
       this.isRevealed = true;

        invalidate();
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.position = y * GameEngine.WIDTH + x;
        invalidate();
    }
}
