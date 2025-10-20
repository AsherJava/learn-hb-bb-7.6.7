/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.billcore.common.AbstractOneBillItemExportTask
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 */
package com.jiuqi.gcreport.invest.investbill.executor;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.billcore.common.AbstractOneBillItemExportTask;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestBillItemExportTask
extends AbstractOneBillItemExportTask {
    @Autowired
    private InvestBillDao investBillDao;

    protected List<Map<String, Object>> getMasterRecords(Map<String, Object> params) {
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        List investBillIds = (List)params.get("investBillIds");
        List<Map<String, Object>> masters = CollectionUtils.isEmpty((Collection)investBillIds) ? this.investBillDao.listInvestBillsByPaging(params) : this.investBillDao.listInvestBillsByIds(investBillIds);
        InvestBillTool.formatBillContent(masters, params, (String)"GC_INVESTBILL", (boolean)false);
        return masters;
    }

    protected String[] getMaterKeyColumnCodes() {
        return new String[]{"UNITCODE", "INVESTEDUNIT"};
    }

    protected String getSheetName() {
        return "\u6295\u8d44\u53f0\u8d26\u53d8\u52a8";
    }
}

