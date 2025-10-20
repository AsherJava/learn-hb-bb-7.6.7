/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.workflow.detection.ParamExtract
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.biz.intf.workflow.detection.ParamExtract;
import java.util.List;
import java.util.Map;

public interface BillParamExtractService {
    public List<ParamExtract> getParamExtractBeanList(String var1);

    public Map<String, Object> getWorkflowBusinessParam(Map<String, Object> var1, String var2, Map<String, Object> var3);
}

