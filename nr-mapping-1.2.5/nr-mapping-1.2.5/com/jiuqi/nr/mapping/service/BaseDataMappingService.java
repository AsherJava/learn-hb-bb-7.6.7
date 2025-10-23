/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.nr.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nr.mapping.bean.BaseDataMapping;
import com.jiuqi.nr.mapping.web.vo.BaseDataVO;
import java.util.List;

public interface BaseDataMappingService {
    public List<BaseDataMapping> getBaseDataMapping(String var1);

    public List<BaseDataItemMapping> getBaseDataItemMapping(String var1, String var2);

    public List<BaseDataItemMapping> getBaseDataItemMappingByMSKey(String var1);

    public List<BaseDataVO> getBaseDataItem(String var1, String var2);

    public void saveBaseDataMapping(String var1, List<BaseDataMapping> var2);

    public void clearByMS(String var1);

    public void saveBaseDataItemMapping(String var1, String var2, List<BaseDataItemMapping> var3);

    public void clearByMSAndBaseData(String var1, String var2);
}

