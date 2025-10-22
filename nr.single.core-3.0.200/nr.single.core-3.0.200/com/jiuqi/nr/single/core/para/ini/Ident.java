/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.ini;

public class Ident {
    private String privateName;
    private String privateValue;

    public final String getName() {
        return this.privateName;
    }

    public final void setName(String value) {
        this.privateName = value;
    }

    public final String getValue() {
        return this.privateValue;
    }

    public final void setValue(String value) {
        this.privateValue = value;
    }

    private int ReadIdentValue(String ini, int CurPos) {
        int i;
        String identValue = "";
        for (i = CurPos; i < ini.length(); ++i) {
            char c = ini.charAt(i);
            if (c == '\n') {
                ++i;
                break;
            }
            identValue = identValue + c;
        }
        String lastChar = identValue;
        if (identValue.length() >= 2) {
            lastChar = identValue.substring(identValue.length() - 1, identValue.length());
        }
        if (lastChar.equals("\r")) {
            identValue = identValue.substring(0, identValue.length() - 1);
        }
        this.setValue(identValue);
        return i;
    }

    private int ReadIdentName(String ini, int CurPos) {
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
        this.setName(identName);
        if (!hasEnd) {
            i = this.ReadIdentValue(ini, i);
        }
        return i;
    }

    public final int ReadIni(String ini, int CurPos) {
        int i = this.ReadIdentName(ini, CurPos);
        return i;
    }
}

