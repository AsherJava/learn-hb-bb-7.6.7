/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.tag.management.environment.TagQueryContextData
 *  com.jiuqi.nr.tag.management.intf.ITagFacade
 *  com.jiuqi.nr.tag.management.intf.ITagQueryContext
 *  com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper;
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
public class CheckWithNodeTags
implements IRowChecker {
    public static final String KEYWORD = "#check-with-node-tags";
    @Resource
    private ITagQueryTemplateHelper tagQueryTemplateHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitSelectorI18nHelper i18nHelper;

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return this.i18nHelper.getMessage(UnitSelectorI18nKeys.CHECK_WITH_NODE_TAGS.i18nKey, "\u5e38\u7528\u5355\u4f4d");
    }

    @Override
    public CheckerGroup[] getGroup() {
        return new CheckerGroup[]{CheckerGroup.QUICK_SELECTION, CheckerGroup.FILTER_SCHEME};
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.RADIO;
    }

    @Override
    public int getOrder() {
        return 30;
    }

    @Override
    public boolean isDisplay(IUnitTreeContext ctx) {
        List tags = this.tagQueryTemplateHelper.getQueryTemplate().getInfoTags(this.getITagQueryContext(ctx));
        return tags != null && !tags.isEmpty();
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public IRowCheckExecutor getExecutor(final IUnitTreeContext ctx) {
        return new IRowCheckExecutor(){

            @Override
            public IFilterCheckValues getValues() {
                IFilterCheckValuesImpl values = new IFilterCheckValuesImpl();
                if (ctx.getEntityDefine() == null) {
                    return values;
                }
                List tags = CheckWithNodeTags.this.tagQueryTemplateHelper.getQueryTemplate().getInfoTags(CheckWithNodeTags.this.getITagQueryContext(ctx));
                if (null != tags) {
                    for (ITagFacade tag : tags) {
                        HashMap<String, String> v = new HashMap<String, String>();
                        v.put("value", tag.getKey());
                        v.put("text", tag.getTitle());
                        values.getValues().add(v);
                    }
                }
                return values;
            }

            @Override
            public List<String> executeCheck(IFilterCheckValues values, IUSelectorEntityRowProvider entityRowsProvider) {
                List<Map<String, String>> checkValue;
                ArrayList<String> resultSet = new ArrayList<String>();
                if (null != values && null != (checkValue = values.getValues())) {
                    ArrayList tagKeys = new ArrayList();
                    checkValue.forEach(v -> tagKeys.add(v.get("value")));
                    Map tagEntMap = CheckWithNodeTags.this.tagQueryTemplateHelper.getQueryTemplate().tagCountUnits(CheckWithNodeTags.this.getITagQueryContext(ctx), tagKeys);
                    for (Map.Entry entry : tagEntMap.entrySet()) {
                        List<IEntityRow> checkRows = entityRowsProvider.getCheckRows((List)entry.getValue());
                        if (checkRows == null || checkRows.isEmpty()) continue;
                        resultSet.addAll(checkRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
                    }
                }
                return resultSet;
            }
        };
    }

    public ITagQueryContext getITagQueryContext(IUnitTreeContext ctx) {
        TagQueryContextData tagQueryContextData = new TagQueryContextData();
        if (ctx.getFormScheme() != null) {
            tagQueryContextData.setFormScheme(ctx.getFormScheme().getKey());
        }
        tagQueryContextData.setPeriod(ctx.getPeriod());
        tagQueryContextData.setEntityId(ctx.getEntityDefine().getId());
        tagQueryContextData.setDimValueSet(ctx.getDimValueSet());
        tagQueryContextData.setCustomVariable(ctx.getCustomVariable());
        return tagQueryContextData;
    }
}

