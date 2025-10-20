/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.extend.DataModelBizType
 */
package com.jiuqi.va.datamodel.bill.biztype;

import com.jiuqi.va.datamodel.bill.common.BillMessageSourceUtil;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelBizType;
import org.springframework.stereotype.Component;

@Component(value="vaDataModelBillBizType")
public class BillBizType
implements DataModelBizType {
    public String getName() {
        return DataModelType.BizType.BILL.toString();
    }

    public String getTitle() {
        return BillMessageSourceUtil.getMessage("datamodel.attribute.biztype.bill", new Object[0]);
    }

    public int getOrdinal() {
        return 10;
    }
}

