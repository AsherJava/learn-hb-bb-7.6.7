/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.Base64
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.authz2.SecurityLevel
 *  com.jiuqi.np.authz2.impl.service.SystemIdentityServiceUserMode
 *  com.jiuqi.np.authz2.service.SecurityLevelService
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionType
 *  com.jiuqi.nr.dataentry.internal.service.util.DateTimeUtil
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService
 *  com.jiuqi.nr.designer.web.facade.UReportTaskL
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.graphics.Point
 *  io.netty.util.internal.StringUtil
 *  javax.annotation.Resource
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 *  org.springframework.web.util.HtmlUtils
 */
package com.jiuqi.nr.analysisreport.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.Base64;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.authz2.SecurityLevel;
import com.jiuqi.np.authz2.impl.service.SystemIdentityServiceUserMode;
import com.jiuqi.np.authz2.service.SecurityLevelService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.analysisreport.async.CatalogGenerateThread;
import com.jiuqi.nr.analysisreport.authority.AnalysisReportAuthorityProvider;
import com.jiuqi.nr.analysisreport.authority.common.AnalysisReportResourceType;
import com.jiuqi.nr.analysisreport.biservice.bi.HttpUtils;
import com.jiuqi.nr.analysisreport.biservice.bi.IBIIntegrationServices;
import com.jiuqi.nr.analysisreport.biservice.chart.ChartTreeNode;
import com.jiuqi.nr.analysisreport.chapter.bean.CatalogVo;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportCatalogItem;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportVersionExtendData;
import com.jiuqi.nr.analysisreport.chapter.dao.IReportVersionExtendDataDao;
import com.jiuqi.nr.analysisreport.chapter.service.IChapterService;
import com.jiuqi.nr.analysisreport.chapter.util.TransUtil;
import com.jiuqi.nr.analysisreport.common.NrAnalysisErrorEnum;
import com.jiuqi.nr.analysisreport.facade.AnalyBigdataDefine;
import com.jiuqi.nr.analysisreport.facade.AnalyBigdataDefineImpl;
import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefineImpl;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.facade.DimensionConfigObj;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.helper.CatalogGenHelper;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportDefineImpl;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportGroupDefineImpl;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import com.jiuqi.nr.analysisreport.internal.service.AnalyBigDataService;
import com.jiuqi.nr.analysisreport.internal.service.AnalysisReportGroupService;
import com.jiuqi.nr.analysisreport.internal.service.AnalysisReportService;
import com.jiuqi.nr.analysisreport.internal.service.ReportVersionExtendDataService;
import com.jiuqi.nr.analysisreport.internal.service.impl.AnalysisReportEntityServiceImpl;
import com.jiuqi.nr.analysisreport.securitylevel.SecurityLevelHelper;
import com.jiuqi.nr.analysisreport.service.SaveAnalysis;
import com.jiuqi.nr.analysisreport.support.IExprParser;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.utils.BorderStyle;
import com.jiuqi.nr.analysisreport.utils.CatalogCacheUtil;
import com.jiuqi.nr.analysisreport.utils.CheckPermissionUtil;
import com.jiuqi.nr.analysisreport.utils.Col;
import com.jiuqi.nr.analysisreport.utils.ConversionDPI;
import com.jiuqi.nr.analysisreport.utils.Row;
import com.jiuqi.nr.analysisreport.utils.SerializeUtils;
import com.jiuqi.nr.analysisreport.utils.Table;
import com.jiuqi.nr.analysisreport.variable.FormVariable;
import com.jiuqi.nr.analysisreport.variable.FormulaVariable;
import com.jiuqi.nr.analysisreport.variable.TempDesignTaskDefine;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO;
import com.jiuqi.nr.analysisreport.vo.ReportVersionGeneratorVo;
import com.jiuqi.nr.analysisreport.vo.ReportVersionVo;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionType;
import com.jiuqi.nr.dataentry.internal.service.util.DateTimeUtil;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.web.facade.UReportTaskL;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.graphics.Point;
import io.netty.util.internal.StringUtil;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.swing.JLabel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.HtmlUtils;

@Service
public class SaveAnalysisImpl
implements SaveAnalysis {
    private static final Logger log = LoggerFactory.getLogger(SaveAnalysisImpl.class);
    @Autowired
    AnalysisHelper analysisHelper;
    @Autowired
    private AnalyBigDataService analyBigDataService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Resource
    IDataAccessProvider dataAccessProvider;
    @Resource
    AnalysisReportEntityServiceImpl analysisReportEntityService;
    @Autowired
    private AnalysisReportAuthorityProvider authProvider;
    @Resource
    private ISecretLevelService iSecretLevelService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    IBIIntegrationServices biIntegrationServices;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Resource
    private SecurityLevelService securityLevelService;
    @Autowired
    private SystemIdentityServiceUserMode systemIdentityService;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    private SecurityLevelHelper securityLevelHelper;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Lazy
    @Autowired(required=false)
    private List<IExprParser> exprParsers;
    @Autowired
    private IChapterService chapterService;
    @Autowired
    private AnalysisReportGroupService analysisReportGroupService;
    @Autowired
    private AnalysisReportService analysisReportService;
    @Autowired
    private IReportVersionExtendDataDao versionExtendDao;
    @Autowired
    private NpApplication npApplication;
    public static String PREFIX = "&lt;expr&gt;([";
    public static String SUFFIX = "])&lt;/expr&gt;";
    public static String INDICATORS_PREFIX = "IN";
    public static String REPORT_PREFIX = "RE";
    public static boolean COLOR_STYLE = false;
    private NedisCacheManager cacheManager;
    @Autowired
    private ReportVersionExtendDataService versionExtendDataService;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    private static final String updateModelLogMsg = "\u4fee\u6539%s\uff1a%s,\u4fee\u6539\u4eba\uff1a%s,\u4fee\u6539\u524d\u6a21\u677f\u6570\u636e\u957f\u5ea6%s,\u4fee\u6539\u540e\u6a21\u677f\u6570\u636e\u957f\u5ea6%s";

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    @Override
    public ReturnObject queryAllTemplates() throws Exception {
        try {
            List<AnalysisTemp> analysisTemps = this.analysisReportDefineToAnalysisTemp(this.analysisHelper.getList());
            analysisTemps.stream().forEach(e -> e.setData(""));
            return AnaUtils.initReturn(true, "\u67e5\u8be2\u6240\u6709\u6a21\u677f\u6570\u636e\u6210\u529f", analysisTemps);
        }
        catch (Exception e2) {
            return AnaUtils.initReturn(false, "\u67e5\u8be2\u6240\u6709\u6a21\u677f\u6570\u636e\u5931\u8d25", null);
        }
    }

    @Override
    public String getList() throws Exception {
        List<AnalysisTemp> analysisTemps = this.analysisReportDefineToAnalysisTemp(this.analysisHelper.getList());
        JSONArray result = new JSONArray();
        if (analysisTemps != null) {
            for (AnalysisTemp analysisTemp : analysisTemps) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("key", analysisTemp.getKey());
                map.put("code", analysisTemp.getCode());
                map.put("title", analysisTemp.getTitle());
                result.put(map);
            }
        }
        return result.toString();
    }

    @Override
    public ReturnObject getListByGroupKey(String key) throws Exception {
        List<AnalysisReportDefine> list = this.analysisHelper.getListByGroupKey(key);
        List<AnalysisReportGroupDefine> groupByParent = this.analysisHelper.getGroupByParent(key);
        List<AnalysisTemp> analysisTemps = this.analysisReportGroupDefineToAnalysisTemp(groupByParent);
        List<AnalysisTemp> analysisTemps1 = this.analysisReportDefineToAnalysisTemp(list);
        int userSecurityLevel = Integer.MAX_VALUE;
        userSecurityLevel = this.getUserSercurityLevel(userSecurityLevel);
        Iterator<AnalysisTemp> iterator = analysisTemps1.iterator();
        while (iterator.hasNext()) {
            String securityLevel = iterator.next().getSecurityLevel();
            if (StringUtil.isNullOrEmpty((String)securityLevel) || userSecurityLevel >= Integer.parseInt(securityLevel)) continue;
            iterator.remove();
        }
        for (AnalysisTemp analysisTemp : analysisTemps1) {
            analysisTemp.setData("");
            analysisTemps.add(analysisTemp);
        }
        return AnaUtils.initReturn(analysisTemps);
    }

    @Override
    public ReturnObject getAllTemplatesByGroupKey(String groupKey) throws Exception {
        List<AnalysisTemp> allTemplateByGroup = this.getAllTemplateByGroup(groupKey);
        return AnaUtils.initReturn(true, "\u83b7\u53d6\u5206\u7ec4\u4e0b\u6240\u6709\u62a5\u544a\u6a21\u7248\u6210\u529f", allTemplateByGroup);
    }

    public List<AnalysisTemp> getAllTemplateByGroup(String groupKey) throws Exception {
        List<AnalysisReportGroupDefine> subGroups;
        AnalysisReportGroupDefine groupDefine = this.analysisHelper.getGroupByKey(groupKey);
        ArrayList<AnalysisTemp> templates = new ArrayList<AnalysisTemp>();
        List<AnalysisReportDefine> subTemplates = this.analysisHelper.getListByGroupKey(groupKey);
        if (!CollectionUtils.isEmpty(subTemplates)) {
            templates.addAll(this.analysisReportDefineToAnalysisTemp(subTemplates, groupDefine.getTitle()));
        }
        if (CollectionUtils.isEmpty(subGroups = this.analysisHelper.getGroupByParent(groupKey))) {
            return templates;
        }
        for (int i = 0; i < subGroups.size(); ++i) {
            templates.addAll(this.getAllTemplateByGroup(subGroups.get(i).getKey()));
        }
        return templates;
    }

    @Override
    public List<AnalysisTemp> getTempListByGroupKey(String key) throws Exception {
        List<AnalysisReportDefine> list = this.analysisHelper.getListByGroupKey(key);
        List<AnalysisReportGroupDefine> groupByParent = this.analysisHelper.getGroupByParent(key);
        List<AnalysisTemp> analysisTemps = this.analysisReportGroupDefineToAnalysisTemp(groupByParent);
        List<AnalysisTemp> analysisTemps1 = this.analysisReportDefineToAnalysisTemp(list);
        for (AnalysisTemp analysisTemp : analysisTemps1) {
            if ("group".equals(analysisTemp.getAntype())) {
                analysisTemps.addAll(this.getTempListByGroupKey(analysisTemp.getKey()));
                continue;
            }
            analysisTemps.add(analysisTemp);
        }
        return analysisTemps;
    }

    @Override
    public ReturnObject getListByKey(String key) throws Exception {
        List<AnalysisTemp> resultList;
        final AnalysisReportDefine listByKey = this.analysisHelper.getListByKey(key);
        boolean canQueryModal = this.authProvider.canReadModal(key, AnalysisReportResourceType.TEMPLATE);
        if (!canQueryModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_401);
        }
        boolean canWriteModal = this.authProvider.canWriteModal(key, AnalysisReportResourceType.TEMPLATE);
        boolean isSecurityLevelEnabled = this.securityLevelService.isSecurityLevelEnabled();
        if (isSecurityLevelEnabled && StringUtil.isNullOrEmpty((String)listByKey.getSecurityLevel())) {
            SecurityLevel minSystemSecurityLevel = this.securityLevelHelper.getMinSystemSecurityLevel();
            listByKey.setSecurityLevel(minSystemSecurityLevel.getName());
        }
        AnalysisTemp result = (resultList = this.analysisReportDefineToAnalysisTemp((List<AnalysisReportDefine>)new ArrayList<AnalysisReportDefine>(){
            {
                this.add(listByKey);
            }
        })) != null && resultList.size() > 0 ? resultList.get(0) : null;
        HashMap<String, Object> obj = new HashMap<String, Object>();
        obj.put("readOnly", !canWriteModal && canQueryModal);
        obj.put("result", result);
        return AnaUtils.initReturn(obj);
    }

    @Override
    public List<AnalysisTemp> getListByKeys(List<String> keys) throws Exception {
        ArrayList<AnalysisReportDefine> listByKeys = new ArrayList<AnalysisReportDefine>();
        for (String key : keys) {
            AnalysisReportDefine listByKey = this.analysisHelper.getListByKey(key);
            boolean canQueryModal = this.authProvider.canReadModal(key, AnalysisReportResourceType.TEMPLATE);
            if (!canQueryModal) {
                throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_401);
            }
            listByKeys.add(listByKey);
        }
        List<AnalysisTemp> resultList = this.analysisReportDefineToAnalysisTemp(listByKeys);
        return resultList;
    }

    @Override
    public ReturnObject getAnalysisModelFullPathByKey(String key) throws Exception {
        final AnalysisReportDefine listByKey = this.analysisHelper.getListByKey(key);
        boolean canQueryModal = this.authProvider.canReadModal(key, AnalysisReportResourceType.TEMPLATE);
        if (!canQueryModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_401);
        }
        List<AnalysisTemp> resultList = this.analysisReportDefineToAnalysisTemp((List<AnalysisReportDefine>)new ArrayList<AnalysisReportDefine>(){
            {
                this.add(listByKey);
            }
        });
        AnalysisTemp result = resultList != null && resultList.size() > 0 ? resultList.get(0) : null;
        HashMap<String, String> obj = new HashMap<String, String>();
        try {
            AnalysisReportGroupDefine groupDefine;
            String parent = result.getGroupKey();
            String fullPath = "/" + result.getTitle();
            while (!"".equals(parent) && !"0".equals(parent) && (groupDefine = this.analysisHelper.getGroupByKey(parent)) != null) {
                fullPath = "/" + groupDefine.getTitle() + fullPath;
                parent = groupDefine.getParentgroup();
            }
            obj.put("fullPath", fullPath);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return AnaUtils.initReturn(obj);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<AnalysisTemp> analysisReportDefineToAnalysisTemp(List<AnalysisReportDefine> list, String groupTitle) throws Exception {
        ArrayList<AnalysisTemp> resultList = new ArrayList<AnalysisTemp>();
        HashMap<String, String> groupTitles = new HashMap<String, String>();
        for (AnalysisReportDefine define : list) {
            String modelKey = define.getKey();
            boolean canQueryModal = this.authProvider.canReadModal(modelKey, AnalysisReportResourceType.TEMPLATE);
            if (!canQueryModal) continue;
            if (groupTitle == null && !groupTitles.containsKey(define.getGroupKey())) {
                AnalysisReportGroupDefine group = this.analysisHelper.getGroupByKey(define.getGroupKey());
                groupTitles.put(define.getGroupKey(), group.getTitle());
            }
            AnalysisTemp temp = new AnalysisTemp();
            temp.setKey(modelKey);
            temp.setGroupKey(define.getGroupKey());
            temp.setGroupTitle(groupTitle == null ? (String)groupTitles.get(define.getGroupKey()) : groupTitle);
            temp.setTitle(define.getTitle());
            temp.setDescription(define.getDescription());
            temp.setData(define.getData() == null ? null : define.getData());
            temp.setCreateuser(define.getCreateuser());
            temp.setModifyuser(define.getModifyuser());
            temp.setOrder(define.getOrder());
            temp.setAntype("model");
            temp.setSecurityLevel(define.getSecurityLevel());
            temp.setSecurityTitle(this.iSecretLevelService.getSecretLevelItem(define.getSecurityLevel()).getTitle());
            temp.setPeriodOffset(define.getPeriodOffset());
            if (!StringUtil.isNullOrEmpty((String)define.getPrintData())) {
                try {
                    JSONObject printData = new JSONObject(define.getPrintData());
                    JSONObject template = null;
                    switch (printData.get("template").getClass().getName()) {
                        case "org.json.JSONObject": {
                            template = printData.getJSONObject("template");
                            break;
                        }
                        case "java.lang.String": {
                            template = new JSONObject(printData.getString("template"));
                            break;
                        }
                        default: {
                            template = new JSONObject(printData.getString("template"));
                        }
                    }
                    BigDecimal b = new BigDecimal(10);
                    String marginLeft = String.valueOf(template.get("marginLeft"));
                    String marginTop = String.valueOf(template.get("marginTop"));
                    String marginBottom = template.has("marginBottom") ? String.valueOf(template.get("marginBottom")) : String.valueOf(template.get("marginTop"));
                    String paperWidth = String.valueOf(template.get("paperWidth"));
                    String paperHeight = String.valueOf(template.get("paperHeight"));
                    if (!StringUtil.isNullOrEmpty((String)marginLeft)) {
                        template.put("marginLeft", new BigDecimal(marginLeft).divide(b).doubleValue());
                    }
                    if (!StringUtil.isNullOrEmpty((String)marginTop)) {
                        template.put("marginTop", new BigDecimal(marginTop).divide(b).doubleValue());
                    }
                    if (!StringUtil.isNullOrEmpty((String)marginBottom)) {
                        template.put("marginBottom", new BigDecimal(marginBottom).divide(b).doubleValue());
                    }
                    if (!StringUtil.isNullOrEmpty((String)paperWidth)) {
                        template.put("paperWidth", new BigDecimal(paperWidth).divide(b).doubleValue());
                    }
                    if (!StringUtil.isNullOrEmpty((String)paperHeight)) {
                        template.put("paperHeight", new BigDecimal(paperHeight).divide(b).doubleValue());
                    }
                    if (template.has("marginRight")) {
                        template.remove("marginRight");
                    }
                    printData.put("template", (Object)JSONObject.valueToString((Object)template));
                    define.setPrintData(JSONObject.valueToString((Object)printData));
                }
                catch (Exception printData) {
                    // empty catch block
                }
            }
            temp.setPrintData(define.getPrintData());
            try {
                temp.setDimension(SerializeUtils.jsonDeserialize(define.getDimension(), DimensionConfigObj.class));
            }
            catch (JQException printData) {
            }
            finally {
                if (temp.getDimension() == null) {
                    temp.setDimension(new DimensionConfigObj());
                }
            }
            if (define.getUpdateTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                temp.setStringdate(sdf.format(define.getUpdateTime()));
            } else {
                temp.setStringdate("");
            }
            DimensionObj dimConfig = null;
            for (DimensionObj obj : temp.getDimension().getSrcDims()) {
                if (obj.getType().getValue() != 2) continue;
                dimConfig = obj;
                break;
            }
            if (dimConfig != null) {
                temp.setShowPeriod(this.dealPeriodByOffset(dimConfig, define.getPeriodOffset()));
            }
            resultList.add(temp);
        }
        return resultList;
    }

    private List<AnalysisTemp> analysisReportDefineToAnalysisTemp(List<AnalysisReportDefine> list) throws Exception {
        return this.analysisReportDefineToAnalysisTemp(list, null);
    }

    private String dealPeriodByOffset(DimensionObj dimConfig, int offset) {
        Date date;
        Object beginPeriodModify = null;
        Date dateAfterFormat = date = DateTimeUtil.getDay();
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(dimConfig.getKey());
        if (dimConfig.getConfig().getPeriodType() != PeriodType.CUSTOM.type()) {
            beginPeriodModify = PeriodUtils.getPeriodFromDate((int)dimConfig.getConfig().getPeriodType(), (Date)dateAfterFormat);
            String offsetModify = beginPeriodModify;
            if (offset != 0) {
                PeriodModifier periodModifier = new PeriodModifier();
                periodModifier.setPeriodModifier(offset);
                offsetModify = periodProvider.modify((String)beginPeriodModify, periodModifier);
            }
            if (PeriodUtils.comparePeriod((String)offsetModify, (String)dimConfig.getConfig().getFromPeriod()) >= 0 && PeriodUtils.comparePeriod((String)offsetModify, (String)dimConfig.getConfig().getToPeriod()) <= 0) {
                beginPeriodModify = offsetModify;
            } else if (PeriodUtils.comparePeriod((String)beginPeriodModify, (String)dimConfig.getConfig().getFromPeriod()) < 0 || PeriodUtils.comparePeriod((String)beginPeriodModify, (String)dimConfig.getConfig().getToPeriod()) > 0) {
                beginPeriodModify = dimConfig.getConfig().getToPeriod();
            }
        } else if (dimConfig.getConfig().getUnitKeys().size() > 0) {
            for (String periodKey : dimConfig.getConfig().getUnitKeys()) {
                Object periodRegion = new Date[]{};
                try {
                    periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dimConfig.getKey()).getPeriodDateRegion(periodKey);
                }
                catch (java.text.ParseException e) {
                    log.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                }
                if (!DateTimeUtil.isEffectiveDate((Date)dateAfterFormat, (Date)periodRegion[0], (Date)periodRegion[1])) continue;
                beginPeriodModify = periodKey;
                break;
            }
            if (beginPeriodModify != null) {
                if (offset != 0) {
                    Object offsetModify = beginPeriodModify;
                    PeriodModifier periodModifier = new PeriodModifier();
                    periodModifier.setPeriodModifier(offset);
                    offsetModify = periodProvider.modify((String)beginPeriodModify, periodModifier);
                    for (String periodKey : dimConfig.getConfig().getUnitKeys()) {
                        if (PeriodUtils.comparePeriod((String)offsetModify, (String)periodKey) != 0) continue;
                        beginPeriodModify = offsetModify;
                        break;
                    }
                }
            } else {
                String maxPeriod = dimConfig.getConfig().getUnitKeys().get(0);
                for (String periodKey : dimConfig.getConfig().getUnitKeys()) {
                    if (PeriodUtils.comparePeriod((String)periodKey, (String)maxPeriod) <= 0) continue;
                    maxPeriod = periodKey;
                }
                beginPeriodModify = maxPeriod;
            }
        }
        return beginPeriodModify;
    }

    private List<AnalysisTemp> analysisReportGroupDefineToAnalysisTemp(List<AnalysisReportGroupDefine> list) {
        ArrayList<AnalysisTemp> resultList = new ArrayList<AnalysisTemp>();
        for (AnalysisReportGroupDefine define : list) {
            String modelKey = define.getKey();
            boolean canQueryModal = this.authProvider.canReadModal(modelKey, AnalysisReportResourceType.GROUP);
            if (!canQueryModal) continue;
            AnalysisTemp temp = new AnalysisTemp();
            temp.setKey(define.getKey());
            temp.setParentgroup(define.getParentgroup());
            temp.setTitle(define.getTitle());
            temp.setDescription(define.getDescription());
            temp.setOrder(define.getOrder());
            temp.setAntype("group");
            resultList.add(temp);
        }
        return resultList;
    }

    @Override
    public ReturnObject insertModel(JsonNode jsonNode) throws Exception {
        String groupKey = jsonNode.get("groupKey").textValue();
        String userId = NpContextHolder.getContext().getUserId();
        boolean canEditGroup = this.authProvider.canWriteModal(groupKey, AnalysisReportResourceType.GROUP);
        if (!canEditGroup) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_404);
        }
        String newModelKey = jsonNode.get("key") != null && !"".equals(jsonNode.get("key").textValue()) ? jsonNode.get("key").textValue() : UUIDUtils.getKey();
        AnalysisReportDefineImpl define = new AnalysisReportDefineImpl();
        define.setKey(newModelKey);
        define.setGroupKey(groupKey);
        define.setTitle(HtmlUtils.htmlEscape((String)jsonNode.get("title").textValue()));
        define.setDescription(jsonNode.get("description") != null ? HtmlUtils.htmlEscape((String)jsonNode.get("description").textValue()) : "");
        define.setCreateuser(userId);
        define.setPeriodOffset(jsonNode.get("periodOffset") != null ? Integer.parseInt(jsonNode.get("periodOffset").toString()) : 0);
        if (jsonNode.get("data") != null) {
            define.setData(jsonNode.get("data").textValue());
        }
        if (jsonNode.get("printData") != null) {
            String printData = "{}";
            JSONObject updatePrintData = new JSONObject(jsonNode.get("printData").asText());
            String extendData = updatePrintData.has("info") ? updatePrintData.getJSONObject("info").toString() : "{}";
            String dataType = updatePrintData.has("type") ? updatePrintData.getString("type") : "template";
            printData = SaveAnalysisImpl.UpdateExtendData(printData, extendData, dataType);
            define.setPrintData(printData);
        }
        if (jsonNode.get("dimension") != null) {
            define.setDimension(jsonNode.get("dimension") != null ? jsonNode.get("dimension").textValue() : "");
        }
        if (this.securityLevelService.isSecurityLevelEnabled() && null != jsonNode.get("securityLevel") && !StringUtil.isNullOrEmpty((String)jsonNode.get("securityLevel").toString())) {
            define.setSecurityLevel(jsonNode.get("securityLevel").textValue());
        }
        this.analysisHelper.insertModel(define);
        this.chapterService.insertDefaultChapter(newModelKey);
        this.authProvider.grantAllPrivileges(newModelKey);
        AnalysisReportLogHelper.log("\u65b0\u5efa\u6a21\u677f", "\u65b0\u5efa\u6a21\u677f\uff1a" + jsonNode.get("title").textValue(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u65b0\u5efa\u6a21\u677f\u6210\u529f");
    }

    @Override
    public ReturnObject updateModel(JsonNode jsonNode) throws Exception {
        String modelKey = jsonNode.get("key").textValue();
        boolean canUpdateModal = this.authProvider.canWriteModal(modelKey, AnalysisReportResourceType.TEMPLATE);
        if (!canUpdateModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_402);
        }
        String key = jsonNode.get("key").textValue();
        String title = HtmlUtils.htmlEscape((String)jsonNode.get("title").textValue());
        String parentGroup = jsonNode.get("parentgroup").textValue();
        String userId = NpContextHolder.getContext().getUserId();
        List<AnalysisReportDefine> models = this.analysisHelper.getListByGroupKey(parentGroup);
        for (AnalysisReportDefine model : models) {
            if (key.equals(model.getKey()) || !title.equals(model.getTitle())) continue;
            return AnaUtils.initReturn(false, "\u4fdd\u5b58\u5931\u8d25\uff0c\u5b58\u5728\u76f8\u540c\u540d\u79f0\u7684\u6a21\u677f");
        }
        AnalysisReportDefine model = this.analysisHelper.getListByKey(key);
        int orginalModelDataLength = StringUtil.length((String)model.getData());
        AnalysisReportDefineImpl define = new AnalysisReportDefineImpl();
        String arcKey = null;
        if (jsonNode.get("arcKey") != null && (arcKey = jsonNode.get("arcKey").textValue()) != null) {
            ReportChapterDefine chapter = this.chapterService.getChapterById(arcKey);
            int orginalDataLength = StringUtil.length((String)chapter.getArcData());
            chapter.setArcData(jsonNode.get("data").textValue());
            chapter.setArcUpdatetime(new Date());
            if (StringUtils.isEmpty((String)chapter.getArcData())) {
                chapter.setCatalog(null);
            }
            this.chapterService.updateChapter(TransUtil.transDtoToVo(chapter));
            if (!StringUtils.isEmpty((String)chapter.getArcData())) {
                this.npApplication.asyncRun((Runnable)new CatalogGenerateThread(chapter));
            }
            define.setAtCatalogUpdatetime(null);
            String logMsg = String.format(updateModelLogMsg, "\u7ae0\u8282", model.getTitle() + "-" + chapter.getArcName(), userId, orginalDataLength, StringUtil.length((String)chapter.getArcData()));
            AnalysisReportLogHelper.log("\u66f4\u65b0\u7ae0\u8282", logMsg, AnalysisReportLogHelper.LOGLEVEL_INFO);
        }
        define.setModifyuser(userId);
        define.setKey(modelKey);
        define.setGroupKey(jsonNode.get("groupKey").textValue());
        define.setTitle(jsonNode.get("title").textValue());
        define.setPeriodOffset(jsonNode.get("periodOffset") != null ? Integer.parseInt(jsonNode.get("periodOffset").toString()) : 0);
        if (null != jsonNode.get("description") && null != jsonNode.get("description").textValue()) {
            define.setDescription(HtmlUtils.htmlEscape((String)jsonNode.get("description").textValue()));
        }
        if (null != jsonNode.get("createuser") && null != jsonNode.get("createuser").textValue()) {
            define.setCreateuser(jsonNode.get("createuser").textValue());
        }
        if (null != jsonNode.get("order") && null != jsonNode.get("order").textValue()) {
            define.setOrder(jsonNode.get("order").textValue());
        } else if (model != null && !StringUtil.isNullOrEmpty((String)model.getOrder())) {
            define.setOrder(model.getOrder());
        } else {
            define.setOrder(OrderGenerator.newOrder());
        }
        define.setData(jsonNode.get("data").textValue());
        AnalysisReportDefine listByKey = this.analysisHelper.getListByKey(jsonNode.get("key").textValue());
        String printData = listByKey.getPrintData();
        if (null != jsonNode.get("printData") && null != jsonNode.get("printData").textValue()) {
            JSONObject updatePrintData = new JSONObject(jsonNode.get("printData").asText());
            String extendData = updatePrintData.has("info") ? updatePrintData.getJSONObject("info").toString() : (updatePrintData.has("template") ? updatePrintData.getString("template") : "{}");
            String dataType = updatePrintData.has("type") ? updatePrintData.getString("type") : "template";
            printData = SaveAnalysisImpl.UpdateExtendData(printData, extendData, dataType);
        }
        define.setPrintData(printData);
        if (null != jsonNode.get("dimension") && !StringUtil.isNullOrEmpty((String)jsonNode.get("dimension").toString())) {
            String dimension = "";
            dimension = jsonNode.get("dimension").asText() == "" || jsonNode.get("dimension").asText() == null ? jsonNode.get("dimension").toString() : jsonNode.get("dimension").asText();
            define.setDimension(dimension);
        } else {
            define.setDimension(listByKey.getDimension());
        }
        if (this.securityLevelService.isSecurityLevelEnabled() && null != jsonNode.get("securityLevel") && !StringUtil.isNullOrEmpty((String)jsonNode.get("securityLevel").toString())) {
            define.setSecurityLevel(jsonNode.get("securityLevel").textValue());
        }
        define.setUpdateTime(new Date());
        this.analysisHelper.updateModel(define);
        String modelLogMsg = StringUtils.isNotEmpty((String)arcKey) ? "\u4fee\u6539\u6a21\u677f" + define.getTitle() : String.format(updateModelLogMsg, "\u6a21\u677f", model.getTitle(), userId, orginalModelDataLength, StringUtil.length((String)model.getData()));
        AnalysisReportLogHelper.log("\u4fee\u6539\u6a21\u677f", modelLogMsg, AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u4fee\u6539\u6a21\u677f\u6210\u529f");
    }

    @Override
    public ReturnObject deleteModel(String key) throws Exception {
        boolean canDeleteModal;
        boolean bl = canDeleteModal = this.authProvider.canDeleteModal(key, AnalysisReportResourceType.TEMPLATE) || this.authProvider.canWriteModal(key, AnalysisReportResourceType.TEMPLATE);
        if (!canDeleteModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_403);
        }
        AnalysisReportDefine report = this.analysisHelper.getListByKey(key);
        this.analysisHelper.deleteModel(key);
        AnalysisReportLogHelper.log("\u5220\u9664\u6a21\u677f", "\u5220\u9664\u6a21\u677f\uff1a" + report.getTitle(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u5220\u9664\u6a21\u677f\u6210\u529f");
    }

    private void deleteAllDataByModel(String key) throws Exception {
        List<ReportChapterDefine> analysisChapterDefines = this.chapterService.queryChapterByModelId(key);
        this.chapterService.deleteChapterByModelId(key);
        List<AnalyVersionDefine> versionList = this.analysisHelper.getVersionList(key);
        for (int i = 0; i < versionList.size(); ++i) {
            this.analyBigDataService.deleteBigDataByKey(versionList.get(i).getBigDataKey());
        }
        this.analysisHelper.deleteVersionByModelKey(key);
    }

    @Override
    public ReturnObject getGroupList() throws Exception {
        List<AnalysisReportGroupDefine> groups = this.analysisHelper.getGroupList();
        ArrayList<AnalysisReportGroupDefine> filteredGroups = new ArrayList<AnalysisReportGroupDefine>();
        if (groups != null) {
            for (AnalysisReportGroupDefine group : groups) {
                boolean canReadModal = this.authProvider.canReadModal(group.getKey(), AnalysisReportResourceType.GROUP);
                if (!canReadModal) continue;
                filteredGroups.add(group);
            }
        }
        return AnaUtils.initReturn(filteredGroups);
    }

    @Override
    public ReturnObject getGroupListByRoot() throws Exception {
        List<AnalysisReportGroupDefine> groups = this.analysisHelper.getGroupByParent("0");
        ArrayList<AnalysisReportGroupDefine> filteredGroups = new ArrayList<AnalysisReportGroupDefine>();
        if (groups != null) {
            for (AnalysisReportGroupDefine group : groups) {
                boolean canReadModal = this.authProvider.canReadModal(group.getKey(), AnalysisReportResourceType.GROUP) || this.authProvider.canWriteModal(group.getKey(), AnalysisReportResourceType.GROUP);
                if (!canReadModal) continue;
                filteredGroups.add(group);
            }
        }
        return AnaUtils.initReturn(filteredGroups);
    }

    @Override
    public ReturnObject getGroupByKey(String key) throws Exception {
        boolean canReadModal = this.authProvider.canReadModal(key, AnalysisReportResourceType.GROUP);
        if (!canReadModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_401);
        }
        return AnaUtils.initReturn(this.analysisHelper.getGroupByKey(key));
    }

    @Override
    public ReturnObject insertGroup(JsonNode jsonNode) throws Exception {
        String parentGroupKey = jsonNode.get("parentgroup") != null && !"".equals(jsonNode.get("parentgroup").textValue()) ? jsonNode.get("parentgroup").textValue() : "0";
        String newGroupKey = UUIDUtils.getKey();
        boolean canEditParentGroup = this.authProvider.canWriteModal(parentGroupKey, AnalysisReportResourceType.GROUP);
        if ("0" != parentGroupKey && !canEditParentGroup) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_404);
        }
        int grouplevel = this.checkGroupLevel(jsonNode.get("parentgroup") == null ? "0" : jsonNode.get("parentgroup").textValue(), 0);
        if (grouplevel > 5) {
            return AnaUtils.initReturn(false, "\u5206\u7ec4\u5c42\u7ea7\u8d85\u8fc7\u4e94\u5c42\uff0c\u4e0d\u5141\u8bb8\u65b0\u5efa\u5206\u7ec4");
        }
        AnalysisReportGroupDefineImpl define = new AnalysisReportGroupDefineImpl();
        define.setKey(newGroupKey);
        define.setTitle(HtmlUtils.htmlEscape((String)jsonNode.get("title").textValue()));
        define.setDescription(jsonNode.get("description") != null ? HtmlUtils.htmlEscape((String)jsonNode.get("description").textValue()) : "");
        define.setParentgroup(parentGroupKey);
        this.analysisHelper.insertGroup(define);
        this.authProvider.grantAllPrivileges(define.getKey());
        AnalysisReportLogHelper.log("\u65b0\u5efa\u5206\u7ec4", "\u65b0\u5efa\u5206\u7ec4\uff1a" + jsonNode.get("title").textValue(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        HashMap<String, String> obj = new HashMap<String, String>();
        obj.put("message", "\u65b0\u5efa\u5206\u7ec4\u6210\u529f");
        obj.put("newKey", newGroupKey);
        return AnaUtils.initReturn(obj);
    }

    private int checkGroupLevel(String parentgroup, int groupLevel) throws Exception {
        if ("0".equals(parentgroup)) {
            ++groupLevel;
        } else {
            AnalysisReportGroupDefine groupByKey = this.analysisHelper.getGroupByKey(parentgroup);
            groupLevel = this.checkGroupLevel(groupByKey.getParentgroup(), groupLevel);
            ++groupLevel;
        }
        return groupLevel;
    }

    @Override
    public ReturnObject updateGroup(JsonNode jsonNode) throws Exception {
        String groupKey = jsonNode.get("key").textValue();
        boolean canUpdateModal = this.authProvider.canWriteModal(groupKey, AnalysisReportResourceType.GROUP);
        if (!canUpdateModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_402);
        }
        String key = jsonNode.get("key").textValue();
        String title = HtmlUtils.htmlEscape((String)jsonNode.get("title").textValue());
        String parentGroup = jsonNode.get("parentgroup").textValue();
        List<AnalysisReportGroupDefine> groups = this.analysisHelper.getGroupByParent(parentGroup);
        for (AnalysisReportGroupDefine group : groups) {
            if (key.equals(group.getKey()) || !title.equals(group.getTitle())) continue;
            return AnaUtils.initReturn(false, "\u4fdd\u5b58\u5931\u8d25\uff0c\u5b58\u5728\u76f8\u540c\u540d\u79f0\u7684\u5206\u7ec4");
        }
        AnalysisReportGroupDefineImpl define = new AnalysisReportGroupDefineImpl();
        define.setKey(key);
        define.setTitle(title);
        define.setParentgroup(parentGroup);
        if (null != jsonNode.get("order") && null != jsonNode.get("order").textValue()) {
            define.setOrder(jsonNode.get("order").textValue());
        }
        if (null != jsonNode.get("description") && null != jsonNode.get("description").textValue()) {
            define.setDescription(HtmlUtils.htmlEscape((String)jsonNode.get("description").textValue()));
        }
        this.analysisHelper.updateGroup(define);
        AnalysisReportLogHelper.log("\u4fee\u6539\u5206\u7ec4", "\u4fee\u6539\u5206\u7ec4\uff1a" + title, AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u4fee\u6539\u5206\u7ec4\u6210\u529f");
    }

    @Override
    public ReturnObject deleteGroup(String key) throws Exception {
        boolean canDeleteModal;
        boolean bl = canDeleteModal = this.authProvider.canDeleteModal(key, AnalysisReportResourceType.GROUP) || this.authProvider.canWriteModal(key, AnalysisReportResourceType.GROUP);
        if (!canDeleteModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_403);
        }
        List<AnalysisReportDefine> list = this.analysisHelper.getListByGroupKey(key);
        if (list.size() > 0) {
            for (AnalysisReportDefine reportDefine : list) {
                if (this.authProvider.canDeleteModal(reportDefine.getKey(), AnalysisReportResourceType.TEMPLATE) || this.authProvider.canWriteModal(reportDefine.getKey(), AnalysisReportResourceType.TEMPLATE)) continue;
                return AnaUtils.initReturn(false, "\u5bf9\u4e8e\u5206\u7ec4\u4e0b\u6a21\u677f[" + reportDefine.getTitle() + "]\u65e0\u5220\u9664\u6743\u9650\uff0c\u4e0d\u5141\u8bb8\u5220\u9664\u5206\u7ec4\uff01");
            }
            for (AnalysisReportDefine reportDefine : list) {
                ReturnObject returnObject = this.deleteModel(reportDefine.getKey());
            }
        }
        AnalysisReportGroupDefine group = this.analysisHelper.getGroupByKey(key);
        this.analysisHelper.deleteGroup(key);
        AnalysisReportLogHelper.log("\u5220\u9664\u5206\u7ec4", "\u5220\u9664\u5206\u7ec4\uff1a" + group.getTitle(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u5220\u9664\u5206\u7ec4\u6210\u529f");
    }

    public List<Map<String, Object>> getAnalusisMultilevelTree(String key) throws Exception {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<AnalysisReportGroupDefine> groupByParent = this.analysisHelper.getGroupByParent("0");
        this.getAllTreeByUserSecurity(groupByParent, result);
        return result;
    }

    private List<Map<String, Object>> getAllTreeByUserSecurity(List<AnalysisReportGroupDefine> groupByParent, List<Map<String, Object>> result) throws Exception {
        if (groupByParent != null && groupByParent.size() > 0) {
            for (AnalysisReportGroupDefine define : groupByParent) {
                boolean canReadGroupModal = this.authProvider.canReadModal(define.getKey(), AnalysisReportResourceType.GROUP);
                if (!canReadGroupModal) continue;
                Map<String, Object> mapEntity = AnaUtils.getMapEntity(define);
                List<AnalysisReportGroupDefine> groupByParent1 = this.analysisHelper.getGroupByParent(define.getKey());
                if (CollectionUtils.isEmpty(groupByParent1)) {
                    mapEntity.put("isLeaf", true);
                } else {
                    List<Map<String, Object>> allTree = this.getAllTreeByUserSecurity(groupByParent1, new ArrayList<Map<String, Object>>());
                    mapEntity.put("children", allTree);
                }
                result.add(mapEntity);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getGroupAndReportTree() throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<AnalysisReportGroupDefine> groupByParent = this.analysisHelper.getGroupByParent("0");
        result = this.getAllGroupAndReportTree(groupByParent, result);
        return result;
    }

    private List<Map<String, Object>> getAllGroupAndReportTree(List<AnalysisReportGroupDefine> groupByParent, List<Map<String, Object>> result) throws Exception {
        if (groupByParent != null && groupByParent.size() > 0) {
            int userSecurityLevel = Integer.MAX_VALUE;
            userSecurityLevel = this.getUserSercurityLevel(userSecurityLevel);
            boolean isSecurityLevel = this.securityLevelService.isSecurityLevelEnabled();
            for (AnalysisReportGroupDefine define : groupByParent) {
                Map<String, Object> mapEntity = AnaUtils.getMapEntity(define);
                boolean canReadGroupModal = this.authProvider.canReadModal(define.getKey(), AnalysisReportResourceType.GROUP);
                if (!canReadGroupModal) continue;
                List<AnalysisReportGroupDefine> groupByParent1 = this.analysisHelper.getGroupByParent(define.getKey());
                List<Map<String, Object>> allTree = this.getAllGroupAndReportTree(groupByParent1, new ArrayList<Map<String, Object>>());
                List<AnalysisReportDefine> listByGroupKey = this.analysisHelper.getListByGroupKey(define.getKey());
                for (AnalysisReportDefine analysisReportDefine : listByGroupKey) {
                    boolean canReadModal = this.authProvider.canReadModal(analysisReportDefine.getKey(), AnalysisReportResourceType.TEMPLATE);
                    if (!canReadModal) continue;
                    String securityLevel = analysisReportDefine.getSecurityLevel();
                    if (isSecurityLevel && StringUtils.isNotEmpty((String)securityLevel) && userSecurityLevel < Integer.parseInt(securityLevel)) continue;
                    String securityTitle = this.iSecretLevelService.getSecretLevelItem(analysisReportDefine.getSecurityLevel()).getTitle();
                    Map<String, Object> mapEntity1 = AnaUtils.getMapEntity(analysisReportDefine, securityTitle);
                    allTree.add(mapEntity1);
                }
                mapEntity.put("children", allTree);
                result.add(mapEntity);
            }
        }
        return result;
    }

    @Override
    public ReturnObject getAnalysisTree(String key) throws Exception {
        return AnaUtils.initReturn(this.getAnalusisMultilevelTree(key));
    }

    @Override
    public ReturnObject analysisGroupTransposition(String key1, String key2) throws Exception {
        boolean canUpdateModal = this.authProvider.canWriteModal(key1, AnalysisReportResourceType.GROUP);
        if (!canUpdateModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_402);
        }
        boolean canUpdateModal2 = this.authProvider.canWriteModal(key2, AnalysisReportResourceType.GROUP);
        if (!canUpdateModal2) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_402);
        }
        AnalysisReportGroupDefine define1 = this.analysisHelper.getGroupByKey(key1);
        AnalysisReportGroupDefine define2 = this.analysisHelper.getGroupByKey(key2);
        if (null == define1 || null == define2) {
            return AnaUtils.initReturn(false, "\u79fb\u52a8\u5931\u8d25");
        }
        String orderTemp = define1.getOrder();
        define1.setOrder(define2.getOrder());
        define2.setOrder(orderTemp);
        this.analysisHelper.updateGroup(define1);
        this.analysisHelper.updateGroup(define2);
        AnalysisReportLogHelper.log("\u79fb\u52a8\u5206\u7ec4", "\u79fb\u52a8\u5206\u7ec4\uff1a[" + define1.getTitle() + "," + define2.getTitle() + "]", AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u4ea4\u6362\u6210\u529f");
    }

    @Override
    public ReturnObject analysisModelTransposition(String key1, String key2) throws Exception {
        boolean canUpdateModal = this.authProvider.canWriteModal(key1, AnalysisReportResourceType.TEMPLATE);
        if (!canUpdateModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_402);
        }
        boolean canUpdateModal2 = this.authProvider.canWriteModal(key2, AnalysisReportResourceType.TEMPLATE);
        if (!canUpdateModal2) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_402);
        }
        AnalysisReportDefine define1 = this.analysisHelper.getListByKey(key1);
        AnalysisReportDefine define2 = this.analysisHelper.getListByKey(key2);
        if (null == define1 || null == define2) {
            return AnaUtils.initReturn(false, "\u79fb\u52a8\u5931\u8d25");
        }
        String orderTemp = define1.getOrder();
        define1.setOrder(define2.getOrder());
        define2.setOrder(orderTemp);
        if (StringUtil.isNullOrEmpty((String)define1.getOrder())) {
            define1.setOrder(OrderGenerator.newOrder());
        }
        if (StringUtil.isNullOrEmpty((String)define2.getOrder())) {
            define2.setOrder(OrderGenerator.newOrder());
        }
        this.analysisHelper.updateModel(define1);
        this.analysisHelper.updateModel(define2);
        AnalysisReportLogHelper.log("\u79fb\u52a8\u6a21\u677f", "\u79fb\u52a8\u6a21\u677f\uff1a[" + define1.getTitle() + "," + define2.getTitle() + "]", AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u79fb\u52a8\u6210\u529f");
    }

    private int getMinSercurityLevel(String modelId) throws Exception {
        int templateSecurityLevel;
        AnalysisReportDefine analysisDefine = this.analysisHelper.getListByKey(modelId);
        int userSecurityLevel = Integer.MAX_VALUE;
        userSecurityLevel = this.getUserSercurityLevel(userSecurityLevel);
        if (StringUtil.isNullOrEmpty((String)analysisDefine.getSecurityLevel())) {
            SecurityLevel minSystemSecurityLevel = this.securityLevelHelper.getMinSystemSecurityLevel();
            templateSecurityLevel = Integer.valueOf(minSystemSecurityLevel.getName());
        } else {
            templateSecurityLevel = Integer.valueOf(analysisDefine.getSecurityLevel());
        }
        return Math.min(userSecurityLevel, templateSecurityLevel);
    }

    private int getUserSercurityLevel(int userSecurityLevel) {
        if (!this.systemIdentityService.isAdmin()) {
            ContextUser user = NpContextHolder.getContext().getUser();
            if (StringUtil.isNullOrEmpty((String)user.getSecuritylevel())) {
                SecurityLevel minUserSecurityLevel = this.securityLevelHelper.getMinUserSecurityLevel();
                userSecurityLevel = Integer.valueOf(minUserSecurityLevel.getName());
            } else {
                userSecurityLevel = Integer.valueOf(user.getSecuritylevel());
            }
        }
        return userSecurityLevel;
    }

    @Override
    public ReturnObject chooseReportTree(String key) throws Exception {
        List<TaskDefine> allTaskDefines = this.analysisReportEntityService.getAllTaskDefines();
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (TaskDefine t : allTaskDefines) {
            if (t == null) continue;
            Map<String, Object> tmap = AnaUtils.getTreeMap(t.getKey(), t.getTitle(), false, false);
            List<FormSchemeDefine> formSchemeDefines = this.analysisReportEntityService.queryFormSchemeByTask(t.getKey());
            ArrayList<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
            for (FormSchemeDefine fs : formSchemeDefines) {
                if (fs == null) continue;
                Map<String, Object> fsmap = AnaUtils.getTreeMap(fs.getKey(), fs.getTitle(), false, true);
                List<FormGroupDefine> formGroupDefines = this.analysisReportEntityService.queryRootGroupsByFormScheme(fs.getKey());
                ArrayList<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
                for (FormGroupDefine g : formGroupDefines) {
                    if (g == null) continue;
                    Map<String, Object> gmap = AnaUtils.getTreeMap(g.getKey(), g.getTitle(), false, true);
                    List<FormDefine> allFormsInGroup = this.analysisReportEntityService.getAllFormsInGroup(g.getKey());
                    ArrayList<Map<String, Object>> flist = new ArrayList<Map<String, Object>>();
                    for (FormDefine f : allFormsInGroup) {
                        Map<String, Object> fmap = AnaUtils.getTreeMap(f.getKey(), f.getTitle(), true, true);
                        Map gdata = (Map)fmap.get("data");
                        gdata.put("taskKey", t.getKey());
                        flist.add(fmap);
                    }
                    gmap.put("children", flist);
                    slist.add(gmap);
                }
                fsmap.put("children", slist);
                tlist.add(fsmap);
            }
            tmap.put("children", tlist);
            result.add(tmap);
        }
        return AnaUtils.initReturn(result);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ReturnObject modelSelectMasterView(String key) throws Exception {
        boolean canReadModal = this.authProvider.canReadModal(key, AnalysisReportResourceType.TEMPLATE);
        if (!canReadModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_401);
        }
        ArrayList<HashMap<String, Object>> masterEntityKeyList = new ArrayList<HashMap<String, Object>>();
        AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(key);
        DimensionConfigObj dimension = null;
        try {
            dimension = SerializeUtils.jsonDeserialize(analysisReportDefine.getDimension(), DimensionConfigObj.class);
        }
        catch (JQException jQException) {
        }
        finally {
            if (dimension == null) {
                dimension = new DimensionConfigObj();
            }
        }
        for (DimensionObj dObj : dimension.getSrcDims()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("key", dObj.getKey());
            map.put("title", dObj.getTitle());
            map.put("type", dObj.getType());
            map.put("hidden", dObj.getConfig().isHidden());
            map.put("defaultEntityConf", dObj.getConfig().getDefaultEntityConf());
            map.put("defaultEntityTitle", dObj.getConfig().getDefaultEntityTitle());
            map.put("defaultEntityVal", dObj.getConfig().getDefaultEntityVal());
            String defaultEntityConf = dObj.getConfig().getDefaultEntityConf();
            String defaultEntityVal = dObj.getConfig().getDefaultEntityVal();
            if ((StringUtils.isEmpty((String)defaultEntityVal) || "none".equals(defaultEntityConf)) && !DimensionType.DIMENSION_PERIOD.equals((Object)dObj.getType())) {
                map = this.getDimTreeFirstNode(map);
            }
            masterEntityKeyList.add((HashMap<String, Object>)map);
        }
        return AnaUtils.initReturn(masterEntityKeyList);
    }

    public Map<String, Object> getDimTreeFirstNode(Map<String, Object> map) {
        try {
            List<IEntityRow> entityRows = this.getEntityRootRows(map.get("key").toString());
            if (!CollectionUtils.isEmpty(entityRows)) {
                IEntityRow iEntityRow = entityRows.get(0);
                map.put("defaultEntityTitle", iEntityRow.getTitle());
                map.put("defaultEntityVal", iEntityRow.getEntityKeyData());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return map;
    }

    private List<IEntityRow> getEntityRootRows(String entityId) {
        IEntityQuery iEntityQuery = this.makeIEntityQuery(entityId);
        ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
        executorContext.setVarDimensionValueSet(iEntityQuery.getMasterKeys());
        try {
            return iEntityQuery.executeReader((IContext)executorContext).getRootRows();
        }
        catch (Exception e) {
            log.error(e.getMessage() + "\u5b9e\u4f53\u67e5\u8be2\u5f02\u5e38:" + entityId, e);
            return null;
        }
    }

    private IEntityQuery makeIEntityQuery(String entityId) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setMasterKeys(new DimensionValueSet());
        query.setAuthorityOperations(AuthorityType.Read);
        EntityViewDefine entityViewDefine = this.viewAdapter.buildEntityView(entityId);
        query.setEntityView(entityViewDefine);
        query.setQueryVersionDate(null);
        query.lazyQuery();
        query.markLeaf();
        return query;
    }

    @Override
    public ReturnObject analysisReportGenerator(ReportGeneratorVO reportGeneratorVO) throws Exception {
        String modelKey = reportGeneratorVO.getKey();
        AnalysisReportDefine define = this.analysisHelper.getListByKey(modelKey);
        AnalysisTemp analysisTemp = this.returnAnalysisTemp(define);
        String fullContent = null;
        if (!StringUtils.isEmpty((String)define.getData())) {
            fullContent = new String(Base64.base64ToByteArray((String)define.getData()));
            analysisTemp.setData(fullContent);
        }
        if (!StringUtils.isEmpty((String)reportGeneratorVO.getArcKey())) {
            String arcKey = reportGeneratorVO.getArcKey();
            ReportChapterDefine chapter = this.chapterService.getChapterById(arcKey);
            analysisTemp.setTypeSpeed(chapter.getTypeSpeed());
        }
        return AnaUtils.initReturn(analysisTemp);
    }

    @Override
    public ReturnObject zbListToCode(String jsonDefine) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tableidNode = mapper.readTree(jsonDefine);
        Iterator iterator = tableidNode.iterator();
        StringBuffer buf = new StringBuffer("");
        if (iterator.hasNext()) {
            JsonNode next = (JsonNode)iterator.next();
            FormulaVariable formulaVar = new FormulaVariable();
            return AnaUtils.initReturn(formulaVar);
        }
        throw new Exception("no fields selected.");
    }

    @Override
    public ReturnObject reportToCode(String jsonDefine) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tableidNode = mapper.readTree(jsonDefine);
        String title = tableidNode.get("title").textValue();
        String key = tableidNode.get("key").textValue();
        FormVariable formVar = new FormVariable();
        formVar.setName(UUID.randomUUID().toString());
        formVar.setCaption(title);
        formVar.setExpression(key);
        return AnaUtils.initReturn(formVar);
    }

    private AnalysisTemp returnAnalysisTemp(AnalysisReportDefine define) {
        AnalysisTemp analysisTemp = new AnalysisTemp();
        analysisTemp.setKey(define.getKey());
        analysisTemp.setGroupKey(define.getGroupKey());
        analysisTemp.setTitle(define.getTitle());
        analysisTemp.setDescription(define.getDescription());
        analysisTemp.setCreateuser(define.getCreateuser());
        analysisTemp.setModifyuser(define.getModifyuser());
        analysisTemp.setOrder(define.getOrder());
        analysisTemp.setPrintData(define.getPrintData());
        return analysisTemp;
    }

    @Override
    public ReturnObject setAnalysisExtendData(JsonNode jsonNode) throws Exception {
        String modelKey = jsonNode.get("modelId").textValue();
        boolean canUpdateModal = this.authProvider.canWriteModal(modelKey, AnalysisReportResourceType.TEMPLATE);
        if (!canUpdateModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_402);
        }
        AnalysisReportDefine listByKey = this.analysisHelper.getListByKey(jsonNode.get("modelId").textValue());
        String extendData = jsonNode.get("info").toString();
        String dataType = jsonNode.get("type").textValue();
        String printData = listByKey.getPrintData();
        printData = SaveAnalysisImpl.UpdateExtendData(printData, extendData, dataType);
        listByKey.setPrintData(printData);
        this.analysisHelper.updateModel(listByKey);
        return AnaUtils.initReturn("\u4fdd\u5b58\u9875\u7709\u8bbe\u7f6e\u6210\u529f");
    }

    private static String UpdateExtendData(String printData, String jsonInfo, String type) throws Exception {
        if (StringUtils.isEmpty((String)printData)) {
            printData = "{\"pageHeader\":{},\"pageFooter\":{},\"pageNumber\":{},\"template\":{}}";
        }
        JSONObject kindJson = new JSONObject(printData);
        kindJson.put(type, (Object)jsonInfo);
        if (kindJson.has("info")) {
            kindJson.remove("info");
        }
        try {
            JSONObject template = new JSONObject(kindJson.getString("template"));
            BigDecimal b = new BigDecimal(10);
            String marginLeft = String.valueOf(template.get("marginLeft"));
            String marginTop = String.valueOf(template.get("marginTop"));
            String marginBottom = template.has("marginBottom") ? String.valueOf(template.get("marginBottom")) : String.valueOf(template.get("marginTop"));
            String paperWidth = String.valueOf(template.get("paperWidth"));
            String paperHeight = String.valueOf(template.get("paperHeight"));
            if (!StringUtil.isNullOrEmpty((String)marginLeft)) {
                template.put("marginLeft", new BigDecimal(marginLeft).multiply(b).doubleValue());
            }
            if (!StringUtil.isNullOrEmpty((String)marginTop)) {
                template.put("marginTop", new BigDecimal(marginTop).multiply(b).doubleValue());
            }
            if (!StringUtil.isNullOrEmpty((String)marginBottom)) {
                template.put("marginBottom", new BigDecimal(marginBottom).multiply(b).doubleValue());
            }
            if (!StringUtil.isNullOrEmpty((String)paperWidth)) {
                template.put("paperWidth", new BigDecimal(paperWidth).multiply(b).doubleValue());
            }
            if (!StringUtil.isNullOrEmpty((String)paperHeight)) {
                template.put("paperHeight", new BigDecimal(paperHeight).multiply(b).doubleValue());
            }
            if (template.has("marginRight")) {
                template.remove("marginRight");
            }
            kindJson.put("template", (Object)template);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return kindJson.toString();
    }

    @Override
    public ReturnObject queryFormById(String jsonDefine) throws Exception {
        JSONObject json = new JSONObject(jsonDefine);
        if (!json.has("varTask")) {
            return AnaUtils.initReturn(false, "\u67e5\u8be2\u7ef4\u5ea6\u5931\u8d25", null);
        }
        String varTask = json.getString("varTask");
        ArrayList defines = new ArrayList();
        Map<String, Map<String, Object>> dimensionDefines = this.analysisReportEntityService.getDimensionDefine(varTask);
        dimensionDefines.keySet().forEach(key -> defines.add(dimensionDefines.get(key)));
        return AnaUtils.initReturn(defines);
    }

    @Override
    public ReturnObject queryEntitysByElements(String jsonDefine) throws ParseException, UnsupportedEncodingException {
        JSONObject json = new JSONObject(jsonDefine);
        String fullContent = json.getString("fullContent");
        fullContent = URLDecoder.decode(fullContent, "UTF-8");
        ArrayList masterEntityKeyList = new ArrayList();
        HashSet<DimensionObj> masterEntityKeySet = new HashSet<DimensionObj>();
        Document doc = Jsoup.parseBodyFragment((String)fullContent);
        Element body = doc.body();
        Elements es = body.getElementsByClass("jqVar");
        for (Element e : es) {
            if (e.hasClass("formulaVar")) {
                try {
                    String formulaExpr = e.attr("var-expr");
                    String formSchemeKey = e.attr("var-formscheme-key");
                    if (StringUtils.isEmpty((String)formSchemeKey)) {
                        masterEntityKeySet.addAll(this.analysisReportEntityService.getDimensionByFormula(formulaExpr));
                        continue;
                    }
                    FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
                    if (formScheme == null) continue;
                    masterEntityKeySet.addAll(this.analysisReportEntityService.makeDimensionObject(formScheme.getTaskKey()));
                }
                catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
                continue;
            }
            if (e.hasClass("formVar")) {
                try {
                    String formExpr = e.attr("var-expr");
                    String taskID = e.attr("var-task");
                    if (StringUtil.isNullOrEmpty((String)formExpr)) continue;
                    masterEntityKeySet.addAll(this.analysisReportEntityService.makeDimensionObject(taskID));
                }
                catch (Exception exception) {}
                continue;
            }
            for (IExprParser exprParser : this.exprParsers) {
                if (!e.hasClass(exprParser.getName()) || exprParser.queryEntitys(e) == null) continue;
                masterEntityKeySet.addAll((Collection)exprParser.queryEntitys(e));
            }
        }
        Iterator exp = masterEntityKeySet.iterator();
        while (exp.hasNext()) {
            masterEntityKeyList.add(exp.next());
        }
        return AnaUtils.initReturn(masterEntityKeyList);
    }

    @Override
    public ReturnObject queryTableByElement(String jsonDefine) throws UnsupportedEncodingException {
        HashMap<String, String> tables = new HashMap<String, String>();
        JSONObject json = new JSONObject(jsonDefine);
        String fullContent = json.getString("fullContent");
        fullContent = URLDecoder.decode(fullContent, "UTF-8");
        Document doc = Jsoup.parseBodyFragment((String)fullContent);
        Element body = doc.body();
        Elements elements = body.getElementsByClass("jqVar");
        String printData = json.getString("printData");
        printData = URLDecoder.decode(printData, "UTF-8");
        JSONObject paperInfo = new JSONObject(printData);
        String DPIString = json.getString("DPI");
        DPIString = URLDecoder.decode(DPIString, "UTF-8");
        ConversionDPI cDPI = new ConversionDPI(DPIString);
        int paperWidth = paperInfo.getInt("orientation") == 0 ? paperInfo.getInt("paperWidth") : paperInfo.getInt("paperHeight");
        int marginLeft = paperInfo.getInt("marginLeft");
        int usableWidth = paperWidth - marginLeft * 2;
        usableWidth = cDPI.mmConvertToPx(usableWidth, ConversionDPI.DEVICE.X);
        for (Element element : elements) {
            String formExpr = element.attr("var-expr");
            if (StringUtil.isNullOrEmpty((String)formExpr) || tables.containsKey(formExpr)) continue;
            Grid2Data grid2Data = this.jtableParamService.getGridData(formExpr.toString());
            int gridWidth = 0;
            for (int colIndex = 1; colIndex < grid2Data.getColumnCount(); ++colIndex) {
                if (grid2Data.isColumnHidden(colIndex)) continue;
                int colWidth = grid2Data.getColumnWidth(colIndex);
                gridWidth += colWidth;
            }
            Table table = new Table();
            table.clearStyle();
            for (int i = 1; i < grid2Data.getRowCount(); ++i) {
                int rowHeight = grid2Data.getRowHeight(i);
                Row row = table.addRow();
                row.getStyle().append("height:" + rowHeight + "px;");
                for (int j = 1; j < grid2Data.getColumnCount(); ++j) {
                    int colWidth;
                    Col col = table.getRow(i - 1).addCol();
                    col.clearStyle();
                    StringBuffer styleBuffer = col.getStyle();
                    StringBuffer attributeBuffer = col.getAttribute();
                    GridCellData gridCellData = grid2Data.getGridCellData(j, i);
                    Point mergeInfo = gridCellData.getMergeInfo();
                    if (mergeInfo != null) {
                        col.setShow(false);
                        continue;
                    }
                    int rowSpan = gridCellData.getRowSpan();
                    int colSpan = gridCellData.getColSpan();
                    String text = gridCellData.getShowText();
                    col.setValue(text);
                    float zoom = 0.0f;
                    if (grid2Data.isColumnHidden(j)) {
                        styleBuffer.append("display: none;");
                    } else if (colSpan == 1) {
                        colWidth = grid2Data.getColumnWidth(j);
                        colWidth = new Double(Math.floor((float)colWidth / (float)gridWidth * 10000.0f / 100.0f)).intValue();
                        styleBuffer.append("width: " + (colWidth > 0 ? colWidth : 1) + "%;");
                        zoom = colWidth > 0 ? (float)colWidth / 100.0f : 0.01f;
                    } else {
                        colWidth = 0;
                        for (int l = 0; l < colSpan; ++l) {
                            int width = new Double(Math.floor((float)grid2Data.getColumnWidth(j + l) / (float)gridWidth * 10000.0f / 100.0f)).intValue();
                            colWidth += width > 0 ? width : 1;
                        }
                        styleBuffer.append("tempwidth: " + colWidth + "%;");
                        zoom = (float)colWidth / 100.0f;
                    }
                    int horzAlign = gridCellData.getCellStyleData().getHorzAlign();
                    String fontName = gridCellData.getCellStyleData().getFontName();
                    int fontSize = gridCellData.getCellStyleData().getFontSize();
                    int fontStyle = gridCellData.getCellStyleData().getFontStyle();
                    JLabel label = new JLabel(text);
                    label.setFont(new Font(fontName, fontStyle, fontSize));
                    FontMetrics metrics = label.getFontMetrics(label.getFont());
                    int textW = metrics.stringWidth(label.getText());
                    if ((float)textW > (float)usableWidth * zoom - 20.0f) {
                        fontSize = (int)((float)fontSize * (((float)usableWidth * zoom - 20.0f) / (float)textW));
                    }
                    String backgrondcolor = AnaUtils.colorLongToARGB(gridCellData.getBackGroundColor());
                    String fontcolor = AnaUtils.colorLongToARGB(gridCellData.getForeGroundColor());
                    if (horzAlign == 3) {
                        styleBuffer.append("text-align: center;");
                    } else if (horzAlign == 2) {
                        styleBuffer.append("text-align: right;");
                    }
                    styleBuffer.append("font-family: '" + fontName + "';").append("font-size: " + fontSize + "px;");
                    if (fontStyle == 2) {
                        styleBuffer.append("font-weight: bold;");
                    }
                    if (COLOR_STYLE) {
                        styleBuffer.append("background: ").append(backgrondcolor).append(";");
                        styleBuffer.append("color:").append(fontcolor).append(";");
                    }
                    if (rowSpan != 0) {
                        attributeBuffer.append("rowspan=\"").append(rowSpan).append("\"  ");
                    }
                    if (colSpan != 0) {
                        attributeBuffer.append("colspan=\"").append(colSpan).append("\"   ");
                    }
                    int bootomBorderColor = gridCellData.getCellStyleData().getBottomBorderColor();
                    int bootomBorderStyle = gridCellData.getCellStyleData().getBottomBorderStyle();
                    BorderStyle bottomBorder = BorderStyle.getBorderStyle(bootomBorderColor, bootomBorderStyle);
                    styleBuffer.append("border-bottom-width: " + bottomBorder.border_width + "; ");
                    styleBuffer.append("border-bottom-style: " + bottomBorder.border_style + "; ");
                    styleBuffer.append("border-bottom-color: " + bottomBorder.border_color + "; ");
                    int rightBorderColor = gridCellData.getCellStyleData().getRightBorderColor();
                    int rightBorderStyle = gridCellData.getCellStyleData().getRightBorderStyle();
                    BorderStyle rightBorder = BorderStyle.getBorderStyle(rightBorderColor, rightBorderStyle);
                    styleBuffer.append("border-right-width: " + rightBorder.border_width + "; ");
                    styleBuffer.append("border-right-style: " + rightBorder.border_style + "; ");
                    styleBuffer.append("border-right-color: " + rightBorder.border_color + "; ");
                    try {
                        GridCellData leftGridCellData = grid2Data.getGridCellData(j - 1, i);
                        int leftBorderColor = leftGridCellData.getCellStyleData().getRightBorderColor();
                        int leftBorderStyle = leftGridCellData.getCellStyleData().getRightBorderStyle();
                        BorderStyle leftBorder = BorderStyle.getBorderStyle(leftBorderColor, leftBorderStyle);
                        if (rightBorder.style == BorderStyle.NONE.style && leftBorder.style == BorderStyle.DEFAULT.style) {
                            leftBorder = BorderStyle.getBorderStyle(0, BorderStyle.NONE.style);
                        } else if (bottomBorder.style == BorderStyle.NONE.style && leftBorder.style == BorderStyle.DEFAULT.style) {
                            leftBorder = BorderStyle.getBorderStyle(0, BorderStyle.NONE.style);
                        }
                        styleBuffer.append("border-left-width: " + leftBorder.border_width + "; ");
                        styleBuffer.append("border-left-style: " + leftBorder.border_style + "; ");
                        styleBuffer.append("border-left-color: " + leftBorder.border_color + "; ");
                    }
                    catch (Exception leftGridCellData) {
                        // empty catch block
                    }
                    try {
                        GridCellData topGridCellData = grid2Data.getGridCellData(j, i - 1);
                        int topBorderColor = topGridCellData.getCellStyleData().getBottomBorderColor();
                        int topBorderStyle = topGridCellData.getCellStyleData().getBottomBorderStyle();
                        BorderStyle topBorder = BorderStyle.getBorderStyle(topBorderColor, topBorderStyle);
                        if (rightBorder.style == BorderStyle.NONE.style && topBorder.style == BorderStyle.DEFAULT.style) {
                            topBorder = BorderStyle.getBorderStyle(0, BorderStyle.NONE.style);
                        } else if (bottomBorder.style == BorderStyle.NONE.style && topBorder.style == BorderStyle.DEFAULT.style) {
                            topBorder = BorderStyle.getBorderStyle(0, BorderStyle.NONE.style);
                        }
                        styleBuffer.append("border-top-width: " + topBorder.border_width + "; ");
                        styleBuffer.append("border-top-style: " + topBorder.border_style + "; ");
                        styleBuffer.append("border-top-color: " + topBorder.border_color + "; ");
                        continue;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            tables.put(formExpr, table.toString());
        }
        return AnaUtils.initReturn(tables);
    }

    @Override
    public ReturnObject queryFormulaById(String jsonDefine) throws Exception {
        JSONObject json = new JSONObject(jsonDefine);
        String expression = json.getString("expression");
        ArrayList maps = new ArrayList();
        Set<Map<String, Object>> masterEntrys = this.analysisReportEntityService.getEntityDefineByFormula(expression);
        if (masterEntrys.size() > 0) {
            masterEntrys.forEach(x -> maps.add(x));
        }
        return AnaUtils.initReturn(maps);
    }

    @Override
    public ReturnObject queryPasteformVarTitles(String jsonDefine) throws Exception {
        JSONObject json = new JSONObject(jsonDefine);
        String elementExprs = json.getString("elementExprs");
        if (StringUtils.isEmpty((String)elementExprs)) {
            return AnaUtils.initReturn("");
        }
        String[] exprs = elementExprs.split(";");
        ArrayList<String> keys = new ArrayList<String>();
        for (int i = 0; i < exprs.length; ++i) {
            String expression = exprs[i];
            keys.add(expression);
        }
        List<FormDefine> forms = this.analysisReportEntityService.queryFormsById(keys);
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < forms.size(); ++i) {
            map.put(forms.get(i).getKey(), forms.get(i).getTitle());
        }
        return AnaUtils.initReturn(map);
    }

    @Override
    public List<ITree<ChartTreeNode>> getRootGroup(String type) {
        ArrayList<ITree<ChartTreeNode>> groups = new ArrayList<ITree<ChartTreeNode>>();
        if (type == "bireport") {
            ChartTreeNode reportNode = ChartTreeNode.buildTreeNodeData("report" + HttpUtils.splitchart + "06079ac0-dc15-11e8-969b-64006a6432d7", "\u5feb\u901f\u5206\u6790\u8868", true, "report");
            reportNode.setCode("06079ac0-dc15-11e8-969b-64006a6432d7");
            ITree root = new ITree((INode)reportNode);
            root.setLeaf(false);
            groups.add((ITree<ChartTreeNode>)root);
        } else if (type == "bi") {
            ChartTreeNode biNode = ChartTreeNode.buildTreeNodeData("bi" + HttpUtils.splitchart + "08079ac0-dc15-11e8-969b-64006a6432d6", "BI\u56fe\u8868", true, "bi");
            biNode.setCode("08079ac0-dc15-11e8-969b-64006a6432d6");
            ITree biroot = new ITree((INode)biNode);
            biroot.setLeaf(false);
            groups.add((ITree<ChartTreeNode>)biroot);
        }
        return groups;
    }

    @Override
    public ReturnObject getVersionDetailById(String bigDataKey, String arcKey) throws Exception {
        AnalyBigdataDefine analyBigdataDefine = StringUtils.isEmpty((String)arcKey) ? this.analyBigDataService.getBykey(bigDataKey) : this.analyBigDataService.getArcBigData(bigDataKey, arcKey);
        String bigData = analyBigdataDefine.getBigData();
        if (StringUtils.isNotEmpty((String)bigData)) {
            Document doc = Jsoup.parseBodyFragment((String)bigData);
            bigData = doc.body().children().toString();
        }
        AnalysisReportLogHelper.log("\u83b7\u53d6\u7248\u672c\u8be6\u60c5\u7248\u672c", "\u83b7\u53d6\u7248\u672c\u8be6\u60c5\u7248\u672c", AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn(true, "\u83b7\u53d6\u6210\u529f", bigData);
    }

    @Override
    public ReturnObject checkVersion(ReportVersionVo reportVersionVo) throws Exception {
        String analytemplateKey = reportVersionVo.getKey();
        StringBuffer entitykeyfinal = new StringBuffer();
        String entitykeyfinalstr = AnaUtils.buildDimStr(reportVersionVo.getChooseUnits());
        String date = reportVersionVo.getPeriodStr();
        String name = reportVersionVo.getName();
        CheckPermissionUtil.checkReadPermission(analytemplateKey);
        AnalyVersionDefineImpl define = new AnalyVersionDefineImpl();
        List<AnalyVersionDefine> analyVersionDefines = this.analysisHelper.checkVersion(analytemplateKey, entitykeyfinalstr, date, name);
        if (analyVersionDefines.size() > 0) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u5df2\u5b58\u5728");
        }
        AnalysisReportLogHelper.log("\u68c0\u67e5\u7248\u672c", "\u68c0\u67e5\u7248\u672c\uff1a" + name, AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u65b0\u7684\u7248\u672c");
    }

    @Override
    public ReturnObject checkVersionExceptSelf(ReportVersionVo reportVersionVo) throws Exception {
        String name = reportVersionVo.getName();
        String analytemplateKey = reportVersionVo.getKey();
        String key = reportVersionVo.getVersionKey();
        String entitykeyfinalstr = AnaUtils.buildDimStr(reportVersionVo.getChooseUnits());
        String date = reportVersionVo.getPeriodStr();
        CheckPermissionUtil.checkReadPermission(analytemplateKey);
        AnalyVersionDefineImpl define = new AnalyVersionDefineImpl();
        List<AnalyVersionDefine> analyVersionDefines = this.analysisHelper.checkVersion(analytemplateKey, entitykeyfinalstr, date, name);
        if (analyVersionDefines.size() > 1) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u5df2\u5b58\u5728");
        }
        if (analyVersionDefines.size() == 1 && !analyVersionDefines.get(0).getKey().equals(key)) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u5df2\u5b58\u5728");
        }
        AnalysisReportLogHelper.log("\u68c0\u67e5\u7248\u672c", "\u68c0\u67e5\u7248\u672c\uff1a" + name, AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u65b0\u7684\u7248\u672c");
    }

    @Override
    public List<AnalyVersionDefine> getVersionList(ReportBaseVO reportBaseVO) throws Exception {
        try {
            String analytemplateKey = reportBaseVO.getKey();
            String entitys = AnaUtils.buildDimStr(reportBaseVO.getChooseUnits());
            CheckPermissionUtil.checkReadPermission(analytemplateKey);
            String date = reportBaseVO.getPeriodStr();
            return this.analysisHelper.getVersionList(analytemplateKey, entitys, date);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public ReturnObject insertVersion(ReportVersionGeneratorVo reportVersionGeneratorVo) throws Exception {
        String newModelKey;
        String bigdata = reportVersionGeneratorVo.getContents();
        String analytemplateKey = reportVersionGeneratorVo.getKey();
        CheckPermissionUtil.checkReadPermission(analytemplateKey);
        String entitykey = AnaUtils.buildDimStr(reportVersionGeneratorVo.getChooseUnits());
        String date = reportVersionGeneratorVo.getPeriodStr();
        String sourceVersionKey = reportVersionGeneratorVo.getVersionKey();
        String name = reportVersionGeneratorVo.getName();
        String userId = NpContextHolder.getContext().getUserId();
        String arcKey = reportVersionGeneratorVo.getArcKey();
        String bigDataKey = newModelKey = UUIDUtils.getKey();
        AnalyVersionDefineImpl define = new AnalyVersionDefineImpl();
        define.setKey(newModelKey);
        define.setAnalytemplateKey(analytemplateKey);
        define.setEntityKey(entitykey);
        define.setDateKey(date);
        define.setCreateUser(userId);
        define.setCreateTime(new Date());
        define.setUpdateTime(new Date());
        define.setUpdateUser(userId);
        define.setBigDataKey(bigDataKey);
        define.setVersionName(name);
        this.analysisHelper.insertVersion(define);
        if (StringUtils.isNotEmpty((String)arcKey)) {
            if (StringUtils.isEmpty((String)sourceVersionKey)) {
                ReportVersionGeneratorVo reportGeneratorVO = reportVersionGeneratorVo;
                this.addTemplateAllArcVerData(reportGeneratorVO, bigDataKey, arcKey, bigdata);
            } else {
                this.copyVersionData(bigDataKey, arcKey, bigdata, sourceVersionKey);
            }
        } else {
            AnalyBigdataDefineImpl analyBigdataDefine = new AnalyBigdataDefineImpl();
            analyBigdataDefine.setKey(bigDataKey);
            analyBigdataDefine.setArcKey("");
            analyBigdataDefine.setBigData(bigdata);
            analyBigdataDefine.setUpdateTime(new Date());
            this.analyBigDataService.inseartBigData(analyBigdataDefine);
        }
        this.authProvider.grantAllPrivileges(newModelKey);
        AnalysisReportLogHelper.log("\u65b0\u589e\u7248\u672c", "\u65b0\u589e\u7248\u672c\uff1a" + reportVersionGeneratorVo.getName(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u65b0\u589e\u7248\u672c\u6210\u529f");
    }

    private void addTemplateAllArcVerData(ReportGeneratorVO reportGeneratorVO, String bigDataKey, String chooseArcKey, String bigData) throws Exception {
        String templateKey = reportGeneratorVO.getKey();
        List<ReportChapterDefine> chapterDefines = this.chapterService.queryChapterByModelId(templateKey);
        ArrayList<AnalyBigdataDefine> bigdataDefines = new ArrayList<AnalyBigdataDefine>();
        ArrayList<String> arcKeys = new ArrayList<String>();
        arcKeys.add(chooseArcKey);
        ArrayList<ReportChapterDefine> chapterDefineList = new ArrayList<ReportChapterDefine>();
        for (int i = 0; i < chapterDefines.size(); ++i) {
            ReportChapterDefine chapterDefine = chapterDefines.get(i);
            AnalyBigdataDefineImpl analyBigdataDefine = new AnalyBigdataDefineImpl();
            analyBigdataDefine.setKey(bigDataKey);
            if (chapterDefine.getArcKey().equals(chooseArcKey)) {
                analyBigdataDefine.setBigData(bigData);
            } else if (StringUtils.isNotEmpty((String)chapterDefine.getArcData())) {
                String centent = this.analysisHelper.generateArc(chapterDefine.getArcKey(), chapterDefine.getArcData(), reportGeneratorVO);
                analyBigdataDefine.setBigData(centent);
                if (StringUtils.isEmpty((String)chapterDefine.getCatalog())) {
                    arcKeys.add(chapterDefine.getArcKey());
                } else {
                    analyBigdataDefine.setCatalog(chapterDefine.getCatalog());
                    analyBigdataDefine.setCatalogUpdatetime(new Date());
                }
            }
            analyBigdataDefine.setArcKey(chapterDefine.getArcKey());
            analyBigdataDefine.setUpdateTime(new Date());
            bigdataDefines.add(analyBigdataDefine);
            ReportChapterDefine reportChapterDefine = new ReportChapterDefine();
            reportChapterDefine.setArcKey(chapterDefine.getArcKey());
            reportChapterDefine.setArcName(chapterDefine.getArcName());
            reportChapterDefine.setTypeSpeed(chapterDefine.getTypeSpeed());
            chapterDefineList.add(reportChapterDefine);
        }
        this.analyBigDataService.inseartBigDatas(bigdataDefines.toArray(new AnalyBigdataDefine[bigdataDefines.size()]));
        this.asyncGenCatalog(bigdataDefines);
        this.insertExtendChapterInfo(bigDataKey, null, chapterDefineList);
    }

    private void copyVersionData(String bigDataKey, String chooseArcKey, String bigData, String sourceVersionKey) throws Exception {
        ArrayList<String> arcKeys = new ArrayList<String>();
        AnalyVersionDefine versionDefine = this.analysisHelper.getVersionBykey(sourceVersionKey);
        List<AnalyBigdataDefine> bigdataDefines = this.analyBigDataService.list(versionDefine.getBigDataKey());
        for (AnalyBigdataDefine analyBigdataDefine : bigdataDefines) {
            if (analyBigdataDefine.getArcKey().equals(chooseArcKey)) {
                analyBigdataDefine.setBigData(bigData);
                arcKeys.add(chooseArcKey);
            }
            analyBigdataDefine.setKey(bigDataKey);
        }
        this.analyBigDataService.inseartBigDatas(bigdataDefines.toArray(new AnalyBigdataDefine[bigdataDefines.size()]));
        this.asyncGenCatalog(bigdataDefines);
        this.insertExtendChapterInfo(bigDataKey, versionDefine.getBigDataKey(), null);
    }

    public void insertExtendChapterInfo(String bigDataKey, String sourceVersionKey, List<ReportChapterDefine> chapterDefineList) throws DBParaException, JsonProcessingException {
        ReportVersionExtendData versionExtend = new ReportVersionExtendData();
        versionExtend.setAvKey(bigDataKey);
        JSONObject extendJson = new JSONObject();
        if (StringUtil.isNullOrEmpty((String)sourceVersionKey)) {
            ObjectMapper objectMapper = new ObjectMapper();
            extendJson.put("EXTEND_CHAPTER_INFO", (Object)objectMapper.writeValueAsString(chapterDefineList));
            versionExtend.setAvExtData(extendJson.toString());
        } else {
            ReportVersionExtendData sourceVersionExtend = this.versionExtendDao.get(sourceVersionKey);
            JSONObject sourceExtendJson = new JSONObject(sourceVersionExtend.getAvExtData());
            extendJson.put("EXTEND_CHAPTER_INFO", sourceExtendJson.get("EXTEND_CHAPTER_INFO"));
            versionExtend.setAvExtData(extendJson.toString());
        }
        this.versionExtendDao.insert(versionExtend);
    }

    private void asyncGenCatalog(List<AnalyBigdataDefine> AnalyBigdataDefines) {
        for (AnalyBigdataDefine analyBigdataDefine : AnalyBigdataDefines) {
            this.npApplication.asyncRun((Runnable)new CatalogGenerateThread(analyBigdataDefine));
        }
    }

    @Override
    public ReturnObject updateVersion(ReportVersionVo reportVersionVo) throws Exception {
        String versionKey = reportVersionVo.getVersionKey();
        AnalyVersionDefine versionDefine = this.analysisHelper.getVersionBykey(versionKey);
        CheckPermissionUtil.checkReadPermission(versionDefine.getAnalytemplateKey());
        String userId = NpContextHolder.getContext().getUserId();
        versionDefine.setUpdateUser(userId);
        versionDefine.setUpdateTime(new Date());
        versionDefine.setVersionName(reportVersionVo.getName());
        AnalyBigdataDefineImpl analyBigdataDefine = new AnalyBigdataDefineImpl();
        analyBigdataDefine.setKey(versionDefine.getBigDataKey());
        analyBigdataDefine.setBigData(reportVersionVo.getContent());
        String arcKey = reportVersionVo.getArcKey();
        if (StringUtils.isNotEmpty((String)arcKey)) {
            analyBigdataDefine.setArcKey(arcKey);
            analyBigdataDefine.setUpdateTime(new Date());
            this.analyBigDataService.updateBigData(analyBigdataDefine);
            this.asyncGenCatalog(Arrays.asList(analyBigdataDefine));
            ReportVersionExtendData reportVersionExtendData = this.versionExtendDao.get(versionDefine.getBigDataKey());
            JSONObject extendJson = new JSONObject(reportVersionExtendData.getAvExtData());
            Object date = null;
            extendJson.put("catalogupdatetime", date);
            reportVersionExtendData.setAvExtData(extendJson.toString());
            this.versionExtendDao.update(reportVersionExtendData);
        } else {
            this.analyBigDataService.updateBigData(analyBigdataDefine);
        }
        this.analysisHelper.updateVersion(versionDefine);
        AnalysisReportLogHelper.log("\u66f4\u65b0\u7248\u672c\u7248\u672c", "\u66f4\u65b0\u7248\u672c\uff1a" + reportVersionVo.getName(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return AnaUtils.initReturn("\u66f4\u65b0\u7248\u672c\u6210\u529f");
    }

    @Override
    public ReturnObject renameVersionName(String key, String name) throws Exception {
        name = URLDecoder.decode(name);
        AnalyVersionDefine versionBykey = this.analysisHelper.getVersionBykey(key);
        if (versionBykey != null) {
            versionBykey.setVersionName(name);
            this.analysisHelper.updateVersion(versionBykey);
            AnalysisReportLogHelper.log("\u4fee\u6539\u7248\u672c\u540d\u79f0", "\u4fee\u6539\u7248\u672c\u540d\u79f0\uff1a" + name, AnalysisReportLogHelper.LOGLEVEL_INFO);
            return AnaUtils.initReturn("\u4fee\u6539\u7248\u672c\u540d\u79f0\u6210\u529f");
        }
        return AnaUtils.initReturn(false, "\u4fee\u6539\u7248\u672c\u540d\u79f0\u5931\u8d25");
    }

    @Override
    public ReturnObject deleteVersion(String key) throws Exception {
        boolean canDeleteModal;
        boolean bl = canDeleteModal = this.authProvider.canDeleteModal(key, AnalysisReportResourceType.TEMPLATE) || this.authProvider.canWriteModal(key, AnalysisReportResourceType.TEMPLATE);
        if (!canDeleteModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_403);
        }
        AnalyVersionDefine versionBykey = this.analysisHelper.getVersionBykey(key);
        if (versionBykey != null) {
            String bigDataKey = versionBykey.getBigDataKey();
            AnalyBigdataDefine bigdataDefine = this.analyBigDataService.getBykey(bigDataKey);
            this.analyBigDataService.deleteBigDataByKey(bigDataKey);
            this.analysisHelper.deleteVersionByKey(key);
            AnalysisReportLogHelper.log("\u5220\u9664\u7248\u672c", "\u5220\u9664\u7248\u672c\uff1a" + versionBykey.getVersionName(), AnalysisReportLogHelper.LOGLEVEL_INFO);
            return AnaUtils.initReturn("\u5220\u9664\u7248\u672c\u6210\u529f");
        }
        return AnaUtils.initReturn(false, "\u5220\u9664\u7248\u672c\u5931\u8d25");
    }

    @Override
    public ReturnObject getSecurityFormularTasks(String key) {
        ArrayList canReadTaskList = new ArrayList();
        List taskList = this.nrDesignTimeController.getAllReportTaskDefines();
        taskList.addAll(this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_SURVEY));
        taskList.addAll(this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_ANALYSIS));
        taskList.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).forEach(task -> {
            if (this.definitionAuthority.canReadTask(task.getKey())) {
                UReportTaskL uTask = new UReportTaskL();
                uTask.setKey(task.getKey());
                uTask.setCode(task.getTaskCode());
                uTask.setTitle(task.getTitle());
                uTask.setCanDesign(Boolean.valueOf(this.definitionAuthority.canModeling(task.getKey())));
                uTask.setCreateUserName(task.getCreateUserName());
                uTask.setCreateTime(task.getCreateTime());
                try {
                    uTask.setTaskPlanPublishObject(this.taskPlanPublishExternalService.queryPlanPublishOfTask(task.getKey()));
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                uTask.setType(task.getTaskType().toString());
                canReadTaskList.add(uTask);
            }
        });
        return AnaUtils.initReturn(canReadTaskList);
    }

    @Override
    public ReturnObject getSecurityFormularRuntimeTasks(String key) {
        ArrayList canReadTaskList = new ArrayList();
        List taskList = this.iRunTimeViewController.getAllReportTaskDefines();
        taskList.addAll(this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_SURVEY));
        taskList.addAll(this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_ANALYSIS));
        taskList.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).forEach(task -> {
            if (this.definitionAuthority.canReadTask(task.getKey())) {
                UReportTaskL uTask = new UReportTaskL();
                uTask.setKey(task.getKey());
                uTask.setCode(task.getTaskCode());
                uTask.setTitle(task.getTitle());
                uTask.setCanDesign(Boolean.valueOf(this.definitionAuthority.canModeling(task.getKey())));
                uTask.setCreateUserName(task.getCreateUserName());
                uTask.setCreateTime(task.getCreateTime());
                try {
                    uTask.setTaskPlanPublishObject(this.taskPlanPublishExternalService.queryPlanPublishOfTask(task.getKey()));
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                uTask.setType(task.getTaskType().toString());
                canReadTaskList.add(uTask);
            }
        });
        return AnaUtils.initReturn(canReadTaskList);
    }

    private List<TempDesignTaskDefine> getFilterDesignTask(String templateKey, List<DesignTaskDefine> designTaskDefines) throws Exception {
        int minSercurityLevel = this.getMinSercurityLevel(templateKey);
        HashMap<String, String> taskSecurityMap = new HashMap<String, String>();
        List keys = designTaskDefines.stream().map(IBaseMetaItem::getKey).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        for (String key : keys) {
            String securityLevel = this.iTaskOptionController.getValue(key, "SL_LEVEL");
            if (Integer.valueOf(securityLevel) > minSercurityLevel) continue;
            taskSecurityMap.put(key, securityLevel);
        }
        HashMap securityMap = new HashMap();
        ArrayList<TempDesignTaskDefine> tempDesignTaskDefineList = new ArrayList<TempDesignTaskDefine>();
        for (TaskDefine taskDefine : designTaskDefines) {
            TempDesignTaskDefine tempDesignTaskDefine = new TempDesignTaskDefine();
            if (taskDefine.getTaskType().equals((Object)TaskType.TASK_TYPE_DEFAULT)) {
                tempDesignTaskDefine.setTitle(taskDefine.getTitle());
            } else {
                if (!taskSecurityMap.containsKey(taskDefine.getKey())) continue;
                String sercurityLevel = (String)taskSecurityMap.get(taskDefine.getKey());
                String sercurityTitle = securityMap.getOrDefault(sercurityLevel, this.iSecretLevelService.getSecretLevelItem(taskDefine.getKey()).getTitle());
                tempDesignTaskDefine.setTitle("[" + taskDefine.getTaskCode() + "]" + taskDefine.getTitle() + "[" + sercurityTitle + "]");
            }
            tempDesignTaskDefine.setKey(taskDefine.getKey());
            tempDesignTaskDefine.setCode(taskDefine.getTaskCode());
            tempDesignTaskDefine.setOrder(taskDefine.getOrder());
            tempDesignTaskDefine.setCreateUserName(taskDefine.getCreateUserName());
            tempDesignTaskDefine.setCreateTime(taskDefine.getCreateTime());
            tempDesignTaskDefine.setType(taskDefine.getTaskType());
            tempDesignTaskDefineList.add(tempDesignTaskDefine);
        }
        return tempDesignTaskDefineList;
    }

    @Override
    public String getModelPathByKey(String key, String type) throws Exception {
        final AnalysisReportDefine analysisReportDefine = this.analysisHelper.getListByKey(key);
        if (analysisReportDefine == null) {
            return null;
        }
        boolean canQueryModal = this.authProvider.canReadModal(key, AnalysisReportResourceType.TEMPLATE);
        if (!canQueryModal) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NR_ANALYSISREPORT_EXCEPTION_401);
        }
        List<AnalysisTemp> resultList = this.analysisReportDefineToAnalysisTemp((List<AnalysisReportDefine>)new ArrayList<AnalysisReportDefine>(){
            {
                this.add(analysisReportDefine);
            }
        });
        AnalysisTemp result = resultList.get(0);
        try {
            AnalysisReportGroupDefine groupDefine;
            String parent = result.getGroupKey();
            String fullPath = null;
            fullPath = "KEY".equals(type) ? " / " + parent : " / " + result.getTitle();
            while (!"".equals(parent) && !"0".equals(parent) && (groupDefine = this.analysisHelper.getGroupByKey(parent)) != null) {
                fullPath = "KEY".equals(type) ? " / " + groupDefine.getKey() + fullPath : " / " + groupDefine.getTitle() + fullPath;
                parent = groupDefine.getParentgroup();
            }
            return fullPath;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<AnalysisTemp> fuzzyQuery(String keyWord) throws Exception {
        List<AnalysisReportGroupDefine> reportGroupDefines = this.analysisReportGroupService.fuzzyQuery(keyWord);
        List<AnalysisTemp> groupTemps = this.analysisReportGroupDefineToAnalysisTemp(reportGroupDefines);
        List<AnalysisReportDefine> reportDefines = this.analysisReportService.fuzzyQuery(keyWord);
        List<AnalysisTemp> reportTemps = this.analysisReportDefineToAnalysisTemp(reportDefines);
        groupTemps.addAll(reportTemps);
        return groupTemps;
    }

    @Override
    public boolean checkGenCatalogCompleted(String templateKey, String versionKey) throws Exception {
        if (!StringUtils.isEmpty((String)versionKey)) {
            return this.analyBigDataService.checkGenCatalogCompleted(versionKey);
        }
        return this.chapterService.checkGenCatalogCompleted(templateKey);
    }

    @Override
    public List<ReportCatalogItem> loadCatalogTree(CatalogVo catalogVo) throws Exception {
        Date dbCatalogUpdateTime = this.getDbCatalogUpdateTime(catalogVo);
        if (dbCatalogUpdateTime == null) {
            this.rebuildCatalog(catalogVo);
        } else {
            Date cacheCatalogUpdateTime = this.getCacheCatalogUpdateTime(catalogVo);
            if (cacheCatalogUpdateTime == null || dbCatalogUpdateTime.compareTo(cacheCatalogUpdateTime) != 0) {
                catalogVo.setCatalogUpdateTime(dbCatalogUpdateTime);
                this.rebuildCatalogCache(catalogVo);
            }
        }
        return CatalogCacheUtil.getCacheCatalog(catalogVo, "top");
    }

    public Date getCacheCatalogUpdateTime(CatalogVo catalogVo) {
        String userCatalogCacheKey;
        NedisCache cache = this.cacheManager.getCache("RCC-REPORT");
        Map catalogCache = (Map)cache.get(userCatalogCacheKey = CatalogCacheUtil.getUserCatalogCacheKey(catalogVo.getAuthorization()), Map.class);
        if (catalogCache == null) {
            return null;
        }
        String updateTimeCacheKey = CatalogCacheUtil.getCatalogUpdateTimeCacheKey(catalogVo.getTemplateKey(), catalogVo.getVersionKey());
        return (Date)catalogCache.get(updateTimeCacheKey);
    }

    public void rebuildCatalog(CatalogVo catalogVo) throws Exception {
        Map<String, List<ReportCatalogItem>> map = this.buidlCatalogTree(catalogVo.getTemplateKey(), catalogVo.getVersionKey());
        catalogVo.setCatalogUpdateTime(new Date());
        this.updateDbCataLog(catalogVo, map);
        CatalogCacheUtil.setCatalogCahe(catalogVo, map);
    }

    public void rebuildCatalogCache(CatalogVo catalogVo) throws Exception {
        String dbCatalog = this.getDbCatalog(catalogVo);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<ReportCatalogItem>> map = new HashMap<String, List<ReportCatalogItem>>();
        if (StringUtils.isNotEmpty((String)dbCatalog)) {
            map = (Map)objectMapper.readValue(dbCatalog, (TypeReference)new TypeReference<Map<String, List<ReportCatalogItem>>>(){});
        }
        CatalogCacheUtil.setCatalogCahe(catalogVo, map);
    }

    public Date getDbCatalogUpdateTime(CatalogVo catalogVo) throws Exception {
        String versionKey = catalogVo.getVersionKey();
        if (StringUtils.isNotEmpty((String)versionKey)) {
            String extendedFieldValue = (String)this.versionExtendDataService.getExtendFieldValue(versionKey, "catalogupdatetime");
            return AnaUtils.strToDate(extendedFieldValue);
        }
        Date catalogUpdateTime = this.analysisHelper.getCatalogUpdateTime(catalogVo.getTemplateKey());
        return catalogUpdateTime;
    }

    public String getDbCatalog(CatalogVo catalogVo) throws Exception {
        String versionKey = catalogVo.getVersionKey();
        if (StringUtils.isNotEmpty((String)versionKey)) {
            return (String)this.versionExtendDataService.getExtendFieldValue(versionKey, "catalog");
        }
        AnalysisReportDefine reportDefine = this.analysisHelper.getListByKey(catalogVo.getTemplateKey());
        return reportDefine.getAtCatalog();
    }

    public void updateDbCataLog(CatalogVo catalogVo, Map<String, List<ReportCatalogItem>> map) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String catalog = objectMapper.writeValueAsString(map);
        if (StringUtils.isNotEmpty((String)catalogVo.getVersionKey())) {
            ReportVersionExtendData versionExtend = this.versionExtendDao.get(catalogVo.getVersionKey());
            JSONObject extendJson = new JSONObject(versionExtend.getAvExtData());
            extendJson.put("catalog", (Object)catalog);
            extendJson.put("catalogupdatetime", (Object)catalogVo.getCatalogUpdateTime());
            versionExtend.setAvExtData(extendJson.toString());
            this.versionExtendDao.update(versionExtend);
        } else {
            AnalysisReportDefine reportDefine = this.analysisHelper.getListByKey(catalogVo.getTemplateKey());
            reportDefine.setAtCatalog(catalog);
            reportDefine.setAtCatalogUpdatetime(catalogVo.getCatalogUpdateTime());
            this.analysisHelper.updateModel(reportDefine);
        }
    }

    @Override
    public Map<String, List<ReportCatalogItem>> buidlCatalogTree(String templateKey, String versionKey) throws Exception {
        LinkedList<ReportCatalogItem> reportCatalogItems = new LinkedList<ReportCatalogItem>();
        this.collectCatalogItems(reportCatalogItems, templateKey, versionKey);
        CatalogGenHelper catalogGenHelper = new CatalogGenHelper();
        LinkedList<ReportCatalogItem> catalogTrees = new LinkedList<ReportCatalogItem>();
        for (int i = 0; i < reportCatalogItems.size(); ++i) {
            ReportCatalogItem catalogItem = (ReportCatalogItem)reportCatalogItems.get(i);
            catalogGenHelper.buildCatalogTree(catalogItem, catalogTrees);
        }
        LinkedHashMap<String, List<ReportCatalogItem>> parentIdCatalogs = new LinkedHashMap<String, List<ReportCatalogItem>>();
        parentIdCatalogs.put("top", new LinkedList());
        catalogGenHelper.genParentIdCatalogs(catalogTrees, parentIdCatalogs);
        return parentIdCatalogs;
    }

    @Override
    public List<ReportCatalogItem> getLocateCatalogTree(String templateKey, String versionKey, String key) throws Exception {
        LinkedList<ReportCatalogItem> reportCatalogItems = new LinkedList<ReportCatalogItem>();
        this.collectCatalogItems(reportCatalogItems, templateKey, versionKey);
        ReportCatalogItem locateCatalog = null;
        CatalogGenHelper catalogGenHelper = new CatalogGenHelper();
        AbstractList catalogTrees = new LinkedList<ReportCatalogItem>();
        for (int i = 0; i < reportCatalogItems.size(); ++i) {
            ReportCatalogItem catalogItem = (ReportCatalogItem)reportCatalogItems.get(i);
            if (catalogItem.getId().equals(key)) {
                locateCatalog = catalogItem;
            }
            catalogGenHelper.append2CatalogTree(catalogItem, catalogTrees, "top");
        }
        Set<String> path = locateCatalog.getPath();
        path.add(key);
        for (ReportCatalogItem reportCatalogItem : catalogTrees) {
            if (!path.contains(reportCatalogItem.getId())) continue;
            catalogTrees = new ArrayList();
            catalogTrees.add((ReportCatalogItem)reportCatalogItem);
            break;
        }
        if (path.size() == 2) {
            ((ReportCatalogItem)catalogTrees.get(0)).setChildren(new ArrayList<ReportCatalogItem>());
            return catalogTrees;
        }
        path.remove(key);
        catalogGenHelper.buildLocateCatalogTree(((ReportCatalogItem)catalogTrees.get(0)).getChildren(), path);
        return catalogTrees;
    }

    private void collectCatalogItems(List<ReportCatalogItem> reportCatalogItems, String templateKey, String versionKey) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        if (StringUtils.isEmpty((String)versionKey)) {
            List<ReportChapterDefine> chapterDefines = this.chapterService.queryChapterByModelId(templateKey);
            for (int i = 0; i < chapterDefines.size(); ++i) {
                ReportChapterDefine chapterDefine = chapterDefines.get(i);
                if (StringUtils.isEmpty((String)chapterDefine.getCatalog())) continue;
                reportCatalogItems.addAll((Collection)objectMapper.readValue(chapterDefine.getCatalog(), (TypeReference)new TypeReference<List<ReportCatalogItem>>(){}));
            }
        } else {
            Map<String, String> catalogMap = this.analyBigDataService.getCatalogMap(versionKey);
            if (CollectionUtils.isEmpty(catalogMap)) {
                return;
            }
            ReportVersionExtendData extendData = this.versionExtendDao.get(versionKey);
            JSONObject extendDataJson = new JSONObject(extendData.getAvExtData());
            String chapter = extendDataJson.getString("EXTEND_CHAPTER_INFO");
            List reportChapterDefines = (List)objectMapper.readValue(chapter, (TypeReference)new TypeReference<List<ReportChapterDefine>>(){});
            for (int i = 0; i < reportChapterDefines.size(); ++i) {
                ReportChapterDefine chapterDefine = (ReportChapterDefine)reportChapterDefines.get(i);
                if (!catalogMap.containsKey(chapterDefine.getArcKey())) continue;
                reportCatalogItems.addAll((Collection)objectMapper.readValue(catalogMap.get(chapterDefine.getArcKey()), (TypeReference)new TypeReference<List<ReportCatalogItem>>(){}));
            }
        }
    }

    @Override
    public List<ReportChapterDefine> getVersionChapterList(String versionKey) throws Exception {
        ReportVersionExtendData reportVersionExtendData = this.versionExtendDao.get(versionKey);
        JSONObject extendData = new JSONObject(reportVersionExtendData.getAvExtData());
        String chapter = extendData.getString("EXTEND_CHAPTER_INFO");
        ObjectMapper objectMapper = new ObjectMapper();
        return (List)objectMapper.readValue(chapter, (TypeReference)new TypeReference<List<ReportChapterDefine>>(){});
    }

    @Override
    public void updateTempModifiedTime(String key, Date data) {
        this.analysisReportService.updateTemplateLastModifiedTime(key, data);
    }

    @Override
    public ReturnObject queryVarDim(String jsonDefine) {
        JSONObject json = new JSONObject(jsonDefine);
        String varType = json.getString("varType");
        HashMap<String, Object> map = new HashMap<String, Object>();
        block0: for (IExprParser exprParser : this.exprParsers) {
            if (varType.contains(exprParser.getName())) {
                map.put(exprParser.getName(), exprParser.queryEntitys(jsonDefine));
                continue;
            }
            List<String> resourceVarNames = exprParser.getResourceVarNames();
            if (CollectionUtils.isEmpty(resourceVarNames)) continue;
            for (String resourceVarName : resourceVarNames) {
                if (!varType.contains(resourceVarName)) continue;
                map.put(resourceVarName, exprParser.queryEntitys(jsonDefine));
                continue block0;
            }
        }
        return AnaUtils.initReturn(map);
    }
}

