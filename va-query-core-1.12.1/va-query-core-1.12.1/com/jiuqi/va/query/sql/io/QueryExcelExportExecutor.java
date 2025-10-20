/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.sql.formula.QueryFormulaContext
 *  com.jiuqi.va.query.sql.vo.Column
 *  com.jiuqi.va.query.sql.vo.QueryGroupField
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.plugin.ToolBarPlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.xssf.streaming.SXSSFCell
 *  org.apache.poi.xssf.streaming.SXSSFRow
 *  org.apache.poi.xssf.streaming.SXSSFSheet
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 */
package com.jiuqi.va.query.sql.io;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.query.common.service.FormulaExecuteHandlerUtil;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.sql.dto.QueryExportGroupStyleEnum;
import com.jiuqi.va.query.sql.formula.QueryFormulaContext;
import com.jiuqi.va.query.sql.vo.Column;
import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.plugin.ToolBarPlugin;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.template.vo.TemplateToolbarInfoVO;
import com.jiuqi.va.query.util.DCQueryExcelTool;
import com.jiuqi.va.query.util.QueryUtils;
import com.jiuqi.va.query.util.export.DCQueryExportUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class QueryExcelExportExecutor {
    private static final Logger logger = LoggerFactory.getLogger(QueryExcelExportExecutor.class);
    private static final int ALIGN_LEFT = 1;
    private static final int ALIGN_RIGHT = 3;
    private DCQueryExcelTool<Map<String, Object>> execTool;
    private List<Column> columns;
    private List<QueryGroupField> groupFields;
    private QueryParamVO queryParamVO;
    private SXSSFSheet currentSheet;
    private int sheetIndex = 1;
    private Map<String, Object> printConfig = new HashMap<String, Object>();

    public QueryExcelExportExecutor() {
    }

    public QueryExcelExportExecutor(QueryParamVO queryParamVO, DCQueryExcelTool<Map<String, Object>> execTool, List<Column> columns, List<QueryGroupField> groupFields) {
        this.queryParamVO = queryParamVO;
        this.execTool = execTool;
        this.columns = columns;
        this.groupFields = groupFields;
    }

    public DCQueryExcelTool<Map<String, Object>> getExecTool() {
        return this.execTool;
    }

    public void setExecTool(DCQueryExcelTool<Map<String, Object>> execTool) {
        this.execTool = execTool;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<QueryGroupField> getGroupFields() {
        return this.groupFields;
    }

    public void setGroupFields(List<QueryGroupField> groupFields) {
        this.groupFields = groupFields;
    }

    private QueryExportGroupStyleEnum getGroupFieldExportGroupType() {
        QueryTemplate queryTemplate = this.queryParamVO.getQueryTemplate();
        String actionID = this.queryParamVO.getActionID();
        if (CollectionUtils.isEmpty(this.groupFields) || Objects.isNull(queryTemplate)) {
            return null;
        }
        return DCQueryExportUtils.getGroupStyle(queryTemplate, actionID);
    }

    public void appendDataList(List<Map<String, Object>> dataList) {
        try {
            this.execTool.setGroupFieldGroupStyle(this.getGroupFieldExportGroupType());
            if (this.currentSheet == null) {
                int blankRowSize = this.getBlankRowSize();
                this.execTool.setTagPrintRowSize(blankRowSize);
                this.currentSheet = this.execTool.writeSheet(this.columns, this.sheetIndex, blankRowSize);
                if (blankRowSize > 0) {
                    this.writeTags();
                }
            }
            int rowSize = this.currentSheet.getLastRowNum() + 1;
            if (dataList.size() + rowSize > DCQueryExcelTool.MAX_ROWSUM) {
                List<Map<String, Object>> firstList = dataList.subList(0, DCQueryExcelTool.MAX_ROWSUM - rowSize);
                this.execTool.writeDataToSheet(firstList, this.columns, rowSize, this.groupFields, this.currentSheet);
                dataList = dataList.subList(DCQueryExcelTool.MAX_ROWSUM - rowSize, dataList.size());
                this.currentSheet = this.execTool.writeSheet(this.columns, this.sheetIndex + 1);
                rowSize = this.currentSheet.getLastRowNum() + 1;
                ++this.sheetIndex;
            }
            this.execTool.writeDataToSheet(dataList, this.columns, rowSize, this.groupFields, this.currentSheet);
        }
        catch (Exception e) {
            logger.error("\u5bfc\u51fa\u5f02\u5e38", e);
            throw new DefinedQueryRuntimeException("\u5bfc\u51fa\u5f02\u5e38");
        }
    }

    public void flushWorkbook(OutputStream outputStream) {
        SXSSFWorkbook workbook = this.execTool.getWorkbook();
        try {
            if (workbook == null) {
                throw new IllegalStateException("Workbook not initialized");
            }
            outputStream.flush();
            workbook.write(outputStream);
        }
        catch (IOException | IllegalStateException e) {
            logger.error(e.getMessage(), e);
            throw new DefinedQueryRuntimeException("\u6587\u4ef6\u5bfc\u51fa\u5931\u8d25\uff1a" + e.getMessage());
        }
        finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                    workbook.dispose();
                }
            }
            catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }

    public int getBlankRowSize() {
        String config;
        Optional<TemplateToolbarInfoVO> first;
        int blankRowSize = 0;
        String actionID = this.queryParamVO.getActionID();
        if (StringUtils.hasText(actionID)) {
            List tools = ((ToolBarPlugin)this.queryParamVO.getQueryTemplate().getPluginByClass(ToolBarPlugin.class)).getTools();
            first = tools.stream().filter(templateToolbarInfoVO -> actionID.equals(templateToolbarInfoVO.getId())).findFirst();
        } else {
            first = ((ToolBarPlugin)this.queryParamVO.getQueryTemplate().getPluginByClass(ToolBarPlugin.class)).getTools().stream().filter(tool -> "export".equals(tool.getAction())).findFirst();
        }
        if (first.isPresent() && StringUtils.hasText(config = first.get().getConfig())) {
            blankRowSize = this.getTagRowNum(config);
            this.printConfig = JSONUtil.parseMap((String)config);
        }
        return blankRowSize;
    }

    private int getTagRowNum(String config) {
        Map map = JSONUtil.parseMap((String)config);
        if (map.containsKey("tagPrint")) {
            Map tagPrintMap = QueryUtils.getMap(map.get("tagPrint"));
            boolean flag = false;
            List<List> dataList = QueryUtils.getList(tagPrintMap.get("data"));
            block0: for (List objectMapList : dataList) {
                for (Map objectMap : objectMapList) {
                    String expressionValue = (String)objectMap.get("v");
                    if (!StringUtils.hasText(expressionValue)) continue;
                    flag = true;
                    continue block0;
                }
            }
            if (flag) {
                return dataList.size();
            }
        }
        return 0;
    }

    public void writeTags() {
        Map configMap = (Map)this.printConfig.get("tagPrint");
        List data = (List)configMap.get("data");
        List rows = (List)configMap.get("rows");
        List styles = (List)configMap.get("styles");
        List mergeInfo = (List)configMap.get("mergeInfo");
        for (int i = 0; i < data.size(); ++i) {
            List row = (List)data.get(i);
            SXSSFRow sheetCoRow = this.currentSheet.getRow(i);
            for (int j = 0; j < row.size(); ++j) {
                SXSSFCell cell = sheetCoRow.getCell(j);
                if (cell == null) continue;
                CellStyle cellStyle = this.execTool.getWorkbook().createCellStyle();
                String s = String.valueOf(((Map)row.get(j)).get("s"));
                Font font = this.execTool.getWorkbook().createFont();
                font.setFontHeightInPoints((short)14);
                font.setColor((short)Short.MAX_VALUE);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                if (!"-1".equals(s)) {
                    QueryExcelExportExecutor.setCellStyle(styles, s, font, cellStyle);
                }
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                String cellValue = this.getCellValue(row, j);
                cell.setCellValue(cellValue);
                int finalI = i;
                int finalJ = j;
                Optional<Map> cellMergeInfo = mergeInfo.stream().filter(merge -> ((Integer)merge.get("rowIndex")).equals(finalI) && ((Integer)merge.get("columnIndex")).equals(finalJ)).findFirst();
                if (!cellMergeInfo.isPresent()) continue;
                int rowIndex = (Integer)cellMergeInfo.get().get("rowIndex");
                int rowSpan = (Integer)cellMergeInfo.get().get("rowSpan");
                int columnSpan = (Integer)cellMergeInfo.get().get("columnSpan");
                int columnIndex = (Integer)cellMergeInfo.get().get("columnIndex");
                CellRangeAddress cra = new CellRangeAddress(rowIndex, rowIndex + rowSpan - 1, columnIndex, columnIndex + columnSpan - 1);
                this.currentSheet.addMergedRegion(cra);
            }
            Map rowConfig = (Map)rows.get(i);
            Integer size = (Integer)rowConfig.get("size");
            double pointsPerMillimeter = 2.8346438836888925;
            double twipsPerPoint = 20.0;
            sheetCoRow.setHeight((short)Math.round((double)size.intValue() * pointsPerMillimeter * twipsPerPoint));
        }
    }

    private static void setCellStyle(List<Map<String, Object>> styles, String s, Font font, CellStyle cellStyle) {
        Map<String, Object> stringStringMap = styles.get(Integer.parseInt(s));
        font.setBold(stringStringMap.get("bold") != null && (Boolean)stringStringMap.get("bold") != false);
        Object fontSize = stringStringMap.get("fontSize");
        if (null != fontSize) {
            font.setFontHeightInPoints(Short.parseShort(String.valueOf(stringStringMap.get("fontSize"))));
        }
        QueryExcelExportExecutor.getAlignment(stringStringMap, cellStyle);
    }

    private String getCellValue(List<Map<String, Object>> row, int j) {
        String expression = String.valueOf(row.get(j).get("v"));
        Object formulaValue = null;
        try {
            QueryFormulaContext queryFormulaContext = new QueryFormulaContext(this.queryParamVO.getParams(), this.queryParamVO.getQueryTemplate());
            formulaValue = FormulaExecuteHandlerUtil.executeFormula(expression, ParamTypeEnum.STRING.getTypeName(), queryFormulaContext);
        }
        catch (Exception e) {
            logger.error("\u6807\u7b7e\u516c\u5f0f\u89e3\u6790\u5f02\u5e38\uff1a" + expression, e);
        }
        return formulaValue == null ? "" : formulaValue.toString();
    }

    private static void getAlignment(Map<String, Object> stringStringMap, CellStyle cellStyle) {
        Object horizontalAlignment = stringStringMap.get("horizontalAlignment");
        if (horizontalAlignment != null) {
            int hval = (Integer)horizontalAlignment;
            if (hval == 1) {
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
            } else if (hval == 3) {
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
            }
        }
    }
}

