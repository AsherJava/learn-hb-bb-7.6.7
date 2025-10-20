/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.layout.borders.Border
 *  com.itextpdf.layout.borders.DashedBorder
 *  com.itextpdf.layout.borders.DottedBorder
 *  com.itextpdf.layout.borders.DoubleBorder
 *  com.itextpdf.layout.borders.SolidBorder
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.print.adapt.PdfHandler
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 */
package com.jiuqi.va.query.print;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.print.adapt.PdfHandler;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.print.PropNameConsts;
import com.jiuqi.va.query.print.TableCellProp;
import com.jiuqi.va.query.print.TableRowTypeEnum;
import com.jiuqi.va.query.print.domain.QueryPrintThreadLocal;
import com.jiuqi.va.query.print.domain.TablePrintDrawContext;
import com.jiuqi.va.query.util.NumberUtils;
import com.jiuqi.va.query.util.QueryPrintUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class TablePrintControl {
    public static final String WIDTH = "width";
    private Border border;
    private List<TableCellProp> cellProps;
    private float paddingLeft;
    private float paddingTop;
    private float paddingRight;
    private float paddingBottom;
    private float parentWidth;
    private Map<String, Object> props;

    public float getParentWidth() {
        return this.parentWidth;
    }

    public void setParentWidth(float parentWidth) {
        this.parentWidth = parentWidth;
    }

    public int getRows() {
        return (Integer)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.rows.name()), Integer.class);
    }

    public TableRowTypeEnum getRowType(int rowNum) {
        Optional<TableCellProp> first = this.getCells().stream().filter(tableCellProp -> tableCellProp.getRowIndex() == rowNum).findFirst();
        return first.map(TableCellProp::getRowType).orElse(null);
    }

    public int getRowProp() {
        return (Integer)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.rows.name()), Integer.class);
    }

    public int getColumns() {
        return (Integer)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.columns.name()), Integer.class);
    }

    public float getColumnWidthWithSpanCol(TableCellProp cellProp, float totalWidth) {
        int columnIndex = cellProp.getColumnIndex();
        List columnsObjs = (List)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.columnsObj.name()), List.class);
        float width = QueryPrintUtil.millimetersToPoints(Float.parseFloat(String.valueOf(((Map)columnsObjs.get(columnIndex)).get(WIDTH))));
        int i = 0;
        for (int spanColumn = cellProp.getSpanColumn(); spanColumn > 1; --spanColumn) {
            width += QueryPrintUtil.millimetersToPoints(Float.parseFloat(String.valueOf(((Map)columnsObjs.get(columnIndex + ++i)).get(WIDTH))));
        }
        return width;
    }

    public float getRowHeight(int rowIndex) {
        List rowObjs = (List)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.rowsObj.name()), List.class);
        float height = QueryPrintUtil.millimetersToPoints(Float.parseFloat(String.valueOf(((Map)rowObjs.get(rowIndex)).get("height"))));
        return (float)NumberUtils.round(height, 0);
    }

    public boolean getRowAutoHeight(int rowIndex) {
        List rowObjs = (List)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.rowsObj.name()), List.class);
        return (Boolean)Convert.cast(((Map)rowObjs.get(rowIndex)).get("autoHeight"), Boolean.class);
    }

    public Border getBorder() {
        if (this.border == null) {
            Map prop = (Map)this.getProp(PropNameConsts.GridControlPropEnum.border.name());
            float borderWidth = Float.parseFloat(prop.get(PropNameConsts.BorderPropEnum.size.name()).toString());
            String borderStyle = String.valueOf(prop.get(PropNameConsts.BorderPropEnum.style.name()));
            this.border = PropNameConsts.BorderTypeEnum.dotted.name().equals(borderStyle) ? new DottedBorder(borderWidth) : (PropNameConsts.BorderTypeEnum.dashed.name().equals(borderStyle) ? new DashedBorder(borderWidth) : (PropNameConsts.BorderTypeEnum.solid.name().equals(borderStyle) ? new SolidBorder(borderWidth) : new DoubleBorder(borderWidth)));
        }
        return this.border;
    }

    public List<TableCellProp> getCells() {
        if (this.cellProps == null) {
            Object cellProp = this.getProp(PropNameConsts.GridControlPropEnum.cells.name());
            List cells = (List)cellProp;
            if (cells == null || cells.isEmpty()) {
                return null;
            }
            this.cellProps = new ArrayList<TableCellProp>();
            for (Object prop : cells) {
                TableCellProp cell = (TableCellProp)JSONUtil.parseObject((String)JSONUtil.toJSONString(prop), TableCellProp.class);
                this.cellProps.add(cell);
            }
        }
        return this.cellProps;
    }

    public void setProps(Map<String, Object> props) {
        this.props = props;
        this.setPadding();
        this.getCells();
        this.getBorder();
    }

    public Object getProp(String name) {
        return this.props == null ? null : this.props.get(name);
    }

    public Map<String, Object> getProps() {
        return this.props;
    }

    public float getPaddingLeft() {
        return this.paddingLeft;
    }

    public void setPaddingLeft(float paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public float getPaddingTop() {
        return this.paddingTop;
    }

    public void setPaddingTop(float paddingTop) {
        this.paddingTop = paddingTop;
    }

    public float getPaddingRight() {
        return this.paddingRight;
    }

    public void setPaddingRight(float paddingRight) {
        this.paddingRight = paddingRight;
    }

    public float getPaddingBottom() {
        return this.paddingBottom;
    }

    public void setPaddingBottom(float paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    private void setPadding() {
        String paddingValues = (String)Convert.cast((Object)this.getProp(PropNameConsts.CommonPropNameEnum.padding.name()), String.class);
        if (!StringUtils.hasText(paddingValues)) {
            this.paddingBottom = 0.0f;
            this.paddingRight = 0.0f;
            this.paddingLeft = 0.0f;
            this.paddingTop = 0.0f;
            return;
        }
        String[] values = paddingValues.split(" ");
        if (values.length != 4) {
            throw new DefinedQueryRuntimeException("\u8868\u683c\u5e03\u5c40padding\u5c5e\u6027\u6570\u636e\u683c\u5f0f\u975e\u6cd5");
        }
        this.paddingTop = (float)NumberUtils.round(this.convertUnit(Float.valueOf(values[0]).floatValue()), 0);
        this.paddingRight = (float)NumberUtils.round(this.convertUnit(Float.valueOf(values[1]).floatValue()), 0);
        this.paddingBottom = (float)NumberUtils.round(this.convertUnit(Float.valueOf(values[2]).floatValue()), 0);
        this.paddingLeft = (float)NumberUtils.round(this.convertUnit(Float.valueOf(values[3]).floatValue()), 0);
    }

    public boolean getPrintEmptyTableFlag() {
        Boolean flag = (Boolean)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.printEmptyTableFlag.name()), Boolean.class);
        return null == flag || flag != false;
    }

    public Object getRowsObjList() {
        Object prop = this.getProp(PropNameConsts.GridControlPropEnum.rowsObj.name());
        if (Objects.isNull(prop)) {
            return null;
        }
        return Convert.cast((Object)prop, List.class);
    }

    public List<Map<String, Object>> getColumnsObjList() {
        Object prop = this.getProp(PropNameConsts.GridControlPropEnum.columnsObj.name());
        if (Objects.isNull(prop)) {
            return null;
        }
        return (List)Convert.cast((Object)prop, List.class);
    }

    public float convertUnit(float unit) {
        return (float)((double)(unit * 72.0f) / 25.4);
    }

    public float[] getHiddenColWidthPercentValue(boolean showColFlag) {
        List columnsObj = new ArrayList();
        TablePrintDrawContext tablePrintDrawContext = QueryPrintThreadLocal.getTablePrintDrawContext();
        if (Boolean.TRUE.equals(showColFlag) && Objects.nonNull(tablePrintDrawContext) && !CollectionUtils.isEmpty(tablePrintDrawContext.getHiddenColumnIndexList())) {
            List tempList = (List)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.columnsObj.name()), List.class);
            List<Integer> columnIndexList = tablePrintDrawContext.getHiddenColumnIndexList();
            int size = tempList.size();
            for (int i = 0; i < size; ++i) {
                boolean flag = true;
                for (Integer integer : columnIndexList) {
                    if (i != integer) continue;
                    flag = false;
                    break;
                }
                if (!flag) continue;
                columnsObj.add(tempList.get(i));
            }
        } else {
            columnsObj = (List)Convert.cast((Object)this.getProp(PropNameConsts.GridControlPropEnum.columnsObj.name()), List.class);
        }
        List list = columnsObj.stream().map(obj -> obj.get(PropNameConsts.GridColPropEnum.width.name())).collect(Collectors.toList());
        Double sumd = list.stream().mapToDouble(value -> (Double)Convert.cast((Object)value, Double.class)).sum();
        float sum = Float.parseFloat(String.valueOf(sumd));
        float[] width = new float[list.size()];
        int size = list.size();
        for (int i = 0; i < size; ++i) {
            width[i] = PdfHandler.createPercentUnitValue((float)(((Float)Convert.cast(list.get(i), Float.TYPE)).floatValue() * 100.0f / sum));
        }
        return width;
    }
}

