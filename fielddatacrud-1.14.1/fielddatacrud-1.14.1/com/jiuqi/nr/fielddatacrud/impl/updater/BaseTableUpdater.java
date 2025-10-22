/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.FieldValidateResult
 *  com.jiuqi.np.dataengine.common.RowExpressionValidResult
 *  com.jiuqi.np.dataengine.common.RowValidateResult
 *  com.jiuqi.np.dataengine.common.ValidateResult
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.DataValidateException
 *  com.jiuqi.np.dataengine.exception.DuplicateRowKeysException
 *  com.jiuqi.np.dataengine.exception.ExpressionValidateException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.exception.ValueValidateException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.ParseReturnRes
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveResItem
 *  com.jiuqi.nr.datacrud.SaveRowData
 *  com.jiuqi.nr.datacrud.i18n.CrudMessageSource
 *  com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogger
 *  com.jiuqi.nr.datacrud.impl.loggger.EmptyDataServiceLogger
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.out.ReturnResInstance
 *  com.jiuqi.nr.datacrud.impl.parse.BaseTypeParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.BooleanParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.ClobParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DateParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DateTimeParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.FileParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.IntParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.ObjParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.PictureParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy
 *  com.jiuqi.nr.datacrud.impl.service.DataEngineService
 *  com.jiuqi.nr.datacrud.spi.IEntityTableFactory
 *  com.jiuqi.nr.datacrud.spi.IExecutorContextFactory
 *  com.jiuqi.nr.datacrud.spi.TypeParseStrategy
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.fielddatacrud.impl.updater;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.FieldValidateResult;
import com.jiuqi.np.dataengine.common.RowExpressionValidResult;
import com.jiuqi.np.dataengine.common.RowValidateResult;
import com.jiuqi.np.dataengine.common.ValidateResult;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.ExpressionValidateException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.ValueValidateException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveRowData;
import com.jiuqi.nr.datacrud.i18n.CrudMessageSource;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogger;
import com.jiuqi.nr.datacrud.impl.loggger.EmptyDataServiceLogger;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.ReturnResInstance;
import com.jiuqi.nr.datacrud.impl.parse.BaseTypeParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.BooleanParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.ClobParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DateParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DateTimeParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.FileParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.IntParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.ObjParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.PictureParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.fielddatacrud.RegionPO;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.dto.AccessDTO;
import com.jiuqi.nr.fielddatacrud.impl.dto.DimField;
import com.jiuqi.nr.fielddatacrud.impl.strategy.CsvEnumParseStrategy;
import com.jiuqi.nr.fielddatacrud.spi.AttachmentMarkService;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.ISBImportActuatorFactory;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class BaseTableUpdater
implements TableUpdater {
    private static final Logger logger = LoggerFactory.getLogger(BaseTableUpdater.class);
    protected TypeParseStrategy defaultParseStrategy;
    protected TypeParseStrategy nonTypeParseStrategy;
    protected final EnumMap<DataFieldType, TypeParseStrategy> typeParseStrategyEnumMap = new EnumMap(DataFieldType.class);
    protected CrudMessageSource messageSource;
    protected IEntityTableFactory entityTableFactory;
    protected IExecutorContextFactory executorContextFactory;
    protected DataEngineService dataEngineService;
    protected FieldRelation fieldRelation;
    protected FieldSaveInfo saveInfo;
    protected AttachmentMarkService attachmentMarkService;
    protected FieldDataStrategyFactory strategyFactory;
    protected ISBImportActuatorFactory sbImportActuatorFactory;
    protected Set<DimensionValueSet> accessMasterKeys;
    protected AccessDTO accessDTO;
    protected DimensionValueSet masterKeys;
    protected TableDimSet tableDimSet;
    protected List<DimField> dimFields;
    protected List<IMetaData> fileMetas;
    protected Set<String> fileGroupKeys;
    protected Set<String> picGroupKeys;
    protected SaveRowData currRow;
    protected boolean rowByDw;
    protected FieldDataProperties fieldDataProperties;
    protected final Set<String> noPermissionDw = new LinkedHashSet<String>();

    public BaseTableUpdater(FieldSaveInfo saveInfo, FieldRelation fieldRelation) {
        DimField dimField;
        this.saveInfo = saveInfo;
        this.fieldRelation = fieldRelation;
        ArrayList<IMetaData> fileMetas = new ArrayList<IMetaData>();
        List<IMetaData> fields = saveInfo.getFields();
        for (IMetaData field : fields) {
            DataField dataField = field.getDataField();
            if (dataField.getDataFieldType() != DataFieldType.FILE && dataField.getDataFieldType() != DataFieldType.PICTURE) continue;
            fileMetas.add(field);
        }
        if (!fileMetas.isEmpty()) {
            this.fileMetas = fileMetas;
            this.fileGroupKeys = new HashSet<String>();
            this.picGroupKeys = new HashSet<String>();
        }
        this.tableDimSet = this.fieldRelation.getTableDim(fields).get(0);
        this.dimFields = new ArrayList<DimField>();
        for (DataField dataField : this.tableDimSet.getDimField()) {
            dimField = new DimField();
            dimField.setCode(dataField.getCode());
            dimField.setDimName(this.tableDimSet.getFieldCode2DimName().get(dataField.getCode()));
            dimField.setType(DimField.P_DIM);
            dimField.setTitle(dataField.getTitle());
            this.dimFields.add(dimField);
        }
        for (DataField dataField : this.tableDimSet.getTableDimField()) {
            dimField = new DimField();
            dimField.setCode(dataField.getCode());
            dimField.setDimName(this.tableDimSet.getFieldCode2DimName().get(dataField.getCode()));
            dimField.setType(DimField.T_DIM);
            dimField.setTitle(dataField.getTitle());
            this.dimFields.add(dimField);
        }
        block3: for (DimField dimField2 : this.dimFields) {
            for (int i = 0; i < fields.size(); ++i) {
                IMetaData metaData = fields.get(i);
                if (!dimField2.getCode().equals(metaData.getCode())) continue;
                dimField2.setIndex(i);
                continue block3;
            }
        }
    }

    @Override
    public void installParseStrategy() {
        this.typeParseStrategyEnumMap.put(DataFieldType.BOOLEAN, (TypeParseStrategy)new BooleanParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.typeParseStrategyEnumMap.put(DataFieldType.DATE, (TypeParseStrategy)new DateParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.typeParseStrategyEnumMap.put(DataFieldType.DATE_TIME, (TypeParseStrategy)new DateTimeParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.typeParseStrategyEnumMap.put(DataFieldType.BIGDECIMAL, (TypeParseStrategy)new DecimalParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.typeParseStrategyEnumMap.put(DataFieldType.INTEGER, (TypeParseStrategy)new IntParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.typeParseStrategyEnumMap.put(DataFieldType.STRING, (TypeParseStrategy)new CsvEnumParseStrategy(this.fieldRelation, this.entityTableFactory).setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.typeParseStrategyEnumMap.put(DataFieldType.CLOB, (TypeParseStrategy)new ClobParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.typeParseStrategyEnumMap.put(DataFieldType.FILE, (TypeParseStrategy)new FileParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.typeParseStrategyEnumMap.put(DataFieldType.PICTURE, (TypeParseStrategy)new PictureParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger()));
        this.nonTypeParseStrategy = new ObjParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger());
        this.defaultParseStrategy = new StringParseStrategy().setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger());
    }

    @Override
    public void registerParseStrategy(int type, TypeParseStrategy parseStrategy) {
        DataFieldType nodeType = DataFieldType.valueOf((int)type);
        if (parseStrategy == null || nodeType == null) {
            return;
        }
        if (parseStrategy instanceof BaseTypeParseStrategy) {
            ((BaseTypeParseStrategy)parseStrategy).setMessageSource(this.messageSource).setCrudLogger((DataServiceLogger)new EmptyDataServiceLogger());
        }
        this.typeParseStrategyEnumMap.put(nodeType, parseStrategy);
    }

    public void setMessageSource(CrudMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setEntityTableFactory(IEntityTableFactory entityTableFactory) {
        this.entityTableFactory = entityTableFactory;
    }

    public void setAccessMasterKeys(Set<DimensionValueSet> accessMasterKeys) {
        this.accessMasterKeys = accessMasterKeys;
        this.masterKeys = accessMasterKeys != null && !accessMasterKeys.isEmpty() ? DimensionValueSetUtil.mergeDimensionValueSet(new ArrayList<DimensionValueSet>(accessMasterKeys)) : null;
    }

    public void setExecutorContextFactory(IExecutorContextFactory executorContextFactory) {
        this.executorContextFactory = executorContextFactory;
    }

    public void setDataEngineService(DataEngineService dataEngineService) {
        this.dataEngineService = dataEngineService;
    }

    public void setAttachmentMarkService(AttachmentMarkService attachmentMarkService) {
        this.attachmentMarkService = attachmentMarkService;
    }

    public void setStrategyFactory(FieldDataStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public void setSbImportActuatorFactory(ISBImportActuatorFactory sbImportActuatorFactory) {
        this.sbImportActuatorFactory = sbImportActuatorFactory;
    }

    public AccessDTO getAccessDTO() {
        return this.accessDTO;
    }

    public void setAccessDTO(AccessDTO accessDTO) {
        this.accessDTO = accessDTO;
        this.setAccessMasterKeys(accessDTO.getAccessMasterKeys());
        if (this.saveInfo.getMode() == ImpMode.FULL && this.saveInfo.getAuthMode() != ResouceType.NONE) {
            for (DimensionValueSet noAccessMasterKey : accessDTO.getNoAccessMasterKeys()) {
                Object value = noAccessMasterKey.getValue(this.fieldRelation.getDwDimName());
                if (value == null) continue;
                this.noPermissionDw.add(value.toString());
            }
        }
    }

    public void setFieldDataProperties(FieldDataProperties fieldDataProperties) {
        this.fieldDataProperties = fieldDataProperties;
    }

    protected List<SaveResItem> exceptionErrors(Exception e) {
        SaveResItem resItem;
        DataValidateException dataValidateException;
        ArrayList<SaveResItem> errorList = new ArrayList<SaveResItem>();
        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        if (e instanceof ValueValidateException) {
            ValueValidateException valueValidateException = (ValueValidateException)e;
            List validateResults = valueValidateException.getRowValidateResults();
            for (RowValidateResult rowValidateResult : validateResults) {
                DimensionValueSet rowKeys = rowValidateResult.getRowKeys();
                DimensionCombination rowCombination = null;
                if (rowKeys != null) {
                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder(rowKeys);
                    rowCombination = builder.getCombination();
                }
                List fieldValidateResults = rowValidateResult.getFieldValidateResults();
                for (FieldValidateResult fieldValidateResult : fieldValidateResults) {
                    String resultMsg = this.getResultMsg(fieldValidateResult);
                    if (!StringUtils.hasLength(resultMsg)) continue;
                    SaveResItem resItem2 = new SaveResItem();
                    resItem2.setDimension(rowCombination);
                    resItem2.setMessage(resultMsg);
                    errorList.add(resItem2);
                }
            }
        } else if (e instanceof ExpressionValidateException) {
            ExpressionValidateException expressionValidateException = (ExpressionValidateException)e;
            List rowExpressionValidResults = expressionValidateException.getRowExpressionValidResults();
            for (RowExpressionValidResult rowExpressionValidResult : rowExpressionValidResults) {
                List errorExpressions = rowExpressionValidResult.getErrorExpressions();
                DimensionValueSet rowKeys = rowExpressionValidResult.getRowKeys();
                DimensionCombination combination = null;
                if (rowKeys != null) {
                    DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(rowKeys);
                    combination = dimensionCombinationBuilder.getCombination();
                }
                for (IParsedExpression errorExpression : errorExpressions) {
                    SaveResItem resItem3 = new SaveResItem();
                    Formula formula = errorExpression.getSource();
                    String message = e.getMessage() + (StringUtils.hasLength(formula.getMeanning()) ? formula.getMeanning() : formula.getFormula());
                    if (!StringUtils.hasLength(message)) {
                        message = "\u6570\u636e\u6821\u9a8c\u5931\u8d25";
                    }
                    resItem3.setDimension(combination);
                    resItem3.setLinkKey(formula.getId());
                    resItem3.setMessage(message);
                    errorList.add(resItem3);
                }
            }
        } else if (e instanceof DataValidateException) {
            dataValidateException = (DataValidateException)e;
            SaveResItem resItem4 = new SaveResItem();
            String message = dataValidateException.getMessage();
            if (!StringUtils.hasLength(message)) {
                message = "\u6570\u636e\u6821\u9a8c\u5931\u8d25";
            }
            resItem4.setMessage(message);
            errorList.add(resItem4);
        } else if (e instanceof DuplicateRowKeysException) {
            dataValidateException = (DuplicateRowKeysException)e;
            List duplicateKeys = dataValidateException.getDuplicateKeys();
            if (duplicateKeys != null) {
                for (DimensionValueSet duplicateKey : duplicateKeys) {
                    ArrayList<String> titles = new ArrayList<String>();
                    ArrayList<String> values = new ArrayList<String>();
                    for (DimField dimField : this.dimFields) {
                        Object dimValue = duplicateKey.getValue(dimField.getDimName());
                        if (!Objects.nonNull(dimValue)) continue;
                        titles.add(dimField.getTitle());
                        values.add(String.valueOf(dimValue));
                    }
                    StringBuilder message = new StringBuilder();
                    if (!titles.isEmpty()) {
                        for (String title : titles) {
                            message.append("\u3010").append(title).append("\u3011");
                        }
                        if (titles.size() > 1) {
                            message.append("\u7684\u7ec4\u5408");
                        }
                        message.append("\u5b58\u5728\u91cd\u590d\u6570\u636e");
                        for (String value : values) {
                            message.append("\u3010").append(value).append("\u3011");
                        }
                    }
                    if (message.length() > 0) {
                        message.append("\u3002");
                    }
                    SaveResItem saveResItem = new SaveResItem();
                    saveResItem.setMessage(message.toString());
                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder(duplicateKey);
                    saveResItem.setDimension(builder.getCombination());
                    errorList.add(saveResItem);
                }
            }
        } else if (e instanceof IncorrectQueryException) {
            resItem = new SaveResItem();
            resItem.setMessage(e.getMessage());
            errorList.add(resItem);
        }
        if (errorList.isEmpty()) {
            resItem = new SaveResItem();
            resItem.setMessage("\u6570\u636e\u4fdd\u5b58\u51fa\u9519");
            errorList.add(resItem);
        }
        return errorList;
    }

    protected String getResultMsg(FieldValidateResult fieldValidateResult) {
        List validateResults = fieldValidateResult.getValidateResult();
        if (CollectionUtils.isEmpty(validateResults)) {
            return null;
        }
        StringBuilder resultMsg = new StringBuilder();
        for (ValidateResult validateResult : validateResults) {
            if (validateResult.isResultValue()) continue;
            resultMsg.append(validateResult.getResultMsg());
        }
        return resultMsg.toString();
    }

    public ReturnRes setData(int index, Object data) {
        AbstractData[] linkValues = this.currRow.getLinkValues();
        if (linkValues == null) {
            return ReturnResInstance.ERR_INSTANCE;
        }
        if (index > linkValues.length || index < 0) {
            return ReturnRes.build((int)1001, (String)"\u6307\u6807\u4e0d\u5b58\u5728\u6216\u5df2\u4e22\u5931");
        }
        IMetaData meta = this.saveInfo.getFields().get(index);
        for (DimField dimField : this.dimFields) {
            if (dimField.getType() != DimField.P_DIM || !dimField.getCode().equals(meta.getCode())) continue;
            linkValues[index] = AbstractData.valueOf((Object)data, (int)meta.getDataType());
            return ReturnRes.success();
        }
        DataFieldType dataType = meta != null ? meta.getDataFieldType() : null;
        try {
            ParseReturnRes res;
            TypeParseStrategy typeParseStrategy;
            if (dataType != null) {
                typeParseStrategy = this.typeParseStrategyEnumMap.get(dataType);
                if (typeParseStrategy == null) {
                    typeParseStrategy = this.defaultParseStrategy;
                }
            } else {
                typeParseStrategy = this.nonTypeParseStrategy;
            }
            DataField dataField = null;
            if (meta != null) {
                dataField = meta.getDataField();
            }
            if ((res = typeParseStrategy.checkParse(null, dataField, data)).isSuccess()) {
                linkValues[index] = res.getAbstractData();
            }
            return res;
        }
        catch (Exception e) {
            logger.warn("\u4fdd\u5b58\u6570\u636e,\u8f6c\u6362\u6570\u636e\u5931\u8d25", e);
            throw new CrudException(4601);
        }
    }

    protected void addFileMark(IDataRow row) {
        if (row == null) {
            return;
        }
        if (CollectionUtils.isEmpty(this.fileMetas) || this.attachmentMarkService == null) {
            return;
        }
        for (IMetaData fileMeta : this.fileMetas) {
            String groupKey = row.getAsString(fileMeta.getIndex());
            if (!StringUtils.hasLength(groupKey)) continue;
            if (DataFieldType.FILE == fileMeta.getDataField().getDataFieldType()) {
                this.fileGroupKeys.add(groupKey);
                continue;
            }
            if (DataFieldType.PICTURE != fileMeta.getDataField().getDataFieldType()) continue;
            this.picGroupKeys.add(groupKey);
        }
    }

    protected void currentFileMark() {
        if (CollectionUtils.isEmpty(this.fileMetas) || this.attachmentMarkService == null) {
            return;
        }
        if (CollectionUtils.isEmpty(this.fileGroupKeys) && CollectionUtils.isEmpty(this.picGroupKeys)) {
            return;
        }
        IParamDataProvider paramDataProvider = this.strategyFactory.getParamDataProvider();
        ParamProvider paramProvider = paramDataProvider.getParamProvider();
        List<String> codes = this.fileMetas.stream().map(IMetaData::getCode).collect(Collectors.toList());
        Set<RegionPO> regions = paramProvider.getRegions(this.tableDimSet.getDataTable().getCode(), codes);
        if (!CollectionUtils.isEmpty(regions)) {
            Optional first = regions.stream().findFirst();
            RegionPO regionPO = (RegionPO)first.get();
            if (!CollectionUtils.isEmpty(this.fileGroupKeys)) {
                this.attachmentMarkService.batchMarkDeletion(regionPO.getFormSchemeKey(), this.fileGroupKeys);
            }
            if (!CollectionUtils.isEmpty(this.picGroupKeys)) {
                this.attachmentMarkService.batchMarkPictureDeletion(regionPO.getFormSchemeKey(), this.picGroupKeys);
            }
        }
        if (!CollectionUtils.isEmpty(this.fileGroupKeys)) {
            this.fileGroupKeys.clear();
        }
        if (!CollectionUtils.isEmpty(this.picGroupKeys)) {
            this.picGroupKeys.clear();
        }
    }

    protected void fileMark(DimensionValueSet masterKeys) throws CrudOperateException {
        if (CollectionUtils.isEmpty(this.fileMetas) || this.attachmentMarkService == null) {
            return;
        }
        HashSet<String> fileGroupKeys = new HashSet<String>();
        HashSet<String> picGroupKeys = new HashSet<String>();
        try {
            IDataQuery dataQuery = this.dataEngineService.getDataQuery();
            for (IMetaData field : this.fileMetas) {
                int i = dataQuery.addColumn((FieldDefine)((DataFieldDTO)field.getDataField()));
                field.setIndex(i);
            }
            Iterator<Variable> varItr = null;
            if (this.saveInfo.getVariables() != null) {
                varItr = this.saveInfo.getVariables().iterator();
            }
            ExecutorContext context = this.executorContextFactory.getExecutorContext((ParamRelation)this.fieldRelation, (DimensionValueSet)null, varItr);
            dataQuery.setMasterKeys(masterKeys);
            IReadonlyTable table = dataQuery.executeReader(context);
            int totalCount = table.getTotalCount();
            for (int r = 0; r < totalCount; ++r) {
                IDataRow row = table.getItem(r);
                for (IMetaData meta : this.fileMetas) {
                    String groupKey = row.getAsString(meta.getIndex());
                    if (!StringUtils.hasLength(groupKey)) continue;
                    if (DataFieldType.FILE == meta.getDataField().getDataFieldType()) {
                        fileGroupKeys.add(groupKey);
                        continue;
                    }
                    if (DataFieldType.PICTURE != meta.getDataField().getDataFieldType()) continue;
                    picGroupKeys.add(groupKey);
                }
            }
            IParamDataProvider paramDataProvider = this.strategyFactory.getParamDataProvider();
            ParamProvider paramProvider = paramDataProvider.getParamProvider();
            List<String> codes = this.fileMetas.stream().map(IMetaData::getCode).collect(Collectors.toList());
            Set<RegionPO> regions = paramProvider.getRegions(this.tableDimSet.getDataTable().getCode(), codes);
            if (!CollectionUtils.isEmpty(regions)) {
                Optional first = regions.stream().findFirst();
                RegionPO regionPO = (RegionPO)first.get();
                this.attachmentMarkService.batchMarkDeletion(regionPO.getFormSchemeKey(), fileGroupKeys);
                this.attachmentMarkService.batchMarkPictureDeletion(regionPO.getFormSchemeKey(), picGroupKeys);
            }
        }
        catch (Exception e) {
            throw new CrudOperateException(4111, "\u9644\u4ef6\u6807\u8bb0\u5220\u9664\u5931\u8d25", (Throwable)e);
        }
    }

    public boolean isRowByDw() {
        return this.rowByDw;
    }

    @Override
    public void setRowByDw(boolean dwOrder) {
        this.rowByDw = dwOrder;
    }

    protected void logAddRowWithPermission(DimensionValueSet masterKeys) {
        if (logger.isTraceEnabled()) {
            logger.trace("\u6dfb\u52a0\u884c\u6570\u636e\u6709\u6743\u9650{}", (Object)masterKeys);
        }
    }

    protected void logAddRowWithoutPermission(DimensionValueSet masterKeys) {
        if (logger.isTraceEnabled()) {
            logger.trace("\u6dfb\u52a0\u884c\u6570\u636e\u65e0\u6743\u9650{}", (Object)masterKeys);
        }
    }

    protected void logAddRowWithOutOfRange(DimensionValueSet masterKeys) {
        if (logger.isTraceEnabled()) {
            logger.trace("\u6dfb\u52a0\u884c\u6570\u636e\u4e0d\u5728\u5bfc\u5165\u7ef4\u5ea6\u8303\u56f4\u5185{}", (Object)masterKeys);
        }
    }
}

