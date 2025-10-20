/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 */
package com.jiuqi.dc.base.common.nvwaTransfer;

import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import java.util.List;

public interface DcTransferModule {
    public List<VaParamTransferCategory> getCategorys();

    default public List<VaParamTransferFolderNode> getFolderNodes(String category, String parent) {
        return null;
    }

    default public VaParamTransferFolderNode getFolderNode(String category, String nodeId) {
        return null;
    }

    default public VaParamTransferFolderNode addFolderNode(String category, VaParamTransferFolderNode node) {
        return null;
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String var1, String var2);

    public VaParamTransferBusinessNode getBusinessNode(String var1, String var2);

    default public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        return null;
    }

    default public List<VaParamTransferBusinessNode> getRelatedBusiness(String category, String nodeId) {
        return null;
    }

    public String getExportModelInfo(String var1, String var2);

    public void importModelInfo(String var1, String var2);

    default public String getExportDataInfo(String category, String nodeId) {
        return null;
    }

    default public void importDataInfo(String category, String targetId, String info) {
    }

    default public List<String> getDependenceFactoryIds(String category) {
        return null;
    }
}

