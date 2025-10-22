/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO$PeriodDim
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO$UnitDim
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.apache.commons.lang3.StringUtils
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.formula.helper;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.var.formula.vo.ReportFormulaGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaDivideHelper {
    @Autowired
    private IAnalysisReportEntityService entityService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    public Map<String, ReportBaseVO.UnitDim> buildCommonDim(ReportVariableParseVO reportVariableParseVO) {
        HashMap<String, ReportBaseVO.UnitDim> map = new HashMap<String, ReportBaseVO.UnitDim>();
        List chooseUnits = reportVariableParseVO.getReportBaseVO().getChooseUnits();
        for (ReportBaseVO.UnitDim chooseUnit : chooseUnits) {
            String dimensionName = this.entityMetaService.getDimensionName(chooseUnit.getViewKey());
            ReportBaseVO.UnitDim unit = new ReportBaseVO.UnitDim();
            unit.setViewKey(chooseUnit.getViewKey());
            unit.setKey(chooseUnit.getCode());
            unit.setTitle(dimensionName);
            map.put(chooseUnit.getViewKey(), unit);
        }
        ReportBaseVO.PeriodDim period = reportVariableParseVO.getReportBaseVO().getPeriod();
        ReportBaseVO.UnitDim unit = new ReportBaseVO.UnitDim();
        unit.setKey(period.getCalendarCode());
        unit.setTitle(NrPeriodConst.DATETIME);
        unit.setViewKey(period.getViewKey());
        map.put(NrPeriodConst.DATETIME, unit);
        return map;
    }

    public ReportFormulaGroup.FormulaDim initDimByDataScheme(Set<String> dataSchemeKeys, Map<String, ReportBaseVO.UnitDim> commonDim) {
        ReportFormulaGroup.FormulaDim formulaDim = new ReportFormulaGroup.FormulaDim();
        DimensionValueSet dimValueSet = new DimensionValueSet();
        ArrayList<String> entityIds = new ArrayList<String>();
        if (dataSchemeKeys.size() == 1 && "FORMULA_NO_DS".equals(dataSchemeKeys.iterator().next())) {
            for (ReportBaseVO.UnitDim value : commonDim.values()) {
                dimValueSet.setValue(value.getTitle(), (Object)value.getCode());
                entityIds.add(value.getViewKey());
            }
        } else {
            this.fillDimsAndEntities(dataSchemeKeys, commonDim, dimValueSet, entityIds);
        }
        formulaDim.setDimensionValueSet(dimValueSet);
        formulaDim.setEntityIds(entityIds);
        return formulaDim;
    }

    private void fillDimsAndEntities(Set<String> dataSchemeKeys, Map<String, ReportBaseVO.UnitDim> commonDim, DimensionValueSet dimValueSet, List<String> entityIds) {
        boolean periodProcessed = false;
        HashSet<String> processedDimKeys = new HashSet<String>();
        for (String schemeKey : dataSchemeKeys) {
            List dimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(schemeKey);
            for (DataDimension dim : dimensions) {
                ReportBaseVO.UnitDim unitDim;
                String dimKey = dim.getDimKey();
                if (!processedDimKeys.add(dimKey)) continue;
                DimensionType type = dim.getDimensionType();
                if (type == DimensionType.PERIOD) {
                    if (periodProcessed) continue;
                    periodProcessed = true;
                    unitDim = commonDim.get(NrPeriodConst.DATETIME);
                    IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(dimKey);
                    dimValueSet.setValue(periodEntity.getDimensionName(), (Object)unitDim.getCode());
                } else if (commonDim.containsKey(dimKey)) {
                    unitDim = commonDim.get(dimKey);
                    dimValueSet.setValue(unitDim.getTitle(), (Object)unitDim.getCode());
                } else if (type == DimensionType.UNIT || type == DimensionType.UNIT_SCOPE) continue;
                entityIds.add(dimKey);
            }
        }
    }

    public Map<String, ReportFormulaGroup> dealElemets(List<Element> elements, ReportVariableParseVO parseVO) throws CloneNotSupportedException {
        Map<String, ReportBaseVO.UnitDim> commonDim = this.buildCommonDim(parseVO);
        ConcurrentHashMap dimMap = new ConcurrentHashMap();
        ConcurrentHashMap<String, ReportFormulaGroup> formulaMap = new ConcurrentHashMap<String, ReportFormulaGroup>();
        for (Element element : elements) {
            String formula = element.attr("var-expr");
            if (StringUtils.isEmpty((CharSequence)formula)) continue;
            Set<String> dataSchemeKeys = this.getFormulaDataSchemeKey(element, formula, parseVO.getExt());
            String dataSchemeGroupKey = dataSchemeKeys.size() == 1 ? dataSchemeKeys.iterator().next() : String.join((CharSequence)",", dataSchemeKeys);
            String formSchemeKey = element.attr("var-formscheme-key");
            String groupKey = dataSchemeGroupKey + formSchemeKey;
            ReportFormulaGroup reportFormulaGroup = formulaMap.computeIfAbsent(groupKey, k -> {
                ReportFormulaGroup group = new ReportFormulaGroup();
                if (StringUtils.isNotEmpty((CharSequence)formSchemeKey)) {
                    group.setFormSchemeKey(formSchemeKey);
                    ReportFormulaGroup.FormulaDim formulaDim = dimMap.computeIfAbsent(dataSchemeGroupKey, key -> this.initDimByDataScheme(dataSchemeKeys, commonDim));
                    group.setFormulaDim(formulaDim);
                }
                return group;
            });
            reportFormulaGroup.getElements().add(element);
            reportFormulaGroup.getFormulas().add(this.stripBrackets(formula));
        }
        return formulaMap;
    }

    private String stripBrackets(String formula) {
        if (formula.startsWith("[") && formula.endsWith("]")) {
            return formula.substring(1, formula.length() - 1);
        }
        return formula;
    }

    public Set<String> getFormulaDataSchemeKey(Element varElement, String varKey, Map<String, Object> varMap) {
        String dataschemeKey = varElement.attr("var-datascheme-key");
        if (StringUtils.isNotEmpty((CharSequence)dataschemeKey)) {
            HashSet<String> set = new HashSet<String>();
            set.add(dataschemeKey);
            return set;
        }
        Set dataSchemeKeys = this.entityService.getDataSchemeByFormula(varKey, varMap);
        if (dataSchemeKeys.size() == 0) {
            dataSchemeKeys.add("FORMULA_NO_DS");
        }
        return dataSchemeKeys;
    }
}

