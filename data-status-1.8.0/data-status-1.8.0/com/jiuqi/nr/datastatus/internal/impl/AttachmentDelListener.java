/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.input.FileRelQueryParam
 *  com.jiuqi.nr.attachment.listener.IFileListener
 *  com.jiuqi.nr.attachment.listener.param.FileBatchDelEvent
 *  com.jiuqi.nr.attachment.listener.param.FileDelEvent
 *  com.jiuqi.nr.attachment.listener.param.FileUpdateEvent
 *  com.jiuqi.nr.attachment.listener.param.FileUploadEvent
 *  com.jiuqi.nr.attachment.output.FileRelInfo
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.datastatus.internal.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.input.FileRelQueryParam;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.param.FileBatchDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileDelEvent;
import com.jiuqi.nr.attachment.listener.param.FileUpdateEvent;
import com.jiuqi.nr.attachment.listener.param.FileUploadEvent;
import com.jiuqi.nr.attachment.output.FileRelInfo;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.datastatus.internal.impl.FileListenerProviderImpl;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class AttachmentDelListener
implements IFileListener {
    private final FileListenerProviderImpl fileListenerProvider;
    private List<FileRelInfo> fileDelInfos;
    private List<FileRelInfo> fileBatchDelInfos;
    private static final Logger logger = LoggerFactory.getLogger(AttachmentDelListener.class);

    public AttachmentDelListener(FileListenerProviderImpl fileListenerProvider) {
        this.fileListenerProvider = fileListenerProvider;
    }

    public void beforeFileDelete(FileDelEvent event) {
        FileRelQueryParam fileRelQueryParam = new FileRelQueryParam();
        fileRelQueryParam.setDataSchemeKey(event.getDataSchemeKey());
        fileRelQueryParam.setFileKeys(event.getFileKeys());
        fileRelQueryParam.setGroupKey(event.getGroupKey());
        this.fileDelInfos = this.fileListenerProvider.getFileService().queryFileRelInfo(fileRelQueryParam);
    }

    public void afterFileDelete(FileDelEvent event) {
        this.afterDel(event.getDataSchemeKey(), event.getTaskKey(), this.fileDelInfos);
    }

    public void beforeBatchFileDelete(FileBatchDelEvent event) {
        block3: {
            block2: {
                if (CollectionUtils.isEmpty(event.getFieldKeys())) break block2;
                this.fileBatchDelInfos = new ArrayList<FileRelInfo>();
                for (String fieldKey : event.getFieldKeys()) {
                    FileRelQueryParam fileRelQueryParam = new FileRelQueryParam();
                    fileRelQueryParam.setDataSchemeKey(event.getDataSchemeKey());
                    fileRelQueryParam.setDimensionCombination(event.getDimensionCombination());
                    fileRelQueryParam.setFieldKey(fieldKey);
                    List fileRelInfos = this.fileListenerProvider.getFileService().queryFileRelInfo(fileRelQueryParam);
                    if (fileRelInfos == null) continue;
                    this.fileBatchDelInfos.addAll(fileRelInfos);
                }
                break block3;
            }
            if (CollectionUtils.isEmpty(event.getGroupKeys())) break block3;
            this.fileBatchDelInfos = new ArrayList<FileRelInfo>();
            for (String groupKey : event.getGroupKeys()) {
                FileRelQueryParam fileRelQueryParam = new FileRelQueryParam();
                fileRelQueryParam.setDataSchemeKey(event.getDataSchemeKey());
                fileRelQueryParam.setDimensionCombination(event.getDimensionCombination());
                fileRelQueryParam.setGroupKey(groupKey);
                List fileRelInfos = this.fileListenerProvider.getFileService().queryFileRelInfo(fileRelQueryParam);
                if (fileRelInfos == null) continue;
                this.fileBatchDelInfos.addAll(fileRelInfos);
            }
        }
    }

    public void afterBatchFileDelete(FileBatchDelEvent event) {
        this.afterDel(event.getDataSchemeKey(), event.getTaskKey(), this.fileBatchDelInfos);
    }

    private void afterDel(String dataSchemeKey, String taskKey, List<FileRelInfo> fileDelInfos) {
        if (CollectionUtils.isEmpty(fileDelInfos)) {
            return;
        }
        for (FileRelInfo fileDelInfo : fileDelInfos) {
            FileDelInfo fileDelParam = new FileDelInfo();
            fileDelParam.setDataSchemeKey(dataSchemeKey);
            fileDelParam.setTaskKey(taskKey);
            fileDelParam.setDimensionCombination(fileDelInfo.getDimensionCombination());
            fileDelParam.setFieldKey(fileDelInfo.getFieldKey());
            fileDelParam.setGroupKey(fileDelInfo.getGroupKey());
            this.handleFileDel(fileDelParam);
        }
    }

    private void handleFileDel(FileDelInfo fileDelInfo) {
        try {
            DataField dataField = this.fileListenerProvider.getRuntimeDataSchemeService().getDataField(fileDelInfo.getFieldKey());
            DataTable dataTable = this.fileListenerProvider.getRuntimeDataSchemeService().getDataTable(dataField.getDataTableKey());
            if (dataTable.getDataTableType() == DataTableType.TABLE) {
                CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
                commonParamsDTO.setDataSchemeKey(fileDelInfo.getDataSchemeKey());
                commonParamsDTO.setTaskKey(fileDelInfo.getTaskKey());
                List fileInfoByGroup = this.fileListenerProvider.getFilePoolService().getFileInfoByGroup(fileDelInfo.getGroupKey(), commonParamsDTO);
                if (CollectionUtils.isEmpty(fileInfoByGroup)) {
                    this.delFileFieldData(fileDelInfo);
                    this.refreshStatus(fileDelInfo);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void refreshStatus(FileDelInfo fileDelInfo) throws SQLException {
        Collection formKeysByField = this.fileListenerProvider.getRunTimeViewController().getFormKeysByField(fileDelInfo.getFieldKey());
        if (CollectionUtils.isEmpty(formKeysByField)) {
            return;
        }
        HashMap fsFmsMap = new HashMap();
        for (String formKey : formKeysByField) {
            FormDefine formDefine;
            if (!StringUtils.hasText(formKey) || (formDefine = this.fileListenerProvider.getRunTimeViewController().queryFormById(formKey)) == null) continue;
            if (!fsFmsMap.containsKey(formDefine.getFormScheme())) {
                fsFmsMap.put(formDefine.getFormScheme(), new ArrayList());
            }
            ((List)fsFmsMap.get(formDefine.getFormScheme())).add(formKey);
        }
        try (Connection connection = DataSourceUtils.getConnection((DataSource)Objects.requireNonNull(this.fileListenerProvider.getJdbcTemplate().getDataSource()));){
            for (Map.Entry e : fsFmsMap.entrySet()) {
                String formSchemeKey = (String)e.getKey();
                List formKeys = (List)e.getValue();
                if (!this.fileListenerProvider.getDataStatusService().openStatus(this.fileListenerProvider.getRunTimeViewController().getFormScheme(formSchemeKey).getTaskKey())) continue;
                this.fileListenerProvider.getDataStatusService().refreshStatusByFormula(connection, formSchemeKey, fileDelInfo.getDimensionCombination().toDimensionValueSet(), formKeys);
            }
        }
    }

    private void delFileFieldData(FileDelInfo fileDelInfo) throws Exception {
        FieldDefine fieldDefine = this.fileListenerProvider.getDataDefinitionRuntimeController().queryFieldDefine(fileDelInfo.getFieldKey());
        DimensionValueSet dimensionValueSet = fileDelInfo.getDimensionCombination().toDimensionValueSet();
        IDataQuery dataQuery = this.fileListenerProvider.getDataAccessProvider().newDataQuery();
        dataQuery.addColumn(fieldDefine);
        dataQuery.setMasterKeys(dimensionValueSet);
        IDataTable dataTable = dataQuery.executeQuery(new ExecutorContext(this.fileListenerProvider.getDataDefinitionRuntimeController()));
        IDataRow row = dataTable.findRow(dimensionValueSet);
        row.setValue(fieldDefine, null);
        dataTable.commitChanges(true);
    }

    public void afterFileUpload(FileUploadEvent event) {
    }

    public void afterFileUpdate(FileUpdateEvent event) {
    }

    private static class FileDelInfo {
        private String fieldKey;
        private String groupKey;
        private String dataSchemeKey;
        private String taskKey;
        private DimensionCombination dimensionCombination;

        private FileDelInfo() {
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }

        public void setDataSchemeKey(String dataSchemeKey) {
            this.dataSchemeKey = dataSchemeKey;
        }

        public DimensionCombination getDimensionCombination() {
            return this.dimensionCombination;
        }

        public void setDimensionCombination(DimensionCombination dimensionCombination) {
            this.dimensionCombination = dimensionCombination;
        }

        public String getFieldKey() {
            return this.fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public String getGroupKey() {
            return this.groupKey;
        }

        public void setGroupKey(String groupKey) {
            this.groupKey = groupKey;
        }

        public String getTaskKey() {
            return this.taskKey;
        }

        public void setTaskKey(String taskKey) {
            this.taskKey = taskKey;
        }
    }
}

