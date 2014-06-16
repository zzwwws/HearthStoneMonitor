package com.example.hearthstonemonitor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class NPCRectView extends View {

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
		canvas.drawText(textht, 210, top_h + 10, paint);
		canvas.drawText(texthb, 210, bot_h+10, paint);
		paint.setColor(0xff3a70bc);
		canvas.drawRect(left_m, top_m, right_m, bot_m, paint);
		String textmt = "MP";
		String textmb = "" + (int) ((right_m-left_m) / 10*1.5);
		canvas.drawText(textmt, 210, top_m + 10, paint);
		canvas.drawText(textmb, 210, bot_m+10, paint);

	}

	public NPCRectView(Context context) {
		super(context);
	}

	public NPCRectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		left_h = 0;
		top_h = 10;
		right_h = 200;
		bot_h = top_h+rectH;
		
		left_m = 0;
		top_m = bot_h + rectgap;
		right_m = 200;
		bot_m = top_m + rectH;
	}
	


	public void setArgs(int type, int point) {
		if(type == 0){
			left_h += 200*point/30;
			if(left_h >= 200)left_h = 200;
		}else{
			left_m += 200*point/30;
			if(left_m >= 200)left_m = 200;
		}
		this.postInvalidate();
	}

	public void init(){
		left_h = 0;
		top_h = 10;
		right_h = 200;
		bot_h = top_h+rectH;
		
		left_m = 0;
		top_m = bot_h + rectgap;
		right_m = 200;
		bot_m = top_m + rectH;
		this.postInvalidate();
	}
}
