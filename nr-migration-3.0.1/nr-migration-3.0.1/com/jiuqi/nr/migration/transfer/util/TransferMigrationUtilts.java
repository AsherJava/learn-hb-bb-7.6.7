/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.transfer.util;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.OrgVersionClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class TransferMigrationUtilts {
    private static final Logger logger = LoggerFactory.getLogger(TransferMigrationUtilts.class);

    public static boolean unzip(File cacheDir) throws IOException {
        File cachedZipFile;
        ZipFile zipfile;
        if (cacheDir.listFiles().length > 0 && (zipfile = new ZipFile(cachedZipFile = cacheDir.listFiles()[0], Charset.forName("UTF-8"))) != null) {
            TransferMigrationUtilts.handleDirectory(cacheDir, zipfile);
            TransferMigrationUtilts.handleFiles(cacheDir, zipfile);
            zipfile.close();
            return true;
        }
        return false;
    }

    private static void handleFiles(File cacheDir, ZipFile zipfile) throws IOException {
        Enumeration<? extends ZipEntry> e = zipfile.entries();
        while (e.hasMoreElements()) {
            boolean createSuccess;
            ZipEntry entry = e.nextElement();
            if (entry.isDirectory()) continue;
            File entryFile = new File(cacheDir, entry.getName());
            if (!entryFile.getParentFile().exists()) {
                entryFile.getParentFile().mkdirs();
            }
            if (!(createSuccess = entryFile.createNewFile())) continue;
            BufferedInputStream is = new BufferedInputStream(zipfile.getInputStream(entry));
            Throwable throwable = null;
            try {
                BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(entryFile), 1024);
                Throwable throwable2 = null;
                try {
                    int len;
                    byte[] dataByte = new byte[1024];
                    while ((len = is.read(dataByte, 0, 1024)) != -1) {
                        dest.write(dataByte, 0, len);
                    }
                    dest.flush();
                }
                catch (Throwable throwable3) {
                    throwable2 = throwable3;
                    throw throwable3;
                }
                finally {
                    if (dest == null) continue;
                    if (throwable2 != null) {
                        try {
                            dest.close();
                        }
                        catch (Throwable throwable4) {
                            throwable2.addSuppressed(throwable4);
                        }
                        continue;
                    }
                    dest.close();
                }
            }
            catch (Throwable throwable5) {
                throwable = throwable5;
                throw throwable5;
            }
            finally {
                if (is == null) continue;
                if (throwable != null) {
                    try {
                        is.close();
                    }
                    catch (Throwable throwable6) {
                        throwable.addSuppressed(throwable6);
                    }
                    continue;
                }
                is.close();
            }
        }
    }

    private static void handleDirectory(File cacheDir, ZipFile zipfile) {
        Enumeration<? extends ZipEntry> dir = zipfile.entries();
        while (dir.hasMoreElements()) {
            ZipEntry entry = dir.nextElement();
            if (!entry.isDirectory()) continue;
            String name = entry.getName();
            name = name.substring(0, name.length() - 1);
            File fileObject = new File(cacheDir + name);
            fileObject.mkdirs();
        }
    }

    public static File cacheFile(MultipartFile zipFile) throws IOException {
        File cacheDir;
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u521b\u5efa\u672c\u5730\u7f13\u5b58...");
        String filename = zipFile.getOriginalFilename();
        if (filename.contains(".")) {
            filename = filename.substring(0, filename.lastIndexOf("."));
        }
        if ((cacheDir = TransferMigrationUtilts.getCacheDir()) != null && cacheDir.exists()) {
            logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u672c\u5730\u7f13\u5b58\u76ee\u5f55 " + cacheDir.getAbsolutePath() + " \u521b\u5efa\u6210\u529f\u3002");
            File destZipFile = new File(cacheDir, zipFile.getOriginalFilename());
            zipFile.transferTo(destZipFile);
        }
        return cacheDir;
    }

    private static File getCacheDir() {
        String tempDir = System.getProperty("java.io.tmpdir");
        if (!tempDir.endsWith(File.separator)) {
            tempDir = tempDir + File.separator;
        }
        String key = UUID.randomUUID().toString().replace("-", "");
        File destDir = new File(tempDir + key);
        if (!destDir.exists() && !destDir.mkdir()) {
            logger.warn("\u9644\u4ef6\u8fc1\u79fb\uff1a\u7f13\u5b58\u76ee\u5f55" + destDir.getAbsolutePath() + "\u521b\u5efa\u5931\u8d25\u3002");
            String cacheLocation = ClassUtils.getDefaultClassLoader().getResource("").getPath().replaceFirst("/", "");
            destDir = new File(cacheLocation + key);
            if (!destDir.exists() && !destDir.mkdir()) {
                logger.warn("\u9644\u4ef6\u8fc1\u79fb\uff1a\u7f13\u5b58\u76ee\u5f55" + destDir.getAbsolutePath() + "\u521b\u5efa\u5931\u8d25\u3002");
                return null;
            }
        }
        return destDir;
    }

    public static OrgDO getNewOrgDO(OrgDO orgDO, String categoryname) {
        OrgDO newOrgDO = new OrgDO();
        for (Map.Entry entry : orgDO.entrySet()) {
            newOrgDO.put((String)entry.getKey(), entry.getValue());
        }
        newOrgDO.setCategoryname(categoryname);
        return newOrgDO;
    }

    public static DataModelDO getDataModel(String categoryname) {
        DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDTO param = new DataModelDTO();
        param.setName(categoryname);
        param.setDeepClone(Boolean.valueOf(false));
        return dataModelClient.get(param);
    }

    public static R handleRequired(String categoryname, Map<String, List<OrgDO>> versionAndDataList) {
        if (CollectionUtils.isEmpty(versionAndDataList)) {
            return R.ok();
        }
        Set<DataModelColumn> requiredFields = TransferMigrationUtilts.getRequiredFields(categoryname);
        if (requiredFields.size() == 0) {
            return R.ok();
        }
        HashMap<String, OrgDO> oldDatas = new HashMap<String, OrgDO>();
        for (Map.Entry<String, List<OrgDO>> entry : versionAndDataList.entrySet()) {
            List<OrgDO> orgDOS = entry.getValue();
            for (OrgDO orgDO : orgDOS) {
                if (!TransferMigrationUtilts.isSetRequiredFields(orgDO, requiredFields)) continue;
                TransferMigrationUtilts.setRequiredFields(categoryname, orgDO, requiredFields, oldDatas);
            }
        }
        return R.ok();
    }

    public static boolean isSetRequiredFields(OrgDO orgDO, Set<DataModelColumn> requiredFields) {
        for (DataModelColumn requiredField : requiredFields) {
            if (orgDO.get((Object)TransferMigrationUtilts.getColumnName(requiredField)) != null) continue;
            return true;
        }
        return false;
    }

    public static boolean isSetRequiredField(OrgDO orgDO, DataModelColumn requiredField) {
        return orgDO.get((Object)TransferMigrationUtilts.getColumnName(requiredField)) == null;
    }

    public static void setRequiredFields(String categoryname, OrgDO orgDO, Set<DataModelColumn> requiredFields, Map<String, OrgDO> oldDatas) {
        OrgDO oldOrgDO = TransferMigrationUtilts.getRequiredOrgDO(categoryname, orgDO, oldDatas);
        if (oldOrgDO == null) {
            for (DataModelColumn requiredField : requiredFields) {
                if (!TransferMigrationUtilts.isSetRequiredField(orgDO, requiredField)) continue;
                Object value = TransferMigrationUtilts.getDefaultVal(requiredField);
                if (value == null) {
                    if (StringUtils.hasLength(requiredField.getMapping())) {
                        String tableName = requiredField.getMapping().split("\\.")[0];
                        value = TransferMigrationUtilts.getBaseDataValue(tableName);
                    }
                    if (value == null) {
                        value = TransferMigrationUtilts.getCusDefaultVal(requiredField);
                    }
                }
                orgDO.put(TransferMigrationUtilts.getColumnName(requiredField), value);
            }
        } else {
            for (DataModelColumn requiredField : requiredFields) {
                if (!TransferMigrationUtilts.isSetRequiredField(orgDO, requiredField)) continue;
                orgDO.put(TransferMigrationUtilts.getColumnName(requiredField), oldOrgDO.getValueOf(TransferMigrationUtilts.getColumnName(requiredField)));
            }
        }
    }

    public static Object getBaseDataValue(String tableName) {
        BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        BaseDataDTO baseDataParam = new BaseDataDTO();
        baseDataParam.setTableName(tableName);
        PageVO list = baseDataClient.list(baseDataParam);
        if (list != null && list.getTotal() > 0) {
            BaseDataDO baseDataDO = (BaseDataDO)list.getRows().get(0);
            return baseDataDO.getCode();
        }
        return null;
    }

    public static Object getDefaultVal(DataModelColumn requiredField) {
        return requiredField.getDefaultVal();
    }

    public static Object getCusDefaultVal(DataModelColumn requiredField) {
        DataModelType.ColumnType columnType = requiredField.getColumnType();
        switch (columnType) {
            case UUID: {
                return DataModelType.ColumnType.UUID.name();
            }
            case NVARCHAR: {
                return DataModelType.ColumnType.NVARCHAR.name();
            }
            case NUMERIC: 
            case INTEGER: {
                return 0;
            }
            case DATE: 
            case TIMESTAMP: {
                return new Date();
            }
            case CLOB: {
                return DataModelType.ColumnType.CLOB.name();
            }
        }
        return new Object();
    }

    public static OrgDO getRequiredOrgDO(String categoryname, OrgDO orgDO, Map<String, OrgDO> oldDatas) {
        String parentcode;
        OrgDO oldOrgDO = TransferMigrationUtilts.getOrgDO(categoryname, orgDO.getCode(), oldDatas);
        if (oldOrgDO == null && StringUtils.hasLength(parentcode = orgDO.getParentcode()) && !"-".equals(parentcode)) {
            oldOrgDO = TransferMigrationUtilts.getOrgDO(categoryname, parentcode, oldDatas);
        }
        return oldOrgDO;
    }

    public static OrgDO getOrgDO(String categoryname, String code, Map<String, OrgDO> oldDatas) {
        if (!oldDatas.containsKey(code)) {
            OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
            OrgVersionClient orgVersionClient = (OrgVersionClient)ApplicationContextRegister.getBean(OrgVersionClient.class);
            OrgVersionDTO paramVersion = new OrgVersionDTO();
            paramVersion.setCategoryname(categoryname);
            PageVO list = orgVersionClient.list(paramVersion);
            OrgDTO param = new OrgDTO();
            param.setCategoryname(categoryname);
            param.setCode(code);
            OrgDO orgDO = null;
            if (list != null && list.getTotal() > 0) {
                for (OrgVersionDO orgVersionDO : list.getRows()) {
                    param.setVersionDate(orgVersionDO.getValidtime());
                    orgDO = orgDataClient.get(param);
                    if (orgDO == null) continue;
                    break;
                }
            } else {
                orgDO = orgDataClient.get(param);
            }
            oldDatas.put(code, orgDO);
        }
        return oldDatas.get(code);
    }

    public static Set<DataModelColumn> getRequiredFields(String categoryname) {
        HashSet<DataModelColumn> requiredFields = new HashSet<DataModelColumn>();
        DataModelDO dataModel = TransferMigrationUtilts.getDataModel(categoryname);
        if (dataModel == null) {
            return requiredFields;
        }
        for (DataModelColumn column : dataModel.getColumns()) {
            if (DataModelType.ColumnAttr.SYSTEM.equals((Object)column.getColumnAttr()) || column.isNullable() == null || column.isNullable().booleanValue()) continue;
            requiredFields.add(column);
        }
        return requiredFields;
    }

    public static String getColumnName(DataModelColumn column) {
        return column.getColumnName().toLowerCase();
    }

    public static String getOrgVersionTitle(String validtime) {
        return validtime;
    }

    public static String getOrgVersionTitle(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String getOrgVersionTitle(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(time);
    }

    public static long StringToLong(String validtime) throws ParseException {
        return TransferMigrationUtilts.StringToDate(validtime).getTime();
    }

    public static Date StringToDate(String validtime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(validtime);
    }
}

