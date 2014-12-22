package com.services.main_screen;

import java.util.ArrayList;
import java.util.List;

public class TileService {

	public static List<Tile> getList() {
		List<Tile> tiles = new ArrayList<Tile>();		
		tiles.add(new Tile("KHCN", "Cong Nghe", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KHCN.epi","fa-gears"));
		tiles.add(new Tile("TG", "The gioi", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheGioi.epi","fa-globe"));
		tiles.add(new Tile("VH", "Van hoa", "news.jpeg","http://m.baomoi.com/Home/mostrecent/VanHoa.epi","fa-book"));
		tiles.add(new Tile("KT", "Kinh te", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KinhTe.epi","fa-usd"));
		tiles.add(new Tile("TT", "The thao", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheThao.epi","fa-futbol-o"));
		tiles.add(new Tile("GT", "Giai tri", "news.jpeg","http://m.baomoi.com/Home/mostrecent/GiaiTri.epi","fa-music"));
		tiles.add(new Tile("SK", "Suc khoe", "news.jpeg","http://m.baomoi.com/Home/mostrecent/SucKhoe.epi","fa-heart"));
		return tiles;
	}

}
