package com.services.main_screen;

import java.util.ArrayList;
import java.util.List;

public class TileService {

	public static List<Tile> getList() {
		List<Tile> tiles = new ArrayList<Tile>();		
		tiles.add(new Tile("KHCN", "CÃ´ng NghÃªÌ£", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KHCN/p/1.epi","fa-gears"));
		tiles.add(new Tile("TG", "ThÃªÌ� GiÆ¡Ì�i", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheGioi/p/1.epi","fa-globe"));
		tiles.add(new Tile("VH", "VÄƒn HoÌ�a", "news.jpeg","http://m.baomoi.com/Home/mostrecent/VanHoa/p/1.epi","fa-book"));
		tiles.add(new Tile("KT", "Kinh TÃªÌ�", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KinhTe/p/1.epi","fa-usd"));
		tiles.add(new Tile("TT", "ThÃªÌ‰ Thao", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheThao/p/1.epi","fa-futbol-o"));
		tiles.add(new Tile("GT", "GiaÌ‰i TriÌ�", "news.jpeg","http://m.baomoi.com/Home/mostrecent/GiaiTri/p/1.epi","fa-music"));
		tiles.add(new Tile("SK", "SÆ°Ì�c KhoÌ‰e", "news.jpeg","http://m.baomoi.com/Home/mostrecent/SucKhoe/p/1.epi","fa-heart"));
		return tiles;
	}

}
