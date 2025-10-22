/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataField;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataLink;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataTable;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFieldDefine;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFormDefine;
import java.util.List;
import java.util.Map;

public interface IFetchSettingImpExpHandleAdaptor {
    public String getBizType();

    public List<ImpExpFormDefine> listFormDefine(String var1, List<String> var2);

    public ImpExpFieldDefine getFieldDefineInfo(String var1, String var2, ImpExpDataTable var3, ImpExpDataField var4, ImpExpDataLink var5, FixedAdaptSettingVO var6);

    public List<ImpExpDataLink> getDataLinkDefines(String var1, String var2);

    public ImpExpDataLink getDataLinkDefine(String var1, String var2, String var3);

    public ImpExpDataLink findDataLinkByMap(Map<String, ImpExpDataLink> var1, Map<String, ImpExpDataLink> var2, String var3);

    public ImpExpDataField getDataField(String var1, String var2, String var3);

    public ImpExpDataTable getDataTable(String var1, String var2, String var3);
}

