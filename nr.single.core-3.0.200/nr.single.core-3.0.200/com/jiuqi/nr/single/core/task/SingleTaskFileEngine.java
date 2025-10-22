/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task;

import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.service.internal.SingleFileParserServiceImpl;
import com.jiuqi.nr.single.core.task.SingleTaskDirEngine;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.file.ISingleTaskFileEngine;
import com.jiuqi.nr.single.core.task.model.SingleTaskInfo;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipParam;
import com.jiuqi.nr.single.core.util.ZipUtil;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleTaskFileEngine
extends SingleTaskDirEngine
implements ISingleTaskFileEngine {
    private static final Logger logger = LoggerFactory.getLogger(SingleTaskFileEngine.class);
    private String fileName;
    private boolean includeFj = true;

    public SingleTaskFileEngine(String taskDir) {
        super(taskDir);
    }

    public SingleTaskFileEngine(String fileName, String taskDir) {
        super(taskDir);
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public void unzipToTaskDir() throws SingleTaskException {
        try {
            SingleFileParserServiceImpl parserService = new SingleFileParserServiceImpl();
            SingleFile singleFile = parserService.getSingleFile(this.getFileName());
            String zipFileName = this.getFileName() + "temp.zip";
            singleFile.unMakeJio(this.getFileName(), zipFileName);
            if (this.includeFj) {
                ZipUtil.unzipFile(this.getTaskDir(), zipFileName, "GBK");
            } else {
                ZipParam zipParam = new ZipParam();
                HashSet<String> filerNames = new HashSet<String>();
                filerNames.add("DATA/SYS_DOC");
                ZipUtil.unzipFile(this.getTaskDir(), zipFileName, "GBK", ZipUtil.TRY_CHARSETS, filerNames, zipParam);
            }
            singleFile.writeTaskSign(this.getTaskDir());
            SinglePathUtil.reNameFile(this.getTaskDir(), "Data", "DATA");
            if (this.includeFj) {
                SinglePathUtil.deleteFile(zipFileName);
            }
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
    }

    @Override
    public SingleTaskInfo getTaskInfoFormFile() throws SingleTaskException {
        SingleTaskInfo taskInfo = new SingleTaskInfo();
        try {
            SingleFileParserServiceImpl parserService = new SingleFileParserServiceImpl();
            SingleFile singleFile = parserService.getSingleFile(this.getFileName());
            taskInfo.setFileFlag(singleFile.getFileFlag());
            taskInfo.setTaskFlag(singleFile.getTaskFlag());
            taskInfo.setTaskTitle(singleFile.getTaskName());
            taskInfo.setTaskYear(singleFile.getTaskYear());
            taskInfo.setTaskType(singleFile.getTaskPeriod());
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
        return taskInfo;
    }

    @Override
    public void deleteTaskDir() {
        try {
            SinglePathUtil.deleteDir(this.getTaskDir());
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
        }
    }
}

