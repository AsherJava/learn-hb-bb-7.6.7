/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz;

public enum DataBaseNames {
    GAUSSDB100("GAUSSDB100", "\u9ad8\u65af\u6570\u636e\u5e93", "GaussDB"),
    DM("DM", "\u8fbe\u68a6\u6570\u636e\u5e93", "DM"),
    GBASE8S("GBase8s", "gbase\u6570\u636e\u5e93", "GBase"),
    INFORMIX("Informix", "gbase\u6570\u636e\u5e93", "GBase"),
    OSCAR("OSCAR", "\u795e\u901a\u6570\u636e\u5e93", "OSCAR"),
    HANA("HANA", "HANA\u6570\u636e\u5e93", "HANA"),
    KINGBASE_8("kingbase8", "\u4eba\u5927\u91d1\u4ed3\u6570\u636e\u5e93", "kingbase"),
    KINGBASE("kingbase", "\u4eba\u5927\u91d1\u4ed3\u6570\u636e\u5e93", "kingbase"),
    DERBY("DERBY", "DERBY\u6570\u636e\u5e93", "DERBY"),
    GAUSSDB("GaussDB", "\u9ad8\u65af\u6570\u636e\u5e93", "GaussDB"),
    POLARDB("polardb", "polardb\u6570\u636e\u5e93", "polardb");

    private String code;
    private String title;
    private String name;

    private DataBaseNames(String code, String title, String name) {
        this.code = code;
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

