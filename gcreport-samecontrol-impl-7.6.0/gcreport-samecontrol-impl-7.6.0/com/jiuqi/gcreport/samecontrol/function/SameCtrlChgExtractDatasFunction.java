/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.samecontrol.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlOffSetItemService;
import com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class SameCtrlChgExtractDatasFunction
extends AdvanceFunction
implements INrFunction {
    @Lazy
    @Autowired
    private transient SameCtrlChgOrgService sameCtrlChgOrgService;
    @Lazy
    @Autowired
    private transient IRunTimeViewController runTimeViewController;
    @Lazy
    @Resource(name="sameCtrlBeginOffSetItemServiceImpl")
    private transient SameCtrlOffSetItemService beginOffSetItemService;
    @Lazy
    @Resource(name="sameCtrlEndOffSetItemServiceImpl")
    private transient SameCtrlOffSetItemService endOffSetItemService;
    @Lazy
    @Autowired
    private transient SameCtrlExtractDataService sameCtrlExtractDataService;

    public String name() {
        return "CalTkData";
    }

    public String title() {
        return "\u540c\u63a7\u63d0\u53d6\u62a5\u8868\u6570\u636e\u548c\u8c03\u6574\u62b5\u9500\u5206\u5f55\u6570\u636e";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 6;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public String evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return this.extractReportAndOffsetDatas((QueryContext)iContext);
    }

    private String extractReportAndOffsetDatas(QueryContext iContext) {
        ChangeOrgCondition changeCondition = this.getChangeCondition(iContext);
        if (changeCondition == null) {
            return "";
        }
        List<SameCtrlChgOrgVO> chgOrgVOS = this.sameCtrlChgOrgService.listSameCtrlChgOrgs(changeCondition);
        if (CollectionUtils.isEmpty(chgOrgVOS)) {
            return "";
        }
        YearPeriodObject yp = new YearPeriodObject(null, changeCondition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)changeCondition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        List allChildrenOrgs = tool.listAllOrgByParentIdContainsSelf(changeCondition.getOrgCode());
        Set<String> allChildrenOrgCodes = allChildrenOrgs.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toSet());
        this.extractEndOffsetAndReportDatas(changeCondition, chgOrgVOS, allChildrenOrgCodes);
        this.extractBeginOffsetData(changeCondition, chgOrgVOS, allChildrenOrgCodes);
        return "";
    }

    private void extractEndOffsetAndReportDatas(ChangeOrgCondition changeCondition, List<SameCtrlChgOrgVO> chgOrgVOS, Set<String> allChildrenOrgCodes) {
        List disposeChgOrgs = chgOrgVOS.stream().filter(chgOrgVO -> allChildrenOrgCodes.contains(chgOrgVO.getVirtualParentCode()) && !changeCondition.getOrgCode().equals(chgOrgVO.getSameParentCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(disposeChgOrgs)) {
            return;
        }
        SameCtrlOffsetCond sameCtrlOffsetCond = new SameCtrlOffsetCond();
        BeanUtils.copyProperties(changeCondition, sameCtrlOffsetCond);
        sameCtrlOffsetCond.setCurrencyCode(changeCondition.getCurrencyId());
        sameCtrlOffsetCond.setMergeUnitCode(changeCondition.getOrgCode());
        sameCtrlOffsetCond.setExtractAllParentsUnitFlag(false);
        SameCtrlExtractReportCond sameCtrlExtractReportData = new SameCtrlExtractReportCond();
        BeanUtils.copyProperties(changeCondition, sameCtrlExtractReportData);
        sameCtrlExtractReportData.setFormulaSchemeKey(changeCondition.getSchemeId());
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl();
        for (SameCtrlChgOrgVO disposeChgOrg : disposeChgOrgs) {
            sameCtrlOffsetCond.setSameCtrlChgId(disposeChgOrg.getId());
            sameCtrlOffsetCond.setChangedUnitCode(disposeChgOrg.getChangedCode());
            sameCtrlOffsetCond.setSameParentCode(disposeChgOrg.getSameParentCode());
            sameCtrlChgEnvContext.setSameCtrlOffsetCond(sameCtrlOffsetCond);
            this.endOffSetItemService.extractData(sameCtrlChgEnvContext);
            sameCtrlExtractReportData.setChangedCode(disposeChgOrg.getChangedCode());
            sameCtrlExtractReportData.setChangeDate(disposeChgOrg.getChangeDate());
            sameCtrlChgEnvContext.setSameCtrlExtractReportCond(sameCtrlExtractReportData);
            this.sameCtrlExtractDataService.extractReportData(sameCtrlChgEnvContext);
        }
    }

    private void extractBeginOffsetData(ChangeOrgCondition changeCondition, List<SameCtrlChgOrgVO> chgOrgVOS, Set<String> allChildrenOrgCodes) {
        List acquirerChgOrgs = chgOrgVOS.stream().filter(chgOrgVO -> allChildrenOrgCodes.contains(chgOrgVO.getChangedParentCode()) && !changeCondition.getOrgCode().equals(chgOrgVO.getSameParentCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(acquirerChgOrgs)) {
            return;
        }
        SameCtrlOffsetCond sameCtrlOffsetCondBegin = new SameCtrlOffsetCond();
        BeanUtils.copyProperties(changeCondition, sameCtrlOffsetCondBegin);
        sameCtrlOffsetCondBegin.setCurrencyCode(changeCondition.getCurrencyId());
        sameCtrlOffsetCondBegin.setExtractAllParentsUnitFlag(false);
        sameCtrlOffsetCondBegin.setMergeUnitCode(changeCondition.getOrgCode());
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl();
        for (SameCtrlChgOrgVO acquirerChgOrg : acquirerChgOrgs) {
            sameCtrlOffsetCondBegin.setSameCtrlChgId(acquirerChgOrg.getId());
            sameCtrlOffsetCondBegin.setChangedUnitCode(acquirerChgOrg.getChangedCode());
            sameCtrlOffsetCondBegin.setSameParentCode(acquirerChgOrg.getSameParentCode());
            sameCtrlChgEnvContext.setSameCtrlOffsetCond(sameCtrlOffsetCondBegin);
            this.beginOffSetItemService.extractData(sameCtrlChgEnvContext);
        }
    }

    private ChangeOrgCondition getChangeCondition(QueryContext iContext) {
        ChangeOrgCondition changeCondition = new ChangeOrgCondition();
        DimensionValueSet ds = iContext.getMasterKeys();
        String orgTypeCode = (String)ds.getValue("MD_GCORGTYPE");
        String dataTime = (String)ds.getValue("DATATIME");
        String orgCode = (String)ds.getValue("MD_ORG");
        String currencyCode = (String)ds.getValue("MD_CURRENCY");
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgTypeCode, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(orgCode);
        if (GcOrgKindEnum.DIFFERENCE != org.getOrgKind()) {
            return null;
        }
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)iContext.getExeContext().getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        changeCondition.setTaskId(formScheme.getTaskKey());
        changeCondition.setSchemeId(formSchemeKey);
        changeCondition.setPeriodStr(dataTime);
        changeCondition.setOrgCode(org.getMergeUnitId());
        changeCondition.setAdministrator(Boolean.valueOf(false));
        changeCondition.setOrgType(orgTypeCode);
        changeCondition.setCurrencyId(currencyCode);
        changeCondition.setAllChildren(Boolean.valueOf(false));
        return changeCondition;
    }
}

