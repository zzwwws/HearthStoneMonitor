package com.example.hearthstonemonitor.data;


public class Card{

	public String id;
	//��������
	public String nameCn;
	//Ӣ����
	public String nameEn;
	//ְҵ
	public String occupation;
	//ϡ�ж�
	public String rare;
	//����
	public String type;
	//����
	public String mp;
	//������
	public String hp;
	//����
	public String ap;
	//���⼼��
	public String skillNum;
	//����
	public String skillDesc;
	//����ͼƬ����
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
