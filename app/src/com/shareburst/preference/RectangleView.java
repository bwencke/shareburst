package com.shareburst.preference;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

public class RectangleView extends View {
	protected ShapeDrawable mDrawable;
	private int level;
	private int color; //Gray = 0, Red = 1; Yellow = 2; Orange = 3; Pink = 4;

	public RectangleView(Context context) {
		super(context);
	}
	
    public RectangleView(Context context, int level, int color) {
    	super(context);
    	this.level = level;
    	this.color = color;

    	int x = 10;
    	int y = 10;
    	int width = 200;
    	int height = 2000;

    	//Resources res = getResources();
    	//mDrawable = (ShapeDrawable) res.getDrawable(R.drawable.rectangle_red);
    	mDrawable = new ShapeDrawable(new RectShape());
    	mDrawable.getPaint().setColor(Color.rgb(223, 224, 224));
    	mDrawable.setBounds(x, y, x + width, y + height);
    }
    
    public void setColor(int c){
    	mDrawable.getPaint().setColor(c);
    }
    
    public void onClick(){
    	mDrawable.getPaint().setColor(0xff74AC23);
    }

    protected void onDraw(Canvas canvas) {
    	mDrawable.draw(canvas);
    }

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getColorId() {
		return color;
	}
	
	public void setColorId(int color){
		this.color = color;
	}

}
