/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.api.FcIncrementCollectClient
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.xlib.utils.StringUtil
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.FcIncrementCollectService;
import com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult;
import com.jiuqi.gcreport.financialcheckapi.dataentry.api.FcIncrementCollectClient;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.xlib.utils.StringUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FcIncrementCollectController
implements FcIncrementCollectClient {
    @Autowired
    FcIncrementCollectService fcIncrementCollectService;

    public BusinessResponseEntity<CheckResult> doIncrementDataCollect(List<Map<String, Object>> items) {
        ArrayList<GcRelatedItemEO> itemEOs = new ArrayList<GcRelatedItemEO>();
        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(item -> {
                try {
                    itemEOs.add(this.convertMap2EO((Map<String, Object>)item));
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException("\u589e\u91cf\u6570\u636e\u505a\u6570\u636e\u8f6c\u6362\u65f6\u51fa\u73b0\u5f02\u5e38", (Throwable)e);
                }
            });
            this.fcIncrementCollectService.collect(itemEOs);
        }
        return BusinessResponseEntity.ok();
    }

    private GcRelatedItemEO convertMap2EO(Map<String, Object> itemMap) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GcRelatedItemEO item = new GcRelatedItemEO();
        item.resetFields(itemMap);
        item.setSrcItemId(Objects.isNull(itemMap.get("SRCITEMID")) ? null : String.valueOf(itemMap.get("SRCITEMID")));
        item.setAcctYear((Integer)itemMap.get("ACCTYEAR"));
        item.setAcctPeriod((Integer)itemMap.get("ACCTPERIOD"));
        item.setUnitId(Objects.isNull(itemMap.get("UNITID")) ? null : String.valueOf(itemMap.get("UNITID")));
        item.setOppUnitId(Objects.isNull(itemMap.get("OPPUNITID")) ? null : String.valueOf(itemMap.get("OPPUNITID")));
        item.setSubjectCode(Objects.isNull(itemMap.get("SUBJECTCODE")) ? null : String.valueOf(itemMap.get("SUBJECTCODE")));
        item.setOriginalCurr(Objects.isNull(itemMap.get("ORIGINALCURR")) ? null : String.valueOf(itemMap.get("ORIGINALCURR")));
        item.setCurrency(Objects.isNull(itemMap.get("CURRENCY")) ? null : String.valueOf(itemMap.get("CURRENCY")));
        item.setDebitOrig(Objects.isNull(itemMap.get("DEBITORIG")) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)itemMap.get("DEBITORIG"))));
        item.setCreditOrig(Objects.isNull(itemMap.get("CREDITORIG")) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)itemMap.get("CREDITORIG"))));
        item.setDebit(Objects.isNull(itemMap.get("DEBIT")) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)itemMap.get("DEBIT"))));
        item.setCredit(Objects.isNull(itemMap.get("CREDIT")) ? null : Double.valueOf(ConverterUtils.getAsDoubleValue((Object)itemMap.get("CREDIT"))));
        item.setBillCode(Objects.isNull(itemMap.get("BILLCODE")) ? null : String.valueOf(itemMap.get("BILLCODE")));
        item.setCreateDate(Objects.isNull(itemMap.get("CREATEDATE")) || StringUtil.isEmpty((String)((String)itemMap.get("CREATEDATE"))) ? null : sdf.parse((String)itemMap.get("CREATEDATE")));
        item.setDigest(Objects.isNull(itemMap.get("DIGEST")) ? null : String.valueOf(itemMap.get("DIGEST")));
        item.setGcNumber(Objects.isNull(itemMap.get("GCNUMBER")) ? null : String.valueOf(itemMap.get("GCNUMBER")));
        item.setItemOrder(Objects.isNull(itemMap.get("ITEMORDER")) ? null : String.valueOf(itemMap.get("ITEMORDER")));
        item.setMemo(Objects.isNull(itemMap.get("MEMO")) ? null : String.valueOf(itemMap.get("MEMO")));
        item.setUpdateTime(Objects.isNull(itemMap.get("UPDATETIME")) ? null : String.valueOf(itemMap.get("UPDATETIME")));
        item.setVchrType(Objects.isNull(itemMap.get("VCHRTYPE")) ? null : String.valueOf(itemMap.get("VCHRTYPE")));
        item.setVchrNum(Objects.isNull(itemMap.get("VCHRNUM")) ? null : String.valueOf(itemMap.get("VCHRNUM")));
        item.setVchrSourceType(Objects.isNull(itemMap.get("VCHRSOURCETYPE")) ? null : String.valueOf(itemMap.get("VCHRSOURCETYPE")));
        item.setRealGcNumber(Objects.isNull(itemMap.get("REALGCNUMBER")) ? null : String.valueOf(itemMap.get("REALGCNUMBER")));
        item.setSrcVchrId(Objects.isNull(itemMap.get("SRCVCHRID")) ? null : String.valueOf(itemMap.get("SRCVCHRID")));
        item.addFieldValue("DELETEFLAG", Objects.isNull(itemMap.get("DELETEFLAG")) ? null : String.valueOf(itemMap.get("DELETEFLAG")));
        item.addFieldValue("ID", Objects.isNull(itemMap.get("ID")) ? null : String.valueOf(itemMap.get("ID")));
        item.setCfItemCode(Objects.isNull(itemMap.get("CFITEMCODE")) ? null : String.valueOf(itemMap.get("CFITEMCODE")));
        item.setVchrId(Objects.isNull(itemMap.get("VCHRID")) ? null : String.valueOf(itemMap.get("VCHRID")));
        item.setRuleChangeHandlerFlag((Integer)itemMap.get("RULECHANGEHANDLERFLAG"));
        item.setReclassifySubjCode(Objects.isNull(itemMap.get("RECLASSIFYSUBJCODE")) ? null : String.valueOf(itemMap.get("RECLASSIFYSUBJCODE")));
        item.setSrcIteMassId(Objects.isNull(itemMap.get("SRCITEMASSID")) ? null : String.valueOf(itemMap.get("SRCITEMASSID")));
        return item;
    }
}

