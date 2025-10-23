/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.service.IReviseCKDRECIDService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.mapping2.service.FormulaMappingService
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.transferdata.service.impl;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.service.IReviseCKDRECIDService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.migration.attachment.bean.ReturnObject;
import com.jiuqi.nr.migration.transferdata.bean.ImportJQRDTO;
import com.jiuqi.nr.migration.transferdata.bean.TransImportContext;
import com.jiuqi.nr.migration.transferdata.common.JqrDataPackageUtil;
import com.jiuqi.nr.migration.transferdata.common.JqrXmlImportProcessor;
import com.jiuqi.nr.migration.transferdata.dbservice.service.ISaveDataService;
import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2Service;
import com.jiuqi.nr.migration.transferdata.log.XmlDataImportLog;
import com.jiuqi.nr.migration.transferdata.service.IDataTransImportService;
import com.jiuqi.util.StringUtils;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DataTransImportServiceImpl
implements IDataTransImportService {
    private static final Logger logger = LoggerFactory.getLogger(DataTransImportServiceImpl.class);
    @Autowired
    private FormulaMappingService formulaMappingService;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private JQRResourceMapping2Service jqrCustomMapping2Service;
    @Autowired
    private ISaveDataService saveExecutor;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ICheckErrorDescriptionService iCheckErrorDescriptionService;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IReviseCKDRECIDService iReviseCKDRECIDService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ReturnObject upload(MultipartFile file) {
        File cacheFile = null;
        try {
            String originalFilename = file.getOriginalFilename();
            assert (originalFilename != null);
            if (originalFilename.endsWith(".xml") || originalFilename.endsWith(".jqr")) {
                logger.info("{}:{}", (Object)"JQR\u6570\u636e\u5305\u5bfc\u5165", (Object)"upload:oss");
                cacheFile = JqrDataPackageUtil.writeByteToFile(originalFilename, file.getBytes());
                JqrXmlImportProcessor jqrXmlImportProcessor = new JqrXmlImportProcessor(this.jqrCustomMapping2Service, this.saveExecutor, this.formulaRunTimeController, this.formulaMappingService, this.runTimeViewController, this.iCheckErrorDescriptionService, this.entityUtil, this.dimensionCollectionUtil);
                ReturnObject parseResult = jqrXmlImportProcessor.parseAndGetXmlInfo(originalFilename, cacheFile);
                if (parseResult.isSuccess()) {
                    ObjectStorageService objectStorageService = ObjectStorageManager.getInstance().createTemporaryObjectService();
                    String ossKey = UUIDUtils.getKey();
                    objectStorageService.upload(ossKey, Files.newInputStream(cacheFile.toPath(), new OpenOption[0]));
                    JSONObject json = new JSONObject();
                    json.put("ossKey", (Object)ossKey);
                    json.put("fileName", (Object)originalFilename);
                    json.put("log", (Object)parseResult.getData().toString());
                    ReturnObject returnObject = ReturnObject.Success(json.toString());
                    return returnObject;
                }
                ReturnObject returnObject = parseResult;
                return returnObject;
            }
            ReturnObject returnObject = ReturnObject.Error("\u6587\u4ef6\u683c\u5f0f\u9519\u8bef");
            return returnObject;
        }
        catch (Exception e) {
            ReturnObject returnObject = ReturnObject.Error(e.getMessage());
            return returnObject;
        }
        finally {
            if (cacheFile != null && cacheFile.exists() && cacheFile.delete()) {
                logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165:\u5220\u9664\u7f13\u5b58\u6587\u4ef6{}", (Object)cacheFile.getAbsolutePath());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ReturnObject importFile(ImportJQRDTO importVo) {
        XmlDataImportLog importLog = new XmlDataImportLog();
        ObjectStorageService objectStorageService = null;
        String ossKey = importVo.getOssKey();
        try {
            InputStream is;
            logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165");
            if (StringUtils.isEmpty((String)importVo.getTaskId()) || StringUtils.isEmpty((String)importVo.getFormSchemeId())) {
                ReturnObject returnObject = ReturnObject.Error("\u4efb\u52a1\u548c\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a");
                return returnObject;
            }
            String filename = importVo.getFileName();
            if (StringUtils.isEmpty((String)filename)) {
                ReturnObject e = ReturnObject.Error("\u6587\u4ef6\u540d\u4e0d\u80fd\u4e3a\u7a7a");
                return e;
            }
            TransImportContext importContext = new TransImportContext();
            importContext.setFormSchemeKey(importVo.getFormSchemeId());
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(importVo.getTaskId());
            if (taskDefine == null) {
                ReturnObject returnObject = ReturnObject.Error("\u4efb\u52a1\u4e0d\u5b58\u5728");
                return returnObject;
            }
            importContext.setTaskDefine(taskDefine);
            importContext.setMappingSchemeKey(importVo.getMappingSchemeId());
            logger.info("{}:{}", (Object)"JQR\u6570\u636e\u5305\u5bfc\u5165", (Object)"download:oss");
            objectStorageService = ObjectStorageManager.getInstance().createTemporaryObjectService();
            try {
                is = objectStorageService.download(ossKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                ReturnObject returnObject = ReturnObject.Error("\u8bf7\u91cd\u65b0\u4e0a\u4f20\u6587\u4ef6");
                try {
                    if (objectStorageService != null) {
                        if (objectStorageService.deleteObject(ossKey)) {
                            logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165:\u5220\u9664oss\u9644\u4ef6\u6c60\u7f13\u5b58\u6587\u4ef6\u6210\u529f\u3002");
                        } else {
                            logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165:\u5220\u9664oss\u9644\u4ef6\u6c60\u7f13\u5b58\u6587\u4ef6\u5931\u8d25\u3002");
                        }
                    }
                }
                catch (ObjectStorageException e2) {
                    logger.error(e2.getMessage(), e2);
                }
                return returnObject;
            }
            byte[] byteData = JqrDataPackageUtil.getByteData(is);
            importContext.setImportLog(importLog);
            JqrXmlImportProcessor jqrXmlImportProcessor = new JqrXmlImportProcessor(this.jqrCustomMapping2Service, this.saveExecutor, this.formulaRunTimeController, this.formulaMappingService, this.runTimeViewController, this.iCheckErrorDescriptionService, this.entityUtil, this.dimensionCollectionUtil);
            if (filename.endsWith(".xml")) {
                logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165:\u5f00\u59cb\u89e3\u6790\u6587\u4ef6{}", (Object)filename);
                importContext.getImportLog().setUnitCount(String.valueOf(1));
                importContext.setLogUnitInfo(jqrXmlImportProcessor.getUnitInfo(filename));
                jqrXmlImportProcessor.analyseDataXml(byteData, importContext);
            } else {
                logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165:\u5f00\u59cb\u89e3\u538b\u6587\u4ef6{}", (Object)filename);
                jqrXmlImportProcessor.decompressJQR(filename, byteData, importContext);
            }
            this.iReviseCKDRECIDService.revise(this.runTimeViewController.getFormScheme(importVo.getFormSchemeId()));
            LogHelper.info((String)"\u5bfc\u5165JQR\u6570\u636e\u5305", (String)"JQR\u6570\u636e\u5305\u5bfc\u5165\u6210\u529f", (String)importLog.getDetailLog());
            JSONObject log = new JSONObject();
            log.put("summaryLog", (Object)importLog.getSummaryLog());
            log.put("detailLog", (Object)importLog.getDetailLog());
            ReturnObject returnObject = ReturnObject.Success(log.toString());
            return returnObject;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            LogHelper.info((String)"\u5bfc\u5165JQR\u6570\u636e\u5305", (String)"JQR\u6570\u636e\u5305\u5bfc\u5165\u5931\u8d25", (String)e.getMessage());
            ReturnObject returnObject = ReturnObject.Error("\u5bfc\u5165\u8fc7\u7a0b\u53d1\u751f\u5f02\u5e38\uff0c\u7a0b\u5e8f\u6267\u884c\u4e2d\u65ad\uff0c\u8bf7\u67e5\u770b\u65e5\u5fd7\u4e2d\u7684\u5f02\u5e38\u4fe1\u606f\u3002");
            return returnObject;
        }
        finally {
            try {
                if (objectStorageService != null) {
                    if (objectStorageService.deleteObject(ossKey)) {
                        logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165:\u5220\u9664oss\u9644\u4ef6\u6c60\u7f13\u5b58\u6587\u4ef6\u6210\u529f\u3002");
                    } else {
                        logger.info("JQR\u6570\u636e\u5305\u5bfc\u5165:\u5220\u9664oss\u9644\u4ef6\u6c60\u7f13\u5b58\u6587\u4ef6\u5931\u8d25\u3002");
                    }
                }
            }
            catch (ObjectStorageException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

