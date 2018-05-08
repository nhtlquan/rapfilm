package com.example.rapfilm.model;

import com.example.rapfilm.TtmlNode;

public enum Page {
    START {
        public String toString() {
            return TtmlNode.START;
        }
    },
    DISCOVER {
        public String toString() {
            return "discover";
        }
    },
    TOP_NEW {
        public String toString() {
            return "top_new";
        }
    },
    TOP_DOWNLOAD {
        public String toString() {
            return "top_download";
        }
    },
    MOVIE {
        public String toString() {
            return "movie";
        }
    },
    SERIES {
        public String toString() {
            return "series";
        }
    },
    LIBRARY {
        public String toString() {
            return "library";
        }
    },
    DETAIL {
        public String toString() {
            return "detail";
        }
    }
}
