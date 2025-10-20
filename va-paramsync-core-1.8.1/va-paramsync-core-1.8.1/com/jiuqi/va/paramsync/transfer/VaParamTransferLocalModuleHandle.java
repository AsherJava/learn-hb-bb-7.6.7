/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 */
package com.jiuqi.va.paramsync.transfer;

import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaParamTransferLocalModuleHandle {
    @Autowired(required=false)
    private List<VaParamTransferModuleIntf> modules;

    public VaParamTransferModuleIntf getModule(String moduleName) {
        for (VaParamTransferModuleIntf module : this.modules) {
            if (!moduleName.equals(module.getName())) continue;
            return module;
        }
        return null;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String moduleName, String category, String parent) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.getFolderNodes(category, parent);
        }
        return null;
    }

    public VaParamTransferFolderNode getFolderNode(String moduleName, String category, String nodeId) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.getFolderNode(category, nodeId);
        }
        return null;
    }

    public VaParamTransferFolderNode addFolderNode(String moduleName, String category, VaParamTransferFolderNode node) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.addFolderNode(category, node);
        }
        return null;
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String moduleName, String category, String parent) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.getBusinessNodes(category, parent);
        }
        return null;
    }

    public VaParamTransferBusinessNode getBusinessNode(String moduleName, String category, String nodeId) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.getBusinessNode(category, nodeId);
        }
        return null;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String moduleName, String category, String nodeId) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.getPathFolders(category, nodeId);
        }
        return null;
    }

    public List<VaParamTransferBusinessNode> getRelatedBusiness(String moduleName, String category, String nodeId) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.getRelatedBusiness(category, nodeId);
        }
        return null;
    }

    public String getExportModelInfo(String moduleName, String category, String nodeId) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.getExportModelInfo(category, nodeId);
        }
        return null;
    }

    public void importModelInfo(String moduleName, String category, String info) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            module.importModelInfo(category, info);
        }
    }

    public String getExportDataInfo(String moduleName, String category, String nodeId) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            return module.getExportDataInfo(category, nodeId);
        }
        return null;
    }

    public void importDataInfo(String moduleName, String category, String targetId, String info) {
        VaParamTransferModuleIntf module = this.getModule(moduleName);
        if (module != null) {
            module.importDataInfo(category, targetId, info);
        }
    }
}

