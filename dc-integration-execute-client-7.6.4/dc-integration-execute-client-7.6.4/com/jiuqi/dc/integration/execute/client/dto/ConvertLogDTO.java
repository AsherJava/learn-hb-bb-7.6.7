/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.client.dto;

import com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO;
import java.io.Serializable;
import java.util.List;

public class ConvertLogDTO
implements Serializable {
    private static final long serialVersionUID = 1156249362952293952L;
    private List<ConvertLogVO> logs;
    private int total;

    public List<ConvertLogVO> getLogs() {
        return this.logs;
    }

    public void setLogs(List<ConvertLogVO> logs) {
        this.logs = logs;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

