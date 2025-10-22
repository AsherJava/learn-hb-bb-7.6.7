/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.param.EntityDimData
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.CompletionDim
 *  com.jiuqi.nr.data.common.service.dto.CompletionDimFinder
 *  com.jiuqi.nr.data.common.service.dto.FilterDim
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.common.ExtConstants
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.integritycheck.service.impl;

import com.csvreader.CsvReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.CompletionDim;
import com.jiuqi.nr.data.common.service.dto.CompletionDimFinder;
import com.jiuqi.nr.data.common.service.dto.FilterDim;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.integritycheck.common.ExpErrDesFileParam;
import com.jiuqi.nr.integritycheck.common.ExpErrDesFileParam2;
import com.jiuqi.nr.integritycheck.common.ExpErrDesInfo;
import com.jiuqi.nr.integritycheck.common.ImpErrDesFileParam;
import com.jiuqi.nr.integritycheck.common.ImpErrDesFileParam2;
import com.jiuqi.nr.integritycheck.constant.ExpConsts;
import com.jiuqi.nr.integritycheck.helper.ICSplitTableHelper;
import com.jiuqi.nr.integritycheck.mapping.IErrDesMappingProvider;
import com.jiuqi.nr.integritycheck.mapping.IErrDesMappingService;
import com.jiuqi.nr.integritycheck.message.ExpErrDesJsonData;
import com.jiuqi.nr.integritycheck.message.FormMappingMessage;
import com.jiuqi.nr.integritycheck.message.FormSchemeMappingMessage;
import com.jiuqi.nr.integritycheck.message.TaskMappingMessage;
import com.jiuqi.nr.integritycheck.service.IErrDesIOService;
import com.jiuqi.nr.integritycheck.service.IIntegrityCheckService;
import com.jiuqi.nr.integritycheck.utils.ErrDesUtil;
import com.jiuqi.nr.integritycheck.utils.FileUtil;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ErrDesIOServiceImpl
implements IErrDesIOService {
    private static final Logger logger = LoggerFactory.getLogger(ErrDesIOServiceImpl.class);
    @Autowired
    private IIntegrityCheckService integrityCheckService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IErrDesMappingProvider errDesMappingProvider;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private ICSplitTableHelper icSplitTableHelper;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    private static final int MAX_WRITE_ROW = 5000;

    @Override
    public Result<File> exportErrorDes(ExpErrDesFileParam param) {
        String rootPath = ExtConstants.EXPORTDIR + File.separator + "ICRErrDes" + LocalDate.now() + File.separator + UUID.randomUUID().toString() + File.separator;
        return this.expErrDes(param.getDims(), param.getFormSchemeKey(), param.getFormKeys(), param.getParamsMapping(), rootPath, null);
    }

    @Override
    public Result<String> exportErrorDes(ExpErrDesFileParam2 param) {
        String rootPath = ExtConstants.EXPORTDIR + File.separator + "ICRErrDes" + LocalDate.now() + File.separator + UUID.randomUUID().toString() + File.separator;
        this.expErrDes(param.getDims(), param.getFormSchemeKey(), param.getFormKeys(), param.getParamsMapping(), rootPath, "1.0");
        return Result.succeed((String)rootPath, (Object)rootPath);
    }

    /*
     * Exception decompiling
     */
    @NotNull
    private Result<File> expErrDes(DimensionCollection dims, String formSchemeKey, List<String> formKeys, ParamsMapping paramsMapping, String rootPath, String version) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @NotNull
    private File packIntoZip(String rootPath) throws IOException {
        File zip;
        block48: {
            boolean delete;
            String destZipFile = rootPath + "ERRORDESDATA.zip";
            zip = new File(destZipFile);
            if (zip.exists() && !(delete = zip.delete())) {
                throw new IOException("\u5220\u9664\u540c\u540d\u538b\u7f29\u6587\u4ef6\u5931\u8d25\uff1a" + destZipFile);
            }
            boolean newFile = zip.createNewFile();
            if (!newFile) {
                throw new IOException("\u521b\u5efa\u538b\u7f29\u6587\u4ef6\u5931\u8d25\uff1a" + destZipFile);
            }
            try (FileOutputStream output = new FileOutputStream(zip);
                 ZipOutputStream zipOut = new ZipOutputStream(output);){
                List<File> files = FileUtil.getFiles(rootPath, null);
                if (null == files) break block48;
                for (File file : files) {
                    if (file.getName().contains("ERRORDESDATA.zip")) continue;
                    FileInputStream input = new FileInputStream(file);
                    Throwable throwable = null;
                    try {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(input);
                        Throwable throwable2 = null;
                        try {
                            int num;
                            ZipEntry zipEntry = new ZipEntry(file.getName());
                            zipOut.putNextEntry(zipEntry);
                            byte[] buffer = new byte[512];
                            while ((num = bufferedInputStream.read(buffer)) != -1) {
                                zipOut.write(buffer, 0, num);
                            }
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (bufferedInputStream == null) continue;
                            if (throwable2 != null) {
                                try {
                                    bufferedInputStream.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            bufferedInputStream.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (input == null) continue;
                        if (throwable != null) {
                            try {
                                input.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        input.close();
                    }
                }
            }
        }
        return zip;
    }

    private void buildMappingData(ParamsMapping paramsMapping, Map<String, FormMappingMessage> formMapping, List<ExpErrDesInfo> expErrDesInfos, EntityDimData dwEntityDimData, List<EntityDimData> dimEntityDimData, String version) {
        Map dwValueMappingValue = null;
        Map dataTimeValueMappingValue = null;
        HashMap<String, Map> dimsValueMappingValue = new HashMap<String, Map>();
        if (null != paramsMapping) {
            boolean tryPeriod = paramsMapping.tryPeriodMap();
            boolean tryOrgCode = paramsMapping.tryOrgCodeMap();
            HashMap<String, Boolean> entityIdTryBaseDataMap = new HashMap<String, Boolean>();
            for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                entityIdTryBaseDataMap.put(dimEntityDimDatum.getEntityId(), paramsMapping.tryBaseDataMap(dimEntityDimDatum.getEntityId()));
            }
            HashSet<String> dwValues = new HashSet<String>();
            HashSet<String> dataTimeValues = new HashSet<String>();
            HashMap<String, Set> dimNameValues = new HashMap<String, Set>();
            for (ExpErrDesInfo expErrDesInfo : expErrDesInfos) {
                DimensionValueSet dimensionValueSet = expErrDesInfo.getDimensionValueSet();
                if (tryOrgCode) {
                    dwValues.add((String)dimensionValueSet.getValue(dwEntityDimData.getDimensionName()));
                }
                if (tryPeriod) {
                    dataTimeValues.add((String)dimensionValueSet.getValue("DATATIME"));
                }
                for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                    String entityId = dimEntityDimDatum.getEntityId();
                    String dimensionName = dimEntityDimDatum.getDimensionName();
                    if (!((Boolean)entityIdTryBaseDataMap.get(entityId)).booleanValue()) continue;
                    Set dimValues = dimNameValues.computeIfAbsent(dimensionName, k -> new HashSet());
                    dimValues.add((String)dimensionValueSet.getValue(dimensionName));
                }
            }
            if (tryOrgCode && !dwValues.isEmpty()) {
                dwValueMappingValue = paramsMapping.getOriginOrgCode(new ArrayList(dwValues));
            }
            if (tryPeriod && !dataTimeValues.isEmpty()) {
                dataTimeValueMappingValue = paramsMapping.getOriginPeriod(new ArrayList(dataTimeValues));
            }
            for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                String entityId = dimEntityDimDatum.getEntityId();
                String dimensionName = dimEntityDimDatum.getDimensionName();
                Set dimValues = (Set)dimNameValues.get(dimensionName);
                if (!((Boolean)entityIdTryBaseDataMap.get(entityId)).booleanValue() || null == dimValues || dimValues.isEmpty()) continue;
                Map originBaseData = paramsMapping.getOriginBaseData(entityId, new ArrayList(dimValues));
                dimsValueMappingValue.put(dimensionName, originBaseData);
            }
        }
        for (ExpErrDesInfo expErrDesInfo : expErrDesInfos) {
            DimensionValueSet dimensionValueSet = expErrDesInfo.getDimensionValueSet();
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String dimName = dimensionValueSet.getName(i);
                String dimValue = (String)dimensionValueSet.getValue(i);
                if (dimName.equals(dwEntityDimData.getDimensionName()) && null != dwValueMappingValue) {
                    dimensionValueSet.setValue(dimName, dwValueMappingValue.get(dimValue));
                    continue;
                }
                if ("DATATIME".equals(dimName) && null != dataTimeValueMappingValue) {
                    dimensionValueSet.setValue(dimName, dataTimeValueMappingValue.get(dimValue));
                    continue;
                }
                if (dimName.equals(dwEntityDimData.getDimensionName()) || "DATATIME".equals(dimName) || null == dimsValueMappingValue.get(dimName)) continue;
                dimensionValueSet.setValue(dimName, ((Map)dimsValueMappingValue.get(dimName)).get(dimValue));
            }
            String formKey = expErrDesInfo.getFormKey();
            FormMappingMessage formMappingMessage = formMapping.get(formKey);
            expErrDesInfo.setFormKey(formMappingMessage.getFormKey());
            if (StringUtils.hasText(version)) continue;
            expErrDesInfo.setFormCode(formMappingMessage.getFormCode());
            expErrDesInfo.setFormTitle(formMappingMessage.getFormTitle());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Result<Void> importErrorDes(File zipFile, ImpErrDesFileParam param) {
        Result result;
        String rootPath = ExtConstants.UPLOADDIR + File.separator + "ICRErrDes" + LocalDate.now() + File.separator + UUID.randomUUID().toString() + File.separator;
        FileUtil.unZip(zipFile, rootPath);
        List<File> files = FileUtil.getFiles(rootPath, null);
        if (null == files) {
            return Result.failed((String)"\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1azip\u5305\u4e2d\u65e0\u6587\u4ef6");
        }
        File jsonFile = null;
        File csvFile = null;
        for (File file : files) {
            if (file.getName().endsWith(".json")) {
                jsonFile = file;
                continue;
            }
            if (!file.getName().endsWith(".csv")) continue;
            csvFile = file;
        }
        if (null == jsonFile || null == csvFile) {
            return Result.failed((String)"\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1azip\u5305\u4e2d\u65e0json\u6216csv\u6587\u4ef6");
        }
        CsvReader reader = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExpErrDesJsonData expErrDesJsonData = (ExpErrDesJsonData)mapper.readValue(jsonFile, ExpErrDesJsonData.class);
            TaskMappingMessage taskInfo = expErrDesJsonData.getTaskMappingMessage();
            FormSchemeMappingMessage formSchemeInfo = expErrDesJsonData.getFormSchemeMappingMessage();
            List<FormMappingMessage> formInfos = expErrDesJsonData.getFormMappingMessges();
            IErrDesMappingService errDesMappingService = this.errDesMappingProvider.getIErrDesMappingService();
            TaskMappingMessage taskMapping = errDesMappingService.getTaskMapping(taskInfo);
            FormSchemeMappingMessage formSchemeMapping = errDesMappingService.getFormSchemeMapping(taskInfo, formSchemeInfo);
            Map<String, FormMappingMessage> formMapping = errDesMappingService.getFormMapping(taskInfo, formSchemeInfo, formInfos);
            IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskMapping.getTaskKey(), formSchemeMapping.getFormSchemeKey());
            List formKeys = formMapping.values().stream().map(FormMappingMessage::getFormKey).collect(Collectors.toList());
            IBatchAccessResult batchAccessResult = dataAccessService.getWriteAccess(param.getDims(), formKeys);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskMapping.getTaskKey());
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String tableName = this.icSplitTableHelper.getICDSplitTableName(dataScheme);
            TableModelDefine icdTable = this.dataModelService.getTableModelDefineByName(tableName);
            if (null == icdTable) {
                logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
                Result result2 = Result.failed((String)"\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
                return result2;
            }
            boolean fullImport = param.isFullImport();
            if (fullImport) {
                this.clearErrDes(param.getDims(), batchAccessResult, icdTable);
            }
            EntityDimData dwEntityDimData = this.dataAccesslUtil.getDwEntityDimData(formSchemeMapping.getFormSchemeKey());
            List dimEntityDimData = this.dataAccesslUtil.getDimEntityDimData(formSchemeMapping.getFormSchemeKey());
            List<String> dimNames = dimEntityDimData.stream().map(EntityDimData::getDimensionName).collect(Collectors.toList());
            reader = new CsvReader(csvFile.getAbsolutePath(), ',', StandardCharsets.UTF_8);
            ArrayList<String> headerCodes = new ArrayList<String>();
            if (reader.readHeaders()) {
                for (int i = 0; i < reader.getHeaderCount(); ++i) {
                    String headerCode = reader.getHeader(i);
                    headerCodes.add(headerCode);
                }
            }
            int readRowCount = 0;
            int importRowCount = 0;
            ArrayList<ExpErrDesInfo> impErrDesInfos = new ArrayList<ExpErrDesInfo>();
            while (reader.readRecord()) {
                ExpErrDesInfo impErrDesInfo = this.buildErrDesInfo(reader, dwEntityDimData, dimNames, headerCodes);
                DimensionCombination dimensionCombination = this.dimCollectionBuildUtil.buildDimensionCombination(impErrDesInfo.getDimensionValueSet(), formSchemeMapping.getFormSchemeKey());
                boolean canWrite = batchAccessResult.getAccess(dimensionCombination, impErrDesInfo.getFormKey()).haveAccess();
                if (!canWrite) continue;
                impErrDesInfos.add(impErrDesInfo);
                if (++readRowCount < 5000) continue;
                this.buildMappingData(param.getMapping(), formMapping, impErrDesInfos, dwEntityDimData, dimEntityDimData, null);
                if (!fullImport) {
                    this.clearErrDes(impErrDesInfos, icdTable);
                }
                this.insertErrDes(taskDefine, icdTable, impErrDesInfos);
                importRowCount += readRowCount;
                readRowCount = 0;
                impErrDesInfos.clear();
            }
            if (readRowCount > 0) {
                this.buildMappingData(param.getMapping(), formMapping, impErrDesInfos, dwEntityDimData, dimEntityDimData, null);
                if (!fullImport) {
                    this.clearErrDes(impErrDesInfos, icdTable);
                }
                this.insertErrDes(taskDefine, icdTable, impErrDesInfos);
                importRowCount += readRowCount;
            }
            Result result3 = Result.succeed((String)("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u6210\u529f\u5bfc\u5165" + importRowCount + "\u6761\u51fa\u9519\u8bf4\u660e\u3002"));
            return result3;
        }
        catch (IOException e) {
            logger.error("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
            result = Result.failed((String)("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()));
            return result;
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u6e05\u9664\u6216\u63d2\u5165\u6570\u636e\u5931\u8d25", e);
            result = Result.failed((String)("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u6e05\u9664\u6216\u63d2\u5165\u6570\u636e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()));
            return result;
        }
        finally {
            if (null != reader) {
                reader.close();
            }
            FileUtil.deleteFiles(rootPath);
        }
    }

    private ExpErrDesInfo buildErrDesInfo(CsvReader reader, EntityDimData dwEntityDimData, List<String> dimNames, List<String> headerCodes) throws IOException {
        ExpErrDesInfo impErrDesInfo = new ExpErrDesInfo();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (String headerCode : headerCodes) {
            String data = reader.get(headerCode);
            if (headerCode.equals(dwEntityDimData.getDimensionName()) || "DATATIME".equals(headerCode) || dimNames.contains(headerCode)) {
                dimensionValueSet.setValue(headerCode, (Object)data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES_OLD.get(0).equals(headerCode)) {
                impErrDesInfo.setFormKey(data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES_OLD.get(3).equals(headerCode)) {
                impErrDesInfo.setDescription(data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES_OLD.get(4).equals(headerCode)) {
                impErrDesInfo.setCreateTime(data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES_OLD.get(5).equals(headerCode)) {
                impErrDesInfo.setCreator(data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES_OLD.get(6).equals(headerCode)) {
                impErrDesInfo.setUpdateTime(data);
                continue;
            }
            if (!ExpConsts.EXP_CSV_HEADER_CODES_OLD.get(7).equals(headerCode)) continue;
            impErrDesInfo.setUpdater(data);
        }
        impErrDesInfo.setDimensionValueSet(dimensionValueSet);
        return impErrDesInfo;
    }

    private void clearErrDes(List<ExpErrDesInfo> impErrDesInfos, TableModelDefine icdTable) throws Exception {
        ArrayList<String> recids = new ArrayList<String>();
        for (ExpErrDesInfo impErrDesInfo : impErrDesInfos) {
            UUID recid = ErrDesUtil.toFakeUUID(impErrDesInfo.getDimensionValueSet() + impErrDesInfo.getFormKey());
            recids.add(recid.toString());
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID());
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
            if (!icdField.getCode().equals("RECID")) continue;
            queryModel.getColumnFilters().put(icdField, recids);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
        iNvwaDataUpdator.deleteAll();
        iNvwaDataUpdator.commitChanges(context);
    }

    private void clearErrDes(DimensionCollection dims, IBatchAccessResult batchAccessResult, TableModelDefine icdTable) throws Exception {
        ArrayList<String> recids = new ArrayList<String>();
        for (DimensionCombination dimensionCombination : dims.getDimensionCombinations()) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            List formKeys = batchAccessResult.getAccessForm(dimensionCombination);
            for (String formKey : formKeys) {
                UUID recid = ErrDesUtil.toFakeUUID(dimensionValueSet + formKey);
                recids.add(recid.toString());
            }
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID());
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
            if (!icdField.getCode().equals("RECID")) continue;
            queryModel.getColumnFilters().put(icdField, recids);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
        iNvwaDataUpdator.deleteAll();
        iNvwaDataUpdator.commitChanges(context);
    }

    private void insertErrDes(TaskDefine taskDefine, TableModelDefine icdTable, List<ExpErrDesInfo> impErrDesInfos) throws Exception {
        IEntityDefine entity = this.entityMetaService.queryEntity(taskDefine.getDw());
        String dwDimName = entity.getDimensionName();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID());
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
        List columns = queryModel.getColumns();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ExpErrDesInfo errDesInfo : impErrDesInfos) {
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            block20: for (int i = 0; i < columns.size(); ++i) {
                switch (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                    case "RECID": {
                        String recid = ErrDesUtil.toFakeUUID(errDesInfo.getDimensionValueSet() + errDesInfo.getFormKey()).toString();
                        iNvwaDataRow.setValue(i, (Object)recid);
                        continue block20;
                    }
                    case "DESCRIPTION": {
                        iNvwaDataRow.setValue(i, (Object)errDesInfo.getDescription());
                        continue block20;
                    }
                    case "CREATETIME": {
                        Date createTime = formatter.parse(errDesInfo.getCreateTime());
                        iNvwaDataRow.setValue(i, (Object)createTime);
                        continue block20;
                    }
                    case "CREATOR": {
                        iNvwaDataRow.setValue(i, (Object)errDesInfo.getCreator());
                        continue block20;
                    }
                    case "UPDATETIME": {
                        Date updateTime = formatter.parse(errDesInfo.getUpdateTime());
                        iNvwaDataRow.setValue(i, (Object)updateTime);
                        continue block20;
                    }
                    case "UPDATER": {
                        iNvwaDataRow.setValue(i, (Object)errDesInfo.getUpdater());
                        continue block20;
                    }
                    case "MDCODE": {
                        iNvwaDataRow.setValue(i, (Object)errDesInfo.getDimensionValueSet().getValue(dwDimName).toString());
                        continue block20;
                    }
                    default: {
                        iNvwaDataRow.setValue(i, (Object)errDesInfo.getDimensionValueSet().getValue(((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()).toString());
                    }
                }
            }
        }
        iNvwaDataUpdator.commitChanges(context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Result<Void> importErrorDes(String impPath, ImpErrDesFileParam2 param) {
        Result result;
        List<File> files = FileUtil.getFiles(impPath, null);
        if (null == files) {
            return Result.failed((String)"\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1azip\u5305\u4e2d\u65e0\u6587\u4ef6");
        }
        File jsonFile = null;
        File csvFile = null;
        for (File file : files) {
            if (file.getName().endsWith(".json")) {
                jsonFile = file;
                continue;
            }
            if (!file.getName().endsWith(".csv")) continue;
            csvFile = file;
        }
        if (null == jsonFile || null == csvFile) {
            return Result.failed((String)"\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1azip\u5305\u4e2d\u65e0json\u6216csv\u6587\u4ef6");
        }
        ParamsMapping paramsMapping = param.getMapping();
        CsvReader reader = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExpErrDesJsonData expErrDesJsonData = (ExpErrDesJsonData)mapper.readValue(jsonFile, ExpErrDesJsonData.class);
            TaskMappingMessage taskInfo = expErrDesJsonData.getTaskMappingMessage();
            FormSchemeMappingMessage formSchemeInfo = expErrDesJsonData.getFormSchemeMappingMessage();
            List<FormMappingMessage> formMappingMessges = expErrDesJsonData.getFormMappingMessges();
            String originTaskKey = paramsMapping.getOriginTaskKey(taskInfo.getTaskKey());
            TaskMappingMessage taskMapping = new TaskMappingMessage(originTaskKey, null, null);
            String originFormSchemeKey = paramsMapping.getOriginFormSchemeKey(formSchemeInfo.getFormSchemeKey());
            FormSchemeMappingMessage formSchemeMapping = new FormSchemeMappingMessage(originFormSchemeKey, null, null);
            Map originFormKeyMap = paramsMapping.getOriginFormKey(formMappingMessges.stream().map(FormMappingMessage::getFormKey).collect(Collectors.toList()));
            IProviderStore providerStore = param.getProviderStore();
            DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = providerStore.getDataPermissionEvaluatorFactory();
            ArrayList originFormKeys = new ArrayList(originFormKeyMap.values());
            DataPermissionEvaluator evaluator = dataPermissionEvaluatorFactory.createEvaluator(new EvaluatorParam(taskMapping.getTaskKey(), formSchemeMapping.getFormSchemeKey(), ResouceType.FORM.getCode()), param.getDims(), originFormKeys);
            Collection accessResources = evaluator.haveAccess(param.getDims(), originFormKeys, AuthType.WRITEABLE).getAccessResources();
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskMapping.getTaskKey());
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String tableName = this.icSplitTableHelper.getICDSplitTableName(dataScheme);
            TableModelDefine icdTable = this.dataModelService.getTableModelDefineByName(tableName);
            if (null == icdTable) {
                logger.error("\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
                Result result2 = Result.failed((String)"\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848");
                return result2;
            }
            boolean fullImport = param.isFullImport();
            if (fullImport) {
                this.clearErrDes2(accessResources, icdTable);
            }
            EntityDimData dwEntityDimData = this.dataAccesslUtil.getDwEntityDimData(formSchemeMapping.getFormSchemeKey());
            List dimEntityDimData = this.dataAccesslUtil.getDimEntityDimData(formSchemeMapping.getFormSchemeKey());
            List<String> dimNames = dimEntityDimData.stream().map(EntityDimData::getDimensionName).collect(Collectors.toList());
            reader = new CsvReader(csvFile.getAbsolutePath(), ',', StandardCharsets.UTF_8);
            ArrayList<String> headerCodes = new ArrayList<String>();
            if (reader.readHeaders()) {
                for (int i = 0; i < reader.getHeaderCount(); ++i) {
                    String headerCode = reader.getHeader(i);
                    headerCodes.add(headerCode);
                }
            }
            ArrayList<DimensionValueSet> impDimRange = new ArrayList<DimensionValueSet>();
            List dimensionCombinations = param.getDims().getDimensionCombinations();
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                impDimRange.add(dimensionCombination.toDimensionValueSet());
            }
            HashSet<String> impFormRange = new HashSet<String>(param.getFormKeys());
            CompletionDim completionDims = param.getCompletionDims();
            FilterDim filterDims = param.getFilterDims();
            int readRowCount = 0;
            ArrayList<ExpErrDesInfo> impErrDesInfos = new ArrayList<ExpErrDesInfo>();
            while (reader.readRecord()) {
                DimensionCombination dimensionCombination;
                boolean canWrite;
                ExpErrDesInfo impErrDesInfo = this.buildErrDesInfo2(reader, dwEntityDimData, dimNames, headerCodes, completionDims, filterDims);
                if (impErrDesInfo == null || !impFormRange.contains(originFormKeyMap.get(impErrDesInfo.getFormKey())) || !impDimRange.contains(impErrDesInfo.getDimensionValueSet()) || !(canWrite = evaluator.haveAccess(dimensionCombination = this.dimCollectionBuildUtil.buildDimensionCombination(impErrDesInfo.getDimensionValueSet(), formSchemeMapping.getFormSchemeKey()), impErrDesInfo.getFormKey(), AuthType.WRITEABLE))) continue;
                impErrDesInfos.add(impErrDesInfo);
                if (++readRowCount < 5000) continue;
                this.buildMappingData2(paramsMapping, originFormKeyMap, impErrDesInfos, dwEntityDimData, dimEntityDimData);
                if (!fullImport) {
                    this.clearErrDes(impErrDesInfos, icdTable);
                }
                this.insertErrDes(taskDefine, icdTable, impErrDesInfos);
                readRowCount = 0;
                impErrDesInfos.clear();
            }
            if (readRowCount > 0) {
                this.buildMappingData2(paramsMapping, originFormKeyMap, impErrDesInfos, dwEntityDimData, dimEntityDimData);
                if (!fullImport) {
                    this.clearErrDes(impErrDesInfos, icdTable);
                }
                this.insertErrDes(taskDefine, icdTable, impErrDesInfos);
            }
            Result result3 = Result.succeed(null);
            return result3;
        }
        catch (IOException e) {
            logger.error("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
            result = Result.failed((String)("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()));
            return result;
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u6e05\u9664\u6216\u63d2\u5165\u6570\u636e\u5931\u8d25", e);
            result = Result.failed((String)("\u5bfc\u5165\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25\uff0c\u6e05\u9664\u6216\u63d2\u5165\u6570\u636e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()));
            return result;
        }
        finally {
            if (null != reader) {
                reader.close();
            }
            FileUtil.deleteFiles(impPath);
        }
    }

    private void clearErrDes2(Collection<DataPermissionResource> accessResources, TableModelDefine icdTable) throws Exception {
        ArrayList<String> recids = new ArrayList<String>();
        for (DataPermissionResource accessResource : accessResources) {
            UUID recid = ErrDesUtil.toFakeUUID(accessResource.getDimensionCombination().toDimensionValueSet() + accessResource.getResourceId());
            recids.add(recid.toString());
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List icdFields = this.dataModelService.getColumnModelDefinesByTable(icdTable.getID());
        for (ColumnModelDefine icdField : icdFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(icdField));
            if (!icdField.getCode().equals("RECID")) continue;
            queryModel.getColumnFilters().put(icdField, recids);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
        iNvwaDataUpdator.deleteAll();
        iNvwaDataUpdator.commitChanges(context);
    }

    private ExpErrDesInfo buildErrDesInfo2(CsvReader reader, EntityDimData dwEntityDimData, List<String> dimNames, List<String> headerCodes, CompletionDim completionDims, FilterDim filterDims) throws IOException {
        ExpErrDesInfo impErrDesInfo = new ExpErrDesInfo();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        boolean filterDim = filterDims.isFilterDim();
        for (String headerCode : headerCodes) {
            String data = reader.get(headerCode);
            if (filterDim && filterDims.getFilterDims().contains(headerCode)) {
                DimensionValueSet fixedFilterDims = filterDims.getFixedFilterDims();
                Object value = fixedFilterDims.getValue(headerCode);
                if (null != value && !Objects.equals(value, data)) {
                    return null;
                }
                List dynamicsFilterDims = filterDims.getDynamicsFilterDims();
                if (dynamicsFilterDims != null && dynamicsFilterDims.contains(headerCode)) continue;
            }
            if (headerCode.equals(dwEntityDimData.getDimensionName()) || "DATATIME".equals(headerCode) || dimNames.contains(headerCode)) {
                dimensionValueSet.setValue(headerCode, (Object)data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES.get(0).equals(headerCode)) {
                impErrDesInfo.setFormKey(data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES.get(1).equals(headerCode)) {
                impErrDesInfo.setDescription(data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES.get(2).equals(headerCode)) {
                impErrDesInfo.setCreateTime(data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES.get(3).equals(headerCode)) {
                impErrDesInfo.setCreator(data);
                continue;
            }
            if (ExpConsts.EXP_CSV_HEADER_CODES.get(4).equals(headerCode)) {
                impErrDesInfo.setUpdateTime(data);
                continue;
            }
            if (!ExpConsts.EXP_CSV_HEADER_CODES.get(5).equals(headerCode)) continue;
            impErrDesInfo.setUpdater(data);
        }
        boolean completionDim = completionDims.isCompletionDim();
        if (completionDim) {
            DimensionValueSet fixedCompletionDims = completionDims.getFixedCompletionDims();
            if (fixedCompletionDims != null) {
                for (int i = 0; i < fixedCompletionDims.size(); ++i) {
                    dimensionValueSet.setValue(fixedCompletionDims.getName(i), fixedCompletionDims.getValue(i));
                }
            }
            CompletionDimFinder finder = completionDims.getFinder();
            List dynamicsCompletionDims = completionDims.getDynamicsCompletionDims();
            if (dynamicsCompletionDims != null) {
                Object dwValue = dimensionValueSet.getValue(dwEntityDimData.getDimensionName());
                String dw = dwValue.toString();
                for (String dynamicsCompletionDim : dynamicsCompletionDims) {
                    dimensionValueSet.setValue(dynamicsCompletionDim, (Object)finder.findByDw(dw, dynamicsCompletionDim));
                }
            }
        }
        impErrDesInfo.setDimensionValueSet(dimensionValueSet);
        return impErrDesInfo;
    }

    private void buildMappingData2(ParamsMapping paramsMapping, Map<String, String> originFormKeyMap, List<ExpErrDesInfo> expErrDesInfos, EntityDimData dwEntityDimData, List<EntityDimData> dimEntityDimData) {
        Map dwValueMappingValue = null;
        Map dataTimeValueMappingValue = null;
        HashMap<String, Map> dimsValueMappingValue = new HashMap<String, Map>();
        if (null != paramsMapping) {
            boolean tryPeriod = paramsMapping.tryPeriodMap();
            boolean tryOrgCode = paramsMapping.tryOrgCodeMap();
            HashMap<String, Boolean> entityIdTryBaseDataMap = new HashMap<String, Boolean>();
            for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                entityIdTryBaseDataMap.put(dimEntityDimDatum.getEntityId(), paramsMapping.tryBaseDataMap(dimEntityDimDatum.getEntityId()));
            }
            HashSet<String> dwValues = new HashSet<String>();
            HashSet<String> dataTimeValues = new HashSet<String>();
            HashMap<String, Set> dimNameValues = new HashMap<String, Set>();
            for (ExpErrDesInfo expErrDesInfo : expErrDesInfos) {
                DimensionValueSet dimensionValueSet = expErrDesInfo.getDimensionValueSet();
                if (tryOrgCode) {
                    dwValues.add((String)dimensionValueSet.getValue(dwEntityDimData.getDimensionName()));
                }
                if (tryPeriod) {
                    dataTimeValues.add((String)dimensionValueSet.getValue("DATATIME"));
                }
                for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                    String entityId = dimEntityDimDatum.getEntityId();
                    String dimensionName = dimEntityDimDatum.getDimensionName();
                    if (!((Boolean)entityIdTryBaseDataMap.get(entityId)).booleanValue()) continue;
                    Set dimValues = dimNameValues.computeIfAbsent(dimensionName, k -> new HashSet());
                    dimValues.add((String)dimensionValueSet.getValue(dimensionName));
                }
            }
            if (tryOrgCode && !dwValues.isEmpty()) {
                dwValueMappingValue = paramsMapping.getOriginOrgCode(new ArrayList(dwValues));
            }
            if (tryPeriod && !dataTimeValues.isEmpty()) {
                dataTimeValueMappingValue = paramsMapping.getOriginPeriod(new ArrayList(dataTimeValues));
            }
            for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                String entityId = dimEntityDimDatum.getEntityId();
                String dimensionName = dimEntityDimDatum.getDimensionName();
                Set dimValues = (Set)dimNameValues.get(dimensionName);
                if (!((Boolean)entityIdTryBaseDataMap.get(entityId)).booleanValue() || null == dimValues || dimValues.isEmpty()) continue;
                Map originBaseData = paramsMapping.getOriginBaseData(entityId, new ArrayList(dimValues));
                dimsValueMappingValue.put(dimensionName, originBaseData);
            }
        }
        for (ExpErrDesInfo expErrDesInfo : expErrDesInfos) {
            DimensionValueSet dimensionValueSet = expErrDesInfo.getDimensionValueSet();
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String dimName = dimensionValueSet.getName(i);
                String dimValue = (String)dimensionValueSet.getValue(i);
                if (dimName.equals(dwEntityDimData.getDimensionName()) && null != dwValueMappingValue) {
                    dimensionValueSet.setValue(dimName, dwValueMappingValue.get(dimValue));
                    continue;
                }
                if ("DATATIME".equals(dimName) && null != dataTimeValueMappingValue) {
                    dimensionValueSet.setValue(dimName, dataTimeValueMappingValue.get(dimValue));
                    continue;
                }
                if (dimName.equals(dwEntityDimData.getDimensionName()) || "DATATIME".equals(dimName) || null == dimsValueMappingValue.get(dimName)) continue;
                dimensionValueSet.setValue(dimName, ((Map)dimsValueMappingValue.get(dimName)).get(dimValue));
            }
            String formKey = expErrDesInfo.getFormKey();
            expErrDesInfo.setFormKey(originFormKeyMap.get(formKey));
        }
    }
}

