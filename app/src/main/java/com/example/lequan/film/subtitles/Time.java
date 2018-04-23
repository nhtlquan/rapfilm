package com.example.lequan.film.subtitles;

import org.apache.commons.lang3.ClassUtils;

public class Time {
    protected int mseconds;

    protected Time(String format, String value) {
        int h;
        int m;
        if (format.equalsIgnoreCase("hh:mm:ss,ms")) {
            h = Integer.parseInt(value.substring(0, 2));
            m = Integer.parseInt(value.substring(3, 5));
            int s = Integer.parseInt(value.substring(6, 8));
            this.mseconds = (((s * 1000) + Integer.parseInt(value.substring(9, 12))) + (60000 * m)) + (3600000 * h);
        } else if (format.equalsIgnoreCase("h:mm:ss.cs")) {
            h = Integer.parseInt(value.substring(0, 1));
            m = Integer.parseInt(value.substring(2, 4));
            this.mseconds = (((Integer.parseInt(value.substring(8, 10)) * 10) + (Integer.parseInt(value.substring(5, 7)) * 1000)) + (60000 * m)) + (3600000 * h);
        } else if (format.equalsIgnoreCase("h:m:s:f/fps")) {
            String[] args = value.split("/");
            float fps = Float.parseFloat(args[1]);
            args = args[0].split(":");
            h = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            this.mseconds = ((((int) (((float) (Integer.parseInt(args[3]) * 1000)) / fps)) + (Integer.parseInt(args[2]) * 1000)) + (60000 * m)) + (3600000 * h);
        }
    }

    protected String getTime(String format) {
        StringBuilder time = new StringBuilder();
        String aux;
        if (format.equalsIgnoreCase("hh:mm:ss,ms")) {
            aux = String.valueOf(this.mseconds / 3600000);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            time.append(':');
            aux = String.valueOf((this.mseconds / 60000) % 60);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            time.append(':');
            aux = String.valueOf((this.mseconds / 1000) % 60);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            time.append(',');
            aux = String.valueOf(this.mseconds % 1000);
            if (aux.length() == 1) {
                time.append("00");
            } else if (aux.length() == 2) {
                time.append('0');
            }
            time.append(aux);
        } else if (format.equalsIgnoreCase("h:mm:ss.cs")) {
            aux = String.valueOf(this.mseconds / 3600000);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            time.append(':');
            aux = String.valueOf((this.mseconds / 60000) % 60);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            time.append(':');
            aux = String.valueOf((this.mseconds / 1000) % 60);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            time.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
            aux = String.valueOf((this.mseconds / 10) % 100);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
        } else if (format.startsWith("hhmmssff/")) {
            float  fps = Float.parseFloat(format.split("/")[1]);
            aux = String.valueOf(this.mseconds / 3600000);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            aux = String.valueOf((this.mseconds / 60000) % 60);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            aux = String.valueOf((this.mseconds / 1000) % 60);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
            aux = String.valueOf(((this.mseconds % 1000) * ((int) fps)) / 1000);
            if (aux.length() == 1) {
                time.append('0');
            }
            time.append(aux);
        } else if (format.startsWith("h:m:s:f/")) {
            float  fps = Float.parseFloat(format.split("/")[1]);
            time.append(String.valueOf(this.mseconds / 3600000));
            time.append(':');
            time.append(String.valueOf((this.mseconds / 60000) % 60));
            time.append(':');
            time.append(String.valueOf((this.mseconds / 1000) % 60));
            time.append(':');
            time.append(String.valueOf(((this.mseconds % 1000) * ((int) fps)) / 1000));
        }
        return time.toString();
    }

    public int getMilliseconds() {
        return this.mseconds;
    }
}
