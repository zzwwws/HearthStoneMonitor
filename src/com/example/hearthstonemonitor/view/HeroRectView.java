package com.example.hearthstonemonitor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class HeroRectView extends View {

	float left_h, right_h, top_h, bot_h;
	float left_m, right_m, top_m, bot_m;
	float rectH = 20;
	float rectgap = 15;

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(0xffe23227);
		canvas.drawRect(left_h, top_h, right_h, bot_h, paint);
		paint.setTextSize(18);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		String textht = "HP";
		String texthb = "" + (int) ((right_h-left_h) / 10*1.5);
		canvas.drawText(textht, 0, top_h + 10, paint);
		canvas.drawText(texthb, 0, bot_h+10, paint);
		paint.setColor(0xff3a70bc);
		canvas.drawRect(left_m, top_m, right_m, bot_m, paint);
		String textmt = "MP";
		String textmb = "" + (int) ((right_m - left_m) / 10*1.5);
		canvas.drawText(textmt, 0, top_m + 10, paint);
		canvas.drawText(textmb, 0, bot_m+10, paint);

	}

	public HeroRectView(Context context) {
		super(context);
	}

	public HeroRectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		left_h = 30;
		top_h = 10;
		right_h = 230;
		bot_h = top_h+rectH;
		
		left_m = 30;
		top_m = bot_h + rectgap;
		right_m = 230;
		bot_m = top_m + rectH;
	}
	


	public void setArgs(int type, int point) {

		if(type == 0){
			right_h -= 200*point/30;
			if(right_h <= 30)right_h = 30;
		}else{
			right_m -= 200*point/30;
			if(right_m <= 30)right_m = 30;
		}
		this.postInvalidate();
	}
	
	public void init(){
		left_h = 30;
		top_h = 10;
		right_h = 230;
		bot_h = top_h+rectH;
		
		left_m = 30;
		top_m = bot_h + rectgap;
		right_m = 230;
		bot_m = top_m + rectH;
		
		this.postInvalidate();
	}

}
