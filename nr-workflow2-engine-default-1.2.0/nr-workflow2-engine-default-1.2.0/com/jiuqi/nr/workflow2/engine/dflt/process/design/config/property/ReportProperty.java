/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;

public interface ReportProperty {
    public String getRename();

    public UploadLayerByLayerStrategy getUploadLayerByLayer();

    public String getForceUpload();

    public FillInDescStrategy getBackDesc();

    public FillInDescStrategy getUploadDesc();

    public RetrieveStrategy getRetrieveOrReturn();
}

