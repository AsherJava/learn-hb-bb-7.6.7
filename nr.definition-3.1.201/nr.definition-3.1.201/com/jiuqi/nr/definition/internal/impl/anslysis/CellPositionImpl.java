/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.definition.internal.impl.anslysis;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.facade.analysis.CellPosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellPositionImpl
implements CellPosition {
    private static final Logger logger = LogFactory.getLogger(CellPositionImpl.class);
    int colNum;
    int rowNum;

    public CellPositionImpl() {
    }

    public CellPositionImpl(String pos) {
        Pattern p;
        Matcher m;
        if (!StringUtils.isEmpty((String)pos) && (m = (p = Pattern.compile("(\\d+),(\\d+)")).matcher(pos)).find()) {
            logger.info(m.group(0));
            this.colNum = Integer.valueOf(m.group(1));
            this.rowNum = Integer.valueOf(m.group(2));
        }
    }

    public static void main(String[] args) {
        CellPositionImpl cellPosition = new CellPositionImpl("[1,2]");
        logger.info(String.valueOf(cellPosition));
    }

    @Override
    public int getColNum() {
        return this.colNum;
    }

    @Override
    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    @Override
    public int getRowNum() {
        return this.rowNum;
    }

    @Override
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String toString() {
        return "[" + this.colNum + "," + this.rowNum + "]";
    }
}

