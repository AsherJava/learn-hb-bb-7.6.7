/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.TreeException;

public final class TreeCodeStruct {
    private int structSize;
    private int[] levelLens;
    private int[] levelStarts;
    private boolean shortCodeSupported;

    public TreeCodeStruct(String structStr) throws TreeException {
        this(structStr, false);
    }

    public TreeCodeStruct(String structStr, boolean supportShortCode) throws TreeException {
        this.shortCodeSupported = supportShortCode;
        if (structStr == null || structStr.length() == 0) {
            this.levelLens = new int[0];
            this.levelStarts = new int[0];
            return;
        }
        String[] lens = structStr.split("[,|;| |\t]+");
        this.levelLens = new int[lens.length];
        this.levelStarts = new int[lens.length];
        this.structSize = 0;
        for (int i = 0; i < lens.length; ++i) {
            try {
                this.levelLens[i] = Integer.parseInt(lens[i]);
            }
            catch (NumberFormatException e) {
                throw new TreeException("\u7f16\u7801\u7ed3\u6784\u9519\u8bef\uff1a" + structStr, e);
            }
            this.levelStarts[i] = this.structSize;
            this.structSize += this.levelLens[i];
        }
    }

    public int getStructSize() {
        return this.structSize;
    }

    public int getLevelSize() {
        return this.levelLens == null ? 0 : this.levelLens.length;
    }

    public int getLevelLength(int level) {
        return this.levelLens[level];
    }

    public int getLevelStart(int level) {
        return this.levelStarts[level];
    }

    public boolean emptyLevel(String code, int level) {
        for (int i = 0; i < this.levelLens[level]; ++i) {
            if (code.length() <= this.levelStarts[level] + i) {
                return true;
            }
            char ch = code.charAt(this.levelStarts[level] + i);
            if (ch == '0' || ch == ' ') continue;
            return false;
        }
        return true;
    }

    public String shortCode(String code) {
        int level;
        if (code == null || code.length() == 0) {
            return code;
        }
        for (level = this.levelLens.length; level > 0 && this.emptyLevel(code, level - 1); --level) {
        }
        if (level == 0) {
            return null;
        }
        if (level == this.levelLens.length) {
            return code;
        }
        return code.substring(0, this.levelStarts[level]);
    }

    public String longCode(String code) {
        if (code == null || code.length() == 0) {
            return code;
        }
        if (code.length() == this.structSize) {
            return code;
        }
        if (code.length() < this.structSize) {
            StringBuffer s = new StringBuffer(this.structSize);
            s.append(code);
            for (int i = code.length(); i < this.structSize; ++i) {
                s.append('0');
            }
            return s.toString();
        }
        return code.substring(0, this.structSize);
    }

    public String parentCode(String code) {
        if (this.structSize == 0) {
            return null;
        }
        if (code == null || code.length() == 0) {
            return null;
        }
        int level = this.levelOf(code);
        if (level <= 0) {
            return null;
        }
        if (this.shortCodeSupported) {
            return code.substring(0, this.levelStarts[level]);
        }
        StringBuffer s = new StringBuffer(this.structSize);
        s.append(code.substring(0, this.levelStarts[level]));
        for (int i = this.levelStarts[level]; i < this.structSize; ++i) {
            s.append('0');
        }
        return s.toString();
    }

    private int levelOf(String code) {
        int level;
        if (this.shortCodeSupported) {
            int level2;
            for (level2 = this.levelLens.length - 1; level2 >= 0 && code.length() <= this.levelStarts[level2]; --level2) {
            }
            while (level2 >= 0 && this.emptyLevel(code, level2)) {
                --level2;
            }
            return level2;
        }
        for (level = this.levelLens.length - 1; level >= 0 && this.emptyLevel(code, level); --level) {
        }
        return level;
    }

    public static String shortCode(String structStr, String code) {
        try {
            TreeCodeStruct tcs = new TreeCodeStruct(structStr);
            return tcs.shortCode(code);
        }
        catch (TreeException e) {
            return code;
        }
    }

    public static String longCode(String structStr, String code) {
        try {
            TreeCodeStruct tcs = new TreeCodeStruct(structStr);
            return tcs.longCode(code);
        }
        catch (TreeException e) {
            return code;
        }
    }

    public static String parentCode(String structStr, String code) {
        try {
            TreeCodeStruct tcs = new TreeCodeStruct(structStr);
            return tcs.parentCode(code);
        }
        catch (TreeException e) {
            return null;
        }
    }

    public static String parentCode(String structStr, String code, boolean supportShortCode) {
        try {
            TreeCodeStruct tcs = new TreeCodeStruct(structStr, supportShortCode);
            return tcs.parentCode(code);
        }
        catch (TreeException e) {
            return null;
        }
    }
}

