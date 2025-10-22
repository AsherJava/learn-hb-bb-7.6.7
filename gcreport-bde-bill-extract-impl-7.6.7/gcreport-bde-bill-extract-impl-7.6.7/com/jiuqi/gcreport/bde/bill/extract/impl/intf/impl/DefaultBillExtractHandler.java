/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleDTO
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleParam
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandler
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO
 *  com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil
 *  com.jiuqi.va.bill.intf.BillModel
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.intf.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleDTO;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleParam;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandler;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO;
import com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultBillExtractHandler
implements IBillExtractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBillExtractHandler.class);
    @Autowired
    private BillExtractSettingClient settingClient;

    public String getModelCode() {
        return "DEFAULT";
    }

    public BillExtractHandleParam parse(BillExtractHandleDTO dto) {
        BillExtractHandleParam handleParam = new BillExtractHandleParam();
        if (StringUtils.isEmpty((String)dto.getStartDateStr())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.parse((String)dto.getEndDateStr()));
            Date firstDateOf = DateUtils.firstDateOf((int)calendar.get(1), (int)(calendar.get(2) + 1));
            handleParam.setStartDateStr(DateUtils.format((Date)firstDateOf));
        }
        handleParam.setEndDateStr(dto.getEndDateStr());
        Map queryOrgMap = BillExtractUtil.queryOrgMap((String)dto.getRpUnitType(), (Date)DateUtils.parse((String)dto.getEndDateStr()));
        if (Boolean.TRUE.equals(dto.getExtractAllUnit())) {
            handleParam.setUnitCodes(queryOrgMap.keySet().stream().collect(Collectors.toList()));
        } else {
            ArrayList<String> unitCodeList = new ArrayList<String>(dto.getUnitCodes().size());
            for (String unitCode : dto.getUnitCodes()) {
                if (queryOrgMap.get(unitCode) == null) {
                    LOGGER.warn("\u5355\u636e\u53d6\u6570\u6267\u884c\u4e2d\u5355\u4f4d\u7c7b\u578b{}\u65f6\u95f4{}\u5355\u4f4d\u4ee3\u7801{}\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u81ea\u52a8\u8df3\u8fc7", dto.getRpUnitType(), dto.getEndDateStr(), unitCode);
                }
                unitCodeList.add(unitCode);
            }
            handleParam.setUnitCodes(unitCodeList);
        }
        return handleParam;
    }

    public void doCheck(BillExtractHandleCtx dto) {
    }

    public List<Map<String, Object>> listBills(BillExtractHandleCtx ctx) {
        return (List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.settingClient.listBills(ctx.getBillDefine(), new BillExtractLisDTO(ctx.getUnitCode(), ctx.getStartDateStr(), ctx.getEndDateStr())));
    }

    public void doSave(BillExtractSaveContext saveCtx, BillExtractSaveData saveData) {
        throw new BusinessRuntimeException("\u5355\u636e\u5217\u8868\u53d6\u6570\u6682\u672a\u652f\u6301");
    }

    public void doSave(BillModel model, BillExtractSaveContext saveContext, BillExtractSaveData saveData) {
        if (saveData == null || saveData.getMasterResult() == null) {
            return;
        }
        for (Map.Entry masterEntry : saveData.getMasterResult().getFixedResults().entrySet()) {
            model.getMaster().setValue((String)masterEntry.getKey(), masterEntry.getValue());
        }
    }
}

