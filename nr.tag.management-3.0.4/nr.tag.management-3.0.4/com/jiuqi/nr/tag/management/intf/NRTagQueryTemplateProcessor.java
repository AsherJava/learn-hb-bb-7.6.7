/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.intf;

import com.jiuqi.nr.tag.management.intf.ITagQueryTemplate;
import com.jiuqi.nr.tag.management.intf.NRTagQueryTemplate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NRTagQueryTemplateProcessor {
    private final Map<String, ITagQueryTemplate> tagQueryTemplateMap = new HashMap<String, ITagQueryTemplate>();
    private final List<ITagQueryTemplate> tagQueryTemplates = new ArrayList<ITagQueryTemplate>();

    public NRTagQueryTemplateProcessor(List<ITagQueryTemplate> tagQueryTemplates) {
        for (ITagQueryTemplate template : tagQueryTemplates) {
            NRTagQueryTemplate annotation = template.getClass().getAnnotation(NRTagQueryTemplate.class);
            if (annotation == null) continue;
            this.tagQueryTemplateMap.put(annotation.value(), template);
            this.tagQueryTemplates.add(template);
        }
        this.tagQueryTemplates.sort(Comparator.comparingInt(ITagQueryTemplate::getOrder));
    }

    public ITagQueryTemplate getTagQueryTemplate(String tempId) {
        return this.tagQueryTemplateMap.get(tempId);
    }

    public ITagQueryTemplate getDefaultQueryTemplate() {
        return this.tagQueryTemplates.isEmpty() ? null : this.tagQueryTemplates.get(this.tagQueryTemplates.size() - 1);
    }
}

