package com.example.rapfilm.subtitles;

import com.example.rapfilm.TtmlNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FormatTTML implements TimedTextFileFormat {
    public TimedTextObject parseFile(String fileName, InputStream is) throws IOException, FatalParsingException {
        int i;
        TimedTextObject tto = new TimedTextObject();
        tto.fileName = fileName;
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
        Node node = doc.getElementsByTagName("ttm:title").item(0);
        if (node != null) {
            tto.title = node.getTextContent();
        }
        node = doc.getElementsByTagName("ttm:copyright").item(0);
        if (node != null) {
            tto.copyrigth = node.getTextContent();
        }
        node = doc.getElementsByTagName("ttm:desc").item(0);
        if (node != null) {
            tto.description = node.getTextContent();
        }
        NodeList styleN = doc.getElementsByTagName(TtmlNode.TAG_STYLE);
        NodeList captionsN = doc.getElementsByTagName(TtmlNode.TAG_P);
        tto.warnings += "Styling attributes are only recognized inside a style definition, to be referenced later in the captions.\n\n";
        for (i = 0; i < styleN.getLength(); i++) {
            Style style = null;
            Style style2 = new Style(Style.defaultID());
            NamedNodeMap attr = styleN.item(i).getAttributes();
            Node currentAtr = attr.getNamedItem(TtmlNode.ATTR_ID);
            if (currentAtr != null) {
                style2.iD = currentAtr.getNodeValue();
            }
            currentAtr = attr.getNamedItem("xml:id");
            if (currentAtr != null) {
                style2.iD = currentAtr.getNodeValue();
            }
            currentAtr = attr.getNamedItem(TtmlNode.TAG_STYLE);
            if (currentAtr != null && tto.styling.containsKey(currentAtr.getNodeValue())) {
                style = new Style(style2.iD, (Style) tto.styling.get(currentAtr.getNodeValue()));
            }
            currentAtr = attr.getNamedItem("tts:backgroundColor");
            if (currentAtr != null) {
                style.backgroundColor = parseColor(currentAtr.getNodeValue(), tto);
            }
            currentAtr = attr.getNamedItem("tts:color");
            if (currentAtr != null) {
                style.color = parseColor(currentAtr.getNodeValue(), tto);
            }
            currentAtr = attr.getNamedItem("tts:fontFamily");
            if (currentAtr != null) {
                style.font = currentAtr.getNodeValue();
            }
            currentAtr = attr.getNamedItem("tts:fontSize");
            if (currentAtr != null) {
                style.fontSize = currentAtr.getNodeValue();
            }
            currentAtr = attr.getNamedItem("tts:fontStyle");
            if (currentAtr != null) {
                if (currentAtr.getNodeValue().equalsIgnoreCase(TtmlNode.ITALIC) || currentAtr.getNodeValue().equalsIgnoreCase("oblique")) {
                    style.italic = true;
                } else if (currentAtr.getNodeValue().equalsIgnoreCase("normal")) {
                    style.italic = false;
                }
            }
            currentAtr = attr.getNamedItem("tts:fontWeight");
            if (currentAtr != null) {
                if (currentAtr.getNodeValue().equalsIgnoreCase(TtmlNode.BOLD)) {
                    style.bold = true;
                } else {
                    try {
                        if (currentAtr.getNodeValue().equalsIgnoreCase("normal")) {
                            style.bold = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new FatalParsingException("Error during parsing: " + e.getMessage());
                    }
                }
            }
            currentAtr = attr.getNamedItem("tts:opacity");
            if (currentAtr != null) {
                try {
                    float alpha = Float.parseFloat(currentAtr.getNodeValue());
                    if (alpha > 1.0f) {
                        alpha = 1.0f;
                    } else if (alpha < 0.0f) {
                        alpha = 0.0f;
                    }
                    String aa = Integer.toHexString((int) (255.0f * alpha));
                    if (aa.length() < 2) {
                        aa = "0" + aa;
                    }
                    style.color = style.color.substring(0, 6) + aa;
                    style.backgroundColor = style.backgroundColor.substring(0, 6) + aa;
                } catch (NumberFormatException e2) {
                }
            }
            currentAtr = attr.getNamedItem("tts:textAlign");
            if (currentAtr != null) {
                if (currentAtr.getNodeValue().equalsIgnoreCase(TtmlNode.LEFT) || currentAtr.getNodeValue().equalsIgnoreCase(TtmlNode.START)) {
                    style.textAlign = "bottom-left";
                } else if (currentAtr.getNodeValue().equalsIgnoreCase(TtmlNode.RIGHT) || currentAtr.getNodeValue().equalsIgnoreCase(TtmlNode.END)) {
                    style.textAlign = "bottom-right";
                }
            }
            currentAtr = attr.getNamedItem("tts:textDecoration");
            if (currentAtr != null) {
                if (currentAtr.getNodeValue().equalsIgnoreCase(TtmlNode.UNDERLINE)) {
                    style.underline = true;
                } else if (currentAtr.getNodeValue().equalsIgnoreCase("noUnderline")) {
                    style.underline = false;
                }
            }
            tto.styling.put(style.iD, style);
        }
        for (i = 0; i < captionsN.getLength(); i++) {
            Caption caption = new Caption();
            caption.content = "";
            boolean validCaption = true;
            node = captionsN.item(i);
            NamedNodeMap  attr = node.getAttributes();
            Node currentAtr = attr.getNamedItem("begin");
            caption.start = new Time("", "");
            caption.end = new Time("", "");
            if (currentAtr != null) {
                caption.start.mseconds = parseTimeExpression(currentAtr.getNodeValue(), tto, doc);
            }
            currentAtr = attr.getNamedItem(TtmlNode.END);
            if (currentAtr != null) {
                caption.end.mseconds = parseTimeExpression(currentAtr.getNodeValue(), tto, doc);
            } else {
                currentAtr = attr.getNamedItem("dur");
                if (currentAtr != null) {
                    caption.end.mseconds = caption.start.mseconds + parseTimeExpression(currentAtr.getNodeValue(), tto, doc);
                } else {
                    validCaption = false;
                }
            }
            currentAtr = attr.getNamedItem(TtmlNode.TAG_STYLE);
            if (currentAtr != null) {
                Style style = (Style) tto.styling.get(currentAtr.getNodeValue());
                if (style != null) {
                    caption.style = style;
                } else {
                    tto.warnings += "unrecoginzed style referenced: " + currentAtr.getNodeValue() + "\n\n";
                }
            }
            NodeList textN = node.getChildNodes();
            for (int j = 0; j < textN.getLength(); j++) {
                if (textN.item(j).getNodeName().equals("#text")) {
                    caption.content += textN.item(j).getTextContent().trim();
                } else if (textN.item(j).getNodeName().equals(TtmlNode.TAG_BR)) {
                    caption.content += "<br />";
                }
            }
            if (caption.content.replaceAll("<br />", "").trim().isEmpty()) {
                validCaption = false;
            }
            if (validCaption) {
                int key = caption.start.mseconds;
                while (tto.captions.containsKey(Integer.valueOf(key))) {
                    key++;
                }
                tto.captions.put(Integer.valueOf(key), caption);
            }
        }
        tto.built = true;
        return tto;
    }

    public TimedTextObject parseFile(String fileName, InputStream is, String endcode) throws IOException, FatalParsingException {
        return null;
    }

    public String[] toFile(TimedTextObject tto) {
        if (!tto.built) {
            return null;
        }
        String title;
        ArrayList<String> file = new ArrayList((tto.styling.size() + 30) + tto.captions.size());
        int i = 0 + 1;
        file.add(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        int i2 = i + 1;
        file.add(i, "<tt xml:lang=\"" + tto.language + "\" xmlns=\"http://www.w3.org/ns/ttml\" xmlns:tts=\"http://www.w3.org/ns/ttml#styling\">");
        i = i2 + 1;
        file.add(i2, "\t<head>");
        i2 = i + 1;
        file.add(i, "\t\t<metadata xmlns:ttm=\"http://www.w3.org/ns/ttml#metadata\">");
        if (tto.title == null || tto.title.isEmpty()) {
            title = tto.fileName;
        } else {
            title = tto.title;
        }
        i = i2 + 1;
        file.add(i2, "\t\t\t<ttm:title>" + title + "</ttm:title>");
        if (tto.copyrigth == null || tto.copyrigth.isEmpty()) {
            i2 = i;
        } else {
            i2 = i + 1;
            file.add(i, "\t\t\t<ttm:copyright>" + tto.copyrigth + "</ttm:copyright>");
        }
        String desc = "Converted by the Online Subtitle Converter developed by J. David Requejo\n";
        if (!(tto.author == null || tto.author.isEmpty())) {
            desc = desc + "\n Original file by: " + tto.author + StringUtils.LF;
        }
        if (!(tto.description == null || tto.description.isEmpty())) {
            desc = desc + tto.description + StringUtils.LF;
        }
        i = i2 + 1;
        file.add(i2, "\t\t\t<ttm:desc>" + desc + "\t\t\t</ttm:desc>");
        i2 = i + 1;
        file.add(i, "\t\t</metadata>");
        i = i2 + 1;
        file.add(i2, "\t\t<styling>");
        i2 = i;
        for (Style style : tto.styling.values()) {
            String line = "\t\t\t<style xml:id=\"" + style.iD + "\"";
            if (style.color != null) {
                line = line + " tts:color=\"#" + style.color + "\"";
            }
            if (style.backgroundColor != null) {
                line = line + " tts:backgroundColor=\"#" + style.backgroundColor + "\"";
            }
            if (style.font != null) {
                line = line + " tts:fontFamily=\"" + style.font + "\"";
            }
            if (style.fontSize != null) {
                line = line + " tts:fontSize=\"" + style.fontSize + "\"";
            }
            if (style.italic) {
                line = line + " tts:fontStyle=\"italic\"";
            }
            if (style.bold) {
                line = line + " tts:fontWeight=\"bold\"";
            }
            line = line + " tts:textAlign=\"";
            if (style.textAlign.contains(TtmlNode.LEFT)) {
                line = line + "left\"";
            } else if (style.textAlign.contains(TtmlNode.RIGHT)) {
                line = line + "rigth\"";
            } else {
                line = line + "center\"";
            }
            if (style.underline) {
                line = line + " tts:textDecoration=\"underline\"";
            }
            i = i2 + 1;
            file.add(i2, line + " />");
            i2 = i;
        }
        i = i2 + 1;
        file.add(i2, "\t\t</styling>");
        i2 = i + 1;
        file.add(i, "\t</head>");
        i = i2 + 1;
        file.add(i2, "\t<body>");
        i2 = i + 1;
        file.add(i, "\t\t<div>");
        for (Caption caption : tto.captions.values()) {
          String  line = ("\t\t\t<p begin=\"" + caption.start.getTime("hh:mm:ss,ms").replace(',', ClassUtils.PACKAGE_SEPARATOR_CHAR) + "\"") + " end=\"" + caption.end.getTime("hh:mm:ss,ms").replace(',', ClassUtils.PACKAGE_SEPARATOR_CHAR) + "\"";
            if (caption.style != null) {
                line = line + " style=\"" + caption.style.iD + "\"";
            }
            i = i2 + 1;
            file.add(i2, line + " >" + caption.content + "</p>\n");
            i2 = i;
        }
        i = i2 + 1;
        file.add(i2, "\t\t</div>");
        i2 = i + 1;
        file.add(i, "\t</body>");
        i = i2 + 1;
        file.add(i2, "</tt>");
        i2 = i + 1;
        file.add(i, "");
        String[] toReturn = new String[file.size()];
        for (int i3 = 0; i3 < toReturn.length; i3++) {
            toReturn[i3] = (String) file.get(i3);
        }
        return toReturn;
    }

    private String parseColor(String color, TimedTextObject tto) {
        String value = "";
        if (color.startsWith("#")) {
            if (color.length() == 7) {
                return color.substring(1) + "ff";
            }
            if (color.length() == 9) {
                return color.substring(1);
            }
            value = "ffffffff";
            tto.warnings += "Unrecoginzed format: " + color + "\n\n";
            return value;
        } else if (color.startsWith("rgb")) {
            boolean alpha = false;
            if (color.startsWith("rgba")) {
                alpha = true;
            }
            try {
                String[] values = color.split("")[1].split(",");
                int a = 255;
                int r = Integer.parseInt(values[0]);
                int g = Integer.parseInt(values[1]);
                int b = Integer.parseInt(values[2].substring(0, 2));
                if (alpha) {
                    a = Integer.parseInt(values[3].substring(0, 2));
                }
                values[0] = Integer.toHexString(r);
                values[1] = Integer.toHexString(g);
                values[2] = Integer.toHexString(b);
                if (alpha) {
                    values[2] = Integer.toHexString(a);
                }
                for (int i = 0; i < values.length; i++) {
                    if (values[i].length() < 2) {
                        values[i] = "0" + values[i];
                    }
                    value = value + values[i];
                }
                if (alpha) {
                    return value;
                }
                return value + "ff";
            } catch (Exception e) {
                value = "ffffffff";
                tto.warnings += "Unrecoginzed color: " + color + "\n\n";
                return value;
            }
        } else {
            value = Style.getRGBValue("name", color);
            if (value != null && !value.isEmpty()) {
                return value;
            }
            value = "ffffffff";
            tto.warnings += "Unrecoginzed color: " + color + "\n\n";
            return value;
        }
    }

    private int parseTimeExpression(String timeExpression, TimedTextObject tto, Document doc) {
        Node n;
        if (timeExpression.contains(":")) {
            String[] parts = timeExpression.split(":");
            if (parts.length == 3) {
                return ((3600000 * Integer.parseInt(parts[0])) + (60000 * Integer.parseInt(parts[1]))) + ((int) (1000.0f * Float.parseFloat(parts[2])));
            } else if (parts.length != 4) {
                return 0;
            } else {
                int frameRate = 25;
                n = doc.getElementsByTagName("ttp:frameRate").item(0);
                if (n != null) {
                    try {
                        frameRate = Integer.parseInt(n.getNodeValue());
                    } catch (NumberFormatException e) {
                    }
                }
                return (((3600000 * Integer.parseInt(parts[0])) + (60000 * Integer.parseInt(parts[1]))) + (Integer.parseInt(parts[2]) * 1000)) + ((int) ((1000.0f * Float.parseFloat(parts[3])) / ((float) frameRate)));
            }
        }
        String metric = timeExpression.substring(timeExpression.length() - 1);
        try {
            double time = Double.parseDouble(timeExpression.substring(0, timeExpression.length() - 1).replace(',', ClassUtils.PACKAGE_SEPARATOR_CHAR).trim());
            if (metric.equalsIgnoreCase("h")) {
                return (int) (3600000.0d * time);
            }
            if (metric.equalsIgnoreCase("m")) {
                return (int) (60000.0d * time);
            }
            if (metric.equalsIgnoreCase("s")) {
                return (int) (1000.0d * time);
            }
            if (metric.equalsIgnoreCase("ms")) {
                return (int) time;
            }
            if (metric.equalsIgnoreCase("f")) {
                n = doc.getElementsByTagName("ttp:frameRate").item(0);
                if (n != null) {
                    return (int) ((1000.0d * time) / ((double) Integer.parseInt(n.getNodeValue())));
                }
                return 0;
            } else if (!metric.equalsIgnoreCase("t")) {
                return 0;
            } else {
                n = doc.getElementsByTagName("ttp:tickRate").item(0);
                if (n != null) {
                    return (int) ((1000.0d * time) / ((double) Integer.parseInt(n.getNodeValue())));
                }
                return 0;
            }
        } catch (NumberFormatException e2) {
            return 0;
        }
    }
}
