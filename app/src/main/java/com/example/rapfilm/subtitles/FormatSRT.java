package com.example.rapfilm.subtitles;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class FormatSRT implements TimedTextFileFormat {
    private static final Pattern SUBRIP_TIMESTAMP = Pattern.compile("(?:(\\d+):)?(\\d+):(\\d+),(\\d+)");
    private static final Pattern SUBRIP_TIMING_LINE = Pattern.compile("(\\S*)\\s*-->\\s*(\\S*)");
    private final String TAG = getClass().getSimpleName();

    public TimedTextObject parseFile(String fileName, InputStream is, String endcode) throws IOException {
        TimedTextObject tto = new TimedTextObject();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, endcode));
        while (true) {
            String currentLine = reader.readLine();
            if (currentLine != null) {
                Caption caption = new Caption();
                StringBuilder textBuilder = new StringBuilder();
                if (currentLine.length() != 0) {
                    try {
                        int index = Integer.parseInt(currentLine.replace(StringUtils.SPACE, "").trim());
                        boolean haveEndTimecode = false;
                        currentLine = reader.readLine();
                        Matcher matcher = SUBRIP_TIMING_LINE.matcher(currentLine);
                        if (matcher.find()) {
                            caption.timeStart = parseTimecode(matcher.group(1));
                            if (!TextUtils.isEmpty(matcher.group(2))) {
                                haveEndTimecode = true;
                                caption.timeEnd = parseTimecode(matcher.group(2));
                            }
                            while (true) {
                                currentLine = reader.readLine();
                                if (TextUtils.isEmpty(currentLine)) {
                                    break;
                                }
                                textBuilder.append(currentLine.trim());
                            }
                            Spanned text = Html.fromHtml(textBuilder.toString());
                            caption.content = textBuilder.toString();
                            if (haveEndTimecode) {
                                tto.captions.put(Integer.valueOf(index), caption);
                            } else {
                                tto.captions.put(Integer.valueOf(index), caption);
                            }
                        } else {
                            Log.w(this.TAG, "Skipping invalid timing: " + currentLine);
                        }
                    } catch (NumberFormatException e) {
                        Log.w(this.TAG, "Skipping invalid index: " + currentLine.replace(StringUtils.SPACE, "").trim());
                    }
                }
            } else {
                Log.wtf("FormatSRT", tto.captions.size() + StringUtils.SPACE);
                tto.built = true;
                return tto;
            }
        }
    }

    private int parseTimecode(String s) throws NumberFormatException {
        Matcher matcher = SUBRIP_TIMESTAMP.matcher(s);
        if (matcher.matches()) {
            return (((((Integer.parseInt(matcher.group(1)) * 60) * 60) * 1000) + ((Integer.parseInt(matcher.group(2)) * 60) * 1000)) + (Integer.parseInt(matcher.group(3)) * 1000)) + Integer.parseInt(matcher.group(4));
        }
        throw new NumberFormatException("has invalid format");
    }

    public String[] toFile(TimedTextObject tto) {
        if (!tto.built) {
            return null;
        }
        int index = 0;
        ArrayList<String> file = new ArrayList(tto.captions.size() * 5);
        int captionNumber = 1;
        for (Caption current : tto.captions.values()) {
            int i = index + 1;
            int captionNumber2 = captionNumber + 1;
            file.add(index, "" + captionNumber);
            if (tto.offset != 0) {
                Time time = current.start;
                time.mseconds += tto.offset;
                time = current.end;
                time.mseconds += tto.offset;
            }
            index = i + 1;
            file.add(i, current.start.getTime("hh:mm:ss,ms") + " --> " + current.end.getTime("hh:mm:ss,ms"));
            if (tto.offset != 0) {
              Time  time = current.start;
                time.mseconds -= tto.offset;
                time = current.end;
                time.mseconds -= tto.offset;
            }
            String[] lines = cleanTextForSRT(current);
            int i2 = 0;
            while (i2 < lines.length) {
                i = index + 1;
                int i3 = i2 + 1;
                file.add(index, "" + lines[i2]);
                i2 = i3;
                index = i;
            }
            i = index + 1;
            file.add(index, "");
            captionNumber = captionNumber2;
            index = i;
        }
        String[] toReturn = new String[file.size()];
        for (int i2 = 0; i2 < toReturn.length; i2++) {
            toReturn[i2] = (String) file.get(i2);
        }
        return toReturn;
    }

    private String[] cleanTextForSRT(Caption current) {
        String[] lines = current.content.split("<br />");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\<.*?\\>", "");
        }
        return lines;
    }
}
