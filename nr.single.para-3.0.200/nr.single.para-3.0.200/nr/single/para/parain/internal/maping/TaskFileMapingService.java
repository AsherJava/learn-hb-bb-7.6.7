/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.file.SingleFileConfigInfo
 *  com.jiuqi.nr.single.core.internal.file.SingleFileImpl
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 *  com.jiuqi.nr.single.core.service.SingleFileHelper
 *  com.jiuqi.nr.single.core.service.SingleFileParserService
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.RuleKind
 *  nr.single.map.configurations.bean.RuleMap
 *  nr.single.map.configurations.bean.SingleMappingConfig
 *  nr.single.map.configurations.bean.TableConfig
 *  nr.single.map.configurations.bean.UnitCustomMapping
 *  nr.single.map.configurations.bean.UnitMapping
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.facade.ISingleMapNrController
 *  nr.single.map.data.facade.SingleFileEnumInfo
 *  nr.single.map.data.facade.SingleFileEnumItem
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileFormulaInfo
 *  nr.single.map.data.facade.SingleFileFormulaItem
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.facade.SingleFileTaskInfo
 *  nr.single.map.data.facade.SingleMapFormSchemeDefine
 *  nr.single.map.data.internal.SingleFileEnumInfoImpl
 *  nr.single.map.data.internal.SingleFileFieldInfoImpl
 *  nr.single.map.data.internal.SingleFileFmdmInfoImpl
 *  nr.single.map.data.internal.SingleFileFormulaItemImpl
 *  nr.single.map.data.internal.SingleFileTableInfoImpl
 *  nr.single.map.data.internal.SingleFileTaskInfoImpl
 *  nr.single.map.data.service.SingleJioFileService
 */
package nr.single.para.parain.internal.maping;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.file.SingleFileConfigInfo;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.service.SingleFileHelper;
import com.jiuqi.nr.single.core.service.SingleFileParserService;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.SingleMappingConfig;
import nr.single.map.configurations.bean.TableConfig;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.data.PathUtil;
import nr.single.map.data.facade.ISingleMapNrController;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileFormulaInfo;
import nr.single.map.data.facade.SingleFileFormulaItem;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.map.data.internal.SingleFileEnumInfoImpl;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import nr.single.map.data.internal.SingleFileFmdmInfoImpl;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;
import nr.single.map.data.internal.SingleFileTableInfoImpl;
import nr.single.map.data.internal.SingleFileTaskInfoImpl;
import nr.single.map.data.service.SingleJioFileService;
import nr.single.para.configurations.service.FileAnalysisService;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping.ITaskFileMapingEnumService;
import nr.single.para.parain.maping.ITaskFileMapingFormService;
import nr.single.para.parain.maping.ITaskFileMapingFormulaService;
import nr.single.para.parain.maping.ITaskFileMapingService;
import nr.single.para.parain.maping.ITaskFileMapingTaskService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileMapingService
implements ITaskFileMapingService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileMapingService.class);
    private SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
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
    private FileAnalysisService configAnalService;
    @Autowired
    private SingleFileParserService singleParserService;
    @Autowired
    private SingleJioFileService jioService;
    @Autowired
    private SingleFileHelper singleHelper;

    @Override
    public SingleMappingConfig MakeSingleToMaping(String taskKey, String formSchemeKey, String mappingKey, String fileName, byte[] fileData) throws Exception {
        SingleParaImportOption option = new SingleParaImportOption();
        option.SelectAll();
        option.setHistoryPara(false);
        return this.MakeSingleToMapingByOption(taskKey, formSchemeKey, mappingKey, fileName, fileData, option);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private SingleMappingConfig MakeSingleToMapingByOption(String taskKey, String formSchemeKey, String mappingKey, String fileName, byte[] fileData, SingleParaImportOption option) throws Exception {
        long currentTimeMillis;
        SingleMappingConfig config = null;
        String filePath = PathUtil.getExportTempFilePath((String)"JioParaImport");
        String saveFileName = OrderGenerator.newOrder() + ".jio";
        this.uploadFile(fileData, filePath, saveFileName);
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
            mapScheme.getTaskInfo().setUploadFileName(fileName);
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
                this.formService.UpdateMapSchemeDefineByForms(importContext);
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
            config = this.copyMapConfig(importContext);
            config.setMappingConfigKey(mappingKey);
            logger.info("\u751f\u6210\u6620\u5c04\u5b8c\u6210,\u65f6\u95f4:" + new Date().toString() + ",\u603b\u8017\u65f6\uff1a" + (System.currentTimeMillis() - startCurrentTimeMillis));
        }
        finally {
            PathUtil.deleteDir((String)jioParaser.getFilePath());
        }
        return config;
    }

    @Override
    public SingleMappingConfig copyMapConfig(TaskImportContext importContext) {
        SingleMappingConfig config = new SingleMappingConfig();
        SingleMapFormSchemeDefine mapScheme = importContext.getMapScheme();
        config.setSchemeKey(importContext.getFormSchemeKey());
        config.setTaskKey(importContext.getTaskKey());
        TableConfig tableConfig = new TableConfig();
        config.setTableConfig(tableConfig);
        SingleFileTaskInfoImpl taskInfo = new SingleFileTaskInfoImpl();
        taskInfo.copyFrom(mapScheme.getTaskInfo());
        config.setTaskInfo((SingleFileTaskInfo)taskInfo);
        ArrayList items = new ArrayList();
        for (SingleFileFormulaInfo info : mapScheme.getFormulaInfos()) {
            info.getTableFormulaInfos().forEach((key, value) -> value.getFormulaItems().forEach((k, v) -> {
                SingleFileFormulaItemImpl item = new SingleFileFormulaItemImpl();
                item.copyFrom(v);
                item.setNetSchemeKey(info.getNetSchemeKey());
                item.setNetSchemeName(info.getNetSchemeName());
                item.setSingleSchemeName(info.getSingleSchemeName());
                item.setSingleTableCode(value.getSingleTableCode());
                item.setNetFormCode(value.getNetFormCode());
                item.setNetFormKey(value.getNetFormKey());
                items.add(item);
            }));
        }
        config.setFormulaInfos(items);
        ArrayList<Object> tableInfos = new ArrayList<Object>();
        ArrayList<Object> fieldInfos = new ArrayList<Object>();
        for (int i = -1; i < mapScheme.getTableInfos().size(); ++i) {
            SingleFileFmdmInfo tableMap = null;
            if (i == -1) {
                SingleFileFmdmInfo fmdminfo = mapScheme.getFmdmInfo();
                SingleFileFmdmInfoImpl fmdmInfoNew = new SingleFileFmdmInfoImpl();
                fmdmInfoNew.copyFrom((SingleFileTableInfo)fmdminfo);
                tableInfos.add(fmdmInfoNew);
                tableMap = fmdminfo;
            } else {
                SingleFileTableInfo tableInfo = (SingleFileTableInfo)mapScheme.getTableInfos().get(i);
                SingleFileTableInfoImpl tableInfoNew = new SingleFileTableInfoImpl();
                tableInfoNew.copyFrom(tableInfo);
                tableInfos.add(tableInfoNew);
                tableMap = tableInfoNew;
            }
            SingleFileFmdmInfo tInfo = tableMap;
            for (SingleFileFieldInfo field : tInfo.getRegion().getFields()) {
                SingleFileFieldInfoImpl fieldNew = new SingleFileFieldInfoImpl();
                fieldNew.copyFrom(field);
                fieldNew.setNetFormCode(tInfo.getNetFormCode());
                fieldInfos.add(fieldNew);
            }
            for (SingleFileRegionInfo subRegion : tInfo.getRegion().getSubRegions()) {
                for (SingleFileFieldInfo field : subRegion.getFields()) {
                    SingleFileFieldInfoImpl fieldNew = new SingleFileFieldInfoImpl();
                    fieldNew.copyFrom(field);
                    fieldNew.setNetFormCode(tInfo.getNetFormCode());
                    fieldInfos.add(fieldNew);
                }
            }
        }
        config.setTableInfos(tableInfos);
        config.setZbFields(fieldInfos);
        LinkedHashMap<String, SingleFileEnumInfoImpl> enumInfos = new LinkedHashMap<String, SingleFileEnumInfoImpl>();
        for (SingleFileEnumInfo info : mapScheme.getEnumInfos()) {
            SingleFileEnumInfoImpl infoNew = new SingleFileEnumInfoImpl();
            infoNew.copyFrom(info);
            enumInfos.put(infoNew.getEnumCode(), infoNew);
        }
        config.setEnumInfos(enumInfos);
        UnitMapping mapping = new UnitMapping();
        ArrayList unitInfos = new ArrayList();
        mapping.setUnitInfos(unitInfos);
        SingleFileEnumInfoImpl taskEnumNew = new SingleFileEnumInfoImpl();
        taskEnumNew.copyFrom(mapScheme.getTaskPeriodEnum());
        taskEnumNew.setEnumCode("sys_taskperiod");
        enumInfos.put(taskEnumNew.getEnumCode(), taskEnumNew);
        HashMap<String, String> periodMapping = new HashMap<String, String>();
        for (SingleFileEnumItem item : mapScheme.getTaskPeriodEnum().getEnumItems()) {
            periodMapping.put(item.getItemCode(), item.getNetItemCode());
        }
        mapping.setPeriodMapping(periodMapping);
        ArrayList<RuleMap> mapRule = new ArrayList<RuleMap>();
        for (String fieldCode : importContext.getMapScheme().getFmdmInfo().getZdmFieldCodes()) {
            if (fieldCode.equalsIgnoreCase(importContext.getMapScheme().getFmdmInfo().getPeriodField())) continue;
            RuleMap map1 = new RuleMap(RuleKind.UNIT_MAP_EXPORT, fieldCode, fieldCode);
            RuleMap map2 = new RuleMap(RuleKind.UNIT_MAP_IMPORT, fieldCode, fieldCode);
            mapRule.add(map1);
            mapRule.add(map2);
        }
        config.setMapRule(mapRule);
        config.setMapping(mapping);
        return config;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void copyMapConfigForm(ISingleMappingConfig destConfig, ISingleMappingConfig srcConfig, boolean isOverWrite) {
        void var12_23;
        ISingleMappingConfig config = destConfig;
        HashMap<String, SingleFileFormulaItem> formulaDic = new HashMap<String, SingleFileFormulaItem>();
        ArrayList<SingleFileFormulaItemImpl> items = srcConfig.getFormulaInfos();
        if (isOverWrite) {
            items = new ArrayList<SingleFileFormulaItemImpl>();
        } else {
            for (SingleFileFormulaItem item : srcConfig.getFormulaInfos()) {
                formulaDic.put(item.getSingleSchemeName() + "_" + item.getSingleFormulaCode(), item);
            }
        }
        for (SingleFileFormulaItem oldItem : srcConfig.getFormulaInfos()) {
            if (formulaDic.containsKey(oldItem.getSingleSchemeName() + "_" + oldItem.getSingleFormulaCode())) continue;
            SingleFileFormulaItemImpl item = new SingleFileFormulaItemImpl();
            item.copyFrom(oldItem);
            item.setNetSchemeKey(item.getNetSchemeKey());
            item.setNetSchemeName(item.getNetSchemeName());
            item.setSingleSchemeName(item.getSingleSchemeName());
            item.setSingleTableCode(item.getSingleTableCode());
            item.setNetFormCode(item.getNetFormCode());
            item.setNetFormKey(item.getNetFormKey());
            items.add(item);
        }
        config.setFormulaInfos(items);
        HashMap<String, Object> tableInfoDic = new HashMap<String, Object>();
        HashMap<String, SingleFileFieldInfo> fieldInfoDic = new HashMap<String, SingleFileFieldInfo>();
        ArrayList<Object> tableInfos = destConfig.getTableInfos();
        ArrayList fieldInfos = destConfig.getZbFields();
        if (isOverWrite) {
            tableInfos = new ArrayList<Object>();
            fieldInfos = new ArrayList();
        } else {
            for (SingleFileTableInfo singleFileTableInfo : tableInfos) {
                tableInfoDic.put(singleFileTableInfo.getSingleTableCode(), singleFileTableInfo);
            }
            for (SingleFileFieldInfo singleFileFieldInfo : fieldInfos) {
                fieldInfoDic.put(singleFileFieldInfo.getFormCode() + "_" + singleFileFieldInfo.getFieldCode(), singleFileFieldInfo);
            }
        }
        for (int i = 0; i < srcConfig.getTableInfos().size(); ++i) {
            SingleFileTableInfo singleFileTableInfo = (SingleFileTableInfo)srcConfig.getTableInfos().get(i);
            if (tableInfoDic.containsKey(singleFileTableInfo.getSingleTableCode())) continue;
            if (i == 0) {
                SingleFileFmdmInfo fmdminfo = (SingleFileFmdmInfo)singleFileTableInfo;
                SingleFileFmdmInfoImpl fmdmInfoNew = new SingleFileFmdmInfoImpl();
                fmdmInfoNew.copyFrom((SingleFileTableInfo)fmdminfo);
                tableInfos.add(fmdmInfoNew);
                tableInfoDic.put(fmdmInfoNew.getSingleTableCode(), fmdmInfoNew);
                continue;
            }
            SingleFileTableInfoImpl tableInfoNew = new SingleFileTableInfoImpl();
            tableInfoNew.copyFrom(singleFileTableInfo);
            tableInfos.add(tableInfoNew);
            tableInfoDic.put(tableInfoNew.getSingleTableCode(), tableInfoNew);
        }
        for (SingleFileFieldInfo singleFileFieldInfo : srcConfig.getZbFields()) {
            SingleFileFieldInfoImpl fieldNew = new SingleFileFieldInfoImpl();
            fieldNew.copyFrom(singleFileFieldInfo);
            fieldNew.setNetFormCode(singleFileFieldInfo.getNetFormCode());
        }
        config.setTableInfos(tableInfos);
        config.setZbFields(fieldInfos);
        HashMap<String, SingleFileEnumInfo> enumInfoDic = new HashMap<String, SingleFileEnumInfo>();
        Map map = destConfig.getEnumInfos();
        if (isOverWrite) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
        } else {
            for (SingleFileEnumInfo info : destConfig.getEnumInfos().values()) {
                enumInfoDic.put(info.getEnumCode(), info);
            }
        }
        for (SingleFileEnumInfo info : srcConfig.getEnumInfos().values()) {
            if (enumInfoDic.containsKey(info.getEnumCode())) continue;
            SingleFileEnumInfoImpl infoNew = new SingleFileEnumInfoImpl();
            infoNew.copyFrom(info);
            var12_23.put(infoNew.getEnumCode(), infoNew);
        }
        config.setEnumInfos((Map)var12_23);
        UnitMapping mapping = destConfig.getMapping();
        ArrayList<UnitCustomMapping> unitInfos = new ArrayList<UnitCustomMapping>();
        for (UnitCustomMapping unitMap : srcConfig.getMapping().getUnitInfos()) {
            unitInfos.add(unitMap);
        }
        mapping.setUnitInfos(unitInfos);
        HashMap periodMapping = new HashMap();
        if (!isOverWrite) {
            for (String aCode : destConfig.getMapping().getPeriodMapping().keySet()) {
                periodMapping.put(aCode, destConfig.getMapping().getPeriodMapping().get(aCode));
            }
        }
        for (String aCode : srcConfig.getMapping().getPeriodMapping().keySet()) {
            if (periodMapping.containsKey(aCode)) continue;
            periodMapping.put(aCode, srcConfig.getMapping().getPeriodMapping().get(aCode));
        }
        mapping.setPeriodMapping(periodMapping);
        config.setMapping(mapping);
    }

    @Override
    public void saveMapConfig(String fileName, byte[] file, SingleMappingConfig config) {
        this.configAnalService.insertDefaultConfigInImport(fileName, file, (ISingleMappingConfig)config);
    }

    @Override
    public void updateMapConfig(String fileName, byte[] file, SingleMappingConfig config) {
        this.configAnalService.updateConfig(fileName, file, (ISingleMappingConfig)config);
    }

    private SingleFileTaskInfo getTaskInfoByJioData(byte[] fileData) throws Exception {
        SingleFileTaskInfo taskInfo = null;
        String filePath = PathUtil.getExportTempFilePath((String)"JioParaImport");
        String fileName = OrderGenerator.newOrder() + ".jio";
        this.uploadFile(fileData, filePath, fileName);
        String file = filePath + fileName;
        taskInfo = this.getTaskInfoByJioFile(file);
        return taskInfo;
    }

    private SingleFileTaskInfo getTaskInfoByJioFile(String file) throws Exception {
        SingleFileTaskInfoImpl taskInfo = new SingleFileTaskInfoImpl();
        SingleFileImpl singleFile = new SingleFileImpl();
        logger.info("\u89e3\u6790JIO\u6587\u4ef6:" + file + ",\u65f6\u95f4:" + new Date().toString());
        singleFile.infoLoad(file);
        taskInfo = this.getTaskInfoFromSingle((SingleFile)singleFile);
        return taskInfo;
    }

    private SingleFileTaskInfo getTaskInfoByJioFile(byte[] fileData) throws Exception {
        SingleFileTaskInfo taskInfo = null;
        SingleFileImpl singleFile = new SingleFileImpl();
        logger.info("\u89e3\u6790JIO\u4fe1\u606f:" + fileData.length + ",\u65f6\u95f4:" + new Date().toString());
        singleFile.infoLoad(fileData);
        taskInfo = this.getTaskInfoFromSingle((SingleFile)singleFile);
        return taskInfo;
    }

    private Map<String, Map<String, SingleFileFieldInfo>> getTableFieldMap(List<SingleFileFieldInfo> fields, boolean isSingle) {
        return this.getTableFieldMapEx(fields, isSingle, true);
    }

    private Map<String, Map<String, SingleFileFieldInfo>> getTableFieldMapEx(List<SingleFileFieldInfo> fields, boolean isSingle, boolean hasTable) {
        HashMap<String, Map<String, SingleFileFieldInfo>> tableDic = new HashMap<String, Map<String, SingleFileFieldInfo>>();
        for (SingleFileFieldInfo field : fields) {
            boolean isExist;
            boolean bl = isExist = StringUtils.isNotEmpty((String)field.getFieldCode()) && StringUtils.isNotEmpty((String)field.getNetFieldCode());
            if (!isExist) continue;
            Map<String, SingleFileFieldInfo> tableFields = null;
            String tableCode = field.getFormCode();
            String fieldCode = field.getFieldCode();
            String fieldFlag = tableCode + "." + fieldCode;
            if (!isSingle) {
                tableCode = field.getNetFormCode();
                fieldCode = field.getNetFieldCode();
                fieldFlag = field.getNetTableCode() + "." + fieldCode;
            } else if (StringUtils.isEmpty((String)tableCode)) {
                tableCode = field.getTableCode();
                fieldFlag = tableCode + "." + fieldCode;
            }
            if (!hasTable) {
                fieldFlag = fieldCode;
            }
            if (!StringUtils.isNotEmpty((String)tableCode) || !StringUtils.isNotEmpty((String)fieldFlag) || !isExist) continue;
            if (tableDic.containsKey(tableCode)) {
                tableFields = (Map)tableDic.get(tableCode);
            } else {
                tableFields = new HashMap();
                tableDic.put(tableCode, tableFields);
            }
            tableFields.put(fieldFlag, field);
        }
        return tableDic;
    }

    private SingleFileTaskInfo getTaskInfoFromSingle(SingleFile singleFile) {
        SingleFileTaskInfoImpl taskInfo = new SingleFileTaskInfoImpl();
        taskInfo.setSingleTaskFlag(singleFile.getInfo().readString("General", "Flag", ""));
        taskInfo.setSingleTaskTitle(singleFile.getInfo().readString("General", "Flag", ""));
        taskInfo.setSingleTaskYear(singleFile.getInfo().readString("General", "Year", ""));
        taskInfo.setSingleFileFlag(singleFile.getInfo().readString("General", "FileFlag", ""));
        taskInfo.setSingleTaskPeriod(singleFile.getInfo().readString("General", "Period", ""));
        taskInfo.setSingleTaskTime(singleFile.getInfo().readString("General", "Time", ""));
        taskInfo.setUpdateTime(new Date());
        return taskInfo;
    }

    private void uploadFile(byte[] file, String filePath, String fileName) throws SingleFileException, IOException {
        File targetFile = new File(SinglePathUtil.normalize((String)filePath));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(SinglePathUtil.normalize((String)(filePath + fileName)));){
            out.write(file);
            out.flush();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public byte[] splitParaData(String fileName, byte[] fileData) throws Exception {
        byte[] fileData2;
        block21: {
            fileData2 = fileData;
            if (fileData != null && fileData.length > 10245760) {
                String filePath = PathUtil.getExportTempFilePath((String)"JioParaImport");
                filePath = PathUtil.createNewPath((String)filePath, (String)this.getDateFormatCode());
                String saveFileName = OrderGenerator.newOrder() + ".jio";
                this.uploadFile(fileData, filePath, saveFileName);
                String jioFile = filePath + saveFileName;
                try {
                    SingleFileConfigInfo singleInfo = null;
                    try {
                        singleInfo = this.jioService.getSingleInfoByJioFile(jioFile);
                    }
                    catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                    if (singleInfo == null || !singleInfo.getInOutData().contains(InOutDataType.QYSJ)) break block21;
                    String paramFile = filePath + "\u53c2\u6570_" + saveFileName;
                    try {
                        this.singleHelper.splitSingleFile(jioFile, paramFile, null);
                        try (FileInputStream soureStream = new FileInputStream(SinglePathUtil.normalize((String)paramFile));){
                            byte[] Buffer = new byte[soureStream.available()];
                            soureStream.read(Buffer, 0, soureStream.available());
                            fileData2 = Buffer;
                        }
                    }
                    finally {
                        PathUtil.deleteFile((String)paramFile);
                    }
                }
                finally {
                    PathUtil.deleteFile((String)jioFile);
                    PathUtil.deleteDir((String)filePath);
                }
            }
        }
        return fileData2;
    }

    private synchronized String getDateFormatCode() {
        return this.sfDate.format(new Date());
    }
}

