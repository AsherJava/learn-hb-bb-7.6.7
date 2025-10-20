/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient
 *  com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.survey.model.Choice
 *  com.jiuqi.nr.survey.model.common.PeriodType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.basedata.service.BaseDataGroupService
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.I18NSurveyService;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.service.ISurveyDesignerService;
import com.jiuqi.nr.designer.service.QuestionnaireService;
import com.jiuqi.nr.designer.web.facade.EntityObj;
import com.jiuqi.nr.designer.web.facade.SaveEntityVO;
import com.jiuqi.nr.designer.web.rest.param.SurveyZBTreePM;
import com.jiuqi.nr.designer.web.rest.vo.RequestAllFormStyle;
import com.jiuqi.nr.designer.web.rest.vo.RequestSurveyProblemHandleVO;
import com.jiuqi.nr.designer.web.rest.vo.RequestSurveySaveVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyCheckVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyProblemHandleVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveySaveVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveySearchVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyTreeLocateVO;
import com.jiuqi.nr.designer.web.rest.vo.SurveyAllEnumVO;
import com.jiuqi.nr.designer.web.rest.vo.SurveyBaseDataTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.SurveyZBTreeNode;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.survey.model.Choice;
import com.jiuqi.nr.survey.model.common.PeriodType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.basedata.service.BaseDataGroupService;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/designer//survey"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class SurveyController {
    private static final Logger log = LoggerFactory.getLogger(SurveyController.class);
    @Autowired
    private IDesignRestService restService;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private I18NSurveyService i18NSurveyService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignDataSchemeService schemeService;
    @Autowired
    private ISurveyDesignerService surveyDesignerService;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private IEntityViewRunTimeController viewRunTimeController;
    @Autowired
    private BaseDataGroupService baseDataGroupService;
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private OrgAdapterClient orgAdapterClient;

    @ApiOperation(value="\u83b7\u53d6\u95ee\u5377\u7684\u8bbe\u8ba1\u671f\u8868\u6837")
    @PostMapping(value={"/requestAllFormStyle"})
    public Map<String, Object> requestAllFormStyle(@RequestBody RequestAllFormStyle requestAllFormStyle) throws JQException {
        try {
            byte[] surveyData = this.designTimeViewController.getSurveyData(requestAllFormStyle.getKey());
            if (null == surveyData || surveyData.length == 0) {
                DesignFormDefine designFormDefine = this.designTimeViewController.queryFormAndExtAttribute(requestAllFormStyle.getKey(), 0);
                String title = designFormDefine.getTitle();
                String json = "{\"title\":\"" + title + "\",\"logoPosition\":\"right\"}";
                surveyData = DesignFormDefineBigDataUtil.StringToBytes((String)json);
            }
            String bytesToString = DesignFormDefineBigDataUtil.bytesToString((byte[])surveyData);
            HashMap<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("surveyData", bytesToString);
            resMap.put("isOpenFilepool", this.filePoolService.isOpenFilepool());
            return resMap;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_167, (Throwable)e);
        }
    }

    @ApiOperation(value="\u4fdd\u5b58\u95ee\u5377\u7684\u8bbe\u8ba1\u671f\u8868\u6837")
    @PostMapping(value={"/save"})
    public ResponseSurveySaveVO save(@RequestBody RequestSurveySaveVO requestSurveySaveVo) throws JQException {
        try {
            ResponseSurveySaveVO responseSurveySaveVO = new ResponseSurveySaveVO();
            this.surveyDesignerService.saveOrUpdateLinkId(requestSurveySaveVo);
            responseSurveySaveVO.setSuccess(true);
            return responseSurveySaveVO;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_138, (Throwable)e);
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u95ee\u5377\u76f8\u5173\u6307\u6807\uff0c\u94fe\u63a5\u7b49\u662f\u5426\u6709\u95ee\u9898")
    @PostMapping(value={"/check"})
    public List<ResponseSurveyCheckVO> check(@RequestBody RequestSurveySaveVO requestSurveySaveVo) throws JQException {
        try {
            return this.surveyDesignerService.checkSurveyModel(requestSurveySaveVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_138, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6267\u884c\u9875\u9762\u4e0a\u7684\u95ee\u9898\u5904\u7406\u64cd\u4f5c")
    @PostMapping(value={"/problem/handle"})
    public ResponseSurveyProblemHandleVO problemHandle(@RequestBody RequestSurveyProblemHandleVO problemHandleVO) throws JQException {
        try {
            return this.surveyDesignerService.problemHandle(problemHandleVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_138, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636evalueName\u67e5\u8be2\u6307\u5b9a\u679a\u4e3e\u9879")
    @PostMapping(value={"/allEnumByZbInfo"})
    public List<Choice> allEnumByZbInfo(@RequestBody SurveyAllEnumVO surveyAllEnumVO) throws JQException {
        try {
            ArrayList<String> zb = new ArrayList<String>();
            zb.add(surveyAllEnumVO.getTableCode());
            zb.add(surveyAllEnumVO.getZbCode());
            DesignDataField dataField = this.surveyDesignerService.getFieldByValueName(zb);
            if (null == dataField) {
                return new ArrayList<Choice>();
            }
            String entityKey = dataField.getRefDataEntityKey();
            if (!StringUtils.hasLength(entityKey)) {
                return new ArrayList<Choice>();
            }
            IEntityQuery entityQuery = this.iEntityDataService.newEntityQuery();
            entityQuery.setIgnoreViewFilter(true);
            entityQuery.setAuthorityOperations(AuthorityType.Read);
            entityQuery.sorted(true);
            entityQuery.setEntityView(this.viewRunTimeController.buildEntityView(entityKey));
            IEntityTable executeReader = entityQuery.executeReader(null);
            List allRows = executeReader.getAllRows();
            ArrayList<Choice> resList = new ArrayList<Choice>();
            allRows.forEach(ier -> {
                Choice data = new Choice();
                data.setText(ier.getTitle());
                data.setValue(ier.getCode());
                resList.add(data);
            });
            List<Choice> choiceFormulas = surveyAllEnumVO.getChoiceFormulas();
            if (null != choiceFormulas) {
                Map<String, Choice> collectMap = choiceFormulas.stream().collect(Collectors.toMap(Choice::getValue, e -> e));
                for (Choice choice : resList) {
                    if (!collectMap.containsKey(choice.getValue())) continue;
                    Choice forumla = collectMap.get(choice.getValue());
                    choice.setEnableIf(forumla.getEnableIf());
                    choice.setVisibleIf(forumla.getVisibleIf());
                }
            }
            return resList;
        }
        catch (Exception e2) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_138, (Throwable)e2);
        }
    }

    @ApiOperation(value="\u6307\u6807\u6811\u8ddf\u8282\u70b9")
    @PostMapping(value={"/tree/root"})
    public List<ITree<SurveyZBTreeNode>> getZBTreeRoot(@RequestBody SurveyZBTreePM param) {
        DesignDataScheme scheme = this.schemeService.getDataScheme(param.getDataSchemeId());
        SurveyZBTreeNode root = new SurveyZBTreeNode();
        root.setKey(scheme.getKey());
        root.setCode(scheme.getCode());
        root.setTitle(scheme.getTitle());
        root.setNodeType(NodeType.SCHEME);
        ITree node = new ITree((INode)root);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME)});
        node.setExpanded(true);
        node.setLeaf(false);
        ArrayList<ITree<SurveyZBTreeNode>> children = new ArrayList<ITree<SurveyZBTreeNode>>();
        this.buildChilderenNode(children, param.getDataSchemeId(), NodeType.SCHEME, param);
        node.setChildren(children);
        ArrayList<ITree<SurveyZBTreeNode>> nodes = new ArrayList<ITree<SurveyZBTreeNode>>();
        nodes.add(node);
        return nodes;
    }

    @ApiOperation(value="\u6307\u6807\u6811\u4e0b\u7ea7\u8282\u70b9")
    @PostMapping(value={"/tree/children"})
    public List<ITree<SurveyZBTreeNode>> getZBTreeChildren(@RequestBody SurveyZBTreePM param) {
        ArrayList<ITree<SurveyZBTreeNode>> children = new ArrayList<ITree<SurveyZBTreeNode>>();
        this.buildChilderenNode(children, param.getCurrentNodeKey(), NodeType.valueOf((int)param.getNodeType()), param);
        return children;
    }

    @ApiOperation(value="\u5b9a\u4f4d\u6307\u6807\u6216\u5b9a\u4f4d\u77e9\u9635\u95ee\u9898\u5173\u8054\u7684\u8868")
    @PostMapping(value={"/tree/locate"})
    public ResponseSurveyTreeLocateVO locateZb(@RequestBody SurveyZBTreePM param) {
        if ("matrixdynamic".equals(param.getQuestionType())) {
            return this.locateTable(param);
        }
        ResponseSurveyTreeLocateVO res = new ResponseSurveyTreeLocateVO();
        DesignDataField field = null;
        DesignDataTable table = null;
        if (StringUtils.hasText(param.getZbKey())) {
            field = this.schemeService.getDataField(param.getZbKey());
            table = this.schemeService.getDataTable(field.getDataTableKey());
        } else {
            String[] arr = param.getValueName().split("\\.");
            if (arr != null && arr.length == 2) {
                String tableCode = arr[0];
                String zbCode = arr[1];
                table = this.schemeService.getDataTableByCode(tableCode);
                if (table != null) {
                    field = this.schemeService.getDataFieldByTableKeyAndCode(table.getKey(), zbCode);
                }
            }
        }
        if (null != table) {
            List<String> parentKeys = this.getTableAllParentKeys((DataTable)table);
            if (field != null) {
                parentKeys.add(field.getKey());
                res.setZbKey(field.getKey());
                res.setZbCode(field.getCode());
                res.setZbTitle(field.getTitle());
            }
            res.setPaths(parentKeys);
        }
        return res;
    }

    public ResponseSurveyTreeLocateVO locateTable(SurveyZBTreePM param) {
        ResponseSurveyTreeLocateVO res = new ResponseSurveyTreeLocateVO();
        DesignDataTable table = null;
        table = StringUtils.hasText(param.getZbKey()) ? this.schemeService.getDataTable(param.getZbKey()) : this.schemeService.getDataTableByCode(param.getValueName());
        List<String> parentKeys = this.getTableAllParentKeys((DataTable)table);
        res.setPaths(parentKeys);
        res.setZbKey(table.getKey());
        res.setZbCode(table.getCode());
        res.setZbTitle(table.getTitle());
        return res;
    }

    @NotNull
    private List<String> getTableAllParentKeys(DataTable table) {
        ArrayList<String> parentKeys = new ArrayList<String>();
        String parentGroup = table.getDataGroupKey();
        this.getTableGroupParent(parentGroup, parentKeys);
        parentKeys.add(0, table.getDataSchemeKey());
        parentKeys.add(table.getKey());
        return parentKeys;
    }

    @ApiOperation(value="\u6307\u6807\u6a21\u7cca\u641c\u7d22")
    @PostMapping(value={"/tree/search"})
    public List<ResponseSurveySearchVO> searchZb(@RequestBody SurveyZBTreePM param) {
        ArrayList<ResponseSurveySearchVO> res = new ArrayList<ResponseSurveySearchVO>();
        if ("matrixdynamic".equals(param.getQuestionType())) {
            ArrayList<String> dataSchemes = new ArrayList<String>();
            dataSchemes.add(param.getDataSchemeId());
            List designDataTables = this.schemeService.searchTable(dataSchemes, param.getFuzzyKey(), DataTableType.DETAIL.getValue());
            for (DesignDataTable t : designDataTables) {
                res.add(new ResponseSurveySearchVO(t.getKey(), t.getTitle(), t.getCode(), t.getCode(), t.getTitle(), param.getDisabledZbs().contains(t.getCode())));
            }
            return res;
        }
        FieldSearchQuery query = new FieldSearchQuery();
        query.setKeyword(param.getFuzzyKey());
        query.setScheme(param.getDataSchemeId());
        int searchType = DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.FIELD.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
        int zbTypeFlag = param.getZbTypeFlag();
        if (zbTypeFlag != 0) {
            searchType = 0;
            if ((zbTypeFlag & DataTableType.TABLE.getValue()) == DataTableType.TABLE.getValue()) {
                searchType |= DataFieldKind.FIELD_ZB.getValue();
            }
            if ((zbTypeFlag & DataTableType.DETAIL.getValue()) == DataTableType.DETAIL.getValue()) {
                searchType |= DataFieldKind.FIELD.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
            }
        }
        query.setKind(Integer.valueOf(searchType));
        List fields = this.schemeService.searchField(query);
        HashMap<String, DesignDataTable> tableCache = new HashMap<String, DesignDataTable>();
        StringBuilder title = new StringBuilder();
        StringBuilder code = new StringBuilder();
        for (DesignDataField f : fields) {
            if (!this.filtByQuestionType((DataField)f, param)) continue;
            DesignDataTable table = (DesignDataTable)tableCache.get(f.getDataTableKey());
            if (table == null) {
                table = this.schemeService.getDataTable(f.getDataTableKey());
                tableCache.put(f.getDataTableKey(), table);
            }
            code.setLength(0);
            code.append(table.getCode()).append(".").append(f.getCode());
            title.setLength(0);
            title.append(f.getCode()).append("|").append(f.getTitle());
            res.add(new ResponseSurveySearchVO(f.getKey(), title.toString(), code.toString(), f.getCode(), f.getTitle(), param.getDisabledZbs().contains(code.toString())));
        }
        return res;
    }

    @ApiOperation(value="\u52a0\u8f7d\u6307\u6807\u4fe1\u606f")
    @GetMapping(value={"/tree/find-zb/{zbKey}"})
    public DesignDataField getZbByKey(@PathVariable String zbKey) {
        DesignDataField zb = this.schemeService.getDataField(zbKey);
        return zb;
    }

    @ApiOperation(value="\u77e9\u9635\u9898\uff1a\u65b9\u6848\u4e0b\u7684\u660e\u7ec6\u8868")
    @GetMapping(value={"/tree/get-tables/{dataSchemeId}/{tableType}"})
    public List<Map<String, String>> getZbsByTable(@PathVariable String dataSchemeId, @PathVariable int tableType) {
        ArrayList<Map<String, String>> res = new ArrayList<Map<String, String>>();
        List allDataTable = this.schemeService.getAllDataTable(dataSchemeId);
        for (DesignDataTable t : allDataTable) {
            if ((tableType & t.getDataTableType().getValue()) != t.getDataTableType().getValue()) continue;
            HashMap<String, String> labelMap = new HashMap<String, String>();
            labelMap.put("value", t.getCode());
            labelMap.put("label", t.getTitle());
            res.add(labelMap);
        }
        return res;
    }

    @ApiOperation(value="\u77e9\u9635\u9898\uff1a\u52a0\u8f7d\u8868\u4e0b\u7684\u6307\u6807")
    @GetMapping(value={"/tree/float-zb/{tableCode}"})
    public List<DesignDataField> getZbsByTable(@PathVariable String tableCode) {
        List zbs = this.schemeService.getDataFieldByTableCodeAndKind(tableCode, new DataFieldKind[]{DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM});
        return zbs;
    }

    @ApiOperation(value="\u57fa\u7840\u6570\u636e\u5206\u7ec4\u6811")
    @PostMapping(value={"/baseData/group/tree"})
    public List<ITree<SurveyBaseDataTreeNode>> getBaseDataGroupTree() {
        PageVO pageVO = this.baseDataGroupService.tree(new BaseDataGroupDTO());
        ITree<SurveyBaseDataTreeNode> iNode = null;
        for (TreeVO oNode : pageVO.getRows()) {
            iNode = this.getSurveyBaseDataTreeNodeITree((TreeVO<BaseDataGroupDO>)oNode, true);
            if (!oNode.isHasChildren()) continue;
            this.dealBaseDataGroupChildren(oNode.getChildren(), iNode);
        }
        ArrayList<ITree<SurveyBaseDataTreeNode>> nodes = new ArrayList<ITree<SurveyBaseDataTreeNode>>();
        nodes.add(iNode);
        return nodes;
    }

    private void dealBaseDataGroupChildren(List<TreeVO<BaseDataGroupDO>> otherTree, ITree<SurveyBaseDataTreeNode> currentNode) {
        ArrayList<ITree<SurveyBaseDataTreeNode>> childrenList = new ArrayList<ITree<SurveyBaseDataTreeNode>>();
        for (TreeVO<BaseDataGroupDO> oNode : otherTree) {
            ITree<SurveyBaseDataTreeNode> iNode = this.getSurveyBaseDataTreeNodeITree(oNode, false);
            if (oNode.isHasChildren()) {
                this.dealBaseDataGroupChildren(oNode.getChildren(), iNode);
            }
            childrenList.add(iNode);
        }
        currentNode.setChildren(childrenList);
    }

    @ApiOperation(value="\u57fa\u7840\u6570\u636e\u6811")
    @PostMapping(value={"/baseData/tree"})
    public List<ITree<SurveyBaseDataTreeNode>> getBaseDataTree() {
        PageVO pageVO = this.baseDataGroupService.tree(new BaseDataGroupDTO());
        List otherTree = pageVO.getRows();
        ITree<SurveyBaseDataTreeNode> iNode = null;
        for (TreeVO oNode : otherTree) {
            iNode = this.getSurveyBaseDataTreeNodeITree((TreeVO<BaseDataGroupDO>)oNode, true);
            if (!oNode.isHasChildren()) continue;
            this.dealBaseDataChildren(oNode.getChildren(), iNode);
        }
        ArrayList<ITree<SurveyBaseDataTreeNode>> nodes = new ArrayList<ITree<SurveyBaseDataTreeNode>>();
        nodes.add(iNode);
        return nodes;
    }

    private void dealBaseDataChildren(List<TreeVO<BaseDataGroupDO>> otherTree, ITree<SurveyBaseDataTreeNode> currentNode) {
        ArrayList<ITree<SurveyBaseDataTreeNode>> childrenList = new ArrayList<ITree<SurveyBaseDataTreeNode>>();
        for (TreeVO<BaseDataGroupDO> oNode : otherTree) {
            ITree<SurveyBaseDataTreeNode> iNode = this.getSurveyBaseDataTreeNodeITree(oNode, false);
            if (oNode.isHasChildren()) {
                this.dealBaseDataChildren(oNode.getChildren(), iNode);
            }
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setGroupname(oNode.getId());
            PageVO list = this.baseDataDefineClient.list(baseDataDefineDTO);
            if (!CollectionUtils.isEmpty(list.getRows())) {
                ArrayList<ITree> defineList = new ArrayList<ITree>();
                for (BaseDataDefineDO defineDO : list.getRows()) {
                    SurveyBaseDataTreeNode dNode = new SurveyBaseDataTreeNode();
                    dNode.setKey(defineDO.getId().toString());
                    dNode.setCode(defineDO.getName());
                    dNode.setTitle(defineDO.getTitle());
                    ITree define = new ITree((INode)dNode);
                    define.setExpanded(false);
                    define.setLeaf(true);
                    defineList.add(define);
                }
                if (CollectionUtils.isEmpty(iNode.getChildren())) {
                    iNode.setChildren(defineList);
                } else {
                    iNode.getChildren().addAll(defineList);
                }
            }
            childrenList.add(iNode);
        }
        currentNode.setChildren(childrenList);
    }

    @NotNull
    private ITree<SurveyBaseDataTreeNode> getSurveyBaseDataTreeNodeITree(TreeVO<BaseDataGroupDO> oNode, boolean expanded) {
        SurveyBaseDataTreeNode node = new SurveyBaseDataTreeNode();
        node.setKey(oNode.getId());
        node.setCode(oNode.getId());
        node.setTitle(oNode.getText());
        ITree iNode = new ITree((INode)node);
        iNode.setExpanded(expanded);
        iNode.setLeaf(!oNode.isHasChildren());
        return iNode;
    }

    private void getTableGroupParent(String tableGroup, List<String> parentKeys) {
        if (!StringUtils.hasText(tableGroup)) {
            return;
        }
        DesignDataGroup dataGroup = this.schemeService.getDataGroup(tableGroup);
        if (dataGroup != null) {
            this.getTableGroupParent(dataGroup.getParentKey(), parentKeys);
        }
        parentKeys.add(tableGroup);
    }

    private void buildChilderenNode(List<ITree<SurveyZBTreeNode>> children, String parentKey, NodeType nodeType, SurveyZBTreePM param) {
        switch (nodeType) {
            case SCHEME: {
                this.buildTableGroup(this.schemeService.getDataGroupByScheme(parentKey), children);
                this.buildTable(this.schemeService.getDataTableByScheme(parentKey), children, param.getZbTypeFlag(), param.getQuestionType());
                break;
            }
            case GROUP: {
                this.buildTableGroup(this.schemeService.getDataGroupByParent(parentKey), children);
                this.buildTable(this.schemeService.getDataTableByGroup(parentKey), children, param.getZbTypeFlag(), param.getQuestionType());
                break;
            }
            case ACCOUNT_TABLE: 
            case TABLE: 
            case DETAIL_TABLE: 
            case MUL_DIM_TABLE: {
                this.buildZB(parentKey, param, children);
            }
        }
    }

    private void buildTableGroup(List<DesignDataGroup> tableGroups, List<ITree<SurveyZBTreeNode>> children) {
        if (!CollectionUtils.isEmpty(tableGroups)) {
            children.addAll(tableGroups.stream().map(g -> this.convertBasicToTreeNode((Basic)g, NodeType.GROUP)).collect(Collectors.toList()));
        }
    }

    private void buildTable(List<DesignDataTable> tables, List<ITree<SurveyZBTreeNode>> children, int zbTypeFlag, String questionType) {
        if (!CollectionUtils.isEmpty(tables)) {
            for (DesignDataTable t : tables) {
                NodeType tType;
                if (zbTypeFlag != 0 && (zbTypeFlag & t.getDataTableType().getValue()) != t.getDataTableType().getValue()) continue;
                switch (t.getDataTableType()) {
                    case TABLE: {
                        tType = NodeType.TABLE;
                        break;
                    }
                    case DETAIL: {
                        tType = NodeType.DETAIL_TABLE;
                        break;
                    }
                    case MULTI_DIM: {
                        tType = NodeType.MUL_DIM_TABLE;
                        break;
                    }
                    case ACCOUNT: {
                        tType = NodeType.ACCOUNT_TABLE;
                        break;
                    }
                    default: {
                        tType = NodeType.TABLE;
                    }
                }
                ITree<SurveyZBTreeNode> node = this.convertBasicToTreeNode((Basic)t, tType);
                if ("matrixdynamic".equals(questionType)) {
                    node.setLeaf(true);
                    ((SurveyZBTreeNode)node.getData()).setZbCode(node.getCode());
                }
                children.add(node);
            }
        }
    }

    private void buildZB(String parentKey, SurveyZBTreePM param, List<ITree<SurveyZBTreeNode>> children) {
        List zbs = this.schemeService.getDataFieldByTableKeyAndKind(parentKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM});
        if (!CollectionUtils.isEmpty(zbs)) {
            zbs = zbs.stream().filter(zb -> this.filtByQuestionType((DataField)zb, param)).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(zbs)) {
            return;
        }
        DesignDataTable table = this.schemeService.getDataTable(((DesignDataField)zbs.get(0)).getDataTableKey());
        children.addAll(zbs.stream().map(arg_0 -> this.lambda$buildZB$4((DataTable)table, param, arg_0)).collect(Collectors.toList()));
    }

    private boolean filtByQuestionType(DataField zb, SurveyZBTreePM param) {
        String[] arr;
        String questionType = param.getQuestionType();
        if (!StringUtils.hasText(questionType)) {
            return true;
        }
        if (StringUtils.hasText(param.getValueName()) && (arr = param.getValueName().split("\\.")) != null && arr.length == 2 && zb.getCode().equals(arr[1])) {
            return true;
        }
        switch (questionType.toLowerCase()) {
            case "blank": {
                log.error("::\u95ee\u5377\u540e\u7aeffiltByQuestionType::\u4e0d\u652f\u6301\u586b\u7a7a\u9898\u8fc7\u6ee4\u6307\u6807");
                break;
            }
            case "number": {
                if (param.getQuestionParam() != null) {
                    if (param.getQuestionParam().getNumberDecimalPoint() == 0) {
                        return zb.getDataFieldType() == DataFieldType.INTEGER || zb.getDataFieldType() == DataFieldType.BIGDECIMAL && zb.getDecimal() == 0;
                    }
                    return zb.getDataFieldType() == DataFieldType.BIGDECIMAL;
                }
                return zb.getDataFieldType() == DataFieldType.INTEGER || zb.getDataFieldType() == DataFieldType.BIGDECIMAL;
            }
            case "period": {
                if (param.getQuestionParam() != null && param.getQuestionParam().getPeriodDatetype() != null) {
                    if (PeriodType.DATETIME.name().toLowerCase().equals(param.getQuestionParam().getPeriodDatetype())) {
                        return zb.getDataFieldType() == DataFieldType.DATE_TIME;
                    }
                    return zb.getDataFieldType() == DataFieldType.DATE;
                }
                return zb.getDataFieldType() == DataFieldType.DATE || zb.getDataFieldType() == DataFieldType.DATE_TIME;
            }
            case "text": {
                return zb.getDataFieldType() == DataFieldType.STRING && !StringUtils.hasText(zb.getRefDataEntityKey());
            }
            case "boolean": {
                return zb.getDataFieldType() == DataFieldType.BOOLEAN;
            }
            case "radiogroup": 
            case "dropdown": {
                if (zb.getDataFieldType() == DataFieldType.STRING && StringUtils.hasText(zb.getRefDataEntityKey())) {
                    return zb.getAllowMultipleSelect() == false && this.canDispaly(zb.getRefDataEntityKey());
                }
                return false;
            }
            case "checkbox": 
            case "tagbox": {
                if (zb.getDataFieldType() == DataFieldType.STRING && StringUtils.hasText(zb.getRefDataEntityKey())) {
                    return zb.getAllowMultipleSelect() != false && this.canDispaly(zb.getRefDataEntityKey());
                }
                return false;
            }
            case "comment": {
                return zb.getDataFieldType() == DataFieldType.CLOB;
            }
            case "file": 
            case "filepool": {
                return zb.getDataFieldType() == DataFieldType.FILE;
            }
            case "picture": {
                return zb.getDataFieldType() == DataFieldType.PICTURE;
            }
        }
        return false;
    }

    private boolean canDispaly(String entityKey) {
        if (entityKey.endsWith("@BASE")) {
            String tableName = entityKey.replace("@BASE", "");
            BaseDataDefineDTO paramDefine = new BaseDataDefineDTO();
            paramDefine.setName(tableName);
            BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(paramDefine);
            if (baseDataDefineDO != null) {
                return baseDataDefineDO.getStructtype() < 4;
            }
        }
        return false;
    }

    private ITree<SurveyZBTreeNode> convertFiledToTreeNode(DesignDataField zb, DataTable table, List<List<String>> disabledZbs) {
        SurveyZBTreeNode r = new SurveyZBTreeNode();
        r.setKey(zb.getKey());
        r.setTitle(zb.getCode() + " | " + zb.getTitle());
        r.setZbCode(zb.getCode());
        r.setNodeType(this.convertDataFieldKindToNodeType(zb.getDataFieldKind()));
        r.setZb(zb);
        StringBuilder code = new StringBuilder();
        code.append(table.getCode()).append(".").append(zb.getCode());
        r.setCode(code.toString());
        ITree node = new ITree((INode)r);
        for (List<String> zbInfo : disabledZbs) {
            String valueName = zbInfo.get(0) + "." + zbInfo.get(1);
            if (!valueName.equals(code.toString())) continue;
            node.setDisabled(true);
        }
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.FIELD)});
        node.setLeaf(true);
        return node;
    }

    private NodeType convertDataFieldKindToNodeType(DataFieldKind kind) {
        switch (kind) {
            case FIELD_ZB: {
                return NodeType.FIELD_ZB;
            }
            case FIELD: {
                return NodeType.FIELD;
            }
            case TABLE_FIELD_DIM: {
                return NodeType.TABLE_DIM;
            }
        }
        return NodeType.FIELD_ZB;
    }

    private ITree<SurveyZBTreeNode> convertBasicToTreeNode(Basic b, NodeType type) {
        SurveyZBTreeNode r = new SurveyZBTreeNode();
        r.setKey(b.getKey());
        r.setTitle(b.getTitle());
        r.setNodeType(type);
        r.setCode(b.getCode());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)type)});
        node.setLeaf(false);
        return node;
    }

    @ApiOperation(value="\u521d\u59cb\u5316\u7a7a\u679a\u4e3e\u5b57\u5178")
    @RequestMapping(value={"/init-enum"}, method={RequestMethod.GET})
    public EntityObj initEnum() throws JQException {
        EntityObj initEnum = this.restService.initEnum();
        return initEnum;
    }

    @ApiOperation(value="\u4fdd\u5b58\u679a\u4e3e\u5b57\u5178")
    @RequestMapping(value={"/save-enum"}, method={RequestMethod.POST})
    public void saveEnum(@RequestBody SaveEntityVO entityVO) throws JQException {
        try {
            this.restService.saveEnum(entityVO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_000, (Throwable)e);
        }
    }

    @ApiOperation(value="\u901a\u8fc7\u89c6\u56fe\u5220\u9664\u679a\u4e3e\uff08\u95ee\u5377\uff09")
    @RequestMapping(value={"/delete-enum/{entityId}"}, method={RequestMethod.GET})
    public void deleteEnum(@PathVariable(value="entityId") String entityId) throws JQException {
        try {
            this.restService.deleteEnum(entityId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_000, (Throwable)e);
        }
    }

    @ApiOperation(value="\u901a\u8fc7\u89c6\u56fe\u83b7\u53d6\u679a\u4e3e\u4fe1\u606f\uff08\u95ee\u5377\uff09")
    @RequestMapping(value={"/get-enum/{entityId}"}, method={RequestMethod.GET})
    public EntityObj getEnum(@PathVariable(value="entityId") String entityId) throws JQException {
        try {
            return this.restService.getEnum(entityId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_000, (Throwable)e);
        }
    }

    @ApiOperation(value="\u4e0a\u4f20\u9644\u4ef6\u6307\u6807\u9ed8\u8ba4\u6a21\u677f")
    @PostMapping(value={"questionUploadFile"})
    public ArrayList<String> upLoadFile(@RequestParam(value="file") MultipartFile[] file, String partition) throws JQException {
        String filePath = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < file.length; ++i) {
            try {
                byte[] bytes = file[i].getBytes();
                String fileName = file[i].getOriginalFilename();
                filePath = this.questionnaireService.uploadFile(fileName, bytes, partition);
                arrayList.add(filePath);
                continue;
            }
            catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return arrayList;
    }

    @ApiOperation(value="\u4e0b\u8f7d\u9644\u4ef6")
    @GetMapping(value={"preview/{parent}/{fileKey}"})
    public void preview(@PathVariable(value="parent") String parent, @PathVariable(value="fileKey") String fileKey, HttpServletResponse rsp) {
        this.questionnaireService.downFild(rsp, fileKey, parent);
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848")
    @RequestMapping(value={"/i18n/survey"}, method={RequestMethod.POST})
    public HashMap<String, String> queryAllI18nSurveyItem() {
        return this.i18NSurveyService.getAllI18nSurveyItem();
    }

    @ApiOperation(value="\u901a\u8fc7\u94fe\u63a5,\u6307\u6807\u67e5\u8be2\u5173\u8054\u7684\u6307\u6807\u4fe1\u606f")
    @RequestMapping(value={"/getDataField/{linkId}/{tableCode}/{zbCode}"}, method={RequestMethod.GET})
    public DataField getDataField(@PathVariable(value="linkId") String linkId, @PathVariable(value="tableCode") String tableCode, @PathVariable(value="zbCode") String zbCode) {
        ArrayList<String> zb = new ArrayList<String>();
        zb.add(tableCode);
        zb.add(zbCode);
        DesignDataField dataField = this.surveyDesignerService.getFieldByValueName(zb);
        if (null != dataField) {
            return dataField;
        }
        DesignDataLinkDefine dataLinkDefine = this.designTimeViewController.queryDataLinkDefine(linkId);
        if (dataLinkDefine != null && (dataField = this.schemeService.getDataField(dataLinkDefine.getLinkExpression())) != null) {
            return dataField;
        }
        return null;
    }

    @ApiOperation(value="\u901a\u8fc7\u8868code\u3001\u6307\u6807code\u67e5\u8be2\u5173\u8054\u679a\u4e3e")
    @RequestMapping(value={"/getBaseDataTitle/{tableCode}/{zbCode}"}, method={RequestMethod.GET})
    public String getBaseDataTitle(@PathVariable String tableCode, @PathVariable String zbCode) {
        TableModelDefine tableModel;
        ArrayList<String> zb = new ArrayList<String>();
        zb.add(tableCode);
        zb.add(zbCode);
        DesignDataField dataField = this.surveyDesignerService.getFieldByValueName(zb);
        if (dataField != null && StringUtils.hasLength(dataField.getRefDataEntityKey()) && (BaseDataAdapterUtil.isBaseData((String)dataField.getRefDataEntityKey()) || OrgAdapterUtil.isOrg((String)dataField.getRefDataEntityKey())) && (tableModel = this.entityMetaService.getTableModel(dataField.getRefDataEntityKey())) != null) {
            return tableModel.getCode() + "|" + tableModel.getTitle();
        }
        return null;
    }

    private /* synthetic */ ITree lambda$buildZB$4(DataTable table, SurveyZBTreePM param, DesignDataField zb) {
        return this.convertFiledToTreeNode(zb, table, param.getDisabledZbs());
    }
}

