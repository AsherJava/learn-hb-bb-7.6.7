/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.OutputDescriptionFileWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessDataFileWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessInstanceFileWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessOperationFileWriter;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PorcessDataFileOutputStream
implements IPorcessDataOutputStream {
    private final TaskDefine task;
    private final File rootDir;
    private final File taskDir;
    private String[] businessObjectDimensionNames;
    private WorkflowObjectType workflowObjectType;
    public static final String DESCRIPTIONFILENAME = "description.properties";
    public static final String INSTANCEFILENAME = "instance.csv";
    public static final String OPERATIONFILENAME = "operation.csv";
    private Map<String, IProcessDataWriter> processDataWriters = new HashMap<String, IProcessDataWriter>();

    @Deprecated
    public PorcessDataFileOutputStream(TaskDefine task, String rootPath, String[] businessObjectDimensionNames, WorkflowObjectType workflowObjectType) {
        this.task = task;
        this.businessObjectDimensionNames = businessObjectDimensionNames;
        this.workflowObjectType = workflowObjectType;
        this.rootDir = new File(rootPath);
        this.taskDir = new File(this.rootDir, task.getTaskCode());
        if (!this.taskDir.exists()) {
            this.taskDir.mkdirs();
        }
    }

    public PorcessDataFileOutputStream(TaskDefine task, String rootPath) {
        this.task = task;
        this.rootDir = new File(rootPath);
        this.taskDir = new File(this.rootDir, task.getTaskCode());
        if (!this.taskDir.exists()) {
            this.taskDir.mkdirs();
        }
    }

    @Override
    public String getTaskKey() {
        return this.task.getKey();
    }

    @Override
    public void writeDescription(ProcessDataOutputDescription description) {
        this.businessObjectDimensionNames = description.getBusinessObjectDimensionNames();
        this.workflowObjectType = description.getWorkflowObjectType();
        File descriptionFile = new File(this.rootDir, DESCRIPTIONFILENAME);
        OutputDescriptionFileWriter descriptionWriter = new OutputDescriptionFileWriter(descriptionFile);
        descriptionWriter.write(description);
    }

    @Override
    public IProcessDataWriter getProcessDataWriter(String period) {
        if (this.businessObjectDimensionNames == null) {
            throw new RuntimeException("\u9519\u8bef\u7684\u65b9\u6cd5\u8c03\u7528\uff1a\u5fc5\u987b\u5728\u5199\u5165\u63cf\u8ff0\u4fe1\u606f\u540e\u624d\u80fd\u8c03\u7528\u8be5\u65b9\u6cd5\u3002");
        }
        IProcessDataWriter writer = this.processDataWriters.get(period);
        if (writer == null) {
            writer = this.createProcessDataWriter(period);
            this.processDataWriters.put(period, writer);
        }
        return writer;
    }

    private IProcessDataWriter createProcessDataWriter(String period) {
        ProcessDataFileWriter dataWriter = new ProcessDataFileWriter(this.task.getKey(), period);
        File periodDir = new File(this.taskDir, period);
        periodDir.mkdir();
        File instanceFile = new File(periodDir, INSTANCEFILENAME);
        ProcessInstanceFileWriter instanceWriter = new ProcessInstanceFileWriter(instanceFile, this.businessObjectDimensionNames, this.workflowObjectType);
        dataWriter.setInstanceWriter(instanceWriter);
        File operationFile = new File(periodDir, OPERATIONFILENAME);
        ProcessOperationFileWriter operationWriter = new ProcessOperationFileWriter(operationFile);
        dataWriter.setOperationWriter(operationWriter);
        return dataWriter;
    }

    @Override
    public void close() throws IOException {
        for (IProcessDataWriter writer : this.processDataWriters.values()) {
            if (writer == null) continue;
            writer.close();
        }
    }
}

