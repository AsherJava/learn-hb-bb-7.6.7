/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.workflow.WorkflowProcessForecastDTO
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.domain.workflow.WorkflowProcessForecastDTO;
import java.util.List;
import java.util.Map;

public interface ProcessForecastService {
    public R getForecastNodeInfo(String var1, String var2);

    public R getAllNodes(Model var1, Map<String, Object> var2);

    public List<Map<String, Object>> processViewForecast(WorkflowProcessForecastDTO var1);
}

