/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.CsvFileWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.DateUtil;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ProcessInstanceFileWriter
extends CsvFileWriter {
    private static final String COLUMN_NAME_FORM_KEY = "FORMKEY";
    private static final String COLUMN_NAME_FORM_GROUP_KEY = "FORMGROUPKEY";
    private static final String COLUMN_NAME_ID = "ID";
    private static final String COLUMN_NAME_STATUS = "STATUS";
    private static final String COLUMN_NAME_USERTASK = "USERTASK";
    private static final String COLUMN_NAME_USERTASK_CODE = "USERTASK_CODE";
    private static final String COLUMN_NAME_START_TIME = "START_TIME";
    private static final String COLUMN_NAME_UPDATE_TIME = "UPDATE_TIME";
    private final String[] businessObjectDimensionNames;
    private final WorkflowObjectType workflowObjectType;
    private final SimpleDateFormat dateFormat;

    public ProcessInstanceFileWriter(File file, String[] businessObjectDimensionNames, WorkflowObjectType workflowObjectType) {
        super(file, workflowObjectType == WorkflowObjectType.FORM || workflowObjectType == WorkflowObjectType.FORM_GROUP ? 7 + businessObjectDimensionNames.length : 6 + businessObjectDimensionNames.length);
        this.businessObjectDimensionNames = businessObjectDimensionNames;
        this.workflowObjectType = workflowObjectType;
        this.dateFormat = DateUtil.createDateFormat();
        this.writeHeadLine();
    }

    public void write(IProcessIOInstance instance) throws ProcessIOException {
        this.writeInstance(instance);
    }

    private void writeHeadLine() throws ProcessIOException {
        String[] headline = ProcessInstanceFileWriter.makeHeadline(this.businessObjectDimensionNames, this.workflowObjectType);
        if (headline.length != this.lineData.length) {
            throw new RuntimeException("\u8868\u5934\u5217\u4e0e\u6570\u636e\u5217\u6570\u4e0d\u4e00\u81f4\u3002");
        }
        try {
            this.writeLine(headline);
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u5199\u5165\u6d41\u7a0b\u5b9e\u4f8b\u6587\u4ef6\u9519\u8bef\u3002", e);
        }
    }

    static String[] makeHeadline(String[] businessObjectDimensionNames, WorkflowObjectType workflowObjectType) {
        int columnCount = workflowObjectType == WorkflowObjectType.FORM || workflowObjectType == WorkflowObjectType.FORM_GROUP ? 7 + businessObjectDimensionNames.length : 6 + businessObjectDimensionNames.length;
        String[] lineData = new String[columnCount];
        int columnIndex = 0;
        lineData[columnIndex] = COLUMN_NAME_ID;
        ++columnIndex;
        String[] stringArray = businessObjectDimensionNames;
        int n = stringArray.length;
        for (int i = 0; i < n; ++i) {
            String dimensionName;
            lineData[columnIndex] = dimensionName = stringArray[i];
            ++columnIndex;
        }
        if (workflowObjectType == WorkflowObjectType.FORM) {
            lineData[columnIndex] = COLUMN_NAME_FORM_KEY;
            ++columnIndex;
        } else if (workflowObjectType == WorkflowObjectType.FORM_GROUP) {
            lineData[columnIndex] = COLUMN_NAME_FORM_GROUP_KEY;
            ++columnIndex;
        }
        lineData[columnIndex] = COLUMN_NAME_STATUS;
        lineData[++columnIndex] = COLUMN_NAME_USERTASK;
        lineData[++columnIndex] = COLUMN_NAME_USERTASK_CODE;
        lineData[++columnIndex] = COLUMN_NAME_START_TIME;
        lineData[++columnIndex] = COLUMN_NAME_UPDATE_TIME;
        return lineData;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void writeInstance(IProcessIOInstance instance) {
        int columnIndex = 0;
        this.lineData[columnIndex] = instance.getId();
        ++columnIndex;
        DimensionCombination dimensions = instance.getBusinessObject().getDimensions();
        for (String dimensionName : this.businessObjectDimensionNames) {
            this.lineData[columnIndex] = (String)dimensions.getValue(dimensionName);
            ++columnIndex;
        }
        if (this.workflowObjectType == WorkflowObjectType.FORM) {
            if (!(instance.getBusinessObject() instanceof IFormObject)) throw new ProcessIOException(null, "businessObject must be IFormObject.");
            this.lineData[columnIndex] = ((IFormObject)instance.getBusinessObject()).getFormKey();
            ++columnIndex;
        } else if (this.workflowObjectType == WorkflowObjectType.FORM_GROUP) {
            if (!(instance.getBusinessObject() instanceof IFormGroupObject)) throw new ProcessIOException(null, "businessObject must be IFormGroupObject.");
            this.lineData[columnIndex] = ((IFormGroupObject)instance.getBusinessObject()).getFormGroupKey();
            ++columnIndex;
        }
        this.lineData[columnIndex] = instance.getStatus();
        this.lineData[++columnIndex] = instance.getCurrentUserTask();
        this.lineData[++columnIndex] = instance.getCurrentUserTaskCode();
        this.lineData[++columnIndex] = this.dateFormat.format(instance.getStartTime().getTime());
        this.lineData[++columnIndex] = this.dateFormat.format(instance.getUpdateTime().getTime());
        try {
            this.writeLine();
            return;
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u5199\u5165\u6d41\u7a0b\u5b9e\u4f8b\u6587\u4ef6\u9519\u8bef\u3002", e);
        }
    }
}

