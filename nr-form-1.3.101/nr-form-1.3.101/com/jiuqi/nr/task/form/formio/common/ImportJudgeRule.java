/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.model.Cell
 */
package com.jiuqi.nr.task.form.formio.common;

import com.jiuqi.nr.task.form.formio.common.ImportCellType;
import com.jiuqi.nvwa.cellbook.model.Cell;
import java.util.Arrays;
import org.apache.poi.ss.usermodel.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportJudgeRule {
    private static final Logger log = LoggerFactory.getLogger(ImportJudgeRule.class);

    public static ImportCellType judgeType(DataFormat dataFormat, org.apache.poi.ss.usermodel.Cell cell, Cell nvwaCell) {
        String format = dataFormat.getFormat(cell.getCellStyle().getDataFormat());
        log.debug("\u5355\u5143\u683c\u3010{},{}\u3011\uff0c\u7c7b\u578b:{}\uff0cparseFormat:{}", cell.getRowIndex() + 1, cell.getColumnIndex() + 1, cell.getCellType().name(), format);
        if ("General".equalsIgnoreCase(format)) {
            return ImportCellType.FORM_STYLE;
        }
        if (ImportJudgeRule.isNumberFormat(format)) {
            return ImportCellType.BIG_DECIMAL;
        }
        if (ImportJudgeRule.isDecimalFormat(format)) {
            return ImportCellType.BIG_DECIMAL;
        }
        if (ImportJudgeRule.isTextFormat(format)) {
            return ImportCellType.STRING;
        }
        if (ImportJudgeRule.isDateFormat(format)) {
            return ImportCellType.DATE;
        }
        return ImportCellType.FORM_STYLE;
    }

    private static boolean isDateFormat(String format) {
        return "yyyy\"\u5e74\"m\"\u6708\"d\"\u65e5\";@".equals(format);
    }

    private static boolean isTimeFormat(String format) {
        return "h:mm:ss;@".equals(format);
    }

    private static boolean isTextFormat(String format) {
        return "@".equals(format);
    }

    private static boolean isNumberFormat(String format) {
        return format.contains("0_") && !format.contains(".");
    }

    public static boolean isDecimalFormat(String format) {
        String[] patterns = new String[]{"0", "#", "$", "\uffe5", "\u20ac"};
        return Arrays.stream(patterns).anyMatch(format::contains);
    }
}

