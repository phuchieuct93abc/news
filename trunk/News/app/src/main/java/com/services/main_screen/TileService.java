package com.services.main_screen;

import com.activity.CategoryScreen;
import com.services.CategoryService_JSON;

import java.util.ArrayList;
import java.util.List;

public class TileService {
private static String zone = CategoryService_JSON.ZONE_LIST_TYPE;
    private static String special = CategoryService_JSON.SPECIAL_LIST_TYPE;

    public static List<Tile> getList() {

        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(new Tile("TN", "Tin nóng", "news.jpeg",2,special, "fa-heart"));
        tiles.add(new Tile("KHCN", "Công nghệ", "news.jpeg",53,zone, "fa-gears"));
        tiles.add(new Tile("TG", "Thế giới", "news.jpeg", 53,zone, "fa-globe"));
        tiles.add(new Tile("VH", "Văn hóa", "news.jpeg", 53,zone, "fa-book"));
        tiles.add(new Tile("KT", "Kinh tế", "news.jpeg",53,zone, "fa-usd"));
        tiles.add(new Tile("TT", "Thể thao", "news.jpeg",53,zone, "fa-futbol-o"));
        tiles.add(new Tile("GT", "Giải trí", "news.jpeg", 53,zone, "fa-music"));
        return tiles;
    }

}
