/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service.impl;

import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.extend.DataCheckPdfService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class DataCheckPdfServiceImpl
implements DataCheckPdfService {
    @Override
    public String getDataCheckPdfHeaderMsg(String asynTaskID, GcBatchEfdcCheckInfo batchCheckInfo, EFDCDataCheckImpl checkResultInfo) {
        Set unitIdSet = batchCheckInfo.getOrgIds();
        int formCount = checkResultInfo.getForm2CheckZbCount().size();
        int checkZbCount = checkResultInfo.getCheckZbCount();
        int failZbCount = checkResultInfo.getFailZbCount();
        int successCount = checkZbCount - failZbCount;
        String rate = "0";
        if (successCount != 0 && checkZbCount != 0) {
            rate = NumberUtils.round((double)((double)successCount * 100.0 / (double)checkZbCount)) + "%";
        }
        String msg = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcResultInfo", (Object[])new Object[]{unitIdSet.size(), formCount, checkZbCount, successCount, rate});
        return msg;
    }
}

