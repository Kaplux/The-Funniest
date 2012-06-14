package fr.mildlyusefulsoftware.thefunniest.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.util.Log;
import fr.mildlyusefulsoftware.imageviewer.service.DatabaseHelper;
import fr.mildlyusefulsoftware.imageviewer.service.Picture;

public class ViewPictureActivity extends fr.mildlyusefulsoftware.imageviewer.activity.ViewPictureActivity {

	@Override
	protected String getAdMobId() {
		return "a14f805fdf159e8";
	}

	private void createPicDb(){
		try {
			Document doc = Jsoup.connect("http://thefunniest.info/top.html").data("query", "Java")
					.userAgent("Mozilla").cookie("auth", "token")
					.timeout(3000).post();
			Elements elts = doc.select("li");
			List<Picture> pictures=new ArrayList<Picture>();
			int i=0;
			for (Element elt : elts) {
				String url = elt.select("img").first().attr("src");
				Log.d("thecutest",url);
				try{
				Picture p =new Picture(i,url,Picture.getPictureThumbnail(url));
				pictures.add(p);
				}catch (Exception e) {
					// TODO: handle exception
				}
				i++;
			}

			DatabaseHelper.connect(this).putPictures(pictures);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		putNewPictures();
	}
	
	private void putNewPictures(){
		String[] url =new String[]{"http://www.dvo.com/newsletter/monthly/2003/october/FunnyVancouver.jpg","http://img375.imageshack.us/img375/4331/sealfunnyoj6.jpg",
				"http://students.cs.byu.edu/~appleman/images/funny/t.jpg","http://i18.photobucket.com/albums/b123/actress47/DSC00566.jpg",
				"http://www.worth1000.com/entries/18000/18262.jpg",
					"http://www.myconfinedspace.com/wp-content/uploads/2006/10/japanese-frog.thumbnail.jpg",
					"http://img50.imageshack.us/img50/1330/geekql6.jpg",
					"http://halbot.haluze.sk/images/2006-07/2425_flickrdeath.jpg",
					"http://mirror.servut.us/kuvat/motivation/u_talking_to_me.jpg",
					"http://i.thefairest.info/funniest_thumbs/3lZtUy.jpeg",
					"http://www.phun.org/newspics/funny_friday/1914.jpg",
					"http://homepage.ntlworld.com/tonystrading/pix/just_for_fun/mousetrap.jpg"		
					
		};
		int i=40;
		List<Picture> pictures=new ArrayList<Picture>();
		for (int j=0;j<url.length;j++){
			try {
				Log.i("thefunniest",url[j]);
				Picture p = new Picture(j+i, url[j],Picture.getPictureThumbnail(url[j]));
				pictures.add(p);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		DatabaseHelper.connect(this).putPictures(pictures);
	}


}
