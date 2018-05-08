package com.example.rapfilm.model;

public enum Type {
    MOVIE {
        public String toString() {
            return "movie";
        }
    },
    TV_SERIES {
        public String toString() {
            return "series";
        }
    },
    TV_SHOW {
        public String toString() {
            return "tvshow";
        }
    },
    ANIME {
        public String toString() {
            return "anime";
        }
    },
    BANNER {
        public String toString() {
            return "banner";
        }
    },
    COLLECTION {
        public String toString() {
            return "collection";
        }
    },
    NONE {
        public String toString() {
            return "none";
        }
    }
}
