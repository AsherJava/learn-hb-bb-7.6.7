/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService
 *  com.jiuqi.nr.io.util.DateUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  sun.misc.BASE64Decoder
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO;
import com.jiuqi.gcreport.reportdatasync.scheduler.impl.type.ReportDataSyncScheduler;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService;
import com.jiuqi.nr.io.util.DateUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

public class ReportDataSyncUtil {
    public static String SPLIT_SIGN = "`";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncUtil.class);

    public static String getSnFolderPath(String sn) {
        String filePath = System.getProperty("java.io.tmpdir");
        filePath = CommonReportUtil.createNewPath((String)filePath, (String)"sync");
        filePath = CommonReportUtil.createNewPath((String)filePath, (String)sn);
        return filePath;
    }

    public static String getFolderPath(String parentFolder, String currFolder) {
        return CommonReportUtil.createNewPath((String)parentFolder, (String)currFolder);
    }

    public static void writeFileJson(Object object, String filePath) throws IOException {
        String json = JsonUtils.writeValueAsString((Object)object);
        try (FileOutputStream out = new FileOutputStream(filePath);){
            out.write(json.getBytes());
            out.flush();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        File jsonFile = new File(fileName);
        if (!jsonFile.exists()) {
            return "{}";
        }
        try (InputStreamReader reader = new InputStreamReader((InputStream)new FileInputStream(jsonFile), "utf-8");){
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = ((Reader)reader).read()) != -1) {
                sb.append((char)ch);
            }
            String string = jsonStr = sb.toString();
            return string;
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return "{}";
        }
    }

    public static void writeBase64(String tableName, List<? extends DefaultTableEntity> dataList, String filePath) throws Exception {
        List<ColumnModelDefine> defines = ReportDataSyncUtil.getFieldDefines(tableName);
        Base64.Encoder encoder = Base64.getEncoder();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));){
            bw.write(ReportDataSyncUtil.headStr(defines));
            for (DefaultTableEntity defaultTableEntity : dataList) {
                String oneRowStr = ReportDataSyncUtil.oneRowData(defines, defaultTableEntity);
                oneRowStr = encoder.encodeToString(oneRowStr.getBytes());
                bw.newLine();
                bw.write(oneRowStr);
            }
            bw.flush();
        }
    }

    public static int readBase64Db(String fileName, String tableName) {
        List<ColumnModelDefine> defines = ReportDataSyncUtil.getFieldDefines(tableName);
        Map<String, ColumnModelType> columnCode2FieldType = defines.stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getColumnType, (e1, e2) -> e1));
        File txtFile = new File(fileName);
        if (!txtFile.exists()) {
            return 0;
        }
        int rowCount = 0;
        BASE64Decoder decoder = new BASE64Decoder();
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile));){
            String oneRow = reader.readLine();
            String[] columnCodes = oneRow.split(SPLIT_SIGN);
            while ((oneRow = reader.readLine()) != null) {
                oneRow = new String(decoder.decodeBuffer(oneRow));
                DefinitionValueV entity = new DefinitionValueV();
                String[] rowDatas = oneRow.split(SPLIT_SIGN);
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)GcAuthorityType.NONE);
                String unitCode = null;
                for (int i = 0; i < columnCodes.length && i < rowDatas.length; ++i) {
                    ColumnModelType fieldType = columnCode2FieldType.get(columnCodes[i]);
                    entity.addFieldValue(columnCodes[i], ReportDataSyncUtil.dataTransferOri(fieldType, rowDatas[i]));
                    if (!columnCodes[i].equals("UNITCODE")) continue;
                    unitCode = rowDatas[i];
                }
                if (!StringUtils.isEmpty(unitCode) && !unitCode.equals("-") && tool.getOrgByCode(unitCode) == null) {
                    LOGGER.info("\u4ee3\u7801" + unitCode + "\u7684\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u8df3\u8fc7\u8be5\u6761\u5355\u4f4d");
                    continue;
                }
                EntNativeSqlDefaultDao.newInstance((String)tableName, DefinitionValueV.class).add((BaseEntity)entity);
                ++rowCount;
            }
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rowCount;
    }

    public static void writeWorkbook(SXSSFWorkbook workbook, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileOutputStream out = new FileOutputStream(filePath);){
            workbook.write(out);
            out.flush();
        }
    }

    public static Workbook readWorkBook(String filePath) {
        Workbook workbook;
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream in = new FileInputStream(file);){
            try {
                workbook = new XSSFWorkbook(in);
            }
            catch (Exception ex) {
                workbook = new HSSFWorkbook(in);
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u89e3\u6790\u5931\u8d25:" + filePath, (Throwable)e);
        }
        return workbook;
    }

    public static List<ColumnModelDefine> getFieldDefines(String tableName) {
        try {
            DataModelService modelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine tableModel = modelService.getTableModelDefineByName(tableName);
            return modelService.getColumnModelDefinesByTable(tableModel.getID());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    public static Object dataTransferOri(ColumnModelType fieldType, String data) {
        if (StringUtils.isEmpty((String)data) || null == fieldType) {
            return null;
        }
        switch (fieldType) {
            case DATETIME: {
                return DateUtil.getOriDatetime((String)data, (String)"yyyy-MM-dd");
            }
            case DOUBLE: 
            case INTEGER: 
            case BIGDECIMAL: {
                return new BigDecimal(data);
            }
            case BOOLEAN: {
                return Boolean.valueOf(data) != false || data.equals("1");
            }
            case STRING: {
                return data;
            }
        }
        return data;
    }

    private static String headStr(List<ColumnModelDefine> defines) {
        StringBuffer oneRowStr = new StringBuffer(128);
        for (ColumnModelDefine define : defines) {
            oneRowStr.append(define.getCode()).append(SPLIT_SIGN);
        }
        oneRowStr.setLength(oneRowStr.length() - 1);
        return oneRowStr.toString();
    }

    private static String oneRowData(List<ColumnModelDefine> defines, DefaultTableEntity entity) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer oneRowStr = new StringBuffer(128);
        for (ColumnModelDefine define : defines) {
            Object value = entity.getFieldValue(define.getCode());
            if (null == value) {
                value = "";
            } else if (define.getColumnType() == ColumnModelType.DATETIME) {
                value = df.format(value);
            }
            oneRowStr.append(value).append(SPLIT_SIGN);
        }
        oneRowStr.setLength(oneRowStr.length() - 1);
        return oneRowStr.toString();
    }

    public static <T extends Collection<String>> T appendImportMsgIfEmpty(T msgList) {
        if (null == msgList) {
            return null;
        }
        while (msgList.remove(null)) {
        }
        if (msgList.isEmpty()) {
            msgList.add((String)"\u5bfc\u5165\u6210\u529f");
        }
        return msgList;
    }

    public static void addMessage(ProgressDataImpl<Set> progressData, String funcTitle, String msg) {
        ((Set)progressData.getResult()).add(String.format("\u3010%1$s\u3011%2$s", funcTitle, msg));
        progressData.addProgressValueAndRefresh(0.0);
    }

    public static boolean isEmptyJson(String json) {
        if (StringUtils.isEmpty((String)json)) {
            return true;
        }
        return json.length() <= 2;
    }

    public static String createNewPath(String filePath, String pathName) {
        return CommonReportUtil.createNewPath((String)filePath, (String)pathName);
    }

    public static Map<String, OrgTypeTreeVO> unitVersionId2TitleMap() {
        String sql = "select ID,TITLE,CATEGORYNAME,VALIDTIME,INVALIDTIME from MD_ORG_VERSION";
        List result = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
        Map<String, OrgTypeTreeVO> versionId2TitleMap = result.stream().collect(Collectors.toMap(item -> (String)item.get("ID"), item -> {
            OrgTypeTreeVO orgTypeTreeVO = new OrgTypeTreeVO();
            orgTypeTreeVO.setId((String)item.get("ID"));
            orgTypeTreeVO.setTitle((String)item.get("TITLE"));
            orgTypeTreeVO.setCategoryName((String)item.get("CATEGORYNAME"));
            orgTypeTreeVO.setValidTime((Date)item.get("VALIDTIME"));
            orgTypeTreeVO.setInvalidTime((Date)item.get("INVALIDTIME"));
            return orgTypeTreeVO;
        }));
        return versionId2TitleMap;
    }

    public static void unzipFolder(String zipFilePath, String folderName, String destDirPath) {
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));){
            File dir;
            String filePath;
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                filePath = destDirPath + File.separator + zipEntry.getName();
                if (zipEntry.isDirectory() && zipEntry.getName().contains(folderName)) {
                    dir = new File(filePath);
                    dir.mkdir();
                    break;
                }
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
            while (zipEntry != null) {
                if (!zipEntry.getName().startsWith(folderName)) {
                    break;
                }
                filePath = destDirPath + zipEntry.getName();
                if (!zipEntry.isDirectory()) {
                    ReportDataSyncUtil.extractFile(zipInputStream, filePath);
                } else {
                    dir = new File(filePath);
                    dir.mkdir();
                }
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    private static void extractFile(ZipInputStream zipInputStream, String filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);){
            int len;
            byte[] buffer = new byte[1024];
            while ((len = zipInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    public static String getSchemeIdByPeriodAndTask(String taskId, String periodStr) {
        String schemeId;
        RuntimeViewController runtimeViewController = (RuntimeViewController)SpringUtil.getBean(RuntimeViewController.class);
        try {
            schemeId = runtimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskId).getSchemeKey();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5f53\u524d\u4efb\u52a1\u548c\u65f6\u671f\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5931\u8d25");
        }
        return schemeId;
    }

    public static String getSchemeIdByPeriodOffsetAndTask(String taskId, Integer periodOffSet) {
        String schemeId;
        RuntimeViewController runtimeViewController = (RuntimeViewController)SpringUtil.getBean(RuntimeViewController.class);
        RunTimeTaskDefineService taskDefineService = (RunTimeTaskDefineService)SpringUtil.getBean(RunTimeTaskDefineService.class);
        try {
            TaskDefine taskDefine = taskDefineService.queryTaskDefine(taskId);
            PeriodType taskPeriodType = taskDefine.getPeriodType();
            PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)taskPeriodType.type(), (int)periodOffSet);
            String periodStr = currentPeriod.toString();
            schemeId = runtimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskId).getSchemeKey();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5f53\u524d\u4efb\u52a1\u548c\u65f6\u671f\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5931\u8d25");
        }
        return schemeId;
    }

    public static String getFileFormat() {
        ReportDataSyncScheduler reportDataSyncScheduler = (ReportDataSyncScheduler)SpringUtil.getBean(ReportDataSyncScheduler.class);
        List<ReportDataSyncServerInfoVO> serverInfoVO = reportDataSyncScheduler.getServerInfoVOList();
        if (CollectionUtils.isEmpty(serverInfoVO)) {
            return "nrd";
        }
        return serverInfoVO.get(0).getFileFormat();
    }

    public static void checkIsSystemIdentity() {
        String userID;
        SystemIdentityService identityService = (SystemIdentityService)SpringUtil.getBean(SystemIdentityService.class);
        if (!identityService.isSystemIdentity(userID = NpContextHolder.getContext().getUserId())) {
            throw new BusinessRuntimeException("\u975e\u7ba1\u7406\u5458\u7528\u6237\u6ca1\u6709\u6743\u9650");
        }
    }
}

