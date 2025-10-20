/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient
 *  com.jiuqi.va.biz.intf.data.DataRow
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.data.DataRow;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewAttachmentAction
extends BillActionBase {
    @Autowired
    private VaAttachmentFeignClient vaAttachmentFeignClient;

    public String getName() {
        return "bill-view-attachment";
    }

    public String getTitle() {
        return "\u67e5\u770b\u9644\u4ef6";
    }

    public String getIcon() {
        return null;
    }

    public boolean isInner() {
        return true;
    }

    @Override
    public Object executeReturn(BillModel billModel, Map<String, Object> params) {
        String tableName = (String)params.get("tableName");
        String fieldName = (String)params.get("fieldName");
        String quotecode = ((DataRow)billModel.getTable(tableName).getRows().get(0)).getString(fieldName);
        return this.vaAttachmentFeignClient.listAttachment(quotecode);
    }
}

