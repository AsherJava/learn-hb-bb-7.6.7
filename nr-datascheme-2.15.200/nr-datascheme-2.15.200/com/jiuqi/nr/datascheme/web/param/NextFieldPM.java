/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.datascheme.web.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NextFieldPM {
    private int type;
    private String code;
    private String tableKey;
    private String schemeKey;
    @JsonIgnore
    private DataFieldKind dataFieldKind;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public DataFieldKind getDataFieldKind() {
        if (this.type != 0) {
            return DataFieldKind.valueOf((int)this.type);
        }
        return null;
    }

    public static String getNextCode(String code) {
        Matcher matcher = Pattern.compile("\\d+$").matcher(code);
        if (matcher.find()) {
            String num = matcher.group();
            return code.substring(0, code.lastIndexOf(num)) + NextFieldPM.getNextNum(num);
        }
        return code + 1;
    }

    private static String getNextNum(String num) {
        char[] chars = new char[num.length() + 1];
        int len = chars.length;
        num.getChars(0, num.length(), chars, len - num.length());
        StringBuilder builder = new StringBuilder();
        int carry = 0;
        for (int i = len - 1; i >= 0; --i) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                char c = chars[i];
                if (carry == 0) {
                    if (i == len - 1) {
                        chars[i] = (char)((c + '\u0001' - 48) % 10 + 48);
                        carry = (c + '\u0001' - 48) / 10;
                    }
                } else {
                    chars[i] = (char)((c + carry - 48) % 10 + 48);
                    carry = (c + carry - 48) / 10;
                }
            }
            if (chars[i] == '\u0000' && carry == 1) {
                chars[i] = 49;
                carry = 0;
            }
            builder.append(chars[i] == '\u0000' ? "" : Character.valueOf(chars[i]));
        }
        return builder.reverse().toString();
    }
}

