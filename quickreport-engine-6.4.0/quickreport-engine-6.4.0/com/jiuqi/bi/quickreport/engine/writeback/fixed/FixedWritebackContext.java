/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.writeback.fixed;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.engine.writeback.fixed.FixedWritebackInfo;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.quickreport.writeback.IWritebackContext;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.util.StringUtils;

final class FixedWritebackContext
implements IWritebackContext {
    private WritebackModel model;
    private String userID;

    public FixedWritebackContext(WritebackModel model, String userID) {
        this.model = model;
        this.userID = userID;
    }

    @Override
    public String getUserId() {
        return this.userID;
    }

    @Override
    public void raiseError(MemoryDataSet<?> data, int rowNum, String fieldName, String errMessage, Throwable cause) throws WritebackException {
        Column column = data.getMetadata().find(fieldName);
        if (column == null || !(column.getInfo() instanceof FixedWritebackInfo)) {
            throw new WritebackException(errMessage, cause);
        }
        FixedWritebackInfo info = (FixedWritebackInfo)column.getInfo();
        StringBuilder message = new StringBuilder();
        message.append("\u6267\u884c\u6570\u636e\u56de\u5199\u51fa\u9519\uff0c\u5904\u7406\u5b57\u6bb5[").append(fieldName).append("]\u7684\u503c\u51fa\u9519\uff08").append(this.model.getSheetName()).append("!").append(info.getPosition()).append("\uff09");
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

