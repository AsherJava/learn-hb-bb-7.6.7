/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringHelper
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.xlib.utils.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dafafill.web;

import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dafafill.common.DataFillErrorEnum;
import com.jiuqi.nr.dafafill.entity.DataFillDefinition;
import com.jiuqi.nr.dafafill.entity.DataFillGroup;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.service.IDataFillDataService;
import com.jiuqi.nr.dafafill.service.IDataFillDefinitionService;
import com.jiuqi.nr.dafafill.service.IDataFillGroupService;
import com.jiuqi.nr.dafafill.web.vo.DataFillDefinitionVO;
import com.jiuqi.nr.dafafill.web.vo.ReturnInfoVO;
import com.jiuqi.nr.dafafill.web.vo.TaskTreeNodeVO;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.xlib.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/datafill/definition"})
@Api(value="\u81ea\u5b9a\u4e49\u5f55\u5165\u6a21\u677f\u5b9a\u4e49\u7ba1\u7406")
public class DataFillDefinitionController {
    @Autowired
    IDataFillGroupService dataFillGroupService;
    @Autowired
    IDataFillDefinitionService dataFillDefinitionService;
    @Autowired
    IDataFillDataService dataFillDataService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthView;

    @GetMapping(value={"/{id}"})
    @ApiOperation(value="\u83b7\u53d6\u6a21\u677f\u5b9a\u4e49")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public DataFillDefinitionVO findById(@PathVariable String id) throws Exception {
        DataFillDefinition definition = this.dataFillDefinitionService.findById(id);
        DataFillDefinitionVO vo = new DataFillDefinitionVO();
        BeanUtils.copyProperties(definition, vo);
        if (StringUtils.hasText((String)definition.getTaskKey())) {
            TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(definition.getTaskKey());
            vo.setTaskCode(taskDefine.getTaskCode());
            vo.setTaskTitle(taskDefine.getTitle());
        }
        return vo;
    }

    @PostMapping(value={"/tree"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u6811")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public List<ITree<TaskTreeNodeVO>> buildTaskTree(@RequestBody String taskKey) {
        ArrayList<ITree<TaskTreeNodeVO>> taskTree = new ArrayList<ITree<TaskTreeNodeVO>>();
        taskTree.add(this.dataFillDefinitionService.buildTaskTree(taskKey));
        return taskTree;
    }

    @PostMapping(value={"add"})
    @ApiOperation(value="\u65b0\u589e\u6a21\u677f\u5b9a\u4e49")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public ReturnInfoVO add(@Valid @RequestBody DataFillDefinition def) throws JQException {
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        def.setModifyTime(new Timestamp(System.currentTimeMillis()));
        if (this.checkDefinition(def, returnInfo)) {
            return returnInfo;
        }
        if (!StringUtils.hasText((String)def.getId())) {
            def.setId(Guid.newGuid());
            def.setCreateTime(def.getModifyTime());
            this.dataFillDefinitionService.add(def);
        } else {
            this.dataFillDefinitionService.modify(def);
        }
        returnInfo.setData(def.getId());
        returnInfo.setSuccess(true);
        return returnInfo;
    }

    private boolean checkDefinition(@RequestBody DataFillDefinition def, ReturnInfoVO returnInfo) {
        if (StringHelper.isNull((String)def.getCode())) {
            returnInfo.setMsg(DataFillErrorEnum.DF_ERROR_CODE_NULL.getMessage());
            return true;
        }
        DataFillDefinition temp = this.dataFillDefinitionService.findByCode(def.getCode());
        if (temp != null && !temp.getId().equals(def.getId())) {
            returnInfo.setMsg(DataFillErrorEnum.DF_ERROR_CODE_REPEAT.getMessage());
            return true;
        }
        List<DataFillGroup> groupList = this.dataFillGroupService.findByParentId(def.getParentId());
        for (DataFillGroup g : groupList) {
            if (!g.getTitle().equals(def.getTitle()) || g.getId().equals(def.getId())) continue;
            returnInfo.setMsg(DataFillErrorEnum.DF_ERROR_TITLE_REPEAT.getMessage());
            return true;
        }
        List<DataFillDefinition> definitionList = this.dataFillDefinitionService.findByParentId(def.getParentId());
        for (DataFillDefinition d : definitionList) {
            if (!d.getTitle().equals(def.getTitle()) || d.getId().equals(def.getId())) continue;
            returnInfo.setMsg(DataFillErrorEnum.DF_ERROR_TITLE_REPEAT.getMessage());
            return true;
        }
        if (def.getSourceType().value() == ModelType.TASK.value()) {
            def.setTaskKey(null);
        }
        return false;
    }

    @PostMapping(value={"/copy/{sourceDefId}/{language}"})
    @ApiOperation(value="\u590d\u5236\u6a21\u677f\u5b9a\u4e49\u53ca\u6570\u636e")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public ReturnInfoVO copy(@Valid @RequestBody DataFillDefinition def, @PathVariable String sourceDefId, @PathVariable String language) throws JQException {
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        if (this.checkDefinition(def, returnInfo)) {
            return returnInfo;
        }
        def.setId(Guid.newGuid());
        def.setModifyTime(new Timestamp(System.currentTimeMillis()));
        def.setCreateTime(def.getModifyTime());
        this.dataFillDefinitionService.add(def);
        DataFillModel dataFillModel = this.dataFillDataService.getModelByDefinition(sourceDefId, language);
        if (dataFillModel != null) {
            this.dataFillDataService.saveModel(def.getId(), language, dataFillModel);
        }
        returnInfo.setData(def.getId());
        returnInfo.setSuccess(true);
        return returnInfo;
    }

    @PostMapping(value={"delete/{id}"})
    @ApiOperation(value="\u5220\u9664\u6a21\u677f\u5b9a\u4e49")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public void delete(@PathVariable String id) throws JQException {
        this.dataFillDefinitionService.delete(id);
    }

    @PostMapping(value={"batch-del"})
    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u6a21\u677f\u5b9a\u4e49")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public void batchDel(List<String> ids) throws JQException {
    }

    @PostMapping(value={"modify"})
    @ApiOperation(value="\u4fee\u6539\u6a21\u677f\u5b9a\u4e49")
    @RequiresPermissions(value={"nr:datafill:manage"})
    public void modify(@RequestBody DataFillDefinition def) throws JQException {
    }
}

