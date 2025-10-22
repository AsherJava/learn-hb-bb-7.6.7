/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.vo;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.upload.domain.CommonParamDTO;

public class TaskQueryVO {
    private CommonParamDTO task;
    private List<CommonParamDTO> formscheme;

    public CommonParamDTO getTask() {
        return this.task;
    }

    public void setTask(CommonParamDTO param) {
        this.task = param;
    }

    public List<CommonParamDTO> getFormscheme() {
        if (this.formscheme == null) {
            this.formscheme = new ArrayList<CommonParamDTO>();
        }
        return this.formscheme;
    }

    public void setFormscheme(List<CommonParamDTO> children) {
        this.formscheme = children;
    }

    public void addChildren(CommonParamDTO commonParamDTO) {
        if (this.formscheme == null) {
            this.formscheme = new ArrayList<CommonParamDTO>();
        }
        this.formscheme.add(commonParamDTO);
    }
}

