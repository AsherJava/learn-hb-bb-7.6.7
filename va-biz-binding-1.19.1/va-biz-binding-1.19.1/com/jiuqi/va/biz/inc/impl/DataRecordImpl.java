/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.inc.impl;

import com.jiuqi.va.biz.inc.intf.DataRecord;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataRecordImpl
implements DataRecord {
    Map<String, Set<UUID>> insert = new ConcurrentHashMap<String, Set<UUID>>();
    Map<String, Map<UUID, Map<String, Object>>> update = new ConcurrentHashMap<String, Map<UUID, Map<String, Object>>>();
    Map<String, List<UUID>> delete = new ConcurrentHashMap<String, List<UUID>>();

    @Override
    public Map<String, Set<UUID>> getInsert() {
        return this.insert;
    }

    @Override
    public Map<String, Map<UUID, Map<String, Object>>> getUpdate() {
        return this.update;
    }

    @Override
    public Map<String, List<UUID>> getDelete() {
        return this.delete;
    }
}

