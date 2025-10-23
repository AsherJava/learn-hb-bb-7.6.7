/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.report.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum;
import com.jiuqi.nr.report.dto.ReportTagDTO;
import com.jiuqi.nr.report.helper.QuickReportHelper;
import com.jiuqi.nr.report.service.IReportTagManageService;
import com.jiuqi.nr.report.web.vo.ChartTreeNode;
import com.jiuqi.nr.report.web.vo.CustomTagVO;
import com.jiuqi.nr.report.web.vo.EntityAttributeVO;
import com.jiuqi.nr.report.web.vo.FormDataVO;
import com.jiuqi.nr.report.web.vo.QuickReportNode;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/report/tag/"})
@Api(tags={"\u5206\u6790\u62a5\u544a\u6a21\u677f\u81ea\u5b9a\u4e49\u6807\u7b7e\u6a21\u5757"})
public class ReportTagController {
    @Autowired
    private QuickReportHelper quickReportHelper;
    @Autowired
    private IReportTagManageService reportTagManageService;

    @ApiOperation(value="\u67e5\u8be2\u62a5\u544a\u6a21\u677f\u4e0b\u7684\u6240\u6709\u81ea\u5b9a\u4e49\u6807\u7b7e")
    @GetMapping(value={"query-tags-by-rpt/{rptKey}/{fileKey}"})
    public List<CustomTagVO> queryTagsByRpt(@PathVariable String rptKey, @PathVariable String fileKey) throws Exception {
        List<ReportTagDTO> reportTagDTOS = this.reportTagManageService.listAllTagsByRpt(rptKey);
        List<CustomTagVO> customTagVOList = CustomTagVO.dtoToCustomTagVOList(reportTagDTOS);
        List<Object> reportTagDefineList = new ArrayList();
        if (customTagVOList.size() != 0) {
            reportTagDefineList = this.reportTagManageService.filterCustomTagsInRpt(rptKey, fileKey);
        }
        ArrayList<CustomTagVO> result = new ArrayList<CustomTagVO>();
        Map<String, CustomTagVO> customTagInFile = customTagVOList.stream().collect(Collectors.toMap(CustomTagVO::getContent, o -> o));
        for (ReportTagDTO reportTagDTO : reportTagDefineList) {
            CustomTagVO customTagVO2 = customTagInFile.get(reportTagDTO.getContent());
            if (customTagVO2 == null) continue;
            customTagVO2.set_disabled(true);
            result.add(customTagVO2);
        }
        result.addAll(customTagVOList.stream().filter(customTagVO -> !customTagVO.is_disabled()).collect(Collectors.toList()));
        this.quickReportHelper.setChartTitle(result);
        this.quickReportHelper.setQuickReportTitle(result);
        this.quickReportHelper.setFormAndEntityTitle(result, rptKey);
        return result;
    }

    @ApiOperation(value="\u5220\u9664\u81ea\u5b9a\u4e49\u6807\u7b7e")
    @PostMapping(value={"del-tags"})
    @Transactional(rollbackFor={Exception.class})
    @TaskLog(operation="\u5220\u9664\u81ea\u5b9a\u4e49\u6807\u7b7e")
    public void delTags(@RequestBody List<String> keys) {
        this.reportTagManageService.deleteTags(keys);
    }

    @ApiOperation(value="\u6279\u91cf\u4fdd\u5b58\u4fdd\u5b58\u81ea\u5b9a\u4e49\u6807\u7b7e\u7684\u4fe1\u606f")
    @PostMapping(value={"batch_save-tag-info"})
    @TaskLog(operation="\u6279\u91cf\u4fdd\u5b58\u4fdd\u5b58\u81ea\u5b9a\u4e49\u6807\u7b7e\u7684\u4fe1\u606f")
    public void saveTagInfos(@RequestBody List<CustomTagVO> customTagVOs) throws JQException {
        this.reportTagManageService.updateTags(customTagVOs);
    }

    @ApiOperation(value="\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u5c5e\u6027")
    @GetMapping(value={"query-entity-attribute/{taskKey}"})
    public List<EntityAttributeVO> getEntityAttribute(@PathVariable String taskKey) throws JQException {
        ArrayList<EntityAttributeVO> result = new ArrayList();
        try {
            result = this.quickReportHelper.getEntityAttribute(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_011, (Throwable)e);
        }
        return result;
    }

    @ApiOperation(value="\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u5c5e\u6027\u6811\u578b")
    @GetMapping(value={"query-entity-attribute-tree/{taskKey}"})
    public List<UITreeNode<EntityAttributeVO>> getEntityAttributeTree(@PathVariable String taskKey) throws JQException {
        ArrayList<UITreeNode<EntityAttributeVO>> entityAttributeTree = new ArrayList<UITreeNode<EntityAttributeVO>>();
        try {
            List<EntityAttributeVO> result = this.quickReportHelper.getEntityAttribute(taskKey);
            for (EntityAttributeVO resource : result) {
                UITreeNode uiTreeNode = new UITreeNode();
                uiTreeNode.setKey(resource.getCode());
                uiTreeNode.setTitle(resource.getCode() + " | " + resource.getTitle());
                uiTreeNode.setData((TreeData)resource);
                uiTreeNode.setLeaf(true);
                entityAttributeTree.add((UITreeNode<EntityAttributeVO>)uiTreeNode);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_011, (Throwable)e);
        }
        return entityAttributeTree;
    }

    @ApiOperation(value="\u67e5\u8be2\u6807\u7b7e\u5173\u8054\u62a5\u8868\u6240\u9700\u6570\u636e")
    @GetMapping(value={"query-form-for-tag/{formSchemeKey}"})
    public List<FormDataVO> getFormData(@PathVariable String formSchemeKey) throws JQException {
        return this.quickReportHelper.getForm(formSchemeKey);
    }

    @ApiOperation(value="\u67e5\u8be2\u6807\u7b7e\u5173\u8054\u62a5\u8868\u6240\u9700\u6570\u636e\u6811\u578b")
    @GetMapping(value={"query-form-tree-for-tag/{formSchemeKey}"})
    public List<UITreeNode<FormDataVO>> getFormDataTree(@PathVariable String formSchemeKey) {
        ArrayList<UITreeNode<FormDataVO>> formDataTree = new ArrayList<UITreeNode<FormDataVO>>();
        List<FormDataVO> form = this.quickReportHelper.getForm(formSchemeKey);
        for (FormDataVO resource : form) {
            UITreeNode uiTreeNode = new UITreeNode();
            uiTreeNode.setKey(resource.getCode());
            uiTreeNode.setTitle(resource.getCode() + " | " + resource.getTitle());
            uiTreeNode.setData((TreeData)resource);
            uiTreeNode.setLeaf(true);
            formDataTree.add((UITreeNode<FormDataVO>)uiTreeNode);
        }
        return formDataTree;
    }

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u5206\u6790\u4e0b\u7684\u5206\u6790\u8868\u6811\u5f62\u8282\u70b9\u6570\u636e")
    @GetMapping(value={"query-quick-report-child-tree/{parent}"})
    public List<UITreeNode<QuickReportNode>> getQuickReportTreeChild(@PathVariable String parent) {
        return this.quickReportHelper.getChildren(parent);
    }

    @ApiOperation(value="\u83b7\u53d6\u5b9a\u4f4d\u5206\u6790\u8868\u8282\u70b9\u7684\u6811\u5f62\u6570\u636e")
    @GetMapping(value={"locate-quick-report-node/{guid}"})
    public List<UITreeNode<QuickReportNode>> locateQuickReportNodeTree(@PathVariable String guid) {
        return this.quickReportHelper.locate(guid);
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u7684\u5206\u6790\u8868\u8282\u70b9")
    @GetMapping(value={"all-quick-report-nodes"})
    public Map<String, String> listAllQuickReportNode() {
        return this.quickReportHelper.getAllNodes();
    }

    @ApiOperation(value="\u56fe\u8868\u6811\u578b\u7684\u4e0b\u7ea7")
    @GetMapping(value={"query-chart-child-tree/{parent}/{type}"})
    public List<UITreeNode<ChartTreeNode>> getChartTreeChild(@PathVariable String parent, @PathVariable String type) throws DataAnalyzeResourceException, DashboardException {
        return this.quickReportHelper.getChartChildern(parent, type, false);
    }

    @ApiOperation(value="\u5b9a\u4f4d\u56fe\u8868\u6811")
    @GetMapping(value={"locate-chart-node/{chartId}"})
    public List<UITreeNode<ChartTreeNode>> locateChartNodeTree(@PathVariable String chartId) throws DataAnalyzeResourceException, DashboardException {
        return this.quickReportHelper.locationChartTrees(chartId);
    }
}

