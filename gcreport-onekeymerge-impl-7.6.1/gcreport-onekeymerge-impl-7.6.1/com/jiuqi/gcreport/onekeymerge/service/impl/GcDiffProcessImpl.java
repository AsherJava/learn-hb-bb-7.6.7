/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NrSqlUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.nr.api.GCFormTabSelectClient
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.nr.vo.FormTreeVo
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessDataVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.rewritesetting.dao.RewriteSettingDao
 *  com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO
 *  com.jiuqi.gcreport.temp.dto.Message$ProgressResult
 *  com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.util.FloatOrderGenerator
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NrSqlUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.nr.api.GCFormTabSelectClient;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.vo.FormTreeVo;
import com.jiuqi.gcreport.onekeymerge.service.FloatBalanceDiffService;
import com.jiuqi.gcreport.onekeymerge.service.GcDiffProcess;
import com.jiuqi.gcreport.onekeymerge.service.GcDiffProcessService;
import com.jiuqi.gcreport.onekeymerge.service.IFloatBalanceService;
import com.jiuqi.gcreport.onekeymerge.task.GcFinishCalcTaskImpl;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessDataVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.rewritesetting.dao.RewriteSettingDao;
import com.jiuqi.gcreport.rewritesetting.entity.RewriteSettingEO;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.util.FloatOrderGenerator;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GcDiffProcessImpl
extends GcDiffProcess
implements GcDiffProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcDiffProcessImpl.class);
    @Autowired
    private GCFormTabSelectClient formTabSelectApi;
    @Autowired
    private IFloatBalanceService floatBalanceService;
    @Autowired
    private FloatBalanceDiffService floatBalanceDiffService;
    @Autowired
    private RewriteSettingDao rewriteSettingDao;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private ProgressService<OnekeyProgressDataImpl, Message.ProgressResult> progressService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public List<FormTreeVo> queryDiffProcessReports(String formSchemeKey, String dataTime) {
        try {
            BusinessResponseEntity reportGruops = this.formTabSelectApi.queryFormTree(formSchemeKey, dataTime);
            ArrayList<FormTreeVo> formTree = new ArrayList<FormTreeVo>();
            formTree.addAll((Collection)reportGruops.getData());
            List rewriteSettingEOS = this.rewriteSettingDao.queryRewriteSettings(formSchemeKey);
            List<String> formKeys = rewriteSettingEOS.stream().map(RewriteSettingEO::getInsideFormKey).collect(Collectors.toList());
            this.filterFormTree(formTree, formKeys);
            return formTree;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u62a5\u8868\u6811\u51fa\u9519", (Throwable)e);
        }
    }

    private void filterFormTree(List<FormTreeVo> formTreeItemList, List<String> formKeys) {
        if (formTreeItemList == null || formTreeItemList.size() == 0) {
            return;
        }
        for (int i = formTreeItemList.size() - 1; i >= 0; --i) {
            FormTreeVo item = formTreeItemList.get(i);
            if (item.getChildren() != null && item.getChildren().size() > 0) {
                this.filterFormTree(item.getChildren(), formKeys);
            }
            if (item.getChildren() != null && item.getChildren().size() != 0 || formKeys.contains(item.getId())) continue;
            formTreeItemList.remove(i);
        }
    }

    @Override
    public List<GcDiffProcessDataVO> queryDifferenceIntermediateDatas(GcDiffProcessCondition condition) {
        ArrayList<GcDiffProcessDataVO> datas;
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        try {
            GcActionParamsVO params = new GcActionParamsVO();
            BeanUtils.copyProperties(condition, params);
            List<RewriteSettingEO> rewriteSettingEOS = this.getRewriteSettings(condition);
            RewriteSettingEO rewriteSettingEO = rewriteSettingEOS.get(0);
            List<String> subjectCodes = GcDiffProcessImpl.listSubjectsByFormKeyAndTableCode(condition, rewriteSettingEO.getInsideTableName());
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(rewriteSettingEO.getInsideTableName());
            List<Map<String, Object>> diffDatas = this.floatBalanceService.queryDifferenceIntermediateDatas(subjectCodes, params, tableModelDefine.getName());
            HashMap mdOrgIsDiff = new HashMap();
            List directOrgDatas = diffDatas.stream().filter(item -> this.isDiffOrg(item.get("MDCODE").toString(), orgCenterTool, mdOrgIsDiff) == false).collect(Collectors.toList());
            Map<ArrayKey, List<Map<String, Object>>> directFloatBalacesMap = directOrgDatas.stream().collect(Collectors.groupingBy(item -> new ArrayKey(new Object[]{item.get("ACCTORGCODE"), "_", item.get("OPPUNITCODE"), "_", item.get("SUBJECTCODE")})));
            Map<ArrayKey, GcDiffProcessDataVO> diffProcessDataVOMap = this.sumGroupFloatBalance(directFloatBalacesMap);
            List<Map<String, Object>> offsetSumData = this.getOffsetSumData(condition);
            String reportSystemId = this.taskService.getConsolidatedSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
            Set<String> allRelateSubjectCodes = this.getSubject2TableNameMap(rewriteSettingEOS, reportSystemId);
            offsetSumData = offsetSumData.stream().filter(data -> 0 == (Integer)data.get("OFFSETSRCTYPE") && allRelateSubjectCodes.contains(data.get("SUBJECTCODE"))).collect(Collectors.toList());
            Map<ArrayKey, List<Map<String, Object>>> diffFloatBalacesMap = offsetSumData.stream().collect(Collectors.groupingBy(item -> new ArrayKey(new Object[]{item.get("UNITID"), "_", item.get("OPPUNITID"), "_", item.get("SUBJECTCODE")})));
            this.sumDiffOrgFloatBalance(diffProcessDataVOMap, diffFloatBalacesMap, orgCenterTool, condition);
            diffProcessDataVOMap.values().forEach(item -> item.setDiffAmt(item.getEndAmt().add(item.getOffsetAmt())));
            datas = new ArrayList<GcDiffProcessDataVO>(diffProcessDataVOMap.values());
        }
        catch (Exception e) {
            LOGGER.error("\u5dee\u989d\u5904\u7406\u4e2d\u95f4\u6570\u636e\u67e5\u8be2\u51fa\u9519:" + e.getMessage(), e);
            throw new BusinessRuntimeException("\u5dee\u989d\u5904\u7406\u4e2d\u95f4\u6570\u636e\u67e5\u8be2\u51fa\u9519:" + e.getMessage(), (Throwable)e);
        }
        return datas;
    }

    private List<RewriteSettingEO> getRewriteSettings(GcDiffProcessCondition condition) {
        List<RewriteSettingEO> rewriteSettingEos = this.rewriteSettingDao.queryRewriteSettings(condition.getSchemeId());
        if ((rewriteSettingEos = rewriteSettingEos.stream().filter(eo -> condition.getCurrFormKey().equalsIgnoreCase(eo.getInsideFormKey()) && condition.getCurrFormKey().equalsIgnoreCase(eo.getOutsideFormKey())).collect(Collectors.toList())).size() == 0) {
            throw new BusinessRuntimeException("\u8bf7\u8bbe\u7f6e\u5f53\u524d\u62a5\u8868\u7684\u56de\u5199\u8bbe\u7f6e\uff01");
        }
        return rewriteSettingEos;
    }

    private Map<ArrayKey, GcDiffProcessDataVO> sumGroupFloatBalance(Map<ArrayKey, List<Map<String, Object>>> diffFloatBalacesMap) {
        HashMap<ArrayKey, GcDiffProcessDataVO> diffProcessDataVOMap = new HashMap<ArrayKey, GcDiffProcessDataVO>();
        for (Map.Entry<ArrayKey, List<Map<String, Object>>> entry : diffFloatBalacesMap.entrySet()) {
            BigDecimal sumValue = entry.getValue().stream().map(data -> new BigDecimal(data.get("AMT") == null ? "0" : data.get("AMT").toString())).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            GcDiffProcessDataVO diffProcessDataVO = new GcDiffProcessDataVO();
            diffProcessDataVO.setEndAmt(sumValue);
            diffProcessDataVO.setOffsetAmt(BigDecimal.ZERO);
            this.assemblyDiffData(entry, diffProcessDataVO, diffProcessDataVOMap);
        }
        return diffProcessDataVOMap;
    }

    private void sumDiffOrgFloatBalance(Map<ArrayKey, GcDiffProcessDataVO> diffProcessDataVOMap, Map<ArrayKey, List<Map<String, Object>>> diffFloatBalacesMap, GcOrgCenterService orgCenterTool, GcDiffProcessCondition condition) {
        String reportSystemId = this.taskService.getConsolidatedSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        HashMap unitTitleMap = new HashMap();
        HashMap subjectTitleMap = new HashMap();
        diffFloatBalacesMap.entrySet().forEach(entry -> {
            GcDiffProcessDataVO vo = diffProcessDataVOMap.get(entry.getKey()) == null ? new GcDiffProcessDataVO() : (GcDiffProcessDataVO)diffProcessDataVOMap.get(entry.getKey());
            BigDecimal sumValue = BigDecimal.ZERO;
            for (Map sumData : (List)entry.getValue()) {
                BigDecimal creditValue = BigDecimal.valueOf((Double)sumData.get("CREDITVALUE"));
                BigDecimal debitValue = BigDecimal.valueOf((Double)sumData.get("DEBITVALUE"));
                BigDecimal amt = BigDecimal.ZERO.add(debitValue).subtract(creditValue);
                int subjectOrient = (Integer)sumData.get("SUBJECTORIENT");
                sumValue = sumValue.add(amt.multiply(new BigDecimal(subjectOrient)));
            }
            vo.setOffsetAmt(sumValue);
            if (diffProcessDataVOMap.get(entry.getKey()) == null) {
                if (!((List)entry.getValue()).isEmpty()) {
                    diffProcessDataVOMap.put((ArrayKey)entry.getKey(), vo);
                    Map data = (Map)((List)entry.getValue()).get(0);
                    vo.setUnitTitle(this.getUnitTitle(orgCenterTool, unitTitleMap, (String)data.get("UNITID")));
                    vo.setOppUnitTitle(this.getUnitTitle(orgCenterTool, unitTitleMap, (String)data.get("OPPUNITID")));
                    vo.setSubjectTitle(this.getSubjectTitle(reportSystemId, subjectTitleMap, (String)data.get("SUBJECTCODE")));
                    vo.setSubjectOrient((Integer)data.get("SUBJECTORIENT"));
                }
                vo.setEndAmt(new BigDecimal(0));
            }
        });
    }

    private String getSubjectTitle(String reportSystemId, Map<String, String> subjectTitleMap, String subjectCode) {
        if (subjectTitleMap.containsKey(subjectCode)) {
            return subjectTitleMap.get(subjectCode);
        }
        ConsolidatedSubjectEO subjectEO = this.subjectService.getSubjectByCode(reportSystemId, subjectCode);
        if (subjectEO != null) {
            subjectTitleMap.put(subjectCode, subjectEO.getTitle());
            return subjectEO.getTitle();
        }
        return "";
    }

    private String getUnitTitle(GcOrgCenterService orgCenterTool, Map<String, String> unitTitleMap, String unitId) {
        if (unitTitleMap.containsKey(unitId)) {
            return unitTitleMap.get(unitId);
        }
        GcOrgCacheVO orgCacheVO = orgCenterTool.getOrgByCode(unitId);
        if (orgCacheVO != null) {
            unitTitleMap.put(unitId, orgCacheVO.getTitle());
            return orgCacheVO.getTitle();
        }
        return "";
    }

    private void assemblyDiffData(Map.Entry<ArrayKey, List<Map<String, Object>>> entry, GcDiffProcessDataVO diffProcessDataVO, Map<ArrayKey, GcDiffProcessDataVO> diffProcessDataVOMap) {
        if (entry.getValue().isEmpty()) {
            return;
        }
        Map<String, Object> data = entry.getValue().get(0);
        diffProcessDataVO.setUnitTitle((String)data.get("UNITNAME"));
        diffProcessDataVO.setOppUnitTitle((String)data.get("OPPUNITNAME"));
        diffProcessDataVO.setSubjectTitle((String)data.get("SUBJECTTITLE"));
        diffProcessDataVO.setSubjectOrient((Integer)data.get("ORIENT"));
        diffProcessDataVOMap.put(entry.getKey(), diffProcessDataVO);
    }

    private Boolean isDiffOrg(String mdOrg, GcOrgCenterService orgCenterTool, Map<String, Boolean> mdOrgIsDiff) {
        Boolean isDiffOrg = mdOrgIsDiff.get(mdOrg);
        if (isDiffOrg == null) {
            GcOrgCacheVO orgCacheVO = orgCenterTool.getOrgByCode(mdOrg);
            if (orgCacheVO != null && orgCacheVO.getOrgKind().equals((Object)GcOrgKindEnum.DIFFERENCE)) {
                isDiffOrg = true;
                mdOrgIsDiff.put(mdOrg, true);
            } else {
                isDiffOrg = false;
                mdOrgIsDiff.put(mdOrg, false);
            }
        }
        return isDiffOrg;
    }

    @Override
    public void transferGroupWithinToOutside(GcDiffProcessCondition condition) {
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = orgCenterTool.getOrgByCode(condition.getOrgId());
        GcOrgCacheVO diffOrgCacheVO = orgCenterTool.getOrgByCode(orgCacheVO.getDiffUnitId());
        this.checkOrgAndFormUploadState(condition, orgCacheVO, orgCenterTool);
        TaskLog taskLog = this.initTaskLog(condition.getSn());
        try {
            taskLog.writeInfoLog("\u6b63\u5728\u51c6\u5907\u53c2\u6570...", Float.valueOf(5.0f)).syncTaskLog();
            GcActionParamsVO params = new GcActionParamsVO();
            BeanUtils.copyProperties(condition, params);
            List rewriteSettings = this.rewriteSettingDao.queryRewriteSettings(condition.getSchemeId());
            Map<String, List<RewriteSettingEO>> rewriteSettingMap = rewriteSettings.stream().collect(Collectors.groupingBy(RewriteSettingEO::getRewSetGroupId));
            taskLog.writeInfoLog("\u5f00\u59cb\u56de\u5199\u5dee\u989d\u5355\u4f4d...", Float.valueOf(15.0f)).syncTaskLog();
            Date nowDate = DateUtils.now();
            this.rewriteFloatBalaceData(condition, orgCacheVO, diffOrgCacheVO, params, rewriteSettingMap);
            taskLog.writeInfoLog("\u56de\u5199\u5dee\u989d\u5355\u4f4d\u5b8c\u6210,\u8017\u65f6:" + DateUtils.diffOf((Date)nowDate, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(50.0f));
            taskLog.writeInfoLog("\u5f00\u59cb\u5dee\u989d\u5355\u4f4d\u6267\u884c\u5168\u7b97", Float.valueOf(51.0f)).syncTaskLog();
            nowDate = DateUtils.now();
            GcFinishCalcTaskImpl gcFinishCalcTask = (GcFinishCalcTaskImpl)SpringContextUtils.getBean(GcFinishCalcTaskImpl.class);
            params.setTaskLogId(condition.getSn());
            List<String> diffUnitIds = Collections.singletonList(orgCacheVO.getDiffUnitId());
            gcFinishCalcTask.calculateForm(params, diffUnitIds, taskLog);
            taskLog.writeInfoLog("\u5dee\u989d\u5355\u4f4d\u6267\u884c\u5168\u7b97\u7ed3\u675f,\u8017\u65f6:" + DateUtils.diffOf((Date)nowDate, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(70.0f));
            taskLog.writeInfoLog("\u5f00\u59cb\u5408\u5e76\u5355\u4f4d\u6570\u636e\u6c47\u603b", Float.valueOf(71.0f)).syncTaskLog();
            nowDate = DateUtils.now();
            List<String> hbUnitids = Collections.singletonList(params.getOrgId());
            String currFormkey = condition.isAllDifferenceProcess() ? null : condition.getCurrFormKey();
            gcFinishCalcTask.dataSum(params, hbUnitids, taskLog, currFormkey);
            taskLog.writeInfoLog("\u5408\u5e76\u5355\u4f4d\u6267\u884c\u6c47\u603b\u7ed3\u675f,\u8017\u65f6:" + DateUtils.diffOf((Date)nowDate, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(95.0f));
            this.clearSumZbEmptyRow(condition, rewriteSettings);
            taskLog.writeInfoLog("\u8f6c\u5165\u96c6\u56e2\u5916\u7ed3\u675f", Float.valueOf(100.0f));
            taskLog.setFinish(true);
            taskLog.setState(TaskStateEnum.SUCCESS);
            taskLog.syncTaskLog();
        }
        catch (Exception e) {
            taskLog.setFinish(true);
            taskLog.setState(TaskStateEnum.ERROR);
            taskLog.setProcessPercent(Float.valueOf(100.0f));
            LOGGER.error("\u5dee\u989d\u5904\u7406\u8f6c\u5165\u96c6\u56e2\u5916\u5f02\u5e38:" + e.getMessage(), e);
            throw new BusinessRuntimeException("\u5dee\u989d\u5904\u7406\u8f6c\u5165\u96c6\u56e2\u5916\u5f02\u5e38:" + e.getMessage());
        }
    }

    private void checkOrgAndFormUploadState(GcDiffProcessCondition condition, GcOrgCacheVO orgCacheVO, GcOrgCenterService orgCenterTool) {
        DimensionParamsVO dimensionParamsVO = new DimensionParamsVO();
        dimensionParamsVO.setCurrency(condition.getCurrency());
        dimensionParamsVO.setCurrencyId(condition.getCurrency());
        dimensionParamsVO.setOrgType(condition.getOrgType());
        dimensionParamsVO.setOrgTypeId(condition.getOrgType());
        dimensionParamsVO.setPeriodStr(condition.getPeriodStr());
        dimensionParamsVO.setSchemeId(condition.getSchemeId());
        dimensionParamsVO.setTaskId(condition.getTaskId());
        FormUploadStateTool formUploadStateTool = FormUploadStateTool.getInstance();
        List formKeyScope = condition.getFormKeyScope();
        dimensionParamsVO.setOrgId(orgCacheVO.getId());
        this.checkCurrOrgUpdateState(formKeyScope, orgCacheVO, formUploadStateTool, dimensionParamsVO);
        dimensionParamsVO.setOrgId(orgCacheVO.getDiffUnitId());
        GcOrgCacheVO diffOrgCacheVO = orgCenterTool.getOrgByID(orgCacheVO.getDiffUnitId());
        this.checkCurrOrgUpdateState(formKeyScope, diffOrgCacheVO, formUploadStateTool, dimensionParamsVO);
    }

    private void checkCurrOrgUpdateState(List<String> formKeyScope, GcOrgCacheVO orgCacheVO, FormUploadStateTool formUploadStateTool, DimensionParamsVO dimensionParamsVO) {
        if (orgCacheVO == null) {
            return;
        }
        List writeAccessDescs = formUploadStateTool.writeable(dimensionParamsVO, formKeyScope);
        for (int i = 0; i < formKeyScope.size(); ++i) {
            String formKey = formKeyScope.get(i);
            List formGroupDefines = this.runTimeViewController.getFormGroupsByFormKey(formKey);
            if (com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)formGroupDefines) || ((ReadWriteAccessDesc)writeAccessDescs.get(i)).getAble().booleanValue()) continue;
            throw new BusinessRuntimeException("\u5355\u4f4d\uff1a" + orgCacheVO.getTitle() + "," + ((FormGroupDefine)formGroupDefines.get(0)).getTitle() + "\u5206\u7ec4\u5df2\u4e0a\u62a5\uff0c\u4e0d\u5141\u8bb8\u6267\u884c\u64cd\u4f5c");
        }
    }

    private void clearSumZbEmptyRow(GcDiffProcessCondition condition, List<RewriteSettingEO> rewriteSettingEOList) {
        Map<String, String> insideFormKey2InsideTableNameMap = rewriteSettingEOList.stream().collect(Collectors.toMap(RewriteSettingEO::getInsideFormKey, eo -> this.getTableNameByCode(eo.getInsideTableName()), (v1, v2) -> v1));
        if (condition.isAllDifferenceProcess()) {
            for (String tableName : insideFormKey2InsideTableNameMap.values()) {
                this.clearSumZbEmptyRow(condition, tableName);
            }
        } else {
            String tableName = insideFormKey2InsideTableNameMap.get(condition.getCurrFormKey());
            this.clearSumZbEmptyRow(condition, tableName);
        }
    }

    private void clearSumZbEmptyRow(GcDiffProcessCondition condition, String tableName) {
        List<String> numberAndGatherFiledCodeList = this.getNumberAndGatherFiledCodes(tableName, null);
        String sql = "delete from %1s i where MDCODE=? and DATATIME=? %2s";
        StringBuilder where = NrSqlUtils.buildEntityTableWhere((Object)condition, (String)"");
        for (String filedCode : numberAndGatherFiledCodeList) {
            where.append(" and coalesce(").append(filedCode).append(",0)=0\n");
        }
        EntNativeSqlDefaultDao.getInstance().execute(String.format(sql, tableName, where), Arrays.asList(condition.getOrgId(), condition.getPeriodStr()));
    }

    private void rewriteFloatBalaceData(GcDiffProcessCondition condition, GcOrgCacheVO orgCacheVO, GcOrgCacheVO diffOrgCacheVO, GcActionParamsVO params, Map<String, List<RewriteSettingEO>> rewriteSettingMap) {
        List<Map<String, Object>> offsetDatas = this.getOffsetSumData(condition);
        String reportSystemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(condition.getTaskId(), condition.getPeriodStr());
        this.batchDeleteAllOutsideTableDatas(params, orgCacheVO.getDiffUnitId(), "\u5f80\u6765\u62b5\u9500\u5dee\u989d", rewriteSettingMap);
        for (Map.Entry<String, List<RewriteSettingEO>> entry : rewriteSettingMap.entrySet()) {
            List<RewriteSettingEO> rewriteSettingEOS = entry.getValue();
            RewriteSettingEO rewriteSettingEO = rewriteSettingEOS.get(0);
            String masterColumnCodes = rewriteSettingEO.getMasterColumnCodes();
            if (StringUtils.isEmpty((String)masterColumnCodes) || masterColumnCodes.split(";").length == 1 && "SUBJECTCODE".equalsIgnoreCase(masterColumnCodes.split(";")[0])) continue;
            ArrayList<String> currTableAllFieldCodes = new ArrayList<String>();
            List<String> numberAndGatherFiledCodes = this.getNumberAndGatherFiledCodes(this.getTableNameByCode(rewriteSettingEO.getInsideTableName()), currTableAllFieldCodes);
            List<Map<String, Object>> directChildFloatDatas = this.floatBalanceService.queryOppNotBelongCurrlevelDatas(params, this.getTableNameByCode(rewriteSettingEO.getInsideTableName()), currTableAllFieldCodes);
            Set<String> allRelateSubjectCodes = this.getSubject2TableNameMap(rewriteSettingEOS, reportSystemId);
            this.writeDataToGroupWithin(params, directChildFloatDatas, orgCacheVO.getDiffUnitId(), numberAndGatherFiledCodes, currTableAllFieldCodes, allRelateSubjectCodes, rewriteSettingEO);
            this.writeDataToGroupOutside(condition, orgCacheVO, diffOrgCacheVO, offsetDatas, reportSystemId, rewriteSettingEOS, directChildFloatDatas);
        }
    }

    private void writeDataToGroupOutside(GcDiffProcessCondition condition, GcOrgCacheVO orgCacheVO, GcOrgCacheVO diffOrgCacheVO, List<Map<String, Object>> offsetDatas, String reportSystemId, List<RewriteSettingEO> rewriteSettingEOS, List<Map<String, Object>> directChildFloatDatas) {
        RewriteSettingEO rewriteSettingEO = rewriteSettingEOS.get(0);
        ArrayList<String> currTableAllFieldCodes = new ArrayList<String>();
        List<String> numberAndGatherFiledCodes = this.getNumberAndGatherFiledCodes(this.getTableNameByCode(rewriteSettingEO.getOutsideTableName()), currTableAllFieldCodes);
        if (!condition.getFormKeyScope().contains(rewriteSettingEO.getOutsideFormKey())) {
            return;
        }
        HashMap<String, Object> fields = new HashMap<String, Object>();
        fields.put("SUBJECTCODE", rewriteSettingEO.getSubjectCode() + "||" + reportSystemId);
        fields.put("MDCODE", orgCacheVO.getDiffUnitId());
        BigDecimal floatSumValue = this.getDirectChildrenFloatSumValue(condition, rewriteSettingEO, directChildFloatDatas);
        BigDecimal offsetSumData = this.getOffsetSumData(offsetDatas, rewriteSettingEOS, reportSystemId);
        BigDecimal diffSumData = floatSumValue.add(offsetSumData);
        if (diffSumData.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        fields.put("AMT", diffSumData);
        numberAndGatherFiledCodes.stream().filter(fieldCode -> !"AMT".equals(fieldCode)).forEach(fieldCode -> fields.put((String)fieldCode, 0));
        this.appendBaseInfo(condition, fields, diffOrgCacheVO);
        fields.put("OPPUNITTITLE", "\u5f80\u6765\u62b5\u9500\u5dee\u989d");
        String acctOrgTitle = diffOrgCacheVO == null ? "" : diffOrgCacheVO.getCode() + "|" + diffOrgCacheVO.getTitle();
        fields.put("ACCTORGTITLE", acctOrgTitle);
        this.floatBalanceDiffService.addBatchFloatBalanceDiffDatas(fields, this.getTableNameByCode(rewriteSettingEO.getOutsideTableName()), currTableAllFieldCodes);
    }

    private BigDecimal getOffsetSumData(List<Map<String, Object>> offsetDatas, List<RewriteSettingEO> rewriteSettingEOS, String reportSystemId) {
        Set<String> allRelateSubjectCodes = this.getSubject2TableNameMap(rewriteSettingEOS, reportSystemId);
        List<Map<String, Object>> currSettingOffsetDatas = offsetDatas.stream().filter(data -> 0 == (Integer)data.get("OFFSETSRCTYPE") && allRelateSubjectCodes.contains(data.get("SUBJECTCODE"))).collect(Collectors.toList());
        return this.getGroupOutsideSumAmt(currSettingOffsetDatas);
    }

    private TaskLog initTaskLog(String sn) {
        OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(OneKeyMergeUtils.generateSN("diffProcess", sn));
        this.progressService.createProgressData((ProgressData)onekeyProgressData);
        return new TaskLog(onekeyProgressData);
    }

    private void writeDataToGroupWithin(GcActionParamsVO params, List<Map<String, Object>> directChildFloatDatas, String diffUnitId, List<String> numberAndGatherFiledCodes, List<String> currTableAllFieldCodes, Set<String> allRelateSubjectCodes, RewriteSettingEO rewriteSettingEO) {
        List currFloatDatas = directChildFloatDatas.stream().filter(item -> this.isNeedRewritre((Map)item, diffUnitId) && allRelateSubjectCodes.contains(((String)item.get("SUBJECTCODE")).substring(0, ((String)item.get("SUBJECTCODE")).indexOf("|")))).collect(Collectors.toList());
        ArrayList<Map<String, Object>> floatBalanceEOS = new ArrayList<Map<String, Object>>();
        BigDecimal negateRatio = new BigDecimal(-1);
        for (Map data : currFloatDatas) {
            HashMap<String, Object> fields = new HashMap<String, Object>();
            fields.put("MDCODE", diffUnitId);
            for (String filedCode : currTableAllFieldCodes) {
                if (numberAndGatherFiledCodes.contains(filedCode)) {
                    BigDecimal fieldValue = data.get(filedCode) == null ? BigDecimal.ZERO : new BigDecimal(data.get(filedCode).toString());
                    fields.put(filedCode, fieldValue.multiply(negateRatio));
                    continue;
                }
                fields.put(filedCode, data.get(filedCode));
            }
            floatBalanceEOS.add(fields);
        }
        this.floatBalanceService.batchDeleteAllBalance(diffUnitId, this.getTableNameByCode(rewriteSettingEO.getInsideTableName()), params);
        this.floatBalanceService.batchRewrite(diffUnitId, params, floatBalanceEOS, this.getTableNameByCode(rewriteSettingEO.getInsideTableName()), currTableAllFieldCodes);
    }

    private BigDecimal getDirectChildrenFloatSumValue(GcDiffProcessCondition condition, RewriteSettingEO rewriteSettingEO, List<Map<String, Object>> directChildFloatDatas) {
        HashMap mdOrgIsDiff = new HashMap();
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        directChildFloatDatas = directChildFloatDatas.stream().filter(data -> this.isDiffOrg(data.get("MDCODE").toString(), orgCenterTool, mdOrgIsDiff) == false).collect(Collectors.toList());
        return directChildFloatDatas.stream().map(data -> new BigDecimal(data.get("AMT") == null ? "0" : data.get("AMT").toString())).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private List<Map<String, Object>> getOffsetSumData(GcDiffProcessCondition condition) {
        ConsolidatedOptionVO optionVO = this.optionService.getOptionDataBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        List finishCalcRuleIds = optionVO.getFinishCalcRewriteRuleIds();
        if (com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)finishCalcRuleIds)) {
            return new ArrayList<Map<String, Object>>();
        }
        GcActionParamsVO paramsVO = new GcActionParamsVO();
        BeanUtils.copyProperties(condition, paramsVO);
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)condition.getPeriodStr());
        paramsVO.setAcctYear(Integer.valueOf(yearPeriodUtil.getYear()));
        paramsVO.setAcctPeriod(Integer.valueOf(yearPeriodUtil.getPeriod()));
        paramsVO.setPeriodType(Integer.valueOf(yearPeriodUtil.getType()));
        return this.getOffsetSumData(paramsVO, condition.getOrgId(), finishCalcRuleIds);
    }

    private Map<String, BigDecimal> sumFloatBalance(List<Map<String, Object>> directChildFloatDatas, List<String> numberAndGatherFiledCodes) {
        Map<String, BigDecimal> sumFields = this.sumGroupFloatBalance(directChildFloatDatas, numberAndGatherFiledCodes);
        HashMap<String, BigDecimal> fields = new HashMap<String, BigDecimal>();
        for (Map.Entry<String, BigDecimal> field : sumFields.entrySet()) {
            fields.put(field.getKey(), field.getValue().multiply(new BigDecimal(-1)));
        }
        return fields;
    }

    private void appendBaseInfo(GcDiffProcessCondition condition, Map<String, Object> fields, GcOrgCacheVO diffOrgCacheVO) {
        FloatOrderGenerator floatOrderGenerator = new FloatOrderGenerator();
        Set entityTableNames = NrTool.getEntityTableNames((String)condition.getSchemeId());
        fields.put("DATATIME", condition.getPeriodStr());
        fields.put("BIZKEYORDER", UUIDOrderUtils.newUUIDStr());
        fields.put("FLOATORDER", floatOrderGenerator.next());
        fields.put("MD_GCADJTYPE", "BEFOREADJ");
        if (entityTableNames.contains("MD_CURRENCY")) {
            fields.put("MD_CURRENCY", condition.getCurrency());
        }
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            fields.put("MD_GCORGTYPE", diffOrgCacheVO.getOrgTypeId());
        }
    }

    @Override
    public ExportExcelSheet exportDiffProcessData(GcDiffProcessCondition condition) {
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u5dee\u989d\u6570\u636e", Integer.valueOf(1));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        String[] titles = new String[]{"\u5e8f\u53f7", "\u672c\u65b9\u5355\u4f4d", "\u5bf9\u65b9\u5355\u4f4d", "\u79d1\u76ee", "\u671f\u672b\u91d1\u989d", "\u62b5\u9500\u91d1\u989d", "\u5dee\u989d"};
        rowDatas.add(titles);
        List<GcDiffProcessDataVO> diffProcessDatas = this.queryDifferenceIntermediateDatas(condition);
        int rowIndex = 2;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        BigDecimal totalEndAmt = BigDecimal.ZERO;
        BigDecimal totalOffsetAmt = BigDecimal.ZERO;
        for (GcDiffProcessDataVO vo : diffProcessDatas) {
            Object[] dataRow = new Object[titles.length];
            int colIndex = 0;
            dataRow[colIndex++] = String.valueOf(rowIndex++);
            dataRow[colIndex++] = vo.getUnitTitle();
            dataRow[colIndex++] = vo.getOppUnitTitle();
            dataRow[colIndex++] = vo.getSubjectTitle();
            dataRow[colIndex++] = df.format(vo.getEndAmt());
            totalEndAmt = totalEndAmt.add(vo.getEndAmt());
            dataRow[colIndex++] = df.format(vo.getOffsetAmt());
            totalOffsetAmt = totalOffsetAmt.add(vo.getOffsetAmt());
            dataRow[colIndex++] = df.format(vo.getDiffAmt());
            rowDatas.add(dataRow);
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        Object[] dataRow = new Object[titles.length];
        dataRow[0] = "1";
        dataRow[1] = "\u5408\u8ba1";
        dataRow[2] = "--";
        dataRow[3] = "--";
        dataRow[4] = df.format(totalEndAmt);
        dataRow[5] = df.format(totalOffsetAmt);
        dataRow[6] = df.format(totalEndAmt.add(totalOffsetAmt));
        exportExcelSheet.getRowDatas().add(1, dataRow);
        return exportExcelSheet;
    }

    @Override
    public String getTableNameByCode(String tableCode) {
        if (StringUtils.isEmpty((String)tableCode)) {
            return tableCode;
        }
        TableModelDefine tableModelDefine = ((DataModelService)SpringContextUtils.getBean(DataModelService.class)).getTableModelDefineByCode(tableCode);
        if (tableModelDefine == null) {
            return tableCode;
        }
        return tableModelDefine.getName();
    }

    public static List<String> listSubjectsByFormKeyAndTableCode(GcDiffProcessCondition condition, String tableCode) throws Exception {
        IDataDefinitionRuntimeController runtimeCtrl = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        TableDefine tableDefine = runtimeCtrl.queryTableDefineByCode(tableCode);
        FieldDefine define = runtimeCtrl.queryFieldByCodeInTable("SUBJECTCODE", tableDefine.getKey());
        ArrayList<String> gcBaseDataCodes = new ArrayList<String>();
        TaskDefine taskDefine = runTimeViewController.queryTaskDefine(condition.getTaskId());
        if (taskDefine == null) {
            return com.jiuqi.common.base.util.CollectionUtils.newArrayList();
        }
        List allRegionDefines = runTimeViewController.getAllRegionsInForm(condition.getCurrFormKey());
        if (allRegionDefines == null) {
            return com.jiuqi.common.base.util.CollectionUtils.newArrayList();
        }
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringBeanUtils.getBean(ConsolidatedTaskService.class);
        String systemId = consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(condition.getTaskId(), condition.getPeriodStr());
        for (DataRegionDefine regionDefine : allRegionDefines) {
            IEntityTable entityTable;
            List allLinkDefines = runTimeViewController.getLinksInRegionByField(regionDefine.getKey(), define.getKey());
            if (CollectionUtils.isEmpty(allLinkDefines) || allLinkDefines.size() > 1) continue;
            EntityViewDefine viewDefine = runTimeViewController.getViewByLinkDefineKey(((DataLinkDefine)allLinkDefines.get(0)).getKey());
            try {
                DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)condition.getOrgId(), (Object)condition.getPeriodStr(), (Object)condition.getCurrency(), (Object)condition.getOrgType(), (String)condition.getSelectAdjustCode(), (String)condition.getTaskId());
                IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
                IEntityViewRunTimeController runTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
                IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
                IEntityQuery entityQuery = entityDataService.newEntityQuery();
                entityQuery.setIsolateCondition(systemId);
                entityQuery.setEntityView(viewDefine);
                entityQuery.setMasterKeys(dimensionValueSet);
                ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
                executorContext.setPeriodView(taskDefine.getDateTime());
                entityTable = entityQuery.executeReader((IContext)executorContext);
            }
            catch (Exception e) {
                continue;
            }
            if (null == entityTable || CollectionUtils.isEmpty(entityTable.getAllRows())) continue;
            for (IEntityRow allRow : entityTable.getAllRows()) {
                gcBaseDataCodes.add(allRow.getCode());
            }
        }
        return gcBaseDataCodes;
    }
}

