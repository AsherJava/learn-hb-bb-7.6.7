/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.exportprocess.service.impl;

import com.jiuqi.gcreport.common.exportprocess.dto.ExportProcess;
import com.jiuqi.gcreport.common.exportprocess.service.GcExportProcessService;
import java.lang.ref.SoftReference;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class GcExportProcessServiceImpl
implements GcExportProcessService {
    private static ConcurrentHashMap<String, SoftReference<ExportProcess>> exportProcessMap = new ConcurrentHashMap();
    private Date nextRemovedDate = new Date();

    @Override
    public void setExportProcess(String sn, ExportProcess exportProcess) {
        if (null == sn) {
            return;
        }
        SoftReference<ExportProcess> exportProcessSoftReference = new SoftReference<ExportProcess>(exportProcess);
        exportProcessMap.put(sn, exportProcessSoftReference);
    }

    @Override
    public ExportProcess getExportProcess(String sn) {
        SoftReference<ExportProcess> exportProcessSoftReference = exportProcessMap.get(sn);
        if (null == exportProcessSoftReference) {
            return null;
        }
        return exportProcessSoftReference.get();
    }

    @Override
    public void removeExportProcess(String sn) {
        exportProcessMap.remove(sn);
        Date currDate = new Date();
        if (this.nextRemovedDate.before(currDate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(5, -2);
            Date beforeTwoDay = calendar.getTime();
            for (Map.Entry<String, SoftReference<ExportProcess>> uuidSoftReferenceEntry : exportProcessMap.entrySet()) {
                SoftReference<ExportProcess> exportProcessSoftReference = uuidSoftReferenceEntry.getValue();
                ExportProcess process = exportProcessSoftReference.get();
                if (null == process) {
                    exportProcessMap.remove(uuidSoftReferenceEntry.getKey());
                    continue;
                }
                if (null == process || null == process.getCreateDate() || !process.getCreateDate().before(beforeTwoDay)) continue;
                exportProcessMap.remove(uuidSoftReferenceEntry.getKey());
            }
            this.nextRemovedDate = currDate;
        }
    }

    @Override
    public ExportProcess getProcess(String sn) {
        ExportProcess exportProcess = this.getExportProcess(sn);
        if (null == exportProcess) {
            return new ExportProcess();
        }
        if (exportProcess.isFinish()) {
            this.removeExportProcess(sn);
        }
        return exportProcess;
    }
}

