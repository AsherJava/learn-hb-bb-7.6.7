/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.FileUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OutputDescriptionFileWriter {
    public static final String PROPERTYNAME_VERSION = "version";
    public static final String PROPERTYNAME_DATATYPE = "data_type";
    public static final String PROPERTYNAME_OBJECTTYPE = "object_types";
    public static final String PROPERTYNAME_DIMENSIONNAME = "dimension_names";
    public static final String ARRAYPROPERTYSPLITCHAR = ",";
    private final File file;

    public OutputDescriptionFileWriter(File file) {
        this.file = file;
    }

    public void write(ProcessDataOutputDescription description) throws ProcessIOException {
        BufferedWriter writer = null;
        try {
            String propertyLine;
            FileUtil.forceCreateNewFile(this.file);
            writer = new BufferedWriter(FileUtil.createFileWriter(this.file));
            if (description.getVersion() != null) {
                propertyLine = "version=" + description.getVersion();
                writer.write(propertyLine);
                writer.newLine();
            }
            if (description.getOutputDataTypes() != null && !description.getOutputDataTypes().isEmpty()) {
                List dataTypes = description.getOutputDataTypes().stream().map(o -> o.name()).collect(Collectors.toList());
                String propertyLine2 = "data_type=" + String.join((CharSequence)ARRAYPROPERTYSPLITCHAR, dataTypes);
                writer.write(propertyLine2);
                writer.newLine();
            }
            if (description.getWorkflowObjectType() != null) {
                propertyLine = "object_types=" + description.getWorkflowObjectType().name();
                writer.write(propertyLine);
                writer.newLine();
            }
            if (description.getBusinessObjectDimensionNames() != null && description.getBusinessObjectDimensionNames().length > 0) {
                propertyLine = "dimension_names=" + String.join((CharSequence)ARRAYPROPERTYSPLITCHAR, description.getBusinessObjectDimensionNames());
                writer.write(propertyLine);
                writer.newLine();
            }
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u5199\u5165\u8bf4\u660e\u6587\u4ef6\u5931\u8d25\u3002", e);
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException iOException) {}
            }
        }
    }
}

