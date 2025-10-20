/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.bill.bd.bill.service;

import com.jiuqi.va.bill.bd.bill.domain.CreateBillEntry;
import com.jiuqi.va.bill.bd.bill.domain.MapInfoDTO;
import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.List;
import java.util.Map;

public interface MaintainBillService {
    public void createBillByMatser(MapInfoDTO var1, BillModelImpl var2, Map<String, List<Map<String, Object>>> var3);

    public void changeBillByMatser(MapInfoDTO var1, BillModelImpl var2, Map<String, List<Map<String, Object>>> var3);

    public void setValuesDetail(RegistrationBillModel var1, Map<String, Object> var2, Map<String, List<Map<String, Object>>> var3, ApplyRegMapDO var4);

    public void createBillByDetail(RegistrationBillModel var1, String var2, CreateBillEntry var3, Map<String, Object> var4, Map<String, List<Map<String, Object>>> var5);

    public void changeBillByDetail(RegistrationBillModel var1, String var2, CreateBillEntry var3, Map<String, Object> var4);

    public void afterApprovalCreateBill(Model var1, List<ApplyRegMapDO> var2, UserLoginDTO var3);

    public void afterApprovalChangeBill(Model var1, List<ApplyRegMapDO> var2, UserLoginDTO var3);
}

