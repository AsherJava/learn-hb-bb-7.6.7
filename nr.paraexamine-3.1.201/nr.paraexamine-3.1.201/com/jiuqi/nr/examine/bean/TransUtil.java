/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.bean;

import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ExamineStatus;
import com.jiuqi.nr.examine.common.ExamineType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.common.RestoreStatus;

public class TransUtil {
    public ExamineStatus transExamineStatus(Integer type) {
        return ExamineStatus.forValue(type);
    }

    public Integer transExamineStatus(ExamineStatus type) {
        return type.getValue();
    }

    public ParaType transParaType(Integer type) {
        return ParaType.forValue(type);
    }

    public Integer transParaType(ParaType type) {
        return type.getValue();
    }

    public RestoreStatus transRestoreStatus(Integer type) {
        return RestoreStatus.forValue(type);
    }

    public Integer transRestoreStatus(RestoreStatus type) {
        return type.getValue();
    }

    public ErrorType transErrorType(Integer type) {
        return ErrorType.forValue(type);
    }

    public Integer transErrorType(ErrorType type) {
        return type.getValue();
    }

    public ExamineType transExamineType(Integer type) {
        return ExamineType.forValue(type);
    }

    public Integer transExamineType(ExamineType type) {
        return type.getValue();
    }
}

