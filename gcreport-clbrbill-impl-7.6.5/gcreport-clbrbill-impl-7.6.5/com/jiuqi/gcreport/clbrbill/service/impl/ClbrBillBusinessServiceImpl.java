/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.clbrbill.enums.ClbrBillTypeEnum
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbrbill.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.clbrbill.dto.ClbrCancelQuoteDTO;
import com.jiuqi.gcreport.clbrbill.enums.ClbrBillTypeEnum;
import com.jiuqi.gcreport.clbrbill.service.ClbrBillBusinessService;
import com.jiuqi.gcreport.clbrbill.utils.ClbrBillUtils;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClbrBillBusinessServiceImpl
implements ClbrBillBusinessService {
    @Autowired
    private BillDefineService billDefineService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelQuote(ClbrCancelQuoteDTO dto) {
        List<String> clbrCodes = Arrays.asList(dto.getClbrCode().split(","));
        for (String clbrCode : clbrCodes) {
            MetaInfoDTO metaInfo = ClbrBillUtils.getMetaInfoByClbrCode(clbrCode);
            BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)new BillContextImpl(), metaInfo.getUniqueCode());
            model.getContext().setContextValue("disableVerify", (Object)true);
            model.getRuler().getRulerExecutor().setEnable(true);
            model.loadByCode(clbrCode);
            model.getData().edit();
            List srcBillCode = ((List)model.getData().getTablesData().get("GC_CLBRSRCBILLITEM")).stream().filter(item -> dto.getSrcBillCode().equals(item.get("SRCBILLCODE"))).collect(Collectors.toList());
            Double amt = ConverterUtils.getAsDouble(((Map)srcBillCode.get(0)).get("AMT"));
            String clbrBillType = ConverterUtils.getAsString(((Map)srcBillCode.get(0)).get("CLBRBILLTYPE"));
            ClbrBillTypeEnum clbrBillTypeEnum = ClbrBillTypeEnum.getEnumByName((String)clbrBillType);
            if (ClbrBillTypeEnum.INITIATE.equals((Object)clbrBillTypeEnum)) {
                Double initQuoteAmt = ConverterUtils.getAsDouble((Object)model.getMaster().getValue("INITIATEQUOTEAMT"), (Double)0.0);
                model.getMaster().setValue("INITIATEQUOTEAMT", (Object)(initQuoteAmt - amt));
            } else if (ClbrBillTypeEnum.RECEIVE.equals((Object)clbrBillTypeEnum)) {
                Double recQuoteAmt = ConverterUtils.getAsDouble((Object)model.getMaster().getValue("RECEIVEQUOTEAMT"), (Double)0.0);
                model.getMaster().setValue("RECEIVEQUOTEAMT", (Object)(recQuoteAmt - amt));
            } else if (ClbrBillTypeEnum.THIRD_PARTY.equals((Object)clbrBillTypeEnum)) {
                Double thirdQuoteAmt = ConverterUtils.getAsDouble((Object)model.getMaster().getValue("THIRDQUOTEAMT"), (Double)0.0);
                model.getMaster().setValue("THIRDQUOTEAMT", (Object)(thirdQuoteAmt - amt));
            } else {
                throw new BusinessRuntimeException("\u534f\u540c\u5355\u7c7b\u578b\u9519\u8bef\u3002");
            }
            model.getTable("GC_CLBRSRCBILLITEM").deleteRowById(((Map)srcBillCode.get(0)).get("ID"));
            ClbrBillUtils.saveModel(model);
        }
    }
}

