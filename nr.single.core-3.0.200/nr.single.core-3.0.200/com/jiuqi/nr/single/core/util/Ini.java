/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.Ident;
import com.jiuqi.nr.single.core.util.Section;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

public class Ini {
    private Map<String, Section> m_Sections = new LinkedHashMap<String, Section>();

    public List<Section> readSections() {
        ArrayList<Section> sections = new ArrayList<Section>();
        Iterator<Section> iterator = this.m_Sections.values().iterator();
        while (iterator.hasNext()) {
            Section obj;
            Section section = obj = iterator.next();
            sections.add(section);
        }
        return sections;
    }

    public List<String> readSection(String sectionName) {
        if (this.m_Sections.containsKey(sectionName)) {
            Section section = this.m_Sections.get(sectionName);
            return section.readIdentNames();
        }
        return null;
    }

    public String readString(String sectionName, String IdentName, String defaultValue) {
        if (this.m_Sections.containsKey(sectionName)) {
            Section section = this.m_Sections.get(sectionName);
            return section.readString(IdentName, defaultValue);
        }
        return defaultValue;
    }

    public void writeString(String section, String ident, String value) {
        Section sec = null;
        if (this.m_Sections.containsKey(section)) {
            sec = this.m_Sections.get(section);
        } else {
            sec = new Section();
            sec.SetName(section);
            this.m_Sections.put(section, sec);
        }
        Ident idt = null;
        if (sec.getIdents().containsKey(ident)) {
            idt = sec.getIdents().get(ident);
        } else {
            idt = new Ident();
            idt.setName(ident);
            sec.getIdents().put(ident, idt);
        }
        idt.setValue(value);
    }

    public boolean sectionExists(String section) {
        return this.m_Sections.containsKey(section);
    }

    private String readFileContent(String fileName) throws IOException, SingleFileException {
        String result = null;
        String fileName2 = FilenameUtils.normalize(fileName);
        SingleSecurityUtils.validatePathManipulation(fileName2);
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(fileName2);
             InputStreamReader reader = new InputStreamReader((InputStream)fis, "GBK");
             BufferedReader bReader = new BufferedReader(reader);){
            String s = "";
            while ((s = bReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        }
        result = sb.toString();
        return result;
    }

    public void loadIniFile(String iniFile) throws IOException, SingleFileException {
        String iniText = this.readFileContent(iniFile);
        if (StringUtils.isNotEmpty((String)iniText)) {
            this.loadIniContent(iniText);
        }
    }

    public void loadIniContent(String context) throws IOException {
        String ini = context;
        int i = 0;
        while (i < ini.length()) {
            char c = ini.charAt(i);
            if (c == '[') {
                Section section = new Section();
                i = section.readIni(ini, i);
                if (StringUtils.isEmpty((String)section.getName()) || this.m_Sections.containsKey(section.getName())) continue;
                this.m_Sections.put(section.getName(), section);
                continue;
            }
            ++i;
        }
    }

    public void saveToFileUTF8(String fileName) throws IOException, SingleFileException {
        String iniFileName = FilenameUtils.normalize(fileName);
        SingleSecurityUtils.validatePathManipulation(iniFileName);
        File file = new File(iniFileName);
        try (FileWriter writer = new FileWriter(file);
             BufferedWriter bWriter = new BufferedWriter(writer);){
            for (Section sec : this.m_Sections.values()) {
                bWriter.write("[" + sec.getName() + "]" + '\r' + '\n');
                for (Ident idt : sec.getIdents().values()) {
                    bWriter.write(idt.getName() + "=" + idt.getValue() + '\r' + '\n');
                }
            }
            bWriter.flush();
        }
    }

    public void saveToFile(String fileName) throws IOException, SingleFileException {
        String iniFileName = FilenameUtils.normalize(fileName);
        SingleSecurityUtils.validatePathManipulation(iniFileName);
        try (FileOutputStream writerStream = new FileOutputStream(iniFileName, false);
             OutputStreamWriter outStream = new OutputStreamWriter((OutputStream)writerStream, "GBK");
             BufferedWriter bWriter = new BufferedWriter(outStream);){
            for (Section sec : this.m_Sections.values()) {
                String secName = "[" + sec.getName() + "]" + '\r' + '\n';
                bWriter.write(secName);
                for (Ident idt : sec.getIdents().values()) {
                    String idtName = idt.getName() + "=" + idt.getValue() + '\r' + '\n';
                    bWriter.write(idtName);
                }
            }
            bWriter.flush();
        }
    }

    public void saveToFile(String fileName, String sectionLists) throws IOException, SingleFileException {
        String iniFileName = FilenameUtils.normalize(fileName);
        SingleSecurityUtils.validatePathManipulation(iniFileName);
        File file = new File(iniFileName);
        try (FileWriter writer = new FileWriter(file);
             BufferedWriter bWriter = new BufferedWriter(writer);){
            for (Section sec : this.m_Sections.values()) {
                if (sectionLists.indexOf(sec.getName()) < 0) continue;
                bWriter.write("[" + sec.getName() + "]" + '\r' + '\n');
                for (Ident idt : sec.getIdents().values()) {
                    bWriter.write(idt.getName() + "=" + idt.getValue() + '\r' + '\n');
                }
            }
            bWriter.flush();
        }
    }
}

