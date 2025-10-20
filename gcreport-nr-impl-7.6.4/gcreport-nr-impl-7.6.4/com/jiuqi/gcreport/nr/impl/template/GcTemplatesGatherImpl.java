/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nr.dataentry.gather.IListGathers
 *  com.jiuqi.nr.dataentry.gather.TemplateItem
 *  com.jiuqi.nr.dataentry.util.Consts$GatherType
 */
package com.jiuqi.gcreport.nr.impl.template;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.nr.impl.template.AbstractGcTemplateItem;
import com.jiuqi.nr.dataentry.gather.IListGathers;
import com.jiuqi.nr.dataentry.gather.TemplateItem;
import com.jiuqi.nr.dataentry.util.Consts;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="gc_dataentry_templates")
public class GcTemplatesGatherImpl
implements IListGathers<TemplateItem> {
    public List<TemplateItem> gather() {
        Collection abstractGcTemplateItem = SpringContextUtils.getBeans(AbstractGcTemplateItem.class);
        ArrayList<TemplateItem> list = new ArrayList<TemplateItem>();
        abstractGcTemplateItem.forEach(list::add);
        return list;
    }

    public Consts.GatherType getGatherType() {
        return Consts.GatherType.TEMPLATE;
    }
}

