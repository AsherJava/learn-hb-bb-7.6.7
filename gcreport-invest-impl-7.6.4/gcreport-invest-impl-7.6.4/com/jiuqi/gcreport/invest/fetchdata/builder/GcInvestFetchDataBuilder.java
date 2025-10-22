/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.billcore.fetchdata.builder.GcBillFetchDataCommonBuilder
 *  com.jiuqi.va.bill.impl.BillModelImpl
 */
package com.jiuqi.gcreport.invest.fetchdata.builder;

import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.billcore.fetchdata.builder.GcBillFetchDataCommonBuilder;
import com.jiuqi.va.bill.impl.BillModelImpl;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GcInvestFetchDataBuilder
extends GcBillFetchDataCommonBuilder {
    public String getBillModelType() {
        return "InvestBillModel";
    }

    public void buildSubDatas(BillModelImpl model, List<Map<String, Object>> vchrSubItems, String subTableName) {
        vchrSubItems.forEach(item -> {
            if (null == item.get("CHANGESCENARIO")) {
                item.put("CHANGESCENARIO", "6666");
            }
        });
        List subItems = (List)model.getData().getTablesData().get(subTableName);
        int maxManualMonth = 0;
        for (Map subItem : subItems) {
            LocalDateTime localDateTime;
            if (null != subItem.get("VCHRUNIQUECODE") || (localDateTime = DateUtils.convertDateToLDT((Date)((Date)subItem.get("CHANGEDATE")))).getMonthValue() <= maxManualMonth) continue;
            maxManualMonth = localDateTime.getMonthValue();
        }
        int finalMaxManualMonth = maxManualMonth;
        List filtedVchrSubItems = vchrSubItems.stream().filter(vchrSubItem -> {
            LocalDateTime localDateTime = DateUtils.convertDateToLDT((Date)DateUtils.parse((String)((String)vchrSubItem.get("CHANGEDATE")), (String)DateCommonFormatEnum.FULL_DIGIT_BY_NONE.getFormat()));
            return localDateTime.getMonthValue() > finalMaxManualMonth;
        }).collect(Collectors.toList());
        super.buildSubDatas(model, filtedVchrSubItems, subTableName);
    }

    public String getSubBizFiledCode() {
        return "VCHRUNIQUECODE";
    }
}

