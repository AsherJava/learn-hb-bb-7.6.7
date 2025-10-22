/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.SaveRowData;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.impl.Const;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.crud.inner.BaseSaveDataBuilder;
import com.jiuqi.nr.datacrud.impl.crud.inner.InputKeyCalc;
import com.jiuqi.nr.datacrud.impl.crud.inner.InputRow;
import com.jiuqi.nr.datacrud.impl.crud.inner.KeyNode;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.ReturnResInstance;
import com.jiuqi.nr.datacrud.impl.param.DataTableDTO;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CheckSaveDataBuilder
extends BaseSaveDataBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CheckSaveDataBuilder.class);
    private List<InputRow> allInputRows = null;
    private InputRow currInputRow = null;
    private final IDataQueryService dataQueryService;
    private final IEntityMetaService entityMetaService;

    public CheckSaveDataBuilder(String regionKey, DimensionCombination dimension, SaveDataBuilderFactory beanGetter) {
        super(regionKey, dimension, beanGetter);
        this.dataQueryService = beanGetter.getDataQueryService();
        this.entityMetaService = beanGetter.getEntityMetaService();
        this.resetRegionKey(this.regionKey);
    }

    @Override
    public SaveDataBuilder resetRegionKey(String regionKey) throws CrudException {
        super.resetRegionKey(regionKey);
        this.currInputRow = null;
        this.allInputRows = new ArrayList<InputRow>();
        return this;
    }

    @Override
    public ReturnRes addRow(DimensionCombination combination, int type) {
        this.currInputRow = new InputRow();
        this.currInputRow.setType(type);
        combination = this.completionDimension(combination);
        this.currInputRow.setCombination(combination);
        this.currInputRow.setRowIndex(this.allInputRows.size());
        if (type != 3) {
            List<Object> rowValues = this.saveData.getLinks().stream().map(link -> Const.UNMODIFIED_VALUE).collect(Collectors.toList());
            this.currInputRow.setLinkValues(rowValues);
        }
        this.allInputRows.add(this.currInputRow);
        return ReturnResInstance.OK_INSTANCE;
    }

    @Override
    public ReturnRes setData(int index, Object data) {
        List<Object> linkValues = this.currInputRow.getLinkValues();
        if (linkValues == null) {
            return ReturnResInstance.ERR_INSTANCE;
        }
        if (index > linkValues.size() || index < 0) {
            return ReturnRes.build(1001, "\u94fe\u63a5\u4e0d\u5b58\u5728\u6216\u5df2\u4e22\u5931");
        }
        String link = this.saveData.getLinks().get(index);
        MetaData metaData = this.regionRelation.getMetaDataByLink(link);
        this.pkUpdateCheck(data, metaData);
        linkValues.set(index, data);
        return ReturnResInstance.OK_INSTANCE;
    }

    @Override
    public SaveReturnRes checkData() {
        boolean checkUnSaveData = false;
        DataTableDTO dataTableDTO = null;
        DataRegionKind regionKind = this.regionRelation.getRegionDefine().getRegionKind();
        SaveReturnRes saveReturnRes = new SaveReturnRes();
        List<SaveResItem> saveResItems = saveReturnRes.getSaveResItems();
        if (regionKind == DataRegionKind.DATA_REGION_SIMPLE) {
            dataTableDTO = this.regionRelation.initMetaData(null, false);
            if (dataTableDTO.getMetaFields().size() > this.saveData.getLinks().size()) {
                checkUnSaveData = true;
            }
        } else {
            this.checkRowKeys(saveResItems);
        }
        HashMap row2Link2Res = new HashMap();
        for (SaveResItem saveResItem : saveResItems) {
            String linkKey = saveResItem.getLinkKey();
            if (!StringUtils.hasLength(linkKey)) continue;
            Map link2Res = row2Link2Res.computeIfAbsent(saveResItem.getRowIndex(), k -> new HashMap());
            link2Res.put(linkKey, saveResItem);
        }
        List<String> links = this.saveData.getLinks();
        for (InputRow row : this.allInputRows) {
            List<Object> linkDataValues = row.getLinkValues();
            IRowData dbRow = null;
            if (checkUnSaveData) {
                QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create(this.regionKey, this.dimension);
                queryInfoBuilder.setDesensitized(false);
                dataTableDTO.getMetaFields().stream().map(MetaData::getLinkKey).forEach(queryInfoBuilder::select);
                IRegionDataSet dbRowSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
                dbRow = dbRowSet.getRowData().stream().findFirst().orElse(null);
                HashSet<String> linksSet = new HashSet<String>(Math.max(16, links.size() * 2), 0.75f);
                linksSet.addAll(links);
                dataTableDTO.getMetaFields().stream().map(MetaData::getLinkKey).filter(((Predicate<String>)linksSet::contains).negate()).forEach(links::add);
            }
            SaveRowData currRow = new SaveRowData();
            currRow.setType(row.getType());
            currRow.setCombination(row.getCombination());
            currRow.setNewKey(row.getNewKey());
            currRow.setSaveData(this.saveData);
            if (row.getType() != 3) {
                currRow.setLinkValues(new AbstractData[this.saveData.getLinks().size()]);
            }
            currRow.setRowIndex(this.saveData.getRowCount());
            this.saveData.getTypeRows(row.getType()).add(currRow);
            if (row.getType() == 3) continue;
            for (TypeParseStrategy value : this.typeParseStrategyEnumMap.values()) {
                value.setRowKey(row.getCombination());
            }
            this.defaultParseStrategy.setRowKey(row.getCombination());
            this.nonTypeParseStrategy.setRowKey(row.getCombination());
            for (int index = 0; index < links.size(); ++index) {
                DataFieldType dataType;
                IDataValue dbValue;
                String linkKey = links.get(index);
                Object data = null;
                if (index < linkDataValues.size()) {
                    data = linkDataValues.get(index);
                } else if (dbRow != null && (dbValue = dbRow.getDataValueByLink(linkKey)) != null && !dbValue.getAsNull()) {
                    data = dbValue.getAbstractData().getAsObject();
                }
                if (Const.UNMODIFIED_VALUE == data) {
                    if (index >= currRow.getLinkValues().length) continue;
                    currRow.getLinkValues()[index] = Const.UNMODIFIED_VALUE;
                    continue;
                }
                DataField dataField = null;
                DataLinkDefine linkDefine = null;
                IFMDMAttribute fmAttribute = null;
                MetaData metaData = this.regionRelation.getMetaDataByLink(linkKey);
                if (dataTableDTO != null) {
                    metaData = dataTableDTO.getMetaField(linkKey);
                }
                if (metaData != null) {
                    dataType = metaData.getDataFieldType();
                } else if (this.floatOrderLinkIndex == index) {
                    dataType = DataFieldType.BIGDECIMAL;
                    dataField = this.regionRelation.getFloatOrderField();
                } else {
                    dataType = null;
                }
                try {
                    ParseReturnRes res;
                    TypeParseStrategy typeParseStrategy;
                    if (dataType != null) {
                        typeParseStrategy = (TypeParseStrategy)this.typeParseStrategyEnumMap.get(dataType);
                        if (typeParseStrategy == null) {
                            typeParseStrategy = this.defaultParseStrategy;
                        }
                    } else {
                        typeParseStrategy = this.nonTypeParseStrategy;
                    }
                    if (metaData != null) {
                        dataField = metaData.getDataField();
                        linkDefine = metaData.getDataLinkDefine();
                        fmAttribute = metaData.getFmAttribute();
                    }
                    if ((res = fmAttribute != null ? typeParseStrategy.checkParse(linkDefine, fmAttribute, data) : typeParseStrategy.checkParse(linkDefine, dataField, data)).isSuccess()) {
                        if (index >= currRow.getLinkValues().length) continue;
                        currRow.getLinkValues()[index] = res.getAbstractData();
                        continue;
                    }
                    Map link2Res = row2Link2Res.getOrDefault(currRow.getRowIndex(), Collections.emptyMap());
                    SaveResItem rowLinkRes = (SaveResItem)link2Res.get(linkKey);
                    if (rowLinkRes == null) {
                        rowLinkRes = new SaveResItem();
                        saveResItems.add(rowLinkRes);
                        rowLinkRes.setDimension(row.getCombination());
                        rowLinkRes.setLinkKey(linkKey);
                        rowLinkRes.setRowIndex(currRow.getRowIndex());
                    }
                    if (res.getMessages() != null) {
                        rowLinkRes.addMessages(res.getMessages());
                        continue;
                    }
                    rowLinkRes.addMessage(res.getMessage());
                    continue;
                }
                catch (Exception e) {
                    logger.warn("\u4fdd\u5b58\u6570\u636e,\u8f6c\u6362\u6570\u636e\u5931\u8d25", e);
                    throw new CrudException(4601);
                }
            }
        }
        if (!saveReturnRes.getSaveResItems().isEmpty()) {
            saveReturnRes.setCode(1900);
        }
        return saveReturnRes;
    }

    private void checkRowKeys(List<SaveResItem> saveResItems) {
        boolean checkDuplicateKey = !this.regionRelation.getDataTable().isRepeatCode();
        InputKeyCalc keyCalc = null;
        if (checkDuplicateKey) {
            keyCalc = new InputKeyCalc();
        }
        for (InputRow inputRow : this.allInputRows) {
            SaveReturnRes rowCheck;
            if (checkDuplicateKey) {
                keyCalc.rowCountCalc(inputRow);
            }
            DimensionCombination checkRowKeys = inputRow.getCombination();
            DimensionValueSet newKey = inputRow.getNewKey();
            if (newKey != null) {
                checkRowKeys = new DimensionCombinationBuilder(newKey).getCombination();
            }
            if ((rowCheck = this.checkDimension(checkRowKeys, inputRow.getType())) == null || rowCheck.isSuccess()) continue;
            List<SaveResItem> rowItems = rowCheck.getSaveResItems();
            if (rowItems != null) {
                for (SaveResItem rowItem : rowItems) {
                    rowItem.setRowIndex(inputRow.getRowIndex());
                    saveResItems.add(rowItem);
                }
            }
            if (!checkDuplicateKey) continue;
            keyCalc.removeKey(newKey != null ? newKey : inputRow.getRowKeys());
        }
        if (!checkDuplicateKey) {
            return;
        }
        ArrayList<DimensionValueSet> checkRowKeys = new ArrayList<DimensionValueSet>();
        for (KeyNode keyNode : keyCalc) {
            int count = keyNode.getCount();
            DimensionValueSet rowKey = keyNode.getKey();
            if (count > 1) {
                SaveResItem saveResItem = this.duplicateMessage(this.regionRelation, rowKey);
                List<Integer> rowIndexes = keyNode.getIndexes();
                if (rowIndexes == null) continue;
                for (Integer rowIndex : rowIndexes) {
                    SaveResItem rowResItem = new SaveResItem();
                    rowResItem.setRowIndex(rowIndex);
                    rowResItem.setDimension(saveResItem.getDimension());
                    rowResItem.addMessages(saveResItem.getMessages());
                    saveResItems.add(rowResItem);
                }
                continue;
            }
            if (count != 1) continue;
            checkRowKeys.add(rowKey);
        }
        boolean bl = keyCalc.isExistNodeType();
        if (!(checkRowKeys.isEmpty() || bl || this.enableDeleteBeforeSave)) {
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
            DimensionValueSet tableRowKeys = DimensionValueSetUtil.mergeDimensionValueSet(checkRowKeys);
            for (int i = 0; i < tableRowKeys.size(); ++i) {
                String name = tableRowKeys.getName(i);
                Object value = tableRowKeys.getValue(i);
                dimensionCombinationBuilder.setValue(name, "", value);
            }
            QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create(this.regionKey, this.dimension);
            queryInfoBuilder.setDesensitized(false);
            for (String link : this.saveData.getLinks()) {
                queryInfoBuilder.select(link);
            }
            RegionGradeInfo regionGradeInfo = new RegionGradeInfo();
            regionGradeInfo.setGrade(false);
            regionGradeInfo.setQuerySummary(false);
            IRegionDataSet checkRowSet = this.dataQueryService.dataLocate(queryInfoBuilder.build(), regionGradeInfo, dimensionCombinationBuilder.getCombination());
            for (IRowData dbRow : checkRowSet.getRowData()) {
                List<Integer> rowIndexes;
                DimensionValueSet dbRowKeys = dbRow.getDimension().toDimensionValueSet();
                SaveResItem saveResItem = this.duplicateMessage(this.regionRelation, dbRowKeys);
                KeyNode keyNode = keyCalc.getKeyNode(dbRowKeys);
                if (keyNode == null || (rowIndexes = keyNode.getIndexes()) == null) continue;
                for (Integer rowIndex : rowIndexes) {
                    SaveResItem rowResItem = new SaveResItem();
                    rowResItem.setRowIndex(rowIndex);
                    rowResItem.setDimension(saveResItem.getDimension());
                    rowResItem.addMessages(saveResItem.getMessages());
                    saveResItems.add(rowResItem);
                }
            }
        }
    }

    private SaveResItem duplicateMessage(RegionRelation relation, DimensionValueSet duplicateKey) {
        List dimFields = relation.getDimFields().stream().filter(k -> k.getDataFieldKind().equals((Object)DataFieldKind.TABLE_FIELD_DIM)).collect(Collectors.toList());
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();
        for (Object dataField : dimFields) {
            Object dimValue;
            String code = dataField.getCode();
            String entityKey = dataField.getRefDataEntityKey();
            if (StringUtils.hasText(entityKey)) {
                code = this.entityMetaService.queryEntity(entityKey).getCode();
            }
            if (!Objects.nonNull(dimValue = duplicateKey.getValue(code))) continue;
            titles.add(dataField.getTitle());
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
        SaveResItem resItem = new SaveResItem();
        resItem.addMessage(message.toString());
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(duplicateKey);
        resItem.setDimension(builder.getCombination());
        return resItem;
    }

    private void pkUpdateCheck(Object data, MetaData metaData) {
        if (metaData == null || metaData.getDataField() == null) {
            return;
        }
        DataField dataField = metaData.getDataField();
        if (dataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) {
            boolean pkUpdate;
            Map<DataField, String> dimensionNameFieldMap = this.getField2DimensionName();
            String dimName = dimensionNameFieldMap.get(dataField);
            DimensionValueSet oldMasterKey = this.currInputRow.getRowKeys();
            Object keyValue = oldMasterKey.getValue(dimName);
            boolean bl = pkUpdate = !Objects.equals(keyValue, data);
            if (pkUpdate) {
                DimensionValueSet newKey = this.currInputRow.getNewKey();
                if (newKey == null) {
                    newKey = new DimensionValueSet(oldMasterKey);
                    this.currInputRow.setNewKey(newKey);
                }
                if (data == null) {
                    data = "";
                }
                newKey.setValue(dimName, data);
            }
        }
    }
}

