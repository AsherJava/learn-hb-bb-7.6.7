/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelTemplateVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  jodd.util.StringUtil
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerListEO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncTypeScheduler;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelTemplateVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportDataSyncUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncUtils.class);

    public static boolean delFiles(File file) {
        boolean result = false;
        if (file.isDirectory()) {
            File[] childrenFiles;
            for (File childFile : childrenFiles = file.listFiles()) {
                result = ReportDataSyncUtils.delFiles(childFile);
                LOGGER.info("\u5220\u9664\u6587\u4ef6\uff1a" + childFile.getAbsolutePath());
                if (result) continue;
                return result;
            }
        }
        result = file.delete();
        LOGGER.info("\u5220\u9664\u6587\u4ef6\uff1a" + file.getAbsolutePath());
        return result;
    }

    public static String getPeriodTitle(String periodStr) {
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(periodStr);
        return periodTitle;
    }

    public static String getBeforePriorPeriod(PeriodType periodType) {
        String periodStr = ReportDataSyncUtils.getCurrentPriorPeriod(periodType);
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        defaultPeriodAdapter.priorPeriod(periodWrapper);
        String priorPeriodStr = periodWrapper.toString();
        return priorPeriodStr;
    }

    public static String getCurrentPriorPeriod(PeriodType periodType) {
        PeriodWrapper currentPeriod = TaskPeriodUtils.getCurrentPeriod((int)periodType.type());
        String priorPeriodStr = currentPeriod.toString();
        return priorPeriodStr;
    }

    public static void createEmptyZipFile(String dataZipLocation) throws IOException {
        File zipFile = new File(dataZipLocation);
        if (!zipFile.exists()) {
            zipFile.createNewFile();
        }
        try (FileOutputStream fOutputStream = new FileOutputStream(zipFile);){
            ZipOutputStream zoutput = new ZipOutputStream(fOutputStream);
            Throwable throwable = null;
            if (zoutput != null) {
                if (throwable != null) {
                    try {
                        zoutput.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    zoutput.close();
                }
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage());
        }
    }

    public static List<MultilevelTemplateVO> getSyncMethod() {
        return SpringContextUtils.getBeans(ISyncMethodScheduler.class).stream().map(v -> new MultilevelTemplateVO(v.code(), v.name())).collect(Collectors.toList());
    }

    public static List<MultilevelTemplateVO> getSyncType() {
        return SpringContextUtils.getBeans(ISyncTypeScheduler.class).stream().map(v -> new MultilevelTemplateVO(v.code(), v.name())).collect(Collectors.toList());
    }

    public static List<MultilevelTemplateVO> listAllReportFileFormat() {
        return ReportFileFormat.listAllReportFileFormat().stream().map(v -> new MultilevelTemplateVO(v.getCode(), v.getName())).collect(Collectors.toList());
    }

    public static List<String> getOrgCodesByServerInfo(ReportDataSyncServerInfoVO serverInfoVO) {
        ArrayList<String> orgCodes = new ArrayList<String>();
        String orgCode = serverInfoVO.getOrgCode();
        orgCodes.add(orgCode);
        if (!CollectionUtils.isEmpty((Collection)serverInfoVO.getManageOrgCodes())) {
            orgCodes.addAll(serverInfoVO.getManageOrgCodes());
        }
        return orgCodes;
    }

    public static List<String> getOrgCodesByServerInfo(ReportDataSyncServerListEO serverInfoEO) {
        ArrayList<String> orgCodes = new ArrayList<String>();
        String orgCode = serverInfoEO.getOrgCode();
        orgCodes.add(orgCode);
        if (!StringUtil.isEmpty((CharSequence)serverInfoEO.getManageOrgCodes())) {
            orgCodes.addAll(Arrays.asList(serverInfoEO.getManageOrgCodes().split(";")));
        }
        return orgCodes;
    }
}

