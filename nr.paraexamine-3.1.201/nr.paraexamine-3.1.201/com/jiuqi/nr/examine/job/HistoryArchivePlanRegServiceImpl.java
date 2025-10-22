/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  net.lingala.zip4j.ZipFile
 *  net.lingala.zip4j.model.ZipParameters
 *  net.lingala.zip4j.model.enums.AesKeyStrength
 *  net.lingala.zip4j.model.enums.CompressionLevel
 *  net.lingala.zip4j.model.enums.CompressionMethod
 *  net.lingala.zip4j.model.enums.EncryptionMethod
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.examine.job;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.examine.job.service.HistoryArchivePlanRegService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class HistoryArchivePlanRegServiceImpl
implements HistoryArchivePlanRegService {
    static final int[] PERIOD_TYPE_CODEs = new int[]{32, 78, 72, 74, 89, 88, 82, 90};

    @Override
    public void removeFile(File file, Logger logger) {
        boolean deleteRst;
        if (file != null && file.exists() && (deleteRst = file.delete()) && logger.isInfoEnabled()) {
            logger.info(String.format("\u6210\u529f\u5220\u9664[%s]\u6587\u4ef6", file.getName()));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void zipFile(String zipFileAbsolutePath, boolean zipPwdSwitch, String pwd, ArrayList<File> files, Logger logger) throws IOException {
        File oldZipFile = new File(zipFileAbsolutePath);
        this.removeFile(oldZipFile, logger);
        try (ZipFile zipFile = new ZipFile(zipFileAbsolutePath, pwd.toCharArray());){
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionLevel(CompressionLevel.NORMAL);
            parameters.setCompressionMethod(CompressionMethod.DEFLATE);
            if (zipPwdSwitch) {
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(EncryptionMethod.AES);
                parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
            }
            zipFile.addFiles(files, parameters);
        }
    }

    @Override
    public boolean checkCustomPeriod(Map<String, String> fcCodeDatetimeMap) {
        boolean hasFound = false;
        for (String fcCode : fcCodeDatetimeMap.keySet()) {
            int[] nArray = PERIOD_TYPE_CODEs;
            int n = nArray.length;
            for (int i = 0; i < n; ++i) {
                Integer i2 = nArray[i];
                if (!i2.toString().equals(fcCodeDatetimeMap.get(fcCode))) continue;
                hasFound = true;
                break;
            }
            if (hasFound) continue;
            return true;
        }
        return false;
    }

    @Override
    public Map<String, String> getFcCodeDatetimeMap(JdbcTemplate jdbcTemplate, String sql, String para1, String fcCode, String timeType, Logger logger) {
        HashMap<String, String> fcCodeDatetimeMap = new HashMap<String, String>();
        try {
            List resultList = jdbcTemplate.queryForList(sql, new Object[]{para1});
            for (Map m : resultList) {
                fcCodeDatetimeMap.put(String.valueOf(m.get(fcCode)), m.get(timeType).toString());
            }
        }
        catch (Exception e) {
            logger.error(String.format("\u67e5\u8be2\u4efb\u52a1\u524d\u7f00\u4e0e\u65f6\u671f\u7c7b\u578b\u6620\u5c04\u5173\u7cfb\u5f02\u5e38\uff1a%s", e.getMessage()), e);
        }
        return fcCodeDatetimeMap;
    }

    @Override
    public Map<String, String> getFcCodeDatetimeMap(JdbcTemplate jdbcTemplate, String sql, String para1, String para2, String fcCode, String timeType, Logger logger) {
        HashMap<String, String> fcCodeDatetimeMap = new HashMap<String, String>();
        try {
            List resultList = jdbcTemplate.queryForList(sql, new Object[]{para1, para2});
            for (Map m : resultList) {
                fcCodeDatetimeMap.put(String.valueOf(m.get(fcCode)), m.get(timeType).toString());
            }
        }
        catch (Exception e) {
            logger.error(String.format("\u67e5\u8be2\u4efb\u52a1\u524d\u7f00\u4e0e\u65f6\u671f\u7c7b\u578b\u6620\u5c04\u5173\u7cfb\u5f02\u5e38\uff1a%s", e.getMessage()), e);
        }
        return fcCodeDatetimeMap;
    }

    @Override
    public Map<String, String> getArchiveNums(int archiveNums, GregorianCalendar calendar) {
        HashMap<String, String> datetimeMap = new HashMap<String, String>();
        for (int i = 0; i < PERIOD_TYPE_CODEs.length; ++i) {
            if (0 == i) continue;
            datetimeMap.put(Character.toString((char)PERIOD_TYPE_CODEs[i]), PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)i, (int)(-archiveNums)).toString());
        }
        return datetimeMap;
    }

    @Override
    public Object getArchiveNums(int archiveNums, GregorianCalendar calendar, Map<String, String> fcCodeDatetimeMap, IPeriodEntityAdapter periodAdapter, Logger logger, String frontend, String backend) {
        HashMap<String, String> datetimeMap = new HashMap<String, String>();
        Object[] result = new Object[2];
        for (int i = 0; i < PERIOD_TYPE_CODEs.length; ++i) {
            if (0 == i) continue;
            datetimeMap.put(Character.toString((char)PERIOD_TYPE_CODEs[i]), PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)i, (int)(-archiveNums)).toString());
        }
        boolean hasFound = false;
        for (String fcCode : fcCodeDatetimeMap.keySet()) {
            for (int i : PERIOD_TYPE_CODEs) {
                if (!Character.toString((char)i).equals(fcCodeDatetimeMap.get(fcCode))) continue;
                hasFound = true;
                break;
            }
            if (!hasFound) {
                IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(fcCodeDatetimeMap.get(fcCode));
                String currPeriod = periodProvider.getCurPeriod().getCode();
                String archivePeriod = "";
                String tempPeriod = currPeriod;
                for (int i = 1; i <= archiveNums; ++i) {
                    archivePeriod = periodProvider.priorPeriod(tempPeriod);
                    if (i >= archiveNums) continue;
                    tempPeriod = archivePeriod;
                }
                if (archivePeriod.equals(tempPeriod)) {
                    fcCodeDatetimeMap.remove(fcCode);
                    logger.info(String.format(frontend + "%s" + backend + "\u8868\u6ca1\u6709\u9700\u8981\u5f52\u6863\u7684\u6570\u636e\u3002", fcCode));
                    break;
                }
                datetimeMap.put(fcCodeDatetimeMap.get(fcCode), archivePeriod);
            }
            hasFound = false;
        }
        result[0] = fcCodeDatetimeMap;
        result[1] = datetimeMap;
        return result;
    }

    @Override
    public Object csvFile(JdbcTemplate jdbcTemplate, Map<String, String> fcCodeDatetimeMap, Map<String, String> datetimeMap, String archivePath, Logger logger, String frontend, String backend) {
        ArrayList<File> files = new ArrayList<File>();
        boolean hasFile = false;
        ArrayList<String> deleteSqlList = new ArrayList<String>();
        Object[] result = new Object[3];
        for (String fc_code : fcCodeDatetimeMap.keySet()) {
            String sql = "SELECT * FROM " + frontend + fc_code + backend + " WHERE PERIOD <= '" + datetimeMap.get(fcCodeDatetimeMap.get(fc_code)) + "'";
            logger.debug(sql);
            List list = jdbcTemplate.queryForList(sql);
            if (!list.isEmpty()) {
                hasFile = true;
                deleteSqlList.add("DELETE FROM " + frontend + fc_code + backend + " WHERE PERIOD <= '" + datetimeMap.get(fcCodeDatetimeMap.get(fc_code)) + "'");
                File dir = new File(archivePath);
                logger.info(String.format("\u5f00\u59cb\u5f52\u6863" + frontend + "%s" + backend + "\u8868\u7684\u6570\u636e", fc_code));
                String fileName = frontend + fc_code + backend + "_BEFORE_" + datetimeMap.get(fcCodeDatetimeMap.get(fc_code));
                String fileAbsolutePath = archivePath + File.separator + fileName + ".csv";
                try {
                    PathUtils.validatePathManipulation((String)fileAbsolutePath);
                }
                catch (SecurityContentException e) {
                    logger.error(String.format("\u65e5\u5fd7\u5f52\u6863\u5f02\u5e38\uff1a%s", e.getMessage()), e);
                }
                File file = new File(fileAbsolutePath);
                this.removeFile(file, logger);
                files.add(file);
                try {
                    FileWriter fileWriter = new FileWriter(fileAbsolutePath);
                    Throwable throwable = null;
                    try {
                        BufferedWriter out = new BufferedWriter(fileWriter);
                        Throwable throwable2 = null;
                        try {
                            List rows = jdbcTemplate.queryForList(sql);
                            StringBuilder csvBuilder = new StringBuilder();
                            for (String columnName : ((Map)rows.get(0)).keySet()) {
                                csvBuilder.append(columnName).append(",");
                            }
                            csvBuilder.deleteCharAt(csvBuilder.length() - 1).append("\n");
                            for (Map row : rows) {
                                for (Object value : row.values()) {
                                    csvBuilder.append(value).append(",");
                                }
                                csvBuilder.deleteCharAt(csvBuilder.length() - 1).append("\n");
                            }
                            out.write(csvBuilder.toString());
                            out.flush();
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (out == null) continue;
                            if (throwable2 != null) {
                                try {
                                    out.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            out.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (fileWriter == null) continue;
                        if (throwable != null) {
                            try {
                                fileWriter.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        fileWriter.close();
                    }
                }
                catch (Exception e) {
                    logger.error(String.format("\u65e5\u5fd7\u5f52\u6863\u5f02\u5e38\uff1a%s", e.getMessage()), e);
                }
                continue;
            }
            logger.info(String.format(frontend + "%s" + backend + "\u8868\u6ca1\u6709\u9700\u8981\u5f52\u6863\u7684\u6570\u636e\u3002", fc_code));
        }
        result[0] = hasFile;
        result[1] = files;
        result[2] = deleteSqlList;
        return result;
    }

    @Override
    public void zipCSVFiles(JdbcTemplate jdbcTemplate, GregorianCalendar calendar, String tableName, String archivePath, boolean haspwd, String zippwd, ArrayList<File> files, Logger logger, ArrayList<String> deleteSqlList) {
        String zipFileName = tableName + "_" + String.valueOf(calendar.get(1)) + String.valueOf(calendar.get(2) + 1) + String.valueOf(calendar.get(5)) + "_" + String.valueOf(calendar.get(11)) + String.valueOf(calendar.get(12)) + String.valueOf(calendar.get(13)) + ".zip";
        String zipFileAbsolutePath = archivePath + File.separator + zipFileName;
        try {
            this.zipFile(zipFileAbsolutePath, haspwd, zippwd, files, logger);
            for (File file : files) {
                this.removeFile(file, logger);
            }
            logger.info(String.format("[%s]\u65e5\u5fd7\u5f52\u6863\u6587\u4ef6\u538b\u7f29\u5b8c\u6210", zipFileName));
            for (String deleteSql : deleteSqlList) {
                try {
                    jdbcTemplate.execute(deleteSql);
                }
                catch (Exception e) {
                    logger.error(String.format("\u5220\u9664\u5386\u53f2\u8868\u5f52\u6863\u6570\u636e\u5931\u8d25\uff0cSQL\u4e3a\uff1a%s", e.getMessage()), (Object)deleteSql);
                }
            }
        }
        catch (Exception e) {
            logger.error(String.format("\u65e5\u5fd7\u5f52\u6863\u5f02\u5e38\uff1a%s", e.getMessage()), e);
        }
    }
}

