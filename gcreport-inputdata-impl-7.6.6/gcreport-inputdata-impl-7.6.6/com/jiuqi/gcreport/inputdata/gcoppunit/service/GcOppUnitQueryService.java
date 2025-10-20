/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.INode
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.elementtable.impl.ElementTableDataVO
 *  com.jiuqi.gcreport.common.elementtable.impl.ElementTableTitleVO
 *  com.jiuqi.gcreport.common.elementtable.impl.ElementTableVO
 *  com.jiuqi.gcreport.common.np.NpReportQueryProvider
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.gcoppunit.ActionEditEnum
 *  com.jiuqi.gcreport.inputdata.gcoppunit.CascaderVO
 *  com.jiuqi.gcreport.inputdata.gcoppunit.DataInputTypeEnum
 *  com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitCondition
 *  com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitDataVO
 *  com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitFieldVO
 *  com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitSubjectVO
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.transfer.vo.TransferColumnVo
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.gcreport.unionrule.vo.SubjectITree
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.exception.BpmException
 *  com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.NotFoundFieldException
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.RegionDataCommitSet
 *  com.jiuqi.nr.jtable.params.input.ReportDataCommitSet
 *  com.jiuqi.nr.jtable.params.output.SaveResult
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.util.DataLinkStyleUtil
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.inputdata.gcoppunit.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.INode;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.elementtable.impl.ElementTableDataVO;
import com.jiuqi.gcreport.common.elementtable.impl.ElementTableTitleVO;
import com.jiuqi.gcreport.common.elementtable.impl.ElementTableVO;
import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.gcoppunit.ActionEditEnum;
import com.jiuqi.gcreport.inputdata.gcoppunit.CascaderVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.DataInputTypeEnum;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitCondition;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitDataVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitFieldVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitSubjectVO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.query.constant.EntitiesOrgMode;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.gcreport.unionrule.vo.SubjectITree;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.exception.NotFoundFieldException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class GcOppUnitQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcOppUnitQueryService.class);
    public static final String MD_OPPUNIT_SUBJECT_REL = "MD_OPPUNIT_SUBJECT_REL";
    @Autowired
    private NpReportQueryProvider provider;
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    IJtableResourceService jtableResourceService;
    @Autowired
    IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private ConsolidatedSubjectUIService consolidatedSubjectUIService;
    @Autowired
    private NvwaDataModelCreateUtil nvwaDataModelCreateUtil;
    @Autowired
    private IPeriodEntityAdapter iPeriodEntityAdapter;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private BaseDataClient baseDataClient;
    private static List<String> ruleTypes = new ArrayList<String>();

    private List<ElementTableTitleVO> queryAllTitle(String taskId) throws Exception {
        ArrayList<ElementTableTitleVO> titles = new ArrayList<ElementTableTitleVO>();
        titles.add(new ElementTableTitleVO("unitName", GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.local.unit"), "left", (Object)false, 300));
        titles.add(new ElementTableTitleVO("oppUnitName", GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.opp.unit.name"), "left", (Object)false, 300));
        titles.add(new ElementTableTitleVO("kmname", GcI18nUtil.getMessage((String)"gc.inputdata.opp.showoffset.subjectTitle"), "left", (Object)false, 300));
        titles.add(new ElementTableTitleVO("amtV", GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.trading.amt"), "right", (Object)false, 300).setExportTypeWithNumber());
        titles.add(new ElementTableTitleVO("remark", GcI18nUtil.getMessage((String)"gc.inputdata.opp.showoffset.memo"), "left", (Object)false, 300));
        List<DataField> dsignFieldDefineList = this.getDsignFieldDefineList(taskId);
        dsignFieldDefineList.stream().filter(field -> !"OPPUNITID".equals(field.getCode()) && !"ORGCODE".equals(field.getCode()) && !"SUBJECTCODE".equals(field.getCode())).forEach(designFieldDefine -> {
            if (!"AMT".equals(designFieldDefine.getCode())) {
                if (designFieldDefine.getType() == FieldType.FIELD_TYPE_FLOAT || designFieldDefine.getType() == FieldType.FIELD_TYPE_DECIMAL) {
                    titles.add(new ElementTableTitleVO(designFieldDefine.getCode(), designFieldDefine.getTitle(), "right", (Object)false, 300).setExportTypeWithNumber());
                } else {
                    titles.add(new ElementTableTitleVO(designFieldDefine.getCode(), designFieldDefine.getTitle(), "left", (Object)false, 300));
                }
            }
        });
        return titles;
    }

    public List<DataField> getDsignFieldDefineList(String taskId) throws Exception {
        String tableId = this.inputDataNameProvider.getDataTableKeyByTaskId(taskId);
        return this.runtimeDataSchemeService.getDataFieldByTable(tableId);
    }

    public Object excute(GcOppUnitCondition condition) {
        if (condition.getType() == ActionEditEnum.FORM) {
            DimensionValueSet masterKeys = DimensionValueSetUtil.getDimensionValueSet((Map)condition.getEnvContext().getDimensionSet());
            GcDataEntryContext context = null;
            try {
                context = new GcDataEntryContext(condition.getEnvContext().getFormSchemeKey(), condition.getRegionid(), this.provider);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.init.error"), (Throwable)e);
            }
            context.initMasterDimension(masterKeys);
            return this.queryFormEditFields(context, condition);
        }
        if (condition.getType() == ActionEditEnum.SAVE) {
            return this.inputNewData(condition);
        }
        if (condition.getType() == ActionEditEnum.QUERYSUBJECT) {
            return this.executeSubjectRule(condition);
        }
        if (condition.getType() == ActionEditEnum.HASMAPPING) {
            return this.queryWhetherExistsMapping();
        }
        DimensionValueSet masterKeys = DimensionValueSetUtil.getDimensionValueSet((Map)condition.getEnvContext().getDimensionSet());
        GcDataEntryContext context = null;
        try {
            context = new GcDataEntryContext(condition.getEnvContext().getFormSchemeKey(), this.provider);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.init.error"), (Throwable)e);
        }
        context.initMasterDimension(masterKeys);
        if (condition.getType() == ActionEditEnum.QUERY) {
            return this.queryAllData(context, condition);
        }
        if (condition.getType() == ActionEditEnum.RULE) {
            this.checkUpload(context, condition);
            GcOppUnitSubjectVO gcOppUnitSubjectVO = this.querySubjectBySelect(context, condition.getSelectData());
            INode localSubject = this.filterSubjectByBaseData(context, condition.getSelectData().getKmcode(), gcOppUnitSubjectVO.getSubjectsByFilter());
            if (Objects.nonNull(localSubject)) {
                gcOppUnitSubjectVO.setSelectSubject(localSubject);
            }
            return gcOppUnitSubjectVO;
        }
        if (condition.getType() == ActionEditEnum.BATCHSAVE) {
            if (Objects.isNull(condition.getSubjectCode())) {
                this.checkUpload(context, condition);
                this.batchSaveDataGroup(condition, context);
            } else {
                this.batchSaveData(condition);
            }
        }
        return false;
    }

    private boolean queryWhetherExistsMapping() {
        BaseDataDTO baseDataCondition = new BaseDataDTO();
        baseDataCondition.setTableName(MD_OPPUNIT_SUBJECT_REL);
        int count = this.baseDataClient.count(baseDataCondition);
        return count > 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void batchSaveDataGroup(GcOppUnitCondition condition, GcDataEntryContext context) {
        List inputDataS;
        List batchSelectData = condition.getBatchSelectData();
        Map<String, List<GcOppUnitDataVO>> selectDatas = batchSelectData.stream().collect(Collectors.groupingBy(ElementTableDataVO::getId));
        String inputTableName = this.inputDataNameProvider.getTableNameByTaskId(context.getTaskKey());
        EntNativeSqlDefaultDao<InputDataEO> inputDataSqlDao = this.templateEntDaoCacheService.getTemplateEntDao(inputTableName, InputDataEO.class);
        String sql = "select * from %s where %s";
        TempTableCondition conditionOfIds = SqlUtils.getConditionOfIds(selectDatas.keySet(), (String)"id");
        String ids = conditionOfIds.getCondition();
        sql = String.format(sql, inputTableName, ids);
        try {
            inputDataS = inputDataSqlDao.selectEntity(sql, new Object[0]);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)conditionOfIds.getTempGroupId());
        }
        Map<String, List<InputDataEO>> inputDataGroup = inputDataS.stream().filter(inputData -> !StringUtils.isEmpty((String)inputData.getUnionRuleId())).collect(Collectors.groupingBy(InputDataEO::getUnionRuleId));
        for (String rule : inputDataGroup.keySet()) {
            try {
                List<InputDataEO> inputDataEOS = inputDataGroup.get(rule);
                GcOppUnitSubjectVO gcOppUnitSubjectVO = this.querySubjectBySelect(context, selectDatas.get(inputDataEOS.get(0).getId()).get(0));
                List oppUnitDataS = inputDataEOS.stream().map(eo -> (GcOppUnitDataVO)((List)selectDatas.get(eo.getId())).get(0)).collect(Collectors.toList());
                for (GcOppUnitDataVO oppUnitData : oppUnitDataS) {
                    this.disposeCondition(gcOppUnitSubjectVO, condition, oppUnitData, context);
                    this.batchSaveData(condition);
                }
            }
            catch (Exception e) {
                LOGGER.error("\u5408\u5e76\u89c4\u5219\u4e3a" + rule + "\u7684\u5bf9\u65b9\u5355\u4f4d\u6570\u636e\u6279\u91cf\u4fdd\u5b58\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage());
            }
        }
    }

    private void disposeCondition(GcOppUnitSubjectVO gcOppUnitSubjectVO, GcOppUnitCondition condition, GcOppUnitDataVO oppUnitData, GcDataEntryContext context) {
        condition.setBatchSelectData(Arrays.asList(oppUnitData));
        INode subject = this.filterSubjectByBaseData(context, oppUnitData.getKmcode(), gcOppUnitSubjectVO.getSubjectsByFilter());
        if (Objects.isNull(subject)) {
            subject = gcOppUnitSubjectVO.getSelectSubject();
        }
        Map subjectRegionRel = gcOppUnitSubjectVO.getSubjectRegionRel();
        if (Objects.isNull(subject) || Objects.isNull(subjectRegionRel) || com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)((Collection)subjectRegionRel.get(subject.getCode())))) {
            throw new BusinessRuntimeException("\u5339\u914d\u6570\u636e\u5f02\u5e38");
        }
        condition.setRegionid(((TransferColumnVo)((List)subjectRegionRel.get(subject.getCode())).get(0)).getKey());
        condition.setSubjectCode(subject.getCode());
        condition.setSubjectName(subject.getTitle());
    }

    private SaveResult batchSaveData(GcOppUnitCondition condition) {
        Map dimensionSet = condition.getEnvContext().getDimensionSet();
        DimensionValueSet masterKeys = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        GcDataEntryContext context = null;
        try {
            context = new GcDataEntryContext(condition.getEnvContext().getFormSchemeKey(), condition.getRegionid(), this.provider);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.init.error"), (Throwable)e);
        }
        context.initMasterDimension(masterKeys);
        GcDataEntryContext finalContext = context;
        ArrayList<GcOppUnitCondition> conditionList = new ArrayList<GcOppUnitCondition>();
        condition.getBatchSelectData().forEach(gcOppUnitDataVO -> {
            GcOppUnitCondition gcOppUnitCondition = new GcOppUnitCondition();
            BeanUtils.copyProperties(condition, gcOppUnitCondition);
            gcOppUnitCondition.setSelectData(gcOppUnitDataVO);
            gcOppUnitCondition.setInputData(this.queryFormEditFields(finalContext, gcOppUnitCondition));
            conditionList.add(gcOppUnitCondition);
        });
        SaveResult saveResult = this.inputNewBatchData(conditionList);
        String taskKey = condition.getEnvContext().getTaskKey();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        String periodTitle = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)("\u5bf9\u65b9\u5355\u4f4d-\u4efb\u52a1" + taskDefine.getTitle() + "-\u65f6\u671f" + periodTitle + "-\u5355\u4f4d" + ((DimensionValue)dimensionSet.get("MD_ORG")).getValue()), (String)("\u751f\u6210" + condition.getBatchSelectData().size() + "\u6761\u5185\u90e8\u8868\u6570\u636e"));
        return saveResult;
    }

    private SaveResult inputNewBatchData(List<GcOppUnitCondition> conditionList) {
        ReportDataCommitSet reportDataCommitSet = new ReportDataCommitSet();
        RegionDataCommitSet set = new RegionDataCommitSet();
        HashMap<String, RegionDataCommitSet> commitData = new HashMap<String, RegionDataCommitSet>();
        conditionList.forEach(condition -> {
            JtableContext context = new JtableContext();
            reportDataCommitSet.setContext(context);
            context.setFormKey(this.provider.getRunTimeViewController().queryDataRegionDefine(condition.getRegionid()).getFormKey());
            context.setFormSchemeKey(condition.getEnvContext().getFormSchemeKey());
            context.setFormulaSchemeKey(condition.getEnvContext().getFormulaSchemeKey());
            context.setTaskKey(condition.getEnvContext().getTaskKey());
            context.setDimensionSet(condition.getEnvContext().getDimensionSet());
            this.isFormLock((GcOppUnitCondition)condition);
            JtableContext setContext = new JtableContext(context);
            set.setContext(setContext);
            ArrayList<String> title = new ArrayList<String>();
            set.getCells().put(condition.getRegionid(), title);
            ArrayList<List<Object>> data = new ArrayList<List<Object>>();
            set.getNewdata().add(data);
            title.add("ID");
            String id = UUIDUtils.newUUIDStr();
            data.add(Arrays.asList(id, id));
            title.add("FLOATORDER");
            Double fo = 1.23456;
            data.add(Arrays.asList(fo, fo));
            condition.getInputData().stream().filter(vo -> !"MEMO".equals(vo.getFieldCode())).forEach(f -> {
                title.add(f.getLinkKey().toString());
                data.add(Arrays.asList(f.getRealValue(), f.getRealValue()));
            });
        });
        SaveResult saveResult = null;
        try {
            commitData.put(conditionList.get(0).getRegionid(), set);
            reportDataCommitSet.setCommitData(commitData);
            saveResult = this.jtableResourceService.saveReportData(reportDataCommitSet);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.save.error"));
        }
        return saveResult;
    }

    private SaveResult inputNewData(GcOppUnitCondition condition) {
        ReportDataCommitSet reportDataCommitSet = new ReportDataCommitSet();
        JtableContext context = new JtableContext();
        reportDataCommitSet.setContext(context);
        context.setFormKey(this.provider.getRunTimeViewController().queryDataRegionDefine(condition.getRegionid()).getFormKey());
        context.setFormSchemeKey(condition.getEnvContext().getFormSchemeKey());
        context.setFormulaSchemeKey(condition.getEnvContext().getFormulaSchemeKey());
        String taskKey = condition.getEnvContext().getTaskKey();
        context.setTaskKey(taskKey);
        Map dimensionSet = condition.getEnvContext().getDimensionSet();
        context.setDimensionSet(dimensionSet);
        this.isFormLock(condition);
        HashMap<String, RegionDataCommitSet> commitData = new HashMap<String, RegionDataCommitSet>();
        RegionDataCommitSet set = new RegionDataCommitSet();
        JtableContext setContext = new JtableContext(context);
        set.setContext(setContext);
        ArrayList<String> title = new ArrayList<String>();
        set.getCells().put(condition.getRegionid(), title);
        ArrayList<List<Object>> data = new ArrayList<List<Object>>();
        set.getNewdata().add(data);
        title.add("ID");
        String id = UUIDUtils.newUUIDStr();
        data.add(Arrays.asList(id, id));
        title.add("FLOATORDER");
        Double fo = 1.23456;
        data.add(Arrays.asList(fo, fo));
        condition.getInputData().stream().forEach(f -> {
            title.add(f.getLinkKey().toString());
            data.add(Arrays.asList(f.getRealValue(), f.getRealValue()));
        });
        commitData.put(condition.getRegionid(), set);
        reportDataCommitSet.setCommitData(commitData);
        SaveResult saveResult = null;
        try {
            saveResult = this.jtableResourceService.saveReportData(reportDataCommitSet);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.save.error"), (Throwable)e);
        }
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        String periodTitle = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u5f55\u5165", (String)("\u5bf9\u65b9\u5355\u4f4d-\u4efb\u52a1" + taskDefine.getTitle() + "-\u65f6\u671f" + periodTitle + "-\u5355\u4f4d" + ((DimensionValue)dimensionSet.get("MD_ORG")).getValue()), (String)"\u751f\u6210\u4e00\u6761\u5185\u90e8\u8868\u6570\u636e");
        return saveResult;
    }

    private List<GcOppUnitFieldVO> queryFormEditFields(GcDataEntryContext context, GcOppUnitCondition condition) {
        ArrayList<GcOppUnitFieldVO> linkFields = new ArrayList<GcOppUnitFieldVO>();
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(context.getTaskKey());
        EntNativeSqlDefaultDao<InputDataEO> inputDataSqlDao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        InputDataEO inputData = new InputDataEO();
        inputData.setId(condition.getSelectData().getId());
        inputData.setBizkeyOrder(condition.getSelectData().getId());
        InputDataEO eo = (InputDataEO)inputDataSqlDao.selectByEntity((BaseEntity)inputData);
        List allLinkDefines = this.provider.getRunTimeViewController().getAllLinksInRegion(context.getRegionDefine().getKey());
        Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])((DesignTimeViewController)SpringBeanUtils.getBean(DesignTimeViewController.class)).getReportDataFromForm(context.getFormKey(), 1));
        Collection calcCellDataLinks = ((IFormulaRunTimeController)SpringBeanUtils.getBean(IFormulaRunTimeController.class)).getCalcCellDataLinks(condition.getEnvContext().getFormulaSchemeKey(), context.getFormKey());
        for (DataLinkDefine dataLinkDefine : allLinkDefines) {
            if (dataLinkDefine.getPosX() == 0 || dataLinkDefine.getPosY() == 0) continue;
            GcOppUnitFieldVO vo = new GcOppUnitFieldVO();
            GridCellData gridCellData = grid2Data.getGridCellData(dataLinkDefine.getPosX(), dataLinkDefine.getPosY());
            vo.setCanEdit(gridCellData.getCellStyleData().isEditable());
            if (calcCellDataLinks.contains(dataLinkDefine.getKey())) {
                vo.setCanEdit(false);
            }
            FieldDefine fieldDefine = null;
            try {
                String fieldId = dataLinkDefine.getLinkExpression();
                if (fieldId != null) {
                    fieldDefine = this.provider.getRuntimeController().queryFieldDefine(fieldId);
                }
            }
            catch (Exception fieldId) {
                // empty catch block
            }
            if (fieldDefine == null) {
                Object[] i18args = new String[]{dataLinkDefine.getKey(), dataLinkDefine.getLinkExpression()};
                throw new NotFoundFieldException(ExceptionCodeCost.NOT_DATALINK_FIELD, new String[]{GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.notfound.error", (Object[])i18args)});
            }
            List<String> specialFields = Arrays.asList("UPDATETIME", "CREATETIME", "CREATEUSER");
            if (specialFields.contains(fieldDefine.getCode())) continue;
            vo.setFieldKey(fieldDefine.getKey());
            vo.setFieldCode(fieldDefine.getCode());
            vo.setFieldTitle(fieldDefine.getTitle());
            vo.setFieldType(DataInputTypeEnum.typeOf((FieldType)fieldDefine.getType()));
            vo.setFieldsize(fieldDefine.getSize().intValue());
            vo.setTaskKey(context.getTask().getKey());
            vo.setFormKey(context.getForm().getKey());
            vo.setRegionKey(context.getRegionDefine().getKey());
            vo.setLinkKey(dataLinkDefine.getKey());
            vo.setRow(dataLinkDefine.getPosY());
            vo.setCol(dataLinkDefine.getPosX());
            vo.setTitle(dataLinkDefine.getTitle());
            vo.setFormat(DataLinkStyleUtil.getOtherLinkStyle((DataLinkDefine)dataLinkDefine, (FieldDefine)fieldDefine));
            EntityViewDefine view = this.provider.getRunTimeViewController().getViewByLinkDefineKey(dataLinkDefine.getKey());
            TableModelDefine table = null;
            if (view != null) {
                try {
                    table = this.entityMetaService.getTableModel(view.getEntityId());
                    vo.setEntityKey(view.getEntityId());
                    vo.setFieldType(DataInputTypeEnum.ENTITY);
                    vo.setEntityTitle("");
                    vo.setEntityCode(table.getName());
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if ("OPPUNITID".equals(vo.getFieldCode())) {
                vo.setValue(condition.getSelectData().getUnitId());
                if (vo.getFieldType() == DataInputTypeEnum.ENTITY) {
                    vo.addEntity(new CascaderVO(vo.getValue(), condition.getSelectData().getUnitName()));
                    vo.setEntityValue(new String[]{vo.getValue()});
                }
                vo.setCanEdit(false);
            } else if ("MDCODE".equals(vo.getFieldCode())) {
                vo.setValue(condition.getSelectData().getOppUnitId());
                if (vo.getFieldType() == DataInputTypeEnum.ENTITY) {
                    vo.addEntity(new CascaderVO(vo.getValue(), condition.getSelectData().getOppUnitName()));
                    vo.setEntityValue(new String[]{vo.getValue()});
                }
                vo.setCanEdit(false);
            } else if ("ORGCODE".equals(vo.getFieldCode())) {
                vo.setValue(condition.getSelectData().getOppUnitId());
                if (vo.getFieldType() == DataInputTypeEnum.ENTITY) {
                    vo.addEntity(new CascaderVO(vo.getValue(), condition.getSelectData().getOppUnitName()));
                    vo.setEntityValue(new String[]{vo.getValue()});
                }
                vo.setCanEdit(false);
            } else if ("SUBJECTOBJ".equals(vo.getFieldCode())) {
                vo.setValue(condition.getSubjectCode());
                if (vo.getFieldType() == DataInputTypeEnum.ENTITY) {
                    vo.addEntity(new CascaderVO(vo.getValue(), condition.getSubjectName()));
                    vo.setEntityValue(new String[]{vo.getValue()});
                }
                vo.setCanEdit(false);
            } else if (!StringUtils.isEmpty((String)vo.getEntityKey()) && vo.getEntityKey().contains("@BASE")) {
                vo.setValue(GcOppUnitQueryService.toViewString(eo.getFieldValue(vo.getFieldCode()), fieldDefine.getFractionDigits()));
                if (!StringUtils.isEmpty((String)vo.getValue())) {
                    vo.setBasedata(GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode(vo.getEntityCode(), vo.getValue())));
                }
            } else if (DataInputTypeEnum.DATE.equals((Object)vo.getFieldType())) {
                vo.setDateValue((Date)eo.getFieldValue(vo.getFieldCode()));
            } else {
                vo.setValue(GcOppUnitQueryService.toViewString(eo.getFieldValue(vo.getFieldCode()), fieldDefine.getFractionDigits()));
                if (vo.getFieldType() == DataInputTypeEnum.ENTITY) {
                    List<CascaderVO> data = this.getItemByCodeIntable(view, table, context);
                    vo.setEntityData(data);
                    vo.setEntityValue(this.getValuePath(data, vo.getValue()));
                    if (data.size() > 0 && this.getValuePath(data, vo.getValue()) == null && !StringUtils.isNull((String)vo.getValue())) {
                        vo.setValue(null);
                    }
                }
            }
            List<String> specialColumns = Arrays.asList("OPPUNITID", "SUBJECTOBJ", "MDCODE", "ORGCODE", "AMT");
            if (calcCellDataLinks.contains(dataLinkDefine.getKey()) && !specialColumns.contains(vo.getFieldCode())) {
                vo.setCanEdit(false);
                vo.setValue(null);
                vo.setBasedata(null);
            }
            linkFields.add(vo);
        }
        return linkFields.stream().sorted(Comparator.comparing(GcOppUnitFieldVO::getCol)).collect(Collectors.toList());
    }

    private String[] getValuePath(List<CascaderVO> data, String value) {
        if (StringUtils.isEmpty((String)value)) {
            return null;
        }
        for (CascaderVO f : data) {
            if (f.getChildren() != null && f.getChildren().size() > 0) {
                String[] p = this.getValuePath(f.getChildren(), value);
                if (p == null || p.length <= 0) continue;
                String[] val = new String[p.length + 1];
                val[0] = f.getValue();
                int i = 1;
                for (String v : p) {
                    val[i++] = v;
                }
                return val;
            }
            if (!f.getValue().equals(value)) continue;
            return new String[]{value};
        }
        return null;
    }

    private List<CascaderVO> getItemByCodeIntable(EntityViewDefine view, TableModelDefine tableModelDefine, GcDataEntryContext context) {
        List<CascaderVO> allBaseData = new ArrayList<CascaderVO>();
        try {
            if (view == null || tableModelDefine == null) {
                return allBaseData;
            }
            String[] bizs = tableModelDefine.getBizKeys().split(";");
            String dimCode = this.dataModelService.getColumnModelDefineByID(bizs[0]).getCode();
            ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringBeanUtils.getBean(ConsolidatedTaskService.class);
            String systemId = consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(context.getTaskKey(), String.valueOf(((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue()));
            IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
            IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
            IEntityQuery entityQuery = entityDataService.newEntityQuery();
            entityQuery.setIsolateCondition(systemId);
            entityQuery.setEntityView(view);
            entityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet()));
            ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(context.getTask().getKey());
            if (taskDefine == null) {
                LOGGER.error("\u67e5\u8be2\u5b9e\u4f53\u7ed3\u679c\u96c6\u53d1\u751f\u5f02\u5e38, \u4efb\u52a1\u4e3a\u7a7a");
                return allBaseData;
            }
            executorContext.setPeriodView(taskDefine.getDateTime());
            IEntityTable table = entityQuery.executeReader((IContext)executorContext);
            allBaseData = this.geCascaderVOTree(table, table.getRootRows(), dimCode);
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u5b9e\u4f53\u7ed3\u679c\u96c6\u53d1\u751f\u5f02\u5e38", e);
        }
        return allBaseData;
    }

    private List<CascaderVO> geCascaderVOTree(IEntityTable table, List<IEntityRow> parent, String dimCode) {
        ArrayList<CascaderVO> vos = new ArrayList<CascaderVO>();
        if (parent != null) {
            parent.stream().forEach(f -> {
                try {
                    CascaderVO vo = new CascaderVO();
                    for (int i = 0; i < f.getFieldsInfo().getFieldCount(); ++i) {
                        try {
                            IEntityAttribute fd = f.getFieldsInfo().getFieldByIndex(i);
                            if (fd == null) continue;
                            if (fd.getCode().equalsIgnoreCase(dimCode)) {
                                vo.setValue(f.getAsString(fd.getName()));
                                continue;
                            }
                            if (fd.getName().equalsIgnoreCase("ID")) {
                                vo.setKey(f.getAsString(fd.getName()));
                                continue;
                            }
                            if (fd.getName().equalsIgnoreCase("KEY")) {
                                vo.setKey(f.getAsString(fd.getName()));
                                continue;
                            }
                            if (fd.getName().equalsIgnoreCase("CODE")) continue;
                            if (fd.getName().equalsIgnoreCase("TITLE")) {
                                vo.setLabel(f.getAsString(fd.getName()));
                                continue;
                            }
                            if (!fd.getName().equalsIgnoreCase("NAME")) continue;
                            vo.setLabel(f.getAsString(fd.getName()));
                            continue;
                        }
                        catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                    vo.setLabel(vo.getValue() + "|" + vo.getLabel());
                    List childrens = table.getChildRows(f.getEntityKeyData());
                    vo.setChildren(this.geCascaderVOTree(table, childrens, dimCode));
                    vos.add(vo);
                }
                catch (RuntimeException e) {
                    e.printStackTrace();
                }
            });
        }
        return vos;
    }

    private void checkUpload(GcDataEntryContext context, GcOppUnitCondition condition) {
        DimensionParamsVO params = new DimensionParamsVO();
        params.setTaskId(condition.getEnvContext().getTaskKey());
        params.setSchemeId(condition.getEnvContext().getFormSchemeKey());
        params.setOrgId((String)context.getMasterKeys().getValue("MD_ORG"));
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)condition.getEnvContext().getTaskKey());
        params.setOrgType(Objects.requireNonNull(orgCategory));
        params.setOrgTypeId((String)context.getMasterKeys().getValue("MD_GCORGTYPE"));
        params.setPeriodStr((String)context.getMasterKeys().getValue("DATATIME"));
        params.setCurrency((String)context.getMasterKeys().getValue("MD_CURRENCY"));
        params.setCurrencyId((String)context.getMasterKeys().getValue("MD_CURRENCY"));
        params.setSelectAdjustCode((String)context.getMasterKeys().getValue("ADJUST"));
        ReadWriteAccessDesc readWriteAccessDesc = FormUploadStateTool.getInstance().writeable(params, condition.getEnvContext().getFormKey());
        if (!Boolean.TRUE.equals(readWriteAccessDesc.getAble())) {
            Object[] i18args = new String[]{context.getSelectOrg().getTitle() + "\uff0c" + readWriteAccessDesc.getDesc()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.upload.state.error", (Object[])i18args));
        }
    }

    private void isFormLock(GcOppUnitCondition condition) {
        DimensionValueSet masterKeys = DimensionValueSetUtil.getDimensionValueSet((Map)condition.getEnvContext().getDimensionSet());
        DimensionParamsVO params = new DimensionParamsVO();
        params.setTaskId(condition.getEnvContext().getTaskKey());
        params.setSchemeId(condition.getEnvContext().getFormSchemeKey());
        params.setOrgId((String)masterKeys.getValue("MD_ORG"));
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)condition.getEnvContext().getTaskKey());
        params.setOrgType(Objects.requireNonNull(orgCategory));
        params.setOrgTypeId((String)masterKeys.getValue("MD_GCORGTYPE"));
        params.setPeriodStr((String)masterKeys.getValue("DATATIME"));
        params.setCurrency((String)masterKeys.getValue("MD_CURRENCY"));
        params.setCurrencyId((String)masterKeys.getValue("MD_CURRENCY"));
        params.setSelectAdjustCode((String)masterKeys.getValue("ADJUST"));
        ReadWriteAccessDesc readWriteAccessDesc = FormUploadStateTool.getInstance().writeable(params, condition.getEnvContext().getFormKey());
        if (!Boolean.TRUE.equals(readWriteAccessDesc.getAble())) {
            Object[] i18args = new String[]{this.provider.getRunTimeViewController().queryFormById(condition.getEnvContext().getFormKey()).getTitle()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.form.locked.error", (Object[])i18args));
        }
    }

    private GcOppUnitSubjectVO querySubjectBySelect(GcDataEntryContext context, GcOppUnitDataVO selectData) {
        GcOppUnitSubjectVO result = new GcOppUnitSubjectVO();
        String inputTableName = this.inputDataNameProvider.getTableNameByTaskId(context.getTaskKey());
        EntNativeSqlDefaultDao<InputDataEO> inputDataSqlDao = this.templateEntDaoCacheService.getTemplateEntDao(inputTableName, InputDataEO.class);
        InputDataEO inputData = new InputDataEO();
        inputData.setId(selectData.getId());
        inputData.setBizkeyOrder(selectData.getId());
        InputDataEO eo = (InputDataEO)inputDataSqlDao.selectByEntity((BaseEntity)inputData);
        AbstractUnionRule rule = this.ruleService.selectUnionRuleDTOById(eo.getUnionRuleId());
        if (ObjectUtils.isEmpty(rule)) {
            throw new BusinessRuntimeException("\u6240\u9009\u6570\u636e\u672a\u5339\u914d\u89c4\u5219\uff0c\u4e0d\u80fd\u8fdb\u884c\u751f\u6210\u672c\u65b9\u8bb0\u5f55");
        }
        String reportSystemId = this.taskService.getConsolidatedSystemIdBySchemeId(context.getFormScheme().getKey(), this.getPeriodByContext(context));
        String inputTableCode = this.inputDataNameProvider.getTableCodeByTaskId(context.getTaskKey());
        HashMap subjectMap = new HashMap();
        List allForms = this.provider.getRunTimeViewController().queryAllFormDefinesByFormScheme(context.getFormSchemeKey());
        List allSubjectBySystemId = this.subjectService.listAllSubjectsBySystemId(reportSystemId);
        allForms.stream().forEach(f -> {
            List allRegions = this.provider.getRunTimeViewController().getAllRegionsInForm(f.getKey());
            allRegions.stream().forEach(r -> {
                String filter = r.getFilterCondition();
                if (!StringUtils.isEmpty((String)filter)) {
                    Set<String> codes = GcOppUnitQueryService.getSubjectCodeFromRegion(filter, inputTableCode);
                    codes.stream().forEach(c -> {
                        List subs = allSubjectBySystemId.stream().filter(subject -> subject.getCode().indexOf((String)c) == 0 && subject.getCode().length() >= c.length()).collect(Collectors.toList());
                        if (com.jiuqi.common.base.util.CollectionUtils.isEmpty(subs)) {
                            return;
                        }
                        subs.stream().forEach(s -> {
                            ArrayList<TransferColumnVo> vos = (ArrayList<TransferColumnVo>)subjectMap.get(s.getCode());
                            if (vos == null) {
                                vos = new ArrayList<TransferColumnVo>();
                                subjectMap.put(s.getCode(), vos);
                            } else {
                                List regionKeys = vos.stream().map(TransferColumnVo::getKey).collect(Collectors.toList());
                                if (regionKeys.contains(r.getKey())) {
                                    return;
                                }
                            }
                            TransferColumnVo vo = new TransferColumnVo();
                            vo.setKey(r.getKey());
                            vo.setLabel(f.getTitle() + "-" + r.getTitle());
                            vos.add(vo);
                        });
                    });
                }
            });
        });
        List debitSubjectCodeList = rule.getSrcDebitSubjectCodeList();
        List creditSubjectCodeList = rule.getSrcCreditSubjectCodeList();
        if (com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)debitSubjectCodeList) || com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)creditSubjectCodeList)) {
            throw new BusinessRuntimeException("\u6240\u9009\u6570\u636e\u5339\u914d\u5230\u7684\u89c4\u5219\u7684\u6570\u636e\u6e90\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Set debitRuleSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(debitSubjectCodeList, reportSystemId);
        Set creditRuleSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(creditSubjectCodeList, reportSystemId);
        HashSet allRuleSubjectCodes = new HashSet();
        if (debitRuleSubjectCodes.contains(selectData.getKmcode())) {
            allRuleSubjectCodes.addAll(creditRuleSubjectCodes);
        } else {
            allRuleSubjectCodes.addAll(debitRuleSubjectCodes);
        }
        List subjectCodesByFilter = allRuleSubjectCodes.stream().filter(code -> subjectMap.keySet().contains(code)).collect(Collectors.toList());
        if (com.jiuqi.common.base.util.CollectionUtils.isEmpty(subjectCodesByFilter)) {
            return result;
        }
        List subjectsByFilter = allSubjectBySystemId.stream().filter(baseDataDO -> subjectCodesByFilter.contains(baseDataDO.getCode())).map(baseDataDO -> GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(BaseDataObjConverter.convert((BaseDataDO)SubjectConvertUtil.convertGcSubjectEOToBaseDataDO((ConsolidatedSubjectEO)baseDataDO)))).collect(Collectors.toList());
        if (com.jiuqi.common.base.util.CollectionUtils.isEmpty(subjectsByFilter)) {
            return result;
        }
        result.setSubjectsByFilter(subjectsByFilter);
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("reportSystem", reportSystemId);
        param.put("subjects", subjectsByFilter);
        List subjectITrees = this.consolidatedSubjectUIService.listSubjectTree(param);
        result.setSubjects(subjectITrees);
        TransferColumnVo selectRule = new TransferColumnVo();
        selectRule.setKey(rule.getRuleCode());
        selectRule.setLabel(rule.getLocalizedName());
        result.setRule(selectRule);
        Map<String, List> subjectRegionRel = subjectMap.entrySet().stream().filter(es -> subjectCodesByFilter.contains(es.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        result.setSubjectRegionRel(subjectRegionRel);
        INode firstLeafSubject = this.findFirstLeafSubject(subjectITrees);
        result.setSelectSubject(firstLeafSubject);
        return result;
    }

    private INode filterSubjectByBaseData(GcDataEntryContext context, String oppSubjectCode, List<GcBaseDataVO> subjectsByFilter) {
        if (com.jiuqi.common.base.util.CollectionUtils.isEmpty(subjectsByFilter)) {
            return null;
        }
        String reportSystemId = this.taskService.getConsolidatedSystemIdBySchemeId(context.getFormScheme().getKey(), this.getPeriodByContext(context));
        BaseDataDTO baseDataCondition = new BaseDataDTO();
        baseDataCondition.setTableName(MD_OPPUNIT_SUBJECT_REL);
        baseDataCondition.put("systemid", (Object)reportSystemId);
        baseDataCondition.put("oppsubject", (Object)oppSubjectCode);
        PageVO list = this.baseDataClient.list(baseDataCondition);
        if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)list.getRows())) {
            BaseDataDO baseDataDO = (BaseDataDO)list.getRows().get(0);
            String localsubject = (String)baseDataDO.get((Object)"localsubject");
            List collect = subjectsByFilter.stream().filter(subject -> localsubject.equals(subject.getCode())).collect(Collectors.toList());
            if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty(collect)) {
                return (INode)collect.get(0);
            }
        }
        return null;
    }

    private INode findFirstLeafSubject(List<SubjectITree<GcBaseDataVO>> subjectITrees) {
        SubjectITree<GcBaseDataVO> node = subjectITrees.get(0);
        if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)node.getChildren())) {
            return this.findFirstLeafSubject(node.getChildren());
        }
        return node.getData();
    }

    private static Set<String> getSubjectCodeFromRegion(String rowFilter, String tableCode) {
        HashSet<String> codes = new HashSet<String>();
        String pattern = tableCode + "\\[SUBJECTCODE\\][^=\\\"]*[=like + RegexOptions.IgnoreCase]{1}[\\s]*\\\"([\\w]+)\\%?\\\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(rowFilter);
        while (m.find()) {
            codes.add(m.group(1));
        }
        return codes;
    }

    private ElementTableVO queryAllData(GcDataEntryContext context, GcOppUnitCondition condition) {
        List<ElementTableTitleVO> title = null;
        Object[] queryResult = new Object[]{0, new ArrayList()};
        HashMap<String, Boolean> params = new HashMap<String, Boolean>();
        params.put("singleUnit", false);
        try {
            title = this.queryAllTitle(context.getTask().getKey());
            if (context.getOrgMode() != EntitiesOrgMode.DIFFERENCE) {
                queryResult = this.getOppUnitData(context, condition);
                params.put("singleUnit", context.getOrgMode() == EntitiesOrgMode.SINGLE);
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        ElementTableVO vo = new ElementTableVO(title, (Integer)queryResult[0], (List)queryResult[1]);
        vo.setParams(params);
        return vo;
    }

    private Object[] getOppUnitData(GcDataEntryContext context, GcOppUnitCondition condition) {
        List<DataField> dsignFieldDefineList;
        String taskid = context.getFormScheme().getTaskKey();
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskid);
        StringBuffer sql = new StringBuffer();
        sql.append(" select \r\n");
        sql.append(" A.NAME  UNITNAME, \r\n");
        sql.append(" B.NAME  OPPUNITNAME, \r\n");
        sql.append(" T.MEMO  REMARK ,\r\n");
        sql.append(SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"T").replace("T.MD_CURRENCY", "MD.NAME"));
        sql.append(" from ").append(tableName).append("  T \r\n");
        sql.append(" join ").append(context.getOrgTableName()).append("  A on T.MDCODE=A.code \r\n");
        sql.append(" join ").append(context.getOrgTableName()).append("  B on T.oppunitid=B.code \r\n");
        sql.append(" join MD_CURRENCY  MD on T.MD_CURRENCY=MD.code \r\n");
        sql.append(" where ");
        String systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(context.getTaskKey(), context.getYearPeriod().toString());
        if (StringUtils.isEmpty((String)systemId)) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        sql.append(" t.reportSystemId = '").append(systemId).append("' \n");
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(context.getYearPeriod().getEndDate());
        params.add(context.getYearPeriod().getEndDate());
        params.add(context.getYearPeriod().getEndDate());
        params.add(context.getYearPeriod().getEndDate());
        sql.append("and A.VALIDTIME<? and A.INVALIDTIME>=? \n");
        sql.append("and B.VALIDTIME<? and B.INVALIDTIME>=? \n");
        sql.append(" AND T.OFFSETSTATE = '0' ");
        sql.append("");
        if (context.getYearPeriod() != null) {
            String periodField = "DATATIME";
            String period = context.getYearPeriod().toString();
            sql.append(" AND T.").append(periodField).append("='").append(period).append("' \r\n");
        }
        if (context.getGcOrgType() != null) {
            String orgTypeField = "MD_GCORGTYPE";
            String orgType = context.getGcOrgType();
            String nullid = GCOrgTypeEnum.NONE.getCode();
            sql.append(" AND T.").append(orgTypeField).append(" in ('").append(orgType).append("','").append(nullid).append("' )\r\n");
        }
        if (!StringUtils.isEmpty((String)context.getOrgTableName()) && context.getInitOrg() != null) {
            if (context.getOrgMode() == EntitiesOrgMode.UNIONORG) {
                String parent = context.getInitOrg().getParentStr();
                int len = parent.length();
                sql.append(" AND B.PARENTS like '").append(parent).append("%' \r\n");
                sql.append(" AND substr(A.PARENTS,1,").append(len).append(") <> '").append(parent).append("' \r\n");
            } else {
                String org = context.getInitOrg().getCode();
                sql.append(" AND T.oppunitid='").append(org).append("' \r\n");
            }
        }
        if (context.getInitCurrency() != null) {
            String currField = "MD_CURRENCY";
            String curr = context.getInitCurrency().getCode();
            sql.append(" AND T.").append(currField).append("= '").append(curr).append("' \r\n");
        }
        if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)condition.getUnitIdList())) {
            sql.append(" AND " + SqlUtils.getConditionOfIdsUseOr((Collection)condition.getUnitIdList(), (String)"T.MDCODE"));
        }
        if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)condition.getSubjectCodeList())) {
            sql.append(" AND " + SqlUtils.getConditionOfMulStrUseOr((Collection)condition.getSubjectCodeList(), (String)"T.SUBJECTCODE"));
        }
        if (DimensionUtils.isExistAdjust((String)context.getTaskKey())) {
            sql.append("AND T.ADJUST = ?");
            params.add(((DimensionValue)context.getDimensionSet().get("ADJUST")).getValue());
        }
        Object[] re = new Object[2];
        re[0] = EntNativeSqlDefaultDao.getInstance().count(sql.toString(), params.toArray());
        if ((Integer)re[0] == 0) {
            re[1] = Collections.emptyList();
            return re;
        }
        List tzData = condition.getPageSize() == Integer.MAX_VALUE ? EntNativeSqlDefaultDao.getInstance().selectMap(sql.toString(), params.toArray()) : EntNativeSqlDefaultDao.getInstance().selectMapByPaging(sql.toString(), condition.getStartPosition().intValue(), condition.getStartPosition() + condition.getPageSize(), params.toArray());
        try {
            dsignFieldDefineList = this.getDsignFieldDefineList(taskid);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.find.table.define.error"));
        }
        String reportSystemId = this.taskService.getConsolidatedSystemIdBySchemeId(condition.getEnvContext().getFormSchemeKey(), this.getPeriodByContext(condition.getEnvContext()));
        if (reportSystemId == null) {
            Object[] i18args = new String[]{condition.getEnvContext().getFormSchemeKey()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.action.not.find.error", (Object[])i18args));
        }
        GcBaseDataCenterTool centerTool = GcBaseDataCenterTool.getInstance();
        HashMap allRefDataS = new HashMap();
        dsignFieldDefineList.stream().forEach(fieldDefine -> {
            TableModelDefine refTable;
            String refDataEntityKey = fieldDefine.getRefDataEntityKey();
            if (this.iPeriodEntityAdapter.isPeriodEntity(refDataEntityKey) || AdjustUtils.isAdjust((String)refDataEntityKey).booleanValue()) {
                return;
            }
            if (!StringUtils.isEmpty((String)refDataEntityKey) && (refTable = this.nvwaDataModelCreateUtil.queryTableModel(refDataEntityKey)) != null) {
                List allRefBaseData = centerTool.queryBasedataItems(refTable.getName());
                HashMap allRefBaseDataMap = new HashMap();
                if (!com.jiuqi.common.base.util.CollectionUtils.isEmpty((Collection)allRefBaseData)) {
                    allRefBaseData.forEach(state -> allRefBaseDataMap.put(state.getCode(), state.getTitle()));
                }
                allRefDataS.put(fieldDefine.getCode(), allRefBaseDataMap);
            }
        });
        re[1] = tzData.stream().map(f -> {
            GcBaseData baseData;
            GcOppUnitDataVO vo = new GcOppUnitDataVO();
            vo.setId(StringUtils.toViewString(f.get("ID")));
            vo.setUnitId(StringUtils.toViewString(f.get("MDCODE")));
            vo.setUnitCode(StringUtils.toViewString(f.get("MDCODE")));
            vo.setUnitName(StringUtils.toViewString(f.get("UNITNAME")));
            vo.setOppUnitId(StringUtils.toViewString(f.get("OPPUNITID")));
            vo.setOppUnitCode(StringUtils.toViewString(f.get("OPPUNITID")));
            vo.setOppUnitName(StringUtils.toViewString(f.get("OPPUNITNAME")));
            String subjectCode = StringUtils.toViewString(f.get("SUBJECTCODE"));
            if (!subjectCode.isEmpty() && (baseData = centerTool.queryBasedataByCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)subjectCode, (String[])new String[]{reportSystemId}))) != null) {
                vo.setKmid(baseData.getId());
                vo.setKmcode(baseData.getCode());
                vo.setKmname(baseData.getTitle());
            }
            vo.setAmt(NumberUtils.formatObject(f.get("AMT")));
            vo.setRemark(StringUtils.toViewString(f.get("REMARK")));
            vo.setOtherDataMap(f);
            dsignFieldDefineList.stream().forEach(designFieldDefine -> {
                boolean allMoneyTwoDecimal;
                boolean bl = allMoneyTwoDecimal = f.containsKey(designFieldDefine.getCode()) && (designFieldDefine.getType() == FieldType.FIELD_TYPE_FLOAT || designFieldDefine.getType() == FieldType.FIELD_TYPE_DECIMAL);
                if (allMoneyTwoDecimal && !"AMT".equals(designFieldDefine.getCode())) {
                    f.put(designFieldDefine.getCode(), this.formatNumberFieldValue(designFieldDefine.getDecimal(), f.get(designFieldDefine.getCode())));
                }
                if (allRefDataS.containsKey(designFieldDefine.getCode()) && f.containsKey(designFieldDefine.getCode())) {
                    Map refBaseDataMap = (Map)allRefDataS.get(designFieldDefine.getCode());
                    refBaseDataMap.keySet().forEach(key -> {
                        if (key.equals(String.valueOf(f.get(designFieldDefine.getCode())))) {
                            f.put(designFieldDefine.getCode(), refBaseDataMap.get(key));
                        }
                    });
                }
            });
            return vo;
        }).collect(Collectors.toList());
        return re;
    }

    private String formatNumberFieldValue(Integer decimal, Object value) {
        Number parse;
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(decimal);
        format.setMinimumFractionDigits(decimal);
        format.setGroupingUsed(true);
        try {
            parse = format.parse(String.valueOf(value));
        }
        catch (ParseException e) {
            parse = 0;
        }
        return format.format(parse);
    }

    public static String toViewString(Object value, int decimal) {
        if (value == null) {
            return "";
        }
        if (value instanceof Double) {
            return NumberUtils.doubleToString((double)NumberUtils.round((double)((Double)value), (int)decimal), (int)decimal, (boolean)false);
        }
        return String.valueOf(value);
    }

    public Object executeSubjectRule(GcOppUnitCondition condition) {
        ArrayList<GcBaseData> dataObjects = new ArrayList<GcBaseData>();
        ArrayList subjectCodes = new ArrayList();
        String formKey = condition.getEnvContext().getFormKey();
        List regionDefineList = this.runTimeViewController.getAllRegionsInForm(formKey);
        ArrayList dataLinkDefinesInFloatRegion = new ArrayList();
        for (DataRegionDefine dataRegionDefine : regionDefineList) {
            if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind())) continue;
            dataLinkDefinesInFloatRegion.addAll(this.runTimeViewController.getAllLinksInRegion(dataRegionDefine.getKey()));
        }
        if (dataLinkDefinesInFloatRegion.isEmpty()) {
            return null;
        }
        ArrayList allRows = new ArrayList();
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringBeanUtils.getBean(ConsolidatedTaskService.class);
        String systemId = consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(condition.getEnvContext().getTaskKey(), String.valueOf(((DimensionValue)condition.getEnvContext().getDimensionSet().get("DATATIME")).getValue()));
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(condition.getEnvContext().getTaskKey());
        if (taskDefine == null) {
            return null;
        }
        for (DataLinkDefine designDataLinkDefine : dataLinkDefinesInFloatRegion) {
            try {
                EntityViewDefine viewDefine;
                DataField fieldDefine = this.runtimeDataSchemeService.getDataField(designDataLinkDefine.getLinkExpression());
                if (!"SUBJECTOBJ".equals(fieldDefine.getCode()) || (viewDefine = this.entityViewController.buildEntityView(fieldDefine.getEntityKey(), designDataLinkDefine.getFilterExpression(), designDataLinkDefine.isIgnorePermissions())) == null) continue;
                IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
                IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
                IEntityQuery entityQuery = entityDataService.newEntityQuery();
                entityQuery.setIsolateCondition(systemId);
                entityQuery.setEntityView(viewDefine);
                entityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet((Map)condition.getEnvContext().getDimensionSet()));
                ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
                executorContext.setPeriodView(taskDefine.getDateTime());
                IEntityTable entityTable = entityQuery.executeReader((IContext)executorContext);
                if (entityTable == null) continue;
                allRows.addAll(entityTable.getAllRows());
            }
            catch (Exception e) {
                throw new BpmException((Throwable)e);
            }
        }
        String reportSystemId = this.taskService.getConsolidatedSystemIdBySchemeId(condition.getEnvContext().getFormSchemeKey(), this.getPeriodByContext(condition.getEnvContext()));
        if (reportSystemId == null) {
            Object[] i18args = new String[]{condition.getEnvContext().getFormSchemeKey()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.opp.unit.action.not.find.error", (Object[])i18args));
        }
        List rules = UnionRuleUtils.selectRuleListByReportSystemAndRuleTypes((String)reportSystemId, ruleTypes);
        if (CollectionUtils.isEmpty(rules)) {
            return null;
        }
        block4: for (AbstractUnionRule rule : rules) {
            Set debitSubjectCodeList = this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(rule.getSrcDebitSubjectCodeList(), reportSystemId);
            Set creditSubjectCodeList = this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(rule.getSrcCreditSubjectCodeList(), reportSystemId);
            for (IEntityRow iEntityRow : allRows) {
                if (!debitSubjectCodeList.contains(iEntityRow.getCode()) && !creditSubjectCodeList.contains(iEntityRow.getCode())) continue;
                if (!subjectCodes.containsAll(debitSubjectCodeList)) {
                    subjectCodes.addAll(debitSubjectCodeList);
                }
                if (subjectCodes.containsAll(creditSubjectCodeList)) continue block4;
                subjectCodes.addAll(creditSubjectCodeList);
                continue block4;
            }
        }
        GcBaseDataCenterTool centerTool = GcBaseDataCenterTool.getInstance();
        for (String subjectCode : subjectCodes) {
            GcBaseData baseData = centerTool.queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)subjectCode, (String[])new String[]{reportSystemId}));
            if (baseData == null) continue;
            dataObjects.add(baseData);
        }
        return dataObjects;
    }

    private String getPeriodByContext(DataEntryContext context) {
        Map dimensionSet = context.getDimensionSet();
        if (dimensionSet.containsKey("DATATIME")) {
            return ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        }
        return null;
    }

    static {
        ruleTypes.add(RuleTypeEnum.FLEXIBLE.getCode());
    }
}

