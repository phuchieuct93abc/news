package com.services.main_screen;

import com.services.CategoryService;

import java.util.ArrayList;
import java.util.List;

public class TileService {

    public static List<Tile> getList() {

        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(new Tile("TN", "Tin nóng", "news.jpeg", CategoryService.TINNONG, "fa-heart"));
        tiles.add(new Tile("KHCN", "Công nghệ", "news.jpeg", CategoryService.KHCN, "fa-gears"));
        tiles.add(new Tile("TG", "Thế giới", "news.jpeg", CategoryService.THEGIOI, "fa-globe"));
        tiles.add(new Tile("KT", "Kinh tế", "news.jpeg", CategoryService.KINHTE, "fa-usd"));
        tiles.add(new Tile("TT", "Thể thao", "news.jpeg", CategoryService.THETHAO, "fa-futbol-o"));
        tiles.add(new Tile("TNhanh", "Tin ảnh", "news.jpeg", CategoryService.TINNANH, "fa-music"));
        tiles.add(new Tile("AD", "Ảnh đẹp", "news.jpeg", CategoryService.ANHDEP, "fa-music"));
        tiles.add(new Tile("CN", "CNET", "news.jpeg", CategoryService.CNET, "fa-book"));
        return tiles;
    }

}
