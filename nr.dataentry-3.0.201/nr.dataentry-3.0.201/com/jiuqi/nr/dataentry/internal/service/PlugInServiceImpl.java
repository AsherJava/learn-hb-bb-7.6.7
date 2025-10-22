/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.np.util.tree.Tree
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.ActionItemImpl;
import com.jiuqi.nr.dataentry.gather.IGathers;
import com.jiuqi.nr.dataentry.gather.IListGathers;
import com.jiuqi.nr.dataentry.gather.ISingletonGather;
import com.jiuqi.nr.dataentry.gather.ITreeGathers;
import com.jiuqi.nr.dataentry.gather.InfoViewItem;
import com.jiuqi.nr.dataentry.gather.SlotItem;
import com.jiuqi.nr.dataentry.gather.TemplateItem;
import com.jiuqi.nr.dataentry.service.IPlugInService;
import com.jiuqi.nr.dataentry.util.Consts;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={NpRollbackException.class})
@Service(value="plugInServiceImpl")
public class PlugInServiceImpl
implements IPlugInService {
    @Autowired
    private List<IGathers> gathers;

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public Tree<ActionItem> getActions() {
        Tree tree = new Tree((Object)new ActionItemImpl("rootNode", "\u6839\u8282\u70b9", "\u6839\u8282\u70b9"));
        for (IGathers gather : this.gathers) {
            if (!gather.getGatherType().getCode().equals(Consts.GatherType.ACTION.getCode())) continue;
            Tree treeItem = ((ITreeGathers)gather).gather();
            tree.addChild(treeItem);
        }
        return tree;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<TemplateItem> getTemplates() {
        ArrayList<TemplateItem> result = new ArrayList<TemplateItem>();
        for (IGathers gather : this.gathers) {
            if (!gather.getGatherType().getCode().equals(Consts.GatherType.TEMPLATE.getCode())) continue;
            result.addAll(((IListGathers)gather).gather());
        }
        return result;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public TemplateItem getTemplateByCode(String code) {
        TemplateItem result = null;
        List<TemplateItem> templates = this.getTemplates();
        for (TemplateItem templateItem : templates) {
            if (!templateItem.getCode().equals(code)) continue;
            result = templateItem;
            break;
        }
        return result;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<InfoViewItem> getInfoViews() {
        ArrayList<InfoViewItem> result = new ArrayList<InfoViewItem>();
        for (IGathers gather : this.gathers) {
            if (!gather.getGatherType().getCode().equals(Consts.GatherType.INFOVIEW.getCode())) continue;
            result.addAll(((IListGathers)gather).gather());
        }
        return result;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<SlotItem> getSlots() {
        ArrayList<SlotItem> result = new ArrayList<SlotItem>();
        if (null != this.gathers) {
            for (IGathers gather : this.gathers) {
                if (!gather.getGatherType().getCode().equals(Consts.GatherType.SLOT.getCode())) continue;
                result.addAll(((IListGathers)gather).gather());
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public String getDefaultTemplate() {
        String defaultTemplate = "";
        for (IGathers gather : this.gathers) {
            if (!gather.getGatherType().getCode().equals(Consts.GatherType.DEFAULTTEMPLATE.getCode())) continue;
            defaultTemplate = (String)((ISingletonGather)gather).gather();
            return defaultTemplate;
        }
        return defaultTemplate;
    }
}

