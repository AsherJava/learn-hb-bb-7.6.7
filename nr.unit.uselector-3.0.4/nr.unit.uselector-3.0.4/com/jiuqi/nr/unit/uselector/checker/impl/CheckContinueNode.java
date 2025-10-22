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

@Component(value="#check-continue-node")
public class CheckContinueNode
implements IRowChecker {
    public static final String KEYWORD = "#check-continue-node";

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return "\u540c\u7ea7\u8282\u70b9\u8303\u56f4\u9009\u4e2d";
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
        return this::checkContinueNode;
    }

    private List<String> checkContinueNode(IFilterCheckValues values, IUSelectorEntityRowProvider entityRowsProvider) {
        List<String> rangeNodeKeys;
        List<IEntityRow> rangeNodeRows;
        List<Map<String, String>> valueMaps;
        if (null != values && null != (valueMaps = values.getValues()) && null != (rangeNodeRows = entityRowsProvider.getContinueNode(rangeNodeKeys = valueMaps.stream().map(map -> (String)map.get("value")).collect(Collectors.toList()))) && !rangeNodeRows.isEmpty()) {
            return rangeNodeRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        }
        return new ArrayList<String>();
    }
}

