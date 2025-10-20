/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO
 *  com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend
 */
package com.jiuqi.va.workflow.service.impl.tablesync;

import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO;
import com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend;
import com.jiuqi.va.workflow.storage.WorkflowOptionStorage;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowOptionParamTableSync
implements VaParamTableSyncExtend {
    public List<VaParamTableSyncDTO> getParams() {
        ArrayList<VaParamTableSyncDTO> list = new ArrayList<VaParamTableSyncDTO>();
        VaParamTableSyncDTO vaParamTableSyncDTO = new VaParamTableSyncDTO();
        vaParamTableSyncDTO.setName("VaWorkflowOption");
        vaParamTableSyncDTO.setTitle("\u5de5\u4f5c\u6d41\u63a7\u5236\u53c2\u6570");
        vaParamTableSyncDTO.addJTableModel(WorkflowOptionStorage.getCreateJTM(TenantUtil.getTenantName()));
        list.add(vaParamTableSyncDTO);
        return list;
    }
}

