/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.dto.IFormTreeNode
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.print.service;

import com.jiuqi.nr.print.web.vo.PrintTemTreeNodeVO;
import com.jiuqi.nr.print.web.vo.SearchParamVO;
import com.jiuqi.nr.print.web.vo.SearchResultVO;
import com.jiuqi.nr.task.api.dto.IFormTreeNode;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.List;

public interface IPrintResourceTreeService {
    public List<UITreeNode<IFormTreeNode>> resourceTreeLoad(String var1);

    public List<UITreeNode<IFormTreeNode>> formTreeLoad(String var1);

    public List<UITreeNode<IFormTreeNode>> treeLocated(String var1, String var2, Boolean var3);

    public List<SearchResultVO> search(SearchParamVO var1);

    public List<SearchResultVO> listPrintScheme(String var1);

    public List<UITreeNode<PrintTemTreeNodeVO>> getFullPrintTemplateTree(String var1, String var2, String var3);
}

