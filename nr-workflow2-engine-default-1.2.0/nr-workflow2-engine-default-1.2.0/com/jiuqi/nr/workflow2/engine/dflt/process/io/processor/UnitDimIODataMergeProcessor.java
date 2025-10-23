/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription$ProcessDataOutputDescriptionBuilder
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.processor;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.IProcessIODataProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.ProcessIODataMergeSet;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.ProcessIOUtil;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.jetbrains.annotations.NotNull;

public class UnitDimIODataMergeProcessor
implements IProcessIODataProcessor {
    public static final String unitInstanceFormKey = "11111111-1111-1111-1111-111111111111";
    private final IPorcessDataInputStream originalInputStream;
    private final WorkflowSettingsDO targetSettings;
    private final ProcessIODataMergeSet mergeSet;
    private final Map<String, Integer> stateLevelMap = new HashMap<String, Integer>(){
        {
            this.put("rejected", 1);
            this.put("backed", 2);
            this.put("unsubmited", 3);
            this.put("unreported", 4);
            this.put("submited", 5);
            this.put("reported", 6);
            this.put("confirmed", 7);
        }
    };

    public UnitDimIODataMergeProcessor(IPorcessDataInputStream originalInputStream, WorkflowSettingsDO targetSettings) {
        this.originalInputStream = originalInputStream;
        this.targetSettings = targetSettings;
        this.mergeSet = new ProcessIODataMergeSet();
    }

    @Override
    public void process(IPorcessDataOutputStream transferOutputStream) {
        ProcessDataOutputDescription originalDescription = this.originalInputStream.readDescription();
        ProcessDataOutputDescription newDescription = new ProcessDataOutputDescription.ProcessDataOutputDescriptionBuilder().workflowObjectType(this.targetSettings.getWorkflowObjectType()).dataTypes((Collection)originalDescription.getOutputDataTypes()).version(originalDescription.getVersion()).businessObjectDimensionNames(originalDescription.getBusinessObjectDimensionNames()).build();
        transferOutputStream.writeDescription(newDescription);
        WorkflowObjectType originalWorkflowObjectType = originalDescription.getWorkflowObjectType();
        List periods = this.originalInputStream.getPeriods();
        for (String period : periods) {
            IProcessIOInstance mergedInstance;
            IProcessIOInstance minStateInstance;
            IProcessIOInstance instance;
            IProcessDataReader reader = this.originalInputStream.getProcessDataReaders(period);
            IProcessDataWriter writer = transferOutputStream.getProcessDataWriter(period);
            Comparator<IProcessIOInstance> comparator = Comparator.comparing(e -> this.stateLevelMap.get(ProcessIOUtil.transferOldStateCodeToNew(e.getStatus()))).thenComparing(Comparator.comparing(IProcessIOInstance::getUpdateTime).reversed());
            PriorityQueue<IProcessIOInstance> minHeap = new PriorityQueue<IProcessIOInstance>(comparator);
            DimensionCombination preLineUnitDim = null;
            while ((instance = reader.readNextInstance()) != null) {
                if (ProcessIOUtil.isUnitInstance(instance, originalWorkflowObjectType)) continue;
                if (preLineUnitDim != null && !instance.getBusinessObject().getDimensions().equals(preLineUnitDim)) {
                    minStateInstance = minHeap.poll();
                    if (minStateInstance != null) {
                        this.mergeSet.addMergedInstanceId(minStateInstance.getId());
                        mergedInstance = UnitDimIODataMergeProcessor.buildMergedInstance(minStateInstance, (IBusinessObject)new DimensionObject(minStateInstance.getBusinessObject().getDimensions()));
                        writer.writeInstance(mergedInstance);
                    }
                    minHeap.clear();
                }
                minHeap.offer(instance);
                preLineUnitDim = instance.getBusinessObject().getDimensions();
            }
            if (!minHeap.isEmpty()) {
                minStateInstance = minHeap.poll();
                this.mergeSet.addMergedInstanceId(minStateInstance.getId());
                mergedInstance = UnitDimIODataMergeProcessor.buildMergedInstance(minStateInstance, (IBusinessObject)new DimensionObject(minStateInstance.getBusinessObject().getDimensions()));
                writer.writeInstance(mergedInstance);
                minHeap.clear();
            }
            IProcessIOOperation operation = reader.readNextOperation();
            while (operation != null) {
                if (this.mergeSet.isMergedInstanceId(operation.getInstanceId())) {
                    writer.writeOperation(operation);
                }
                operation = reader.readNextOperation();
            }
        }
    }

    @NotNull
    public static IProcessIOInstance buildMergedInstance(IProcessIOInstance minStateInstance, IBusinessObject businessObject) {
        ProcessIOInstance mergedInstance = new ProcessIOInstance(minStateInstance.getId());
        mergedInstance.setBusinessObject(businessObject);
        mergedInstance.setCurrentUserTask(minStateInstance.getCurrentUserTask());
        mergedInstance.setStatus(minStateInstance.getStatus());
        mergedInstance.setCurrentUserTaskCode(minStateInstance.getCurrentUserTaskCode());
        mergedInstance.setStartTime(minStateInstance.getStartTime());
        mergedInstance.setUpdateTime(minStateInstance.getUpdateTime());
        return mergedInstance;
    }
}

