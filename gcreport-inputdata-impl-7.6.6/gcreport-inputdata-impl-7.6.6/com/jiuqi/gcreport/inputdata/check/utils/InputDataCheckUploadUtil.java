/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckAbleResult
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.gcreport.inputdata.check.utils;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckAbleResult;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InputDataCheckUploadUtil {
    private Map<String, ReadWriteAccessDesc> orgCache = new HashMap<String, ReadWriteAccessDesc>(16);

    public InputDataCheckAbleResult checkUpload(InputDataCheckCondition inputDataCheckCondition, String unitId, String oppUnitId) {
        ReadWriteAccessDesc diffWriteAble;
        ReadWriteAccessDesc mergeWriteAble;
        GcOrgCacheVO oppUnitOrg;
        GcOrgCacheVO unitOrg;
        YearPeriodObject yp;
        GcOrgCenterService orgTool;
        GcOrgCacheVO mergeOrg;
        InputDataCheckAbleResult result = new InputDataCheckAbleResult();
        String taskId = inputDataCheckCondition.getTaskId();
        String formSchemeKey = null;
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).querySchemePeriodLinkByPeriodAndTask(inputDataCheckCondition.getDataTime(), inputDataCheckCondition.getTaskId());
            formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
        }
        catch (Exception e) {
            throw new RuntimeException("\u6839\u636e\u4efb\u52a1\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38\u3002");
        }
        IWorkflow iWorkflow = (IWorkflow)SpringContextUtils.getBean(IWorkflow.class);
        WorkFlowType startType = iWorkflow.queryStartType(formSchemeKey);
        String orgCategory = inputDataCheckCondition.getUnitDefine();
        if (StringUtils.isEmpty((String)orgCategory)) {
            orgCategory = DimensionUtils.getDwEntitieTableByTaskKey((String)taskId);
        }
        if (Objects.isNull(mergeOrg = (orgTool = GcOrgPublicTool.getInstance((String)orgCategory, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)(yp = new YearPeriodObject(formSchemeKey, inputDataCheckCondition.getDataTime())))).getCommonUnit(unitOrg = orgTool.getOrgByCode(unitId), oppUnitOrg = orgTool.getOrgByCode(oppUnitId)))) {
            result.setMsg(GcI18nUtil.getMessage((String)"gc.inputdata.check.unionnotexistmsg"));
            return result;
        }
        if (this.orgCache.containsKey(mergeOrg.getCode())) {
            mergeWriteAble = this.orgCache.get(mergeOrg.getCode());
        } else {
            DimensionParamsVO mergeParams = this.structureParam(inputDataCheckCondition, formSchemeKey, orgCategory, orgTool, mergeOrg.getCode());
            mergeWriteAble = UploadStateTool.getInstance().writeable(mergeParams);
            this.orgCache.put(mergeOrg.getCode(), mergeWriteAble);
        }
        if (Boolean.FALSE.equals(mergeWriteAble.getAble())) {
            result.setMsg(GcI18nUtil.getMessage((String)"gc.inputdata.check.unionuploadmsg"));
            return result;
        }
        String diffUnitId = mergeOrg.getDiffUnitId();
        if (StringUtils.isEmpty((String)diffUnitId)) {
            Object[] args = new String[]{mergeOrg.getCode() + "|" + mergeOrg.getTitle()};
            result.setMsg(GcI18nUtil.getMessage((String)"gc.inputdata.check.diffnotexistmsg", (Object[])args));
            return result;
        }
        if (this.orgCache.containsKey(diffUnitId)) {
            diffWriteAble = this.orgCache.get(diffUnitId);
        } else {
            DimensionParamsVO diffParams = this.structureParam(inputDataCheckCondition, formSchemeKey, orgCategory, orgTool, diffUnitId);
            diffWriteAble = UploadStateTool.getInstance().writeable(diffParams);
            this.orgCache.put(diffUnitId, diffWriteAble);
        }
        if (Boolean.FALSE.equals(diffWriteAble.getAble())) {
            result.setMsg(GcI18nUtil.getMessage((String)"gc.inputdata.check.diffuploadmsg"));
            return result;
        }
        result.setWriteAble(Boolean.valueOf(true));
        return result;
    }

    private DimensionParamsVO structureParam(InputDataCheckCondition inputDataCheckCondition, String formSchemeKey, String orgCategory, GcOrgCenterService orgTool, String orgId) {
        DimensionParamsVO params = new DimensionParamsVO();
        GcOrgCacheVO orgCacheVO = orgTool.getOrgByCode(orgId);
        if (Objects.isNull(orgCacheVO)) {
            // empty if block
        }
        params.setTaskId(inputDataCheckCondition.getTaskId());
        params.setSchemeId(formSchemeKey);
        params.setOrgId(orgId);
        params.setOrgTypeId(orgCacheVO.getOrgTypeId());
        params.setOrgType(Objects.requireNonNull(orgCategory));
        params.setPeriodStr(inputDataCheckCondition.getDataTime());
        params.setCurrency(inputDataCheckCondition.getCurrencyCode());
        params.setCurrencyId(inputDataCheckCondition.getCurrencyCode());
        params.setSelectAdjustCode(inputDataCheckCondition.getSelectAdjustCode());
        return params;
    }
}

