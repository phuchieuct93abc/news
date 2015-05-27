package com.services.main_screen;

import com.services.CategoryService_JSON;

import java.util.ArrayList;
import java.util.List;

public class TileService {

    public static List<Tile> getList() {

        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(new Tile("TN", "Tin nóng", "news.jpeg", CategoryService_JSON.TINNONG, "fa-heart"));
        tiles.add(new Tile("KHCN", "Công nghệ", "news.jpeg", CategoryService_JSON.KHCN, "fa-gears"));
        tiles.add(new Tile("TG", "Thế giới", "news.jpeg", CategoryService_JSON.THEGIOI, "fa-globe"));
        tiles.add(new Tile("XH", "Xã hội", "news.jpeg", CategoryService_JSON.XAHOI, "fa-book"));
        tiles.add(new Tile("KT", "Kinh tế", "news.jpeg", CategoryService_JSON.KINHTE, "fa-usd"));
        tiles.add(new Tile("TT", "Thể thao", "news.jpeg", CategoryService_JSON.THETHAO, "fa-futbol-o"));
        tiles.add(new Tile("GT", "Giải trí", "news.jpeg", CategoryService_JSON.GIAITRI, "fa-music"));
        tiles.add(new Tile("TNhanh", "Tin ảnh", "news.jpeg", CategoryService_JSON.TINNANH, "fa-music"));
        tiles.add(new Tile("AD", "Ảnh đẹp", "news.jpeg", CategoryService_JSON.ANHDEP, "fa-music"));


        return tiles;
    }

}
