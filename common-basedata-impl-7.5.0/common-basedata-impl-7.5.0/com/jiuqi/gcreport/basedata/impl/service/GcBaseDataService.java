/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 */
package com.jiuqi.gcreport.basedata.impl.service;

import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDTO;
import java.util.List;

public interface GcBaseDataService {
    public List<GcBaseData> listBaseDataObj(GcBaseDataDTO var1);

    public List<BaseDataVO> treeBaseDataObj(String var1);

    public List<GcBaseData> queryBasedataItems(String var1);

    public List<GcBaseData> queryBasedataItems(String var1, AuthType var2);

    public List<GcBaseData> queryBasedataItemsBySearch(String var1, String var2);

    public List<GcBaseData> queryBasedataItemsBySearch(String var1, String var2, AuthType var3);

    public List<GcBaseData> queryRootBasedataItems(String var1);

    public List<GcBaseData> queryAllBasedataItemsByParentid(String var1, String var2);

    public List<GcBaseData> queryAllBasedataItemsByParentid(String var1, String var2, AuthType var3);

    public List<GcBaseData> queryAllWithSelfItemsByParentid(String var1, String var2);

    public List<GcBaseData> queryAllWithSelfItemsByParentid(String var1, String var2, AuthType var3);

    public List<GcBaseData> queryDirectBasedataItemsByParentid(String var1, String var2);

    public List<GcBaseData> queryDirectBasedataItemsByParentid(String var1, String var2, AuthType var3);

    public List<GcBaseData> lazyLoadDirectBasedataItemsByParentid(String var1, String var2);

    public List<GcBaseData> lazyLoadDirectBasedataItemsByParentid(String var1, String var2, AuthType var3);

    public GcBaseData queryBasedataByCode(String var1, String var2);

    public GcBaseData queryBasedataByObjCode(String var1, String var2);

    public List<GcBaseData> batchQueryBasedata(String var1, List<String> var2);

    public GcBaseData queryBaseDataSimpleItem(String var1, String var2);

    public BaseDataVO queryBaseDataVoByCode(String var1, String var2);

    public List<GcBaseData> listBaseData(BaseDataParam var1);

    public Integer getBaseDataMaxDepth(BaseDataParam var1);
}

