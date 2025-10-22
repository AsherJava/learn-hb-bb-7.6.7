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

@Component(value="selector-check-nodes-filter-v2")
public class CheckNodes
implements IRowChecker {
    @Resource
    private UnitSelectorI18nHelper i18nHelper;

    @Override
    public String getKeyword() {
        return "#check-nodes";
    }

    @Override
    public String getShowText() {
        return this.i18nHelper.getMessage(UnitSelectorI18nKeys.CHECK_NODES.i18nKey, "\u52fe\u9009\u8282\u70b9");
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
            List<String> checkValues;
            List<IEntityRow> checkRows;
            List<Map<String, String>> valueMaps;
            if (null != values && null != (valueMaps = values.getValues()) && null != (checkRows = entityRowsProvider.getCheckRows(checkValues = valueMaps.stream().map(map -> (String)map.get("value")).collect(Collectors.toList()))) && !checkRows.isEmpty()) {
                return checkRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            }
            return new ArrayList();
        };
    }
}

