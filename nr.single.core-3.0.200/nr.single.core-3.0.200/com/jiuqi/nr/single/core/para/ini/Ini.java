/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.ini;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.ini.EncodingType;
import com.jiuqi.nr.single.core.para.ini.Ident;
import com.jiuqi.nr.single.core.para.ini.Iini;
import com.jiuqi.nr.single.core.para.ini.Section;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ini
implements Iini {
    private static final Logger logger = LoggerFactory.getLogger(Ini.class);
    private HashMap<String, Section> m_Sections = new LinkedHashMap<String, Section>();
    private List<Section> s_Sections = new ArrayList<Section>();
    private static final String CHARSET_GB2312 = "GB2312";
    private static final String CHARSET_GBK = "GBK";

    @Override
    public ArrayList<Section> ReadSections() {
        ArrayList<Section> sections = new ArrayList<Section>();
        for (Section section : this.s_Sections) {
            sections.add(section);
        }
        return sections;
    }

    @Override
    public ArrayList<String> ReadSection(String sectionName) {
        if (this.m_Sections.containsKey(sectionName)) {
            Section section = this.m_Sections.get(sectionName);
            return section.ReadIdentNames();
        }
        return null;
    }

    @Override
    public String ReadString(String sectionName, String IdentName, String defaultValue) {
        if (this.m_Sections.containsKey(sectionName)) {
            Section section = this.m_Sections.get(sectionName);
            return section.ReadString(IdentName, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public void WriteString(String section, String ident, String value) {
        if (this.m_Sections.containsKey(section)) {
            Section aSection = this.m_Sections.get(section);
            aSection.WriteString(ident, value);
        } else {
            Section aSection = new Section();
            aSection.setName(section);
            aSection.WriteString(ident, value);
            this.m_Sections.put(aSection.getName(), aSection);
            this.s_Sections.add(aSection);
        }
    }

    public boolean SectionExists(String section) {
        return this.m_Sections.containsKey(section);
    }

    private String ReadFileContent(String fileName) throws StreamException, IOException {
        File file = new File(FilenameUtils.normalize(fileName));
        if (file.exists()) {
            MemStream stream = new MemStream();
            stream.loadFromFile(fileName);
            stream.seek(0L, 0);
            stream.setUseEncode(true);
            String charSet = EncodingType.GetType(fileName);
            if (CHARSET_GB2312.equalsIgnoreCase(charSet)) {
                charSet = CHARSET_GBK;
            }
            stream.setCharset(charSet);
            return stream.readString((int)stream.getSize());
        }
        return "";
    }

    private void WriteFileContent(String fileName, String content) throws StreamException, IOException {
        File file = new File(FilenameUtils.normalize(fileName));
        String charset = CHARSET_GBK;
        if (file.exists() && CHARSET_GB2312.equalsIgnoreCase(charset = EncodingType.GetType(fileName))) {
            charset = CHARSET_GBK;
        }
        MemStream stream = new MemStream();
        stream.seek(0L, 0);
        stream.setUseEncode(true);
        stream.setCharset(charset);
        stream.writeString(content);
        stream.saveToFile(fileName);
    }

    @Override
    public final void loadIniFile(String iniFile) throws StreamException, IOException {
        String iniText = this.ReadFileContent(iniFile);
        if (StringUtils.isNotEmpty((String)iniText)) {
            this.perseIniContext(iniText);
        }
    }

    @Override
    public final void loadIni(String ini) {
        if (new File(FilenameUtils.normalize(ini)).exists()) {
            try {
                ini = this.ReadFileContent(ini);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.perseIniContext(ini);
    }

    @Override
    public void loadIniFromBytes(byte[] datas, String fileName) throws StreamException, IOException {
        String charSet = EncodingType.GetType(datas);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(datas);
        MemStream stream = new MemStream();
        stream.seek(0L, 0);
        stream.setUseEncode(true);
        stream.copyFrom((InputStream)byteStream, (long)byteStream.available());
        stream.setCharset(charSet);
        stream.seek(0L, 0);
        String iniContext = stream.readString((int)stream.getSize());
        this.perseIniContext(iniContext);
    }

    private void perseIniContext(String iniContext) {
        if (StringUtils.isNotEmpty((String)iniContext)) {
            int i = 0;
            while (i < iniContext.length()) {
                char c = iniContext.charAt(i);
                if (c == '[') {
                    Section section = new Section();
                    i = section.ReadIni(iniContext, i);
                    if ("".equals(section.getName()) || this.m_Sections.containsKey(section.getName())) continue;
                    this.m_Sections.put(section.getName(), section);
                    this.s_Sections.add(section);
                    continue;
                }
                ++i;
            }
        }
    }

    private String getIniContext() {
        String iniContext = "";
        for (Section section : this.s_Sections) {
            iniContext = iniContext + "[" + section.getName() + ']' + '\r' + '\n';
            for (int i = 0; i < section.getIdentCount(); ++i) {
                Ident idet = section.getIdent(i);
                iniContext = iniContext + idet.getName() + "=" + idet.getValue() + '\r' + '\n';
            }
        }
        return iniContext;
    }

    @Override
    public void saveIni(String fileName) {
        String iniContext = this.getIniContext();
        try {
            this.WriteFileContent(fileName, iniContext);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public byte[] saveIniToBytes(String fileName) {
        String iniContext = this.getIniContext();
        byte[] data = new byte[]{};
        try {
            MemStream stream = new MemStream();
            stream.seek(0L, 0);
            stream.setUseEncode(true);
            String charset = CHARSET_GBK;
            if (StringUtils.isNotEmpty((String)fileName)) {
                try {
                    charset = EncodingType.GetType(fileName);
                    if (CHARSET_GB2312.equalsIgnoreCase(charset)) {
                        charset = CHARSET_GBK;
                    }
                }
                catch (Exception e1) {
                    charset = CHARSET_GBK;
                }
            }
            stream.setCharset(charset);
            stream.writeString(iniContext);
            data = stream.getBytes();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return data;
    }

    @Override
    public List<String> iniLoadStrs(String section) {
        ArrayList<String> list = new ArrayList<String>();
        String ident = "Count";
        String countValue = this.ReadString(section, ident, "");
        if (StringUtils.isNotEmpty((String)countValue)) {
            int count = Integer.parseInt(countValue);
            for (int i = 0; i < count; ++i) {
                list.add(this.ReadString(section, "Item_" + String.valueOf(i), ""));
            }
        }
        return list;
    }
}

