/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.Gson
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.print.vo.PrintAttributeVo
 *  com.jiuqi.nr.definition.print.vo.PrintWordVo
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.controller.DataSetSystemOptionController
 *  com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket
 *  com.jiuqi.nvwa.ticket.service.TicketService
 *  com.jiuqi.xg.print.PrinterDevice
 *  com.jiuqi.xg.print.util.AsyncWorkContainnerUtil
 *  com.jiuqi.xg.process.table.TablePaginateConfig
 *  io.netty.util.internal.StringUtil
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.query.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.print.vo.PrintAttributeVo;
import com.jiuqi.nr.definition.print.vo.PrintWordVo;
import com.jiuqi.nr.query.ai.QueryNvwaFieldParam;
import com.jiuqi.nr.query.block.ColumnWidth;
import com.jiuqi.nr.query.block.EntityDimensionExtension;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.block.QueryEntityData;
import com.jiuqi.nr.query.block.QueryGridExtension;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.block.QuerySelectItem;
import com.jiuqi.nr.query.chart.BIChartConfig;
import com.jiuqi.nr.query.chart.ChartType;
import com.jiuqi.nr.query.chart.HttpUtils;
import com.jiuqi.nr.query.common.GridBlockType;
import com.jiuqi.nr.query.common.QueryBlockType;
import com.jiuqi.nr.query.common.QueryLayoutType;
import com.jiuqi.nr.query.common.QuerySelectionType;
import com.jiuqi.nr.query.dao.IQueryBlockDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.defines.QueryEntityDataObject;
import com.jiuqi.nr.query.print.PrintService;
import com.jiuqi.nr.query.print.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.query.print.service.impl.QueryPrintServiceImpl;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nr.query.querymodal.QueryModelCategory;
import com.jiuqi.nr.query.querymodal.QueryModelType;
import com.jiuqi.nr.query.querymodal.QueryType;
import com.jiuqi.nr.query.service.AiConfigs;
import com.jiuqi.nr.query.service.IQueryModalController;
import com.jiuqi.nr.query.service.QueryCacheManager;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nr.query.service.impl.DataQueryHelper;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.controller.DataSetSystemOptionController;
import com.jiuqi.nvwa.dataanalysis.bi.config.systemoption.services.BIIntegrationConfig;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.intergration.sdk.ticket.bean.Ticket;
import com.jiuqi.nvwa.ticket.service.TicketService;
import com.jiuqi.xg.print.PrinterDevice;
import com.jiuqi.xg.print.util.AsyncWorkContainnerUtil;
import com.jiuqi.xg.process.table.TablePaginateConfig;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/query-Manager"})
public class IQueryGridDataController {
    @Autowired
    TicketService tokenServices;
    @Resource
    IDataAccessProvider dataAccessProvider;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IQueryModalController queryModalController;
    @Autowired
    Environment environment;
    @Autowired
    BIChartConfig biChartConfig;
    @Autowired
    BIIntegrationConfig biConfig;
    @Autowired
    QueryCacheManager queryCacheManager;
    @Autowired
    private IQueryBlockDefineDao blockDao;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IQueryModalDefineDao modelDao;
    @Autowired
    IRunTimeViewController runtimeView;
    @Autowired
    private DataSetSystemOptionController systemOptionController;
    @Autowired
    private QueryEntityUtil queryEntityUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    public IColumnModelFinder columnModelFinder;
    private static final Logger logger = LoggerFactory.getLogger(IQueryGridDataController.class);
    @Autowired
    private QueryPrintServiceImpl queryPrint;
    private QueryCacheManager blockCacheManager;
    private static final String PRINT_QUERYBLOCK = "print_queryBlock";
    private static final String PRINT_ATTRVOCONFIG = "print_attrVoConfig";
    private static final String PRINT_PAGINATECONFIG = "print_paginateConfig";
    private static final String PRINT_MODELTYPE = "print_modeltype";
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private PrintService printService;

    @PostMapping(value={"/query-createAiBlock"})
    @ApiOperation(value="\u521b\u5efaai\u67e5\u8be2\u5757")
    public String AiBlock(@RequestBody AiConfigs aiConfig) {
        try {
            if (aiConfig == null) {
                return null;
            }
            List<QueryNvwaFieldParam> fieldParams = aiConfig.schemes;
            if (fieldParams == null || fieldParams.size() == 0) {
                return null;
            }
            TableDefine table = null;
            QueryBlockDefine block = new QueryBlockDefine();
            block.setId(UUID.randomUUID().toString());
            QueryDimensionDefine fieldDim = new QueryDimensionDefine();
            fieldDim.setIsPeriodDim(false);
            fieldDim.setDimensionType(QueryDimensionType.QDT_FIELD);
            fieldDim.setBlockId(block.getId());
            ArrayList<QuerySelectField> fields = new ArrayList<QuerySelectField>();
            HashMap<String, TableDefine> tables = new HashMap<String, TableDefine>();
            List<QueryDimensionDefine> dims = block.getQueryDimensions();
            if (dims == null) {
                dims = new ArrayList<QueryDimensionDefine>();
            }
            dims.add(fieldDim);
            block.setQueryDimensions(dims);
            LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
            result.put("curMasterDims", "1");
            result.put("isrelated", "True");
            result.put("periodtype", "DAY");
            for (QueryNvwaFieldParam param : fieldParams) {
                FieldDefine field;
                if (!param.isVisible) continue;
                table = (TableDefine)tables.get(param.tableName);
                if (table == null && (table = this.queryEntityUtil.queryTableDefineByCode(param.tableName)) != null) {
                    this.setDimension(block, aiConfig.dimensions, table, fields, result, aiConfig.periodType);
                    if (StringUtil.isNullOrEmpty((String)block.getQueryMastersStr())) {
                        Gson gson = new Gson();
                        block.setQueryMastersStr(gson.toJson(result));
                    }
                    tables.put(param.tableName, table);
                }
                if ((field = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(param.fieldCode, table.getKey())) == null) continue;
                QuerySelectField queryField = this.initField(field, "False");
                fields.add(queryField);
            }
            dims = block.getQueryDimensions();
            if (dims != null && dims.size() > 0) {
                dims.get(0).setSelectFields(fields);
                block.setQueryDimensions(dims);
            }
            ObjectMapper mapper = new ObjectMapper();
            QueryGridExtension extension = new QueryGridExtension();
            extension.setGridBlockType(GridBlockType.DETAILED);
            block.setBlockExtension(mapper.writeValueAsString((Object)extension));
            DataQueryHelper queryHelper = new DataQueryHelper();
            List<QueryDimensionDefine> oldDimensions = block.getQueryDimensions();
            QueryBlockDefine tempBlock = this.getTempBlock(block, null);
            tempBlock.setQueryType(QueryType.QUERY);
            tempBlock = queryHelper.getQueryDataGrid(tempBlock, QueryModelType.SIMPLEOWER, false);
            block.setGridData(tempBlock.getGridData());
            block.setQueryDimensions(oldDimensions);
            return HtmlUtils.cleanUrlXSS((String)mapper.writeValueAsString((Object)block));
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            LogHelper.error((String)LogModuleEnum.NRQUERY.getCode(), (String)"AI\u6307\u6807\u67e5\u8be2\u9519\u8bef", (String)("\u9519\u8bef\u4fe1\u606f\uff1a" + ex.getMessage()));
            return null;
        }
    }

    private QuerySelectField initField(FieldDefine field, String isMaster) {
        List deployInfoByDataFieldKeys = this.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
        DataFieldDeployInfoDO deployInfoByColumnKey = new DataFieldDeployInfoDO();
        if (deployInfoByDataFieldKeys.size() > 0) {
            deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        }
        QuerySelectField queryField = new QuerySelectField();
        queryField.setCode(field.getKey());
        queryField.setTitle(field.getTitle());
        queryField.setIsMaster(isMaster);
        queryField.setTableName(deployInfoByColumnKey.getTableName());
        queryField.setFiledType(field.getType());
        return queryField;
    }

    private void setDimension(QueryBlockDefine block, Map<String, String> dimensions, TableDefine table, List<QuerySelectField> fields, Map<String, String> result, String periodType) {
        try {
            List<QueryDimensionDefine> blockDims = block.getQueryDimensions();
            if (blockDims == null) {
                blockDims = new ArrayList<QueryDimensionDefine>();
            }
            String[] bizFields = table.getBizKeyFieldsID();
            ENameSet nameSet = new ENameSet();
            String masterKeys = "";
            for (String fieldKey : bizFields) {
                FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                if (field.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) continue;
                FieldDefine reffield = QueryHelper.getReferentField(fieldKey);
                DataFieldDeployInfo deployInfoByColumnKey = this.iRuntimeDataSchemeService.getDeployInfoByColumnKey(reffield.getKey());
                TableDefine viewTable = this.dataDefinitionRuntimeController.queryTableDefineByCode(deployInfoByColumnKey.getTableName());
                String dimName = QueryHelper.getDimName(reffield);
                if (dimensions.size() > 1 && !dimensions.containsKey(dimName)) continue;
                nameSet.add(dimName);
                QueryDimensionDefine dim = new QueryDimensionDefine();
                dim.setDimensionName(dimName);
                dim.setDimensionType(QueryDimensionType.QDT_ENTITY);
                dim.setFieldKey(field.getKey());
                dim.setLayoutType(QueryLayoutType.LYT_CONDITION);
                boolean isPeriod = viewTable.getKind() == TableKind.TABLE_KIND_ENTITY_PERIOD;
                dim.setIsPeriodDim(isPeriod);
                dim.setTableKind(viewTable.getKind().toString());
                EntityViewDefine view = this.entityViewRunTimeController.buildEntityView(reffield.getEntityKey());
                dim.setViewId(view.getEntityId());
                if (isPeriod) {
                    dim.setPeriodType("DAY");
                } else {
                    dim.setIstree("true");
                }
                masterKeys = StringUtil.isNullOrEmpty((String)masterKeys) ? view.getEntityId() + ";" : masterKeys + view.getEntityId() + ";";
                String itemStr = dimensions.get(dimName);
                dim.setBlockId(block.getId());
                ArrayList<QuerySelectItem> items = new ArrayList<QuerySelectItem>();
                if (!StringUtil.isNullOrEmpty((String)itemStr)) {
                    String[] fieldKeys;
                    for (String key : fieldKeys = itemStr.split(";")) {
                        QuerySelectItem item = new QuerySelectItem();
                        item.setCode(key);
                        item.setTitle(key);
                        items.add(item);
                    }
                    if (isPeriod && items.size() < 2) {
                        items.add((QuerySelectItem)items.get(0));
                    }
                    dim.setDefaultItems(items);
                    dim.setSelectItems(items);
                }
                String dimensionExtension = dim.getDimensionExtension();
                ObjectMapper objectMapper = new ObjectMapper();
                EntityDimensionExtension ext = new EntityDimensionExtension(dimensionExtension);
                ext.setQuerySelectionType(QuerySelectionType.MULTIITES);
                dimensionExtension = objectMapper.writeValueAsString((Object)ext);
                dim.setDimensionExtension(dimensionExtension);
                blockDims.add(dim);
                QuerySelectField queryField = this.initField(field, items.size() > 0 ? "True" : "False");
                fields.add(queryField);
            }
            block.setQueryDimensions(blockDims);
            block.setCode("dsfsdf");
            result.put("masterdimensions", nameSet.toString());
            result.put("masterKeys", masterKeys.toString());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @RequestMapping(value={"/query-createDefaultblock"}, method={RequestMethod.POST})
    public String defaultBlock(@RequestBody Map<String, String> gridBlockType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String blocktype = gridBlockType.get("blocktype");
            if (blocktype == null) {
                blocktype = "QBT_GRID";
            }
            QueryBlockType btype = QueryBlockType.valueOf(blocktype);
            DataQueryHelper queryHelper = new DataQueryHelper();
            String type = gridBlockType.get("type");
            if (type == null) {
                type = "DETAILED";
            }
            String id = gridBlockType.get("blockId");
            QueryBlockDefine blockDefine = null;
            switch (btype) {
                case QBT_GRID: {
                    blockDefine = queryHelper.getQueryFormGrid(null, id, GridBlockType.valueOf(type));
                    break;
                }
                case QBT_CUSTOMENTRY: {
                    blockDefine = queryHelper.getQueryFormGrid(null, id, GridBlockType.valueOf(type));
                    blockDefine.setBlockType(QueryBlockType.QBT_CUSTOMENTRY);
                    break;
                }
                case QBT_CHART: {
                    blockDefine = queryHelper.getQueryFormGrid(null, id, GridBlockType.HORIZONALED);
                    blockDefine.setBlockType(QueryBlockType.QBT_CHART);
                    blockDefine.setChartType(ChartType.valueOf(type));
                    break;
                }
                case QBT_ANALYSIS: {
                    blockDefine = queryHelper.getQueryFormGrid(null, id, GridBlockType.HORIZONALED);
                    blockDefine.setBlockType(QueryBlockType.QBT_ANALYSIS);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + (Object)((Object)btype));
                }
            }
            return mapper.writeValueAsString((Object)blockDefine);
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u6a21\u7248-\u67e5\u8be2\u7ed3\u679c:" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/query-createDefaultchart"}, method={RequestMethod.POST})
    public String defaultChartBlock(@RequestBody Map<String, String> chartParams) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String type = chartParams.get("type");
            String id = chartParams.get("blockId");
            DataQueryHelper queryHelper = new DataQueryHelper();
            QueryBlockDefine blockDefine = queryHelper.getQueryFormGrid(null, id, GridBlockType.HORIZONALED);
            blockDefine.setBlockType(QueryBlockType.QBT_CHART);
            blockDefine.setChartType(ChartType.valueOf(type));
            return mapper.writeValueAsString((Object)blockDefine);
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u6a21\u7248-\u67e5\u8be2\u7ed3\u679c:" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/query-blockgridstyle"}, method={RequestMethod.POST})
    public String QueryBlockStyle(@RequestBody QueryBlockDefine queryBlockDefine) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            DataQueryHelper queryHelper = new DataQueryHelper();
            QueryBlockDefine blockDefine = queryHelper.getQueryFormGrid(queryBlockDefine, null, null);
            return mapper.writeValueAsString((Object)blockDefine);
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u6a21\u7248-\u67e5\u8be2\u7ed3\u679c:" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/query-getDimName"}, method={RequestMethod.POST})
    public String QueryGetDimName(@RequestBody QueryBlockDefine queryBlockDefine) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            DataQueryHelper queryHelper = new DataQueryHelper();
            if (!queryBlockDefine.getHasUserForm()) {
                queryBlockDefine.setQueryDimensions(queryHelper.addDimType(queryBlockDefine.getQueryDimensions()));
            }
            return mapper.writeValueAsString((Object)queryBlockDefine);
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u7ef4\u5ea6\u540d-\u67e5\u8be2\u7ed3\u679c:" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/query-getConditionTiTle"}, method={RequestMethod.POST})
    public String getConditionTiTle(@RequestBody QueryBlockDefine queryBlockDefine) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            DataQueryHelper queryHelper = new DataQueryHelper();
            queryBlockDefine.setQueryDimensions(queryHelper.setDimConditionTitle(queryBlockDefine.getQueryDimensions()));
            return mapper.writeValueAsString((Object)queryBlockDefine);
        }
        catch (JsonProcessingException e) {
            logger.error("\u67e5\u8be2\u7ef4\u5ea6\u540d-\u67e5\u8be2\u7ed3\u679c:" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/query-createChartBlock"}, method={RequestMethod.POST})
    public String chartBlock(@RequestBody Map<String, String> chartBlock, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String url = this.biConfig.getBiAddress();
            String isSimple = chartBlock.get("isSimple");
            String type = chartBlock.get("chartType");
            String id = chartBlock.get("linkedid");
            String block = chartBlock.get("block");
            String ticket = this.biChartConfig.getTicket("query-createChartBlock", this.biConfig.getCAIdentify());
            String datasettype = this.systemOptionController.getServiceName();
            QueryBlockDefine thisBlock = (QueryBlockDefine)mapper.readValue(block, QueryBlockDefine.class);
            DataQueryHelper queryHelper = new DataQueryHelper();
            QueryBlockDefine blockDefine = queryHelper.getChartFormGrid(thisBlock, ChartType.valueOf(type), id, url, "", datasettype, ticket, isSimple);
            blockDefine.setQueryDimensions(queryHelper.addDimType(blockDefine.getQueryDimensions()));
            return mapper.writeValueAsString((Object)blockDefine);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u6a21\u7248-\u67e5\u8be2\u7ed3\u679c:" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/query-deleteChartBlock"}, method={RequestMethod.GET})
    public String deleteChartBlock(String blockId, HttpServletRequest request) {
        JSONObject jsonResult = new JSONObject();
        try {
            QueryBlockDefine block = this.blockDao.GetQueryBlockDefineById(blockId);
            QueryModalDefine modal = this.modelDao.getQueryModalDefineById(block.getModelID());
            if (!"simpleOwer".equals(modal.getModelType().toString())) {
                this.blockDao.DeleteQueryBlockDefineById(blockId);
            }
            String url = this.biConfig.getBiAddress();
            String ticket = this.biChartConfig.getTicket("query-deleteChartBlock", this.biConfig.getCAIdentify());
            String deleteUrl = url + "/api/ms/dashboard/chart/item/" + blockId + "?" + "ticket" + "=" + ticket;
            String para = "_method=DELETE";
            logger.info("deleteUrl:" + deleteUrl);
            jsonResult = HttpUtils.doRequest(deleteUrl, para, "POST");
            return jsonResult.toString();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @RequestMapping(value={"/query-updateChartBlock"}, method={RequestMethod.GET})
    public String updateChartBlock(@RequestParam(value="blockId") String blockId, @RequestParam(value="title") String title, HttpServletRequest request) {
        JSONObject jsonResult = new JSONObject();
        try {
            String url = this.biConfig.getBiAddress();
            String ticket = this.biChartConfig.getTicket("query-updateChartBlock", this.biConfig.getCAIdentify());
            String updateUrl = url + "/api/ms/dashboard/chart/item/" + blockId + "?" + "ticket" + "=" + ticket;
            JSONObject item = new JSONObject();
            item.put("guid", (Object)blockId);
            title = URLEncoder.encode(title, "UTF-8");
            item.put("title", (Object)title);
            String para = "item=" + item.toString();
            logger.info("updataUrl:" + updateUrl);
            jsonResult = HttpUtils.doRequest(updateUrl, para, "POST");
            return jsonResult.toString();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @GetMapping(value={"/query-getChartType"})
    public String getChartType(HttpServletRequest request) {
        String url = this.biConfig.getBiAddress();
        String ticket = this.biChartConfig.getTicket("query-getChartType", this.biConfig.getCAIdentify());
        String serverIdentify = this.biConfig.getServerIdentify();
        String getChartUrl = url + "/api/ms/dashboard/chart/type?" + "ticket" + "=" + ticket + "&as" + "=" + serverIdentify;
        JSONObject result = HttpUtils.doRequest(getChartUrl, null, "GET");
        return result.toString();
    }

    private String getTicket(String fromApiName) {
        try {
            Ticket ticket = this.tokenServices.apply();
            return ticket.getId();
        }
        catch (Exception ex) {
            logger.error(fromApiName + "\u83b7\u53d6ticket\u5931\u8d25", ex);
            return null;
        }
    }

    @GetMapping(value={"/query-getChartConfig"})
    public String getChartConfig() {
        String url = this.biConfig.getBiAddress();
        JSONObject result = new JSONObject();
        result.put("dashboard", (Object)url);
        return result.toString();
    }

    @GetMapping(value={"/query-getBiAddressF"})
    public String getBiAddressF() {
        JSONObject result = new JSONObject();
        try {
            String url = this.biConfig.getBiFrontAddress();
            if (StringUtil.isNullOrEmpty((String)url)) {
                url = this.biConfig.getBiAddress();
            }
            result.put("bifrontaddress", (Object)url);
            return result.toString();
        }
        catch (Exception e) {
            return "";
        }
    }

    @RequestMapping(value={"/query-modalgriddata"}, method={RequestMethod.POST})
    public String QueryModalData(@RequestBody QueryModalDefine queryModal) {
        try {
            ArrayList<QueryBlockDefine> queryBlockDefines = new ArrayList<QueryBlockDefine>();
            ObjectMapper mapper = new ObjectMapper();
            List<QueryDimensionDefine> modalConditions = queryModal.getConditionDimensions();
            if (queryModal.getModelCategory() == QueryModelCategory.SIMPLEQUERY) {
                queryModal.setModelType(QueryModelType.SIMPLEOWER);
            }
            Map<String, QueryDimensionDefine> modalConditionMap = null;
            if (modalConditions != null && modalConditions.size() > 0) {
                modalConditionMap = modalConditions.stream().collect(Collectors.toMap(QueryDimensionDefine::getViewId, m -> m, (k1, k2) -> k1));
            }
            for (QueryBlockDefine queryBlockDefine : queryModal.getBlocks()) {
                List<QueryDimensionDefine> oldDimensions = queryBlockDefine.getQueryDimensions();
                QueryBlockDefine tempBlock = this.getTempBlock(queryBlockDefine, modalConditionMap);
                tempBlock.setQueryType(queryModal.getQueryType());
                DataQueryHelper queryHelper = new DataQueryHelper();
                tempBlock = queryHelper.getQueryDataGrid(tempBlock, queryModal.getModelType(), false);
                queryBlockDefine.setGridData(tempBlock.getGridData());
                queryBlockDefine.setQueryDimensions(oldDimensions);
                Boolean end = tempBlock.getEnd();
                List<ColumnWidth> columnWidth = tempBlock.getBlockInfo().getColumnWidth();
                int totalCount = tempBlock.getBlockInfo().getTotalCount();
                queryBlockDefine.setEnd(end);
                queryBlockDefine.getBlockInfo().setTotalCount(totalCount);
                queryBlockDefine.getBlockInfo().setColumnWidth(columnWidth);
                if (queryModal.getBlocks().size() == 1) {
                    queryBlockDefine.setTitle(queryModal.getTitle());
                }
                queryBlockDefines.add(queryBlockDefine);
            }
            return mapper.writeValueAsString(queryBlockDefines);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u6a21\u7248-\u67e5\u8be2\u7ed3\u679c:" + e.getMessage());
            return null;
        }
    }

    private QueryBlockDefine getTempBlock(QueryBlockDefine tempBlock, Map<String, QueryDimensionDefine> modalConditionMap) {
        LinkedHashMap<String, String> inConditionDim = new LinkedHashMap<String, String>();
        if (modalConditionMap != null) {
            LinkedHashMap<String, Integer> conditionDimIndex = new LinkedHashMap<String, Integer>();
            ArrayList<QueryDimensionDefine> newDimensions = new ArrayList<QueryDimensionDefine>();
            List<QueryDimensionDefine> tempDimensions = tempBlock.getQueryDimensions();
            ArrayList<String> modalConditionInNewList = new ArrayList<String>();
            for (int i = 0; i < tempDimensions.size(); ++i) {
                QueryDimensionDefine dimension = tempDimensions.get(i);
                String viewId = dimension.getViewId();
                if (dimension.getLayoutType() == QueryLayoutType.LYT_CONDITION) {
                    conditionDimIndex.put(viewId, i);
                    if (modalConditionMap.containsKey(viewId)) {
                        modalConditionInNewList.add(viewId);
                        newDimensions.add(modalConditionMap.get(viewId));
                        inConditionDim.put(viewId, viewId);
                        continue;
                    }
                    newDimensions.add(dimension);
                    continue;
                }
                if (inConditionDim.containsKey(viewId)) {
                    newDimensions.add(dimension);
                    continue;
                }
                if (modalConditionMap.containsKey(viewId)) {
                    modalConditionInNewList.add(viewId);
                    if (conditionDimIndex.containsKey(viewId)) {
                        newDimensions.add((Integer)conditionDimIndex.get(viewId), modalConditionMap.get(viewId));
                    } else {
                        newDimensions.add(modalConditionMap.get(viewId));
                    }
                    inConditionDim.put(viewId, viewId);
                }
                newDimensions.add(dimension);
            }
            if (modalConditionInNewList.size() < modalConditionMap.size()) {
                String masterKeys = tempBlock.getMasterKeyValue("masterKeys");
                for (QueryDimensionDefine dimension : modalConditionMap.values()) {
                    String viewId = dimension.getViewId();
                    if (modalConditionInNewList.contains(viewId) || masterKeys.indexOf(viewId) < 0) continue;
                    newDimensions.add(dimension);
                }
            }
            tempBlock.setQueryDimensions(newDimensions);
        }
        return tempBlock;
    }

    @RequestMapping(value={"/query-modalgriddatabyjudge"}, method={RequestMethod.POST})
    public String QueryModalDataByJudge(@RequestBody QueryModalDefine queryModal) {
        try {
            ArrayList<QueryBlockDefine> queryBlockDefines = new ArrayList<QueryBlockDefine>();
            ObjectMapper mapper = new ObjectMapper();
            for (QueryBlockDefine queryBlockDefine : queryModal.getBlocks()) {
                DataQueryHelper queryHelper = new DataQueryHelper();
                queryBlockDefine.setQueryType(queryModal.getQueryType());
                queryBlockDefine = queryModal.getQueryModelExtension().isAutoQuery() ? queryHelper.getQueryDataGrid(queryBlockDefine, queryModal.getModelType(), false) : queryHelper.getQueryFormGrid(queryBlockDefine, null, null);
                queryBlockDefines.add(queryBlockDefine);
            }
            return mapper.writeValueAsString(queryBlockDefines);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u6a21\u7248-\u67e5\u8be2\u7ed3\u679c:" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value={"/query-exportData"}, method={RequestMethod.POST})
    public void export(@RequestBody QueryBlockDefine block, HttpServletResponse response, HttpServletRequest request) {
        try {
            DataQueryHelper queryHelper = new DataQueryHelper();
            queryHelper.Export(response, request, block);
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u5757\u5bfc\u51fa", (String)("\u67e5\u8be2\u5757\u5bfc\u51fa\u6210\u529f\uff0c\u6a21\u677fid:" + block.getModelID() + ",\u6a21\u677f\u6807\u9898\uff1a" + block.getTitle()));
        }
        catch (Exception e) {
            LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u67e5\u8be2\u5757\u5bfc\u51fa\u5f02\u5e38", (String)("\u67e5\u8be2\u5757\u5bfc\u51fa\u5f02\u5e38\u4fe1\u606f\uff1a" + e));
            logger.error("\u5bfc\u51fa\u9519\u8bef" + e.getMessage(), e);
        }
    }

    @RequestMapping(value={"/query-printPDF"}, method={RequestMethod.POST})
    public void printPDF(HttpServletResponse response, HttpServletRequest request) {
        try {
            Object queryBlockObj = this.blockCacheManager.getCache(NpContextHolder.getContext().getUserId(), PRINT_QUERYBLOCK);
            Object attrVoConfigObj = this.blockCacheManager.getCache(NpContextHolder.getContext().getUserId(), PRINT_ATTRVOCONFIG);
            Object paginateConfigObj = this.blockCacheManager.getCache(NpContextHolder.getContext().getUserId(), PRINT_PAGINATECONFIG);
            Object modelTypeObj = this.blockCacheManager.getCache(NpContextHolder.getContext().getUserId(), PRINT_MODELTYPE);
            ObjectMapper mapper = new ObjectMapper();
            QueryBlockDefine block = (QueryBlockDefine)mapper.readValue(queryBlockObj.toString(), QueryBlockDefine.class);
            PrintAttributeVo attrVoConfig = (PrintAttributeVo)mapper.readValue(attrVoConfigObj.toString(), PrintAttributeVo.class);
            TablePaginateConfig paginateConfig = (TablePaginateConfig)mapper.readValue(paginateConfigObj.toString(), TablePaginateConfig.class);
            QueryModelType modelType = QueryModelType.valueOf((String)mapper.readValue(modelTypeObj.toString(), String.class));
            DataQueryHelper queryHelper = new DataQueryHelper();
            Grid2Data grid2Data = queryHelper.getPrintGrid2Data(response, request, block, modelType);
            ByteArrayOutputStream out = this.queryPrint.print(grid2Data, block, attrVoConfig, paginateConfig);
            queryHelper.printGrid2Data(response, request, out, block);
            this.blockCacheManager.reSetCache(NpContextHolder.getContext().getUserId(), PRINT_QUERYBLOCK);
            this.blockCacheManager.reSetCache(NpContextHolder.getContext().getUserId(), PRINT_ATTRVOCONFIG);
            this.blockCacheManager.reSetCache(NpContextHolder.getContext().getUserId(), PRINT_PAGINATECONFIG);
            this.blockCacheManager.reSetCache(NpContextHolder.getContext().getUserId(), PRINT_MODELTYPE);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping(value={"/query-printBlockCache"}, method={RequestMethod.POST})
    public void printBlockCache(@RequestBody Map<String, Object> printCacheMap) {
        try {
            Object queryBlockStr = printCacheMap.get(PRINT_QUERYBLOCK);
            Object attrVoConfig = printCacheMap.get(PRINT_ATTRVOCONFIG);
            Object paginateConfig = printCacheMap.get(PRINT_PAGINATECONFIG);
            Object modelType = printCacheMap.get(PRINT_MODELTYPE);
            QueryCacheManager blockCacheManager = (QueryCacheManager)BeanUtil.getBean(QueryCacheManager.class);
            blockCacheManager.setItem(NpContextHolder.getContext().getUserId(), PRINT_QUERYBLOCK, queryBlockStr);
            blockCacheManager.setItem(NpContextHolder.getContext().getUserId(), PRINT_ATTRVOCONFIG, attrVoConfig);
            blockCacheManager.setItem(NpContextHolder.getContext().getUserId(), PRINT_PAGINATECONFIG, paginateConfig);
            blockCacheManager.setItem(NpContextHolder.getContext().getUserId(), PRINT_MODELTYPE, modelType);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @PostMapping(value={"/print/start"})
    @ApiOperation(value="\u5f00\u59cb\u6253\u5370")
    public AsyncTaskInfo startbatchPrint(@RequestBody Map<String, Object> printCacheMap, HttpServletResponse response, HttpServletRequest request) {
        Object queryBlockObj = printCacheMap.get(PRINT_QUERYBLOCK);
        Object attrVoConfigObj = printCacheMap.get(PRINT_ATTRVOCONFIG);
        Object paginateConfigObj = printCacheMap.get(PRINT_PAGINATECONFIG);
        Object modelTypeObj = printCacheMap.get(PRINT_MODELTYPE);
        try {
            ObjectMapper mapper = new ObjectMapper();
            QueryBlockDefine block = (QueryBlockDefine)mapper.readValue(queryBlockObj.toString(), QueryBlockDefine.class);
            PrintAttributeVo attrVoConfig = (PrintAttributeVo)mapper.readValue(attrVoConfigObj.toString(), PrintAttributeVo.class);
            TablePaginateConfig paginateConfig = (TablePaginateConfig)mapper.readValue(paginateConfigObj.toString(), TablePaginateConfig.class);
            QueryModelType modelType = null;
            if (modelTypeObj != null) {
                modelType = QueryModelType.valueOf((String)mapper.readValue(modelTypeObj.toString(), String.class));
            }
            String taskId = UUID.randomUUID().toString();
            String PrinterID = request.getHeader("PrinterID");
            SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, this.cacheObjectResourceRemote);
            PrinterDevice printerDevice = AsyncWorkContainnerUtil.getPrinterDevice((String)PrinterID);
            DataQueryHelper queryHelper = new DataQueryHelper();
            Grid2Data grid2Data = queryHelper.getPrintGrid2Data(response, request, block, modelType);
            this.npApplication.asyncRun(() -> {
                try {
                    this.printService.print(printerDevice, asyncTaskMonitor, block, grid2Data, attrVoConfig, paginateConfig);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            });
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(taskId);
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)"");
            asyncTaskInfo.setState(TaskState.PROCESSING);
            asyncTaskInfo.setUrl("/api/v1/dataentry/actions/progress/query?progressId=");
            return asyncTaskInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @PostMapping(value={"/query-getDefaultWordLabel"})
    public PrintWordVo getDefaultPrintWordVo() {
        PrintWordVo wordLabel = new PrintWordVo();
        return wordLabel;
    }

    @PostMapping(value={"/query-checkFormulaParse"})
    public String checkFormulaParse(@RequestBody Map<String, String> formula) {
        try {
            String formulaVal = formula.get("formula");
            boolean res = QueryHelper.checkFormula(formulaVal);
            return String.valueOf(res);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Deprecated
    @RequestMapping(value={"/query-treeRootByMasterKey"}, method={RequestMethod.POST})
    public List<ITree<QueryEntityDataObject>> queryTreeRootByMasterKey(@RequestBody List<QueryDimensionDefine> dims, @RequestParam(value="defaultStartDate") String start, @RequestParam(value="defaultEndDate") String end, @RequestParam(value="pType") String ptype) {
        try {
            QueryHelper helper = new QueryHelper();
            List<String> periods = helper.queryPeriodsInDim(dims, start, end, ptype);
            DimensionValueSet dateValSet = new DimensionValueSet();
            if (periods.size() > 0) {
                dateValSet.setValue("DATATIME", (Object)periods.get(periods.size() - 1));
            }
            DataQueryHelper queryHelper = new DataQueryHelper();
            return queryHelper.queryTreeRootByMasterKey(dims.get(0), dateValSet);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Deprecated
    @RequestMapping(value={"/query-unitByMaster/children"}, method={RequestMethod.POST})
    public List<ITree<QueryEntityDataObject>> queryEntityChildren(@RequestBody List<QueryDimensionDefine> dims, @RequestParam(value="defaultStartDate") String start, @RequestParam(value="defaultEndDate") String end, @RequestParam(value="pType") String ptype, @RequestParam(value="viewKey") String viewKey, @RequestParam(value="isEntity") String isEntity, @RequestParam(value="key") String key) {
        List<ITree<QueryEntityDataObject>> children = null;
        DataQueryHelper dataQueryHelper = new DataQueryHelper();
        try {
            QueryHelper helper = new QueryHelper();
            List<String> periods = helper.queryPeriodsInDim(dims, start, end, ptype);
            DimensionValueSet dateValSet = new DimensionValueSet();
            if (periods.size() > 0) {
                dateValSet.setValue("DATATIME", (Object)periods.get(periods.size() - 1));
            }
            HashMap<String, String> tb = new HashMap<String, String>();
            tb.put("viewKey", viewKey);
            tb.put("isEntity", isEntity);
            tb.put("key", key);
            children = dataQueryHelper.queryEntityRootsByEntityAndDateV(tb, dateValSet);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return children;
    }

    @RequestMapping(value={"/query-unitByMaster/selectionUnitEntity"}, method={RequestMethod.POST})
    public List<QueryEntityData> querySelectionUnitEntity(@RequestBody QueryDimensionDefine dimension) {
        List<QueryEntityData> children = null;
        try {
            DataQueryHelper queryHelper = new DataQueryHelper();
            QueryLayoutType layoutType = dimension.getLayoutType();
            String viewId = dimension.getViewId();
            if (layoutType == QueryLayoutType.LYT_ROW || layoutType == QueryLayoutType.LYT_COL) {
                children = queryHelper.queryEntityData(dimension.getSelectItems());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return children;
    }

    @RequestMapping(value={"/query-bizFieldLink"}, method={RequestMethod.POST})
    public String getBizGrid(@RequestBody Map<String, Object> param) {
        try {
            QueryHelper helper = new QueryHelper();
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                Object value = entry.getValue();
                if (StringUtils.isEmpty((CharSequence)value.toString())) continue;
                Map bizField = (Map)entry.getValue();
                String regionKey = bizField.get("RegionKey").toString();
                String key = bizField.get("FieldKey").toString();
                List<DataLinkDefine> linkDefines = helper.getAllLinkDefinesInRegion(regionKey);
                for (DataLinkDefine linkDefine : linkDefines) {
                    if (!linkDefine.getLinkExpression().equals(key)) continue;
                    jsonObject.put(key, (Object)linkDefine.getKey());
                }
            }
            return jsonObject.toString();
        }
        catch (Exception e) {
            logger.error("\u4e1a\u52a1\u4e3b\u952e\u94fe\u63a5\u83b7\u53d6\u9519\u8bef\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value={"/query-getFieldsDimNames"}, method={RequestMethod.POST})
    public List<String> getFieldDimName(@RequestBody List<QuerySelectField> params) {
        ArrayList<String> rtn = new ArrayList<String>();
        for (QuerySelectField field : params) {
            try {
                TableModelDefine tableModelDefine = null;
                tableModelDefine = Boolean.parseBoolean(field.getIsMaster()) ? (field.getDataSheet() == null ? this.dataModelService.getTableModelDefineById(field.getTableKey()) : this.dataModelService.getTableModelDefineById(field.getTableKey())) : this.dataModelService.getTableModelDefineById(field.getTableKey());
                ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), field.getFileExtension());
                FieldDefine f = this.columnModelFinder.findFieldDefine(columnModelDefine);
                if ("DW".equals(field.getFileExtension()) && f == null) {
                    columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), "MDCODE");
                    f = this.columnModelFinder.findFieldDefine(columnModelDefine);
                }
                String dimName = QueryHelper.getDimName(f);
                rtn.add(dimName);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return rtn;
    }
}

