/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.util.DefinitionOptionUtils;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ParsedExpressionFilter {
    @Autowired
    private INvwaSystemOptionService optionService;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IRuntimeDataSchemeService service;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DefinitionOptionUtils definitionOptionUtils;

    public List<IParsedExpression> removeCrossDimensionFML(String formSchemeKey, List<IParsedExpression> iParsedExpressions, IMonitor formulaMonitor) {
        if (!this.definitionOptionUtils.isSpecifyDimensionAssignment()) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            List reportDimension = this.service.getReportDimension(taskDefine.getDataScheme());
            List dwDimension = this.service.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.UNIT);
            List periodDimension = this.service.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.PERIOD);
            reportDimension.addAll(dwDimension);
            reportDimension.addAll(periodDimension);
            Set<String> dimensionNames = reportDimension.stream().map(d -> this.entityMetaService.getDimensionName(d.getDimKey())).collect(Collectors.toSet());
            return this.removeCrossDimensionFML(dimensionNames, iParsedExpressions, formulaMonitor);
        }
        return iParsedExpressions;
    }

    public List<IParsedExpression> removeCrossDimensionFML(Set<String> reportDimensionNames, List<IParsedExpression> iParsedExpressions, IMonitor formulaMonitor) {
        if (this.definitionOptionUtils.isSpecifyDimensionAssignment()) {
            return iParsedExpressions;
        }
        if (CollectionUtils.isEmpty(iParsedExpressions)) {
            return iParsedExpressions;
        }
        List<IParsedExpression> result = iParsedExpressions.stream().filter(f -> {
            DynamicDataNode assignNode = f.getAssignNode();
            if (assignNode == null) {
                return true;
            }
            if (StringUtils.hasText(assignNode.getRelateTaskItem())) {
                this.fixException(formulaMonitor, f.getSource(), "\u4e0d\u652f\u6301\u5173\u8054\u4efb\u52a1\u8d4b\u503c\uff01");
                return false;
            }
            DimensionValueSet dimensionValueSet = null;
            if (assignNode.getDataModelLink() == null) {
                if (assignNode.getQueryField() != null) {
                    QueryTable table = assignNode.getQueryField().getTable();
                    if (table.getPeriodModifier() != null) {
                        this.fixException(formulaMonitor, f.getSource(), "\u4e0d\u652f\u6301\u6307\u5b9a\u65f6\u671f\u7ef4\u5ea6\u8d4b\u503c\uff01");
                        return false;
                    }
                    dimensionValueSet = table.getDimensionRestriction();
                }
            } else {
                if (assignNode.getDataModelLink().getPeriodModifier() != null) {
                    this.fixException(formulaMonitor, f.getSource(), "\u4e0d\u652f\u6301\u6307\u5b9a\u65f6\u671f\u7ef4\u5ea6\u8d4b\u503c\uff01");
                    return false;
                }
                dimensionValueSet = assignNode.getDataModelLink().getDimensionRestriction();
            }
            if (dimensionValueSet == null || dimensionValueSet.size() == 0) {
                return true;
            }
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String dimensionName = dimensionValueSet.getName(i);
                if (dimensionName.equals("ADJUST")) {
                    this.fixException(formulaMonitor, f.getSource(), "\u4e0d\u652f\u6301\u6307\u5b9a\u8c03\u6574\u671f\u8d4b\u503c\uff01");
                    return false;
                }
                Optional<String> dimension = reportDimensionNames.stream().filter(d -> dimensionName.equals(d)).findFirst();
                if (!dimension.isPresent()) continue;
                this.fixException(formulaMonitor, f.getSource(), "\u4e0d\u652f\u6301\u6307\u5b9a\u7ef4\u5ea6\u8d4b\u503c\uff01");
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        return result;
    }

    private void fixException(IMonitor formulaMonitor, Formula formula, String message) {
        if (formulaMonitor != null) {
            FormulaParseException formulaParseException = new FormulaParseException(message);
            formulaParseException.setErrorFormua(formula);
            formulaMonitor.exception((Exception)formulaParseException);
        }
    }
}

