package com.services.main_screen;

import java.util.ArrayList;
import java.util.List;

public class TileService {

	public static List<Tile> getList() {
		List<Tile> tiles = new ArrayList<Tile>();		
		tiles.add(new Tile("KHCN", "Công Nghệ", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KHCN.epi","fa-gears"));
		tiles.add(new Tile("TG", "Thế Giới", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheGioi.epi","fa-globe"));
		tiles.add(new Tile("VH", "Văn Hóa", "news.jpeg","http://m.baomoi.com/Home/mostrecent/VanHoa.epi","fa-book"));
		tiles.add(new Tile("KT", "Kinh Tế", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KinhTe.epi","fa-usd"));
		tiles.add(new Tile("TT", "Thể Thao", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheThao.epi","fa-futbol-o"));
		tiles.add(new Tile("GT", "Giải Trí", "news.jpeg","http://m.baomoi.com/Home/mostrecent/GiaiTri.epi","fa-music"));
		tiles.add(new Tile("SK", "Sức Khỏe", "news.jpeg","http://m.baomoi.com/Home/mostrecent/SucKhoe.epi","fa-heart"));
		return tiles;
	}

}
