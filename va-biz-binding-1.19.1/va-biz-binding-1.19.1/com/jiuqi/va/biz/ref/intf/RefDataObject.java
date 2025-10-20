/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.va.biz.ref.intf;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.va.biz.ref.intf.RefDataEntry;
import com.jiuqi.va.biz.ref.intf.RefDataObjectSerializer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JsonSerialize(using=RefDataObjectSerializer.class)
public interface RefDataObject
extends Map<String, Object> {
    public static final String NAME_IS_NULL = "isNull";
    public static final String NAME_NAME = "name";
    public static final String NAME_TITLE = "title";
    public static final String NAME_SHOW_TITLE = "showTitle";

    public boolean isNull();

    public String getName();

    public String getTitle();

    default public String getShowTitle() {
        return null;
    }

    @Override
    default public Set<Map.Entry<String, Object>> entrySet() {
        return this.buildEntrySet();
    }

    default public Set<Map.Entry<String, Object>> buildEntrySet() {
        HashSet<Map.Entry<String, Object>> entrySet = new HashSet<Map.Entry<String, Object>>();
        entrySet.add(new RefDataEntry(this, NAME_IS_NULL));
        if (!this.isNull()) {
            entrySet.add(new RefDataEntry(this, NAME_NAME));
            entrySet.add(new RefDataEntry(this, NAME_TITLE));
            entrySet.add(new RefDataEntry(this, NAME_SHOW_TITLE));
        }
        return entrySet;
    }
}

