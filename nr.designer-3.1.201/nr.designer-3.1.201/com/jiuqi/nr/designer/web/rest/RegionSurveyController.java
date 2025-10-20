/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 *  com.jiuqi.nr.filterTemplate.service.IFilterTemplateService
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.survey.model.BlankQuestion
 *  com.jiuqi.nr.survey.model.ChoicesQuestion
 *  com.jiuqi.nr.survey.model.Item
 *  com.jiuqi.nr.survey.model.SurveyModel
 *  com.jiuqi.nr.survey.model.common.QuestionType
 *  com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp
 *  com.jiuqi.nr.survey.model.link.SurveyQuestion
 *  com.jiuqi.nr.survey.model.link.SurveyQuestionLink
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.RegionSurveyHelper;
import com.jiuqi.nr.designer.web.rest.param.SurveyZBTreePM;
import com.jiuqi.nr.designer.web.rest.vo.RegionSurveyParam;
import com.jiuqi.nr.designer.web.rest.vo.RegionZBTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyCheckDTO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyCheckVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyProblemHandleVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveySearchVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyTreeLocateVO;
import com.jiuqi.nr.designer.web.rest.vo.SurveyCheckItem;
import com.jiuqi.nr.designer.web.rest.vo.SurveyCheckQuestion;
import com.jiuqi.nr.designer.web.rest.vo.SurveyErrorType;
import com.jiuqi.nr.designer.web.rest.vo.SurveyLink;
import com.jiuqi.nr.designer.web.rest.vo.SurveyZBTreeNode;
import com.jiuqi.nr.designer.web.treebean.CheckRegionInfo;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.survey.model.BlankQuestion;
import com.jiuqi.nr.survey.model.ChoicesQuestion;
import com.jiuqi.nr.survey.model.Item;
import com.jiuqi.nr.survey.model.SurveyModel;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class RegionSurveyController {
    private static final Logger log = LoggerFactory.getLogger(RegionSurveyController.class);
    @Autowired
    private IDesignTimeViewController nrDesignTimeController;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    IRunTimeViewController IRunTimeViewController;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private IDesignDataSchemeService schemeService;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private RegionSurveyHelper regionSurveyHelper;
    @Autowired
    private DesignFormDefineService formDefineService;
    @Autowired
    private IFilterTemplateService filterTemplateService;

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u5361\u7247\u5f55\u5165\u67e5\u8be2")
    @RequestMapping(value={"region/survey/search"}, method={RequestMethod.POST})
    public Map<String, Object> searchRegionSurvey(@RequestBody RegionSurveyParam regionSurveyParam) throws JQException {
        try {
            String bytesToString = "";
            if (!StringUtils.isNotEmpty((String)regionSurveyParam.getHisData())) {
                DesignDataRegionDefine regionDefine = this.nrDesignTimeController.queryDataRegionDefine(regionSurveyParam.getRegionId());
                if (!DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)regionDefine.getRegionKind()) && !DataRegionKind.DATA_REGION_COLUMN_LIST.equals((Object)regionDefine.getRegionKind())) {
                    throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_231);
                }
                String title = "";
                if (DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)regionDefine.getRegionKind())) {
                    title = "\u884c\u6d6e\u52a8\u533a\u57df" + regionDefine.getRegionTop() + "-" + regionDefine.getRegionBottom();
                } else if (DataRegionKind.DATA_REGION_COLUMN_LIST.equals((Object)regionDefine.getRegionKind())) {
                    title = "\u5217\u6d6e\u52a8\u533a\u57df" + regionDefine.getRegionLeft() + "-" + regionDefine.getRegionRight();
                }
                String json = "{\"title\":\"" + title + "\",\"logoPosition\":\"right\"}";
                byte[] surveyData = DesignFormDefineBigDataUtil.StringToBytes((String)json);
                bytesToString = DesignFormDefineBigDataUtil.bytesToString((byte[])surveyData);
            } else {
                SurveyModel surveyModel = this.getSurveyModel(regionSurveyParam.getHisData());
                if (surveyModel != null) {
                    this.setRightEnumerationLink(surveyModel);
                    String style = this.regionSurveyHelper.toStyle(surveyModel);
                    byte[] surveyData = DesignFormDefineBigDataUtil.StringToBytes((String)style);
                    bytesToString = DesignFormDefineBigDataUtil.bytesToString((byte[])surveyData);
                } else {
                    bytesToString = regionSurveyParam.getHisData();
                }
            }
            HashMap<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("surveyData", bytesToString);
            resMap.put("isOpenFilepool", this.filePoolService.isOpenFilepool());
            return resMap;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_231, (Throwable)e);
        }
    }

    private void setRightEnumerationLink(SurveyModel surveyModel) throws Exception {
        List surveyQuestions = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
        List needHandleQuestions = surveyQuestions.stream().filter(surveyQuestion -> surveyQuestion.getType() == QuestionType.RADIOGROUP || surveyQuestion.getType() == QuestionType.TAGBOX || surveyQuestion.getType() == QuestionType.CHECKBOX || surveyQuestion.getType() == QuestionType.DROPDOWN).collect(Collectors.toList());
        for (SurveyQuestion surveyQuestion2 : needHandleQuestions) {
            ChoicesQuestion question = (ChoicesQuestion)surveyQuestion2.getQuestion();
            DesignDataLinkDefine dataLink = this.nrDesignTimeController.queryDataLinkDefine(question.getLinkId());
            if (StringUtils.isNotEmpty((String)dataLink.getFilterExpression())) {
                if (dataLink.getFilterExpression().equals(question.getFilterFormula())) continue;
                question.setFilterFormula(dataLink.getFilterExpression());
                continue;
            }
            if (StringUtils.isNotEmpty((String)dataLink.getFilterTemplate())) {
                FilterTemplateDTO filterTemplate = this.filterTemplateService.getFilterTemplate(dataLink.getFilterTemplate());
                if (!StringUtils.isNotEmpty((String)filterTemplate.getFilterContent()) || filterTemplate.getFilterContent().equals(question.getFilterFormula())) continue;
                question.setFilterFormula(filterTemplate.getFilterContent());
                continue;
            }
            question.setFilterFormula(" ");
        }
    }

    private SurveyModel getSurveyModel(String hisData) throws Exception {
        SurveyModel surveyModel = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            surveyModel = (SurveyModel)objectMapper.readValue(hisData, SurveyModel.class);
        }
        catch (Exception e) {
            new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_231, (Throwable)e);
        }
        return surveyModel;
    }

    private List<SurveyQuestion> readQuestion(String data) throws JQException {
        ArrayList<SurveyQuestion> allSurveyQuestion = new ArrayList();
        try {
            SurveyModel surveyModel = this.getSurveyModel(data);
            allSurveyQuestion = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
        }
        catch (Exception e) {
            new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_231, (Throwable)e);
        }
        return allSurveyQuestion;
    }

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u5361\u7247\u5f55\u5165\u6821\u9a8c")
    @RequestMapping(value={"region/survey/check"}, method={RequestMethod.POST})
    public List<ResponseSurveyCheckVO> checkRegionSurvey(@RequestBody RegionSurveyParam regionSurveyParam) throws JQException {
        ArrayList<ResponseSurveyCheckVO> resList = new ArrayList<ResponseSurveyCheckVO>();
        try {
            if (StringUtils.isNotEmpty((String)regionSurveyParam.getHisData())) {
                try {
                    List<SurveyQuestion> allSurveyQuestion = this.readQuestion(regionSurveyParam.getHisData());
                    HashMap<String, SurveyLink> linkMap = new HashMap<String, SurveyLink>();
                    if (!regionSurveyParam.getLinks().isEmpty()) {
                        for (SurveyLink link : regionSurveyParam.getLinks()) {
                            linkMap.put(link.getId(), link);
                        }
                    }
                    ArrayList<SurveyCheckQuestion> questions = new ArrayList<SurveyCheckQuestion>();
                    SurveyCheckQuestion checkQuestion = new SurveyCheckQuestion();
                    checkQuestion.setName(null);
                    checkQuestion.setType(null);
                    checkQuestion.setZb(null);
                    for (SurveyQuestion surveyQuestion : allSurveyQuestion) {
                        for (SurveyQuestionLink questionLink : surveyQuestion.getLinks()) {
                            SurveyCheckItem item = new SurveyCheckItem();
                            item.setId(surveyQuestion.getName() + "_" + questionLink.getName());
                            item.setNumber(surveyQuestion.getNumber());
                            item.setName(questionLink.getName());
                            item.setType(questionLink.getType());
                            item.setTitle(questionLink.getTitle());
                            item.setZb(questionLink.getZb());
                            SurveyErrorType errorType = null;
                            if (null == linkMap.get(questionLink.getLinkId())) {
                                errorType = SurveyErrorType.MISS_ZB;
                                item.setReadOnly(true);
                            } else if (null == questionLink.getZb() || questionLink.getZb().isEmpty()) {
                                errorType = SurveyErrorType.MISS_ZB;
                            } else {
                                List zb = questionLink.getZb();
                                DesignDataField dataField = this.getFieldByValueName(zb);
                                if (dataField == null) {
                                    errorType = SurveyErrorType.MISS_ZB;
                                    item.setReadOnly(true);
                                } else {
                                    if (StringUtils.isNotEmpty((String)((String)zb.get(0))) && !((String)zb.get(0)).equals(((SurveyLink)linkMap.get(questionLink.getLinkId())).getTableCode())) {
                                        errorType = SurveyErrorType.MISS_ZB;
                                        item.setReadOnly(true);
                                    }
                                    if (StringUtils.isNotEmpty((String)((String)zb.get(1))) && !((String)zb.get(1)).equals(((SurveyLink)linkMap.get(questionLink.getLinkId())).getFieldCode())) {
                                        errorType = SurveyErrorType.MISS_ZB;
                                        item.setReadOnly(true);
                                    }
                                }
                            }
                            if (null == errorType) continue;
                            item.setErrorType(errorType);
                            checkQuestion.addError(item);
                        }
                    }
                    if (null != checkQuestion.getItems() && !checkQuestion.getItems().isEmpty()) {
                        questions.add(checkQuestion);
                    }
                    if (null != checkQuestion.getItems() && !checkQuestion.getItems().isEmpty()) {
                        ResponseSurveyCheckVO resVo = new ResponseSurveyCheckVO();
                        resVo.setTableExist(false);
                        resVo.setTableCode(null);
                        resVo.setTableName(null);
                        resVo.setItems(checkQuestion.getItems());
                        resList.add(resVo);
                    }
                }
                catch (Exception e) {
                    log.error("\u95ee\u5377\u8868\u6837\u8f6c\u6362\u62a5\u9519\uff01", e);
                }
            }
            return resList;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_231, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u5361\u7247\u5f55\u5165\u6821\u9a8c")
    @RequestMapping(value={"region/survey/checkAll"}, method={RequestMethod.POST})
    public CheckRegionInfo checkRegionSurveyAll(@RequestBody List<RegionSurveyParam> regionSurveyParams) throws JQException {
        CheckRegionInfo resList = null;
        for (RegionSurveyParam regionSurveyParam : regionSurveyParams) {
            Object dataField;
            if (!StringUtils.isNotEmpty((String)regionSurveyParam.getHisData())) continue;
            List<SurveyQuestion> surveyQuestions = this.readQuestion(regionSurveyParam.getHisData());
            if (surveyQuestions.isEmpty()) {
                resList = new CheckRegionInfo();
                resList.setRegionId(regionSurveyParam.getRegionId());
                resList.setText("\u5361\u7247\u5f55\u5165\u4e2d\u672a\u8bbe\u7f6e\u95ee\u9898\uff0c\u8bf7\u68c0\u67e5\u3002");
            } else {
                List<ResponseSurveyCheckVO> responseSurveyCheckVOS = this.checkRegionSurvey(regionSurveyParam);
                if (!responseSurveyCheckVOS.isEmpty()) {
                    for (ResponseSurveyCheckVO responseSurveyCheckVO : responseSurveyCheckVOS) {
                        if (null == responseSurveyCheckVO.getItems() || responseSurveyCheckVO.getItems().isEmpty()) continue;
                        resList = new CheckRegionInfo();
                        resList.setRegionId(regionSurveyParam.getRegionId());
                        resList.setText("\u5361\u7247\u5f55\u5165\u4e2d\u95ee\u9898\u672a\u5173\u8054\u6307\u6807\uff0c\u8bf7\u68c0\u67e5\u3002");
                        break;
                    }
                }
            }
            DesignDataTable table = null;
            for (SurveyLink surveyLink : regionSurveyParam.getLinks()) {
                DesignDataField dataField2 = this.schemeService.getDataField(surveyLink.getExpression());
                if (null == dataField2) continue;
                table = this.schemeService.getDataTable(dataField2.getDataTableKey());
                break;
            }
            if (null == table) continue;
            ArrayList<String> fieldkeys = new ArrayList<String>();
            for (String bizKey : table.getBizKeys()) {
                dataField = this.schemeService.getDataField(bizKey);
                if (null == dataField || !DataFieldKind.TABLE_FIELD_DIM.equals((Object)dataField.getDataFieldKind())) continue;
                fieldkeys.add(dataField.getKey());
            }
            HashMap<String, String> hashMap = new HashMap<String, String>();
            for (SurveyLink link : regionSurveyParam.getLinks()) {
                if (!fieldkeys.contains(link.getExpression())) continue;
                hashMap.put(link.getId(), link.getExpression());
            }
            ArrayList<String> useLink = new ArrayList<String>();
            for (SurveyQuestion surveyQuestion : surveyQuestions) {
                dataField = surveyQuestion.getLinks().iterator();
                while (dataField.hasNext()) {
                    SurveyQuestionLink questionLink = (SurveyQuestionLink)dataField.next();
                    if (!hashMap.containsKey(questionLink.getLinkId())) continue;
                    useLink.add(questionLink.getLinkId());
                }
            }
            for (String lk : hashMap.keySet()) {
                if (useLink.contains(lk) || !StringUtils.isEmpty((String)(dataField = this.schemeService.getDataField((String)hashMap.get(lk))).getDefaultValue())) continue;
                resList = new CheckRegionInfo();
                resList.setRegionId(regionSurveyParam.getRegionId());
                resList.setText("\u8bf7\u68c0\u67e5\u5361\u7247\u5f55\u5165\u53c2\u6570\u914d\u7f6e\u3002");
            }
        }
        return resList;
    }

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u5361\u7247\u5f55\u5165\u68c0\u67e5\u5904\u7406")
    @RequestMapping(value={"region/survey/problem"}, method={RequestMethod.POST})
    public ResponseSurveyProblemHandleVO problem(@RequestBody RegionSurveyParam problemHandleVO) throws JQException {
        try {
            ResponseSurveyProblemHandleVO result = new ResponseSurveyProblemHandleVO();
            result.setSuccess(true);
            result.setMessage("\u64cd\u4f5c\u6210\u529f\uff01");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(problemHandleVO.getHisData(), SurveyModel.class);
            List surveyQuestions = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
            List<ResponseSurveyCheckDTO> checkResList = problemHandleVO.getItems();
            DesignDataTable tableDefine = null;
            List fields = new ArrayList();
            HashMap<String, SurveyLink> linkMap = new HashMap<String, SurveyLink>();
            if (!problemHandleVO.getLinks().isEmpty()) {
                for (SurveyLink link : problemHandleVO.getLinks()) {
                    linkMap.put(link.getId(), link);
                }
                String expression = problemHandleVO.getLinks().get(0).getExpression();
                DesignDataField dataField = this.schemeService.getDataField(expression);
                if (null != dataField) {
                    tableDefine = this.schemeService.getDataTable(dataField.getDataTableKey());
                    fields = this.schemeService.getDataFieldByTable(dataField.getDataTableKey());
                }
            }
            if (null == tableDefine) {
                result.setStyle(problemHandleVO.getHisData());
                result.setSuccess(true);
                result.setMessage("\u64cd\u4f5c\u6210\u529f\uff01");
                return result;
            }
            HashMap<String, DesignDataField> fieldMap = new HashMap<String, DesignDataField>();
            for (DesignDataField field : fields) {
                fieldMap.put(field.getCode(), field);
            }
            for (int i = 0; i < checkResList.size(); ++i) {
                List<SurveyCheckItem> items = checkResList.get(i).getItems();
                if (CollectionUtils.isEmpty(items)) continue;
                for (SurveyCheckItem item : items) {
                    SurveyErrorType errorType = item.getErrorType();
                    List<String> zb = item.getZb();
                    if (errorType != SurveyErrorType.MISS_ZB || zb == null) continue;
                    String tableCode = zb.get(0);
                    String fieldCode = zb.get(1);
                    if (!tableDefine.getCode().equals(tableCode) || null == linkMap.get(item.getLinkId())) continue;
                    String key = ((DesignDataField)fieldMap.get(fieldCode)).getKey();
                    String expression = ((SurveyLink)linkMap.get(item.getLinkId())).getExpression();
                    if (null == fieldMap.get(fieldCode) || !key.equals(expression)) continue;
                    zb = new ArrayList<String>();
                    zb.add(tableCode);
                    zb.add(fieldCode);
                    zb.add(((DesignDataField)fieldMap.get(fieldCode)).getTitle());
                    String name = item.getId().split("_")[0];
                    Optional<SurveyQuestion> surveyQuestionOptional = surveyQuestions.stream().filter(each -> each.getName().equals(name)).findFirst();
                    if (!surveyQuestionOptional.isPresent()) continue;
                    SurveyQuestion surveyQuestion = surveyQuestionOptional.get();
                    if (QuestionType.BLANK.equals((Object)surveyQuestion.getType())) {
                        List blankItems = ((BlankQuestion)surveyQuestion.getQuestion()).getItems();
                        surveyQuestion.setLinkId(null);
                        surveyQuestion.setZb(null);
                        for (Item link : blankItems) {
                            if (!item.getName().equals(link.getName())) continue;
                            ArrayList<String> zbList = new ArrayList<String>();
                            zbList.add(tableCode);
                            zbList.add(fieldCode);
                            zbList.add(((DesignDataField)fieldMap.get(fieldCode)).getTitle());
                            link.setZb(zbList);
                            link.setLinkId(item.getLinkId());
                        }
                        continue;
                    }
                    ArrayList<String> zbList = new ArrayList<String>();
                    zbList.add(tableCode);
                    zbList.add(fieldCode);
                    zbList.add(((DesignDataField)fieldMap.get(fieldCode)).getTitle());
                    surveyQuestion.getQuestion().setZb(zbList);
                    surveyQuestion.setTitle(tableDefine.getTitle());
                    surveyQuestion.getQuestion().setLinkId(item.getLinkId());
                }
            }
            result.setStyle(this.regionSurveyHelper.toStyle(surveyModel));
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_231, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u5361\u7247\u5f55\u5165\u6307\u6807\u6811\u5f62")
    @RequestMapping(value={"region/survey/tree"}, method={RequestMethod.POST})
    public List<ITree<RegionZBTreeNode>> getZBTreeRoot(@RequestBody RegionSurveyParam regionSurveyParam) throws JQException {
        ArrayList<ITree<RegionZBTreeNode>> nodes = new ArrayList<ITree<RegionZBTreeNode>>();
        if (null == regionSurveyParam || null == regionSurveyParam.getLinks() || regionSurveyParam.getLinks().isEmpty()) {
            return nodes;
        }
        this.buildChilderenNode(nodes, regionSurveyParam);
        return nodes;
    }

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u5361\u7247\u5f55\u5165\u6307\u6807\u6811\u5f62")
    @RequestMapping(value={"region/survey/locate"}, method={RequestMethod.POST})
    public ResponseSurveyTreeLocateVO locateZb(@RequestBody RegionSurveyParam param) {
        ResponseSurveyTreeLocateVO res = new ResponseSurveyTreeLocateVO();
        DesignDataField field = null;
        DesignDataTable table = null;
        if (StringUtils.isEmpty((String)param.getValueName())) {
            field = this.schemeService.getDataField(param.getZbKey());
            if (null != field) {
                table = this.schemeService.getDataTable(field.getDataTableKey());
            }
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

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u5361\u7247\u5f55\u5165\u6307\u6807\u6811\u5f62\u5b50\u8282\u70b9")
    @RequestMapping(value={"region/survey/treechildren"}, method={RequestMethod.POST})
    public List<ITree<SurveyZBTreeNode>> getZBTreeChildren(@RequestBody SurveyZBTreePM param) {
        return new ArrayList<ITree<SurveyZBTreeNode>>();
    }

    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u5361\u7247\u5f55\u5165\u6a21\u7cca\u641c\u7d22")
    @RequestMapping(value={"region/survey/searchzb"}, method={RequestMethod.POST})
    public List<ResponseSurveySearchVO> searchZb(@RequestBody RegionSurveyParam param) {
        ArrayList<ResponseSurveySearchVO> res = new ArrayList<ResponseSurveySearchVO>();
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
        String fuzz = param.getFuzzyKey().trim();
        HashMap<String, DesignDataTable> tableCache = new HashMap<String, DesignDataTable>();
        StringBuilder title = new StringBuilder();
        StringBuilder code = new StringBuilder();
        ArrayList<String> fieldKeys = new ArrayList<String>();
        if (!param.getLinks().isEmpty()) {
            for (SurveyLink link : param.getLinks()) {
                fieldKeys.add(link.getExpression());
            }
        }
        List fields = this.schemeService.getDataFields(fieldKeys);
        for (DesignDataField f : fields) {
            if (f.getCode().indexOf(fuzz.toUpperCase()) == -1 && f.getTitle().indexOf(fuzz.toUpperCase()) == -1 || !this.filtByQuestionType((DataField)f, param)) continue;
            DesignDataTable table = (DesignDataTable)tableCache.get(f.getDataTableKey());
            if (table == null) {
                table = this.schemeService.getDataTable(f.getDataTableKey());
                tableCache.put(f.getDataTableKey(), table);
            }
            code.setLength(0);
            code.append(table.getCode()).append(".").append(f.getCode());
            title.setLength(0);
            title.append(f.getCode()).append("|").append(f.getTitle());
            res.add(new ResponseSurveySearchVO(f.getKey(), title.toString(), code.toString(), f.getCode(), f.getTitle(), false));
        }
        return res;
    }

    public DesignDataField getFieldByValueName(List<String> zb) {
        DesignDataTable designDataTable = this.schemeService.getDataTableByCode(zb.get(0));
        if (designDataTable == null) {
            return null;
        }
        return this.schemeService.getDataFieldByTableKeyAndCode(designDataTable.getKey(), zb.get(1));
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

    private void getTableGroupParent(String tableGroup, List<String> parentKeys) {
        if (!org.springframework.util.StringUtils.hasText(tableGroup)) {
            return;
        }
        DesignDataGroup dataGroup = this.schemeService.getDataGroup(tableGroup);
        if (dataGroup != null) {
            this.getTableGroupParent(dataGroup.getParentKey(), parentKeys);
        }
        parentKeys.add(tableGroup);
    }

    private void buildChilderenNode(List<ITree<RegionZBTreeNode>> node, RegionSurveyParam param) {
        HashMap<String, String> linkMap = new HashMap<String, String>();
        String tableKey = "";
        for (SurveyLink link : param.getLinks()) {
            DesignDataField dataField;
            linkMap.put(link.getExpression(), link.getId());
            if (!StringUtils.isEmpty((String)tableKey) || null == (dataField = this.schemeService.getDataField(link.getExpression()))) continue;
            tableKey = dataField.getDataTableKey();
        }
        List zbs = this.schemeService.getDataFieldByTable(tableKey);
        if (CollectionUtils.isEmpty(zbs)) {
            return;
        }
        zbs = zbs.stream().filter(e -> null != linkMap.get(e.getKey())).collect(Collectors.toList());
        if (org.springframework.util.StringUtils.hasText(param.getQuestionType()) && !CollectionUtils.isEmpty(zbs)) {
            zbs = zbs.stream().filter(zb -> this.filtByQuestionType((DataField)zb, param)).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(zbs)) {
            return;
        }
        DesignDataTable table = this.schemeService.getDataTable(((DesignDataField)zbs.get(0)).getDataTableKey());
        for (DesignDataField zb2 : zbs) {
            ITree<RegionZBTreeNode> regionZBTreeNodeITree = this.convertFiledToTreeNode(zb2, (DataTable)table, new ArrayList<List<String>>());
            ((RegionZBTreeNode)regionZBTreeNodeITree.getData()).setLinkId((String)linkMap.get(zb2.getKey()));
            ((RegionZBTreeNode)regionZBTreeNodeITree.getData()).setNodeType(NodeType.FIELD_ZB);
            node.add(regionZBTreeNodeITree);
        }
    }

    private void buildZB(String parentKey, RegionSurveyParam param, List<ITree<RegionZBTreeNode>> tableChilds) {
        List zbs = this.schemeService.getDataFieldByTableKeyAndKind(parentKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM});
        if (org.springframework.util.StringUtils.hasText(param.getQuestionType()) && !CollectionUtils.isEmpty(zbs)) {
            zbs = zbs.stream().filter(zb -> this.filtByQuestionType((DataField)zb, param)).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(zbs)) {
            return;
        }
        DesignDataTable table = this.schemeService.getDataTable(((DesignDataField)zbs.get(0)).getDataTableKey());
        List<SurveyLink> links = param.getLinks();
        HashMap<String, String> collect = new HashMap<String, String>();
        for (SurveyLink link : links) {
            collect.put(link.getExpression(), link.getId());
        }
        ArrayList<DesignDataField> asLinkField = new ArrayList<DesignDataField>();
        for (DesignDataField zb2 : zbs) {
            if (null == collect.get(zb2.getKey())) continue;
            asLinkField.add(zb2);
        }
        if (CollectionUtils.isEmpty(asLinkField)) {
            return;
        }
        tableChilds.addAll(asLinkField.stream().map(arg_0 -> this.lambda$buildZB$5((DataTable)table, collect, arg_0)).collect(Collectors.toList()));
    }

    private ITree<RegionZBTreeNode> convertFiledToTreeNode(DesignDataField zb, DataTable table, List<List<String>> disabledZbs) {
        RegionZBTreeNode r = new RegionZBTreeNode();
        r.setKey(zb.getKey());
        r.setTitle(zb.getCode() + " | " + zb.getTitle());
        r.setZbCode(zb.getCode());
        r.setNodeType(this.convertDataFieldKindToNodeType(zb.getDataFieldKind()));
        zb.setDataFieldKind(DataFieldKind.FIELD_ZB);
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

    private boolean filtByQuestionType(DataField zb, RegionSurveyParam param) {
        String questionType = param.getQuestionType();
        if (!org.springframework.util.StringUtils.hasText(questionType)) {
            return true;
        }
        switch (questionType.toLowerCase()) {
            case "blank": {
                log.error("::\u95ee\u5377\u540e\u7aeffiltByQuestionType::\u4e0d\u652f\u6301\u586b\u7a7a\u9898\u8fc7\u6ee4\u6307\u6807");
                break;
            }
            case "number": {
                return zb.getDataFieldType() == DataFieldType.INTEGER || zb.getDataFieldType() == DataFieldType.BIGDECIMAL;
            }
            case "period": {
                return zb.getDataFieldType() == DataFieldType.DATE || zb.getDataFieldType() == DataFieldType.DATE_TIME;
            }
            case "text": {
                return zb.getDataFieldType() == DataFieldType.STRING && !org.springframework.util.StringUtils.hasText(zb.getRefDataEntityKey());
            }
            case "comment": {
                return (zb.getDataFieldType() == DataFieldType.STRING || zb.getDataFieldType() == DataFieldType.CLOB) && !org.springframework.util.StringUtils.hasText(zb.getRefDataEntityKey());
            }
            case "boolean": {
                return zb.getDataFieldType() == DataFieldType.BOOLEAN;
            }
            case "radiogroup": 
            case "dropdown": {
                if (zb.getDataFieldType() == DataFieldType.STRING && org.springframework.util.StringUtils.hasText(zb.getRefDataEntityKey())) {
                    return zb.getAllowMultipleSelect() == false && this.canDispaly(zb.getRefDataEntityKey());
                }
                return false;
            }
            case "checkbox": 
            case "tagbox": {
                if (zb.getDataFieldType() == DataFieldType.STRING && org.springframework.util.StringUtils.hasText(zb.getRefDataEntityKey())) {
                    return zb.getAllowMultipleSelect() != false && this.canDispaly(zb.getRefDataEntityKey());
                }
                return false;
            }
            case "file": 
            case "filepool": {
                return zb.getDataFieldType() == DataFieldType.FILE;
            }
            case "image": 
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
                return baseDataDefineDO.getStructtype() < 2;
            }
        }
        return false;
    }

    private void buildTable(List<DesignDataTable> tables, List<ITree<RegionZBTreeNode>> children, RegionSurveyParam param, Set<String> tableKeys) {
        if (!CollectionUtils.isEmpty(tables)) {
            for (DesignDataTable t : tables) {
                NodeType tType;
                if (!tableKeys.contains(t.getKey())) continue;
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
                ITree<RegionZBTreeNode> node = this.convertBasicToTreeNode((Basic)t, tType);
                children.add(node);
                ArrayList<ITree<RegionZBTreeNode>> tableChilds = new ArrayList<ITree<RegionZBTreeNode>>();
                node.setChildren(tableChilds);
                this.buildZB(t.getKey(), param, tableChilds);
            }
        }
    }

    private ITree<RegionZBTreeNode> convertBasicToTreeNode(Basic b, NodeType type) {
        RegionZBTreeNode r = new RegionZBTreeNode();
        r.setKey(b.getKey());
        r.setTitle(b.getTitle());
        r.setNodeType(type);
        r.setCode(b.getCode());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)type)});
        node.setLeaf(false);
        return node;
    }

    private /* synthetic */ ITree lambda$buildZB$5(DataTable table, Map collect, DesignDataField zb) {
        ITree<RegionZBTreeNode> regionZBTreeNodeITree = this.convertFiledToTreeNode(zb, table, new ArrayList<List<String>>());
        ((RegionZBTreeNode)regionZBTreeNodeITree.getData()).setLinkId((String)collect.get(zb.getKey()));
        ((RegionZBTreeNode)regionZBTreeNodeITree.getData()).setNodeType(NodeType.FIELD_ZB);
        return regionZBTreeNodeITree;
    }
}

