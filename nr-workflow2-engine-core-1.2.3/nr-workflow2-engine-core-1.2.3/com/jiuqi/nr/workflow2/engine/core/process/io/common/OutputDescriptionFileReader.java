/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType;
import com.jiuqi.nr.workflow2.engine.core.process.io.Version;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class OutputDescriptionFileReader {
    private final File file;

    public OutputDescriptionFileReader(File file) {
        this.file = file;
    }

    public ProcessDataOutputDescription read() throws ProcessIOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(this.file));
            ProcessDataOutputDescription.ProcessDataOutputDescriptionBuilder builder = new ProcessDataOutputDescription.ProcessDataOutputDescriptionBuilder();
            String lineText = reader.readLine();
            while (lineText != null) {
                int i = lineText.indexOf("=");
                if (i < 0 || i == 0 || i == lineText.length() - 1) continue;
                String key = lineText.substring(0, i).trim();
                String value = lineText.substring(i + 1).trim();
                switch (key) {
                    case "version": {
                        builder.version(Version.valueOf(value));
                        break;
                    }
                    case "data_type": {
                        String[] dataTypeNames = value.split(",");
                        HashSet<ProcessDataType> dataTypes = new HashSet<ProcessDataType>();
                        for (String dataTypeName : dataTypeNames) {
                            dataTypes.add(ProcessDataType.valueOf(dataTypeName));
                        }
                        builder.dataTypes(dataTypes);
                        break;
                    }
                    case "object_types": {
                        builder.workflowObjectType(WorkflowObjectType.valueOf(value));
                        break;
                    }
                    case "dimension_names": {
                        String[] dimensionNames = value.split(",");
                        builder.businessObjectDimensionNames(dimensionNames);
                        break;
                    }
                }
                lineText = reader.readLine();
            }
            ProcessDataOutputDescription processDataOutputDescription = builder.build();
            return processDataOutputDescription;
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u8bfb\u53d6\u8bf4\u660e\u6587\u4ef6\u5931\u8d25\u3002", e);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException iOException) {}
            }
        }
    }
}

