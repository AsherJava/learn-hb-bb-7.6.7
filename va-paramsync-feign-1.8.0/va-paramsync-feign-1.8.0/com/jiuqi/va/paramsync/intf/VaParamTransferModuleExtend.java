/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.intf;

import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import java.util.List;

public interface VaParamTransferModuleExtend {
    public String getName();

    public String getTitle();

    default public String getModuleId() {
        return this.getName();
    }

    default public String getParentCategoryId() {
        return null;
    }

    default public List<VaParamTransferCategory> getCategorys() {
        return null;
    }

    default public List<String> getDependenceFactoryIds() {
        return null;
    }

    default public List<VaParamTransferFolderNode> getFolderNodes(String category, String parent) {
        return null;
    }

    default public VaParamTransferFolderNode getFolderNode(String category, String nodeId) {
        return null;
    }

    default public VaParamTransferFolderNode addFolderNode(String category, VaParamTransferFolderNode node) {
        return null;
    }

    default public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        return null;
    }

    default public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        return null;
    }

    default public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        return null;
    }

    default public List<VaParamTransferBusinessNode> getRelatedBusiness(String category, String nodeId) {
        return null;
    }

    default public String getExportModelInfo(String category, String nodeId) {
        return null;
    }

    default public void importModelInfo(String category, String info) {
    }

    default public void importModelInfo(String category, String info, boolean isImportMultiLanguage) {
        this.importModelInfo(category, info);
    }

    default public void importModelInfo(String category, String info, boolean importMultiLanguage, boolean importDataFlag) {
        this.importModelInfo(category, info, importMultiLanguage);
    }

    default public String getExportDataInfo(String category, String nodeId) {
        return null;
    }

    default public void importDataInfo(String category, String targetId, String info) {
    }

    default public void importDataInfo(String category, String targetId, String info, boolean isImportMultiLanguage) {
        this.importDataInfo(category, targetId, info);
    }
}

