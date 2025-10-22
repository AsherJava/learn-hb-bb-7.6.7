/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.query.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.query.gather.ActionItem;
import com.jiuqi.nr.query.service.IQueryPlugInService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryI18nResourceRegister
implements I18NResource {
    private static final long serialVersionUID = 4377339115119435178L;
    @Autowired
    private IQueryPlugInService plugInService;

    public String name() {
        return "\u65b0\u62a5\u8868/\u67e5\u8be2\u6a21\u5757/\u5de5\u5177\u6761\u6309\u94ae\u6536\u96c6";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || "".equals(parentId)) {
            Tree<ActionItem> tree = this.plugInService.getActions();
            for (int i = 0; i < tree.getChildren().size(); ++i) {
                List children = ((Tree)tree.getChildren().get(i)).getChildren();
                for (Tree child : children) {
                    resourceObjects.add(new I18NResourceItem(((ActionItem)child.getData()).getCode(), ((ActionItem)child.getData()).getTitle()));
                    if (!child.hasChildren()) continue;
                    List children2 = child.getChildren();
                    for (Tree child2 : children2) {
                        resourceObjects.add(new I18NResourceItem(((ActionItem)child2.getData()).getCode(), ((ActionItem)child2.getData()).getTitle()));
                    }
                }
            }
        }
        return resourceObjects;
    }
}

