package com.example.hearthstonemonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.hearthstonemonitor.data.Card;

public class CardMonitorHelper {

	private Random random = new Random();

	public CardMonitorHelper() {

	}

	public int[] getRandomHero() {
		int[] hero = new int[3];
		int ra = 0;
		while (ra == 0) {
			ra = random.nextInt(10);
		}
		hero[0] = ra;
		while (ra == 0 || ra == hero[0]) {
			ra = random.nextInt(10);
		}
		hero[1] = ra;
		while (ra == 0 || ra == hero[0] || ra == hero[1]) {
			ra = random.nextInt(10);
		}
		hero[2] = ra;
		return hero;
	}

	public ArrayList<Card> getCardListSortByOc(List<Card> cardList, int oc) {
		String occupation = getOcByNum(oc);
		System.out.println(occupation);
		ArrayList<Card> cardListOc = new ArrayList<Card>();
		for (Card cardItem : cardList) {
			if (cardItem.occupation.equals(occupation)
					|| cardItem.occupation.equals("����")) {
				cardListOc.add(cardItem);
			}
		}
		return cardListOc;
	}

	public ArrayList<Card> getCardListSortByRare(List<Card> cardListOc, int rare) {
		ArrayList<Card> cardListRa = new ArrayList<Card>();
		String ra = getRareByNum(rare);
		System.out.println(ra);
		for (Card cardItem : cardListOc) {
			if (cardItem.rare.equals(ra)) {
				cardListRa.add(cardItem);
			}
		}
		return cardListRa;
	}

	public ArrayList<Card> generateCardByRandom(List<Card> cardListRa) {
		ArrayList<Card> cardRes = new ArrayList<Card>();
		int neutralCnt = 0, heroCnt = 0;
		for (Card cardItem : cardListRa) {
			if (cardItem.occupation.equals("����")) {
				neutralCnt++;
			} else
				heroCnt++;
		}
		int rate = neutralCnt / heroCnt;
		System.out.println("===============neutralCnt==========" + neutralCnt);
		System.out.println("===============heroCnt==========" + heroCnt);
		Random random = new Random();

		for (int i = 0; i < 3; i++) {
			int rateRan = random.nextInt(rate);
			System.out.println("===============rateran==========" + rateRan);
			int id = 0;
			while(id == 0 || cardRes.contains(cardListRa.get(id))){
				if (rateRan <= rate / 2) {
					id = random.nextInt(neutralCnt);
				} else {
					id = random.nextInt(heroCnt) + neutralCnt;
					if(id == neutralCnt)id = random.nextInt(neutralCnt);
				}
			}
			cardRes.add(cardListRa.get(id));
			System.out.println("===============add success==========" + i);
//			cardListRa.get(id).print();
		}
		return cardRes;
	}
	

	private static String getRareByNum(int rare) {
		String res = "";
		switch (rare) {
		case 0:
			res = "���";
			break;
		case 1:
			res = "��ͨ";
			break;
		case 2:
			res = "ϡ��";
			break;
		case 3:
			res = "ʷʫ";
			break;
		case 4:
			res = "��˵";
			break;
		}
		return res;
	}

	public static String getOcByNum(int oc) {
		String res = "";
		switch (oc) {
		case 0:
			res = "����";
			break;
		case 1:
			res = "��ʦ";
			break;
		case 2:
			res = "����";
			break;
		case 3:
			res = "��ʦ";
			break;
		case 4:
			res = "��ʿ";
			break;
		case 5:
			res = "սʿ";
			break;
		case 6:
			res = "����";
			break;
		case 7:
			res = "����";
			break;
		case 8:
			res = "ʥ��ʿ";
			break;
		case 9:
			res = "��³��";
			break;
		}
		return res;
	}

	public int getRandomRareNum() {
		int rare = random.nextInt(30);
		int rareNum = 0;
		if (rare >= 0 && rare <= 2)
			rareNum = 4;
		else if (rare > 2 && rare <= 5)
			rareNum = 3;
		else if (rare > 5 && rare <= 15)
			rareNum = 2;
		else if (rare > 15 && rare <= 25)
			rareNum = 1;
		else
			rare = 0;
		return rareNum;
	}

}
