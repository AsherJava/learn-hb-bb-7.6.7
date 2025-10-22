/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.nr.data.logic.facade.param.input.BatchDelCheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.internal.service.ICKDChangeNotifier;
import com.jiuqi.nr.data.logic.spi.ICKDChangeListener;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CKDChangeNotifierImpl
implements ICKDChangeNotifier {
    @Autowired(required=false)
    private List<ICKDChangeListener> changeListeners;

    @Override
    public void beforeDelete(BatchDelCheckDesParam delCheckDesParam) {
        if (CollectionUtils.isEmpty(this.changeListeners)) {
            return;
        }
        for (ICKDChangeListener changeListener : this.changeListeners) {
            changeListener.beforeDelete(delCheckDesParam);
        }
    }

    @Override
    public void afterDelete(List<CheckDesObj> checkDesObjs) {
        if (CollectionUtils.isEmpty(this.changeListeners)) {
            return;
        }
        for (ICKDChangeListener changeListener : this.changeListeners) {
            changeListener.afterDelete(checkDesObjs);
        }
    }

    @Override
    public void afterInsert(List<CheckDesObj> checkDesObjs) {
        if (CollectionUtils.isEmpty(this.changeListeners)) {
            return;
        }
        for (ICKDChangeListener changeListener : this.changeListeners) {
            changeListener.afterInsert(checkDesObjs);
        }
    }

    @Override
    public void afterUpdateOrInsert(List<CheckDesObj> checkDesObjs) {
        if (CollectionUtils.isEmpty(this.changeListeners)) {
            return;
        }
        for (ICKDChangeListener changeListener : this.changeListeners) {
            changeListener.afterUpdateOrInsert(checkDesObjs);
        }
    }
}

