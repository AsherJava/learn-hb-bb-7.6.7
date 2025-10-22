/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.filter.BBLXEntityRowFilter
 *  com.jiuiqi.nr.unit.treebase.entity.query.BBLXNodeDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.filter.BBLXEntityRowFilter;
import com.jiuiqi.nr.unit.treebase.entity.query.BBLXNodeDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.DisplayType;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CheckWithBBLX
implements IRowChecker {
    public static final String KEYWORD = "#check-with-bblx";
    @Resource
    private UnitSelectorI18nHelper i18nHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return this.i18nHelper.getMessage(UnitSelectorI18nKeys.CHECK_WITH_BBLX.i18nKey, "\u62a5\u8868\u7c7b\u578b");
    }

    @Override
    public CheckerGroup[] getGroup() {
        return new CheckerGroup[]{CheckerGroup.QUICK_SELECTION, CheckerGroup.FILTER_SCHEME};
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.CHECKBOX;
    }

    @Override
    public boolean isDisplay(IUnitTreeContext ctx) {
        return this.contextWrapper.getBBLXEntityRefer(ctx.getEntityDefine()) != null;
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public int getOrder() {
        return 20;
    }

    @Override
    public IRowCheckExecutor getExecutor(final IUnitTreeContext ctx) {
        return new IRowCheckExecutor(){

            @Override
            public IFilterCheckValues getValues() {
                IFilterCheckValuesImpl values = new IFilterCheckValuesImpl();
                BBLXNodeDataQuery dataTable = new BBLXNodeDataQuery(ctx);
                List bblxRows = dataTable.getAllBBLXRows();
                if (null != bblxRows) {
                    for (IEntityRow row : bblxRows) {
                        HashMap<String, String> v = new HashMap<String, String>();
                        v.put("value", row.getCode());
                        v.put("text", row.getCode() + " | " + row.getTitle());
                        values.getValues().add(v);
                    }
                }
                return values;
            }

            @Override
            public List<String> executeCheck(IFilterCheckValues values, IUSelectorEntityRowProvider entityRowsProvider) {
                List<Map<String, String>> valueMaps;
                if (null != values && null != (valueMaps = values.getValues()) && !valueMaps.isEmpty()) {
                    List codesOfBBLX = valueMaps.stream().map(map -> (String)map.get("value")).collect(Collectors.toList());
                    BBLXEntityRowFilter filter = new BBLXEntityRowFilter(ctx, (IUnitTreeEntityDataQuery)CheckWithBBLX.this.entityDataQuery, codesOfBBLX);
                    List matchRows = filter.getMatchResultSet(codesOfBBLX);
                    if (null != matchRows && !matchRows.isEmpty()) {
                        return matchRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                    }
                }
                return new ArrayList<String>();
            }
        };
    }
}

