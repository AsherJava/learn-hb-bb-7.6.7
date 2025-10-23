/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ReportAuditType
 */
package com.jiuqi.nr.workflow2.engine.core.settings.compatible;

import com.jiuqi.nr.definition.common.ReportAuditType;
import java.util.List;
import org.springframework.lang.Nullable;

public interface Workflow2EngineCompatibleExtend {
    public String getWorkflowEngine();

    public boolean isSubmitEnabled(String var1);

    public String getUploadLayerByLayer(String var1);

    public String getForceUpload(String var1);

    public String getBackDesc(String var1);

    public String getUploadDesc(String var1);

    public String getReportNodeRetrieveOrReturn(String var1);

    public boolean isReturnLayerByLayer(String var1);

    public boolean isReturnAllSuperior(String var1);

    public String getReturnDesc(String var1);

    public String getReturnType(String var1);

    public List<String> getCalculateFormulaSchemeList(String var1, @Nullable String var2, String var3);

    public List<String> getReviewFormulaSchemeList(String var1, @Nullable String var2, String var3);

    public ReportAuditType getReviewCurrencyType(String var1, @Nullable String var2, String var3);

    public List<String> getReviewCustomCurrencyValue(String var1, @Nullable String var2, String var3);

    public List<Integer> getIgnoreErrorStatus(String var1, @Nullable String var2, String var3);

    public List<Integer> getNeedCommentErrorStatus(String var1, @Nullable String var2, String var3);

    public boolean getSysMsgShow(String var1, String var2, @Nullable String var3);

    public boolean getMailShow(String var1, @Nullable String var2, String var3);
}

