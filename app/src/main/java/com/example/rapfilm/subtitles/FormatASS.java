package com.example.rapfilm.subtitles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

public class FormatASS implements TimedTextFileFormat {
    public TimedTextObject parseFile(InputStream is) throws IOException {
        return parseFile(null, is);
    }

    public TimedTextObject parseFile(String fileName, InputStream is) throws IOException {
        TimedTextObject tto = new TimedTextObject();
        tto.fileName = fileName;
        Caption caption = new Caption();
        float timer = 100.0f;
        boolean isASS = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        int lineCounter = 0 + 1;
        while (line != null) {
            line = line.trim();
            if (!line.startsWith("[")) {
                line = br.readLine();
                lineCounter++;
            } else if (line.equalsIgnoreCase("[Script info]")) {
                lineCounter++;
                line = br.readLine().trim();
                while (!line.startsWith("[")) {
                    if (line.startsWith("Title:")) {
                        tto.title = line.split(":")[1].trim();
                    } else if (line.startsWith("Original Script:")) {
                        tto.author = line.split(":")[1].trim();
                    } else {
                        try {
                            if (line.startsWith("Script Type:")) {
                                if (line.split(":")[1].trim().equalsIgnoreCase("v4.00+")) {
                                    isASS = true;
                                } else if (!line.split(":")[1].trim().equalsIgnoreCase("v4.00")) {
                                    tto.warnings += "Script version is older than 4.00, it may produce parsing errors.";
                                }
                            } else if (line.startsWith("Timer:")) {
                                timer = Float.parseFloat(line.split(":")[1].trim().replace(',', ClassUtils.PACKAGE_SEPARATOR_CHAR));
                            }
                        } catch (NullPointerException e) {
                            tto.warnings += "unexpected end of file, maybe last caption is not complete.\n\n";
                        } finally {
                            is.close();
                        }
                    }
                    lineCounter++;
                    line = br.readLine().trim();
                }
                continue;
            } else if (line.equalsIgnoreCase("[v4 Styles]") || line.equalsIgnoreCase("[v4 Styles+]") || line.equalsIgnoreCase("[v4+ Styles]")) {
                if (line.contains("+") && !isASS) {
                    isASS = true;
                    tto.warnings += "ScriptType should be set to v4:00+ in the [Script Info] section.\n\n";
                }
                lineCounter++;
                line = br.readLine().trim();
                if (!line.startsWith("Format:")) {
                    tto.warnings += "Format: (format definition) expected at line " + line + " for the styles section\n\n";
                    while (!line.startsWith("Format:")) {
                        lineCounter++;
                        line = br.readLine().trim();
                    }
                }
                String[] styleFormat = line.split(":")[1].trim().split(",");
                lineCounter++;
                line = br.readLine().trim();
                while (!line.startsWith("[")) {
                    if (line.startsWith("Style:")) {
                        Style style = parseStyleForASS(line.split(":")[1].trim().split(","), styleFormat, lineCounter, isASS, tto.warnings);
                        tto.styling.put(style.iD, style);
                    }
                    lineCounter++;
                    line = br.readLine().trim();
                }
            } else if (line.trim().equalsIgnoreCase("[Events]")) {
                lineCounter++;
                line = br.readLine().trim();
                tto.warnings += "Only dialogue events are considered, all other events are ignored.\n\n";
                if (!line.startsWith("Format:")) {
                    tto.warnings += "Format: (format definition) expected at line " + line + " for the events section\n\n";
                    while (!line.startsWith("Format:")) {
                        lineCounter++;
                        line = br.readLine().trim();
                    }
                }
                String[] dialogueFormat = line.split(":")[1].trim().split(",");
                lineCounter++;
                line = br.readLine().trim();
                while (!line.startsWith("[")) {
                    if (line.startsWith("Dialogue:")) {
                        caption = parseDialogueForASS(line.split(":", 2)[1].trim().split(",", 10), dialogueFormat, timer, tto);
                        int key = caption.start.mseconds;
                        while (tto.captions.containsKey(Integer.valueOf(key))) {
                            key++;
                        }
                        tto.captions.put(Integer.valueOf(key), caption);
                    }
                    lineCounter++;
                    line = br.readLine().trim();
                }
            } else if (line.trim().equalsIgnoreCase("[Fonts]") || line.trim().equalsIgnoreCase("[Graphics]")) {
                tto.warnings += "The section " + line.trim() + " is not supported for conversion, all information there will be lost.\n\n";
                line = br.readLine().trim();
            } else {
                tto.warnings += "Unrecognized section: " + line.trim() + " all information there is ignored.";
                line = br.readLine().trim();
            }
        }
        tto.cleanUnusedStyles();
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
        ArrayList<String> file = new ArrayList((tto.styling.size() + 30) + tto.captions.size());
        int index = 0 + 1;
        file.add(0, "[Script Info]");
        String title = "Title: ";
        if (tto.title == null || tto.title.isEmpty()) {
            title = title + tto.fileName;
        } else {
            title = title + tto.title;
        }
        int index2 = index + 1;
        file.add(index, title);
        String author = "Original Script: ";
        if (tto.author == null || tto.author.isEmpty()) {
            author = author + "Unknown";
        } else {
            author = author + tto.author;
        }
        index = index2 + 1;
        file.add(index2, author);
        if (tto.copyrigth == null || tto.copyrigth.isEmpty()) {
            index2 = index;
        } else {
            index2 = index + 1;
            file.add(index, "; " + tto.copyrigth);
        }
        if (!(tto.description == null || tto.description.isEmpty())) {
            index = index2 + 1;
            file.add(index2, "; " + tto.description);
            index2 = index;
        }
        index = index2 + 1;
        file.add(index2, "; Converted by the Online Subtitle Converter developed by J. David Requejo");
        if (tto.useASSInsteadOfSSA) {
            index2 = index + 1;
            file.add(index, "Script Type: V4.00+");
        } else {
            index2 = index + 1;
            file.add(index, "Script Type: V4.00");
        }
        index = index2 + 1;
        file.add(index2, "Collisions: Normal");
        index2 = index + 1;
        file.add(index, "Timer: 100,0000");
        if (tto.useASSInsteadOfSSA) {
            index = index2 + 1;
            file.add(index2, "WrapStyle: 1");
            index2 = index;
        }
        index = index2 + 1;
        file.add(index2, "");
        if (tto.useASSInsteadOfSSA) {
            index2 = index + 1;
            file.add(index, "[V4+ Styles]");
        } else {
            index2 = index + 1;
            file.add(index, "[V4 Styles]");
        }
        if (tto.useASSInsteadOfSSA) {
            index = index2 + 1;
            file.add(index2, "Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding");
            index2 = index;
        } else {
            index = index2 + 1;
            file.add(index2, "Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, TertiaryColour, BackColour, Bold, Italic, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, AlphaLevel, Encoding");
            index2 = index;
        }
        for (Style current : tto.styling.values()) {
            String styleLine = ((((((("Style: " + current.iD + ",") + current.font + ",") + current.fontSize + ",") + getColorsForASS(tto.useASSInsteadOfSSA, current)) + getOptionsForASS(tto.useASSInsteadOfSSA, current)) + "1,2,2,") + getAlignForASS(tto.useASSInsteadOfSSA, current.textAlign)) + ",0,0,0,";
            if (!tto.useASSInsteadOfSSA) {
                styleLine = styleLine + "0,";
            }
            index = index2 + 1;
            file.add(index2, styleLine + "0");
            index2 = index;
        }
        index = index2 + 1;
        file.add(index2, "");
        index2 = index + 1;
        file.add(index, "[Events]");
        if (tto.useASSInsteadOfSSA) {
            index = index2 + 1;
            file.add(index2, "Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
            index2 = index;
        } else {
            index = index2 + 1;
            file.add(index2, "Format: Marked, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
            index2 = index;
        }
        for (Caption current2 : tto.captions.values()) {
            String line = "Dialogue: 0,";
            if (tto.offset != 0) {
                Time time = current2.start;
                time.mseconds += tto.offset;
                time = current2.end;
                time.mseconds += tto.offset;
            }
            line = (line + current2.start.getTime("h:mm:ss.cs") + ",") + current2.end.getTime("h:mm:ss.cs") + ",";
            if (tto.offset != 0) {
                Time  time = current2.start;
                time.mseconds -= tto.offset;
                time = current2.end;
                time.mseconds -= tto.offset;
            }
            if (current2.style != null) {
                line = line + current2.style.iD;
            } else {
                line = line + "Default";
            }
            index = index2 + 1;
            file.add(index2, (line + ",,0000,0000,0000,,") + current2.content.replaceAll("<br />", "�N").replaceAll("\\<.*?\\>", "").replace('�', '\\'));
            index2 = index;
        }
        index = index2 + 1;
        file.add(index2, "");
        String[] toReturn = new String[file.size()];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = (String) file.get(i);
        }
        return toReturn;
    }

    private Style parseStyleForASS(String[] line, String[] styleFormat, int index, boolean isASS, String warnings) {
        Style newStyle = new Style(Style.defaultID());
        if (line.length != styleFormat.length) {
            warnings = warnings + "incorrectly formated line at " + index + "\n\n";
        } else {
            for (int i = 0; i < styleFormat.length; i++) {
                if (styleFormat[i].trim().equalsIgnoreCase("Name")) {
                    newStyle.iD = line[i].trim();
                } else if (styleFormat[i].trim().equalsIgnoreCase("Fontname")) {
                    newStyle.font = line[i].trim();
                } else if (styleFormat[i].trim().equalsIgnoreCase("Fontsize")) {
                    newStyle.fontSize = line[i].trim();
                } else if (styleFormat[i].trim().equalsIgnoreCase("PrimaryColour")) {
                    String color = line[i].trim();
                    if (isASS) {
                        if (color.startsWith("&H")) {
                            newStyle.color = Style.getRGBValue("&HAABBGGRR", color);
                        } else {
                            newStyle.color = Style.getRGBValue("decimalCodedAABBGGRR", color);
                        }
                    } else if (color.startsWith("&H")) {
                        newStyle.color = Style.getRGBValue("&HBBGGRR", color);
                    } else {
                        newStyle.color = Style.getRGBValue("decimalCodedBBGGRR", color);
                    }
                } else if (styleFormat[i].trim().equalsIgnoreCase("BackColour")) {
                    String  color = line[i].trim();
                    if (isASS) {
                        if (color.startsWith("&H")) {
                            newStyle.backgroundColor = Style.getRGBValue("&HAABBGGRR", color);
                        } else {
                            newStyle.backgroundColor = Style.getRGBValue("decimalCodedAABBGGRR", color);
                        }
                    } else if (color.startsWith("&H")) {
                        newStyle.backgroundColor = Style.getRGBValue("&HBBGGRR", color);
                    } else {
                        newStyle.backgroundColor = Style.getRGBValue("decimalCodedBBGGRR", color);
                    }
                } else if (styleFormat[i].trim().equalsIgnoreCase("Bold")) {
                    newStyle.bold = Boolean.parseBoolean(line[i].trim());
                } else if (styleFormat[i].trim().equalsIgnoreCase("Italic")) {
                    newStyle.italic = Boolean.parseBoolean(line[i].trim());
                } else if (styleFormat[i].trim().equalsIgnoreCase("Underline")) {
                    newStyle.underline = Boolean.parseBoolean(line[i].trim());
                } else if (styleFormat[i].trim().equalsIgnoreCase("Alignment")) {
                    int placement = Integer.parseInt(line[i].trim());
                    if (!isASS) {
                        switch (placement) {
                            case 1:
                                newStyle.textAlign = "mid-left";
                                break;
                            case 2:
                                newStyle.textAlign = "mid-center";
                                break;
                            case 3:
                                newStyle.textAlign = "mid-right";
                                break;
                            case 5:
                                newStyle.textAlign = "top-left";
                                break;
                            case 6:
                                newStyle.textAlign = "top-center";
                                break;
                            case 7:
                                newStyle.textAlign = "top-right";
                                break;
                            case 9:
                                newStyle.textAlign = "bottom-left";
                                break;
                            case 10:
                                newStyle.textAlign = "bottom-center";
                                break;
                            case 11:
                                newStyle.textAlign = "bottom-right";
                                break;
                            default:
                                warnings = warnings + "undefined alignment for style at line " + index + "\n\n";
                                break;
                        }
                    }
                    switch (placement) {
                        case 1:
                            newStyle.textAlign = "bottom-left";
                            break;
                        case 2:
                            newStyle.textAlign = "bottom-center";
                            break;
                        case 3:
                            newStyle.textAlign = "bottom-right";
                            break;
                        case 4:
                            newStyle.textAlign = "mid-left";
                            break;
                        case 5:
                            newStyle.textAlign = "mid-center";
                            break;
                        case 6:
                            newStyle.textAlign = "mid-right";
                            break;
                        case 7:
                            newStyle.textAlign = "top-left";
                            break;
                        case 8:
                            newStyle.textAlign = "top-center";
                            break;
                        case 9:
                            newStyle.textAlign = "top-right";
                            break;
                        default:
                            warnings = warnings + "undefined alignment for style at line " + index + "\n\n";
                            break;
                    }
                }
            }
        }
        return newStyle;
    }

    private Caption parseDialogueForASS(String[] line, String[] dialogueFormat, float timer, TimedTextObject tto) {
        Caption newCaption = new Caption();
        newCaption.content = line[9].replaceAll("\\{.*?\\}", "").replace(StringUtils.LF, "<br />").replace("\\N", "<br />");
        for (int i = 0; i < dialogueFormat.length; i++) {
            if (dialogueFormat[i].trim().equalsIgnoreCase("Style")) {
                Style s = (Style) tto.styling.get(line[i].trim());
                if (s != null) {
                    newCaption.style = s;
                } else {
                    tto.warnings += "undefined style: " + line[i].trim() + "\n\n";
                }
            } else if (dialogueFormat[i].trim().equalsIgnoreCase("Start")) {
                newCaption.start = new Time("h:mm:ss.cs", line[i].trim());
            } else if (dialogueFormat[i].trim().equalsIgnoreCase("End")) {
                newCaption.end = new Time("h:mm:ss.cs", line[i].trim());
            }
        }
        if (timer != 100.0f) {
            Time time = newCaption.start;
            time.mseconds = (int) (((float) time.mseconds) / (timer / 100.0f));
            time = newCaption.end;
            time.mseconds = (int) (((float) time.mseconds) / (timer / 100.0f));
        }
        return newCaption;
    }

    private String getColorsForASS(boolean useASSInsteadOfSSA, Style style) {
        if (useASSInsteadOfSSA) {
            return Integer.parseInt("00" + style.color.substring(4, 6) + style.color.substring(2, 4) + style.color.substring(0, 2), 16) + ",16777215,0," + Long.parseLong("80" + style.backgroundColor.substring(4, 6) + style.backgroundColor.substring(2, 4) + style.backgroundColor.substring(0, 2), 16) + ",";
        }
        return Long.parseLong(style.color.substring(4, 6) + style.color.substring(2, 4) + style.color.substring(0, 2), 16) + ",16777215,0," + Long.parseLong(style.backgroundColor.substring(4, 6) + style.backgroundColor.substring(2, 4) + style.backgroundColor.substring(0, 2), 16) + ",";
    }

    private String getOptionsForASS(boolean useASSInsteadOfSSA, Style style) {
        String options;
        if (style.bold) {
            options = "-1,";
        } else {
            options = "0,";
        }
        if (style.italic) {
            options = options + "-1,";
        } else {
            options = options + "0,";
        }
        if (!useASSInsteadOfSSA) {
            return options;
        }
        if (style.underline) {
            options = options + "-1,";
        } else {
            options = options + "0,";
        }
        return options + "0,100,100,0,0,";
    }

    private int getAlignForASS(boolean useASSInsteadOfSSA, String align) {
        int placement;
        if (useASSInsteadOfSSA) {
            placement = 2;
            if ("bottom-left".equals(align)) {
                placement = 1;
            } else if ("bottom-center".equals(align)) {
                placement = 2;
            } else if ("bottom-right".equals(align)) {
                placement = 3;
            } else if ("mid-left".equals(align)) {
                placement = 4;
            } else if ("mid-center".equals(align)) {
                placement = 5;
            } else if ("mid-right".equals(align)) {
                placement = 6;
            } else if ("top-left".equals(align)) {
                placement = 7;
            } else if ("top-center".equals(align)) {
                placement = 8;
            } else if ("top-right".equals(align)) {
                placement = 9;
            }
            return placement;
        }
        placement = 10;
        if ("bottom-left".equals(align)) {
            placement = 9;
        } else if ("bottom-center".equals(align)) {
            placement = 10;
        } else if ("bottom-right".equals(align)) {
            placement = 11;
        } else if ("mid-left".equals(align)) {
            placement = 1;
        } else if ("mid-center".equals(align)) {
            placement = 2;
        } else if ("mid-right".equals(align)) {
            placement = 3;
        } else if ("top-left".equals(align)) {
            placement = 5;
        } else if ("top-center".equals(align)) {
            placement = 6;
        } else if ("top-right".equals(align)) {
            placement = 7;
        }
        return placement;
    }
}
