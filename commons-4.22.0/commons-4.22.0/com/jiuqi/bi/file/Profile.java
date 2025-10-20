/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Profile {
    private Map<String, Properties> sections = new HashMap<String, Properties>();

    public int sectionSize() {
        return this.sections.size();
    }

    public String[] allSections() {
        Set<String> keys = this.sections.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    public boolean contains(String secName) {
        return this.sections.containsKey(secName);
    }

    public boolean contains(String secName, String propName) {
        Properties sec = this.getSections(secName);
        return sec == null ? false : sec.containsKey(propName);
    }

    public Properties getSections(String secName) {
        return this.sections.get(secName);
    }

    public String getProperty(String secName, String propName) {
        Properties sec = this.getSections(secName);
        return sec == null ? null : sec.getProperty(propName);
    }

    public void setProperty(String secName, String propName, String value) {
        Properties sec = this.sections.get(secName);
        if (sec == null) {
            sec = new Properties();
            this.sections.put(secName, sec);
        }
        sec.put(propName, value == null ? "" : value);
    }

    public void load(InputStream inStream) throws IOException {
        this.load(inStream, null);
    }

    public synchronized void load(InputStream inStream, String encoding) throws IOException {
        this.sections.clear();
        InputStreamReader sr = encoding == null ? new InputStreamReader(inStream) : new InputStreamReader(inStream, encoding);
        BufferedReader reader = new BufferedReader(sr);
        String curSection = null;
        String line = reader.readLine();
        while (line != null) {
            if ((line = line.trim()).length() > 0 && !line.startsWith("#")) {
                if (line.startsWith("[") && line.endsWith("]")) {
                    curSection = line.substring(1, line.length() - 1);
                } else {
                    int p = line.indexOf(61);
                    if (p == -1) {
                        this.setProperty(curSection, line, null);
                    } else {
                        this.setProperty(curSection, line.substring(0, p), line.substring(p + 1));
                    }
                }
            }
            line = reader.readLine();
        }
    }

    public void loadFromFile(String filename) throws IOException {
        try (FileInputStream in = new FileInputStream(filename);){
            this.load(in);
        }
    }
}

