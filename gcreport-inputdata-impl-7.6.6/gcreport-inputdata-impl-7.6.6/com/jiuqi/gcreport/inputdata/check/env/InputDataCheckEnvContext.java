/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 */
package com.jiuqi.gcreport.inputdata.check.env;

import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import java.util.List;

public interface InputDataCheckEnvContext
extends ProgressData<List<String>> {
    public InputDataCheckCondition getInputDataCheckCondition();

    public List<String> getResult();

    public void addResultItem(String var1);
}

