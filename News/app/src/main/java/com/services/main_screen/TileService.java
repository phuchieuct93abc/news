package com.services.main_screen;

import java.util.ArrayList;
import java.util.List;

public class TileService {

    public static List<Tile> getList() {
        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(new Tile("TN", "Tin nóng", "news.jpeg",53, "fa-heart"));
        tiles.add(new Tile("KHCN", "Công nghệ", "news.jpeg",53, "fa-gears"));
        tiles.add(new Tile("TG", "Thế giới", "news.jpeg", 53, "fa-globe"));
        tiles.add(new Tile("VH", "Văn hóa", "news.jpeg", 53, "fa-book"));
        tiles.add(new Tile("KT", "Kinh tế", "news.jpeg",53, "fa-usd"));
        tiles.add(new Tile("TT", "Thể thao", "news.jpeg",53, "fa-futbol-o"));
        tiles.add(new Tile("GT", "Giải trí", "news.jpeg", 53, "fa-music"));
        return tiles;
    }

}
