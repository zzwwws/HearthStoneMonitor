package com.example.hearthstonemonitor;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hearthstonemonitor.data.Card;
import com.example.hearthstonemonitor.data.ImageData;
import com.example.hearthstonemonitor.view.CardStaticRectView;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onNewIntent(Intent intent) {
		if(intent != null && intent.getBooleanExtra("isFromPK", false)){
			if(cardPk != null){
				cardPk.clear();
			}
		}
		super.onNewIntent(intent);
	}

	private Button[] btnHero = new Button[3];
	private ImageView[] imgHero = new ImageView[3];
	private LinearLayout llyHero;
	private LinearLayout llyStatic;
	private ImageView refreshHero;
	private TextView txtCnt;
	private ViewPager pager;
	private Button btnGoPk;
	private CardStaticRectView mpRectView = null;
	private CardStaticRectView hpRectView = null;
	private CardStaticRectView apRectView = null;
	private CardMonitorHelper monitorHelper = new CardMonitorHelper();
	CardPagerAdapter pagerAdapter = null;
	private int[] hero = new int[3];
	private ArrayList<Integer> imgArray = new ArrayList<Integer>();
	private int oc;
	private int cardCnt = 0;
	private List<Card> cardList = new ArrayList<Card>();
	private List<Card> cardListOc = new ArrayList<Card>();
	private List<Card> cardListRa = new ArrayList<Card>();
	private List<Card> cardRes = new ArrayList<Card>();
	public static List<Card> cardPk = new ArrayList<Card>();
	private static final int MSG_HERO_CHANGE = 1;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_HERO_CHANGE:
				clear();
				for (int i = 0; i < hero.length; i++) {
					int resId = ImageData.heroImage[hero[i] - 1];
					imgHero[i].setImageResource(resId);
					btnHero[i].setTag(hero[i]);
				}
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		llyHero = (LinearLayout) this.findViewById(R.id.hero_panel);
		llyStatic = (LinearLayout) this.findViewById(R.id.lly_static);
		pager = (ViewPager) this.findViewById(R.id.pager);
		txtCnt = (TextView) this.findViewById(R.id.txtcnt);

		btnHero[0] = (Button) this.findViewById(R.id.btn1);
		btnHero[1] = (Button) this.findViewById(R.id.btn2);
		btnHero[2] = (Button) this.findViewById(R.id.btn3);

		imgHero[0] = (ImageView) this.findViewById(R.id.img1);
		imgHero[1] = (ImageView) this.findViewById(R.id.img2);
		imgHero[2] = (ImageView) this.findViewById(R.id.img3);

		refreshHero = (ImageView) this.findViewById(R.id.img_actionbar_rt);
		refreshHero.setOnClickListener(this);

		btnGoPk = (Button) this.findViewById(R.id.btn_go_pk);
		btnGoPk.setOnClickListener(this);
		btnGoPk.setEnabled(false);

		for (int i = 0; i < 3; i++)
			btnHero[i].setOnClickListener(this);

		hero = monitorHelper.getRandomHero();
		if (hero.length > 1) {
			handler.sendEmptyMessage(MSG_HERO_CHANGE);
		}
		initCardData();
		pagerAdapter = new CardPagerAdapter(this, imgArray);
		pager.setAdapter(pagerAdapter);

		mpRectView = (CardStaticRectView) this.findViewById(R.id.mpview);
		mpRectView.initType(CardStaticRectView.TYPE_MP);
		hpRectView = (CardStaticRectView) this.findViewById(R.id.hpview);
		hpRectView.initType(CardStaticRectView.TYPE_HP);
		apRectView = (CardStaticRectView) this.findViewById(R.id.apview);
		apRectView.initType(CardStaticRectView.TYPE_AP);

	}

	private void clear() {
		llyStatic.setVisibility(View.GONE);
		llyHero.setVisibility(View.VISIBLE);
		pager.setVisibility(View.GONE);
		if(btnGoPk != null){
			btnGoPk.setEnabled(false);
			btnGoPk.setTextColor(Color.WHITE);
		}

		if (cardListOc != null) {
			cardListOc.clear();
		}
		if (cardListRa != null) {
			cardListRa.clear();
		}
		if (cardRes != null) {
			cardRes.clear();
		}
		if(mpRectView != null){
			mpRectView.clear();
		}
		if(hpRectView != null){
			hpRectView.clear();
		}
		if(apRectView != null){
			apRectView.clear();
		}
		cardCnt = 0;
	};

	private void getCardImgArray() {
		cardCnt++;
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				txtCnt.setText((cardCnt-1) + "/30");
			}
		});
		if (cardCnt <= 30) {
			if (imgArray != null)
				imgArray.clear();
			int rare = monitorHelper.getRandomRareNum();
			cardListRa = monitorHelper.getCardListSortByRare(cardListOc, rare);
			cardRes = monitorHelper.generateCardByRandom(cardListRa);
			for (int i = 0; i < 3; i++) {
				String id = cardRes.get(i).id;
				imgArray.add(ImageData.cardImage[Integer.parseInt(id)]);
			}
			pagerAdapter.notifyDataSetChanged();
			pager.setCurrentItem(0);
		} else {
			btnGoPk.setEnabled(true);
			btnGoPk.setTextColor(Color.BLACK);
		}

	}

	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn1:
		case R.id.btn2:
		case R.id.btn3:
			llyHero.setVisibility(View.GONE);
			llyStatic.setVisibility(View.VISIBLE);
			pager.setVisibility(View.VISIBLE);
			txtCnt.setVisibility(View.VISIBLE);
			oc = (Integer) arg0.getTag();
			cardListOc = monitorHelper.getCardListSortByOc(cardList, oc);
			getCardImgArray();
			pagerAdapter.notifyDataSetChanged();
			break;
		case R.id.img_actionbar_rt:
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setMessage("确定要重新选择英雄吗");
			builder.setTitle("提示");
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							hero = monitorHelper.getRandomHero();
							if (hero.length > 1) {
								handler.sendEmptyMessage(MSG_HERO_CHANGE);
							}
						}
					});
			Dialog mAlertDialog = builder.create();
			mAlertDialog.show();

			break;
		case R.id.btn_go_pk:
			Intent intent = new Intent(MainActivity.this, HeroPkActivity.class);
			intent.putExtra("oc", oc);
			startActivity(intent);
			break;
		}
	}

	private void initCardData() {
		cardList.addAll(SplashActivity.cardList);
	}

	class CardPagerAdapter extends PagerAdapter {

		private ArrayList<Integer> imgData = new ArrayList<Integer>();

		private LayoutInflater inflater;

		private ImageView[] imagView = new ImageView[3];

		private int[] imagId = { R.id.viewpager_item_img1,
				R.id.viewpager_item_img2, R.id.viewpager_item_img3 };

		public CardPagerAdapter(Context context, ArrayList<Integer> imgArray) {
			this.imgData = imgArray;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View convertView = null;
			switch (position) {
			case 0:
				convertView = inflater.inflate(R.layout.card_choose_viewpager,
						null);
				for (int i = 0; i < 3; i++) {
					imagView[i] = (ImageView) convertView
							.findViewById(imagId[i]);
					imagView[i].setImageResource(imgData.get(i));
					imagView[i].setTag(Integer.valueOf(i));
					imagView[i].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (cardCnt <= 30) {
								int index = (Integer) v.getTag();
								Card card = cardRes.get(index);
								refreshCardStaticView(card);
								cardPk.add(card);
								getCardImgArray();
							}

						}
					});
				}
				break;
			}
			((ViewPager) container).addView(convertView, 0);
			return convertView;
		}

	}

	private void refreshCardStaticView(Card card) {
		String mp = card.mp;
		String hp = card.hp;
		String ap = card.ap;
		int pos_mp = 0, pos_hp = 1, pos_ap = 0;
		try {
			pos_mp = Integer.parseInt(mp);
			pos_hp = Integer.parseInt(hp);
			pos_ap = Integer.parseInt(ap);
		} catch (Exception e) {

		}
		mpRectView.setArgs(pos_mp, 10);
		hpRectView.setArgs(pos_hp, 10);
		apRectView.setArgs(pos_ap, 10);
	}
}
