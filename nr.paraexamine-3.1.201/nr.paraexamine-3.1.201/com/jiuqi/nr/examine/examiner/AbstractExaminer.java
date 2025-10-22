/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.examine.bean.ParamExamineDetailInfo;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ExamineType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.IErrorWriter;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractExaminer {
    private final Logger logger = LogFactory.getLogger(this.getClass());
    protected ExamineTask task;
    protected List<ParamExamineDetailInfo> errors;

    public AbstractExaminer(ExamineTask task) {
        this.task = task;
    }

    public void examine() {
        this.init();
        int taskType = this.task.getTaskType();
        if ((taskType & ExamineType.REFUSE.getValue()) > 0) {
            this.checkRefuse();
        }
        if ((taskType & ExamineType.QUOTE.getValue()) > 0) {
            this.checkQuote();
        }
        if ((taskType & ExamineType.ERROR.getValue()) > 0) {
            this.checkError();
        }
    }

    protected void checkFlowEntity(String key, String entitiesKey, IErrorWriter writer) {
        String[] entityKeys = this.getViewArr(entitiesKey);
        if (entityKeys == null) {
            return;
        }
        Set entitySet = Arrays.stream(entityKeys).collect(Collectors.toSet());
        try {
            DesignTaskFlowsDefine flowsSetting = this.task.getEnv().getNrDesignController().queryFlowDefine(key);
            String flowEntityViews = flowsSetting.getDesignTableDefines();
            if (flowsSetting.getFlowsType() != FlowsType.NOSTARTUP) {
                if (StringUtils.isEmpty((String)flowEntityViews)) {
                    writer.quoteError();
                } else {
                    String[] flowViews = flowEntityViews.split(";");
                    Arrays.stream(flowViews).forEach(entityID -> {
                        if (!entitySet.contains(entityID)) {
                            writer.error();
                        }
                    });
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u53c2\u6570\u68c0\u67e5\u53d1\u751f\u9519\u8bef\uff01", (Throwable)e);
        }
    }

    private String[] getViewArr(String entitiesKey) {
        if (StringUtils.isEmpty((String)entitiesKey)) {
            return null;
        }
        return entitiesKey.split(";");
    }

    protected void writeRefuse(String paraKey, String title, ErrorType errorType) {
        this.writeDB(paraKey, title, errorType, ExamineType.REFUSE);
    }

    protected void writeQuotError(String paraKey, String title, ErrorType errorType) {
        this.writeDB(paraKey, title, errorType, ExamineType.QUOTE);
    }

    protected void writeError(String paraKey, String title, ErrorType errorType) {
        this.writeDB(paraKey, title, errorType, ExamineType.ERROR);
    }

    protected abstract ParaType getParaType();

    private void writeDB(String paraKey, String title, ErrorType errorType, ExamineType type) {
        ParamExamineDetailInfo detail = new ParamExamineDetailInfo();
        detail.setKey(UUIDUtils.getKey());
        detail.setInfoKey(this.task.getCheckInfoKey());
        detail.setParaType(this.getParaType());
        detail.setParaKey(paraKey);
        detail.setExamineType(type);
        detail.setErrorType(errorType);
        detail.setDescription(errorType.getDescription() + (title == null ? "" : ":" + title));
        if (this.task.isNeedWriteDB()) {
            this.task.getEnv().getService().insertDetail(detail);
        } else {
            if (this.errors == null) {
                this.errors = new ArrayList<ParamExamineDetailInfo>();
            }
            this.errors.add(detail);
        }
    }

    public List<ParamExamineDetailInfo> getErrors() {
        return this.errors;
    }

    protected abstract void checkRefuse();

    protected abstract void checkQuote();

    protected abstract void checkError();

    protected abstract void init();
}

