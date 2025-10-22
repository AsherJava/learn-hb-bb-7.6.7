/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParseReturnRes
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.spi.TypeParseStrategy
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.fielddatacrud.impl.updater;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.ISBActuator;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.impl.dto.ActuatorConfigDTO;
import com.jiuqi.nr.fielddatacrud.impl.dto.DimField;
import com.jiuqi.nr.fielddatacrud.impl.dto.SaveResDTO;
import com.jiuqi.nr.fielddatacrud.impl.updater.BaseTableUpdater;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TzDataUpdaterFullMode
extends BaseTableUpdater
implements TableUpdater {
    private static final Logger logger = LoggerFactory.getLogger(TzDataUpdaterFullMode.class);
    private ISBActuator isbActuator;
    private final Set<String> failDw = new LinkedHashSet<String>();
    private final Set<String> processDw = new LinkedHashSet<String>();
    private final Map<String, ReturnRes> failRes = new HashMap<String, ReturnRes>();
    protected int importCount = 0;

    private void getSbImportActuator() throws CrudOperateException {
        ActuatorConfigDTO config = new ActuatorConfigDTO();
        String dataTableKey = this.saveInfo.getFields().get(0).getDataField().getDataTableKey();
        config.setDestTable(dataTableKey);
        Object period = this.masterKeys.getValue("DATATIME");
        if (period instanceof String) {
            if (StringUtil.isEmpty((String)String.valueOf(period))) {
                throw new CrudOperateException("\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01");
            }
        } else {
            throw new CrudOperateException("\u4e0d\u652f\u6301\u591a\u65f6\u671f\uff01");
        }
        config.setDestPeriod(String.valueOf(period));
        Map<String, String> field2DimMap = this.dimFields.stream().collect(Collectors.toMap(DimField::getCode, DimField::getDimName));
        config.setField2DimMap(field2DimMap);
        if (this.isRowByDw()) {
            config.setBatchByUnit(true);
            config.setRowByDw(true);
        } else {
            config.setBatchByUnit(true);
        }
        ArrayList<DataField> dataFields = new ArrayList<DataField>();
        for (IMetaData field : this.saveInfo.getFields()) {
            if ("BIZKEYORDER".equals(field.getFieldKey())) {
                config.setBatchByUnit(true);
            }
            dataFields.add(field.getDataField());
        }
        this.isbActuator = this.sbImportActuatorFactory.getActuator(config);
        this.isbActuator.setDataFields(dataFields);
        this.isbActuator.prepare();
        Object dwCodes = this.masterKeys.getValue(this.tableDimSet.getDwDimName());
        if (dwCodes instanceof Collection) {
            this.isbActuator.setMdCodeScope((Collection)dwCodes);
        } else {
            ArrayList<String> dwCode = new ArrayList<String>();
            dwCode.add(String.valueOf(dwCodes));
            this.isbActuator.setMdCodeScope(dwCode);
        }
    }

    public TzDataUpdaterFullMode(FieldSaveInfo saveInfo, FieldRelation fieldRelation) {
        super(saveInfo, fieldRelation);
    }

    @Override
    public ReturnRes addRow(List<Object> values) throws CrudOperateException {
        return this.addRow(values.toArray());
    }

    @Override
    public ReturnRes addRow(Object[] values) throws CrudOperateException {
        if (this.isbActuator == null) {
            this.initUpdater();
        }
        DimensionValueSet masterKeys = new DimensionValueSet();
        for (DimField dimField : this.dimFields) {
            int index = dimField.getIndex();
            if (values.length <= index) {
                throw new CrudOperateException(1202, "\u6570\u636e\u884c\u4e2d\u7f3a\u5c11\u7ef4\u5ea6\u6570\u636e");
            }
            if (index < 0 || dimField.getType() != DimField.P_DIM) continue;
            masterKeys.setValue(dimField.getDimName(), values[index]);
        }
        String dw = masterKeys.getValue(this.tableDimSet.getDwDimName()).toString();
        if (this.failDw.contains(dw)) {
            return this.failRes.get(dw);
        }
        if (this.accessMasterKeys.contains(masterKeys)) {
            this.logAddRowWithPermission(masterKeys);
            ArrayList<Object> valuesList = new ArrayList<Object>();
            block5: for (int i = 0; i < this.saveInfo.getFields().size(); ++i) {
                ParseReturnRes res;
                valuesList.add(null);
                IMetaData meta = this.saveInfo.getFields().get(i);
                for (DimField dimField : this.dimFields) {
                    if (dimField.getType() != DimField.P_DIM || !dimField.getCode().equals(meta.getCode())) continue;
                    valuesList.set(i, values[i]);
                    continue block5;
                }
                DataFieldType dataType = meta != null ? meta.getDataFieldType() : null;
                try {
                    TypeParseStrategy typeParseStrategy;
                    if (dataType != null) {
                        typeParseStrategy = (TypeParseStrategy)this.typeParseStrategyEnumMap.get(dataType);
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
                    if ((res = typeParseStrategy.checkParse(null, dataField, values[i])).isSuccess()) {
                        AbstractData abstractData = res.getAbstractData();
                        if (abstractData != null && !abstractData.getAsNull()) {
                            valuesList.set(i, abstractData.getAsObject());
                        }
                        continue;
                    }
                    this.failDw.add(dw);
                    this.failRes.put(dw, (ReturnRes)res);
                    return res;
                }
                catch (Exception e) {
                    this.failDw.add(dw);
                    res = ReturnRes.build((int)1900, (String)("\u8f6c\u6362\u6570\u636e\u5931\u8d25\uff01\u6570\u636e\u503c\uff1a" + values[i]));
                    this.failRes.put(dw, (ReturnRes)res);
                    logger.warn("\u4fdd\u5b58\u6570\u636e,\u8f6c\u6362\u6570\u636e\u5931\u8d25", e);
                    throw new CrudException(4601);
                }
            }
            try {
                this.isbActuator.put(valuesList);
                ++this.importCount;
            }
            catch (Exception e) {
                this.failDw.add(dw);
                ReturnRes res = ReturnRes.build((int)1900, (String)"\u53f0\u8d26\u8868\u6dfb\u52a0\u884c\u6570\u636e\u5931\u8d25\uff01");
                this.failRes.put(dw, res);
                return res;
            }
            this.processDw.add(dw);
            return ReturnRes.ok(null);
        }
        Set<DimensionValueSet> noAccessMasterKeys = this.accessDTO.getNoAccessMasterKeys();
        if (noAccessMasterKeys.contains(masterKeys)) {
            this.logAddRowWithoutPermission(masterKeys);
            this.noPermissionDw.add(dw);
            return ReturnRes.build((int)1101);
        }
        this.logAddRowWithOutOfRange(masterKeys);
        return ReturnRes.build((int)1103);
    }

    private void initUpdater() throws CrudOperateException {
        this.getSbImportActuator();
    }

    @Override
    public void commit() throws CrudOperateException {
        try {
            if (this.isbActuator == null) {
                this.initUpdater();
            }
            this.isbActuator.commitData();
        }
        catch (Exception e) {
            this.failDw.addAll(this.processDw);
            for (String pdw : this.processDw) {
                this.failRes.put(pdw, ReturnRes.build((int)1900, (String)e.getMessage()));
            }
            throw new CrudOperateException(1900, e.getMessage(), (Throwable)e);
        }
        finally {
            if (this.isbActuator != null) {
                this.isbActuator.close();
            }
        }
    }

    @Override
    public SaveRes getSaveRes() {
        SaveResDTO saveRes = new SaveResDTO();
        saveRes.setSaveDw(this.processDw);
        saveRes.setFailDw(this.failDw);
        saveRes.setFailMessage(this.failRes);
        saveRes.setNoPermissionDw(this.noPermissionDw);
        saveRes.setCount(this.importCount);
        return saveRes;
    }
}

