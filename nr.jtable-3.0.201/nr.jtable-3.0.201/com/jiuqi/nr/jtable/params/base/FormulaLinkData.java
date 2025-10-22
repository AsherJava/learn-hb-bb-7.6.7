/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.FloatData
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class FormulaLinkData
extends LinkData {
    private String thoundsMark;
    private boolean showThoundMark;
    private boolean percent;
    private String currencyMark;
    private boolean showCurrencyMark;
    private String formula;
    private String entityKey;
    private List<String> capnames;
    private String pname;

    public FormulaLinkData(DataLinkDefine dataLinkDefine, FormDefine formDefine) {
        super(dataLinkDefine, (FieldDefine)null);
        this.type = LinkType.LINK_TYPE_FORMULA.getValue();
        this.formula = dataLinkDefine.getLinkExpression();
        FormatProperties formatProperties = dataLinkDefine.getFormatProperties();
        if (formatProperties != null) {
            switch (formatProperties.getFormatType()) {
                case 0: {
                    this.style = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, (FieldDefine)null);
                    break;
                }
                default: {
                    this.type = LinkType.LINK_TYPE_DECIMAL.getValue();
                    NumberFormatParser numberFormatParser = NumberFormatParser.parse((FormatProperties)formatProperties);
                    this.thoundsMark = ",";
                    this.showThoundMark = numberFormatParser.isThousands() != null ? numberFormatParser.isThousands() : false;
                    this.percent = numberFormatParser.isPercentage() != null ? numberFormatParser.isPercentage() : false;
                    this.currencyMark = numberFormatParser.getCurrency() != null ? numberFormatParser.getCurrency() : "";
                    this.showCurrencyMark = StringUtils.isNotEmpty((String)this.currencyMark);
                    this.style = DataLinkStyleUtil.getNumberLinkStyle(numberFormatParser, (FieldDefine)null, formatProperties == null, formDefine);
                    break;
                }
            }
        } else {
            this.style = "";
        }
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public List<String> getCapnames() {
        return this.capnames;
    }

    public void setCapnames(List<String> capnames) {
        this.capnames = capnames;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData;
        }
        if (StringUtils.isNotEmpty((String)this.entityKey)) {
            if (dataFormaterCache.isJsonData()) {
                return this.getFormatJsonData(data.getAsString(), dataFormaterCache);
            }
            return this.getFormatData(data.getAsString(), dataFormaterCache);
        }
        if (data instanceof FloatData) {
            double floatData = data.getAsFloat();
            String formatStr = "#0";
            DecimalFormat df = new DecimalFormat(formatStr);
            df.setRoundingMode(RoundingMode.HALF_UP);
            if (String.valueOf(floatData).contains("E") || String.valueOf(floatData).contains("e")) {
                BigDecimal bigDecimal = data.getAsCurrency();
                return df.format(bigDecimal);
            }
        }
        return data.getAsString();
    }

    private String getFormatData(String dataStr, DataFormaterCache dataFormaterCache) {
        StringBuffer returnStr = new StringBuffer();
        if (StringUtils.isNotEmpty((String)dataStr)) {
            String[] split;
            for (String entityCode : split = dataStr.split(";")) {
                EntityData entityDataInfo = this.getEntityDataInfo(entityCode, dataFormaterCache);
                String entityShowTitle = entityCode;
                if (entityDataInfo != null) {
                    entityShowTitle = entityDataInfo.getRowCaption();
                }
                returnStr.append(returnStr.length() > 0 ? ";" + entityShowTitle : entityShowTitle);
            }
        }
        return returnStr.toString();
    }

    public String getFormatJsonData(String dataStr, DataFormaterCache dataFormaterCache) {
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isNotEmpty((String)dataStr)) {
            String[] split;
            for (String entityCode : split = dataStr.split(";")) {
                EntityData entityDataInfo = this.getEntityDataInfo(entityCode, dataFormaterCache);
                JSONObject jsonObject = new JSONObject();
                if (entityDataInfo == null) {
                    jsonObject.put("code", (Object)entityCode);
                    jsonObject.put("title", (Object)entityCode);
                    jsonObject.put("codevalue", (Object)entityCode);
                } else {
                    jsonObject.put("code", (Object)entityDataInfo.getId());
                    jsonObject.put("title", (Object)entityDataInfo.getRowCaption());
                    jsonObject.put("codevalue", (Object)entityDataInfo.getCode());
                }
                jsonArray.put((Object)jsonObject);
            }
        }
        return jsonArray.toString();
    }

    private EntityData getEntityDataInfo(String keyOrCode, DataFormaterCache dataFormaterCache) {
        EntityData entityDataInfo;
        Map<String, Map<String, EntityData>> entityDataInfoMap = dataFormaterCache.getEntityDataInfoMap();
        Map<String, EntityData> entityMap = entityDataInfoMap.get(this.entityKey.toString() + ";" + this.key);
        if (entityMap == null) {
            entityMap = new HashMap<String, EntityData>(1000);
            entityDataInfoMap.put(this.entityKey.toString() + ";" + this.key, entityMap);
        }
        if ((entityDataInfo = entityMap.get(keyOrCode)) == null) {
            EntityByKeyReturnInfo returnInfo;
            IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setContext(dataFormaterCache.getJtableContext());
            entityQueryByKeyInfo.setEntityViewKey(this.entityKey);
            entityQueryByKeyInfo.setDataLinkKey(this.key);
            entityQueryByKeyInfo.setEntityKey(keyOrCode);
            Map<String, Set<String>> entityCaptionFields = dataFormaterCache.getEntityCaptionFields();
            if (entityCaptionFields != null && entityCaptionFields.containsKey(this.entityKey)) {
                entityQueryByKeyInfo.setCaptionFields(entityCaptionFields.get(this.entityKey));
            }
            if ((entityDataInfo = (returnInfo = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo)).getEntity()) == null) {
                return null;
            }
            List<String> parents = entityDataInfo.getParents();
            for (String parent : parents) {
                EntityData parentEntityDataInfo = this.getEntityDataInfo(parent, dataFormaterCache);
                if (parentEntityDataInfo != null) continue;
                entityDataInfo.setParentId(null);
                entityDataInfo.setParents(new ArrayList<String>());
                break;
            }
            if (this.capnames != null && this.capnames.size() > 0) {
                String rowCaption = "";
                List<String> cells = returnInfo.getCells();
                for (String captionField : this.capnames) {
                    if (!cells.contains(captionField)) continue;
                    int captionFieldIndex = cells.indexOf(captionField);
                    if (rowCaption.length() > 0) {
                        rowCaption = rowCaption + "|";
                    }
                    if (StringUtils.isNotEmpty((String)this.pname) && captionField.equals(this.pname)) {
                        String pCaption = "";
                        parents = entityDataInfo.getParents();
                        for (String parent : parents) {
                            EntityData parentEntityDataInfo = this.getEntityDataInfo(parent, dataFormaterCache);
                            if (pCaption.length() > 0) {
                                pCaption = pCaption + "/";
                            }
                            pCaption = pCaption + parentEntityDataInfo.getData().get(captionFieldIndex);
                        }
                        if (pCaption.length() > 0) {
                            pCaption = pCaption + "/";
                        }
                        pCaption = pCaption + entityDataInfo.getData().get(captionFieldIndex);
                        rowCaption = rowCaption + pCaption;
                        continue;
                    }
                    rowCaption = rowCaption + entityDataInfo.getData().get(captionFieldIndex);
                }
                if (StringUtils.isNotEmpty((String)rowCaption)) {
                    entityDataInfo.setRowCaption(rowCaption);
                }
            }
            entityMap.put(keyOrCode, entityDataInfo);
            Map<String, EntityReturnInfo> entityDataMap = dataFormaterCache.getEntityDataMap();
            if (entityDataMap != null) {
                EntityReturnInfo entityData = entityDataMap.get(this.entityKey.toString());
                if (entityData == null) {
                    entityData = new EntityReturnInfo();
                    entityData.setCells(returnInfo.getCells());
                    entityData.getEntitys().add(entityDataInfo);
                    entityDataMap.put(this.entityKey.toString(), entityData);
                } else {
                    entityData.getEntitys().add(entityDataInfo);
                }
            }
        }
        return entityDataInfo;
    }

    @Override
    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo) {
        if (saveErrorDataInfo == null) {
            saveErrorDataInfo = new ImportErrorDataInfo();
        }
        super.getData(value, dataFormaterCache, saveErrorDataInfo);
        return null;
    }

    public String getThoundsMark() {
        return this.thoundsMark;
    }

    public void setThoundsMark(String thoundsMark) {
        this.thoundsMark = thoundsMark;
    }

    public boolean isShowThoundMark() {
        return this.showThoundMark;
    }

    public void setShowThoundMark(boolean showThoundMark) {
        this.showThoundMark = showThoundMark;
    }

    public boolean isPercent() {
        return this.percent;
    }

    public void setPercent(boolean percent) {
        this.percent = percent;
    }

    public boolean isShowCurrencyMark() {
        return this.showCurrencyMark;
    }

    public void setShowCurrencyMark(boolean showCurrencyMark) {
        this.showCurrencyMark = showCurrencyMark;
    }

    public String getCurrencyMark() {
        return this.currencyMark;
    }

    public void setCurrencyMark(String currencyMark) {
        this.currencyMark = currencyMark;
    }
}

