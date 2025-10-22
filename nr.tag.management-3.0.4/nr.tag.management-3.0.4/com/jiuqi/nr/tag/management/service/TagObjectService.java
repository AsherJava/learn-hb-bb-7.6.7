/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.service;

import com.jiuqi.nr.tag.management.intf.ITagFacade;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import com.jiuqi.nr.tag.management.intf.ITagQueryTemplate;
import com.jiuqi.nr.tag.management.intf.NRTagQueryTemplateProcessor;
import java.util.List;
import java.util.Map;

public class TagObjectService
implements ITagQueryTemplate {
    private final NRTagQueryTemplateProcessor processor;

    public TagObjectService(NRTagQueryTemplateProcessor processor) {
        this.processor = processor;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public List<ITagFacade> getInfoTags(ITagQueryContext context) {
        ITagQueryTemplate template = this.processor.getDefaultQueryTemplate();
        return template.getInfoTags(context);
    }

    @Override
    public List<ITagFacade> getInfoTags(ITagQueryContext context, List<String> tagKeys) {
        ITagQueryTemplate template = this.processor.getDefaultQueryTemplate();
        return template.getInfoTags(context, tagKeys);
    }

    @Override
    public Map<String, List<String>> tagCountUnits(ITagQueryContext context, List<String> tagKeys) {
        ITagQueryTemplate template = this.processor.getDefaultQueryTemplate();
        return template.tagCountUnits(context, tagKeys);
    }

    @Override
    public Map<String, List<String>> unitCountTags(ITagQueryContext context, List<String> entDataKeys) {
        ITagQueryTemplate template = this.processor.getDefaultQueryTemplate();
        return template.unitCountTags(context, entDataKeys);
    }
}

