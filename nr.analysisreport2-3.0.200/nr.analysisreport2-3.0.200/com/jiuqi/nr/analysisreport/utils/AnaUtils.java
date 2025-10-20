/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.GridFieldList
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.grid.CellDataProperty
 *  com.jiuqi.np.grid.CellDataPropertyIntf
 *  com.jiuqi.np.grid.NumberCellProperty
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.common.params.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 *  com.jiuqi.nr.jtable.params.base.DecimalLinkData
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.EnumLinkData
 *  com.jiuqi.nr.jtable.params.base.FloatLinkData
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.params.output.RegionDataSet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellAddedData
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Document$OutputSettings
 */
package com.jiuqi.nr.analysisreport.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridFieldList;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.grid.CellDataProperty;
import com.jiuqi.np.grid.CellDataPropertyIntf;
import com.jiuqi.np.grid.NumberCellProperty;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.internal.service.impl.AnalysisReportEntityServiceImpl;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.common.params.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.jtable.params.base.DecimalLinkData;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FloatLinkData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellAddedData;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class AnaUtils {
    private static Logger logger = LoggerFactory.getLogger(AnaUtils.class);

    public static DimensionType getDimensionType(EntityViewData entity) {
        IAnalysisReportEntityService analysisReportEntityService = (IAnalysisReportEntityService)BeanUtil.getBean(AnalysisReportEntityServiceImpl.class);
        if (entity.isMasterEntity()) {
            return DimensionType.DIMENSION_UNIT;
        }
        if (analysisReportEntityService.isPeriodEntity(entity.getKey())) {
            return DimensionType.DIMENSION_PERIOD;
        }
        if (entity.getDimensionName().equals("VERSIONID")) {
            return DimensionType.DIMENSION_VERSION;
        }
        return DimensionType.DIMENSION_NOMAL;
    }

    public static String colorLongToARGB(long color) {
        if (color == -16777211L) {
            color = 0xF1F1F1L;
        }
        long r = (color & 0xFF0000L) >> 16;
        long g = (color & 0xFF00L) >> 8;
        long b = color & 0xFFL;
        return "rgb(" + r + "," + g + "," + b + ")";
    }

    public static String colorLongToHex(long color) {
        if (color == -16777211L) {
            color = 0xF1F1F1L;
        }
        long r = (color & 0xFF0000L) >> 16;
        long g = (color & 0xFF00L) >> 8;
        long b = color & 0xFFL;
        return "#" + AnaUtils.getHexColor(r) + AnaUtils.getHexColor(g) + AnaUtils.getHexColor(b);
    }

    public static String getHexColor(Long color) {
        String hexString = Long.toHexString(color);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    public static Map<String, Object> getTreeMap(String key, String title, boolean isLeaf, boolean isParent) {
        HashMap<String, Object> gMap = new HashMap<String, Object>();
        gMap.put("ID", key);
        gMap.put("Selected", false);
        gMap.put("Title", title);
        gMap.put("code", key);
        gMap.put("icon", null);
        gMap.put("isLeaf", isLeaf);
        gMap.put("key", key);
        gMap.put("noDrag", false);
        gMap.put("noDrop", false);
        gMap.put("title", title);
        gMap.put("selected", false);
        gMap.put("expanded", false);
        HashMap gdata = new HashMap();
        gdata.put("code", gMap.get("code"));
        gdata.put("icon", gMap.get("icon"));
        gdata.put("isLeaf", gMap.get("isLeaf"));
        gdata.put("key", gMap.get("key"));
        gdata.put("noDrag", gMap.get("noDrag"));
        gdata.put("noDrop", gMap.get("noDrop"));
        gdata.put("selected", gMap.get("selected"));
        gdata.put("title", gMap.get("title"));
        gdata.put("expanded", gMap.get("expanded"));
        if (isParent) {
            gdata.put("parent", true);
        }
        gMap.put("data", gdata);
        return gMap;
    }

    public static Map<String, Object> getTreeMap(String key, String title, boolean isLeaf, boolean isParent, String taskId) {
        HashMap<String, Object> gMap = new HashMap<String, Object>();
        gMap.put("ID", key);
        gMap.put("Selected", false);
        gMap.put("Title", title);
        gMap.put("code", key);
        gMap.put("icon", null);
        gMap.put("isLeaf", isLeaf);
        gMap.put("key", key);
        gMap.put("noDrag", false);
        gMap.put("noDrop", false);
        gMap.put("title", title);
        gMap.put("selected", false);
        gMap.put("expanded", false);
        gMap.put("taskId", taskId);
        HashMap gdata = new HashMap();
        gdata.put("code", gMap.get("code"));
        gdata.put("icon", gMap.get("icon"));
        gdata.put("isLeaf", gMap.get("isLeaf"));
        gdata.put("key", gMap.get("key"));
        gdata.put("noDrag", gMap.get("noDrag"));
        gdata.put("noDrop", gMap.get("noDrop"));
        gdata.put("selected", gMap.get("selected"));
        gdata.put("title", gMap.get("title"));
        gdata.put("expanded", gMap.get("expanded"));
        gdata.put("taskId", gMap.get("taskId"));
        if (isParent) {
            gdata.put("parent", true);
        }
        gMap.put("data", gdata);
        return gMap;
    }

    public static Map<String, Object> getMapEntity(AnalysisReportGroupDefine define) {
        HashMap<String, Object> gMap = new HashMap<String, Object>();
        gMap.put("ID", define.getKey());
        gMap.put("Selected", false);
        gMap.put("Title", define.getTitle());
        gMap.put("icon", null);
        gMap.put("isLeaf", false);
        gMap.put("key", define.getKey());
        gMap.put("noDrag", false);
        gMap.put("noDrop", false);
        gMap.put("title", define.getTitle());
        gMap.put("selected", false);
        gMap.put("antype", "group");
        HashMap gdata = new HashMap();
        gdata.put("code", gMap.get("code"));
        gdata.put("icon", gMap.get("icon"));
        gdata.put("isLeaf", gMap.get("isLeaf"));
        gdata.put("key", gMap.get("key"));
        gdata.put("noDrag", gMap.get("noDrag"));
        gdata.put("noDrop", gMap.get("noDrop"));
        gdata.put("selected", gMap.get("selected"));
        gdata.put("title", gMap.get("title"));
        gdata.put("antype", "group");
        gMap.put("data", gdata);
        return gMap;
    }

    public static Map<String, Object> getMapEntity(AnalysisReportDefine define) {
        HashMap<String, Object> mMap = new HashMap<String, Object>();
        mMap.put("ID", define.getKey());
        mMap.put("Selected", false);
        mMap.put("Title", define.getTitle());
        mMap.put("icon", null);
        mMap.put("isLeaf", true);
        mMap.put("key", define.getKey());
        mMap.put("noDrag", false);
        mMap.put("noDrop", false);
        mMap.put("title", define.getTitle());
        mMap.put("selected", false);
        mMap.put("antype", "model");
        mMap.put("securityLevel", define.getSecurityLevel());
        HashMap mdata = new HashMap();
        mdata.put("code", mMap.get("code"));
        mdata.put("icon", mMap.get("icon"));
        mdata.put("isLeaf", mMap.get("isLeaf"));
        mdata.put("key", mMap.get("key"));
        mdata.put("noDrag", mMap.get("noDrag"));
        mdata.put("noDrop", mMap.get("noDrop"));
        mdata.put("selected", mMap.get("selected"));
        mdata.put("title", mMap.get("title"));
        mdata.put("parent", true);
        mdata.put("securityLevel", mMap.get("securityLevel"));
        mMap.put("data", mdata);
        return mMap;
    }

    public static Map<String, Object> getMapEntity(AnalysisReportDefine define, String securityTitle) {
        HashMap<String, Object> mMap = new HashMap<String, Object>();
        mMap.put("ID", define.getKey());
        mMap.put("Selected", false);
        mMap.put("Title", define.getTitle());
        mMap.put("icon", null);
        mMap.put("isLeaf", true);
        mMap.put("key", define.getKey());
        mMap.put("noDrag", false);
        mMap.put("noDrop", false);
        mMap.put("title", define.getTitle());
        mMap.put("selected", false);
        mMap.put("antype", "model");
        mMap.put("securityLevel", define.getSecurityLevel());
        mMap.put("securityTitle", securityTitle);
        HashMap mdata = new HashMap();
        mdata.put("code", mMap.get("code"));
        mdata.put("icon", mMap.get("icon"));
        mdata.put("isLeaf", mMap.get("isLeaf"));
        mdata.put("key", mMap.get("key"));
        mdata.put("noDrag", mMap.get("noDrag"));
        mdata.put("noDrop", mMap.get("noDrop"));
        mdata.put("selected", mMap.get("selected"));
        mdata.put("title", mMap.get("title"));
        mdata.put("parent", true);
        mdata.put("securityLevel", mMap.get("securityLevel"));
        mdata.put("securityTitle", mMap.get("securityTitle"));
        mdata.put("antype", "model");
        mMap.put("data", mdata);
        return mMap;
    }

    public static ReturnObject initReturn(boolean b, String message) {
        ReturnObject returnObject = new ReturnObject();
        if (b) {
            returnObject.setSuccess(true);
            returnObject.setMessage(message != null ? message : "success");
            returnObject.setCode("200");
        } else {
            returnObject.setSuccess(false);
            returnObject.setMessage(message != null ? message : "warning");
            returnObject.setCode("400");
        }
        return returnObject;
    }

    public static ReturnObject initReturn(boolean b) {
        ReturnObject returnObject = new ReturnObject();
        if (b) {
            returnObject.setSuccess(true);
            returnObject.setMessage("success");
            returnObject.setCode("200");
        } else {
            returnObject.setSuccess(false);
            returnObject.setMessage("warning");
            returnObject.setCode("400");
        }
        return returnObject;
    }

    public static ReturnObject initReturn(boolean b, String message, Object o) {
        ReturnObject returnObject = new ReturnObject();
        if (b) {
            returnObject.setSuccess(true);
            returnObject.setMessage(message != null ? message : "success");
            returnObject.setObj(o != null ? o : null);
            returnObject.setCode("200");
        } else {
            returnObject.setSuccess(false);
            returnObject.setMessage(message != null ? message : "warning");
            returnObject.setCode("400");
        }
        return returnObject;
    }

    public static ReturnObject initReturn(Object o) {
        ReturnObject returnObject = new ReturnObject();
        returnObject.setObj(o);
        return returnObject;
    }

    public static ReturnObject initReturn(String message) {
        ReturnObject returnObject = new ReturnObject();
        returnObject.setMessage(message);
        return returnObject;
    }

    public static boolean nameIsEmpty(JsonNode jsonNode, String key, boolean b) {
        if (null == jsonNode) {
            return false;
        }
        if (null == jsonNode.get(key)) {
            return false;
        }
        return !b || !"".equals(jsonNode.get(key).textValue());
    }

    public static Object booleanToString(Object o, Object text) {
        if (o instanceof Boolean) {
            boolean bolg = (Boolean)o;
            if (bolg) {
                return "\u662f";
            }
            return "\u5426";
        }
        return text;
    }

    public static Object numberToString(Object o, Object text) {
        if (o instanceof Double || o instanceof Integer || o instanceof BigDecimal || o instanceof Float || o instanceof Long || o instanceof Short) {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            return nf.format(o);
        }
        return text;
    }

    public static Object numberToString(Object o, Object text, String taskId) {
        if (o instanceof Double || o instanceof Integer || o instanceof BigDecimal || o instanceof Float || o instanceof Long || o instanceof Short) {
            ITaskOptionController taskOptionController = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
            String numberZeroShow = taskOptionController.getValue(taskId, "NUMBER_ZERO_SHOW");
            if (numberZeroShow == null) {
                numberZeroShow = "";
            }
            try {
                if (o instanceof Double ? Math.abs((Double)o) < 1.0E-10 && !"0".equals(numberZeroShow) : (o instanceof Integer ? (double)Math.abs((Integer)o) < 1.0E-10 && !"0".equals(numberZeroShow) : (o instanceof Float ? (double)Math.abs(((Float)o).floatValue()) < 1.0E-10 && !"0".equals(numberZeroShow) : (o instanceof Long || o instanceof Short) && (double)Math.abs((Long)o) < 1.0E-10 && !"0".equals(numberZeroShow)))) {
                    return numberZeroShow;
                }
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                return nf.format(o);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return text;
    }

    public static Object entityReturnInfo(Object o, RegionDataSet regionDataSet, Object text, LinkData dataLink) {
        if (dataLink instanceof EnumLinkData) {
            EnumLinkData enumLink = (EnumLinkData)dataLink;
            EntityReturnInfo entityReturnInfo = (EntityReturnInfo)regionDataSet.getEntityDataMap().get(enumLink.getEntityKey());
            if (entityReturnInfo != null) {
                List entityData = entityReturnInfo.getEntitys();
                for (EntityData entityDatum : entityData) {
                    if (!entityDatum.getId().equals(o)) continue;
                    return entityDatum.getRowCaption();
                }
                return text;
            }
        }
        return text;
    }

    public static String ResultFormat(AbstractData result) throws DataTypeException {
        int plcae;
        String resultData = result.getAsString();
        switch (result.dataType) {
            case 4: {
                resultData = result.getAsInt() + "";
                break;
            }
            case 3: {
                resultData = result.getAsFloat() + "";
                break;
            }
            case 10: {
                break;
            }
            default: {
                return resultData;
            }
        }
        String symbol = "";
        String decimal = "";
        String bigData = resultData;
        String res = new BigDecimal(resultData).toString();
        if (res.indexOf("-") == 0) {
            symbol = "-";
            res = res.replace("-", "");
        }
        if ((plcae = res.indexOf(".")) > 0) {
            decimal = res.substring(res.indexOf("."), res.length());
            bigData = res.substring(0, res.indexOf("."));
        } else {
            bigData = res;
        }
        BigDecimal b = new BigDecimal(bigData);
        DecimalFormat df = new DecimalFormat("###,##0");
        String format = df.format(b);
        return symbol + format + (!"".equals(decimal) ? decimal : "");
    }

    public static String resultFormat(Object result, Boolean isScientific) {
        int place;
        String resultData = "";
        if (result instanceof Double) {
            logger.info("\u4fee\u6539\u524d\u672a\u5904\u7406" + result + result.getClass());
        }
        if (result instanceof Double) {
            logger.info("\u4fee\u6539\u524d\u5df2\u5904\u7406" + Convert.toString((Object)result));
        }
        if (!isScientific.booleanValue() && (result instanceof Double || result instanceof Float)) {
            BigDecimal db = new BigDecimal(result.toString());
            return db.toPlainString();
        }
        if (result instanceof Integer || result instanceof Float) {
            resultData = result + "";
        } else if (result instanceof BigDecimal) {
            resultData = result.toString();
        } else {
            return Convert.toString((Object)result);
        }
        String symbol = "";
        String decimal = "";
        String bigData = resultData;
        String res = new BigDecimal(resultData).toString();
        if (res.indexOf("-") == 0) {
            symbol = "-";
            res = res.replace("-", "");
        }
        if ((place = res.indexOf(".")) > 0) {
            decimal = res.substring(res.indexOf("."), res.length());
            bigData = res.substring(0, res.indexOf("."));
        } else {
            bigData = res;
        }
        BigDecimal b = new BigDecimal(bigData);
        DecimalFormat df = new DecimalFormat("###,##0");
        String format = df.format(b);
        return symbol + format + (!"".equals(decimal) ? decimal : "");
    }

    public static void dataTodata2(GridData n, Grid2Data o) {
        o.setColumnCount(n.getColCount());
        o.setRowCount(n.getRowCount());
        o.setHeaderColumnCount(n.getScrollTopCol());
        o.setHeaderRowCount(n.getScrollTopRow());
        o.setFooterColumnCount(n.getScrollBottomCol());
        o.setFooterRowCount(n.getScrollBottomRow());
        for (int i = 0; i < n.getRowCount(); ++i) {
            o.setRowHeight(i, n.getRowHeights(i));
            o.setRowHidden(i, !n.getRowVisible(i));
            o.setRowAutoHeight(i, n.getRowAutoSize(i));
            for (int j = 0; j < n.getColCount(); ++j) {
                if (i == 0) {
                    o.setColumnWidth(j, n.getColWidths(j));
                    o.setColumnHidden(j, !n.getColVisible(j));
                    o.setColumnAutoWidth(j, n.getColAutoSize(j));
                }
                AnaUtils.copyCellData(n.getCell(j, i), o.getGridCellData(j, i), 1);
            }
        }
        GridFieldList gfl = n.merges();
        CellField cf = null;
        if (gfl != null) {
            for (int i = 0; i < gfl.count(); ++i) {
                cf = gfl.get(i);
                o.mergeCells(cf.left, cf.top, cf.right, cf.bottom);
            }
        }
    }

    private static void copyCellData(GridCell c, GridCellData d, int direction) {
        if (direction == 0) {
            String formatter;
            if (d.getBackGroundColor() != -1) {
                c.setBackColor(d.getBackGroundColor());
            }
            c.setBackStyle(d.getBackGroundStyle());
            if (d.getForeGroundColor() != -1) {
                c.setFontColor(d.getForeGroundColor());
            }
            c.setFontSize(d.getFontSize());
            c.setFontName(d.getFontName());
            c.setFontBold((d.getFontStyle() & 2) != 0);
            c.setFontItalic((d.getFontStyle() & 4) != 0);
            c.setFontStrikeOut((d.getFontStyle() & 0x10) != 0);
            c.setFontUnderLine((d.getFontStyle() & 8) != 0);
            c.setSilverHead(d.isSilverHead());
            if (d.getRightBorderColor() != -1) {
                c.setREdgeColor(d.getRightBorderColor());
            }
            if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 1);
            } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle() || d.getRightBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 2);
            } else if (d.getRightBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setREdgeStyle(d.getRightBorderStyle() + 3);
            }
            if (d.getBottomBorderColor() != -1) {
                c.setBEdgeColor(d.getBottomBorderColor());
            }
            if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.AUTO.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.NONE.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.SOLID.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 1);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DASH.getStyle() || d.getBottomBorderStyle() == GridEnums.GridBorderStyle.DOUBLE.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 2);
            } else if (d.getBottomBorderStyle() == GridEnums.GridBorderStyle.BOLD.getStyle()) {
                c.setBEdgeStyle(d.getBottomBorderStyle() + 3);
            }
            c.setCanSelect(d.isSelectable());
            c.setCanModify(d.isEditable());
            c.setWrapLine(d.isWrapLine());
            c.setIndent(d.getIndent());
            c.setVertAlign(d.getVertAlign());
            c.setHorzAlign(d.getHorzAlign());
            c.setVertText(d.isVertText());
            c.setShowText(d.getShowText());
            c.setCssClass(d.getEditText());
            c.setDataType(d.getDataType());
            if (d.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.Number) && !StringUtils.isEmpty((String)(formatter = d.getFormatter()))) {
                String[] formatters = formatter.split("\\.");
                if (formatters.length == 2) {
                    int length = formatters[1].length();
                    CellDataProperty cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                    NumberCellProperty numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                    numberCellProperty.setDecimal(length);
                    c.setDataFlag((int)numberCellProperty.getCellDataProperty().getDataFlag());
                    if (formatters[0].contains(",")) {
                        numberCellProperty.setThoundsMarks(true);
                    }
                    c.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                } else if (formatters[0].contains(",")) {
                    CellDataProperty cdp = new CellDataProperty(c.getDataType(), c.getDataFlag(), c.getEditMode(), c.getDataFormat(), c.getDataProperty());
                    NumberCellProperty numberCellProperty = new NumberCellProperty((CellDataPropertyIntf)cdp);
                    numberCellProperty.setThoundsMarks(true);
                    c.setDataProperty((int)numberCellProperty.getCellDataProperty().getDataProperty());
                }
            }
            c.setMultiLine(d.isMultiLine());
            c.setFitFontSize(d.isFitFontSize());
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException numberCellProperty) {
                    // empty catch block
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            try {
                obj = new JSONObject(d.getDataExString());
                data.setDataEx(obj);
            }
            catch (JSONException numberCellProperty) {
                // empty catch block
            }
            c.setScript(data.toString());
        } else if (direction == 1) {
            if (c.getBackAlpha() == 0) {
                d.setBackGroundColor(-1);
            } else {
                d.setBackGroundColor(c.getBackColor());
            }
            d.setBackGroundStyle(c.getBackStyle());
            d.setForeGroundColor(c.getFontColor());
            d.setFontSize(c.getFontSize());
            d.setFontName(c.getFontName());
            int style = 0;
            if (c.getFontBold()) {
                style |= 2;
            }
            if (c.getFontItalic()) {
                style |= 4;
            }
            if (c.getFontStrikeOut()) {
                style |= 0x10;
            }
            if (c.getFontUnderLine()) {
                style |= 8;
            }
            d.setFontStyle(style);
            d.setRightBorderColor(c.getREdgeColor());
            if (c.getREdgeStyle() == 0 || c.getREdgeStyle() == 1 || c.getREdgeStyle() == 2) {
                d.setRightBorderStyle(c.getREdgeStyle() - 1);
            } else if (c.getREdgeStyle() == 4 || c.getREdgeStyle() == 10) {
                d.setRightBorderStyle(c.getREdgeStyle() - 2);
            } else if (c.getREdgeStyle() == 7) {
                d.setRightBorderStyle(c.getREdgeStyle() - 3);
            }
            d.setBottomBorderColor(c.getBEdgeColor());
            if (c.getBEdgeStyle() == 0 || c.getBEdgeStyle() == 1 || c.getBEdgeStyle() == 2) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 1);
            } else if (c.getBEdgeStyle() == 4 || c.getBEdgeStyle() == 10) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 2);
            } else if (c.getBEdgeStyle() == 7) {
                d.setBottomBorderStyle(c.getBEdgeStyle() - 3);
            }
            d.setSelectable(c.getCanSelect());
            d.setEditable(c.getCanModify());
            d.setWrapLine(c.getWrapLine());
            d.setIndent(c.getIndent());
            d.setVertAlign(c.getVertAlign());
            d.setHorzAlign(c.getHorzAlign());
            d.setVertText(c.getVertText());
            d.setShowText(c.getShowText());
            d.setEditText(c.getCssClass());
            d.setDataType(c.getDataType());
            d.setSilverHead(c.getSilverHead());
            d.setMultiLine(c.getMultiLine());
            d.setFitFontSize(c.getFitFontSize());
            GridCellAddedData data = null;
            String addDataStr = c.getScript();
            JSONObject obj = null;
            if (addDataStr != null) {
                try {
                    data = new GridCellAddedData(addDataStr);
                }
                catch (JSONException jSONException) {
                    // empty catch block
                }
            }
            if (data == null) {
                data = new GridCellAddedData();
            }
            if ((obj = data.getDataEx()) != null) {
                try {
                    d.setDataExFromString(obj.toString());
                }
                catch (JSONException jSONException) {
                    // empty catch block
                }
            }
        }
    }

    public static String ExceptionResolution(Exception e) {
        StringBuffer print = new StringBuffer();
        try {
            print.append(e.getClass().toString().replaceAll("class (.*)", "$1")).append("\r\n");
            for (StackTraceElement ste : e.getStackTrace()) {
                print.append("\tat ").append(ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")").append("\r\n");
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return print.toString();
    }

    public static String convertFileToBase64(String imgPath) {
        byte[] data = null;
        try (FileInputStream in = new FileInputStream(imgPath);){
            logger.info("\u6587\u4ef6\u5927\u5c0f\uff08\u5b57\u8282\uff09=" + ((InputStream)in).available());
            data = new byte[((InputStream)in).available()];
            ((InputStream)in).read(data);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        Base64.Encoder encoder = Base64.getEncoder();
        String base64Str = encoder.encodeToString(data);
        return base64Str;
    }

    public static final boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim())) {
            return s.matches("^[0-9]*$");
        }
        return false;
    }

    public static List<String> findRex(String tag, String rex) {
        ArrayList<String> res = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rex);
        Matcher m = pattern.matcher(tag);
        while (m.find()) {
            res.add(m.group());
        }
        return res;
    }

    public static Object covertToPercentIfRequired(Object text, LinkData linkData) {
        if (text == null || text == "") {
            return text;
        }
        boolean needCovert = false;
        int fraction = 2;
        int displayDigits = 0;
        if (linkData instanceof DecimalLinkData) {
            fraction = ((DecimalLinkData)linkData).getFraction();
            needCovert = ((DecimalLinkData)linkData).isPercent();
            displayDigits = AnaUtils.calcDisDigits(linkData.getStyle());
        }
        if (linkData instanceof FloatLinkData) {
            fraction = ((FloatLinkData)linkData).getFraction();
            needCovert = ((FloatLinkData)linkData).isPercent();
            displayDigits = AnaUtils.calcDisDigits(linkData.getStyle());
        }
        if (needCovert) {
            text = AnaUtils.doCovertToPercent(text.toString(), fraction, displayDigits);
        }
        return text;
    }

    private static int calcDisDigits(String style) {
        int displayDigits = 0;
        if (StringUtils.isNotEmpty((String)style) && style.length() > 16) {
            displayDigits = Integer.parseInt(style.substring(15, 17));
        }
        return displayDigits;
    }

    public static String doCovertToPercent(String text, int fraction, int displayDigits) {
        String percentStr = null;
        BigDecimal textDecimal = new BigDecimal(text);
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(fraction);
        percentStr = numberFormat.format(textDecimal);
        String[] vals = percentStr.split("%");
        String percentVal = vals[0];
        BigDecimal perDecimal = new BigDecimal(percentVal);
        DecimalFormat decimalFormat = AnaUtils.getDecimalFormatByScale(displayDigits);
        percentStr = decimalFormat.format(perDecimal) + "%";
        return percentStr;
    }

    public static DecimalFormat getDecimalFormatByScale(int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
        }
        if (scale == 0) {
            return new DecimalFormat("0");
        }
        String formatStr = "0.";
        for (int i = 0; i < scale; ++i) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr);
    }

    public static Object doformat(Object text, LinkData linkData) {
        if (text == null || text == "") {
            return text;
        }
        if (linkData instanceof DecimalLinkData || linkData instanceof FloatLinkData) {
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            DataLinkDefine dataLinkDefine = iRunTimeViewController.queryDataLinkDefine(linkData.getKey());
            FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
            if (formatProperties == null) {
                return text;
            }
            String pattern = formatProperties.getPattern();
            DecimalFormat df = new DecimalFormat(pattern);
            String str = StringUtils.join((Object[])text.toString().split(","), (String)"");
            Double dText = new Double(str);
            return df.format(dText);
        }
        return text;
    }

    public static Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return date;
    }

    public static void setFormulaVariable(VariableManager variableManager, Map<String, Object> map) {
        for (Map.Entry<String, Object> keyValue : map.entrySet()) {
            Object value = keyValue.getValue();
            String key = keyValue.getKey();
            if (value == null) continue;
            int dataType = AnaUtils.getDataType(value);
            variableManager.add(new Variable(key, key, dataType, value));
        }
    }

    public static int getDataType(Object value) {
        if (value instanceof Float) {
            return 3;
        }
        if (value instanceof String) {
            return 6;
        }
        if (value instanceof Integer) {
            return 4;
        }
        if (value instanceof Boolean) {
            return 1;
        }
        if (value instanceof Date) {
            return 5;
        }
        if (value instanceof Time) {
            return 2;
        }
        if (value instanceof Timestamp) {
            return 2;
        }
        if (value instanceof BigDecimal) {
            return 10;
        }
        if (value instanceof Arrays) {
            return 11;
        }
        if (value instanceof List) {
            return 65;
        }
        return 0;
    }

    public static String buildDimStr(List<ReportBaseVO.UnitDim> chooseunits) {
        boolean isFirst = true;
        StringBuilder entitykeyfinal = new StringBuilder();
        for (ReportBaseVO.UnitDim chooseUnit : chooseunits) {
            if (!isFirst) {
                entitykeyfinal.append(";");
            }
            entitykeyfinal.append(chooseUnit.getCode());
            isFirst = false;
        }
        return entitykeyfinal.toString();
    }

    public static Document parseBodyFragment(String content) {
        return Jsoup.parseBodyFragment((String)content).outputSettings(new Document.OutputSettings().prettyPrint(false));
    }

    public static String getVarMaxThreadOptionItem(String varName) {
        return "MAX_THREAD_NUM" + varName;
    }

    public static ReportBaseVO.UnitDim getMainDim(List<ReportBaseVO.UnitDim> unitDims) {
        for (int i = 0; i < unitDims.size(); ++i) {
            ReportBaseVO.UnitDim unitDim = unitDims.get(i);
            if (!unitDim.getChooseAll() && CollectionUtils.isEmpty(unitDim.getCodes())) continue;
            if (i != 0) {
                Collections.swap(unitDims, 0, i);
            }
            return unitDim;
        }
        return null;
    }
}

