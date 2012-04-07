package fr.mildlyusefulsoftware.thefunniest.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;
import fr.mildlyusefulsoftware.imageviewer.service.DatabaseHelper;
import fr.mildlyusefulsoftware.imageviewer.service.Picture;

public class ViewPictureActivity extends fr.mildlyusefulsoftware.imageviewer.activity.ViewPictureActivity {

	@Override
	protected String getAdMobId() {
		return "a14f805d0f7e457";
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

}
