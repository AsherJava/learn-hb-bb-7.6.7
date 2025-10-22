/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.attachment.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.attachment.input.DeleteMarkFileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteFileExecutor
extends JobExecutor {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FileOperationService fileOperationService;

    public void execute(JobContext jobContext) throws JobExecutionException {
        if (this.runtimeDataSchemeService == null) {
            this.runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        }
        if (this.fileOperationService == null) {
            this.fileOperationService = (FileOperationService)SpringBeanUtils.getBean(FileOperationService.class);
        }
        List dataSchemes = this.runtimeDataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : dataSchemes) {
            DeleteMarkFileInfo deleteMarkFileInfo = new DeleteMarkFileInfo();
            deleteMarkFileInfo.setDataSchemeKey(dataScheme.getKey());
            this.fileOperationService.deleteMarkFile(deleteMarkFileInfo, null);
        }
    }
}

