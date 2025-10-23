/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.entity.common.AdapterException
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityIOService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.entity.service.impl.EntityIOServiceImpl
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.common.DataSchemeIOErrorEnum;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.datascheme.internal.service.IDataSchemeEntityIOService;
import com.jiuqi.nr.entity.common.AdapterException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityIOService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.entity.service.impl.EntityIOServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataSchemeEntityIOServiceImpl
implements IDataSchemeEntityIOService {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeEntityIOServiceImpl.class);
    private static final String TEMPFILE_PATH = "temp.data";
    private static final String SUBFILE_PATH_ENTITY = "meta.zip";
    private static final String SUBFILE_PATH_ENTITYDATA = "data.zip";
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityIOService entityIOService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void correlateExport(List<String> entityIds, OutputStream outputStream) throws JQException {
        ArrayList<String> correlateIds = new ArrayList<String>();
        for (String entityId : entityIds) {
            if (!correlateIds.contains(entityId)) {
                correlateIds.add(entityId);
            }
            List entityRefer = null;
            try {
                entityRefer = this.entityMetaService.getEntityRefer(entityId);
            }
            catch (Exception e2) {
                continue;
            }
            if (CollectionUtils.isEmpty(entityRefer)) continue;
            for (IEntityRefer refer : entityRefer) {
                if (correlateIds.contains(refer.getReferEntityId())) continue;
                correlateIds.add(refer.getReferEntityId());
            }
        }
        ArrayList<ZipUtils.ZipSubFile> zipSubFiles = new ArrayList<ZipUtils.ZipSubFile>();
        String newTempDir = ZipUtils.newTempDir();
        this.checkFilePath(newTempDir);
        try {
            this.exportEntity(zipSubFiles, newTempDir, correlateIds);
        }
        finally {
            File tempDir = new File(newTempDir);
            if (tempDir.exists()) {
                tempDir.delete();
            }
        }
        List<String> filterExportData = correlateIds.stream().filter(e -> !e.startsWith("MD_ORG")).collect(Collectors.toList());
        newTempDir = ZipUtils.newTempDir();
        this.checkFilePath(newTempDir);
        try {
            this.exportEntityData(zipSubFiles, newTempDir, filterExportData);
        }
        finally {
            File tempDir = new File(newTempDir);
            if (tempDir.exists()) {
                tempDir.delete();
            }
        }
        ZipUtils.zip(outputStream, zipSubFiles);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void correlateImport(InputStream inputStream) throws JQException {
        Map<String, ZipUtils.ZipSubFile> zipSubFileMap = ZipUtils.unZip(inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFileName, f -> f));
        if (zipSubFileMap.containsKey(SUBFILE_PATH_ENTITY)) {
            List entityDefines;
            ArrayList<String> referEntity = new ArrayList<String>();
            try (InputStream subFileInputStream = zipSubFileMap.get(SUBFILE_PATH_ENTITY).getSubFileInputStream();){
                entityDefines = this.entityIOService.importEntityDefine(subFileInputStream);
            }
            catch (Exception e2) {
                throw new RuntimeException(e2.getMessage(), e2);
            }
            for (IEntityDefine entityDefine : entityDefines) {
                List entityRefer = null;
                try {
                    entityRefer = this.entityMetaService.getEntityRefer(entityDefine.getId());
                }
                catch (Exception e3) {
                    logger.error(String.format("\u89e3\u6790\u5b9e\u4f53%s\u7684\u5173\u8054\u5173\u7cfb\u65f6\u62a5\u9519.", entityDefine.getId()), e3);
                    continue;
                }
                List filterRefers = entityRefer.stream().filter(e -> !"MD_ORG@ORG".equals(e.getReferEntityId())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(filterRefers)) continue;
                referEntity.add(entityDefine.getId());
            }
            if (!CollectionUtils.isEmpty(referEntity)) {
                Throwable throwable;
                boolean needImport = false;
                String path = EntityIOServiceImpl.ZIP_PATH + File.separator + System.currentTimeMillis();
                this.checkFilePath(path);
                File file = new File(path);
                String tempFilePath = ZipUtils.newTempDir() + ZipUtils.SEPARATOR + TEMPFILE_PATH;
                this.checkFilePath(tempFilePath);
                try {
                    FileUtils.copyToFile(zipSubFileMap.get(SUBFILE_PATH_ENTITY).getSubFileInputStream(), file);
                }
                catch (IOException e4) {
                    throw new JQException((ErrorEnum)AdapterException.ERROR_READ_FILE, (Throwable)e4);
                }
                try {
                    throwable = null;
                    try (ZipFile zipFile = new ZipFile(file);){
                        Enumeration<? extends ZipEntry> entries = zipFile.entries();
                        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFilePath);
                             ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream);){
                            while (entries.hasMoreElements()) {
                                InputStream is;
                                block99: {
                                    ZipEntry zipEntry = entries.nextElement();
                                    is = zipFile.getInputStream(zipEntry);
                                    Throwable throwable2 = null;
                                    try {
                                        String zipEntryName = zipEntry.getName();
                                        String id = zipEntryName.substring(("entityDefine" + File.separator).length());
                                        if (referEntity.contains(id)) {
                                            needImport = true;
                                            zipOut.putNextEntry(new ZipEntry(zipEntryName));
                                            IOUtils.copy(is, (OutputStream)zipOut);
                                        }
                                        if (is == null) continue;
                                        if (throwable2 == null) break block99;
                                    }
                                    catch (Throwable throwable3) {
                                        try {
                                            throwable2 = throwable3;
                                            throw throwable3;
                                        }
                                        catch (Throwable throwable4) {
                                            if (is == null) throw throwable4;
                                            if (throwable2 == null) {
                                                is.close();
                                                throw throwable4;
                                            }
                                            try {
                                                is.close();
                                                throw throwable4;
                                            }
                                            catch (Throwable throwable5) {
                                                throwable2.addSuppressed(throwable5);
                                                throw throwable4;
                                            }
                                        }
                                    }
                                    try {
                                        is.close();
                                        continue;
                                    }
                                    catch (Throwable throwable6) {
                                        throwable2.addSuppressed(throwable6);
                                        continue;
                                    }
                                }
                                is.close();
                            }
                        }
                    }
                    catch (Throwable throwable7) {
                        throwable = throwable7;
                        throw throwable7;
                    }
                }
                catch (IOException e5) {
                    throw new JQException((ErrorEnum)AdapterException.ERROR_READ_FILE, (Throwable)e5);
                }
                if (needImport) {
                    try {
                        throwable = null;
                        try (FileInputStream fileInputStream = new FileInputStream(tempFilePath);){
                            this.entityIOService.importEntityDefine((InputStream)fileInputStream);
                        }
                        catch (Throwable throwable8) {
                            throwable = throwable8;
                            throw throwable8;
                        }
                    }
                    catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
            }
        }
        if (!zipSubFileMap.containsKey(SUBFILE_PATH_ENTITYDATA)) return;
        List<ZipUtils.ZipSubFile> entityDataSubFile = ZipUtils.unZip(zipSubFileMap.get(SUBFILE_PATH_ENTITYDATA).getSubFileInputStream());
        Iterator<ZipUtils.ZipSubFile> iterator = entityDataSubFile.iterator();
        while (iterator.hasNext()) {
            ZipUtils.ZipSubFile zipSubFile = iterator.next();
            try {
                InputStream subFileInputStream = zipSubFile.getSubFileInputStream();
                Throwable throwable = null;
                try {
                    this.entityIOService.importEntityData(subFileInputStream);
                }
                catch (Throwable throwable9) {
                    throwable = throwable9;
                    throw throwable9;
                }
                finally {
                    if (subFileInputStream == null) continue;
                    if (throwable != null) {
                        try {
                            subFileInputStream.close();
                        }
                        catch (Throwable throwable10) {
                            throwable.addSuppressed(throwable10);
                        }
                        continue;
                    }
                    subFileInputStream.close();
                }
            }
            catch (Exception e7) {
                throw new RuntimeException(e7.getMessage(), e7);
            }
        }
    }

    private void exportEntity(List<ZipUtils.ZipSubFile> zipSubFiles, String newTempDir, List<String> entityKeys) throws JQException {
        String tempFilePath = newTempDir + ZipUtils.SEPARATOR + TEMPFILE_PATH;
        this.checkFilePath(tempFilePath);
        try (FileOutputStream tempOS = new FileOutputStream(tempFilePath);){
            this.entityIOService.exportEntityDefine(entityKeys, (OutputStream)tempOS);
            try (FileInputStream tempIS = new FileInputStream(tempFilePath);){
                ZipUtils.ZipSubFile zipSubFile = new ZipUtils.ZipSubFile(SUBFILE_PATH_ENTITY, tempIS);
                zipSubFiles.add(zipSubFile);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_102);
        }
    }

    private void exportEntityData(List<ZipUtils.ZipSubFile> zipSubFiles, String newTempDir, List<String> entityDataKeys) throws JQException {
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
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (tempOS == null) continue;
                        if (throwable != null) {
                            try {
                                tempOS.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
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
                ZipUtils.ZipSubFile zipSubFile = new ZipUtils.ZipSubFile(SUBFILE_PATH_ENTITYDATA, tempIS);
                zipSubFiles.add(zipSubFile);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)DataSchemeIOErrorEnum.DS_IO_102);
            }
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
}

