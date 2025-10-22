/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.np.NpReportQueryProvider
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.exception.NotFoundEntityException
 *  com.jiuqi.nr.jtable.params.base.RegionTab
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.inputdata.query.base;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.gcreport.inputdata.query.constant.EntitiesOrgMode;
import com.jiuqi.gcreport.inputdata.query.constant.FInnerTableTabsDoc;
import com.jiuqi.gcreport.inputdata.query.constant.InnerTableTabsType;
import com.jiuqi.gcreport.inputdata.query.constant.InputDataQueryFilterType;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.exception.NotFoundEntityException;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class GcDataEntryContext
extends DataEntryContext {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(GcDataEntryContext.class);
    protected static final String MAIN_TABLE_NAME = "GC_INPUTDATA";
    protected static final String OFFSET_TABLE_NAME = "GC_OFFSETVCHRITEM";
    private boolean isGcQuery;
    private static final String CURRENCY_CODE = "CNY";
    private InnerTableTabsType TabType;
    private InputDataQueryFilterType filterType;
    private GcOrgCacheVO initOrg;
    private List<GcOrgCacheVO> initOrgList;
    private GcOrgCacheVO selectOrg;
    private EntitiesOrgMode orgMode;
    private YearPeriodDO yearPeriod;
    private GcBaseData initCurrency;
    private String gcOrgType;
    private TaskDefine task;
    private FormSchemeDefine formScheme;
    private DataRegionDefine regionDefine;
    private FormDefine form;
    private DimensionValueSet masterKeys;
    private QueryParam queryParam;
    private String mainTableName;
    private String orgTableName;

    public GcDataEntryContext(QueryEnvironment env, NpReportQueryProvider provider) throws Exception {
        this(env.getFormSchemeKey(), env.getRegionKey(), env.getFormulaSchemeKey(), provider, true);
    }

    public GcDataEntryContext(@NotNull String formSchemeKey, NpReportQueryProvider provider) throws Exception {
        this(formSchemeKey, null, null, provider, false);
    }

    public GcDataEntryContext(@NotNull String formSchemeKey, @NotNull String regionKey, NpReportQueryProvider provider) throws Exception {
        this(formSchemeKey, regionKey, null, provider, true);
    }

    private GcDataEntryContext(String formSchemeKey, String regionKey, String formulaSchemeKey, NpReportQueryProvider provider, boolean throwException) throws Exception {
        this.queryParam = provider.getQueryParam();
        this.isGcQuery = true;
        if (formSchemeKey == null) {
            this.isGcQuery = false;
            throw new NotFoundEntityException("\u62a5\u8868\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a", new String[]{""});
        }
        this.formScheme = provider.getRunTimeViewController().getFormScheme(formSchemeKey);
        if (this.formScheme == null) {
            logger.error("\u62a5\u8868\u65b9\u6848ID" + formSchemeKey + "\u6709\u8bef,\u627e\u4e0d\u5230\u5bf9\u5e94\u65b9\u6848");
            this.isGcQuery = false;
            throw new NotFoundEntityException("\u62a5\u8868\u65b9\u6848ID" + formSchemeKey + "\u6709\u8bef,\u627e\u4e0d\u5230\u5bf9\u5e94\u65b9\u6848", new String[]{""});
        }
        this.orgTableName = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)this.formScheme.getTaskKey());
        this.setFormulaSchemeKey(formulaSchemeKey == null ? "" : formulaSchemeKey);
        this.task = provider.getRunTimeViewController().queryTaskDefine(this.formScheme.getTaskKey());
        this.setFormSchemeKey(formSchemeKey);
        this.setTaskKey(this.formScheme.getTaskKey());
        if (regionKey == null) {
            this.isGcQuery = false;
            if (throwException) {
                logger.debug("\u62a5\u8868\u65b9\u6848ID" + formSchemeKey + ",\u533a\u57dfID\u4e3a\u7a7a,\u629b\u51fa\u5f02\u5e38,\u8d70\u62a5\u8868\u9ed8\u8ba4\u67e5\u8be2\u5668DataQueryImpl");
                throw new NotFoundEntityException("\u6682\u65f6\u8d70NR\u7684DataQueryImpl", new String[]{""});
            }
        } else {
            this.regionDefine = provider.getRunTimeViewController().queryDataRegionDefine(regionKey);
            if (this.regionDefine == null) {
                logger.debug("\u62a5\u8868\u65b9\u6848ID" + formSchemeKey + ",\u533a\u57dfID" + regionKey + "\u627e\u4e0d\u5230\u5bf9\u5e94\u533a\u57df,\u4e0d\u80fd\u8d70\u5185\u90e8\u8868\u67e5\u8be2");
                this.isGcQuery = false;
            } else if (this.regionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE) {
                logger.debug("\u62a5\u8868\u65b9\u6848ID" + formSchemeKey + ",\u533a\u57dfID" + regionKey + "\u662f\u7b80\u5355\u5206\u533a\u533a\u57df,\u4e0d\u80fd\u8d70\u5185\u90e8\u8868\u67e5\u8be2");
                this.isGcQuery = false;
                this.form = provider.getRunTimeViewController().queryFormById(this.regionDefine.getFormKey());
                this.setFormKey(this.form.getKey().toString());
            } else {
                logger.debug("\u62a5\u8868\u65b9\u6848ID" + formSchemeKey + ",\u533a\u57dfID" + regionKey + "\u8d70\u5185\u90e8\u8868\u67e5\u8be2\u5bf9\u5e94\u8868\u5355ID\u662f" + this.regionDefine.getFormKey());
                this.form = provider.getRunTimeViewController().queryFormById(this.regionDefine.getFormKey());
                this.setFormKey(this.form.getKey());
            }
        }
        if (this.regionDefine != null) {
            List fields = ((IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class)).getDeployInfoByDataFieldKeys(provider.getRunTimeViewController().getFieldKeysInRegion(this.regionDefine.getKey()).toArray(new String[0]));
            this.mainTableName = ((DataFieldDeployInfo)fields.get(0)).getTableName();
        } else if (this.form != null) {
            List regions = provider.getRunTimeViewController().getAllRegionsInForm(this.form.getKey());
            List fields = ((IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class)).getDeployInfoByDataFieldKeys(provider.getRunTimeViewController().getFieldKeysInRegion(((DataRegionDefine)regions.get(0)).getKey()).toArray(new String[0]));
            this.mainTableName = ((DataFieldDeployInfo)fields.get(0)).getTableName();
        }
        if (this.getMainTable() != null && !this.getMainTable().toUpperCase().contains(MAIN_TABLE_NAME)) {
            logger.debug(this.getLog("\u4e0d\u662f\u5185\u90e8\u8868\u67e5\u8be2"));
            this.isGcQuery = false;
        }
        if (this.isGcQuery) {
            logger.debug(this.getLog("\u662f\u5185\u90e8\u8868\u67e5\u8be2,\u521d\u59cb\u5b57\u6bb5"));
            FInnerTableTabsDoc.initQueryField(this.getMainTable());
        }
    }

    public DimensionValueSet initMasterDimension(DimensionValueSet masterKeys) {
        Object periodObj;
        logger.debug(this.getLog("\u683c\u5f0f\u5316\u7ef4\u5ea6\u6761\u4ef6"));
        if (masterKeys != null) {
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)masterKeys);
            this.setDimensionSet(dimensionSet);
        }
        this.setMasterKeys(masterKeys);
        if (masterKeys.hasValue("MD_CURRENCY")) {
            Object currencyCodeObj = masterKeys.getValue("MD_CURRENCY");
            String currCode = null;
            if (currencyCodeObj instanceof String) {
                currCode = StringUtils.toViewString((Object)currencyCodeObj);
            } else if (currencyCodeObj instanceof List) {
                currCode = StringUtils.toViewString(((List)currencyCodeObj).get(0));
            }
            if (currCode != null) {
                this.initCurrency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", currCode);
            }
            if (this.getInitCurrency() == null) {
                try {
                    logger.debug(this.getLog("\u5e01\u522b\u7ef4\u5ea6\u627e\u4e0d\u5230,\u521d\u59cb\u5316\u4e3a:CNY"));
                    this.initCurrency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", CURRENCY_CODE);
                }
                catch (Exception exception) {}
            } else {
                logger.debug(this.getLog("\u5e01\u522b\u7ef4\u5ea6\u4e3a:" + this.getInitCurrency().getCode()));
            }
        }
        if (masterKeys.hasValue("MD_GCORGTYPE")) {
            Object typeCodeObj = masterKeys.getValue("MD_GCORGTYPE");
            String typeCode = null;
            if (typeCodeObj instanceof String) {
                typeCode = StringUtils.toViewString((Object)typeCodeObj);
            } else if (typeCodeObj instanceof List) {
                typeCode = StringUtils.toViewString(((List)typeCodeObj).get(0));
            }
            if (typeCode != null) {
                this.gcOrgType = typeCode;
            }
        }
        if ((periodObj = masterKeys.getValue("DATATIME")) instanceof String) {
            this.yearPeriod = YearPeriodUtil.transform((String)this.getFormSchemeKey(), (String)((String)periodObj));
        } else if (periodObj instanceof List) {
            this.yearPeriod = YearPeriodUtil.transform((String)this.getFormSchemeKey(), (String)((String)((List)periodObj).get(0)));
        }
        logger.debug(this.getLog("\u5e74\u5ea6\u671f\u95f4:" + this.yearPeriod.toString()));
        if (!StringUtils.isEmpty((String)this.getOrgTableName())) {
            Object orgCodeObj = masterKeys.getValue("MD_ORG");
            this.initOrgEvent(orgCodeObj);
        }
        if (this.orgMode != null && this.initOrg != null) {
            logger.debug(this.getLog("\u6e05\u9664\u5e01\u522b\u7ef4\u5ea6"));
        }
        return masterKeys;
    }

    public void initOrgEvent(Object orgCodeObj) {
        this.orgMode = null;
        if (orgCodeObj != null && this.yearPeriod != null) {
            if (!StringUtils.isEmpty((String)this.gcOrgType)) {
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)this.getOrgTableName(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(this.getFormSchemeKey(), this.yearPeriod.toString()));
                if (orgCodeObj instanceof String) {
                    this.initOneOrg(tool, orgCodeObj);
                } else if (orgCodeObj instanceof List) {
                    this.initOrgList(tool, orgCodeObj);
                }
            } else {
                logger.error(this.getLog("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e3a\u7a7a"));
            }
        } else {
            logger.error(this.getLog("\u7ec4\u7ec7\u673a\u6784\u7ef4\u5ea6\u503c\u4e3a\u7a7a"));
        }
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        this.masterKeys = new DimensionValueSet();
        this.masterKeys.assign(masterKeys);
    }

    public String initRowFilter(String rowFilter) {
        if (StringUtils.isEmpty((String)rowFilter)) {
            return null;
        }
        logger.debug(this.getLog("\u683c\u5f0f\u5316\u8fc7\u6ee4\u6761\u4ef6" + rowFilter));
        String pattern = "#([\\S]+)#";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(rowFilter);
        while (matcher.find()) {
            InputDataQueryFilterType queryFilterType;
            String type = matcher.group(1);
            InnerTableTabsType tableTabsType = InnerTableTabsType.findTabsType(type);
            if (tableTabsType != null) {
                this.TabType = tableTabsType;
                rowFilter = rowFilter.replace(matcher.group(), " 1=1 ");
            }
            if ((queryFilterType = InputDataQueryFilterType.findTabsType(type)) == null) continue;
            this.filterType = queryFilterType;
            rowFilter = rowFilter.replace(matcher.group(), " 1=1 ");
        }
        if (this.TabType == null) {
            this.TabType = this.getInnerTableTabsType();
            logger.debug(this.getLog("rowFilter\u6ca1\u6709\u8bbe\u7f6e\u9875\u7b7e\u8fc7\u6ee4\u6761\u4ef6\u5904\u7406\uff0c\u9875\u7b7e\u4fe1\u606f\u4e3a\uff1a" + this.TabType.getTitle()));
        }
        if (this.filterType == null) {
            this.filterType = InputDataQueryFilterType.ALL;
            logger.debug(this.getLog("\u6ca1\u6709\u6570\u636e\u5c42\u7ea7\u8fc7\u6ee4\u6761\u4ef6"));
        }
        return rowFilter;
    }

    public String getMainTable() {
        return this.mainTableName;
    }

    public String getOrgTableName() {
        return this.orgTableName;
    }

    public GcOrgCacheVO getInitOrg() {
        return this.initOrg;
    }

    public List<GcOrgCacheVO> getInitOrgList() {
        return this.initOrgList;
    }

    public GcOrgCacheVO getSelectOrg() {
        return this.selectOrg;
    }

    public GcBaseData getInitCurrency() {
        return this.initCurrency;
    }

    public YearPeriodDO getYearPeriod() {
        return this.yearPeriod;
    }

    public EntitiesOrgMode getOrgMode() {
        return this.orgMode;
    }

    public String getGcOrgType() {
        return this.gcOrgType;
    }

    public String getCurrencyCode() {
        return this.initCurrency == null ? CURRENCY_CODE : this.initCurrency.getCode();
    }

    public boolean isGcQuery() {
        return this.isGcQuery;
    }

    public InnerTableTabsType getTabType() {
        return this.TabType;
    }

    public TaskDefine getTask() {
        return this.task;
    }

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public DataRegionDefine getRegionDefine() {
        return this.regionDefine;
    }

    public FormDefine getForm() {
        return this.form;
    }

    public DimensionValueSet getMasterKeys() {
        DimensionValueSet set = new DimensionValueSet();
        set.assign(this.masterKeys);
        return set;
    }

    public QueryParam getQueryParam() {
        return this.queryParam;
    }

    public InputDataQueryFilterType getFilterType() {
        return this.filterType;
    }

    public String getLog(String msg) {
        String logs = " \u62a5\u8868\u65b9\u6848ID:%1$s,\u533a\u57dfID:%2$s,\u8868\u5355ID:%3$s,\u7ef4\u5ea6\u4fe1\u606f:[%4$s];\u9519\u8bef\u4fe1\u606f\u4e3a:%5$s";
        return String.format(logs, this.formScheme.getKey(), this.regionDefine == null ? "null" : this.regionDefine.getKey(), this.form == null ? "null" : this.form.getKey(), this.masterKeys == null ? "null" : this.masterKeys.toString(), msg);
    }

    private void initOrgList(GcOrgCenterService tool, Object orgCodeObj) {
        this.initOrgList = new ArrayList<GcOrgCacheVO>();
        List orgCodes = (List)orgCodeObj;
        if (CollectionUtils.isEmpty(orgCodes)) {
            logger.error(this.getLog("\u7ec4\u7ec7\u673a\u6784\u7ef4\u5ea6\u503c\u4e3a\u7a7a"));
            return;
        }
        this.orgMode = EntitiesOrgMode.SINGLE;
        for (String orgCode : orgCodes) {
            GcOrgCacheVO orgCache = tool.getOrgByCode(orgCode);
            if (orgCache != null) {
                if (!orgCache.isLeaf()) {
                    logger.error(this.getLog("\u8be5\u64cd\u4f5c\u4e0d\u652f\u6301\u591a\u7ef4\u5ea6\u7ec4\u7ec7\u673a\u6784ID" + orgCode + "\u7ec4\u7ec7\u673a\u6784\u662f\u5408\u5e76\u6237\u5355\u4f4d"));
                    continue;
                }
                if (orgCache.getOrgKind() == GcOrgKindEnum.DIFFERENCE) {
                    logger.error(this.getLog("\u8be5\u64cd\u4f5c\u4e0d\u652f\u6301\u7ec4\u7ec7\u673a\u6784ID" + orgCode + "\u7ec4\u7ec7\u673a\u6784\u662f\u5dee\u989d\u6237\u5355\u4f4d"));
                    continue;
                }
                logger.debug(this.getLog(",\u7ec4\u7ec7\u673a\u6784CODE" + orgCode + "\u7ec4\u7ec7\u673a\u6784\u662f\u5355\u6237\u5355\u4f4d"));
                this.initOrgList.add(orgCache);
                continue;
            }
            logger.error(this.getLog("\u627e\u4e0d\u5230\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784,\u7ec4\u7ec7\u673a\u6784ID" + orgCode + " \u7ec4\u7ec7\u673a\u6784" + this.getOrgTableName() + "  \u65f6\u671f" + this.yearPeriod.toString()));
        }
    }

    private void initOneOrg(GcOrgCenterService tool, Object orgCodeObj) {
        String orgCode = StringUtils.toViewString((Object)orgCodeObj);
        this.selectOrg = tool.getOrgByCode(orgCode);
        if (this.selectOrg != null) {
            if (!this.selectOrg.isLeaf()) {
                logger.debug(this.getLog(",\u7ec4\u7ec7\u673a\u6784ID" + orgCode + "\u7ec4\u7ec7\u673a\u6784\u662f\u5408\u5e76\u6237\u5355\u4f4d"));
                this.initOrg = this.selectOrg;
                this.orgMode = EntitiesOrgMode.UNIONORG;
            } else if (this.selectOrg.getOrgKind() == GcOrgKindEnum.DIFFERENCE) {
                logger.debug(this.getLog(",\u7ec4\u7ec7\u673a\u6784ID" + orgCode + "\u7ec4\u7ec7\u673a\u6784\u662f\u5dee\u989d\u6237\u5355\u4f4d"));
                this.initOrg = tool.getMergeUnitByDifference(orgCode);
                this.orgMode = EntitiesOrgMode.DIFFERENCE;
            } else {
                logger.debug(this.getLog(",\u7ec4\u7ec7\u673a\u6784ID" + orgCode + "\u7ec4\u7ec7\u673a\u6784\u662f\u5355\u6237\u5355\u4f4d"));
                this.initOrg = this.selectOrg;
                this.orgMode = EntitiesOrgMode.SINGLE;
            }
        } else {
            logger.error(this.getLog("\u627e\u4e0d\u5230\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784,\u7ec4\u7ec7\u673a\u6784ID" + orgCode + " \u7ec4\u7ec7\u673a\u6784" + this.getOrgTableName() + "  \u65f6\u671f" + this.yearPeriod.toString()));
        }
    }

    private InnerTableTabsType getInnerTableTabsType() {
        if (Objects.isNull(this.getRegionDefine()) || StringUtils.isEmpty((String)this.getRegionDefine().getKey())) {
            logger.debug("\u533a\u57df\u4fe1\u606f\u4e3a\u7a7a\u65f6\uff0c\u53d6\u660e\u7ec6\u9875\u7b7e");
            return InnerTableTabsType.DETAILDATA;
        }
        String regionKey = this.getRegionDefine().getKey();
        IJtableParamService entityControlService = (IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class);
        List regionTabs = entityControlService.getRegionTabs(regionKey);
        if (CollectionUtils.isEmpty(regionTabs)) {
            logger.debug("\u533a\u57df\u4e2d\u672a\u8bbe\u7f6e\u9875\u7b7e\u65f6\uff0c\u53d6\u660e\u7ec6\u9875\u7b7e");
            return InnerTableTabsType.DETAILDATA;
        }
        RegionTab regionTab = (RegionTab)regionTabs.stream().findFirst().get();
        String regionTabFilter = regionTab.getFilter();
        String pattern = "#([\\S]+)#";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(regionTabFilter);
        InnerTableTabsType tableTabsType = null;
        while (matcher.find()) {
            String tabFilter = matcher.group(1);
            tableTabsType = InnerTableTabsType.findTabsType(tabFilter);
        }
        if (Objects.isNull(tableTabsType)) {
            logger.debug("\u89e3\u6790\u533a\u57df\u4e2d\u9875\u7b7e\u540e\u4e0d\u5339\u914d\u65f6\uff0c\u53d6\u660e\u7ec6\u9875\u7b7e");
            return InnerTableTabsType.DETAILDATA;
        }
        return tableTabsType;
    }
}

