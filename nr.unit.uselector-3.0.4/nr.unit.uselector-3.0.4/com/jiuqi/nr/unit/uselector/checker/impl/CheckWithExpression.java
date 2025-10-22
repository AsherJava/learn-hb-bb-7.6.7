/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.DisplayType;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.impl.CheckWithFormula;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CheckWithExpression
implements IRowChecker {
    public static final String KEYWORD = "#check-with-expression";
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
        return this.i18nHelper.getMessage(UnitSelectorI18nKeys.CHECK_WITH_EXPRESSION.i18nKey, "\u8868\u8fbe\u5f0f\u7b5b\u9009");
    }

    @Override
    public CheckerGroup[] getGroup() {
        return new CheckerGroup[]{CheckerGroup.OTHERS, CheckerGroup.FILTER_SCHEME};
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.INPUT;
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public IRowCheckExecutor getExecutor(IUnitTreeContext ctx) {
        return (values, entityRowsProvider) -> {
            List<IEntityRow> filterRows;
            List expressions;
            List<Map<String, String>> valueMaps;
            if (null != values && null != (valueMaps = values.getValues()) && !(expressions = valueMaps.stream().map(map -> (String)map.get("value")).collect(Collectors.toList())).isEmpty() && null != (filterRows = entityRowsProvider.filterByFormulas(CheckWithFormula.getExecuteContext(this.runtimeDataSchemeService, this.entityMetaService, this.runTimeViewController, ctx, values), (String)expressions.get(0))) && !filterRows.isEmpty()) {
                return filterRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
            return new ArrayList();
        };
    }
}

