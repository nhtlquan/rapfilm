package com.example.rapfilm.model;

public class Syn {
    public Favourite bookmark;
    public String id;
    public Recent recent;
    public TypeSyn typeSyn;

    public Syn(String episodeId, TypeSyn typeSyn, Recent recent) {
        this.id = episodeId;
        this.typeSyn = typeSyn;
        this.recent = recent;
    }

    public Syn(String filmId, TypeSyn typeSyn, Favourite bookmark) {
        this.id = filmId;
        this.typeSyn = typeSyn;
        this.bookmark = bookmark;
    }
}
