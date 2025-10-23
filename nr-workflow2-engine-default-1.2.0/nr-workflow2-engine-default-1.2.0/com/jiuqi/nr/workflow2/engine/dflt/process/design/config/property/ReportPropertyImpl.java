/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.ReportProperty;

public class ReportPropertyImpl
implements ReportProperty {
    private String rename;
    private UploadLayerByLayerStrategy uploadLayerByLayer;
    private String forceUpload;
    private FillInDescStrategy backDesc;
    private FillInDescStrategy uploadDesc;
    private RetrieveStrategy retrieveOrReturn;

    @Override
    public String getRename() {
        return this.rename;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

    @Override
    public UploadLayerByLayerStrategy getUploadLayerByLayer() {
        return this.uploadLayerByLayer;
    }

    public void setUploadLayerByLayer(UploadLayerByLayerStrategy uploadLayerByLayer) {
        this.uploadLayerByLayer = uploadLayerByLayer;
    }

    @Override
    public String getForceUpload() {
        return this.forceUpload;
    }

    public void setForceUpload(String forceUpload) {
        this.forceUpload = forceUpload;
    }

    @Override
    public FillInDescStrategy getBackDesc() {
        return this.backDesc;
    }

    public void setBackDesc(FillInDescStrategy backDesc) {
        this.backDesc = backDesc;
    }

    @Override
    public FillInDescStrategy getUploadDesc() {
        return this.uploadDesc;
    }

    public void setUploadDesc(FillInDescStrategy uploadDesc) {
        this.uploadDesc = uploadDesc;
    }

    @Override
    public RetrieveStrategy getRetrieveOrReturn() {
        return this.retrieveOrReturn;
    }

    public void setRetrieveOrReturn(RetrieveStrategy retrieveOrReturn) {
        this.retrieveOrReturn = retrieveOrReturn;
    }
}

