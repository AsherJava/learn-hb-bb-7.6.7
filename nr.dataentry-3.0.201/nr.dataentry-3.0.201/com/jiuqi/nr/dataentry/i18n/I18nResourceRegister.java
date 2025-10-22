/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.dataentry.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.gather.InfoViewItem;
import com.jiuqi.nr.dataentry.service.IPlugInService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class I18nResourceRegister
implements I18NResource {
    private static final long serialVersionUID = 4377339115119435178L;

    public String name() {
        return "\u65b0\u62a5\u8868/\u6570\u636e\u5f55\u5165/\u5de5\u5177\u6761\u6309\u94ae\u6536\u96c6";
    }

    public String getNameSpace() {
        return "nr";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        IPlugInService plugInService = (IPlugInService)BeanUtil.getBean(IPlugInService.class);
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            Tree<ActionItem> tree = plugInService.getActions();
            List list = tree.getChildren();
            for (int i = 0; i < list.size(); ++i) {
                List children = ((Tree)list.get(i)).getChildren();
                for (Tree child : children) {
                    resourceObjects.add(new I18NResourceItem(((ActionItem)child.getData()).getCode(), ((ActionItem)child.getData()).getTitle()));
                    if (!child.hasChildren()) continue;
                    List children2 = child.getChildren();
                    for (Tree child2 : children2) {
                        resourceObjects.add(new I18NResourceItem(((ActionItem)child2.getData()).getCode(), ((ActionItem)child2.getData()).getTitle()));
                    }
                }
            }
            List<InfoViewItem> infoViews = plugInService.getInfoViews();
            for (InfoViewItem item : infoViews) {
                resourceObjects.add(new I18NResourceItem(item.getCode(), item.getTitle()));
            }
        }
        return resourceObjects;
    }
}

