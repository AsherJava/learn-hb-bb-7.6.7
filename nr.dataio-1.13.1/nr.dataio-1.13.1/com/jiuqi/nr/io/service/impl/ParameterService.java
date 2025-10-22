/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nr.io.service.IoQualifier;
import com.jiuqi.nr.io.service.MzOrgCodeRepairService;
import com.jiuqi.nr.io.service.impl.DataAuthReadable;
import com.jiuqi.nvwa.definition.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ParameterService {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IoEntityService ioEntityService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired(required=false)
    private IoQualifier ioQualifier;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private NrIoProperties nrIoProperties;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private FilePoolService filepoolservice;
    @Autowired(required=false)
    private AttachmentIOService attachmentIOService;
    @Autowired
    private DataAuthReadable dataAuthReadable;
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;
    @Autowired(required=false)
    private MzOrgCodeRepairService mzOrgCodeRepairService;
    @Autowired
    private ITaskOptionController taskOptionController;

    public MzOrgCodeRepairService getMzOrgCodeRepairService() {
        return this.mzOrgCodeRepairService;
    }

    public DataAuthReadable getDataAuthReadable() {
        return this.dataAuthReadable;
    }

    public AttachmentIOService getAttachmentIOService() {
        return this.attachmentIOService;
    }

    public FilePoolService getFilepoolservice() {
        return this.filepoolservice;
    }

    public FileOperationService getFileOperationService() {
        return this.fileOperationService;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }

    public IDataDefinitionRuntimeController getDataDefinitionRuntimeController() {
        return this.dataDefinitionRuntimeController;
    }

    public IEntityViewRunTimeController getEntityViewRunTimeController() {
        return this.entityViewRunTimeController;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public IoEntityService getIoEntityService() {
        return this.ioEntityService;
    }

    public DataModelService getDataModelService() {
        return this.dataModelService;
    }

    public IRuntimeDataSchemeService getRuntimeDataSchemeService() {
        return this.runtimeDataSchemeService;
    }

    public IoQualifier getIoQualifier() {
        return this.ioQualifier;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public IDataAccessProvider getDataAccessProvider() {
        return this.dataAccessProvider;
    }

    public FileInfoService getFileInfoService() {
        return this.fileInfoService;
    }

    public FileService getFileService() {
        return this.fileService;
    }

    public NrIoProperties getNrIoProperties() {
        return this.nrIoProperties;
    }

    public IDataAccessServiceProvider getDataAccessServiceProvider() {
        return this.iDataAccessServiceProvider;
    }

    public ITaskOptionController getTaskOptionController() {
        return this.taskOptionController;
    }
}

