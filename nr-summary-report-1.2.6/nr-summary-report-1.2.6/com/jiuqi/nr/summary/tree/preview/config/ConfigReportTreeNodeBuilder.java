/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.tree.preview.config;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.vo.NodeType;
import java.util.List;
import org.json.JSONObject;

public class ConfigReportTreeNodeBuilder
implements TreeNodeBuilder<SummaryReport> {
    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        if (treeQueryParam != null) {
            String extJsonStr = treeQueryParam.getCustomValue("ext").toString();
            JSONObject extJson = new JSONObject(extJsonStr);
            int type = extJson.optInt("type");
            return type == NodeType.SUMMARY_SOLUTION.getCode();
        }
        return false;
    }

    @Override
    public List<SummaryReport> queryData(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        IRuntimeSummarySolutionService runtimSumSolutionService = (IRuntimeSummarySolutionService)SpringBeanUtils.getBean(IRuntimeSummarySolutionService.class);
        return runtimSumSolutionService.getSummaryReportDefinesBySolu(treeQueryParam.getNodeKey());
    }

    @Override
    public TreeNode buildTreeNode(SummaryReport report, TreeQueryParam treeQueryParam) throws SummaryCommonException {
        TreeNode treeNode = new TreeNode();
        treeNode.setKey(report.getKey());
        treeNode.setTitle(report.getTitle());
        treeNode.setLeaf(true);
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", NodeType.SUMMARY_REPORT.getCode());
        treeNode.setData(extDataJson.toString());
        return treeNode;
    }
}

