/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.config;

import nr.single.data.datacopy.ITaskDataCopyCheckInfoService;
import nr.single.data.datacopy.ITaskDataCopyFMDMService;
import nr.single.data.datacopy.ITaskDataCopyFormsService;
import nr.single.data.datacopy.ITaskDataCopyService;
import nr.single.data.datacopy.internal.TaskDataCopyCheckInfoServiceImpl;
import nr.single.data.datacopy.internal.TaskDataCopyFMDMServiceImpl;
import nr.single.data.datacopy.internal.TaskDataCopyFormsServiceImpl;
import nr.single.data.datacopy.internal.TaskDataCopyServiceImpl;
import nr.single.data.datain.internal.service.TaskFileBatchImportDataService;
import nr.single.data.datain.internal.service.TaskFileImportDataService;
import nr.single.data.datain.internal.service.TaskFileReadDataServiceImpl;
import nr.single.data.datain.service.ITaskFileBatchImportDataService;
import nr.single.data.datain.service.ITaskFileImportDataService;
import nr.single.data.datain.service.ITaskFileReadDataService;
import nr.single.data.dataout.internal.service.FormDataExportService;
import nr.single.data.dataout.internal.service.TaskFileBatchExportDataService;
import nr.single.data.dataout.internal.service.TaskFileBatchExportFMDMServiceImpl;
import nr.single.data.dataout.internal.service.TaskFileExportDataService;
import nr.single.data.dataout.service.IFormDataExportService;
import nr.single.data.dataout.service.ITaskFileBatchExportDataService;
import nr.single.data.dataout.service.ITaskFileBatchExportFMDMService;
import nr.single.data.dataout.service.ITaskFileExportDataService;
import nr.single.data.reg.internal.service.SingleParaAuthServiceImpl;
import nr.single.data.reg.internal.service.SingleSoftAuthServiceImpl;
import nr.single.data.reg.service.SingleParaAuthService;
import nr.single.data.reg.service.SingleSoftAuthService;
import nr.single.data.treecheck.internal.service.EntityTreeCheckServiceImpl;
import nr.single.data.treecheck.service.IEntityTreeCheckService;
import nr.single.data.util.TaskFileDataCommonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"nr.single.data.util.internal.service", "nr.single.data.system"})
@Configuration
public class SingleDataConfig {
    @Bean
    public ITaskFileExportDataService getTaskFileExportDataService() {
        return new TaskFileExportDataService();
    }

    @Bean
    public IFormDataExportService getFormDataExportService() {
        return new FormDataExportService();
    }

    @Bean
    public ITaskFileImportDataService getTaskFileImportDataService() {
        return new TaskFileImportDataService();
    }

    @Bean
    public ITaskFileBatchImportDataService getTaskFileBatchImportDataService() {
        return new TaskFileBatchImportDataService();
    }

    @Bean
    public TaskFileDataCommonService getTaskFileDataCommonService() {
        return new TaskFileDataCommonService();
    }

    @Bean
    public ITaskFileBatchExportDataService getTaskFileBatchExportDataService() {
        return new TaskFileBatchExportDataService();
    }

    @Bean
    public IEntityTreeCheckService getEntityTreeCheckService() {
        return new EntityTreeCheckServiceImpl();
    }

    @Bean
    public ITaskDataCopyService getTaskDataCopyService() {
        return new TaskDataCopyServiceImpl();
    }

    @Bean
    public ITaskDataCopyFMDMService getTaskDataCopyFMDMService() {
        return new TaskDataCopyFMDMServiceImpl();
    }

    @Bean
    public ITaskDataCopyFormsService getITaskDataCopyFormsService() {
        return new TaskDataCopyFormsServiceImpl();
    }

    @Bean
    public ITaskDataCopyCheckInfoService getTaskDataCopyCheckInfoService() {
        return new TaskDataCopyCheckInfoServiceImpl();
    }

    @Bean
    public ITaskFileBatchExportFMDMService getTaskFileBatchExportFMDMService() {
        return new TaskFileBatchExportFMDMServiceImpl();
    }

    @Bean
    public SingleParaAuthService getSingleParaAuthService() {
        return new SingleParaAuthServiceImpl();
    }

    @Bean
    public SingleSoftAuthService getSingleSoftAuthService() {
        return new SingleSoftAuthServiceImpl();
    }

    @Bean
    public ITaskFileReadDataService getTaskFileReadDataService() {
        return new TaskFileReadDataServiceImpl();
    }
}

