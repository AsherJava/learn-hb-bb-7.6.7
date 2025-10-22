/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.util;

public class Ident {
    private String name;
    private String value;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private int readIdentValue(String ini, int curPos) {
        int i;
        String identValue = "";
        for (i = curPos; i < ini.length(); ++i) {
            char c = ini.charAt(i);
            if (c == '\n') {
                ++i;
                break;
            }
            identValue = identValue + c;
        }
        if ((identValue = identValue.trim()).endsWith(String.valueOf('\r'))) {
            identValue = identValue.substring(0, identValue.length() - 1);
        }
        this.value = identValue.trim();
        return i;
    }

    private int readIdentName(String ini, int CurPos) {
        char c;
        int i;
        String identName = "";
        boolean hasEnd = false;
        for (i = CurPos; i < ini.length() && (c = ini.charAt(i)) != ']'; ++i) {
            if (c == '=') {
                ++i;
                break;
            }
            if (c == '\n') {
                hasEnd = true;
                ++i;
                break;
            }
            identName = identName + c;
        }
        this.name = identName;
        if (!hasEnd) {
            i = this.readIdentValue(ini, i);
        }
        return i;
    }

    public int readIni(String ini, int CurPos) {
        int i = this.readIdentName(ini, CurPos);
        return i;
    }
}

