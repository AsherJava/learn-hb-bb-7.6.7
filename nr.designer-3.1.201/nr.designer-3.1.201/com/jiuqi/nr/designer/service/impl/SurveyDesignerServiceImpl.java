/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.survey.model.Choice
 *  com.jiuqi.nr.survey.model.ChoicesByUrl
 *  com.jiuqi.nr.survey.model.Element
 *  com.jiuqi.nr.survey.model.SurveyModel
 *  com.jiuqi.nr.survey.model.ValueBean
 *  com.jiuqi.nr.survey.model.common.QuestionType
 *  com.jiuqi.nr.survey.model.define.IChoicesQuestion
 *  com.jiuqi.nr.survey.model.define.INumberQuestion
 *  com.jiuqi.nr.survey.model.define.IPeriodQuestion
 *  com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp
 *  com.jiuqi.nr.survey.model.link.SurveyQuestion
 *  com.jiuqi.nr.survey.model.link.SurveyQuestionLink
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.designer.service.ISurveyDesignerService;
import com.jiuqi.nr.designer.web.rest.vo.RequestSurveyProblemHandleVO;
import com.jiuqi.nr.designer.web.rest.vo.RequestSurveySaveVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyCheckDTO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyCheckVO;
import com.jiuqi.nr.designer.web.rest.vo.ResponseSurveyProblemHandleVO;
import com.jiuqi.nr.designer.web.rest.vo.SurveyCheckItem;
import com.jiuqi.nr.designer.web.rest.vo.SurveyCheckQuestion;
import com.jiuqi.nr.designer.web.rest.vo.SurveyDataFieldVO;
import com.jiuqi.nr.designer.web.rest.vo.SurveyErrorType;
import com.jiuqi.nr.designer.web.rest.vo.SurveyProblemHandleVO;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.survey.model.Choice;
import com.jiuqi.nr.survey.model.ChoicesByUrl;
import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.SurveyModel;
import com.jiuqi.nr.survey.model.ValueBean;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import com.jiuqi.nr.survey.model.define.INumberQuestion;
import com.jiuqi.nr.survey.model.define.IPeriodQuestion;
import com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class SurveyDesignerServiceImpl
implements ISurveyDesignerService {
    private static final Logger log = LoggerFactory.getLogger(SurveyDesignerServiceImpl.class);
    private static final String SUFFIX = "";
    private static final String BASE = "@BASE";
    private static final String SURVEYTITLE = "\u95ee\u5377\u6307\u6807\u8868";
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private IEntityViewRunTimeController viewRunTimeController;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private DesignFormDefineService formService;

    @Override
    public List<SurveyQuestion> saveOrUpdateLinkId(RequestSurveySaveVO requestSurveySaveVo) {
        List links;
        String style = requestSurveySaveVo.getStyle();
        byte[] stringToBytes = DesignFormDefineBigDataUtil.StringToBytes((String)style);
        this.designTimeViewController.setSurveyData(requestSurveySaveVo.getFormId(), stringToBytes);
        String formId = requestSurveySaveVo.getFormId();
        List regions = this.designTimeViewController.getAllRegionsInForm(formId);
        HashMap<String, DesignDataLinkDefine> dataLinkMap = new HashMap<String, DesignDataLinkDefine>();
        String fixReginKey = SUFFIX;
        for (DesignDataRegionDefine region : regions) {
            if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) continue;
            fixReginKey = region.getKey();
            List links2 = this.designTimeViewController.getAllLinksInRegion(region.getKey());
            for (DesignDataLinkDefine link : links2) {
                dataLinkMap.put(link.getKey(), link);
            }
        }
        ArrayList<SurveyQuestionLink> nowlinks = new ArrayList<SurveyQuestionLink>();
        ArrayList<SurveyQuestion> floatQuestions = new ArrayList<SurveyQuestion>();
        List allSurveyQuestion = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)requestSurveySaveVo.get_surveyModel());
        for (Object surveyQuestion : allSurveyQuestion) {
            if (surveyQuestion.getType().equals((Object)QuestionType.MATRIXDYNAMIC) || surveyQuestion.getType().equals((Object)QuestionType.MATRIXFLOAT) || surveyQuestion.getType().equals((Object)QuestionType.PANELDYNAMIC)) {
                floatQuestions.add((SurveyQuestion)surveyQuestion);
                continue;
            }
            List links3 = surveyQuestion.getLinks();
            for (SurveyQuestionLink questionLink : links3) {
                if (null == questionLink.getZb() || questionLink.getZb().isEmpty()) continue;
                nowlinks.add(questionLink);
            }
        }
        this.updateLink(dataLinkMap, fixReginKey, nowlinks);
        ArrayList<String> floatReginKeys = new ArrayList<String>();
        if (!floatQuestions.isEmpty()) {
            for (SurveyQuestion surveyQuestion : floatQuestions) {
                ArrayList<SurveyQuestionLink> floatNowlinks = new ArrayList<SurveyQuestionLink>();
                links = surveyQuestion.getLinks();
                String linkId = surveyQuestion.getLinkId();
                String reginKey = SUFFIX;
                if (linkId != null && linkId.length() != 0) {
                    reginKey = linkId;
                }
                if (links != null) {
                    for (SurveyQuestionLink questionLink : links) {
                        if (null == questionLink.getZb() || questionLink.getZb().isEmpty()) continue;
                        floatNowlinks.add(questionLink);
                    }
                }
                if (!StringUtils.hasLength(reginKey) || floatNowlinks.isEmpty()) continue;
                DesignDataRegionDefine floatRegionDefine = this.designTimeViewController.queryDataRegionDefine(reginKey);
                if (null == floatRegionDefine) {
                    floatRegionDefine = this.designTimeViewController.createDataRegionDefine();
                    floatRegionDefine.setFormKey(formId);
                    floatRegionDefine.setKey(reginKey);
                    floatRegionDefine.setRegionKind(DataRegionKind.DATA_REGION_ROW_LIST);
                    this.designTimeViewController.insertDataRegionDefine(floatRegionDefine);
                }
                List floatLinks = this.designTimeViewController.getAllLinksInRegion(floatRegionDefine.getKey());
                HashMap<String, DesignDataLinkDefine> floatDataLinkMap = new HashMap<String, DesignDataLinkDefine>();
                for (DesignDataLinkDefine link : floatLinks) {
                    floatDataLinkMap.put(link.getKey(), link);
                }
                this.updateLink(floatDataLinkMap, reginKey, floatNowlinks);
                floatReginKeys.add(reginKey);
            }
        }
        regions = this.designTimeViewController.getAllRegionsInForm(formId);
        Iterator iterator = regions.iterator();
        while (iterator.hasNext()) {
            DesignDataRegionDefine region = (DesignDataRegionDefine)iterator.next();
            if (fixReginKey.equals(region.getKey()) || floatReginKeys.contains(region.getKey())) continue;
            try {
                List links4 = this.designTimeViewController.getAllLinksInRegion(region.getKey());
                if (!links4.isEmpty()) {
                    List<String> collect = links4.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                    this.designTimeViewController.deleteDataLinkDefines(collect.toArray(new String[collect.size()]));
                }
                this.designTimeViewController.deleteDataRegionDefine(region.getKey());
            }
            catch (Exception e) {
                log.error("\u95ee\u5377\u4fdd\u5b58\u5f02\u5e38", e);
            }
            iterator.remove();
        }
        for (int r = 0; r < regions.size(); ++r) {
            DesignDataRegionDefine region = (DesignDataRegionDefine)regions.get(r);
            links = this.designTimeViewController.getAllLinksInRegion(region.getKey());
            for (int i = 0; i < links.size(); ++i) {
                DesignDataLinkDefine link = (DesignDataLinkDefine)links.get(i);
                link.setPosX(i + 1);
                link.setPosY(r + 1);
                this.designTimeViewController.updateDataLinkDefine(link);
            }
            region.setRegionTop(r + 1);
            region.setRegionBottom(r + 2);
            region.setRegionLeft(0);
            region.setRegionRight(links.size() + 2);
            this.designTimeViewController.updateDataRegionDefine(region);
        }
        this.formService.updateFormTime(formId);
        return allSurveyQuestion;
    }

    private void updateLink(Map<String, DesignDataLinkDefine> dataLinkMap, String fixReginKey, List<SurveyQuestionLink> nowlinks) {
        Set<String> keySet;
        ArrayList<SurveyQuestionLink> addlinks = new ArrayList<SurveyQuestionLink>();
        ArrayList<SurveyQuestionLink> updatelinks = new ArrayList<SurveyQuestionLink>();
        for (SurveyQuestionLink surveyQuestionLink : nowlinks) {
            if (dataLinkMap.containsKey(surveyQuestionLink.getLinkId())) {
                dataLinkMap.remove(surveyQuestionLink.getLinkId());
                updatelinks.add(surveyQuestionLink);
                continue;
            }
            addlinks.add(surveyQuestionLink);
        }
        if (!addlinks.isEmpty()) {
            ArrayList list = new ArrayList();
            for (SurveyQuestionLink surveyQuestionLink : addlinks) {
                String filterFormula;
                DesignDataLinkDefineImpl dataLink = new DesignDataLinkDefineImpl();
                dataLink.setKey(surveyQuestionLink.getLinkId());
                dataLink.setUniqueCode(OrderGenerator.newOrder());
                dataLink.setRegionKey(fixReginKey);
                dataLink.setAllowNotLeafNodeRefer(true);
                DesignDataField dataField = this.getFieldByValueName(surveyQuestionLink.getZb());
                if (null != dataField) {
                    dataLink.setLinkExpression(dataField.getKey());
                    list.add(dataLink);
                }
                if (!StringUtils.hasLength(filterFormula = surveyQuestionLink.getFilterFormula())) continue;
                dataLink.setFilterExpression(filterFormula);
            }
            this.designTimeViewController.insertDataLinkDefines(list.toArray(new DesignDataLinkDefine[list.size()]));
        }
        if (!updatelinks.isEmpty()) {
            for (SurveyQuestionLink surveyQuestionLink : updatelinks) {
                DesignDataLinkDefine designDataLinkDefine = this.designTimeViewController.queryDataLinkDefine(surveyQuestionLink.getLinkId());
                DesignDataField dataField = this.getFieldByValueName(surveyQuestionLink.getZb());
                String filterFormula = surveyQuestionLink.getFilterFormula();
                designDataLinkDefine.setFilterExpression(filterFormula);
                if (null != dataField) {
                    designDataLinkDefine.setAllowNotLeafNodeRefer(true);
                    designDataLinkDefine.setLinkExpression(dataField.getKey());
                    this.designTimeViewController.updateDataLinkDefine(designDataLinkDefine);
                    continue;
                }
                dataLinkMap.put(surveyQuestionLink.getLinkId(), designDataLinkDefine);
            }
        }
        if (null != (keySet = dataLinkMap.keySet()) && !keySet.isEmpty()) {
            this.designTimeViewController.deleteDataLinkDefines(keySet.toArray(new String[keySet.size()]));
        }
    }

    @Override
    public DesignDataField getFieldByValueName(List<String> zb) {
        DesignDataTable designDataTable = this.designDataSchemeService.getDataTableByCode(zb.get(0));
        if (designDataTable == null) {
            return null;
        }
        return this.designDataSchemeService.getDataFieldByTableKeyAndCode(designDataTable.getKey(), zb.get(1));
    }

    @Override
    public List<ResponseSurveyCheckVO> checkSurveyModel(RequestSurveySaveVO requestSurveySaveVo) {
        ArrayList<ResponseSurveyCheckVO> resList = new ArrayList<ResponseSurveyCheckVO>();
        ResponseSurveyCheckVO surveyCheckVo = new ResponseSurveyCheckVO();
        ArrayList<SurveyCheckQuestion> questions = new ArrayList<SurveyCheckQuestion>();
        List<SurveyQuestion> surveyLinks = this.saveOrUpdateLinkId(requestSurveySaveVo);
        HashMap existZBIdMap = new HashMap();
        if (surveyLinks == null) {
            return resList;
        }
        for (SurveyQuestion surveyQuestion : surveyLinks) {
            SurveyCheckQuestion checkQuestion = new SurveyCheckQuestion();
            checkQuestion.setName(surveyQuestion.getName());
            checkQuestion.setType(surveyQuestion.getType());
            checkQuestion.setZb(surveyQuestion.getZb());
            List links = surveyQuestion.getLinks();
            if (links == null) continue;
            for (SurveyQuestionLink questionLink : links) {
                if (checkQuestion.getZb() != null && questionLink.getZb() != null) {
                    existZBIdMap.put(questionLink.getZb().get(1), true);
                }
                SurveyCheckItem item = new SurveyCheckItem();
                item.setId(surveyQuestion.getName() + "_" + questionLink.getName());
                item.setNumber(surveyQuestion.getNumber());
                item.setName(questionLink.getName());
                item.setType(questionLink.getType());
                item.setTitle(questionLink.getTitle());
                if (questionLink.isMatrix() && surveyQuestion.getType() != QuestionType.PANELDYNAMIC && surveyQuestion.getType() != QuestionType.MATRIXDYNAMIC) {
                    item.setReadOnly(true);
                } else {
                    item.setReadOnly(false);
                }
                item.setZb(questionLink.getZb());
                ValueBean obj = questionLink.getQuestion();
                item.setObj(obj);
                if (obj instanceof IChoicesQuestion) {
                    IChoicesQuestion question = (IChoicesQuestion)obj;
                    item.setChoices(question.getChoices());
                }
                SurveyErrorType errorType = null;
                if (null == questionLink.getZb() || questionLink.getZb().isEmpty()) {
                    errorType = SurveyErrorType.MISS_ZB;
                } else {
                    List zb = questionLink.getZb();
                    DesignDataField dataField = this.getFieldByValueName(zb);
                    if (dataField == null) {
                        errorType = SurveyErrorType.DELETE_ZB;
                        item.setReadOnly(true);
                    }
                }
                QuestionType type = questionLink.getType();
                List zb = questionLink.getZb();
                if (null == errorType) {
                    DesignDataField dataField = this.getFieldByValueName(zb);
                    DataFieldType dataFieldType = dataField.getDataFieldType();
                    SurveyDataFieldVO dataFieldV = new SurveyDataFieldVO();
                    BeanUtils.copyProperties(dataField, dataFieldV);
                    item.setDataField(dataFieldV);
                    switch (dataFieldType) {
                        case STRING: {
                            if (type != QuestionType.TEXT && type != QuestionType.COMMENT && type != QuestionType.RADIOGROUP && type != QuestionType.CHECKBOX && type != QuestionType.DROPDOWN && type != QuestionType.TAGBOX) {
                                errorType = SurveyErrorType.MIS_MATCH;
                                item.setReadOnly(true);
                                break;
                            }
                            if (type != QuestionType.TEXT && type != QuestionType.COMMENT) {
                                String refDataEntityKey = dataField.getRefDataEntityKey();
                                if (!StringUtils.hasLength(refDataEntityKey)) {
                                    errorType = SurveyErrorType.NO_ENUM;
                                } else {
                                    List<Choice> noReadOnly;
                                    List<Choice> choices = item.getChoices();
                                    if (null != choices && !choices.isEmpty() && !(noReadOnly = choices.stream().filter(e -> !e.isReadOnly()).collect(Collectors.toList())).isEmpty()) {
                                        errorType = SurveyErrorType.ADD_ENUM;
                                        item.setChoices(noReadOnly);
                                    }
                                }
                            }
                            if (!(obj instanceof Element)) break;
                            Element questionE = (Element)obj;
                            Integer precision = dataField.getPrecision();
                            int maxLength = questionE.getMaxLength();
                            if (0 == maxLength || null == precision) break;
                            try {
                                if (maxLength <= precision) break;
                                errorType = SurveyErrorType.LENGTH_RANGE;
                                item.setReadOnly(true);
                            }
                            catch (Exception e2) {
                                log.debug("\u89e3\u6790\u6570\u503c\u95ee\u9898\u6700\u5927\u957f\u5ea6\u62a5\u9519", e2);
                            }
                            break;
                        }
                        case BOOLEAN: {
                            if (type == QuestionType.BOOLEAN) break;
                            errorType = SurveyErrorType.MIS_MATCH;
                            item.setReadOnly(true);
                            break;
                        }
                        case DATE_TIME: 
                        case DATE: {
                            if (type == QuestionType.PERIOD) break;
                            errorType = SurveyErrorType.MIS_MATCH;
                            item.setReadOnly(true);
                            break;
                        }
                        case INTEGER: 
                        case BIGDECIMAL: {
                            if (type != QuestionType.NUMBER) {
                                errorType = SurveyErrorType.MIS_MATCH;
                                item.setReadOnly(true);
                                break;
                            }
                            if (!(obj instanceof INumberQuestion)) break;
                            INumberQuestion numberQuestion = (INumberQuestion)obj;
                            Integer zbDecimal = dataField.getDecimal();
                            int decimal = numberQuestion.getDecimal();
                            if (zbDecimal == null) {
                                zbDecimal = 0;
                            }
                            if (decimal > zbDecimal) {
                                errorType = SurveyErrorType.DECIMAL_RANGE;
                                item.setReadOnly(true);
                            }
                            Integer precision = dataField.getPrecision();
                            int maxLength = numberQuestion.getMaxLength();
                            if (0 == maxLength || null == precision) break;
                            try {
                                if (maxLength <= precision) break;
                                errorType = SurveyErrorType.LENGTH_RANGE;
                                item.setReadOnly(true);
                            }
                            catch (Exception e3) {
                                log.debug("\u89e3\u6790\u6570\u503c\u95ee\u9898\u6700\u5927\u957f\u5ea6\u62a5\u9519", e3);
                            }
                            break;
                        }
                        case PICTURE: {
                            if (type == QuestionType.PICTURE) break;
                            errorType = SurveyErrorType.MIS_MATCH;
                            item.setReadOnly(true);
                            break;
                        }
                        case FILE: {
                            if (type == QuestionType.FILE || type == QuestionType.FILEPOOL) break;
                            errorType = SurveyErrorType.MIS_MATCH;
                            item.setReadOnly(true);
                            break;
                        }
                    }
                }
                if (null == errorType) continue;
                item.setErrorType(errorType);
                checkQuestion.addError(item);
            }
            if (null == checkQuestion.getItems() || checkQuestion.getItems().isEmpty()) continue;
            questions.add(checkQuestion);
        }
        if (null != questions && !questions.isEmpty()) {
            ArrayList<SurveyCheckItem> fixZBItems = new ArrayList<SurveyCheckItem>();
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(requestSurveySaveVo.getDataSchemeId());
            String prefix = dataScheme.getPrefix();
            DesignFormDefine formDefine = this.designTimeViewController.querySoftFormDefine(requestSurveySaveVo.getFormId());
            String formCode = formDefine.getFormCode();
            String tableCode = SUFFIX;
            tableCode = StringUtils.hasLength(prefix) ? prefix + "_" + formCode + SUFFIX : formCode + SUFFIX;
            String tableTitle = formDefine.getTitle();
            DesignDataTable dataTable = this.designDataSchemeService.getDataTableByCode(tableCode);
            int begin = 0;
            if (null != dataTable) {
                tableTitle = dataTable.getTitle();
                List dataFieldByTableCode = this.designDataSchemeService.getDataFieldByTableCode(dataTable.getCode());
                if (null != dataFieldByTableCode) {
                    for (DesignDataField designDataField : dataFieldByTableCode) {
                        String code = designDataField.getCode();
                        if (!code.contains("_")) continue;
                        String replaceAll = code.replace(formCode + "_", SUFFIX);
                        try {
                            int num = Integer.parseInt(replaceAll);
                            if (num <= begin) continue;
                            begin = num;
                        }
                        catch (Exception e4) {
                            log.debug("\u81ea\u52a8\u751f\u6210\u6307\u6807\u540d\u79f0\u62a5\u9519", e4);
                        }
                    }
                }
            }
            surveyCheckVo.setTableCode(tableCode);
            surveyCheckVo.setTableName(tableTitle);
            int tableNo = 1;
            for (SurveyCheckQuestion checkQuestion : questions) {
                List<SurveyCheckItem> items = checkQuestion.getItems();
                if (QuestionType.PANELDYNAMIC == checkQuestion.getType() || QuestionType.MATRIXDYNAMIC == checkQuestion.getType() || QuestionType.MATRIXFLOAT == checkQuestion.getType()) {
                    ResponseSurveyCheckVO resVo = new ResponseSurveyCheckVO();
                    if (checkQuestion.getZb() != null) {
                        resVo.setTableCode(checkQuestion.getZb().get(0));
                        resVo.setTableName(checkQuestion.getZb().get(2));
                        resVo.setTableExist(true);
                        ArrayList<DesignDataField> candidateZBList = new ArrayList<DesignDataField>();
                        List allZBList = this.designDataSchemeService.getDataFieldByTableCodeAndKind(resVo.getTableCode(), new DataFieldKind[]{DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM});
                        for (DesignDataField each : allZBList) {
                            if (existZBIdMap.getOrDefault(each.getCode(), false).booleanValue()) continue;
                            candidateZBList.add(each);
                        }
                        resVo.setCandidateZBList(candidateZBList);
                    } else {
                        DesignDataTable newTable = null;
                        do {
                            resVo.setTableCode(tableCode + "_" + tableNo);
                            resVo.setTableName(tableTitle + "_" + tableNo);
                            ++tableNo;
                        } while ((newTable = this.designDataSchemeService.getDataTableByCode(resVo.getTableCode())) != null);
                    }
                    resVo.setItems(items);
                    if (resList.isEmpty()) {
                        surveyCheckVo.setItems(fixZBItems);
                        surveyCheckVo.setTableExist(this.designDataSchemeService.getDataTableByCode(surveyCheckVo.getTableCode()) != null);
                        resList.add(surveyCheckVo);
                    }
                    resList.add(resVo);
                } else {
                    fixZBItems.addAll(items);
                }
                for (SurveyCheckItem surveyCheckItem : items) {
                    BaseDataDefineDTO baseDataDefine;
                    SurveyErrorType errorType = surveyCheckItem.getErrorType();
                    String zbCode = SUFFIX;
                    String zbTitle = SUFFIX;
                    if (errorType == SurveyErrorType.MISS_ZB) {
                        SurveyDataFieldVO dataField = new SurveyDataFieldVO();
                        dataField.setDataFieldKind(DataFieldKind.FIELD_ZB);
                        dataField.setTitle(StringUtils.hasLength(surveyCheckItem.getTitle()) ? surveyCheckItem.getTitle() : surveyCheckItem.getName());
                        dataField.setNullable(true);
                        zbTitle = dataField.getTitle();
                        String codeNum = SUFFIX;
                        codeNum = ++begin < 10 ? formCode + "_00" + begin : (begin < 100 ? formCode + "_0" + begin : formCode + "_" + begin);
                        dataField.setCode(codeNum);
                        zbCode = codeNum;
                        QuestionType type = surveyCheckItem.getType();
                        ValueBean obj = surveyCheckItem.getObj();
                        switch (type) {
                            case NUMBER: {
                                int decimalPoint = -1;
                                int precision = 18;
                                if (obj instanceof INumberQuestion) {
                                    INumberQuestion numberQuestion = (INumberQuestion)obj;
                                    decimalPoint = numberQuestion.getDecimal();
                                    precision = numberQuestion.getMaxLength();
                                    if (0 == precision) {
                                        precision = -1;
                                    }
                                }
                                dataField.setDimension(1);
                                dataField.setMeasureUnit("YUAN");
                                dataField.setDataFieldType(DataFieldType.BIGDECIMAL);
                                dataField.setDecimal(decimalPoint > -1 ? decimalPoint : 2);
                                dataField.setPrecision(precision > -1 ? precision : 18);
                                dataField.setDataFieldGatherType(DataFieldGatherType.SUM);
                                break;
                            }
                            case PERIOD: {
                                IPeriodQuestion period;
                                DataFieldType dataFieldType = DataFieldType.DATE;
                                if (obj instanceof IPeriodQuestion && "datetime".equals((period = (IPeriodQuestion)obj).getDatetype())) {
                                    dataFieldType = DataFieldType.DATE_TIME;
                                }
                                dataField.setDataFieldType(dataFieldType);
                                dataField.setDimension(-1);
                                dataField.setDataFieldGatherType(DataFieldGatherType.NONE);
                                break;
                            }
                            case BOOLEAN: {
                                dataField.setDataFieldType(DataFieldType.BOOLEAN);
                                dataField.setDimension(-1);
                                dataField.setDataFieldGatherType(DataFieldGatherType.NONE);
                                break;
                            }
                            case TAGBOX: 
                            case CHECKBOX: {
                                dataField.setDataFieldType(DataFieldType.STRING);
                                dataField.setDimension(-1);
                                dataField.setPrecision(50);
                                dataField.setDataFieldGatherType(DataFieldGatherType.NONE);
                                dataField.setAllowMultipleSelect(true);
                                errorType = SurveyErrorType.NO_ENUM;
                                break;
                            }
                            case COMMENT: 
                            case TEXT: 
                            case RADIOGROUP: 
                            case DROPDOWN: {
                                dataField.setDataFieldType(DataFieldType.STRING);
                                dataField.setDimension(-1);
                                dataField.setPrecision(type == QuestionType.COMMENT ? 2000 : 50);
                                dataField.setDataFieldGatherType(DataFieldGatherType.NONE);
                                if (type != QuestionType.RADIOGROUP && type != QuestionType.DROPDOWN) break;
                                errorType = SurveyErrorType.NO_ENUM;
                                break;
                            }
                            case FILE: 
                            case FILEPOOL: {
                                dataField.setDataFieldType(DataFieldType.FILE);
                                dataField.setDimension(-1);
                                dataField.setDataFieldGatherType(DataFieldGatherType.NONE);
                                break;
                            }
                            case PICTURE: {
                                dataField.setDataFieldType(DataFieldType.PICTURE);
                                dataField.setDimension(-1);
                                dataField.setDataFieldGatherType(DataFieldGatherType.NONE);
                                break;
                            }
                        }
                        surveyCheckItem.setDataField(dataField);
                    }
                    if (errorType == SurveyErrorType.NO_ENUM) {
                        ValueBean questionEl = surveyCheckItem.getObj();
                        if (!StringUtils.hasLength(zbCode)) {
                            zbCode = (String)questionEl.getZb().get(1);
                            zbTitle = (String)questionEl.getZb().get(2);
                        }
                        String baseDataName = "MD_" + zbCode;
                        int start = 0;
                        while (true) {
                            BaseDataDefineDTO paramDefine = new BaseDataDefineDTO();
                            paramDefine.setName(baseDataName);
                            BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(paramDefine);
                            if (null == baseDataDefineDO) break;
                            baseDataName = baseDataName + "_" + start;
                            ++start;
                        }
                        BaseDataDefineDTO baseDataDefine2 = new BaseDataDefineDTO();
                        baseDataDefine2.setName(baseDataName);
                        baseDataDefine2.setAuthflag(Integer.valueOf(0));
                        baseDataDefine2.setDesignname("-");
                        baseDataDefine2.setDummyflag(Integer.valueOf(0));
                        baseDataDefine2.setGroupname("NR_GROUP");
                        baseDataDefine2.setInspecttitle(Integer.valueOf(0));
                        baseDataDefine2.setSharetype(Integer.valueOf(0));
                        baseDataDefine2.setStructtype(Integer.valueOf(0));
                        baseDataDefine2.setTitle(zbTitle);
                        baseDataDefine2.setVersionflag(Integer.valueOf(0));
                        surveyCheckItem.setBaseDataDefine(baseDataDefine2);
                    }
                    if (errorType != SurveyErrorType.ADD_ENUM || null != (baseDataDefine = surveyCheckItem.getBaseDataDefine())) continue;
                    baseDataDefine = new BaseDataDefineDTO();
                    ValueBean questionEl = surveyCheckItem.getObj();
                    DesignDataField dataField = this.getFieldByValueName(questionEl.getZb());
                    String refDataEntityKey = dataField.getRefDataEntityKey();
                    String baseDataName = refDataEntityKey.replace(BASE, SUFFIX);
                    BaseDataDefineDTO paramDefine = new BaseDataDefineDTO();
                    paramDefine.setName(baseDataName);
                    BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(paramDefine);
                    BeanUtils.copyProperties(baseDataDefineDO, baseDataDefine);
                    surveyCheckItem.setBaseDataDefine(baseDataDefine);
                }
            }
            if (!fixZBItems.isEmpty() && resList.isEmpty()) {
                surveyCheckVo.setItems(fixZBItems);
                surveyCheckVo.setTableExist(this.designDataSchemeService.getDataTableByCode(surveyCheckVo.getTableCode()) != null);
                resList.add(surveyCheckVo);
            }
        }
        return resList;
    }

    @Override
    public ResponseSurveyProblemHandleVO problemHandle(RequestSurveyProblemHandleVO problemHandleVO) {
        ResponseSurveyProblemHandleVO result = new ResponseSurveyProblemHandleVO();
        result.setSuccess(true);
        result.setMessage("\u64cd\u4f5c\u6210\u529f\uff01");
        String formId = problemHandleVO.getFormId();
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(problemHandleVO.getDataSchemeId());
        String prefix = dataScheme.getPrefix();
        List surveyQuestions = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)problemHandleVO.get_surveyModel());
        List<ResponseSurveyCheckDTO> checkResList = problemHandleVO.getItems();
        DesignFormDefine formDefine = this.designTimeViewController.querySoftFormDefine(formId);
        for (int i = 0; i < checkResList.size(); ++i) {
            List<SurveyCheckItem> items = checkResList.get(i).getItems();
            DataTableType dataTableType = DataTableType.TABLE;
            DesignFormDefineImpl formDefineParam = new DesignFormDefineImpl();
            BeanUtils.copyProperties(formDefine, formDefineParam);
            if (i != 0) {
                dataTableType = DataTableType.DETAIL;
                formDefineParam.setFormCode(checkResList.get(i).getTableCode());
            }
            if (CollectionUtils.isEmpty(items)) continue;
            for (SurveyCheckItem item : items) {
                SurveyErrorType errorType = item.getErrorType();
                boolean readOnly = item.isReadOnly();
                if (!readOnly) {
                    String entityKey;
                    QuestionType questionType = item.getType();
                    List<String> zb = item.getZb();
                    DesignDataField dataField = null;
                    if (errorType == SurveyErrorType.MISS_ZB) {
                        if (null != zb && !zb.isEmpty()) {
                            this.setQuesionValueName(surveyQuestions, item, zb, null);
                        } else {
                            DesignDataField old;
                            DesignDataTable dataTable = this.getDesignDataTable(problemHandleVO, prefix, (DesignFormDefine)formDefineParam, dataTableType);
                            SurveyDataFieldVO dataFieldVO = item.getDataField();
                            dataField = this.designDataSchemeService.initDataField();
                            BeanUtils.copyProperties(dataFieldVO, dataField);
                            dataField.setDataFieldApplyType(dataFieldVO.getApplyType());
                            dataField.setDataTableKey(dataTable.getKey());
                            dataField.setDataSchemeKey(problemHandleVO.getDataSchemeId());
                            if (dataTableType == DataTableType.DETAIL) {
                                dataField.setDataFieldKind(DataFieldKind.FIELD);
                            }
                            if ((old = this.designDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), dataField.getCode())) == null) {
                                this.designDataSchemeService.insertDataField(dataField);
                                zb = new ArrayList<String>();
                                zb.add(dataTable.getCode());
                                zb.add(dataField.getCode());
                                zb.add(dataField.getTitle());
                                this.setQuesionValueName(surveyQuestions, item, zb, null);
                            } else {
                                result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u6307\u6807code\u91cd\u590d\uff01", SurveyErrorType.MISS_ZB));
                                errorType = null;
                            }
                            if (i != 0) {
                                String name = item.getId().split("_")[0];
                                Optional<SurveyQuestion> surveyQuestionOptional = surveyQuestions.stream().filter(each -> each.getName().equals(name)).findFirst();
                                if (surveyQuestionOptional.isPresent()) {
                                    SurveyQuestion surveyQuestion = surveyQuestionOptional.get();
                                    ArrayList<String> zbList = new ArrayList<String>();
                                    zbList.add(dataTable.getCode());
                                    zbList.add(dataTable.getCode());
                                    zbList.add(dataTable.getTitle());
                                    surveyQuestion.getQuestion().setZb(zbList);
                                    surveyQuestion.setTitle(dataTable.getTitle());
                                    if (!StringUtils.hasLength(surveyQuestion.getQuestion().getLinkId())) {
                                        surveyQuestion.getQuestion().setLinkId(UUID.randomUUID().toString());
                                    }
                                }
                            }
                        }
                        if (null != errorType && (questionType == QuestionType.DROPDOWN || questionType == QuestionType.CHECKBOX || questionType == QuestionType.TAGBOX || questionType == QuestionType.RADIOGROUP)) {
                            String refDataEntityKey;
                            if (null == dataField) {
                                dataField = this.getFieldByValueName(zb);
                            }
                            errorType = !StringUtils.hasLength(refDataEntityKey = dataField.getRefDataEntityKey()) ? SurveyErrorType.NO_ENUM : SurveyErrorType.ADD_ENUM;
                        }
                    }
                    if (errorType == SurveyErrorType.NO_ENUM) {
                        R r;
                        BaseDataDefineDTO baseDataDefine = item.getBaseDataDefine();
                        if (null == baseDataDefine.getId() && (r = this.baseDataDefineClient.add(baseDataDefine)).getCode() != 0) {
                            result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u679a\u4e3e\u5b9a\u4e49\u65b0\u589e\u5931\u8d25\uff01" + r.getMsg(), SurveyErrorType.NO_ENUM));
                            errorType = null;
                        }
                        if (null != errorType) {
                            if (null == dataField) {
                                dataField = this.getFieldByValueName(zb);
                            }
                            dataField.setRefDataEntityKey(baseDataDefine.getName() + BASE);
                            this.designDataSchemeService.updateDataField(dataField);
                            errorType = SurveyErrorType.ADD_ENUM;
                        }
                    }
                    if (errorType != SurveyErrorType.ADD_ENUM) continue;
                    if (null == dataField) {
                        dataField = this.getFieldByValueName(zb);
                    }
                    if ((entityKey = dataField.getRefDataEntityKey()).endsWith(BASE)) {
                        boolean isTree;
                        String tableName = entityKey.replace(BASE, SUFFIX);
                        BaseDataDefineDTO paramDefine = new BaseDataDefineDTO();
                        paramDefine.setName(tableName);
                        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(paramDefine);
                        if (baseDataDefineDO == null) {
                            result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), String.format("\u679a\u4e3e\u6570\u636e\u65b0\u589e\uff0c\u57fa\u7840\u6570\u636e%s\u67e5\u4e0d\u5230\u5b9a\u4e49", tableName), SurveyErrorType.ADD_ENUM));
                            errorType = null;
                            continue;
                        }
                        boolean bl = isTree = baseDataDefineDO.getStructtype() > 1;
                        if (isTree) {
                            result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u679a\u4e3e\u6570\u636e\u65b0\u589e\u53ea\u9488\u5bf9\u5217\u8868\u5f62\u5f0f\u57fa\u7840\u6570\u636e", SurveyErrorType.ADD_ENUM));
                            continue;
                        }
                        List<Object> addChoice = item.getChoices();
                        if (null != addChoice) {
                            addChoice = addChoice.stream().filter(e -> !e.isReadOnly()).collect(Collectors.toList());
                            item.setChoices(addChoice);
                        }
                        StringBuilder builder = new StringBuilder();
                        ArrayList<Choice> reserveChice = new ArrayList<Choice>();
                        if (null == addChoice || addChoice.isEmpty()) {
                            this.setQuesionValueName(surveyQuestions, item, null, reserveChice);
                            continue;
                        }
                        SurveyProblemHandleVO problem = new SurveyProblemHandleVO();
                        problem.setId(item.getId());
                        ArrayList<String> erros = new ArrayList<String>();
                        for (Choice choice : addChoice) {
                            Map<String, Object> datas;
                            BaseDataDTO basedataDTO = new BaseDataDTO();
                            basedataDTO.setTableName(tableName);
                            basedataDTO.setCode(choice.getValue().toUpperCase());
                            basedataDTO.setName(choice.getText());
                            basedataDTO.setParentcode("-");
                            R r = this.baseDataClient.add(basedataDTO);
                            if (r.getCode() != 0) {
                                if (builder.length() == 0) {
                                    builder.append("\u57fa\u7840\u6570\u636e[" + tableName + "]\u65b0\u589e\u6570\u636e\u9879\u5931\u8d25\u3002");
                                }
                                log.error("\u679a\u4e3e\u6570\u636e\u65b0\u589e\u5931\u8d25\uff01" + r.getMsg(), (Object)choice);
                                if (r.getCode() == 201) {
                                    erros.add(choice.getText() + "|" + choice.getValue());
                                } else {
                                    builder.append(choice.getText()).append("|").append(choice.getValue()).append(r.getMsg()).append("\u3001");
                                }
                                reserveChice.add(choice);
                            }
                            if (null == (datas = problem.getDatas())) {
                                datas = new HashMap<String, Object>();
                                problem.setDatas(datas);
                            }
                            datas.put(choice.getValue(), r.getCode() == 0);
                        }
                        if (builder.length() != 0) {
                            if (!erros.isEmpty()) {
                                for (String string : erros) {
                                    builder.append(string).append("\u3001");
                                }
                                builder.append(" \u4ee3\u7801\u91cd\u590d");
                            }
                            if (builder.toString().endsWith("\u3001")) {
                                builder.setLength(builder.length() - 1);
                            }
                            problem.setMessage(builder.toString());
                            problem.setErrorType(SurveyErrorType.ADD_ENUM);
                            result.addItem(checkResList.get(i).getTableCode(), problem);
                        }
                        this.setQuesionValueName(surveyQuestions, item, null, reserveChice);
                        continue;
                    }
                    result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u679a\u4e3e\u6570\u636e\u65b0\u589e\u53ea\u9488\u5bf9\u5217\u8868\u5f62\u5f0f\u57fa\u7840\u6570\u636e", SurveyErrorType.ADD_ENUM));
                    continue;
                }
                if (errorType == SurveyErrorType.MIS_MATCH) {
                    result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u8bf7\u624b\u52a8\u5904\u7406\u7c7b\u578b\u4e0d\u5339\u914d\u7684\u9519\u8bef", SurveyErrorType.MIS_MATCH));
                    continue;
                }
                if (errorType == SurveyErrorType.DECIMAL_RANGE) {
                    result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u8bf7\u624b\u52a8\u5904\u7406\u5c0f\u6570\u4f4d\u8d85\u51fa\u6307\u6807\u8303\u56f4\u7684\u9519\u8bef", SurveyErrorType.DECIMAL_RANGE));
                    continue;
                }
                if (errorType == SurveyErrorType.LENGTH_RANGE) {
                    result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u8bf7\u624b\u52a8\u5904\u7406\u957f\u5ea6\u8d85\u51fa\u6307\u6807\u8303\u56f4\u7684\u9519\u8bef", SurveyErrorType.LENGTH_RANGE));
                    continue;
                }
                if (errorType == SurveyErrorType.DELETE_ZB) {
                    result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u8bf7\u624b\u52a8\u5904\u7406\u5173\u8054\u6307\u6807\u5df2\u7ecf\u4e0d\u5b58\u5728\u9519\u8bef", SurveyErrorType.DELETE_ZB));
                    continue;
                }
                result.addItem(checkResList.get(i).getTableCode(), new SurveyProblemHandleVO(item.getId(), "\u8bf7\u624b\u52a8\u5904\u7406\u6d6e\u52a8\u76f8\u5173\u95ee\u9898", errorType));
            }
        }
        for (SurveyQuestion surveyQuestion : surveyQuestions) {
            List links = surveyQuestion.getLinks();
            if (null == links) continue;
            for (SurveyQuestionLink surveyQuestionLink : links) {
                IChoicesQuestion question;
                List choices;
                ValueBean obj;
                QuestionType type = surveyQuestionLink.getType();
                if (type != QuestionType.CHECKBOX && type != QuestionType.DROPDOWN && type != QuestionType.TAGBOX && type != QuestionType.RADIOGROUP || !((obj = surveyQuestionLink.getQuestion()) instanceof IChoicesQuestion) || null == (choices = (question = (IChoicesQuestion)obj).getChoices()) || choices.isEmpty()) continue;
                List noReadOnly = choices.stream().filter(e -> !e.isReadOnly()).collect(Collectors.toList());
                question.setChoices(noReadOnly);
            }
        }
        problemHandleVO._surveyConvertStyle();
        this.saveOrUpdateLinkId(problemHandleVO);
        String style = problemHandleVO.getStyle();
        result.setStyle(style);
        return result;
    }

    private void fillFormDefineParam(RequestSurveyProblemHandleVO problemHandleVO, String formId, String prefix, DesignFormDefine formDefineParam) {
        block4: {
            List dataGroupByParent;
            Optional<DesignDataGroup> dataGroupOptional;
            List groups = this.designTimeViewController.getFormGroupsByFormId(formId);
            String title = ((DesignFormGroupDefine)groups.get(0)).getTitle();
            List allDataGroup = this.designDataSchemeService.getAllDataGroup(problemHandleVO.getDataSchemeId());
            Optional<DesignDataGroup> optionalDataGroup = allDataGroup.stream().filter(group -> group.getTitle().equals(title)).findFirst();
            if (optionalDataGroup.isPresent() && (dataGroupOptional = (dataGroupByParent = this.designDataSchemeService.getDataGroupByParent(optionalDataGroup.get().getKey())).stream().filter(group -> group.getTitle().equals(formDefineParam.getTitle())).findFirst()).isPresent()) {
                List tableList = this.designDataSchemeService.getDataTableByGroup(dataGroupOptional.get().getKey());
                String finalFormCode = prefix + "_" + formDefineParam.getFormCode() + "_1";
                Optional<DesignDataTable> table = tableList.stream().filter(each -> each.getCode().equals(finalFormCode)).findFirst();
                if (table.isPresent()) {
                    int no = 2;
                    while (true) {
                        String newFormCode = prefix + "_" + formDefineParam.getFormCode() + "_" + no;
                        table = tableList.stream().filter(each -> each.getCode().equals(newFormCode)).findFirst();
                        if (!table.isPresent()) {
                            formDefineParam.setFormCode(formDefineParam.getFormCode() + "_" + no);
                            break block4;
                        }
                        ++no;
                    }
                }
                formDefineParam.setFormCode(formDefineParam.getFormCode() + "_1");
            }
        }
    }

    private DesignDataTable getDesignDataTable(RequestSurveyProblemHandleVO problemHandleVO, String prefix, DesignFormDefine formDefine, DataTableType dataTableType) {
        if (dataTableType == null) {
            dataTableType = DataTableType.TABLE;
        }
        String tableCode = SUFFIX;
        String formCode = formDefine.getFormCode();
        tableCode = dataTableType == DataTableType.DETAIL ? formDefine.getFormCode() : (StringUtils.hasLength(prefix) ? prefix + "_" + formCode + SUFFIX : formCode + SUFFIX);
        String dataSchemeId = problemHandleVO.getDataSchemeId();
        String formId = problemHandleVO.getFormId();
        DesignDataTable dataTable = this.designDataSchemeService.getDataTableByCode(tableCode);
        if (null == dataTable) {
            List groups = this.designTimeViewController.getFormGroupsByFormId(formId);
            String title = ((DesignFormGroupDefine)groups.get(0)).getTitle();
            List allDataGroup = this.designDataSchemeService.getAllDataGroup(dataSchemeId);
            Optional<DesignDataGroup> optionalDataGroup = allDataGroup.stream().filter(group -> group.getTitle().equals(title)).findFirst();
            DesignDataGroup designDataGroup = null;
            if (optionalDataGroup.isPresent()) {
                designDataGroup = optionalDataGroup.get();
            } else {
                String schemeGroupKey = SUFFIX;
                DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeId);
                Optional<DesignDataGroup> optionalDataScheme = allDataGroup.stream().filter(group -> group.getTitle().equals(dataScheme.getTitle())).findFirst();
                if (optionalDataScheme.isPresent()) {
                    schemeGroupKey = optionalDataScheme.get().getKey();
                } else {
                    DesignDataGroup designScheme = this.designDataSchemeService.initDataGroup();
                    designScheme.setKey(UUID.randomUUID().toString());
                    designScheme.setTitle(dataScheme.getTitle());
                    designScheme.setCode(OrderGenerator.newOrder());
                    designScheme.setDataGroupKind(DataGroupKind.TABLE_GROUP);
                    designScheme.setParentKey(dataSchemeId);
                    designScheme.setDataSchemeKey(problemHandleVO.getDataSchemeId());
                    this.designDataSchemeService.insertDataGroup(designScheme);
                    schemeGroupKey = designScheme.getKey();
                }
                designDataGroup = this.designDataSchemeService.initDataGroup();
                designDataGroup.setKey(UUID.randomUUID().toString());
                designDataGroup.setTitle(title);
                designDataGroup.setCode(OrderGenerator.newOrder());
                designDataGroup.setDataGroupKind(DataGroupKind.TABLE_GROUP);
                designDataGroup.setParentKey(schemeGroupKey);
                designDataGroup.setDataSchemeKey(problemHandleVO.getDataSchemeId());
                this.designDataSchemeService.insertDataGroup(designDataGroup);
            }
            String groupTitle = formDefine.getTitle();
            List dataGroupByParent = this.designDataSchemeService.getDataGroupByParent(designDataGroup.getKey());
            Optional<DesignDataGroup> dataGroupOptional = dataGroupByParent.stream().filter(group -> group.getTitle().equals(groupTitle)).findFirst();
            DesignDataGroup dataGroup = null;
            if (!dataGroupOptional.isPresent()) {
                dataGroup = this.designDataSchemeService.initDataGroup();
                dataGroup.setKey(UUID.randomUUID().toString());
                dataGroup.setTitle(groupTitle);
                dataGroup.setCode(OrderGenerator.newOrder());
                dataGroup.setDataGroupKind(DataGroupKind.TABLE_GROUP);
                dataGroup.setParentKey(designDataGroup.getKey());
                dataGroup.setDataSchemeKey(problemHandleVO.getDataSchemeId());
                this.designDataSchemeService.insertDataGroup(dataGroup);
            } else {
                dataGroup = dataGroupOptional.get();
            }
            String tableTitle = formDefine.getTitle();
            List dataTableByGroup = this.designDataSchemeService.getDataTableByGroup(dataGroup.getKey());
            Optional<DesignDataTable> dataTableOptional = dataTableByGroup.stream().filter(group -> group.getTitle().equals(formDefine.getTitle())).findFirst();
            if (dataTableOptional.isPresent()) {
                int index = 1;
                while (true) {
                    String newTableTitle = formDefine.getTitle() + "_" + index;
                    dataTableOptional = dataTableByGroup.stream().filter(group -> group.getTitle().equals(newTableTitle)).findFirst();
                    if (!dataTableOptional.isPresent()) {
                        tableTitle = newTableTitle;
                        break;
                    }
                    ++index;
                }
            }
            dataTable = this.designDataSchemeService.initDataTable();
            dataTable.setCode(tableCode);
            dataTable.setDataTableType(dataTableType);
            dataTable.setDataSchemeKey(dataSchemeId);
            dataTable.setTitle(tableTitle);
            dataTable.setDataGroupKey(dataGroup.getKey());
            if (dataTableType == DataTableType.DETAIL) {
                dataTable.setRepeatCode(Boolean.valueOf(true));
            }
            this.designDataSchemeService.insertDataTable(dataTable);
        }
        return dataTable;
    }

    private void setQuesionValueName(List<SurveyQuestion> surveyQuestions, SurveyCheckItem item, List<String> zb, List<Choice> reserveChice) {
        for (SurveyQuestion surveyQuestion : surveyQuestions) {
            IChoicesQuestion questionItem;
            String name = surveyQuestion.getName();
            SurveyQuestionLink questionLink = surveyQuestion.getLinks().stream().filter(l -> item.getId().equals(name + "_" + l.getName())).findFirst().orElse(null);
            if (null == questionLink) continue;
            if (null != zb && !zb.isEmpty()) {
                QuestionType type = surveyQuestion.getType();
                if (type == QuestionType.PANELDYNAMIC) {
                    type = item.getType();
                }
                switch (type) {
                    case TAGBOX: 
                    case CHECKBOX: 
                    case RADIOGROUP: 
                    case DROPDOWN: {
                        ValueBean unQuestion = questionLink.getQuestion();
                        if (unQuestion instanceof IChoicesQuestion) {
                            ChoicesByUrl choicesByUrl;
                            questionItem = (IChoicesQuestion)unQuestion;
                            unQuestion.setZb(zb);
                            if (!StringUtils.hasLength(unQuestion.getLinkId())) {
                                unQuestion.setLinkId(UUID.randomUUID().toString());
                            }
                            if (null != (choicesByUrl = questionItem.getChoicesByUrl())) break;
                            choicesByUrl = new ChoicesByUrl();
                            choicesByUrl.setUrl("url");
                            choicesByUrl.setPath("data");
                            choicesByUrl.setValueName("value");
                            choicesByUrl.setAllowEmptyResponse(true);
                            questionItem.setChoicesByUrl(choicesByUrl);
                            break;
                        }
                        ValueBean question = questionLink.getQuestion();
                        question.setZb(zb);
                        if (StringUtils.hasLength(question.getLinkId())) break;
                        question.setLinkId(UUID.randomUUID().toString());
                        break;
                    }
                    default: {
                        ValueBean question = questionLink.getQuestion();
                        question.setZb(zb);
                        if (StringUtils.hasLength(question.getLinkId())) break;
                        question.setLinkId(UUID.randomUUID().toString());
                    }
                }
            }
            if (null == reserveChice) continue;
            ValueBean unQuestion = questionLink.getQuestion();
            List choices = null;
            if (unQuestion instanceof IChoicesQuestion) {
                questionItem = (IChoicesQuestion)unQuestion;
                choices = questionItem.getChoices();
            }
            if (null == choices) continue;
            Iterator iterator = choices.iterator();
            while (iterator.hasNext()) {
                Choice choice = (Choice)iterator.next();
                if (reserveChice.isEmpty()) {
                    iterator.remove();
                    continue;
                }
                boolean find = false;
                for (Choice removeChoice : reserveChice) {
                    if (!choice.getValue().equals(removeChoice.getValue())) continue;
                    find = true;
                    break;
                }
                if (find) continue;
                iterator.remove();
            }
        }
    }
}

