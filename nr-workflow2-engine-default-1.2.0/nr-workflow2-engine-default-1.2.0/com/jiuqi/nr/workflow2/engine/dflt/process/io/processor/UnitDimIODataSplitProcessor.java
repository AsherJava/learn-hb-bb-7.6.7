/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
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
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.processor;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
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
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.IProcessIODataProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.ProcessIODataSplitMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class UnitDimIODataSplitProcessor
implements IProcessIODataProcessor {
    private final IPorcessDataInputStream originalInputStream;
    private final WorkflowSettingsDO targetSettings;
    private final IRunTimeViewController runTimeViewController;
    private final ProcessIODataSplitMapper mapper;

    public UnitDimIODataSplitProcessor(IPorcessDataInputStream originalInputStream, WorkflowSettingsDO targetSettings, IRunTimeViewController runTimeViewController) {
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
            Object operation;
            String preInstanceId;
            ArrayList<Object> operationGroupByInstanceId;
            Object newInstanceId;
            FormObject splitFormDim;
            IProcessIOInstance instance;
            IProcessDataReader reader = this.originalInputStream.getProcessDataReaders(period);
            IProcessDataWriter writer = transferOutputStream.getProcessDataWriter(period);
            SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, transferOutputStream.getTaskKey());
            if (schemePeriodLinkByPeriodAndTask == null) {
                throw new FormSchemeNotFoundException(transferOutputStream.getTaskKey(), period);
            }
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
            if (WorkflowObjectType.FORM.equals((Object)this.targetSettings.getWorkflowObjectType())) {
                List formDefines = this.runTimeViewController.listFormByFormScheme(formSchemeDefine.getKey());
                while ((instance = reader.readNextInstance()) != null) {
                    ArrayList<String> splitInstanceIds2 = new ArrayList<String>();
                    for (FormDefine formDefine : formDefines) {
                        splitFormDim = new FormObject(instance.getBusinessObject().getDimensions(), formDefine.getKey());
                        newInstanceId = UUID.randomUUID().toString();
                        splitInstanceIds2.add((String)newInstanceId);
                        writer.writeInstance(UnitDimIODataSplitProcessor.buildSplitInstance(newInstanceId, instance, (IBusinessObject)splitFormDim));
                    }
                    this.mapper.putSplitRelation(instance.getId(), splitInstanceIds2);
                }
                operationGroupByInstanceId = new ArrayList<Object>();
                preInstanceId = null;
                while ((operation = reader.readNextOperation()) != null) {
                    if (preInstanceId != null && !operation.getInstanceId().equals(preInstanceId)) {
                        splitInstanceIds = this.mapper.getSplitInstanceIds(preInstanceId);
                        for (String splitInstanceId : splitInstanceIds) {
                            for (IProcessIOOperation iProcessIOOperation : operationGroupByInstanceId) {
                                writer.writeOperation(UnitDimIODataSplitProcessor.buildSplitOperation(splitInstanceId, iProcessIOOperation));
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
                    for (IProcessIOOperation iProcessIOOperation : operationGroupByInstanceId) {
                        writer.writeOperation(UnitDimIODataSplitProcessor.buildSplitOperation(splitInstanceId, iProcessIOOperation));
                    }
                }
                operationGroupByInstanceId.clear();
                continue;
            }
            if (!WorkflowObjectType.FORM_GROUP.equals((Object)this.targetSettings.getWorkflowObjectType())) continue;
            List formGroupDefines = this.runTimeViewController.listFormGroupByFormScheme(formSchemeDefine.getKey());
            instance = reader.readNextInstance();
            while (instance != null) {
                ArrayList<String> splitInstanceIds2 = new ArrayList<String>();
                operation = formGroupDefines.iterator();
                while (operation.hasNext()) {
                    FormGroupDefine formGroupDefine = (FormGroupDefine)operation.next();
                    splitFormDim = new FormGroupObject(instance.getBusinessObject().getDimensions(), formGroupDefine.getKey());
                    newInstanceId = UUID.randomUUID().toString();
                    splitInstanceIds2.add((String)newInstanceId);
                    writer.writeInstance(UnitDimIODataSplitProcessor.buildSplitInstance((String)newInstanceId, instance, (IBusinessObject)splitFormDim));
                }
                this.mapper.putSplitRelation(instance.getId(), splitInstanceIds2);
                instance = reader.readNextInstance();
            }
            operationGroupByInstanceId = new ArrayList();
            preInstanceId = null;
            while ((operation = reader.readNextOperation()) != null) {
                if (preInstanceId != null && !operation.getInstanceId().equals(preInstanceId)) {
                    splitInstanceIds = this.mapper.getSplitInstanceIds(preInstanceId);
                    for (String splitInstanceId : splitInstanceIds) {
                        for (IProcessIOOperation iProcessIOOperation : operationGroupByInstanceId) {
                            writer.writeOperation(UnitDimIODataSplitProcessor.buildSplitOperation(splitInstanceId, iProcessIOOperation));
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
                for (IProcessIOOperation iProcessIOOperation : operationGroupByInstanceId) {
                    writer.writeOperation(UnitDimIODataSplitProcessor.buildSplitOperation(splitInstanceId, iProcessIOOperation));
                }
            }
            operationGroupByInstanceId.clear();
        }
    }

    @NotNull
    public static IProcessIOInstance buildSplitInstance(String newInstanceId, IProcessIOInstance instance, IBusinessObject splitDim) {
        ProcessIOInstance splitInstance = new ProcessIOInstance(newInstanceId);
        splitInstance.setBusinessObject(splitDim);
        splitInstance.setCurrentUserTask(instance.getCurrentUserTask());
        splitInstance.setStatus(instance.getStatus());
        splitInstance.setCurrentUserTaskCode(instance.getCurrentUserTaskCode());
        splitInstance.setStartTime(instance.getStartTime());
        splitInstance.setUpdateTime(instance.getUpdateTime());
        return splitInstance;
    }

    @NotNull
    public static IProcessIOOperation buildSplitOperation(String splitInstanceId, IProcessIOOperation operation) {
        ProcessIOOperation splitOperation = new ProcessIOOperation(splitInstanceId);
        splitOperation.setSourceUserTask(operation.getSourceUserTask());
        splitOperation.setTargetUserTask(operation.getTargetUserTask());
        splitOperation.setAction(operation.getAction());
        splitOperation.setOperateTime(operation.getOperateTime());
        splitOperation.setOperator(operation.getOperator());
        splitOperation.setComment(operation.getComment());
        splitOperation.setForceReport(operation.isForceReport());
        return splitOperation;
    }
}

