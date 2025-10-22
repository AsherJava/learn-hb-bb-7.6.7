/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.impl;

import com.jiuqi.np.office.excel2.steam.xlsx.exceptions.NotSupportedException;
import com.jiuqi.np.office.excel2.steam.xlsx.impl.Supplier;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class StreamingCell
implements Cell {
    private static final Supplier NULL_SUPPLIER = new Supplier(){

        @Override
        public Object getContent() {
            return null;
        }
    };
    private static final String FALSE_AS_STRING = "0";
    private static final String TRUE_AS_STRING = "1";
    private int columnIndex;
    private int rowIndex;
    private final boolean use1904Dates;
    private Supplier contentsSupplier = NULL_SUPPLIER;
    private Object rawContents;
    private String formula;
    private String numericFormat;
    private Short numericFormatIndex;
    private String type;
    private Row row;
    private CellStyle cellStyle;
    private boolean formulaType;

    public StreamingCell(int columnIndex, int rowIndex, boolean use1904Dates) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        this.use1904Dates = use1904Dates;
    }

    public void setContentSupplier(Supplier contentsSupplier) {
        this.contentsSupplier = contentsSupplier;
    }

    public void setRawContents(Object rawContents) {
        this.rawContents = rawContents;
    }

    public String getNumericFormat() {
        return this.numericFormat;
    }

    public void setNumericFormat(String numericFormat) {
        this.numericFormat = numericFormat;
    }

    public Short getNumericFormatIndex() {
        return this.numericFormatIndex;
    }

    public void setNumericFormatIndex(Short numericFormatIndex) {
        this.numericFormatIndex = numericFormatIndex;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(boolean formulaType) {
        this.formulaType = formulaType;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    @Override
    public int getColumnIndex() {
        return this.columnIndex;
    }

    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    @Override
    public Row getRow() {
        return this.row;
    }

    @Override
    public CellType getCellType() {
        if (this.formulaType) {
            return CellType.FORMULA;
        }
        if (this.contentsSupplier.getContent() == null || this.type == null) {
            return CellType.BLANK;
        }
        if ("n".equals(this.type)) {
            return CellType.NUMERIC;
        }
        if ("s".equals(this.type) || "inlineStr".equals(this.type) || "str".equals(this.type)) {
            return CellType.STRING;
        }
        if ("str".equals(this.type)) {
            return CellType.FORMULA;
        }
        if ("b".equals(this.type)) {
            return CellType.BOOLEAN;
        }
        if ("e".equals(this.type)) {
            return CellType.ERROR;
        }
        throw new UnsupportedOperationException("Unsupported cell type '" + this.type + "'");
    }

    @Override
    public String getStringCellValue() {
        Object c = this.contentsSupplier.getContent();
        return c == null ? "" : c.toString();
    }

    @Override
    public double getNumericCellValue() {
        return this.rawContents == null ? 0.0 : Double.parseDouble((String)this.rawContents);
    }

    @Override
    public Date getDateCellValue() {
        if (this.getCellType() == CellType.STRING) {
            throw new IllegalStateException("Cell type cannot be CELL_TYPE_STRING");
        }
        return this.rawContents == null ? null : DateUtil.getJavaDate(this.getNumericCellValue(), this.use1904Dates);
    }

    @Override
    public boolean getBooleanCellValue() {
        CellType cellType = this.getCellType();
        if (cellType == CellType.BLANK) {
            return false;
        }
        if (cellType == CellType.BOOLEAN) {
            return this.rawContents != null && TRUE_AS_STRING.equals(this.rawContents);
        }
        if (cellType == CellType.FORMULA) {
            throw new NotSupportedException();
        }
        throw StreamingCell.typeMismatch(CellType.BOOLEAN, cellType, false);
    }

    private static RuntimeException typeMismatch(CellType expectedType, CellType actualType, boolean isFormulaCell) {
        String msg = "Cannot get a " + StreamingCell.getCellTypeName(expectedType) + " value from a " + StreamingCell.getCellTypeName(actualType) + " " + (isFormulaCell ? "formula " : "") + "cell";
        return new IllegalStateException(msg);
    }

    private static String getCellTypeName(CellType cellType) {
        if (cellType == CellType.BLANK) {
            return "blank";
        }
        if (cellType == CellType.STRING) {
            return "text";
        }
        if (cellType == CellType.BOOLEAN) {
            return "boolean";
        }
        if (cellType == CellType.ERROR) {
            return "error";
        }
        if (cellType == CellType.NUMERIC) {
            return "numeric";
        }
        if (cellType == CellType.FORMULA) {
            return "formula";
        }
        return "#unknown cell type (" + (Object)((Object)cellType) + ")#";
    }

    @Override
    public CellStyle getCellStyle() {
        return this.cellStyle;
    }

    @Override
    public String getCellFormula() {
        if (!this.formulaType) {
            throw new IllegalStateException("This cell does not have a formula");
        }
        return this.formula;
    }

    @Override
    public CellType getCachedFormulaResultType() {
        if (this.formulaType) {
            if (this.contentsSupplier.getContent() == null || this.type == null) {
                return CellType.BLANK;
            }
            if ("n".equals(this.type)) {
                return CellType.NUMERIC;
            }
            if ("s".equals(this.type) || "inlineStr".equals(this.type) || "str".equals(this.type)) {
                return CellType.STRING;
            }
            if ("b".equals(this.type)) {
                return CellType.BOOLEAN;
            }
            if ("e".equals(this.type)) {
                return CellType.ERROR;
            }
            throw new UnsupportedOperationException("Unsupported cell type '" + this.type + "'");
        }
        throw new IllegalStateException("Only formula cells have cached results");
    }

    @Override
    public Sheet getSheet() {
        throw new NotSupportedException();
    }

    @Override
    public void setCellValue(double value) {
        throw new NotSupportedException();
    }

    @Override
    public void setCellValue(Date value) {
        throw new NotSupportedException();
    }

    @Override
    public void setCellValue(Calendar value) {
        throw new NotSupportedException();
    }

    @Override
    public void setCellValue(RichTextString value) {
        throw new NotSupportedException();
    }

    @Override
    public void setCellValue(String value) {
        throw new NotSupportedException();
    }

    @Override
    public void setCellFormula(String formula) throws FormulaParseException {
        throw new NotSupportedException();
    }

    @Override
    public XSSFRichTextString getRichStringCellValue() {
        CellType cellType = this.getCellType();
        XSSFRichTextString rt = null;
        if (cellType == CellType.BLANK) {
            rt = new XSSFRichTextString("");
        } else if (cellType == CellType.STRING) {
            rt = new XSSFRichTextString(this.getStringCellValue());
        } else {
            throw new NotSupportedException();
        }
        return rt;
    }

    @Override
    public void setCellValue(boolean value) {
        throw new NotSupportedException();
    }

    @Override
    public void setCellErrorValue(byte value) {
        throw new NotSupportedException();
    }

    @Override
    public byte getErrorCellValue() {
        if (this.rawContents == null || this.rawContents.equals("")) {
            return 0;
        }
        try {
            return FormulaError.forString(this.rawContents.toString()).getCode();
        }
        catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unexpected error code", e);
        }
    }

    @Override
    public void setAsActiveCell() {
        throw new NotSupportedException();
    }

    @Override
    public void setCellComment(Comment comment) {
        throw new NotSupportedException();
    }

    @Override
    public Comment getCellComment() {
        throw new NotSupportedException();
    }

    @Override
    public void removeCellComment() {
        throw new NotSupportedException();
    }

    @Override
    public Hyperlink getHyperlink() {
        throw new NotSupportedException();
    }

    @Override
    public void setHyperlink(Hyperlink link) {
        throw new NotSupportedException();
    }

    @Override
    public CellRangeAddress getArrayFormulaRange() {
        throw new NotSupportedException();
    }

    @Override
    public boolean isPartOfArrayFormulaGroup() {
        throw new NotSupportedException();
    }

    @Override
    public void setCellType(CellType cellType) {
        throw new NotSupportedException();
    }

    @Override
    public void setBlank() {
        throw new NotSupportedException();
    }

    @Override
    public void removeFormula() throws IllegalStateException {
        throw new NotSupportedException();
    }

    @Override
    public CellAddress getAddress() {
        throw new NotSupportedException();
    }

    @Override
    public void removeHyperlink() {
        throw new NotSupportedException();
    }

    @Override
    public void setCellValue(LocalDateTime value) {
        throw new NotSupportedException();
    }

    @Override
    public LocalDateTime getLocalDateTimeCellValue() {
        throw new NotSupportedException();
    }
}

