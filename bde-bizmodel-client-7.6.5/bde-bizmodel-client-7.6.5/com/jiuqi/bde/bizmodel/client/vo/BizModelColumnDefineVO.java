/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 */
package com.jiuqi.bde.bizmodel.client.vo;

import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import java.util.List;

public class BizModelColumnDefineVO {
    private String fetchSourceCode;
    private String computationModelIcon;
    private List<SelectOptionVO> optionItems;
    private List<ColumnDefineVO> columnDefines;

    public String getFetchSourceCode() {
        return this.fetchSourceCode;
    }

    public void setFetchSourceCode(String fetchSourceCode) {
        this.fetchSourceCode = fetchSourceCode;
    }

    public String getComputationModelIcon() {
        return this.computationModelIcon;
    }

    public void setComputationModelIcon(String computationModelIcon) {
        this.computationModelIcon = computationModelIcon;
    }

    public List<SelectOptionVO> getOptionItems() {
        return this.optionItems;
    }

    public void setOptionItems(List<SelectOptionVO> optionItems) {
        this.optionItems = optionItems;
    }

    public List<ColumnDefineVO> getColumnDefines() {
        return this.columnDefines;
    }

    public void setColumnDefines(List<ColumnDefineVO> columnDefines) {
        this.columnDefines = columnDefines;
    }
}

