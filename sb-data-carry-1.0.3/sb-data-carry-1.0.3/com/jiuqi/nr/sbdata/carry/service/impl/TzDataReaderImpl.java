/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DateParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DateTimeParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy
 *  com.jiuqi.nr.datacrud.spi.TypeParseStrategy
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.fielddatacrud.FieldRelation
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.fielddatacrud.FieldSaveInfo
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 *  com.jiuqi.nr.fielddatacrud.TableUpdater
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataService
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory
 *  com.jiuqi.nr.fielddatacrud.spi.IDataReader
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 */
package com.jiuqi.nr.sbdata.carry.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DateParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DateTimeParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.DecimalParseStrategy;
import com.jiuqi.nr.datacrud.impl.parse.strategy.StringParseStrategy;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.sbdata.carry.bean.DataTableCarryResult;
import com.jiuqi.nr.sbdata.carry.bean.TzCarryDownDTO;
import com.jiuqi.nr.sbdata.carry.exception.TzCarryDownException;
import com.jiuqi.nr.sbdata.carry.util.TzCarryUtils;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TzDataReaderImpl
implements IDataReader {
    private static final Logger logger = LoggerFactory.getLogger(TzDataReaderImpl.class);
    private List<IMetaData> metaData = new ArrayList<IMetaData>();
    private int periodIndex;
    private final List<DataField> destDataFields;
    private final TzCarryDownDTO param;
    private TableUpdater tableUpdater;
    private boolean needCommit = true;
    private List<Object> errorRowData;
    private final Set<String> noPermissionDw = new HashSet<String>();
    private final IRuntimeDataSchemeService runtimeDataSchemeService;
    private final FieldRelationFactory fieldRelationFactory;
    private final IFieldDataServiceFactory fieldDataServiceFactory;
    private long totalCount = -1L;

    public TzDataReaderImpl(TzCarryDownDTO param, List<DataField> destDataFields, IRuntimeDataSchemeService runtimeDataSchemeService, FieldRelationFactory fieldRelationFactory, IFieldDataServiceFactory fieldDataServiceFactory) {
        this.param = param;
        this.destDataFields = destDataFields;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.fieldRelationFactory = fieldRelationFactory;
        this.fieldDataServiceFactory = fieldDataServiceFactory;
    }

    public void start(List<IMetaData> metas, long totalCount) {
        this.metaData = metas;
        this.totalCount = totalCount;
        for (IMetaData item : this.metaData) {
            if (!item.getCode().equals("DATATIME")) continue;
            this.periodIndex = this.metaData.indexOf(item);
        }
    }

    public void readRow(IRowData rowData) {
        ArrayList<Object> record = new ArrayList<Object>();
        for (int i = 0; i < this.metaData.size(); ++i) {
            if (i == this.periodIndex) {
                record.add(this.param.getDestPeriod());
                continue;
            }
            IDataValue dataValueByField = rowData.getDataValueByField(this.metaData.get(i).getFieldKey());
            record.add(dataValueByField.getAsObject());
        }
        try {
            ReturnRes returnRes = this.tableUpdater.addRow(record);
            if (returnRes.getCode() != 0 && returnRes.getCode() != 1101) {
                this.needCommit = false;
                this.errorRowData = record;
                throw new TzCarryDownException("\u6dfb\u52a0\u6570\u636e\u5931\u8d25\uff01");
            }
        }
        catch (CrudOperateException e) {
            this.needCommit = false;
            this.errorRowData = record;
            throw new TzCarryDownException("\u6dfb\u52a0\u6570\u636e\u5931\u8d25\uff01", e);
        }
    }

    public void finish() {
        if (this.needCommit) {
            try {
                this.tableUpdater.commit();
            }
            catch (CrudOperateException e) {
                throw new TzCarryDownException("\u63d0\u4ea4\u5931\u8d25\uff01", e);
            }
        }
    }

    public void initDestTableUpdater(boolean allowIllegalData) {
        logger.info("\u5f00\u59cb\u83b7\u53d6\u76ee\u6807\u8868updater");
        List destDataFieldKeys = this.destDataFields.stream().map(Basic::getKey).collect(Collectors.toList());
        List destTablePublicDims = this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(this.destDataFields.get(0).getDataTableKey(), new DataFieldKind[]{DataFieldKind.PUBLIC_FIELD_DIM});
        destDataFieldKeys.addAll(destTablePublicDims.stream().map(Basic::getKey).collect(Collectors.toList()));
        FieldRelation fieldRelation = this.fieldRelationFactory.getFieldRelation();
        List destMetaData = fieldRelation.getMetaData(destDataFieldKeys);
        FieldSaveInfo fieldSaveInfo = new FieldSaveInfo();
        fieldSaveInfo.setMode(ImpMode.FULL);
        fieldSaveInfo.setAuthMode(ResouceType.ZB);
        fieldSaveInfo.setFields(destMetaData);
        fieldSaveInfo.setMasterKey(this.param.getDestMasterKey());
        IParamDataProvider paramDataProvider = TzCarryUtils.getParamDataProvider(this.param.getDestTaskKey(), this.param.getDestFormSchemeKey());
        IFieldDataService fieldDataFileService = this.fieldDataServiceFactory.getFieldDataFileService(paramDataProvider);
        try {
            this.tableUpdater = fieldDataFileService.getTableUpdater(fieldSaveInfo);
            this.tableUpdater.setRowByDw(true);
            this.tableUpdater.installParseStrategy();
            if (allowIllegalData) {
                DecimalParseStrategy decimalParseStrategy = new DecimalParseStrategy();
                decimalParseStrategy.setRoundingMode(RoundingMode.HALF_UP);
                this.tableUpdater.registerParseStrategy(DataFieldType.BIGDECIMAL.getValue(), (TypeParseStrategy)decimalParseStrategy);
                StringParseStrategy stringParseStrategy = new StringParseStrategy();
                stringParseStrategy.setOverLengthTruncated(true);
                this.tableUpdater.registerParseStrategy(DataFieldType.STRING.getValue(), (TypeParseStrategy)stringParseStrategy);
                DateParseStrategy dateParseStrategy = new DateParseStrategy();
                dateParseStrategy.setCheckDateRange(false);
                this.tableUpdater.registerParseStrategy(DataFieldType.DATE.getValue(), (TypeParseStrategy)dateParseStrategy);
                DateTimeParseStrategy dateTimeParseStrategy = new DateTimeParseStrategy();
                dateTimeParseStrategy.setCheckDateRange(false);
                this.tableUpdater.registerParseStrategy(DataFieldType.DATE_TIME.getValue(), (TypeParseStrategy)dateTimeParseStrategy);
            }
        }
        catch (CrudException e) {
            if (e.getCode() == 4000) {
                String value;
                DimensionValue dimensionValue = this.param.getSourceDimensionSet().get(fieldRelation.getDwDimName());
                if (dimensionValue != null && dimensionValue.getValue() != null && StringUtils.isNotEmpty((String)(value = dimensionValue.getValue()))) {
                    this.noPermissionDw.addAll(Arrays.asList(value.split(";")));
                }
                throw e;
            }
            throw new TzCarryDownException("\u83b7\u53d6\u76ee\u6807\u8868updater\u5931\u8d25\uff01", e);
        }
        logger.info("\u83b7\u53d6\u76ee\u6807\u8868updater\u5b8c\u6210\u3002");
    }

    public DataTableCarryResult getSaveRes() {
        DataTableCarryResult result = new DataTableCarryResult();
        result.setSuccess(true);
        if (this.tableUpdater != null) {
            TzCarryUtils.convertResult(result, this.tableUpdater.getSaveRes(), this.errorRowData);
        } else {
            result.setNoAuthDw(this.noPermissionDw);
        }
        return result;
    }
}

