package dasd;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class dasd {
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("http://www.baomoi.com/Home/CNTT/www.thanhnien.com.vn/Iron-Man-va-Gundamse-xuat-hien-trong-Tam-Quoc-Lun/15319717.epi").get();
		
		System.out.print(doc.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
