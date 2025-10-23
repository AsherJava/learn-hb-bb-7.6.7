/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
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
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.processor;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
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
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.IProcessIODataProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.UnitDimIODataSplitProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.ProcessIODataSplitMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class FormGroupDimIODataSplitProcessor
implements IProcessIODataProcessor {
    private final IPorcessDataInputStream originalInputStream;
    private final WorkflowSettingsDO targetSettings;
    private final IRunTimeViewController runTimeViewController;
    private final ProcessIODataSplitMapper mapper;

    public FormGroupDimIODataSplitProcessor(IPorcessDataInputStream originalInputStream, WorkflowSettingsDO targetSettings, IRunTimeViewController runTimeViewController) {
        this.originalInputStream = originalInputStream;
        this.targetSettings = targetSettings;
        this.runTimeViewController = runTimeViewController;
        this.mapper = new ProcessIODataSplitMapper();
    }

    @Override
    public void process(IPorcessDataOutputStream transferOutputStream) {
        ProcessDataOutputDescription originalDescription = this.originalInputStream.readDescription();
        ProcessDataOutputDescription newDescription = new ProcessDataOutputDescription.ProcessDataOutputDescriptionBuilder().workflowObjectType(this.targetSettings.getWorkflowObjectType()).dataTypes((Collection)originalDescription.getOutputDataTypes()).version(originalDescription.getVersion()).businessObjectDimensionNames(originalDescription.getBusinessObjectDimensionNames()).build();
        transferOutputStream.writeDescription(newDescription);
        List periods = this.originalInputStream.getPeriods();
        for (String period : periods) {
            List<String> splitInstanceIds;
            IProcessIOOperation operation;
            IProcessDataReader reader = this.originalInputStream.getProcessDataReaders(period);
            IProcessDataWriter writer = transferOutputStream.getProcessDataWriter(period);
            SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, this.targetSettings.getTaskId());
            if (schemePeriodLinkByPeriodAndTask == null) {
                throw new FormSchemeNotFoundException(this.targetSettings.getTaskId(), period);
            }
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
            IProcessIOInstance instance = reader.readNextInstance();
            while (instance != null) {
                ArrayList<String> splitInstanceIds2 = new ArrayList<String>();
                FormGroupObject formGroupObject = (FormGroupObject)instance.getBusinessObject();
                List formDefinesByGroup = this.runTimeViewController.listFormByGroup(formGroupObject.getFormGroupKey(), formSchemeDefine.getKey());
                for (FormDefine formDefine : formDefinesByGroup) {
                    FormObject splitFormDim = new FormObject(formGroupObject.getDimensions(), formDefine.getKey());
                    String newInstanceId = UUID.randomUUID().toString();
                    splitInstanceIds2.add(newInstanceId);
                    writer.writeInstance(UnitDimIODataSplitProcessor.buildSplitInstance(newInstanceId, instance, (IBusinessObject)splitFormDim));
                }
                this.mapper.putSplitRelation(instance.getId(), splitInstanceIds2);
                instance = reader.readNextInstance();
            }
            ArrayList<IProcessIOOperation> operationGroupByInstanceId = new ArrayList<IProcessIOOperation>();
            String preInstanceId = null;
            while ((operation = reader.readNextOperation()) != null) {
                if (preInstanceId != null && !operation.getInstanceId().equals(preInstanceId)) {
                    splitInstanceIds = this.mapper.getSplitInstanceIds(preInstanceId);
                    for (String splitInstanceId : splitInstanceIds) {
                        for (IProcessIOOperation optGroupByInstanceId : operationGroupByInstanceId) {
                            writer.writeOperation(UnitDimIODataSplitProcessor.buildSplitOperation(splitInstanceId, optGroupByInstanceId));
                        }
                    }
                    operationGroupByInstanceId.clear();
                }
                preInstanceId = operation.getInstanceId();
                operationGroupByInstanceId.add(operation);
            }
            if (operationGroupByInstanceId.isEmpty()) continue;
            splitInstanceIds = this.mapper.getSplitInstanceIds(preInstanceId);
            for (String splitInstanceId : splitInstanceIds) {
                for (IProcessIOOperation optGroupByInstanceId : operationGroupByInstanceId) {
                    writer.writeOperation(UnitDimIODataSplitProcessor.buildSplitOperation(splitInstanceId, optGroupByInstanceId));
                }
            }
            operationGroupByInstanceId.clear();
        }
    }
}

