/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.vo;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DimensionParamsVO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DimensionParamsVO.class);
    private String taskId;
    private String schemeId;
    private String periodStr;
    private String currency;
    private String currencyId;
    private String orgType;
    private String orgTypeId;
    private String orgId;
    private String selectAdjustCode;

    public DimensionParamsVO() {
    }

    public DimensionParamsVO(Map<String, DimensionValue> dimensionSet, String schemeId) {
        this.schemeId = schemeId;
        this.periodStr = this.findDimValue(dimensionSet, "DATATIME");
        this.orgType = this.findDimValue(dimensionSet, "MD_GCORGTYPE");
        this.currencyId = this.currency = this.findDimValue(dimensionSet, "MD_CURRENCY");
    }

    private String findDimValue(Map<String, DimensionValue> dimensionSet, String dimName) {
        DimensionValue dimensionValue = dimensionSet.get(dimName);
        if (null == dimensionValue) {
            return null;
        }
        return dimensionValue.getValue();
    }

    public String getOrgId() {
        return this.orgId;
    }

    public DimensionParamsVO setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public DimensionParamsVO setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
        return this;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Deprecated
    public String getOrgType() {
        if (StringUtils.isEmpty((String)this.orgType)) {
            return "MD_ORG_CORPORATE";
        }
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgTypeId() {
        String orgVersionType = this.getOrgVersionType();
        if (StringUtils.isEmpty((String)orgVersionType) || StringUtils.isEmpty((String)this.orgId) || StringUtils.isEmpty((String)this.schemeId) || StringUtils.isEmpty((String)this.periodStr)) {
            return null;
        }
        YearPeriodObject yp = new YearPeriodObject(this.schemeId, this.periodStr);
        GcOrgCacheVO org = GcOrgPublicTool.getInstance((String)orgVersionType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp).getOrgByCode(this.orgId);
        if (null == org) {
            String msg = GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.org.error", (Object[])new Object[]{orgVersionType, this.orgId, this.periodStr});
            throw new RuntimeException(msg);
        }
        return org.getOrgTypeId();
    }

    @Deprecated
    public DimensionParamsVO setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId;
        return this;
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getOrgVersionType() {
        String orgVersionType = null;
        if (!StringUtils.isEmpty((String)this.orgType)) {
            orgVersionType = this.orgType;
        } else if (!StringUtils.isEmpty((String)this.taskId)) {
            orgVersionType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId(this.taskId);
        } else if (!StringUtils.isEmpty((String)this.schemeId)) {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            FormSchemeDefine formSchemeDefine = runTimeViewController.getFormScheme(this.schemeId);
            orgVersionType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId(formSchemeDefine.getTaskKey());
        }
        if (StringUtils.isEmpty((String)orgVersionType)) {
            String msg = GcI18nUtil.getMessage((String)"gc.org.nr.uploadstatetool.orgversiontype.error", (Object[])new Object[]{this.taskId, this.schemeId});
            LOGGER.error(msg);
        }
        if (StringUtils.isEmpty((String)orgVersionType)) {
            orgVersionType = this.getOrgType();
        }
        return orgVersionType;
    }
}

