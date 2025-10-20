/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.datamapping.impl.dao;

import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;

public interface DataRefConfigureDao {
    public int countAll(BaseDataMappingDefineDTO var1, DataRefListDTO var2);

    public int countUnref(BaseDataMappingDefineDTO var1, DataRefListDTO var2);

    public int countHasref(BaseDataMappingDefineDTO var1, DataRefListDTO var2);

    public int countAutoMatch(BaseDataMappingDefineDTO var1, DataRefListDTO var2);

    public int countResultByDataSource(String var1, BaseDataMappingDefineDTO var2, DataRefListDTO var3);

    public List<DataRefDTO> selectAll(BaseDataMappingDefineDTO var1, DataRefListDTO var2);

    public List<DataRefDTO> selectUnref(BaseDataMappingDefineDTO var1, DataRefListDTO var2);

    public List<DataRefDTO> selectHasref(BaseDataMappingDefineDTO var1, DataRefListDTO var2);

    public int countMultiSchemeHasref(BaseDataMappingDefineDTO var1, DataRefListDTO var2, Set<String> var3);

    public List<DataRefDTO> selectMultiSchemeHasref(BaseDataMappingDefineDTO var1, DataRefListDTO var2, Set<String> var3);

    public List<DataRefDTO> selectAutoMatch(BaseDataMappingDefineDTO var1, DataRefListDTO var2);

    public List<DataRefDTO> selectByOdsIdList(String var1, List<String> var2);

    public List<DataRefDTO> selectByCodeList(String var1, List<String> var2);

    public List<DataRefDTO> selectByOdsIdListAndScheme(String var1, String var2, List<String> var3);

    public List<DataRefDTO> selectAllRefData(BaseDataMappingDefineDTO var1);

    public List<DataRefDTO> getResultByDataSource(String var1, BaseDataMappingDefineDTO var2, DataRefListDTO var3);

    public List<DataRefDTO> getResultByDataSource(String var1, BaseDataMappingDefineDTO var2, DataRefListDTO var3, int var4);

    public void batchInsert(BaseDataMappingDefineDTO var1, List<DataRefDTO> var2);

    public void batchUpdate(BaseDataMappingDefineDTO var1, List<DataRefDTO> var2);

    public void batchDelete(String var1, List<DataRefDTO> var2);

    public String getDataSchemeCodeByUnitCode(String var1);

    public int deleteByBaseDataRefDefine(@NotNull(message="\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a") DataMappingDefineDTO var1);

    public void updateHandleStatusById(String var1, String var2, String var3);

    public List<String> selectConflictData(String var1, String var2, IsolationParamContext var3);

    public List<DataRefDTO> selectPendingData(String var1, String var2, IsolationParamContext var3);

    public void batchUpdateDcUnitCode(String var1, String var2, List<String> var3, List<String> var4);

    public void deleteByDcUnitCode(String var1, String var2, List<String> var3);
}

