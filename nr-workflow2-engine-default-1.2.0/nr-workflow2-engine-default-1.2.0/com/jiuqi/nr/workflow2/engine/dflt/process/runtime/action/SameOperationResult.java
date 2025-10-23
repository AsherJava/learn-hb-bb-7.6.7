/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;

public class SameOperationResult
implements IBizObjectOperateResult<Void> {
    private static final IOperateResult<Void> CANCEL = OperateResult.newFailResult((ErrorCode)ErrorCode.CANCELD);
    private static final IOperateResult<Void> SUCCESS = OperateResult.newSuccessResult();
    private final IBusinessObjectCollection businessObjectCollection;
    private final IOperateResult<Void> result;

    public static SameOperationResult newSuccessResult(IBusinessObjectCollection businessObjectCollection) {
        return new SameOperationResult(businessObjectCollection, SUCCESS);
    }

    public static SameOperationResult newCancelResult(IBusinessObjectCollection businessObjectCollection) {
        return new SameOperationResult(businessObjectCollection, CANCEL);
    }

    private SameOperationResult(IBusinessObjectCollection businessObjectCollection, IOperateResult<Void> result) {
        this.businessObjectCollection = businessObjectCollection;
        this.result = result;
    }

    public int size() {
        return this.businessObjectCollection.size();
    }

    public IOperateResult<Void> getResult(IBusinessObject businessObject) {
        return this.result;
    }

    public Iterable<IBusinessObject> getInstanceKeys() {
        return this.businessObjectCollection;
    }
}

