package com.services.main_screen;

import java.util.ArrayList;
import java.util.List;

public class TileService {

	public static List<Tile> getList() {
		List<Tile> tiles = new ArrayList<Tile>();		
		tiles.add(new Tile("KHCN", "Công nghệ", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KHCN/p/1.epi","fa-gears"));
		tiles.add(new Tile("TG", "Thế giới", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheGioi/p/1.epi","fa-globe"));
		tiles.add(new Tile("VH", "Văn hóa", "news.jpeg","http://m.baomoi.com/Home/mostrecent/VanHoa/p/1.epi","fa-book"));
		tiles.add(new Tile("KT", "Kinh tế", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KinhTe/p/1.epi","fa-usd"));
		tiles.add(new Tile("TT", "Thể thao", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheThao/p/1.epi","fa-futbol-o"));
		tiles.add(new Tile("GT", "Giải trí", "news.jpeg","http://m.baomoi.com/Home/mostrecent/GiaiTri/p/1.epi","fa-music"));
		tiles.add(new Tile("SK", "Sức khỏe", "news.jpeg","http://m.baomoi.com/Home/mostrecent/SucKhoe/p/1.epi","fa-heart"));
		return tiles;
	}

}
