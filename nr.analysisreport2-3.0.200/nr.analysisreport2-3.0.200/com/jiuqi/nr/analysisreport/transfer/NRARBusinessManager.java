/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.nr.analysisreport.transfer;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.internal.service.AnalysisReportService;
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
public class NRARBusinessManager
extends AbstractBusinessManager {
    protected final Logger logger = LoggerFactory.getLogger(NRARBusinessManager.class);
    @Autowired
    private AnalysisHelper analysisHelper;
    @Autowired
    private AnalysisReportService analysisReportService;

    public List<BusinessNode> getBusinessNodes(String parentId) throws TransferException {
        parentId = TransferUtil.getkey(parentId);
        try {
            List<AnalysisReportDefine> group = this.analysisHelper.getListByGroupKey(parentId);
            if (!CollectionUtils.isEmpty(group)) {
                ArrayList<BusinessNode> folders = new ArrayList<BusinessNode>();
                for (AnalysisReportDefine analysisTemp : group) {
                    BusinessNode businessNode = TransferUtil.schemeToBusinessNode(analysisTemp);
                    folders.add(businessNode);
                }
                return folders;
            }
        }
        catch (Exception e) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa \uff1agetBusinessNodes\uff1a\uff1a" + e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public BusinessNode getBusinessNode(String nodeId) throws TransferException {
        nodeId = TransferUtil.getkey(nodeId);
        try {
            AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(nodeId);
            return TransferUtil.schemeToBusinessNode(analysisReportDefine);
        }
        catch (Exception e) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa \uff1agetBusinessNode\uff1a\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    public BusinessNode getBusinessByNameAndType(String name, String type) throws TransferException {
        if ("NRAR".equals(type)) {
            try {
                List<AnalysisReportDefine> reportDefines = this.analysisReportService.fuzzyQuery(name);
                if (!CollectionUtils.isEmpty(reportDefines)) {
                    return TransferUtil.schemeToBusinessNode(reportDefines.get(0));
                }
            }
            catch (Exception e) {
                this.logger.error("\u5206\u6790\u62a5\u544a\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa \uff1agetBusinessByNameAndType\uff1a" + e.getMessage(), e);
            }
        }
        return null;
    }

    public List<FolderNode> getPathFolders(String nodeId) throws TransferException {
        ArrayList<FolderNode> folders = new ArrayList<FolderNode>();
        if (StringUtils.hasText(nodeId)) {
            nodeId = TransferUtil.getkey(nodeId);
        }
        try {
            AnalysisReportGroupDefine group;
            AnalysisReportDefine reportDefine = this.analysisHelper.getListByKey(nodeId);
            if (reportDefine == null) {
                return folders;
            }
            String parent = reportDefine.getGroupKey();
            while (StringUtils.hasText(parent) && !"0".equals(parent) && (group = this.analysisHelper.getGroupByKey(parent)) != null) {
                folders.add(TransferUtil.groupToFolderNode(group));
                parent = group.getParentgroup();
            }
            Collections.reverse(folders);
        }
        catch (Exception e) {
            this.logger.error("\u5206\u6790\u62a5\u544a\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa \uff1agetPathFolders\uff1a" + e.getMessage(), e);
        }
        return folders;
    }

    public void moveBusiness(BusinessNode businessNode, String targetFolderGuid) throws TransferException {
    }
}

