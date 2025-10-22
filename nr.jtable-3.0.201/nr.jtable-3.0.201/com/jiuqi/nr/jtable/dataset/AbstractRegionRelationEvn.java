/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.nr.annotation.message.RegionAnnotationResult
 *  com.jiuqi.nr.annotation.output.FormAnnotationResult
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.definition.util.DataValidationIntepretUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.EntityValueType
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.annotation.output.FormAnnotationResult;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.util.DataValidationIntepretUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.EntityValueType;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.exception.NotFoundEntityException;
import com.jiuqi.nr.jtable.exception.NotFoundFieldException;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.ErrorLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormulaLinkData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IAnnotationApplyService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class AbstractRegionRelationEvn {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRegionRelationEvn.class);
    protected IJtableParamService jtableParamService;
    protected IJtableResourceService jtableResourceService;
    protected IJtableDataEngineService jtableDataEngineService;
    protected IJtableEntityService jtableEntityService;
    protected IAnnotationApplyService annotationService;
    protected RegionData regionData;
    protected Map<String, String> fieldDataLinkMap = new LinkedHashMap<String, String>();
    protected Map<String, LinkData> dataLinkMap = new LinkedHashMap<String, LinkData>();
    protected Map<String, LinkData> linkPosMap = new LinkedHashMap<String, LinkData>();
    protected Map<String, FieldData> dataLinkFieldMap = new LinkedHashMap<String, FieldData>();
    protected Map<String, String> dataLinkFormulaMap = new LinkedHashMap<String, String>();
    protected Map<String, String> fieldBalanceFormulaMap = new LinkedHashMap<String, String>();
    protected static String balanceKey = "balance|";
    protected Map<String, List<String>> dataLinkExpressionMap = new LinkedHashMap<String, List<String>>();
    protected List<List<FieldData>> bizKeyOrderFields = new ArrayList<List<FieldData>>();
    protected List<String> bizKeyLinks = new ArrayList<String>();
    protected List<FieldData> floatOrderFields = new ArrayList<FieldData>();
    protected List<String> tableKeys = new ArrayList<String>();
    protected Map<String, Integer> cellIndexMap = new HashMap<String, Integer>();
    protected Map<String, Set<String>> entityCaptionFields = new LinkedHashMap<String, Set<String>>();
    protected List<String> enumRelationLinks = new ArrayList<String>();
    protected String gatherType;
    protected boolean entityGrade = false;
    protected boolean paginate = false;
    List<IParsedExpression> expressions = new ArrayList<IParsedExpression>();
    private boolean ignoreAnnoData = false;
    private boolean isCommitData = false;
    private DataFormaterCache dataFormaterCache;

    public AbstractRegionRelationEvn(RegionData regionData, JtableContext jtableContext) {
        this.jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.jtableResourceService = (IJtableResourceService)BeanUtil.getBean(IJtableResourceService.class);
        this.jtableDataEngineService = (IJtableDataEngineService)BeanUtil.getBean(IJtableDataEngineService.class);
        this.jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        this.regionData = regionData;
        this.dataFormaterCache = new DataFormaterCache(jtableContext);
        this.dataFormaterCache.setEntityCaptionFields(this.entityCaptionFields);
        this.annotationService = (IAnnotationApplyService)BeanUtil.getBean(IAnnotationApplyService.class);
        this.initOrderFieldData();
        this.initLinkFieldData(jtableContext);
    }

    protected abstract void initOrderFieldData();

    protected void initLinkFieldData(JtableContext jtableContext) {
        boolean isNotFoundDataLink;
        List<LinkData> allLinks = this.jtableParamService.getLinks(this.regionData.getKey());
        boolean bl = isNotFoundDataLink = (allLinks == null || allLinks.isEmpty()) && this.regionData.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue();
        if (isNotFoundDataLink) {
            Log.info((String)("\u533a\u57df" + this.regionData.getTitle() + "\u4e2d\u6ca1\u6709\u6307\u6807\u6216\u516c\u5f0f"));
            return;
        }
        LinkedHashSet<String> enumFieldPosEntity = new LinkedHashSet<String>();
        for (LinkData link : allLinks) {
            Position position = new Position(link.getCol(), link.getRow());
            this.linkPosMap.put(position.toString(), link);
        }
        for (LinkData link : allLinks) {
            EnumLinkData enumLink;
            boolean enumLinkHasDefaultVal;
            if (link.getCol() < this.regionData.getRegionLeft() || link.getRow() < this.regionData.getRegionTop() || link.getCol() > this.regionData.getRegionRight() || link.getRow() > this.regionData.getRegionBottom()) continue;
            this.dataLinkMap.put(link.getKey(), link);
            if (link.getExpression() != null && link.getExpression().size() > 0) {
                this.dataLinkExpressionMap.put(link.getKey(), link.getExpression());
            }
            boolean bl2 = enumLinkHasDefaultVal = link instanceof EnumLinkData && !CollectionUtils.isEmpty(this.regionData.getRegionEntityDefaultValue());
            if (StringUtils.isNotEmpty((String)link.getDefaultValue()) || enumLinkHasDefaultVal) {
                if (enumLinkHasDefaultVal) {
                    enumLink = (EnumLinkData)link;
                    Optional<EntityDefaultValue> optional = this.regionData.getRegionEntityDefaultValue().stream().filter(Objects::nonNull).filter(e -> e.getEntityId() != null).filter(e -> e.getEntityId().equals(enumLink.getEntityKey())).findFirst();
                    if (optional.isPresent()) {
                        EntityDefaultValue entityDefaultValue = optional.get();
                        Object val = RegionSettingUtil.checkRegionDefaultValueGetIfAbsent(this.regionData, link);
                        Object fieldValue = link.getFormatData((AbstractData)new StringData(val.toString()), this.dataFormaterCache, jtableContext);
                        if (entityDefaultValue.getEntityValueType() == EntityValueType.DATA_ITEM_CODE) {
                            Map<String, EntityReturnInfo> entityDataMap = this.dataFormaterCache.getEntityDataMap();
                            EntityReturnInfo entityReturnInfo = entityDataMap.get(enumLink.getEntityKey());
                            if (entityReturnInfo != null && entityReturnInfo.getEntitys() != null && entityReturnInfo.getEntitys().size() > 0) {
                                link.setDefaultValue(fieldValue.toString());
                            }
                        } else {
                            link.setDefaultValue(fieldValue.toString());
                        }
                    } else if (StringUtils.isNotEmpty((String)link.getDefaultValue())) {
                        AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(link.getDefaultValue(), jtableContext, DimensionValueSetUtil.getDimensionValueSet(jtableContext));
                        if (expressionEvaluat != null) {
                            Object fieldValue = link.getFormatData(expressionEvaluat, this.dataFormaterCache, jtableContext);
                            if (fieldValue == null) {
                                link.setDefaultValue(link.getDefaultValue());
                            } else {
                                link.setDefaultValue(fieldValue.toString());
                            }
                        } else {
                            link.setDefaultValue(link.getDefaultValue());
                        }
                    }
                } else {
                    AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(link.getDefaultValue(), jtableContext, DimensionValueSetUtil.getDimensionValueSet(jtableContext));
                    if (expressionEvaluat != null) {
                        Object fieldValue = link.getFormatData(expressionEvaluat, this.dataFormaterCache, jtableContext);
                        if (fieldValue == null) {
                            link.setDefaultValue(link.getDefaultValue());
                        } else {
                            link.setDefaultValue(fieldValue.toString());
                        }
                    } else {
                        link.setDefaultValue(link.getDefaultValue());
                    }
                }
            }
            if (link instanceof FormulaLinkData) {
                FormulaLinkData formulaLink = (FormulaLinkData)link;
                this.dataLinkFormulaMap.put(formulaLink.getKey(), formulaLink.getFormula());
            } else {
                if (link instanceof ErrorLinkData) {
                    throw new NotFoundEntityException(JtableExceptionCodeCost.NOTFOUND_ENTITY_BYKEY, new String[]{((ErrorLinkData)link).getError()});
                }
                FieldData fieldData = this.jtableParamService.getField(link.getZbid());
                FieldType type = FieldType.forValue((int)fieldData.getFieldType());
                if (type == FieldType.FIELD_TYPE_DECIMAL && jtableContext.getDecimal() != null && !jtableContext.getDecimal().equals("")) {
                    fieldData.setFractionDigits(Integer.valueOf(jtableContext.getDecimal()));
                }
                if (StringUtils.isNotEmpty((String)link.getBindingExpression())) {
                    String formula = link.getBindingExpression();
                    this.dataLinkFormulaMap.put(link.getKey(), formula);
                    this.dataLinkFieldMap.put(link.getKey(), fieldData);
                    this.fieldDataLinkMap.put(link.getZbid(), link.getKey());
                } else {
                    this.fieldDataLinkMap.put(link.getZbid(), link.getKey());
                    this.dataLinkFieldMap.put(link.getKey(), fieldData);
                }
            }
            if (!(link instanceof EnumLinkData)) continue;
            enumLink = (EnumLinkData)link;
            FieldData fieldData = this.jtableParamService.getField(link.getZbid());
            Set<Object> captionFields = null;
            if (this.entityCaptionFields.containsKey(enumLink.getEntityKey())) {
                captionFields = this.entityCaptionFields.get(enumLink.getEntityKey());
            } else {
                captionFields = new LinkedHashSet();
                this.entityCaptionFields.put(enumLink.getEntityKey(), captionFields);
            }
            if (enumLink.getCapnames() != null && !enumLink.getCapnames().isEmpty()) {
                captionFields.addAll(enumLink.getCapnames());
            }
            if (enumLink.getDropnames() != null && !enumLink.getDropnames().isEmpty()) {
                captionFields.addAll(enumLink.getDropnames());
            }
            if (enumLink.getEnumFieldPosMap() == null || enumLink.getEnumFieldPosMap().isEmpty()) continue;
            captionFields.addAll(enumLink.getEnumFieldPosMap().keySet());
            for (Map.Entry<String, String> posMap : enumLink.getEnumFieldPosMap().entrySet()) {
                String fieldPos = posMap.getValue();
                LinkData enumFieldPosLink = this.linkPosMap.get(fieldPos);
                if (enumFieldPosLink != null) {
                    this.enumRelationLinks.add(enumFieldPosLink.getKey());
                }
                if (enumFieldPosLink == null || StringUtils.isEmpty((String)fieldData.getEntityKey())) continue;
                enumFieldPosEntity.add(fieldData.getEntityKey());
            }
        }
        for (String entityKey : enumFieldPosEntity) {
            EntityQueryByViewInfo entityQueryByViewInfo = new EntityQueryByViewInfo();
            entityQueryByViewInfo.setEntityViewKey(entityKey);
            entityQueryByViewInfo.setContext(jtableContext);
            entityQueryByViewInfo.setCaptionFields(this.entityCaptionFields.get(entityKey));
            EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryEntityData(entityQueryByViewInfo);
            this.dataFormaterCache.addEntityData(entityKey, entityReturnInfo);
        }
        Map<String, String> balanceFormulaMap = FormulaUtil.getBalanceFormulaMap(jtableContext.getFormulaSchemeKey(), jtableContext.getFormKey());
        for (String fieldKey : balanceFormulaMap.keySet()) {
            if (!this.fieldDataLinkMap.containsKey(fieldKey)) continue;
            String condition = balanceFormulaMap.get(fieldKey);
            this.fieldBalanceFormulaMap.put(balanceKey + fieldKey, condition);
        }
    }

    public boolean getAllowDuplicate() {
        if (this.bizKeyOrderFields.isEmpty()) {
            return true;
        }
        return this.regionData.getAllowDuplicateKey();
    }

    public RegionData getRegionData() {
        return this.regionData;
    }

    public List<List<FieldData>> getBizKeyOrderFields() {
        return this.bizKeyOrderFields;
    }

    public List<String> getBizKeyLinks() {
        return this.bizKeyLinks;
    }

    public List<FieldData> getFloatOrderFields() {
        return this.floatOrderFields;
    }

    public Map<String, FieldData> getDataLinkFieldMap() {
        return this.dataLinkFieldMap;
    }

    public String getDataLinkKeyByFiled(String fieldKey) {
        return this.fieldDataLinkMap.get(fieldKey);
    }

    public LinkData getDataLinkByFiled(String fieldKey) {
        String linkKey = this.fieldDataLinkMap.get(fieldKey);
        if (StringUtils.isNotEmpty((String)linkKey)) {
            return this.dataLinkMap.get(linkKey);
        }
        return null;
    }

    public LinkData getDataLinkByKey(String linkKey) {
        return this.dataLinkMap.get(linkKey);
    }

    public FieldData getFieldByDataLink(String dataLinkKey) {
        return this.dataLinkFieldMap.get(dataLinkKey);
    }

    public Map<String, String> getDataLinkFormulaMap() {
        return this.dataLinkFormulaMap;
    }

    public Map<String, String> getFieldBalanceFormulaMap() {
        return this.fieldBalanceFormulaMap;
    }

    public Map<String, List<String>> getDataLinkExpressionMap() {
        return this.dataLinkExpressionMap;
    }

    public List<IParsedExpression> getExpressions() {
        if (this.expressions.isEmpty() && !this.dataLinkExpressionMap.isEmpty()) {
            ArrayList<Formula> formulas = new ArrayList<Formula>();
            IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
            IRuntimeDataLinkService runtimeDataLinkService = (IRuntimeDataLinkService)BeanUtil.getBean(IRuntimeDataLinkService.class);
            IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
            ValidationRule validationRule = null;
            for (String key : this.dataLinkExpressionMap.keySet()) {
                DataLinkDefine dataLinkDefine = runtimeDataLinkService.queryDataLink(key);
                DataField dataField = runtimeDataSchemeService.getDataField(dataLinkDefine.getLinkExpression());
                DataTable dataTable = runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
                String bizKeys = Arrays.toString(dataTable.getBizKeys());
                boolean isAccBizKey = dataTable.getDataTableType() == DataTableType.ACCOUNT && bizKeys.contains(dataField.getKey());
                List<String> linkExps = this.dataLinkExpressionMap.get(key);
                for (String linkExp : linkExps) {
                    validationRule = DataSchemeUtils.getValidationRule((String)linkExp);
                    if (isAccBizKey && (validationRule.getVerification().contains("NOT ISNULL") || validationRule.getCompareType() == CompareType.NOTNULL) || null == validationRule) continue;
                    Formula formula = new Formula();
                    formula.setId(key);
                    formula.setFormula(validationRule.getVerification());
                    formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                    if (StringUtils.isEmpty((String)validationRule.getMessage())) {
                        formula.setMeanning(DataValidationIntepretUtil.intepret((IDataDefinitionRuntimeController)dataDefinitionRuntimeController, (String)formula.getFormula()));
                    } else {
                        formula.setMeanning(validationRule.getMessage());
                    }
                    formulas.add(formula);
                }
            }
            ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
            try {
                List parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK);
                if (parsedExpressions != null && !parsedExpressions.isEmpty()) {
                    this.expressions.addAll(parsedExpressions);
                }
            }
            catch (ParseException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return this.expressions;
    }

    public void putCellIndex(String cell, int cellIndex) {
        this.cellIndexMap.put(cell, cellIndex);
    }

    public int getCellIndex(String cell) {
        Integer index = this.cellIndexMap.get(cell);
        return index == null ? -1 : index;
    }

    public List<String> getTableKeyList() {
        return this.tableKeys;
    }

    public Map<String, Set<String>> getEntityCaptionFields() {
        return this.entityCaptionFields;
    }

    public String getGatherType() {
        return this.gatherType;
    }

    public FieldData getFieldData(String fieldKey) {
        FieldData fieldData = null;
        try {
            fieldData = this.jtableParamService.getField(fieldKey);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (fieldData == null) {
            throw new NotFoundFieldException(new String[]{fieldKey});
        }
        return fieldData;
    }

    public RegionAnnotationResult getRegionAnnotationResult(JtableContext context) {
        Map regions;
        RegionAnnotationResult result = new RegionAnnotationResult();
        if (this.ignoreAnnoData) {
            return result;
        }
        FormAnnotationResult queryFormAnnotation = this.annotationService.queryFormAnnotation(context, this.regionData.getKey());
        result.setRegionKey(this.regionData.getKey());
        if (null != queryFormAnnotation && null != (regions = queryFormAnnotation.getRegions())) {
            if (regions.containsKey(this.regionData.getKey())) {
                return (RegionAnnotationResult)regions.get(this.regionData.getKey());
            }
            return result;
        }
        return result;
    }

    public List<String> getCalcDataLinks() {
        ArrayList<String> regionCalcDataLinks = new ArrayList<String>();
        List<String> calcDataLinks = this.jtableParamService.getCalcDataLinks(this.dataFormaterCache.getJtableContext());
        for (String calcDataLink : calcDataLinks) {
            if (!this.dataLinkMap.containsKey(calcDataLink)) continue;
            regionCalcDataLinks.add(calcDataLink);
        }
        return regionCalcDataLinks;
    }

    public boolean isEntityGrade() {
        return this.entityGrade;
    }

    public void setEntityGrade(boolean entityGrade) {
        entityGrade = true;
        if (true) {
            this.paginate = false;
        }
        this.entityGrade = entityGrade;
    }

    public boolean isPaginate() {
        return this.paginate;
    }

    public void setPaginate(boolean paginate) {
        this.paginate = paginate;
    }

    public Map<String, LinkData> getDataLinkMap() {
        return this.dataLinkMap;
    }

    public Map<String, LinkData> getLinkPosMap() {
        return this.linkPosMap;
    }

    public List<String> getEnumRelationLinks() {
        return this.enumRelationLinks;
    }

    public boolean isIgnoreAnnoData() {
        return this.ignoreAnnoData;
    }

    public void setIgnoreAnnoData(boolean ignoreAnnoData) {
        this.ignoreAnnoData = ignoreAnnoData;
    }

    public DataFormaterCache getDataFormaterCache() {
        return this.dataFormaterCache;
    }

    public boolean isCommitData() {
        return this.isCommitData;
    }

    public void setCommitData(boolean isCommitData) {
        this.isCommitData = isCommitData;
    }
}

