package com.example.hearthstonemonitor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.hearthstonemonitor.data.Card;
import com.example.hearthstonemonitor.utils.XmlUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity{

	public static ArrayList<Card> cardList = new ArrayList<Card>();
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 1:
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				SplashActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		}
		
		
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
			    long timePast = initData();
				long time = 0;
				if(timePast < 4000){
					time = 4000 - timePast;
				}
				handler.sendEmptyMessageDelayed(1, time);
			}
			
		}).start();
	}

	private long initData(){
		long pretime = System.currentTimeMillis();
		AssetManager am = getAssets();
		InputStream is;
		try {
			is = am.open("cardList.xml");
			cardList = XmlUtils.parserXml(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long curtime = System.currentTimeMillis();
		return (curtime-pretime);
	}

	
}
