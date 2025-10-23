/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.tree.zb;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.tree.zb.ZbTreeNodeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataSchemeTreeNodeBuilder
implements TreeNodeBuilder<DataScheme> {
    @Autowired
    private SummaryParamService summaryParamService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IDesignSummarySolutionService designSummarySolutionService;

    @Override
    public boolean needQuery(TreeQueryParam treeQueryParam) {
        return !StringUtils.hasLength(treeQueryParam.getNodeKey());
    }

    @Override
    public List<DataScheme> queryData(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        ArrayList<DataScheme> dataSchemes = new ArrayList<DataScheme>();
        Map<String, Object> customParams = treeQueryParam.getCustomParams();
        String taskKey = customParams.get("taskKey").toString();
        TaskDefine taskDefine = this.summaryParamService.getTaskDefine(taskKey);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        dataSchemes.add(dataScheme);
        String sumTaskKey = customParams.get("sumTaskKey").toString();
        SummarySolutionModel solutionModel = this.designSummarySolutionService.getSummarySolutionModel(sumTaskKey);
        List<String> relationTasks = solutionModel.getRelationTasks();
        if (!CollectionUtils.isEmpty(relationTasks)) {
            for (String relTask : relationTasks) {
                TaskDefine relTaskDefine = this.summaryParamService.getTaskDefine(relTask);
                DataScheme relDataScheme = this.dataSchemeService.getDataScheme(relTaskDefine.getDataScheme());
                dataSchemes.add(relDataScheme);
            }
        }
        return dataSchemes;
    }

    @Override
    public TreeNode buildTreeNode(DataScheme dataScheme, TreeQueryParam treeQueryParam) {
        TreeNode node = new TreeNode();
        node.setKey(dataScheme.getKey());
        node.setCode(dataScheme.getCode());
        node.setTitle(dataScheme.getTitle());
        node.setIcon("#icon-16_SHU_A_NR_shujufangan");
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", ZbTreeNodeType.DATASCHEME.getCode());
        node.setData(extDataJson.toString());
        return node;
    }
}

