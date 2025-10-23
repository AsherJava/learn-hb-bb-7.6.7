/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.transfer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.migration.transfer.bean.OrgDTO;
import com.jiuqi.nr.migration.transfer.bean.OrgVersionDTO;
import com.jiuqi.nr.migration.transfer.service.IOrgDataTransferService;
import com.jiuqi.nr.migration.transfer.service.ITransferMigrationService;
import com.jiuqi.nr.migration.transfer.util.TransferMigrationUtilts;
import com.jiuqi.nr.migration.transfer.vo.OrgDataFile;
import com.jiuqi.nr.migration.transfer.vo.OrgVersion;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OrgDataTransferServiceImpl
implements IOrgDataTransferService {
    private static final Logger logger = LoggerFactory.getLogger(OrgDataTransferServiceImpl.class);
    private static final String ORGDATA_FILENAME = "data.txt";
    private static final String ORGVERSION_FILENAME = "version.txt";
    private static final String ORGDATA_TEMPFILE = "org.data";
    private static final String LOG_PREFIX_UPLOAD = "[\u6587\u4ef6\u4e0a\u4f20]";
    private static final String LOG_UPLOAD = "[\u6587\u4ef6\u4e0a\u4f20]:{}";
    private static final String LOG_PREFIX_IMPORT = "[\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165]";
    private static final String LOG_IMPORT = "[\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165]:{}";
    private static final String ORGDATA_ADVANCE = "ORGDATAADVANCE";
    @Autowired
    private ITransferMigrationService transferMigrationService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public OrgDataFile uploadFile(MultipartFile file) throws Exception {
        OrgDataFile orgDataFile = new OrgDataFile();
        File tempDir = this.createTempDir(LOG_PREFIX_UPLOAD);
        File orgDataTempFile = this.createOrgDataTempFile(tempDir.getAbsolutePath(), LOG_PREFIX_UPLOAD);
        try (OutputStream os = Files.newOutputStream(orgDataTempFile.toPath(), new OpenOption[0]);
             ZipInputStream zis = new ZipInputStream(file.getInputStream());){
            OrgDTO orgDTO = this.readOrgDTO(zis);
            if (orgDTO == null) {
                throw new RuntimeException("[\u6587\u4ef6\u4e0a\u4f20]:OrgDTO\u5bf9\u8c61\u89e3\u6790\u5931\u8d25,\u8bf7\u4e0a\u4f20\u6709\u6548\u7684\u6570\u636e\u6587\u4ef6");
            }
            if (!CollectionUtils.isEmpty(orgDTO.getItems())) {
                String validtime = (String)orgDTO.getItems().keySet().stream().findFirst().get();
                try {
                    Long.parseLong(validtime);
                    throw new RuntimeException("[\u6587\u4ef6\u4e0a\u4f20]:\u6570\u636e\u5305\u7248\u672c\u4f4e");
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            logger.info(LOG_UPLOAD, (Object)"\u5199\u5165\u4e34\u65f6\u6587\u4ef6");
            os.write(file.getBytes());
            os.close();
            logger.info(LOG_UPLOAD, (Object)"\u8bfb\u53d6\u4e34\u65f6\u6587\u4ef6\uff0c\u5b58\u50a8OSS");
            ObjectStorageService objectStorageService = ObjectStorageManager.getInstance().createTemporaryObjectService();
            String key = UUIDUtils.getKey();
            objectStorageService.upload(key, Files.newInputStream(orgDataTempFile.toPath(), new OpenOption[0]));
            orgDataFile.setKey(key);
            orgDataFile.setOrgType(orgDTO.getDefineName());
            orgDataFile.setFileName(file.getOriginalFilename());
            if (orgDTO.getItems().size() == 1) {
                orgDataFile.setUnitCount(orgDTO.getItems().values().size());
            } else {
                ArrayList<OrgVersion> versions = new ArrayList<OrgVersion>();
                for (String validtime : orgDTO.getItems().keySet()) {
                    OrgVersion version = new OrgVersion();
                    version.setName(TransferMigrationUtilts.getOrgVersionTitle(validtime));
                    version.setCount(orgDTO.getItems().get(validtime).size());
                    versions.add(version);
                }
                versions.sort(Comparator.comparing(OrgVersion::getName));
                orgDataFile.setVersions(versions);
            }
        }
        finally {
            this.deleteTempFile(tempDir, LOG_PREFIX_UPLOAD);
        }
        return orgDataFile;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void importOrgData(String key) throws Exception {
        if (!StringUtils.hasLength(key)) {
            throw new RuntimeException("[\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165]:OrgDTO\u5bf9\u8c61key\u4e3a\u7a7a");
        }
        File tempDir = this.createTempDir(LOG_PREFIX_IMPORT);
        File orgDataTempFile = this.createOrgDataTempFile(tempDir.getAbsolutePath(), LOG_PREFIX_IMPORT);
        Path path = orgDataTempFile.toPath();
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(path, new OpenOption[0]));){
            logger.info(LOG_IMPORT, (Object)"\u8bfb\u53d6OSS\u6587\u4ef6");
            ObjectStorageService objectStorageService = ObjectStorageManager.getInstance().createTemporaryObjectService();
            objectStorageService.download(key, Files.newOutputStream(path, new OpenOption[0]));
            logger.info(LOG_IMPORT, (Object)"\u89e3\u6790OrgDTO\u5bf9\u8c61");
            OrgDTO orgDTO = this.readOrgDTO(zis);
            if (orgDTO == null) {
                throw new RuntimeException("[\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165]:OrgDTO\u5bf9\u8c61\u89e3\u6790\u5931\u8d25");
            }
            if (ORGDATA_ADVANCE.equals(orgDTO.getType())) {
                logger.info("{}:\u5f00\u59cb\u5bfc\u5165[{}]\u6570\u636e", (Object)LOG_PREFIX_IMPORT, (Object)orgDTO.getDefineName());
                Map<String, List<OrgDO>> items = orgDTO.getItems();
                R r = this.transferMigrationService.updateOrgData(orgDTO.getDefineName(), items);
                if (r.getCode() != 0) {
                    throw new Exception(r.getMsg());
                }
            } else if ("MD_ORG".equalsIgnoreCase(orgDTO.getDefineName())) {
                logger.info("{}:\u5f00\u59cb\u5bfc\u5165[{}]\u6570\u636e", (Object)LOG_PREFIX_IMPORT, (Object)orgDTO.getDefineName());
                Map<String, List<OrgDO>> items = orgDTO.getItems();
                if (items.entrySet().iterator().hasNext()) {
                    Map.Entry<String, List<OrgDO>> next = items.entrySet().iterator().next();
                    List<OrgDO> value = next.getValue();
                    this.transferMigrationService.importMDORG(value);
                }
            } else {
                OrgVersionDTO versionDTO = this.readOrgVersionDTO(zis);
                if (versionDTO == null) {
                    throw new RuntimeException("[\u7ec4\u7ec7\u673a\u6784\u5bfc\u5165]:OrgVersionDTO\u5bf9\u8c61\u89e3\u6790\u5931\u8d25");
                }
                logger.info("{}:\u5f00\u59cb\u5bfc\u5165[{}]\u7248\u672c", (Object)LOG_PREFIX_IMPORT, (Object)orgDTO.getDefineName());
                this.transferMigrationService.importOrgVersion(versionDTO.getDefineName(), versionDTO.getItems());
                logger.info("{}:\u5f00\u59cb\u5bfc\u5165[{}]\u6570\u636e", (Object)LOG_PREFIX_IMPORT, (Object)versionDTO.getDefineName());
                this.transferMigrationService.importOrgData(versionDTO.getDefineName(), orgDTO.getItems());
            }
            logger.info(LOG_IMPORT, (Object)"\u5bfc\u5165\u6210\u529f");
        }
        finally {
            this.deleteTempFile(tempDir, LOG_PREFIX_IMPORT);
        }
    }

    private File createTempDir(String logPrefix) throws IOException {
        String tempDir;
        File tempDirFile;
        String sysTempDir = System.getProperty("java.io.tmpdir");
        if (!sysTempDir.endsWith(File.separator)) {
            sysTempDir = sysTempDir + File.separator;
        }
        if ((tempDirFile = new File(tempDir = sysTempDir + UUIDUtils.getKey())).mkdir()) {
            logger.info("{}:\u521b\u5efa\u4e34\u65f6\u76ee\u5f55[{}]", (Object)logPrefix, (Object)tempDirFile.getName());
            return tempDirFile;
        }
        throw new IOException(logPrefix + ":\u521b\u5efa\u4e34\u65f6\u76ee\u5f55\u5931\u8d25");
    }

    private File createOrgDataTempFile(String dir, String logPrefix) throws IOException {
        logger.info("{}:\u521b\u5efa\u4e34\u65f6\u6587\u4ef6{}", (Object)logPrefix, (Object)ORGDATA_TEMPFILE);
        File file = new File(dir + File.separator + ORGDATA_TEMPFILE);
        if (file.createNewFile()) {
            return file;
        }
        throw new RuntimeException(logPrefix + ":\u521b\u5efaorgData\u4e34\u65f6\u6587\u4ef6\u5931\u8d25");
    }

    private OrgDTO readOrgDTO(ZipInputStream zis) throws IOException {
        ZipEntry currZipEntry;
        while ((currZipEntry = zis.getNextEntry()) != null) {
            if (!currZipEntry.getName().equals(ORGDATA_FILENAME)) continue;
            byte[] dataBytes = this.readOrgData(zis);
            ObjectMapper objectMapper = new ObjectMapper();
            return (OrgDTO)objectMapper.readValue(dataBytes, OrgDTO.class);
        }
        return null;
    }

    private OrgVersionDTO readOrgVersionDTO(ZipInputStream zis) throws IOException {
        ZipEntry currZipEntry;
        while ((currZipEntry = zis.getNextEntry()) != null) {
            if (!currZipEntry.getName().equals(ORGVERSION_FILENAME)) continue;
            byte[] dataBytes = this.readOrgData(zis);
            ObjectMapper objectMapper = new ObjectMapper();
            return (OrgVersionDTO)objectMapper.readValue(dataBytes, OrgVersionDTO.class);
        }
        return null;
    }

    private byte[] readOrgData(ZipInputStream zis) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();){
            int len;
            byte[] bytes = new byte[8192];
            while ((len = zis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            byte[] byArray = bos.toByteArray();
            return byArray;
        }
    }

    private void deleteTempFile(File tempDirFile, String logPrefix) {
        File[] files = tempDirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    this.deleteTempFile(file, logPrefix);
                    continue;
                }
                if (!file.delete()) continue;
                logger.info("{}:\u5220\u9664\u4e34\u65f6\u6587\u4ef6[{}]\u6210\u529f", (Object)logPrefix, (Object)file.getName());
            }
        }
        if (tempDirFile.delete()) {
            logger.info("{}:\u5220\u9664\u4e34\u65f6\u76ee\u5f55[{}]\u6210\u529f", (Object)logPrefix, (Object)tempDirFile.getName());
        }
    }
}

