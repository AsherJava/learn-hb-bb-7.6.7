/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.offsetitem.api.GcOffSetAppClient
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffSetItemAdjustExecutorImpl
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.offsetitem.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.offsetitem.api.GcOffSetAppClient;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffSetItemAdjustExecutorImpl;
import com.jiuqi.gcreport.offsetitem.service.GcInputAdjustService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.task.OffsetPenetrateFactory;
import com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcOffSetAppController
implements GcOffSetAppClient {
    @Autowired
    private GcOffSetItemAdjustCoreService adjustCoreService;
    @Autowired
    private GcOffSetAppOffsetService adjustingEntryService;
    @Autowired
    private OffsetPenetrateFactory offsetPenetrateFactory;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private GcOffSetItemAdjustExecutorImpl gcOffSetItemAdjustExecutor;
    @Autowired
    private GcInputAdjustService inputAdjustService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BusinessResponseEntity<Pagination<Map<String, Object>>> getOffsetEntry(@Valid @RequestBody QueryParamsVO queryParamsVO) {
        queryParamsVO.setFilterDisableItem(false);
        Pagination<Map<String, Object>> pagination = this.adjustingEntryService.listOffsetRecordsForAction(queryParamsVO);
        return BusinessResponseEntity.ok(pagination);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteOffsetEntrysByMrecid(@RequestBody QueryParamsVO queryParamsVO) {
        List mrecids = queryParamsVO.getMrecids();
        CalcLogUtil.getInstance().log(this.getClass(), "deleteOffsetEntrys-\u754c\u9762\u52fe\u9009\u53d6\u6d88", (Object)mrecids);
        Collection srcOffsetGroupIds = this.adjustCoreService.listOffsetGroupIdsByMrecid((Collection)mrecids);
        if (!CollectionUtils.isEmpty((Collection)srcOffsetGroupIds)) {
            GcTaskBaseArguments gcTaskBaseArguments = new GcTaskBaseArguments();
            gcTaskBaseArguments.setAcctYear(queryParamsVO.getAcctYear());
            gcTaskBaseArguments.setAcctPeriod(queryParamsVO.getAcctPeriod());
            gcTaskBaseArguments.setCurrency(queryParamsVO.getCurrency());
            this.inputAdjustService.deleteFutureMonthByOffsetGroupId(srcOffsetGroupIds, OffSetSrcTypeEnum.MODIFIED_INPUT.getSrcTypeValue(), gcTaskBaseArguments);
        }
        this.adjustingEntryService.batchDelete(mrecids, queryParamsVO.getTaskId(), queryParamsVO.getAcctYear(), queryParamsVO.getAcctPeriod(), null, queryParamsVO.getCurrency());
        return BusinessResponseEntity.ok((Object)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.cancelSuccess"));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteOffsetEntrys(@Valid @RequestBody QueryParamsVO queryParamsVO) {
        queryParamsVO.setDelete(true);
        ArrayList<Integer> forbidOffSetSrcTypes = new ArrayList<Integer>();
        forbidOffSetSrcTypes.add(OffSetSrcTypeEnum.CONNECTED_TRANSACTION.getSrcTypeValue());
        queryParamsVO.setForbidOffSetSrcTypes(forbidOffSetSrcTypes);
        CalcLogUtil.getInstance().log(this.getClass(), "deleteOffsetEntrys-\u62b5\u9500\u5206\u5f55\u6279\u91cf\u5220\u9664", (Object)queryParamsVO);
        Set<String> mrecids = this.adjustingEntryService.deleteAllOffsetEntrys(queryParamsVO);
        this.doDeleteLogByParam(queryParamsVO, mrecids.size());
        return BusinessResponseEntity.ok((Object)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.cancelOffsetNum", (Object[])new Object[]{mrecids.size()}));
    }

    private void doDeleteLogByParam(QueryParamsVO queryParamsVO, int size) {
        try {
            String taskTitle = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId()).getTitle();
            String defaultPeriod = queryParamsVO.getPeriodStr();
            StringBuilder elmModeTitleSb = new StringBuilder();
            List elmModes = queryParamsVO.getElmModes();
            Iterator iterator = elmModes.iterator();
            while (iterator.hasNext()) {
                int elmMode = (Integer)iterator.next();
                elmModeTitleSb.append(OffsetElmModeEnum.getElmModeTitle((Integer)elmMode));
            }
            StringBuilder ruleTitleSb = new StringBuilder();
            for (String rule : queryParamsVO.getRules()) {
                ruleTitleSb.append(this.unionRuleService.selectUnionRuleDTOById(rule).getLocalizedName());
            }
            StringBuilder message = new StringBuilder("\u53d6\u6d88\u62b5\u9500" + size + "\u7ec4\uff1a\n");
            message.append("\u4efb\u52a1-").append(taskTitle).append("\uff1b\u65f6\u671f-").append(defaultPeriod).append("\uff1b\u5408\u5e76\u5355\u4f4d-").append(queryParamsVO.getOrgId()).append("\uff1b\u5408\u5e76\u89c4\u5219-").append((CharSequence)ruleTitleSb).append("\uff1b\u62b5\u9500\u65b9\u5f0f-").append((CharSequence)elmModeTitleSb).append("\n");
            LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u5206\u7c7b\u53d6\u6d88\u62b5\u9500-\u4efb\u52a1" + taskTitle + "-\u65f6\u671f" + defaultPeriod), (String)message.toString());
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u53d6\u6d88\u62b5\u9500\u5206\u5f55\u65e5\u5fd7\u51fa\u9519:" + e.getMessage(), e);
        }
    }

    public BusinessResponseEntity<List<DesignFieldDefineVO>> queryOffsetColumnSelect() {
        List<DesignFieldDefineVO> offsetColumnSelects = this.adjustingEntryService.listOffsetColumnSelects();
        return BusinessResponseEntity.ok(offsetColumnSelects);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> cancelRuleData(@RequestParam(value="taskId") String taskId, @RequestParam(value="systemId") String systemId, @RequestParam(value="periodStr") String periodStr, @RequestParam(value="ruleId") String ruleId, @RequestBody List<String> subjectCode, @RequestParam(value="orgType") String orgType, @RequestParam(value="selectAdjustCode") String selectAdjustCode) {
        this.adjustingEntryService.cancelRuleData(taskId, systemId, periodStr, ruleId, subjectCode, orgType, selectAdjustCode);
        CalcLogUtil.getInstance().log(this.getClass(), "cancelRuleData-\u53d6\u6d88\u89c4\u5219", taskId, systemId, periodStr, ruleId, subjectCode, orgType);
        return BusinessResponseEntity.ok((Object)GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.cancelSuccess"));
    }

    public BusinessResponseEntity<List<GcOrgCacheVO>> getOffsetOrgData(@RequestBody QueryParamsVO queryParamsVO) {
        return BusinessResponseEntity.ok(this.adjustingEntryService.getOffsetOrgData(queryParamsVO));
    }

    public BusinessResponseEntity<List<GcOrgCacheVO>> getMergeUnitOrgData(@RequestBody QueryParamsVO queryParamsVO) {
        return BusinessResponseEntity.ok(this.adjustingEntryService.getMergeUnitOrgData(queryParamsVO));
    }

    public BusinessResponseEntity<List<GcOffSetVchrItemVO>> writeOffShow(@PathVariable(value="mrecids") @RequestBody List<String> mrecids, @RequestBody QueryParamsVO queryParamsVO) {
        List<GcOffSetVchrItemVO> offSetVchrItemVOS = this.adjustingEntryService.writeOffShow(mrecids, queryParamsVO);
        return BusinessResponseEntity.ok(offSetVchrItemVOS);
    }

    public BusinessResponseEntity<String> writeOffSave(@PathVariable(value="mrecids") @RequestBody List<String> mrecids, @RequestBody QueryParamsVO queryParamsVO) {
        this.adjustingEntryService.writeOffSave(mrecids, queryParamsVO);
        return BusinessResponseEntity.ok((Object)"\u51b2\u9500\u6210\u529f\u3002");
    }

    public BusinessResponseEntity<GcOrgCacheVO> getHBOrgByCE(@PathVariable(value="orgType") String orgType, @PathVariable(value="yyyyMMdd") String periodStr, @PathVariable(value="orgCode") String orgCode) {
        return BusinessResponseEntity.ok((Object)this.adjustingEntryService.getHBOrgByCE(orgType, periodStr, orgCode));
    }

    public List<Map<String, Object>> allowDelAll() {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> allow = new HashMap<String, Object>();
        allow.put("title", "\u662f");
        allow.put("key", 1);
        result.add(allow);
        HashMap<String, Object> notAllow = new HashMap<String, Object>();
        notAllow.put("title", "\u5426");
        notAllow.put("key", 0);
        result.add(notAllow);
        return result;
    }

    public BusinessResponseEntity<String> updateOffsetDisabledFlag(List<String> mrecids, boolean isDisabled) {
        this.adjustingEntryService.updateOffsetDisabledFlag(mrecids, isDisabled);
        return BusinessResponseEntity.ok();
    }

    public List<Map<String, Object>> allowDisableOffset() {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> allow = new HashMap<String, Object>();
        allow.put("title", "\u662f");
        allow.put("key", 1);
        result.add(allow);
        HashMap<String, Object> notAllow = new HashMap<String, Object>();
        notAllow.put("title", "\u5426");
        notAllow.put("key", 0);
        result.add(notAllow);
        return result;
    }

    public BusinessResponseEntity<Object> updateMemo(Map<String, Object> params) {
        this.adjustingEntryService.updateMemo(params);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> getMemoLength(String tabName) {
        return BusinessResponseEntity.ok((Object)this.adjustingEntryService.getMemoLength(tabName));
    }

    public BusinessResponseEntity<Object> getPenetrateData(String mrecid, String ruleId, String ruleType, String orgType) {
        return BusinessResponseEntity.ok(this.offsetPenetrateFactory.getOffsetPenetrateType(ruleType).getPenetrateData(mrecid, ruleId, orgType));
    }

    public BusinessResponseEntity<Object> listSumTabRecords(@Valid @RequestBody QueryParamsVO queryParamsVO) {
        GcOffsetItemUtils.logOffsetEntryFilterCondition(queryParamsVO, "\u672c\u7ea7\u5168\u90e8");
        Pagination<Map<String, Object>> pagination = this.adjustingEntryService.listSumTabRecords(queryParamsVO);
        return BusinessResponseEntity.ok(pagination);
    }

    public BusinessResponseEntity<String> queryOffsetButtons(GcOffsetExecutorVO gcOffsetExecutorVO) {
        return BusinessResponseEntity.ok((Object)this.gcOffSetItemAdjustExecutor.listButtonsForCode(gcOffsetExecutorVO));
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<Object> deleteAdjust(@RequestBody List<String> mrecids, String orgType, String periodStr) {
        CalcLogUtil.getInstance().log(this.getClass(), "\u62b5\u9500\u521d\u59cb\u52fe\u9009\u5220\u9664mrecids", (Object)mrecids);
        this.adjustingEntryService.batchDelete(mrecids, null, null, 0, null, null);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<ReadWriteAccessDesc> writeableByOrgCodeAndDiffCode(DimensionParamsVO queryParamsVO) {
        return BusinessResponseEntity.ok((Object)this.adjustingEntryService.writeableByOrgCodeAndDiffCode(queryParamsVO));
    }
}

