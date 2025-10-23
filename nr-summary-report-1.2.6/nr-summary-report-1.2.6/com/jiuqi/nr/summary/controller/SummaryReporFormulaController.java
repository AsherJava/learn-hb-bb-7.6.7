/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.exception.DBParaException
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.formula.FormulaResourceDirNodeProvider;
import com.jiuqi.nr.summary.formula.FormulaResourceNodeProvider;
import com.jiuqi.nr.summary.model.formula.Formula;
import com.jiuqi.nr.summary.vo.ResourceNode;
import com.jiuqi.nr.summary.vo.TreeNode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_report/formula"})
public class SummaryReporFormulaController {
    @Autowired
    private IDesignSummarySolutionService designSummarySolutionService;
    @Autowired
    private FormulaResourceDirNodeProvider formulaResourceDirNodeProvider;
    @Autowired
    private FormulaResourceNodeProvider formulaResourceNodeProvider;

    @GetMapping(value={"/tree/children/{solutionKey}"})
    public List<TreeNode> getTreeChildren(@PathVariable String solutionKey) {
        return this.formulaResourceDirNodeProvider.getChilds(solutionKey);
    }

    @GetMapping(value={"/resource_data/{reportKey}"})
    public List<ResourceNode> getResourceDatas(@PathVariable String reportKey) {
        return this.formulaResourceNodeProvider.getNodes(reportKey);
    }

    @PostMapping(value={"/add"})
    public void addFormula(@RequestBody Formula formula) throws DBParaException {
        this.designSummarySolutionService.insertFormula(formula);
    }

    @PostMapping(value={"/update"})
    public void updateFormula(@RequestBody Formula formula) throws DBParaException {
        this.designSummarySolutionService.updateFormula(formula);
    }

    @PostMapping(value={"/batch_add_update"})
    public void batchInsertOrUpdateFormula(@RequestBody List<Formula> formulas) throws DBParaException {
        ArrayList<Formula> insertFormulas = new ArrayList<Formula>();
        ArrayList<Formula> updateFormulas = new ArrayList<Formula>();
        for (Formula formula : formulas) {
            if (StringUtils.hasLength(formula.getKey())) {
                updateFormulas.add(formula);
                continue;
            }
            insertFormulas.add(formula);
        }
        if (!CollectionUtils.isEmpty(insertFormulas)) {
            this.designSummarySolutionService.batchInsertFormula(insertFormulas);
        }
        if (!CollectionUtils.isEmpty(updateFormulas)) {
            this.designSummarySolutionService.batchUpdateFormula(updateFormulas);
        }
    }

    @GetMapping(value={"/delete/{formulaKey}"})
    public void deleteFormulaByKey(@PathVariable String formulaKey) throws DBParaException {
        this.designSummarySolutionService.deleteFormulaByKey(formulaKey);
    }

    @PostMapping(value={"/delete"})
    public void deleteFormulaByKeys(@RequestBody List<String> formulaKeys) throws DBParaException {
        this.designSummarySolutionService.deleteFormulaByKeys(formulaKeys);
    }
}

