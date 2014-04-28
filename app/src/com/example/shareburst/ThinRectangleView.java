package com.example.shareburst;

import android.content.Context;

public class ThinRectangleView extends RectangleView {
	
	public ThinRectangleView(Context context, int level, int color){
		super(context, level, color);
		mDrawable.setBounds(10, 10, 210, 20);
	}
}
