/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.inspector.service;

import com.jiuqi.gc.inspector.common.TaskTypeEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import java.util.List;
import java.util.Map;

public interface InpectorService {
    public InspectResultVO execute(TaskTypeEnum var1, String var2, Map<String, Object> var3);

    public InspectResultVO executeInspect(String var1, Map<String, Object> var2);

    public InspectResultVO executeFix(String var1, Map<String, Object> var2);

    public List<InspectResultVO> executeInspect(Map<String, Map<String, Object>> var1);

    public List<InspectResultVO> executeFix(Map<String, Map<String, Object>> var1);
}

