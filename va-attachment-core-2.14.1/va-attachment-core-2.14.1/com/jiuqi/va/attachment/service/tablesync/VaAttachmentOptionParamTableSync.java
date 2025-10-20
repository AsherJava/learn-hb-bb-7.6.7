/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO
 *  com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend
 */
package com.jiuqi.va.attachment.service.tablesync;

import com.jiuqi.va.attachment.storage.FileOptionStorage;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO;
import com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaAttachmentOptionParamTableSync
implements VaParamTableSyncExtend {
    public List<VaParamTableSyncDTO> getParams() {
        ArrayList<VaParamTableSyncDTO> list = new ArrayList<VaParamTableSyncDTO>();
        VaParamTableSyncDTO vaParamTableSyncDTO = new VaParamTableSyncDTO();
        vaParamTableSyncDTO.setName("VaFileOption");
        vaParamTableSyncDTO.setTitle("\u6587\u4ef6\u63a7\u5236\u53c2\u6570");
        vaParamTableSyncDTO.addJTableModel(FileOptionStorage.getCreateJTM(TenantUtil.getTenantName()));
        list.add(vaParamTableSyncDTO);
        return list;
    }
}

