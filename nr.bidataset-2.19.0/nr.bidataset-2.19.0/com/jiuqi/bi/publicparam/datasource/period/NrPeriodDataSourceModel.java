/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.period.common.utils.PeriodPropertyGroup
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.DataBusinessType
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.datasource.period;

import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class NrPeriodDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.publicparam.datasource.date";
    public static final String TITLE = "\u62a5\u8868\u65f6\u671f";
    protected static final String TAG_ENTITY_VIEW_ID = "entityViewId";
    protected static final String TAG_PERIOD_TYPE = "periodType";
    protected static final String TAG_PERIOD_START_END = "periodStartEnd";
    protected static final String TAG_IS_REMOTE = "isRemote";
    protected static final String TAG_MIN_FISCAL_MONTH_ID = "minFiscalMonth";
    protected static final String TAG_MAX_FISCAL_MONTH_ID = "maxFiscalMonth";
    protected static final String TAG_ALIAS_MAP = "aliasMap";
    protected static final String TAG_SIMPLE_TITLE_MAP = "simpleTitleMap";
    protected String entityViewId;
    protected int periodType;
    protected int minFiscalMonth = -1;
    protected int maxFiscalMonth = -1;
    protected String periodStartEnd;
    protected boolean isRemote = false;
    protected PeriodPropertyGroup periodPropertyGroup;
    protected HashMap<String, String> simpleTitleMap;
    protected HashMap<String, String> aliasMap;
    protected PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanProvider.getBean(PeriodEngineService.class);

    public NrPeriodDataSourceModel() {
        this.businessType = DataBusinessType.TIME_DIM;
        this.dataType = 6;
    }

    public String getType() {
        return TYPE;
    }

    public String getEntityViewId() {
        return this.entityViewId;
    }

    public void setEntityViewId(String entityViewId) {
        this.entityViewId = entityViewId;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStartEnd() {
        return this.periodStartEnd;
    }

    public void setPeriodStartEnd(String periodStartEnd) {
        this.periodStartEnd = periodStartEnd;
    }

    protected void fromExtJson(JSONObject json) throws JSONException {
        this.entityViewId = json.optString(TAG_ENTITY_VIEW_ID);
        this.periodType = json.optInt(TAG_PERIOD_TYPE);
        this.periodStartEnd = json.optString(TAG_PERIOD_START_END);
        if (this.businessType == null || this.businessType == DataBusinessType.NONE) {
            this.businessType = DataBusinessType.TIME_DIM;
        }
        if (json.has(TAG_IS_REMOTE)) {
            this.isRemote = json.optBoolean(TAG_IS_REMOTE);
        }
        this.doInit();
    }

    public void doInit() {
        IPeriodEntity periodEntity;
        PeriodType pt = PeriodType.fromType((int)this.periodType);
        TimeGranularity timeGranularity = TimeDimUtils.periodTypeToTimeGranularity((PeriodType)pt);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        if (timeGranularity != null) {
            this.timegranularity = timeGranularity.value();
            this.timekey = true;
            if (timeGranularity == TimeGranularity.MONTH && StringUtils.isNotEmpty((String)this.entityViewId) && !this.entityViewId.equals("Y")) {
                periodEntity = periodAdapter.getPeriodEntity(this.entityViewId);
                this.minFiscalMonth = periodEntity.getMinFiscalMonth();
                this.maxFiscalMonth = periodEntity.getMaxFiscalMonth();
            }
        }
        if (StringUtils.isEmpty((String)this.entityViewId)) {
            this.entityViewId = String.valueOf((char)pt.code());
        }
        periodEntity = periodAdapter.getPeriodEntity(this.entityViewId);
        if (PeriodType.CUSTOM.type() == this.periodType && periodEntity != null) {
            this.periodPropertyGroup = periodEntity.getPeriodPropertyGroup();
        }
        boolean needAliasMap = PeriodUtils.checkTitle((IPeriodEntity)periodEntity);
        boolean needSimpleTitleMap = PeriodUtils.checkSimpleTitle((IPeriodEntity)periodEntity);
        if (needAliasMap || needSimpleTitleMap) {
            List peirodItems = periodAdapter.getPeriodProvider(periodEntity.getKey()).getPeriodItems();
            if (needAliasMap) {
                peirodItems.forEach(item -> {
                    if (this.aliasMap == null) {
                        this.aliasMap = new HashMap();
                    }
                    this.aliasMap.put(item.getCode(), item.getTitle());
                });
            }
            if (needSimpleTitleMap) {
                peirodItems.forEach(item -> {
                    String simpleTitle = item.getSimpleTitle();
                    if (StringUtils.isNotEmpty((String)simpleTitle)) {
                        if (this.simpleTitleMap == null) {
                            this.simpleTitleMap = new HashMap();
                        }
                        this.simpleTitleMap.put(item.getCode(), simpleTitle);
                    }
                });
            }
        }
    }

    protected void toExtJson(JSONObject json) throws JSONException {
        json.put(TAG_ENTITY_VIEW_ID, (Object)this.entityViewId);
        json.put(TAG_PERIOD_TYPE, this.periodType);
        json.put(TAG_PERIOD_START_END, (Object)this.periodStartEnd);
        json.put(TAG_IS_REMOTE, this.isRemote);
        json.put(TAG_MIN_FISCAL_MONTH_ID, this.minFiscalMonth);
        json.put(TAG_MAX_FISCAL_MONTH_ID, this.maxFiscalMonth);
        if (this.aliasMap != null) {
            JSONObject aliasMapJson = new JSONObject();
            this.aliasMap.forEach((k, v) -> aliasMapJson.put(k, v));
            json.put(TAG_ALIAS_MAP, (Object)aliasMapJson);
        }
        if (this.simpleTitleMap != null) {
            JSONObject simpleTitleMapJson = new JSONObject();
            this.simpleTitleMap.forEach((k, v) -> simpleTitleMapJson.put(k, v));
            json.put(TAG_SIMPLE_TITLE_MAP, (Object)simpleTitleMapJson);
        }
    }

    public boolean isRemote() {
        return this.isRemote;
    }

    public void setRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    public PeriodPropertyGroup getPeriodPropertyGroup() {
        return this.periodPropertyGroup;
    }

    public ParameterDataSourceHierarchyType getHierarchyType() {
        if (this.periodPropertyGroup != null) {
            return ParameterDataSourceHierarchyType.PARENTMODE;
        }
        return ParameterDataSourceHierarchyType.NONE;
    }

    public boolean hasHierarchy() {
        ParameterDataSourceHierarchyType hierarchyType = this.getHierarchyType();
        return hierarchyType == ParameterDataSourceHierarchyType.PARENTMODE || hierarchyType == ParameterDataSourceHierarchyType.STRUCTURECODE;
    }

    public int getMinFiscalMonth() {
        return this.minFiscalMonth;
    }

    public int getMaxFiscalMonth() {
        return this.maxFiscalMonth;
    }

    public void setMinFiscalMonth(int minFiscalMonth) {
        this.minFiscalMonth = minFiscalMonth;
    }

    public void setMaxFiscalMonth(int maxFiscalMonth) {
        this.maxFiscalMonth = maxFiscalMonth;
    }

    public void setPeriodPropertyGroup(PeriodPropertyGroup periodPropertyGroup) {
        this.periodPropertyGroup = periodPropertyGroup;
    }

    public HashMap<String, String> getSimpleTitleMap() {
        return this.simpleTitleMap;
    }

    public void setSimpleTitleMap(HashMap<String, String> simpleTitleMap) {
        this.simpleTitleMap = simpleTitleMap;
    }

    public HashMap<String, String> getAliasMap() {
        return this.aliasMap;
    }

    public void setAliasMap(HashMap<String, String> aliasMap) {
        this.aliasMap = aliasMap;
    }
}

