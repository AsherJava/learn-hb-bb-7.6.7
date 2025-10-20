/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO
 *  com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 */
package com.jiuqi.gcreport.billextract.impl.service;

import com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import java.util.List;
import java.util.Map;

public interface BillExtractSettingService {
    public List<MetaInfoDim> listMetaInfo(String var1);

    public MetaInfoDTO getMetaInfoByUniqueCode(String var1);

    public String getMasterTableName(String var1);

    public DataModelDO getDataModelByName(String var1);

    public DataModelColumn getDataModelColumn(String var1, String var2);

    public String getOrgType(String var1);

    public List<Map<String, Object>> listBills(String var1, BillExtractLisDTO var2);

    public Map<String, Object> getBill(String var1, String var2);

    public BillSchemeConfigDTO getSchemeByOrgId(String var1, String var2);
}

