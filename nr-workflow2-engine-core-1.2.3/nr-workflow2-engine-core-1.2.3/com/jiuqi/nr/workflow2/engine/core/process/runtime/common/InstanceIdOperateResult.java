/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IInstanceIdOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceIdOperateResult<T>
implements IInstanceIdOperateResult<T> {
    private final List<String> instanceIds = new ArrayList<String>();
    private final Map<String, IOperateResult<T>> operateResult = new HashMap<String, IOperateResult<T>>();

    public void appendResult(String instanceId, IOperateResult<T> result) {
        this.operateResult.put(instanceId, result);
        this.instanceIds.add(instanceId);
    }

    @Override
    public int size() {
        return this.instanceIds.size();
    }

    @Override
    public IOperateResult<T> getResult(String instanceId) {
        return this.operateResult.get(instanceId);
    }

    @Override
    public Iterable<String> getInstanceKeys() {
        return this.instanceIds;
    }
}

