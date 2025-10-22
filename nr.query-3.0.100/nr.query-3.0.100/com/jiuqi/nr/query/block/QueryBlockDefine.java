/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.text.StringEscapeUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectCache;
import com.jiuqi.nr.query.block.BlockInfo;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryFieldPosition;
import com.jiuqi.nr.query.block.QueryGridPage;
import com.jiuqi.nr.query.chart.ChartType;
import com.jiuqi.nr.query.common.BlockTitleAlign;
import com.jiuqi.nr.query.common.BlockTitleRule;
import com.jiuqi.nr.query.common.ImageLayout;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.deserializer.QueryBlockDefineDeserializer;
import com.jiuqi.nr.query.querymodal.QueryType;
import com.jiuqi.nr.query.serializer.QueryBlockDefineSerializer;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

@DBAnno.DBTable(dbTable="SYS_QUERYBLOCKDEFINE")
@JsonSerialize(using=QueryBlockDefineSerializer.class)
@JsonDeserialize(using=QueryBlockDefineDeserializer.class)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class QueryBlockDefine {
    public static final String QUERYBLOCK_BLOCKID = "id";
    public static final String QUERYBLOCK_BLOCKTITLE = "title";
    public static final String QUERYBLOCK_BLOCKMODALID = "modalid";
    public static final String QUERYBLOCK_BLOCKGRIDDATA = "griddata";
    public static final String QUERYBLOCK_BLOCKFORMDATA = "formdata";
    public static final String QUERYBLOCK_BLOCKPRINTDATA = "printdata";
    public static final String QUERYBLOCK_BLOCKUPDATETIME = "updatetime";
    public static final String QUERYBLOCK_BLOCKINFO = "blockinfo";
    public static final String QUERYBLOCK_BLOCKEXTENSION = "extension";
    public static final String QUERYBLOCK_MASTERINFO = "masterinfo";
    public static final String QUERYBLOCK_MASTERKEYS = "masterkeys";
    public static final String QUERYBLOCK_TASKDEFSTARTPERIOD = "taskdefstartperiod";
    public static final String QUERYBLOCK_TASKDEFENDPERIOD = "taskdefendperiod";
    public static final String QUERYBLOCK_HASUSERFORM = "isuserform";
    public static final String QUERYBLOCK_PAGE = "page";
    public static final String QUERYBLOCK_ISEND = "isEnd";
    public static final String QUERYBLOCK_POSX = "x";
    public static final String QUERYBLOCK_POSY = "y";
    public static final String QUERYBLOCK_POSW = "w";
    public static final String QUERYBLOCK_POSH = "h";
    public static final String QUERYBLOCK_POSI = "i";
    public static final String QUERYBLOCK_ISFROMDB = "fromdb";
    public static final String QUERYBLOCK_BLOCKTYPE = "blocktype";
    private static final Logger logger = LoggerFactory.getLogger(QueryBlockDefine.class);
    @DBAnno.DBField(dbField="QBD_ID", dbType=String.class, isPk=true)
    private String id;
    @DBAnno.DBField(dbField="QBD_TITLE")
    private String title;
    @DBAnno.DBField(dbField="QBD_MODELID", dbType=String.class, isPk=false)
    private String modelID;
    @DBAnno.DBField(dbField="QBD_HASUSERFORM", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean hasUserForm;
    private Boolean isEnd = false;
    private QueryType queryType;
    @DBAnno.DBField(dbField="QBD_MASTERINFOR", dbType=Clob.class)
    private String queryMasterKeysStr;
    private Map<String, Object> queryMasterKeys;
    private JsonNode masterinfor;
    @DBAnno.DBField(dbField="QMD_EXTENSION", dbType=Clob.class)
    private String blockExtension;
    @DBAnno.DBField(dbField="UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updeTime;
    private BlockInfo blockInfo = new BlockInfo();
    @DBAnno.DBField(dbField="QBD_BLOCKINFO", dbType=Clob.class)
    private String blockInfoClob;
    @DBAnno.DBField(dbField="QBD_GRIDDATA", dbType=Clob.class)
    private String gridData;
    @DBAnno.DBField(dbField="QBD_FORMDATA", dbType=Clob.class)
    private String formData;
    @DBAnno.DBField(dbField="QBD_PRINT", dbType=Clob.class)
    private String printData;
    private int PageDirection = 0;
    private QueryGridPage page;
    private String taskDefStartPeriod;
    private String taskDefEndPeriod;
    boolean isFormDb;
    @DBAnno.DBField(dbField="QBD_QUERYBLOCKTYPE", tranWith="transBlockType", dbType=String.class, appType=QueryBlockType.class)
    private QueryBlockType blockType;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHasUserForm(boolean hasUserForm) {
        this.hasUserForm = hasUserForm;
    }

    public boolean getHasUserForm() {
        return this.hasUserForm;
    }

    public String getTitle() {
        return this.title;
    }

    public void setShowSum(boolean showSum) {
        this.blockInfo.setShowSum(showSum);
    }

    public boolean isShowSum() {
        return this.blockInfo.isShowSum();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BlockTitleAlign getTitleAlign() {
        return this.blockInfo.getTitleAlign();
    }

    public void setTitleAlign(BlockTitleAlign titleAlign) {
        this.blockInfo.setTitleAlign(titleAlign);
    }

    public String getCode() {
        return this.blockInfo.getCode();
    }

    public void setCode(String code) {
        this.blockInfo.setCode(code);
    }

    public String getModelID() {
        return this.modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public boolean getIsDataSet() {
        return this.blockInfo.getIsDataSet();
    }

    public void setIsDataSet(boolean isdataset) {
        this.blockInfo.setIsDateSet(isdataset);
    }

    public void setChartType(ChartType chartType) {
        this.blockInfo.setChartType(chartType);
    }

    public Integer getPosX() {
        return this.blockInfo.getPosX();
    }

    public void setPosX(Integer posX) {
        this.blockInfo.setPosX(posX);
    }

    public Integer getPosY() {
        return this.blockInfo.getPosY();
    }

    public void setPosY(Integer posY) {
        this.blockInfo.setPosY(posY);
    }

    public Integer getWidth() {
        return this.blockInfo.getWidth();
    }

    public void setWidth(Integer width) {
        this.blockInfo.setWidth(width);
    }

    public Integer getHeight() {
        return this.blockInfo.getHeight();
    }

    public void setHeight(Integer height) {
        this.blockInfo.setHeight(height);
    }

    public String getHeadBackColor() {
        return this.blockInfo.getBackColor();
    }

    public void setHeadBackColor(String headBackColor) {
        this.blockInfo.setBackColor(headBackColor);
    }

    public String getFontColor() {
        return this.blockInfo.getFontColor();
    }

    public void setFontColor(String fontColor) {
        this.blockInfo.setFontColor(fontColor);
    }

    public String getFontSize() {
        return this.blockInfo.getFontSize();
    }

    public void setFontSize(String fontSize) {
        this.blockInfo.setFontSize(fontSize);
    }

    public String getFontName() {
        return this.blockInfo.getFontName();
    }

    public void setFontName(String fontName) {
        this.blockInfo.setFontName(fontName);
    }

    public BlockTitleRule getTitleRule() {
        return this.blockInfo.getTitleRule();
    }

    public void setTitleRule(BlockTitleRule titleRule) {
        this.blockInfo.setTitleRule(titleRule);
    }

    public String getBackColor() {
        return this.blockInfo.getBackColor();
    }

    public void setBackColor(String backColor) {
        this.blockInfo.setBackColor(backColor);
    }

    public String getBackgroundImage() {
        return this.blockInfo.getBackgroundImage();
    }

    public void setBackgroundImage(String backgroundImage) {
        this.blockInfo.setBackgroundImage(backgroundImage);
    }

    public ImageLayout getBackgroundImageLayout() {
        return this.blockInfo.getBackgroundImageLayout();
    }

    public void setBackgroundImageLayout(ImageLayout backgroundImageLayout) {
        this.blockInfo.setBackgroundImageLayout(backgroundImageLayout);
    }

    public double getOpacity() {
        return this.blockInfo.getOpacity();
    }

    public void setOpacity(double opacity) {
        this.blockInfo.setOpacity(opacity);
    }

    public List<QueryDimensionDefine> getQueryDimensions() {
        return this.blockInfo.getQueryDimensions();
    }

    public void setQueryDimensions(List<QueryDimensionDefine> queryDimensions) {
        this.blockInfo.setQueryDimensions(queryDimensions);
    }

    public String getBlockExtension() {
        return this.blockExtension;
    }

    public void setBlockExtension(String blockExtension) {
        this.blockExtension = blockExtension;
    }

    public String getOrder() {
        return this.blockInfo.getOrder();
    }

    public void setOrder(String order) {
        this.blockInfo.setOrder(order);
    }

    public boolean isShowNullRow() {
        return this.blockInfo.isShowNullRow();
    }

    public void setShowNullRow(boolean isShow) {
        this.blockInfo.setShowNullRow(isShow);
    }

    public boolean isShowZeroRow() {
        return this.blockInfo.isShowZeroRow();
    }

    public void setShowZeroRow(boolean isShow) {
        this.blockInfo.setShowZeroRow(isShow);
    }

    public boolean isShowSubTotal() {
        return this.blockInfo.isShowSubTotal();
    }

    public boolean isShowDetail() {
        return this.blockInfo.isShowDetail();
    }

    public void setShowSubTotal(boolean isShow) {
        this.blockInfo.setShowSubTotal(isShow);
    }

    public boolean isPaging() {
        return this.blockInfo.isPaging();
    }

    public void setIsPaging(boolean isPaging) {
        this.blockInfo.setIsPaging(isPaging);
    }

    public Date getUpdeTime() {
        return this.updeTime;
    }

    public void setUpdeTime(Date updeTime) {
        this.updeTime = updeTime;
    }

    public BlockInfo getBlockInfo() {
        return this.blockInfo;
    }

    public String getBlockInfoStr() {
        try {
            return this.convertBlockInfoToJson();
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public void setBlockInfoStr(String blockInfoStr) {
        try {
            boolean i = false;
            this.blockInfo = this.convertJsonToBlckInfo(blockInfoStr);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String getBlockInfoBlob() {
        return this.blockInfoClob;
    }

    public void setBlockInfoBlob(String infoBlob) {
        this.blockInfoClob = infoBlob;
    }

    public String getGridData() {
        return this.gridData;
    }

    public void setGridData(String gridData) {
        this.gridData = gridData;
    }

    public String getFormdata() {
        if (this.formData == null) {
            this.formData = "";
        }
        return this.formData;
    }

    public void setFormdata(String formdata) {
        this.formData = formdata;
    }

    public String getPrintData() {
        return this.printData;
    }

    public void setPrintData(String printData) {
        this.printData = printData;
    }

    public Boolean getEnd() {
        return this.isEnd;
    }

    public void setEnd(Boolean end) {
        this.isEnd = end;
    }

    public QueryType getQueryType() {
        return this.queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public void setQueryMastersStr(String masterKeys) {
        this.parseMasterKeys(masterKeys);
        this.queryMasterKeysStr = masterKeys;
    }

    private void parseMasterKeys(String masterKeys) {
        try {
            Map maps;
            if (masterKeys.substring(0, 1).equals("\"")) {
                masterKeys = masterKeys.substring(1, masterKeys.length() - 1);
                masterKeys = StringEscapeUtils.unescapeJava((String)masterKeys);
            }
            ObjectMapper mapper = new ObjectMapper();
            this.queryMasterKeys = maps = (Map)mapper.readValue(masterKeys, Map.class);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public String getQueryMastersStr() {
        return this.queryMasterKeysStr;
    }

    public void setQueryMasters(Map<String, Object> masterKeys) {
        this.queryMasterKeys = masterKeys;
        if (masterKeys != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                this.queryMasterKeysStr = objectMapper.writeValueAsString(masterKeys);
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public Map<String, Object> getQueryMasters() {
        if (this.queryMasterKeys == null && !StringUtil.isNullOrEmpty((String)this.queryMasterKeysStr)) {
            this.parseMasterKeys(this.queryMasterKeysStr);
        }
        return this.queryMasterKeys;
    }

    public void setMasterInfo(JsonNode masterInfor) {
        this.masterinfor = masterInfor;
    }

    public String getQueryMasterKeys() {
        return this.getMasterKeyValue("masterKeys");
    }

    public String getPeriodTypeInCache() {
        return this.getMasterKeyValue("periodtype");
    }

    public String getQueryMasterScheme() {
        return this.getMasterKeyValue("schemeKey");
    }

    private String getCacheKeyValue(String key) {
        String[] splits = this.queryMasterKeysStr.split(",");
        String masterKeys = "";
        for (String str : splits) {
            if (str.indexOf(key) < 0) continue;
            String[] kv = str.split(":");
            masterKeys = kv[1].substring(kv[1].indexOf("\"") + 1, kv[1].lastIndexOf(";"));
            break;
        }
        return masterKeys;
    }

    public String getMasterKeyValue(String key) {
        String[] splits = String.valueOf(this.queryMasterKeysStr).split(",");
        String masterKeys = "";
        for (String str : splits) {
            String[] kv;
            if (str.indexOf(key) < 0) continue;
            boolean isMapFormat = str.indexOf("=") > 0;
            String[] stringArray = kv = isMapFormat ? str.split("=") : str.split(":");
            if (key.equals("periodtype")) {
                masterKeys = kv[1].substring(kv[1].indexOf("\"") + 1).replace("\\\\", "");
                String type = masterKeys.endsWith(",") || masterKeys.endsWith("\"") ? masterKeys.substring(0, masterKeys.length() - 1) : masterKeys;
                int index = type.indexOf("\\");
                if (index > 0) {
                    type = type.substring(0, type.length() - 1);
                }
                return type;
            }
            masterKeys = kv[1].substring(kv[1].indexOf("\"") + 1).replaceAll("\\\\", "");
            if ("null".equals(masterKeys)) {
                return masterKeys;
            }
            if (!masterKeys.endsWith(",")) {
                masterKeys = masterKeys.substring(0, masterKeys.length() - 1);
                break;
            }
            masterKeys = masterKeys.substring(0, masterKeys.length() - (isMapFormat ? 1 : 2));
            break;
        }
        return masterKeys;
    }

    private Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            ReflectionUtils.makeAccessible(field);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    public FieldSelectCache getMasterCache1() {
        try {
            QueryBlockDefineDeserializer queryBlockDefineDeserializer = new QueryBlockDefineDeserializer();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    private Map<String, Object> getQueryMastersForFieldSelect() {
        try {
            LinkedHashMap<String, Object> info = new LinkedHashMap<String, Object>();
            Map<String, Object> temp = this.objectToMap(this.queryMasterKeys);
            JSONObject temp1 = new JSONObject(temp.get("_value").toString());
            Iterator keys = temp1.keys();
            while (keys.hasNext()) {
                String key = (String)keys.next();
                if (!(key.equals("isrelated") || key.equals("periodtype") || key.equals("changed"))) {
                    JSONObject obj = new JSONObject(temp1.get(key).toString());
                    if (obj == null) continue;
                    FieldSelectCache cache = new FieldSelectCache();
                    String bizFieldKeys = obj.get("bizFieldKeys").toString();
                    cache.setBizFieldKeys(bizFieldKeys);
                    String schemeKey = obj.get("schemeKey").toString();
                    cache.setSchemeKey(schemeKey);
                    String masterDimensions = obj.get("masterDimensions").toString();
                    cache.setMasterDimensions(masterDimensions);
                    String masterKeys = obj.get("masterKeys").toString();
                    cache.setMasterKeys(masterKeys);
                    info.put(key, cache);
                    continue;
                }
                info.put(key, temp1.get(key));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    public void converBlockToByte() {
        try {
            String blockInfoStr;
            this.blockInfoClob = blockInfoStr = this.convertBlockInfoToJson();
        }
        catch (JsonProcessingException jsonE) {
            this.blockInfo = null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.blockInfo = null;
        }
    }

    private String convertBlockInfoToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String blockJson = objectMapper.writeValueAsString((Object)this.blockInfo);
        return blockJson;
    }

    private BlockInfo convertJsonToBlckInfo(String blockInfoStr) {
        try {
            if (StringUtil.isNullOrEmpty((String)blockInfoStr)) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            BlockInfo item = (BlockInfo)objectMapper.readValue(blockInfoStr, BlockInfo.class);
            return item;
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public int getPageDirection() {
        return this.PageDirection;
    }

    public void setPageDirection(int val) {
        this.PageDirection = val;
    }

    public QueryGridPage getPageData() {
        int size;
        if (this.page == null) {
            this.page = new QueryGridPage();
        }
        this.page.setPageSize((size = this.blockInfo.getPageSize()) == 0 ? QueryGridPage.CONST_DEFAULTPAGESIZE : size);
        return this.page;
    }

    public void setPageData(QueryGridPage page) {
        this.page = page;
    }

    public void setPageData(String page) {
        try {
            if (StringUtil.isNullOrEmpty((String)page)) {
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            this.page = (QueryGridPage)objectMapper.readValue(page, QueryGridPage.class);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public int getDimLevelShowType() {
        return this.blockInfo.getDimLevelShowType();
    }

    public QueryFieldPosition getFieldPosition() {
        return this.blockInfo.getFieldPosition();
    }

    public String getTaskDefStartPeriod() {
        return this.taskDefStartPeriod;
    }

    public void setTaskDefStartPeriod(String taskDefStartPeriod) {
        this.taskDefStartPeriod = taskDefStartPeriod;
    }

    public String getTaskDefEndPeriod() {
        return this.taskDefEndPeriod;
    }

    public void setTaskDefEndPeriod(String taskDefEndPeriod) {
        this.taskDefEndPeriod = taskDefEndPeriod;
    }

    public void setFromDb(boolean isFromDb) {
        this.isFormDb = this.isFormDb;
    }

    public boolean getFromDb() {
        return this.isFormDb;
    }

    public QueryBlockType getBlockType() {
        if (this.blockType == null) {
            this.blockType = this.blockInfo.getBlockType();
        } else if (!this.blockType.equals((Object)this.blockInfo.getBlockType())) {
            this.blockType = this.blockInfo.getBlockType();
        }
        return this.blockType;
    }

    public void setBlockType(QueryBlockType blockType) {
        this.blockType = blockType;
        this.blockInfo.setBlockType(blockType);
    }
}

