/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.nr.analysisreport.transfer;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportGroupDefineImpl;
import com.jiuqi.nr.analysisreport.internal.service.AnalysisReportGroupService;
import com.jiuqi.nr.analysisreport.transfer.util.TransferUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class NRARFolderManager
extends AbstractFolderManager {
    protected final Logger logger = LoggerFactory.getLogger(NRARFolderManager.class);
    @Autowired
    private AnalysisHelper analysisHelper;
    @Autowired
    private AnalysisReportGroupService analysisReportGroupService;

    public List<FolderNode> getFolderNodes(String parentId) throws TransferException {
        parentId = TransferUtil.getkey(parentId);
        try {
            List<AnalysisReportGroupDefine> group = this.analysisHelper.getGroupByParent(parentId);
            if (!CollectionUtils.isEmpty(group)) {
                ArrayList<FolderNode> folders = new ArrayList<FolderNode>();
                for (AnalysisReportGroupDefine analysisTemp : group) {
                    FolderNode folder = TransferUtil.groupToFolderNode(analysisTemp);
                    folders.add(folder);
                }
                return folders;
            }
        }
        catch (Exception e) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1agetFolderNodes\uff1a\uff1a" + e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public FolderNode getFolderNode(String nodeId) throws TransferException {
        if (StringUtils.hasText(nodeId)) {
            nodeId = TransferUtil.getkey(nodeId);
        }
        try {
            AnalysisReportGroupDefine group = this.analysisHelper.getGroupByKey(nodeId);
            if (group == null) {
                return null;
            }
            return TransferUtil.groupToFolderNode(group);
        }
        catch (Exception e) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1agetFolderNode\uff1a\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    public List<FolderNode> getPathFolders(String s) throws TransferException {
        return null;
    }

    public String addFolder(FolderNode folderNode) throws TransferException {
        AnalysisReportGroupDefineImpl group = TransferUtil.folderNodeToGroup(folderNode);
        try {
            this.analysisHelper.insertGroup(group);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return folderNode.getGuid();
    }

    public void modifyFolder(FolderNode folderNode) throws TransferException {
        AnalysisReportGroupDefineImpl group = TransferUtil.folderNodeToGroup(folderNode);
        try {
            this.analysisHelper.updateGroup(group);
        }
        catch (Exception e) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1amodifyFolder\uff1a\uff1a" + e.getMessage(), e);
        }
    }

    public FolderNode getFolderByTitle(String parentGuid, String title, String type) throws TransferException {
        try {
            List<AnalysisReportGroupDefine> analysisReportGroupDefines = this.analysisReportGroupService.fuzzyQuery(title);
            if (CollectionUtils.isEmpty(analysisReportGroupDefines)) {
                return null;
            }
            parentGuid = !StringUtils.hasText(parentGuid) ? "0" : TransferUtil.getkey(parentGuid);
            for (AnalysisReportGroupDefine group : analysisReportGroupDefines) {
                if (!parentGuid.equals(group.getKey())) continue;
                return TransferUtil.groupToFolderNode(group);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1agetFolderByTitle\uff1a\uff1a" + e.getMessage(), e);
        }
        return null;
    }
}

