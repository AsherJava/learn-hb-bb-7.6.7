/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.TaskResultEnum
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.io.record.service.ImportHistoryExportService
 *  com.jiuqi.nr.tds.Costs
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 */
package com.jiuqi.nr.nrdx.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.TaskResultEnum;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.io.record.service.ImportHistoryExportService;
import com.jiuqi.nr.nrdx.data.dto.FileDTO;
import com.jiuqi.nr.tds.Costs;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="NR", groupTitle="NRDX\u65e5\u5fd7\u6570\u636e\u5bfc\u51fa")
public class NRDXLogExportJob
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(NRDXLogExportJob.class);
    private static final long serialVersionUID = 1063636352646656L;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void executeWithNpContext(JobContext jobContext) {
        String recKey = this.getArgs();
        File file = null;
        this.asyncTaskStepMonitor.startTask("\u5bfc\u51fa\u7cfb\u7edf\u65e5\u5fd7", 2);
        try {
            ImportHistoryExportService exportService = (ImportHistoryExportService)SpringBeanUtils.getBean(ImportHistoryExportService.class);
            LocalTime hour = LocalTime.now().withMinute(0).withSecond(0).withNano(0);
            String hourStr = hour.format(Costs.FORMATTER);
            String path = Costs.TEMP_DIR + LocalDate.now() + Costs.FILE_SEPARATOR + hourStr + Costs.FILE_SEPARATOR + OrderGenerator.newOrder() + Costs.FILE_SEPARATOR;
            String fileName = "\u5bfc\u5165\u7ed3\u679c.xlsx";
            file = new File(path + fileName);
            Costs.createPathIfNotExists((Path)new File(path).toPath());
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                exportService.exportLog(recKey, (OutputStream)fileOutputStream);
            }
            this.asyncTaskStepMonitor.stepIn("\u751f\u6210\u65e5\u5fd7\u5b8c\u6210");
            var10_11 = null;
            try (ObjectStorageService service = ObjectStorageManager.getInstance().createObjectService("TEMP");
                 FileInputStream fileInputStream = new FileInputStream(file);){
                String key = UUIDUtils.getKey();
                key = key.replace("-", "");
                ObjectInfo objectInfo = new ObjectInfo();
                objectInfo.setKey(key);
                objectInfo.setName(fileName);
                objectInfo.setOwner(NpContextHolder.getContext().getUserId());
                service.upload(key, (InputStream)fileInputStream, objectInfo);
                FileDTO res = new FileDTO();
                res.setDownloadKey(key);
                String result = this.objectMapper.writeValueAsString((Object)res);
                this.asyncTaskStepMonitor.stepIn("\u65e5\u5fd7\u6587\u4ef6\u4e0a\u4f20\u6210\u529f");
                this.asyncTaskStepMonitor.finishTask("\u5bfc\u51fa\u7cfb\u7edf\u65e5\u5fd7", "\u5bfc\u51fa\u5b8c\u6210", result);
            }
            catch (Throwable throwable) {
                var10_11 = throwable;
                throw throwable;
            }
            if (file == null) return;
        }
        catch (Exception e) {
            log.error("\u5bfc\u51fa NRDX \u65e5\u5fd7\u5931\u8d25", e);
            this.asyncTaskStepMonitor.finishTask("\u5bfc\u51fa\u7cfb\u7edf\u65e5\u5fd7", "\u5bfc\u51fa\u5931\u8d25", null, TaskResultEnum.FAILURE);
            return;
        }
        finally {
            if (file != null) {
                FileUtils.deleteQuietly(file);
            }
        }
        FileUtils.deleteQuietly(file);
        return;
    }
}

