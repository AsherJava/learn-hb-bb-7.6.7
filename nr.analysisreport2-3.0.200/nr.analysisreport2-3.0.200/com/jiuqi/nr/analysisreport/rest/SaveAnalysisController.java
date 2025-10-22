/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.StreamReadConstraints
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.JsonObject
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.authz2.SecurityLevel
 *  com.jiuqi.np.authz2.SecurityLevelDTO
 *  com.jiuqi.np.authz2.impl.service.SystemIdentityServiceUserMode
 *  com.jiuqi.np.authz2.service.SecurityLevelService
 *  com.jiuqi.np.authz2.vo.SecurityLevelV
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 *  com.jiuqi.nvwa.authority.securitylevel.SecurityLevelController
 *  com.jiuqi.nvwa.authority.vo.ResultObject
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.DataSetSystemDefine
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.controller.DataSetSystemOptionController
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeProviderService
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.IAnalyzeResourceDataProvider
 *  com.jiuqi.nvwa.dataanalyze.api.ResourceTreeApi
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  com.jiuqi.nvwa.datav.dashboard.domain.WidgetItem
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager
 *  io.netty.util.internal.StringUtil
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.csv.CSVFormat
 *  org.apache.commons.csv.CSVParser
 *  org.apache.commons.csv.CSVPrinter
 *  org.apache.commons.csv.CSVRecord
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.analysisreport.rest;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.authz2.SecurityLevel;
import com.jiuqi.np.authz2.SecurityLevelDTO;
import com.jiuqi.np.authz2.impl.service.SystemIdentityServiceUserMode;
import com.jiuqi.np.authz2.service.SecurityLevelService;
import com.jiuqi.np.authz2.vo.SecurityLevelV;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.analysisreport.async.CatalogGenerateThread;
import com.jiuqi.nr.analysisreport.async.executor.NrArBatchExportAsyncTaskExecutor;
import com.jiuqi.nr.analysisreport.authority.bean.AnalysisTable;
import com.jiuqi.nr.analysisreport.authority.bean.RequestTypeEnum;
import com.jiuqi.nr.analysisreport.biservice.bi.BIIntegrationHelper;
import com.jiuqi.nr.analysisreport.biservice.chart.ChartTreeNode;
import com.jiuqi.nr.analysisreport.chapter.bean.CatalogVo;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportCatalogItem;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.service.IChapterService;
import com.jiuqi.nr.analysisreport.common.NrAnalysisErrorEnum;
import com.jiuqi.nr.analysisreport.facade.AnalyVersionDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.helper.GridDataHelper;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.securitylevel.SecurityLevelHelper;
import com.jiuqi.nr.analysisreport.service.IAnalysisReport2WordService;
import com.jiuqi.nr.analysisreport.service.INrArBatchExportServie;
import com.jiuqi.nr.analysisreport.service.SaveAnalysis;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.utils.CatalogCacheUtil;
import com.jiuqi.nr.analysisreport.utils.CheckPermissionUtil;
import com.jiuqi.nr.analysisreport.utils.CipherUtil;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.utils.SerializeUtils;
import com.jiuqi.nr.analysisreport.utils.WordUtil;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportExportVO;
import com.jiuqi.nr.analysisreport.vo.ReportFormulaVo;
import com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO;
import com.jiuqi.nr.analysisreport.vo.ReportVersionGeneratorVo;
import com.jiuqi.nr.analysisreport.vo.ReportVersionVo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import com.jiuqi.nvwa.authority.securitylevel.SecurityLevelController;
import com.jiuqi.nvwa.authority.vo.ResultObject;
import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.DataSetSystemDefine;
import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.controller.DataSetSystemOptionController;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeProviderService;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResource;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.IAnalyzeResourceDataProvider;
import com.jiuqi.nvwa.dataanalyze.api.ResourceTreeApi;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import com.jiuqi.nvwa.datav.dashboard.domain.WidgetItem;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.manager.DashboardManager;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags={"\u5206\u6790\u62a5\u544a"})
@RestController
@RequestMapping(value={"/analysis"})
public class SaveAnalysisController {
    @Autowired
    SaveAnalysis saveAnalysis;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    ResourceTreeApi resourceTreeApi;
    @Autowired
    BIIntegrationHelper biIntegrationHelper;
    @Resource
    private ISecretLevelService secretLevelService;
    @Resource
    private SecurityLevelService securityLevelService;
    @Autowired
    DashboardManager dashboardManager;
    @Autowired
    DataSetSystemOptionController dataSetSystemOptionController;
    @Autowired
    private SystemIdentityServiceUserMode systemIdentityService;
    @Autowired
    private DataAnalyzeProviderService providerService;
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;
    @Autowired
    private DefaultAuthQueryService authQueryService;
    @Resource
    private SecurityLevelController securityLevelController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private SecurityLevelHelper securityLevelHelper;
    @Autowired
    private List<IAnalysisReport2WordService> analysisReport2WordServices;
    private Logger logger = LoggerFactory.getLogger(SaveAnalysisController.class);
    private String analysisTableType = "com.jiuqi.nvwa.quickreport.business";
    private String dashboardType = "com.jiuqi.nvwa.datav.dashboard";
    private static final String ROOT_ID = "root";
    @Value(value="${spring.extend.jackson.maxstringlen:167772162}")
    private int jsonMaxStringLength;
    @Autowired
    private GridDataHelper gridDataHelper;
    @Autowired
    private IChapterService chapterService;
    private NedisCacheManager cacheManager;
    @Autowired
    private NpApplication npApplication;
    @Value(value="${spring.redis.enabled:false}")
    private boolean redisEnable;
    @Autowired
    private IAnalysisReportEntityService analysisReportEntityService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private INrArBatchExportServie iNrArBatchExportServie;
    private static final String TOP_GROUP_KEY = "top";
    @Autowired
    BIIntegrationHelper helper;
    @Resource
    AnalysisHelper analysisHelper;
    @Autowired
    private USelectorResultSet uSelectorResultSet;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
    }

    @RequestMapping(value={"/menuType"}, method={RequestMethod.GET})
    @ResponseBody
    public String getMenuType() throws Exception {
        StringBuffer result = new StringBuffer("");
        result.append("[{\"key\":\"analysisModel\",\"code\":\"JQTABDA\",\"title\":\"\u62a5\u544a\u6a21\u677f\",\"canDesign\":true},{\"key\":\"analysisReport\",\"code\":\"JQTABDB\",\"title\":\"\u5206\u6790\u62a5\u544a\",\"canDesign\":true}]");
        return result.toString();
    }

    @RequestMapping(value={"/api/getAnalysisTree/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getAnalysisTree(@PathVariable(value="key") String key) throws Exception {
        try {
            return this.saveAnalysis.getAnalysisTree(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_180);
        }
    }

    @RequestMapping(value={"/getAnalysisModelList"}, method={RequestMethod.GET})
    @ResponseBody
    public String getList() throws Exception {
        try {
            return this.saveAnalysis.getList();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_100);
        }
    }

    @RequestMapping(value={"/api/getAnalysisModelList/{groupKey}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getListByGroupKey(@PathVariable(value="groupKey") String key) throws Exception {
        if (TOP_GROUP_KEY.equals(key)) {
            return this.saveAnalysis.queryAllTemplates();
        }
        try {
            return this.saveAnalysis.getAllTemplatesByGroupKey(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_100);
        }
    }

    @RequestMapping(value={"/api/getAnalysisModel/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getListByKey(@PathVariable(value="key") String key, @RequestParam(value="arcKey", required=false) String arcKey) throws Exception {
        if (null == key || "".equals(key)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            ReturnObject reportDefineRes = this.saveAnalysis.getListByKey(key);
            if (StringUtils.isEmpty((CharSequence)arcKey)) {
                return reportDefineRes;
            }
            Map obj = (Map)reportDefineRes.getObj();
            AnalysisTemp analysisTemp = (AnalysisTemp)obj.get("result");
            ReportChapterDefine chapter = this.chapterService.getChapterById(arcKey);
            analysisTemp.setData(chapter.getArcData());
            analysisTemp.setArcKey(arcKey);
            return reportDefineRes;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_101);
        }
    }

    @RequestMapping(value={"/api/getAnalysisModelFullPath/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getAnalysisModelFullPathByKey(@PathVariable(value="key") String key) throws Exception {
        if (null == key || "".equals(key)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            return this.saveAnalysis.getAnalysisModelFullPathByKey(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_101);
        }
    }

    @RequiresPermissions(value={"nr:analysisreport:template"})
    @RequestMapping(value={"/api/analysis/a"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject insertModel(@RequestBody String defineJson) throws Exception {
        if (null == defineJson) {
            return AnaUtils.initReturn(false, "\u6a21\u677f\u4fe1\u606f\u5f02\u5e38");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tableidNode = mapper.readTree(defineJson);
        if (null != tableidNode.get("key")) {
            return AnaUtils.initReturn(false, "\u6a21\u677fkey\u975e\u7a7a\uff0c\u521b\u5efa\u5931\u8d25");
        }
        if (null == tableidNode.get("groupKey")) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f\u5206\u7ec4");
        }
        if (!AnaUtils.nameIsEmpty(tableidNode, "title", true)) {
            return AnaUtils.initReturn(false, "\u672a\u586b\u5199\u6a21\u677f\u6807\u9898");
        }
        try {
            return this.saveAnalysis.insertModel(tableidNode);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_102);
        }
    }

    @RequiresPermissions(value={"nr:analysisreport:template"})
    @RequestMapping(value={"/api/analysis/u"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject updateModel(@RequestBody String defineJson) throws Exception {
        if (null == defineJson) {
            return AnaUtils.initReturn(false, "\u6a21\u677f\u4fe1\u606f\u5f02\u5e38");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().setStreamReadConstraints(StreamReadConstraints.builder().maxStringLength(this.jsonMaxStringLength).build());
        JsonNode tableidNode = mapper.readTree(defineJson);
        try {
            return this.saveAnalysis.updateModel(tableidNode);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_103);
        }
    }

    @RequiresPermissions(value={"nr:analysisreport:template"})
    @RequestMapping(value={"/api/analysis/d/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject deleteModel(@PathVariable(value="key") String key) throws Exception {
        if (StringUtils.isEmpty((CharSequence)key)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            return this.saveAnalysis.deleteModel(key);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_104);
        }
    }

    @RequestMapping(value={"/api/analysisGroupList"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getGroupList() throws Exception {
        try {
            return this.saveAnalysis.getGroupList();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_120);
        }
    }

    @RequestMapping(value={"/api/analysisGroupListByRoot"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getGroupListByRoot() throws Exception {
        try {
            return this.saveAnalysis.getGroupListByRoot();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_120);
        }
    }

    @RequestMapping(value={"/api/analysisGroupList/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getGroupByKey(@PathVariable(value="key") String key) throws Exception {
        if (StringUtils.isEmpty((CharSequence)key)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f\u5206\u7ec4");
        }
        try {
            return this.saveAnalysis.getGroupByKey(key);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_121);
        }
    }

    @RequiresPermissions(value={"nr:analysisreport:group"})
    @RequestMapping(value={"/api/analysisGroup/a"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject insertGroup(@RequestBody String defineJson) throws Exception {
        if (null == defineJson) {
            return AnaUtils.initReturn(false, "\u5206\u7ec4\u4fe1\u606f\u5f02\u5e38");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tableidNode = mapper.readTree(defineJson);
        if (null != tableidNode.get("key")) {
            return AnaUtils.initReturn(false, "\u5206\u7ec4Key\u975e\u7a7a\uff0c\u521b\u5efa\u5931\u8d25");
        }
        if (!AnaUtils.nameIsEmpty(tableidNode, "title", true)) {
            return AnaUtils.initReturn(false, "\u672a\u586b\u5199\u5206\u7ec4\u6807\u9898");
        }
        try {
            return this.saveAnalysis.insertGroup(tableidNode);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_122);
        }
    }

    @RequiresPermissions(value={"nr:analysisreport:group"})
    @RequestMapping(value={"/api/analysisGroup/u"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject updateGroup(@RequestBody String defineJson) throws Exception {
        if (null == defineJson) {
            return AnaUtils.initReturn(false, "\u5206\u7ec4\u4fe1\u606f\u5f02\u5e38");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tableidNode = mapper.readTree(defineJson);
        if (!AnaUtils.nameIsEmpty(tableidNode, "key", true)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u5206\u7ec4");
        }
        if (!AnaUtils.nameIsEmpty(tableidNode, "title", true)) {
            return AnaUtils.initReturn(false, "\u672a\u586b\u5199\u5206\u7ec4\u6807\u9898");
        }
        try {
            return this.saveAnalysis.updateGroup(tableidNode);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_123);
        }
    }

    @RequiresPermissions(value={"nr:analysisreport:group"})
    @RequestMapping(value={"/api/analysisGroup/d/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject deleteGroup(@PathVariable(value="key") String key) throws Exception {
        if (StringUtils.isEmpty((CharSequence)key)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u5206\u7ec4");
        }
        try {
            return this.saveAnalysis.deleteGroup(key);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_124);
        }
    }

    @RequestMapping(value={"api/analysisModelTransposition/{key1}/{key2}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject analysisModelTransposition(@PathVariable(value="key1") String key1, @PathVariable(value="key2") String key2) throws Exception {
        if (StringUtils.isEmpty((CharSequence)key1) || StringUtils.isEmpty((CharSequence)key2)) {
            return AnaUtils.initReturn(false, "\u6a21\u677f\u79fb\u52a8\u53c2\u6570\u5f02\u5e38");
        }
        try {
            return this.saveAnalysis.analysisModelTransposition(key1, key2);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_181);
        }
    }

    @RequestMapping(value={"api/analysisGroupTransposition/{key1}/{key2}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject analysisGroupTransposition(@PathVariable(value="key1") String key1, @PathVariable(value="key2") String key2) throws Exception {
        if (StringUtils.isEmpty((CharSequence)key1) || StringUtils.isEmpty((CharSequence)key2)) {
            return AnaUtils.initReturn(false, "\u5206\u7ec4\u79fb\u52a8\u53c2\u6570\u5f02\u5e38");
        }
        try {
            return this.saveAnalysis.analysisGroupTransposition(key1, key2);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_182);
        }
    }

    @RequiresPermissions(value={"nr:analysisreport:generator"})
    @RequestMapping(value={"api/analysisReport/generator"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject analysisReportGenerator(@RequestBody ReportGeneratorVO reportGeneratorVO, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (null == reportGeneratorVO) {
            throw new Exception("\u751f\u6210\u62a5\u544a\u53c2\u6570\u5f02\u5e38");
        }
        if (null == reportGeneratorVO.getKey()) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        CheckPermissionUtil.checkReadPermission(reportGeneratorVO.getKey());
        Iterator<ReportBaseVO.UnitDim> chooseUnits = reportGeneratorVO.getChooseUnits().iterator();
        if (!chooseUnits.hasNext() && !StringUtils.isEmpty((CharSequence)reportGeneratorVO.getPeriodStr())) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u89c6\u56fe");
        }
        if (StringUtils.isEmpty((CharSequence)reportGeneratorVO.getPeriodStr()) && chooseUnits.hasNext()) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u65e5\u671f");
        }
        if (null == reportGeneratorVO.getCurTimeStamp()) {
            return AnaUtils.initReturn(false, "\u672a\u4f20\u9012\u5f53\u524d\u65f6\u95f4\u6233");
        }
        String contents = null;
        if (reportGeneratorVO.getContents() != null) {
            contents = reportGeneratorVO.getContents();
        }
        WordUtil wordUtil = new WordUtil();
        String newword = null;
        LockCacheUtil.putLockCacheKey(request, reportGeneratorVO);
        try {
            newword = !StringUtils.isEmpty((CharSequence)contents) ? wordUtil.createHtml(reportGeneratorVO, contents) : wordUtil.createHtml(reportGeneratorVO, new String[0]);
        }
        catch (Exception e) {
            this.logger.error("\u751f\u6210\u62a5\u544a\u5931\u8d25", (Object)e.getMessage());
            return AnaUtils.initReturn(false, "\u751f\u6210\u62a5\u544a\u5931\u8d25");
        }
        ReturnObject returnObject = this.saveAnalysis.analysisReportGenerator(reportGeneratorVO);
        AnalysisTemp analysisTemp = (AnalysisTemp)returnObject.getObj();
        analysisTemp.setData(newword);
        analysisTemp.setStringdate(reportGeneratorVO.getCurTimeStamp());
        AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a", "\u751f\u6210\u62a5\u544a\uff1a" + analysisTemp.getTitle(), AnalysisReportLogHelper.LOGLEVEL_INFO);
        return returnObject;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RequiresPermissions(value={"nr:analysisreport:export"})
    @RequestMapping(value={"api/analysisReportToWord"}, method={RequestMethod.POST})
    @ResponseBody
    public void analysisReportToWord(@RequestBody ReportExportVO reportExportParam, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (null == reportExportParam) {
            throw new Exception("\u751f\u6210\u62a5\u544a\u53c2\u6570\u5f02\u5e38");
        }
        if (null == reportExportParam.getKey()) {
            throw new Exception("\u672a\u9009\u62e9\u6a21\u677f");
        }
        CheckPermissionUtil.checkReadPermission(reportExportParam.getKey());
        Iterator<ReportBaseVO.UnitDim> unitDimIterator = reportExportParam.getChooseUnits().iterator();
        if (!unitDimIterator.hasNext() && !StringUtils.isEmpty((CharSequence)reportExportParam.getPeriodStr())) {
            throw new Exception("\u672a\u9009\u62e9\u89c6\u56fe");
        }
        if (StringUtils.isEmpty((CharSequence)reportExportParam.getPeriodStr()) && unitDimIterator.hasNext()) {
            throw new Exception("\u672a\u9009\u62e9\u65e5\u671f");
        }
        String taskId = !StringUtils.isEmpty((CharSequence)reportExportParam.getTaskid()) ? reportExportParam.getTaskid().replaceAll("[^0-9a-zA-Z-]", "") : UUID.randomUUID().toString();
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
        asyncTaskMonitor.progressAndMessage(0.05, "\u6b63\u5728\u5bfc\u51fa\u5206\u6790\u62a5\u544a");
        LockCacheUtil.putLockCacheKey(request, reportExportParam);
        try {
            if (this.analysisReport2WordServices.size() == 1) {
                this.analysisReport2WordServices.get(0).report2Word(response, reportExportParam, (AsyncTaskMonitor)asyncTaskMonitor);
                return;
            }
            this.analysisReport2WordServices.sort(new Comparator<IAnalysisReport2WordService>(){

                @Override
                public int compare(IAnalysisReport2WordService o1, IAnalysisReport2WordService o2) {
                    return o2.getOrder() - o1.getOrder();
                }
            });
            this.analysisReport2WordServices.get(0).report2Word(response, reportExportParam, (AsyncTaskMonitor)asyncTaskMonitor);
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u6a21\u677f\u5185\u5bb9\u4e3a\u7a7a\uff01\u8bf7\u586b\u5199\u6a21\u677f\u5185\u5bb9", e);
        }
        finally {
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.finish("\u5206\u6790\u62a5\u544a\u5bfc\u51fa\u5b8c\u6210\u3002", (Object)JsonUtil.objectToJson((Object)new BatchReturnInfo()));
            }
        }
    }

    @RequestMapping(value={"api/getReportList/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getReportList(@PathVariable(value="key") String key) throws Exception {
        try {
            return this.saveAnalysis.chooseReportTree(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_184);
        }
    }

    /*
     * WARNING - void declaration
     */
    @RequestMapping(value={"api/getChart"}, method={RequestMethod.GET})
    @ResponseBody
    public String getChart(@RequestParam String group, @RequestParam String type, @RequestParam String requestType, HttpServletRequest request, @RequestParam String modelId) throws Exception {
        ArrayList<ITree<AnalysisTable>> nodeList = new ArrayList<ITree<AnalysisTable>>();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> queryTypes = new ArrayList<String>();
        queryTypes.add(this.dashboardType);
        Integer minSercurityLevel = Integer.MAX_VALUE;
        if (this.securityLevelService.isSecurityLevelEnabled()) {
            minSercurityLevel = this.getMinSercurityLevel(modelId);
        }
        if (StringUtil.isNullOrEmpty((String)group) || "null".equals(group)) {
            DataSetSystemDefine dataSetSystemOption;
            List<ResourceTreeNode> localTree = this.getChildren(ROOT_ID, false, true, queryTypes);
            ResourceTreeNode rootLocalNode = null;
            if (localTree != null && localTree.size() > 0) {
                rootLocalNode = localTree.get(0);
            }
            if (null == (dataSetSystemOption = this.dataSetSystemOptionController.getDataSetSystemOption()) || StringUtil.isNullOrEmpty((String)dataSetSystemOption.getBiAddress())) {
                this.createNode(rootLocalNode, null, nodeList, group, true, "\u56fe\u8868");
                return objectMapper.writeValueAsString(nodeList);
            }
            String s = this.dataSetSystemOptionController.connectivityTest(dataSetSystemOption.getBiAddress(), dataSetSystemOption.getCAIdentify(), dataSetSystemOption.getIdentify(), request);
            if (!"false".equals(s)) {
                void var15_23;
                List<ITree<ChartTreeNode>> biTree = this.saveAnalysis.getRootGroup("bi");
                Object var15_21 = null;
                if (biTree != null && biTree.size() > 0) {
                    ITree<ChartTreeNode> iTree = biTree.get(0);
                }
                this.createNode(rootLocalNode, (ITree<ChartTreeNode>)var15_23, nodeList, group, true, "\u56fe\u8868");
            } else {
                this.createNode(rootLocalNode, null, nodeList, group, true, "\u56fe\u8868");
            }
            return objectMapper.writeValueAsString(nodeList);
        }
        if (RequestTypeEnum.REMOTE.toString().equals(requestType)) {
            List<ITree<ChartTreeNode>> bitree = this.helper.getBiTree(group, type, minSercurityLevel);
            bitree.stream().forEach(tree -> this.createNode(null, (ITree<ChartTreeNode>)tree, nodeList, null, false, null));
            return objectMapper.writeValueAsString(nodeList);
        }
        if (RequestTypeEnum.LOCAL.toString().equals(requestType)) {
            String[] split = group.split(";");
            String parent = split[split.length - 1];
            List<ResourceTreeNode> nodeInfo = this.getChildren(parent, false, true, queryTypes);
            ArrayList<ResourceTreeNode> resourceTreeNode = new ArrayList<ResourceTreeNode>();
            for (ResourceTreeNode resourceTreeNode2 : nodeInfo) {
                if (StringUtil.isNullOrEmpty((String)resourceTreeNode2.getSecurityLevel()) || "null".equals(resourceTreeNode2.getSecurityLevel())) {
                    resourceTreeNode.add(resourceTreeNode2);
                    continue;
                }
                if (Integer.parseInt(resourceTreeNode2.getSecurityLevel()) > minSercurityLevel) continue;
                resourceTreeNode.add(resourceTreeNode2);
            }
            resourceTreeNode.stream().forEach(treeNode -> this.createNode((ResourceTreeNode)treeNode, null, (List<ITree<AnalysisTable>>)nodeList, parent, false, "\u56fe\u8868"));
            if (type.equals(this.dashboardType)) {
                return objectMapper.writeValueAsString(this.getWidgetItems(parent));
            }
            for (ITree iTree : nodeList) {
                if (!iTree.isLeaf() || !((AnalysisTable)iTree.getData()).getType().equals(this.dashboardType)) continue;
                iTree.setLeaf(false);
            }
            return objectMapper.writeValueAsString(nodeList);
        }
        return null;
    }

    private List<ResourceTreeNode> getChildren(String parent, boolean privilegeWrite, boolean query, List<String> types) throws DataAnalyzeResourceException {
        if (StringUtils.isEmpty((CharSequence)parent)) {
            parent = ROOT_ID;
        }
        List children = null;
        if (query) {
            if (types == null || types.size() <= 0) {
                types = this.providerService.listResources().stream().filter(type -> type.supperQuery()).map(DataAnalyzeResource::type).collect(Collectors.toList());
            }
            children = this.resourceTreeNodeService.getChildren(parent, types);
        } else {
            children = this.resourceTreeNodeService.getChildren(parent);
        }
        return this.filterAuth(children, privilegeWrite ? "dataanalysis_write" : "dataanalysis_read");
    }

    private List<ResourceTreeNode> filterAuth(List<ResourceTreeNode> nodes, String privilegeId) {
        if (this.securityLevelService.isSecurityLevelEnabled()) {
            List systemSecurityLevels = this.securityLevelService.getSystemSecurityLevels();
            List<SecurityLevel> canAccessSecurityLevels = this.getCanAccessSecurityLevels(systemSecurityLevels);
            String userSecurityLevel = NpContextHolder.getContext().getUser().getSecuritylevel();
            if (StringUtil.isNullOrEmpty((String)userSecurityLevel) && CollectionUtils.isEmpty(canAccessSecurityLevels)) {
                canAccessSecurityLevels.add((SecurityLevel)systemSecurityLevels.get(0));
            }
            return nodes.stream().filter(node -> (node.isFolder() || this.canAccessSecurityLevel(canAccessSecurityLevels, node.getSecurityLevel())) && this.authQueryService.hasAuth(privilegeId, NpContextHolder.getContext().getIdentityId(), (Object)node.getGuid())).map(node -> this.fillIcon((ResourceTreeNode)node)).collect(Collectors.toList());
        }
        return nodes.stream().filter(node -> this.authQueryService.hasAuth(privilegeId, NpContextHolder.getContext().getIdentityId(), (Object)node.getGuid())).collect(Collectors.toList());
    }

    private List<SecurityLevel> getCanAccessSecurityLevels(List<SecurityLevel> allLevels) {
        ArrayList<SecurityLevel> levels = new ArrayList<SecurityLevel>();
        for (SecurityLevel level : allLevels) {
            if (!this.securityLevelService.canAccess(level)) continue;
            levels.add(level);
        }
        return levels;
    }

    private ResourceTreeNode fillIcon(ResourceTreeNode node) {
        IAnalyzeResourceDataProvider provider = this.providerService.getProvider(node.getType());
        if (provider != null) {
            node.setIcon(provider.getResourceIcon(node));
        }
        return node;
    }

    private boolean canAccessSecurityLevel(List<SecurityLevel> levels, String levelName) {
        if (StringUtils.isEmpty((CharSequence)levelName)) {
            return true;
        }
        for (SecurityLevel level : levels) {
            if (!levelName.equalsIgnoreCase(level.getName())) continue;
            return true;
        }
        return false;
    }

    @RequestMapping(value={"api/getQuickAnalysisTable"}, method={RequestMethod.GET})
    @ResponseBody
    public String getQuickAnalysisTable(@RequestParam String parent, @RequestParam String requestType, @RequestParam String modelId, HttpServletRequest request) throws Exception {
        ArrayList<ITree<AnalysisTable>> nodeList = new ArrayList<ITree<AnalysisTable>>();
        ArrayList<String> queryTypes = new ArrayList<String>();
        queryTypes.add(this.analysisTableType);
        Integer minSercurityLevel = Integer.MAX_VALUE;
        if (this.securityLevelService.isSecurityLevelEnabled()) {
            minSercurityLevel = this.getMinSercurityLevel(modelId);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        if (StringUtil.isNullOrEmpty((String)parent) || "null".equals(parent)) {
            DataSetSystemDefine dataSetSystemOption;
            List<ResourceTreeNode> localTree = this.getChildren(ROOT_ID, false, true, queryTypes);
            ResourceTreeNode rootLocalNode = null;
            if (localTree != null && localTree.size() > 0) {
                rootLocalNode = localTree.get(0);
            }
            if (null == (dataSetSystemOption = this.dataSetSystemOptionController.getDataSetSystemOption()) || StringUtil.isNullOrEmpty((String)dataSetSystemOption.getBiAddress())) {
                this.createNode(rootLocalNode, null, nodeList, parent, true, "\u5feb\u901f\u5206\u6790\u8868");
                return objectMapper.writeValueAsString(nodeList);
            }
            String s = "false";
            if (!"false".equals(s)) {
                List<ITree<ChartTreeNode>> biTree = this.biIntegrationHelper.getRootGroup("bireport");
                ITree<ChartTreeNode> rootBiNode = null;
                if (biTree != null && biTree.size() > 0) {
                    rootBiNode = biTree.get(0);
                }
                this.createNode(rootLocalNode, rootBiNode, nodeList, parent, true, "\u5feb\u901f\u5206\u6790\u8868");
            } else {
                this.createNode(rootLocalNode, null, nodeList, parent, true, "\u5feb\u901f\u5206\u6790\u8868");
            }
            return objectMapper.writeValueAsString(nodeList);
        }
        if (RequestTypeEnum.REMOTE.toString().equals(requestType)) {
            List<ITree<ChartTreeNode>> biTree = this.biIntegrationHelper.getBiTree(parent, "bireport", minSercurityLevel);
            biTree.stream().forEach(tree -> this.createNode(null, (ITree<ChartTreeNode>)tree, nodeList, parent, false, null));
            return objectMapper.writeValueAsString(nodeList);
        }
        if (RequestTypeEnum.LOCAL.toString().equals(requestType)) {
            List<ResourceTreeNode> nodeInfo = this.getChildren(parent, false, true, queryTypes);
            ArrayList<ResourceTreeNode> resourceTreeNode = new ArrayList<ResourceTreeNode>();
            for (ResourceTreeNode treeNode2 : nodeInfo) {
                if (StringUtil.isNullOrEmpty((String)treeNode2.getSecurityLevel()) || "null".equals(treeNode2.getSecurityLevel())) {
                    resourceTreeNode.add(treeNode2);
                    continue;
                }
                if (Integer.valueOf(treeNode2.getSecurityLevel()) > minSercurityLevel) continue;
                resourceTreeNode.add(treeNode2);
            }
            resourceTreeNode.stream().forEach(treeNode -> this.createNode((ResourceTreeNode)treeNode, null, (List<ITree<AnalysisTable>>)nodeList, parent, false, "\u5feb\u901f\u5206\u6790\u8868"));
            return objectMapper.writeValueAsString(nodeList);
        }
        return null;
    }

    List<ITree<AnalysisTable>> filterNode(List<ITree<AnalysisTable>> nodeList, String type) throws DataAnalyzeResourceException, DashboardException {
        ArrayList<ITree<AnalysisTable>> node = new ArrayList<ITree<AnalysisTable>>();
        for (ITree<AnalysisTable> analysisTableITree : nodeList) {
            if (!analysisTableITree.isLeaf()) {
                this.getResourceTreeNode(analysisTableITree, type, node);
                continue;
            }
            if (((AnalysisTable)analysisTableITree.getData()).getType().equals(this.dashboardType)) {
                analysisTableITree.setLeaf(false);
            }
            node.add(analysisTableITree);
        }
        return node;
    }

    void getResourceTreeNode(ITree<AnalysisTable> analysisTableITree, String type, List<ITree<AnalysisTable>> node) throws DataAnalyzeResourceException {
        List<ResourceTreeNode> nodes = this.getChildren(analysisTableITree.getCode(), false, false, null);
        if (nodes.size() > 0) {
            boolean i = false;
            for (ResourceTreeNode resourceTreeNode : nodes) {
                if (i) continue;
                if (resourceTreeNode.isFolder()) {
                    List<ResourceTreeNode> nodeChilen = this.getChildren(resourceTreeNode.getGuid(), false, false, null);
                    for (ResourceTreeNode resourceTree : nodeChilen) {
                        if (!type.equals(resourceTree.getType())) continue;
                        node.add(analysisTableITree);
                        i = true;
                        break;
                    }
                }
                if (!type.equals(resourceTreeNode.getType())) continue;
                node.add(analysisTableITree);
                i = true;
                break;
            }
        }
    }

    List<ITree<AnalysisTable>> getWidgetItems(String parent) throws DashboardException {
        ArrayList<ITree<AnalysisTable>> nodeList = new ArrayList<ITree<AnalysisTable>>();
        List widgetItems = this.dashboardManager.getWidgetItems(parent, true);
        String[] icons = new String[]{"#icon-_ModalTfujiedian"};
        if (widgetItems.size() > 0) {
            for (WidgetItem widgetItem : widgetItems) {
                ITree nvWaTree = new ITree();
                String key = "bireport;" + parent + ";" + widgetItem.getId();
                AnalysisTable analysisTable = new AnalysisTable(key, "\u4eea\u8868\u76d8\u56fe\u8868", widgetItem.getId(), "bireport", RequestTypeEnum.LOCAL);
                nvWaTree.setData((INode)analysisTable);
                nvWaTree.setTitle(widgetItem.getTitle());
                nvWaTree.setCode(widgetItem.getId());
                nvWaTree.setLeaf(true);
                nvWaTree.setIcons(icons);
                nodeList.add((ITree<AnalysisTable>)nvWaTree);
            }
        }
        return nodeList;
    }

    void createNode(ResourceTreeNode treeNode, ITree<ChartTreeNode> tree, List<ITree<AnalysisTable>> nodeList, String parent, Boolean isRoot, String nodeName) {
        String securityTitle = null;
        if (null != treeNode) {
            securityTitle = this.secretLevelService.getSecretLevelItem(treeNode.getSecurityLevel()).getTitle();
            if (treeNode.isFolder() || this.analysisTableType.equals(treeNode.getType()) && "\u5feb\u901f\u5206\u6790\u8868".equals(nodeName) || this.dashboardType.equals(treeNode.getType()) && "\u56fe\u8868".equals(nodeName)) {
                AnalysisTable analysisTable = null;
                if (isRoot.booleanValue()) {
                    analysisTable = "\u5feb\u901f\u5206\u6790\u8868".equals(nodeName) ? new AnalysisTable(null, nodeName, ROOT_ID, "report", RequestTypeEnum.LOCAL) : new AnalysisTable("bi;root", nodeName, ROOT_ID, "bi", RequestTypeEnum.LOCAL);
                } else {
                    String key = "bireport;" + parent + ";" + treeNode.getGuid();
                    analysisTable = new AnalysisTable(key, treeNode.getTitle(), treeNode.getGuid(), "bireport", RequestTypeEnum.LOCAL);
                }
                analysisTable.setType(treeNode.getType());
                String[] icons = new String[]{"#icon-_ModalTfujiedian"};
                ITree NvWaTree = new ITree();
                NvWaTree.setCode(treeNode.getGuid());
                NvWaTree.setLeaf(!treeNode.isFolder());
                boolean isLeaf = NvWaTree.isLeaf();
                if (this.securityLevelService.isSecurityLevelEnabled()) {
                    NvWaTree.setTitle(isRoot != false ? nodeName : (isLeaf ? treeNode.getTitle() + "[" + securityTitle + "]" : treeNode.getTitle()));
                } else {
                    NvWaTree.setTitle(isRoot != false ? nodeName : treeNode.getTitle());
                }
                NvWaTree.setIcons(icons);
                NvWaTree.setData((INode)analysisTable);
                nodeList.add((ITree<AnalysisTable>)NvWaTree);
            }
        }
        if (null != tree) {
            AnalysisTable BiNode = new AnalysisTable();
            ITree BiTree = new ITree();
            BiNode.setCode(((ChartTreeNode)tree.getData()).getCode());
            BiNode.setType(((ChartTreeNode)tree.getData()).getType());
            BiNode.setKey(((ChartTreeNode)tree.getData()).getKey());
            BiNode.setTitle(tree.getTitle());
            BiNode.setRequestTypeEnum(RequestTypeEnum.REMOTE);
            BiTree.setCode(tree.getCode());
            BiTree.setLeaf(tree.isLeaf());
            BiTree.setTitle(isRoot != false ? tree.getTitle() + "(BI\u5fae\u670d\u52a1)" : tree.getTitle());
            BiTree.setData((INode)BiNode);
            BiTree.setIcons(tree.getIcons());
            nodeList.add((ITree<AnalysisTable>)BiTree);
        }
    }

    @RequestMapping(value={"api/modelSelectMainView/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject modelSelectMainView(@PathVariable(value="key") String key) throws Exception {
        if (null == key) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            return this.saveAnalysis.modelSelectMasterView(key);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_185);
        }
    }

    @RequestMapping(value={"api/queryEntitysByElements"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject queryEntitysByElements(@RequestBody String jsonDefine) throws Exception {
        if (null == jsonDefine) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            return this.saveAnalysis.queryEntitysByElements(jsonDefine);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_185);
        }
    }

    @RequestMapping(value={"api/queryTableByElement"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject queryTableByElement(@RequestBody String jsonDefine) throws Exception {
        if (null == jsonDefine) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            return this.saveAnalysis.queryTableByElement(jsonDefine);
        }
        catch (UnsupportedEncodingException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_185);
        }
    }

    @RequestMapping(value={"api/zbListSelectMainView"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject zbListToCode(@RequestBody String jsonDefine) throws Exception {
        if (jsonDefine == null || "".equals(jsonDefine)) {
            AnaUtils.initReturn(false, "\u6307\u6807\u9009\u62e9\u5931\u8d25");
        }
        try {
            return this.saveAnalysis.zbListToCode(jsonDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_186);
        }
    }

    @RequestMapping(value={"api/reportToCode"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject reportToCode(@RequestBody String jsonDefine) throws Exception {
        if (jsonDefine == null || "".equals(jsonDefine)) {
            AnaUtils.initReturn(false, "\u62a5\u8868\u9009\u62e9\u5931\u8d25");
        }
        try {
            return this.saveAnalysis.reportToCode(jsonDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_187);
        }
    }

    @RequestMapping(value={"/api/setExtendData"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject setAnalysisExtendData(@RequestBody String defineJson) throws Exception {
        if (null == defineJson) {
            return AnaUtils.initReturn(false, "\u6a21\u677f\u4fe1\u606f\u5f02\u5e38");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tableidNode = mapper.readTree(defineJson);
        if (null == tableidNode.get("modelId")) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            return this.saveAnalysis.setAnalysisExtendData(tableidNode);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_189);
        }
    }

    @RequestMapping(value={"/api/getExtendData/{modelId}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getAnalysisExtendDataByKey(@PathVariable(value="modelId") String modelId) throws Exception {
        if (null == modelId || "".equals(modelId)) {
            return AnaUtils.initReturn(false, "\u9519\u8bef\u7684\u6a21\u677f\u4fe1\u606f");
        }
        try {
            return this.saveAnalysis.getListByKey(modelId);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_190);
        }
    }

    @RequestMapping(value={"api/progress/query"}, method={RequestMethod.GET})
    @ResponseBody
    public AsyncTaskInfo progressQuery(String progressId) {
        Object find = this.cacheObjectResourceRemote.find((Object)progressId);
        if (null != find) {
            return (AsyncTaskInfo)find;
        }
        this.logger.info("Empty AsyncTaskInfo: " + progressId);
        return null;
    }

    @RequestMapping(value={"api/queryFormById"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject queryFormById(@RequestBody String jsonDefine) throws Exception {
        if (jsonDefine == null || "".equals(jsonDefine)) {
            return AnaUtils.initReturn(false, "\u672a\u6307\u5b9a\u62a5\u8868");
        }
        try {
            return this.saveAnalysis.queryFormById(jsonDefine);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return AnaUtils.initReturn(false, "\u67e5\u8be2\u62a5\u8868\u7ef4\u5ea6\u5931\u8d25");
        }
    }

    @RequestMapping(value={"api/queryFormulaById"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject queryFormulaById(@RequestBody String jsonDefine) throws JQException {
        if (jsonDefine == null || "".equals(jsonDefine)) {
            AnaUtils.initReturn(false, "\u672a\u6307\u5b9a\u516c\u5f0f");
        }
        try {
            return this.saveAnalysis.queryFormulaById(jsonDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_187);
        }
    }

    @RequestMapping(value={"api/fileupload"}, method={RequestMethod.GET})
    @ResponseBody
    public String fileupload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String res = "\r\n{\r\n    \r\n    \"imageActionName\": \"uploadimage\", \r\n    \"imageFieldName\": \"upfile\", \r\n    \"imageMaxSize\": 2048000, \r\n    \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], \r\n    \"imageCompressEnable\": true, \r\n    \"imageCompressBorder\": 1600, \r\n    \"imageInsertAlign\": \"none\", \r\n    \"imageUrlPrefix\": \"\", \r\n    \"imagePathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n                                \r\n\r\n    \r\n    \"scrawlActionName\": \"uploadscrawl\", \r\n    \"scrawlFieldName\": \"upfile\", \r\n    \"scrawlPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", \r\n    \"scrawlMaxSize\": 2048000, \r\n    \"scrawlUrlPrefix\": \"\", \r\n    \"scrawlInsertAlign\": \"none\",\r\n\r\n    \r\n    \"snapscreenActionName\": \"uploadimage\", \r\n    \"snapscreenPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", \r\n    \"snapscreenUrlPrefix\": \"\", \r\n    \"snapscreenInsertAlign\": \"none\", \r\n\r\n    \r\n    \"catcherLocalDomain\": [\"127.0.0.1\", \"localhost\", \"img.baidu.com\"],\r\n    \"catcherActionName\": \"catchimage\", \r\n    \"catcherFieldName\": \"source\", \r\n    \"catcherPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", \r\n    \"catcherUrlPrefix\": \"\", \r\n    \"catcherMaxSize\": 2048000, \r\n    \"catcherAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], \r\n\r\n    \r\n    \"videoActionName\": \"uploadvideo\", \r\n    \"videoFieldName\": \"upfile\", \r\n    \"videoPathFormat\": \"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}\", \r\n    \"videoUrlPrefix\": \"\", \r\n    \"videoMaxSize\": 102400000, \r\n    \"videoAllowFiles\": [\r\n        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\"], \r\n\r\n    \r\n    \"fileActionName\": \"uploadfile\", \r\n    \"fileFieldName\": \"upfile\", \r\n    \"filePathFormat\": \"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}\", \r\n    \"fileUrlPrefix\": \"\", \r\n    \"fileMaxSize\": 51200000, \r\n    \"fileAllowFiles\": [\r\n        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\r\n        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\r\n        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\r\n        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\r\n    ], \r\n\r\n    \r\n    \"imageManagerActionName\": \"listimage\", \r\n    \"imageManagerListPath\": \"/ueditor/jsp/upload/image/\", \r\n    \"imageManagerListSize\": 20, \r\n    \"imageManagerUrlPrefix\": \"\", \r\n    \"imageManagerInsertAlign\": \"none\", \r\n    \"imageManagerAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], \r\n\r\n    \r\n    \"fileManagerActionName\": \"listfile\", \r\n    \"fileManagerListPath\": \"/ueditor/jsp/upload/file/\", \r\n    \"fileManagerUrlPrefix\": \"\", \r\n    \"fileManagerListSize\": 20, \r\n    \"fileManagerAllowFiles\": [\r\n        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\r\n        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\r\n        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\r\n        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\r\n        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\r\n    ] \r\n\r\n}";
        return res;
    }

    @RequestMapping(value={"api/queryPasteformVarTitles"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject queryPasteformVarTitles(@RequestBody String jsonDefine) throws JQException {
        if (jsonDefine == null || "".equals(jsonDefine)) {
            AnaUtils.initReturn(false, "\u672a\u8bbe\u7f6e\u516c\u5f0f");
        }
        try {
            return this.saveAnalysis.queryPasteformVarTitles(jsonDefine);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_192);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RequiresPermissions(value={"nr:analysisreport:exportmodel"})
    @ApiOperation(value="\u5bfc\u51fa\u62a5\u544a\u6a21\u677f")
    @RequestMapping(value={"api/exportModel"}, method={RequestMethod.POST})
    @ResponseBody
    public void exportModel(@RequestBody String defineJson, HttpServletResponse response, HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tableidNode = mapper.readTree(defineJson);
        String taskId = tableidNode.has("taskid") && tableidNode.get("taskid") != null ? tableidNode.get("taskid").toString().replaceAll("[^0-9a-zA-Z-]", "") : UUID.randomUUID().toString();
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
        asyncTaskMonitor.progressAndMessage(0.05, "\u6b63\u5728\u5bfc\u51fa\u5206\u6790\u62a5\u544a\u6a21\u677f");
        OutputStream resOut = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();){
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a", "\u5bfc\u51fa\u62a5\u544a\u6a21\u677f", AnalysisReportLogHelper.LOGLEVEL_INFO);
            Iterator exports = tableidNode.has("exports") && tableidNode.get("exports") != null ? tableidNode.get("exports").iterator() : null;
            HashMap<String, AnalysisTemp> analysisTemps = new HashMap<String, AnalysisTemp>();
            ArrayList<String> keys = new ArrayList<String>();
            if (exports != null) {
                asyncTaskMonitor.progressAndMessage(0.3, "\u6b63\u5728\u8bfb\u53d6\u6a21\u677f\u4fe1\u606f");
                while (exports.hasNext()) {
                    JsonNode export = (JsonNode)exports.next();
                    if (!export.has("key") || !export.has("antype")) continue;
                    String key = export.get("key").textValue();
                    String antype = export.get("antype").textValue();
                    if ("group".equals(antype)) {
                        List<AnalysisTemp> temps = this.saveAnalysis.getTempListByGroupKey(key);
                        if (temps == null || temps.size() <= 0) continue;
                        for (AnalysisTemp temp : temps) {
                            analysisTemps.put(temp.getKey(), temp);
                        }
                        continue;
                    }
                    keys.add(key);
                }
                List<AnalysisTemp> temps = this.saveAnalysis.getListByKeys(keys);
                if (temps != null && temps.size() > 0) {
                    for (AnalysisTemp temp : temps) {
                        analysisTemps.put(temp.getKey(), temp);
                    }
                }
            }
            asyncTaskMonitor.progressAndMessage(0.8, "\u6b63\u5728\u5bfc\u51fa\u6a21\u677f");
            String fileName = "JoinCheer.csv";
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            List<ReportChapterDefine> reportChapterDefines = this.chapterService.queryChapterByModelId((String)keys.get(0));
            String atChapter = null;
            if (!CollectionUtils.isEmpty(reportChapterDefines)) {
                ObjectMapper objectMapper = new ObjectMapper();
                atChapter = objectMapper.writeValueAsString(reportChapterDefines);
            }
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(new String[]{"AT_TITLE", "AT_DESCRIPTION", "AT_DATA", "AT_PRINTDATA", "AT_DIMENSION,AT_CHAPTER"});
            OutputStreamWriter osw = new OutputStreamWriter((OutputStream)out, "UTF-8");
            CSVPrinter csvPrinter = new CSVPrinter((Appendable)osw, csvFormat);
            if (!analysisTemps.isEmpty()) {
                for (String key : analysisTemps.keySet()) {
                    AnalysisTemp temp = (AnalysisTemp)analysisTemps.get(key);
                    String dimensionConfigObjSerialize = SerializeUtils.jsonSerialize(temp.getDimension());
                    csvPrinter.printRecord(new Object[]{StringUtils.isEmpty((CharSequence)temp.getTitle()) ? null : URLEncoder.encode(temp.getTitle(), "UTF-8"), StringUtils.isEmpty((CharSequence)temp.getDescription()) ? null : URLEncoder.encode(temp.getDescription(), "UTF-8"), StringUtils.isEmpty((CharSequence)temp.getData()) ? null : URLEncoder.encode(temp.getData(), "UTF-8"), StringUtils.isEmpty((CharSequence)temp.getPrintData()) ? null : URLEncoder.encode(temp.getPrintData(), "UTF-8"), StringUtils.isEmpty((CharSequence)dimensionConfigObjSerialize) ? null : URLEncoder.encode(dimensionConfigObjSerialize, "UTF-8"), StringUtils.isEmpty((CharSequence)atChapter) ? null : URLEncoder.encode(atChapter, "UTF-8")});
                }
            }
            csvPrinter.flush();
            csvPrinter.close();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            resOut = response.getOutputStream();
            CipherUtil.encrypt(in, resOut);
        }
        catch (IOException e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u6a21\u677f\u5bfc\u51fa", "\u5206\u6790\u62a5\u544a\u5bfc\u51fa\u5f02\u5e38", e);
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u6a21\u677f\u5bfc\u51fa", "\u6a21\u677f\u5185\u5bb9\u4e3a\u7a7a\uff01\u8bf7\u586b\u5199\u6a21\u677f\u5185\u5bb9", e);
        }
        finally {
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.finish("\u5206\u6790\u62a5\u544a\u5b8c\u6210\u3002", (Object)JsonUtil.objectToJson((Object)new BatchReturnInfo()));
            }
            if (resOut != null) {
                try {
                    resOut.close();
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @RequiresPermissions(value={"nr:analysisreport:importmodel"})
    @ApiOperation(value="\u5bfc\u5165\u62a5\u544a\u6a21\u677f")
    @PostMapping(value={"/api/importModel"})
    public String importModel(@RequestParam(value="file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws JQException {
        JSONObject jsonObject = new JSONObject();
        String taskId = request.getParameter("taskId");
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
        asyncTaskMonitor.progressAndMessage(0.05, "\u6b63\u5728\u5bfc\u5165\u5206\u6790\u62a5\u544a\u6a21\u677f");
        ByteArrayInputStream is = null;
        InputStreamReader isr = null;
        try (InputStream in = file.getInputStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream();){
            asyncTaskMonitor.progressAndMessage(0.1, "\u6b63\u5728\u8bfb\u53d6\u6a21\u677f");
            String modelID = request.getParameter("modelID");
            String groupID = request.getParameter("groupID");
            ArrayList<String> keys = new ArrayList<String>();
            keys.add(modelID);
            List<AnalysisTemp> temps = this.saveAnalysis.getListByKeys(keys);
            CipherUtil.decrypt(in, out);
            is = new ByteArrayInputStream(out.toByteArray());
            isr = new InputStreamReader((InputStream)is, "UTF-8");
            BufferedReader reader = new BufferedReader(isr);
            CSVParser parser = CSVFormat.EXCEL.withHeader(new String[0]).parse((Reader)reader);
            List list = parser.getRecords();
            if (temps != null && temps.size() > 0) {
                AnalysisTemp temp = temps.get(0);
                if (list != null && list.size() > 0) {
                    int index = 0;
                    for (CSVRecord record : list) {
                        String AT_TITLE = StringUtils.isEmpty((CharSequence)record.get(0)) ? null : URLDecoder.decode(record.get(0), "UTF-8");
                        String AT_DATA = StringUtils.isEmpty((CharSequence)record.get(2)) ? null : URLDecoder.decode(record.get(2), "UTF-8");
                        String AT_PRINTDATA = StringUtils.isEmpty((CharSequence)record.get(3)) ? null : URLDecoder.decode(record.get(3), "UTF-8");
                        String STR_DIMENSION = StringUtils.isEmpty((CharSequence)record.get(4)) ? null : URLDecoder.decode(record.get(4), "UTF-8");
                        String AT_CHAPTER = null;
                        if (record.size() == 6) {
                            AT_CHAPTER = StringUtils.isEmpty((CharSequence)record.get(5)) ? null : URLDecoder.decode(record.get(5), "UTF-8");
                        }
                        asyncTaskMonitor.progressAndMessage((double)(++index / list.size()) * 0.8 + 0.1, "\u6b63\u5728\u5bfc\u5165" + AT_TITLE);
                        JsonObject defineJson = new JsonObject();
                        defineJson.addProperty("key", modelID);
                        defineJson.addProperty("title", temp.getTitle());
                        defineJson.addProperty("description", temp.getDescription());
                        defineJson.addProperty("data", AT_DATA);
                        defineJson.addProperty("printData", AT_PRINTDATA);
                        defineJson.addProperty("dimension", STR_DIMENSION);
                        defineJson.addProperty("groupKey", temp.getGroupKey());
                        defineJson.addProperty("parentgroup", temp.getGroupKey());
                        boolean hasChapter = this.dealChapter(modelID, AT_CHAPTER, AT_DATA);
                        if (hasChapter) {
                            defineJson.addProperty("data", "");
                        }
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode tableidNode = mapper.readTree(defineJson.toString());
                        this.saveAnalysis.updateModel(tableidNode);
                    }
                }
            } else if (list != null && list.size() > 0) {
                list.remove(0);
                int index = 0;
                for (CSVRecord record : list) {
                    String AT_TITLE = StringUtils.isEmpty((CharSequence)record.get(0)) ? null : URLDecoder.decode(record.get(0), "UTF-8");
                    String AT_DESCRIPTION = StringUtils.isEmpty((CharSequence)record.get(1)) ? null : URLDecoder.decode(record.get(1), "UTF-8");
                    String AT_DATA = StringUtils.isEmpty((CharSequence)record.get(2)) ? null : URLDecoder.decode(record.get(1), "UTF-8");
                    String AT_PRINTDATA = StringUtils.isEmpty((CharSequence)record.get(3)) ? null : URLDecoder.decode(record.get(2), "UTF-8");
                    String STR_DIMENSION = StringUtils.isEmpty((CharSequence)record.get(4)) ? null : URLDecoder.decode(record.get(3), "UTF-8");
                    String AT_CHAPTER = null;
                    if (record.size() == 6) {
                        AT_CHAPTER = StringUtils.isEmpty((CharSequence)record.get(5)) ? null : URLDecoder.decode(record.get(5), "UTF-8");
                    }
                    asyncTaskMonitor.progressAndMessage((double)(++index / list.size()) * 0.8 + 0.1, "\u6b63\u5728\u5bfc\u5165" + AT_TITLE);
                    JSONObject defineJson = new JSONObject();
                    defineJson.put("key", (Object)modelID);
                    defineJson.put("title", (Object)AT_TITLE);
                    defineJson.put("description", (Object)AT_DESCRIPTION);
                    defineJson.put("data", (Object)AT_DATA);
                    defineJson.put("printData", (Object)AT_PRINTDATA);
                    defineJson.put("dimension", (Object)STR_DIMENSION);
                    defineJson.put("groupKey", (Object)groupID);
                    defineJson.put("parentgroup", (Object)groupID);
                    ObjectMapper mapper = new ObjectMapper();
                    boolean hasChapter = this.dealChapter(modelID, AT_CHAPTER, AT_DATA);
                    if (hasChapter) {
                        defineJson.put("data", (Object)"");
                    }
                    JsonNode tableidNode = mapper.readTree(defineJson.toString());
                    this.saveAnalysis.insertModel(tableidNode);
                }
            }
            jsonObject.put("success", true);
        }
        catch (IOException e) {
            jsonObject.put("message", (Object)"\u6587\u4ef6\u8bfb\u53d6\u5931\u8d25");
            jsonObject.put("success", false);
        }
        catch (Exception e) {
            jsonObject.put("message", (Object)"\u5bfc\u5165\u5931\u8d25");
            jsonObject.put("success", false);
        }
        finally {
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.finish("\u5206\u6790\u62a5\u544a\u6a21\u677f\u5bfc\u51fa\u5b8c\u6210\u3002", (Object)JsonUtil.objectToJson((Object)new BatchReturnInfo()));
            }
            if (isr != null) {
                try {
                    isr.close();
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
        }
        return jsonObject.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean dealChapter(String modelId, String chapter, String atData) throws Exception {
        if (StringUtils.isEmpty((CharSequence)chapter)) {
            List<ReportChapterDefine> reportChapterDefines = this.chapterService.queryChapterByModelId(modelId);
            this.chapterService.deleteChapterByModelId(modelId);
            if (CollectionUtils.isEmpty(reportChapterDefines)) return false;
            ReportChapterDefine reportChapterDefine = new ReportChapterDefine();
            reportChapterDefine.setArcKey(UUID.randomUUID().toString());
            reportChapterDefine.setArcName("\u9ed8\u8ba4\u7ae0\u8282");
            reportChapterDefine.setArcAtKey(modelId);
            reportChapterDefine.setArcOrder(OrderGenerator.newOrder());
            reportChapterDefine.setArcParent(TOP_GROUP_KEY);
            reportChapterDefine.setArcData(atData);
            reportChapterDefine.setArcUpdatetime(new Date());
            this.chapterService.insertChapter(reportChapterDefine);
            if (!StringUtils.isNotEmpty((CharSequence)atData)) return true;
            this.npApplication.asyncRun((Runnable)new CatalogGenerateThread(reportChapterDefine));
            return true;
        } else {
            this.chapterService.deleteChapterByModelId(modelId);
            ObjectMapper objectMapper = new ObjectMapper();
            List chapterList = (List)objectMapper.readValue(chapter, (TypeReference)new TypeReference<List<ReportChapterDefine>>(){});
            for (ReportChapterDefine reportChapterDefine : chapterList) {
                String oldkey = reportChapterDefine.getArcKey();
                reportChapterDefine.setArcKey(UUID.randomUUID().toString());
                reportChapterDefine.setArcAtKey(modelId);
                Date date = new Date();
                String catalog = reportChapterDefine.getCatalog();
                if (StringUtils.isNotEmpty((CharSequence)catalog)) {
                    catalog = catalog.replace(oldkey, reportChapterDefine.getArcKey());
                    reportChapterDefine.setCatalog(catalog);
                }
                reportChapterDefine.setArcUpdatetime(date);
                reportChapterDefine.setCatalogUpdatetime(date);
            }
            this.chapterService.batchInsert(chapterList.toArray(new ReportChapterDefine[chapterList.size()]));
        }
        return true;
    }

    @RequestMapping(value={"/query-charttree"}, method={RequestMethod.GET})
    public String getTree(String group, String type) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (StringUtil.isNullOrEmpty((String)group) || "null".equals(group)) {
                return objectMapper.writeValueAsString(this.saveAnalysis.getRootGroup("bi"));
            }
            if (StringUtil.isNullOrEmpty((String)type)) {
                return "";
            }
            ArrayList<ITree<ChartTreeNode>> charttree = new ArrayList<ITree<ChartTreeNode>>();
            List<ITree<ChartTreeNode>> bitree = this.helper.getBiTree(group, type);
            if (bitree.size() > 0) {
                charttree.addAll(bitree);
            }
            return objectMapper.writeValueAsString(charttree);
        }
        catch (Exception e) {
            return null;
        }
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

    @RequestMapping(value={"/api/analysisReport/getVersionDetail/{bigDataKey}"}, method={RequestMethod.GET})
    public ReturnObject getVersionDetail(@PathVariable(value="bigDataKey") String bigDataKey, @RequestParam(value="arcKey", required=false) String arcKey) throws Exception {
        if (StringUtils.isEmpty((CharSequence)bigDataKey)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u7248\u672c");
        }
        try {
            return this.saveAnalysis.getVersionDetailById(bigDataKey, arcKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_210);
        }
    }

    @RequestMapping(value={"/api/analysisReport/checkTheSame"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject checkTheSame(@RequestBody ReportVersionVo reportVersionVo) throws Exception {
        if (null == reportVersionVo) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u4fe1\u606f\u9519\u8bef");
        }
        if (StringUtils.isEmpty((CharSequence)reportVersionVo.getName())) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u4e3a\u7a7a");
        }
        try {
            return this.saveAnalysis.checkVersion(reportVersionVo);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_211);
        }
    }

    @RequestMapping(value={"/api/analysisReport/checkVersionExceptSelf"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject checkVersionExceptSelf(@RequestBody ReportVersionVo reportVersionVo) throws Exception {
        if (null == reportVersionVo) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u4fe1\u606f\u9519\u8bef");
        }
        if (StringUtils.isEmpty((CharSequence)reportVersionVo.getName())) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u4e3a\u7a7a");
        }
        try {
            return this.saveAnalysis.checkVersionExceptSelf(reportVersionVo);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_211);
        }
    }

    @RequestMapping(value={"/api/analysisReport/getVersionList"}, method={RequestMethod.POST})
    @ResponseBody
    public List<AnalyVersionDefine> getVersionList(@RequestBody ReportBaseVO reportBaseVO) throws Exception {
        try {
            List<AnalyVersionDefine> versionList = this.saveAnalysis.getVersionList(reportBaseVO);
            return versionList;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_212);
        }
    }

    @RequestMapping(value={"/api/analysisReport/newEditVersion"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject insertVersion(@RequestBody ReportVersionGeneratorVo reportVersionGeneratorVo, HttpServletRequest request) throws Exception {
        if (null == reportVersionGeneratorVo) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u4fe1\u606f\u9519\u8bef");
        }
        if (StringUtils.isEmpty((CharSequence)reportVersionGeneratorVo.getName())) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u4e3a\u7a7a\uff0c\u521b\u5efa\u5931\u8d25");
        }
        try {
            LockCacheUtil.putLockCacheKey(request, reportVersionGeneratorVo);
            return this.saveAnalysis.insertVersion(reportVersionGeneratorVo);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_217);
        }
    }

    @RequestMapping(value={"/api/analysisReport/changeEditedVersion"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject changeEditedVersion(@RequestBody ReportVersionVo reportVersionVo) throws Exception {
        if (null == reportVersionVo) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u4fe1\u606f\u9519\u8bef");
        }
        if (StringUtils.isEmpty((CharSequence)reportVersionVo.getName())) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u4e3a\u7a7a\uff0c\u521b\u5efa\u5931\u8d25");
        }
        try {
            return this.saveAnalysis.updateVersion(reportVersionVo);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_213);
        }
    }

    @RequestMapping(value={"/api/analysisReport/saveVersionOnly"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject saveVersionOnly(@RequestBody ReportVersionVo reportVersionVo) throws Exception {
        if (null == reportVersionVo) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u4fe1\u606f\u9519\u8bef");
        }
        if (StringUtils.isEmpty((CharSequence)reportVersionVo.getName())) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u4e3a\u7a7a\uff0c\u521b\u5efa\u5931\u8d25");
        }
        try {
            return this.saveAnalysis.updateVersion(reportVersionVo);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_214);
        }
    }

    @RequestMapping(value={"/api/analysisReport/renameVersionName"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject renameVersionName(@RequestParam(value="versionKey") String versionKey, @RequestParam(value="name") String name) throws Exception {
        if (StringUtils.isEmpty((CharSequence)versionKey)) {
            return AnaUtils.initReturn(false, "\u7248\u672ckey\u4e3a\u7a7a\uff0c\u91cd\u547d\u540d\u5931\u8d25");
        }
        if (StringUtils.isEmpty((CharSequence)name)) {
            return AnaUtils.initReturn(false, "\u7248\u672c\u540d\u79f0\u4e3a\u7a7a\uff0c\u91cd\u547d\u540d\u5931\u8d25");
        }
        try {
            return this.saveAnalysis.renameVersionName(versionKey, name);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_215);
        }
    }

    @RequestMapping(value={"/api/analysisReport/deleteVersion/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject deleteVersion(@PathVariable(value="key") String key) throws Exception {
        if (StringUtils.isEmpty((CharSequence)key)) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u7248\u672c");
        }
        try {
            return this.saveAnalysis.deleteVersion(key);
        }
        catch (JQException e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_216);
        }
    }

    @RequestMapping(value={"api/getSecurityformularTasks/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getSecurityFormularTasks(@PathVariable(value="key") String key) throws Exception {
        try {
            return this.saveAnalysis.getSecurityFormularTasks(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_184);
        }
    }

    @RequestMapping(value={"api/getSecurityFormularRuntimeTasks/{key}"}, method={RequestMethod.GET})
    @ResponseBody
    public ReturnObject getSecurityFormularRuntimeTasks(@PathVariable(value="key") String key) throws Exception {
        try {
            return this.saveAnalysis.getSecurityFormularRuntimeTasks(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_184);
        }
    }

    @RequestMapping(value={"api/getRunTimeFormSchemesByTask/{taskKey}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848")
    public List<Map<String, String>> getRunTimeFormSchemesByTask(@PathVariable(value="taskKey") String taskKey) throws Exception {
        List formSchemes = this.runTimeViewController.queryFormSchemeByTask(taskKey);
        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
        if (null != formSchemes && !formSchemes.isEmpty()) {
            HashMap<String, String> item = null;
            for (FormSchemeDefine formSchemeDefine : formSchemes) {
                item = new HashMap<String, String>();
                item.put("key", formSchemeDefine.getKey());
                item.put("title", formSchemeDefine.getTitle());
                result.add(item);
            }
        }
        return result;
    }

    @RequestMapping(value={"api/secretLevelEnable"}, method={RequestMethod.GET})
    @ResponseBody
    public boolean secretLevelEnable() {
        return this.securityLevelService.isSecurityLevelEnabled();
    }

    @RequestMapping(value={"api/filterSecurityLevelDTO"}, method={RequestMethod.GET})
    @ResponseBody
    public ResultObject filterSecurityLevelDTO() {
        ResultObject resultObject = this.securityLevelController.initSecurityLevel();
        SecurityLevelDTO securityLevelDTO = (SecurityLevelDTO)resultObject.getData();
        if (!securityLevelDTO.getStatus()) {
            return resultObject;
        }
        int userSecurityLevel = Integer.MAX_VALUE;
        userSecurityLevel = this.getUserSercurityLevel(userSecurityLevel);
        List systemSecurityLevels = securityLevelDTO.getSystemSecurityLevels();
        List userSecurityLevels = securityLevelDTO.getUserSecurityLevels();
        Iterator iterator = systemSecurityLevels.iterator();
        while (iterator.hasNext()) {
            if (userSecurityLevel >= Integer.parseInt(((SecurityLevelV)iterator.next()).getName())) continue;
            iterator.remove();
        }
        iterator = userSecurityLevels.iterator();
        while (iterator.hasNext()) {
            String securityLevel = ((SecurityLevelV)iterator.next()).getName();
            if (StringUtil.isNullOrEmpty((String)securityLevel) || userSecurityLevel >= Integer.parseInt(securityLevel)) continue;
            iterator.remove();
        }
        securityLevelDTO.setSystemSecurityLevels(systemSecurityLevels);
        securityLevelDTO.setUserSecurityLevels(userSecurityLevels);
        return resultObject.setData((Object)securityLevelDTO);
    }

    @PostMapping(value={"/getGridData"})
    public ReturnObject getGridData(@RequestBody Map<String, String> params) {
        try {
            Object gridData = this.gridDataHelper.genGridData(params);
            return AnaUtils.initReturn(true, "\u6210\u529f\u83b7\u53d6\u8868\u6837", gridData);
        }
        catch (Exception e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
    }

    @PostMapping(value={"/isFloatTable"})
    public ReturnObject getFloatRowAndClo(@RequestBody Map<String, String> params) {
        try {
            Map<String, HashSet<Integer>> rowColFloat = this.gridDataHelper.getFloatRowAndCol(params);
            return AnaUtils.initReturn(true, "\u83b7\u53d6\u8868\u662f\u6d6e\u52a8\u884c|\u6d6e\u52a8\u5217\u6210\u529f", rowColFloat);
        }
        catch (Exception e) {
            return AnaUtils.initReturn(false);
        }
    }

    @GetMapping(value={"/fuzzyQuery"})
    public ReturnObject fuzzyQuery(@RequestParam String keyWord) {
        try {
            List<AnalysisTemp> analysisTemps = this.saveAnalysis.fuzzyQuery(keyWord);
            return AnaUtils.initReturn(true, "\u67e5\u8be2\u6210\u529f", analysisTemps);
        }
        catch (Exception e) {
            this.logger.error("\u901a\u8fc7\u6807\u9898\u6a21\u7cca\u67e5\u8be2\u62a5\u544a\u5206\u7ec4\u3001\u6a21\u677f\u5931\u8d25", e);
            return AnaUtils.initReturn(false, e.getMessage());
        }
    }

    @GetMapping(value={"/clearTemplate"})
    @RequiresPermissions(value={"nr:analysisreport:template"})
    public ReturnObject clearTemplate(@RequestParam String templateKey) {
        try {
            this.chapterService.clearTemplateArcData(templateKey);
            return AnaUtils.initReturn("\u6210\u529f\u6e05\u7a7a\u6a21\u677f\u4e0b\u6240\u6709\u7ae0\u8282\u6570\u636e");
        }
        catch (Exception e) {
            return AnaUtils.initReturn("\u6e05\u7a7a\u6a21\u677f\u4e0b\u6240\u6709\u7ae0\u8282\u6570\u636e\u5931\u8d25");
        }
    }

    @GetMapping(value={"/getGroupAndReportTree"})
    public ReturnObject getGroupAndReportTree() {
        try {
            List<Map<String, Object>> groupAndReportTree = this.saveAnalysis.getGroupAndReportTree();
            return AnaUtils.initReturn(true, "\u67e5\u8be2\u6210\u529f", groupAndReportTree);
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u5206\u7ec4\u6a21\u677f\u6811\u5931\u8d25", e);
            return AnaUtils.initReturn(false, e.getMessage());
        }
    }

    @GetMapping(value={"/checkGenCatalogCompleted"})
    public ReturnObject checkGenCatalogCompleted(@RequestParam String templateKey, @RequestParam(required=false) String versionKey) {
        try {
            templateKey = Html.cleanName((String)templateKey, (char[])new char[0]);
            versionKey = Html.cleanName((String)versionKey, (char[])new char[0]);
            boolean completed = this.saveAnalysis.checkGenCatalogCompleted(templateKey, versionKey);
            if (completed) {
                return AnaUtils.initReturn(true, "\u76ee\u5f55\u89e3\u6790\u5b8c\u6210", true);
            }
            return AnaUtils.initReturn(true, "\u76ee\u5f55\u89e3\u6790\u672a\u5b8c\u6210", false);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return AnaUtils.initReturn(false, "\u68c0\u67e5\u5f02\u6b65\u76ee\u5f55\u662f\u5426\u751f\u6210\u5b8c\u6210\u5f02\u5e38");
        }
    }

    @GetMapping(value={"/loadCatalogTree"})
    public ReturnObject loadCatalogTree(HttpServletRequest request, @RequestParam String templateKey, @RequestParam(required=false) String versionKey) {
        try {
            if (StringUtils.isEmpty((CharSequence)versionKey)) {
                versionKey = "";
            }
            String authorization = request.getHeader("Authorization");
            CatalogVo catalogVo = new CatalogVo();
            catalogVo.setVersionKey(versionKey);
            catalogVo.setTemplateKey(templateKey);
            catalogVo.setAuthorization(authorization);
            List<ReportCatalogItem> catalogTree = this.saveAnalysis.loadCatalogTree(catalogVo);
            return AnaUtils.initReturn(true, "\u76ee\u5f55\u89e3\u6790\u5b8c\u6210", catalogTree);
        }
        catch (Exception e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
    }

    @GetMapping(value={"/loadCatalogChildren"})
    public ReturnObject loadCatalogChildren(HttpServletRequest request, @RequestParam String templateKey, @RequestParam String parentId, @RequestParam(required=false) String versionKey) {
        try {
            if (StringUtils.isEmpty((CharSequence)versionKey)) {
                versionKey = "";
            }
            String authorization = request.getHeader("Authorization");
            CatalogVo catalogVo = new CatalogVo();
            catalogVo.setAuthorization(authorization);
            catalogVo.setTemplateKey(templateKey);
            catalogVo.setVersionKey(versionKey);
            List<ReportCatalogItem> catalogItems = CatalogCacheUtil.getCacheCatalog(catalogVo, parentId);
            if (!this.redisEnable && catalogItems == null) {
                this.saveAnalysis.loadCatalogTree(catalogVo);
                catalogItems = CatalogCacheUtil.getCacheCatalog(catalogVo, parentId);
            }
            return AnaUtils.initReturn(true, "\u4ece\u7f13\u5b58\u4e2d\u83b7\u53d6\u76ee\u5f55\u6210\u529f", catalogItems);
        }
        catch (Exception e) {
            return AnaUtils.initReturn(false, "\u4ece\u7f13\u5b58\u4e2d\u83b7\u53d6\u76ee\u5f55\u5f02\u5e38");
        }
    }

    @GetMapping(value={"/getVersionChapterList"})
    public ReturnObject getVersionChapterList(@RequestParam String versionKey, @RequestParam String modelId) {
        try {
            if (StringUtils.isNotEmpty((CharSequence)versionKey)) {
                List<ReportChapterDefine> versionChapterList = this.saveAnalysis.getVersionChapterList(versionKey);
                return AnaUtils.initReturn(versionChapterList);
            }
            List<ReportChapterDefine> reportChapterDefines = this.chapterService.listOnlyArcNameAndArcKey(modelId);
            return AnaUtils.initReturn(reportChapterDefines);
        }
        catch (Exception e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
    }

    @GetMapping(value={"/getLocateCatalogTree"})
    public ReturnObject getLocateCatalogTree(@RequestParam String templateKey, @RequestParam(required=false) String versionKey, @RequestParam String key) {
        try {
            List<ReportCatalogItem> locateCatalogTree = this.saveAnalysis.getLocateCatalogTree(templateKey, versionKey, key);
            return AnaUtils.initReturn(true, "\u83b7\u53d6\u9009\u4e2d\u8282\u70b9\u76ee\u5f55\u6811", locateCatalogTree);
        }
        catch (Exception e) {
            return AnaUtils.initReturn(false, e.getMessage());
        }
    }

    @GetMapping(value={"/api/getEntityRowByKey"})
    public ReturnObject getEntityRowByKey(@RequestParam String key) {
        List entityRows = this.uSelectorResultSet.getFilterEntityRows(key);
        if (!CollectionUtils.isEmpty(entityRows)) {
            IEntityRow iEntityRow = (IEntityRow)entityRows.get(0);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("key", iEntityRow.getEntityKeyData());
            map.put("code", iEntityRow.getCode());
            map.put("title", iEntityRow.getTitle());
            return AnaUtils.initReturn(true, "\u67e5\u8be2\u6210\u529f", map);
        }
        return null;
    }

    @RequestMapping(value={"/api/queryVarDim"}, method={RequestMethod.POST})
    @ResponseBody
    public ReturnObject queryVarDim(@RequestBody String jsonDefine) throws Exception {
        if (null == jsonDefine) {
            return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
        }
        try {
            return this.saveAnalysis.queryVarDim(jsonDefine);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrAnalysisErrorEnum.NRANALYSISERRORENUM_185);
        }
    }

    @PostMapping(value={"/api/getDataSchemeKeyByFormula"})
    public ReturnObject getDataSchemeKeyByFormule(@RequestBody ReportFormulaVo reportFormulaVo) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty((CharSequence)reportFormulaVo.getFormula())) {
            resultMap.put("dataSchemeKey", "");
            return AnaUtils.initReturn(resultMap);
        }
        Map<String, Object> ext = reportFormulaVo.getExt();
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(ext) && ext.get("QCY_PROJECTID") != null) {
            map.put("QCY_PROJECTID", ext.get("QCY_PROJECTID"));
        }
        Set<String> dataSchemeKey = this.analysisReportEntityService.getDataSchemeByFormula(reportFormulaVo.getFormula(), map);
        resultMap.put("isMultipleDataScheme", false);
        if (dataSchemeKey == null) {
            resultMap.put("dataSchemeKey", "");
        } else if (dataSchemeKey.size() == 0) {
            resultMap.put("dataSchemeKey", "FORMULA_NO_DS");
        } else if (dataSchemeKey.size() == 1) {
            resultMap.put("dataSchemeKey", dataSchemeKey.iterator().next());
        } else {
            resultMap.put("dataSchemeKey", "");
        }
        return AnaUtils.initReturn(resultMap);
    }

    @RequiresPermissions(value={"nr:analysisreport:batchexport"})
    @PostMapping(value={"/api/batchExport"})
    public ReturnObject batchExport(HttpServletRequest request, @RequestBody ReportExportVO reportExportVO) {
        try {
            if (reportExportVO == null) {
                return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u6a21\u677f");
            }
            if (CollectionUtils.isEmpty(reportExportVO.getChooseUnits())) {
                return AnaUtils.initReturn(false, "\u672a\u9009\u62e9\u7ef4\u5ea6\uff0c\u65e0\u6cd5\u6267\u884c\u6279\u91cf\u5bfc\u51fa");
            }
            ReportBaseVO.UnitDim mainDim = AnaUtils.getMainDim(reportExportVO.getChooseUnits());
            if (mainDim.getChooseAll()) {
                IEntityTable iEntityTable = this.iNrArBatchExportServie.buildEntityTable(reportExportVO);
                if (iEntityTable.getTotalCount() == 0) {
                    return AnaUtils.initReturn(false, "\u4e3b\u7ef4\u5ea6\u6570\u636e\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u6267\u884c\u6279\u91cf\u5bfc\u51fa");
                }
            } else if (CollectionUtils.isEmpty(mainDim.getCodes())) {
                return AnaUtils.initReturn(false, "\u4e3b\u7ef4\u5ea6\u672a\u9009\u62e9\u5355\u4f4d\uff0c\u65e0\u6cd5\u6267\u884c\u6279\u91cf\u5bfc\u51fa");
            }
            NpContextHolder.getContext().getExtension("Authorization").put("Authorization", (Serializable)((Object)request.getHeader("Authorization")));
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)reportExportVO));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new NrArBatchExportAsyncTaskExecutor());
            String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
            return AnaUtils.initReturn(true, "\u6279\u91cf\u5bfc\u51fa\u6267\u884c\u4e2d", asyncTaskId);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return AnaUtils.initReturn(false, "\u6279\u91cf\u5bfc\u51fa\u6267\u884c\u5f02\u5e38");
        }
    }
}

