/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.customExcelBatchImport.service;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelCheckResultInfo;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.List;
import java.util.Map;

public interface ICustomExcelRegionTitleService {
    public Map<String, DataRegionDefine> getAllRegionTitleMap(String var1);

    public String getTitleByRegion(DataRegionDefine var1);

    public DataRegionDefine getRegionByTitle(String var1, String var2);

    public List<FieldDefine> getRegionFieldDefineList(DataRegionDefine var1);

    public Map<String, List<FieldDefine>> getDimFieldDefineMap(DataRegionDefine var1, CustomExcelCheckResultInfo var2);
}

