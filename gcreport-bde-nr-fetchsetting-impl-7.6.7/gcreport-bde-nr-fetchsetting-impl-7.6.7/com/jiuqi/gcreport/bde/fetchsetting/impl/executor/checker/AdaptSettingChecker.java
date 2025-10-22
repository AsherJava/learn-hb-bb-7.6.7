/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.budget.component.monitor.AdaptCheckMonitor
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.budget.component.monitor.AdaptCheckMonitor;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FixedAdaptSettingExcelDTO;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdaptSettingChecker {
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    public boolean doCheck(FixedAdaptSettingExcelDTO fixedAdaptSettingDTO, FetchSettingExcelContext fetchSettingExcelContext) {
        if (StringUtils.isEmpty((String)fixedAdaptSettingDTO.getAdaptFormula())) {
            return true;
        }
        ArrayList<Formula> formulaList = new ArrayList<Formula>();
        Formula formula = new Formula();
        formula.setFormula(fixedAdaptSettingDTO.getAdaptFormula());
        formulaList.add(formula);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        ConcurrentHashMap map = new ConcurrentHashMap(1);
        try {
            AdaptCheckMonitor adaptCheckMonitor = new AdaptCheckMonitor();
            DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, formulaList, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.EXPRESSION, (IMonitor)adaptCheckMonitor);
            if (!StringUtils.isEmpty((String)adaptCheckMonitor.getFormulaCheckResult())) {
                return false;
            }
        }
        catch (ParseException e) {
            return false;
        }
        return true;
    }

    public boolean doCheck(List<FixedAdaptSettingExcelDTO> fixedAdaptSettingDTOList, FetchSettingExcelContext fetchSettingExcelContext) {
        if (fixedAdaptSettingDTOList.stream().noneMatch(item -> StringUtils.isEmpty((String)item.getAdaptFormula()))) {
            return true;
        }
        return fixedAdaptSettingDTOList.stream().allMatch(item -> StringUtils.isEmpty((String)item.getAdaptFormula()));
    }
}

