/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.exception.Error
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult
 */
package com.jiuqi.nr.workflow2.service.result;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.exception.Error;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;
import java.util.Map;

public class UnitStateQueryResult
implements IBizObjectOperateResult<IProcessStatus> {
    private final IBusinessKeyCollection businessKeyCollection;
    private final Map<DimensionCombination, IProcessStatus> unitStateMap;

    public UnitStateQueryResult(IBusinessKeyCollection businessKeyCollection, Map<DimensionCombination, IProcessStatus> unitStateMap) {
        this.unitStateMap = unitStateMap;
        this.businessKeyCollection = businessKeyCollection;
    }

    public int size() {
        return this.unitStateMap.size();
    }

    public Iterable<IBusinessObject> getInstanceKeys() {
        return this.businessKeyCollection.getBusinessObjects();
    }

    public IOperateResult<IProcessStatus> getResult(IBusinessObject key) {
        IProcessStatus status = this.unitStateMap.get(key.getDimensions());
        if (status != null) {
            return OperateResult.newSuccessResult((Object)status);
        }
        return OperateResult.newFailResult((Error)Error.NOERROR);
    }
}

