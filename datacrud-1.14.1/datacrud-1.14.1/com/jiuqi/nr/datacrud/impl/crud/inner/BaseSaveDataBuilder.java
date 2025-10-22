/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.i18n.CrudMessageSource;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogWrapper;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogger;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.parse.BaseTypeParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.BooleanParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.ClobParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DateParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DateTimeParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.EnumParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.FileParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.ObjParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.PictureParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datacrud.util.TypeStrategyUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public abstract class BaseSaveDataBuilder
implements SaveDataBuilder {
    protected final String regionKey;
    protected final DimensionCombination dimension;
    protected final RegionRelationFactory regionRelationFactory;
    protected final DataEngineService dataEngineService;
    protected final TypeStrategyUtil typeStrategyUtil;
    protected final DataServiceLogWrapper dataServiceLogWrapper;
    protected final CrudMessageSource messageSource;
    protected final EnumMap<DataFieldType, TypeParseStrategy> typeParseStrategyEnumMap = new EnumMap(DataFieldType.class);
    protected final IEntityTableFactory entityTableFactory;
    protected TypeParseStrategy defaultParseStrategy;
    protected TypeParseStrategy nonTypeParseStrategy;
    protected SaveData saveData = null;
    protected RegionRelation regionRelation;
    protected IDataAssist dataAssist;
    private Map<String, DataField> dimensionNameMap;
    private Map<DataField, String> dimensionNameFieldMap;
    protected int floatOrderLinkIndex = -1;
    protected String formulaSchemeKey;
    protected DataServiceLogger crudLogger;
    protected boolean enableDeleteBeforeSave;
    protected List<RowFilter> rowFilter;

    public BaseSaveDataBuilder(String regionKey, DimensionCombination dimension, SaveDataBuilderFactory beanGetter) {
        this.regionKey = regionKey;
        this.dimension = dimension;
        this.regionRelationFactory = beanGetter.getRegionRelationFactory();
        this.dataEngineService = beanGetter.getDataEngineService();
        this.typeStrategyUtil = beanGetter.getTypeStrategyUtil();
        this.dataServiceLogWrapper = beanGetter.getDataServiceLogWrapper();
        this.messageSource = beanGetter.getMessageSource();
        this.entityTableFactory = beanGetter.getEntityTableFactory();
    }

    @Override
    public SaveDataBuilder resetRegionKey(String regionKey) throws CrudException {
        this.regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        this.regionRelation.getRegionDefine();
        this.regionRelation.getMetaData(null);
        this.saveData = new SaveData();
        this.dimensionNameMap = null;
        this.dimensionNameFieldMap = null;
        this.floatOrderLinkIndex = -1;
        this.typeParseStrategyEnumMap.clear();
        if (this.crudLogger == null) {
            this.crudLogger = this.dataServiceLogWrapper.getCrudLogger(this.regionRelation, this.dimension);
            this.crudLogger.beginSaveData("");
        }
        return this.installParseStrategy();
    }

    public IDataAssist getDataAssist() {
        if (this.dataAssist != null) {
            return this.dataAssist;
        }
        ExecutorContext context = this.dataEngineService.getExecutorContext(this.regionRelation, this.dimension);
        this.dataAssist = this.dataEngineService.getDataAssist(context);
        return this.dataAssist;
    }

    @Override
    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    @Override
    public SaveDataBuilder installParseStrategy() {
        this.typeParseStrategyEnumMap.put(DataFieldType.BOOLEAN, new BooleanParseStrategy().setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.typeParseStrategyEnumMap.put(DataFieldType.DATE, new DateParseStrategy().setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.typeParseStrategyEnumMap.put(DataFieldType.DATE_TIME, new DateTimeParseStrategy().setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.typeParseStrategyEnumMap.put(DataFieldType.BIGDECIMAL, this.typeStrategyUtil.initFloatTypeParseStrategy(this.regionRelation).setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.typeParseStrategyEnumMap.put(DataFieldType.INTEGER, this.typeStrategyUtil.initIntTypeParseStrategy(this.regionRelation).setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.typeParseStrategyEnumMap.put(DataFieldType.STRING, new EnumParseStrategy(this.regionRelation, this.entityTableFactory).setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.typeParseStrategyEnumMap.put(DataFieldType.CLOB, new ClobParseStrategy().setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.typeParseStrategyEnumMap.put(DataFieldType.FILE, new FileParseStrategy().setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.typeParseStrategyEnumMap.put(DataFieldType.PICTURE, new PictureParseStrategy().setCrudLogger(this.crudLogger).setMessageSource(this.messageSource));
        this.nonTypeParseStrategy = new ObjParseStrategy().setCrudLogger(this.crudLogger).setMessageSource(this.messageSource);
        this.defaultParseStrategy = new StringParseStrategy().setCrudLogger(this.crudLogger).setMessageSource(this.messageSource);
        return this;
    }

    @Override
    public SaveDataBuilder unInstallParseStrategy() {
        this.typeParseStrategyEnumMap.clear();
        this.defaultParseStrategy = null;
        return this;
    }

    @Override
    public void setDefaultTypeStrategy(TypeParseStrategy defaultTypeStrategy) {
        this.defaultParseStrategy = defaultTypeStrategy;
    }

    @Override
    public void enableDeleteBeforeSave(boolean enable) {
        this.enableDeleteBeforeSave = enable;
    }

    @Override
    public SaveDataBuilder where(RowFilter filter) {
        if (this.rowFilter == null) {
            this.rowFilter = new ArrayList<RowFilter>();
        }
        this.rowFilter.add(filter);
        return this;
    }

    @Override
    public int addLink(String link) {
        MetaData metaData;
        FormType formType = this.regionRelation.getFormDefine().getFormType();
        boolean isFmDm = formType == FormType.FORM_TYPE_NEWFMDM;
        boolean floatOrderLink = "FLOATORDER".equals(link);
        if (!floatOrderLink && !isFmDm && (metaData = this.regionRelation.getMetaDataByLink(link)) == null) {
            return -1;
        }
        List<String> links = this.saveData.getLinks();
        int indexOf = links.indexOf(link);
        if (indexOf != -1) {
            return indexOf;
        }
        int index = links.size();
        links.add(link);
        if (floatOrderLink) {
            this.floatOrderLinkIndex = index;
        }
        return index;
    }

    protected SaveReturnRes checkDimension(DimensionCombination combination, int type) {
        String message;
        if (this.shouldSkipCompletion(this.regionRelation)) {
            return null;
        }
        List<DataField> dimFields = this.regionRelation.getDimFields();
        if (CollectionUtils.isEmpty(dimFields)) {
            throw new CrudException(4507, "\u533a\u57df\u7684\u4e1a\u52a1\u4e3b\u952e\u6307\u6807\u4e3a\u7a7a\uff0c\u6e05\u68c0\u67e5\u53c2\u6570");
        }
        Map<String, DataField> dimensionName2Field = this.getDimensionName2Field();
        int nullSize = 0;
        int tableDim = 0;
        DataField[] dataFields = null;
        for (FixedDimensionValue fixedDim : combination) {
            String name = fixedDim.getName();
            DataField dataField = dimensionName2Field.get(name);
            if (dataField == null) {
                return SaveReturnRes.build(1202, "\u4e1a\u52a1\u4e3b\u952e\u548c\u53c2\u6570\u4e0d\u5339\u914d");
            }
            Object value = fixedDim.getValue();
            if (dataField.getDataFieldKind() != DataFieldKind.TABLE_FIELD_DIM) continue;
            ++tableDim;
            if (ObjectUtils.isEmpty(value)) {
                if (dataFields == null) {
                    dataFields = new DataField[dimFields.size()];
                }
                dataFields[nullSize++] = dataField;
                continue;
            }
            if (StringUtils.hasText(value.toString())) continue;
            if (dataFields == null) {
                dataFields = new DataField[dimFields.size()];
            }
            dataFields[nullSize++] = dataField;
        }
        if (tableDim == 0) {
            return null;
        }
        DataTable dataTable = this.regionRelation.getDataTable();
        DataTableType dataTableType = dataTable.getDataTableType();
        if (DataTableType.ACCOUNT == dataTableType) {
            if (nullSize != 0 && nullSize == tableDim) {
                message = this.dimNullMsg(dataFields, "data.dim.not.all.null");
                this.crudLogger.dataCheckFail(message);
                return this.getTableDimNullRes(combination, dataFields, message);
            }
        } else if (nullSize != 0) {
            message = this.dimNullMsg(dataFields, "data.dim.not.null");
            this.crudLogger.dataCheckFail(message);
            return this.getTableDimNullRes(combination, dataFields, message);
        }
        return null;
    }

    protected DimensionCombination completionDimension(DimensionCombination combination) {
        if (this.shouldSkipCompletion(this.regionRelation)) {
            return combination;
        }
        Map<String, DataField> dimensionName2Field = this.getDimensionName2Field();
        DimensionCombinationBuilder builder = this.createDimensionCombinationBuilder(combination, dimensionName2Field);
        return builder != null ? builder.getCombination() : combination;
    }

    private boolean shouldSkipCompletion(RegionRelation regionRelation) {
        FormDefine formDefine = regionRelation.getFormDefine();
        DataRegionDefine regionDefine = regionRelation.getRegionDefine();
        return regionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE || formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM;
    }

    private DimensionCombinationBuilder createDimensionCombinationBuilder(DimensionCombination combination, Map<String, DataField> dimensionName2Field) {
        DimensionCombinationBuilder builder = null;
        for (Map.Entry<String, DataField> entry : dimensionName2Field.entrySet()) {
            AbstractData data;
            String dimName = entry.getKey();
            DataField dataField = entry.getValue();
            if (combination.hasValue(dimName) || (data = this.regionRelation.getTableDimDefaultValue(dataField.getKey())) == null) continue;
            String dimValue = data.getAsString();
            if (builder == null) {
                builder = this.initializeBuilder(combination);
            }
            builder.setValue(dimName, "", (Object)dimValue);
        }
        return builder;
    }

    private DimensionCombinationBuilder initializeBuilder(DimensionCombination combination) {
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        for (FixedDimensionValue fixedDimensionValue : combination) {
            builder.setValue(fixedDimensionValue);
        }
        return builder;
    }

    private SaveReturnRes getTableDimNullRes(DimensionCombination combination, DataField[] dataFields, String message) {
        ArrayList<SaveResItem> saveResItems = new ArrayList<SaveResItem>();
        for (DataField dataField : dataFields) {
            MetaData meta;
            if (dataField == null || (meta = this.regionRelation.getMetaDataByFieldKey(dataField.getKey())) == null) continue;
            String linkKey = meta.getLinkKey();
            SaveResItem saveResItem = new SaveResItem();
            saveResItem.addMessage(message);
            saveResItem.setDimension(combination);
            saveResItem.setLinkKey(linkKey);
            saveResItems.add(saveResItem);
        }
        SaveReturnRes returnRes = SaveReturnRes.build(1202, message);
        returnRes.setSaveResItems(saveResItems);
        return returnRes;
    }

    private String dimNullMsg(DataField[] dataFields, String code) {
        StringBuilder msg = new StringBuilder();
        for (DataField dataField : dataFields) {
            if (dataField == null) break;
            msg.append("\u3010").append(dataField.getTitle()).append("\u3011,");
        }
        if (msg.length() > 0) {
            msg.setLength(msg.length() - 1);
        }
        return this.messageSource.getMessage(code, (Object)msg.toString());
    }

    protected Map<String, DataField> getDimensionName2Field() {
        if (this.dimensionNameMap != null) {
            return this.dimensionNameMap;
        }
        List<DataField> dimFields = this.regionRelation.getDimFields();
        if (CollectionUtils.isEmpty(dimFields)) {
            throw new CrudException(4507, "\u533a\u57df\u7684\u4e1a\u52a1\u4e3b\u952e\u6307\u6807\u4e3a\u7a7a\uff0c\u6e05\u68c0\u67e5\u53c2\u6570");
        }
        this.dimensionNameMap = new HashMap<String, DataField>();
        this.dimensionNameFieldMap = new HashMap<DataField, String>();
        IDataAssist assist = this.getDataAssist();
        for (DataField dimField : dimFields) {
            String dimensionName = assist.getDimensionName((FieldDefine)((DataFieldDTO)dimField));
            if (dimensionName == null) {
                dimensionName = dimField.getCode();
            }
            this.dimensionNameMap.put(dimensionName, dimField);
            this.dimensionNameFieldMap.put(dimField, dimensionName);
        }
        return this.dimensionNameMap;
    }

    protected Map<DataField, String> getField2DimensionName() {
        if (this.dimensionNameFieldMap != null) {
            return this.dimensionNameFieldMap;
        }
        this.getDimensionName2Field();
        return this.dimensionNameFieldMap;
    }

    @Override
    public SaveDataBuilder registerParseStrategy(int type, TypeParseStrategy parseStrategy) {
        DataFieldType nodeType = DataFieldType.valueOf((int)type);
        if (parseStrategy == null || nodeType == null) {
            return this;
        }
        if (parseStrategy instanceof BaseTypeParseStrategy) {
            ((BaseTypeParseStrategy)parseStrategy).setCrudLogger(this.crudLogger).setMessageSource(this.messageSource);
        }
        this.typeParseStrategyEnumMap.put(nodeType, parseStrategy);
        return this;
    }

    @Override
    public TypeParseStrategy getTypeParseStrategy(int type) {
        DataFieldType nodeType = DataFieldType.valueOf((int)type);
        if (nodeType == null) {
            return null;
        }
        return this.typeParseStrategyEnumMap.get(nodeType);
    }

    @Override
    public SaveReturnRes checkData() {
        return null;
    }

    @Override
    public ISaveInfo build() {
        return new SaveInfo();
    }

    private class SaveInfo
    implements ISaveInfo {
        private SaveInfo() {
        }

        @Override
        public String getRegionKey() {
            return BaseSaveDataBuilder.this.regionKey;
        }

        @Override
        public DimensionCombination getDimensionCombination() {
            return BaseSaveDataBuilder.this.dimension;
        }

        @Override
        public SaveData getSaveData() {
            if (BaseSaveDataBuilder.this.saveData == null) {
                return new SaveData();
            }
            return BaseSaveDataBuilder.this.saveData;
        }

        @Override
        public RegionRelation getRegionRelation() {
            return BaseSaveDataBuilder.this.regionRelation;
        }

        @Override
        public String getFormulaSchemeKey() {
            return BaseSaveDataBuilder.this.formulaSchemeKey;
        }

        @Override
        public boolean enableDeleteBeforeSave() {
            return BaseSaveDataBuilder.this.enableDeleteBeforeSave;
        }

        @Override
        public Iterator<RowFilter> rowFilterItr() {
            if (BaseSaveDataBuilder.this.rowFilter == null) {
                return null;
            }
            return BaseSaveDataBuilder.this.rowFilter.iterator();
        }
    }
}

