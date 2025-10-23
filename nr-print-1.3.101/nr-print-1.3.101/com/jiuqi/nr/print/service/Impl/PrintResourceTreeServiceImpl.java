/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.task.api.dto.IFormTreeNode
 *  com.jiuqi.nr.task.api.dto.IFormTreeNode$NodeType
 *  com.jiuqi.nr.task.api.tree.TreeConfig
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.print.helper.TreeBuildHelper;
import com.jiuqi.nr.print.service.IPrintResourceTreeService;
import com.jiuqi.nr.print.web.vo.PrintTemTreeNodeVO;
import com.jiuqi.nr.print.web.vo.SearchParamVO;
import com.jiuqi.nr.print.web.vo.SearchResultVO;
import com.jiuqi.nr.task.api.dto.IFormTreeNode;
import com.jiuqi.nr.task.api.tree.TreeConfig;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PrintResourceTreeServiceImpl
implements IPrintResourceTreeService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private DesignBigDataTableDao bigDataTableService;
    private TreeBuildHelper treeBuilder = new TreeBuildHelper();

    @Override
    public List<UITreeNode<IFormTreeNode>> resourceTreeLoad(String formSchemeKey) {
        List<UITreeNode<IFormTreeNode>> nodeList = this.buildPrintResourceTreeNode(formSchemeKey);
        if (!CollectionUtils.isEmpty(nodeList)) {
            TreeConfig treeConfig = new TreeConfig();
            return this.treeBuilder.buildTree(nodeList, treeConfig);
        }
        return Collections.emptyList();
    }

    @Override
    public List<UITreeNode<IFormTreeNode>> formTreeLoad(String formSchemeKey) {
        ArrayList<UITreeNode<IFormTreeNode>> nodeList = new ArrayList<UITreeNode<IFormTreeNode>>();
        this.buildReportTree(nodeList, formSchemeKey);
        if (!CollectionUtils.isEmpty(nodeList)) {
            TreeConfig treeConfig = new TreeConfig();
            List<UITreeNode<IFormTreeNode>> treeNodes = this.treeBuilder.buildTree(nodeList, treeConfig);
            treeNodes.get(0).setExpand(true);
            return treeNodes;
        }
        return Collections.emptyList();
    }

    @Override
    public List<UITreeNode<IFormTreeNode>> treeLocated(String formSchemeKey, String nodeKey, Boolean isExcel) {
        ArrayList<UITreeNode<IFormTreeNode>> nodeList = new ArrayList<UITreeNode<IFormTreeNode>>();
        if (isExcel.booleanValue()) {
            this.buildReportTree(nodeList, formSchemeKey);
        } else {
            nodeList.addAll(this.buildPrintResourceTreeNode(formSchemeKey));
        }
        if (!CollectionUtils.isEmpty(nodeList)) {
            TreeConfig treeConfig = new TreeConfig().selected(new String[]{nodeKey});
            return this.treeBuilder.buildTree(nodeList, treeConfig);
        }
        return Collections.emptyList();
    }

    private List<UITreeNode<IFormTreeNode>> buildPrintResourceTreeNode(String formSchemeKey) {
        ArrayList<UITreeNode<IFormTreeNode>> nodeList = new ArrayList<UITreeNode<IFormTreeNode>>();
        this.buildSpecialRoot(nodeList);
        this.buildReportTree(nodeList, formSchemeKey);
        return nodeList;
    }

    private void buildSpecialRoot(List<UITreeNode<IFormTreeNode>> nodeList) {
        UITreeNode coverDesignNode = new UITreeNode();
        coverDesignNode.setKey("coverTem");
        coverDesignNode.setParent(null);
        coverDesignNode.setTitle("\u5c01\u9762\u8bbe\u8ba1");
        coverDesignNode.setOrder("0");
        coverDesignNode.setIcon("icon-table");
        IFormTreeNode coverDesignNodeData = new IFormTreeNode();
        coverDesignNodeData.setType(IFormTreeNode.NodeType.FORMGROUP);
        coverDesignNode.setData((TreeData)coverDesignNodeData);
        nodeList.add((UITreeNode<IFormTreeNode>)coverDesignNode);
        UITreeNode commonTemDesignNode = new UITreeNode();
        commonTemDesignNode.setKey("commonTem");
        commonTemDesignNode.setParent(null);
        commonTemDesignNode.setTitle("\u6bcd\u7248\u8bbe\u7f6e");
        commonTemDesignNode.setOrder("1");
        commonTemDesignNode.setIcon("icon-table");
        IFormTreeNode commonTemDesignNodeData = new IFormTreeNode();
        commonTemDesignNodeData.setType(IFormTreeNode.NodeType.FORMGROUP);
        commonTemDesignNode.setData((TreeData)commonTemDesignNodeData);
        nodeList.add((UITreeNode<IFormTreeNode>)commonTemDesignNode);
        UITreeNode formRootNode = new UITreeNode();
        formRootNode.setKey("formRootNode");
        formRootNode.setParent(null);
        formRootNode.setTitle("\u8868\u5355\u8bbe\u7f6e");
        formRootNode.setOrder("2");
        formRootNode.setIcon("icon-folder");
        IFormTreeNode formRootNodeData = new IFormTreeNode();
        formRootNodeData.setType(IFormTreeNode.NodeType.FORMGROUP);
        formRootNode.setData((TreeData)formRootNodeData);
        nodeList.add((UITreeNode<IFormTreeNode>)formRootNode);
    }

    private void buildReportTree(List<UITreeNode<IFormTreeNode>> nodeList, String formSchemeKey) {
        List formGroups = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        for (DesignFormGroupDefine formGroup : formGroups) {
            nodeList.add(this.treeBuilder.buildNode((IMetaGroup)formGroup));
            List formDefines = this.designTimeViewController.listFormByGroup(formGroup.getKey());
            for (DesignFormDefine formDefine : formDefines) {
                nodeList.add(this.treeBuilder.buildNode(formDefine, formGroup.getKey()));
            }
        }
    }

    @Override
    public List<SearchResultVO> search(SearchParamVO searchParamVO) {
        SearchResultVO searchResult;
        Object path;
        ArrayList<SearchResultVO> searchResults = new ArrayList<SearchResultVO>();
        String keyWords = searchParamVO.getKeyWords().trim().toLowerCase(Locale.ROOT);
        List formGroups = this.designTimeViewController.listFormGroupByFormScheme(searchParamVO.getFormSchemeId());
        Map<String, DesignFormGroupDefine> keyToFormGroupMap = formGroups.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        List forms = this.designTimeViewController.listFormByFormScheme(searchParamVO.getFormSchemeId());
        List associatedForms = forms.stream().filter(form -> form.getTitle().toLowerCase(Locale.ROOT).contains(keyWords) || form.getFormCode().toLowerCase(Locale.ROOT).contains(keyWords)).collect(Collectors.toList());
        for (DesignFormDefine form2 : associatedForms) {
            List formBelongGroup = this.designTimeViewController.listFormGroupByForm(form2.getKey());
            if (formBelongGroup.size() == 1) {
                path = "\u8868\u5355\u8bbe\u7f6e/" + ((DesignFormGroupDefine)formBelongGroup.get(0)).getTitle() + '/' + form2.getTitle();
                searchResult = new SearchResultVO(form2, (String)path);
                searchResults.add(searchResult);
                continue;
            }
            for (DesignFormGroupDefine formGroup2 : formBelongGroup) {
                String path2 = "\u8868\u5355\u8bbe\u7f6e/" + this.getPath(formGroup2, keyToFormGroupMap);
                SearchResultVO searchResult2 = new SearchResultVO(form2, path2);
                searchResults.add(searchResult2);
            }
        }
        List associatedFormGroups = formGroups.stream().filter(formGroup -> formGroup.getTitle().toLowerCase(Locale.ROOT).contains(keyWords)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(associatedFormGroups)) {
            for (DesignFormGroupDefine formGroup3 : associatedFormGroups) {
                path = "\u8868\u5355\u8bbe\u7f6e/" + this.getPath(formGroup3, keyToFormGroupMap);
                searchResult = new SearchResultVO(formGroup3, (String)path);
                searchResults.add(searchResult);
            }
        }
        return searchResults;
    }

    @Override
    public List<SearchResultVO> listPrintScheme(String formSchemeId) {
        List designPrintTemplateSchemeDefines = this.designTimePrintController.listPrintTemplateSchemeByFormScheme(formSchemeId);
        return designPrintTemplateSchemeDefines.stream().map(scheme -> new SearchResultVO(scheme.getKey(), scheme.getTitle(), "")).collect(Collectors.toList());
    }

    private String getPath(DesignFormGroupDefine define, Map<String, DesignFormGroupDefine> keyToFormGroupMap) {
        String path = define.getTitle();
        if (StringUtils.hasLength(define.getParentKey())) {
            String fatherPath = this.getPath(keyToFormGroupMap.get(define.getParentKey()), keyToFormGroupMap);
            path = fatherPath + "/" + path;
        }
        return path;
    }

    @Override
    public List<UITreeNode<PrintTemTreeNodeVO>> getFullPrintTemplateTree(String formSchemeKey, String printSchemeKey, String locateKey) {
        UITreeNode node;
        ArrayList<UITreeNode<PrintTemTreeNodeVO>> treeNodes = new ArrayList<UITreeNode<PrintTemTreeNodeVO>>();
        HashMap<String, UITreeNode<PrintTemTreeNodeVO>> allNodes = new HashMap<String, UITreeNode<PrintTemTreeNodeVO>>();
        UITreeNode<PrintTemTreeNodeVO> coverNode = PrintTemTreeNodeVO.getCoverNode();
        treeNodes.add(coverNode);
        allNodes.put(coverNode.getKey(), coverNode);
        UITreeNode<PrintTemTreeNodeVO> commonGroupNode = PrintTemTreeNodeVO.getCommonGroupNode();
        treeNodes.add(commonGroupNode);
        allNodes.put(commonGroupNode.getKey(), commonGroupNode);
        List comTemDefines = this.designTimePrintController.listPrintComTemBySchemeWithoutBigData(printSchemeKey);
        if (CollectionUtils.isEmpty((Collection)comTemDefines)) {
            UITreeNode<PrintTemTreeNodeVO> commonNode = PrintTemTreeNodeVO.getCommonDefaultNode(printSchemeKey);
            commonGroupNode.addChildren(commonNode);
            allNodes.put(commonNode.getKey(), commonNode);
        } else {
            boolean hasDefault = false;
            for (DesignPrintComTemDefine comTemDefine : comTemDefines) {
                UITreeNode<PrintTemTreeNodeVO> commonNode = PrintTemTreeNodeVO.getCommonNode((PrintComTemDefine)comTemDefine);
                commonGroupNode.addChildren(commonNode);
                allNodes.put(commonNode.getKey(), commonNode);
                hasDefault |= comTemDefine.isDefault();
            }
            if (!hasDefault) {
                UITreeNode<PrintTemTreeNodeVO> commonNode = PrintTemTreeNodeVO.getCommonDefaultNode(printSchemeKey);
                commonGroupNode.getChildren().add(0, commonNode);
                allNodes.put(commonNode.getKey(), commonNode);
            }
        }
        Map<String, PrintTemplateDefine> templates = this.designTimePrintController.listPrintTemplateByScheme(printSchemeKey).stream().collect(Collectors.toMap(PrintTemplateDefine::getFormKey, a -> a, (a1, a2) -> a1));
        UITreeNode<PrintTemTreeNodeVO> rootGroupNode = PrintTemTreeNodeVO.getTemplateGroupNode();
        treeNodes.add(rootGroupNode);
        allNodes.put(rootGroupNode.getKey(), rootGroupNode);
        List formGroups = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        for (DesignFormGroupDefine formGroup : formGroups) {
            UITreeNode<PrintTemTreeNodeVO> groupNode = PrintTemTreeNodeVO.getTemplateGroupNode((FormGroupDefine)formGroup);
            rootGroupNode.addChildren(groupNode);
            allNodes.put(groupNode.getKey(), groupNode);
            List forms = this.designTimeViewController.listFormByGroup(formGroup.getKey());
            if (CollectionUtils.isEmpty((Collection)forms)) continue;
            List formKeys = forms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            Map updateTime = this.bigDataTableService.queryUpdateTime(formKeys, "FORM_DATA");
            for (DesignFormDefine form : forms) {
                UITreeNode<PrintTemTreeNodeVO> templateNode = PrintTemTreeNodeVO.getTemplateNode((FormGroupDefine)formGroup, (FormDefine)form, (Date)updateTime.get(form.getKey()), templates.get(form.getKey()));
                groupNode.addChildren(templateNode);
                allNodes.put(templateNode.getKey(), templateNode);
            }
        }
        if (StringUtils.hasText(locateKey) && null != (node = (UITreeNode)allNodes.get(locateKey))) {
            node.setSelected(true);
            while (null != (node = (UITreeNode)allNodes.get(node.getParent()))) {
                node.setExpand(true);
            }
        }
        return treeNodes;
    }
}

