/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.formtype.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.formtype.common.FormTypeExceptionEnum;
import com.jiuqi.nr.formtype.common.FormTypeParamKind;
import com.jiuqi.nr.formtype.common.RelatedState;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.formtype.service.IFormTypeGroupService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import com.jiuqi.nr.formtype.web.vo.FormTypeDataVO;
import com.jiuqi.nr.formtype.web.vo.FormTypeGroupVO;
import com.jiuqi.nr.formtype.web.vo.FormTypeTreeVO;
import com.jiuqi.nr.formtype.web.vo.FormTypeVO;
import com.jiuqi.nr.formtype.web.vo.UnitNatureVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/formtype/"})
@Api(tags={"\u62a5\u8868\u7c7b\u578b\u67e5\u8be2\u670d\u52a1"})
public class FormTypeQureyController {
    @Autowired
    private IFormTypeService iFormTypeService;
    @Autowired
    private IFormTypeGroupService iFormTypeGroupService;
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;

    @ApiOperation(value="\u62a5\u8868\u7c7b\u578b\u6811\u5f62\u67e5\u8be2\u6839\u76ee\u5f55\u4e0b\u6811\u5f62\u8282\u70b9")
    @GetMapping(value={"query/root/tree"})
    public List<ITree<FormTypeTreeVO>> queryRootFormTypeTree() {
        return this.queryRootFormTypeTree(false);
    }

    private List<ITree<FormTypeTreeVO>> queryRootFormTypeTree(boolean onlyGroup) {
        ITree rootNote = new ITree((INode)FormTypeTreeVO.getRoot());
        rootNote.setLeaf(false);
        rootNote.setExpanded(true);
        rootNote.setChildren(this.queryFormTypeTree("--", onlyGroup));
        return Collections.singletonList(rootNote);
    }

    @ApiOperation(value="\u62a5\u8868\u7c7b\u578b\u6811\u5f62\u67e5\u8be2\u5b50\u76ee\u5f55\u4e0b\u6811\u5f62\u8282\u70b9")
    @GetMapping(value={"query/tree/{groupKey}"})
    public List<ITree<FormTypeTreeVO>> queryFormTypeTree(@PathVariable String groupKey) {
        return this.queryFormTypeTree(groupKey, false);
    }

    private List<ITree<FormTypeTreeVO>> queryFormTypeTree(String groupKey, boolean onlyGroup) {
        List<FormTypeDefine> defines;
        ArrayList<ITree<FormTypeTreeVO>> childrenNotes = new ArrayList<ITree<FormTypeTreeVO>>();
        List<FormTypeGroupDefine> groups = this.iFormTypeGroupService.queryByParentId(groupKey);
        if (!CollectionUtils.isEmpty(groups)) {
            for (FormTypeGroupDefine group : groups) {
                childrenNotes.add(this.createNote(group));
            }
        }
        if (!onlyGroup && !CollectionUtils.isEmpty(defines = this.iFormTypeService.queryByGroup(groupKey))) {
            for (FormTypeDefine define : defines) {
                childrenNotes.add(this.createNote(define));
            }
        }
        return childrenNotes;
    }

    @ApiOperation(value="\u62a5\u8868\u7c7b\u578b\u6811\u5f62\u67e5\u8be2")
    @PostMapping(value={"query/tree/search"})
    public List<FormTypeTreeVO> serachTree(@RequestParam(value="keyword") String keyword) {
        ArrayList<FormTypeTreeVO> notes = new ArrayList<FormTypeTreeVO>();
        List<FormTypeDefine> defines = this.iFormTypeService.search(keyword);
        if (!CollectionUtils.isEmpty(defines)) {
            for (FormTypeDefine define : defines) {
                notes.add(new FormTypeTreeVO(define));
            }
        }
        return notes;
    }

    @ApiOperation(value="\u62a5\u8868\u7c7b\u578b\u6811\u5f62\u5b9a\u4f4d")
    @PostMapping(value={"query/tree/local/{onlyGroup}"})
    public List<ITree<FormTypeTreeVO>> localTree(@RequestBody FormTypeTreeVO vo, @PathVariable boolean onlyGroup) {
        if ("--".equals(vo.getKey())) {
            return this.queryRootFormTypeTree(onlyGroup);
        }
        Stack<String> keys = new Stack<String>();
        String groupKey = vo.getGroupKey();
        if (!StringUtils.hasLength(groupKey)) {
            groupKey = FormTypeParamKind.GROUP == vo.getKind() ? this.iFormTypeGroupService.queryById(vo.getKey()).getGroupId() : this.iFormTypeService.queryFormTypeOnlyById(vo.getKey()).getGroupId();
        }
        while (!"--".equals(groupKey)) {
            keys.push(groupKey);
            groupKey = this.iFormTypeGroupService.queryById(groupKey).getGroupId();
        }
        String key = null;
        ITree treeNote = null;
        List<ITree<FormTypeTreeVO>> rootTreeData = this.queryRootFormTypeTree(onlyGroup);
        List<ITree<FormTypeTreeVO>> treeData = rootTreeData.get(0).getChildren();
        while (!keys.empty()) {
            key = (String)keys.pop();
            for (ITree iTree : treeData) {
                if (!key.equals(iTree.getKey())) continue;
                treeNote = iTree;
                break;
            }
            treeData = this.queryFormTypeTree(key, onlyGroup);
            if (null == treeNote) continue;
            treeNote.setExpanded(true);
            treeNote.setChildren(treeData);
        }
        for (ITree iTree : treeData) {
            if (!vo.getKey().equals(iTree.getKey())) continue;
            iTree.setSelected(true);
        }
        return rootTreeData;
    }

    private ITree<FormTypeTreeVO> createNote(FormTypeTreeVO vo) {
        ITree iTree = new ITree((INode)vo);
        iTree.setLeaf(FormTypeParamKind.GROUP != vo.getKind());
        return iTree;
    }

    private ITree<FormTypeTreeVO> createNote(FormTypeGroupDefine group) {
        return this.createNote(new FormTypeTreeVO(group));
    }

    private ITree<FormTypeTreeVO> createNote(FormTypeDefine type) {
        return this.createNote(new FormTypeTreeVO(type));
    }

    @ApiOperation(value="\u62a5\u8868\u7c7b\u578b\u6811\u5f62\u5b8c\u6574\u6811\u5f62\u67e5\u8be2\u4e0e\u5b9a\u4f4d")
    @GetMapping(value={"query/full/tree/{onlyGroup}", "query/full/tree/{onlyGroup}/{localKey}"})
    public List<ITree<FormTypeTreeVO>> getFormTypeFullTree(@PathVariable boolean onlyGroup, @PathVariable(required=false) String localKey) {
        ITree rootNote = new ITree((INode)FormTypeTreeVO.getRoot());
        rootNote.setLeaf(false);
        rootNote.setExpanded(true);
        List allGorupNotes = this.iFormTypeGroupService.queryAll().stream().map(this::createNote).collect(Collectors.toList());
        ArrayList<ITree<FormTypeTreeVO>> allNotes = new ArrayList<ITree<FormTypeTreeVO>>(allGorupNotes);
        if (!onlyGroup) {
            List allTypeNotes = this.iFormTypeService.queryAllFormType().stream().map(this::createNote).collect(Collectors.toList());
            allNotes.addAll(allTypeNotes);
        }
        Map<String, List<ITree>> allNotesMap = allNotes.stream().collect(Collectors.groupingBy(note -> ((FormTypeTreeVO)note.getData()).getGroupKey()));
        for (ITree groupNote : allGorupNotes) {
            if (!allNotesMap.containsKey(groupNote.getKey())) continue;
            groupNote.setChildren(allNotesMap.get(groupNote.getKey()));
        }
        rootNote.setChildren(allNotesMap.get(rootNote.getKey()));
        if (StringUtils.hasLength(localKey)) {
            this.localTree(allNotes, localKey);
        } else {
            rootNote.setSelected(true);
        }
        return Collections.singletonList(rootNote);
    }

    private void localTree(List<ITree<FormTypeTreeVO>> allNotes, String localKey) {
        ITree localNote = null;
        for (ITree iTree : allNotes) {
            if (!localKey.equals(iTree.getKey())) continue;
            localNote = iTree;
            break;
        }
        if (null != localNote) {
            localNote.setSelected(true);
            while (null != localNote.getParent()) {
                localNote = localNote.getParent();
                localNote.setExpanded(true);
            }
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u5206\u7ec4\u8be6\u7ec6\u4fe1\u606f")
    @GetMapping(value={"query/group/{groupKey}"})
    public FormTypeGroupVO queryGroup(@PathVariable String groupKey) {
        FormTypeGroupDefine group = this.iFormTypeGroupService.queryById(groupKey);
        return null == group ? null : new FormTypeGroupVO(group);
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u7c7b\u578b\u8be6\u7ec6\u4fe1\u606f")
    @GetMapping(value={"query/formtype/{formTypeKey}"})
    public FormTypeVO queryFormType(@PathVariable String formTypeKey) throws JQException {
        FormTypeDefine define = this.iFormTypeService.queryById(formTypeKey);
        return null == define ? null : new FormTypeVO(define);
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u7c7b\u578b\u6570\u636e\u9879\u5217\u8868")
    @GetMapping(value={"query/datas/{formTypeCode}", "query/datas/{formTypeCode}/{unitNature}"})
    public List<FormTypeDataVO> queryDataByFormType(@PathVariable String formTypeCode, @PathVariable(required=false) Integer unitNature) {
        List<FormTypeDataDefine> datas = this.iFormTypeService.queryFormTypeDatas(formTypeCode);
        if (!CollectionUtils.isEmpty(datas)) {
            if (null != unitNature) {
                return datas.stream().filter(d -> unitNature.intValue() == d.getUnitNatrue().getValue()).map(this::toVO).collect(Collectors.toList());
            }
            return datas.stream().map(this::toVO).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private FormTypeDataVO toVO(FormTypeDataDefine data) {
        FormTypeDataVO formTypeDataVO = new FormTypeDataVO(data);
        if (!StringUtils.hasText(formTypeDataVO.getIcon())) {
            formTypeDataVO.setShowIcon(this.iFormTypeApplyService.getSysIconByUnitNature(formTypeDataVO.getUnitNature()));
        } else {
            formTypeDataVO.setShowIcon(formTypeDataVO.getIcon());
        }
        return formTypeDataVO;
    }

    @ApiOperation(value="\u67e5\u8be2\u5355\u4f4d\u6027\u8d28")
    @GetMapping(value={"query/nature"})
    public List<UnitNatureVO> queryUnitNature() {
        ArrayList<UnitNatureVO> vos = new ArrayList<UnitNatureVO>();
        for (UnitNature iUnitNature : UnitNature.values()) {
            vos.add(new UnitNatureVO(iUnitNature, this.iFormTypeApplyService.getSysIconByUnitNature(iUnitNature)));
        }
        return vos;
    }

    @ApiOperation(value="\u68c0\u67e5\u62a5\u8868\u7c7b\u578b\u6807\u8bc6\u662f\u5426\u5b58\u5728")
    @GetMapping(value={"check/formtype/code/{formTypeCode}"})
    public void checkFormTypeCode(@PathVariable String formTypeCode) throws JQException {
        if (this.iFormTypeService.checkCode(formTypeCode)) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.CODE_EXISTS_ERROR);
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u62a5\u8868\u7c7b\u578b\u6570\u636e\u9879\u6807\u8bc6\u662f\u5426\u5b58\u5728")
    @GetMapping(value={"check/data/code/{formTypeCode}/{dataCode}"})
    public void checkDataCode(@PathVariable String formTypeCode, @PathVariable String dataCode) throws JQException {
        if (this.iFormTypeService.checkDataCode(formTypeCode, dataCode)) {
            throw new JQException((ErrorEnum)FormTypeExceptionEnum.CODE_EXISTS_ERROR);
        }
    }

    @ApiOperation(value="\u68c0\u67e5\u62a5\u8868\u7c7b\u578b\u662f\u5426\u88ab\u5f15\u7528\u4e86")
    @GetMapping(value={"check/formtype/related/{formTypeCode}"})
    public RelatedState checkRelated(@PathVariable String formTypeCode) {
        return this.iFormTypeService.checkRelatedState(formTypeCode);
    }
}

