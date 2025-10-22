/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.attachment.listener.IFileListener
 *  com.jiuqi.nr.attachment.listener.IFileListenerProvider
 *  com.jiuqi.nr.attachment.listener.param.FileListenerContext
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.attachment.service.FileService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.datastatus.internal.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.listener.param.FileListenerContext;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.service.FileService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.impl.AttachmentDelListener;
import com.jiuqi.nr.datastatus.internal.impl.DataStatusServiceImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class FileListenerProviderImpl
implements IFileListenerProvider {
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataStatusService dataStatusService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FileService fileService;

    public IFileListener getFileListener(FileListenerContext fileListenerContext) {
        return new AttachmentDelListener(this);
    }

    public IDataAccessProvider getDataAccessProvider() {
        return this.dataAccessProvider;
    }

    public IDataDefinitionRuntimeController getDataDefinitionRuntimeController() {
        return this.dataDefinitionRuntimeController;
    }

    public DataStatusServiceImpl getDataStatusService() {
        return (DataStatusServiceImpl)this.dataStatusService;
    }

    public FilePoolService getFilePoolService() {
        return this.filePoolService;
    }

    public FileService getFileService() {
        return this.fileService;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public IRuntimeDataSchemeService getRuntimeDataSchemeService() {
        return this.runtimeDataSchemeService;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }
}

