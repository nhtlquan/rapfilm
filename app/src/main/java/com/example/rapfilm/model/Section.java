package com.example.rapfilm.model;

import com.example.rapfilm.TtmlNode;

public enum Section {
    HEADER {
        public String toString() {
            return "header";
        }
    },
    MIDDLE {
        public String toString() {
            return "middle";
        }
    },
    FOOTER {
        public String toString() {
            return "footer";
        }
    },
    LEFT {
        public String toString() {
            return TtmlNode.LEFT;
        }
    },
    RIGHT {
        public String toString() {
            return TtmlNode.RIGHT;
        }
    }
}
