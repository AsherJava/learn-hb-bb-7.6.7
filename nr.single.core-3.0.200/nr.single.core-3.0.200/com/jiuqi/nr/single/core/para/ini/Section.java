/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.ini;

import com.jiuqi.nr.single.core.para.ini.Ident;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Section {
    private HashMap<String, Ident> m_Idents = new LinkedHashMap<String, Ident>();
    private List<Ident> s_Idents = new ArrayList<Ident>();
    private String privateName;

    public final String getName() {
        return this.privateName;
    }

    public final void setName(String value) {
        this.privateName = value;
    }

    public final Ident getIdent(int index) {
        return this.s_Idents.get(index);
    }

    public final int getIdentCount() {
        return this.m_Idents.size();
    }

    public final ArrayList<String> ReadIdentNames() {
        ArrayList<String> identNames = new ArrayList<String>();
        for (Ident ident : this.s_Idents) {
            identNames.add(ident.getName());
        }
        return identNames;
    }

    public final String ReadString(String ident, String defaultValue) {
        if (this.m_Idents.containsKey(ident)) {
            return this.m_Idents.get(ident).getValue();
        }
        return defaultValue;
    }

    public final void WriteString(String ident, String value) {
        if (this.m_Idents.containsKey(ident)) {
            this.m_Idents.get(ident).setValue(value);
        } else {
            Ident idet = new Ident();
            idet.setName(ident);
            idet.setValue(value);
            this.m_Idents.put(ident, idet);
            this.s_Idents.add(idet);
        }
    }

    private int ReadSectionName(String ini, int CurPos) {
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
                this.setName(sectionName);
            }
            if (c != '\n') continue;
            ++i;
            break;
        }
        return i;
    }

    public final int ReadIni(String ini, int CurPos) {
        int i = CurPos;
        boolean otherSection = false;
        while (i < ini.length()) {
            char c = ini.charAt(i);
            if (c == '[') {
                if (otherSection) break;
                i = this.ReadSectionName(ini, i + 1);
                otherSection = true;
            }
            if (c == '\r' || c == '\n') {
                ++i;
                continue;
            }
            if (i < ini.length()) {
                Ident ident = new Ident();
                i = ident.ReadIni(ini, i) - 1;
                if (!"".equals(ident.getName()) && !this.m_Idents.containsKey(ident.getName())) {
                    this.m_Idents.put(ident.getName(), ident);
                    this.s_Idents.add(ident);
                }
            }
            ++i;
        }
        return i;
    }
}

