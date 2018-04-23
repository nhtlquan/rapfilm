package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.example.lequan.film.common.FilmPreferences;
import com.example.lequan.film.common.Language;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilmDetail implements Serializable  {
    private String[] actors;
    private String banner;
    @SerializedName("base_link_trailer")
    private String baseLinkTrailer;
    private boolean children;
    private String country;
    private Poster cover;
    private String description;
    private String[] director;
    private int download;
    private boolean english_subtitle;
    private ArrayList<Season> episodes = new ArrayList();
    @SerializedName("user_favourite")
    private boolean favourited = false;
    private List<Category> genre = new ArrayList();
    private String id;
    private String imdb_code;
    private float imdb_rate;
    private int imdb_votes;
    private String language;
    private int lastDuration;
    private int like;
    private ArrayList<Video> link_trailer = new ArrayList();
    private int markRated;
    private int metascore;
    private String name;
    private String name_vi;
    @SerializedName("notification")
    private boolean notify;
    private int order;
    private Poster poster;
    private String quality;
    private boolean rate;
    @SerializedName("rate_info")
    private Rate rateInfo;
    private int rating;
    private String released;
    private String runtime;
    private boolean season;
    private String slug;
    private ArrayList<Film> suggestion = new ArrayList();
    private int total_comment;
    private boolean tv_show;
    private String type;
    private long updated_time;
    @SerializedName("user_rate")
    private Rate userRated;
//    private boolean vietnamese_subtitle;
    private int view;
    private boolean voice_over;
    private int votes;
    private String[] writer;
    private String year;


    public ArrayList<Video> getLink_trailer() {
        return this.link_trailer;
    }

    public void setLink_trailer(ArrayList<Video> link_trailer) {
        this.link_trailer = link_trailer;
    }

    public String getBaseLinkTrailer() {
        return this.baseLinkTrailer;
    }

    public void setBaseLinkTrailer(String baseLinkTrailer) {
        this.baseLinkTrailer = baseLinkTrailer;
    }

    public Poster getCoverFilm() {
        return this.cover;
    }

    public void setCoverFilm(Poster coverFilm) {
        this.cover = coverFilm;
    }

    public boolean isVoice_over() {
        return this.voice_over;
    }

    public void setVoice_over(boolean voice_over) {
        this.voice_over = voice_over;
    }

//    public boolean isVietnamese_subtitle() {
//        return this.vietnamese_subtitle;
//    }
//
//    public void setVietnamese_subtitle(boolean vietnamese_subtitle) {
//        this.vietnamese_subtitle = vietnamese_subtitle;
//    }

    public boolean isEnglish_subtitle() {
        return this.english_subtitle;
    }

    public void setEnglish_subtitle(boolean english_subtitle) {
        this.english_subtitle = english_subtitle;
    }

    public String getQuality() {
        return this.quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public boolean isTv_show() {
        return this.tv_show;
    }

    public void setTv_show(boolean tv_show) {
        this.tv_show = tv_show;
    }

    public String getName_vi() {
        return this.name_vi;
    }

    public void setName_vi(String name_vi) {
        this.name_vi = name_vi;
    }

    public boolean isFavourited() {
        return this.favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }

    public String getBanner() {
        return this.banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getMark() {
        return this.markRated;
    }

    public void setMark(int mark) {
        this.markRated = mark;
    }

    public boolean isRate() {
        return this.rate;
    }

    public void setRate(boolean rate) {
        this.rate = rate;
    }

    public boolean isSeason() {
        return this.season;
    }

    public void setSeason(boolean season) {
        this.season = season;
    }

    public float getImdb_rate() {
        return this.imdb_rate;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setTotal_comment(int total_comment) {
        this.total_comment = total_comment;
    }

    public int getTotal_comment() {
        return this.total_comment;
    }

    public void setImdb_rate(float imdb_rate) {
        this.imdb_rate = imdb_rate;
    }

    public int getImdb_votes() {
        return this.imdb_votes;
    }

    public void setImdb_votes(int imdb_votes) {
        this.imdb_votes = imdb_votes;
    }

    public String getImdb_code() {
        return this.imdb_code;
    }

    public void setImdb_code(String imdb_code) {
        this.imdb_code = imdb_code;
    }

    public int getMetascore() {
        return this.metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReleased() {
        return this.released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return this.runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String[] getDirector() {
        return this.director;
    }

    public void setDirector(String[] director) {
        this.director = director;
    }

    public String[] getWriter() {
        return this.writer;
    }

    public void setWriter(String[] writer) {
        this.writer = writer;
    }

    public String[] getActors() {
        return this.actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return this.votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getDownload() {
        return this.download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public int getView() {
        return this.view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getLike() {
        return this.like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isChildren() {
        return this.children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public long getUpdated_time() {
        return this.updated_time;
    }

    public void setUpdated_time(long updated_time) {
        this.updated_time = updated_time;
    }

    public List<Category> getGenre() {
        int i = 0;
        while (i < this.genre.size()) {
            if (((Category) this.genre.get(i)).getName() == null) {
                this.genre.remove(i);
            } else {
                i++;
            }
        }
        return this.genre;
    }

    public void setGenre(List<Category> genre) {
        this.genre.addAll(genre);
    }

    public Poster getPoster() {
        return this.poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Film> getSuggestion() {
        return this.suggestion;
    }

    public void setSuggestion(ArrayList<Film> suggestion) {
        this.suggestion.addAll(suggestion);
    }

    public ArrayList<Season> getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(ArrayList<Season> episodes) {
        this.episodes.addAll(episodes);
    }

    public int getMarkRated() {
        return this.markRated;
    }

    public void setMarkRated(int markRated) {
        this.markRated = markRated;
    }

    public Rate getUserRated() {
        return this.userRated;
    }

    public void setUserRated(Rate userRated) {
        this.userRated = userRated;
    }

    public Rate getRateInfo() {
        return this.rateInfo;
    }

    public void setRateInfo(Rate rateInfo) {
        this.rateInfo = rateInfo;
    }

    public boolean isNotify() {
        return this.notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public String getTitleWithEpisode(int season, int episode) {
        if (isSeason()) {
            return getTitle() + " - " + ((Season) this.episodes.get(season)).getName() + " - " + ((Episode) ((Season) this.episodes.get(season)).getContents().get(episode)).getName();
        }
        if (this.type.equals(Type.TV_SERIES.toString())) {
            return getTitle() + " - " + ((Episode) ((Season) this.episodes.get(season)).getContents().get(episode)).getName();
        }
        return getTitle();
    }

    public String getTitle() {
        return (FilmPreferences.getInstance().getLanguage() != Language.VI || this.name_vi.length() <= 0) ? this.name : this.name_vi;
    }

    public void setLastDuration(int lastDuration) {
        this.lastDuration = lastDuration;
    }

    public int getLastDuration() {
        return this.lastDuration;
    }

}
