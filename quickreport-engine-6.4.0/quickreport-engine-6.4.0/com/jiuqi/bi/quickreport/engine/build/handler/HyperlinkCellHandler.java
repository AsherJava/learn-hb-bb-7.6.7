/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.build.handler;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.handler.ICellContentHandler;
import com.jiuqi.bi.quickreport.engine.build.handler.IHandlerContext;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.HyperlinkData;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DataSetNodeProvider;
import com.jiuqi.bi.quickreport.model.HyperlinkInfo;
import com.jiuqi.bi.quickreport.model.HyperlinkType;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class HyperlinkCellHandler
implements ICellContentHandler {
    private ICellContentHandler nextHandler;
    private ReportContext context;
    private HyperlinkEnv env;
    private boolean needMessage;
    private boolean needURL;

    public HyperlinkCellHandler(ICellContentHandler nextHandler, ReportContext context, HyperlinkEnv env) {
        this.nextHandler = nextHandler;
        this.context = context;
        this.env = env;
        this.needMessage = true;
        this.needURL = true;
    }

    public HyperlinkCellHandler setNeedMessage(boolean needMessage) {
        this.needMessage = needMessage;
        return this;
    }

    public HyperlinkCellHandler setNeedURL(boolean needURL) {
        this.needURL = needURL;
        return this;
    }

    @Override
    public void setCell(IHandlerContext context, int col, int row, CellValue value) throws ReportBuildException {
        GridData grid = context.getResultGrid().getGridData();
        if (value.displayValue instanceof HyperlinkData) {
            this.convertToHyperlink(grid, col, row, value);
        } else {
            this.nextHandler.setCell(context, col, row, value);
            if (this.isHyperlink(value)) {
                if (this.isHyperlinkable(value)) {
                    this.setHyperlink(grid, col, row, value);
                } else {
                    this.resetHyperlink(grid, col, row);
                }
            }
        }
    }

    private void convertToHyperlink(GridData grid, int col, int row, CellValue value) {
        HyperlinkData linkData = (HyperlinkData)value.displayValue;
        GridCell cell = grid.getCellEx(col, row);
        cell.setHyperlink(true);
        cell.setLinkInformation(linkData.text(), linkData.url(), linkData.target());
        if (cell.getFontColor() == 0 && !cell.getFontUnderLine()) {
            cell.setFontColor(255);
            cell.setFontUnderLine(true);
        }
        grid.setCell(cell);
        grid.setObj(col, row, (Object)value);
    }

    private boolean isHyperlink(CellValue value) {
        return value._bindingInfo != null && value._bindingInfo.getCellMap() != null && value._bindingInfo.getCellMap().getHyperlink().getType() != HyperlinkType.NONE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isHyperlinkable(CellValue value) throws ReportBuildException {
        switch (value._bindingInfo.getCellMap().getHyperlink().getType()) {
            case MESSAGE: {
                if (this.needMessage) break;
                return false;
            }
            case NORMAL: {
                if (this.needURL) break;
                return false;
            }
            default: {
                return false;
            }
        }
        if (value._bindingInfo.getHyperlinkFilter() == null) {
            return true;
        }
        if (value._bindingInfo == null || !value._bindingInfo.isMaster()) return value._bindingInfo.getHyperlinkFilter().judge(this.context);
        value._restrictions.forEach(d -> d.fillFilters(this.context.getCurrentFilters()));
        try {
            boolean bl = value._bindingInfo.getHyperlinkFilter().judge(this.context);
            this.context.getCurrentFilters().clear();
            return bl;
        }
        catch (Throwable throwable) {
            try {
                this.context.getCurrentFilters().clear();
                throw throwable;
            }
            catch (ReportExpressionException e) {
                throw new ReportBuildException("\u5355\u5143\u683c" + value._bindingInfo.getPosition() + "\u8d85\u94fe\u63a5\u9002\u5e94\u6761\u4ef6\u5224\u65ad\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
        }
    }

    private void setHyperlink(GridData grid, int col, int row, CellValue value) throws ReportBuildException {
        GridCell cell = grid.getCellEx(col, row);
        String cellData = cell.getCellData();
        String showText = cell.getShowText();
        if (StringUtils.isEmpty((String)cellData)) {
            return;
        }
        int cellID = this.env.addCell(value._bindingInfo);
        int filterID = this.getFilterID(value);
        HyperlinkInfo hyperlink = value._bindingInfo.getCellMap().getHyperlink();
        String url = this.buildLinkURL(hyperlink, cellID, filterID);
        String target = this.getLinkTarget(hyperlink);
        cell.setHyperlink(true);
        cell.setLinkInformation(showText, url, target, cellData);
        grid.setCell(cell);
    }

    private void resetHyperlink(GridData grid, int col, int row) {
        GridCell cell = grid.getCell(col, row);
        if (cell.getFontUnderLine()) {
            cell.setFontUnderLine(false);
            grid.setCell(cell);
        }
    }

    private String buildLinkURL(HyperlinkInfo hyperlink, int cellID, int filterID) {
        StringBuilder url = new StringBuilder();
        url.append("qr://").append(hyperlink.getType() == HyperlinkType.MESSAGE ? "msg" : "link").append("?").append("cell_id").append('=').append(cellID).append('&').append("filter_id").append('=').append(filterID);
        return url.toString();
    }

    private String getLinkTarget(HyperlinkInfo hyperlink) {
        switch (hyperlink.getTargetMode()) {
            case BLANK: {
                return "_blank";
            }
            case SELF: {
                return "_self";
            }
        }
        return null;
    }

    private int getFilterID(CellValue value) throws ReportBuildException {
        List<IFilterDescriptor> filters = value._restrictions != null && this.context.getCurrentFilters().isEmpty() ? this.getFilters(value._restrictions) : this.context.getCurrentFilters();
        filters = this.rebuildRowNumFilters(filters);
        return this.env.addRestrictions(filters);
    }

    private List<IFilterDescriptor> getFilters(List<AxisDataNode> restrictions) {
        ArrayList<IFilterDescriptor> filters = new ArrayList<IFilterDescriptor>();
        for (AxisDataNode data : restrictions) {
            if (data.getRegion().isStatic()) continue;
            data.getRegion().appendNexFilters(filters);
            filters.add(data.toFilter());
        }
        return filters;
    }

    private List<IFilterDescriptor> rebuildRowNumFilters(List<IFilterDescriptor> filters) throws ReportBuildException {
        List<IFilterDescriptor> rowNums = this.findRowNumFilters(filters);
        if (rowNums.isEmpty()) {
            return filters;
        }
        ArrayList<IFilterDescriptor> restrictions = new ArrayList<IFilterDescriptor>();
        HashSet<String> dataSets = new HashSet<String>();
        for (IFilterDescriptor rowNum : rowNums) {
            if (!(rowNum instanceof ValueFilterDescriptor) || dataSets.contains(rowNum.getDataSetName())) continue;
            dataSets.add(rowNum.getDataSetName());
            this.collectDSRestrictions((ValueFilterDescriptor)rowNum, restrictions);
        }
        this.reserveResrictions(filters, dataSets, restrictions);
        return restrictions;
    }

    private List<IFilterDescriptor> findRowNumFilters(List<IFilterDescriptor> filters) {
        ArrayList<IFilterDescriptor> rowNums = new ArrayList<IFilterDescriptor>();
        for (IFilterDescriptor filter : filters) {
            if (StringUtils.isEmpty((String)filter.getDataSetName()) || filter.getField() != DataSetNodeProvider.SYS_ROWNUM) continue;
            rowNums.add(filter);
        }
        return rowNums;
    }

    private void collectDSRestrictions(ValueFilterDescriptor rowNumFilter, List<IFilterDescriptor> restrictions) throws ReportBuildException {
        BIDataSet dataSet;
        DSModel model;
        try {
            model = this.context.openDataSetModel(rowNumFilter.getDataSetName());
            dataSet = this.context.openDataSet(rowNumFilter.getDataSetName(), Arrays.asList(rowNumFilter));
        }
        catch (ReportContextException e) {
            throw new ReportBuildException(e);
        }
        for (DSField field : model.getFields()) {
            if (field.getFieldType() != FieldType.TIME_DIM && field.getFieldType() != FieldType.GENERAL_DIM || field.getKeyField() == null || !field.getName().equals(field.getKeyField())) continue;
            IFilterDescriptor filter = this.createFieldFilter(dataSet, model, field);
            restrictions.add(filter);
        }
    }

    private IFilterDescriptor createFieldFilter(BIDataSet dataSet, DSModel model, DSField field) throws ReportBuildException {
        BIDataSet valDS;
        try {
            valDS = dataSet.distinct(Arrays.asList(field.getName()));
        }
        catch (BIDataSetException e) {
            throw new ReportBuildException(e);
        }
        switch (valDS.getRecordCount()) {
            case 0: {
                return new ValueFilterDescriptor(model.getName(), field, null);
            }
            case 1: {
                return new ValueFilterDescriptor(model.getName(), field, valDS.get(0).getValue(0));
            }
        }
        ArrayList<Object> values = new ArrayList<Object>();
        for (BIDataRow row : valDS) {
            values.add(row.getValue(0));
        }
        return new ValuesFilterDescriptor(model.getName(), field, values);
    }

    private void reserveResrictions(List<IFilterDescriptor> filters, Set<String> dataSets, List<IFilterDescriptor> restrictions) {
        for (IFilterDescriptor filter : filters) {
            if (filter.getField() == DataSetNodeProvider.SYS_ROWNUM || dataSets.contains(filter.getDataSetName())) continue;
            restrictions.add(filter);
        }
    }
}

