package com.example.lequan.film.subtitles;

import java.util.Hashtable;
import java.util.TreeMap;

public class TimedTextObject {
    public String author;
    public boolean built;
    public TreeMap<Integer, Caption> captions = new TreeMap();
    public String copyrigth;
    public String description;
    public String fileName;
    public String language;
    public Hashtable<String, Region> layout = new Hashtable();
    public int offset = 0;
    public Hashtable<String, Style> styling = new Hashtable();
    public String title;
    public boolean useASSInsteadOfSSA = false;
    public String warnings = "List of non fatal errors produced during parsing:\n\n";

    protected TimedTextObject() {
    }

    public String[] toSRT() {
        return new FormatSRT().toFile(this);
    }

    public String[] toASS() {
        return new FormatASS().toFile(this);
    }

    public byte[] toSTL() {
        return new FormatSTL().toFile(this);
    }

    public String[] toSCC() {
        return new FormatSCC().toFile(this);
    }

    public String[] toTTML() {
        return new FormatTTML().toFile(this);
    }

    protected void cleanUnusedStyles() {
        Hashtable<String, Style> usedStyles = new Hashtable();
        for (Caption current : this.captions.values()) {
            if (current.style != null) {
                String iD = current.style.iD;
                if (!usedStyles.containsKey(iD)) {
                    usedStyles.put(iD, current.style);
                }
            }
        }
        this.styling = usedStyles;
    }
}
