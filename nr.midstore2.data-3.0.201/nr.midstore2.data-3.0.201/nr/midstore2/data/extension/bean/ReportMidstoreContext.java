/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultSourceData
 *  com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData
 *  com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType
 */
package nr.midstore2.data.extension.bean;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreContext;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultSourceData;
import com.jiuqi.nvwa.midstore.core.definition.bean.dto.MidstoreWorkResultTableData;
import com.jiuqi.nvwa.midstore.core.result.common.MidstoreStatusType;
import java.util.UUID;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import nr.midstore2.data.extension.bean.ReportDimEntityCache;

public class ReportMidstoreContext
extends MidstoreContext {
    private String excuteNrPeriod;
    private MidstoreExeContext exeContext;
    private ReportDataSourceDTO dataSourceDTO;
    private ReportDimEntityCache dimEntityCache;
    private TaskDefine taskDefine;
    private String taskKey;
    private String formSchemeKey;
    private int hasDoTableNum = 0;
    private MidstoreWorkResultSourceData sourceResult;

    public ReportMidstoreContext() {
    }

    public ReportMidstoreContext(MidstoreExeContext exeContext) {
        this.exeContext = exeContext;
    }

    public MidstoreExeContext getExeContext() {
        return this.exeContext;
    }

    public void setExeContext(MidstoreExeContext exeContext) {
        this.exeContext = exeContext;
    }

    public ReportDataSourceDTO getDataSourceDTO() {
        return this.dataSourceDTO;
    }

    public void setDataSourceDTO(ReportDataSourceDTO dataSourceDTO) {
        this.dataSourceDTO = dataSourceDTO;
    }

    public ReportDimEntityCache getDimEntityCache() {
        if (this.dimEntityCache == null) {
            this.dimEntityCache = new ReportDimEntityCache();
        }
        return this.dimEntityCache;
    }

    public void setDimEntityCache(ReportDimEntityCache dimEntityCache) {
        this.dimEntityCache = dimEntityCache;
    }

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public MidstoreWorkResultSourceData getSourceResult() {
        return this.sourceResult;
    }

    public void setSourceResult(MidstoreWorkResultSourceData sourceResult) {
        this.sourceResult = sourceResult;
    }

    public String getExcuteNrPeriod() {
        return this.excuteNrPeriod;
    }

    public void setExcuteNrPeriod(String excuteNrPeriod) {
        this.excuteNrPeriod = excuteNrPeriod;
    }

    public int getHasDoTableNum() {
        return this.hasDoTableNum;
    }

    public void setHasDoTableNum(int hasDoTableNum) {
        this.hasDoTableNum = hasDoTableNum;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updatDoTableNumAsyn(int addTableCount) {
        ReportMidstoreContext reportMidstoreContext = this;
        synchronized (reportMidstoreContext) {
            this.hasDoTableNum += addTableCount;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MidstoreWorkResultTableData recordTableResult(DataTable dataTable) {
        MidstoreWorkResultTableData tableResult = null;
        ReportMidstoreContext reportMidstoreContext = this;
        synchronized (reportMidstoreContext) {
            tableResult = this.getWorkResult().getPeriodResult().getTableResult(dataTable.getKey());
            if (tableResult == null) {
                tableResult = new MidstoreWorkResultTableData();
                tableResult.getTableDTO().setKey(UUID.randomUUID().toString());
                tableResult.getTableDTO().setSourceTableKey(dataTable.getKey());
                tableResult.getTableDTO().setSourceTableCode(dataTable.getCode());
                tableResult.getTableDTO().setResultKey(this.getWorkResult().getPeriodResult().getWorkResultDTO().getKey());
                tableResult.getTableDTO().setSourceType(this.getExeContext().getSourceTypeId());
                tableResult.getTableDTO().setStauts(MidstoreStatusType.STATUS_SUCCESS);
                tableResult.getTableDTO().setObjectErrorCount(0);
                tableResult.getTableDTO().setObjectItemCount(0);
                tableResult.getTableDTO().setTotalRecordSize(0);
                tableResult.getTableDTO().setErrorRecordSize(0);
                this.getWorkResult().getPeriodResult().addTableResult(tableResult);
            }
        }
        return tableResult;
    }
}

