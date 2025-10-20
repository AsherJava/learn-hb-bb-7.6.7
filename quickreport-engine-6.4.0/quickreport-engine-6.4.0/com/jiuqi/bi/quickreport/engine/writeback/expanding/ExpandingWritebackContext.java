/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.writeback.expanding;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.engine.writeback.expanding.ExpandingWritebackInfo;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.quickreport.writeback.IWritebackContext;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class ExpandingWritebackContext
implements IWritebackContext {
    private WritebackModel model;
    private String userID;
    private List<Integer> rowMaps;

    public ExpandingWritebackContext(WritebackModel model, String userID) {
        this.model = model;
        this.userID = userID;
        this.rowMaps = new ArrayList<Integer>();
    }

    @Override
    public String getUserId() {
        return this.userID;
    }

    public String getSheetName() {
        return this.model.getSheetName();
    }

    public List<Integer> getRowMaps() {
        return this.rowMaps;
    }

    @Override
    public void raiseError(MemoryDataSet<?> data, int rowNum, String fieldName, String errMessage, Throwable cause) throws WritebackException {
        if (rowNum < 0 || rowNum >= this.rowMaps.size() || StringUtils.isEmpty((String)fieldName)) {
            throw new WritebackException(errMessage, cause);
        }
        Column column = data.getMetadata().find(fieldName);
        if (column == null || !(column.getInfo() instanceof ExpandingWritebackInfo)) {
            throw new WritebackException(errMessage, cause);
        }
        ExpandingWritebackInfo info = (ExpandingWritebackInfo)column.getInfo();
        int row = this.rowMaps.get(rowNum);
        Position pos = Position.valueOf((int)info.getCol(), (int)row);
        StringBuilder message = new StringBuilder();
        message.append("\u6267\u884c\u6570\u636e\u56de\u5199\u51fa\u9519\uff0c\u5904\u7406\u5b57\u6bb5[").append(fieldName).append("]\u7684\u503c\u51fa\u9519\uff08").append(this.model.getSheetName()).append("!").append(pos).append("\uff09");
        if (!StringUtils.isEmpty((String)errMessage)) {
            message.append("\uff0c").append(errMessage);
        } else if (cause != null) {
            message.append("\uff0c").append(cause.getMessage());
        }
        throw new WritebackException(message.toString(), cause);
    }

    @Override
    public WritebackModel getModel() {
        return this.model;
    }
}

