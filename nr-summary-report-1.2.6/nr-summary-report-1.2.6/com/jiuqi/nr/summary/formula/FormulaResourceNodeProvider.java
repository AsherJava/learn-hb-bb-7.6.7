/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.formula;

import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.model.formula.Formula;
import com.jiuqi.nr.summary.vo.NodeType;
import com.jiuqi.nr.summary.vo.ResourceNode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaResourceNodeProvider {
    @Autowired
    private IDesignSummarySolutionService designSummarySolutionService;

    public List<ResourceNode> getNodes(String parentKey) {
        return this.designSummarySolutionService.getFormulasByReport(parentKey).stream().map(this::buildFormulaNode).collect(Collectors.toList());
    }

    private ResourceNode buildFormulaNode(Formula formula) {
        ResourceNode resourceNode = new ResourceNode(formula.getKey(), formula.getFmCode(), formula.getExpression(), NodeType.SUMMARY_FORMULA, formula.getModifyTime());
        resourceNode.setData(formula);
        return resourceNode;
    }
}

