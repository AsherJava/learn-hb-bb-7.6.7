/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.snapshot.bean.FormCompareDifference
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataentry.bean.DataVersionData;
import com.jiuqi.nr.dataentry.bean.DataVersionParam;
import com.jiuqi.nr.dataentry.paramInfo.DataVersionCompareParam;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.snapshot.bean.FormCompareDifference;
import java.util.List;

public interface IDataVersionService {
    public ReturnInfo createOrUpdateDataVersion(DataVersionParam var1) throws JTableException;

    public ReturnInfo createOrUpdateDataVersion(DataVersionParam var1, boolean var2) throws JTableException;

    public List<DataVersionData> queryAll(JtableContext var1);

    public ReturnInfo deleteDataVersion(DataVersionParam var1);

    public AsyncTaskInfo overwriteDataVersion(DataVersionParam var1);

    public List<FormCompareDifference> compareDataVersion(DataVersionCompareParam var1);
}

