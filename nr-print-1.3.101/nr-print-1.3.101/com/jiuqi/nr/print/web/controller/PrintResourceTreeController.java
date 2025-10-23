/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.task.api.dto.IFormTreeNode
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.commons.lang3.BooleanUtils
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.print.web.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.print.service.IPrintResourceTreeService;
import com.jiuqi.nr.print.web.vo.PrintTemTreeNodeVO;
import com.jiuqi.nr.print.web.vo.SearchParamVO;
import com.jiuqi.nr.print.web.vo.SearchResultVO;
import com.jiuqi.nr.print.web.vo.TreeLocationVO;
import com.jiuqi.nr.task.api.dto.IFormTreeNode;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@Api(tags={"\u6253\u5370\u8bbe\u8ba1\u5de6\u4fa7\u8d44\u6e90\u6811"})
@RequestMapping(value={"api/v2/print/"})
public class PrintResourceTreeController {
    @Autowired
    private IPrintResourceTreeService treeService;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private DesignBigDataTableDao bigDataTableService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @ApiOperation(value="\u52a0\u8f7d\u5de6\u4fa7\u8d44\u6e90\u6811")
    @RequestMapping(value={"form-tree-Load/{formSchemeKey}"}, method={RequestMethod.GET})
    public List<UITreeNode<IFormTreeNode>> formTreeLoad(@PathVariable String formSchemeKey) {
        return this.treeService.formTreeLoad(formSchemeKey);
    }

    @ApiOperation(value="\u5b9a\u4f4d\u6811")
    @RequestMapping(value={"tree-locate"}, method={RequestMethod.POST})
    public List<UITreeNode<IFormTreeNode>> treeLocate(@RequestBody TreeLocationVO locationVO) {
        return this.treeService.treeLocated(locationVO.getFormSchemeId(), locationVO.getNodeId(), BooleanUtils.isTrue((Boolean)locationVO.isExcel()));
    }

    @ApiOperation(value="\u8d44\u6e90\u641c\u7d22")
    @RequestMapping(value={"search"}, method={RequestMethod.POST})
    public List<SearchResultVO> search(@RequestBody SearchParamVO searchVO) {
        return this.treeService.search(searchVO);
    }

    @ApiOperation(value="\u8bbe\u8ba1\u754c\u9762\u5185\u67e5\u8be2\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"query/printScheme/{formSchemeId}"}, method={RequestMethod.GET})
    public List<SearchResultVO> listPrintScheme(@PathVariable String formSchemeId) {
        return this.treeService.listPrintScheme(formSchemeId);
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u5168\u91cf\u6811\u5f62")
    @RequestMapping(value={"tree/{formSchemeKey}/{printSchemeKey}", "tree/{formSchemeKey}/{printSchemeKey}/{locateKey}"}, method={RequestMethod.GET})
    public List<UITreeNode<PrintTemTreeNodeVO>> getFullPrintTemplateTree(@PathVariable String formSchemeKey, @PathVariable String printSchemeKey, @PathVariable(required=false) String locateKey) {
        return this.treeService.getFullPrintTemplateTree(formSchemeKey, printSchemeKey, locateKey);
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u6811\u5f62\u8282\u70b9\u6570\u636e")
    @RequestMapping(value={"tree/node/{printSchemeKey}"}, method={RequestMethod.POST})
    public UITreeNode<PrintTemTreeNodeVO> getPrintTemplateTreeNode(@PathVariable String printSchemeKey, @RequestBody PrintTemTreeNodeVO vo) {
        switch (vo.getType()) {
            case COVER: {
                return PrintTemTreeNodeVO.getCoverNode();
            }
            case COMMON: {
                DesignPrintComTemDefine define = this.designTimePrintController.getPrintComTem(vo.getKey());
                if (null == define) {
                    return null;
                }
                return PrintTemTreeNodeVO.getCommonNode((PrintComTemDefine)define);
            }
            case TEMPLATE: {
                DesignFormDefine form = this.designTimeViewController.getForm(vo.getFormKey());
                if (null == form) {
                    return null;
                }
                DesignFormGroupDefine group = this.designTimeViewController.listFormGroupByForm(vo.getFormKey()).stream().findFirst().orElse(null);
                if (null == group) {
                    return null;
                }
                DesignBigDataTable grid = this.bigDataTableService.queryigDataDefine(vo.getKey(), "FORM_DATA");
                if (null == grid) {
                    return null;
                }
                DesignPrintTemplateDefine template = this.designTimePrintController.getPrintTemplateBySchemeAndForm(printSchemeKey, vo.getFormKey());
                return PrintTemTreeNodeVO.getTemplateNode((FormGroupDefine)group, (FormDefine)form, grid.getUpdateTime(), (PrintTemplateDefine)template);
            }
        }
        return null;
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u6811\u5f62\u8282\u70b9\u6570\u636e")
    @RequestMapping(value={"tree/linked/node/{printSchemeKey}/{commonCode}"}, method={RequestMethod.GET})
    public List<UITreeNode<PrintTemTreeNodeVO>> getPrintTemplateTreeLinkedNode(@PathVariable String printSchemeKey, @PathVariable String commonCode) {
        DesignPrintTemplateSchemeDefine scheme = this.designTimePrintController.getPrintTemplateScheme(printSchemeKey);
        if (null == scheme) {
            return Collections.emptyList();
        }
        List all = this.designTimePrintController.listPrintTemplateByScheme(printSchemeKey);
        ArrayList<String> formKeys = new ArrayList<String>();
        ArrayList<DesignPrintTemplateDefine> templates = new ArrayList<DesignPrintTemplateDefine>();
        for (DesignPrintTemplateDefine template : all) {
            if (!commonCode.equals(template.getComTemCode())) continue;
            formKeys.add(template.getFormKey());
            templates.add(template);
        }
        Map updateTime = this.bigDataTableService.queryUpdateTime(formKeys, "FORM_DATA");
        Map forms = this.designTimeViewController.listForm(formKeys).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, Function.identity()));
        HashMap<String, DesignFormGroupDefine> groups = new HashMap<String, DesignFormGroupDefine>();
        ArrayList<UITreeNode<PrintTemTreeNodeVO>> nodes = new ArrayList<UITreeNode<PrintTemTreeNodeVO>>();
        for (DesignPrintTemplateDefine template : templates) {
            DesignFormDefine form = (DesignFormDefine)forms.get(template.getFormKey());
            if (null == form) continue;
            DesignFormGroupDefine group = (DesignFormGroupDefine)groups.get(template.getFormKey());
            if (null == group) {
                group = this.designTimeViewController.listFormGroupByForm(template.getFormKey()).stream().findFirst().orElse(null);
                groups.put(template.getFormKey(), group);
            }
            if (null == group) continue;
            nodes.add(PrintTemTreeNodeVO.getTemplateNode((FormGroupDefine)group, (FormDefine)form, (Date)updateTime.get(template.getFormKey()), (PrintTemplateDefine)template));
        }
        return nodes;
    }
}

