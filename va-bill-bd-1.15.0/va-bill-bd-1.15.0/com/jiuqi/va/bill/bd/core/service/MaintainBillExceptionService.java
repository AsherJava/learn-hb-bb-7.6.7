/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bill.bd.core.service;

import com.jiuqi.va.bill.bd.bill.domain.CreateBillExceptionDTO;
import com.jiuqi.va.domain.common.R;
import java.util.List;

public interface MaintainBillExceptionService {
    public List<CreateBillExceptionDTO> queryData(CreateBillExceptionDTO var1);

    public List<CreateBillExceptionDTO> queryExceptionData(CreateBillExceptionDTO var1);

    public List<CreateBillExceptionDTO> queryLatestData(CreateBillExceptionDTO var1);

    public R queryAndInsert(CreateBillExceptionDTO var1, String var2);

    public R insertData(CreateBillExceptionDTO var1);

    public R updateData(CreateBillExceptionDTO var1);

    public R republish(List<CreateBillExceptionDTO> var1);
}

