/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.bean;

import java.io.Serializable;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.bean.ParaCompareTaskInfo;

public class ParaCompareParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String compareId;
    private ParaCompareOption option;
    private ParaCompareTaskInfo netTaskInfo;

    public String getCompareId() {
        return this.compareId;
    }

    public void setCompareId(String compareId) {
        this.compareId = compareId;
    }

    public ParaCompareOption getOption() {
        if (this.option == null) {
            this.option = new ParaCompareOption();
        }
        return this.option;
    }

    public void setOption(ParaCompareOption option) {
        this.option = option;
    }

    public ParaCompareTaskInfo getNetTaskInfo() {
        if (this.netTaskInfo == null) {
            this.netTaskInfo = new ParaCompareTaskInfo();
        }
        return this.netTaskInfo;
    }

    public void setNetTaskInfo(ParaCompareTaskInfo netTaskInfo) {
        this.netTaskInfo = netTaskInfo;
    }
}

