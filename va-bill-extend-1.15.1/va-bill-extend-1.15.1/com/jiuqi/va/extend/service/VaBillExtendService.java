/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.CopyProgressDO
 *  com.jiuqi.va.bill.intf.BillDefine
 */
package com.jiuqi.va.extend.service;

import com.jiuqi.va.attachment.domain.CopyProgressDO;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.extend.domain.BillReuseField;
import java.util.List;

public interface VaBillExtendService {
    public List<BillReuseField> filterFields(BillDefine var1);

    public CopyProgressDO getCopyProgress(String var1);
}

