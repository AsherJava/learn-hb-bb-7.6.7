/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.zb.scheme.web.vo.GenerateItem;
import java.util.ArrayList;
import java.util.List;

public class GenerateZbResult {
    private final List<GenerateItem> resultInfo = new ArrayList<GenerateItem>();
    private int addGroupCount;

    public void putForm(String formKey, String formPath, int addCount, int updateCount) {
        GenerateItem item = new GenerateItem(formKey, formPath, addCount, updateCount);
        this.resultInfo.add(item);
    }

    public List<GenerateItem> getResultInfo() {
        return this.resultInfo;
    }

    public int getAddGroupCount() {
        return this.addGroupCount;
    }

    public void setAddGroupCount(int addGroupCount) {
        this.addGroupCount = addGroupCount;
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("\u65b0\u589e\u5206\u7ec4\u6570\u91cf[").append(this.addGroupCount).append("]");
        sb.append("\r\n");
        this.resultInfo.forEach(item -> {
            sb.append(item.getFormPath());
            sb.append("\uff1a");
            sb.append("\u65b0\u589e\u6307\u6807\u6570\u91cf[").append(item.getAddCount()).append("]");
            sb.append("\u4fee\u6539\u6307\u6807\u6570\u91cf[").append(item.getUpdateCount()).append("]");
            sb.append("\r\n");
        });
        return sb.toString();
    }

    public String toJson() throws Exception {
        return new ObjectMapper().writeValueAsString((Object)this);
    }
}

