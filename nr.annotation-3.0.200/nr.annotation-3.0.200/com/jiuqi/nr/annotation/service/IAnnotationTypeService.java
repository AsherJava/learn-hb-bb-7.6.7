/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.basedata.select.bean.BaseDataInfo
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.annotation.service;

import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.Map;
import java.util.Set;

public interface IAnnotationTypeService {
    public boolean isOpenAnnotationType();

    public boolean onlyLeafNode();

    public String getTypeBaseDataKey();

    public ITree<BaseDataInfo> queryTypeBaseData(DimensionCombination var1, String var2, String var3);

    public ITree<BaseDataInfo> positioningTypebaseData(DimensionCombination var1, String var2, String var3);

    public ITree<BaseDataInfo> searchTypeBaseData(DimensionCombination var1, String var2, String var3);

    public Map<String, IEntityRow> queryTypeEntityRow(DimensionCombination var1, String var2, Set<String> var3);
}

