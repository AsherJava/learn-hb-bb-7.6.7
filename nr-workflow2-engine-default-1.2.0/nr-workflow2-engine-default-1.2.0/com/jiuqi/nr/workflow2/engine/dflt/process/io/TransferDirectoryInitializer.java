/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileOutputStream;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.FormGroupDimIODataMergeProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.FormGroupDimIODataSplitProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.IProcessIODataProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.UnitDimIODataMergeProcessor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.processor.UnitDimIODataSplitProcessor;
import java.io.Closeable;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferDirectoryInitializer
implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String transferDir;
    private final TaskDefine taskDefine;
    private final IPorcessDataInputStream originalInputStream;
    private final WorkflowSettingsDO newSettings;
    private IPorcessDataOutputStream transferOutputStream;
    private IProcessIODataProcessor processor;
    private boolean isNeedProcess;
    private final IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);

    public TransferDirectoryInitializer(String transferDir, IPorcessDataInputStream originalInputStream, WorkflowSettingsDO newSettings) {
        this.transferDir = transferDir;
        this.taskDefine = this.runTimeViewController.getTask(newSettings.getTaskId());
        this.newSettings = newSettings;
        this.originalInputStream = originalInputStream;
        try {
            this.init();
        }
        catch (Exception e) {
            try {
                this.close();
            }
            catch (IOException ioE) {
                throw new RuntimeException(ioE);
            }
            throw new RuntimeException("\u6570\u636e\u8fc1\u79fb\u4e34\u65f6\u8868\u548c\u8868\u5185\u6570\u636e\u521d\u59cb\u5316\u5668[" + this.getClass().getName() + "]\u521d\u59cb\u5316\u5931\u8d25" + e);
        }
    }

    private void init() {
        ProcessDataOutputDescription originalDescription = this.originalInputStream.readDescription();
        WorkflowObjectType originalWorkflowObjectType = originalDescription.getWorkflowObjectType();
        WorkflowObjectType newWorkflowObjectType = this.newSettings.getWorkflowObjectType();
        if (WorkflowObjectType.MAIN_DIMENSION.equals((Object)originalWorkflowObjectType) || WorkflowObjectType.MD_WITH_SFR.equals((Object)originalWorkflowObjectType)) {
            if (WorkflowObjectType.FORM.equals((Object)newWorkflowObjectType) || WorkflowObjectType.FORM_GROUP.equals((Object)newWorkflowObjectType)) {
                this.processor = new UnitDimIODataSplitProcessor(this.originalInputStream, this.newSettings, this.runTimeViewController);
            }
        } else if (WorkflowObjectType.FORM.equals((Object)originalWorkflowObjectType)) {
            if (WorkflowObjectType.MAIN_DIMENSION.equals((Object)newWorkflowObjectType) || WorkflowObjectType.MD_WITH_SFR.equals((Object)newWorkflowObjectType)) {
                this.processor = new UnitDimIODataMergeProcessor(this.originalInputStream, this.newSettings);
            } else if (WorkflowObjectType.FORM_GROUP.equals((Object)newWorkflowObjectType)) {
                this.processor = new FormGroupDimIODataMergeProcessor(this.originalInputStream, this.newSettings, this.runTimeViewController);
            }
        } else if (WorkflowObjectType.FORM_GROUP.equals((Object)originalWorkflowObjectType)) {
            if (WorkflowObjectType.FORM.equals((Object)newWorkflowObjectType)) {
                this.processor = new FormGroupDimIODataSplitProcessor(this.originalInputStream, this.newSettings, this.runTimeViewController);
            } else if (WorkflowObjectType.MAIN_DIMENSION.equals((Object)newWorkflowObjectType) || WorkflowObjectType.MD_WITH_SFR.equals((Object)newWorkflowObjectType)) {
                this.processor = new UnitDimIODataMergeProcessor(this.originalInputStream, this.newSettings);
            }
        }
        if (this.processor != null) {
            this.isNeedProcess = true;
            this.transferOutputStream = new PorcessDataFileOutputStream(this.taskDefine, this.transferDir);
            this.processor.process(this.transferOutputStream);
        } else {
            this.isNeedProcess = false;
        }
    }

    public boolean isNeedProcess() {
        return this.isNeedProcess;
    }

    @Override
    public void close() throws IOException {
        if (this.transferOutputStream != null) {
            this.transferOutputStream.close();
        }
    }
}

