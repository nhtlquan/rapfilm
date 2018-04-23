package com.example.lequan.film.subtitles;

import java.io.IOException;
import java.io.InputStream;

public interface TimedTextFileFormat {
    TimedTextObject parseFile(String str, InputStream inputStream, String str2) throws IOException, FatalParsingException;

    Object toFile(TimedTextObject timedTextObject);
}
