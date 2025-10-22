/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.service;

import com.jiuqi.nr.tag.management.environment.TagCountContextData;
import com.jiuqi.nr.tag.management.environment.TagQueryContextData;
import com.jiuqi.nr.tag.management.intf.ITagCountDataSet;
import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.intf.ITagQueryTemplate;
import com.jiuqi.nr.tag.management.intf.NRTagQueryTemplateProcessor;
import com.jiuqi.nr.tag.management.intf.TagCountDataSet;
import com.jiuqi.nr.tag.management.service.ITagManagementQueryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagManagementQueryService
implements ITagManagementQueryService {
    private final NRTagQueryTemplateProcessor processor;

    @Autowired
    public TagManagementQueryService(NRTagQueryTemplateProcessor processor) {
        this.processor = processor;
    }

    @Override
    public List<ITagFacade> queryAllTags(TagQueryContextData context) {
        ITagQueryTemplate queryTemplate = this.processor.getDefaultQueryTemplate();
        if (queryTemplate != null) {
            return queryTemplate.getInfoTags(context);
        }
        return null;
    }

    @Override
    public ITagCountDataSet tagCountUnits(TagCountContextData context) {
        TagCountDataSet dataSet = new TagCountDataSet();
        ITagQueryTemplate queryTemplate = this.processor.getDefaultQueryTemplate();
        if (queryTemplate != null) {
            dataSet.setTags(queryTemplate.getInfoTags(context, context.getDataKeys()));
            dataSet.setDataSet(queryTemplate.tagCountUnits(context, context.getDataKeys()));
        }
        return dataSet;
    }

    @Override
    public ITagCountDataSet unitCountTags(TagCountContextData context) {
        TagCountDataSet dataSet = new TagCountDataSet();
        ITagQueryTemplate queryTemplate = this.processor.getDefaultQueryTemplate();
        if (queryTemplate != null) {
            dataSet.setTags(queryTemplate.getInfoTags(context));
            dataSet.setDataSet(queryTemplate.unitCountTags(context, context.getDataKeys()));
        }
        return dataSet;
    }
}

