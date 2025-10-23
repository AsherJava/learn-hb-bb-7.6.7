/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.nr.transmission.data.vo.TransmissionDataGatherVO;
import java.util.ArrayList;
import java.util.List;

public class TransmissionDataVO {
    List<TransmissionDataGatherVO> dataGathers = new ArrayList<TransmissionDataGatherVO>();
    boolean showExportContent;

    public List<TransmissionDataGatherVO> getDataGathers() {
        return this.dataGathers;
    }

    public void setDataGathers(List<TransmissionDataGatherVO> dataGathers) {
        this.dataGathers = dataGathers;
    }

    public boolean isShowExportContent() {
        return this.showExportContent;
    }

    public boolean getShowExportContent() {
        return this.showExportContent;
    }

    public void setShowExportContent(boolean showExportContent) {
        this.showExportContent = showExportContent;
    }
}

