/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.reader.JdbcCheckEventInfoReader;
import java.math.BigDecimal;
import java.sql.ResultSet;

public class JdbcCheckValueReader
extends JdbcCheckEventInfoReader {
    private int rightFieldIndex;

    public JdbcCheckValueReader(ResultSet rs, int fieldIndex, int dataType, int rightFieldIndex) {
        super(rs, fieldIndex, dataType);
        this.rightFieldIndex = rightFieldIndex;
    }

    @Override
    public void readToEvent(FormulaCheckEventImpl event) throws Exception {
        AbstractData leftValue = this.readField(this.fieldIndex);
        event.setLeftValue(leftValue);
        AbstractData rightValue = null;
        rightValue = this.readField(this.rightFieldIndex);
        event.setRightValue(rightValue);
        AbstractData differenceValue = null;
        if (this.dataType == 4) {
            int left = leftValue.getAsInt();
            int right = leftValue.getAsInt();
            differenceValue = AbstractData.valueOf(left - right);
        } else if (this.dataType == 3) {
            double left = leftValue.getAsFloat();
            double right = rightValue.getAsFloat();
            differenceValue = AbstractData.valueOf(left - right);
        } else if (this.dataType == 10) {
            BigDecimal left = leftValue.getAsCurrency();
            BigDecimal right = rightValue.getAsCurrency();
            differenceValue = AbstractData.valueOf(left.subtract(right), this.dataType);
        }
        if (differenceValue == null) {
            differenceValue = DataTypes.getNullValue(this.dataType);
        }
        event.setDifferenceValue(differenceValue);
    }
}

