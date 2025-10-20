/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.inspector.intf;

import com.jiuqi.gc.inspector.domain.InspectResultVO;
import java.util.Map;

public interface InspectBaseItem {
    public String getGroup();

    public String getName();

    public String getTitle();

    default public int getOrdinal() {
        return -1;
    }

    public InspectResultVO executeInspect(Map<String, Object> var1);

    default public InspectResultVO executeFix(Map<String, Object> params) {
        return InspectResultVO.unSupportResult();
    }
}

