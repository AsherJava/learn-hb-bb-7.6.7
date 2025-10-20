/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

import com.jiuqi.va.query.sql.vo.ResultVO;
import com.jiuqi.va.query.tree.vo.TableHeaderVO;
import java.util.List;

public class QueryResultVO {
    private ResultVO resultVO;
    private List<TableHeaderVO> columns;

    public ResultVO getResultVO() {
        return this.resultVO;
    }

    public void setResultVO(ResultVO resultVO) {
        this.resultVO = resultVO;
    }

    public List<TableHeaderVO> getColumns() {
        return this.columns;
    }

    public void setColumns(List<TableHeaderVO> columns) {
        this.columns = columns;
    }
}

