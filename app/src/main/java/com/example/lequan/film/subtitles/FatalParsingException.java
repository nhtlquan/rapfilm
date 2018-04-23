package com.example.lequan.film.subtitles;

public class FatalParsingException extends Exception {
    private static final long serialVersionUID = 6798827566637277804L;
    private String parsingErrror;

    public FatalParsingException(String parsingError) {
        super(parsingError);
        this.parsingErrror = parsingError;
    }

    public String getLocalizedMessage() {
        return this.parsingErrror;
    }
}
