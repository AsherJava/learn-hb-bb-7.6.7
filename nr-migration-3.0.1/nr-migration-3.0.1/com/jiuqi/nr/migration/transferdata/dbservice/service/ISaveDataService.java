/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.fmdm.exception.FMDMUpdateException
 */
package com.jiuqi.nr.migration.transferdata.dbservice.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.TransMemo;
import java.util.List;
import java.util.Map;

public interface ISaveDataService {
    public void storageOneData(List<DimInfo> var1, String var2, String var3, String var4, List<IMetaData> var5, List<Object> var6) throws Exception;

    public int storageFloatDatas(List<DimInfo> var1, String var2, String var3, String var4, List<IMetaData> var5, List<List<Object>> var6) throws Exception;

    public void storageFmdmData(List<DimInfo> var1, String var2, String var3, List<String> var4, Map<String, Object> var5) throws FMDMUpdateException;

    public void storageDataState(String var1, DimensionValueSet var2, int var3, boolean var4);

    public void storageWorkFlowHi(String var1, DimensionValueSet var2, List<DimInfo> var3, List<TransMemo> var4);
}

