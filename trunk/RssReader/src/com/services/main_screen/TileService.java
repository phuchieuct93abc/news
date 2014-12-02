package com.services.main_screen;

import java.util.ArrayList;
import java.util.List;

public class TileService {

	public static List<Tile> getList() {
		List<Tile> tiles = new ArrayList<Tile>();
		tiles.add(new Tile("KHCN", "Khoa Hoc CN", "news.jpeg","http://google.com"));
		tiles.add(new Tile("Anh", "Anh", "news.jpeg","http://google.com"));

		tiles.add(new Tile("Phap Luat", "Phap Luat", "news.jpeg","http://google.com"));

		tiles.add(new Tile("Van hoc", "Van hoc", "news.jpeg","http://google.com"));

		

		return tiles;
	}

}
