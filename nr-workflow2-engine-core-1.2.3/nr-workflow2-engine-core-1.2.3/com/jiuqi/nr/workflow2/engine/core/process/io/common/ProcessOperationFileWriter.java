/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.CsvFileWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.DateUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ProcessOperationFileWriter
extends CsvFileWriter {
    private static final String COLUMN_NAME_INSTANCE_ID = "INSTANCEID";
    private static final String COLUMN_NAME_SOURCE_USERTASK = "SOURCEUSERTASK";
    private static final String COLUMN_NAME_TARGET_USERTASK = "TARGETUSERTASK";
    private static final String COLUMN_NAME_ACTION = "ACTION";
    private static final String COLUMN_NAME_OPERATE_TIME = "OPERATETIME";
    private static final String COLUMN_NAME_OPERATOR = "OPERATOR";
    private static final String COLUMN_NAME_COMMENT = "COMMENT";
    private static final String COLUMN_FORCE_REPORT = "FORCEREPORT";
    static final String[] HEADLINE = new String[]{"INSTANCEID", "SOURCEUSERTASK", "TARGETUSERTASK", "ACTION", "OPERATETIME", "OPERATOR", "COMMENT", "FORCEREPORT"};
    private final SimpleDateFormat dateFormat = DateUtil.createDateFormat();

    public ProcessOperationFileWriter(File file) {
        super(file, HEADLINE.length);
        this.writeHeadLine();
    }

    public void write(IProcessIOOperation operation) throws ProcessIOException {
        this.writeOperation(operation);
    }

    private void writeHeadLine() {
        try {
            this.writeLine(HEADLINE);
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u5199\u5165\u6d41\u7a0b\u5386\u53f2\u6587\u4ef6\u9519\u8bef\u3002", e);
        }
    }

    static String[] makeHeadline() {
        String[] lineData = new String[HEADLINE.length];
        for (int i = 0; i < HEADLINE.length; ++i) {
            lineData[i] = HEADLINE[i];
        }
        return lineData;
    }

    private void writeOperation(IProcessIOOperation operation) {
        int c = 0;
        this.lineData[c++] = operation.getInstanceId();
        this.lineData[c++] = operation.getSourceUserTask();
        this.lineData[c++] = operation.getTargetUserTask();
        this.lineData[c++] = operation.getAction();
        this.lineData[c++] = this.dateFormat.format(operation.getOperateTime().getTime());
        this.lineData[c++] = operation.getOperator();
        this.lineData[c++] = operation.getComment();
        this.lineData[c++] = String.valueOf(operation.isForceReport());
        try {
            this.writeLine();
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u5199\u5165\u6d41\u7a0b\u5386\u53f2\u6587\u4ef6\u9519\u8bef\u3002", e);
        }
    }
}

