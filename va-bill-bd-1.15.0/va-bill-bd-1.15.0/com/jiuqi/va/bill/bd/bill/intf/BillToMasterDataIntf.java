/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.data.DataRow
 */
package com.jiuqi.va.bill.bd.bill.intf;

import com.jiuqi.va.bill.bd.bill.domain.MapInfoDTO;
import com.jiuqi.va.bill.bd.bill.model.BasedataApplyBillModel;
import com.jiuqi.va.bill.bd.bill.model.BillAlterModel;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.biz.intf.data.DataRow;
import java.util.List;
import java.util.Map;

public interface BillToMasterDataIntf {
    public String getType();

    public boolean isEnable(String var1);

    public void beforeCheckByApply(BasedataApplyBillModel var1, Map<String, Object> var2, ApplyRegMapDO var3, MapInfoDTO var4, Map<String, String> var5, String var6, Map<String, Map<String, List<String>>> var7, List<Map<String, Object>> var8);

    public void beforeCheckByAlter(BillAlterModel var1, Map<String, Object> var2, ApplyRegMapDO var3, MapInfoDTO var4, Map<String, String> var5, String var6, List<Map<String, Object>> var7);

    public void beforeCheckByCreate(DataRow var1, String var2);

    public boolean checkDataExist(DataRow var1, Map<String, Object> var2, Boolean var3, Map<String, String> var4, String var5);

    public Object createMasterData(RegistrationBillModel var1, String var2);

    public Object getDelMasterData(DataRow var1, String var2);

    public void syncMasterData(Object var1, String var2);

    public void delMasterData(Object var1, String var2);

    public boolean checkMasterDataIsChangeByDataRow(Map<String, String> var1, DataRow var2, DataRow var3, String var4);

    public boolean checkMasterDataIsChangeByDataMap(DataRow var1, Map<String, String> var2, Map<String, Object> var3, Map<String, Object> var4, String var5);
}

