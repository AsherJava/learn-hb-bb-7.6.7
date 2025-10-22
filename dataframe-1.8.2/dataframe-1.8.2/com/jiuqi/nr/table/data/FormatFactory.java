/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.data;

import com.jiuqi.nr.table.data.ObjectItemFormat;
import java.text.Format;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class FormatFactory {
    private DateTimeFormatter dateTimeFormatter;
    private DateTimeFormatter dateFormatter;
    private ObjectItemFormat objectItemFormatter;
    private Map<String, Format> formatterMap;

    public Format choose(Object col, Object row) {
        return null;
    }

    public void register(String name, Format format) {
        this.formatterMap.put(name, format);
    }

    public Format get(String name) {
        return this.formatterMap.get(name);
    }
}

