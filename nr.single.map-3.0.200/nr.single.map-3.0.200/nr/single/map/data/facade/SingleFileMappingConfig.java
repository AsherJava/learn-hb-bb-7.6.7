/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade;

import java.util.List;
import nr.single.map.data.facade.ISingleFileMappingConfig;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;

public class SingleFileMappingConfig
implements ISingleFileMappingConfig {
    private static final long serialVersionUID = 1L;
    private SingleFileTaskInfo taskInfo;
    private SingleFileFmdmInfo fmdmInfo;
    private List<SingleFileTableInfo> tableInfos;

    @Override
    public SingleFileTaskInfo getTaskInfo() {
        return this.taskInfo;
    }

    @Override
    public void setTaskInfo(SingleFileTaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public List<SingleFileTableInfo> getTableInfos() {
        return this.tableInfos;
    }

    @Override
    public void setTableInfos(List<SingleFileTableInfo> tableInfos) {
        this.tableInfos = tableInfos;
    }

    @Override
    public SingleFileFmdmInfo getFmdmInfo() {
        return this.fmdmInfo;
    }

    @Override
    public void setFmdmInfo(SingleFileFmdmInfo fmdmInfo) {
        this.fmdmInfo = fmdmInfo;
    }
}

