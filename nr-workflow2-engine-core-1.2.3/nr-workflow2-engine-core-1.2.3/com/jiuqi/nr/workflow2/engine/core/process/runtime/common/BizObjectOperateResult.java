/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BizObjectOperateResult<T>
implements IBizObjectOperateResult<T> {
    private final List<IBusinessObject> businessObjectList = new ArrayList<IBusinessObject>();
    private final Map<IBusinessObject, IOperateResult<T>> operateResult = new HashMap<IBusinessObject, IOperateResult<T>>();

    public void appendResult(IBusinessObject businessObject, IOperateResult<T> result) {
        this.operateResult.put(businessObject, result);
        this.businessObjectList.add(businessObject);
    }

    @Override
    public int size() {
        return this.businessObjectList.size();
    }

    @Override
    public IOperateResult<T> getResult(IBusinessObject businessObject) {
        return this.operateResult.get(businessObject);
    }

    @Override
    public Iterable<IBusinessObject> getInstanceKeys() {
        return this.businessObjectList;
    }
}

