/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.mapping2.dto.JIOUploadParam
 *  com.jiuqi.nr.mapping2.dto.JIOUploadParam$UpdateWay
 *  com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO
 *  com.jiuqi.nr.mapping2.service.JIOProvider
 *  com.jiuqi.nr.mapping2.web.vo.Result
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 *  com.jiuqi.nr.single.core.service.SingleFileParserService
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.facade.ISingleMapNrController
 *  nr.single.map.data.facade.SingleMapFormSchemeDefine
 */
package nr.single.para.parain.internal.maping2;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.mapping2.dto.JIOUploadParam;
import com.jiuqi.nr.mapping2.dto.NrMappingSchemeDTO;
import com.jiuqi.nr.mapping2.service.JIOProvider;
import com.jiuqi.nr.mapping2.web.vo.Result;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.service.SingleFileParserService;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nr.single.map.data.PathUtil;
import nr.single.map.data.facade.ISingleMapNrController;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping.ITaskFileMapingEnumService;
import nr.single.para.parain.maping.ITaskFileMapingFormService;
import nr.single.para.parain.maping.ITaskFileMapingFormulaService;
import nr.single.para.parain.maping.ITaskFileMapingTaskService;
import nr.single.para.parain.maping2.ITaskFileMapingTansService;
import nr.single.para.parain.maping2.SingleMappingTransOption;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JIOProviderImpl
implements JIOProvider {
    private static final Logger logger = LoggerFactory.getLogger(JIOProviderImpl.class);
    @Autowired
    private ISingleMapNrController mapController;
    @Autowired
    private ITaskFileMapingTaskService taskService;
    @Autowired
    private ITaskFileMapingFormService formService;
    @Autowired
    private ITaskFileMapingEnumService enumService;
    @Autowired
    private ITaskFileMapingFormulaService formulaService;
    @Autowired
    private IParaImportCommonService paraCommonService;
    @Autowired
    private SingleFileParserService singleParserService;
    @Autowired
    private IMappingSchemeService mapingSchemeService;
    @Autowired
    private ITaskFileMapingTansService mappingTransService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Result execute(String fileOssKey, byte[] jioFile, String msKey, JIOUploadParam jioUpload) {
        SingleParaImportOption option = new SingleParaImportOption();
        option.SelectAll();
        option.setHistoryPara(false);
        try {
            long currentTimeMillis;
            MappingScheme mappingScheme = this.mapingSchemeService.getSchemeByKey(msKey);
            NrMappingSchemeDTO nrMapingScheme = new NrMappingSchemeDTO(mappingScheme);
            String taskKey = nrMapingScheme.getTask();
            String formSchemeKey = nrMapingScheme.getFormScheme();
            String filePath = PathUtil.getExportTempFilePath((String)"JioParaImport");
            String saveFileName = OrderGenerator.newOrder() + ".jio";
            this.uploadFile(jioFile, filePath, saveFileName);
            String file = filePath + saveFileName;
            TaskImportContext importContext = new TaskImportContext();
            logger.info("\u5f00\u59cb\u89e3\u6790\u6587\u4ef6:" + file + ",\u65f6\u95f4:" + new Date().toString());
            long startCurrentTimeMillis = currentTimeMillis = System.currentTimeMillis();
            importContext.setImportOption(option);
            JIOParamParser jioParaser = this.singleParserService.getParaParaser(file);
            try {
                List getInOutData = jioParaser.getInOutData();
                ArrayList getInOutData1 = new ArrayList();
                getInOutData1.addAll(getInOutData);
                jioParaser.setInOutData(getInOutData1);
                logger.info("\u89e3\u6790JIO\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                currentTimeMillis = System.currentTimeMillis();
                importContext.setJioParser(jioParaser);
                importContext.setParaInfo(jioParaser.getParaInfo());
                SingleMapFormSchemeDefine mapScheme = this.mapController.CreateSingleMapDefine();
                mapScheme.getTaskInfo().setUploadFileName(saveFileName);
                importContext.setMapScheme(mapScheme);
                importContext.setTaskKey(taskKey);
                importContext.setFormSchemeKey(formSchemeKey);
                this.taskService.UpdateContextCache(importContext);
                if (option.isUploadTask()) {
                    this.taskService.UpdateMapSchemeDefineByTask(importContext);
                    this.paraCommonService.UpdatePeriodEntity(importContext);
                    logger.info("\u751f\u6210\u4efb\u52a1\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                    currentTimeMillis = System.currentTimeMillis();
                }
                if (option.isUploadForm()) {
                    int mapType = 0;
                    if (jioUpload.getUpdateWay() == JIOUploadParam.UpdateWay.ZB_MATCH_TITLE) {
                        mapType = 1;
                    } else if (jioUpload.getUpdateWay() == JIOUploadParam.UpdateWay.GRID) {
                        mapType = 2;
                    }
                    this.formService.UpdateMapSchemeDefineByForms(importContext, mapType);
                    logger.info("\u751f\u6210\u8868\u5355\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                    currentTimeMillis = System.currentTimeMillis();
                }
                if (option.isUploadEnum()) {
                    this.enumService.mapingEnumTableDefines(importContext);
                    logger.info("\u751f\u6210\u679a\u4e3e\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                    currentTimeMillis = System.currentTimeMillis();
                }
                if (option.isUploadFormula()) {
                    this.formulaService.mapingFormulaDefines(importContext, formSchemeKey);
                    logger.info("\u751f\u6210\u516c\u5f0f\u8017\u65f6\uff1a===================" + (System.currentTimeMillis() - currentTimeMillis));
                    currentTimeMillis = System.currentTimeMillis();
                }
                SingleMappingTransOption tranOption = new SingleMappingTransOption();
                if (jioUpload == null) {
                    tranOption.selectAll();
                } else {
                    tranOption.setUpdateZb(jioUpload.isUpdateZb());
                    tranOption.setUpdateFormula(jioUpload.isUpdateFormula());
                    tranOption.setUpdateBaseData(jioUpload.isUpdateBaseData());
                    tranOption.setUpdatePeriod(jioUpload.isUpdatePeriod());
                    tranOption.setUpdateConfig(jioUpload.isUpdateConfig());
                }
                this.mappingTransService.copyAndUploadJioConfigByFileKey(importContext, msKey, fileOssKey, saveFileName, tranOption, false);
                logger.info("\u751f\u6210\u6620\u5c04\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString() + ",\u603b\u8017\u65f6\uff1a" + (System.currentTimeMillis() - startCurrentTimeMillis));
            }
            finally {
                PathUtil.deleteDir((String)jioParaser.getFilePath());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            Result result = new Result(false, null, "JIO\u751f\u6210\u6620\u5c04\u5f02\u5e38\uff1a" + e.getMessage());
            return result;
        }
        return new Result(true, null, "");
    }

    private void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        String filePath1 = SinglePathUtil.normalize((String)filePath);
        File targetFile = new File(filePath1);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String fileName1 = SinglePathUtil.normalize((String)(filePath + fileName));
        try (FileOutputStream out = new FileOutputStream(fileName1);){
            out.write(file);
            out.flush();
        }
    }
}

