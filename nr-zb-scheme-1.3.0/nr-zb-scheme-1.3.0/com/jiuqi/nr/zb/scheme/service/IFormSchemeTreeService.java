/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.nr.zb.scheme.internal.tree.FormSchemeTreeNode;
import com.jiuqi.nr.zb.scheme.web.vo.FormSchemeTreeSearchItem;
import java.util.List;

public interface IFormSchemeTreeService {
    public List<FormSchemeTreeNode> getRoot(String var1);

    public List<FormSchemeTreeNode> getChildren(String var1);

    public List<FormSchemeTreeSearchItem> search(String var1, String var2);
}

