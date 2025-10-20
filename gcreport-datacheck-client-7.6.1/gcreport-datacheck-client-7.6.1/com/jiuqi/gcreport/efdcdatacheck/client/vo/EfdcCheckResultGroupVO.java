/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.FormVo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EfdcCheckResultGroupVO {
    private List<List<EfdcCheckResultVO>> efdcCheckResultVOs = new ArrayList<List<EfdcCheckResultVO>>();
    private List<FormVo> formVos = new ArrayList<FormVo>();
    private int locateFormIndex;
    private String periodTitle;
    private String msg;
    private Map<String, Object> options;

    public void addOneGroup(FormVo formVo, List<EfdcCheckResultVO> efdcCheckResultVOList) {
        this.formVos.add(formVo);
        this.efdcCheckResultVOs.add(efdcCheckResultVOList);
    }

    public List<List<EfdcCheckResultVO>> getEfdcCheckResultVOs() {
        return this.efdcCheckResultVOs;
    }

    public List<FormVo> getFormVos() {
        return this.formVos;
    }

    public int getLocateFormIndex() {
        return this.locateFormIndex;
    }

    public void setLocateFormIndex(int locateFormIndex) {
        this.locateFormIndex = locateFormIndex;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getOptions() {
        return this.options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
}

