/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeReportController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.dto.ITransferContext
 *  com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.face.IResourceDeploy
 *  com.jiuqi.nr.task.api.face.IResourceIOProvider
 */
package com.jiuqi.nr.report.internal;

import com.jiuqi.nr.definition.api.IDesignTimeReportController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.report.internal.ReportResourceDataProvider;
import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.dto.ITransferContext;
import com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.face.IResourceDeploy;
import com.jiuqi.nr.task.api.face.IResourceIOProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportDefineExt
extends AbstractFormSchemeResourceFactory {
    public static final String REPORT_CODE = "REPORT_DEFINE";
    public static final String REPORT_TITLE = "\u62a5\u544a\u6a21\u677f";
    @Autowired
    private IDesignTimeReportController reportDesignTimeController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    public String code() {
        return REPORT_CODE;
    }

    public String title() {
        return REPORT_TITLE;
    }

    public double order() {
        return 3.0;
    }

    public boolean enable(String formSchemeKey) {
        return true;
    }

    public IResourceDataProvider dataProvider() {
        return new ReportResourceDataProvider(this.reportDesignTimeController, this.designTimeViewController);
    }

    public IResourceIOProvider transferProvider(ITransferContext context) {
        return null;
    }

    public IResourceDeploy deployProvider() {
        return null;
    }

    public ComponentDefine component() {
        return new ComponentDefine("reportManage", "@nr", "report");
    }
}

