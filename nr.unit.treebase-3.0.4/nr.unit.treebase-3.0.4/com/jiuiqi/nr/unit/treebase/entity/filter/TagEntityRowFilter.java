/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.tag.management.environment.TagQueryContextData
 *  com.jiuqi.nr.tag.management.intf.ITagQueryContext
 *  com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper
 */
package com.jiuiqi.nr.unit.treebase.entity.filter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.filter.IFilterEntityRow;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper;
import java.util.List;
import java.util.Map;

public class TagEntityRowFilter
implements IFilterEntityRow {
    private ITagQueryTemplateHelper tagQueryTemplateHelper;
    private List<String> tags;
    protected IUnitTreeContext context;
    protected IUnitTreeEntityDataQuery entityDataQuery;
    protected Map<String, List<String>> tags2NodeMap;

    public TagEntityRowFilter(IUnitTreeContext context, IUnitTreeEntityDataQuery entityDataQuery, List<String> tags) {
        this.tags = tags;
        this.context = context;
        this.entityDataQuery = entityDataQuery;
        this.tagQueryTemplateHelper = (ITagQueryTemplateHelper)SpringBeanUtils.getBean(ITagQueryTemplateHelper.class);
        this.tags2NodeMap = this.initFilter(context, tags);
    }

    @Override
    public boolean matchRow(IEntityRow row) {
        boolean checked = false;
        for (String tagKey : this.tags) {
            List<String> entKeys = this.tags2NodeMap.get(tagKey);
            if (!entKeys.contains(row.getEntityKeyData())) continue;
            checked = true;
            break;
        }
        return checked;
    }

    @Override
    public void setMatchRangeRows(List<IEntityRow> rangeRows) {
    }

    @Override
    public List<IEntityRow> getMatchResultSet(List<String> tags) {
        return null;
    }

    public List<IEntityRow> filterEntityRows(List<IEntityRow> oriRows, List<String> tags) {
        return null;
    }

    private Map<String, List<String>> initFilter(IUnitTreeContext context, List<String> tags) {
        TagQueryContextData tagQueryContextData = new TagQueryContextData();
        tagQueryContextData.setFormScheme(context.getFormScheme().getKey());
        tagQueryContextData.setPeriod(context.getPeriod());
        tagQueryContextData.setEntityId(context.getEntityDefine().getId());
        tagQueryContextData.setDimValueSet(context.getDimValueSet());
        tagQueryContextData.setCustomVariable(context.getCustomVariable());
        return this.tagQueryTemplateHelper.getQueryTemplate().tagCountUnits((ITagQueryContext)tagQueryContextData, tags);
    }
}

