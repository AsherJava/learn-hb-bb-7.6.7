/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.asynctask;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.EfdcDataCheckService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcBatchEfdcCheckAsyncTaskExecutor
implements NpAsyncTaskExecutor {
    @Autowired
    private EfdcDataCheckService batchCheckService;

    public void execute(Object args, AsyncTaskMonitor asyncTaskMonitor) {
        try {
            if (args instanceof GcBatchEfdcCheckInfo) {
                GcBatchEfdcCheckInfo batchCheckInfo = (GcBatchEfdcCheckInfo)args;
                this.buildOrgDimension(batchCheckInfo.getFormSchemeKey(), batchCheckInfo.getDimensionSet());
                EFDCDataCheckImpl dataCheck = new EFDCDataCheckImpl();
                dataCheck.setAsyncTaskMonitor(asyncTaskMonitor);
                dataCheck.batchEfdcDataCheck(asyncTaskMonitor.getTaskId(), batchCheckInfo);
                if (asyncTaskMonitor.isCancel()) {
                    String retStr = "\u4efb\u52a1\u53d6\u6d88";
                    asyncTaskMonitor.canceled(retStr, (Object)retStr);
                }
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)nrCommonException);
            nrCommonException.printStackTrace();
        }
        catch (Exception e) {
            asyncTaskMonitor.error("\u4efb\u52a1\u51fa\u9519", (Throwable)e);
            e.printStackTrace();
        }
    }

    private void buildOrgDimension(String schemeKey, Map<String, DimensionValue> dimensionSet) throws Exception {
        if (dimensionSet == null) {
            return;
        }
        DimensionValue orgDimension = dimensionSet.get("MD_ORG");
        if (orgDimension == null) {
            return;
        }
        String orgsId = orgDimension.getValue();
        String[] orgIdArr = orgsId.split(";");
        if (orgIdArr == null || orgIdArr.length == 0) {
            return;
        }
        DimensionValue dateDimension = dimensionSet.get("DATATIME");
        if (dateDimension == null) {
            return;
        }
        String periodWrapper = dateDimension.getValue();
        YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)schemeKey, (String)periodWrapper);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)GCOrgTypeEnum.CORPORATE.getCode(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yp);
        HashSet<String> orgIds = new HashSet<String>();
        HashSet<String> removedOrgIds = new HashSet<String>();
        for (String orgCode : Arrays.asList(orgIdArr)) {
            GcOrgCacheVO org = tool.getOrgByCode(orgCode);
            if (null == org) continue;
            removedOrgIds.add(org.getDiffUnitId());
            List orgs = tool.listAllOrgByParentIdContainsSelf(org.getId());
            if (orgs.size() == 0) {
                orgIds.add(org.getId());
                continue;
            }
            for (GcOrgCacheVO oneOrg : orgs) {
                removedOrgIds.add(oneOrg.getParentId());
                removedOrgIds.add(StringUtils.toViewString((Object)oneOrg.getDiffUnitId()));
                orgIds.add(oneOrg.getId());
            }
        }
        orgIds.removeAll(removedOrgIds);
        StringBuffer org = new StringBuffer(512);
        for (String uuid : orgIds) {
            org.append(uuid).append(";");
        }
        org.setLength(org.length() - 1);
        orgDimension.setValue(org.toString());
    }

    public String getTaskPoolType() {
        return GcAsyncTaskPoolType.ASYNCTASK_BATCHEFDCCHECK.getName();
    }
}

