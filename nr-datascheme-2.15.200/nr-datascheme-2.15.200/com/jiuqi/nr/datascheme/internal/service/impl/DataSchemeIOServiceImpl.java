/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeIOService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.service.IEntityIOService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.formtype.service.IFormTypeIOService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeIOService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeIOErrorEnum;
import com.jiuqi.nr.datascheme.common.io.JsonDataConverter;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.impl.DataSchemeEntityIOServiceImpl;
import com.jiuqi.nr.entity.service.IEntityIOService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formtype.service.IFormTypeIOService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DataSchemeIOServiceImpl
implements IDataSchemeIOService {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeIOServiceImpl.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityIOService entityIOService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataSchemeEntityIOServiceImpl dataSchemeEntityIOService;
    @Autowired(required=false)
    private IFormTypeIOService iFormTypeIOService;
    private static final String SUBFILE_DATA_VERSION = "1";
    private static final String SUBFILE_PATH_DATASCHEME_DIR = "\u6570\u636e\u65b9\u6848";
    private static final String SUBFILE_PATH_DATASCHEME = "datascheme.json";
    private static final String SUBFILE_PATH_DATADIM = "datadim.json";
    private static final String SUBFILE_PATH_DATAGROUP = "datagroup.json";
    private static final String SUBFILE_PATH_DATATABLE = "datatable.json";
    private static final String SUBFILE_PATH_DATAFIELD = "datafield.json";
    private static final String SUBFILE_PATH_ENTITY_DIR = "\u5b9e\u4f53";
    private static final String SUBFILE_PATH_ENTITY = "entity.zip";
    private static final String SUBFILE_PATH_ENTITYDATA = "entitydata.zip";
    private static final String SUBFILE_PATH_PERIOD_DIR = "\u65f6\u671f";
    private static final String SUBFILE_PATH_PERIOD = "period.zip";
    private static final String SUBFILE_PATH_PERIODDATA = "perioddata.zip";
    private static final String SUBFILE_PATH_FORMTYPE_DIR = "\u62a5\u8868\u7c7b\u578b";
    private static final String SUBFILE_PATH_FORMTYPE = "formtype.zip";
    private static final String TEMPFILE_PATH = "temp.data";
    @Autowired
    private IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;
    @Autowired
    private IDataDimDao<DesignDataDimDO> dataDimDao;
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;

    public void exportDataScheme(String dataSchemeKey, OutputStream outputStream) throws JQException {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        if (null == dataScheme) {
            return;
        }
        List<ZipUtils.ZipSubFile> zipSubFiles = this.getZipSubFiles(dataScheme);
        ZipUtils.zip(outputStream, zipSubFiles);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<ZipUtils.ZipSubFile> getZipSubFiles(DataScheme dataScheme) throws JQException {
        DataDimension[] dataSchemeDims = (DataDimension[])this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey()).stream().toArray(DataDimension[]::new);
        DataGroup[] allDataGroups = (DataGroup[])this.runtimeDataSchemeService.getAllDataGroup(dataScheme.getKey()).stream().toArray(DataGroup[]::new);
        DataTable[] allDataTables = (DataTable[])this.runtimeDataSchemeService.getAllDataTable(dataScheme.getKey()).stream().toArray(DataTable[]::new);
        DataField[] allDataFields = (DataField[])this.runtimeDataSchemeService.getAllDataField(dataScheme.getKey()).stream().toArray(DataField[]::new);
        ArrayList<ZipUtils.ZipSubFile> zipSubFiles = new ArrayList<ZipUtils.ZipSubFile>();
        ZipUtils.ZipSubFile zipSubFile = null;
        String subPath = "";
        try {
            subPath = SUBFILE_PATH_DATASCHEME_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_DATASCHEME;
            this.checkFilePath(subPath);
            zipSubFile = new ZipUtils.ZipSubFile(subPath, new JsonDataConverter<DataScheme>(dataScheme, SUBFILE_DATA_VERSION), this::moduleRegister);
            zipSubFiles.add(zipSubFile);
            subPath = SUBFILE_PATH_DATASCHEME_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_DATADIM;
            this.checkFilePath(subPath);
            zipSubFile = new ZipUtils.ZipSubFile(subPath, new JsonDataConverter<DataDimension[]>(dataSchemeDims, SUBFILE_DATA_VERSION), this::moduleRegister);
            zipSubFiles.add(zipSubFile);
            subPath = SUBFILE_PATH_DATASCHEME_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_DATAGROUP;
            this.checkFilePath(subPath);
            zipSubFile = new ZipUtils.ZipSubFile(subPath, new JsonDataConverter<DataGroup[]>(allDataGroups, SUBFILE_DATA_VERSION), this::moduleRegister);
            zipSubFiles.add(zipSubFile);
            subPath = SUBFILE_PATH_DATASCHEME_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_DATATABLE;
            this.checkFilePath(subPath);
            zipSubFile = new ZipUtils.ZipSubFile(subPath, new JsonDataConverter<DataTable[]>(allDataTables, SUBFILE_DATA_VERSION), this::moduleRegister);
            zipSubFiles.add(zipSubFile);
            subPath = SUBFILE_PATH_DATASCHEME_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_DATAFIELD;
            this.checkFilePath(subPath);
            zipSubFile = new ZipUtils.ZipSubFile(subPath, new JsonDataConverter<DataField[]>(allDataFields, SUBFILE_DATA_VERSION), this::moduleRegister);
            zipSubFiles.add(zipSubFile);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_101);
        }
        DataDimension periodDim = null;
        HashSet<String> entityKeys = new HashSet<String>();
        HashSet<String> entityDataKeys = new HashSet<String>();
        if (null != dataSchemeDims && 0 != dataSchemeDims.length) {
            for (DataDimension dataDimension : dataSchemeDims) {
                if (DimensionType.PERIOD.getValue() == dataDimension.getDimensionType().getValue()) {
                    periodDim = dataDimension;
                    continue;
                }
                entityKeys.add(dataDimension.getDimKey());
            }
        }
        if (null != allDataFields && 0 != allDataFields.length) {
            for (DataDimension dataDimension : allDataFields) {
                if (!StringUtils.hasLength(dataDimension.getRefDataEntityKey()) || DataFieldKind.PUBLIC_FIELD_DIM.getValue() == dataDimension.getDataFieldKind().getValue()) continue;
                entityKeys.add(dataDimension.getRefDataEntityKey());
                entityDataKeys.add(dataDimension.getRefDataEntityKey());
            }
        }
        String newTempDir = ZipUtils.newTempDir();
        this.checkFilePath(newTempDir);
        try {
            this.exportEntity(zipSubFiles, newTempDir, entityKeys);
            this.exportPeriod(zipSubFiles, newTempDir, periodDim);
            this.exportFormType(zipSubFiles, newTempDir, entityKeys);
        }
        finally {
            File tempDir = new File(newTempDir);
            if (tempDir.exists()) {
                tempDir.delete();
            }
        }
        return zipSubFiles;
    }

    private void exportFormType(List<ZipUtils.ZipSubFile> zipSubFiles, String newTempDir, Set<String> entityDataKeys) throws JQException {
        if (null == this.iFormTypeIOService) {
            return;
        }
        String tempFilePath = newTempDir + ZipUtils.SEPARATOR + TEMPFILE_PATH;
        this.checkFilePath(tempFilePath);
        try (FileOutputStream tempOS = new FileOutputStream(tempFilePath);){
            boolean isEmpty = this.iFormTypeIOService.exportFormTypeByEntity((OutputStream)tempOS, entityDataKeys.toArray(new String[0]));
            if (isEmpty) {
                return;
            }
            try (FileInputStream tempIS = new FileInputStream(tempFilePath);){
                String subFilePath = SUBFILE_PATH_FORMTYPE_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_FORMTYPE;
                this.checkFilePath(subFilePath);
                ZipUtils.ZipSubFile zipSubFile = new ZipUtils.ZipSubFile(subFilePath, tempIS);
                zipSubFiles.add(zipSubFile);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_102);
        }
    }

    private void closeIO(Closeable closeable) {
        if (null == closeable) {
            return;
        }
        try {
            closeable.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void exportPeriod(List<ZipUtils.ZipSubFile> zipSubFiles, String newTempDir, DataDimension periodDim) throws JQException {
        String tempFilePath = newTempDir + ZipUtils.SEPARATOR + TEMPFILE_PATH;
        this.checkFilePath(tempFilePath);
        if (null != periodDim) {
            ZipUtils.ZipSubFile zipSubFile;
            String subFilePath2;
            Throwable throwable;
            FileInputStream tempIS2;
            Throwable throwable2;
            FileOutputStream tempOS;
            try {
                tempOS = new FileOutputStream(tempFilePath);
                throwable2 = null;
                try {
                    this.periodEngineService.getPeriodIOService().exportPeriod(periodDim.getDimKey(), (OutputStream)tempOS);
                    tempIS2 = new FileInputStream(tempFilePath);
                    throwable = null;
                    try {
                        subFilePath2 = SUBFILE_PATH_PERIOD_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_PERIOD;
                        this.checkFilePath(subFilePath2);
                        zipSubFile = new ZipUtils.ZipSubFile(subFilePath2, tempIS2);
                        zipSubFiles.add(zipSubFile);
                    }
                    catch (Throwable subFilePath2) {
                        throwable = subFilePath2;
                        throw subFilePath2;
                    }
                    finally {
                        if (tempIS2 != null) {
                            if (throwable != null) {
                                try {
                                    tempIS2.close();
                                }
                                catch (Throwable subFilePath2) {
                                    throwable.addSuppressed(subFilePath2);
                                }
                            } else {
                                tempIS2.close();
                            }
                        }
                    }
                }
                catch (Throwable tempIS2) {
                    throwable2 = tempIS2;
                    throw tempIS2;
                }
                finally {
                    if (tempOS != null) {
                        if (throwable2 != null) {
                            try {
                                tempOS.close();
                            }
                            catch (Throwable tempIS2) {
                                throwable2.addSuppressed(tempIS2);
                            }
                        } else {
                            tempOS.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_102);
            }
            try {
                tempOS = new FileOutputStream(tempFilePath);
                throwable2 = null;
                try {
                    this.periodEngineService.getPeriodIOService().exportPeriodData(periodDim.getDimKey(), (OutputStream)tempOS);
                    tempIS2 = new FileInputStream(tempFilePath);
                    throwable = null;
                    try {
                        subFilePath2 = SUBFILE_PATH_PERIOD_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_PERIOD;
                        this.checkFilePath(subFilePath2);
                        zipSubFile = new ZipUtils.ZipSubFile(subFilePath2, tempIS2);
                        zipSubFiles.add(zipSubFile);
                    }
                    catch (Throwable throwable3) {
                        throwable = throwable3;
                        throw throwable3;
                    }
                    finally {
                        if (tempIS2 != null) {
                            if (throwable != null) {
                                try {
                                    tempIS2.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable.addSuppressed(throwable4);
                                }
                            } else {
                                tempIS2.close();
                            }
                        }
                    }
                }
                catch (Throwable throwable5) {
                    throwable2 = throwable5;
                    throw throwable5;
                }
                finally {
                    if (tempOS != null) {
                        if (throwable2 != null) {
                            try {
                                tempOS.close();
                            }
                            catch (Throwable throwable6) {
                                throwable2.addSuppressed(throwable6);
                            }
                        } else {
                            tempOS.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_102);
            }
        }
    }

    private void exportEntityData(List<ZipUtils.ZipSubFile> zipSubFiles, String newTempDir, Set<String> entityDataKeys) throws JQException {
        String tempFilePath = newTempDir + ZipUtils.SEPARATOR + TEMPFILE_PATH;
        this.checkFilePath(tempFilePath);
        if (!entityDataKeys.isEmpty()) {
            ArrayList<ZipUtils.ZipSubFile> entityDataSubFiles = new ArrayList<ZipUtils.ZipSubFile>();
            for (String entityDataKey : entityDataKeys) {
                try {
                    FileOutputStream tempOS = new FileOutputStream(tempFilePath);
                    Throwable throwable = null;
                    try {
                        this.entityIOService.exportEntityData(entityDataKey, (OutputStream)tempOS);
                        FileInputStream tempIS = new FileInputStream(tempFilePath);
                        Throwable throwable2 = null;
                        try {
                            this.checkFilePath(entityDataKey);
                            ZipUtils.ZipSubFile zipSubFile = new ZipUtils.ZipSubFile(entityDataKey, tempIS);
                            entityDataSubFiles.add(zipSubFile);
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (tempIS == null) continue;
                            if (throwable2 != null) {
                                try {
                                    tempIS.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            tempIS.close();
                        }
                    }
                    catch (Throwable tempIS) {
                        throwable = tempIS;
                        throw tempIS;
                    }
                    finally {
                        if (tempOS == null) continue;
                        if (throwable != null) {
                            try {
                                tempOS.close();
                            }
                            catch (Throwable tempIS) {
                                throwable.addSuppressed(tempIS);
                            }
                            continue;
                        }
                        tempOS.close();
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_102);
                }
            }
            String newZip = ZipUtils.newZip(newTempDir, TEMPFILE_PATH, entityDataSubFiles);
            this.checkFilePath(newZip);
            try (FileInputStream tempIS = new FileInputStream(newZip);){
                String subFilePath = SUBFILE_PATH_ENTITY_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_ENTITYDATA;
                this.checkFilePath(subFilePath);
                ZipUtils.ZipSubFile zipSubFile = new ZipUtils.ZipSubFile(subFilePath, tempIS);
                zipSubFiles.add(zipSubFile);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_102);
            }
        }
    }

    private void exportEntity(List<ZipUtils.ZipSubFile> zipSubFiles, String newTempDir, Set<String> entityKeys) throws JQException {
        String tempFilePath = newTempDir + ZipUtils.SEPARATOR + TEMPFILE_PATH;
        this.checkFilePath(tempFilePath);
        try (FileOutputStream tempOS = new FileOutputStream(tempFilePath);){
            this.dataSchemeEntityIOService.correlateExport(new ArrayList<String>(entityKeys), tempOS);
            try (FileInputStream tempIS = new FileInputStream(tempFilePath);){
                String subFilePath = SUBFILE_PATH_ENTITY_DIR + ZipUtils.SEPARATOR + SUBFILE_PATH_ENTITY;
                this.checkFilePath(subFilePath);
                ZipUtils.ZipSubFile zipSubFile = new ZipUtils.ZipSubFile(subFilePath, tempIS);
                zipSubFiles.add(zipSubFile);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_102);
        }
    }

    public String importDataScheme(String dataSchemeKey, InputStream inputStream, boolean importEntityData) throws JQException {
        if (null == inputStream) {
            return null;
        }
        logger.info("\u6570\u636e\u65b9\u6848\u5bfc\u5165\uff1a\u5f00\u59cb\u5bfc\u5165\u6570\u636e\u65b9\u6848\uff0c\u89e3\u538b\u538b\u7f29\u5305\u3002");
        Map<String, ZipUtils.ZipSubFile> zipSubFileMap = ZipUtils.unZip(inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFileName, f -> f));
        try {
            Throwable throwable;
            InputStream subFileInputStream;
            IOContext newIOContext = new IOContext(zipSubFileMap);
            DesignDataScheme newDataScheme = newIOContext.getDataScheme();
            if (null == newDataScheme) {
                throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_001);
            }
            if (StringUtils.hasLength(dataSchemeKey) && !dataSchemeKey.equals(newDataScheme.getKey())) {
                throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_002);
            }
            if (zipSubFileMap.containsKey(SUBFILE_PATH_ENTITY)) {
                logger.info("\u6570\u636e\u65b9\u6848\u5bfc\u5165\uff1a\u5bfc\u5165\u5b9e\u4f53\u5b9a\u4e49\u3002");
                this.dataSchemeEntityIOService.correlateImport(zipSubFileMap.get(SUBFILE_PATH_ENTITY).getSubFileInputStream());
            }
            if (zipSubFileMap.containsKey(SUBFILE_PATH_PERIOD)) {
                logger.info("\u6570\u636e\u65b9\u6848\u5bfc\u5165\uff1a\u5bfc\u5165\u65f6\u671f\u5b9a\u4e49\u3002");
                subFileInputStream = zipSubFileMap.get(SUBFILE_PATH_PERIOD).getSubFileInputStream();
                throwable = null;
                try {
                    this.periodEngineService.getPeriodIOService().importPeriod(subFileInputStream);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (subFileInputStream != null) {
                        if (throwable != null) {
                            try {
                                subFileInputStream.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                        } else {
                            subFileInputStream.close();
                        }
                    }
                }
            }
            if (zipSubFileMap.containsKey(SUBFILE_PATH_PERIODDATA)) {
                logger.info("\u6570\u636e\u65b9\u6848\u5bfc\u5165\uff1a\u5bfc\u5165\u65f6\u671f\u6570\u636e\u3002");
                subFileInputStream = zipSubFileMap.get(SUBFILE_PATH_PERIODDATA).getSubFileInputStream();
                throwable = null;
                try {
                    this.periodEngineService.getPeriodIOService().importPeriodData(subFileInputStream);
                }
                catch (Throwable throwable4) {
                    throwable = throwable4;
                    throw throwable4;
                }
                finally {
                    if (subFileInputStream != null) {
                        if (throwable != null) {
                            try {
                                subFileInputStream.close();
                            }
                            catch (Throwable throwable5) {
                                throwable.addSuppressed(throwable5);
                            }
                        } else {
                            subFileInputStream.close();
                        }
                    }
                }
            }
            if (zipSubFileMap.containsKey(SUBFILE_PATH_FORMTYPE) && null != this.iFormTypeIOService) {
                logger.info("\u6570\u636e\u65b9\u6848\u5bfc\u5165\uff1a\u5bfc\u5165\u62a5\u8868\u7c7b\u578b\u3002");
                subFileInputStream = zipSubFileMap.get(SUBFILE_PATH_FORMTYPE).getSubFileInputStream();
                throwable = null;
                try {
                    this.iFormTypeIOService.importFormType(subFileInputStream);
                }
                catch (Throwable throwable6) {
                    throwable = throwable6;
                    throw throwable6;
                }
                finally {
                    if (subFileInputStream != null) {
                        if (throwable != null) {
                            try {
                                subFileInputStream.close();
                            }
                            catch (Throwable throwable7) {
                                throwable.addSuppressed(throwable7);
                            }
                        } else {
                            subFileInputStream.close();
                        }
                    }
                }
            }
            IOContext oldIOContext = new IOContext(newDataScheme.getKey());
            logger.info("\u6570\u636e\u65b9\u6848\u5bfc\u5165\uff1a\u5bfc\u5165\u6570\u636e\u65b9\u6848\u3002");
            dataSchemeKey = this.doImport(oldIOContext, newIOContext);
            logger.info("\u6570\u636e\u65b9\u6848\u5bfc\u5165\uff1a\u5bfc\u5165\u6570\u636e\u65b9\u6848\u7ed3\u675f\u3002");
        }
        catch (JQException e) {
            logger.error("\u6570\u636e\u65b9\u6848\u5bfc\u5165\u5931\u8d25\uff1a{}\u3002", (Object)e.getMessage(), (Object)e);
            throw e;
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_003);
        }
        return dataSchemeKey;
    }

    private void moduleRegister(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(DesignDataScheme.class, DesignDataSchemeDO.class);
        module.addAbstractTypeMapping(DesignDataDimension.class, DesignDataDimDO.class);
        module.addAbstractTypeMapping(DesignDataGroup.class, DesignDataGroupDO.class);
        module.addAbstractTypeMapping(DesignDataTable.class, DesignDataTableDO.class);
        module.addAbstractTypeMapping(DesignDataField.class, DesignDataFieldDO.class);
        module.addAbstractTypeMapping(ValidationRule.class, ValidationRuleDTO.class);
        module.addDeserializer(Instant.class, (JsonDeserializer)new InstantJsonDeserializer());
        module.addSerializer(Instant.class, (JsonSerializer)new InstantJsonSerializer());
        objectMapper.registerModule((Module)module);
    }

    private String doImport(IOContext oldIOContext, IOContext newIOContext) {
        if (null == oldIOContext.getDataScheme()) {
            this.doAddDataScheme(newIOContext);
            return newIOContext.getDataScheme().getKey();
        }
        this.doUpdateDataScheme(oldIOContext, newIOContext);
        return oldIOContext.getDataScheme().getKey();
    }

    private void doAddDataScheme(IOContext newIOContext) {
        DesignDataScheme dataScheme = newIOContext.getDataScheme();
        dataScheme.setBizCode(OrderGenerator.newOrder());
        dataScheme.setDataGroupKey("00000000-0000-0000-0000-000000000000");
        this.dataSchemeDao.insert(Convert.iDs2Do(dataScheme));
        for (DesignDataDimension dimension : newIOContext.getDims()) {
            this.dataDimDao.insertNoUpdateTime(Convert.iDm2Do(dimension));
        }
        this.dataGroupDao.batchInsert(newIOContext.getDataGroups().stream().map(Convert::iDg2Do).collect(Collectors.toList()));
        this.dataTableDao.batchInsert(newIOContext.getDataTables().stream().map(Convert::iDt2Do).collect(Collectors.toList()));
        this.dataFieldDao.batchInsert(newIOContext.getDataFields().stream().map(f -> {
            this.doUpdateDataFieldRefField(newIOContext, (DesignDataField)f);
            return Convert.iDf2Do(f);
        }).collect(Collectors.toList()));
    }

    private void doUpdateDataScheme(IOContext oldIOContext, IOContext newIOContext) {
        DesignDataScheme dataScheme = newIOContext.getDataScheme();
        dataScheme.setKey(oldIOContext.getDataSchemeKey());
        dataScheme.setBizCode(oldIOContext.getDataScheme().getBizCode());
        dataScheme.setDataGroupKey("00000000-0000-0000-0000-000000000000");
        this.dataSchemeDao.update(Convert.iDs2Do(dataScheme));
        this.dataDimDao.deleteByDataScheme(oldIOContext.getDataSchemeKey());
        for (DesignDataDimension dimension : newIOContext.getDims()) {
            dimension.setDataSchemeKey(oldIOContext.getDataSchemeKey());
            this.dataDimDao.insertNoUpdateTime(Convert.iDm2Do(dimension));
        }
        this.doUpdateDataGroup(oldIOContext, newIOContext, null, null);
    }

    private void doUpdateDataGroup(IOContext oldIOContext, IOContext newIOContext, String oldParentKey, String parentKey) {
        Collection<DesignDataGroup> dataGroups = newIOContext.getDataGroupsByParentKey(parentKey);
        if (null != dataGroups && !dataGroups.isEmpty()) {
            for (DesignDataGroup designDataGroup : dataGroups) {
                designDataGroup.setDataSchemeKey(oldIOContext.getDataSchemeKey());
                String oldGroupKey = null;
                String newGroupKey = designDataGroup.getKey();
                DesignDataGroup oldGroup = oldIOContext.getDataGroupByTitle(oldParentKey, designDataGroup.getTitle());
                if (null == oldGroup) {
                    oldGroup = oldIOContext.getDataGroupByKey(designDataGroup.getKey());
                }
                if (null == oldGroup) {
                    designDataGroup.setParentKey(oldParentKey);
                    this.dataGroupDao.insert(Convert.iDg2Do(designDataGroup));
                } else {
                    oldGroupKey = oldGroup.getKey();
                    designDataGroup.setKey(oldGroup.getKey());
                    designDataGroup.setParentKey(oldParentKey);
                    this.dataGroupDao.update(Convert.iDg2Do(designDataGroup));
                }
                this.doUpdateDataTable(oldIOContext, newIOContext, designDataGroup.getKey());
                this.doUpdateDataGroup(oldIOContext, newIOContext, oldGroupKey, newGroupKey);
            }
        }
        if (!StringUtils.hasLength(parentKey)) {
            this.doUpdateDataTable(oldIOContext, newIOContext, null);
        }
    }

    private void doUpdateDataTable(IOContext oldIOContext, IOContext newIOContext, String groupKey) {
        Collection<DesignDataTable> dataTables = newIOContext.getDataTablesByGroup(groupKey);
        if (null != dataTables && !dataTables.isEmpty()) {
            for (DesignDataTable designDataTable : dataTables) {
                designDataTable.setDataSchemeKey(oldIOContext.getDataSchemeKey());
                designDataTable.setDataGroupKey(groupKey);
                String tableKey = designDataTable.getKey();
                String oldTableKey = null;
                DesignDataTable oldDataTable = oldIOContext.getDataTableByCode(designDataTable.getCode());
                if (null == oldDataTable) {
                    oldDataTable = oldIOContext.getDataTableByKey(designDataTable.getKey());
                    if (null == oldDataTable) {
                        this.dataTableDao.insert(Convert.iDt2Do(designDataTable));
                    } else {
                        oldTableKey = oldDataTable.getKey();
                        designDataTable.setKey(oldDataTable.getKey());
                        designDataTable.setDataGroupKey(oldDataTable.getDataGroupKey());
                        this.dataTableDao.update(Convert.iDt2Do(designDataTable));
                    }
                } else {
                    oldTableKey = oldDataTable.getKey();
                    designDataTable.setKey(oldDataTable.getKey());
                    designDataTable.setDataGroupKey(oldDataTable.getDataGroupKey());
                    this.dataTableDao.update(Convert.iDt2Do(designDataTable));
                }
                this.doUpdateDataField(oldIOContext, newIOContext, oldTableKey, tableKey);
            }
        }
    }

    private void doUpdateDataField(IOContext oldIOContext, IOContext newIOContext, String oldTableKey, String tableKey) {
        Collection<DesignDataField> dataFields = newIOContext.getDataFieldsByTable(tableKey);
        if (!StringUtils.hasLength(oldTableKey)) {
            this.dataFieldDao.batchInsert(dataFields.stream().map(f -> {
                f.setDataSchemeKey(oldIOContext.getDataSchemeKey());
                this.doUpdateDataFieldRefField(newIOContext, (DesignDataField)f);
                return Convert.iDf2Do(f);
            }).collect(Collectors.toList()));
        } else {
            ArrayList<DesignDataFieldDO> addDataFields = new ArrayList<DesignDataFieldDO>();
            ArrayList<DesignDataFieldDO> updateDataFields = new ArrayList<DesignDataFieldDO>();
            for (DesignDataField designDataField : dataFields) {
                this.doUpdateDataFieldRefField(newIOContext, designDataField);
                designDataField.setDataSchemeKey(oldIOContext.getDataSchemeKey());
                designDataField.setDataTableKey(oldTableKey);
                DesignDataField oldDataField = oldIOContext.getDataFieldByKey(designDataField.getKey());
                if (null == oldDataField) {
                    oldDataField = oldIOContext.getDataFieldByCode(oldTableKey, designDataField.getCode());
                    if (null == oldDataField) {
                        addDataFields.add(Convert.iDf2Do(designDataField));
                        continue;
                    }
                    designDataField.setKey(oldDataField.getKey());
                    updateDataFields.add(Convert.iDf2Do(designDataField));
                    continue;
                }
                updateDataFields.add(Convert.iDf2Do(designDataField));
                oldIOContext.refreshDataField(oldDataField, designDataField);
            }
            if (!updateDataFields.isEmpty()) {
                this.dataFieldDao.batchUpdate(updateDataFields);
            }
            if (!addDataFields.isEmpty()) {
                this.dataFieldDao.batchInsert(addDataFields);
            }
        }
    }

    private void doUpdateDataFieldRefField(IOContext newIOContext, DesignDataField designDataField) {
        if (StringUtils.hasLength(designDataField.getRefDataEntityKey())) {
            designDataField.setRefDataFieldKey(newIOContext.getRefFieldKey(designDataField.getRefDataEntityKey()));
        }
    }

    private void checkFilePath(String path) throws JQException {
        try {
            PathUtils.validatePathManipulation((String)path);
        }
        catch (SecurityContentException e) {
            throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_103);
        }
    }

    class IOContext {
        private DesignDataScheme dataScheme;
        private Map<String, DesignDataDimension> dataDimEntityMap;
        private Map<String, DesignDataGroup> dataGroupKeyMap;
        private Map<String, DesignDataGroup> dataGroupTitleMap;
        private Map<String, DesignDataTable> dataTableKeyMap;
        private Map<String, DesignDataTable> dataTableCodeMap;
        private Map<String, DesignDataField> dataFieldKeyMap;
        private Map<String, DesignDataField> dataFieldCodeMap;
        private Map<String, String> refFieldMap;

        public IOContext(String dataSchemeKey) {
            this.dataScheme = DataSchemeIOServiceImpl.this.designDataSchemeService.getDataScheme(dataSchemeKey);
            if (null != this.dataScheme) {
                List allDataField;
                List allDataTable;
                List dataSchemeDimension = DataSchemeIOServiceImpl.this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
                this.dataDimEntityMap = new HashMap<String, DesignDataDimension>();
                for (Object dim : dataSchemeDimension) {
                    this.dataDimEntityMap.put(dim.getDimKey(), (DesignDataDimension)dim);
                }
                List allDataGroup = DataSchemeIOServiceImpl.this.designDataSchemeService.getAllDataGroup(dataSchemeKey);
                if (null != allDataGroup && !allDataGroup.isEmpty()) {
                    this.dataGroupKeyMap = new HashMap<String, DesignDataGroup>();
                    this.dataGroupTitleMap = new HashMap<String, DesignDataGroup>();
                    for (Object group : allDataGroup) {
                        this.dataGroupKeyMap.put(group.getKey(), (DesignDataGroup)group);
                        this.dataGroupTitleMap.put(this.getComKey(group.getParentKey(), group.getTitle()), (DesignDataGroup)group);
                    }
                }
                if (null != (allDataTable = DataSchemeIOServiceImpl.this.designDataSchemeService.getAllDataTable(dataSchemeKey)) && !allDataTable.isEmpty()) {
                    this.dataTableKeyMap = new HashMap<String, DesignDataTable>();
                    this.dataTableCodeMap = new HashMap<String, DesignDataTable>();
                    for (DesignDataTable table : allDataTable) {
                        this.dataTableKeyMap.put(table.getKey(), table);
                        this.dataTableCodeMap.put(table.getCode(), table);
                    }
                }
                if (null != (allDataField = DataSchemeIOServiceImpl.this.designDataSchemeService.getAllDataField(dataSchemeKey)) && !allDataField.isEmpty()) {
                    this.dataFieldKeyMap = new HashMap<String, DesignDataField>();
                    this.dataFieldCodeMap = new HashMap<String, DesignDataField>();
                    for (DesignDataField field : allDataField) {
                        this.dataFieldKeyMap.put(field.getKey(), field);
                        this.dataFieldCodeMap.put(this.getComKey(field.getDataTableKey(), field.getCode()), field);
                    }
                }
            }
        }

        public IOContext(Map<String, ZipUtils.ZipSubFile> zipSubFileMap) throws IOException {
            DesignDataField[] allDataField;
            DesignDataTable[] allDataTable;
            DesignDataGroup[] allDataGroup;
            if (zipSubFileMap.containsKey(DataSchemeIOServiceImpl.SUBFILE_PATH_DATASCHEME)) {
                this.dataScheme = zipSubFileMap.get(DataSchemeIOServiceImpl.SUBFILE_PATH_DATASCHEME).getSubFileJsonDataConverter(DesignDataScheme.class, x$0 -> DataSchemeIOServiceImpl.this.moduleRegister(x$0)).getData();
            }
            if (null == this.dataScheme) {
                return;
            }
            if (zipSubFileMap.containsKey(DataSchemeIOServiceImpl.SUBFILE_PATH_DATADIM)) {
                DesignDataDimension[] dataDims = zipSubFileMap.get(DataSchemeIOServiceImpl.SUBFILE_PATH_DATADIM).getSubFileJsonDataConverter(DesignDataDimension[].class, x$0 -> DataSchemeIOServiceImpl.this.moduleRegister(x$0)).getData();
                this.dataDimEntityMap = new HashMap<String, DesignDataDimension>();
                for (DesignDataDimension designDataDimension : dataDims) {
                    this.dataDimEntityMap.put(designDataDimension.getDimKey(), designDataDimension);
                }
            }
            if (zipSubFileMap.containsKey(DataSchemeIOServiceImpl.SUBFILE_PATH_DATAGROUP) && null != (allDataGroup = zipSubFileMap.get(DataSchemeIOServiceImpl.SUBFILE_PATH_DATAGROUP).getSubFileJsonDataConverter(DesignDataGroup[].class, x$0 -> DataSchemeIOServiceImpl.this.moduleRegister(x$0)).getData()) && 0 != allDataGroup.length) {
                this.dataGroupKeyMap = new HashMap<String, DesignDataGroup>();
                this.dataGroupTitleMap = new HashMap<String, DesignDataGroup>();
                for (DesignDataDimension designDataDimension : allDataGroup) {
                    this.dataGroupKeyMap.put(designDataDimension.getKey(), (DesignDataGroup)designDataDimension);
                    this.dataGroupTitleMap.put(this.getComKey(designDataDimension.getParentKey(), designDataDimension.getTitle()), (DesignDataGroup)designDataDimension);
                }
            }
            if (zipSubFileMap.containsKey(DataSchemeIOServiceImpl.SUBFILE_PATH_DATATABLE) && null != (allDataTable = zipSubFileMap.get(DataSchemeIOServiceImpl.SUBFILE_PATH_DATATABLE).getSubFileJsonDataConverter(DesignDataTable[].class, x$0 -> DataSchemeIOServiceImpl.this.moduleRegister(x$0)).getData()) && 0 != allDataTable.length) {
                this.dataTableKeyMap = new HashMap<String, DesignDataTable>();
                this.dataTableCodeMap = new HashMap<String, DesignDataTable>();
                for (DesignDataDimension designDataDimension : allDataTable) {
                    this.dataTableKeyMap.put(designDataDimension.getKey(), (DesignDataTable)designDataDimension);
                    this.dataTableCodeMap.put(designDataDimension.getCode(), (DesignDataTable)designDataDimension);
                }
            }
            if (zipSubFileMap.containsKey(DataSchemeIOServiceImpl.SUBFILE_PATH_DATAFIELD) && null != (allDataField = zipSubFileMap.get(DataSchemeIOServiceImpl.SUBFILE_PATH_DATAFIELD).getSubFileJsonDataConverter(DesignDataField[].class, x$0 -> DataSchemeIOServiceImpl.this.moduleRegister(x$0)).getData()) && 0 != allDataField.length) {
                this.dataFieldKeyMap = new HashMap<String, DesignDataField>();
                this.dataFieldCodeMap = new HashMap<String, DesignDataField>();
                for (DesignDataDimension designDataDimension : allDataField) {
                    this.dataFieldKeyMap.put(designDataDimension.getKey(), (DesignDataField)designDataDimension);
                    this.dataFieldCodeMap.put(this.getComKey(designDataDimension.getDataTableKey(), designDataDimension.getCode()), (DesignDataField)designDataDimension);
                }
            }
        }

        public String getDataSchemeKey() {
            if (null == this.dataScheme) {
                return null;
            }
            return this.dataScheme.getKey();
        }

        public DesignDataScheme getDataScheme() {
            return this.dataScheme;
        }

        public DesignDataDimension getDimByEntityKey(String entityKey) {
            if (null == this.dataDimEntityMap) {
                return null;
            }
            return this.dataDimEntityMap.get(entityKey);
        }

        public Collection<DesignDataDimension> getDims() {
            if (null == this.dataDimEntityMap) {
                return Collections.emptyList();
            }
            return this.dataDimEntityMap.values();
        }

        public DesignDataGroup getDataGroupByKey(String groupKey) {
            if (null == this.dataGroupKeyMap) {
                return null;
            }
            return this.dataGroupKeyMap.get(groupKey);
        }

        public DesignDataGroup getDataGroupByTitle(String parentKey, String groupTitle) {
            if (null == this.dataGroupTitleMap) {
                return null;
            }
            return this.dataGroupTitleMap.get(this.getComKey(parentKey, groupTitle));
        }

        public Collection<DesignDataGroup> getDataGroups() {
            if (null == this.dataGroupKeyMap) {
                return Collections.emptyList();
            }
            return this.dataGroupKeyMap.values();
        }

        public Collection<DesignDataGroup> getDataGroupsByParentKey(String parentKey) {
            if (null == this.dataGroupKeyMap) {
                return Collections.emptyList();
            }
            if (StringUtils.hasLength(parentKey)) {
                return this.dataGroupKeyMap.values().stream().filter(v -> parentKey.equals(v.getParentKey())).collect(Collectors.toList());
            }
            return this.dataGroupKeyMap.values().stream().filter(v -> !StringUtils.hasLength(v.getParentKey())).collect(Collectors.toList());
        }

        private String getComKey(String ... keys) {
            return String.join((CharSequence)"-", keys);
        }

        public DesignDataTable getDataTableByKey(String tableKey) {
            if (null == this.dataTableKeyMap) {
                return null;
            }
            return this.dataTableKeyMap.get(tableKey);
        }

        public DesignDataTable getDataTableByCode(String tableCode) {
            if (null == this.dataTableCodeMap) {
                return null;
            }
            return this.dataTableCodeMap.get(tableCode);
        }

        public Collection<DesignDataTable> getDataTables() {
            if (null == this.dataTableKeyMap) {
                return Collections.emptyList();
            }
            return this.dataTableKeyMap.values();
        }

        public Collection<DesignDataTable> getDataTablesByGroup(String groupKey) {
            if (null == this.dataTableKeyMap) {
                return Collections.emptyList();
            }
            if (StringUtils.hasLength(groupKey)) {
                return this.dataTableKeyMap.values().stream().filter(v -> groupKey.equals(v.getDataGroupKey())).collect(Collectors.toList());
            }
            return this.dataTableKeyMap.values().stream().filter(v -> !StringUtils.hasLength(v.getDataGroupKey())).collect(Collectors.toList());
        }

        public DesignDataField getDataFieldByKey(String fieldKey) {
            if (null == this.dataFieldKeyMap) {
                return null;
            }
            return this.dataFieldKeyMap.get(fieldKey);
        }

        public DesignDataField getDataFieldByCode(String tableKey, String fieldCode) {
            if (null == this.dataFieldCodeMap) {
                return null;
            }
            return this.dataFieldCodeMap.get(this.getComKey(tableKey, fieldCode));
        }

        public void refreshDataField(DesignDataField oldDataField, DesignDataField dataField) {
            if (null == this.dataFieldCodeMap || oldDataField.getCode().equals(dataField.getCode())) {
                return;
            }
            this.dataFieldCodeMap.remove(this.getComKey(oldDataField.getDataTableKey(), oldDataField.getCode()));
            this.dataFieldCodeMap.put(this.getComKey(dataField.getDataTableKey(), dataField.getCode()), dataField);
        }

        public Collection<DesignDataField> getDataFields() {
            if (null == this.dataFieldKeyMap) {
                return Collections.emptyList();
            }
            return this.dataFieldKeyMap.values();
        }

        public Collection<DesignDataField> getDataFieldsByTable(String tableKey) {
            if (null == this.dataFieldKeyMap) {
                return Collections.emptyList();
            }
            return this.dataFieldKeyMap.values().stream().filter(v -> v.getDataTableKey().equals(tableKey)).collect(Collectors.toList());
        }

        public String getRefFieldKey(String refEntityKey) {
            if (null == this.refFieldMap) {
                this.refFieldMap = new HashMap<String, String>();
            }
            if (this.refFieldMap.containsKey(refEntityKey)) {
                return this.refFieldMap.get(refEntityKey);
            }
            boolean isPeriod = DataSchemeIOServiceImpl.this.periodEngineService.getPeriodAdapter().isPeriodEntity(refEntityKey);
            TableModelDefine tableModel = !isPeriod ? DataSchemeIOServiceImpl.this.entityMetaService.getTableModel(refEntityKey) : DataSchemeIOServiceImpl.this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(refEntityKey);
            if (null != tableModel) {
                this.refFieldMap.put(refEntityKey, tableModel.getBizKeys());
                return tableModel.getBizKeys();
            }
            return null;
        }
    }
}

