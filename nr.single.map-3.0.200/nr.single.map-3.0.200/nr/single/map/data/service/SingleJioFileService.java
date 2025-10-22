/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.file.SingleFile
 *  com.jiuqi.nr.single.core.file.SingleFileConfigInfo
 */
package nr.single.map.data.service;

import com.jiuqi.nr.single.core.file.SingleFile;
import com.jiuqi.nr.single.core.file.SingleFileConfigInfo;
import java.util.List;
import java.util.Map;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;

public interface SingleJioFileService {
    public SingleFileTaskInfo getTaskInfoByJioData(byte[] var1) throws SingleDataException;

    public SingleFileTaskInfo getTaskInfoByJioFile(String var1) throws SingleDataException;

    public SingleFileTaskInfo getTaskInfoByJioFile(byte[] var1) throws SingleDataException;

    public SingleFileTaskInfo getTaskInfoFromSingle(SingleFile var1);

    public SingleFileConfigInfo getSingleInfoByJioFile(String var1) throws SingleDataException;

    public SingleFileConfigInfo getSingleInfoByTaskDir(String var1) throws SingleDataException;

    public SingleFile getSingleFileByTaskDir(String var1) throws SingleDataException;

    public Map<String, Map<String, SingleFileFieldInfo>> getTableFieldMap(List<SingleFileFieldInfo> var1, boolean var2);

    public Map<String, Map<String, SingleFileFieldInfo>> getTableFieldMapEx(List<SingleFileFieldInfo> var1, boolean var2, boolean var3);
}

