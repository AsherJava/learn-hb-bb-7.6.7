/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.maker.JIOParamMaker
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 */
package nr.single.para.paraout.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.maker.JIOParamMaker;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import nr.single.para.paraout.bean.ParaExportParam;
import nr.single.para.paraout.bean.ParaExportResult;
import nr.single.para.paraout.bean.TaskExportContext;
import nr.single.para.paraout.bean.exception.ParaExportException;
import nr.single.para.paraout.service.ITaskDefineEportService;
import nr.single.para.paraout.service.ITaskFileExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileExportServiceImpl
implements ITaskFileExportService {
    private static final Logger log = LoggerFactory.getLogger(TaskFileExportServiceImpl.class);
    @Autowired
    private ITaskDefineEportService taskDefineExportSerice;

    @Override
    public ParaExportResult exportNetFormSchemeToSingle(String formSchemeKey, String file, ParaExportParam option, AsyncTaskMonitor asyncMonitor) throws ParaExportException {
        try {
            String tempDir = SinglePathUtil.getExportTempFilePath((String)"jioParaExport");
            String taskDir = SinglePathUtil.createNewPath((String)tempDir, (String)(OrderGenerator.newOrder() + ".TSK"));
            ParaExportResult result = new ParaExportResult();
            TaskExportContext context = new TaskExportContext();
            context.setExportParam(option);
            context.setResult(result);
            context.setFormSchemeKey(formSchemeKey);
            ParaInfo paraInfo = new ParaInfo(taskDir);
            context.setParaInfo(paraInfo);
            this.taskDefineExportSerice.initCahce(context);
            this.taskDefineExportSerice.exportTaskGroupDefines(context);
            this.taskDefineExportSerice.exportTaskDefine(context);
            JIOParamMaker maker = new JIOParamMaker(paraInfo, taskDir);
            maker.make();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ParaExportException(ex.getMessage(), ex);
        }
        return null;
    }
}

