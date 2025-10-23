/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.exception.DesignCodeCheckException
 *  com.jiuqi.nr.definition.exception.DesignTitleCheckException
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.util.GridDataTransform
 *  com.jiuqi.nr.task.api.cellbook.CellBookInit
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.form.service.impl;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.exception.DesignCodeCheckException;
import com.jiuqi.nr.definition.exception.DesignTitleCheckException;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.util.GridDataTransform;
import com.jiuqi.nr.task.api.cellbook.CellBookInit;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.dto.AnalysisNode;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.form.exception.FormRunTimeException;
import com.jiuqi.nr.task.form.form.service.IFormDefineService;
import com.jiuqi.nr.task.form.form.service.IFormGroupService;
import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;
import com.jiuqi.nr.task.form.service.IAnalysisService;
import com.jiuqi.nr.task.form.vo.AnalysisInsertResult;
import com.jiuqi.nr.task.form.vo.AnalysisInsertVo;
import com.jiuqi.nr.task.form.vo.ResultVo;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class AnalysisServiceImpl
implements IAnalysisService {
    public static final String FOLDER_NODE_TYPE = "com.jiuqi.nvwa.dataanalyze";
    public static final String FORMAL_FORM = "\u57fa\u7840\u8868";
    public static final String ANALYSIS_FORM = "\u5206\u6790\u8868";
    public static final String SELECTED_ANALYSIS_FORM = "\u5df2\u9009\u5206\u6790\u8868";
    private static final Logger logger = LoggerFactory.getLogger(AnalysisServiceImpl.class);
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private QuickReportModelService quickReportModelService;
    @Autowired
    private IFormDefineService formDefineService;
    @Autowired
    private IFormGroupService formGroupService;

    @Override
    public List<UITreeNode<AnalysisNode>> getRootTree() {
        List<UITreeNode<AnalysisNode>> root = this.getChildrenTree("root", false);
        return root;
    }

    @Override
    public List<UITreeNode<AnalysisNode>> getChildrenTree(String parent, boolean hasRecursion) {
        List<String> types = AnalysisServiceImpl.getTypes();
        ArrayList<UITreeNode<AnalysisNode>> result = new ArrayList<UITreeNode<AnalysisNode>>();
        try {
            List children = this.resourceTreeNodeService.getChildren(parent, types);
            for (ResourceTreeNode resource : children) {
                AnalysisNode quickReportNode = new AnalysisNode(resource);
                UITreeNode uiTreeNode = new UITreeNode();
                uiTreeNode.setKey(quickReportNode.getKey());
                uiTreeNode.setTitle(quickReportNode.getTitle());
                uiTreeNode.setParent(quickReportNode.getParent());
                uiTreeNode.setData((TreeData)quickReportNode);
                uiTreeNode.setIcon(quickReportNode.getIcon());
                if (FOLDER_NODE_TYPE.equals(resource.getType()) && hasRecursion) {
                    List<UITreeNode<AnalysisNode>> childrenTree = this.getChildrenTree(uiTreeNode.getKey(), true);
                    uiTreeNode.setChildren(childrenTree);
                }
                uiTreeNode.setLeaf(resource.getType().equals("com.jiuqi.nvwa.quickreport.business"));
                result.add((UITreeNode<AnalysisNode>)uiTreeNode);
            }
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u5206\u6790\u8868\u8d44\u6e90\u83b7\u53d6\u5f02\u5e38:" + e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public List<UITreeNode<AnalysisNode>> getAllChildrenTree() {
        List<UITreeNode<AnalysisNode>> root = this.getChildrenTree("root", true);
        if (!CollectionUtils.isEmpty(root)) {
            root.get(0).setExpand(true);
        }
        return root;
    }

    @Override
    public List<UITreeNode<AnalysisNode>> locationTree(String key) {
        List<UITreeNode<AnalysisNode>> rootChildren = null;
        try {
            if (!StringUtils.isEmpty(key)) {
                rootChildren = this.getChildrenTree("root", false);
                List path = this.resourceTreeNodeService.getPath(key);
                this.appendPathNode(rootChildren, path, key);
            }
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u5206\u6790\u8868\u8282\u70b9\u8def\u5f84\u83b7\u53d6\u5f02\u5e38:" + e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return rootChildren;
    }

    @Override
    public List<AnalysisNode> search(String keyWords) throws Exception {
        List<String> types = AnalysisServiceImpl.getTypes();
        ArrayList<AnalysisNode> result = new ArrayList<AnalysisNode>();
        try {
            List search = this.resourceTreeNodeService.search(keyWords, types);
            for (ResourceTreeNode resourceTreeNode : search) {
                AnalysisNode quickReportNode = new AnalysisNode(resourceTreeNode);
                result.add(quickReportNode);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    private void appendPathNode(List<UITreeNode<AnalysisNode>> rootChildren, List<ResourceTreeNode> path, String target) {
        if (!CollectionUtils.isEmpty(path)) {
            List<UITreeNode<AnalysisNode>> cur = rootChildren;
            for (ResourceTreeNode resourceTreeNode : path) {
                cur = this.loadNext(cur, resourceTreeNode.getGuid(), target);
            }
        }
    }

    private List<UITreeNode<AnalysisNode>> loadNext(List<UITreeNode<AnalysisNode>> cur, String curPathNode, String target) {
        List<UITreeNode<AnalysisNode>> children = this.getChildrenTree(curPathNode, false);
        if (target.equals(curPathNode)) {
            cur.forEach(o -> {
                if (o.getKey().equals(target)) {
                    o.setSelected(true);
                }
            });
        }
        if (!target.equals(curPathNode)) {
            for (UITreeNode<AnalysisNode> c : cur) {
                if (!c.getKey().equals(curPathNode)) continue;
                c.setExpand(true);
                c.setChildren(children);
                return children;
            }
        }
        return cur;
    }

    private static List<String> getTypes() {
        ArrayList<String> types = new ArrayList<String>();
        types.add("com.jiuqi.nvwa.quickreport.business");
        return types;
    }

    @Override
    @Transactional
    public ResultVo insertForm(AnalysisInsertVo analysisInsertVo) throws Exception {
        FormDTO formDTO = new FormDTO();
        try {
            String analysisKey = analysisInsertVo.getAnalysisKey();
            if (!StringUtils.hasLength(analysisInsertVo.getFormGroup())) {
                throw new FormRunTimeException("\u65b0\u589e\u5206\u6790\u8868\uff1a " + analysisKey + " \u65f6\u5019\u62a5\u8868\u5206\u7ec4\u4e3a\u7a7a");
            }
            QuickReport quickReportByGuidOrId = this.quickReportModelService.getQuickReportByGuidOrId(analysisKey);
            if (quickReportByGuidOrId == null) {
                throw new FormRunTimeException("\u65b0\u589e\u5206\u6790\u8868\uff1a " + analysisKey + " \u65f6\u5019\u5f15\u7528\u7684\u5206\u6790\u8868\u4e0d\u5b58\u5728");
            }
            formDTO.setFormGroupKey(analysisInsertVo.getFormGroup());
            formDTO.setTitle(quickReportByGuidOrId.getTitle());
            formDTO.setFormCode(quickReportByGuidOrId.getName());
            formDTO.setFormType(FormType.FORM_TYPE_INSERTANALYSIS);
            formDTO.setFormScheme(analysisInsertVo.getFormSchemeKey());
            formDTO.addExtensions("analysisGuid", analysisKey);
            formDTO.addExtensions("analysisTitle", quickReportByGuidOrId.getTitle());
            this.formDefineService.insertForm(formDTO);
            this.formGroupService.insertGroupLink(formDTO);
            Grid2Data grid2Data = this.queryAnalysisStyle(quickReportByGuidOrId);
            this.designTimeViewController.insertFormStyle(formDTO.getKey(), grid2Data);
        }
        catch (DesignCodeCheckException e) {
            logger.error(e.getMessage());
        }
        catch (DesignTitleCheckException e) {
            logger.error(e.getMessage() + "\u540d\u79f0\u65e0\u6548\u6216\u8005\u4e0d\u552f\u4e00");
        }
        catch (Exception e) {
            AnalysisInsertResult thisResult = e.getMessage().contains("\u6807\u8bc6\u4e0d\u552f\u4e00") ? new AnalysisInsertResult(formDTO.getKey(), formDTO.getFormCode(), formDTO.getTitle(), FORMAL_FORM, "\u6807\u8bc6\u91cd\u590d", false) : (e.getMessage().contains("\u540d\u79f0\u65e0\u6548\u6216\u8005\u4e0d\u552f\u4e00") ? new AnalysisInsertResult(formDTO.getKey(), formDTO.getFormCode(), formDTO.getTitle(), FORMAL_FORM, "\u540d\u79f0\u65e0\u6548", false) : new AnalysisInsertResult(formDTO.getKey(), formDTO.getFormCode(), formDTO.getTitle(), FORMAL_FORM, "\u65b0\u589e\u5f02\u5e38: " + e.getMessage(), false));
            return new ResultVo<AnalysisInsertResult>(false, thisResult, e.getMessage());
        }
        AnalysisInsertResult thisResult = new AnalysisInsertResult(formDTO.getKey(), formDTO.getFormCode(), formDTO.getTitle(), FORMAL_FORM, "\u65b0\u5efa\u62a5\u8868\u6210\u529f", true);
        return new ResultVo<AnalysisInsertResult>(true, thisResult, "\u65b0\u5efa\u62a5\u8868\u6210\u529f");
    }

    @Override
    public List<ResultVo> batchInsertForm(List<AnalysisInsertVo> analysisInsertVos) throws Exception {
        ArrayList<ResultVo> result = new ArrayList<ResultVo>();
        HashSet<String> analysisKey = new HashSet<String>();
        ArrayList<ResourceTreeNode> selectedAnalysis = new ArrayList<ResourceTreeNode>();
        List allResourceTreeNodes = this.resourceTreeNodeService.getAllResourceTreeNodes(AnalysisServiceImpl.getTypes());
        Map<String, ResourceTreeNode> analysisMaps = allResourceTreeNodes.stream().collect(Collectors.toMap(ResourceTreeNode::getGuid, a -> a, (k1, k2) -> k1));
        for (AnalysisInsertVo analysisInsertVo : analysisInsertVos) {
            ResourceTreeNode resourceTreeNode = analysisMaps.get(analysisInsertVo.getAnalysisKey());
            if (resourceTreeNode == null) continue;
            if (analysisKey.add(analysisInsertVo.getAnalysisKey())) {
                selectedAnalysis.add(resourceTreeNode);
                ResultVo resultVo = this.insertForm(analysisInsertVo);
                if (resultVo.getSuccess()) continue;
                result.add(resultVo);
                continue;
            }
            QuickReport quickReportByGuidOrId = this.quickReportModelService.getQuickReportByGuidOrId(analysisInsertVo.getAnalysisKey());
            AnalysisInsertResult thisResult = new AnalysisInsertResult("", quickReportByGuidOrId.getName(), quickReportByGuidOrId.getTitle(), SELECTED_ANALYSIS_FORM, "\u5df2\u9009\u5206\u6790\u8868\u6807\u8bc6\u91cd\u590d", false);
            result.add(new ResultVo<AnalysisInsertResult>(false, thisResult, "\u5df2\u9009\u5206\u6790\u8868\u6807\u8bc6\u91cd\u590d"));
        }
        if (result.size() > 0) {
            result.add(new ResultVo(true, selectedAnalysis, SELECTED_ANALYSIS_FORM));
        }
        return result;
    }

    @Override
    @Transactional
    public void updateForm(FormDTO formDTO) throws Exception {
        if (!StringUtils.hasText(formDTO.getKey())) {
            throw new FormRunTimeException("\u62a5\u8868\u6ca1\u6709\u4e3b\u952e\uff0c\u4fee\u6539\u5f02\u5e38\uff01");
        }
        DesignFormDefine formDefine = this.designTimeViewController.getForm(formDTO.getKey());
        formDefine.setFormCondition(formDTO.getFormCondition());
        this.designTimeViewController.updateForm(formDefine);
    }

    @Override
    public FormStyleDTO getStyle(String formKey) {
        FormStyleDTO formStyleDTO = new FormStyleDTO();
        formStyleDTO.setKey(formKey);
        Grid2Data grid2Data = this.designTimeViewController.getFormStyle(formKey);
        CellBook cellBook = CellBookInit.init();
        CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)grid2Data, (CellBook)cellBook, (String)formKey);
        formStyleDTO.setCellBook(cellBook);
        return formStyleDTO;
    }

    private Grid2Data queryAnalysisStyle(String analysisKey) throws Exception {
        Grid2Data grid2Data = new Grid2Data();
        QuickReport quickReportByGuidOrId = this.quickReportModelService.getQuickReportByGuidOrId(analysisKey);
        List worksheets = quickReportByGuidOrId.getWorksheets();
        WorksheetModel worksheetModel = (WorksheetModel)worksheets.get(0);
        GridData griddata = worksheetModel.getGriddata();
        GridDataTransform.gridDataToGrid2Data((GridData)griddata, (Grid2Data)grid2Data);
        GridDataTransform.Grid2DataTextFilter((Grid2Data)grid2Data);
        return grid2Data;
    }

    private Grid2Data queryAnalysisStyle(QuickReport quickReportByGuidOrId) throws Exception {
        Grid2Data grid2Data = new Grid2Data();
        List worksheets = quickReportByGuidOrId.getWorksheets();
        WorksheetModel worksheetModel = (WorksheetModel)worksheets.get(0);
        GridData griddata = worksheetModel.getGriddata();
        GridDataTransform.gridDataToGrid2Data((GridData)griddata, (Grid2Data)grid2Data);
        GridDataTransform.Grid2DataTextFilter((Grid2Data)grid2Data);
        return grid2Data;
    }
}

