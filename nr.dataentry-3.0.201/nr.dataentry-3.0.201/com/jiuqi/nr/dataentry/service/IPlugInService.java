/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.InfoViewItem;
import com.jiuqi.nr.dataentry.gather.SlotItem;
import com.jiuqi.nr.dataentry.gather.TemplateItem;
import java.util.List;

public interface IPlugInService {
    public Tree<ActionItem> getActions();

    public List<TemplateItem> getTemplates();

    public TemplateItem getTemplateByCode(String var1);

    public List<InfoViewItem> getInfoViews();

    public List<SlotItem> getSlots();

    public String getDefaultTemplate();

    default public String getDefaultTemplateCode() {
        return "standardTemplate";
    }
}

