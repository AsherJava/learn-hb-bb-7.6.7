/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.QueryCondition;
import com.jiuqi.gcreport.nr.impl.function.impl.util.CrossTaskFunctionUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GcSimpleCopyParam {
    private QueryCondition srcCondition = new QueryCondition();
    private QueryCondition desCondition = new QueryCondition();
    private Map<Integer, Integer> updateColumns = CollectionUtils.newHashMap();
    private Map<Integer, Integer> insertColumns = CollectionUtils.newHashMap();
    private String reportName;
    private String alias;
    private int periodOffset;
    private boolean clearBeforeCopy;
    private String orgCode;
    private String period;

    public QueryCondition getDesCondition() {
        return this.desCondition;
    }

    public QueryCondition getSrcCondition() {
        return this.srcCondition;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public int getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    public boolean isClearBeforeCopy() {
        return this.clearBeforeCopy;
    }

    public void setClearBeforeCopy(boolean clearBeforeCopy) {
        this.clearBeforeCopy = clearBeforeCopy;
    }

    public Map<Integer, Integer> getInsertColumns() {
        return this.insertColumns;
    }

    public Map<Integer, Integer> getUpdateColumns() {
        return this.updateColumns;
    }

    public void addUpdateColumn(String srcField, String destField) {
        int srcIndex = this.srcCondition.addColumn(srcField);
        int destIndex = this.desCondition.addColumn(destField);
        if (srcIndex > -1 && destIndex > -1) {
            this.updateColumns.put(destIndex, srcIndex);
        }
    }

    public void addInsertColumn(String srcField, String destField) {
        int srcIndex = this.srcCondition.addColumn(srcField);
        int destIndex = this.desCondition.addColumn(destField);
        if (srcIndex > -1 && destIndex > -1) {
            this.insertColumns.put(destIndex, srcIndex);
        }
    }

    public DimensionValueSet getDimValueSetList(QueryContext qContext) {
        DimensionValueSet currValueSet = qContext.getCurrentMasterKey();
        String periodValue = (String)currValueSet.getValue("DATATIME");
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        if (!StringUtils.isEmpty((String)this.alias)) {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
            TaskLinkDefine taskLinkDefine = runTimeViewController.queryTaskLinkByCurrentFormSchemeAndNumber(formSchemeKey, this.alias);
            if (Objects.isNull(taskLinkDefine)) {
                throw new BusinessRuntimeException("\u672a\u67e5\u8be2\u5230\u5173\u8054\u4efb\u52a1");
            }
            FormSchemeDefine linkedFormScheme = runTimeViewController.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
            if (Objects.isNull(formSchemeKey)) {
                throw new BusinessRuntimeException("\u5173\u8054\u4efb\u52a1\u5173\u8054\u7684\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728");
            }
            FormSchemeDefine currFormScheme = runTimeViewController.getFormScheme(formSchemeKey);
            if (Objects.isNull(currFormScheme)) {
                throw new BusinessRuntimeException("\u5f53\u524d\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u5931\u8d25\uff1a" + formSchemeKey);
            }
            if (!Arrays.stream(currFormScheme.getDims().split(";")).collect(Collectors.toSet()).equals(Arrays.stream(linkedFormScheme.getDims().split(";")).collect(Collectors.toSet()))) {
                throw new BusinessRuntimeException("\u76ee\u6807\u6d6e\u52a8\u884c\u6240\u5728\u62a5\u8868\u65b9\u6848\u4e0e\u6e90\u6d6e\u52a8\u884c\u6240\u5728\u62a5\u8868\u65b9\u6848\u7684\u60c5\u666f\u4e0d\u4e00\u6837\uff0c\u4e0d\u80fd\u8fdb\u884c\u8de8\u4efb\u52a1\u62f7\u8d1d");
            }
            formSchemeKey = linkedFormScheme.getKey();
            PeriodType periodType = linkedFormScheme.getPeriodType();
            PeriodType currPeriodType = PeriodType.fromType((int)new PeriodWrapper(periodValue).getType());
            if (periodType.type() != currPeriodType.type()) {
                try {
                    periodValue = CrossTaskFunctionUtil.convertPeriod(periodValue, currPeriodType, periodType);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException("\u8de8\u4efb\u52a1\u65f6\u671f\u8f6c\u6362\u5f02\u5e38\u65e5\u671f\u8f6c\u6362", (Throwable)e);
                }
            }
        }
        DimensionValueSet newValueSet = new DimensionValueSet(currValueSet);
        PeriodWrapper srcPeriod = this.getSrcPeriod(periodValue);
        String periodStr = srcPeriod.toString();
        if (!StringUtils.isEmpty((String)this.period)) {
            periodStr = this.period;
        }
        newValueSet.setValue("DATATIME", (Object)periodStr);
        if (!StringUtils.isEmpty((String)this.orgCode)) {
            newValueSet.setValue("MD_ORG", (Object)this.orgCode);
        }
        if (!StringUtils.isEmpty((String)this.alias) || !StringUtils.isEmpty((String)this.orgCode)) {
            EntityViewData dwEntity = ((IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class)).getDwEntity(formSchemeKey);
            if (Objects.isNull(dwEntity)) {
                throw new BusinessRuntimeException("\u62a5\u8868\u65b9\u6848\u5bf9\u5e94\u7684\u5355\u4f4d\u4e3b\u4f53\u65b9\u4e0d\u5b58\u5728:" + formSchemeKey);
            }
            YearPeriodObject yp = new YearPeriodObject(null, periodStr);
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)dwEntity.getTableName(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO mergeOrg = orgTool.getOrgByCode((String)newValueSet.getValue("MD_ORG"));
            newValueSet.setValue("MD_GCORGTYPE", (Object)mergeOrg.getOrgTypeId());
        }
        return newValueSet;
    }

    private PeriodWrapper getSrcPeriod(String periodValue) {
        PeriodWrapper srcPeriod = new PeriodWrapper(periodValue);
        srcPeriod.modifyPeriod(this.periodOffset);
        return srcPeriod;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}

