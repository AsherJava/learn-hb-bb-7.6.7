/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO
 *  com.jiuqi.dc.base.common.module.IModuleGather
 *  com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 */
package com.jiuqi.dc.base.impl.onlinePeriod.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO;
import com.jiuqi.dc.base.common.module.IModuleGather;
import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule;
import com.jiuqi.dc.base.impl.onlinePeriod.domain.OnlinePeriodDefineDO;
import com.jiuqi.dc.base.impl.onlinePeriod.mapper.OnlinePeriodDefineMapper;
import com.jiuqi.dc.base.impl.onlinePeriod.service.OnlinePeriodDefineService;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombDefineDO;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombDefineMapper;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OnlinePeriodDcTransferModule
implements DcTransferModule {
    private static final String MODULE_NAME = "DC_YbzTransferModule";
    private static final String ONLINE_PERIOD = "OnlinePeriod";
    private static final String ONLINE_PERIOD_TITLE = "\u4e0a\u7ebf\u671f\u95f4";
    private static final String ORG_COMB_GROUP = "OrgCombGroup";
    private static final String ONLINE_PERIOD_PRFX = "OlinePeriod##";
    @Autowired
    private IModuleGather iModuleGather;
    @Autowired
    private OnlinePeriodDefineMapper onlinePeriodDefineMapper;
    @Autowired
    private OnlinePeriodDefineService onlinePeriodDefineService;
    @Autowired
    private OrgCombDefineMapper orgCombDefineMapper;

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categorys = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName(ONLINE_PERIOD);
        category.setTitle(ONLINE_PERIOD_TITLE);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categorys.add(category);
        return categorys;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String category, String parent) {
        if (StringUtils.hasText(parent)) {
            return null;
        }
        ArrayList<VaParamTransferFolderNode> businessNodes = new ArrayList<VaParamTransferFolderNode>();
        List iModules = this.iModuleGather.getModules();
        iModules.forEach(iModule -> {
            VaParamTransferFolderNode node = new VaParamTransferFolderNode();
            node.setId(ONLINE_PERIOD_PRFX + iModule.getCode());
            node.setName(iModule.getCode());
            node.setTitle(iModule.getName());
            businessNodes.add(node);
        });
        return businessNodes;
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        if (parent == null || !parent.startsWith(ONLINE_PERIOD_PRFX)) {
            return null;
        }
        parent = parent.substring(ONLINE_PERIOD_PRFX.length());
        ArrayList<VaParamTransferBusinessNode> businessNodes = new ArrayList<VaParamTransferBusinessNode>();
        List onlinePeriodDefines = this.onlinePeriodDefineMapper.select((Object)new OnlinePeriodDefineDO(parent));
        onlinePeriodDefines.forEach(item -> {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            node.setId(item.getId());
            node.setName(item.getOnlinePeriod() + "-" + item.getId());
            node.setTitle(item.getOnlinePeriod());
            node.setType(ONLINE_PERIOD);
            node.setTypeTitle(ONLINE_PERIOD_TITLE);
            businessNodes.add(node);
        });
        return businessNodes;
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
        OnlinePeriodDefineVO onlinePeriodDefine = null;
        try {
            onlinePeriodDefine = this.onlinePeriodDefineService.getPeriodDataById(nodeId);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (Objects.isNull(onlinePeriodDefine)) {
            return null;
        }
        node.setId(onlinePeriodDefine.getId());
        node.setName(onlinePeriodDefine.getOnlinePeriod() + "-" + onlinePeriodDefine.getId());
        node.setTitle(onlinePeriodDefine.getOnlinePeriod());
        node.setType(ONLINE_PERIOD);
        node.setTypeTitle(ONLINE_PERIOD_TITLE);
        return node;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        ArrayList<VaParamTransferFolderNode> folders = new ArrayList<VaParamTransferFolderNode>();
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        OnlinePeriodDefineVO onlinePeriodDefine = null;
        try {
            onlinePeriodDefine = this.onlinePeriodDefineService.getPeriodDataById(nodeId);
        }
        catch (Exception exception) {
            // empty catch block
        }
        List iModules = this.iModuleGather.getModules();
        OnlinePeriodDefineVO finalOnlinePeriodDefine = onlinePeriodDefine;
        iModules.forEach(iModule -> {
            if (finalOnlinePeriodDefine != null && iModule.getCode().equals(finalOnlinePeriodDefine.getModuleCode())) {
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                node.setId(ONLINE_PERIOD_PRFX + iModule.getCode());
                node.setName(iModule.getCode());
                node.setTitle(iModule.getName());
                folders.add(node);
            }
        });
        return folders;
    }

    public String getExportModelInfo(String category, String nodeId) {
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        OnlinePeriodDefineVO onlinePeriodDefine = null;
        try {
            onlinePeriodDefine = this.onlinePeriodDefineService.getPeriodDataById(nodeId);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return onlinePeriodDefine != null ? JsonUtils.writeValueAsString((Object)onlinePeriodDefine) : null;
    }

    public void importModelInfo(String category, String info) {
        if (!StringUtils.hasText(category)) {
            return;
        }
        OnlinePeriodDefineVO onlinePeriodDefineVO = (OnlinePeriodDefineVO)JsonUtils.readValue((String)info, OnlinePeriodDefineVO.class);
        Assert.isNotNull((Object)onlinePeriodDefineVO);
        OnlinePeriodDefineVO oldOnlinePeriod = null;
        try {
            oldOnlinePeriod = this.onlinePeriodDefineService.getPeriodDataById(onlinePeriodDefineVO.getId());
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (Objects.isNull(oldOnlinePeriod)) {
            this.onlinePeriodDefineService.nvwaImportPeriod(onlinePeriodDefineVO);
        } else {
            this.onlinePeriodDefineService.updatePeriod(onlinePeriodDefineVO);
        }
    }

    public List<VaParamTransferBusinessNode> getRelatedBusiness(String category, String nodeId) {
        String[] codes;
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        ArrayList<VaParamTransferBusinessNode> resList = new ArrayList<VaParamTransferBusinessNode>();
        OnlinePeriodDefineVO onlinePeriod = null;
        try {
            onlinePeriod = this.onlinePeriodDefineService.getPeriodDataById(nodeId);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (onlinePeriod == null) {
            return resList;
        }
        for (String code : codes = onlinePeriod.getOrgCombCodes().split(";")) {
            OrgCombDefineDO queryWrapper = new OrgCombDefineDO();
            queryWrapper.setCode(code);
            List orgCombDefines = this.orgCombDefineMapper.select((Object)queryWrapper);
            if (orgCombDefines.isEmpty()) continue;
            OrgCombDefineDO orgCombDefineDO = (OrgCombDefineDO)((Object)orgCombDefines.get(0));
            VaParamTransferBusinessNode resNode = new VaParamTransferBusinessNode();
            resNode.setId(orgCombDefineDO.getId());
            resNode.setCategoryId(ORG_COMB_GROUP);
            resNode.setModuleId(MODULE_NAME);
            resList.add(resNode);
        }
        return resList;
    }
}

