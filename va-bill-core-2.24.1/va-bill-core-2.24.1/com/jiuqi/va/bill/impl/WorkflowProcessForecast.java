/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.process.ProcessForecast
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.process.ProcessForecast;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class WorkflowProcessForecast
implements ProcessForecast {
    @Autowired
    private WorkflowServerClient workflowServerClient;

    public String getForecastCode() {
        return "FORECAST_WORKFLOW";
    }

    public List<Map<String, Object>> getForecastNodeInfos(Model model, Map<String, Object> params) {
        WorkflowDTO workflowDTO = (WorkflowDTO)params.get("workflowDTO");
        R r = this.workflowServerClient.processForecast(workflowDTO);
        if (r.getCode() == 0) {
            List data = (List)r.get((Object)"data");
            if (CollectionUtils.isEmpty(data)) {
                return new ArrayList<Map<String, Object>>();
            }
            data.remove(0);
            data.remove(data.size() - 1);
            return data;
        }
        throw new BillException(r.getMsg());
    }

    public String getForecastOrder() {
        return "001";
    }

    public Map<String, Object> getForecastNodeInfo(String bizCode, String stencilId) {
        return null;
    }
}

