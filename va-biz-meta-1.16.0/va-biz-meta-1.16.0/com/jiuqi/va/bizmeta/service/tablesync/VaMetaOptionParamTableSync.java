/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO
 *  com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend
 */
package com.jiuqi.va.bizmeta.service.tablesync;

import com.jiuqi.va.bizmeta.storage.MetaDataOptionStorage;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO;
import com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaMetaOptionParamTableSync
implements VaParamTableSyncExtend {
    @Autowired
    private MetaDataOptionStorage MetaDataOptionStorage;

    public List<VaParamTableSyncDTO> getParams() {
        ArrayList<VaParamTableSyncDTO> list = new ArrayList<VaParamTableSyncDTO>();
        VaParamTableSyncDTO vaParamTableSyncDTO = new VaParamTableSyncDTO();
        vaParamTableSyncDTO.setName("VaMetaOption");
        vaParamTableSyncDTO.setTitle("\u5143\u6570\u636e\u63a7\u5236\u53c2\u6570");
        vaParamTableSyncDTO.addJTableModel(this.MetaDataOptionStorage.getSyncCreateJTM(TenantUtil.getTenantName()));
        list.add(vaParamTableSyncDTO);
        return list;
    }
}

