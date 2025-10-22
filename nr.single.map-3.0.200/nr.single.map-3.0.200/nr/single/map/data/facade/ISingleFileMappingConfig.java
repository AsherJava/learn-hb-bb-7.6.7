/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade;

import java.io.Serializable;
import java.util.List;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;

public interface ISingleFileMappingConfig
extends Serializable {
    public SingleFileTaskInfo getTaskInfo();

    public void setTaskInfo(SingleFileTaskInfo var1);

    public SingleFileFmdmInfo getFmdmInfo();

    public void setFmdmInfo(SingleFileFmdmInfo var1);

    public List<SingleFileTableInfo> getTableInfos();

    public void setTableInfos(List<SingleFileTableInfo> var1);
}

