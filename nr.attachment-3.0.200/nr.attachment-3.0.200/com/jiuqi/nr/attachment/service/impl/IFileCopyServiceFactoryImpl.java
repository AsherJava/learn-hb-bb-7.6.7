/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.nr.attachment.factory.IFileCopyServiceFactory;
import com.jiuqi.nr.attachment.listener.IFileListenerProvider;
import com.jiuqi.nr.attachment.provider.IFileBucketNameProvider;
import com.jiuqi.nr.attachment.provider.IFileCopyParamProvider;
import com.jiuqi.nr.attachment.service.AttachmentIOService;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachment.service.FileUploadCheckService;
import com.jiuqi.nr.attachment.service.IFileCopyService;
import com.jiuqi.nr.attachment.service.impl.IFileCopyServiceImpl;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IFileCopyServiceFactoryImpl
implements IFileCopyServiceFactory {
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private FileOperationService fileOperationService;
    @Autowired
    private AttachmentIOService attachmentIOService;
    @Autowired(required=false)
    private IFileBucketNameProvider fileBucketNameProvider;
    @Autowired(required=false)
    private FileUploadCheckService fileUploadCheckService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired(required=false)
    private List<IFileListenerProvider> fileListenerProviders;

    @Override
    public IFileCopyService getFileCopyService(IFileCopyParamProvider fileCopyParamProvider) {
        IFileCopyServiceImpl fileCopyService = new IFileCopyServiceImpl();
        fileCopyService.setFilePoolService(this.filePoolService).setFileOperationService(this.fileOperationService).setAttachmentIOService(this.attachmentIOService).setFileBucketNameProvider(this.fileBucketNameProvider).setFileUploadCheckService(this.fileUploadCheckService).setRunTimeViewController(this.runTimeViewController).setEntityMetaService(this.entityMetaService).setDataModelService(this.dataModelService).setRuntimeDataSchemeService(this.runtimeDataSchemeService).setNvwaDataAccessProvider(this.nvwaDataAccessProvider).setDataEngineAdapter(this.dataEngineAdapter).setFileListenerProviders(this.fileListenerProviders).setFileCopyParamProvider(fileCopyParamProvider);
        return fileCopyService;
    }
}

