package com.example.hearthstonemonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hearthstonemonitor.data.Card;
import com.example.hearthstonemonitor.data.ImageData;
import com.example.hearthstonemonitor.view.HeroRectView;
import com.example.hearthstonemonitor.view.NPCRectView;

public class HeroPkActivity extends Activity implements OnClickListener {
	private ScrollView sv;
	private LinearLayout lly;
	private Button btn;
	private ImageView heroUse;
	private ImageView imgHero, imgNpc;
	private TextView txtHero, txtNpc;
	private static final float CELL_HEIGHT = -236.0f;
	private Random random = new Random();
	private int hero;
	private int npc;
	private String heroName = "";
	private String npcName = "";
	private boolean isChooseHero = true;
	private HeroRectView heroRect;
	private NPCRectView npcRect;
	private List<Card> cardListHero = new ArrayList<Card>();
	private List<Card> cardListNpc = new ArrayList<Card>();
	private int[] imgArrayHero = new int[30];
	private int[] imgArrayNpc = new int[30];
	private int round = 0;
	private int npcHp = 30, npcMp = 30, heroHp = 30, heroMp = 30;

	private static final int MSG_NEXT_CARD = 1;
	private static final int MSG_CLEAR = 2;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_CLEAR:
				heroRect.init();
				npcRect.init();
				
				btn.setText("匹配对手");
				btn.setEnabled(true);
				isChooseHero = true;
				
				round = -1;
				npc = 0;
				cardListNpc.clear();
				npcHp = 30;
				npcMp = 30;
				heroHp = 30;
				heroMp = 30;
				
				findViewById(R.id.lly_pk).setVisibility(View.GONE);
				txtHero.setVisibility(View.GONE);
				txtNpc.setVisibility(View.GONE);
				break;
			case MSG_NEXT_CARD:
				if(round < 0){
					break;
				}
				if (round < 30) {
					imgHero.setImageResource(imgArrayHero[round]);
					imgNpc.setImageResource(imgArrayNpc[round]);

					Animation anim = AnimationUtils.loadAnimation(
							HeroPkActivity.this, R.anim.fade_in);
					imgHero.startAnimation(anim);
					imgNpc.startAnimation(anim);
					Card hero = cardListHero.get(round);
					Card npc = cardListNpc.get(round);
					int npchpchange, npcmpchange, herohpchange, herompchange;

					// hero mp
					herompchange = Integer.parseInt(hero.mp);
					heroMp -= herompchange;
					heroRect.setArgs(1, herompchange);

					// npc mp
					npcmpchange = Integer.parseInt(npc.mp);
					npcMp -= npcmpchange;
					npcRect.setArgs(1, npcmpchange);

					if (heroMp <= 0) {// hero no mp
						npchpchange = 1;
					} else {
						// npc hp
						try {
							npchpchange = Integer.parseInt(hero.ap);
						} catch (Exception e) {
							npchpchange = 0;
						}
						if (npchpchange == 0) {
							try{
								npchpchange = random.nextInt(Integer
										.parseInt(hero.mp));
							}catch(Exception e){
								npchpchange = 0;
							}

						}
					}
					npcHp -= npchpchange;
					npcRect.setArgs(0, npchpchange);
					if (npcHp <= 0) {
						txtNpc.setText("");
						txtHero.setText("玩家"+heroName + "赢得了比赛");
						animationAfterPk(txtHero);
						break;
					}
					txtHero.setText(heroName + "使用了" + hero.nameCn + "，" + "对"
							+ npcName + "造成了" + npchpchange + "点伤害");

					if (npcMp <= 0) {
						herohpchange = 1;
					} else {
						// hero hp
						try {
							herohpchange = Integer.parseInt(npc.ap);
						} catch (Exception e) {
							herohpchange = 0;
						}
						if (herohpchange == 0) {
							try{
								herohpchange = random.nextInt(Integer
										.parseInt(npc.mp));
							}catch(Exception e){
								herohpchange = 0;
							}

						}
					}

					heroHp -= herohpchange;
					heroRect.setArgs(0, herohpchange);
					if (heroHp <= 0) {
						txtHero.setText("");
						txtNpc.setText("对手"+npcName + "赢得了比赛");
						break;
					}
					txtNpc.setText(npcName + "使用了" + npc.nameCn + "，" + "对"
							+ heroName + "造成了" + herohpchange + "点伤害");


					round++;
					handler.sendEmptyMessageDelayed(MSG_NEXT_CARD, 4000);
				}

				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pk);
		heroRect = (HeroRectView) this.findViewById(R.id.hero_rect);
		npcRect = (NPCRectView) this.findViewById(R.id.npc_rect);
		lly = (LinearLayout) this.findViewById(R.id.lly_img);
		imgHero = (ImageView) this.findViewById(R.id.card_img1);
		imgNpc = (ImageView) this.findViewById(R.id.card_img2);
		txtHero = (TextView) this.findViewById(R.id.txthero);
		txtNpc = (TextView) this.findViewById(R.id.txtnpc);
		heroUse = (ImageView) this.findViewById(R.id.img_hero);
		sv = (ScrollView) this.findViewById(R.id.sv);
		sv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				return true;
			}
		});
		TextView title = (TextView) this.findViewById(R.id.txt_actionbar);
		title.setText("英雄PK");
		ImageView back = (ImageView) this.findViewById(R.id.img_actionbar_lf);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		ImageView refresh = (ImageView) this
				.findViewById(R.id.img_actionbar_rt);
		refresh.setOnClickListener(this);
		initData();

		btn = (Button) this.findViewById(R.id.btn);
		btn.setOnClickListener(this);

	}

	private void initData() {

		cardListHero.addAll(MainActivity.cardPk);
		for (int i = 0; i < 30; i++) {
			int id = Integer.parseInt(cardListHero.get(i).id);
			imgArrayHero[i] = ImageData.cardImage[id];
		}
		hero = getIntent().getIntExtra("oc", 0);
		heroUse.setImageResource(ImageData.heroImage[hero - 1]);
		heroName = CardMonitorHelper.getOcByNum(hero);
		for (int i = 0; i < 40; i++) {
			ImageView img = new ImageView(this);
			int ra = random.nextInt(9);
			img.setImageDrawable(this.getResources().getDrawable(
					ImageData.heroImage[ra]));
			img.setTag(ra + 1);
			lly.addView(img);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn:
			// TODO Auto-generated method stub
			if (isChooseHero) {
				int ra = 30 + random.nextInt(9);
				npc = (Integer) lly.getChildAt(ra).getTag();
				npcName = CardMonitorHelper.getOcByNum(npc);
				getNpcCardList(npc);
				Animation anim = new TranslateAnimation(0, 0, 0.0f, CELL_HEIGHT
						* ra);
				anim.setFillAfter(true);
				anim.setDuration(ra * 200);
				anim.setInterpolator(new DecelerateInterpolator());
				anim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation arg0) {
						// TODO Auto-generated method stub
						btn.setEnabled(false);
					}

					@Override
					public void onAnimationRepeat(Animation arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation arg0) {
						// TODO Auto-generated method stub
						isChooseHero = false;
						btn.setText("开始PK");
						btn.setEnabled(true);
					}
				});
				lly.startAnimation(anim);
			} else {// 开始pk
				btn.setEnabled(false);
				findViewById(R.id.lly_pk).setVisibility(View.VISIBLE);
				txtHero.setVisibility(View.VISIBLE);
				txtNpc.setVisibility(View.VISIBLE);
				round = 0;
				handler.sendEmptyMessage(MSG_NEXT_CARD);
			}

			break;
		case R.id.img_actionbar_lf:
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setMessage("确定要重新选择卡牌吗");
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

							Intent intent = new Intent(HeroPkActivity.this,
									MainActivity.class);
							intent.putExtra("isFromPK", true);
							startActivity(intent);
							HeroPkActivity.this.finish();
						}
					});
			Dialog mAlertDialog = builder.create();
			mAlertDialog.show();

			break;
		case R.id.img_actionbar_rt:
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this)
					.setMessage("确定要重新选择pk英雄吗");
			builder1.setTitle("提示");
			builder1.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			builder1.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							handler.sendEmptyMessage(MSG_CLEAR);							
						}
					});
			Dialog mAlertDialog1 = builder1.create();
			mAlertDialog1.show();
			break;
		}
	}
	
	private void animationAfterPk(View view){
		findViewById(R.id.lly_pk).setVisibility(View.GONE);
		Animation anim = new TranslateAnimation(0, 0, 0.0f, CELL_HEIGHT
				* 100);
		anim.setFillAfter(true);
		anim.setDuration(600);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
			((ImageView)findViewById(R.id.card_bag)).setVisibility(View.VISIBLE);
			((ImageView)findViewById(R.id.card_bag)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(HeroPkActivity.this, OpenCardActivity.class);
					startActivity(intent);
					finish();
				}
			});
			}
		});
		view.setAnimation(anim);
	}

	private void getNpcCardList(final int npc) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				CardMonitorHelper monitorHelper = new CardMonitorHelper();
				List<Card> cardListOc = monitorHelper.getCardListSortByOc(
						SplashActivity.cardList, npc);
				int size = cardListOc.size();
				for (int i = 0; i < 30; i++) {
					int ra = random.nextInt(size);
					cardListNpc.add(cardListOc.get(ra));
					int id = Integer.parseInt(cardListNpc.get(i).id);
					imgArrayNpc[i] = ImageData.cardImage[id];
				}
			}

		}).start();
	}

}
