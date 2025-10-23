/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.tree.core.ITreeService;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeQueryParamVO;
import com.jiuqi.nr.summary.tree.core.TreeServiceHolder;
import com.jiuqi.nr.summary.utils.ITreeConvert;
import com.jiuqi.nr.summary.utils.ParameterBuilder;
import com.jiuqi.nr.summary.vo.JobConfigParameter;
import com.jiuqi.nr.summary.vo.ParameterBuildInfo;
import com.jiuqi.nr.summary.vo.ParameterModelItem;
import com.jiuqi.nr.summary.vo.ParameterModelWrapper;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_report/job"})
public class SummaryReportJobController {
    private static final Logger logger = LoggerFactory.getLogger(SummaryReportJobController.class);
    @Autowired
    private IRuntimeSummarySolutionService runtimeSolutionService;
    @Autowired
    private ParameterBuilder parameterBuilder;
    @Autowired
    private TreeServiceHolder treeServiceHolder;

    @GetMapping(value={"/title/solution/{solutionKey}"})
    public String getSumSolutionTitle(@PathVariable String solutionKey) {
        SummarySolution solutionDefine = this.runtimeSolutionService.getSummarySolutionDefine(solutionKey);
        return solutionDefine.getTitle();
    }

    @PostMapping(value={"/config/tree/solution/root"})
    public List<com.jiuqi.nr.summary.vo.vue2.TreeNode> getSolutionTreeRoot(@RequestBody TreeQueryParamVO treeQueryParam) throws SummaryCommonException {
        ITreeService treeService = this.treeServiceHolder.getTreeService(treeQueryParam.getTreeId());
        List<TreeNode> roots = treeService.getRoots(treeQueryParam.getQueryParam());
        return roots.stream().map(ITreeConvert::fromTreeNode).collect(Collectors.toList());
    }

    @PostMapping(value={"/config/tree/solution/childs"})
    public List<com.jiuqi.nr.summary.vo.vue2.TreeNode> getSolutionTreeChilds(@RequestBody TreeQueryParamVO treeQueryParam) throws SummaryCommonException {
        ITreeService treeService = this.treeServiceHolder.getTreeService(treeQueryParam.getTreeId());
        List<TreeNode> roots = treeService.getChilds(treeQueryParam.getQueryParam());
        return roots.stream().map(ITreeConvert::fromTreeNode).collect(Collectors.toList());
    }

    @GetMapping(value={"/config/list/report/{solutionKey}"})
    public List<SummaryReport> getReportsBySolu(@PathVariable String solutionKey) {
        return this.runtimeSolutionService.getSummaryReportDefinesBySolu(solutionKey);
    }

    @PostMapping(value={"/config/xfform/parameter"})
    public List<JobConfigParameter> getParameter(@RequestBody ParameterBuildInfo buildInfo) throws SummaryCommonException {
        ArrayList<JobConfigParameter> paramters = new ArrayList<JobConfigParameter>();
        ParameterModelWrapper parameterModelWrapper = this.parameterBuilder.buildParameter(buildInfo);
        try {
            String paramValue;
            ParameterModelItem periodParam = parameterModelWrapper.getPeriodParam();
            ParameterModelItem masterParam = parameterModelWrapper.getMasterParam();
            List<ParameterModelItem> sceneParams = parameterModelWrapper.getSceneParams();
            if (periodParam != null) {
                paramValue = ParameterConvertor.toJson(null, (ParameterModel)periodParam.getParam(), (boolean)false).toString();
                paramters.add(new JobConfigParameter(periodParam.getName(), paramValue));
            }
            if (masterParam != null) {
                paramValue = ParameterConvertor.toJson(null, (ParameterModel)masterParam.getParam(), (boolean)false).toString();
                paramters.add(new JobConfigParameter(masterParam.getName(), paramValue));
            }
            if (!CollectionUtils.isEmpty(sceneParams)) {
                for (ParameterModelItem modelItem : sceneParams) {
                    String paramValue2 = ParameterConvertor.toJson(null, (ParameterModel)modelItem.getParam(), (boolean)false).toString();
                    paramters.add(new JobConfigParameter(modelItem.getName(), paramValue2));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SummaryCommonException(SummaryErrorEnum.XFFORM_LOAD_FAILED);
        }
        return paramters;
    }
}

