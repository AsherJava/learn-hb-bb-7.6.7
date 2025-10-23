/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv
 *  com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor
 *  com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.hyperlink.HyperlinkEnv;
import com.jiuqi.bi.quickreport.engine.context.filter.ValueFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.ValuesFilterDescriptor;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.zbquery.common.ZBQueryErrorEnum;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionType;
import com.jiuqi.nr.zbquery.model.DefaultValueMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import com.jiuqi.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class HyperLinkDataCover {
    public static void coverDefaultValues(ZBQueryModel zbQueryModel, JSONObject linkMessage) throws JQException {
        QueryModelFinder queryModelFinder = new QueryModelFinder(zbQueryModel);
        List<ConditionField> conditions = zbQueryModel.getConditions();
        for (ConditionField conditionField : conditions) {
            QueryObject queryObject = queryModelFinder.getQueryObject(conditionField.getFullName());
            String alasName = StringUtils.isNotEmpty((String)queryObject.getMessageAlias()) ? queryObject.getMessageAlias() : queryObject.getName();
            if (!linkMessage.has(alasName)) continue;
            HyperLinkDataCover.setAdjustToConditionField(queryModelFinder, conditionField, linkMessage);
            String[] linkData = null;
            try {
                Object data = linkMessage.get(alasName);
                if (data instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray)data;
                    linkData = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        linkData[i] = jsonArray.get(i).toString();
                    }
                } else if (data instanceof String && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)((String)data).trim())) {
                    linkData = new String[]{(String)data};
                }
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_001);
            }
            if (linkData == null || linkData.length <= 0) continue;
            if (conditionField.getConditionType() == ConditionType.SINGLE) {
                conditionField.setDefaultValueMode(DefaultValueMode.APPOINT);
                conditionField.setDefaultValues(new String[]{linkData[0]});
                continue;
            }
            if (conditionField.getConditionType() == ConditionType.RANGE) {
                conditionField.setDefaultValueMode(DefaultValueMode.APPOINT);
                conditionField.setDefaultValues(new String[]{linkData[0]});
                conditionField.setDefaultMaxValueMode(DefaultValueMode.APPOINT);
                if (linkData.length == 1) {
                    conditionField.setDefaultMaxValue(linkData[0]);
                    continue;
                }
                conditionField.setDefaultMaxValue(linkData[1]);
                continue;
            }
            conditionField.setDefaultValueMode(DefaultValueMode.APPOINT);
            conditionField.setDefaultValues(linkData);
        }
    }

    private static void setAdjustToConditionField(QueryModelFinder queryModelFinder, ConditionField conditionField, JSONObject linkMessage) {
        QueryDimension dimension = queryModelFinder.getQueryDimension(conditionField.getFullName());
        if (dimension.isEnableAdjust() && dimension.getDimensionType() == QueryDimensionType.PERIOD) {
            String adjustAlasName;
            QueryObject adjustObject = queryModelFinder.getQueryObject("ADJUST");
            String string = adjustAlasName = StringUtils.isNotEmpty((String)adjustObject.getMessageAlias()) ? adjustObject.getMessageAlias() : adjustObject.getName();
            if (linkMessage.has(adjustAlasName)) {
                Object adjustValue = linkMessage.get(adjustAlasName);
                if (adjustValue instanceof String && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)((String)adjustValue).trim())) {
                    conditionField.setDefaultBinding((String)adjustValue);
                }
                if (adjustValue instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray)adjustValue;
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        conditionField.setDefaultBinding(jsonArray.get(i).toString());
                    }
                }
            }
        }
    }

    public static JSONObject hyperlinkCellMsg2Json(GridData gridData) {
        JSONArray dataJsons = new JSONArray();
        for (int row = 1; row < gridData.getRowCount(); ++row) {
            JSONArray rowJson = new JSONArray();
            for (int i = 1; i < gridData.getColCount(); ++i) {
                GridCell gridCell = gridData.getCell(i, row);
                JSONObject serialNumberHeaderJson = HyperLinkDataCover.gridCellToJson(gridCell);
                rowJson.put((Object)serialNumberHeaderJson);
            }
            dataJsons.put((Object)rowJson);
        }
        JSONObject result = new JSONObject();
        result.put("ql", (Object)dataJsons);
        return result;
    }

    private static JSONObject gridCellToJson(GridCell gridCell) {
        String linkStr;
        String[] linkInfos;
        JSONObject cellDataJson = new JSONObject();
        if (gridCell.isHyperlink() && (linkInfos = gridCell.getLinkInformation()) != null && (linkInfos.length == 3 || linkInfos.length == 4) && (linkStr = linkInfos[1]) != null && linkStr.length() != 0) {
            String qrParamStr;
            String[] params;
            if (linkStr.startsWith("ql")) {
                String qlFunction = linkStr.replace("ql(", "").replace(")", "");
                String[] pos = qlFunction.split(",");
                if (pos.length == 2) {
                    cellDataJson.put("col", (Object)pos[0]);
                    cellDataJson.put("restriction", (Object)pos[1]);
                }
            } else if (linkStr.startsWith("qr") && (params = (qrParamStr = linkStr.substring(linkStr.indexOf("?") + 1)).split("&")).length == 2) {
                String colValue = params[0].split("=")[1];
                String restrictionValue = params[1].split("=")[1];
                cellDataJson.put("col", (Object)colValue);
                cellDataJson.put("restriction", (Object)restrictionValue);
            }
        }
        return cellDataJson;
    }

    public static JSONObject hyperlinkEnv2Json(HyperlinkEnv hyperlinkEnv) {
        JSONArray json_paramValues = new JSONArray();
        hyperlinkEnv.getParamValues().forEach((key, value) -> {
            JSONObject json = new JSONObject();
            json.put(key, value);
            json_paramValues.put((Object)json);
        });
        JSONArray json_restrictions = new JSONArray();
        hyperlinkEnv.getRestrictions().forEach(filters -> {
            JSONArray json_filters = new JSONArray();
            filters.forEach(filter -> {
                if (!(filter instanceof ValueFilterDescriptor) && !(filter instanceof ValuesFilterDescriptor)) {
                    return;
                }
                Object value = filter instanceof ValueFilterDescriptor ? ((ValueFilterDescriptor)filter).getValue() : ((ValuesFilterDescriptor)filter).getValues();
                DSField field = filter.getField();
                String msgName = com.jiuqi.bi.util.StringUtils.isEmpty((String)field.getMessageAlias()) ? field.getName() : field.getMessageAlias();
                String key = com.jiuqi.bi.util.StringUtils.upperCase((String)msgName);
                JSONObject json = new JSONObject();
                json.put(key, (Object)HyperLinkDataCover.toMessage(value));
                json_filters.put((Object)json);
            });
            json_restrictions.put((Object)json_filters);
        });
        JSONArray json_linkInfos = new JSONArray();
        hyperlinkEnv.getLinkInfos().forEach(linkInfo -> json_linkInfos.put((Object)linkInfo.toJSON()));
        JSONObject json_hyperlinkEnv = new JSONObject();
        json_hyperlinkEnv.put("paramValues", (Object)json_paramValues);
        json_hyperlinkEnv.put("restrictions", (Object)json_restrictions);
        json_hyperlinkEnv.put("linkInfos", (Object)json_linkInfos);
        return json_hyperlinkEnv;
    }

    private static String toMessage(Object value) {
        if (value instanceof List) {
            return HyperLinkDataCover.formatList((List)value);
        }
        return HyperLinkDataCover.formatValue(value);
    }

    private static String formatList(List values) {
        StringBuilder buffer = new StringBuilder();
        boolean started = false;
        for (Object value : values) {
            String valStr = HyperLinkDataCover.formatValue(value);
            if (started) {
                buffer.append('|');
            } else {
                started = true;
            }
            buffer.append(com.jiuqi.bi.util.StringUtils.isEmpty((String)valStr) ? " " : valStr);
        }
        return buffer.toString();
    }

    private static String formatValue(Object value) {
        int type = DataType.typeOf((Object)value);
        if (type == 2) {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            return format.format(((Calendar)value).getTime());
        }
        return DataType.formatValue((int)0, (Object)value);
    }
}

