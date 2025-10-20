/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO
 */
package com.jiuqi.gcreport.clbr.service;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.clbr.dto.ClbrBillReceBillCodeDTO;

public interface ClbrBillReceBillCodeService {
    public BusinessResponseEntity<Object> updateOrDeleteClbrReceBillCode(ClbrBillReceBillCodeDTO var1);
}

