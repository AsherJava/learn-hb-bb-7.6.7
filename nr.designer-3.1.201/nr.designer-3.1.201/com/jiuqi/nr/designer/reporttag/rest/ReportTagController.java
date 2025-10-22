/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignReportTagDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum
 *  com.jiuqi.nr.definition.reportTag.common.ReportTagType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.exception.FMDMQueryException
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.reporttag.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum;
import com.jiuqi.nr.definition.reportTag.common.ReportTagType;
import com.jiuqi.nr.designer.reporttag.helper.QuickReportHelper;
import com.jiuqi.nr.designer.reporttag.rest.vo.CustomTagVO;
import com.jiuqi.nr.designer.reporttag.rest.vo.EntityAttributeVO;
import com.jiuqi.nr.designer.reporttag.rest.vo.FormDataVO;
import com.jiuqi.nr.designer.reporttag.rest.vo.QuickReportNode;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5206\u6790\u62a5\u544a\u6a21\u677f\u81ea\u5b9a\u4e49\u6807\u7b7e\u6a21\u5757"})
public class ReportTagController {
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private QuickReportHelper quickReportHelper;
    @Autowired
    private IEntityMetaService entityMetaService;

    @ApiOperation(value="\u67e5\u8be2\u62a5\u544a\u6a21\u677f\u4e0b\u7684\u6240\u6709\u81ea\u5b9a\u4e49\u6807\u7b7e")
    @GetMapping(value={"query-tags-by-rpt/{rptKey}/{fileKey}"})
    public List<CustomTagVO> queryTagsByRpt(@PathVariable String rptKey, @PathVariable String fileKey) throws JQException {
        List reportTagDefines = this.nrDesignTimeController.queryAllTagsByRptKey(rptKey);
        List<CustomTagVO> customTagVOList = CustomTagVO.toCustomTagVOList(reportTagDefines);
        List reportTagDefineList = new ArrayList();
        if (customTagVOList.size() != 0) {
            byte[] reportTemplateFileByte = this.runtimeViewController.getReportTemplateFile(fileKey);
            ByteArrayInputStream reportTemplateFileIS = new ByteArrayInputStream(reportTemplateFileByte);
            reportTagDefineList = this.nrDesignTimeController.filterCustomTagsInRpt((InputStream)reportTemplateFileIS, rptKey);
            for (DesignReportTagDefine reportTagDefine : reportTagDefineList) {
                for (CustomTagVO customTagVO2 : customTagVOList) {
                    if (!reportTagDefine.getContent().equals(customTagVO2.getContent())) continue;
                    customTagVO2.set_disabled(true);
                }
            }
        }
        ArrayList<CustomTagVO> result = new ArrayList<CustomTagVO>();
        Map<String, CustomTagVO> customTagInFile = customTagVOList.stream().filter(CustomTagVO::is_disabled).collect(Collectors.toMap(CustomTagVO::getContent, o -> o));
        for (DesignReportTagDefine tag : reportTagDefineList) {
            result.add(customTagInFile.get(tag.getContent()));
        }
        result.addAll(customTagVOList.stream().filter(customTagVO -> !customTagVO.is_disabled()).collect(Collectors.toList()));
        return result;
    }

    @ApiOperation(value="\u5220\u9664\u81ea\u5b9a\u4e49\u6807\u7b7e")
    @PostMapping(value={"del-tags"})
    @Transactional(rollbackFor={Exception.class})
    public void delTags(@RequestBody List<String> keys) throws JQException {
        this.nrDesignTimeController.deleteTagByKeys(keys);
    }

    @ApiOperation(value="\u4fdd\u5b58\u81ea\u5b9a\u4e49\u6807\u7b7e\u7684\u4fe1\u606f")
    @PostMapping(value={"save-tag-info"})
    public void saveTagInfo(@RequestBody CustomTagVO customTagVO) throws JQException {
        DesignReportTagDefineImpl reportTagDefine = new DesignReportTagDefineImpl();
        reportTagDefine.setKey(customTagVO.getKey());
        reportTagDefine.setRptKey(customTagVO.getRptKey());
        reportTagDefine.setType(ReportTagType.getKeyByValue((String)customTagVO.getType()));
        reportTagDefine.setContent(customTagVO.getContent());
        reportTagDefine.setExpression(customTagVO.getExpression());
        this.nrDesignTimeController.saveTag((DesignReportTagDefine)reportTagDefine);
    }

    @ApiOperation(value="\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u5c5e\u6027")
    @GetMapping(value={"query-entity-attribute/{taskKey}"})
    public List<EntityAttributeVO> getEntityAttribute(@PathVariable String taskKey) throws JQException {
        List showFields;
        DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(taskKey);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(designTaskDefine.getDw());
        try {
            showFields = entityModel.getShowFields();
        }
        catch (FMDMQueryException e) {
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_011, (Throwable)e);
        }
        ArrayList<EntityAttributeVO> result = new ArrayList<EntityAttributeVO>();
        for (IEntityAttribute showField : showFields) {
            result.add(new EntityAttributeVO(showField.getCode(), showField.getTitle()));
        }
        return result;
    }

    @ApiOperation(value="\u67e5\u8be2\u6807\u7b7e\u5173\u8054\u62a5\u8868\u6240\u9700\u6570\u636e")
    @GetMapping(value={"query-form-for-tag/{formSchemeKey}"})
    public List<FormDataVO> getFormData(@PathVariable String formSchemeKey) throws JQException {
        List forms = this.iDesignTimeViewController.queryFormsByTypeInScheme(formSchemeKey, FormType.FORM_TYPE_NEWFMDM);
        forms.addAll(this.iDesignTimeViewController.queryFormsByTypeInScheme(formSchemeKey, FormType.FORM_TYPE_FIX));
        forms.addAll(this.iDesignTimeViewController.queryFormsByTypeInScheme(formSchemeKey, FormType.FORM_TYPE_FLOAT));
        forms.addAll(this.iDesignTimeViewController.queryFormsByTypeInScheme(formSchemeKey, FormType.FORM_TYPE_ACCOUNT));
        return FormDataVO.toFormDataVO(forms);
    }

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u5206\u6790\u4e0b\u7684\u5206\u6790\u8868\u6811\u5f62\u8282\u70b9\u6570\u636e")
    @GetMapping(value={"query-quick-report-tree/{parent}"})
    public List<ITree<QuickReportNode>> getQuickReportTree(@PathVariable String parent) {
        return this.quickReportHelper.getChildren(parent);
    }

    @ApiOperation(value="\u83b7\u53d6\u5b9a\u4f4d\u5206\u6790\u8868\u8282\u70b9\u7684\u6811\u5f62\u6570\u636e")
    @GetMapping(value={"locate-quick-report-node/{guid}"})
    public List<ITree<QuickReportNode>> locateQuickReportNode(@PathVariable String guid) {
        return this.quickReportHelper.locate(guid);
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u7684\u5206\u6790\u8868\u8282\u70b9")
    @GetMapping(value={"all-quick-report-nodes"})
    public List<QuickReportNode> listAllQuickReportNode() {
        return this.quickReportHelper.getAllNodes();
    }
}

