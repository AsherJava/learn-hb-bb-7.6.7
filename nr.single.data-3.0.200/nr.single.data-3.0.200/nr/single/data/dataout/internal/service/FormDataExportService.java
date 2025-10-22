/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.single.core.internal.file.SingleFileImpl
 *  com.jiuqi.nr.single.core.util.Ini
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.ZipUtil
 *  javax.annotation.Resource
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileTaskInfo
 */
package nr.single.data.dataout.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipUtil;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.annotation.Resource;
import nr.single.data.dataout.service.IFormDataExportService;
import nr.single.map.data.PathUtil;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileTaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormDataExportService
implements IFormDataExportService {
    private static final Logger logger = LoggerFactory.getLogger(FormDataExportService.class);
    @Resource
    private IJtableResourceService jtableResourceService;
    @Autowired
    private IRunTimeViewController viewController;

    @Override
    public String ExpoxtTaskDataToSingleFromScheme(TaskDataContext exportContext, String schemeKey, String path, String file) throws Exception {
        Object ini;
        FormSchemeDefine scheme = this.viewController.getFormScheme(schemeKey);
        String taskId = scheme.getTaskKey();
        TaskDefine task = this.viewController.queryTaskDefine(taskId);
        SingleFileImpl singleFile = new SingleFileImpl();
        SingleFileTaskInfo singleTask = null;
        if (null != exportContext.getMapingCache().getMapConfig()) {
            singleTask = exportContext.getMapingCache().getMapConfig().getTaskInfo();
        }
        if (singleTask != null && "YS".equalsIgnoreCase(singleTask.getSingleFileFlag())) {
            String taskDataPath = PathUtil.createNewPath((String)path, (String)"DATA");
            exportContext.info(logger, "\u5199\u5165\u6587\u4ef6\uff1aDataLoadCheck.ini");
            ini = new Ini();
            ini.writeString("Set", "File", "NR");
            ini.saveToFile(taskDataPath + "DataLoadCheck.ini");
        }
        exportContext.info(logger, "\u5bfc\u51fa\u4efb\u52a1:" + taskId + ",\u751f\u6210JIO\u6587\u4ef6,\u65f6\u95f4:" + new Date().toString());
        FileOutputStream outStream = new FileOutputStream(SinglePathUtil.normalize((String)(file + "temp.zip")));
        ini = null;
        try {
            ZipUtil.zipDirectory((String)path, (OutputStream)outStream, null, (String)"GBK");
        }
        catch (Throwable throwable) {
            ini = throwable;
            throw throwable;
        }
        finally {
            if (outStream != null) {
                if (ini != null) {
                    try {
                        outStream.close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)ini).addSuppressed(throwable);
                    }
                } else {
                    outStream.close();
                }
            }
        }
        String taskGroup = "";
        if (StringUtils.isNotEmpty((String)task.getGroupName())) {
            taskGroup = task.getGroupName();
        }
        if (null != singleTask) {
            singleFile.getInfo().writeString("General", "Name", singleTask.getSingleTaskTitle());
            singleFile.getInfo().writeString("General", "Flag", singleTask.getSingleTaskFlag());
            singleFile.getInfo().writeString("General", "FileFlag", singleTask.getSingleFileFlag());
            singleFile.getInfo().writeString("General", "Year", singleTask.getSingleTaskYear());
            singleFile.getInfo().writeString("General", "Period", singleTask.getSingleTaskPeriod());
            singleFile.getInfo().writeString("General", "Time", singleTask.getSingleTaskTime());
            singleFile.getInfo().writeString("General", "Version", "20190418142926");
            singleFile.getInfo().writeString("General", "Group", taskGroup);
            singleFile.getInfo().writeString("General", "InputClien", "0");
            singleFile.getInfo().writeString("General", "NetPeriodT", singleTask.getSingleTaskPeriod());
            singleFile.getInfo().writeString("Data", "QYSJ", "1");
            if (exportContext.isHasCheckInfo()) {
                singleFile.getInfo().writeString("Data", "SHSM", "1");
            }
            if (exportContext.isHasEntityCheck()) {
                singleFile.getInfo().writeString("Data", "HSHD", "1");
            }
        } else {
            singleFile.getInfo().writeString("General", "Name", scheme.getTitle());
            singleFile.getInfo().writeString("General", "Flag", scheme.getTaskPrefix());
            singleFile.getInfo().writeString("General", "FileFlag", task.getTaskFilePrefix());
            String taskYear = "2020";
            if (StringUtils.isNotEmpty((String)task.getFromPeriod())) {
                taskYear = task.getFromPeriod().substring(0, 4);
            }
            singleFile.getInfo().writeString("General", "Year", taskYear);
            singleFile.getInfo().writeString("General", "Period", "N");
            singleFile.getInfo().writeString("General", "Time", "0");
            singleFile.getInfo().writeString("General", "Version", "20180927142926");
            singleFile.getInfo().writeString("General", "Group", taskGroup);
            singleFile.getInfo().writeString("General", "InputClien", "0");
            singleFile.getInfo().writeString("General", "NetPeriodT", "N");
            singleFile.getInfo().writeString("Data", "QYSJ", "1");
            if (exportContext.isHasCheckInfo()) {
                singleFile.getInfo().writeString("Data", "SHSM", "1");
            }
            if (exportContext.isHasEntityCheck()) {
                singleFile.getInfo().writeString("Data", "HSHD", "1");
            }
        }
        if (exportContext.isHasQueryCheck()) {
            singleFile.getInfo().writeString("Data", "MBSM", "1");
        }
        singleFile.getInfo().writeString("General", "System", "6.1.7601");
        singleFile.getInfo().writeString("Data", "Source", "NR\u7f51\u7edc\u62a5\u8868");
        singleFile.makeJio(file + "temp.zip", file);
        PathUtil.deleteFile((String)(file + "temp.zip"));
        exportContext.info(logger, "\u751f\u6210JIO\u6587\u4ef6\u5b8c\u6210\uff1a" + file + ",\u65f6\u95f4:" + new Date().toString());
        return schemeKey;
    }
}

