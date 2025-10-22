/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO;
import java.util.List;

public class ILFieldPageVO {
    private int count;
    private List<ILFieldVO> iLFieldVOList;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ILFieldVO> getILFieldVOList() {
        return this.iLFieldVOList;
    }

    public void setILFieldVOList(List<ILFieldVO> iLFieldVOList) {
        this.iLFieldVOList = iLFieldVOList;
    }
}

