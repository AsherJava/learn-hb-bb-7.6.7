/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.data.attachment.service.ExpFieldDataFileService
 *  com.jiuqi.nr.data.common.DataFile
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.fielddatacrud.FieldQueryInfoBuilder
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.fielddatacrud.FieldSort
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataService
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory
 *  com.jiuqi.nr.fielddatacrud.spi.IDataReader
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 */
package com.jiuqi.nr.data.text.service.impl;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.data.attachment.service.ExpFieldDataFileService;
import com.jiuqi.nr.data.common.DataFile;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.text.IFieldFileParam;
import com.jiuqi.nr.data.text.service.ExpFieldDataService;
import com.jiuqi.nr.data.text.service.impl.DataFileImpl;
import com.jiuqi.nr.data.text.service.impl.IDataReaderImpl;
import com.jiuqi.nr.data.text.spi.IFieldDataMonitor;
import com.jiuqi.nr.data.text.spi.IParamDataFileProvider;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.fielddatacrud.FieldQueryInfoBuilder;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.FieldSort;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import java.io.IOException;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpFieldDataServiceImpl
implements ExpFieldDataService {
    private static final Logger logger = LoggerFactory.getLogger(ExpFieldDataServiceImpl.class);
    private IFieldDataServiceFactory fieldDataServiceFactory;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private FieldRelationFactory fieldRelationFactory;
    private ExpFieldDataFileService expFieldDataFileService;
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    private IProviderStore providerStore;
    private IParamDataFileProvider paramDataFileProvider;

    public ExpFieldDataServiceImpl(IFieldDataServiceFactory fieldDataServiceFactory, IRuntimeDataSchemeService runtimeDataSchemeService, FieldRelationFactory fieldRelationFactory, ExpFieldDataFileService expFieldDataFileService, DataServiceLoggerFactory dataServiceLoggerFactory) {
        this.fieldDataServiceFactory = fieldDataServiceFactory;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.fieldRelationFactory = fieldRelationFactory;
        this.expFieldDataFileService = expFieldDataFileService;
        this.dataServiceLoggerFactory = dataServiceLoggerFactory;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public void setParamDataProvider(IParamDataFileProvider paramDataFileProvider) {
        this.paramDataFileProvider = paramDataFileProvider;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public DataFile export(IFieldFileParam param) {
        try (IDataReaderImpl dataReader = new IDataReaderImpl(this.runtimeDataSchemeService, this.fieldRelationFactory, this.expFieldDataFileService, this.dataServiceLoggerFactory, param.getFileName(), param.isExpZip(), param.getFileWriter(), param.getExportCount());){
            ParamProvider paramProvider = this.paramDataFileProvider.getParamProvider();
            dataReader.setParamProvider(paramProvider);
            ParamsMapping paramsMapping = this.paramDataFileProvider.getParamMapping();
            if (null != paramsMapping) {
                dataReader.setParamDataProvider(paramsMapping);
            }
            this.buildFieldDataService(param, dataReader);
            DataFileImpl dataFileImpl = dataReader.getDataFile();
            return dataFileImpl;
        }
        catch (IOException e) {
            logger.error("\u5bfc\u51fa\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public DataFile export(IFieldFileParam param, IFieldDataMonitor zbDataMonitor) {
        try (IDataReaderImpl dataReader = new IDataReaderImpl(this.runtimeDataSchemeService, this.fieldRelationFactory, this.expFieldDataFileService, this.dataServiceLoggerFactory, param.getFileName(), param.isExpZip(), zbDataMonitor, param.getFileWriter(), param.getExportCount());){
            ParamProvider paramProvider = this.paramDataFileProvider.getParamProvider();
            dataReader.setParamProvider(paramProvider);
            ParamsMapping paramsMapping = this.paramDataFileProvider.getParamMapping();
            if (null != paramsMapping) {
                dataReader.setParamDataProvider(paramsMapping);
            }
            this.buildFieldDataService(param, dataReader);
            DataFileImpl dataFileImpl = dataReader.getDataFile();
            return dataFileImpl;
        }
        catch (IOException e) {
            logger.error("\u5bfc\u51fa\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }

    private void buildFieldDataService(IFieldFileParam param, IDataReaderImpl dataReader) {
        ResouceType authMode;
        PageInfo pageInfo;
        Iterator variableItr;
        Iterator fieldSortItr;
        DataField dataField;
        String fieldKey;
        IFieldDataService fieldDataService = null;
        fieldDataService = null == this.providerStore && null == this.paramDataFileProvider ? this.fieldDataServiceFactory.getFieldDataFileService() : (null != this.providerStore && null == this.paramDataFileProvider ? this.fieldDataServiceFactory.getFieldDataFileService(this.providerStore) : (null == this.providerStore ? this.fieldDataServiceFactory.getFieldDataFileService((IParamDataProvider)this.paramDataFileProvider) : this.fieldDataServiceFactory.getFieldDataFileService(this.providerStore, (IParamDataProvider)this.paramDataFileProvider)));
        Iterator fieldKeys = param.selectFieldItr();
        FieldQueryInfoBuilder fieldQueryInfoBuilder = FieldQueryInfoBuilder.create((DimensionCollection)param.getDimensionCollection());
        if (fieldKeys.hasNext()) {
            DataField bizKeyOrderField;
            fieldKey = (String)fieldKeys.next();
            dataField = this.runtimeDataSchemeService.getDataField(fieldKey);
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
            if (dataTable.getDataTableType() == DataTableType.DETAIL || dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                bizKeyOrderField = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), "FLOATORDER");
                fieldQueryInfoBuilder.select(bizKeyOrderField.getKey());
            }
            if (param.expBizKey() && dataTable.isRepeatCode()) {
                bizKeyOrderField = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), "BIZKEYORDER");
                fieldQueryInfoBuilder.select(bizKeyOrderField.getKey());
            }
            if (param.expFile()) {
                fieldQueryInfoBuilder.select(fieldKey);
            } else if (!param.expFile() && !this.isAttachMentDataField(dataField)) {
                fieldQueryInfoBuilder.select(fieldKey);
            }
        }
        while (fieldKeys.hasNext()) {
            boolean isFile;
            fieldKey = (String)fieldKeys.next();
            if (!param.expFile() && (isFile = this.isAttachMentDataField(dataField = this.runtimeDataSchemeService.getDataField(fieldKey)))) continue;
            fieldQueryInfoBuilder.select(fieldKey);
        }
        Iterator rowFilterItr = param.rowFilterItr();
        if (null != rowFilterItr) {
            while (rowFilterItr.hasNext()) {
                fieldQueryInfoBuilder.where((RowFilter)rowFilterItr.next());
            }
        }
        if (null != (fieldSortItr = param.fieldSortItr())) {
            while (fieldSortItr.hasNext()) {
                fieldQueryInfoBuilder.orderBy((FieldSort)fieldSortItr.next());
            }
        }
        if (null != (variableItr = param.variableItr())) {
            while (variableItr.hasNext()) {
                fieldQueryInfoBuilder.addVariable((Variable)variableItr.next());
            }
        }
        if (null != (pageInfo = param.getPageInfo())) {
            fieldQueryInfoBuilder.setPage(pageInfo);
        }
        if (null != (authMode = param.getAuthMode())) {
            fieldQueryInfoBuilder.setAuthType(authMode);
        }
        fieldDataService.queryTableData(fieldQueryInfoBuilder.build(), (IDataReader)dataReader);
    }

    private boolean isAttachMentDataField(DataField dataField) {
        return DataFieldType.FILE == dataField.getDataFieldType() || DataFieldType.PICTURE == dataField.getDataFieldType();
    }
}

