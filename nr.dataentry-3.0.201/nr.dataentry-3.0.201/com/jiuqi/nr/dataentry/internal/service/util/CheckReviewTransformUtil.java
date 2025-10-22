/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.GroupType
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckSchemeRecordDTO
 *  com.jiuqi.nr.data.logic.internal.obj.EntityData
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.data.logic.internal.util.DimensionUtil
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityDataLoader
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckSchemeRecordDTO;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityDataLoader;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.dataentry.bean.ReviewInfoParam;
import com.jiuqi.nr.dataentry.model.DimensionObj;
import com.jiuqi.nr.dataentry.model.EntityDataObj;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.paramInfo.ReviewSelectedInfo;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class CheckReviewTransformUtil {
    private static final Logger logger = LoggerFactory.getLogger(CheckReviewTransformUtil.class);
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private ExportExcelNameService exportExcelNameService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;

    public ReviewInfoParam getReviewInfoParam(CheckSchemeRecordDTO checkSchemeRecordDTO) {
        if (checkSchemeRecordDTO != null) {
            ReviewInfoParam reviewInfoParam = new ReviewInfoParam();
            CheckResultQueryParam checkResultQueryParam = checkSchemeRecordDTO.getCheckResultQueryParam();
            reviewInfoParam.setCheckInfo(this.getBatchCheckResultGroupInfo(checkResultQueryParam));
            Date date = new Date(checkSchemeRecordDTO.getCheckTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            reviewInfoParam.setReviewDate(sdf.format(date));
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(checkSchemeRecordDTO.getFormSchemeKey());
            if (formScheme != null) {
                reviewInfoParam.setFormSchemeTitle(formScheme.getTitle());
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                reviewInfoParam.setTaskName(taskDefine.getTitle());
            }
            StringBuilder str = new StringBuilder();
            for (String formulaSchemeKey : checkResultQueryParam.getFormulaSchemeKeys()) {
                FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
                if (formulaSchemeDefine == null) continue;
                str.append(formulaSchemeDefine.getTitle()).append(";");
            }
            String periodName = this.jtableParamService.getDataTimeEntity(checkSchemeRecordDTO.getFormSchemeKey()).getDimensionName();
            String dwName = this.jtableParamService.getDwEntity(checkSchemeRecordDTO.getFormSchemeKey()).getDimensionName();
            DimensionValueSet dimensionValueSet = checkResultQueryParam.getDimensionCollection().combineDim();
            Object value = dimensionValueSet.getValue(periodName);
            reviewInfoParam.setFormSchemeDate((String)value);
            reviewInfoParam.setDateTitle(this.exportExcelNameService.getPeriodTitle(checkSchemeRecordDTO.getFormSchemeKey(), (String)value));
            reviewInfoParam.setFormulaSchemeTitle(str.toString());
            EntityDataLoader entityDataLoader = this.entityUtil.getEntityDataLoader(this.entityUtil.getContextMainDimId(formScheme.getDw()), formScheme.getDateTime(), dimensionValueSet, null);
            String[] unitKeys = ((DimensionValue)reviewInfoParam.getCheckInfo().getContext().getDimensionSet().get(dwName)).getValue().split(";");
            ArrayList<ReviewSelectedInfo> unitList = new ArrayList<ReviewSelectedInfo>();
            for (String unitKey : unitKeys) {
                IEntityRow unitData = entityDataLoader.getRowByEntityDataKey(unitKey);
                if (unitData == null) continue;
                ReviewSelectedInfo unitInfo = new ReviewSelectedInfo();
                unitInfo.setCode(unitData.getCode());
                unitInfo.setTitle(unitData.getTitle());
                unitList.add(unitInfo);
            }
            reviewInfoParam.setUnitList(unitList);
            ArrayList<ReviewSelectedInfo> formList = new ArrayList<ReviewSelectedInfo>();
            List rangeKeys = checkResultQueryParam.getRangeKeys();
            if (checkResultQueryParam.getMode().equals((Object)Mode.FORM)) {
                for (Object rangeKey : rangeKeys) {
                    FormDefine formDefine = this.runTimeViewController.queryFormById((String)rangeKey);
                    ReviewSelectedInfo formInfo = new ReviewSelectedInfo();
                    formInfo.setTitle(formDefine.getTitle());
                    formInfo.setCode(formDefine.getFormCode());
                    formList.add(formInfo);
                }
            } else if (checkResultQueryParam.getMode().equals((Object)Mode.FORMULA)) {
                HashSet<String> formKeys = new HashSet<String>();
                for (String rangeKey : rangeKeys) {
                    FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(rangeKey);
                    String formKey = formulaDefine.getFormKey();
                    if (formKeys.contains(formKey)) continue;
                    FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                    ReviewSelectedInfo formInfo = new ReviewSelectedInfo();
                    formInfo.setTitle(formDefine.getTitle());
                    formInfo.setCode(formDefine.getFormCode());
                    formList.add(formInfo);
                    formKeys.add(formKey);
                }
            }
            reviewInfoParam.setFormList(formList);
            IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)com.jiuqi.nr.definition.internal.BeanUtil.getBean(IRunTimeViewController.class);
            TaskOrgLinkListStream taskOrgLinkListStream = iRunTimeViewController.listTaskOrgLinkStreamByTask(reviewInfoParam.getCheckInfo().getContext().getTaskKey());
            if (taskOrgLinkListStream.auth().i18n().getList().size() > 1) {
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(reviewInfoParam.getCheckInfo().getContext().getTaskKey());
                String dwDimensionName = "";
                if (taskDefine != null) {
                    dwDimensionName = iEntityMetaService.getDimensionName(taskDefine.getDw());
                }
                String entityId = com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionUtil.getDimensionValue((DimensionCollection)checkSchemeRecordDTO.getCheckResultQueryParam().getDimensionCollection(), (String)dwDimensionName).getEntityID();
                for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkListStream.auth().i18n().getList()) {
                    if (!entityId.equals(taskOrgLinkDefine.getEntity())) continue;
                    String entityTitle = "";
                    entityTitle = com.jiuqi.bi.util.StringUtils.isNotEmpty((String)taskOrgLinkDefine.getEntityAlias()) ? taskOrgLinkDefine.getEntityAlias() : iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle();
                    reviewInfoParam.setUnitCorporateTitle(entityTitle);
                }
            }
            reviewInfoParam.setDimList(this.queryOtherDims(reviewInfoParam.getCheckInfo().getContext()));
            return reviewInfoParam;
        }
        return null;
    }

    public List<DimensionObj> queryOtherDims(JtableContext context) {
        ArrayList<DimensionObj> result = new ArrayList<DimensionObj>();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (String key : context.getDimensionSet().keySet()) {
            builder.setValue(key, new Object[]{((DimensionValue)context.getDimensionSet().get(key)).getValue()});
        }
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(builder.getCollection());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String periodDimensionName = this.dataAccesslUtil.getPeriodDimensionName(entityId);
        List dimEntities = this.entityUtil.getDimEntities(formScheme);
        for (EntityData dimEntity : dimEntities) {
            String entityKey = dimEntity.getKey();
            String id = this.formSchemeService.getDimAttributeByReportDim(context.getFormSchemeKey(), entityKey);
            if (!StringUtils.hasText(id) || !this.entityMetaService.getEntityModel(formScheme.getDw()).getAttribute(id).isMultival()) continue;
            String dimensionName = dimEntity.getDimensionName();
            String originalDimValue = ((DimensionValue)context.getDimensionSet().get(dimensionName)).getValue();
            String dimensionTitle = "";
            if ("ADJUST_ENTITY_KIND".equals(dimEntity.getKind())) {
                dimensionTitle = "\u8c03\u6574\u671f";
            } else if (dimEntity.getEntityDefine() != null) {
                dimensionTitle = dimEntity.getEntityDefine().getTitle();
            }
            if (com.jiuqi.bi.util.StringUtils.isEmpty((String)originalDimValue)) {
                result.add(new DimensionObj(dimensionName, "\u5168\u90e8" + dimensionTitle, new ArrayList<EntityDataObj>()));
                continue;
            }
            if (originalDimValue.equals("PROVIDER_BASECURRENCY")) {
                result.add(new DimensionObj(dimensionName, "\u672c\u4f4d\u5e01", new ArrayList<EntityDataObj>()));
                continue;
            }
            if (originalDimValue.equals("PROVIDER_PBASECURRENCY")) {
                result.add(new DimensionObj(dimensionName, "\u4e0a\u7ea7\u672c\u4f4d\u5e01", new ArrayList<EntityDataObj>()));
                continue;
            }
            Object dimValues = dimensionValueSet.getValue(dimensionName);
            ArrayList<String> split = new ArrayList<String>();
            if (dimValues instanceof String) {
                if (!StringUtils.hasText((String)dimValues)) continue;
                for (String value : ((String)dimValues).split(";")) {
                    split.add(value);
                }
            } else {
                split.addAll((ArrayList)dimValues);
            }
            ArrayList<EntityDataObj> dims = new ArrayList<EntityDataObj>();
            if ("ADJUST_ENTITY_KIND".equals(dimEntity.getKind())) {
                String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
                List adjustPeriods = this.adjustPeriodService.queryAdjustPeriods(dataScheme, ((DimensionValue)context.getDimensionSet().get(periodDimensionName)).getValue());
                HashSet valueSet = new HashSet(split);
                adjustPeriods.forEach(adjustPeriod -> {
                    if (valueSet.contains(adjustPeriod.getCode())) {
                        dims.add(new EntityDataObj(adjustPeriod.getCode(), adjustPeriod.getTitle()));
                    }
                });
            } else {
                Date entityQueryVersionDate = this.entityUtil.period2Date(formScheme.getDateTime(), ((DimensionValue)context.getDimensionSet().get(periodDimensionName)).getValue());
                IEntityQuery query = this.entityUtil.getEntityQuery(dimEntity.getKey(), entityQueryVersionDate, dimensionValueSet, null);
                query.sorted(true);
                query.sortedByQuery(false);
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                try {
                    IEntityTable entityTable = query.executeReader((IContext)executorContext);
                    entityTable.getAllRows().forEach(row -> dims.add(new EntityDataObj(row.getEntityKeyData(), row.getTitle())));
                }
                catch (Exception e) {
                    logger.error("\u5b9e\u4f53ID\u4e3a{}\u7684\u5b9e\u4f53\u6570\u636e\u672a\u627e\u5230:{}", dimEntity.getKey(), e.getMessage(), e);
                }
            }
            result.add(new DimensionObj(dimensionName, dimensionTitle, dims));
        }
        return result;
    }

    public List<DimensionObj> queryDimObj(JtableContext context) {
        ArrayList<DimensionObj> result = new ArrayList<DimensionObj>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        List dimEntities = this.entityUtil.getDimEntities(formScheme);
        for (EntityData dimEntity : dimEntities) {
            String entityKey = dimEntity.getKey();
            String id = this.formSchemeService.getDimAttributeByReportDim(context.getFormSchemeKey(), entityKey);
            if (!StringUtils.hasText(id) || !this.entityMetaService.getEntityModel(formScheme.getDw()).getAttribute(id).isMultival()) continue;
            String dimensionName = dimEntity.getDimensionName();
            String dimensionTitle = "";
            if ("ADJUST_ENTITY_KIND".equals(dimEntity.getKind())) {
                dimensionTitle = "\u8c03\u6574\u671f";
            } else if (dimEntity.getEntityDefine() != null) {
                dimensionTitle = dimEntity.getEntityDefine().getTitle();
            }
            result.add(new DimensionObj(dimensionName, dimensionTitle, new ArrayList<EntityDataObj>()));
        }
        return result;
    }

    public Map<String, String> queryAllDimTitle(JtableContext context) {
        HashMap<String, String> result = new HashMap<String, String>();
        JtableContext curContext = new JtableContext(context);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        String entityId = this.dataAccesslUtil.contextEntityId(formScheme.getDw());
        String periodDimensionName = this.dataAccesslUtil.getPeriodDimensionName(entityId);
        List dimEntities = this.entityUtil.getDimEntities(formScheme);
        for (EntityData dimEntity : dimEntities) {
            String entityKey = dimEntity.getKey();
            String id = this.formSchemeService.getDimAttributeByReportDim(context.getFormSchemeKey(), entityKey);
            if (!StringUtils.hasText(id) || !this.entityMetaService.getEntityModel(formScheme.getDw()).getAttribute(id).isMultival()) continue;
            ((DimensionValue)curContext.getDimensionSet().get(dimEntity.getDimensionName())).setValue("");
        }
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(this.dimensionCollectionUtil.getDimensionCollection(curContext.getDimensionSet(), curContext.getFormSchemeKey()));
        for (EntityData dimEntity : dimEntities) {
            String entityKey = dimEntity.getKey();
            String id = this.formSchemeService.getDimAttributeByReportDim(context.getFormSchemeKey(), entityKey);
            if (!StringUtils.hasText(id) || !this.entityMetaService.getEntityModel(formScheme.getDw()).getAttribute(id).isMultival()) continue;
            String dimensionName = dimEntity.getDimensionName();
            Object dimValues = dimensionValueSet.getValue(dimensionName);
            ArrayList<String> split = new ArrayList<String>();
            if (dimValues instanceof String) {
                if (!StringUtils.hasText((String)dimValues)) continue;
                for (String value : ((String)dimValues).split(";")) {
                    split.add(value);
                }
            } else {
                split.addAll((ArrayList)dimValues);
            }
            if ("ADJUST_ENTITY_KIND".equals(dimEntity.getKind())) {
                String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
                List adjustPeriods = this.adjustPeriodService.queryAdjustPeriods(dataScheme, ((DimensionValue)curContext.getDimensionSet().get(periodDimensionName)).getValue());
                HashSet valueSet = new HashSet(split);
                adjustPeriods.forEach(adjustPeriod -> {
                    if (valueSet.contains(adjustPeriod.getCode())) {
                        result.put(adjustPeriod.getCode(), adjustPeriod.getTitle());
                    }
                });
                continue;
            }
            Date entityQueryVersionDate = this.entityUtil.period2Date(formScheme.getDateTime(), ((DimensionValue)curContext.getDimensionSet().get(periodDimensionName)).getValue());
            IEntityQuery query = this.entityUtil.getEntityQuery(dimEntity.getKey(), entityQueryVersionDate, dimensionValueSet, null);
            query.sorted(true);
            query.sortedByQuery(false);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            try {
                IEntityTable entityTable = query.executeReader((IContext)executorContext);
                entityTable.getAllRows().forEach(row -> result.put(row.getEntityKeyData(), row.getTitle()));
            }
            catch (Exception e) {
                logger.error("\u5b9e\u4f53ID\u4e3a{}\u7684\u5b9e\u4f53\u6570\u636e\u672a\u627e\u5230:{}", dimEntity.getKey(), e.getMessage(), e);
            }
        }
        return result;
    }

    public CheckSchemeRecordDTO getCheckSchemeRecordDTO(ReviewInfoParam reviewInfoParam) {
        CheckSchemeRecordDTO checkSchemeRecordDTO = new CheckSchemeRecordDTO();
        if (reviewInfoParam != null) {
            checkSchemeRecordDTO.setCheckResultQueryParam(this.getCheckResultQueryParam(reviewInfoParam.getCheckInfo()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                checkSchemeRecordDTO.setCheckTime(sdf.parse(reviewInfoParam.getReviewDate()).getTime());
            }
            catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
            checkSchemeRecordDTO.setFormSchemeKey(reviewInfoParam.getCheckInfo().getContext().getFormSchemeKey());
            checkSchemeRecordDTO.setKey(UUID.randomUUID().toString());
            checkSchemeRecordDTO.setUserID(NpContextHolder.getContext().getUser().getId());
        }
        return checkSchemeRecordDTO;
    }

    public CheckResultQueryParam getCheckResultQueryParam(BatchCheckResultGroupInfo batchCheckResultGroupInfo) {
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        if (batchCheckResultGroupInfo != null) {
            checkResultQueryParam.setBatchId(batchCheckResultGroupInfo.getAsyncTaskKey());
            Map dimensionSet = batchCheckResultGroupInfo.getContext().getDimensionSet();
            DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, batchCheckResultGroupInfo.getContext().getFormSchemeKey());
            checkResultQueryParam.setDimensionCollection(dimensionCollection);
            Map<String, List<String>> formulas = batchCheckResultGroupInfo.getFormulas();
            if (formulas.isEmpty()) {
                checkResultQueryParam.setMode(Mode.FORM);
                checkResultQueryParam.setRangeKeys(new ArrayList());
            } else {
                Iterator<Map.Entry<String, List<String>>> iterator = formulas.entrySet().iterator();
                if (iterator.hasNext()) {
                    Map.Entry<String, List<String>> entry = iterator.next();
                    if (entry.getValue().isEmpty()) {
                        checkResultQueryParam.setMode(Mode.FORM);
                        checkResultQueryParam.setRangeKeys(new ArrayList<String>(formulas.keySet()));
                    } else {
                        checkResultQueryParam.setMode(Mode.FORMULA);
                        ArrayList<String> formulaKeys = new ArrayList<String>();
                        for (List<String> list : formulas.values()) {
                            formulaKeys.addAll(list);
                        }
                        checkResultQueryParam.setRangeKeys(formulaKeys);
                    }
                }
            }
            checkResultQueryParam.setVariableMap(batchCheckResultGroupInfo.getContext().getVariableMap());
            checkResultQueryParam.setPagerInfo(batchCheckResultGroupInfo.getPagerInfo());
            HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
            boolean checkDesNull = batchCheckResultGroupInfo.isCheckDesNull();
            List<Integer> uploadCheckTypes = batchCheckResultGroupInfo.getUploadCheckTypes();
            List<Integer> checkTypes1 = batchCheckResultGroupInfo.getCheckTypes();
            if (uploadCheckTypes.isEmpty()) {
                for (Integer integer : checkTypes1) {
                    checkTypes.put(integer, null);
                }
            } else if (checkDesNull) {
                for (Integer integer : checkTypes1) {
                    checkTypes.put(integer, false);
                }
            } else {
                for (Integer integer : checkTypes1) {
                    checkTypes.put(integer, true);
                }
            }
            checkResultQueryParam.setCheckTypes(checkTypes);
            List list = this.getFormulaSchemeList(batchCheckResultGroupInfo.getContext().getFormSchemeKey(), batchCheckResultGroupInfo.getFormulaSchemeKeys()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            checkResultQueryParam.setFormulaSchemeKeys(list);
            GroupType groupType = GroupType.unit;
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)batchCheckResultGroupInfo.getOrderField())) {
                groupType = GroupType.getByKey((String)batchCheckResultGroupInfo.getOrderField().toLowerCase());
            }
            checkResultQueryParam.setGroupType(groupType);
        }
        return checkResultQueryParam;
    }

    public BatchCheckResultGroupInfo getBatchCheckResultGroupInfo(CheckResultQueryParam checkResultQueryParam) {
        BatchCheckResultGroupInfo batchCheckResultGroupInfo = new BatchCheckResultGroupInfo();
        if (checkResultQueryParam != null) {
            batchCheckResultGroupInfo.setAsyncTaskKey(checkResultQueryParam.getBatchId());
            batchCheckResultGroupInfo.setPagerInfo(checkResultQueryParam.getPagerInfo());
            batchCheckResultGroupInfo.setCheckTypes(new ArrayList<Integer>(checkResultQueryParam.getCheckTypes().keySet()));
            StringBuilder str = new StringBuilder();
            List formulaSchemeKeys = checkResultQueryParam.getFormulaSchemeKeys();
            for (String formulaSchemeKey : formulaSchemeKeys) {
                str.append(formulaSchemeKey).append(";");
            }
            batchCheckResultGroupInfo.setFormulaSchemeKeys(str.toString());
            batchCheckResultGroupInfo.setOrderField(checkResultQueryParam.getGroupType().getKey());
            List rangeKeys = checkResultQueryParam.getRangeKeys();
            HashMap<String, List<String>> formulas = new HashMap<String, List<String>>();
            if (!CollectionUtils.isEmpty(rangeKeys)) {
                if (checkResultQueryParam.getMode() == Mode.FORM) {
                    for (Object rangeKey : rangeKeys) {
                        formulas.put((String)rangeKey, Collections.emptyList());
                    }
                } else if (checkResultQueryParam.getMode() == Mode.FORMULA) {
                    Map<String, FormulaDefine> formulaDefineMap = this.formulaRunTimeController.getCheckFormulasInScheme((String)checkResultQueryParam.getFormulaSchemeKeys().get(0)).stream().filter(o -> o.getKey() != null).collect(Collectors.toMap(IBaseMetaItem::getKey, o -> o, (v1, v2) -> v2));
                    for (String rangeKey : rangeKeys) {
                        FormulaDefine formulaDefine = formulaDefineMap.get(rangeKey);
                        assert (formulaDefine != null) : "\u516c\u5f0f\u672a\u627e\u5230";
                        Object formKey = formulaDefine.getFormKey();
                        if (formKey == null) {
                            formKey = "00000000-0000-0000-0000-000000000000";
                        }
                        if (formulas.containsKey(formKey)) {
                            ((List)formulas.get(formKey)).add(rangeKey);
                            continue;
                        }
                        ArrayList<String> value = new ArrayList<String>();
                        value.add(rangeKey);
                        formulas.put((String)formKey, value);
                    }
                }
            }
            batchCheckResultGroupInfo.setFormulas(formulas);
            JtableContext jtableContext = new JtableContext();
            jtableContext.setVariableMap(checkResultQueryParam.getVariableMap());
            DimensionValueSet dimensionValueSet = checkResultQueryParam.getDimensionCollection().combineDim();
            Map dimensionSet = DimensionUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
            jtableContext.setDimensionSet(dimensionSet);
            StringBuffer sb = new StringBuffer();
            for (String key : checkResultQueryParam.getFormulaSchemeKeys()) {
                sb.append(key).append(";");
            }
            FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine((String)checkResultQueryParam.getFormulaSchemeKeys().get(0));
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formulaSchemeDefine.getFormSchemeKey());
            jtableContext.setFormSchemeKey(formulaSchemeDefine.getFormSchemeKey());
            jtableContext.setFormulaSchemeKey(sb.toString());
            jtableContext.setTaskKey(formSchemeDefine.getTaskKey());
            batchCheckResultGroupInfo.setContext(jtableContext);
        }
        return batchCheckResultGroupInfo;
    }

    private List<FormulaSchemeDefine> getFormulaSchemeList(String formSchemeKey, String formulaSchemeKeyStr) {
        List formulaSchemes;
        ArrayList<FormulaSchemeDefine> formulaSchemeDefine = new ArrayList<FormulaSchemeDefine>();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)formulaSchemeKeyStr)) {
            String[] idArray = formulaSchemeKeyStr.split(";");
            for (String formulaSchemeKey : idArray) {
                formulaSchemeDefine.add(this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey));
            }
        }
        if (!formulaSchemeDefine.isEmpty()) {
            return formulaSchemeDefine;
        }
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)formSchemeKey) && (formulaSchemes = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey)) != null && formulaSchemes.size() > 0) {
            for (FormulaSchemeDefine formulaScheme : formulaSchemes) {
                if (!formulaScheme.isDefault()) continue;
                formulaSchemeDefine.add(formulaScheme);
            }
        }
        return formulaSchemeDefine;
    }
}

