/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.api.DesignSummaryFormula;
import com.jiuqi.nr.summary.executor.deploy.AbstractExecutor;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.internal.dto.SummaryFormulaDTO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryFormulaService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryFormulaService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

class FormulaDeployExecutor
extends AbstractExecutor {
    private final IDesignSummaryFormulaService designFormulaService;
    private final IRuntimeSummaryFormulaService runtimeFormulaService;

    public FormulaDeployExecutor(DeployContext context, AsyncTaskMonitor monitor, IDesignSummaryFormulaService designFormulaService, IRuntimeSummaryFormulaService runtimeFormulaService) {
        super(context, monitor);
        this.designFormulaService = designFormulaService;
        this.runtimeFormulaService = runtimeFormulaService;
    }

    @Override
    public boolean execute() {
        String solutionKey = this.context.getSolutionModel().getKey();
        try {
            this.runtimeFormulaService.deleteSummaryFormulaBySolution(solutionKey);
            List<DesignSummaryFormula> designFormulas = this.designFormulaService.getSummaryFormulasBySolution(solutionKey);
            if (!CollectionUtils.isEmpty(designFormulas)) {
                List<SummaryFormulaDTO> summaryFormulaDTOs = designFormulas.stream().map(designSummaryFormula -> (SummaryFormulaDTO)((Object)designSummaryFormula)).collect(Collectors.toList());
                this.runtimeFormulaService.batchInsertSummaryFormula(summaryFormulaDTOs);
                this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6c47\u603b\u65b9\u6848:\u540c\u6b65\u4e86\u3010%s\u3011\u4e2a\u5355\u5143\u683c\u5230\u8fd0\u884c\u671f", String.valueOf(summaryFormulaDTOs.size()));
            }
        }
        catch (DBParaException e) {
            this.error(e);
        }
        return false;
    }
}

