package com.example.hearthstonemonitor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class CardStaticRectView extends View {

	int type = 0;//0 hp,1 mp, 2 ap
	int color;
	float[] left = new float[8];
	float[] top = new float[8];
	float[] right = new float[8];
	float[] bottom = new float[8];

	float rectw = 20;
	float rectgap = 5;
	int topGap = 10;;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		Paint paintext = new Paint();
		paintext.setTextSize(24);
		paintext.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setColor(color);
		for (int i = 0; i < 8; i++) {
			if(type > 0){// mp, ap
				canvas.drawRect(left[i], top[i], right[i], bottom[i], paint);
				String textb = "" + i;
				if(i >= 7)textb = "7+";
				String textt = "" + (int) (120 - top[i]) / topGap;
				canvas.drawText(textb, left[i], bottom[i] + 30, paintext);
				canvas.drawText(textt, left[i], top[i] - 10, paintext);	
			}else{ // hp
				if(i > 0){
					canvas.drawRect(left[i], top[i], right[i], bottom[i], paint);
					String textb = "" + i;
					if(i >= 7)textb = "7+";
					String textt = "" + (int) (120 - top[i]) / topGap;
					canvas.drawText(textb, left[i], bottom[i] + 30, paintext);
					canvas.drawText(textt, left[i], top[i] - 10, paintext);
				}

			}

		}

	}

	public CardStaticRectView(Context context) {
		super(context);
	}

	public CardStaticRectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		for (int i = 0; i < 8; i++) {
			left[i] = rectw * i + rectgap * i + 30;
			right[i] = left[i] + rectw;
			top[i] = 120;
			bottom[i] = 122;
		}
	}
	public static final int TYPE_HP = 0;
	public static final int TYPE_MP = 1;
	public static final int TYPE_AP = 2;
	
	
	public void initType(int type){
		this.type = type;
		switch(type){
		case 0:
//			color = Color.RED;
			color = 0xffe23227;
			break;
		case 1:
//			color = Color.BLUE;
			color = 0xff3a70bc;
			break;
		case 2:
//			color = Color.YELLOW;
			color = 0xffd8a013;
			break;
		}
		this.postInvalidate();
		
	}
	public void setArgs(int pos, float top) {

		topGap = (int) top;
		if(pos >=7){
			this.top[7] -= top;
		}else{
			this.top[pos] -= top;
		}
		this.postInvalidate();

	}
	public void clear(){
		for (int i = 0; i < 8; i++) {
			left[i] = rectw * i + rectgap * i + 30;
			right[i] = left[i] + rectw;
			top[i] = 120;
			bottom[i] = 122;
		}
		this.postInvalidate();
	}

}
