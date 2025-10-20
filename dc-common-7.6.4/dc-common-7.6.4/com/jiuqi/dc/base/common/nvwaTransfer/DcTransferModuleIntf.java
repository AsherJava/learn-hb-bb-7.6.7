/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 */
package com.jiuqi.dc.base.common.nvwaTransfer;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule;
import com.jiuqi.dc.base.common.nvwaTransfer.TransferModuleFactory;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DcTransferModuleIntf
extends VaParamTransferModuleIntf {
    private static final String MODULE_NAME = "DC_YbzTransferModule";
    private static final String MODULE_TITLE = "\u4e00\u672c\u8d26";
    @Autowired
    private TransferModuleFactory transferModuleFactory;

    public String getModuleId() {
        return MODULE_NAME;
    }

    public String getName() {
        return MODULE_NAME;
    }

    public String getTitle() {
        return MODULE_TITLE;
    }

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categorys = new ArrayList<VaParamTransferCategory>();
        List<DcTransferModule> transferModules = this.transferModuleFactory.getTransferModules();
        if (!CollectionUtils.isEmpty(transferModules)) {
            for (DcTransferModule transferModule : transferModules) {
                categorys.addAll(transferModule.getCategorys());
            }
        }
        return categorys;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String category, String parent) {
        return this.transferModuleFactory.getTransferModule(category).getFolderNodes(category, parent);
    }

    public VaParamTransferFolderNode getFolderNode(String category, String nodeId) {
        return this.transferModuleFactory.getTransferModule(category).getFolderNode(category, nodeId);
    }

    public VaParamTransferFolderNode addFolderNode(String category, VaParamTransferFolderNode node) {
        return this.transferModuleFactory.getTransferModule(category).addFolderNode(category, node);
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        return this.transferModuleFactory.getTransferModule(category).getBusinessNodes(category, parent);
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        return this.transferModuleFactory.getTransferModule(category).getBusinessNode(category, nodeId);
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        return this.transferModuleFactory.getTransferModule(category).getPathFolders(category, nodeId);
    }

    public String getExportModelInfo(String category, String nodeId) {
        return this.transferModuleFactory.getTransferModule(category).getExportModelInfo(category, nodeId);
    }

    public void importModelInfo(String category, String info) {
        this.transferModuleFactory.getTransferModule(category).importModelInfo(category, info);
    }

    public List<VaParamTransferBusinessNode> getRelatedBusiness(String category, String nodeId) {
        return this.transferModuleFactory.getTransferModule(category).getRelatedBusiness(category, nodeId);
    }

    public String getExportDataInfo(String category, String nodeId) {
        return this.transferModuleFactory.getTransferModule(category).getExportDataInfo(category, nodeId);
    }

    public void importDataInfo(String category, String targetId, String info) {
        this.transferModuleFactory.getTransferModule(category).importDataInfo(category, targetId, info);
    }
}

