/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 */
package com.jiuqi.gcreport.samecontrol.env.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.gcreport.samecontrol.env.SameCtrlChgEnvContext;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlExtractManageCond;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SameCtrlChgEnvContextImpl
extends ProgressDataImpl<List<String>>
implements SameCtrlChgEnvContext {
    private SameCtrlExtractReportCond sameCtrlExtractReportCond;
    private SameCtrlOffsetCond sameCtrlOffsetCond;
    private SameCtrlExtractManageCond sameCtrlExtractManageCond;

    public SameCtrlChgEnvContextImpl() {
        this(UUIDUtils.newUUIDStr());
    }

    public SameCtrlChgEnvContextImpl(String sn) {
        super(sn, new CopyOnWriteArrayList(), "SameCtrlChgEnvContextImpl");
    }

    @Override
    public SameCtrlExtractReportCond getSameCtrlExtractReportCond() {
        return this.sameCtrlExtractReportCond;
    }

    public void setSameCtrlExtractReportCond(SameCtrlExtractReportCond sameCtrlExtractReportCond) {
        this.sameCtrlExtractReportCond = sameCtrlExtractReportCond;
    }

    @Override
    public SameCtrlOffsetCond getSameCtrlOffsetCond() {
        return this.sameCtrlOffsetCond;
    }

    public void setSameCtrlOffsetCond(SameCtrlOffsetCond sameCtrlOffsetCond) {
        this.sameCtrlOffsetCond = sameCtrlOffsetCond;
    }

    @Override
    public SameCtrlExtractManageCond getSameCtrlExtractManageCond() {
        return this.sameCtrlExtractManageCond;
    }

    public void setSameCtrlExtractManageCond(SameCtrlExtractManageCond sameCtrlExtractManageCond) {
        this.sameCtrlExtractManageCond = sameCtrlExtractManageCond;
    }

    @Override
    public void addResultItem(String resultItem) {
        if (StringUtils.isEmpty((String)resultItem)) {
            return;
        }
        ((List)this.getResult()).add(resultItem);
    }
}

