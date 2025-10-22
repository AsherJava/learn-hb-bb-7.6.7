/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

public enum DocumentSize {
    TABLOID(15840L, 24480L, 3L, "Tabloid"),
    LEDGER(15840L, 24480L, 4L, "Ledger"),
    EXECUTIVE(10440L, 15120L, 7L, "Executive"),
    A3(16838L, 23811L, 8L, "A3"),
    A4(11906L, 16838L, 9L, "A4"),
    LETTER(12240L, 15840L, 119L, "Letter"),
    LEGAL(12240L, 20160L, 120L, "Legal"),
    SCREEN(7455L, 9360L, 122L, "Legal"),
    ANSIC(24480L, 31680L, 123L, "ANSIC"),
    ARCHA(12960L, 17280L, 127L, "ARCHA"),
    ARCHB(17280L, 25920L, 128L, "ARCHB"),
    ISOB5(9978L, 14179L, 140L, "ISOB5"),
    ISOB4(14179L, 20018L, 141L, "ISOB4"),
    JISB4(14576L, 20636L, 145L, "JISB4"),
    JISB3(20636L, 29197L, 146L, "JISB3"),
    K16(10433L, 14742L, 1116L, "K16"),
    K32(7371L, 10433L, 1132L, "K32"),
    K32H(7938L, 11510L, 1232L, "K32H");

    private long width;
    private long height;
    private long code;
    private String name;

    private DocumentSize(long width, long height, long code, String name) {
        this.width = width;
        this.height = height;
        this.code = code;
        this.name = name;
    }

    public long getWidth() {
        return this.width;
    }

    public long getHeight() {
        return this.height;
    }

    public long getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static DocumentSize getDocumentSize(String name) {
        for (DocumentSize size : DocumentSize.values()) {
            if (!size.getName().equals(name)) continue;
            return size;
        }
        return null;
    }
}

