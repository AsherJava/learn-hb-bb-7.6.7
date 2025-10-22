/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import java.util.List;

public interface GatherEventHandler {
    public void beforeNodeGather(String var1, GatherCondition var2);

    public void afterNodeGather(String var1, GatherCondition var2, List<String> var3);

    default public void afterOneTableNodeGather(String targetKey, GatherCondition gatherCondition, List<String> gatherEntitys, GatherTableDefine gatherTableDefine) {
    }

    public void beforeSelectGather(String var1, GatherCondition var2, List<String> var3);

    public void afterSelectGather(String var1, GatherCondition var2, List<String> var3);

    default public void afterOneTableSelectGather(String targetKey, GatherCondition gatherCondition, List<String> sourceKeys, GatherTableDefine gatherTableDefine) {
    }
}

