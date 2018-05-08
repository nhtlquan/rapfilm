package com.example.rapfilm.subtitles;

import android.support.v4.view.MotionEventCompat;
import com.parse.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.prober.HebrewProber;
import org.mozilla.universalchardet.prober.contextanalysis.SJISContextAnalysis;

public class FormatSCC implements TimedTextFileFormat {
    public TimedTextObject parseFile(String fileName, InputStream is) throws IOException, FatalParsingException {
        TimedTextObject tto = new TimedTextObject();
        Caption newCaption = null;
        String textBuffer = "";
        boolean isChannel1 = false;
        boolean isBuffered = true;
        boolean underlined = false;
        boolean italics = false;
        String color = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        tto.fileName = fileName;
        int lineCounter = 0 + 1;
        if (br.readLine().trim().equalsIgnoreCase("Scenarist_SCC V1.0")) {
            int key;
            createSCCStyles(tto);
            tto.warnings += "Only data from CC channel 1 will be extracted.\n\n";
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                line = line.trim();
                lineCounter++;
                if (!line.isEmpty()) {
                    String[] data = line.split("\t");
                    Time currentTime = new Time("h:m:s:f/fps", data[0] + "/29.97");
                    data = data[1].split(StringUtils.SPACE);
                    int j = 0;
                    Caption newCaption2 = newCaption;
                    while (j < data.length) {
                        try {
                            int word = Integer.parseInt(data[j], 16) & 32639;
                            if ((word & 24576) != 0) {
                                if (isChannel1) {
                                    byte c1 = (byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & word) >>> 8);
                                    byte c2 = (byte) (word & 255);
                                    if (isBuffered) {
                                        textBuffer = (textBuffer + decodeChar(c1)) + decodeChar(c2);
                                    } else {
                                        newCaption2.content += decodeChar(c1);
                                        newCaption2.content += decodeChar(c2);
                                    }
                                    newCaption = newCaption2;
                                }
                                newCaption = newCaption2;
                            } else if (word == 0) {
                                currentTime.mseconds = (int) (((double) currentTime.mseconds) + 33.366700033366705d);
                                newCaption = newCaption2;
                            } else {
                                if (j + 1 < data.length && data[j].equals(data[j + 1])) {
                                    j++;
                                }
                                if ((word & 2048) != 0) {
                                    isChannel1 = false;
                                    newCaption = newCaption2;
                                } else if ((word & 5744) != 5152) {
                                    if (isChannel1) {
                                        if ((word & 4160) == 4160) {
                                            color = "white";
                                            underlined = false;
                                            italics = false;
                                            if (isBuffered && !textBuffer.isEmpty()) {
                                                textBuffer = textBuffer + "<br />";
                                            }
                                            if (!(isBuffered || newCaption2.content.isEmpty())) {
                                                newCaption2.content += "<br />";
                                            }
                                            if ((word & 1) == 1) {
                                                underlined = true;
                                            }
                                            if ((word & 16) != 16) {
                                                switch ((short) ((word & 14) >> 1)) {
                                                    case (short) 0:
                                                        color = "white";
                                                        newCaption = newCaption2;
                                                        break;
                                                    case (short) 1:
                                                        color = "green";
                                                        newCaption = newCaption2;
                                                        break;
                                                    case (short) 2:
                                                        color = "blue";
                                                        newCaption = newCaption2;
                                                        break;
                                                    case (short) 3:
                                                        color = "cyan";
                                                        newCaption = newCaption2;
                                                        break;
                                                    case (short) 4:
                                                        color = "red";
                                                        newCaption = newCaption2;
                                                        break;
                                                    case (short) 5:
                                                        color = "yellow";
                                                        newCaption = newCaption2;
                                                        break;
                                                    case (short) 6:
                                                        color = "magenta";
                                                        newCaption = newCaption2;
                                                        break;
                                                    case (short) 7:
                                                        italics = true;
                                                        newCaption = newCaption2;
                                                        break;
                                                    default:
                                                        newCaption = newCaption2;
                                                        break;
                                                }
                                            }
                                            color = "white";
                                            newCaption = newCaption2;
                                        } else if ((word & 6000) == 4384) {
                                            if ((word & 1) == 1) {
                                                underlined = true;
                                            } else {
                                                underlined = false;
                                            }
                                            switch ((short) ((word & 14) >> 1)) {
                                                case (short) 0:
                                                    color = "white";
                                                    italics = false;
                                                    newCaption = newCaption2;
                                                    break;
                                                case (short) 1:
                                                    color = "green";
                                                    italics = false;
                                                    newCaption = newCaption2;
                                                    break;
                                                case (short) 2:
                                                    color = "blue";
                                                    italics = false;
                                                    newCaption = newCaption2;
                                                    break;
                                                case (short) 3:
                                                    color = "cyan";
                                                    italics = false;
                                                    newCaption = newCaption2;
                                                    break;
                                                case (short) 4:
                                                    color = "red";
                                                    italics = false;
                                                    newCaption = newCaption2;
                                                    break;
                                                case (short) 5:
                                                    color = "yellow";
                                                    italics = false;
                                                    newCaption = newCaption2;
                                                    break;
                                                case (short) 6:
                                                    color = "magenta";
                                                    italics = false;
                                                    newCaption = newCaption2;
                                                    break;
                                                case (short) 7:
                                                    italics = true;
                                                    newCaption = newCaption2;
                                                    break;
                                                default:
                                                    newCaption = newCaption2;
                                                    break;
                                            }
                                        } else if ((word & 6012) == 5920) {
                                            newCaption = newCaption2;
                                        } else if ((word & 6000) == 4400) {
                                            word &= 15;
                                            if (isBuffered) {
                                                textBuffer = textBuffer + decodeSpecialChar(word);
                                                newCaption = newCaption2;
                                            } else {
                                                newCaption2.content += decodeSpecialChar(word);
                                                newCaption = newCaption2;
                                            }
                                        } else if ((word & 5728) == 4640) {
                                            word &= 287;
                                            if (isBuffered) {
                                                decodeXtChar(textBuffer, word);
                                                newCaption = newCaption2;
                                            } else {
                                                decodeXtChar(newCaption2.content, word);
                                                newCaption = newCaption2;
                                            }
                                        }
                                    }
                                    newCaption = newCaption2;
                                } else if ((word & 256) == 0) {
                                    isChannel1 = true;
                                    switch (word & 15) {
                                        case 0:
                                            isBuffered = true;
                                            textBuffer = "";
                                            newCaption = newCaption2;
                                            break;
                                        case 5:
                                        case 6:
                                        case 7:
                                            textBuffer = "";
                                            if (newCaption2 != null) {
                                                newCaption2.end = currentTime;
                                                String style = "" + color;
                                                if (underlined) {
                                                    style = style + "U";
                                                }
                                                if (italics) {
                                                    style = style + "I";
                                                }
                                                newCaption2.style = (Style) tto.styling.get(style);
                                                tto.captions.put(Integer.valueOf(newCaption2.start.mseconds), newCaption2);
                                            }
                                            newCaption = new Caption();
                                            newCaption.start = currentTime;
                                            isBuffered = false;
                                            break;
                                        case 9:
                                            isBuffered = false;
                                            newCaption = new Caption();
                                            newCaption.start = currentTime;
                                            break;
                                        case 12:
                                            if (newCaption2 != null) {
                                                newCaption2.end = currentTime;
                                                if (newCaption2.start != null) {
                                                    for (key = newCaption2.start.mseconds; tto.captions.containsKey(Integer.valueOf(key)); key++) {
                                                    }
                                                    tto.captions.put(Integer.valueOf(newCaption2.start.mseconds), newCaption2);
                                                    newCaption = new Caption();
                                                    break;
                                                }
                                            }
                                            break;
                                        case 14:
                                            textBuffer = "";
                                            newCaption = newCaption2;
                                            break;
                                        case 15:
                                            newCaption = new Caption();
                                            try {
                                                newCaption.start = currentTime;
                                                newCaption.content += textBuffer;
                                                break;
                                            } catch (NullPointerException e) {
                                                break;
                                            }
                                        default:
                                            newCaption = newCaption2;
                                            break;
                                    }
                                    newCaption = newCaption2;
                                } else {
                                    isChannel1 = false;
                                    newCaption = newCaption2;
                                }
                            }
                            j++;
                            newCaption2 = newCaption;
                        } catch (NullPointerException e2) {
                            newCaption = newCaption2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            newCaption = newCaption2;
                        }
                    }
                    newCaption = newCaption2;
                }
            }
            newCaption.end = new Time("h:m:s:f/fps", "99:59:59:29/29.97");
            if (newCaption.start != null) {
                for (key = newCaption.start.mseconds; tto.captions.containsKey(Integer.valueOf(key)); key++) {
                }
                tto.captions.put(Integer.valueOf(newCaption.start.mseconds), newCaption);
            }
            tto.cleanUnusedStyles();
            is.close();
            tto.built = true;
            return tto;
        }
        try {
            tto.warnings += "unexpected end of file at line " + lineCounter + ", maybe last caption is not complete.\n\n";
            is.close();
            tto.built = true;
            return tto;
        } catch (Throwable th3) {
            is.close();
            throw th3;
        }
    }

    public TimedTextObject parseFile(String fileName, InputStream is, String endcode) throws IOException, FatalParsingException {
        return null;
    }

    public String[] toFile(TimedTextObject tto) {
        if (!tto.built) {
            return null;
        }
        ArrayList<String> file = new ArrayList((tto.captions.size() * 2) + 20);
        int index = 0 + 1;
        file.add(0, "Scenarist_SCC V1.0\n");
        Caption newC = new Caption();
        newC.content = "";
        newC.end = new Time("h:mm:ss.cs", "0:00:00.00");
        int i = index;
        for (Caption newC2 : tto.captions.values()) {
            String line = "";
            Caption oldC = newC2;
            Time time;
            if (oldC.end.mseconds > newC2.start.mseconds) {
                newC2.content += "<br />" + oldC.content;
                time = newC2.start;
                time.mseconds = (int) (((double) time.mseconds) - 33.366700033366705d);
                line = line + newC2.start.getTime("hh:mm:ss:ff/29.97") + "\t942c 942c ";
                time = newC2.start;
                time.mseconds = (int) (((double) time.mseconds) + 33.366700033366705d);
                line = line + "94ae 94ae 9420 9420 ";
            } else if (oldC.end.mseconds < newC2.start.mseconds) {
                line = line + oldC.end.getTime("hh:mm:ss:ff/29.97") + "\t942c 942c\n\n";
                time = newC2.start;
                time.mseconds = (int) (((double) time.mseconds) - 33.366700033366705d);
                line = line + newC2.start.getTime("hh:mm:ss:ff/29.97") + "\t94ae 94ae 9420 9420 ";
                time = newC2.start;
                time.mseconds = (int) (((double) time.mseconds) + 33.366700033366705d);
            } else {
                time = newC2.start;
                time.mseconds = (int) (((double) time.mseconds) - 33.366700033366705d);
                line = line + newC2.start.getTime("hh:mm:ss:ff/29.97") + "\t942c 942c 94ae 94ae 9420 9420 ";
                time = newC2.start;
                time.mseconds = (int) (((double) time.mseconds) + 33.366700033366705d);
            }
            index = i + 1;
            file.add(i, (line + codeText(newC2)) + "8080 8080 942f 942f\n");
            i = index;
        }
        index = i + 1;
        file.add(i, "");
        String[] toReturn = new String[file.size()];
        for (int i2 = 0; i2 < toReturn.length; i2++) {
            toReturn[i2] = (String) file.get(i2);
        }
        return toReturn;
    }

    private String codeText(Caption newC) {
        String toReturn = "";
        String[] lines = newC.content.split("<br />");
        if (lines[0].length() > 32) {
            lines[0] = lines[0].substring(0, 32);
        }
        int tab = (32 - lines[0].length()) / 2;
        toReturn = toReturn + "1340 1340 ";
        if (tab % 4 != 0) {
            toReturn = toReturn + codeChar(lines[0].toCharArray());
        } else {
            toReturn = toReturn + codeChar(lines[0].toCharArray());
        }
        if (lines.length <= 1) {
            return toReturn;
        }
        int i = 0 + 1;
        if (lines[i].length() > 32) {
            lines[i] = lines[i].substring(0, 32);
        }
        tab = (32 - lines[i].length()) / 2;
        toReturn = toReturn + "13e0 13e0 ";
        if (tab % 4 != 0) {
            toReturn = toReturn + codeChar(lines[i].toCharArray());
        } else {
            toReturn = toReturn + codeChar(lines[i].toCharArray());
        }
        if (lines.length <= 2) {
            return toReturn;
        }
        i++;
        if (lines[i].length() > 32) {
            lines[i] = lines[i].substring(0, 32);
        }
        tab = (32 - lines[i].length()) / 2;
        toReturn = toReturn + "9440 9440 ";
        if (tab % 4 != 0) {
            toReturn = toReturn + codeChar(lines[i].toCharArray());
        } else {
            toReturn = toReturn + codeChar(lines[i].toCharArray());
        }
        if (lines.length <= 3) {
            return toReturn;
        }
        i++;
        if (lines[i].length() > 32) {
            lines[i] = lines[i].substring(0, 32);
        }
        tab = (32 - lines[i].length()) / 2;
        toReturn = toReturn + "94e0 94e0 ";
        if (tab % 4 != 0) {
        }
        return toReturn + codeChar(lines[i].toCharArray());
    }

    private String codeChar(char[] chars) {
        String toReturn = "";
        int i = 0;
        while (i < chars.length) {
            switch (chars[i]) {
                case ' ':
                    toReturn = toReturn + "20";
                    break;
                case '!':
                    toReturn = toReturn + "a1";
                    break;
                case '\"':
                    toReturn = toReturn + "a2";
                    break;
                case '#':
                    toReturn = toReturn + "23";
                    break;
                case '$':
                    toReturn = toReturn + "a4";
                    break;
                case '%':
                    toReturn = toReturn + "25";
                    break;
                case '&':
                    toReturn = toReturn + "26";
                    break;
                case '\'':
                    toReturn = toReturn + "a7";
                    break;
                case '(':
                    toReturn = toReturn + "a8";
                    break;
                case ')':
                    toReturn = toReturn + "29";
                    break;
                case '+':
                    toReturn = toReturn + "ab";
                    break;
                case ',':
                    toReturn = toReturn + "2c";
                    break;
                case '-':
                    toReturn = toReturn + "ad";
                    break;
                case '.':
                    toReturn = toReturn + "ae";
                    break;
                case '/':
                    toReturn = toReturn + "2f";
                    break;
                case '0':
                    toReturn = toReturn + "b0";
                    break;
                case '1':
                    toReturn = toReturn + "31";
                    break;
                case '2':
                    toReturn = toReturn + "32";
                    break;
                case '3':
                    toReturn = toReturn + "b3";
                    break;
                case '4':
                    toReturn = toReturn + "34";
                    break;
                case '5':
                    toReturn = toReturn + "b5";
                    break;
                case '6':
                    toReturn = toReturn + "b6";
                    break;
                case '7':
                    toReturn = toReturn + "37";
                    break;
                case '8':
                    toReturn = toReturn + "38";
                    break;
                case '9':
                    toReturn = toReturn + "b9";
                    break;
                case ':':
                    toReturn = toReturn + "ba";
                    break;
                case ';':
                    toReturn = toReturn + "3b";
                    break;
                case '<':
                    toReturn = toReturn + "bc";
                    break;
                case '=':
                    toReturn = toReturn + "3d";
                    break;
                case '>':
                    toReturn = toReturn + "3e";
                    break;
                case '?':
                    toReturn = toReturn + "bf";
                    break;
                case '@':
                    toReturn = toReturn + "40";
                    break;
                case 'A':
                    toReturn = toReturn + "c1";
                    break;
                case 'B':
                    toReturn = toReturn + "c2";
                    break;
                case 'C':
                    toReturn = toReturn + "43";
                    break;
                case 'D':
                    toReturn = toReturn + "c4";
                    break;
                case 'E':
                    toReturn = toReturn + "45";
                    break;
                case 'F':
                    toReturn = toReturn + "46";
                    break;
                case 'G':
                    toReturn = toReturn + "c7";
                    break;
                case 'H':
                    toReturn = toReturn + "c8";
                    break;
                case 'I':
                    toReturn = toReturn + "49";
                    break;
                case 'J':
                    toReturn = toReturn + "4a";
                    break;
                case 'K':
                    toReturn = toReturn + "cb";
                    break;
                case 'L':
                    toReturn = toReturn + "4c";
                    break;
                case 'M':
                    toReturn = toReturn + "cd";
                    break;
                case 'N':
                    toReturn = toReturn + "ce";
                    break;
                case 'O':
                    toReturn = toReturn + "4f";
                    break;
                case 'P':
                    toReturn = toReturn + "d0";
                    break;
                case 'Q':
                    toReturn = toReturn + "51";
                    break;
                case 'R':
                    toReturn = toReturn + "52";
                    break;
                case 'S':
                    toReturn = toReturn + "d3";
                    break;
                case 'T':
                    toReturn = toReturn + "54";
                    break;
                case 'U':
                    toReturn = toReturn + "d5";
                    break;
                case 'V':
                    toReturn = toReturn + "d6";
                    break;
                case 'W':
                    toReturn = toReturn + "57";
                    break;
                case 'X':
                    toReturn = toReturn + "58";
                    break;
                case 'Y':
                    toReturn = toReturn + "d9";
                    break;
                case 'Z':
                    toReturn = toReturn + "da";
                    break;
                case '[':
                    toReturn = toReturn + "5b";
                    break;
                case ']':
                    toReturn = toReturn + "5d";
                    break;
                case 'a':
                    toReturn = toReturn + "61";
                    break;
                case 'b':
                    toReturn = toReturn + "62";
                    break;
                case 'c':
                    toReturn = toReturn + "e3";
                    break;
                case 'd':
                    toReturn = toReturn + "64";
                    break;
                case 'e':
                    toReturn = toReturn + "e5";
                    break;
                case 'f':
                    toReturn = toReturn + "e6";
                    break;
                case 'g':
                    toReturn = toReturn + "67";
                    break;
                case 'h':
                    toReturn = toReturn + "68";
                    break;
                case 'i':
                    toReturn = toReturn + "e9";
                    break;
                case 'j':
                    toReturn = toReturn + "ea";
                    break;
                case 'k':
                    toReturn = toReturn + "6b";
                    break;
                case 'l':
                    toReturn = toReturn + "ec";
                    break;
                case 'm':
                    toReturn = toReturn + "6d";
                    break;
                case 'n':
                    toReturn = toReturn + "6e";
                    break;
                case 'o':
                    toReturn = toReturn + "ef";
                    break;
                case 'p':
                    toReturn = toReturn + "70";
                    break;
                case 'q':
                    toReturn = toReturn + "f1";
                    break;
                case 'r':
                    toReturn = toReturn + "f2";
                    break;
                case ParseException.PUSH_MISCONFIGURED /*115*/:
                    toReturn = toReturn + "73";
                    break;
                case ParseException.OBJECT_TOO_LARGE /*116*/:
                    toReturn = toReturn + "f4";
                    break;
                case 'u':
                    toReturn = toReturn + "75";
                    break;
                case 'v':
                    toReturn = toReturn + "76";
                    break;
                case ParseException.OPERATION_FORBIDDEN /*119*/:
                    toReturn = toReturn + "f7";
                    break;
                case ParseException.CACHE_MISS /*120*/:
                    toReturn = toReturn + "f8";
                    break;
                case ParseException.INVALID_NESTED_KEY /*121*/:
                    toReturn = toReturn + "79";
                    break;
                case 'z':
                    toReturn = toReturn + "7a";
                    break;
                case ParseException.TIMEOUT /*124*/:
                    toReturn = toReturn + "7f";
                    break;
                case ParseException.INVALID_SESSION_TOKEN /*209*/:
                    toReturn = toReturn + "fd";
                    break;
                case 'á':
                    toReturn = toReturn + "2a";
                    break;
                case 'ç':
                    toReturn = toReturn + "fb";
                    break;
                case 'é':
                    toReturn = toReturn + "dc";
                    break;
                case HebrewProber.FINAL_MEM /*237*/:
                    toReturn = toReturn + "5e";
                    break;
                case SJISContextAnalysis.HIRAGANA_LOWBYTE_END /*241*/:
                    toReturn = toReturn + "fe";
                    break;
                case 'ó':
                    toReturn = toReturn + "df";
                    break;
                case '÷':
                    toReturn = toReturn + "7c";
                    break;
                case 'ú':
                    toReturn = toReturn + "e0";
                    break;
                default:
                    toReturn = toReturn + "7f";
                    break;
            }
            if (i % 2 == 1) {
                toReturn = toReturn + StringUtils.SPACE;
            }
            i++;
        }
        if (i % 2 == 1) {
            return toReturn + "80 ";
        }
        return toReturn;
    }

    private String decodeChar(byte c) {
        switch (c) {
            case (byte) 0:
                return "";
            case (byte) 42:
                return "�";
            case (byte) 92:
                return "�";
            case (byte) 94:
                return "�";
            case (byte) 95:
                return "�";
            case (byte) 96:
                return "�";
            case ParseException.INVALID_ACL /*123*/:
                return "�";
            case ParseException.TIMEOUT /*124*/:
                return "�";
            case ParseException.INVALID_EMAIL_ADDRESS /*125*/:
                return "�";
            case (byte) 126:
                return "�";
            case 127:
                return "|";
            default:
                return "" + ((char) c);
        }
    }

    private String decodeSpecialChar(int word) {
        switch (word) {
            case 0:
                return "�";
            case 1:
                return "�";
            case 2:
                return "�";
            case 3:
                return "�";
            case 4:
                return "�";
            case 5:
                return "�";
            case 6:
                return "�";
            case 7:
                return "♪";
            case 8:
                return "�";
            case 9:
                return " ";
            case 10:
                return "�";
            case 11:
                return "�";
            case 12:
                return "�";
            case 13:
                return "�";
            case 14:
                return "�";
            case 15:
                return "�";
            default:
                return "";
        }
    }

    private void decodeXtChar(String textBuffer, int word) {
    }

    private void createSCCStyles(TimedTextObject tto) {
        Style style = new Style("white");
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
    }
}
