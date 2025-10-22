/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.i18n.CrudMessageSource
 *  com.jiuqi.nr.datacrud.impl.service.DataEngineService
 *  com.jiuqi.nr.datacrud.spi.IEntityTableFactory
 *  com.jiuqi.nr.datacrud.spi.IExecutorContextFactory
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.i18n.CrudMessageSource;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.dto.AccessDTO;
import com.jiuqi.nr.fielddatacrud.impl.updater.BaseTableUpdater;
import com.jiuqi.nr.fielddatacrud.impl.updater.FixTableUpdaterFullMode;
import com.jiuqi.nr.fielddatacrud.impl.updater.FixTableUpdaterFullModeByData;
import com.jiuqi.nr.fielddatacrud.impl.updater.NoPermissionUpdater;
import com.jiuqi.nr.fielddatacrud.impl.updater.TableUpdaterFullMode;
import com.jiuqi.nr.fielddatacrud.impl.updater.TableUpdaterFullModeByData;
import com.jiuqi.nr.fielddatacrud.impl.updater.TzDataUpdaterFullMode;
import com.jiuqi.nr.fielddatacrud.spi.AttachmentMarkService;
import com.jiuqi.nr.fielddatacrud.spi.ISBImportActuatorFactory;
import com.jiuqi.nr.fielddatacrud.spi.TableUpdaterProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class TableUpdaterProviderImpl
implements TableUpdaterProvider {
    @Autowired
    private FieldRelationFactory fieldRelationFactory;
    @Autowired
    protected DataEngineService dataEngineService;
    @Autowired
    protected CrudMessageSource messageSource;
    @Autowired
    protected IEntityTableFactory entityTableFactory;
    @Autowired
    protected IExecutorContextFactory executorContextFactory;
    @Autowired(required=false)
    protected AttachmentMarkService attachmentMarkService;
    @Autowired
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired(required=false)
    protected ISBImportActuatorFactory isbImportActuatorFactory;
    @Autowired
    protected FieldDataProperties fieldDataProperties;
    private final Logger LOGGER = LoggerFactory.getLogger(TableUpdaterProviderImpl.class);

    @Override
    public TableUpdater getTableUpdater(FieldSaveInfo saveInfo, FieldDataStrategyFactory strategyFactory, AccessDTO accessDTO) {
        BaseTableUpdater updater;
        ArrayList<String> fieldKeys = new ArrayList<String>();
        String dataTableKey = saveInfo.getDataTableKey();
        DataTable dataTable = null;
        if (StringUtils.hasLength(dataTableKey)) {
            dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
            List fields = this.runtimeDataSchemeService.getDataFieldByTable(dataTableKey);
            Iterator iterator = fields.iterator();
            while (iterator.hasNext()) {
                DataField field = (DataField)iterator.next();
                fieldKeys.add(field.getKey());
            }
        } else {
            for (IMetaData field : saveInfo.getFields()) {
                fieldKeys.add(field.getFieldKey());
            }
        }
        FieldRelation fieldRelation = this.fieldRelationFactory.getFieldRelation(fieldKeys);
        if (CollectionUtils.isEmpty(accessDTO.getAccessMasterKeys())) {
            NoPermissionUpdater noPermissionUpdater = new NoPermissionUpdater(saveInfo, fieldRelation);
            noPermissionUpdater.setAccessDTO(accessDTO);
            return noPermissionUpdater;
        }
        if (dataTable == null) {
            List<TableDimSet> tableDim = fieldRelation.getTableDim(fieldRelation.getMetaData());
            dataTable = tableDim.get(0).getDataTable();
        }
        boolean fixTable = dataTable.getDataTableType() == DataTableType.TABLE || dataTable.getDataTableType() == DataTableType.MD_INFO;
        switch (saveInfo.getMode()) {
            case FULL: {
                if (fixTable) {
                    updater = new FixTableUpdaterFullMode(saveInfo, fieldRelation);
                    break;
                }
                if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                    updater = new TzDataUpdaterFullMode(saveInfo, fieldRelation);
                    break;
                }
                updater = new TableUpdaterFullMode(saveInfo, fieldRelation);
                break;
            }
            case FULL_BY_DATA: {
                if (fixTable) {
                    updater = new FixTableUpdaterFullModeByData(saveInfo, fieldRelation);
                    break;
                }
                if (dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                    updater = new TableUpdaterFullModeByData(saveInfo, fieldRelation);
                    this.LOGGER.warn("\u8d26\u6237\u8868\u4e0d\u652f\u6301\u7a7a\u8868\u4e0d\u8986\u76d6\u6a21\u5f0f\u5bfc\u5165,\u6309\u7167\u5168\u91cf\u8986\u76d6\u5bfc\u5165");
                    break;
                }
                updater = new TableUpdaterFullModeByData(saveInfo, fieldRelation);
                break;
            }
            case ADD: {
                throw new UnsupportedOperationException("\u6682\u4e0d\u652f\u6301\u8ffd\u52a0\u5bfc\u5165");
            }
            default: {
                throw new UnsupportedOperationException("\u8bf7\u6307\u5b9a\u5bfc\u5165\u6a21\u5f0f");
            }
        }
        updater.setMessageSource(this.messageSource);
        updater.setDataEngineService(this.dataEngineService);
        updater.setEntityTableFactory(this.entityTableFactory);
        updater.setExecutorContextFactory(this.executorContextFactory);
        updater.setAttachmentMarkService(this.attachmentMarkService);
        updater.setSbImportActuatorFactory(this.isbImportActuatorFactory);
        updater.setFieldDataProperties(this.fieldDataProperties);
        updater.setStrategyFactory(strategyFactory);
        updater.setAccessDTO(accessDTO);
        return updater;
    }
}

