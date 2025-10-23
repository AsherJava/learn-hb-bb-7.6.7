/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult
 */
package com.jiuqi.nr.workflow2.form.reject.ext.engine;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.ext.engine.FormRejectProcessStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RejectFormStateQueryResult
implements IBizObjectOperateResult<IProcessStatus> {
    private final List<IBusinessObject> instanceKeys = new ArrayList<IBusinessObject>();
    private final Map<IBusinessObject, IOperateResult<IProcessStatus>> statusMap = new HashMap<IBusinessObject, IOperateResult<IProcessStatus>>();

    public RejectFormStateQueryResult(List<IRejectFormRecordEntity> formRecordEntities) {
        for (IRejectFormRecordEntity formRecordEntity : formRecordEntities) {
            this.instanceKeys.add((IBusinessObject)formRecordEntity.getFormObject());
            this.statusMap.put((IBusinessObject)formRecordEntity.getFormObject(), (IOperateResult<IProcessStatus>)OperateResult.newSuccessResult((Object)new FormRejectProcessStatus(formRecordEntity)));
        }
    }

    public int size() {
        return this.instanceKeys.size();
    }

    public Iterable<IBusinessObject> getInstanceKeys() {
        return this.instanceKeys;
    }

    public IOperateResult<IProcessStatus> getResult(IBusinessObject key) {
        return this.statusMap.get(key);
    }
}

