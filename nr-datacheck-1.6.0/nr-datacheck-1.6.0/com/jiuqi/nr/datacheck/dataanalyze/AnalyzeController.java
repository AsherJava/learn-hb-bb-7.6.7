/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult
 *  com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService
 *  com.jiuqi.bsp.contentcheckrules.service.impl.ContentCheckServiceFactory
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.multcheck2.bean.MCErrorDescription
 *  com.jiuqi.nr.multcheck2.bean.MultcheckResOrg
 *  com.jiuqi.nr.multcheck2.dynamic.IMCContextService
 *  com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService
 *  com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService
 *  com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO
 *  com.jiuqi.nr.multcheck2.web.vo.MCLabel
 *  com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeContext
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.datacheck.dataanalyze;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult;
import com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService;
import com.jiuqi.bsp.contentcheckrules.service.impl.ContentCheckServiceFactory;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeErrorCheckService;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModel;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModelAuth;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeRuleGroupParam;
import com.jiuqi.nr.datacheck.dataanalyze.CheckCondition;
import com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType;
import com.jiuqi.nr.datacheck.dataanalyze.vo.AnalyzeErrorInfo;
import com.jiuqi.nr.datacheck.dataanalyze.vo.AnalyzeErrorInfoAddVO;
import com.jiuqi.nr.datacheck.dataanalyze.vo.AnalyzeParam;
import com.jiuqi.nr.datacheck.dataanalyze.vo.AnalyzeResult;
import com.jiuqi.nr.datacheck.dataanalyze.vo.BatchDelErrorParam;
import com.jiuqi.nr.datacheck.dataanalyze.vo.CheckModelState;
import com.jiuqi.nr.datacheck.dataanalyze.vo.CheckUnitState;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ErrorNode;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ErrorParam;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ItemOrgParam;
import com.jiuqi.nr.datacheck.dataanalyze.vo.OrgStateVO;
import com.jiuqi.nr.datacheck.dataanalyze.vo.ResourceNodeVO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import com.jiuqi.nr.multcheck2.bean.MultcheckResOrg;
import com.jiuqi.nr.multcheck2.dynamic.IMCContextService;
import com.jiuqi.nr.multcheck2.dynamic.IMCErrorInfoService;
import com.jiuqi.nr.multcheck2.dynamic.IMCItemOrgService;
import com.jiuqi.nr.multcheck2.service.dto.MCOrgTreeDTO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeContext;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datacheck/dataanalyze"})
public class AnalyzeController {
    private static final Logger logger = LoggerFactory.getLogger(AnalyzeController.class);
    public static final String CONFIRM_DESC = "CONFIRM-TRUE";
    public static final String TYPE_UNIT = "unit";
    public static final String TYPE_SCHEME = "scheme";
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;
    @Autowired
    private IMCErrorInfoService errorInfoService;
    @Autowired
    private IMCContextService mcContextService;
    @Autowired
    private IMCItemOrgService orgService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private AnalyzeErrorCheckService errorCheckService;
    @Autowired
    private ContentCheckServiceFactory checkFactory;

    @PostMapping(value={"/get-tree"})
    public Map<String, Object> getTree(@RequestBody List<AnalyzeModel> models) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        AnalyzeModel rootModel = new AnalyzeModel("root", "root", "\u6570\u636e\u5206\u6790", null, "GROUP");
        ITree root = new ITree((INode)rootModel);
        root.setExpanded(true);
        List<ITree<AnalyzeModel>> nodeList = this.getChildren("root");
        root.setChildren(nodeList.stream().filter(e -> !CollectionUtils.isEmpty(e.getChildren())).collect(Collectors.toList()));
        map.put("tree", Collections.singletonList(root));
        ArrayList<AnalyzeModel> resultList = new ArrayList<AnalyzeModel>();
        for (AnalyzeModel model : models) {
            ResourceTreeNode resource = this.resourceTreeNodeService.get(this.buildContext(), model.getKey());
            if (resource == null) continue;
            resultList.add(model);
        }
        map.put("resultList", resultList);
        return map;
    }

    @PostMapping(value={"/get-nodes"})
    public List<AnalyzeModelAuth> getNodes(@RequestBody List<AnalyzeModel> models) throws Exception {
        ArrayList<AnalyzeModelAuth> nodes = new ArrayList<AnalyzeModelAuth>();
        for (AnalyzeModel model : models) {
            ResourceTreeNode resource = this.resourceTreeNodeService.get(this.buildContext(), model.getKey());
            if (resource == null) {
                nodes.add(new AnalyzeModelAuth(model, false));
                continue;
            }
            nodes.add(new AnalyzeModelAuth(model, true));
        }
        return nodes;
    }

    @PostMapping(value={"/export"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@RequestBody AnalyzeParam param, HttpServletResponse response) throws Exception {
        param.setEntityLabelValues(param.getDimSet());
        AnalyzeResult models = this.getModels(param);
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");
        Row head = sheet.createRow(0);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("\u9ed1\u4f53");
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setFont(font);
        ArrayList<String> cols = new ArrayList<String>();
        cols.add("\u5355\u4f4d");
        for (String key : models.getEntitys().keySet()) {
            cols.add(models.getEntitys().get(key));
        }
        cols.add("\u6a21\u677f");
        cols.add("\u5ba1\u6838\u7ed3\u679c");
        cols.add("\u9519\u8bef\u8bf4\u660e");
        for (int i = 0; i < cols.size(); ++i) {
            Cell cell = head.createCell(i);
            cell.setCellValue((String)cols.get(i));
            cell.setCellStyle(headStyle);
            sheet.setColumnWidth(i, 6000);
        }
        int rowNum = 1;
        for (AnalyzeErrorInfo error : models.getErrors()) {
            int colNum = 0;
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(colNum++);
            cell.setCellValue(error.getOrg());
            for (String key : error.getDimStr().keySet()) {
                cell = row.createCell(colNum++);
                cell.setCellValue(error.getDimStr().get(key));
            }
            cell = row.createCell(colNum++);
            cell.setCellValue(error.getResourceStr());
            cell = row.createCell(colNum++);
            cell.setCellValue(error.getState().title());
            cell = row.createCell(colNum++);
            cell.setCellValue(CONFIRM_DESC.equals(error.getDescription()) ? "\u5df2\u786e\u8ba4" : error.getDescription());
        }
        try {
            String fileName = "\u51fa\u9519\u8bf4\u660e.xls";
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            BufferedOutputStream outputStream = new BufferedOutputStream((OutputStream)response.getOutputStream());
            response.setContentType("application/octet-stream");
            workbook.write(outputStream);
            ((OutputStream)outputStream).flush();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @PostMapping(value={"/init"})
    public AnalyzeResult getModels(@Valid @RequestBody AnalyzeParam param) throws Exception {
        HashMap<String, Integer> modelState;
        HashMap<String, List<MultcheckResOrg>> orgResMap;
        DsContextImpl newContext = (DsContextImpl)DsContextHolder.createEmptyContext();
        newContext.setEntityId(param.getOrgEntity());
        DsContextHolder.setDsContext((DsContext)newContext);
        AnalyzeResult result = new AnalyzeResult();
        HashMap<String, ResourceNodeVO> modelMap = new HashMap<String, ResourceNodeVO>();
        ArrayList<ResourceNodeVO> nodes = new ArrayList<ResourceNodeVO>();
        ArrayList<String> models = new ArrayList<String>();
        for (String guid : param.getModels()) {
            ResourceTreeNode treeNode = this.resourceTreeNodeService.get(this.buildContext(), guid);
            if (treeNode == null) continue;
            ResourceNodeVO resource = new ResourceNodeVO(treeNode);
            nodes.add(resource);
            modelMap.put(guid, resource);
            models.add(guid);
        }
        result.setTabs(nodes);
        String task = param.getTask();
        String period = param.getPeriod();
        boolean hasSelected = !CollectionUtils.isEmpty(param.getEntityLabelValues());
        result.setEntityLabelValues(hasSelected ? param.getEntityLabelValues() : new HashMap<String, String>());
        HashMap<String, List<MCLabel>> entityLabels = new HashMap<String, List<MCLabel>>();
        result.setEntityLabels(entityLabels);
        HashMap<String, DimensionValue> dimSet = new HashMap<String, DimensionValue>();
        for (String key : param.getDimSet().keySet()) {
            DimensionValue dimensionValue = new DimensionValue();
            String value = param.getDimSet().get(key);
            if (value.contains(";")) {
                result.setHasEntityLabels(true);
                entityLabels.put(key, new ArrayList());
                if (!hasSelected) {
                    result.getEntityLabelValues().put(key, value.split(";")[0]);
                }
            }
            dimensionValue.setValue(value);
            dimensionValue.setName(key);
            dimSet.put(key, dimensionValue);
        }
        List<String> orgCodes = param.getOrgCodes();
        List labelList = this.mcContextService.getOrgLabels(task, period, param.getOrgEntity(), orgCodes);
        Map<String, MCLabel> orgMap = labelList.stream().collect(Collectors.toMap(MCLabel::getCode, l -> l));
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        List dimsList = this.mcContextService.getDynamicFieldsByTask(taskDefine.getKey());
        List dimsPageList = this.mcContextService.getDynamicDimNamesForPage(taskDefine.getDataScheme());
        HashMap<String, Map<String, String>> entityValues = new HashMap<String, Map<String, String>>();
        boolean hasDim = false;
        if (!CollectionUtils.isEmpty(dimsList)) {
            hasDim = true;
            HashMap<String, String> entitys = new HashMap<String, String>();
            result.setEntitys(entitys);
            if (!CollectionUtils.isEmpty(dimsPageList)) {
                for (String dimKey : dimsPageList) {
                    DimensionValue dimensionValue = (DimensionValue)dimSet.get(dimKey);
                    EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(dimKey);
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimKey);
                    entitys.put(dimKey, entityDefine.getTitle());
                    HashMap<String, String> dimValues = new HashMap<String, String>();
                    entityValues.put(dimKey, dimValues);
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue("DATATIME", (Object)period);
                    if (dimensionValue != null && StringUtils.hasText(dimensionValue.getValue()) && !"PROVIDER_BASECURRENCY".equals(dimensionValue.getValue()) && !"PROVIDER_PBASECURRENCY".equals(dimensionValue.getValue())) {
                        dimensionValueSet.setValue(dimKey, Arrays.asList(dimensionValue.getValue().split(";")));
                    }
                    IEntityQuery query = this.entityDataService.newEntityQuery();
                    query.sorted(true);
                    query.setAuthorityOperations(AuthorityType.Read);
                    query.setEntityView(entityViewDefine);
                    query.setMasterKeys(dimensionValueSet);
                    ExecutorContext context1 = new ExecutorContext(this.dataDefinitionRuntimeController);
                    context1.setVarDimensionValueSet(dimensionValueSet);
                    context1.setPeriodView(taskDefine.getDateTime());
                    IEntityTable entityTable = query.executeReader((IContext)context1);
                    List allrows = entityTable.getAllRows();
                    for (Object row : allrows) {
                        dimValues.put(row.getCode(), row.getTitle());
                        if (!entityLabels.containsKey(dimKey)) continue;
                        ((List)entityLabels.get(dimKey)).add(new MCLabel(row.getCode(), row.getTitle()));
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(result.getEntityLabelValues())) {
            for (String key : dimSet.keySet()) {
                ((DimensionValue)dimSet.get(key)).setValue(result.getEntityLabelValues().get(key));
            }
        }
        ContentCheckByGroupService checkService = this.checkFactory.getCheckService();
        if (TYPE_SCHEME.equals(param.getType())) {
            ArrayList<ITree<ErrorNode>> modelNodes = new ArrayList<ITree<ErrorNode>>();
            ITree modelRoot = new ITree();
            ArrayList<ITree> modelNodeChild = new ArrayList<ITree>();
            result.setModels(modelNodes);
            modelNodes.add((ITree<ErrorNode>)modelRoot);
            modelRoot.setChildren(modelNodeChild);
            modelRoot.setKey("ROOT");
            modelRoot.setCode("ROOT");
            modelRoot.setTitle("\u5168\u90e8\u6a21\u677f");
            modelRoot.setLeaf(false);
            modelRoot.setExpanded(true);
            modelRoot.setSelected(true);
            orgResMap = new HashMap<String, List<MultcheckResOrg>>();
            modelState = new HashMap();
            long t = System.currentTimeMillis();
            List orgResList = this.orgService.getByItem(param.getRecordKey(), param.getItemKey(), param.getTask());
            logger.info("orgService.getByItem=" + orgResList.size() + "::time=" + (System.currentTimeMillis() - t));
            this.buildOrgModelState(models, orgResMap, modelState, orgResList);
            DimensionCollection dimensionCollection = this.mcContextService.buildDimensionCollection(task, period, orgCodes, dimSet);
            List combinations = dimensionCollection.getDimensionCombinations();
            HashMap<String, List<MCErrorDescription>> errorMap = new HashMap<String, List<MCErrorDescription>>();
            HashMap<String, ArrayList<AnalyzeErrorInfo>> errorVoMap = new HashMap<String, ArrayList<AnalyzeErrorInfo>>();
            t = System.currentTimeMillis();
            this.buildResoureErrorMap(task, period, orgCodes, errorMap, models);
            logger.info("buildResoureErrorMap=" + errorMap.size() + "::time=" + (System.currentTimeMillis() - t));
            for (String model : models) {
                ResourceNodeVO resource = (ResourceNodeVO)modelMap.get(model);
                ITree modelNode = new ITree();
                modelNode.setCode(resource.getGuid());
                modelNode.setKey(resource.getGuid());
                modelNode.setTitle(resource.getTitle());
                modelNode.setLeaf(true);
                modelNodeChild.add(modelNode);
                Integer state = (Integer)modelState.get(model);
                this.buildModelState(param, resource, String.valueOf(state));
                String icon_state = "";
                switch (resource.getState()) {
                    case SUCCESS: {
                        icon_state = "#icon-J_HXGLB_C_ZFJS_GreenDot";
                        break;
                    }
                    case SUCCESS_ERROR: {
                        icon_state = "#icon-J_HXGLB_C_ZFJS_YellowDot";
                        break;
                    }
                    default: {
                        icon_state = "#icon-J_HXGLB_C_ZFJS_RedDot";
                    }
                }
                modelNode.setIcons(new String[]{icon_state});
                for (DimensionCombination combination : combinations) {
                    String org = (String)combination.getDWDimensionValue().getValue();
                    String key = org + "@" + model;
                    AnalyzeErrorInfo error = this.createError(param, dimsList, entityValues, hasDim, errorMap, orgResMap, combination, model, orgMap, modelMap, checkService);
                    ArrayList<AnalyzeErrorInfo> analyzeErrors = (ArrayList<AnalyzeErrorInfo>)errorVoMap.get(key);
                    if (analyzeErrors == null) {
                        analyzeErrors = new ArrayList<AnalyzeErrorInfo>();
                        errorVoMap.put(key, analyzeErrors);
                    }
                    analyzeErrors.add(error);
                }
            }
            ArrayList<AnalyzeErrorInfo> analyzeErrorInfoList = new ArrayList<AnalyzeErrorInfo>();
            for (MCLabel o : labelList) {
                for (String model : models) {
                    analyzeErrorInfoList.addAll((Collection)errorVoMap.get(o.getCode() + "@" + model));
                }
            }
            result.setErrors(analyzeErrorInfoList);
        } else {
            HashMap<String, List<MCErrorDescription>> errorMap = new HashMap<String, List<MCErrorDescription>>();
            ArrayList<AnalyzeErrorInfo> analyzeErrorList = new ArrayList<AnalyzeErrorInfo>();
            result.setErrors(analyzeErrorList);
            List errors = this.errorInfoService.getByResourcesAndOrg(task, period, "dataanalyze", models, orgCodes.get(0));
            if (!CollectionUtils.isEmpty(errors)) {
                for (MCErrorDescription error : errors) {
                    this.addError(errorMap, error);
                }
            }
            orgResMap = new HashMap();
            modelState = new HashMap<String, Integer>();
            List orgResList = this.orgService.getByOrg(param.getRecordKey(), param.getItemKey(), param.getTask(), orgCodes.get(0));
            this.buildOrgModelState(models, orgResMap, modelState, orgResList);
            for (String model : models) {
                this.buildModelState(param, (ResourceNodeVO)modelMap.get(model), String.valueOf((Integer)modelState.get(model)));
            }
            DimensionCollection dimensionCollection = this.mcContextService.buildDimensionCollection(task, period, orgCodes, dimSet);
            List combinations = dimensionCollection.getDimensionCombinations();
            for (DimensionCombination combination : combinations) {
                for (String model : models) {
                    analyzeErrorList.add(this.createError(param, dimsList, entityValues, hasDim, errorMap, orgResMap, combination, model, orgMap, modelMap, checkService));
                }
            }
        }
        return result;
    }

    private void buildOrgModelState(List<String> models, Map<String, List<MultcheckResOrg>> orgResMap, Map<String, Integer> modelState, List<MultcheckResOrg> orgResList) {
        if (!CollectionUtils.isEmpty(orgResList)) {
            for (MultcheckResOrg orgRes : orgResList) {
                if (!models.contains(orgRes.getResource())) continue;
                this.addOrgRes(orgResMap, orgRes);
                this.addModelState(modelState, orgRes.getResource(), orgRes.getResult());
            }
        }
    }

    private void buildModelState(AnalyzeParam param, ResourceNodeVO resourceNodeVO, String result) {
        if (param.getCheckRequires() == null) {
            if ("TRUE".equals(result)) {
                resourceNodeVO.setState(CheckModelState.SUCCESS);
            } else if ("FALSE".equals(result)) {
                resourceNodeVO.setState(CheckModelState.FAILED);
            }
        } else {
            OrgStateVO state = new OrgStateVO(result);
            if (CheckCondition.NEEDERROR == param.getCheckRequires()) {
                if (state.isOrgRes()) {
                    resourceNodeVO.setState(state.isFmlRes() ? CheckModelState.SUCCESS : CheckModelState.SUCCESS_ERROR);
                } else {
                    resourceNodeVO.setState(CheckModelState.FAILED);
                }
            } else {
                resourceNodeVO.setState(state.isOrgRes() ? CheckModelState.SUCCESS : CheckModelState.FAILED);
            }
        }
    }

    private void addModelState(Map<String, Integer> modelState, String model, String result) {
        Integer state = modelState.get(model);
        if ("TRUE".equals(result)) {
            result = "7";
        } else if ("FALSE".equals(result)) {
            result = "0";
        }
        if (state == null) {
            modelState.put(model, Integer.valueOf(result));
        } else {
            int res = state & Integer.valueOf(result);
            modelState.put(model, res);
        }
    }

    @PostMapping(value={"/get-models"})
    public List<ResourceNodeVO> getModels(@RequestBody List<String> guids) throws Exception {
        ArrayList<ResourceNodeVO> nodes = new ArrayList<ResourceNodeVO>();
        for (String guid : guids) {
            ResourceTreeNode treeNode = this.resourceTreeNodeService.get(this.buildContext(), guid);
            if (treeNode == null) continue;
            nodes.add(new ResourceNodeVO(treeNode));
        }
        return nodes;
    }

    @PostMapping(value={"/get-success-org-resource"})
    public Map<String, String> getItemOrgSuccess(@Valid @RequestBody ItemOrgParam param) {
        HashMap<String, String> res = new HashMap<String, String>();
        HashMap dims = new HashMap();
        if (!CollectionUtils.isEmpty(param.getDimSet())) {
            for (Map.Entry entry : param.getDimSet().entrySet()) {
                dims.put(entry.getKey(), ((DimensionValue)entry.getValue()).getValue());
            }
        }
        for (String string : param.getOrgs()) {
            List orgResList = this.orgService.getByOrg(param.getRecordKey(), param.getItemKey(), param.getTask(), "dataanalyze", string, dims);
            String successModels = "";
            if (!CollectionUtils.isEmpty(orgResList)) {
                for (MultcheckResOrg orgRes : orgResList) {
                    if (!orgRes.getResult().equals("TRUE")) continue;
                    successModels = successModels + orgRes.getResource() + ";";
                }
            }
            res.put(string, successModels);
        }
        return res;
    }

    @GetMapping(value={"/get-model-org/{task}/{period}/{org}/{orgSelectType}"})
    public List<String> getOrgModels(@PathVariable String task, @PathVariable String period, @PathVariable String org, @PathVariable OrgSelectType orgSelectType) throws Exception {
        task = HtmlUtils.cleanUrlXSS((String)task);
        period = HtmlUtils.cleanUrlXSS((String)period);
        org = HtmlUtils.cleanUrlXSS((String)org);
        ArrayList<String> res = new ArrayList<String>();
        IEntityTable entityTable = this.getEntityTable(task, period);
        res.add(org);
        List childRows = null;
        if (OrgSelectType.UCURRENTDIRECTSUB == orgSelectType) {
            childRows = entityTable.getChildRows(org);
        } else if (OrgSelectType.UCURRENTALLSUB == orgSelectType) {
            childRows = entityTable.getAllChildRows(org);
        }
        if (!CollectionUtils.isEmpty(childRows)) {
            res.addAll(childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet()));
        }
        return res;
    }

    private IEntityTable getEntityTable(String taskKey, String period) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        EntityViewDefine entityView = this.runTimeViewController.getViewByTaskDefineKey(taskKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        query.sortedByQuery(false);
        SchemePeriodLinkDefine scheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, scheme.getSchemeKey());
        context.setEnv((IFmlExecEnvironment)environment);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(taskDefine.getDateTime());
        return query.executeFullBuild((IContext)context);
    }

    @PostMapping(value={"/get-org-tree"})
    public List<ITree<OrgTreeNode>> getOrgTree(@RequestBody AnalyzeParam param) throws Exception {
        MCOrgTreeDTO orgTreeDTO = this.mcContextService.getOrgTreeByTaskPeriodOrg(param.getTask(), param.getPeriod(), param.getOrgEntity(), param.getFormScheme(), param.getOrgCodes());
        return orgTreeDTO.getTreeList();
    }

    private List<ITree<AnalyzeModel>> getChildren(String parentId) throws DataAnalyzeResourceException {
        ArrayList<ITree<AnalyzeModel>> groupList = new ArrayList<ITree<AnalyzeModel>>();
        List treeNodes = this.resourceTreeNodeService.getChildren(this.buildContext(), parentId);
        for (ResourceTreeNode treeNode : treeNodes) {
            ITree<AnalyzeModel> node;
            if (treeNode.isFolder()) {
                List<ITree<AnalyzeModel>> children = this.getChildren(treeNode.getGuid());
                if (children.isEmpty()) continue;
                ITree<AnalyzeModel> node2 = this.convertToNode(treeNode, false, null, "GROUP");
                node2.setChildren(children);
                groupList.add(node2);
                continue;
            }
            if ("com.jiuqi.nr.zbquery.manage".equals(treeNode.getType())) {
                node = this.convertToNode(treeNode, true, new String[]{"#icon-16_DH_A_NR_guoluchaxun"}, treeNode.getType());
                groupList.add(node);
            }
            if (!"com.jiuqi.nvwa.quickreport.business".equals(treeNode.getType())) continue;
            node = this.convertToNode(treeNode, true, new String[]{"#icon16_SHU_A_NW_kuaisubaobiao"}, treeNode.getType());
            groupList.add(node);
        }
        return groupList;
    }

    private ResourceTreeContext buildContext() {
        ResourceTreeContext context = new ResourceTreeContext();
        context.setUserId(NpContextHolder.getContext().getIdentityId());
        context.setPrivilegeId("dataanalysis_read");
        return context;
    }

    private ITree<AnalyzeModel> convertToNode(ResourceTreeNode resource, boolean leaf, String[] icons, String type) {
        AnalyzeModel treeNode = new AnalyzeModel(resource.getGuid(), resource.getName(), resource.getTitle(), null, type);
        ITree node = new ITree((INode)treeNode);
        node.setExpanded(false);
        node.setLeaf(leaf);
        if (icons != null) {
            node.setIcons(icons);
        }
        return node;
    }

    private void buildResoureErrorMap(String task, String period, List<String> orgCodes, Map<String, List<MCErrorDescription>> errorMap, List<String> models) throws Exception {
        List errors = this.errorInfoService.getByResourcesAndOrgs(task, period, "dataanalyze", models, orgCodes);
        if (!CollectionUtils.isEmpty(errors)) {
            for (MCErrorDescription error : errors) {
                this.addError(errorMap, error);
            }
        }
    }

    private AnalyzeErrorInfo createError(AnalyzeParam param, List<String> dimsList, Map<String, Map<String, String>> entityValues, boolean hasDim, Map<String, List<MCErrorDescription>> errorMap, Map<String, List<MultcheckResOrg>> orgResMap, DimensionCombination combination, String model, Map<String, MCLabel> orgMap, Map<String, ResourceNodeVO> modelMap, ContentCheckByGroupService checkService) {
        String task = param.getTask();
        String period = param.getPeriod();
        CheckCondition checkRequires = param.getCheckRequires();
        String org = (String)combination.getDWDimensionValue().getValue();
        String key = org + "@" + model;
        List<MultcheckResOrg> orgResList = orgResMap.get(key);
        ResourceNodeVO resource = modelMap.get(model);
        List<MCErrorDescription> errorList = errorMap.get(key);
        if (CollectionUtils.isEmpty(errorList)) {
            AnalyzeErrorInfo error = this.initError(task, period, org, resource, orgMap);
            this.initErrorDim(dimsList, entityValues, hasDim, combination, error);
            MultcheckResOrg checkOrgRes = hasDim ? this.getSameOrgRes(dimsList, combination, orgResList) : orgResList.get(0);
            error.setState(this.buildOrgResState(checkOrgRes, checkRequires));
            return error;
        }
        if (hasDim) {
            AnalyzeErrorInfo error = null;
            MCErrorDescription sameError = this.getSameError(dimsList, combination, errorList);
            error = sameError == null ? this.initError(task, period, org, resource, orgMap) : this.initErrorByMCError(orgMap, org, resource, sameError);
            this.initErrorDim(dimsList, entityValues, true, combination, error);
            MultcheckResOrg checkOrgRes = this.getSameOrgRes(dimsList, combination, orgResList);
            error.setState(this.buildOrgResState(checkOrgRes, checkRequires));
            this.checkContentForTable(param, checkService, error);
            return error;
        }
        AnalyzeErrorInfo error = this.initErrorByMCError(orgMap, org, resource, errorList.get(0));
        MultcheckResOrg checkOrgRes = orgResList.get(0);
        error.setState(this.buildOrgResState(checkOrgRes, checkRequires));
        this.checkContentForTable(param, checkService, error);
        return error;
    }

    private void checkContentForTable(AnalyzeParam param, ContentCheckByGroupService checkService, AnalyzeErrorInfo error) {
        ContentCheckResult checkResult;
        if (error.getState() == CheckUnitState.FAILED_ERROR_ILLEGAL && StringUtils.hasText(error.getDescription()) && !(checkResult = this.checkContent(param.getTask(), param.getCheckSchemeKey(), param.getFormScheme(), param.getOrgEntity(), error.getDescription(), checkService)).getStatus()) {
            error.setTip(String.join((CharSequence)";", checkResult.getMessages()));
        }
    }

    private CheckUnitState buildOrgResState(MultcheckResOrg checkOrgRes, CheckCondition checkRequires) {
        String result = checkOrgRes.getResult();
        if (checkRequires == null) {
            if ("TRUE".equals(result)) {
                return CheckUnitState.SUCCESS;
            }
            return CheckUnitState.FAILED;
        }
        if ("TRUE".equals(result)) {
            result = "7";
        } else if ("FALSE".equals(result)) {
            result = "0";
        }
        OrgStateVO state = new OrgStateVO(result);
        if (CheckCondition.NEEDERROR == checkRequires) {
            if (state.isOrgRes()) {
                if (state.isFmlRes()) {
                    return CheckUnitState.SUCCESS;
                }
                return CheckUnitState.FAILED_ERROR_LEGAL;
            }
            if (state.isErrorRes()) {
                return CheckUnitState.FAILED_ERROR_ILLEGAL;
            }
            return CheckUnitState.FAILED_ERROR_NODE;
        }
        return state.isOrgRes() ? CheckUnitState.SUCCESS : CheckUnitState.FAILED;
    }

    @NotNull
    private AnalyzeErrorInfo initErrorByMCError(Map<String, MCLabel> orgMap, String org, ResourceNodeVO resource, MCErrorDescription sameError) {
        AnalyzeErrorInfo error = new AnalyzeErrorInfo(sameError);
        error.setOrgStr(orgMap.get(org).getTitle());
        error.setResourceStr(resource.getTitle());
        return error;
    }

    @Nullable
    private MCErrorDescription getSameError(List<String> dimsList, DimensionCombination combination, List<MCErrorDescription> errorList) {
        MCErrorDescription sameError = null;
        for (MCErrorDescription e : errorList) {
            boolean different = false;
            for (String dim : dimsList) {
                String errorDimValue;
                if (different) break;
                String dimValue = (String)combination.getValue(dim);
                if (dimValue.equals(errorDimValue = (String)e.getDims().get(dim))) continue;
                different = true;
            }
            if (different) continue;
            sameError = e;
            break;
        }
        return sameError;
    }

    private MultcheckResOrg getSameOrgRes(List<String> dimsList, DimensionCombination combination, List<MultcheckResOrg> orgResList) {
        MultcheckResOrg sameRes = null;
        for (MultcheckResOrg resOrg : orgResList) {
            boolean different = false;
            for (String dim : dimsList) {
                String errorDimValue;
                if (different) break;
                String dimValue = (String)combination.getValue(dim);
                if (dimValue.equals(errorDimValue = (String)resOrg.getDims().get(dim))) continue;
                different = true;
            }
            if (different) continue;
            sameRes = resOrg;
            break;
        }
        return sameRes;
    }

    private void initErrorDim(List<String> dimsList, Map<String, Map<String, String>> entityValues, boolean hasDim, DimensionCombination combination, AnalyzeErrorInfo error) {
        if (hasDim) {
            HashMap<String, String> dims = new HashMap<String, String>();
            HashMap<String, String> dimsStr = new HashMap<String, String>();
            error.setDims(dims);
            error.setDimStr(dimsStr);
            for (String dimKey : dimsList) {
                String dimValue = (String)combination.getValue(dimKey);
                dims.put(dimKey, dimValue);
                if (!entityValues.containsKey(dimKey)) continue;
                dimsStr.put(dimKey, entityValues.get(dimKey).get(dimValue));
            }
        }
    }

    private AnalyzeErrorInfo initError(String task, String period, String org, ResourceNodeVO resource, Map<String, MCLabel> orgMap) {
        AnalyzeErrorInfo error = new AnalyzeErrorInfo();
        error.setOrg(org);
        error.setOrgStr(orgMap.get(org).getTitle());
        error.setResource(resource.getGuid());
        error.setResourceStr(resource.getTitle());
        return error;
    }

    private void addError(Map<String, List<MCErrorDescription>> errorMap, MCErrorDescription e) {
        String key = e.getOrg() + "@" + e.getResource();
        List<MCErrorDescription> errorList = errorMap.get(key);
        if (errorList == null) {
            errorList = new ArrayList<MCErrorDescription>();
            errorMap.put(key, errorList);
        }
        errorList.add(e);
    }

    private void addOrgRes(Map<String, List<MultcheckResOrg>> orgResMap, MultcheckResOrg orgRes) {
        String key = orgRes.getOrg() + "@" + orgRes.getResource();
        List<MultcheckResOrg> errorList = orgResMap.get(key);
        if (errorList == null) {
            errorList = new ArrayList<MultcheckResOrg>();
            orgResMap.put(key, errorList);
        }
        errorList.add(orgRes);
    }

    @PostMapping(value={"/add-error"})
    public String addError(@Valid @RequestBody AnalyzeErrorInfoAddVO error) {
        MCErrorDescription errorDescription = new MCErrorDescription();
        BeanUtils.copyProperties((Object)error, errorDescription);
        errorDescription.setItemType("dataanalyze");
        try {
            ContentCheckResult checkResult = this.checkContent(error.getTask(), error.getCheckSchemeKey(), error.getFormSchemeKey(), error.getOrgEntity(), errorDescription.getDescription(), this.checkFactory.getCheckService());
            if (!checkResult.getStatus()) {
                return "FALSE" + String.join((CharSequence)";", checkResult.getMessages());
            }
            if (StringUtils.hasText(error.getKey())) {
                this.errorInfoService.modify(errorDescription);
                return error.getKey();
            }
            return this.errorInfoService.add(errorDescription);
        }
        catch (Exception e) {
            logger.error("\u6dfb\u52a0\u9519\u8bef\u8bf4\u660e\u5f02\u5e38\uff1a\uff1a", e);
            return "FALSE";
        }
    }

    private ContentCheckResult checkContent(String task, String mcScheme, String formScheme, String org, String content, ContentCheckByGroupService doRuleCheckService) {
        AnalyzeRuleGroupParam ruleParam = new AnalyzeRuleGroupParam();
        ruleParam.setTaskKey(task);
        ruleParam.setCheckSchemeKey(mcScheme);
        ruleParam.setFormSchemeKey(formScheme);
        ruleParam.setOrg(org);
        String ruleGroup = this.errorCheckService.getRuleGroup(ruleParam);
        return this.errorCheckService.checkContent(content, ruleGroup, doRuleCheckService);
    }

    @PostMapping(value={"/batch-add-errors"})
    @Transactional
    public String addErrors(@Valid @RequestBody ErrorParam param) {
        List<AnalyzeErrorInfo> errors = param.getErrors();
        if (CollectionUtils.isEmpty(errors)) {
            return "\u9009\u62e9\u5355\u4f4d\u6a21\u677f\u4e3a\u7a7a\uff01";
        }
        ContentCheckResult checkResult = this.checkContent(param.getTask(), param.getCheckSchemeKey(), param.getFormSchemeKey(), param.getOrgEntity(), param.getDescription(), this.checkFactory.getCheckService());
        if (!checkResult.getStatus()) {
            return String.join((CharSequence)";", checkResult.getMessages());
        }
        ArrayList<MCErrorDescription> adds = new ArrayList<MCErrorDescription>();
        ArrayList<String> keys = new ArrayList<String>();
        for (AnalyzeErrorInfo error : errors) {
            MCErrorDescription errorDescription = new MCErrorDescription();
            BeanUtils.copyProperties(error, errorDescription);
            errorDescription.setTask(param.getTask());
            errorDescription.setPeriod(param.getPeriod());
            errorDescription.setItemType("dataanalyze");
            if (StringUtils.hasText(error.getDescription()) && StringUtils.hasText(error.getKey())) {
                if (!param.isCover()) continue;
                keys.add(errorDescription.getKey());
                errorDescription.setDescription(param.getDescription());
                adds.add(errorDescription);
                continue;
            }
            errorDescription.setDescription(param.getDescription());
            adds.add(errorDescription);
        }
        try {
            long t = System.currentTimeMillis();
            if (!CollectionUtils.isEmpty(keys)) {
                this.errorInfoService.batchDeleteByKeys(keys, param.getTask());
            }
            logger.info("errorInfoService.batchDeleteByKeys=" + keys.size() + "::time=" + (System.currentTimeMillis() - t));
            t = System.currentTimeMillis();
            if (!CollectionUtils.isEmpty(adds)) {
                this.errorInfoService.batchAdd(adds, param.getTask());
            }
            logger.info("errorInfoService.batchAdd=" + adds.size() + "::time=" + (System.currentTimeMillis() - t));
        }
        catch (Exception e) {
            logger.error("\u6279\u91cf\u6dfb\u52a0\u9519\u8bef\u8bf4\u660e\u5f02\u5e38\uff1a\uff1a", e);
            return e.getMessage();
        }
        return "TRUE";
    }

    @GetMapping(value={"/del-error/{task}/{key}"})
    public String delError(@PathVariable String task, @PathVariable String key) {
        task = HtmlUtils.cleanUrlXSS((String)task);
        key = HtmlUtils.cleanUrlXSS((String)key);
        try {
            this.errorInfoService.deleteByKey(key, task);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "TRUE";
    }

    @PostMapping(value={"/batch-del-error"})
    public String delError(@Valid @RequestBody BatchDelErrorParam param) {
        try {
            this.errorInfoService.batchDeleteByOrgAndModel(param.getModelKeys(), param.getOrgCodes(), param.getTask(), param.getPeriod(), "dataanalyze");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "TRUE";
    }

    @PostMapping(value={"/batch-del-error/keys/{task}"})
    public String delError(@PathVariable String task, @RequestBody List<String> keys) {
        try {
            this.errorInfoService.batchDeleteByKeys(keys, task);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "TRUE";
    }
}

