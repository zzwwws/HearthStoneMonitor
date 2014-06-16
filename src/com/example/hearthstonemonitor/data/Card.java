package com.example.hearthstonemonitor.data;


public class Card{

	public String id;
	//卡牌名字
	public String nameCn;
	//英文名
	public String nameEn;
	//职业
	public String occupation;
	//稀有度
	public String rare;
	//类型
	public String type;
	//法力
	public String mp;
	//生命力
	public String hp;
	//攻击
	public String ap;
	//特殊技能
	public String skillNum;
	//描述
	public String skillDesc;
	//卡牌图片链接
	public String imgUrl;
	
	public void print(){
		System.out.println("nameCn: "+ nameCn);
		System.out.println("nameEn: "+ nameEn);
		System.out.println("occupation: "+ occupation);
		System.out.println("rare: "+ rare);
		System.out.println("mp: "+ mp);
		System.out.println("hp: "+ hp);
		System.out.println("ap: "+ ap);
		System.out.println("skillNum: "+ skillNum);
		System.out.println("skillDesc: "+ skillDesc);
		System.out.println("imgUrl: "+ imgUrl);
		
		
	}
}
