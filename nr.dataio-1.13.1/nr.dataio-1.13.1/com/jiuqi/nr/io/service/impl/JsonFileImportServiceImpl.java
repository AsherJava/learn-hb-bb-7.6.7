/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.io.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.io.dataset.impl.RegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.Datablocks;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.params.output.ExportJsonData;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.sb.dataset.impl.SBRegionDataSet;
import com.jiuqi.nr.io.service.FileImportService;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nr.io.tz.dataset.AbstractRegionDataSet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="JsonFileImportServiceImpl")
public class JsonFileImportServiceImpl
implements FileImportService {
    private static final Logger log = LoggerFactory.getLogger(JsonFileImportServiceImpl.class);
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IoEntityService ioEntityService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> dealFileData(File file, TableContext tableContext) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        String input = null;
        try {
            input = FileUtils.readFileToString(file, "UTF-8");
        }
        catch (IOException e) {
            log.info("\u8bfb\u53d6json\u6587\u4ef6\u6570\u636e\u51fa\u9519.{}", (Object)e.getMessage(), (Object)e);
            results.put("msg", "error");
            return results;
        }
        ExportJsonData formData = (ExportJsonData)this.objectMapper.readValue(input, ExportJsonData.class);
        String path = file.getPath().replace(file.getName(), "");
        tableContext.setPwd(FilenameUtils.normalize(path));
        List<RegionData> formRegions = this.getFormRegions(tableContext, formData);
        ImportInformations success = null;
        Object error = null;
        ExecutorContext executorContext = this.getExecutorContext(tableContext);
        for (Datablocks item : formData.getDatablocks()) {
            List<String> linkDatasFilesName;
            List<ExportEntity> entitys = formData.getEntitys();
            if (tableContext.isImportEntitys() && null != entitys && !entitys.isEmpty()) {
                this.importEntitys(tableContext, executorContext, entitys);
            }
            int regionTop = item.getRegionTop();
            RegionData regionData = null;
            for (RegionData region : formRegions) {
                if (region.getRegionTop() != regionTop) continue;
                regionData = region;
                break;
            }
            if (null == regionData) continue;
            boolean noPage = false;
            FormDefine formDefine = this.getFormDefine(tableContext, formData);
            AbstractRegionDataSet regionDataSet = null;
            if (null != formDefine && formDefine.getFormType() == FormType.FORM_TYPE_ACCOUNT && regionData.getRegionTop() > 1) {
                noPage = true;
                regionDataSet = new SBRegionDataSet(tableContext, regionData, item.getFields());
            } else {
                regionDataSet = new RegionDataSet(tableContext, regionData, item.getFields());
            }
            List<Object> obj1 = item.getDatas();
            if (null != obj1 && !obj1.isEmpty()) {
                for (int i = 0; i < obj1.size(); ++i) {
                    Object object = obj1.get(i);
                    List javaList = (List)object;
                    regionDataSet.importDatas(javaList);
                }
                regionDataSet.commit();
            }
            if (null == (linkDatasFilesName = item.getLinkDatasFilesName())) continue;
            for (String fileName : linkDatasFilesName) {
                File dataFile = new File(FilenameUtils.normalize(path + fileName));
                try {
                    input = FileUtils.readFileToString(dataFile, "UTF-8");
                }
                catch (IOException e) {
                    log.info("\u8bfb\u53d6json\u6587\u4ef6\u6570\u636e\u51fa\u9519.{}", (Object)e.getMessage(), (Object)e);
                    this.buildErrInfo(results, formData, regionData, e);
                    return results;
                }
                List dataList = (List)this.objectMapper.readValue(input, Object.class);
                for (int i = 0; i < dataList.size(); ++i) {
                    regionDataSet.importDatas((List)dataList.get(i));
                    if (noPage || i == 0 || i % 20000 != 0) continue;
                    try {
                        regionDataSet.commit();
                        continue;
                    }
                    catch (Exception e) {
                        log.info("\u8bfb\u53d6json\u6587\u4ef6\u6570\u636e\u51fa\u9519.{}", (Object)e.getMessage(), (Object)e);
                        this.buildErrInfo(results, formData, regionData, e);
                    }
                }
                try {
                    regionDataSet.commit();
                }
                catch (Exception e) {
                    log.info("\u8bfb\u53d6json\u6587\u4ef6\u6570\u636e\u51fa\u9519.{}", (Object)e.getMessage(), (Object)e);
                    this.buildErrInfo(results, formData, regionData, e);
                }
            }
        }
        try {
            String formKey = formRegions.get(0).getFormKey();
            FormDefine formDefine = this.viewController.queryFormById(formKey);
            ArrayList<ImportInformations> successInfo = new ArrayList<ImportInformations>();
            success = new ImportInformations(formKey, formData.getFormCode(), formDefine.getTitle(), null, "");
            successInfo.add(success);
            results.put("successInfo", successInfo);
        }
        catch (Exception e) {
            log.info("\u6784\u5efa\u5bfc\u5165\u4fe1\u606f\u51fa\u9519{}", (Object)e.getMessage());
        }
        results.put("msg", "success");
        return results;
    }

    private ExecutorContext getExecutorContext(TableContext context) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.viewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        return executorContext;
    }

    private void buildErrInfo(Map<String, Object> results, ExportJsonData formData, RegionData regionData, Exception e) {
        ImportInformations error = new ImportInformations(regionData.getFormKey(), regionData.getTitle(), formData.getFormCode(), e.getMessage(), "");
        ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
        errorInfo.add(error);
        results.put("errorInfo", errorInfo);
        results.put("msg", "error");
    }

    private void importEntitys(TableContext context, ExecutorContext executorContext, List<ExportEntity> entitys) {
        this.ioEntityService.importEntitys(context, executorContext, entitys);
    }

    private List<RegionData> getFormRegions(TableContext tableContext, ExportJsonData formData) {
        FormDefine formDefine = this.getFormDefine(tableContext, formData);
        ArrayList allRegions = null != formDefine ? this.viewController.getAllRegionsInForm(formDefine.getKey()) : new ArrayList();
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        for (DataRegionDefine dataRegionDefine : allRegions) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }

    private FormDefine getFormDefine(TableContext tableContext, ExportJsonData formData) {
        FormDefine formDefine;
        block4: {
            formDefine = null;
            try {
                if (null == tableContext.getFormSchemeKey() && null != tableContext.getTaskKey()) {
                    List schemeByTask = this.viewController.queryFormSchemeByTask(tableContext.getTaskKey());
                    for (FormSchemeDefine fsd : schemeByTask) {
                        formDefine = this.viewController.queryFormByCodeInScheme(fsd.getKey(), formData.getFormCode());
                        if (null == formDefine) continue;
                        tableContext.setFormSchemeKey(fsd.getKey());
                        break block4;
                    }
                    break block4;
                }
                formDefine = this.viewController.queryFormByCodeInScheme(tableContext.getFormSchemeKey(), formData.getFormCode());
            }
            catch (Exception e) {
                log.info("\u901a\u8fc7\u8868\u5355\u4ee3\u7801\u67e5\u627e\u6307\u5b9a\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355\u51fa\u9519 {}", (Object)formData.getFormCode(), (Object)e);
            }
        }
        return formDefine;
    }
}

