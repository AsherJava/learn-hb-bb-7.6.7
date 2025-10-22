/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 */
package com.jiuqi.gcreport.inputdata.check.env.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.env.InputDataCheckEnvContext;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InputDataCheckEnvContextImpl
extends ProgressDataImpl<List<String>>
implements InputDataCheckEnvContext {
    private InputDataCheckCondition inputDataCheckCondition;

    public InputDataCheckEnvContextImpl() {
        this(UUIDUtils.newUUIDStr());
    }

    public InputDataCheckEnvContextImpl(String sn) {
        super(sn, new CopyOnWriteArrayList(), "InputDataCheckEnvContextImpl");
    }

    @Override
    public InputDataCheckCondition getInputDataCheckCondition() {
        return this.inputDataCheckCondition;
    }

    public void setInputDataCheckCondition(InputDataCheckCondition inputDataCheckCondition) {
        this.inputDataCheckCondition = inputDataCheckCondition;
    }

    @Override
    public void addResultItem(String resultItem) {
        if (StringUtils.isEmpty((String)resultItem)) {
            return;
        }
        ((List)this.getResult()).add(resultItem);
    }
}

