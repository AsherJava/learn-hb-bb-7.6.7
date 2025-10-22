/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao
 *  com.jiuqi.nr.multcheck2.common.CheckRestultState
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgInfo
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.multcheck2.service.IMCSchemeService
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  nr.single.data.bean.CheckResultNode
 *  nr.single.data.treecheck.service.IEntityTreeCheckService
 */
package com.jiuqi.nr.datacheck.entitytree.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.datacheck.entitytree.TreeCheckConfig;
import com.jiuqi.nr.datacheck.entitytree.service.ITreeCheckService;
import com.jiuqi.nr.datacheck.entitytree.vo.TreeCheckPM;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import nr.single.data.bean.CheckResultNode;
import nr.single.data.treecheck.service.IEntityTreeCheckService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class TreeCheckServiceImpl
implements ITreeCheckService {
    private static final Logger logger = LoggerFactory.getLogger(TreeCheckServiceImpl.class);
    @Autowired
    private MultCheckResDao multCheckResDao;
    @Autowired
    private IEntityTreeCheckService entityTreeCheckService;
    @Autowired
    private IMCSchemeService mcSchemeService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public CheckItemResult runCheck(CheckItemParam param) {
        AsyncTaskMonitor taskMonitor = param.getAsyncTaskMonitor();
        if (taskMonitor.isCancel()) {
            return null;
        }
        CheckItemResult result = this.initRes();
        try {
            List resultNodes = this.entityTreeCheckService.CheckTreeNodeByTask(param.getContext().getTaskKey(), param.getContext().getPeriod(), true, taskMonitor);
            if (taskMonitor.isCancel()) {
                return null;
            }
            taskMonitor.finish("\u6811\u5f62\u5ba1\u6838\u5b8c\u6210", null);
            ArrayList<CheckResultNode> mergedErrorNodes = new ArrayList<CheckResultNode>(resultNodes.size());
            this.buildResult(param, resultNodes, mergedErrorNodes, result);
            this.log(param, mergedErrorNodes);
        }
        catch (Exception e) {
            logger.error("\u6811\u5f62\u5ba1\u6838\u5931\u8d25", e);
            taskMonitor.error("\u6811\u5f62\u5ba1\u6838\u5931\u8d25", (Throwable)e);
            result.setResult(CheckRestultState.FAIL);
        }
        return result;
    }

    private void buildResult(CheckItemParam param, List<CheckResultNode> resultNodes, List<CheckResultNode> errNodes, CheckItemResult result) throws Exception {
        String entityID = ((DimensionCombination)param.getContext().getDims().getDimensionCombinations().get(0)).getDWDimensionValue().getEntityID();
        String taskKey = param.getContext().getTaskKey();
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        IEntityTable entityTable = this.getEntityTable(entityID, param.getContext().getPeriod(), task);
        Map failedOrgs = result.getFailedOrgs();
        Map<String, List<CheckResultNode>> nodeCodeMap = resultNodes.stream().collect(Collectors.groupingBy(CheckResultNode::getUnitKey));
        for (String code : param.getContext().getOrgList()) {
            if (nodeCodeMap.containsKey(code)) {
                List<CheckResultNode> nodes = nodeCodeMap.get(code);
                if (CollectionUtils.isEmpty(nodes)) continue;
                failedOrgs.put(code, this.buildFailedOrgInfo(nodes));
                errNodes.addAll(nodes);
                continue;
            }
            List rows = entityTable.getAllChildRows(code);
            HashSet children = new HashSet();
            rows.forEach(r -> children.add(r.getEntityKeyData()));
            if (children.stream().anyMatch(nodeCodeMap::containsKey)) {
                children.retainAll(nodeCodeMap.keySet());
                Set childrenCodeSet = children.stream().map(nodeCodeMap::get).filter(Objects::nonNull).flatMap(Collection::stream).map(CheckResultNode::getOrgCode).filter(Objects::nonNull).collect(Collectors.toSet());
                String message = String.format("\u8be5\u5355\u4f4d\u4e0b\u7ea7\u5355\u4f4d%s\u51fa\u73b0\u9519\u8bef", childrenCodeSet);
                failedOrgs.put(code, this.buildFailedOrgInfo(message));
                this.addMergedErrorNode(errNodes, entityTable.findByEntityKey(code), message);
                continue;
            }
            result.getSuccessOrgs().add(code);
        }
        result.setResult(failedOrgs.isEmpty() ? CheckRestultState.SUCCESS : CheckRestultState.FAIL);
    }

    private FailedOrgInfo buildFailedOrgInfo(String message) {
        FailedOrgInfo orgInfo = new FailedOrgInfo();
        orgInfo.setDesc(message);
        return orgInfo;
    }

    private void addMergedErrorNode(List<CheckResultNode> resultNodes, IEntityRow byCode, String error) {
        if (byCode != null) {
            CheckResultNode node = new CheckResultNode();
            node.setUnitKey(byCode.getEntityKeyData());
            node.setUnitCode(byCode.getCode());
            node.setUnitTitle(byCode.getTitle());
            node.setErrorMsg(error);
            resultNodes.add(node);
        }
    }

    private FailedOrgInfo buildFailedOrgInfo(List<CheckResultNode> nodes) {
        FailedOrgInfo orgInfo = new FailedOrgInfo();
        orgInfo.setDesc(nodes.stream().map(CheckResultNode::getErrorMsg).filter(Objects::nonNull).collect(Collectors.joining(";")));
        return orgInfo;
    }

    private IEntityTable getEntityTable(String entityId, String period, TaskDefine task) throws Exception {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        entityQuery.setEntityView(entityViewDefine);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        entityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.definitionRuntimeController);
        executorContext.setPeriodView(task.getDateTime());
        return entityQuery.executeReader((IContext)executorContext);
    }

    private void log(CheckItemParam param, List<CheckResultNode> result) {
        String merge = UUIDMerger.merge((String)param.getRunId(), (String)param.getCheckItem().getKey());
        try {
            this.multCheckResDao.insert(merge, SerializeUtil.serializeToJson(result));
        }
        catch (Exception e) {
            logger.error("\u6811\u5f62\u5ba1\u6838\u7ed3\u679c\u5e8f\u5217\u5316\u5931\u8d25");
        }
    }

    private CheckItemResult initRes() {
        CheckItemResult result = new CheckItemResult();
        ArrayList successOrgs = new ArrayList();
        HashMap failedOrgs = new HashMap();
        result.setSuccessOrgs(successOrgs);
        result.setFailedOrgs(failedOrgs);
        return result;
    }

    @Override
    public List<CheckResultNode> queryRunCheckResult(TreeCheckPM treeCheckPM) {
        try {
            MultCheckRes byId = this.multCheckResDao.findById(UUIDMerger.merge((String)treeCheckPM.getContext().getRunId(), (String)treeCheckPM.getContext().getItemKey()));
            if (byId != null) {
                TaskDefine task = this.runTimeViewController.getTask(treeCheckPM.getContext().getTask());
                List<CheckResultNode> nodes = this.deserializeResult(byId.getData());
                IEntityTable entityTable = this.getEntityTable(treeCheckPM.getContext().getOrgEntity(), treeCheckPM.getContext().getPeriod(), task);
                HashSet<String> codes = new HashSet<String>(treeCheckPM.getOrgCode());
                Set collect = entityTable.getAllRows().stream().filter(x -> codes.contains(x.getEntityKeyData()) || treeCheckPM.getOrgCode().stream().anyMatch(code -> x.getParentEntityKey().contains((CharSequence)code))).map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
                return nodes.stream().filter(x -> collect.contains(x.getUnitKey())).collect(Collectors.toList());
            }
        }
        catch (Exception e) {
            logger.error("\u6811\u5f62\u5ba1\u6838\u5931\u8d25", e);
        }
        return Collections.emptyList();
    }

    private List<CheckResultNode> deserializeResult(String data) throws JsonProcessingException {
        if (data == null) {
            return Collections.emptyList();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (List)objectMapper.readValue(data, (TypeReference)new TypeReference<List<CheckResultNode>>(){});
    }

    @Override
    public void exportRunCheckResult(HttpServletResponse response, TreeCheckPM treeCheckPM) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("\u5ba1\u6838\u7ed3\u679c");
        this.createHead(workbook, sheet);
        this.createBody(sheet, treeCheckPM);
        this.exportExcel(workbook, response);
    }

    private void exportExcel(Workbook workbook, HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream();){
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("\u6811\u5f62\u7ed3\u6784\u68c0\u67e5\u7ed3\u679c.xlsx", "UTF-8"));
            workbook.write((OutputStream)outputStream);
            workbook.close();
        }
        catch (IOException e) {
            logger.error("\u5bfc\u51fa\u7ed3\u679c\u5931\u8d25", e);
        }
    }

    private void createBody(Sheet sheet, TreeCheckPM treeCheckPM) {
        MultCheckRes byId = this.multCheckResDao.findById(UUIDMerger.merge((String)treeCheckPM.getContext().getRunId(), (String)treeCheckPM.getContext().getItemKey()));
        if (byId == null) {
            return;
        }
        try {
            List<CheckResultNode> resultNodes = this.deserializeResult(byId.getData());
            if (resultNodes != null) {
                int r = 1;
                for (CheckResultNode resultNode : resultNodes) {
                    Row row = sheet.createRow(r++);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(resultNode.getUnitKey());
                    cell = row.createCell(1);
                    cell.setCellValue(resultNode.getUnitTitle());
                    cell = row.createCell(2);
                    cell.setCellValue(resultNode.getErrorMsg());
                }
            }
        }
        catch (JsonProcessingException e) {
            logger.error("\u53cd\u5e8f\u5217\u5316\u7ed3\u679c\u5931\u8d25", e);
        }
    }

    private void createHead(Workbook workbook, Sheet sheet) {
        Row head = sheet.createRow(0);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u9ed1\u4f53");
        Cell cell = head.createCell(0);
        cell.setCellValue("\u5355\u4f4d\u4ee3\u7801");
        cell = head.createCell(1);
        cell.setCellValue("\u5355\u4f4d\u540d\u79f0");
        cell = head.createCell(2);
        cell.setCellValue("\u5ba1\u6838\u7ed3\u679c");
        int defaultWidth = 6000;
        sheet.setColumnWidth(0, defaultWidth);
        sheet.setColumnWidth(1, defaultWidth);
    }

    @Override
    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        MultCheckItemDTO checkItemDTO = new MultCheckItemDTO();
        checkItemDTO.setType("TREE_STRUCTURE");
        checkItemDTO.setTitle("\u6811\u5f62\u7ed3\u6784\u68c0\u67e5");
        try {
            TreeCheckConfig treeCheckConfig = new TreeCheckConfig();
            treeCheckConfig.setFormSchemeKey(formSchemeKey);
            checkItemDTO.setConfig(SerializeUtil.serializeToJson(treeCheckConfig));
            return checkItemDTO;
        }
        catch (Exception e) {
            logger.error("\u5e8f\u5217\u5316\u5931\u8d25", e);
            return null;
        }
    }
}

