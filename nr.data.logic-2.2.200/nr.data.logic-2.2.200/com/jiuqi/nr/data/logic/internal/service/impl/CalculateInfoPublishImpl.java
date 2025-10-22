/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.nr.data.logic.facade.listener.ICalculateListener;
import com.jiuqi.nr.data.logic.facade.listener.obj.CalculateInfo;
import com.jiuqi.nr.data.logic.internal.service.ICalculateInfoPublish;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateInfoPublishImpl
implements ICalculateInfoPublish {
    @Autowired(required=false)
    private List<ICalculateListener> calculateListeners;

    @Override
    public void publishMessage(CalculateInfo calculateInfo) {
        if (this.calculateListeners != null) {
            for (ICalculateListener calculateListener : this.calculateListeners) {
                calculateListener.afterCalculate(calculateInfo);
            }
        }
    }
}

