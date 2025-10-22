/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.nr.dataentry.dao.TemplateConfigDao;
import com.jiuqi.nr.dataentry.gather.ExtendTemplateImpl;
import com.jiuqi.nr.dataentry.gather.IListGathers;
import com.jiuqi.nr.dataentry.gather.InfoDownTemplate;
import com.jiuqi.nr.dataentry.gather.MiniTemplateImpl;
import com.jiuqi.nr.dataentry.gather.SimpleTemplateImpl;
import com.jiuqi.nr.dataentry.gather.StandardTemplateImpl;
import com.jiuqi.nr.dataentry.gather.TemplateItem;
import com.jiuqi.nr.dataentry.util.Consts;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="dataentry_templates")
public class TemplatesGatherImp
implements IListGathers<TemplateItem> {
    @Autowired
    private TemplateConfigDao templateConfigDao;

    @Override
    public List<TemplateItem> gather() {
        ArrayList<TemplateItem> list = new ArrayList<TemplateItem>();
        StandardTemplateImpl standard = new StandardTemplateImpl();
        list.add(standard);
        SimpleTemplateImpl simple = new SimpleTemplateImpl();
        list.add(simple);
        MiniTemplateImpl mini = new MiniTemplateImpl();
        list.add(mini);
        InfoDownTemplate infoDown = new InfoDownTemplate();
        list.add(infoDown);
        List<ExtendTemplateImpl> extendTemplateImpls = this.templateConfigDao.getExtendTemplateImpls();
        if (extendTemplateImpls != null && extendTemplateImpls.size() > 0) {
            list.addAll(extendTemplateImpls);
        }
        return list;
    }

    @Override
    public Consts.GatherType getGatherType() {
        return Consts.GatherType.TEMPLATE;
    }
}

