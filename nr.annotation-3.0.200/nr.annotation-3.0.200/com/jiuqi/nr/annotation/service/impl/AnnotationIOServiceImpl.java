/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.csvreader.CsvWriter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.data.access.param.EntityDimData
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
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
package com.jiuqi.nr.annotation.service.impl;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.annotation.constant.ExpConsts;
import com.jiuqi.nr.annotation.input.ExpAnnotationFileParam;
import com.jiuqi.nr.annotation.input.ExpAnnotationParam;
import com.jiuqi.nr.annotation.input.ImpAnnotationFileParam;
import com.jiuqi.nr.annotation.mapping.IAnnotationMappingProvider;
import com.jiuqi.nr.annotation.mapping.IAnnotationMappingService;
import com.jiuqi.nr.annotation.message.ExpAnnotationJsonData;
import com.jiuqi.nr.annotation.message.FormMappingMessage;
import com.jiuqi.nr.annotation.message.FormSchemeMappingMessage;
import com.jiuqi.nr.annotation.message.ImpAnnotationInfo;
import com.jiuqi.nr.annotation.message.ImpRelInfo;
import com.jiuqi.nr.annotation.message.ImpTypeInfo;
import com.jiuqi.nr.annotation.message.LinkMappingMessage;
import com.jiuqi.nr.annotation.message.RegionMappingMessage;
import com.jiuqi.nr.annotation.message.TaskMappingMessage;
import com.jiuqi.nr.annotation.output.ExpAnnotationComment;
import com.jiuqi.nr.annotation.output.ExpAnnotationRel;
import com.jiuqi.nr.annotation.output.ExpAnnotationResult;
import com.jiuqi.nr.annotation.service.IAnnotationIOService;
import com.jiuqi.nr.annotation.service.IAnnotationService;
import com.jiuqi.nr.annotation.util.FileUtil;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

@Service
public class AnnotationIOServiceImpl
implements IAnnotationIOService {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationIOServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IAnnotationMappingProvider annotationMappingProvider;
    @Autowired
    private IAnnotationService annotationService;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider nvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    private static final String PERIOD = "DATATIME";
    private static final int MAX_WRITE_ROW = 5000;

    @Override
    public Result<File> exportAnnotation(ExpAnnotationFileParam param) {
        ExpAnnotationParam expAnnotationParam = new ExpAnnotationParam();
        expAnnotationParam.setDims(param.getDims());
        expAnnotationParam.setFormSchemeKey(param.getFormSchemeKey());
        expAnnotationParam.setFormKeys(param.getFormKeys());
        List<ExpAnnotationResult> expAnnotationResults = this.annotationService.queryAnnotation(expAnnotationParam);
        if (null == expAnnotationResults || expAnnotationResults.isEmpty()) {
            return Result.succeed(null);
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List formDefines = this.runTimeViewController.queryFormsById(param.getFormKeys());
        IAnnotationMappingService annotationMappingService = this.annotationMappingProvider.getIAnnotationMappingService();
        TaskMappingMessage taskMappingMessageParam = new TaskMappingMessage(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle());
        TaskMappingMessage taskMapping = annotationMappingService.getTaskMapping(taskMappingMessageParam);
        FormSchemeMappingMessage formSchemeParam = new FormSchemeMappingMessage(formScheme.getKey(), formScheme.getFormSchemeCode(), formScheme.getTitle());
        FormSchemeMappingMessage formSchemeMapping = annotationMappingService.getFormSchemeMapping(taskMappingMessageParam, formSchemeParam);
        ArrayList<FormMappingMessage> formParams = new ArrayList<FormMappingMessage>();
        for (FormDefine formDefine : formDefines) {
            FormMappingMessage formParam = new FormMappingMessage(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle());
            formParams.add(formParam);
        }
        Map<String, FormMappingMessage> formMappings = annotationMappingService.getFormMapping(taskMappingMessageParam, formSchemeParam, formParams);
        HashMap<String, RegionMappingMessage> regionMappings = new HashMap<String, RegionMappingMessage>();
        HashMap<String, LinkMappingMessage> linkMappings = new HashMap<String, LinkMappingMessage>();
        for (String formKey : param.getFormKeys()) {
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            FormMappingMessage formParam = new FormMappingMessage(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle());
            List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
            ArrayList<RegionMappingMessage> regionParams = new ArrayList<RegionMappingMessage>();
            for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                RegionMappingMessage regionParam = new RegionMappingMessage(dataRegionDefine.getKey(), dataRegionDefine.getRegionLeft(), dataRegionDefine.getRegionRight(), dataRegionDefine.getRegionTop(), dataRegionDefine.getRegionBottom(), dataRegionDefine.getRegionKind().getValue());
                regionParams.add(regionParam);
                List dataLinkDefines = this.runTimeViewController.getAllLinksInRegion(dataRegionDefine.getKey());
                ArrayList<LinkMappingMessage> linkParams = new ArrayList<LinkMappingMessage>();
                for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                    linkParams.add(new LinkMappingMessage(dataLinkDefine.getKey(), dataLinkDefine.getPosX(), dataLinkDefine.getPosY()));
                }
                Map<String, LinkMappingMessage> linkMapping = annotationMappingService.getLinkMapping(taskMappingMessageParam, formSchemeParam, formParam, regionParam, linkParams);
                if (null == linkMapping) continue;
                linkMappings.putAll(linkMapping);
            }
            Map<String, RegionMappingMessage> regionMapping = annotationMappingService.getRegionMapping(taskMappingMessageParam, formSchemeParam, formParam, regionParams);
            regionMappings.putAll(regionMapping);
        }
        EntityDimData dwEntityDimData = this.dataAccesslUtil.getDwEntityDimData(param.getFormSchemeKey());
        List dimEntityDimData = this.dataAccesslUtil.getDimEntityDimData(param.getFormSchemeKey());
        this.buildMappingData(param.getParamsMapping(), formMappings, regionMappings, linkMappings, expAnnotationResults, dwEntityDimData, dimEntityDimData);
        return this.importZipFile(param, expAnnotationResults, formScheme, taskMapping, formSchemeMapping, formMappings, dwEntityDimData, dimEntityDimData);
    }

    /*
     * Exception decompiling
     */
    @NotNull
    private Result<File> importZipFile(ExpAnnotationFileParam param, List<ExpAnnotationResult> expAnnotationResults, FormSchemeDefine formScheme, TaskMappingMessage taskMapping, FormSchemeMappingMessage formSchemeMapping, Map<String, FormMappingMessage> formMappings, EntityDimData dwEntityDimData, List<EntityDimData> dimEntityDimData) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 7 blocks at once
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

    private void finalClose(CsvWriter annoCsvWriter, CsvWriter relCsvWriter, CsvWriter commentCsvWriter, CsvWriter typeCsvWriter, File jsonFile, File annoCsvFile, File relCsvFile, File commentCsvFile, File typeCsvFile) {
        if (null != annoCsvWriter) {
            annoCsvWriter.close();
        }
        if (null != relCsvWriter) {
            relCsvWriter.close();
        }
        if (null != commentCsvWriter) {
            commentCsvWriter.close();
        }
        if (null != typeCsvWriter) {
            typeCsvWriter.close();
        }
        if (null != jsonFile) {
            FileUtil.deleteFiles(jsonFile);
        }
        if (null != annoCsvFile) {
            FileUtil.deleteFiles(annoCsvFile);
        }
        if (null != relCsvFile) {
            FileUtil.deleteFiles(relCsvFile);
        }
        if (null != commentCsvFile) {
            FileUtil.deleteFiles(commentCsvFile);
        }
        if (null != typeCsvFile) {
            FileUtil.deleteFiles(typeCsvFile);
        }
    }

    private void writeDetaildData(List<ExpAnnotationResult> expAnnotationResults, EntityDimData dwEntityDimData, List<EntityDimData> dimEntityDimData, CsvWriter annoCsvWriter, CsvWriter relCsvWriter, CsvWriter commentCsvWriter, CsvWriter typeCsvWriter) throws IOException {
        int annoWriteRowCount = 0;
        int relWriteRowCount = 0;
        int commentWriteRowCount = 0;
        int typeWriteRowCount = 0;
        for (ExpAnnotationResult expAnnotationResult : expAnnotationResults) {
            List<String> list;
            ArrayList<String> annoRowValues = new ArrayList<String>();
            Map<String, String> dimNameValue = expAnnotationResult.getDimNameValue();
            annoRowValues.add(dimNameValue.get(dwEntityDimData.getDimensionName()));
            annoRowValues.add(dimNameValue.get(PERIOD));
            for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                annoRowValues.add(dimNameValue.get(dimEntityDimDatum.getDimensionName()));
            }
            annoRowValues.add(expAnnotationResult.getId());
            annoRowValues.add(expAnnotationResult.getContent());
            annoRowValues.add(expAnnotationResult.getUserName());
            annoRowValues.add(String.valueOf(expAnnotationResult.getDate()));
            String[] annoRowDataArray = new String[annoRowValues.size()];
            annoRowValues.toArray(annoRowDataArray);
            annoCsvWriter.writeRecord(annoRowDataArray);
            if (++annoWriteRowCount >= 5000) {
                annoCsvWriter.flush();
                annoWriteRowCount = 0;
            }
            List<ExpAnnotationRel> relations = expAnnotationResult.getRelations();
            for (ExpAnnotationRel expAnnotationRel : relations) {
                String[] relRowDataArray = new String[ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.size()];
                relRowDataArray[0] = expAnnotationRel.getId();
                relRowDataArray[1] = expAnnotationResult.getId();
                relRowDataArray[2] = expAnnotationRel.getFormKey();
                relRowDataArray[3] = expAnnotationRel.getFormCode();
                relRowDataArray[4] = expAnnotationRel.getFormTitle();
                relRowDataArray[5] = expAnnotationRel.getRegionKey();
                relRowDataArray[6] = String.valueOf(expAnnotationRel.getRegionLeft());
                relRowDataArray[7] = String.valueOf(expAnnotationRel.getRegionRight());
                relRowDataArray[8] = String.valueOf(expAnnotationRel.getRegionTop());
                relRowDataArray[9] = String.valueOf(expAnnotationRel.getRegionBottom());
                relRowDataArray[10] = String.valueOf(expAnnotationRel.getType());
                relRowDataArray[11] = expAnnotationRel.getDataLinkKey();
                relRowDataArray[12] = String.valueOf(expAnnotationRel.getPosX());
                relRowDataArray[13] = String.valueOf(expAnnotationRel.getPosY());
                relRowDataArray[14] = expAnnotationRel.getFieldKey();
                relRowDataArray[15] = expAnnotationRel.getRowId();
                relRowDataArray[16] = expAnnotationRel.getShow();
                relCsvWriter.writeRecord(relRowDataArray);
                if (++relWriteRowCount < 5000) continue;
                relCsvWriter.flush();
                relWriteRowCount = 0;
            }
            List<ExpAnnotationComment> comments = expAnnotationResult.getComments();
            if (null != comments && !comments.isEmpty()) {
                for (ExpAnnotationComment comment : comments) {
                    String[] commentRowDataArray = new String[ExpConsts.EXP_COMMENT_ANNO_CSV_HEADER_CODES.size()];
                    commentRowDataArray[0] = comment.getId();
                    commentRowDataArray[1] = expAnnotationResult.getId();
                    commentRowDataArray[2] = comment.getContent();
                    commentRowDataArray[3] = comment.getUserName();
                    commentRowDataArray[4] = comment.getRepyUserName();
                    commentRowDataArray[5] = String.valueOf(comment.getDate());
                    commentCsvWriter.writeRecord(commentRowDataArray);
                    if (++commentWriteRowCount < 5000) continue;
                    commentCsvWriter.flush();
                    commentWriteRowCount = 0;
                }
            }
            if (null == (list = expAnnotationResult.getTypeCode()) || list.isEmpty()) continue;
            for (String typeCode : list) {
                String[] typeRowDataArray = new String[ExpConsts.EXP_TYPE_ANNO_CSV_HEADER_CODES.size()];
                typeRowDataArray[0] = expAnnotationResult.getId();
                typeRowDataArray[1] = typeCode;
                typeCsvWriter.writeRecord(typeRowDataArray);
                if (++typeWriteRowCount < 5000) continue;
                typeCsvWriter.flush();
                typeWriteRowCount = 0;
            }
        }
        if (annoWriteRowCount > 0) {
            annoCsvWriter.flush();
        }
        if (relWriteRowCount > 0) {
            relCsvWriter.flush();
        }
        if (commentWriteRowCount > 0) {
            commentCsvWriter.flush();
        }
        if (typeWriteRowCount > 0) {
            typeCsvWriter.flush();
        }
    }

    private void writeHeadData(EntityDimData dwEntityDimData, List<EntityDimData> dimEntityDimData, CsvWriter annoCsvWriter, CsvWriter relCsvWriter, CsvWriter commentCsvWriter, CsvWriter typeCsvWriter) throws IOException {
        ArrayList<String> annoHeaderCode = new ArrayList<String>();
        annoHeaderCode.add(dwEntityDimData.getDimensionName());
        annoHeaderCode.add(PERIOD);
        for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
            annoHeaderCode.add(dimEntityDimDatum.getDimensionName());
        }
        annoHeaderCode.addAll(ExpConsts.EXP_ANNO_CSV_HEADER_CODES);
        ArrayList<String> relHeaderCode = new ArrayList<String>(ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES);
        ArrayList<String> commentHeaderCode = new ArrayList<String>(ExpConsts.EXP_COMMENT_ANNO_CSV_HEADER_CODES);
        ArrayList<String> typeHeaderCode = new ArrayList<String>(ExpConsts.EXP_TYPE_ANNO_CSV_HEADER_CODES);
        String[] annoCsvHeads = new String[annoHeaderCode.size()];
        annoHeaderCode.toArray(annoCsvHeads);
        annoCsvWriter.writeRecord(annoCsvHeads);
        annoCsvWriter.flush();
        String[] relCsvHeads = new String[relHeaderCode.size()];
        relHeaderCode.toArray(relCsvHeads);
        relCsvWriter.writeRecord(relCsvHeads);
        relCsvWriter.flush();
        String[] commentCsvHeads = new String[commentHeaderCode.size()];
        commentHeaderCode.toArray(commentCsvHeads);
        commentCsvWriter.writeRecord(commentCsvHeads);
        commentCsvWriter.flush();
        String[] typeCsvHeads = new String[typeHeaderCode.size()];
        typeHeaderCode.toArray(typeCsvHeads);
        typeCsvWriter.writeRecord(typeCsvHeads);
        typeCsvWriter.flush();
    }

    @NotNull
    private File packIntoZip(FormSchemeDefine formScheme, String rootPath) throws IOException {
        File zip;
        block48: {
            boolean delete;
            String destZipFile = rootPath + formScheme.getTitle() + ".zip";
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
                    if (file.getName().contains(".zip")) continue;
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

    private void buildMappingData(ParamsMapping paramsMapping, Map<String, FormMappingMessage> formMappings, Map<String, RegionMappingMessage> regionMappings, Map<String, LinkMappingMessage> linkMappings, List<ExpAnnotationResult> expAnnotationInfos, EntityDimData dwEntityDimData, List<EntityDimData> dimEntityDimData) {
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
            HashSet dataTimeValues = new HashSet();
            HashMap<String, Set> hashMap = new HashMap<String, Set>();
            for (ExpAnnotationResult expAnnotationInfo : expAnnotationInfos) {
                Map<String, String> dimNameValue = expAnnotationInfo.getDimNameValue();
                if (tryOrgCode) {
                    dwValues.add(dimNameValue.get(dwEntityDimData.getDimensionName()));
                }
                if (tryPeriod) {
                    dataTimeValues.add(dimNameValue.get(PERIOD));
                }
                for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                    String entityId = dimEntityDimDatum.getEntityId();
                    String dimensionName = dimEntityDimDatum.getDimensionName();
                    if (!((Boolean)entityIdTryBaseDataMap.get(entityId)).booleanValue()) continue;
                    Set dimValues = hashMap.computeIfAbsent(dimensionName, k -> new HashSet());
                    dimValues.add(dimNameValue.get(dimensionName));
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
                Set dimValues = (Set)hashMap.get(dimensionName);
                if (!((Boolean)entityIdTryBaseDataMap.get(entityId)).booleanValue() || null == dimValues || dimValues.isEmpty()) continue;
                Map originBaseData = paramsMapping.getOriginBaseData(entityId, new ArrayList(dimValues));
                dimsValueMappingValue.put(dimensionName, originBaseData);
            }
        }
        for (ExpAnnotationResult expAnnotationInfo : expAnnotationInfos) {
            Map<String, String> dimNameValueMap = expAnnotationInfo.getDimNameValue();
            HashMap<String, String> mappingDimNameValueMap = new HashMap<String, String>(dimNameValueMap);
            for (Map.Entry entry : dimNameValueMap.entrySet()) {
                String dimName = (String)entry.getKey();
                String dimValue = (String)entry.getValue();
                if (dimName.equals(dwEntityDimData.getDimensionName()) && null != dwValueMappingValue) {
                    mappingDimNameValueMap.put(dimName, (String)dwValueMappingValue.get(dimValue));
                    continue;
                }
                if (PERIOD.equals(dimName) && null != dataTimeValueMappingValue) {
                    mappingDimNameValueMap.put(dimName, (String)dataTimeValueMappingValue.get(dimValue));
                    continue;
                }
                if (dimName.equals(dwEntityDimData.getDimensionName()) || PERIOD.equals(dimName) || null == dimsValueMappingValue.get(dimName)) continue;
                mappingDimNameValueMap.put(dimName, (String)((Map)dimsValueMappingValue.get(dimName)).get(dimValue));
            }
            expAnnotationInfo.setDimNameValue(mappingDimNameValueMap);
            List<ExpAnnotationRel> relations = expAnnotationInfo.getRelations();
            if (null == relations || relations.isEmpty()) continue;
            for (ExpAnnotationRel relation : relations) {
                String formKey = relation.getFormKey();
                String regionKey = relation.getRegionKey();
                String dataLinkKey = relation.getDataLinkKey();
                FormMappingMessage formMappingMessage = formMappings.get(formKey);
                relation.setFormKey(formMappingMessage.getFormKey());
                relation.setFormCode(formMappingMessage.getFormCode());
                relation.setFormTitle(formMappingMessage.getFormTitle());
                RegionMappingMessage regionMappingMessage = regionMappings.get(regionKey);
                relation.setRegionKey(regionMappingMessage.getRegionKey());
                relation.setRegionLeft(regionMappingMessage.getRegionLeft());
                relation.setRegionRight(regionMappingMessage.getRegionRight());
                relation.setRegionTop(regionMappingMessage.getRegionTop());
                relation.setRegionBottom(regionMappingMessage.getRegionBottom());
                relation.setType(regionMappingMessage.getType());
                LinkMappingMessage linkMappingMessage = linkMappings.get(dataLinkKey);
                relation.setDataLinkKey(linkMappingMessage.getLinkKey());
                relation.setPosX(linkMappingMessage.getPosX());
                relation.setPosY(linkMappingMessage.getPosY());
                relation.setFieldKey("");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Result<Void> importAnnotation(File zipFile, ImpAnnotationFileParam param) {
        Result result;
        String rootPath = ExtConstants.UPLOADDIR + File.separator + "annotation" + LocalDate.now() + File.separator + UUID.randomUUID().toString() + File.separator;
        FileUtil.unZip(zipFile, rootPath);
        List<File> files = FileUtil.getFiles(rootPath, null);
        if (null == files) {
            return Result.failed((String)"\u5bfc\u5165\u6279\u6ce8\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1azip\u5305\u4e2d\u65e0\u6587\u4ef6");
        }
        File jsonFile = null;
        File annoCsvFile = null;
        File relCsvFile = null;
        File commentCsvFile = null;
        File typeCsvFile = null;
        for (File file : files) {
            if (file.getName().endsWith(".json")) {
                jsonFile = file;
                continue;
            }
            if (file.getName().endsWith("-anno.csv")) {
                annoCsvFile = file;
                continue;
            }
            if (file.getName().endsWith("-rel.csv")) {
                relCsvFile = file;
                continue;
            }
            if (file.getName().endsWith("-comment.csv")) {
                commentCsvFile = file;
                continue;
            }
            if (!file.getName().endsWith("-type.csv")) continue;
            typeCsvFile = file;
        }
        if (null == jsonFile || null == annoCsvFile || null == relCsvFile || null == commentCsvFile || null == typeCsvFile) {
            return Result.failed((String)"\u5bfc\u5165\u6279\u6ce8\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1azip\u5305\u4e2d\u7f3a\u5c11\u6587\u4ef6");
        }
        CsvReader annoReader = null;
        CsvReader relReader = null;
        CsvReader commentReader = null;
        CsvReader typeReader = null;
        try {
            Result result2;
            ObjectMapper mapper = new ObjectMapper();
            ExpAnnotationJsonData expAnnotationJsonData = (ExpAnnotationJsonData)mapper.readValue(jsonFile, ExpAnnotationJsonData.class);
            TaskMappingMessage taskInfo = expAnnotationJsonData.getTaskMappingMessage();
            FormSchemeMappingMessage formSchemeInfo = expAnnotationJsonData.getFormSchemeMappingMessage();
            List<FormMappingMessage> formInfos = expAnnotationJsonData.getFormMappingMessges();
            IAnnotationMappingService annotationMappingService = this.annotationMappingProvider.getIAnnotationMappingService();
            FormSchemeMappingMessage formSchemeMapping = annotationMappingService.getFormSchemeMapping(taskInfo, formSchemeInfo);
            Map<String, FormMappingMessage> formMappings = annotationMappingService.getFormMapping(taskInfo, formSchemeInfo, formInfos);
            HashMap<String, RegionMappingMessage> regionMappings = new HashMap<String, RegionMappingMessage>();
            HashMap<String, LinkMappingMessage> linkMappings = new HashMap<String, LinkMappingMessage>();
            for (FormMappingMessage formInfo : formInfos) {
                FormDefine formDefine = this.runTimeViewController.queryFormById(formInfo.getFormKey());
                FormMappingMessage formParam = new FormMappingMessage(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle());
                List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formInfo.getFormKey());
                ArrayList<RegionMappingMessage> regionParams = new ArrayList<RegionMappingMessage>();
                for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                    RegionMappingMessage regionParam = new RegionMappingMessage(dataRegionDefine.getKey(), dataRegionDefine.getRegionLeft(), dataRegionDefine.getRegionRight(), dataRegionDefine.getRegionTop(), dataRegionDefine.getRegionBottom(), dataRegionDefine.getRegionKind().getValue());
                    regionParams.add(regionParam);
                    List dataLinkDefines = this.runTimeViewController.getAllLinksInRegion(dataRegionDefine.getKey());
                    ArrayList<LinkMappingMessage> linkParams = new ArrayList<LinkMappingMessage>();
                    for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                        linkParams.add(new LinkMappingMessage(dataLinkDefine.getKey(), dataLinkDefine.getPosX(), dataLinkDefine.getPosY()));
                    }
                    Map<String, LinkMappingMessage> linkMapping = annotationMappingService.getLinkMapping(taskInfo, formSchemeInfo, formParam, regionParam, linkParams);
                    if (null == linkMapping) continue;
                    linkMappings.putAll(linkMapping);
                }
                Map<String, RegionMappingMessage> regionMapping = annotationMappingService.getRegionMapping(taskInfo, formSchemeInfo, formParam, regionParams);
                regionMappings.putAll(regionMapping);
            }
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeMapping.getFormSchemeKey());
            TableModelDefine annotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
            TableModelDefine dataLinkFieldTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANDF_" + formScheme.getFormSchemeCode());
            TableModelDefine annotationCommentTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
            TableModelDefine annotationTypeTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode());
            if (null == annotationTable || null == dataLinkFieldTable || null == annotationCommentTable || null == annotationTypeTable) {
                logger.error("\u6279\u6ce8\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1");
                result2 = Result.failed((String)"\u5bfc\u5165\u6279\u6ce8\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a\u6279\u6ce8\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1");
                return result2;
            }
            this.clearAnnotation(param.getDims(), formSchemeMapping.getFormSchemeKey(), formMappings, annotationTable, dataLinkFieldTable, annotationCommentTable, annotationTypeTable);
            annoReader = this.insertAnnoData(param, annoCsvFile, formSchemeMapping, formScheme, annotationTable);
            relReader = this.insertRelData(relCsvFile, formMappings, regionMappings, linkMappings, dataLinkFieldTable);
            commentReader = this.insertCommentData(commentCsvFile, annotationCommentTable);
            typeReader = this.insertTypeData(typeCsvFile, annotationTypeTable);
            result2 = Result.succeed(null);
            this.finalClose(rootPath, annoReader, relReader, commentReader, typeReader);
            return result2;
        }
        catch (IOException e) {
            logger.error("\u5bfc\u5165\u6279\u6ce8\u5931\u8d25", e);
            result = Result.failed((String)("\u5bfc\u5165\u6279\u6ce8\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()));
            return result;
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u6279\u6ce8\u5931\u8d25\uff0c\u6e05\u9664\u6216\u63d2\u5165\u6570\u636e\u5931\u8d25", e);
            result = Result.failed((String)("\u5bfc\u5165\u6279\u6ce8\u5931\u8d25\uff0c\u6e05\u9664\u6216\u63d2\u5165\u6570\u636e\u5931\u8d25\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()));
            return result;
        }
        finally {
            this.finalClose(rootPath, annoReader, relReader, commentReader, typeReader);
        }
    }

    private void finalClose(String rootPath, CsvReader annoReader, CsvReader relReader, CsvReader commentReader, CsvReader typeReader) {
        if (null != annoReader) {
            annoReader.close();
        }
        if (null != relReader) {
            relReader.close();
        }
        if (null != commentReader) {
            commentReader.close();
        }
        if (null != typeReader) {
            typeReader.close();
        }
        FileUtil.deleteFiles(rootPath);
    }

    private CsvReader insertTypeData(File typeCsvFile, TableModelDefine annotationTypeTable) throws Exception {
        CsvReader typeReader = new CsvReader(typeCsvFile.getAbsolutePath(), ',', StandardCharsets.UTF_8);
        ArrayList<String> headerCodes = new ArrayList<String>();
        if (typeReader.readHeaders()) {
            for (int i = 0; i < typeReader.getHeaderCount(); ++i) {
                String headerCode = typeReader.getHeader(i);
                headerCodes.add(headerCode);
            }
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List typeFields = this.dataModelService.getColumnModelDefinesByTable(annotationTypeTable.getID());
        for (ColumnModelDefine typeField : typeFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(typeField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
        List columns = queryModel.getColumns();
        int readRowCount = 0;
        ArrayList<ImpTypeInfo> impTypeInfos = new ArrayList<ImpTypeInfo>();
        while (typeReader.readRecord()) {
            ImpTypeInfo impTypeInfo = this.buildTypeInfo(typeReader, headerCodes);
            impTypeInfos.add(impTypeInfo);
            if (++readRowCount < 5000) continue;
            this.insertTypeData(context, iNvwaDataUpdator, columns, impTypeInfos);
            readRowCount = 0;
            impTypeInfos.clear();
        }
        if (readRowCount > 0) {
            this.insertTypeData(context, iNvwaDataUpdator, columns, impTypeInfos);
        }
        return typeReader;
    }

    private void insertTypeData(DataAccessContext context, INvwaDataUpdator iNvwaDataUpdator, List<NvwaQueryColumn> columns, List<ImpTypeInfo> impTypeInfos) throws Exception {
        for (ImpTypeInfo impTypeInfo : impTypeInfos) {
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            block9: for (int i = 0; i < columns.size(); ++i) {
                switch (columns.get(i).getColumnModel().getCode()) {
                    case "TYPE_FMCEAN_ID": {
                        iNvwaDataRow.setValue(i, (Object)impTypeInfo.getAnnoId());
                        continue block9;
                    }
                    case "TYPE_CODE": {
                        iNvwaDataRow.setValue(i, (Object)impTypeInfo.getTypeCode());
                        continue block9;
                    }
                }
            }
        }
        iNvwaDataUpdator.commitChanges(context);
    }

    private ImpTypeInfo buildTypeInfo(CsvReader reader, List<String> headerCodes) throws IOException {
        ImpTypeInfo result = new ImpTypeInfo();
        for (String headerCode : headerCodes) {
            String data = reader.get(headerCode);
            if (ExpConsts.EXP_TYPE_ANNO_CSV_HEADER_CODES.get(0).equals(headerCode)) {
                result.setAnnoId(data);
                continue;
            }
            if (!ExpConsts.EXP_TYPE_ANNO_CSV_HEADER_CODES.get(1).equals(headerCode)) continue;
            result.setTypeCode(data);
        }
        return result;
    }

    private CsvReader insertCommentData(File commentCsvFile, TableModelDefine annotationCommentTable) throws Exception {
        CsvReader commentReader = new CsvReader(commentCsvFile.getAbsolutePath(), ',', StandardCharsets.UTF_8);
        ArrayList<String> headerCodes = new ArrayList<String>();
        if (commentReader.readHeaders()) {
            for (int i = 0; i < commentReader.getHeaderCount(); ++i) {
                String headerCode = commentReader.getHeader(i);
                headerCodes.add(headerCode);
            }
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List commentFields = this.dataModelService.getColumnModelDefinesByTable(annotationCommentTable.getID());
        for (ColumnModelDefine commentField : commentFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(commentField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
        List columns = queryModel.getColumns();
        int readRowCount = 0;
        ArrayList<ExpAnnotationComment> impCommentInfos = new ArrayList<ExpAnnotationComment>();
        while (commentReader.readRecord()) {
            ExpAnnotationComment impRelInfo = this.buildCommentInfo(commentReader, headerCodes);
            impCommentInfos.add(impRelInfo);
            if (++readRowCount < 5000) continue;
            this.insertCommentData(context, iNvwaDataUpdator, columns, impCommentInfos);
            readRowCount = 0;
            impCommentInfos.clear();
        }
        if (readRowCount > 0) {
            this.insertCommentData(context, iNvwaDataUpdator, columns, impCommentInfos);
        }
        return commentReader;
    }

    private void insertCommentData(DataAccessContext context, INvwaDataUpdator iNvwaDataUpdator, List<NvwaQueryColumn> columns, List<ExpAnnotationComment> impCommentInfos) throws Exception {
        for (ExpAnnotationComment impCommentInfo : impCommentInfos) {
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            block17: for (int i = 0; i < columns.size(); ++i) {
                switch (columns.get(i).getColumnModel().getCode()) {
                    case "FMCEANCO_ID": {
                        iNvwaDataRow.setValue(i, (Object)impCommentInfo.getId());
                        continue block17;
                    }
                    case "FMCEANCO_FMCEAN_ID": {
                        iNvwaDataRow.setValue(i, (Object)impCommentInfo.getAnnoId());
                        continue block17;
                    }
                    case "FMCEANCO_CONTENT": {
                        iNvwaDataRow.setValue(i, (Object)impCommentInfo.getContent());
                        continue block17;
                    }
                    case "FMCEANCO_USER_ID": {
                        iNvwaDataRow.setValue(i, (Object)impCommentInfo.getUserName());
                        continue block17;
                    }
                    case "FMCEANCO_REPY_USER_ID": {
                        iNvwaDataRow.setValue(i, (Object)impCommentInfo.getRepyUserName());
                        continue block17;
                    }
                    case "FMCEANCO_UPDATE_DATE": {
                        iNvwaDataRow.setValue(i, (Object)new Date(impCommentInfo.getDate()));
                        continue block17;
                    }
                }
            }
        }
        iNvwaDataUpdator.commitChanges(context);
    }

    private ExpAnnotationComment buildCommentInfo(CsvReader reader, List<String> headerCodes) throws IOException {
        ExpAnnotationComment result = new ExpAnnotationComment();
        for (String headerCode : headerCodes) {
            String data = reader.get(headerCode);
            if (ExpConsts.EXP_COMMENT_ANNO_CSV_HEADER_CODES.get(0).equals(headerCode)) {
                result.setId(data);
                continue;
            }
            if (ExpConsts.EXP_COMMENT_ANNO_CSV_HEADER_CODES.get(1).equals(headerCode)) {
                result.setAnnoId(data);
                continue;
            }
            if (ExpConsts.EXP_COMMENT_ANNO_CSV_HEADER_CODES.get(2).equals(headerCode)) {
                result.setContent(data);
                continue;
            }
            if (ExpConsts.EXP_COMMENT_ANNO_CSV_HEADER_CODES.get(3).equals(headerCode)) {
                result.setUserName(data);
                continue;
            }
            if (ExpConsts.EXP_COMMENT_ANNO_CSV_HEADER_CODES.get(4).equals(headerCode)) {
                result.setRepyUserName(data);
                continue;
            }
            if (!ExpConsts.EXP_COMMENT_ANNO_CSV_HEADER_CODES.get(5).equals(headerCode)) continue;
            result.setDate(Long.parseLong(data));
        }
        return result;
    }

    private CsvReader insertRelData(File relCsvFile, Map<String, FormMappingMessage> formMappings, Map<String, RegionMappingMessage> regionMappings, Map<String, LinkMappingMessage> linkMappings, TableModelDefine dataLinkFieldTable) throws Exception {
        CsvReader relReader = new CsvReader(relCsvFile.getAbsolutePath(), ',', StandardCharsets.UTF_8);
        ArrayList<String> headerCodes = new ArrayList<String>();
        if (relReader.readHeaders()) {
            for (int i = 0; i < relReader.getHeaderCount(); ++i) {
                String headerCode = relReader.getHeader(i);
                headerCodes.add(headerCode);
            }
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List relFields = this.dataModelService.getColumnModelDefinesByTable(dataLinkFieldTable.getID());
        for (ColumnModelDefine relField : relFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(relField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
        List columns = queryModel.getColumns();
        int readRowCount = 0;
        ArrayList<ImpRelInfo> impRelInfos = new ArrayList<ImpRelInfo>();
        while (relReader.readRecord()) {
            ImpRelInfo impRelInfo = this.buildRelInfo(relReader, headerCodes);
            impRelInfos.add(impRelInfo);
            if (++readRowCount < 5000) continue;
            this.buildMappingData(formMappings, regionMappings, linkMappings, impRelInfos);
            this.insertRelData(context, iNvwaDataUpdator, columns, impRelInfos);
            readRowCount = 0;
            impRelInfos.clear();
        }
        if (readRowCount > 0) {
            this.buildMappingData(formMappings, regionMappings, linkMappings, impRelInfos);
            this.insertRelData(context, iNvwaDataUpdator, columns, impRelInfos);
        }
        return relReader;
    }

    private void insertRelData(DataAccessContext context, INvwaDataUpdator iNvwaDataUpdator, List<NvwaQueryColumn> columns, List<ImpRelInfo> impRelInfos) throws Exception {
        for (ImpRelInfo impRelInfo : impRelInfos) {
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            block21: for (int i = 0; i < columns.size(); ++i) {
                switch (columns.get(i).getColumnModel().getCode()) {
                    case "FMCEANDF_ID": {
                        iNvwaDataRow.setValue(i, (Object)impRelInfo.getId());
                        continue block21;
                    }
                    case "FMCEANDF_FMCEAN_ID": {
                        iNvwaDataRow.setValue(i, (Object)impRelInfo.getAnnoId());
                        continue block21;
                    }
                    case "FORM_KEY": {
                        iNvwaDataRow.setValue(i, (Object)impRelInfo.getFormKey());
                        continue block21;
                    }
                    case "REGION_KEY": {
                        iNvwaDataRow.setValue(i, (Object)impRelInfo.getRegionKey());
                        continue block21;
                    }
                    case "DATALINK_KEY": {
                        iNvwaDataRow.setValue(i, (Object)impRelInfo.getDataLinkKey());
                        continue block21;
                    }
                    case "FIELD_KEY": {
                        iNvwaDataRow.setValue(i, (Object)impRelInfo.getFieldKey());
                        continue block21;
                    }
                    case "ROW_ID": {
                        iNvwaDataRow.setValue(i, (Object)impRelInfo.getRowId());
                        continue block21;
                    }
                    case "FMCEANDF_SHOW": {
                        iNvwaDataRow.setValue(i, (Object)impRelInfo.getShow());
                        continue block21;
                    }
                }
            }
        }
        iNvwaDataUpdator.commitChanges(context);
    }

    private void buildMappingData(Map<String, FormMappingMessage> formMappings, Map<String, RegionMappingMessage> regionMappings, Map<String, LinkMappingMessage> linkMappings, List<ImpRelInfo> impAnnotationInfos) {
        for (ImpRelInfo impRelInfo : impAnnotationInfos) {
            String formKey = impRelInfo.getFormKey();
            String regionKey = impRelInfo.getRegionKey();
            String dataLinkKey = impRelInfo.getDataLinkKey();
            FormMappingMessage formMappingMessage = formMappings.get(formKey);
            impRelInfo.setFormKey(formMappingMessage.getFormKey());
            RegionMappingMessage regionMappingMessage = regionMappings.get(regionKey);
            impRelInfo.setRegionKey(regionMappingMessage.getRegionKey());
            LinkMappingMessage linkMappingMessage = linkMappings.get(dataLinkKey);
            impRelInfo.setDataLinkKey(linkMappingMessage.getLinkKey());
        }
    }

    private ImpRelInfo buildRelInfo(CsvReader reader, List<String> headerCodes) throws IOException {
        ImpRelInfo result = new ImpRelInfo();
        for (String headerCode : headerCodes) {
            String data = reader.get(headerCode);
            if (ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.get(0).equals(headerCode)) {
                result.setId(data);
                continue;
            }
            if (ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.get(1).equals(headerCode)) {
                result.setAnnoId(data);
                continue;
            }
            if (ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.get(2).equals(headerCode)) {
                result.setFormKey(data);
                continue;
            }
            if (ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.get(5).equals(headerCode)) {
                result.setRegionKey(data);
                continue;
            }
            if (ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.get(11).equals(headerCode)) {
                result.setDataLinkKey(data);
                continue;
            }
            if (ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.get(14).equals(headerCode)) {
                result.setFieldKey(data);
                continue;
            }
            if (ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.get(15).equals(headerCode)) {
                result.setRowId(data);
                continue;
            }
            if (!ExpConsts.EXP_REL_ANNO_CSV_HEADER_CODES.get(16).equals(headerCode)) continue;
            result.setShow(data);
        }
        return result;
    }

    private CsvReader insertAnnoData(ImpAnnotationFileParam param, File annoCsvFile, FormSchemeMappingMessage formSchemeMapping, FormSchemeDefine formScheme, TableModelDefine annotationTable) throws Exception {
        EntityDimData dwEntityDimData = this.dataAccesslUtil.getDwEntityDimData(formSchemeMapping.getFormSchemeKey());
        List dimEntityDimData = this.dataAccesslUtil.getDimEntityDimData(formSchemeMapping.getFormSchemeKey());
        List<String> dimNames = dimEntityDimData.stream().map(EntityDimData::getDimensionName).collect(Collectors.toList());
        CsvReader annoReader = new CsvReader(annoCsvFile.getAbsolutePath(), ',', StandardCharsets.UTF_8);
        ArrayList<String> headerCodes = new ArrayList<String>();
        if (annoReader.readHeaders()) {
            for (int i = 0; i < annoReader.getHeaderCount(); ++i) {
                String headerCode = annoReader.getHeader(i);
                headerCodes.add(headerCode);
            }
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List annoFields = this.dataModelService.getColumnModelDefinesByTable(annotationTable.getID());
        for (ColumnModelDefine annoField : annoFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(annoField));
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.nvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
        List columns = queryModel.getColumns();
        int readRowCount = 0;
        ArrayList<ImpAnnotationInfo> impAnnotationInfos = new ArrayList<ImpAnnotationInfo>();
        while (annoReader.readRecord()) {
            ImpAnnotationInfo impAnnotationInfo = this.buildAnnotationInfo(annoReader, dwEntityDimData, dimNames, headerCodes);
            impAnnotationInfos.add(impAnnotationInfo);
            if (++readRowCount < 5000) continue;
            this.buildMappingData(param.getMapping(), impAnnotationInfos, dwEntityDimData, dimEntityDimData);
            this.insertAnnoData(dimensionChanger, context, iNvwaDataUpdator, columns, impAnnotationInfos);
            readRowCount = 0;
            impAnnotationInfos.clear();
        }
        if (readRowCount > 0) {
            this.buildMappingData(param.getMapping(), impAnnotationInfos, dwEntityDimData, dimEntityDimData);
            this.insertAnnoData(dimensionChanger, context, iNvwaDataUpdator, columns, impAnnotationInfos);
        }
        return annoReader;
    }

    private void insertAnnoData(DimensionChanger dimensionChanger, DataAccessContext context, INvwaDataUpdator iNvwaDataUpdator, List<NvwaQueryColumn> columns, List<ImpAnnotationInfo> impAnnotationInfos) throws Exception {
        for (ImpAnnotationInfo impAnnotationInfo : impAnnotationInfos) {
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            block13: for (int i = 0; i < columns.size(); ++i) {
                switch (columns.get(i).getColumnModel().getCode()) {
                    case "FMCEAN_ID": {
                        iNvwaDataRow.setValue(i, (Object)impAnnotationInfo.getId());
                        continue block13;
                    }
                    case "FMCEAN_CONTENT": {
                        iNvwaDataRow.setValue(i, (Object)impAnnotationInfo.getContent());
                        continue block13;
                    }
                    case "FMCEAN_USER_ID": {
                        iNvwaDataRow.setValue(i, (Object)impAnnotationInfo.getUserName());
                        continue block13;
                    }
                    case "FMCEAN_UPDATE_DATE": {
                        iNvwaDataRow.setValue(i, (Object)new Date(impAnnotationInfo.getDate()));
                        continue block13;
                    }
                    default: {
                        String dimensionName = dimensionChanger.getDimensionName(columns.get(i).getColumnModel());
                        if (null == impAnnotationInfo.getDimNameValue().get(dimensionName)) continue block13;
                        iNvwaDataRow.setValue(i, (Object)impAnnotationInfo.getDimNameValue().get(dimensionName));
                    }
                }
            }
        }
        iNvwaDataUpdator.commitChanges(context);
    }

    private void buildMappingData(ParamsMapping paramsMapping, List<ImpAnnotationInfo> impAnnotationInfos, EntityDimData dwEntityDimData, List<EntityDimData> dimEntityDimData) {
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
            for (ImpAnnotationInfo impAnnotationInfo : impAnnotationInfos) {
                Map<String, String> dimNameValue = impAnnotationInfo.getDimNameValue();
                if (tryOrgCode) {
                    dwValues.add(dimNameValue.get(dwEntityDimData.getDimensionName()));
                }
                if (tryPeriod) {
                    dataTimeValues.add(dimNameValue.get(PERIOD));
                }
                for (EntityDimData dimEntityDimDatum : dimEntityDimData) {
                    String entityId = dimEntityDimDatum.getEntityId();
                    String dimensionName = dimEntityDimDatum.getDimensionName();
                    if (!((Boolean)entityIdTryBaseDataMap.get(entityId)).booleanValue()) continue;
                    Set dimValues = dimNameValues.computeIfAbsent(dimensionName, k -> new HashSet());
                    dimValues.add(dimNameValue.get(dimensionName));
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
        for (ImpAnnotationInfo impAnnotationInfo : impAnnotationInfos) {
            Map<String, String> dimNameValueMap = impAnnotationInfo.getDimNameValue();
            HashMap<String, String> mappingDimNameValueMap = new HashMap<String, String>(dimNameValueMap);
            for (Map.Entry<String, String> dimNameValue : dimNameValueMap.entrySet()) {
                String dimName = dimNameValue.getKey();
                String dimValue = dimNameValue.getValue();
                if (dimName.equals(dwEntityDimData.getDimensionName()) && null != dwValueMappingValue) {
                    mappingDimNameValueMap.put(dimName, (String)dwValueMappingValue.get(dimValue));
                    continue;
                }
                if (PERIOD.equals(dimName) && null != dataTimeValueMappingValue) {
                    mappingDimNameValueMap.put(dimName, (String)dataTimeValueMappingValue.get(dimValue));
                    continue;
                }
                if (dimName.equals(dwEntityDimData.getDimensionName()) || PERIOD.equals(dimName) || null == dimsValueMappingValue.get(dimName)) continue;
                mappingDimNameValueMap.put(dimName, (String)((Map)dimsValueMappingValue.get(dimName)).get(dimValue));
            }
            impAnnotationInfo.setDimNameValue(mappingDimNameValueMap);
        }
    }

    private ImpAnnotationInfo buildAnnotationInfo(CsvReader reader, EntityDimData dwEntityDimData, List<String> dimNames, List<String> headerCodes) throws IOException {
        ImpAnnotationInfo result = new ImpAnnotationInfo();
        HashMap<String, String> dimNameValue = new HashMap<String, String>();
        for (String headerCode : headerCodes) {
            String data = reader.get(headerCode);
            if (headerCode.equals(dwEntityDimData.getDimensionName()) || PERIOD.equals(headerCode) || dimNames.contains(headerCode)) {
                dimNameValue.put(headerCode, data);
                continue;
            }
            if (ExpConsts.EXP_ANNO_CSV_HEADER_CODES.get(0).equals(headerCode)) {
                result.setId(data);
                continue;
            }
            if (ExpConsts.EXP_ANNO_CSV_HEADER_CODES.get(1).equals(headerCode)) {
                result.setContent(data);
                continue;
            }
            if (ExpConsts.EXP_ANNO_CSV_HEADER_CODES.get(2).equals(headerCode)) {
                result.setUserName(data);
                continue;
            }
            if (!ExpConsts.EXP_ANNO_CSV_HEADER_CODES.get(3).equals(headerCode)) continue;
            result.setDate(Long.parseLong(data));
        }
        result.setDimNameValue(dimNameValue);
        return result;
    }

    private void clearAnnotation(DimensionCollection dims, String formSchemeKey, Map<String, FormMappingMessage> formMappings, TableModelDefine annotationTable, TableModelDefine dataLinkFieldTable, TableModelDefine annotationCommentTable, TableModelDefine annotationTypeTable) throws Exception {
        ExpAnnotationParam expAnnotationParam = new ExpAnnotationParam();
        expAnnotationParam.setDims(dims);
        expAnnotationParam.setFormSchemeKey(formSchemeKey);
        List<String> formKeys = formMappings.values().stream().map(FormMappingMessage::getFormKey).collect(Collectors.toList());
        expAnnotationParam.setFormKeys(formKeys);
        List<ExpAnnotationResult> expAnnotationResults = this.annotationService.queryAnnotation(expAnnotationParam);
        if (null == expAnnotationResults || expAnnotationResults.isEmpty()) {
            return;
        }
        List deleteIds = expAnnotationResults.stream().map(ExpAnnotationResult::getId).collect(Collectors.toList());
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel annotationQueryModel = new NvwaQueryModel();
        List annotationFields = this.dataModelService.getColumnModelDefinesByTable(annotationTable.getID());
        for (ColumnModelDefine filed : annotationFields) {
            annotationQueryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (!"FMCEAN_ID".equals(filed.getCode())) continue;
            annotationQueryModel.getColumnFilters().put(filed, deleteIds);
        }
        INvwaUpdatableDataAccess annotationUpdatable = this.nvwaDataAccessProvider.createUpdatableDataAccess(annotationQueryModel);
        INvwaDataUpdator annotationUpdator = annotationUpdatable.openForUpdate(context);
        annotationUpdator.deleteAll();
        annotationUpdator.commitChanges(context);
        NvwaQueryModel dataLinkQueryModel = new NvwaQueryModel();
        List dataLinkFields = this.dataModelService.getColumnModelDefinesByTable(dataLinkFieldTable.getID());
        for (ColumnModelDefine filed : dataLinkFields) {
            dataLinkQueryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (!"FMCEANDF_FMCEAN_ID".equals(filed.getCode())) continue;
            dataLinkQueryModel.getColumnFilters().put(filed, deleteIds);
        }
        INvwaUpdatableDataAccess dataLinkUpdatable = this.nvwaDataAccessProvider.createUpdatableDataAccess(dataLinkQueryModel);
        INvwaDataUpdator dataLinkUpdator = dataLinkUpdatable.openForUpdate(context);
        dataLinkUpdator.deleteAll();
        dataLinkUpdator.commitChanges(context);
        NvwaQueryModel commentQueryModel = new NvwaQueryModel();
        List commentFields = this.dataModelService.getColumnModelDefinesByTable(annotationCommentTable.getID());
        for (ColumnModelDefine filed : commentFields) {
            commentQueryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (!"FMCEANCO_FMCEAN_ID".equals(filed.getCode())) continue;
            commentQueryModel.getColumnFilters().put(filed, deleteIds);
        }
        INvwaUpdatableDataAccess commentUpdatable = this.nvwaDataAccessProvider.createUpdatableDataAccess(commentQueryModel);
        INvwaDataUpdator commentUpdator = commentUpdatable.openForUpdate(context);
        commentUpdator.deleteAll();
        commentUpdator.commitChanges(context);
        NvwaQueryModel typeQueryModel = new NvwaQueryModel();
        List typeFields = this.dataModelService.getColumnModelDefinesByTable(annotationTypeTable.getID());
        for (ColumnModelDefine filed : typeFields) {
            typeQueryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (!"TYPE_FMCEAN_ID".equals(filed.getCode())) continue;
            typeQueryModel.getColumnFilters().put(filed, deleteIds);
        }
        INvwaUpdatableDataAccess typeUpdatable = this.nvwaDataAccessProvider.createUpdatableDataAccess(typeQueryModel);
        INvwaDataUpdator typeUpdator = typeUpdatable.openForUpdate(context);
        typeUpdator.deleteAll();
        typeUpdator.commitChanges(context);
    }
}

