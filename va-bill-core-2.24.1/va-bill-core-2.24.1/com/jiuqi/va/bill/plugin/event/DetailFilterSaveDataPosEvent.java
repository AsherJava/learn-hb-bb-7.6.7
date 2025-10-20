/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.bill.plugin.event;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DetailFilterSaveDataPosEvent
implements DataPostEvent {
    public void beforeSave(DataImpl data) {
    }

    public void afterSave(DataImpl data) {
        BillContextImpl context = (BillContextImpl)data.getModel().getContext();
        Object detailEnableFilter = context.getContextValue("X--detailFilterDataId");
        if (detailEnableFilter == null) {
            return;
        }
        DataTableNodeContainerImpl tables = data.getTables();
        Map dataUpdateMap = data.getChangeData();
        Map<String, Map<String, Object>> changeRecord = BillUtils.getUpdateData(dataUpdateMap, (DataTableNodeContainerImpl<DataTableImpl>)tables);
        if (CollectionUtils.isEmpty(changeRecord)) {
            return;
        }
        LogUtil.add((String)"\u5355\u636e", (String)"\u4fdd\u5b58\uff08\u8fc7\u6ee4\uff09", (String)data.getModel().getDefine().getName(), null, (String)JSONUtil.toJSONString(changeRecord));
    }

    public void beforeDelete(DataImpl data) {
    }

    public void afterDelete(DataImpl data) {
    }
}

