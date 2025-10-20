/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.event.BillCodeGenerateEvent
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModelService
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.intf.data.DataPostEvent
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.bd.bill.common;

import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.bill.service.WriteBackService;
import com.jiuqi.va.bill.bd.utils.BillBdI18nUtil;
import com.jiuqi.va.bill.impl.event.BillCodeGenerateEvent;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModelService;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.data.DataPostEvent;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BackWriteAndUpdateTransEvent
implements DataPostEvent {
    private static final Logger logger = LoggerFactory.getLogger(BackWriteAndUpdateTransEvent.class);

    public void beforeSave(DataImpl data) {
        RegistrationBillModel registrationBillModel = (RegistrationBillModel)data.getModel();
        if (StringUtils.hasText(registrationBillModel.getDeleteFlagField())) {
            return;
        }
        if (registrationBillModel.getWriteBackFlag() != null && registrationBillModel.getWriteBackFlag().booleanValue()) {
            R billcode;
            WriteBackService writeBackService = (WriteBackService)ApplicationContextRegister.getBean(WriteBackService.class);
            if (registrationBillModel.getMaster().getString("BILLCODE") == null) {
                BillCodeGenerateEvent billCodeGenerateEvent = new BillCodeGenerateEvent((BillModelService)ApplicationContextRegister.getBean(BillModelService.class));
                billCodeGenerateEvent.beforeSave(registrationBillModel.getData());
            }
            if ((billcode = writeBackService.writeBackToApplyBill(registrationBillModel.getTableName(), registrationBillModel.getFiledName(), registrationBillModel.getMaster().getString("BILLCODE"), registrationBillModel.getId())).getCode() != 0) {
                throw new BillException(BillBdI18nUtil.getMessage("va.billbd.back.write.error") + billcode.getMsg());
            }
        }
    }

    public void afterSave(DataImpl data) {
    }

    public void beforeDelete(DataImpl data) {
    }

    public void afterDelete(DataImpl data) {
    }
}

