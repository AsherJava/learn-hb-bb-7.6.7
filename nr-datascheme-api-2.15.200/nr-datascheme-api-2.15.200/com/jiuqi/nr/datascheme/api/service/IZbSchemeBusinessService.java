/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.zb.scheme.internal.tree.INode
 *  com.jiuqi.nr.zb.scheme.internal.tree.ITree
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.DataSchemeCalResult;
import com.jiuqi.nr.datascheme.api.service.DataFieldView;
import com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;
import com.jiuqi.nr.zb.scheme.internal.tree.ITree;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.List;

public interface IZbSchemeBusinessService {
    public List<DesignDataScheme> getSyncDataScheme(String var1, String var2);

    public void referZbInfo(String var1, List<String> var2);

    public void pullZbInfo(List<String> var1);

    public List<ITree<INode>> queryZbGroupTree(String var1, String var2);

    public List<ITree<INode>> filterZbInfo(String var1, String var2);

    public String executeCalFormula(DataSchemeCalcTask var1);

    public ProgressItem queryCalZbProgress(String var1);

    public boolean checkZbRefer(String var1, String var2);

    public DataSchemeCalResult queryCalResult(String var1);

    default public List<DataFieldView> getDataFieldView(List<String> keys) {
        return this.getDataFieldView(keys, null);
    }

    public List<DataFieldView> getDataFieldView(List<String> var1, String var2);
}

