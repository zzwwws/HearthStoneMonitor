package com.example.hearthstonemonitor;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hearthstonemonitor.data.Card;
import com.example.hearthstonemonitor.data.ImageData;

public class OpenCardActivity extends Activity implements OnClickListener{

	private ImageView[] imgArray = new ImageView[5];
	private int[] imgRes = {R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5};
	private Button shareBtn;

	private ArrayList<Card> cardlist = new ArrayList<Card>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_card);
		for(int i = 0; i < 5; i++){
			imgArray[i] = (ImageView)this.findViewById(imgRes[i]);
			imgArray[i].setOnClickListener(this);
		}
		shareBtn = (Button)this.findViewById(R.id.btn_share);
		shareBtn.setOnClickListener(this);
		this.findViewById(R.id.img_actionbar_lf).setOnClickListener(this);
		this.findViewById(R.id.img_actionbar_rt).setOnClickListener(this);
		
		cardlist.addAll(SplashActivity.cardList);
		getOpenCard();
	}

	private void getOpenCard(){
		Random random = new Random();
		for(int i = 0; i< 5; i++){
			int id = random.nextInt(381);
			imgArray[i].setTag(id);
		}
		

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.img1:
		case R.id.img2:
		case R.id.img3:
		case R.id.img4:
		case R.id.img5:
			arg0.setEnabled(false);
			int id = (Integer) arg0.getTag();
			int resId = ImageData.cardImage[id];
			((ImageView)arg0).setImageResource(resId);
			break;
		case R.id.btn_share:
			Intent intent = new Intent(OpenCardActivity.this, MainActivity.class);
			intent.putExtra("isFromPK", true);
			startActivity(intent);
			this.finish();
			break;
		case R.id.img_actionbar_lf:
			break;
		case R.id.img_actionbar_rt:
			break;
		}
		
	}

	
}
