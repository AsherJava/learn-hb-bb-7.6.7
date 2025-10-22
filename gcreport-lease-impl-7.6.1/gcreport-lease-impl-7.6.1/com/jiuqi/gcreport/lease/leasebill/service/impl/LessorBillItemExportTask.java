/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.common.AbstractOneBillItemExportTask
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils$Relation
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.lease.leasebill.service.impl;

import com.jiuqi.gcreport.billcore.common.AbstractOneBillItemExportTask;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.lease.leasebill.dao.LessorBillDao;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LessorBillItemExportTask
extends AbstractOneBillItemExportTask {
    @Autowired
    private LessorBillDao lessorBillDao;

    protected List<Map<String, Object>> getMasterRecords(Map<String, Object> params) {
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        TempTableCondition tempTableCondition = this.getTempTableCondition(params);
        if (null == tempTableCondition) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> masters = this.lessorBillDao.listLessorBillsByPaging(tempTableCondition, params);
        InvestBillTool.formatBillContent(masters, params, (String)"GC_LESSORBILL", (boolean)false);
        return masters;
    }

    protected String[] getMaterKeyColumnCodes() {
        return new String[]{"UNITCODE", "OPPUNITCODE"};
    }

    protected String getSheetName() {
        return "\u51fa\u79df\u65b9\u53f0\u8d26\u5b50\u8868";
    }

    private TempTableCondition getTempTableCondition(Map<String, Object> params) {
        String mergeUnit = (String)params.get("mergeUnit");
        String periodStr = (String)params.get("periodStr");
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgCenterTool.getOrgByCode(mergeUnit);
        if (null == orgCacheVO) {
            return null;
        }
        List orgIds = orgCenterTool.listAllOrgByParentIdContainsSelf(mergeUnit).stream().map(GcOrgCacheVO::getId).collect(Collectors.toList());
        return SqlUtils.getConditionOfIds(orgIds, (String)" ", (SqlUtils.Relation)SqlUtils.Relation.POSITIVE, (boolean)false);
    }
}

