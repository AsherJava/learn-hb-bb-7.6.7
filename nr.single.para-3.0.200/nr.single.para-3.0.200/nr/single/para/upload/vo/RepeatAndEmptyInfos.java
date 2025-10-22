/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;

public class RepeatAndEmptyInfos {
    private List<String> emptyInfos;
    private List<String> repeatInfos;

    public List<String> getEmptyInfos() {
        if (this.emptyInfos == null) {
            this.emptyInfos = new ArrayList<String>();
        }
        return this.emptyInfos;
    }

    public void setEmptyInfos(List<String> emptyInfos) {
        this.emptyInfos = emptyInfos;
    }

    public List<String> getRepeatInfos() {
        if (this.repeatInfos == null) {
            this.repeatInfos = new ArrayList<String>();
        }
        return this.repeatInfos;
    }

    public void setRepeatInfos(List<String> repeatInfos) {
        this.repeatInfos = repeatInfos;
    }
}

