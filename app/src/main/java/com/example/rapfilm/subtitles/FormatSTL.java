package com.example.rapfilm.subtitles;

import com.example.rapfilm.TtmlNode;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.prober.HebrewProber;
import org.mozilla.universalchardet.prober.contextanalysis.SJISContextAnalysis;

public class FormatSTL implements TimedTextFileFormat {
    public TimedTextObject parseFile(String fileName, InputStream is) throws IOException, FatalParsingException {
        TimedTextObject tto = new TimedTextObject();
        tto.fileName = fileName;
        byte[] gsiBlock = new byte[1024];
        byte[] ttiBlock = new byte[128];
        try {
            createSTLStyles(tto);
            if (is.read(gsiBlock) < 1024) {
                throw new FatalParsingException("The file must contain at least a GSI block");
            }
            int fps = Integer.parseInt(new String(new byte[]{gsiBlock[6], gsiBlock[7]}));
            int table = Integer.parseInt(new String(new byte[]{gsiBlock[12], gsiBlock[13]}));
            byte[] opt = new byte[32];
            System.arraycopy(gsiBlock, 16, opt, 0, 32);
            String str = new String(opt);
            byte[] oet = new byte[32];
            System.arraycopy(gsiBlock, 48, oet, 0, 32);
            String episodeTitle = new String(oet);
            int numberOfTTIBlocks = Integer.parseInt(new String(new byte[]{gsiBlock[HebrewProber.NORMAL_MEM], gsiBlock[239], gsiBlock[240], gsiBlock[SJISContextAnalysis.HIRAGANA_LOWBYTE_END], gsiBlock[242]}));
            int numberOfSubtitles = Integer.parseInt(new String(new byte[]{gsiBlock[243], gsiBlock[HebrewProber.NORMAL_PE], gsiBlock[HebrewProber.FINAL_TSADI], gsiBlock[HebrewProber.NORMAL_TSADI], gsiBlock[247]}));
            tto.title = (str.trim() + StringUtils.SPACE + episodeTitle.trim()).trim();
            if (table > 4 || table < 0) {
                tto.warnings += "Invalid Character Code table number, corrupt data? will try to parse anyways assuming it is latin.\n\n";
            } else if (table != 0) {
                tto.warnings += "Only latin alphabet supported for import from STL, other languages may produce unexpected results.\n\n";
            }
            int subtitleNumber = 0;
            boolean additionalText = false;
            Caption currentCaption = null;
            int i = 0;
            while (i < numberOfTTIBlocks) {
                if (is.read(ttiBlock) < 128) {
                    tto.warnings += "Unexpected end of file, " + i + " blocks read, expecting " + numberOfTTIBlocks + " blocks in total.\n\n";
                    break;
                }
                if (!additionalText) {
                    currentCaption = new Caption();
                }
                if (ttiBlock[1] + (ttiBlock[2] * 256) != subtitleNumber) {
                    tto.warnings += "Unexpected subtitle number at TTI block " + i + ". Parsing proceeds...\n\n";
                }
                if (ttiBlock[3] != -1) {
                    additionalText = true;
                } else {
                    additionalText = false;
                }
                String startTime = "" + ttiBlock[5] + ":" + ttiBlock[6] + ":" + ttiBlock[7] + ":" + ttiBlock[8];
                String endTime = "" + ttiBlock[9] + ":" + ttiBlock[10] + ":" + ttiBlock[11] + ":" + ttiBlock[12];
                int justification = ttiBlock[14];
                if (ttiBlock[15] == (byte) 0) {
                    byte[] textField = new byte[112];
                    System.arraycopy(ttiBlock, 16, textField, 0, 112);
                    if (additionalText) {
                        parseTextForSTL(currentCaption, textField, justification, tto);
                    } else {
                        currentCaption.start = new Time("h:m:s:f/fps", startTime + "/" + fps);
                        currentCaption.end = new Time("h:m:s:f/fps", endTime + "/" + fps);
                        parseTextForSTL(currentCaption, textField, justification, tto);
                    }
                }
                if (!additionalText) {
                    subtitleNumber++;
                }
                i++;
            }
            if (subtitleNumber != numberOfSubtitles) {
                tto.warnings += "Number of parsed subtitles (" + subtitleNumber + ") different from expected number of subtitles (" + numberOfSubtitles + ").\n\n";
            }
            is.close();
            tto.cleanUnusedStyles();
            tto.built = true;
            return tto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FatalParsingException("Format error in the file, migth be due to corrupt data.\n" + e.getMessage());
        }
    }

    public TimedTextObject parseFile(String fileName, InputStream is, String endcode) throws IOException, FatalParsingException {
        return null;
    }

    public byte[] toFile(TimedTextObject tto) {
        if (!tto.built) {
            return null;
        }
        int i;
        byte[] gsiBlock = new byte[1024];
        byte[] ttiBlock = new byte[128];
        byte[] file = new byte[((tto.captions.size() * 128) + 1024)];
        byte[] extra = "850STL25.0110000".getBytes();
        System.arraycopy(extra, 0, gsiBlock, 0, extra.length);
        extra = tto.title.getBytes();
        for (i = 0; i < 208; i++) {
            if (i < extra.length) {
                gsiBlock[i + 16] = extra[i];
            } else {
                gsiBlock[i + 16] = (byte) 32;
            }
        }
        String aux = new SimpleDateFormat("yyMMdd").format(new Date());
        aux = aux + aux + "00";
        String aux2 = "" + tto.captions.size();
        while (aux2.length() < 5) {
            aux2 = "0" + aux2;
        }
        extra = (((aux + aux2 + aux2 + "0013216100000000") + ((Caption) tto.captions.get(tto.captions.firstKey())).start.getTime("hhmmssff/25")) + "11OOO").getBytes();
        System.arraycopy(extra, 0, gsiBlock, 224, extra.length);
        for (i = 277; i < 1024; i++) {
            gsiBlock[i] = (byte) 32;
        }
        System.arraycopy(gsiBlock, 0, file, 0, gsiBlock.length);
        int subtitleNumber = 0;
        for (Caption currentC : tto.captions.values()) {
            int pos;
            ttiBlock[0] = (byte) 0;
            ttiBlock[1] = (byte) (subtitleNumber % 256);
            ttiBlock[2] = (byte) (subtitleNumber / 256);
            ttiBlock[3] = (byte) -1;
            ttiBlock[4] = (byte) 0;
            String[] timeCode = currentC.start.getTime("h:m:s:f/25").split(":");
            ttiBlock[5] = Byte.parseByte(timeCode[0]);
            ttiBlock[6] = Byte.parseByte(timeCode[1]);
            ttiBlock[7] = Byte.parseByte(timeCode[2]);
            ttiBlock[8] = Byte.parseByte(timeCode[3]);
            timeCode = currentC.end.getTime("h:m:s:f/25").split(":");
            ttiBlock[9] = Byte.parseByte(timeCode[0]);
            ttiBlock[10] = Byte.parseByte(timeCode[1]);
            ttiBlock[11] = Byte.parseByte(timeCode[2]);
            ttiBlock[12] = Byte.parseByte(timeCode[3]);
            ttiBlock[13] = (byte) 18;
            if (currentC.style == null) {
                ttiBlock[14] = (byte) 2;
            } else if (currentC.style.textAlign.contains(TtmlNode.LEFT)) {
                ttiBlock[14] = (byte) 1;
            } else if (currentC.style.textAlign.contains(TtmlNode.RIGHT)) {
                ttiBlock[14] = (byte) 3;
            }
            ttiBlock[15] = (byte) 0;
            String[] lines = currentC.content.split("<br />");
            int i2 = 16;
            for (i = 0; i < lines.length; i++) {
                lines[i] = lines[i].replaceAll("\\<.*?\\>", "");
            }
            if (currentC.style != null) {
                Style style = currentC.style;
                if (style.italic) {
                    pos = 16 + 1;
                    ttiBlock[16] = Byte.MIN_VALUE;
                    i2 = pos;
                } else {
                    pos = 16 + 1;
                    ttiBlock[16] = (byte) -127;
                    i2 = pos;
                }
                if (style.underline) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) -126;
                    i2 = pos;
                } else {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) -125;
                    i2 = pos;
                }
                String color = style.color.substring(0, 6);
                if (color.equalsIgnoreCase("000000")) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) 0;
                    i2 = pos;
                } else if (color.equalsIgnoreCase("0000ff")) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) 4;
                    i2 = pos;
                } else if (color.equalsIgnoreCase("00ffff")) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) 6;
                    i2 = pos;
                } else if (color.equalsIgnoreCase("00ff00")) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) 2;
                    i2 = pos;
                } else if (color.equalsIgnoreCase("ff0000")) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) 1;
                    i2 = pos;
                } else if (color.equalsIgnoreCase("ffff00")) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) 3;
                    i2 = pos;
                } else if (color.equalsIgnoreCase("ff00ff")) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) 5;
                    i2 = pos;
                } else {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) 7;
                    i2 = pos;
                }
            }
            for (i = 0; i < lines.length; i++) {
                char[] chars = lines[i].toCharArray();
                int j = 0;
                while (j < chars.length && i2 <= 126) {
                    if (chars[j] >= ' ' && chars[j] <= '') {
                        pos = i2 + 1;
                        ttiBlock[i2] = (byte) chars[j];
                        i2 = pos;
                    }
                    j++;
                }
                if (i + 1 < lines.length) {
                    pos = i2 + 1;
                    ttiBlock[i2] = (byte) -118;
                    i2 = pos;
                }
            }
            pos = i2;
            while (pos < 128) {
                i2 = pos + 1;
                ttiBlock[pos] = (byte) -113;
                pos = i2;
            }
            System.arraycopy(ttiBlock, 0, file, (subtitleNumber * 128) + 1024, ttiBlock.length);
            ttiBlock = new byte[128];
            subtitleNumber++;
        }
        return file;
    }

    private void parseTextForSTL(Caption currentCaption, byte[] textField, int justification, TimedTextObject tto) {
        boolean italics = false;
        boolean underline = false;
        String color = "white";
        String text = "";
        int i = 0;
        while (i < textField.length) {
            if (textField[i] < (byte) 0) {
                if (textField[i] <= (byte) -113) {
                    if (i + 1 < textField.length && textField[i] == textField[i + 1]) {
                        i++;
                    }
                    switch (textField[i]) {
                        case Byte.MIN_VALUE:
                            italics = true;
                            break;
                        case (byte) -127:
                            italics = false;
                            break;
                        case (byte) -126:
                            underline = true;
                            break;
                        case (byte) -125:
                            underline = false;
                            break;
                        case (byte) -118:
                            currentCaption.content += text + "<br />";
                            text = "";
                            break;
                        case (byte) -113:
                            currentCaption.content += text;
                            text = "";
                            if (underline) {
                                color = color + "U";
                            }
                            if (italics) {
                                color = color + "I";
                            }
                            Style style = (Style) tto.styling.get(color);
                            Style style2;
                            if (justification == 1) {
                                color = color + "ListFilmNotification";
                                if (tto.styling.get(color) == null) {
                                    style2 = new Style(color, style);
                                    style2.textAlign = "bottom-left";
                                    tto.styling.put(color, style2);
                                    style = style2;
                                } else {
                                    style = (Style) tto.styling.get(color);
                                }
                            } else if (justification == 3) {
                                color = color + "R";
                                if (tto.styling.get(color) == null) {
                                    style2 = new Style(color, style);
                                    style2.textAlign = "bottom-rigth";
                                    tto.styling.put(color, style2);
                                    style = style2;
                                } else {
                                    style = (Style) tto.styling.get(color);
                                }
                            }
                            currentCaption.style = style;
                            int key = currentCaption.start.mseconds;
                            while (tto.captions.containsKey(Integer.valueOf(key))) {
                                key++;
                            }
                            tto.captions.put(Integer.valueOf(key), currentCaption);
                            i = textField.length;
                            break;
                        default:
                            break;
                    }
                }
            } else if (textField[i] < (byte) 32) {
                if (i + 1 < textField.length && textField[i] == textField[i + 1]) {
                    i++;
                }
                switch (textField[i]) {
                    case (byte) 0:
                        color = "black";
                        break;
                    case (byte) 1:
                        color = "red";
                        break;
                    case (byte) 2:
                        color = "green";
                        break;
                    case (byte) 3:
                        color = "yellow";
                        break;
                    case (byte) 4:
                        color = "blue";
                        break;
                    case (byte) 5:
                        color = "magenta";
                        break;
                    case (byte) 6:
                        color = "cyan";
                        break;
                    case (byte) 7:
                        color = "white";
                        break;
                    default:
                        break;
                }
            } else {
                text = text + new String(new byte[]{textField[i]});
            }
            i++;
        }
    }

    private void createSTLStyles(TimedTextObject tto) {
        Style style = new Style("white");
        style.color = Style.getRGBValue("name", "white");
        tto.styling.put(style.iD, style);
        Style style2 = new Style("whiteU", style);
        style2.underline = true;
        tto.styling.put(style2.iD, style2);
        style = new Style("whiteUI", style2);
        style.italic = true;
        tto.styling.put(style.iD, style);
        style2 = new Style("whiteI", style);
        style2.underline = false;
        tto.styling.put(style2.iD, style2);
        style = new Style("green");
        style.color = Style.getRGBValue("name", "green");
        tto.styling.put(style.iD, style);
        style2 = new Style("greenU", style);
        style2.underline = true;
        tto.styling.put(style2.iD, style2);
        style = new Style("greenUI", style2);
        style.italic = true;
        tto.styling.put(style.iD, style);
        style2 = new Style("greenI", style);
        style2.underline = false;
        tto.styling.put(style2.iD, style2);
        style = new Style("blue");
        style.color = Style.getRGBValue("name", "blue");
        tto.styling.put(style.iD, style);
        style2 = new Style("blueU", style);
        style2.underline = true;
        tto.styling.put(style2.iD, style2);
        style = new Style("blueUI", style2);
        style.italic = true;
        tto.styling.put(style.iD, style);
        style2 = new Style("blueI", style);
        style2.underline = false;
        tto.styling.put(style2.iD, style2);
        style = new Style("cyan");
        style.color = Style.getRGBValue("name", "cyan");
        tto.styling.put(style.iD, style);
        style2 = new Style("cyanU", style);
        style2.underline = true;
        tto.styling.put(style2.iD, style2);
        style = new Style("cyanUI", style2);
        style.italic = true;
        tto.styling.put(style.iD, style);
        style2 = new Style("cyanI", style);
        style2.underline = false;
        tto.styling.put(style2.iD, style2);
        style = new Style("red");
        style.color = Style.getRGBValue("name", "red");
        tto.styling.put(style.iD, style);
        style2 = new Style("redU", style);
        style2.underline = true;
        tto.styling.put(style2.iD, style2);
        style = new Style("redUI", style2);
        style.italic = true;
        tto.styling.put(style.iD, style);
        style2 = new Style("redI", style);
        style2.underline = false;
        tto.styling.put(style2.iD, style2);
        style = new Style("yellow");
        style.color = Style.getRGBValue("name", "yellow");
        tto.styling.put(style.iD, style);
        style2 = new Style("yellowU", style);
        style2.underline = true;
        tto.styling.put(style2.iD, style2);
        style = new Style("yellowUI", style2);
        style.italic = true;
        tto.styling.put(style.iD, style);
        style2 = new Style("yellowI", style);
        style2.underline = false;
        tto.styling.put(style2.iD, style2);
        style = new Style("magenta");
        style.color = Style.getRGBValue("name", "magenta");
        tto.styling.put(style.iD, style);
        style2 = new Style("magentaU", style);
        style2.underline = true;
        tto.styling.put(style2.iD, style2);
        style = new Style("magentaUI", style2);
        style.italic = true;
        tto.styling.put(style.iD, style);
        style2 = new Style("magentaI", style);
        style2.underline = false;
        tto.styling.put(style2.iD, style2);
        style = new Style("black");
        style.color = Style.getRGBValue("name", "black");
        tto.styling.put(style.iD, style);
        style2 = new Style("blackU", style);
        style2.underline = true;
        tto.styling.put(style2.iD, style2);
        style = new Style("blackUI", style2);
        style.italic = true;
        tto.styling.put(style.iD, style);
        style2 = new Style("blackI", style);
        style2.underline = false;
        tto.styling.put(style2.iD, style2);
    }
}
