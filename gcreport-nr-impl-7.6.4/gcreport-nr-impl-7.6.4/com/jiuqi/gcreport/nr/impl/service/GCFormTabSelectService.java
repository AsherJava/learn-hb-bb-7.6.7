/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.nr.vo.FormTreeVo
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.gcreport.nr.impl.service;

import com.jiuqi.gcreport.nr.vo.FormTreeVo;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.List;
import java.util.function.Consumer;

public interface GCFormTabSelectService {
    public boolean isFormCondition(JtableContext var1, String var2, DimensionValueSet var3);

    public List<FormTreeVo> queryFormTree(String var1, String var2) throws Exception;

    public String queryFormData(String var1, Consumer<GridCellData> var2);

    public FormTree getFormTree(String var1, String var2) throws Exception;
}

