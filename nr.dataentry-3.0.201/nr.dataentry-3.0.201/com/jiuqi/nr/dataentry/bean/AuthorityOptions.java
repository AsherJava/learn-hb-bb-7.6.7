/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import java.util.HashMap;

public enum AuthorityOptions {
    EXPORT("\u5bfc\u51fa", "66666666-1111-1111-1111-666666666666", 0),
    BATCHEXPORT("\u6279\u91cf\u5bfc\u51fa", "66666666-2222-2222-2222-666666666666", 1),
    PRINT("\u6253\u5370", "66666666-3333-3333-3333-666666666666", 2),
    BATCHPRINT("\u6279\u91cf\u6253\u5370", "66666666-4444-4444-4444-666666666666", 3),
    EFDC("EFDC\u53d6\u6570", "66666666-5555-5555-5555-666666666666", 4),
    ETL("ETL\u53d6\u6570\u3010\u4e0e\u4efb\u52a1\u6570\u636e\u6743\u9650\u65e0\u5173\u8054\u5173\u7cfb\u3011", "66666666-6666-6666-6666-666666666666", 5);

    private String title;
    private String id;
    private int value;
    private static final HashMap<Integer, AuthorityOptions> MAP;
    private static final HashMap<String, AuthorityOptions> TITLE_MAP;
    private static final HashMap<String, AuthorityOptions> ID_MAP;

    private AuthorityOptions(String title, String id, int value) {
        this.title = title;
        this.id = id;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public static AuthorityOptions valueOf(int value) {
        return MAP.get(value);
    }

    public static AuthorityOptions titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    public static AuthorityOptions idOf(String id) {
        return ID_MAP.get(id);
    }

    static {
        AuthorityOptions[] values = AuthorityOptions.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        ID_MAP = new HashMap(values.length);
        for (AuthorityOptions value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
            ID_MAP.put(value.id, value);
        }
    }
}

