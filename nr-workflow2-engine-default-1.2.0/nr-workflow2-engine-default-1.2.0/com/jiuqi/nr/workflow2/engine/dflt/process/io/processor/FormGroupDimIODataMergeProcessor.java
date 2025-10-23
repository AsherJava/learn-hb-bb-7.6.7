/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.workflow2.engine.core.exception.FormSchemeNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription$ProcessDataOutputDescriptionBuilder
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.processor;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.workflow2.engine.core.exception.FormSchemeNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.IProcessIODataProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.UnitDimIODataMergeProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.ProcessIODataMergeSet;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.ProcessIOUtil;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class FormGroupDimIODataMergeProcessor
implements IProcessIODataProcessor {
    private final IPorcessDataInputStream originalInputStream;
    private final WorkflowSettingsDO targetSettings;
    private final IRunTimeViewController runTimeViewController;
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

    public FormGroupDimIODataMergeProcessor(IPorcessDataInputStream originalInputStream, WorkflowSettingsDO targetSettings, IRunTimeViewController runTimeViewController) {
        this.originalInputStream = originalInputStream;
        this.targetSettings = targetSettings;
        this.runTimeViewController = runTimeViewController;
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
            PriorityQueue minHeap;
            String formGroupKey;
            IProcessIOInstance instance;
            IProcessDataReader reader = this.originalInputStream.getProcessDataReaders(period);
            IProcessDataWriter writer = transferOutputStream.getProcessDataWriter(period);
            SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, transferOutputStream.getTaskKey());
            if (schemePeriodLinkByPeriodAndTask == null) {
                throw new FormSchemeNotFoundException(transferOutputStream.getTaskKey(), period);
            }
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
            Comparator<IProcessIOInstance> comparator = Comparator.comparing(e -> this.stateLevelMap.get(ProcessIOUtil.transferOldStateCodeToNew(e.getStatus()))).thenComparing(Comparator.comparing(IProcessIOInstance::getUpdateTime).reversed());
            HashMap<String, PriorityQueue> minHeapMap = new HashMap<String, PriorityQueue>();
            DimensionCombination preLineUnitDim = null;
            while ((instance = reader.readNextInstance()) != null) {
                if (ProcessIOUtil.isUnitInstance(instance, originalWorkflowObjectType)) continue;
                if (preLineUnitDim != null && !instance.getBusinessObject().getDimensions().equals(preLineUnitDim)) {
                    for (Map.Entry entry : minHeapMap.entrySet()) {
                        formGroupKey = (String)entry.getKey();
                        minHeap = (PriorityQueue)entry.getValue();
                        minStateInstance = (IProcessIOInstance)minHeap.poll();
                        if (minStateInstance != null) {
                            this.mergeSet.addMergedInstanceId(minStateInstance.getId());
                            mergedInstance = UnitDimIODataMergeProcessor.buildMergedInstance(minStateInstance, (IBusinessObject)new FormGroupObject(minStateInstance.getBusinessObject().getDimensions(), formGroupKey));
                            writer.writeInstance(mergedInstance);
                        }
                        minHeap.clear();
                    }
                    minHeapMap.clear();
                }
                FormObject formObject = (FormObject)instance.getBusinessObject();
                FormGroupDefine formGroupDefine = (FormGroupDefine)this.runTimeViewController.listFormGroupByForm(formObject.getFormKey(), formSchemeDefine.getKey()).get(0);
                PriorityQueue minHeap2 = minHeapMap.computeIfAbsent(formGroupDefine.getKey(), key -> new PriorityQueue(comparator));
                minHeap2.offer(instance);
                preLineUnitDim = instance.getBusinessObject().getDimensions();
            }
            if (!minHeapMap.isEmpty()) {
                for (Map.Entry entry : minHeapMap.entrySet()) {
                    formGroupKey = (String)entry.getKey();
                    minHeap = (PriorityQueue)entry.getValue();
                    minStateInstance = (IProcessIOInstance)minHeap.poll();
                    if (minStateInstance != null) {
                        this.mergeSet.addMergedInstanceId(minStateInstance.getId());
                        mergedInstance = UnitDimIODataMergeProcessor.buildMergedInstance(minStateInstance, (IBusinessObject)new FormGroupObject(minStateInstance.getBusinessObject().getDimensions(), formGroupKey));
                        writer.writeInstance(mergedInstance);
                    }
                    minHeap.clear();
                }
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
}

