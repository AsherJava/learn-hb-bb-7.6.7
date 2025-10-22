/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CheckAllParents
implements IRowChecker {
    public static final String KEYWORD = "#check-all-parents";
    @Resource
    private UnitSelectorI18nHelper i18nHelper;

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return this.i18nHelper.getMessage(UnitSelectorI18nKeys.CHECK_ALL_PARENTS.i18nKey, "\u9009\u62e9\u6240\u6709\u4e0a\u7ea7");
    }

    @Override
    public CheckerGroup[] getGroup() {
        return new CheckerGroup[]{CheckerGroup.CONTEXT_MENU};
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public int getOrder() {
        return 30;
    }

    @Override
    public IRowCheckExecutor getExecutor(IUnitTreeContext ctx) {
        return (values, entityRowsProvider) -> {
            List<String> checkValues;
            List<IEntityRow> parentRows;
            List<Map<String, String>> valueMaps;
            if (null != values && null != (valueMaps = values.getValues()) && null != (parentRows = entityRowsProvider.getAllParentRows(checkValues = valueMaps.stream().map(map -> (String)map.get("value")).collect(Collectors.toList()))) && !parentRows.isEmpty()) {
                return parentRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
            return new ArrayList();
        };
    }
}

