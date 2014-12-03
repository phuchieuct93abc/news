package com.services.main_screen;

import java.util.ArrayList;
import java.util.List;

public class TileService {

	public static List<Tile> getList() {
		List<Tile> tiles = new ArrayList<Tile>();
		tiles.add(new Tile("KHCN", "Khoa Hoc CN", "news.jpeg","http://www.baomoi.com/Home/KHCN.rss"));
		tiles.add(new Tile("TG", "The gioi", "news.jpeg","http://www.baomoi.com/Home/TheThao.rss"));

		tiles.add(new Tile("VH", "Van hoa", "news.jpeg","http://www.baomoi.com/Home/VanHoa.rss"));

		tiles.add(new Tile("KT", "Kinh te", "news.jpeg","http://google.com"));
		tiles.add(new Tile("TT", "The thao", "news.jpeg","http://google.com"));
		tiles.add(new Tile("GT", "Giai tri", "news.jpeg","http://google.com"));
		tiles.add(new Tile("SK", "Suc khoe", "news.jpeg","http://google.com"));
		return tiles;
	}

}
