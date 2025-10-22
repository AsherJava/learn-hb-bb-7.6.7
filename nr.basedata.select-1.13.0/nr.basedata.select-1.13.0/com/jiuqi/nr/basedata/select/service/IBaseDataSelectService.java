/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.basedata.select.service;

import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.basedata.select.param.BaseDataOpenResponse;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfoExtend;
import com.jiuqi.nr.basedata.select.param.BaseDataResponse;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;
import java.util.Map;

public interface IBaseDataSelectService {
    public BaseDataResponse getBaseDataTree(BaseDataQueryInfo var1);

    public Map<String, Map<String, String>> getBaseDataAttributeVale(BaseDataQueryInfoExtend var1);

    public List<ITree<BaseDataInfo>> getBaseDataEntry(BaseDataQueryInfoExtend var1);

    public Map<String, List<String>> getFullPathAttributeVale(BaseDataQueryInfoExtend var1);

    public BaseDataOpenResponse getBaseDataOpenParam(BaseDataQueryInfo var1);
}

