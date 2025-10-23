/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.csvreader.CsvReader
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.csvreader.CsvReader;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.DateUtil;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.FileUtil;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessInstanceFileWriter;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProcessInstanceFileReader
implements Closeable {
    private final File file;
    private final CsvReader csvReader;
    private final String[] businessObjectDimensionNames;
    private final WorkflowObjectType workflowObjectType;
    private final SimpleDateFormat dateFormat;

    public ProcessInstanceFileReader(File file, String[] businessObjectDimensionNames, WorkflowObjectType workflowObjectType) {
        this.file = file;
        this.csvReader = new CsvReader(FileUtil.createFileInputStream(file), ',', Charset.forName("UTF-8"));
        this.businessObjectDimensionNames = businessObjectDimensionNames;
        this.workflowObjectType = workflowObjectType;
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
            throw new ProcessIOException(null, "read instance file error: " + this.file.getPath(), e);
        }
        if (headline == null) {
            throw new ProcessIOException(null, "can not read head line from file " + this.file.getPath());
        }
        String[] standardHeadline = ProcessInstanceFileWriter.makeHeadline(this.businessObjectDimensionNames, this.workflowObjectType);
        for (int i = 0; i < standardHeadline.length; ++i) {
            if (i + 1 <= headline.length && standardHeadline[i].equals(headline[i])) continue;
            throw new ProcessIOException(null, "column '" + standardHeadline[i] + "' is missing in file " + this.file.getPath());
        }
    }

    public IProcessIOInstance readNextInstance() {
        String[] linedata;
        try {
            if (!this.csvReader.readRecord()) {
                return null;
            }
            linedata = this.csvReader.getValues();
        }
        catch (Exception e) {
            throw new ProcessIOException(null, "read instance file error: " + this.file.getPath(), e);
        }
        ProcessIOInstance instance = new ProcessIOInstance();
        int columnIndex = 0;
        instance.setId(linedata[columnIndex++]);
        DimensionCombinationBuilder dimensionBuilder = new DimensionCombinationBuilder();
        for (int i = 0; i < this.businessObjectDimensionNames.length; ++i) {
            dimensionBuilder.setValue(this.businessObjectDimensionNames[i], (Object)linedata[columnIndex]);
            ++columnIndex;
        }
        switch (this.workflowObjectType) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                DimensionObject dimensionObject = new DimensionObject(dimensionBuilder.getCombination());
                instance.setBusinessObject(dimensionObject);
                break;
            }
            case FORM: {
                FormObject formObject = new FormObject(dimensionBuilder.getCombination(), linedata[columnIndex++]);
                instance.setBusinessObject(formObject);
                break;
            }
            case FORM_GROUP: {
                FormGroupObject formGroupObject = new FormGroupObject(dimensionBuilder.getCombination(), linedata[columnIndex++]);
                instance.setBusinessObject(formGroupObject);
                break;
            }
        }
        instance.setStatus(linedata[columnIndex++]);
        instance.setCurrentUserTask(linedata[columnIndex++]);
        instance.setCurrentUserTaskCode(linedata[columnIndex++]);
        String startTimeStr = linedata[columnIndex++];
        String updateTimeStr = linedata[columnIndex++];
        try {
            Date startDate = this.dateFormat.parse(startTimeStr);
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(startDate);
            instance.setStartTime(startTime);
            Date updateDate = this.dateFormat.parse(updateTimeStr);
            Calendar updateTime = Calendar.getInstance();
            updateTime.setTime(updateDate);
            instance.setUpdateTime(updateTime);
        }
        catch (ParseException e) {
            throw new ProcessIOException(null, "time format error: " + startTimeStr, e);
        }
        return instance;
    }

    @Override
    public void close() throws IOException {
        if (this.csvReader != null) {
            this.csvReader.close();
        }
    }
}

