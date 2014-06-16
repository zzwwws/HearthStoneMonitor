package com.example.hearthstonemonitor.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.example.hearthstonemonitor.data.Card;

public class XmlUtils {

	public static ArrayList<Card> parserXml(InputStream is) {
		SAXReader reader = new SAXReader();
		ArrayList<Card> cardList = new ArrayList<Card>();
		try {
			Document document = reader.read(is);

			Element root = document.getRootElement();

			/**
			 * ±éÀúXML½Úµã
			 */
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element el = (Element) i.next();

				Card card = new Card();
				card.id = el.elementText("id");
				card.nameCn = el.elementText("nameCn");
				card.nameEn = el.elementText("nameEn");
				card.occupation = el.elementText("occupation");
				card.rare = el.elementText("rare");
				card.type = el.elementText("type");
				card.mp = el.elementText("mp");
				card.hp = el.elementText("hp");
				card.ap = el.elementText("ap");
				card.skillNum = el.elementText("skillNum");
				card.skillDesc = el.elementText("skillDesc");
				card.imgUrl = el.elementText("imgUrl");
				
				cardList.add(card);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return cardList;
	}
}
