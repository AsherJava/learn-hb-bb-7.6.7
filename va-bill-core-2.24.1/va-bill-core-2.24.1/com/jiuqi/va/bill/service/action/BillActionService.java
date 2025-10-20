/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.R
 */
package com.jiuqi.va.bill.service.action;

import com.jiuqi.va.bill.domain.action.CustomActionParamDTO;
import com.jiuqi.va.biz.utils.R;

public interface BillActionService {
    public R<String> billListCustomActionExecute(CustomActionParamDTO var1);
}

