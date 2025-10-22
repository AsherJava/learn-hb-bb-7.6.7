/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.ctx.USelectorFilterExecuteContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CheckWithFormula
implements IRowChecker {
    public static final String KEYWORD = "#check-with-formula";
    @Resource
    private UnitSelectorI18nHelper i18nHelper;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return this.i18nHelper.getMessage(UnitSelectorI18nKeys.CHECK_WITH_FORMULA.i18nKey, "\u516c\u5f0f\u7b5b\u9009");
    }

    @Override
    public CheckerGroup[] getGroup() {
        return new CheckerGroup[]{CheckerGroup.OTHERS};
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public IRowCheckExecutor getExecutor(IUnitTreeContext ctx) {
        return (values, entityRowsProvider) -> {
            List<IEntityRow> filterRows;
            List formulas;
            List<Map<String, String>> valueMaps;
            if (null != values && null != (valueMaps = values.getValues()) && !(formulas = valueMaps.stream().map(map -> (String)map.get("value")).collect(Collectors.toList())).isEmpty() && null != (filterRows = entityRowsProvider.filterByFormulas(CheckWithFormula.getExecuteContext(this.runtimeDataSchemeService, this.entityMetaService, this.runTimeViewController, ctx, values), (String)formulas.get(0))) && !filterRows.isEmpty()) {
                return filterRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
            return new ArrayList();
        };
    }

    public static IUnitTreeContext getExecuteContext(IRuntimeDataSchemeService runtimeDataSchemeService, IEntityMetaService entityMetaService, IRunTimeViewController runTimeViewController, IUnitTreeContext ctx, IFilterCheckValues checkValues) {
        USelectorFilterExecuteContext context = new USelectorFilterExecuteContext(ctx);
        context.setDimValueSet(CheckWithFormula.getExecuteFormulaDims(runtimeDataSchemeService, entityMetaService, ctx));
        if (ctx.getEntityQueryPloy() == IEntityQueryPloy.MAIN_DIM_QUERY) {
            String formSchemeKey;
            FormSchemeDefine formScheme;
            if (ctx.getFormScheme() != null) {
                return context;
            }
            Map<String, String> runtimePara = checkValues.getRuntimePara();
            if (runtimePara.containsKey("formSchemeKey") && (formScheme = runTimeViewController.getFormScheme(formSchemeKey = runtimePara.get("formSchemeKey"))) != null) {
                context.setSchemeDefine(formScheme);
                return context;
            }
        }
        return context;
    }

    private static Map<String, DimensionValue> getExecuteFormulaDims(IRuntimeDataSchemeService runtimeDataSchemeService, IEntityMetaService entityMetaService, IUnitTreeContext ctx) {
        TaskDefine taskDefine = ctx.getTaskDefine();
        Map oriDimValueSet = ctx.getDimValueSet();
        if (taskDefine != null && oriDimValueSet != null && !oriDimValueSet.isEmpty()) {
            List dataSchemeDimension = runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
            for (DataDimension dimension : dataSchemeDimension) {
                if (!CheckWithFormula.isCorporate(runtimeDataSchemeService, entityMetaService, taskDefine, dimension)) continue;
                IEntityDefine entityDefine = entityMetaService.queryEntity(dimension.getDimKey());
                HashMap<String, DimensionValue> executeFormulaDims = new HashMap<String, DimensionValue>();
                for (Map.Entry entry : oriDimValueSet.entrySet()) {
                    if (entityDefine.getDimensionName().equalsIgnoreCase((String)entry.getKey())) continue;
                    executeFormulaDims.put((String)entry.getKey(), (DimensionValue)entry.getValue());
                }
                return executeFormulaDims;
            }
        }
        return ctx.getDimValueSet();
    }

    public static boolean isCorporate(IRuntimeDataSchemeService runtimeDataSchemeService, IEntityMetaService entityMetaService, TaskDefine taskDefine, DataDimension dimension) {
        String dimAttribute = dimension.getDimAttribute();
        IEntityModel dwEntityModel = entityMetaService.getEntityModel(taskDefine.getDw());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        String dimReferAttr = CheckWithFormula.getDimAttributeByReportDim(runtimeDataSchemeService, taskDefine, dimension.getDimKey());
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }

    private static String getDimAttributeByReportDim(IRuntimeDataSchemeService runtimeDataSchemeService, TaskDefine taskDefine, String dimKey) {
        String dataSchemeKey = taskDefine.getDataScheme();
        if (dataSchemeKey == null) {
            return null;
        }
        List dimensions = runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        DataDimension report = dimensions.stream().filter(dataDimension -> dimKey.equals(dataDimension.getDimKey())).findFirst().orElse(null);
        return report == null ? null : report.getDimAttribute();
    }
}

