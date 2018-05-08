package com.example.rapfilm.subtitles;

public class Style {
    private static int styleCounter;
    protected String backgroundColor;
    protected boolean bold;
    protected String color;
    protected String font;
    protected String fontSize;
    protected String iD;
    protected boolean italic;
    protected String textAlign = "";
    protected boolean underline;

    protected Style(String styleName) {
        this.iD = styleName;
    }

    protected Style(String styleName, Style style) {
        this.iD = styleName;
        this.font = style.font;
        this.fontSize = style.fontSize;
        this.color = style.color;
        this.backgroundColor = style.backgroundColor;
        this.textAlign = style.textAlign;
        this.italic = style.italic;
        this.underline = style.underline;
        this.bold = style.bold;
    }

    protected static String getRGBValue(String format, String value) {
        if (format.equalsIgnoreCase("name")) {
            if (value.equals("transparent")) {
                return "00000000";
            }
            if (value.equals("black")) {
                return "000000ff";
            }
            if (value.equals("silver")) {
                return "c0c0c0ff";
            }
            if (value.equals("gray")) {
                return "808080ff";
            }
            if (value.equals("white")) {
                return "ffffffff";
            }
            if (value.equals("maroon")) {
                return "800000ff";
            }
            if (value.equals("red")) {
                return "ff0000ff";
            }
            if (value.equals("purple")) {
                return "800080ff";
            }
            if (value.equals("fuchsia")) {
                return "ff00ffff";
            }
            if (value.equals("magenta")) {
                return "ff00ffff ";
            }
            if (value.equals("green")) {
                return "008000ff";
            }
            if (value.equals("lime")) {
                return "00ff00ff";
            }
            if (value.equals("olive")) {
                return "808000ff";
            }
            if (value.equals("yellow")) {
                return "ffff00ff";
            }
            if (value.equals("navy")) {
                return "000080ff";
            }
            if (value.equals("blue")) {
                return "0000ffff";
            }
            if (value.equals("teal")) {
                return "008080ff";
            }
            if (value.equals("aqua")) {
                return "00ffffff";
            }
            if (value.equals("cyan")) {
                return "00ffffff ";
            }
            return null;
        } else if (format.equalsIgnoreCase("&HBBGGRR")) {
            StringBuilder  sb = new StringBuilder();
            sb.append(value.substring(6));
            sb.append(value.substring(4, 5));
            sb.append(value.substring(2, 3));
            sb.append("ff");
            return sb.toString();
        } else if (format.equalsIgnoreCase("&HAABBGGRR")) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.substring(8));
            sb.append(value.substring(6, 7));
            sb.append(value.substring(4, 5));
            sb.append(value.substring(2, 3));
            return sb.toString();
        } else if (format.equalsIgnoreCase("decimalCodedBBGGRR")) {
            String  color = Integer.toHexString(Integer.parseInt(value));
            while (color.length() < 6) {
                color = "0" + color;
            }
            return color.substring(4) + color.substring(2, 4) + color.substring(0, 2) + "ff";
        } else if (!format.equalsIgnoreCase("decimalCodedAABBGGRR")) {
            return null;
        } else {
            String color = Long.toHexString(Long.parseLong(value));
            while (color.length() < 8) {
                color = "0" + color;
            }
            return color.substring(6) + color.substring(4, 6) + color.substring(2, 4) + color.substring(0, 2);
        }
    }

    protected static String defaultID() {
        StringBuilder append = new StringBuilder().append("default");
        int i = styleCounter;
        styleCounter = i + 1;
        return append.append(i).toString();
    }
}
