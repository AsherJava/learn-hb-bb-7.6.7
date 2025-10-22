/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.single.core.util.SingleSecurityUtils
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.constraints.NotNull
 *  nr.single.map.configurations.bean.AutoAppendCode
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.MappingConfig
 *  nr.single.map.configurations.bean.SingleFileInfo
 *  nr.single.map.configurations.bean.SingleMappingConfig
 *  nr.single.map.configurations.bean.UnitMapping
 *  nr.single.map.configurations.dao.ConfigDao
 *  nr.single.map.configurations.dao.FileInfoDao
 *  nr.single.map.configurations.enumaration.FileKind
 *  nr.single.map.configurations.file.bean.FileAnalysisPojo
 *  nr.single.map.configurations.file.bean.UploadingParam
 *  nr.single.map.configurations.internal.bean.IllegalData
 *  nr.single.map.configurations.service.HandleUpdateConfigService
 *  nr.single.map.configurations.service.MappingConfigService
 *  nr.single.map.configurations.service.ParseMapping
 *  nr.single.map.data.facade.SingleFileFormulaItem
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.configurations.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.SingleFileInfo;
import nr.single.map.configurations.bean.SingleMappingConfig;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.dao.ConfigDao;
import nr.single.map.configurations.dao.FileInfoDao;
import nr.single.map.configurations.enumaration.FileKind;
import nr.single.map.configurations.file.bean.FileAnalysisPojo;
import nr.single.map.configurations.file.bean.UploadingParam;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.configurations.service.HandleUpdateConfigService;
import nr.single.map.configurations.service.MappingConfigService;
import nr.single.map.configurations.service.ParseMapping;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.para.configurations.service.FileAnalysisService;
import nr.single.para.file.SingleParaOSSUtils;
import nr.single.para.parain.maping.ITaskFileMapingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

public class FileAnalysisServiceImpl
implements FileAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(FileAnalysisServiceImpl.class);
    private static final int VERSION_LENGTH = 3;
    private static final String DEFAULT_FILE_NAME = "\u6620\u5c04\u6587\u4ef6\u5bfc\u51fa";
    private static final String FILE_NAME_FORMULA = "\u516c\u5f0f\u6620\u5c04";
    private static final String FILE_NAME_ENTITY = "\u5355\u4f4d\u6620\u5c04";
    @Autowired
    private FileService fileService;
    @Autowired
    private FileInfoDao fileInfoDao;
    @Autowired
    private ConfigDao configDao;
    @Autowired
    @Qualifier(value="zbMappingServiceImpl")
    private ParseMapping parseZb;
    @Autowired
    @Qualifier(value="entityMappingServiceImpl")
    private ParseMapping parseEntity;
    @Autowired
    @Qualifier(value="formulaMappingServiceImpl")
    private ParseMapping parseFormula;
    @Autowired
    private ITaskFileMapingService singleService;
    @Autowired
    private HandleUpdateConfigService handleUpdateConfigService;
    @Autowired
    private MappingConfigService mappingConfigService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    private NedisCache cache;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager("SINGLE_MAPPING_CACHE").getCache("SINGLE_CACHE");
    }

    @Override
    public FileAnalysisPojo getMappingConfig(String taskKey, String mappingKey, String scheme, String fileName, byte[] file) {
        boolean isCreate = true;
        if (mappingKey == null || "".equals(mappingKey)) {
            mappingKey = UUID.randomUUID().toString();
        } else {
            isCreate = false;
        }
        byte[] jiofileData = file;
        try {
            if (file != null && file.length > 10245760) {
                jiofileData = this.singleService.splitParaData(fileName, file);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        FileInfo info = this.insertFile(mappingKey, fileName, jiofileData, FileKind.JIO_FILE);
        SingleMappingConfig config = new SingleMappingConfig();
        try {
            config = this.singleService.MakeSingleToMaping(taskKey, scheme, mappingKey, fileName, jiofileData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (!isCreate) {
            ISingleMappingConfig query = this.configDao.query(config.getMappingConfigKey());
            UnitMapping mapping = query.getMapping();
            config.setMapping(mapping);
            config.setConfig(query.getConfig());
        }
        this.insertConfig((ISingleMappingConfig)config, isCreate, scheme);
        return new FileAnalysisPojo(mappingKey, config.getConfigName(), info);
    }

    @Override
    public FileAnalysisPojo reloadMappingConfig(String taskKey, String mappingKey, String scheme, String fileName, byte[] file) {
        FileInfo info = this.insertFile(mappingKey, fileName, file, FileKind.JIO_FILE);
        SingleMappingConfig config = new SingleMappingConfig();
        try {
            config = this.singleService.MakeSingleToMaping(taskKey, scheme, mappingKey, fileName, file);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        ISingleMappingConfig query = this.configDao.query(config.getMappingConfigKey());
        UnitMapping mapping = config.getMapping();
        mapping.setUnitInfos(query.getMapping().getUnitInfos());
        config.setZbFields(query.getZbFields());
        config.setFormulaInfos(query.getFormulaInfos());
        config.setMapRule(query.getMapRule());
        config.setConfig(query.getConfig());
        config.setConfigName(query.getConfigName());
        this.configDao.update((ISingleMappingConfig)config);
        return new FileAnalysisPojo(mappingKey, config.getConfigName(), info);
    }

    @Override
    public void insertDefaultConfigInImport(String fileName, byte[] file, ISingleMappingConfig config) {
        this.insertFile(config.getMappingConfigKey(), fileName, file, FileKind.JIO_FILE);
        this.insertConfig(config, true, config.getSchemeKey());
    }

    @Override
    public void updateConfig(String fileName, byte[] file, ISingleMappingConfig config) {
        this.insertFile(config.getMappingConfigKey(), fileName, file, FileKind.JIO_FILE);
        this.configDao.update(config);
    }

    private void insertConfig(ISingleMappingConfig config, boolean isCreate, String schemKey) {
        List infos = this.configDao.queryConfigByScheme(schemKey);
        if (isCreate) {
            config.setConfig(MappingConfig.getDefaultConfig());
        }
        config.setConfigName(this.mappingConfigService.buildConfigName(infos, config.getTaskInfo().getSingleTaskTitle(), false));
        if (isCreate) {
            this.configDao.insert(config);
        } else {
            this.configDao.update(config);
        }
    }

    @Override
    public FileAnalysisPojo getEntityFile(String mapping, String fileName, byte[] file) {
        this.parseEntity.initData();
        int count = this.parseEntity.parseFileMapping(file);
        ISingleMappingConfig config = this.configDao.query(mapping);
        UnitMapping importUnit = this.parseEntity.getUnitInfo();
        IllegalData illegalData = new IllegalData();
        UnitMapping unitMapping = this.handleUpdateConfigService.saveEntity(importUnit, config, illegalData);
        IllegalData errorData = this.parseEntity.getErrorData();
        errorData.addErrorItems(illegalData.getErrorItems());
        this.configDao.update(config);
        if (errorData.getTotal() > 0) {
            this.cacheErrorData("ENTITY_", errorData, config, file);
            FileAnalysisPojo fileAnalysisPojo = new FileAnalysisPojo(mapping, fileName, errorData);
            fileAnalysisPojo.setCount(count);
            return fileAnalysisPojo;
        }
        FileInfo fileInfo = this.insertFile(mapping, fileName, file, FileKind.ENTITY_FILE);
        FileAnalysisPojo fileAnalysisPojo = new FileAnalysisPojo(mapping, fileInfo, unitMapping, null, null, null);
        fileAnalysisPojo.setCount(count);
        return fileAnalysisPojo;
    }

    @Override
    public FileAnalysisPojo getZbFile(String mapping, String fileName, byte[] file) {
        this.parseZb.initData();
        ISingleMappingConfig config = this.configDao.query(mapping);
        this.parseZb.setSchemeKey(config.getSchemeKey());
        this.parseZb.parseFileMapping(file);
        List zbInfo = this.parseZb.getZbInfo();
        this.handleUpdateConfigService.saveZb(zbInfo, config);
        this.parseZb.convertFromConfig(config);
        IllegalData errorData = this.parseZb.getErrorData();
        errorData.addErrorItems(errorData.getErrorItems());
        zbInfo = this.parseZb.getZbInfo();
        this.configDao.update(config);
        if (errorData.getTotal() > 0) {
            this.cacheErrorData("ZB_", errorData, config, file);
            return new FileAnalysisPojo(mapping, fileName, errorData);
        }
        FileInfo fileInfo = this.insertFile(mapping, fileName, file, FileKind.ZB_FILE);
        return new FileAnalysisPojo(mapping, fileInfo, null, null, zbInfo, null);
    }

    @Override
    public FileAnalysisPojo getFormulaFile(String mapping, String fileName, byte[] file, String schemeKey) {
        this.parseFormula.initData();
        this.parseFormula.parseFileMapping(file);
        List formulaInfos = this.parseFormula.getFormulaInfo();
        IllegalData errorData = new IllegalData();
        this.buildFormulaInfo(formulaInfos, schemeKey, errorData);
        ISingleMappingConfig config = this.handleUpdateConfigService.saveFormulas(mapping, schemeKey, formulaInfos, errorData);
        this.configDao.update(config);
        if (errorData.getTotal() > 0) {
            this.cacheErrorData("FORMULA_", errorData, config, file);
            return new FileAnalysisPojo(mapping, fileName, errorData);
        }
        FileInfo fileInfo = this.insertFile(mapping, fileName, file, FileKind.FORMULA_FILE);
        return new FileAnalysisPojo(mapping, fileInfo, null, formulaInfos, null, null);
    }

    private void buildFormulaInfo(List<SingleFileFormulaItem> formulaInfos, String schemeKey, IllegalData errorData) {
        Map<String, List<SingleFileFormulaItem>> formItemMap = formulaInfos.stream().filter(e -> !StringUtils.isEmpty((CharSequence)e.getNetFormulaCode())).collect(Collectors.groupingBy(SingleFileFormulaItem::getNetFormCode));
        FormulaSchemeDefine schemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(schemeKey);
        Set<String> formCodeSet = formItemMap.keySet();
        for (String formCode : formCodeSet) {
            FormDefine formDefine;
            List<SingleFileFormulaItem> items = formItemMap.get(formCode);
            try {
                formDefine = this.runTimeViewController.queryFormByCodeInScheme(schemeDefine.getFormSchemeKey(), formCode);
            }
            catch (Exception e2) {
                for (SingleFileFormulaItem item : items) {
                    errorData.addErrorFormula("\u4e0d\u5b58\u5728\u7684\u7f51\u7edc\u62a5\u8868\uff01", IllegalData.getFormluaIdx((String)item.getImportIndex()), item.getNetFormulaCode());
                }
                continue;
            }
            if (formDefine == null) continue;
            List allFormulasInForm = this.formulaRunTimeController.getAllFormulasInForm(schemeKey, formDefine.getKey());
            Map<String, FormulaDefine> codeMap = allFormulasInForm.stream().collect(Collectors.toMap(FormulaDefine::getCode, e -> e));
            for (SingleFileFormulaItem item : items) {
                String netFormulaCode = item.getNetFormulaCode();
                FormulaDefine formulaDefine = codeMap.get(netFormulaCode);
                if (formulaDefine == null) continue;
                item.setNetFormKey(formDefine.getKey());
                item.setNetSchemeKey(schemeKey);
                item.setNetSchemeName(schemeDefine.getTitle());
                item.setSingleSchemeName(schemeDefine.getTitle());
                item.setNetFormulaKey(formulaDefine.getKey());
                item.setNetFormulaExp(formulaDefine.getExpression());
                item.setSingleFormulaExp(formulaDefine.getExpression());
            }
        }
    }

    private void cacheErrorData(String cachePrefix, IllegalData errorData, ISingleMappingConfig config, byte[] file) {
        this.cache.put(cachePrefix + "KEY_" + errorData.getErrorInfo(), (Object)config);
        this.cache.put(cachePrefix + "FILE_" + errorData.getErrorInfo(), (Object)file);
    }

    @Override
    public void exportFormulaMapping(@NotNull String mappingKey, @NotNull String formulaSchemeKey, HttpServletResponse response) {
        ISingleMappingConfig query = this.configDao.query(mappingKey);
        List infos = query.getFormulaInfos();
        this.parseFormula.setSchemeKey(query.getSchemeKey());
        infos = infos.stream().filter(e -> e.getNetSchemeKey() != null && e.getNetSchemeKey().equals(formulaSchemeKey)).collect(Collectors.toList());
        this.parseFormula.setFormulaInfo(infos);
        String export = this.parseFormula.buildTextFile();
        this.exportTxt(response, export, FILE_NAME_FORMULA);
    }

    @Override
    public void exportEntityMapping(String mappingKey, HttpServletResponse response) {
        UnitMapping mapping = this.configDao.query(mappingKey).getMapping();
        this.parseEntity.setUnitInfo(mapping);
        String export = this.parseEntity.buildTextFile();
        this.exportTxt(response, export, FILE_NAME_ENTITY);
    }

    @Override
    public FileInfo uploadCacheData(UploadingParam param) throws Exception {
        Object cacheValue = this.cache.get(param.getCacheType() + "KEY_" + param.getCacheKey()).get();
        if (cacheValue == null) {
            throw new RuntimeException(String.format("\u7f13\u5b58\u4e2d\u914d\u7f6e\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u7ee7\u7eed\u66f4\u65b0\u3002CacheType:%s\uff0cCacheKey\uff1a%s", param.getCacheType(), param.getCacheKey()));
        }
        ISingleMappingConfig configObj = (ISingleMappingConfig)cacheValue;
        this.configDao.update(configObj);
        byte[] fileObj = (byte[])this.cache.get(param.getCacheType() + "FILE_" + param.getCacheKey()).get();
        if (fileObj == null) {
            throw new RuntimeException(String.format("\u7f13\u5b58\u4e2d\u4e0a\u4f20\u4e3a\u7a7a\uff0c\u5df2\u66f4\u65b0\u914d\u7f6e\uff0c\u4f46\u65e0\u6cd5\u4e0b\u8f7d\u6b64\u6587\u4ef6.CacheType:%s\uff0cCacheKey\uff1a%s", param.getCacheType(), param.getCacheKey()));
        }
        return this.insertFile(configObj.getMappingConfigKey(), param.getFileName(), fileObj, FileKind.ZB_FILE);
    }

    @Override
    public AutoAppendCode uploadAppendCodeMapping(String mappingKey, byte[] files) {
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(files));
        BufferedReader br = new BufferedReader(reader);
        AutoAppendCode config = this.readAppendFile(br);
        return config;
    }

    @Override
    public void exportAppendCode(String mappingKey, HttpServletResponse response) {
        ISingleMappingConfig query = this.configDao.query(mappingKey);
        AutoAppendCode autoAppendCode = query.getConfig().getAutoAppendCode();
        StringBuilder sbs = new StringBuilder();
        if (autoAppendCode != null && autoAppendCode.isAutoAppendCode()) {
            Map codeMapping = autoAppendCode.getCodeMapping();
            sbs.append("[").append(autoAppendCode.getAppendCodeZb()).append("]").append("\r\n");
            codeMapping.forEach((key, value) -> sbs.append((String)key).append("=").append((String)value).append("\r\n"));
        }
        this.exportTxt(response, sbs.toString(), query.getConfigName());
    }

    private AutoAppendCode readAppendFile(BufferedReader br) {
        AutoAppendCode appendCode = new AutoAppendCode();
        HashMap<String, String> codeMapping = new HashMap<String, String>();
        ArrayList<String> errorEntity = new ArrayList<String>();
        ArrayList<String> errorCode = new ArrayList<String>();
        ArrayList<String> errorText = new ArrayList<String>();
        HashSet<String> collectorCode = new HashSet<String>();
        String zbCode = null;
        String lineTxt = null;
        try {
            while ((lineTxt = br.readLine()) != null) {
                String trimText = lineTxt.trim();
                if ("".equals(trimText)) continue;
                if (trimText.indexOf("[") != -1 && trimText.indexOf("]") != -1) {
                    zbCode = trimText.substring(trimText.indexOf("[") + 1, trimText.indexOf("]"));
                    appendCode.setAppendCodeZb(zbCode);
                    continue;
                }
                Map<String, String> codeMap = this.buildCodeMapping(trimText);
                if (CollectionUtils.isEmpty(codeMap)) {
                    errorText.add(trimText);
                }
                for (String key : codeMap.keySet()) {
                    String code = (String)codeMapping.get(key);
                    boolean flag = true;
                    if (StringUtils.isNotEmpty((CharSequence)code)) {
                        errorEntity.add(key);
                        flag = false;
                    }
                    if (collectorCode.contains(codeMap.get(key))) {
                        errorCode.add(codeMap.get(key));
                        flag = false;
                    }
                    if (flag) {
                        codeMapping.put(key, codeMap.get(key));
                    }
                    collectorCode.add(codeMap.get(key));
                }
            }
            br.close();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (!(CollectionUtils.isEmpty(errorText) && CollectionUtils.isEmpty(errorEntity) && CollectionUtils.isEmpty(errorCode))) {
            StringBuffer errorMsg = new StringBuffer();
            errorMsg.append("\u5bfc\u5165\u6587\u4ef6\u4e2d\u5b58\u5728\u975e\u6cd5\u6570\u636e:</br>");
            if (!CollectionUtils.isEmpty(errorText)) {
                errorMsg.append("\u6570\u636e\u683c\u5f0f\u9519\u8bef:");
                for (String msg : errorText) {
                    errorMsg.append(msg).append(";");
                }
                errorMsg.append("</br>");
            }
            if (!CollectionUtils.isEmpty(errorEntity)) {
                errorMsg.append("\u91cd\u590d\u7684\u5355\u4f4d\u6807\u8bc6:");
                for (String msg : errorEntity) {
                    errorMsg.append(msg).append(";");
                }
                errorMsg.append("</br>");
            }
            if (!CollectionUtils.isEmpty(errorCode)) {
                errorMsg.append("\u91cd\u590d\u7684\u5355\u4f4d\u52a0\u957f\u7801:");
                for (String msg : errorCode) {
                    errorMsg.append(msg).append(";");
                }
            }
            throw new RuntimeException(errorMsg.toString());
        }
        appendCode.setCodeMapping(codeMapping);
        return appendCode;
    }

    private Map<String, String> buildCodeMapping(String lineTxt) {
        HashMap<String, String> codeMapping = new HashMap<String, String>();
        String[] appendMap = lineTxt.split("=");
        if (appendMap.length != 2) {
            return codeMapping;
        }
        String code = null;
        String append = null;
        for (int i = 0; i < appendMap.length; ++i) {
            if (i == 0) {
                code = appendMap[i];
                continue;
            }
            if (i != 1) continue;
            append = appendMap[i];
        }
        if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(append)) {
            codeMapping.put(code, append);
        }
        return codeMapping;
    }

    protected FileInfo insertFile(String mapping, String fileName, byte[] file, FileKind kind) {
        SingleParaOSSUtils.initBucket("single", "\u7528\u4e8eJIO\u65e7\u6620\u5c04\u65b9\u6848");
        FileAreaService singleFile = this.fileService.area("single");
        FileInfo upload = singleFile.upload(fileName, file);
        SingleFileInfo query = this.fileInfoDao.query(mapping);
        SingleFileInfo fileInfo = new SingleFileInfo();
        if (query != null) {
            fileInfo = query;
        }
        fileInfo.setKey(mapping);
        if (kind.getFile() == FileKind.ENTITY_FILE.getFile()) {
            fileInfo.setEntityKey(upload.getKey());
        } else if (kind.getFile() == FileKind.ZB_FILE.getFile()) {
            fileInfo.setZbKey(upload.getKey());
        } else if (kind.getFile() == FileKind.FORMULA_FILE.getFile()) {
            fileInfo.setFormulaKey(upload.getKey());
        } else if (kind.getFile() == FileKind.JIO_FILE.getFile()) {
            fileInfo.setJioKey(upload.getKey());
        }
        if (query != null) {
            this.fileInfoDao.updata(fileInfo);
        } else {
            this.fileInfoDao.insert(fileInfo);
        }
        return upload;
    }

    public void exportTxt(HttpServletResponse response, String text, String fileName) {
        String fileName1 = SingleSecurityUtils.cleanUrlXSS((String)fileName);
        fileName1 = SingleSecurityUtils.cleanHeaderValue((String)fileName1);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + this.genAttachmentFileName(fileName1, DEFAULT_FILE_NAME) + ".txt");
        try (ServletOutputStream outStr = response.getOutputStream();
             BufferedOutputStream buff = new BufferedOutputStream((OutputStream)outStr);){
            buff.write(text.getBytes("UTF-8"));
            buff.flush();
            buff.close();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String genAttachmentFileName(String cnName, String defaultName) {
        try {
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
        }
        catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }
}

