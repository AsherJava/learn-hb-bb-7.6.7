/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.csvreader.CsvReader;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.DateUtil;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.FileUtil;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessOperationFileWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProcessOperationFileReader
implements Closeable {
    private final File file;
    private final CsvReader csvReader;
    private final SimpleDateFormat dateFormat;

    public ProcessOperationFileReader(File file) {
        this.file = file;
        this.csvReader = new CsvReader(FileUtil.createFileInputStream(file), ',', Charset.forName("UTF-8"));
        this.dateFormat = DateUtil.createDateFormat();
        this.verifyHeadline();
    }

    private void verifyHeadline() {
        String[] headline = null;
        try {
            if (this.csvReader.readHeaders()) {
                headline = this.csvReader.getHeaders();
            }
        }
        catch (Exception e) {
            throw new ProcessIOException(null, "read operation file error: " + this.file.getPath(), e);
        }
        if (headline == null) {
            throw new ProcessIOException(null, "can not read head line from file " + this.file.getPath());
        }
        String[] standardHeadline = ProcessOperationFileWriter.HEADLINE;
        for (int i = 0; i < standardHeadline.length; ++i) {
            if (i + 1 <= headline.length && standardHeadline[i].equals(headline[i])) continue;
            throw new ProcessIOException(null, "column '" + standardHeadline[i] + "' is missing in file " + this.file.getPath());
        }
    }

    public IProcessIOOperation readNextOperation() {
        String[] lineData;
        try {
            if (!this.csvReader.readRecord()) {
                return null;
            }
            lineData = this.csvReader.getValues();
        }
        catch (Exception e) {
            throw new ProcessIOException(null, "read operation file error: " + this.file.getPath(), e);
        }
        ProcessIOOperation operation = new ProcessIOOperation();
        int c = 0;
        operation.setInstanceId(lineData[c++]);
        operation.setSourceUserTask(lineData[c++]);
        operation.setTargetUserTask(lineData[c++]);
        operation.setAction(lineData[c++]);
        String operateTimeText = lineData[c++];
        try {
            Date date = this.dateFormat.parse(operateTimeText);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            operation.setOperateTime(calendar);
        }
        catch (ParseException e) {
            throw new ProcessIOException(null, "time format error: " + operateTimeText, e);
        }
        operation.setOperator(lineData[c++]);
        operation.setComment(lineData[c++]);
        operation.setForceReport(Boolean.parseBoolean(lineData[c++]));
        return operation;
    }

    @Override
    public void close() throws IOException {
        if (this.csvReader != null) {
            this.csvReader.close();
        }
    }
}

