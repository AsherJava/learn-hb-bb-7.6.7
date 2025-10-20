/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO
 *  com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend
 */
package com.jiuqi.va.bill.impl.tablesync;

import com.jiuqi.va.bill.storage.BillAttachOptionStorage;
import com.jiuqi.va.bill.storage.BillOptionStorage;
import com.jiuqi.va.bill.storage.BillRuleOptionItemStorage;
import com.jiuqi.va.bill.storage.BillRuleOptionStorage;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO;
import com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VaBillOptionParamTableSync
implements VaParamTableSyncExtend {
    public List<VaParamTableSyncDTO> getParams() {
        ArrayList<VaParamTableSyncDTO> list = new ArrayList<VaParamTableSyncDTO>();
        VaParamTableSyncDTO vaParamTableSyncDTO = new VaParamTableSyncDTO();
        vaParamTableSyncDTO.setName("VaBillOption");
        vaParamTableSyncDTO.setTitle("\u5355\u636e\u8054\u67e5\u53c2\u6570");
        vaParamTableSyncDTO.addJTableModel(BillOptionStorage.getCreateJTM(TenantUtil.getTenantName()));
        list.add(vaParamTableSyncDTO);
        VaParamTableSyncDTO vaParamTableSyncDTO1 = new VaParamTableSyncDTO();
        vaParamTableSyncDTO1.setName("BillAttachOption");
        vaParamTableSyncDTO1.setTitle("\u5355\u636e\u9644\u4ef6\u63a7\u5236\u53c2\u6570");
        vaParamTableSyncDTO1.addJTableModel(BillAttachOptionStorage.getCreateJTM(TenantUtil.getTenantName()));
        list.add(vaParamTableSyncDTO1);
        VaParamTableSyncDTO vaParamTableSyncDTO2 = new VaParamTableSyncDTO();
        vaParamTableSyncDTO2.setName("BillRuleOption");
        vaParamTableSyncDTO2.setTitle("\u5355\u636e\u89c4\u5219\u63a7\u5236\u53c2\u6570");
        vaParamTableSyncDTO2.addJTableModel(BillRuleOptionStorage.getCreateJTM(TenantUtil.getTenantName()));
        vaParamTableSyncDTO2.addJTableModel(BillRuleOptionItemStorage.getCreateJTM(TenantUtil.getTenantName()));
        list.add(vaParamTableSyncDTO2);
        return list;
    }
}

