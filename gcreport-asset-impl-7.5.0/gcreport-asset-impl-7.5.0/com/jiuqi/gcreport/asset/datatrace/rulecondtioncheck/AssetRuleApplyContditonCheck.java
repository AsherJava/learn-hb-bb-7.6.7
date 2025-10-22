/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO
 *  com.jiuqi.gcreport.billcore.offsetcheck.enums.CheckStatusEnum
 *  com.jiuqi.gcreport.billcore.offsetcheck.enums.OffsetCheckInfoEnum
 *  com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheck
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.gcreport.asset.datatrace.rulecondtioncheck;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.asset.assetbill.dto.GcAssetBillGroupDTO;
import com.jiuqi.gcreport.asset.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.CheckStatusEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.OffsetCheckInfoEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheck;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetRuleApplyContditonCheck
implements RuleCondtionCheck {
    public static final String RULE_CONDITION_CODE = GcDataTraceTypeEnum.ZC.getType() + "_APPLY_CONTDITON";
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;

    public String getRuleContionType() {
        return GcDataTraceTypeEnum.ZC.getType();
    }

    public String getRuleContionCode() {
        return RULE_CONDITION_CODE;
    }

    public String getRuleContionTitle() {
        return "\u8d44\u4ea7\u89c4\u5219\u9002\u5e94\u6761\u4ef6\u6821\u9a8c";
    }

    public OffsetCheckResultDTO check(AbstractUnionRule unionRule, GcDataTraceCondi gcDataTraceCondi, boolean existOriginOffsetItem) {
        if (!RuleTypeEnum.FIXED_ASSETS.getCode().equals(unionRule.getRuleType())) {
            return null;
        }
        if (StringUtils.isEmpty((String)unionRule.getRuleCondition())) {
            return null;
        }
        String billCode = gcDataTraceCondi.getBillCode();
        DefaultTableEntity master = InvestBillTool.getEntityByBillCode((String)billCode, (String)"GC_COMMONASSETBILL");
        List items = InvestBillTool.listItemByMasterId(Arrays.asList((String)master.getFieldValue("ID")), (String)"GC_COMMONASSETBILLITEM");
        GcAssetBillGroupDTO gcAssetBillGroupDTO = new GcAssetBillGroupDTO(master, items);
        DimensionValueSet dset = new DimensionValueSet();
        dset.setValue("DATATIME", (Object)gcDataTraceCondi.getPeriodStr());
        dset.setValue("MD_CURRENCY", master.getFieldValue(gcDataTraceCondi.getCurrency()));
        dset.setValue("MD_GCORGTYPE", (Object)gcDataTraceCondi.getOrgType());
        dset.setValue("MD_ORG", master.getFieldValue("UNITCODE"));
        GcCalcArgmentsDTO calcArgmentsDTO = new GcCalcArgmentsDTO();
        calcArgmentsDTO.setPeriodStr(gcDataTraceCondi.getPeriodStr());
        calcArgmentsDTO.setTaskId(gcDataTraceCondi.getTaskId());
        calcArgmentsDTO.setSchemeId(this.getSchemeId(gcDataTraceCondi));
        calcArgmentsDTO.setCurrency((String)master.getFieldValue(gcDataTraceCondi.getCurrency()));
        calcArgmentsDTO.setOrgType(gcDataTraceCondi.getOrgType());
        calcArgmentsDTO.setOrgId((String)master.getFieldValue("UNITCODE"));
        calcArgmentsDTO.setSelectAdjustCode(gcDataTraceCondi.getSelectAdjustCode());
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl();
        env.setCalcArgments(calcArgmentsDTO);
        boolean isPass = this.billFormulaEvalService.checkAssetBillData((GcCalcEnvContext)env, dset, unionRule.getRuleCondition(), gcAssetBillGroupDTO);
        if (isPass) {
            return null;
        }
        return new OffsetCheckResultDTO(OffsetCheckInfoEnum.RULE_CONDITION_NOT_MET.getOffsetCheckSceneTypeName(), CheckStatusEnum.UNGENERATED.getCode());
    }

    private String getSchemeId(GcDataTraceCondi gcDataTraceCondi) {
        if (StringUtils.isEmpty((String)gcDataTraceCondi.getSchemeId())) {
            try {
                SchemePeriodLinkDefine schemePeriodLinkDefine = ((IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class)).querySchemePeriodLinkByPeriodAndTask(gcDataTraceCondi.getPeriodStr(), gcDataTraceCondi.getTaskId());
                return schemePeriodLinkDefine.getSchemeKey();
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u6839\u636e\u4efb\u52a1\u548c\u65f6\u671f\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848: " + e.getMessage(), (Throwable)e);
            }
        }
        return gcDataTraceCondi.getSchemeId();
    }
}

