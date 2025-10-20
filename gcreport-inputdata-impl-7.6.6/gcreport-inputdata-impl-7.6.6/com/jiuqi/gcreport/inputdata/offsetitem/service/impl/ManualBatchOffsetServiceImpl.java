/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.InputDataRuleExecutor;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.FlexibleRuleExecutorImpl;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset.AmtQueryAction;
import com.jiuqi.gcreport.inputdata.offsetitem.service.ManualBatchOffsetService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.ManualBatchOffsetParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ManualBatchOffsetServiceImpl
implements ManualBatchOffsetService {
    @Autowired
    private ConsolidatedTaskService consolidatedTaskCacheService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private InputDataDao inputdataDao;
    @Autowired
    private AmtQueryAction amtQueryAction;

    @Override
    public void manualBatchOffset(ManualBatchOffsetParamsVO manualBatchOffsetParamsVO) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(manualBatchOffsetParamsVO, queryParamsVO);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(manualBatchOffsetParamsVO.getTaskId());
        String systemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        if (systemId == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.manualbatchoffset.notsystemerrormsg"));
        }
        YearPeriodObject yp = new YearPeriodObject(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        ReadWriteAccessDesc readWriteAccessDesc = this.getCommonUnitStatus(queryParamsVO, tool);
        Assert.isTrue((boolean)readWriteAccessDesc.getAble(), (String)readWriteAccessDesc.getDesc(), (Object[])new Object[0]);
        HashSet<String> unitIds = new HashSet<String>();
        HashSet<String> oppUnitIds = new HashSet<String>();
        if (!CollectionUtils.isEmpty(manualBatchOffsetParamsVO.getUnitOrgList())) {
            unitIds.addAll(this.orgTreeToList(manualBatchOffsetParamsVO.getUnitOrgList()));
        }
        if (!CollectionUtils.isEmpty(manualBatchOffsetParamsVO.getOppUnitOrgList())) {
            oppUnitIds.addAll(this.orgTreeToList(manualBatchOffsetParamsVO.getOppUnitOrgList()));
        }
        queryParamsVO.setSystemId(systemId);
        queryParamsVO.setFixedUnitQueryPosition(false);
        queryParamsVO.setUnitIdList(new ArrayList(unitIds));
        queryParamsVO.setOppUnitIdList(new ArrayList(oppUnitIds));
        queryParamsVO.setPageNum(-1);
        GcOffsetExecutorVO gcOffsetExecutorVO = new GcOffsetExecutorVO(queryParamsVO.getActionCode(), queryParamsVO.getPageCode(), queryParamsVO.getDataSourceCode(), queryParamsVO.getFilterMethod(), (Object)queryParamsVO);
        Pagination pagination = (Pagination)this.amtQueryAction.execute(gcOffsetExecutorVO);
        List unOffsetDatas = pagination.getContent();
        List<InputDataEO> inputItems = this.inputdataDao.queryByManualBatchOffsetParams(queryParamsVO, ReportOffsetStateEnum.NOTOFFSET.getValue(), unOffsetDatas, manualBatchOffsetParamsVO);
        HashMap<String, AbstractUnionRule> ruleGroup = new HashMap<String, AbstractUnionRule>(16);
        HashMap<String, List<InputDataEO>> inputItemGroup = new HashMap<String, List<InputDataEO>>(16);
        this.groupInputItems(inputItems, ruleGroup, inputItemGroup, queryParamsVO.getRules(), manualBatchOffsetParamsVO.isChooseFilter());
        if (CollectionUtils.isEmpty(inputItemGroup)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.manualbatchoffset.nooffsetdatamsg"));
        }
        inputItemGroup.forEach((ruleId, sameGroupItems) -> {
            FlexibleRuleDTO rule = (FlexibleRuleDTO)ruleGroup.get(ruleId);
            InputDataRuleExecutor ruleProcessor = this.getRuleProcessor((AbstractUnionRule)rule);
            if (ruleProcessor == null) {
                return;
            }
            FlexibleRuleDTO manualBatchOffsetRule = new FlexibleRuleDTO();
            if (StringUtils.hasText(manualBatchOffsetParamsVO.getOffsetType())) {
                manualBatchOffsetRule.setOffsetType(OffsetTypeEnum.valueOf((String)manualBatchOffsetParamsVO.getOffsetType()));
            } else {
                manualBatchOffsetRule.setOffsetType(rule.getOffsetType());
            }
            if (manualBatchOffsetParamsVO.getUnilateralOffsetFlag() != null) {
                manualBatchOffsetRule.setUnilateralOffsetFlag(manualBatchOffsetParamsVO.getUnilateralOffsetFlag());
            } else {
                manualBatchOffsetRule.setUnilateralOffsetFlag(rule.getUnilateralOffsetFlag());
            }
            if (manualBatchOffsetParamsVO.getCanBothDc() != null) {
                manualBatchOffsetRule.setUnilateralOffsetFlag(Boolean.valueOf(manualBatchOffsetParamsVO.getCanBothDc() == false));
            }
            manualBatchOffsetRule.setToleranceRange(manualBatchOffsetParamsVO.getToleranceRange());
            manualBatchOffsetRule.setDiffSubjectCode(manualBatchOffsetParamsVO.getDiffSubjectCode());
            Map<String, Set<String>> offsetedOrgAndItemIdMapping = ruleProcessor.manualBatchOffset((AbstractUnionRule)rule, (AbstractUnionRule)manualBatchOffsetRule, (List<InputDataEO>)sameGroupItems);
            Set<String> offsetItems = offsetedOrgAndItemIdMapping.keySet();
            int offsetNum = CollectionUtils.isEmpty(offsetItems) ? 0 : offsetItems.size();
            String manualUnitCodes = CollectionUtils.isEmpty(manualBatchOffsetParamsVO.getUnitOrgList()) ? "" : manualBatchOffsetParamsVO.getUnitOrgList().toString();
            String manualOppUnitCodes = CollectionUtils.isEmpty(manualBatchOffsetParamsVO.getOppUnitOrgList()) ? "" : manualBatchOffsetParamsVO.getOppUnitOrgList().toString();
            String unitCodes = CollectionUtils.isEmpty(offsetItems) ? manualUnitCodes : offsetItems.toString();
            String message = String.format("\u81ea\u52a8\u62b5\u9500%1s\u7ec4\uff1b\u4efb\u52a1\uff1a%2s\uff1b\u65f6\u671f\uff1a%3s\uff1b\u5408\u5e76\u89c4\u5219:%4s\uff1b\u5408\u5e76\u5355\u4f4d\uff1a%7s\uff1b\u672c\u65b9\u5355\u4f4d:%5s\uff1b\u5bf9\u65b9\u5355\u4f4d:%6s\uff1b", offsetNum, taskDefine.getTitle(), manualBatchOffsetParamsVO.getPeriodStr(), rule.getLocalizedName(), unitCodes, manualOppUnitCodes, manualBatchOffsetParamsVO.getOrgId());
            LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u6279\u91cf\u624b\u52a8\u62b5\u9500-\u4efb\u52a1" + taskDefine.getTitle() + "-\u65f6\u671f" + manualBatchOffsetParamsVO.getPeriodStr()), (String)message);
        });
    }

    private void groupInputItems(List<InputDataEO> inputItems, Map<String, AbstractUnionRule> ruleGroup, Map<String, List<InputDataEO>> inputItemGroup, List<String> ruldIds, boolean chooseFilter) {
        inputItems.forEach(inputItem -> {
            if (ReportOffsetStateEnum.OFFSET.getValue().equals(inputItem.getOffsetState())) {
                return;
            }
            String ruleId = inputItem.getUnionRuleId();
            if (ruleId == null) {
                return;
            }
            AbstractUnionRule rule = (AbstractUnionRule)ruleGroup.get(ruleId);
            if (rule == null) {
                rule = UnionRuleUtils.getAbstractUnionRuleById((String)ruleId);
                ruleGroup.put(ruleId, rule);
            }
            if (rule == null) {
                return;
            }
            List sameGroupItems = inputItemGroup.computeIfAbsent(ruleId, k -> new ArrayList());
            sameGroupItems.add(inputItem);
        });
    }

    private InputDataRuleExecutor getRuleProcessor(AbstractUnionRule rule) {
        if (rule instanceof FlexibleRuleDTO) {
            return new FlexibleRuleExecutorImpl();
        }
        return null;
    }

    private Set<String> orgTreeToList(List<GcOrgCacheVO> orgs) {
        if (CollectionUtils.isEmpty(orgs)) {
            return Collections.emptySet();
        }
        HashSet<String> orgIds = new HashSet<String>();
        for (GcOrgCacheVO orgCacheVO : orgs) {
            if (orgCacheVO == null) continue;
            orgIds.add(orgCacheVO.getCode());
            if (orgCacheVO.isLeaf()) continue;
            orgIds.addAll(this.orgTreeToList(orgCacheVO.getChildren()));
        }
        return orgIds;
    }

    private ReadWriteAccessDesc getCommonUnitStatus(QueryParamsVO queryParams, GcOrgCenterService tool) {
        DimensionParamsVO params = new DimensionParamsVO();
        params.setTaskId(queryParams.getTaskId());
        params.setSchemeId(queryParams.getSchemeId());
        params.setCurrency(queryParams.getCurrency());
        params.setCurrencyId(queryParams.getCurrency());
        params.setOrgType(queryParams.getOrgType());
        params.setPeriodStr(queryParams.getPeriodStr());
        params.setSelectAdjustCode(queryParams.getSelectAdjustCode());
        GcOrgCacheVO commonUnit = tool.getOrgByCode(queryParams.getOrgId());
        if (commonUnit != null) {
            params.setOrgId(commonUnit.getCode());
            params.setOrgTypeId(commonUnit.getOrgTypeId());
            return new UploadStateTool().writeable(params);
        }
        return new ReadWriteAccessDesc(Boolean.valueOf(false), "\u6279\u91cf\u624b\u52a8\u62b5\u9500\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u4e3a\u7a7a\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500");
    }
}

