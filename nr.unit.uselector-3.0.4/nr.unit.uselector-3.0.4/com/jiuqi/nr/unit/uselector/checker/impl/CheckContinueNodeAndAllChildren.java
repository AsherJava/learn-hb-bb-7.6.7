/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component(value="#check-continue-node-and-allChildren")
public class CheckContinueNodeAndAllChildren
implements IRowChecker {
    public static final String KEYWORD = "#check-continue-node-and-allChildren";

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return "\u540c\u7ea7\u8282\u70b9\uff08\u5305\u542b\u6240\u6709\u4e0b\u7ea7\uff09\u8303\u56f4\u9009\u4e2d";
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
        return this::checkContinueNodeAndAllChildren;
    }

    private List<String> checkContinueNodeAndAllChildren(IFilterCheckValues values, IUSelectorEntityRowProvider entityRowsProvider) {
        List<String> rangeNodeKeys;
        List<IEntityRow> rangeNodeAndChildrenRows;
        List<Map<String, String>> valueMaps;
        if (null != values && null != (valueMaps = values.getValues()) && null != (rangeNodeAndChildrenRows = entityRowsProvider.getContinueNodeAndAllChildren(rangeNodeKeys = valueMaps.stream().map(map -> (String)map.get("value")).collect(Collectors.toList()))) && !rangeNodeAndChildrenRows.isEmpty()) {
            return rangeNodeAndChildrenRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        }
        return new ArrayList<String>();
    }
}

