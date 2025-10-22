/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  nr.single.map.configurations.bean.SingleFileInfo
 *  nr.single.map.configurations.service.MappingFileService
 *  nr.single.para.configurations.service.FileAnalysisService
 *  org.springframework.transaction.annotation.Transactional
 */
package nr.single.client.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import java.io.FileNotFoundException;
import nr.single.client.bean.SingleExportData;
import nr.single.client.bean.SingleExportParam;
import nr.single.client.internal.service.export.ExportJIOServiceImpl;
import nr.single.client.service.ISingleExportService;
import nr.single.map.configurations.bean.SingleFileInfo;
import nr.single.map.configurations.service.MappingFileService;
import nr.single.para.configurations.service.FileAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="EXPORT_JIO_PARA_MAP")
@Transactional(rollbackFor={NpRollbackException.class})
public class ExportJIOParaMapServiceImpl
implements ISingleExportService {
    private static final Logger log = LoggerFactory.getLogger(ExportJIOServiceImpl.class);
    @Autowired
    private FileAnalysisService fileAnalysisService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MappingFileService mappingFileService;

    @Override
    public SingleExportData export(SingleExportParam param, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        SingleFileInfo sinfo;
        String configKey;
        SingleExportData data = null;
        if (null != asyncTaskMonitor) {
            asyncTaskMonitor.progressAndMessage(0.1, "");
        }
        if (StringUtils.isNotEmpty((String)(configKey = param.getConfigKey())) && (sinfo = this.mappingFileService.queryFileInfo(configKey)) != null) {
            FileAreaService singleFile = this.fileService.area("single");
            FileInfo info = singleFile.getInfo(sinfo.getJioKey());
            if (info == null) {
                throw new FileNotFoundException(sinfo.getJioKey());
            }
            String fileName = info.getName();
            byte[] Buffer = singleFile.download(sinfo.getJioKey());
            data = new SingleExportData(fileName, Buffer);
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.progressAndMessage(0.98, "");
            }
        }
        return data;
    }
}

