/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.enums.VchrStateEnum
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.biz.intf.data.DataState
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.dc.bill.event;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.enums.VchrStateEnum;
import com.jiuqi.dc.bill.dao.BillVoucherHandleDao;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;

public class VoucherHandleEvent
implements DataPostEvent {
    public void beforeSave(DataImpl data) {
    }

    public void afterSave(DataImpl data) {
        this.handleVoucher(data.getState(), data.getTablesData());
    }

    public void beforeDelete(DataImpl data) {
        this.handleVoucher(null, data.getTablesData());
    }

    public void afterDelete(DataImpl data) {
    }

    private void handleVoucher(DataState dataState, Map<String, List<Map<String, Object>>> tablesData) {
        List<Map<String, Object>> voucherDatas = tablesData.get("DC_BILL_VOUCHER");
        if (CollectionUtils.isEmpty(voucherDatas)) {
            return;
        }
        List<String> vchrIds = voucherDatas.stream().map(data -> MapUtils.getString((Map)data, (Object)"ID")).collect(Collectors.toList());
        Integer vchrState = null;
        if (Objects.isNull(dataState)) {
            vchrState = VchrStateEnum.DELETE.getCode();
        } else if (DataState.NEW.equals((Object)dataState)) {
            vchrState = VchrStateEnum.ADD.getCode();
        } else if (DataState.EDIT.equals((Object)dataState)) {
            vchrState = VchrStateEnum.UPDATE.getCode();
        }
        ((BillVoucherHandleDao)ApplicationContextRegister.getBean(BillVoucherHandleDao.class)).insertVouchers(vchrIds, vchrState);
    }
}

