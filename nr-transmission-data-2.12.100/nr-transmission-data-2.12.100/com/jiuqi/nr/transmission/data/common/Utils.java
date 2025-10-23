/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.file.exception.FileException
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.utils.FileUtils
 *  com.jiuqi.nr.fileupload.exception.FileOssException
 */
package com.jiuqi.nr.transmission.data.common;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.file.exception.FileException;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.utils.FileUtils;
import com.jiuqi.nr.fileupload.exception.FileOssException;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.intf.DataImportMessage;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.service.ISyncSchemeService;
import com.jiuqi.nr.transmission.data.service.impl.SyncSchemeServiceImpl;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private static final String TRANSMISSION_FILE_NAME = "NR_TRANSMISSION";
    private static final ISyncSchemeService syncSchemeService = (ISyncSchemeService)BeanUtil.getBean(SyncSchemeServiceImpl.class);
    private static final IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);

    public static void deleteAllFilesOfDir(File path) {
        if (path.exists()) {
            if (path.isFile()) {
                path.delete();
                return;
            }
            try {
                PathUtils.validatePathManipulation((String)path.getPath());
                org.apache.commons.io.FileUtils.deleteDirectory(path);
            }
            catch (Exception e) {
                logger.error("\u5220\u9664\u4e34\u65f6\u6587\u4ef6\u5931\u8d25" + e.getMessage());
            }
        } else {
            logger.info("\u8981\u5220\u9664\u4e34\u65f6\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
    }

    public static void deleteAllFilesOfDirByPath(String path) {
        if (StringUtils.hasText(path)) {
            File tempFile2 = new File(path);
            Utils.deleteAllFilesOfDir(tempFile2);
        }
    }

    public static Comparator<Object> getChineseComparator() {
        return Collator.getInstance(Locale.CHINA);
    }

    public static ObjectStorageService objService() throws ObjectStorageException {
        ObjectStorageService temporaryObjectService = ObjectStorageManager.getInstance().createTemporaryObjectService();
        return temporaryObjectService;
    }

    public static String fileUpload(InputStream input) throws IOException {
        try {
            if (input == null) {
                throw new IllegalArgumentException("\u6587\u4ef6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            }
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            String newKey = FileUtils.generateFileKey();
            ObjectInfo info = new ObjectInfo();
            info.setKey(newKey);
            info.setOwner(currentUser);
            Utils.objService().upload(newKey, input, info);
            return newKey;
        }
        catch (Exception e) {
            throw new FileException("\u6587\u4ef6\u4fdd\u5b58\u5931\u8d25\uff01", (Throwable)e);
        }
    }

    public static void fileDownLoad(String fileKey, OutputStream outputStream) throws Exception {
        boolean b = false;
        ObjectStorageService objectStorageService = null;
        try {
            objectStorageService = Utils.objService();
            b = objectStorageService.existObject(fileKey);
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
        }
        if (!b) {
            throw new FileOssException("\u6587\u4ef6\u4e0d\u5b58\u5728\uff01");
        }
        try {
            InputStream download = objectStorageService.download(fileKey);
            if (download != null) {
                Utils.writeInput2Output(outputStream, download);
            }
        }
        catch (Exception e) {
            throw new FileOssException("\u6587\u4ef6\u4e0d\u5b58\u5728\uff01", (Throwable)e);
        }
    }

    public static boolean fileDelete(String fileKey) {
        boolean deleteResult = true;
        try {
            boolean b = Utils.objService().existObject(fileKey);
            if (b) {
                deleteResult = Utils.objService().deleteObject(fileKey);
            }
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
            return false;
        }
        return deleteResult;
    }

    public static void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    public static DimensionValueSet getDimensionValueSetWithOutDim(DimensionValueSet dimensionValueSet, String dimensionName) {
        DimensionValueSet dimensionSet = new DimensionValueSet();
        dimensionSet.setValue("DATATIME", dimensionValueSet.getValue("DATATIME"));
        dimensionSet.setValue(dimensionName, dimensionValueSet.getValue(dimensionName));
        if (dimensionValueSet.getValue("ADJUST") != null) {
            dimensionSet.setValue("ADJUST", dimensionValueSet.getValue("ADJUST"));
        }
        return dimensionSet;
    }

    public static String getLog(ILogHelper logHelper) {
        List<String> logs = logHelper.getLogs();
        return String.join((CharSequence)"\r\n", logs);
    }

    public static void addSyncResult(DataImportResult targetResult, DataImportResult sourceResult) {
        if (!CollectionUtils.isEmpty(sourceResult.getSuccessFormulaCheckScheme())) {
            targetResult.getSuccessFormulaCheckScheme().addAll(sourceResult.getSuccessFormulaCheckScheme());
        }
        if (!CollectionUtils.isEmpty(sourceResult.getFailFormulaCheckSchemes())) {
            targetResult.addFailFormulaCheckSchemes(sourceResult.getFailFormulaCheckSchemes());
        }
        if (!CollectionUtils.isEmpty(sourceResult.getFailUnits())) {
            targetResult.addFailUnits(sourceResult.getFailUnits());
        }
        if (!CollectionUtils.isEmpty(sourceResult.getFailForms())) {
            targetResult.addFailForms(sourceResult.getFailForms());
        }
        targetResult.sum(sourceResult);
    }

    public static boolean isEnglish() {
        NpContext context = NpContextHolder.getContext();
        Locale locale = context.getLocale();
        return Locale.US.equals(locale);
    }

    public static String getSchemeName(SyncSchemeParamDTO param) {
        String schemeName = param.getSchemeName();
        if (!StringUtils.hasText(schemeName)) {
            SyncSchemeDTO syncSchemeDTO;
            schemeName = StringUtils.hasText(param.getSchemeKey()) ? (StringUtils.hasText((syncSchemeDTO = syncSchemeService.getWithOutParam(param.getSchemeKey())).getTitle()) ? syncSchemeDTO.getTitle() : (Utils.isEnglish() ? "SyncScheme" : "\u540c\u6b65\u65b9\u6848")) : (Utils.isEnglish() ? "SyncScheme" : "\u540c\u6b65\u65b9\u6848");
            param.setSchemeName(schemeName);
        }
        return schemeName;
    }

    public static void setMessage(Collection<String> units, Map<String, EntityInfoParam> entityRowMap, StringBuilder sbs) {
        int i = 0;
        for (String s : units) {
            EntityInfoParam entityRow = entityRowMap.get(s);
            if (entityRow != null) {
                sbs.append(entityRow.getTitle()).append("[").append(entityRow.getEntityKeyData()).append("]");
            } else {
                sbs.append("[").append(s).append("]");
            }
            if (i < units.size() - 1) {
                sbs.append("\uff0c");
            } else {
                sbs.append("\u3002");
            }
            ++i;
        }
    }

    public static String getFormScheme(String taskKey, String periodValue) {
        String formSchemeKey = null;
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodValue, taskKey);
            formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException(String.format("\u67e5\u8be2\u4efb\u52a1:%s\u65f6\u671f:%s\u5173\u8054\u7684\u65b9\u6848\u65f6\uff0c\u53d1\u751f\u5f02\u5e38", taskKey, periodValue));
        }
        return formSchemeKey;
    }

    public static void allowEditingUnitError(DataImportResult dataImportResult, ILogHelper logHelper, List<String> noExistUnit) {
        Map<String, List<DataImportMessage>> failUnits = dataImportResult.getFailUnits();
        String noName = MultilingualLog.fmdmDataImportMessage(8, "");
        String unitInsertErrorMessage = MultilingualLog.fmdmDataImportMessage(11, "");
        logHelper.appendLog(unitInsertErrorMessage);
        noExistUnit.forEach(a -> failUnits.computeIfAbsent((String)a, key -> new ArrayList()).add(new DataImportMessage(noName, (String)a, unitInsertErrorMessage)));
    }
}

