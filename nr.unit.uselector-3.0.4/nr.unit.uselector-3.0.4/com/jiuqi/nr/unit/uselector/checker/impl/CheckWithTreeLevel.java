/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.filter.LevelsEntityFilter
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.filter.LevelsEntityFilter;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.DisplayType;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CheckWithTreeLevel
implements IRowChecker {
    public static final String KEYWORD = "#check-with-tree-level";
    @Resource
    private UnitSelectorI18nHelper i18nHelper;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return this.i18nHelper.getMessage(UnitSelectorI18nKeys.CHECK_WITH_TREE_LEVEL.i18nKey, "\u7ea7\u6b21\u9009\u62e9");
    }

    @Override
    public CheckerGroup[] getGroup() {
        return new CheckerGroup[]{CheckerGroup.FILTER_SCHEME};
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.INPUT_NUMBER;
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public boolean isDisplay(IUnitTreeContext ctx) {
        return this.entityDataQuery.isTreeData(ctx);
    }

    @Override
    public IRowCheckExecutor getExecutor(IUnitTreeContext ctx) {
        return (values, entityRowsProvider) -> {
            List<Map<String, String>> checkValue = values.getValues();
            if (null != checkValue && !checkValue.isEmpty()) {
                List collect;
                LevelsEntityFilter filter;
                List levelRows;
                ArrayList<Integer> levels = new ArrayList<Integer>();
                for (Map<String, String> v : checkValue) {
                    int lv = Integer.parseInt(v.get("value"));
                    if (lv <= -1) continue;
                    levels.add(lv);
                }
                if (!levels.isEmpty() && null != (levelRows = (filter = new LevelsEntityFilter(ctx, (IUnitTreeEntityDataQuery)this.entityDataQuery)).getMatchResultSet(collect = levels.stream().map(Object::toString).collect(Collectors.toList())))) {
                    return levelRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                }
            }
            return new ArrayList();
        };
    }
}

