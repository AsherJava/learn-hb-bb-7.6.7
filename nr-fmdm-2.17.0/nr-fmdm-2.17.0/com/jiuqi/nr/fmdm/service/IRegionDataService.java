/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.fmdm.service;

import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.fmdm.domain.FMDMModifyDTO;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.fmdm.internal.provider.ModifyDataProvider;
import java.util.List;

public interface IRegionDataService {
    public List<IDataRow> queryRegionData(QueryParamDTO var1, List<FieldDefine> var2);

    public void updateRegionData(FMDMModifyDTO var1, List<FieldDefine> var2, QueryParamDTO var3, ModifyDataProvider var4);

    public void batchUpdateRegionData(QueryParamDTO var1, List<FMDMModifyDTO> var2, List<FieldDefine> var3, ModifyDataProvider var4);

    public void batchUpdateRegionData(QueryParamDTO var1, List<FMDMModifyDTO> var2, List<FieldDefine> var3, ModifyDataProvider var4, boolean var5);

    public void batchAddRegionData(QueryParamDTO var1, List<FMDMModifyDTO> var2, List<FieldDefine> var3, ModifyDataProvider var4);

    public void batchAddRegionData(QueryParamDTO var1, List<FMDMModifyDTO> var2, List<FieldDefine> var3, ModifyDataProvider var4, boolean var5);
}

