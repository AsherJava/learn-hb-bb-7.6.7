/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.ext.unittree.BSSchemeInfo
 *  com.jiuqi.nr.batch.summary.service.ext.zbquery.ZBQueryEntryPara
 *  com.jiuqi.nr.batch.summary.service.ext.zbquery.ZBQueryEntryService
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryButtonList
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  com.jiuqi.nr.zbquery.model.ZBQueryModel
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.batch.summary.web;

import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.ext.unittree.BSSchemeInfo;
import com.jiuqi.nr.batch.summary.service.ext.zbquery.ZBQueryEntryPara;
import com.jiuqi.nr.batch.summary.service.ext.zbquery.ZBQueryEntryService;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummaryButtonList;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.web.app.func.para.OpenDataEntryFuncPara;
import com.jiuqi.nr.batch.summary.web.app.func.para.OpenFuncParamImpl;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/batch-summary/show-data"})
@Api(tags={"\u6c47\u603b\u6570\u636e\u67e5\u770b-API"})
public class BSViewDataController {
    @Resource
    private BSSchemeService schemeService;
    @Resource
    private IRunTimeViewController rtViewService;
    @Resource
    private BeforeViewPageDataHandler pageHandler;
    @Resource
    private ZBQueryEntryService zbQueryEntryService;
    @Resource
    private DefinitionAuthorityProvider authorityProvider;

    @ResponseBody
    @ApiOperation(value="\u67e5\u770b\u6c47\u603b\u6570\u636e-\u8bf7\u6c42\u529f\u80fd\u53c2\u6570")
    @PostMapping(value={"/load-func-para"})
    public IReturnObject<OpenDataEntryFuncPara> openDataEntryPage(@Valid @RequestBody OpenFuncParamImpl funcParam) {
        if ("com.jiuqi.nr.batch.summary.web.app.action.show.schemes".equals(funcParam.getActionId())) {
            return this.getBatchSchemeFuncPara(funcParam);
        }
        return this.getOneSchemeFuncPara(funcParam);
    }

    @ResponseBody
    @ApiOperation(value="\u7a7f\u900f\u660e\u7ec6\u67e5\u8be2-\u8bf7\u6c42\u529f\u80fd\u53c2\u6570")
    @PostMapping(value={"/zb-query/load-func-para"})
    public IReturnObject<ZBQueryModel> openZBQueryPage(@RequestBody ZBQueryEntryPara funcParam) {
        IReturnObject instance;
        ZBQueryModel queryModel = null;
        try {
            queryModel = this.zbQueryEntryService.newZBQueryModel(funcParam);
            instance = IReturnObject.getSuccessInstance((Object)queryModel);
        }
        catch (Exception e) {
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), (Object)queryModel);
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return instance;
    }

    private IReturnObject<OpenDataEntryFuncPara> getOneSchemeFuncPara(OpenFuncParamImpl funcParam) {
        ResourceNode currentTableRow = funcParam.getCurrentTableRow();
        SummaryScheme scheme = this.schemeService.findScheme(currentTableRow.getId());
        if (scheme == null) {
            return IReturnObject.getErrorInstance((String)"\u6253\u5f00\u6570\u636e\u67e5\u770b\u754c\u9762\u5931\u8d25\uff0c\u6ca1\u6709\u53ef\u4ee5\u67e5\u770b\u7684\u6c47\u603b\u65b9\u6848\uff01");
        }
        IReturnObject<String> returnObj = this.pageHandler.beforeViewData(funcParam.getContextData());
        if (returnObj != null && !returnObj.isSuccess()) {
            return IReturnObject.getErrorInstance((String)returnObj.getMessage());
        }
        TaskDefine taskDefine = this.rtViewService.queryTaskDefine(scheme.getTask());
        if (!this.authorityProvider.canReadTask(taskDefine.getKey())) {
            return IReturnObject.getErrorInstance((String)"\u60a8\u5bf9\u6c47\u603b\u65b9\u6848\u7684\u6765\u6e90\u4efb\u52a1\u6ca1\u6709\u8bbf\u95ee\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
        }
        OpenDataEntryFuncPara funcPara = this.madeOpenDataEntryFuncPara(taskDefine, scheme, funcParam);
        return IReturnObject.getSuccessInstance((Object)funcPara);
    }

    private IReturnObject<OpenDataEntryFuncPara> getBatchSchemeFuncPara(OpenFuncParamImpl funcParam) {
        List<SummaryScheme> schemes = this.getShowSchemes(funcParam.getContextData().getTaskId(), funcParam.getCheckTableRow());
        if (schemes.isEmpty()) {
            return IReturnObject.getErrorInstance((String)"\u6253\u5f00\u6570\u636e\u67e5\u770b\u754c\u9762\u5931\u8d25\uff0c\u6ca1\u6709\u53ef\u4ee5\u67e5\u770b\u7684\u6c47\u603b\u65b9\u6848\uff01");
        }
        IReturnObject<String> returnObj = this.pageHandler.beforeViewData(funcParam.getContextData());
        if (returnObj != null && !returnObj.isSuccess()) {
            return IReturnObject.getErrorInstance((String)returnObj.getMessage());
        }
        TaskDefine taskDefine = this.checkSummarySchemes(schemes);
        if (!this.authorityProvider.canReadTask(taskDefine.getKey())) {
            return IReturnObject.getErrorInstance((String)"\u60a8\u5bf9\u6c47\u603b\u65b9\u6848\u7684\u6765\u6e90\u4efb\u52a1\u6ca1\u6709\u8bbf\u95ee\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
        }
        SummaryScheme scheme = schemes.get(0);
        OpenDataEntryFuncPara funcPara = this.madeOpenDataEntryFuncPara(taskDefine, scheme, funcParam);
        funcPara.getVariableMap().put("batchShowSchemeCodes", schemes.stream().map(this::madeBSSchemeInfo));
        return IReturnObject.getSuccessInstance((Object)funcPara);
    }

    private BSSchemeInfo madeBSSchemeInfo(SummaryScheme scheme) {
        BSSchemeInfo schemeInfo = new BSSchemeInfo();
        schemeInfo.setKey(scheme.getKey());
        schemeInfo.setDimType(scheme.getTargetDim().getTargetDimType().value + "");
        schemeInfo.setDimValue(scheme.getTargetDim().getDimValue());
        return schemeInfo;
    }

    private OpenDataEntryFuncPara madeOpenDataEntryFuncPara(TaskDefine taskDefine, SummaryScheme scheme, OpenFuncParamImpl funcParam) {
        OpenDataEntryFuncPara funcPara = new OpenDataEntryFuncPara();
        funcPara.setTaskId(taskDefine.getKey());
        funcPara.getVariableMap().put("batchGatherSchemeCode", scheme.getKey());
        funcPara.getVariableMap().put("dimType", scheme.getTargetDim().getTargetDimType().value + "");
        funcPara.getVariableMap().put("dimValue", scheme.getTargetDim().getDimValue());
        funcPara.getVariableMap().put("productName", funcParam.getContextData().getProductName());
        SummaryButtonList summaryButtonList = new SummaryButtonList();
        summaryButtonList.setSummary(scheme.getCreator().equals(BatchSummaryUtils.getCurrentUserID()) && !funcParam.getContextData().getProductName().equals("dt"));
        summaryButtonList.setBatchSummary(scheme.getCreator().equals(BatchSummaryUtils.getCurrentUserID()) && funcParam.getContextData().getProductName().equals("dt"));
        summaryButtonList.setCalc(scheme.getCreator().equals(BatchSummaryUtils.getCurrentUserID()) && !scheme.getKey().isEmpty());
        summaryButtonList.setCalcAll(scheme.getCreator().equals(BatchSummaryUtils.getCurrentUserID()) && !scheme.getKey().isEmpty());
        summaryButtonList.setCheck(scheme.getCreator().equals(BatchSummaryUtils.getCurrentUserID()));
        summaryButtonList.setCheckAll(scheme.getCreator().equals(BatchSummaryUtils.getCurrentUserID()));
        summaryButtonList.setSummaryDetails(scheme.getCreator().equals(BatchSummaryUtils.getCurrentUserID()) && !scheme.getKey().isEmpty());
        funcPara.getVariableMap().put("summaryButtonsControl", summaryButtonList);
        return funcPara;
    }

    private List<SummaryScheme> getShowSchemes(String taskId, List<ResourceNode> checkTableRow) {
        ArrayList<SummaryScheme> schemes = new ArrayList<SummaryScheme>();
        if (checkTableRow == null || checkTableRow.isEmpty()) {
            return this.schemeService.findSchemes(taskId);
        }
        schemes.addAll(this.querySchemesByGroup(taskId, checkTableRow));
        schemes.addAll(this.querySchemesByKeys(taskId, checkTableRow));
        return schemes;
    }

    private List<SummaryScheme> querySchemesByGroup(String taskId, List<ResourceNode> checkTableRow) {
        List<ResourceNode> groupNodes = checkTableRow.stream().filter(this::isGroupNode).collect(Collectors.toList());
        ArrayList<SummaryScheme> schemes = new ArrayList<SummaryScheme>();
        groupNodes.forEach(n -> schemes.addAll(this.schemeService.findAllChildSchemeByGroup(taskId, n.getId())));
        return schemes;
    }

    private List<SummaryScheme> querySchemesByKeys(String taskId, List<ResourceNode> checkTableRow) {
        List schemeNodes = checkTableRow.stream().filter(this::isSchemeNode).collect(Collectors.toList());
        List schemeKeys = schemeNodes.stream().map(ResourceNode::getId).collect(Collectors.toList());
        List schemes = this.schemeService.findSchemes(schemeKeys);
        if (schemes != null) {
            return schemes;
        }
        return new ArrayList<SummaryScheme>();
    }

    private TaskDefine checkSummarySchemes(List<SummaryScheme> schemes) {
        List taskIds = schemes.stream().map(SummaryScheme::getTask).collect(Collectors.toList());
        if (!taskIds.stream().allMatch(((String)taskIds.get(0))::equals)) {
            throw new RuntimeException("\u6253\u5f00\u6570\u636e\u67e5\u770b\u754c\u9762\u5931\u8d25\uff0c\u6279\u91cf\u67e5\u770b\u53ea\u652f\u6301\u540c\u4e00\u4e2a\u6765\u6e90\u4efb\u52a1\uff01");
        }
        return this.rtViewService.queryTaskDefine((String)taskIds.get(0));
    }

    private boolean isGroupNode(ResourceNode node) {
        return NodeType.NODE_GROUP == node.getType();
    }

    private boolean isSchemeNode(ResourceNode node) {
        return NodeType.NODE_DATA == node.getType();
    }
}

