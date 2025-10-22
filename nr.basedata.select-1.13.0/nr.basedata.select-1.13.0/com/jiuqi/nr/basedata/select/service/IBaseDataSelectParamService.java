/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.basedata.select.service;

import com.jiuqi.nr.basedata.select.bean.BaseDataAttribute;
import com.jiuqi.nr.basedata.select.bean.BaseDataSelectFilterInfo;
import com.jiuqi.nr.basedata.select.exception.BaseDataException;
import com.jiuqi.nr.basedata.select.param.BaseDataQueryInfo;
import com.jiuqi.nr.basedata.select.service.IBaseDataSelectFilter;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.List;
import java.util.Map;

public interface IBaseDataSelectParamService {
    public IEntityTable buildEntityTable(BaseDataQueryInfo var1, boolean var2, boolean var3) throws BaseDataException;

    public List<String> getFilterFields(String var1, List<String> var2);

    public List<BaseDataAttribute> getBaseDataAttributes(String var1, List<String> var2);

    public Map<String, String> getReferEntityIdMap(String var1, List<String> var2);

    public List<IBaseDataSelectFilter> getEnableBaseDataFilterList(List<BaseDataSelectFilterInfo> var1);
}

