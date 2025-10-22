/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import java.util.List;

public class WorkingPaperTableVO {
    private List<WorkingPaperTableHeaderVO> titles;
    private List<WorkingPaperTableDataVO> datas;
    private String systemId;

    public List<WorkingPaperTableHeaderVO> getTitles() {
        return this.titles;
    }

    public void setTitles(List<WorkingPaperTableHeaderVO> titles) {
        this.titles = titles;
    }

    public List<WorkingPaperTableDataVO> getDatas() {
        return this.datas;
    }

    public void setDatas(List<WorkingPaperTableDataVO> datas) {
        this.datas = datas;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}

