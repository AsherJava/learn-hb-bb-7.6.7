/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.summary.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.common.convert.Convert;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummarySolutionManageException;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionService;
import com.jiuqi.nr.summary.manage.provider.ResourceDirNodeProvider;
import com.jiuqi.nr.summary.manage.provider.ResourceNodeProvider;
import com.jiuqi.nr.summary.manage.provider.SummaryParamVOProvider;
import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.service.ISummarySearchService;
import com.jiuqi.nr.summary.vo.BatchDeleteVO;
import com.jiuqi.nr.summary.vo.ReportCopyRequestParam;
import com.jiuqi.nr.summary.vo.ResourceNode;
import com.jiuqi.nr.summary.vo.SummarySolutionModelForm;
import com.jiuqi.nr.summary.vo.TaskParamVO;
import com.jiuqi.nr.summary.vo.TreeNode;
import com.jiuqi.nr.summary.vo.search.SummarySearchItem;
import com.jiuqi.nr.summary.vo.search.SummarySearchPosition;
import com.jiuqi.nr.summary.vo.search.SummarySearchPositionRequestParam;
import com.jiuqi.nr.summary.vo.search.SummarySearchRequestParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/summary_solution"})
public class SummarySolutionManageController {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignSummarySolutionService designSolutionService;
    @Autowired
    private ISummarySolutionService solutionService;
    @Autowired
    private IDesignSummaryReportService designReportService;
    @Autowired
    private ResourceDirNodeProvider resourceDirNodeProvider;
    @Autowired
    private ResourceNodeProvider resourceNodeProvider;
    @Autowired
    private SummaryParamVOProvider paramVOProvider;
    @Autowired
    private ISummarySearchService searchService;

    @GetMapping(value={"/tree/root"})
    public List<TreeNode> getSummarySolutionTreeRoot() {
        return this.resourceDirNodeProvider.getRoots();
    }

    @GetMapping(value={"/tree/children/{parentKey}"})
    public List<TreeNode> getSummarySolutionTreeChildren(@PathVariable String parentKey) {
        return this.resourceDirNodeProvider.getChilds("_summary_solution_root_group_".equals(parentKey) ? null : parentKey);
    }

    @PostMapping(value={"/group/add"})
    public void addSummarySolutionGroup(@RequestBody SummarySolutionGroup solutionGroup) throws SummaryCommonException {
        this.designSolutionService.insertSummarySolutionGroup(solutionGroup);
    }

    @PostMapping(value={"/group/update"})
    public void updateSummarySolutionGroup(@RequestBody SummarySolutionGroup solutionGroup) throws SummaryCommonException {
        this.designSolutionService.updateSummarySolutionGroup(solutionGroup);
    }

    @PostMapping(value={"/group/validate"})
    public void validateSummarySolutionGroup(@RequestBody SummarySolutionGroup solutionGroup) throws SummarySolutionManageException {
    }

    @GetMapping(value={"/group/delete/{groupKey}"})
    public void deleteSummarySolutionGroup(@PathVariable String groupKey) throws SummaryCommonException {
        this.designSolutionService.deleteSummarySolutionGroup(groupKey);
    }

    @GetMapping(value={"/resource_datas/{type}/{parentKey}"})
    public List<ResourceNode> getResourceDatas(@PathVariable int type, @PathVariable String parentKey) throws SummarySolutionManageException {
        return this.resourceNodeProvider.getNodes(type, "_summary_solution_root_group_".equals(parentKey) ? null : parentKey);
    }

    @PostMapping(value={"/model/name/validate"})
    public void validateSummarySolutionName(@RequestBody SummarySolutionModel model) {
    }

    @PostMapping(value={"/model/title/validate"})
    public void validateSummarySolutionTitle(@RequestBody SummarySolutionModel model) {
    }

    @PostMapping(value={"/solution/add"})
    public void addSummarySolution(@RequestBody SummarySolutionModel summarySolutionModel) throws SummaryCommonException {
        this.designSolutionService.insertSummarySolutionModel(summarySolutionModel);
    }

    @PostMapping(value={"/solution/update"})
    public void updateSummarySolution(@RequestBody SummarySolutionModel summarySolutionModel) throws SummaryCommonException {
        this.solutionService.updateSummarySolution(Convert.summarySolutionModelConvert.VO2DTO(summarySolutionModel));
    }

    @GetMapping(value={"/solution/delete/{solutionKey}"})
    public void deleteSummarySolution(@PathVariable String solutionKey) throws SummaryCommonException {
        this.solutionService.deleteSummarySolutionByKey(solutionKey);
    }

    @PostMapping(value={"/batchDelete"})
    public void batchDelete(@RequestBody BatchDeleteVO batchDelete) throws SummaryCommonException {
        List<String> sumSolutionGroupKeys = batchDelete.getSumSolutionGroupKeys();
        List<String> sumSolutionKeys = batchDelete.getSumSolutionKeys();
        List<String> sumReportKeys = batchDelete.getSumReportKeys();
        if (!CollectionUtils.isEmpty(sumSolutionGroupKeys)) {
            this.designSolutionService.deleteSummarySolutionGroups(sumSolutionGroupKeys);
        }
        if (!CollectionUtils.isEmpty(sumSolutionKeys)) {
            this.designSolutionService.batchDeleteSummarySolutions(sumSolutionKeys);
        }
        if (!CollectionUtils.isEmpty(sumReportKeys)) {
            this.designReportService.deleteSummaryReportByKeys(sumReportKeys);
        }
    }

    @GetMapping(value={"/model/get_formInfo/{solutionKey}"})
    public SummarySolutionModelForm getSummarySolution(@PathVariable String solutionKey) throws SummaryCommonException {
        return this.paramVOProvider.getSummarySolutionForm(solutionKey);
    }

    @GetMapping(value={"/task_tree/root"})
    public List<TreeNode> getTaskTreeRootNode() {
        return this.paramVOProvider.getTasks(null);
    }

    @GetMapping(value={"/task_tree/nodes/{parentKey}"})
    public List<TreeNode> getTaskTreeNodes(@PathVariable String parentKey) {
        return this.paramVOProvider.getTasks("_task_root_key_".equals(parentKey) ? null : parentKey);
    }

    @GetMapping(value={"/task/param/{taskKey}"})
    public TaskParamVO getTaskParam(@PathVariable String taskKey) throws SummaryCommonException {
        return this.paramVOProvider.getTaskParamForSolution(taskKey);
    }

    @GetMapping(value={"/related_task/used/{relatedTaskKey}"})
    public boolean getRelatedTaskUsed(@PathVariable String relatedTaskKey) throws SummarySolutionManageException {
        return this.paramVOProvider.relatedTaskUsed(relatedTaskKey);
    }

    @PostMapping(value={"/report/name/validate"})
    public void validateSummaryReportName(@RequestBody SummaryReport report) {
    }

    @PostMapping(value={"/report/title/validate"})
    public void validateSummaryReportTitle(@RequestBody SummaryReport report) {
    }

    @PostMapping(value={"/report/add"})
    public String addSummaryReport(@RequestBody SummaryReport report) throws SummaryCommonException {
        return this.designReportService.insertSummaryReport(Convert.baseDesignSummaryReportConvert.VO2DTO(report));
    }

    @PostMapping(value={"/report/update"})
    public void updateSummaryReport(@RequestBody SummaryReport report) throws SummaryCommonException {
        this.designReportService.updateSummaryReport(Convert.baseDesignSummaryReportConvert.VO2DTO(report), true);
    }

    @PostMapping(value={"/report/copy"})
    public void copySummaryReport(@RequestBody ReportCopyRequestParam copyReqParam) throws SummaryCommonException {
        this.designSolutionService.copySummaryReport(copyReqParam);
    }

    @GetMapping(value={"/report/delete/{reportKey}"})
    public void deleteSummaryReport(@PathVariable String reportKey) throws SummaryCommonException {
        this.designReportService.deleteSummaryReportByKey(reportKey);
    }

    @GetMapping(value={"/report/moveUp/{solutionKey}/{reportKey}"})
    public void moveUpSummaryReport(@PathVariable String solutionKey, @PathVariable String reportKey) throws SummaryCommonException {
        this.designReportService.moveSummaryReport(solutionKey, reportKey, 0);
    }

    @GetMapping(value={"/report/moveDown/{solutionKey}/{reportKey}"})
    public void moveDownSummaryReport(@PathVariable String solutionKey, @PathVariable String reportKey) throws SummaryCommonException {
        this.designReportService.moveSummaryReport(solutionKey, reportKey, 1);
    }

    @PostMapping(value={"/manage/search"})
    public List<SummarySearchItem> search(@RequestBody SummarySearchRequestParam searchReqParam) {
        return this.searchService.search(searchReqParam);
    }

    @PostMapping(value={"/manage/search/position"})
    public SummarySearchPosition position(@RequestBody SummarySearchPositionRequestParam searchPositionReqParam) {
        return this.searchService.position(searchPositionReqParam);
    }
}

