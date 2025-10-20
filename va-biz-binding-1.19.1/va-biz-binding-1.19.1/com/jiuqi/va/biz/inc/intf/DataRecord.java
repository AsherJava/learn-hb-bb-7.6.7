/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.inc.intf;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface DataRecord {
    public Map<String, Set<UUID>> getInsert();

    public Map<String, Map<UUID, Map<String, Object>>> getUpdate();

    public Map<String, List<UUID>> getDelete();
}

