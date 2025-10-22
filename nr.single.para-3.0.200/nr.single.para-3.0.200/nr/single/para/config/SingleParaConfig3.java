/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.config;

import nr.single.para.asyn.SingleAsynController;
import nr.single.para.basedata.IBaseDataDefineService;
import nr.single.para.basedata.IBaseDataVerService;
import nr.single.para.basedata.IOrgDataDefineService;
import nr.single.para.basedata.impl.BaseDataDefineServiceImpl;
import nr.single.para.basedata.impl.BaseDataVerServiceImpl;
import nr.single.para.basedata.impl.OrgDataDefineServiceImpl;
import nr.single.para.parain.controller.ISingleParaImportController;
import nr.single.para.parain.internal.controller.SingleParaImportController;
import nr.single.para.parain.internal.maping.TaskFileMapingEnumService;
import nr.single.para.parain.internal.maping.TaskFileMapingFormService;
import nr.single.para.parain.internal.maping.TaskFileMapingFormulaService;
import nr.single.para.parain.internal.maping.TaskFileMapingService;
import nr.single.para.parain.internal.maping.TaskFileMapingTaskService;
import nr.single.para.parain.internal.service2.period.PeriodDefineImportPeriodEntityService;
import nr.single.para.parain.internal.service3.AnalInnerImportServiceImpl;
import nr.single.para.parain.internal.service3.AnalTaskImportServiceImpl;
import nr.single.para.parain.internal.service3.AttachmentFileImportServiceImpl;
import nr.single.para.parain.internal.service3.DataSchemeImportServiceImpl;
import nr.single.para.parain.internal.service3.EnumBBLXDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.EnumTableDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.FormDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.FormFieldDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.FormGroupDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.FormRegionDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.FormulaDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.ParaImportCommonServiceImpl;
import nr.single.para.parain.internal.service3.PrintDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.QueryModalDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.TaskDefineImportServiceImpl;
import nr.single.para.parain.internal.service3.TaskFileImportServiceImpl;
import nr.single.para.parain.internal.util.EnumLevelCodeUtilImpl;
import nr.single.para.parain.internal.util.ParaImportEntityUtilImplV1;
import nr.single.para.parain.internal.util.ParaImportFileServcieImpl;
import nr.single.para.parain.internal.util.ParaImportLogServcieImpl;
import nr.single.para.parain.internal.util.ParaServeCodeService;
import nr.single.para.parain.internal.util.SingleSchemePeriodUtilImpl;
import nr.single.para.parain.maping.ITaskFileMapingEnumService;
import nr.single.para.parain.maping.ITaskFileMapingFormService;
import nr.single.para.parain.maping.ITaskFileMapingFormulaService;
import nr.single.para.parain.maping.ITaskFileMapingService;
import nr.single.para.parain.maping.ITaskFileMapingTaskService;
import nr.single.para.parain.service.IAnalInnerImportService;
import nr.single.para.parain.service.IAnalTaskImportService;
import nr.single.para.parain.service.IAttachmentFileImportService;
import nr.single.para.parain.service.IDataSchemeImportService;
import nr.single.para.parain.service.IEnumBBLXDefineImportService;
import nr.single.para.parain.service.IEnumTableDefineImportService;
import nr.single.para.parain.service.IFormDefineImportService;
import nr.single.para.parain.service.IFormFieldDefineImportService;
import nr.single.para.parain.service.IFormGroupDefineImportService;
import nr.single.para.parain.service.IFormRegionDefineImportService;
import nr.single.para.parain.service.IFormulaDefineImportService;
import nr.single.para.parain.service.IParaImportCommonService;
import nr.single.para.parain.service.IPeriodDefineImportService;
import nr.single.para.parain.service.IPrintDefineImportService;
import nr.single.para.parain.service.IQueryModalDefineImportService;
import nr.single.para.parain.service.ITaskDefineImportService;
import nr.single.para.parain.service.ITaskFileImportService;
import nr.single.para.parain.util.IEnumLevelCodeUtil;
import nr.single.para.parain.util.IParaImportEntityUtil;
import nr.single.para.parain.util.IParaImportFileServcie;
import nr.single.para.parain.util.IParaImportLogServcie;
import nr.single.para.parain.util.IParaServeCodeService;
import nr.single.para.parain.util.ISingleSchemePeriodUtil;
import nr.single.para.var.JIOSQCache;
import nr.single.para.var.JIOSQDeployListener;
import nr.single.para.web.SingleController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"nr.single.para.parain.internal.maping2", "nr.single.para.paraout.internal.service"})
@Configuration
public class SingleParaConfig3 {
    @Bean
    public SingleController getSingleController() {
        return new SingleController();
    }

    @Bean
    public ISingleParaImportController getSingleParaImportController() {
        return new SingleParaImportController();
    }

    @Bean
    public ITaskDefineImportService getTaskDefineImportService() {
        return new TaskDefineImportServiceImpl();
    }

    @Bean
    public IFormDefineImportService getFormDefineImportService() {
        return new FormDefineImportServiceImpl();
    }

    @Bean
    public IFormulaDefineImportService getFormulaDefineImportService() {
        return new FormulaDefineImportServiceImpl();
    }

    @Bean
    public ITaskFileImportService getTaskFileImportService() {
        return new TaskFileImportServiceImpl();
    }

    @Bean
    public IPrintDefineImportService getPrintDefineImportService() {
        return new PrintDefineImportServiceImpl();
    }

    @Bean
    public JIOSQCache getJIOSQCache() {
        return new JIOSQCache();
    }

    @Bean
    public JIOSQDeployListener getJIOSQDeployListener() {
        return new JIOSQDeployListener();
    }

    @Bean
    public IEnumTableDefineImportService getEnumTableDefineImportService() {
        return new EnumTableDefineImportServiceImpl();
    }

    @Bean
    public IQueryModalDefineImportService getQueryModalDefineImportService() {
        return new QueryModalDefineImportServiceImpl();
    }

    @Bean
    public ITaskFileMapingService getTaskFileMapingService() {
        return new TaskFileMapingService();
    }

    @Bean
    public ITaskFileMapingTaskService getTaskFileMapingTaskService() {
        return new TaskFileMapingTaskService();
    }

    @Bean
    public ITaskFileMapingFormulaService getTaskFileMapingFormulaService() {
        return new TaskFileMapingFormulaService();
    }

    @Bean
    public ITaskFileMapingFormService getTaskFileMapingFormService() {
        return new TaskFileMapingFormService();
    }

    @Bean
    public ITaskFileMapingEnumService getTaskFileMapingEnumService() {
        return new TaskFileMapingEnumService();
    }

    @Bean
    public IParaImportCommonService getParaImportCommonService() {
        return new ParaImportCommonServiceImpl();
    }

    @Bean
    public IAttachmentFileImportService getAttachmentFileImportService() {
        return new AttachmentFileImportServiceImpl();
    }

    @Bean
    public IAnalTaskImportService getAnalTaskImportService() {
        return new AnalTaskImportServiceImpl();
    }

    @Bean
    public IParaServeCodeService getParaServeCodeService() {
        return new ParaServeCodeService();
    }

    @Bean
    public IAnalInnerImportService getAnalInnerImportService() {
        return new AnalInnerImportServiceImpl();
    }

    @Bean
    public SingleAsynController getSingleAsynController() {
        return new SingleAsynController();
    }

    @Bean
    public IParaImportEntityUtil getParaImportEntityUtil() {
        return new ParaImportEntityUtilImplV1();
    }

    @Bean
    public IBaseDataDefineService getBaseDataDefineService() {
        return new BaseDataDefineServiceImpl();
    }

    @Bean
    public IBaseDataVerService getBaseDataVerService() {
        return new BaseDataVerServiceImpl();
    }

    @Bean
    public IOrgDataDefineService getIOrgDataDefineService() {
        return new OrgDataDefineServiceImpl();
    }

    @Bean
    public IDataSchemeImportService getDataSchemeImportService() {
        return new DataSchemeImportServiceImpl();
    }

    @Bean
    public IPeriodDefineImportService getPeriodDefineImportService() {
        return new PeriodDefineImportPeriodEntityService();
    }

    @Bean
    public IEnumLevelCodeUtil getEnumLevelCodeUtil() {
        return new EnumLevelCodeUtilImpl();
    }

    @Bean
    public ISingleSchemePeriodUtil getSingleSchemePeriodUtil() {
        return new SingleSchemePeriodUtilImpl();
    }

    @Bean
    public IEnumBBLXDefineImportService getEnumBBLXDefineImportService() {
        return new EnumBBLXDefineImportServiceImpl();
    }

    @Bean
    public IFormGroupDefineImportService getFormGroupDefineImportService() {
        return new FormGroupDefineImportServiceImpl();
    }

    @Bean
    public IFormRegionDefineImportService getFormRegionDefineImportService() {
        return new FormRegionDefineImportServiceImpl();
    }

    @Bean
    public IFormFieldDefineImportService getFormFieldDefineImportService() {
        return new FormFieldDefineImportServiceImpl();
    }

    @Bean
    public IParaImportLogServcie getParaImportLogServcie() {
        return new ParaImportLogServcieImpl();
    }

    @Bean
    public IParaImportFileServcie getParaImportFileServcie() {
        return new ParaImportFileServcieImpl();
    }
}

