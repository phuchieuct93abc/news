package com.services.main_screen;

import com.services.CategoryService;

import java.util.ArrayList;
import java.util.List;

public class TileService {

    public static List<Tile> getList() {

        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(new Tile("TN", "Tin nóng", "news.jpeg", CategoryService.TINNONG, "fa-heart", "success"));
        tiles.add(new Tile("KHCN", "Công nghệ", "news.jpeg", CategoryService.KHCN, "fa-gears", "primary"));
        tiles.add(new Tile("TG", "Thế giới", "news.jpeg", CategoryService.THEGIOI, "fa-globe", "info"));
        tiles.add(new Tile("XH", "Xã hội", "news.jpeg", CategoryService.XAHOI, "fa-book", "warning"));
        tiles.add(new Tile("KT", "Kinh tế", "news.jpeg", CategoryService.KINHTE, "fa-usd", "danger"));
        tiles.add(new Tile("TT", "Thể thao", "news.jpeg", CategoryService.THETHAO, "fa-futbol-o", "success"));
        tiles.add(new Tile("GT", "Giải trí", "news.jpeg", CategoryService.GIAITRI, "fa-music", "primary"));
        tiles.add(new Tile("TNhanh", "Tin ảnh", "news.jpeg", CategoryService.TINNANH, "fa-music", "info"));
        tiles.add(new Tile("AD", "Ảnh đẹp", "news.jpeg", CategoryService.ANHDEP, "fa-music", "danger"));


        return tiles;
    }

}
