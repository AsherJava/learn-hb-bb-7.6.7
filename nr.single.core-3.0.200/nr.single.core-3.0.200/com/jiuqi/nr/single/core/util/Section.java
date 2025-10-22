/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.util.Ident;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Section {
    private String name;
    private Map<String, Ident> m_Idents = new LinkedHashMap<String, Ident>();

    public String getName() {
        return this.name;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public int getIdentCount() {
        return this.m_Idents.size();
    }

    public List<String> readIdentNames() {
        ArrayList<String> identNames = new ArrayList<String>();
        for (Object obj : this.m_Idents.values().toArray()) {
            Ident ident = (Ident)obj;
            identNames.add(ident.getName());
        }
        return identNames;
    }

    public Map<String, Ident> getIdents() {
        return this.m_Idents;
    }

    public String readString(String ident, String defaultValue) {
        if (this.m_Idents.containsKey(ident)) {
            return this.m_Idents.get(ident).getValue();
        }
        return defaultValue;
    }

    private int readSectionName(String ini, int CurPos) {
        int i;
        String sectionName = "";
        boolean sectionEnd = false;
        for (i = CurPos; i < ini.length(); ++i) {
            char c = ini.charAt(i);
            if (c == ']') {
                sectionEnd = true;
            }
            if (!sectionEnd) {
                sectionName = sectionName + c;
            } else {
                this.name = sectionName;
            }
            if (c != '\n') continue;
            ++i;
            break;
        }
        return i;
    }

    public int readIni(String ini, int CurPos) {
        int i;
        boolean otherSection = false;
        for (i = CurPos; i < ini.length(); ++i) {
            char c = ini.charAt(i);
            if (c == '[') {
                if (otherSection) break;
                i = this.readSectionName(ini, i + 1);
                otherSection = true;
            }
            if (i >= ini.length()) continue;
            Ident ident = new Ident();
            i = ident.readIni(ini, i) - 1;
            if (StringUtils.isEmpty((String)ident.getName()) || this.m_Idents.containsKey(ident.getName())) continue;
            this.m_Idents.put(ident.getName(), ident);
        }
        return i;
    }
}

