/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public class BSFormClearTableInfo {
    private boolean floatForm;
    private TaskDefine taskDefine;
    private TableModelDefine tableModelDefine;
    private List<ColumnModelDefine> dataColumnModelDefines;
    private List<ColumnModelDefine> bizColumnModelDefines;
    private String curSummaryDBTableName;
    private String DWColumnCode;
    private String PeriodColumnCode;
    private String SchemeColumnCode;
    private String defaultTableName;

    public boolean isFloatForm() {
        return this.floatForm;
    }

    public void setFloatForm(boolean floatForm) {
        this.floatForm = floatForm;
    }

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public TableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    public void setTableModelDefine(TableModelDefine tableModelDefine) {
        this.tableModelDefine = tableModelDefine;
    }

    public List<ColumnModelDefine> getDataColumnModelDefines() {
        return this.dataColumnModelDefines;
    }

    public void setDataColumnModelDefines(List<ColumnModelDefine> dataColumnModelDefines) {
        this.dataColumnModelDefines = dataColumnModelDefines;
    }

    public List<ColumnModelDefine> getBizColumnModelDefines() {
        return this.bizColumnModelDefines;
    }

    public void setBizColumnModelDefines(List<ColumnModelDefine> bizColumnModelDefines) {
        this.bizColumnModelDefines = bizColumnModelDefines;
    }

    public String getCurSummaryDBTableName() {
        return this.curSummaryDBTableName;
    }

    public void setCurSummaryDBTableName(String curSummaryDBTableName) {
        this.curSummaryDBTableName = curSummaryDBTableName;
    }

    public String getDWColumnCode() {
        return this.DWColumnCode;
    }

    public void setDWColumnCode(String dWColumnCode) {
        this.DWColumnCode = dWColumnCode;
    }

    public String getPeriodColumnCode() {
        return "DATATIME";
    }

    public void setPeriodColumnCode(String periodColumnCode) {
        this.PeriodColumnCode = periodColumnCode;
    }

    public String getSchemeColumnCode() {
        return "GATHER_SCHEME_CODE";
    }

    public void setSchemeColumnCode(String schemeColumnCode) {
        this.SchemeColumnCode = schemeColumnCode;
    }

    public String getDefaultTableName() {
        return this.defaultTableName;
    }

    public void setDefaultTableName(String defaultTableName) {
        this.defaultTableName = defaultTableName;
    }
}

