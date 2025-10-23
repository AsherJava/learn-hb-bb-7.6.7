/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType;
import com.jiuqi.nr.workflow2.engine.core.process.io.Version;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ProcessDataOutputDescription {
    private Version version;
    private Set<ProcessDataType> outputDataTypes;
    private WorkflowObjectType workflowObjectType;
    private String[] businessObjectDimensionNames;

    public Version getVersion() {
        return this.version;
    }

    public Set<ProcessDataType> getOutputDataTypes() {
        return this.outputDataTypes;
    }

    public WorkflowObjectType getWorkflowObjectType() {
        return this.workflowObjectType;
    }

    public String[] getBusinessObjectDimensionNames() {
        return this.businessObjectDimensionNames;
    }

    private ProcessDataOutputDescription() {
    }

    static /* synthetic */ String[] access$402(ProcessDataOutputDescription x0, String[] x1) {
        x0.businessObjectDimensionNames = x1;
        return x1;
    }

    public static class ProcessDataOutputDescriptionBuilder {
        private final ProcessDataOutputDescription description = new ProcessDataOutputDescription();

        public ProcessDataOutputDescriptionBuilder version(Version version) {
            this.description.version = version;
            return this;
        }

        public ProcessDataOutputDescriptionBuilder dataTypes(Collection<ProcessDataType> outputDataTypes) {
            this.description.outputDataTypes = new HashSet<ProcessDataType>(outputDataTypes);
            return this;
        }

        public ProcessDataOutputDescriptionBuilder workflowObjectType(WorkflowObjectType workflowObjectType) {
            this.description.workflowObjectType = workflowObjectType;
            return this;
        }

        public ProcessDataOutputDescriptionBuilder businessObjectDimensionNames(String[] businessObjectDimensionNames) {
            ProcessDataOutputDescription.access$402(this.description, businessObjectDimensionNames);
            return this;
        }

        public ProcessDataOutputDescription build() {
            return this.description;
        }
    }
}

