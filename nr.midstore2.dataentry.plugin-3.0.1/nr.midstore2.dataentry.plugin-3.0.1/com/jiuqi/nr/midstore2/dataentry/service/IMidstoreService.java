/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.JQException
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.midstore2.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.midstore2.dataentry.bean.MidstoreParam;
import com.jiuqi.nr.midstore2.dataentry.web.vo.MidstoreResultVO;
import javax.servlet.http.HttpServletResponse;

public interface IMidstoreService {
    public void midstorePullPush(MidstoreParam var1, AsyncTaskMonitor var2);

    public void exportResult(MidstoreResultVO var1, HttpServletResponse var2) throws JQException;
}

