/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.biz.intf.data.DataField
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 */
package com.jiuqi.gcreport.billcore.common;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;

public class GcBillModelImpl
extends BillModelImpl {
    public BillContext getContext() {
        BillContextImpl contextImpl = (BillContextImpl)super.getContext();
        contextImpl.setDisableVerify(true);
        return super.getContext();
    }

    public String getUnitCategory(String tableName) {
        return StringUtils.isEmpty((String)tableName) ? "MD_ORG_CORPORATE" : tableName;
    }

    public String getOrgType() {
        DataFieldDefine fieldDefine = ((DataField)this.getMasterTable().getFields().get("UNITCODE")).getDefine();
        return null == fieldDefine ? "MD_ORG_CORPORATE" : fieldDefine.getRefTableName();
    }
}

