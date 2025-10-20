/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule
 *  com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 */
package com.jiuqi.dc.penetratebill.impl.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule;
import com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO;
import com.jiuqi.dc.penetratebill.impl.service.impl.PenetrateBillServiceImpl;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PenetrateBillTransferModule
implements DcTransferModule {
    private static final String PENETRATE_BILL = "penetrateBill";
    private static final String PENETRATE_BILL_TITLE = "\u5355\u636e\u8054\u67e5\u65b9\u6848";
    @Autowired
    private PenetrateBillServiceImpl penetrateBillService;

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categorys = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName(PENETRATE_BILL);
        category.setTitle(PENETRATE_BILL_TITLE);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categorys.add(category);
        return categorys;
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        ArrayList<VaParamTransferBusinessNode> businessNodes = new ArrayList<VaParamTransferBusinessNode>();
        List penetrateSchemes = this.penetrateBillService.listAll().stream().sorted(Comparator.comparing(PenetrateSchemeDTO::getCreateDate)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(penetrateSchemes)) {
            return businessNodes;
        }
        for (PenetrateSchemeDTO penetrateScheme : penetrateSchemes) {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            node.setId(penetrateScheme.getId());
            node.setName(penetrateScheme.getId());
            node.setTitle(penetrateScheme.getSchemeName());
            node.setType(PENETRATE_BILL);
            node.setTypeTitle(PENETRATE_BILL_TITLE);
            businessNodes.add(node);
        }
        return businessNodes;
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (StringUtils.isEmpty((String)nodeId)) {
            return null;
        }
        VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
        PenetrateSchemeDTO penetrateScheme = this.penetrateBillService.getById(nodeId);
        if (penetrateScheme == null) {
            return null;
        }
        node.setId(penetrateScheme.getId());
        node.setName(penetrateScheme.getId());
        node.setTitle(penetrateScheme.getSchemeName());
        node.setType(PENETRATE_BILL);
        node.setTypeTitle(PENETRATE_BILL_TITLE);
        return node;
    }

    public String getExportModelInfo(String category, String nodeId) {
        if (StringUtils.isEmpty((String)nodeId)) {
            return JsonUtils.writeValueAsString((Object)"");
        }
        PenetrateSchemeDTO penetrateScheme = this.penetrateBillService.getById(nodeId);
        if (penetrateScheme == null) {
            return JsonUtils.writeValueAsString((Object)"");
        }
        return JsonUtils.writeValueAsString((Object)penetrateScheme);
    }

    public void importModelInfo(String category, String info) {
        if (StringUtils.isEmpty((String)info)) {
            return;
        }
        PenetrateSchemeDTO penetrateScheme = (PenetrateSchemeDTO)JsonUtils.readValue((String)info, PenetrateSchemeDTO.class);
        PenetrateSchemeDTO oldPenetrateScheme = this.penetrateBillService.getById(penetrateScheme.getId());
        if (oldPenetrateScheme == null) {
            this.penetrateBillService.create(penetrateScheme);
        } else {
            penetrateScheme.setVer(oldPenetrateScheme.getVer());
            this.penetrateBillService.modify(penetrateScheme);
        }
    }
}

