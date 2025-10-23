/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.OutputDescriptionFileReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessDataFileReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessInstanceFileReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessOperationFileReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PorcessDataFileInputStream
implements IPorcessDataInputStream {
    private final File rootDir;
    private File descriptionFile;
    private ProcessDataOutputDescription description;
    private File taskDir;
    private final Map<String, IProcessDataReader> processDataReaders = new HashMap<String, IProcessDataReader>();

    public PorcessDataFileInputStream(String rootPath) {
        File[] files;
        this.rootDir = new File(rootPath);
        if (!this.rootDir.exists()) {
            throw new ProcessIOException(null, "path " + rootPath + " not exists.");
        }
        for (File file : files = this.rootDir.listFiles()) {
            if (file.getName().equals("description.properties")) {
                this.descriptionFile = file;
                continue;
            }
            this.taskDir = file;
        }
        if (this.descriptionFile == null) {
            throw new ProcessIOException(null, "description.properties not found.");
        }
        if (this.taskDir == null) {
            throw new ProcessIOException(null, "task dir not found.");
        }
    }

    @Override
    public void close() throws IOException {
        for (IProcessDataReader reader : this.processDataReaders.values()) {
            if (reader == null) continue;
            reader.close();
        }
    }

    @Override
    public String getTaskCode() {
        return this.taskDir.getName();
    }

    @Override
    public List<String> getPeriods() {
        return Arrays.asList(this.taskDir.list());
    }

    @Override
    public ProcessDataOutputDescription readDescription() throws ProcessIOException {
        if (this.description == null) {
            OutputDescriptionFileReader reader = new OutputDescriptionFileReader(this.descriptionFile);
            this.description = reader.read();
        }
        return this.description;
    }

    @Override
    public IProcessDataReader getProcessDataReaders(String period) throws ProcessIOException {
        IProcessDataReader reader = this.processDataReaders.get(period);
        if (reader == null) {
            reader = this.createProcessDataReader(period);
            this.processDataReaders.put(period, reader);
        }
        return reader;
    }

    private IProcessDataReader createProcessDataReader(String period) {
        File operationFile;
        File periodDir = new File(this.taskDir, period);
        if (!periodDir.exists()) {
            throw new IllegalArgumentException("data dir to period '" + period + "' not found.");
        }
        ProcessDataFileReader dataReader = new ProcessDataFileReader(period);
        File instanceFile = new File(periodDir, "instance.csv");
        if (instanceFile.exists()) {
            ProcessDataOutputDescription description = this.readDescription();
            ProcessInstanceFileReader instanceReader = new ProcessInstanceFileReader(instanceFile, description.getBusinessObjectDimensionNames(), description.getWorkflowObjectType());
            dataReader.setInstanceReader(instanceReader);
        }
        if ((operationFile = new File(periodDir, "operation.csv")).exists()) {
            ProcessOperationFileReader operationReader = new ProcessOperationFileReader(operationFile);
            dataReader.setOperationReader(operationReader);
        }
        return dataReader;
    }
}

