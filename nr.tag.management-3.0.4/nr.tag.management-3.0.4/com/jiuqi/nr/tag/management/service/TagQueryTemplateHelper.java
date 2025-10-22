/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.management.service;

import com.jiuqi.nr.tag.management.intf.ITagQueryTemplate;
import com.jiuqi.nr.tag.management.intf.NRTagQueryTemplateProcessor;
import com.jiuqi.nr.tag.management.service.ITagQueryTemplateHelper;
import com.jiuqi.nr.tag.management.service.TagObjectService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TagQueryTemplateHelper
implements ITagQueryTemplateHelper {
    @Resource
    private NRTagQueryTemplateProcessor processor;

    @Override
    public ITagQueryTemplate getQueryTemplate() {
        return new TagObjectService(this.processor);
    }
}

