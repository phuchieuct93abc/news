package com.services.main_screen;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;

public class TileService {

	public static List<Tile> getList() {
		List<Tile> tiles = new ArrayList<Tile>();		
		tiles.add(new Tile("KHCN", "Khoa Hoc CN", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KHCN.epi"));
		tiles.add(new Tile("TG", "The gioi", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheGioi.epi"));
		tiles.add(new Tile("VH", "Van hoa", "news.jpeg","http://m.baomoi.com/Home/mostrecent/VanHoa.epi"));
		tiles.add(new Tile("KT", "Kinh te", "news.jpeg","http://m.baomoi.com/Home/mostrecent/KinhTe.epi"));
		tiles.add(new Tile("TT", "The thao", "news.jpeg","http://m.baomoi.com/Home/mostrecent/TheThao.epi"));
		tiles.add(new Tile("GT", "Giai tri", "news.jpeg","http://m.baomoi.com/Home/mostrecent/GiaiTri.epi"));
		tiles.add(new Tile("SK", "Suc khoe", "news.jpeg","http://m.baomoi.com/Home/mostrecent/SucKhoe.epi"));
		return tiles;
	}

}
