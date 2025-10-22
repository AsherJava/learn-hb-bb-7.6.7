/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.workbench.share.bean.dto.ShareDataBaseDTO
 *  com.jiuqi.nvwa.workbench.share.service.IShareService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dafafill.owner.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dafafill.owner.entity.DataFillDefinitionPrivate;
import com.jiuqi.nr.dafafill.owner.entity.DataFillGroupPrivate;
import com.jiuqi.nr.dafafill.owner.service.IDataFillPrivateService;
import com.jiuqi.nr.dafafill.owner.web.vo.DefinitionVO;
import com.jiuqi.nr.dafafill.owner.web.vo.GroupVO;
import com.jiuqi.nr.dafafill.owner.web.vo.ModelTreeNode;
import com.jiuqi.nr.dafafill.web.vo.ReturnInfoVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.workbench.share.bean.dto.ShareDataBaseDTO;
import com.jiuqi.nvwa.workbench.share.service.IShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datafill/private"})
@Api(value="\u6211\u7684\u6a21\u677f")
public class DataFillPrivateController {
    private static final String ICON = "#icon16_DH_A_NW_gongnengfenzushouqi";
    @Autowired
    private IDataFillPrivateService dataFillPrivateService;
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private IShareService shareService;

    @GetMapping(value={"/getDefinition/{key}"})
    @ApiOperation(value="\u83b7\u53d6\u6211\u7684\u6a21\u677f\u6811\u5f62")
    public DataFillDefinitionPrivate getDefinition(@PathVariable String key) {
        return this.dataFillPrivateService.getDefinitionByKey(key);
    }

    @GetMapping(value={"/getTree"})
    @ApiOperation(value="\u83b7\u53d6\u6211\u7684\u6a21\u677f\u6811\u5f62")
    public List<ITree<ModelTreeNode>> getTree() throws JQException {
        ArrayList<ITree<ModelTreeNode>> res = new ArrayList<ITree<ModelTreeNode>>();
        this.buildRootNode(res);
        this.buildShareNode(res);
        this.buildChildren((ITree<ModelTreeNode>)((ITree)res.get(0)), NpContextHolder.getContext().getUserName());
        return res;
    }

    @GetMapping(value={"/getGroupTree"})
    @ApiOperation(value="\u83b7\u53d6\u6211\u7684\u6a21\u677f\u6811\u5f62")
    public List<ITree<ModelTreeNode>> getGroupTree() {
        ArrayList<ITree<ModelTreeNode>> res = new ArrayList<ITree<ModelTreeNode>>();
        this.buildRootNode(res);
        this.buildChildrenGroup((ITree<ModelTreeNode>)((ITree)res.get(0)), NpContextHolder.getContext().getUserName());
        return res;
    }

    @PostMapping(value={"/addGroup"})
    @ApiOperation(value="\u65b0\u589e\u5206\u7ec4")
    public ReturnInfoVO addGroup(@Valid @RequestBody GroupVO groupVO) {
        ReturnInfoVO returnInfoVO = new ReturnInfoVO();
        if (this.checkGroup(groupVO, returnInfoVO)) {
            return returnInfoVO;
        }
        DataFillGroupPrivate group = new DataFillGroupPrivate();
        String key = UUID.randomUUID().toString();
        group.setCreateUser(NpContextHolder.getContext().getUserName());
        group.setKey(key);
        group.setParentId(groupVO.getKey());
        group.setTitle(groupVO.getTitle());
        this.dataFillPrivateService.addGroup(group);
        ModelTreeNode node = new ModelTreeNode(key, groupVO.getTitle(), "GROUP");
        returnInfoVO.setData(new ITree((INode)node));
        returnInfoVO.setMsg("\u4fdd\u5b58\u6210\u529f");
        returnInfoVO.setSuccess(true);
        return returnInfoVO;
    }

    @PostMapping(value={"/deleteGroup/{key}"})
    @ApiOperation(value="\u5220\u9664\u5206\u7ec4")
    public void deleteGroup(@PathVariable String key) {
        this.dataFillPrivateService.deleteGroupByKey(key);
    }

    @PostMapping(value={"/editGroup"})
    @ApiOperation(value="\u4fee\u6539\u5206\u7ec4")
    public ReturnInfoVO editGroup(@Valid @RequestBody GroupVO groupVO) {
        ReturnInfoVO returnInfoVO = new ReturnInfoVO();
        if (this.checkGroup(groupVO, returnInfoVO)) {
            return returnInfoVO;
        }
        this.dataFillPrivateService.modifyGroup(groupVO.getKey(), groupVO.getTitle());
        returnInfoVO.setSuccess(true);
        returnInfoVO.setMsg("\u4fdd\u5b58\u6210\u529f");
        return returnInfoVO;
    }

    @PostMapping(value={"/addDefinition/{language}"})
    @ApiOperation(value="\u65b0\u589e\u6a21\u677f")
    public ReturnInfoVO addDefinition(@Valid @RequestBody DefinitionVO definitionVO, @PathVariable String language) throws JQException, JsonProcessingException {
        language = HtmlUtils.cleanUrlXSS((String)language);
        ReturnInfoVO returnInfoVO = new ReturnInfoVO();
        if (this.checkDefinition(definitionVO, returnInfoVO)) {
            return returnInfoVO;
        }
        DataFillDefinitionPrivate definition = new DataFillDefinitionPrivate();
        definition.setGroupId(definitionVO.getGroup());
        definition.setTitle(definitionVO.getTitle());
        definition.setSourceType(definitionVO.getSourceType());
        definition.setTask(definitionVO.getTask());
        definition.setCreateUser(NpContextHolder.getContext().getUserName());
        String key = UUID.randomUUID().toString();
        definition.setKey(key);
        this.dataFillPrivateService.addDefinition(definition, definitionVO.getModel(), language);
        returnInfoVO.setSuccess(true);
        returnInfoVO.setData(this.getDefinitionNode(definition));
        returnInfoVO.setMsg("\u4fdd\u5b58\u6210\u529f");
        return returnInfoVO;
    }

    @PostMapping(value={"/deleteDefinition/{key}"})
    @ApiOperation(value="\u5220\u9664\u6a21\u677f")
    public void deleteDefinition(@PathVariable String key) throws JQException {
        this.dataFillPrivateService.deleteDefinitionByKey(key);
    }

    @PostMapping(value={"/modifyDefinition/{key}/{language}"})
    @ApiOperation(value="\u4fee\u6539\u6a21\u677f")
    public ReturnInfoVO modifyDefinition(@PathVariable String key, @PathVariable String language, @Valid @RequestBody DefinitionVO definitionVO) throws Exception {
        key = HtmlUtils.cleanUrlXSS((String)key);
        language = HtmlUtils.cleanUrlXSS((String)language);
        ReturnInfoVO returnInfoVO = new ReturnInfoVO();
        DataFillDefinitionPrivate definition = new DataFillDefinitionPrivate();
        definition.setKey(key);
        definition.setTitle(definitionVO.getTitle());
        definition.setGroupId(definitionVO.getGroup());
        definition.setTask(definitionVO.getTask());
        definition.setCreateUser(NpContextHolder.getContext().getUserName());
        if (definitionVO.getModel() == null) {
            if (this.checkDefinition(definitionVO, returnInfoVO)) {
                return returnInfoVO;
            }
            this.dataFillPrivateService.modifyDefinition(definition);
        } else {
            this.dataFillPrivateService.modifyModel(key, definitionVO.getModel(), language);
        }
        returnInfoVO.setData(this.getDefinitionNode(definition));
        returnInfoVO.setSuccess(true);
        return returnInfoVO;
    }

    private boolean checkDefinition(DefinitionVO definitionVO, ReturnInfoVO returnInfoVO) {
        if (!StringUtils.hasText(definitionVO.getTitle())) {
            returnInfoVO.setMsg("\u6a21\u677f\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a");
            return true;
        }
        List<DataFillDefinitionPrivate> definitions = this.dataFillPrivateService.getDefinitionByParentAndUser(definitionVO.getGroup(), NpContextHolder.getContext().getUserName());
        for (DataFillDefinitionPrivate d : definitions) {
            if (!d.getTitle().equals(definitionVO.getTitle())) continue;
            returnInfoVO.setMsg("\u6a21\u677f\u6807\u9898\u91cd\u590d");
            return true;
        }
        return false;
    }

    private boolean checkGroup(GroupVO groupVO, ReturnInfoVO returnInfoVO) {
        if (!StringUtils.hasText(groupVO.getTitle())) {
            returnInfoVO.setMsg("\u5206\u7ec4\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a");
            return true;
        }
        List<DataFillGroupPrivate> groups = this.dataFillPrivateService.getGroupByParentAndUser(groupVO.getKey(), NpContextHolder.getContext().getUserName());
        for (DataFillGroupPrivate group : groups) {
            if (!group.getTitle().equals(groupVO.getTitle())) continue;
            returnInfoVO.setMsg("\u5206\u7ec4\u6807\u9898\u91cd\u590d");
            return true;
        }
        return false;
    }

    private void buildRootNode(List<ITree<ModelTreeNode>> res) {
        ITree root = new ITree();
        root.setKey("00000000000000000000000000000000");
        root.setCode("ROOT_MODEL");
        root.setTitle("\u6211\u7684\u6a21\u677f");
        root.setIcons(new String[]{ICON});
        root.setExpanded(true);
        root.setSelected(true);
        res.add((ITree<ModelTreeNode>)root);
    }

    private void buildShareNode(List<ITree<ModelTreeNode>> res) throws JQException {
        Map<String, DataFillDefinitionPrivate> definitionMap = this.dataFillPrivateService.getAllDefinition().stream().collect(Collectors.toMap(DataFillDefinitionPrivate::getKey, e -> e));
        Map<String, TaskDefine> taskMap = this.runTime.getAllTaskDefines().stream().collect(Collectors.toMap(IBaseMetaItem::getKey, e -> e));
        ITree share = new ITree();
        share.setKey("00000000000000000000000000000010");
        share.setCode("ROOT_SHARE");
        share.setTitle("\u5171\u4eab");
        share.setIcons(new String[]{ICON});
        share.setExpanded(false);
        share.setSelected(false);
        ArrayList<ITree> children = new ArrayList<ITree>();
        share.setChildren(children);
        res.add((ITree<ModelTreeNode>)share);
        ITree other = new ITree();
        children.add(other);
        other.setKey("00000000000000000000000000000011");
        other.setCode("OTHER_GROUP");
        other.setTitle("\u4ed6\u4eba\u5171\u4eab");
        other.setIcons(new String[]{ICON});
        other.setExpanded(false);
        other.setSelected(false);
        ArrayList<ITree<ModelTreeNode>> otherChildren = new ArrayList<ITree<ModelTreeNode>>();
        other.setChildren(otherChildren);
        List baseByReceiver = this.shareService.getBaseByReceiver(NpContextHolder.getContext().getUserId(), "com.jiuqi.nr.dafafill.owner");
        this.buildShareChildren(otherChildren, baseByReceiver, false, definitionMap, taskMap);
        ITree my = new ITree();
        children.add(my);
        my.setKey("00000000000000000000000000000012");
        my.setCode("MY_GROUP");
        my.setTitle("\u6211\u7684\u5171\u4eab");
        my.setIcons(new String[]{ICON});
        my.setExpanded(false);
        my.setSelected(false);
        ArrayList<ITree<ModelTreeNode>> myChildren = new ArrayList<ITree<ModelTreeNode>>();
        my.setChildren(myChildren);
        List baseBySharer = this.shareService.getBaseBySharer(NpContextHolder.getContext().getUserId(), "com.jiuqi.nr.dafafill.owner");
        this.buildShareChildren(myChildren, baseBySharer, true, definitionMap, taskMap);
    }

    private void buildShareChildren(List<ITree<ModelTreeNode>> otherChildren, List<ShareDataBaseDTO> baseByReceiver, boolean my, Map<String, DataFillDefinitionPrivate> definitionMap, Map<String, TaskDefine> taskMap) {
        String code = my ? "MY_MODEL" : "OTHER_MODEL";
        for (ShareDataBaseDTO dto : baseByReceiver) {
            ITree node = new ITree((INode)new ModelTreeNode(dto.getId(), code, dto.getDataId(), definitionMap, taskMap));
            node.setTitle(dto.getTitle());
            node.setIcons(new String[]{"#icon-16_DH_A_NR_guoluchaxun"});
            node.setExpanded(false);
            node.setSelected(false);
            node.setLeaf(true);
            otherChildren.add((ITree<ModelTreeNode>)node);
        }
    }

    private void buildChildren(ITree<ModelTreeNode> res, String user) {
        res.setChildren(new ArrayList());
        this.dataFillPrivateService.getGroupByParentAndUser(res.getKey(), user).forEach(group -> {
            ITree child = new ITree();
            child.setKey(group.getKey());
            child.setCode("GROUP");
            child.setTitle(group.getTitle());
            child.setIcons(new String[]{ICON});
            res.getChildren().add(child);
            this.buildChildren((ITree<ModelTreeNode>)child, user);
        });
        this.dataFillPrivateService.getDefinitionByParentAndUser(res.getKey(), user).forEach(definition -> res.getChildren().add(this.getDefinitionNode((DataFillDefinitionPrivate)definition)));
    }

    private void buildChildrenGroup(ITree<ModelTreeNode> res, String user) {
        res.setChildren(new ArrayList());
        this.dataFillPrivateService.getGroupByParentAndUser(res.getKey(), user).forEach(group -> {
            ITree child = new ITree();
            child.setKey(group.getKey());
            child.setCode("GROUP");
            child.setTitle(group.getTitle());
            child.setIcons(new String[]{ICON});
            res.getChildren().add(child);
            this.buildChildrenGroup((ITree<ModelTreeNode>)child, user);
        });
    }

    private ITree<ModelTreeNode> getDefinitionNode(DataFillDefinitionPrivate definition) {
        String taskKey = definition.getTask();
        String taskTitle = null;
        String taskCode = null;
        if (StringUtils.hasText(taskKey)) {
            TaskDefine taskDefine = this.runTime.queryTaskDefine(taskKey);
            taskTitle = taskDefine.getTitle();
            taskCode = taskDefine.getTaskCode();
        }
        ITree node = new ITree((INode)new ModelTreeNode(definition.getKey(), definition.getTitle(), "MODEL", taskKey, taskTitle, taskCode));
        node.setLeaf(true);
        node.setIcons(new String[]{"#icon-16_DH_A_NR_guoluchaxun"});
        return node;
    }
}

