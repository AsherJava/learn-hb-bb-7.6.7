/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.dc.datamapping.client.vo;

import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.va.domain.common.PageVO;
import java.io.Serializable;

public class DataRefListVO
implements Serializable {
    private static final long serialVersionUID = -650452958706074654L;
    private PageVO<DataRefDTO> pageVo;
    private int unRefCount;

    public PageVO<DataRefDTO> getPageVo() {
        return this.pageVo;
    }

    public void setPageVo(PageVO<DataRefDTO> pageVo) {
        this.pageVo = pageVo;
    }

    public int getUnRefCount() {
        return this.unRefCount;
    }

    public void setUnRefCount(int unRefCount) {
        this.unRefCount = unRefCount;
    }
}

