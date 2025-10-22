/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 */
package com.jiuqi.nr.data.gather.refactor.provider;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.gather.refactor.provider.NodeCheckOptionProvider;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultNodeCheckOptionProvider
implements NodeCheckOptionProvider {
    @Autowired
    private ITaskOptionController iTaskOptionController;

    @Override
    public BigDecimal getNodeCheckTolerance(String taskKey) {
        BigDecimal errorRange = BigDecimal.ZERO;
        String value = this.iTaskOptionController.getValue(taskKey, "NODE_CHECK_TOLERANCE");
        if (StringUtils.isNotEmpty((String)value)) {
            errorRange = new BigDecimal(value);
        }
        return errorRange;
    }
}

