/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.operate.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.clbrbill.dispatcher.operate.ClbrUpdateBusinessDataOperateHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrUpdateDataDTO;
import com.jiuqi.gcreport.clbrbill.utils.ClbrBillUtils;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultClbrUpdateBusinessDataSyncBillStateHandlerImpl
implements ClbrUpdateBusinessDataOperateHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultClbrUpdateBusinessDataSyncBillStateHandlerImpl.class);
    @Autowired
    BillDefineService billDefineService;

    @Override
    public String getOperateType() {
        return "SYNCBILLSTATE";
    }

    @Override
    public Object handler(ClbrUpdateDataDTO dto) {
        String[] clbrCodes;
        LOGGER.info("\u534f\u540c\u64cd\u4f5c\u7c7b\u578b\uff1a{}\uff0c\u5f00\u59cb\u5904\u7406\u6570\u636e", (Object)this.getOperateType());
        Map<String, Object> params = dto.getParams();
        if (!params.containsKey("SRCBILLSTATE")) {
            throw new BusinessRuntimeException("\u540c\u6b65\u5171\u4eab\u5355\u636e\u72b6\u6001\u65f6\uff0csrcBillState\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String srcBillState = ConverterUtils.getAsString((Object)params.get("SRCBILLSTATE"));
        for (String clbrCode : clbrCodes = dto.getClbrCode().split(",")) {
            MetaInfoDTO metaInfo = ClbrBillUtils.getMetaInfoByClbrCode(clbrCode);
            BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)new BillContextImpl(), metaInfo.getUniqueCode());
            model.getContext().setContextValue("disableVerify", (Object)true);
            model.getRuler().getRulerExecutor().setEnable(true);
            model.loadByCode(clbrCode);
            model.getData().edit();
            Map tablesData = model.getData().getTablesData();
            List srcBillCode = ((List)tablesData.get("GC_CLBRSRCBILLITEM")).stream().filter(item -> dto.getSrcBillCode().equals(item.get("SRCBILLCODE"))).collect(Collectors.toList());
            for (Map billItem : srcBillCode) {
                billItem.replace("SRCBILLSTATE", srcBillState.toUpperCase(Locale.ROOT));
            }
            model.getData().setTablesData(tablesData);
            ClbrBillUtils.saveModel(model);
        }
        return null;
    }
}

