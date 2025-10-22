/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.JsonObject
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  io.netty.util.internal.StringUtil
 *  io.swagger.annotations.Api
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.customentry.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.customentry.define.CustomEntryContext;
import com.jiuqi.nr.customentry.define.DataFormatInfo;
import com.jiuqi.nr.customentry.service.CustomEntryHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.query.defines.QueryEntityDataObject;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nvwa.definition.service.DataModelService;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/customentry"})
@Api(tags={"\u81ea\u5b9a\u4e49\u5f55\u5165"})
public class CustomeEntryServices {
    private static final Logger logger = LoggerFactory.getLogger(CustomeEntryServices.class);
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataModelService modelService;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    @RequestMapping(value={"/custom-enumdata"}, method={RequestMethod.GET})
    public List<ITree<QueryEntityDataObject>> getEnumData(String linkkey, String nodekey, String fieldkey) {
        try {
            QueryHelper helper = new QueryHelper();
            DataLinkDefine linkDefine = helper.queryLinkDefineByKey(linkkey);
            ArrayList<ITree<QueryEntityDataObject>> children = new ArrayList<ITree<QueryEntityDataObject>>();
            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldkey);
            String entityKey = fieldDefine.getEntityKey();
            if (entityKey != null) {
                IEntityDefine queryEntity = this.metaService.queryEntity(fieldDefine.getEntityKey());
                EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(queryEntity.getId());
                IEntityTable entityTable = QueryHelper.getEntityTableOnce(entityViewDefine, null);
                List childRows = null;
                childRows = StringUtil.isNullOrEmpty((String)nodekey) ? entityTable.getRootRows() : entityTable.getChildRows(nodekey);
                for (IEntityRow row : childRows) {
                    QueryEntityDataObject entObj = QueryEntityDataObject.buildEntityData(row);
                    entObj.setTitle(QueryHelper.getCaption(row, linkDefine, true));
                    entObj.setCaption(QueryHelper.getCaption(row, linkDefine, false));
                    ITree treeNode = new ITree((INode)entObj);
                    treeNode.setIcons(null);
                    int directChildCount = entityTable.getDirectChildCount(row.getEntityKeyData());
                    ((QueryEntityDataObject)treeNode.getData()).setChildCount(directChildCount);
                    ((QueryEntityDataObject)treeNode.getData()).setFieldkey(fieldkey);
                    treeNode.setLeaf(directChildCount == 0);
                    children.add((ITree<QueryEntityDataObject>)treeNode);
                }
            }
            return children;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.error((String)"CustomEntry", (String)"\u679a\u4e3e\u6570\u636e\u52a0\u8f7d\u5f02\u5e38", (String)ex.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/custom-save"}, method={RequestMethod.POST})
    public String SaveData(@RequestBody CustomEntryContext context) {
        try {
            CustomEntryHelper helper = (CustomEntryHelper)BeanUtil.getBean(CustomEntryHelper.class);
            String res = helper.saveData(context);
            return res;
        }
        catch (Exception ex) {
            LogHelper.error((String)"CustomEntry", (String)"\u6570\u636e\u4fdd\u5b58\u5931\u8d25", (String)("\u5931\u8d25\u539f\u56e0\uff1a" + ex.getMessage()));
            return "false";
        }
    }

    private static String getDigits(int size) {
        String digitStr = "";
        for (int i = 0; i < size; ++i) {
            digitStr = digitStr + "0";
        }
        if (!StringUtil.isNullOrEmpty((String)digitStr)) {
            return "." + digitStr;
        }
        return digitStr;
    }

    @RequestMapping(value={"/custom-format"}, method={RequestMethod.GET})
    public String format(String value, Integer type, Integer digits) {
        JsonObject val = new JsonObject();
        value = HtmlUtils.cleanUrlXSS((String)value);
        try {
            if (value == "\u2014\u2014" || StringUtil.isNullOrEmpty((String)value)) {
                val.addProperty("value", value);
                return HtmlUtils.cleanUrlXSS((String)val.getAsString());
            }
            String formatter = "#,##0" + CustomeEntryServices.getDigits(digits);
            DecimalFormat decimalFormat = new DecimalFormat(formatter);
            switch (FieldType.forValue((int)type)) {
                case FIELD_TYPE_DECIMAL: {
                    BigDecimal decimalValue = Convert.toBigDecimal((Object)value);
                    BigDecimal deciValue = decimalValue.setScale((int)digits, 4);
                    val.addProperty("value", decimalFormat.format(deciValue));
                    return HtmlUtils.cleanUrlXSS((String)val.getAsString());
                }
                case FIELD_TYPE_FLOAT: {
                    double floatValue = Convert.toDouble((String)value);
                    Double dbVal = Round.callFunction((Number)floatValue, (int)digits);
                    val.addProperty("value", decimalFormat.format(dbVal));
                    return HtmlUtils.cleanUrlXSS((String)val.getAsString());
                }
                case FIELD_TYPE_INTEGER: {
                    Integer iVal = Convert.toInt((String)value);
                    val.addProperty("value", decimalFormat.format(iVal));
                    return HtmlUtils.cleanUrlXSS((String)val.getAsString());
                }
                case FIELD_TYPE_LOGIC: {
                    if (StringUtil.isNullOrEmpty((String)value) && value == "0") {
                        val.addProperty("value", "\u5426");
                        return HtmlUtils.cleanUrlXSS((String)val.getAsString());
                    }
                    if (StringUtil.isNullOrEmpty((String)value) && value == "1") {
                        val.addProperty("value", "\u662f");
                        return HtmlUtils.cleanUrlXSS((String)val.getAsString());
                    }
                    return HtmlUtils.cleanUrlXSS((String)val.getAsString());
                }
            }
            val.addProperty("value", value);
            return HtmlUtils.cleanUrlXSS((String)val.getAsString());
        }
        catch (Exception ex) {
            LogHelper.error((String)"CustomEntry", (String)"\u81ea\u5b9a\u4e49\u5f55\u5165\u7c7b\u578b\u8f6c\u6362\u9519\u8bef", (String)ex.getMessage());
            val.addProperty("value", value);
            return HtmlUtils.cleanUrlXSS((String)val.getAsString());
        }
    }

    @RequestMapping(value={"/custom-floatOrder"}, method={RequestMethod.GET})
    public String getFloatOrder(String regionKey, String fieldKey, String masterKey) {
        try {
            CustomEntryHelper helper = (CustomEntryHelper)BeanUtil.getBean(CustomEntryHelper.class);
            String floatOrder = helper.generateFloatOrder(regionKey, fieldKey, masterKey);
            return floatOrder;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @RequestMapping(value={"/custom-enum-format"}, method={RequestMethod.POST})
    public String getformatEnum(@RequestBody Map<String, Object> linkDataMap) {
        try {
            JSONObject res = new JSONObject();
            QueryHelper queryHelper = new QueryHelper();
            CustomEntryHelper helper = (CustomEntryHelper)BeanUtil.getBean(CustomEntryHelper.class);
            if (!CollectionUtils.isEmpty(linkDataMap)) {
                for (Map.Entry<String, Object> entry : linkDataMap.entrySet()) {
                    ArrayList datas;
                    Object fieldDefine = null;
                    String linkKey = entry.getKey();
                    JSONObject resInfo = new JSONObject();
                    String viewKey = null;
                    DataLinkDefine linkDefine = queryHelper.queryLinkDefineByKey(linkKey);
                    IEntityTable entityTable = null;
                    if (linkDefine != null) {
                        entityTable = QueryHelper.getEntityTable(viewKey);
                    }
                    ArrayDeque<String> notMatchedRowStack = new ArrayDeque<String>();
                    HashMap<Integer, String> notMatchedRow = new HashMap<Integer, String>();
                    if (entry.getValue() instanceof List && !CollectionUtils.isEmpty(datas = (ArrayList)entry.getValue())) {
                        for (int i = 0; i < datas.size(); ++i) {
                            String entityKey;
                            String order = String.valueOf(i);
                            String data = (String)datas.get(i);
                            String[] entityKeyList = data.split("\\|");
                            IEntityRow entityRow = null;
                            String[] stringArray = entityKeyList;
                            int n = stringArray.length;
                            for (int j = 0; j < n && (entityRow = helper.getEntityRow(entityKey = stringArray[j], entityTable)) == null; ++j) {
                            }
                            DataFormatInfo dataFormatInfo = null;
                            if (entityRow != null) {
                                dataFormatInfo = new DataFormatInfo(entityRow.getTitle(), entityRow.getCode(), entityRow.getTitle());
                            } else {
                                notMatchedRowStack.push(data + "," + order);
                                notMatchedRow.put(i, data);
                            }
                            ObjectMapper objectMapper = new ObjectMapper();
                            String obj = objectMapper.writeValueAsString((Object)dataFormatInfo);
                            resInfo.put(order, (Object)obj);
                        }
                    }
                    if (!notMatchedRow.isEmpty()) {
                        List allRows = entityTable.getAllRows();
                        for (IEntityRow row : allRows) {
                            for (Map.Entry e : notMatchedRow.entrySet()) {
                                DataFormatInfo dataFormatInfo = null;
                                Integer order = (Integer)e.getKey();
                                String value = (String)e.getValue();
                                if (!row.getTitle().equals(value)) continue;
                                dataFormatInfo = new DataFormatInfo(row.getTitle(), row.getCode(), row.getTitle());
                                ObjectMapper objectMapper = new ObjectMapper();
                                String obj = objectMapper.writeValueAsString((Object)dataFormatInfo);
                                resInfo.put(String.valueOf(order), (Object)obj);
                                notMatchedRowStack.pop();
                            }
                            if (!notMatchedRowStack.isEmpty()) continue;
                            break;
                        }
                    }
                    res.put(linkKey, (Object)resInfo);
                }
            }
            return res.toString();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @RequestMapping(value={"/custom-AllowDuplicate"}, method={RequestMethod.GET})
    public String getAllowDuplicateKey(String regionKey) {
        try {
            IRunTimeViewController viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            DataRegionDefine region = viewController.queryDataRegionDefine(regionKey);
            boolean allowDuplicateKey = region.getAllowDuplicateKey();
            return String.valueOf(allowDuplicateKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/custom-rowDuplicate"}, method={RequestMethod.POST})
    public String getAllowDuplicateKey(String regionKey, @RequestBody List<String> masterKeys) {
        try {
            regionKey = HtmlUtils.cleanUrlXSS((String)regionKey);
            QueryHelper queryHelper = new QueryHelper();
            JSONObject returnValue = new JSONObject();
            if (!CollectionUtils.isEmpty(masterKeys)) {
                for (String masterKey : masterKeys) {
                    masterKey = HtmlUtils.cleanUrlXSS((String)masterKey);
                    DimensionValueSet masterKeySet = new DimensionValueSet();
                    masterKeySet.parseString(masterKey);
                    Boolean res = queryHelper.rowDuQuery(regionKey, masterKeySet);
                    returnValue.put(masterKey, (Object)res);
                }
            }
            return HtmlUtils.cleanUrlXSS((String)returnValue.toString());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/custom-getBizKeyOrders"}, method={RequestMethod.GET})
    public String getBizKeyOrders(String rowSize) {
        try {
            int size = Integer.parseInt(rowSize);
            JSONObject returnValue = new JSONObject();
            for (int i = 0; i < size; ++i) {
                UUID uuid = UUID.randomUUID();
                String key = "RECORDKEY=" + uuid.toString();
                returnValue.put(String.valueOf(i), (Object)key);
            }
            return returnValue.toString();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

