/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.vo.DefaultCodeObject;

public class RepeatAndDefaultCodeVO {
    private List<String> repeatKeyList;
    private List<DefaultCodeObject> defaultCodeList;

    public List<String> getRepeatKeyList() {
        if (this.repeatKeyList == null) {
            this.repeatKeyList = new ArrayList<String>();
        }
        return this.repeatKeyList;
    }

    public void setRepeatKeyList(List<String> repeatKeyList) {
        this.repeatKeyList = repeatKeyList;
    }

    public List<DefaultCodeObject> getDefaultCodeList() {
        if (this.defaultCodeList == null) {
            this.defaultCodeList = new ArrayList<DefaultCodeObject>();
        }
        return this.defaultCodeList;
    }

    public void setDefaultCodeList(List<DefaultCodeObject> defaultCodeList) {
        this.defaultCodeList = defaultCodeList;
    }
}

