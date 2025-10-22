/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.attachment.service.AttachmentIOService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.io.datacheck.DefaultTransfer;
import com.jiuqi.nr.io.datacheck.TaskOptionTransfer;
import com.jiuqi.nr.io.datacheck.param.TransferParam;
import com.jiuqi.nr.io.service.IDataTransfer;
import com.jiuqi.nr.io.service.IDataTransferProvider;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultDataTransferProvider
implements IDataTransferProvider {
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private FileService fileService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private FileAreaService fileAreaService;
    @Autowired
    private AttachmentIOService attachmentIOService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IoEntityService ioEntityService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    @Override
    public IDataTransfer getDataTransfer(TransferParam param) {
        String value = this.taskOptionController.getValue(param.getTaskKey(), "IllegalDataImport_2132");
        if (value.equals("0")) {
            return new DefaultTransfer(this, param);
        }
        return new TaskOptionTransfer(this, param);
    }

    public FileService getFileService() {
        return this.fileService;
    }

    public IDataDefinitionRuntimeController getDataDefinitionRuntimeController() {
        return this.dataDefinitionRuntimeController;
    }

    public DataModelService getDataModelService() {
        return this.dataModelService;
    }

    public FileAreaService getFileAreaService() {
        return this.fileAreaService;
    }

    public AttachmentIOService getAttachmentIOService() {
        return this.attachmentIOService;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public IoEntityService getIoEntityService() {
        return this.ioEntityService;
    }

    public INvwaSystemOptionService getNvwaSystemOptionService() {
        return this.nvwaSystemOptionService;
    }
}

