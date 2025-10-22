/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.basedata.filter.BiSyntaxBaseDataExpressionParser
 */
package com.jiuqi.nr.dafafill.tree;

import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.tree.DataFillSchemeTree;
import com.jiuqi.nr.dafafill.tree.DataFillTaskTree;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.dafafill.web.vo.ReturnInfoVO;
import com.jiuqi.nr.dafafill.web.vo.ZBSelectorParam;
import com.jiuqi.nr.dafafill.web.vo.ZBSelectorZBInfo;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.basedata.filter.BiSyntaxBaseDataExpressionParser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ZBSelector {
    @Autowired
    private IRuntimeDataSchemeService schemeService;
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private DataFillSchemeTree schemeTree;
    @Autowired
    private DataFillTaskTree taskTree;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private BiSyntaxBaseDataExpressionParser parser;
    private static final String MSG = "\u4e0d\u652f\u6301\u5c01\u9762\u4ee3\u7801\u8868\u6307\u6807\u3001\u56fa\u5b9a\u6307\u6807\u3001\u6d6e\u52a8\u6307\u6807\u4e00\u8d77\u5f55\u5165\u6570\u636e\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9\u6307\u6807";

    public ReturnInfoVO getQueryFiled(ZBSelectorParam param) {
        StringBuffer msg;
        List<String> selectZbs = param.getZbKeyList();
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        if (CollectionUtils.isEmpty(selectZbs)) {
            returnInfo.setMsg("\u6ca1\u6709\u9009\u62e9\u6307\u6807");
            return returnInfo;
        }
        if (param.getModelType() == ModelType.TASK) {
            return this.getFMDMQueryFiled(param.getTaskCode(), selectZbs);
        }
        String fmdmFormKey = "";
        String taskKey = "";
        String formSchemeKey = "";
        boolean fixZb = false;
        boolean floatZb = false;
        boolean fmdmZb = false;
        HashSet<String> formSet = new HashSet<String>();
        for (ZBSelectorZBInfo zbInfo : param.getZbList()) {
            FormDefine formDefine = this.runTime.queryFormById(zbInfo.getForm());
            if (formDefine == null) continue;
            formSet.add(zbInfo.getForm());
            if (FormType.FORM_TYPE_NEWFMDM == formDefine.getFormType()) {
                if (fixZb || floatZb) {
                    returnInfo.setMsg(MSG);
                    return returnInfo;
                }
                fmdmZb = true;
                fmdmFormKey = zbInfo.getForm();
                taskKey = zbInfo.getTask();
                formSchemeKey = zbInfo.getFormScheme();
                continue;
            }
            if (fmdmZb) {
                returnInfo.setMsg(MSG);
                return returnInfo;
            }
            DataLinkDefine dl = this.runTime.queryDataLinkDefine(zbInfo.getZb());
            DataField df = this.schemeService.getDataField(dl.getLinkExpression());
            if (df == null) continue;
            if (fixZb && (df.getDataFieldKind() == DataFieldKind.FIELD || df.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM)) {
                returnInfo.setMsg(MSG);
                return returnInfo;
            }
            if (floatZb && df.getDataFieldKind() == DataFieldKind.FIELD_ZB) {
                returnInfo.setMsg(MSG);
                return returnInfo;
            }
            fixZb = df.getDataFieldKind() == DataFieldKind.FIELD_ZB;
            floatZb = df.getDataFieldKind() == DataFieldKind.FIELD || df.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM;
        }
        String hasZB = param.getHasZB();
        if (StringUtils.hasText(param.getFmdmFormKey())) {
            if (formSet.size() > 1 || !param.getFmdmFormKey().equals(fmdmFormKey)) {
                returnInfo.setMsg("\u5f53\u524d\u6a21\u677f\u5df2\u9009\u5c01\u9762\u4ee3\u7801\u8868\u6307\u6807\uff0c\u4e0d\u652f\u6301\u5c01\u9762\u4ee3\u7801\u8868\u6307\u6807\u3001\u56fa\u5b9a\u6307\u6807\u3001\u6d6e\u52a8\u6307\u6807\u4e00\u8d77\u5f55\u5165\u6570\u636e");
                return returnInfo;
            }
        } else if (!"false".equals(hasZB)) {
            if (FieldType.ZB.name().equals(hasZB)) {
                if (StringUtils.hasText(fmdmFormKey) || floatZb) {
                    returnInfo.setMsg("\u5f53\u524d\u6a21\u677f\u5df2\u9009\u56fa\u5b9a\u6307\u6807\uff0c\u4e0d\u652f\u6301\u5c01\u9762\u4ee3\u7801\u8868\u6307\u6807\u3001\u56fa\u5b9a\u6307\u6807\u3001\u6d6e\u52a8\u6307\u6807\u4e00\u8d77\u5f55\u5165\u6570\u636e");
                    return returnInfo;
                }
            } else if (FieldType.FIELD.name().equals(hasZB) && (StringUtils.hasText(fmdmFormKey) || fixZb)) {
                returnInfo.setMsg("\u5f53\u524d\u6a21\u677f\u5df2\u9009\u6d6e\u52a8\u6307\u6807\uff0c\u4e0d\u652f\u6301\u5c01\u9762\u4ee3\u7801\u8868\u6307\u6807\u3001\u56fa\u5b9a\u6307\u6807\u3001\u6d6e\u52a8\u6307\u6807\u4e00\u8d77\u5f55\u5165\u6570\u636e");
                return returnInfo;
            }
        }
        if (StringUtils.hasText(fmdmFormKey)) {
            return this.getNewFMDMQueryFiled(fmdmFormKey, taskKey, formSchemeKey, param);
        }
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        if (!StringUtils.hasText(param.getDataSchemeCode())) {
            DataLinkDefine datalink = this.getFirstZB(selectZbs);
            if (datalink == null) {
                returnInfo.setMsg("\u8bf7\u9009\u62e9\u975e\u56fe\u7247\u3001\u9644\u4ef6\u6307\u6807");
                return returnInfo;
            }
            DataField dataField = this.schemeService.getDataField(datalink.getLinkExpression());
            DataScheme scheme = this.schemeService.getDataScheme(dataField.getDataSchemeKey());
            return this.buildQueryFieldsByZBType(param, queryFields, scheme, datalink, dataField);
        }
        DataScheme scheme = this.schemeService.getDataSchemeByCode(param.getDataSchemeCode());
        List dims = this.schemeService.getDataSchemeDimension(scheme.getKey());
        if (FieldType.ZB.name().equals(hasZB)) {
            return this.addSameDimQueryFields(param, queryFields, scheme);
        }
        if (FieldType.FIELD.name().equals(hasZB)) {
            msg = new StringBuffer();
            for (String id : selectZbs) {
                DataLinkDefine dl = this.runTime.queryDataLinkDefine(id);
                DataField df = this.schemeService.getDataField(dl.getLinkExpression());
                if (DataFieldKind.FIELD_ZB == df.getDataFieldKind()) {
                    msg.append(df.getTitle()).append("[").append(df.getCode()).append("]\uff0c").append(NrDataFillI18nUtil.buildCode("nr.dataFill.notAllowedFixedField")).append("\n");
                    continue;
                }
                msg.append(df.getTitle()).append("[").append(df.getCode()).append("]\uff0c").append(NrDataFillI18nUtil.buildCode("nr.dataFill.notAllowedFloatField")).append("\n");
            }
            returnInfo.setSuccess(true);
            if (msg.length() > 0) {
                returnInfo.setMsg(msg.toString());
            }
            return returnInfo;
        }
        msg = new StringBuffer();
        for (String id : selectZbs) {
            DataLinkDefine dl = this.runTime.queryDataLinkDefine(id);
            DataField df = this.schemeService.getDataField(dl.getLinkExpression());
            if (df != null && df.getDataSchemeKey().equals(scheme.getKey())) {
                return this.buildQueryFieldsByZBType(param, queryFields, scheme, dl, df);
            }
            List tempDims = this.schemeService.getDataSchemeDimension(df.getDataSchemeKey());
            if (this.sameDim(dims, tempDims)) {
                return this.buildQueryFieldsByZBType(param, queryFields, scheme, dl, df);
            }
            msg.append(df.getTitle()).append("[").append(df.getCode()).append("]\uff0c").append(NrDataFillI18nUtil.buildCode("nr.dataFill.differentDimension")).append("\n");
        }
        returnInfo.setSuccess(true);
        if (msg.length() > 0) {
            returnInfo.setMsg(msg.toString());
        }
        return returnInfo;
    }

    private ReturnInfoVO getFMDMQueryFiled(String taskCode, List<String> selectZbs) {
        TaskDefine taskDefine = this.runTime.queryTaskDefineByCode(taskCode);
        String enittyId = taskDefine.getDw();
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(enittyId);
        for (String id : selectZbs) {
            String mdmZb;
            IEntityAttribute attribute;
            DataLinkDefine dataLinkDefine = this.runTime.queryDataLinkDefine(id);
            if (dataLinkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FMDM || (attribute = entityModel.getAttribute(mdmZb = dataLinkDefine.getLinkExpression())) == null) continue;
            queryFields.add(this.taskTree.convertZbToQueryField(entityModel, attribute, taskCode));
        }
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        if (CollectionUtils.isEmpty(queryFields)) {
            returnInfo.setMsg("\u9009\u62e9\u6307\u6807\u4e0d\u5408\u6cd5\uff01");
        } else {
            returnInfo.setSuccess(true);
        }
        returnInfo.setData(queryFields);
        return returnInfo;
    }

    private ReturnInfoVO getNewFMDMQueryFiled(String fmdmFormKey, String taskKey, String formSchemeKey, ZBSelectorParam param) {
        TaskDefine taskDefine = this.runTime.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.schemeService.getDataScheme(taskDefine.getDataScheme());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        List<QueryField> queryFields = this.getFMDMQueryFields(taskDefine, entityModel, param.getZbKeyList(), dataScheme.getCode());
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        if (CollectionUtils.isEmpty(queryFields)) {
            returnInfo.setMsg("\u9009\u62e9\u6307\u6807\u4e0d\u5408\u6cd5\uff01");
        } else {
            returnInfo.setSuccess(true);
        }
        returnInfo.setData(queryFields);
        returnInfo.addExtendMap("TABLETYPE", TableType.FMDM.name());
        returnInfo.addExtendMap("FORMKEY", fmdmFormKey);
        returnInfo.addExtendMap("FORMSCHEMEKEY", formSchemeKey);
        this.buildTaskInfo(returnInfo, param, taskDefine);
        return returnInfo;
    }

    private DataLinkDefine getFirstZB(List<String> selectZbs) {
        for (String id : selectZbs) {
            DataField df;
            DataLinkDefine datalink = this.runTime.queryDataLinkDefine(id);
            if (datalink.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || this.notSupport(df = this.schemeService.getDataField(datalink.getLinkExpression()))) continue;
            return datalink;
        }
        return null;
    }

    private ReturnInfoVO buildQueryFieldsByZBType(ZBSelectorParam param, List<QueryField> queryFields, DataScheme scheme, DataLinkDefine dl, DataField df) {
        if (this.isFixed(df)) {
            return this.addSameDimQueryFields(param, queryFields, scheme);
        }
        return this.addFloatQueryField(queryFields, dl, df, scheme, param);
    }

    private boolean isFixed(DataField df) {
        return FieldType.ZB == this.schemeTree.convertDataFieldKind(df.getDataFieldKind());
    }

    private boolean notSupport(DataField df) {
        return df == null || df.getDataFieldType() == DataFieldType.PICTURE || df.getDataFieldType() == DataFieldType.FILE;
    }

    private ReturnInfoVO addFloatQueryField(List<QueryField> queryFields, DataLinkDefine datalink, DataField dataField, DataScheme scheme, ZBSelectorParam param) {
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        StringBuffer msg = new StringBuffer();
        DataTable table = this.schemeService.getDataTable(dataField.getDataTableKey());
        List allLinks = this.runTime.getAllLinksInRegion(datalink.getRegionKey()).stream().sorted((o1, o2) -> {
            if (o1.getPosX() > o2.getPosX()) {
                return 1;
            }
            if (o1.getPosX() == o2.getPosX()) {
                if (o1.getPosY() > o2.getPosY()) {
                    return 1;
                }
                if (o1.getPosY() == o2.getPosY()) {
                    return 0;
                }
            }
            return -1;
        }).collect(Collectors.toList());
        for (DataLinkDefine tempDataLink : allLinks) {
            DataField df = this.schemeService.getDataField(tempDataLink.getLinkExpression());
            if (this.notSupport(df)) {
                if (df == null) continue;
                msg.append(df.getTitle()).append("[").append(df.getCode()).append("]\uff0c\u662f\u56fe\u7247\u6216\u9644\u4ef6\u6307\u6807\n");
                continue;
            }
            queryFields.add(this.schemeTree.convertZbToQueryField(df, scheme, table, tempDataLink));
        }
        returnInfo.setSuccess(true);
        if (msg.length() > 0) {
            returnInfo.setMsg(msg.toString());
        }
        returnInfo.setData(queryFields);
        this.buildTaskInfo(returnInfo, param, null);
        return returnInfo;
    }

    private boolean sameDim(List<DataDimension> sourceDim, List<DataDimension> targetDims) {
        List sourceDimKey = sourceDim.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
        if (sourceDimKey.size() != targetDims.size()) {
            return false;
        }
        if (sourceDimKey.indexOf("ADJUST") > -1) {
            return false;
        }
        for (DataDimension dim : targetDims) {
            if ("ADJUST".equals(dim.getDimKey())) {
                return false;
            }
            if (sourceDimKey.contains(dim.getDimKey())) continue;
            return false;
        }
        return true;
    }

    private ReturnInfoVO addSameDimQueryFields(ZBSelectorParam param, List<QueryField> queryFields, DataScheme scheme) {
        ReturnInfoVO returnInfo = new ReturnInfoVO();
        StringBuffer msg = new StringBuffer();
        List dims = this.schemeService.getDataSchemeDimension(scheme.getKey());
        for (String id : param.getZbKeyList()) {
            DataLinkDefine dl = this.runTime.queryDataLinkDefine(id);
            DataField df = this.schemeService.getDataField(dl.getLinkExpression());
            if (!this.isFixed(df)) {
                msg.append(df.getTitle()).append("[").append(df.getCode()).append("]\uff0c").append(NrDataFillI18nUtil.buildCode("nr.dataFill.notFixedField")).append("\n");
                continue;
            }
            if (this.notSupport(df)) {
                msg.append(df.getTitle()).append("[").append(df.getCode()).append("]\uff0c").append(NrDataFillI18nUtil.buildCode("nr.dataFill.poaField")).append("\n");
                continue;
            }
            DataTable table = this.schemeService.getDataTable(df.getDataTableKey());
            if (df.getDataSchemeKey().equals(scheme.getKey())) {
                queryFields.add(this.schemeTree.convertZbToQueryField(df, scheme, table, dl));
                continue;
            }
            List tempDims = this.schemeService.getDataSchemeDimension(df.getDataSchemeKey());
            if (this.sameDim(dims, tempDims)) {
                queryFields.add(this.schemeTree.convertZbToQueryField(df, scheme, table, dl));
                continue;
            }
            msg.append(df.getTitle()).append("[").append(df.getCode()).append("]\uff0c").append(NrDataFillI18nUtil.buildCode("nr.dataFill.differentDimension")).append("\n");
        }
        returnInfo.setSuccess(true);
        if (msg.length() > 0) {
            returnInfo.setMsg(msg.toString());
        }
        returnInfo.setData(queryFields);
        this.buildTaskInfo(returnInfo, param, null);
        return returnInfo;
    }

    private void buildTaskInfo(ReturnInfoVO returnInfo, ZBSelectorParam param, TaskDefine taskDefine) {
        if (taskDefine == null) {
            taskDefine = this.runTime.queryTaskDefine(param.getTaskKey());
        }
        if (taskDefine != null) {
            returnInfo.addExtendMap("TASKKEY", taskDefine.getKey());
            returnInfo.addExtendMap("TASKCODE", taskDefine.getTaskCode());
            returnInfo.addExtendMap("TASKTITLE", taskDefine.getTitle());
            if (!returnInfo.getExtendMap().containsKey("FORMSCHEMEKEY")) {
                String formSchemeKey = null;
                List<ZBSelectorZBInfo> zbList = param.getZbList();
                if (!CollectionUtils.isEmpty(zbList)) {
                    formSchemeKey = zbList.get(0).getFormScheme();
                }
                if (StringUtils.hasText(formSchemeKey)) {
                    returnInfo.addExtendMap("FORMSCHEMEKEY", formSchemeKey);
                }
            }
        }
    }

    public List<QueryField> getFMDMQueryFields(TaskDefine taskDefine, IEntityModel entityModel, List<String> selectZbs, String fmdmCode) {
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        DataScheme scheme = this.schemeService.getDataScheme(taskDefine.getDataScheme());
        for (String linkKey : selectZbs) {
            DataLinkDefine dl = this.runTime.queryDataLinkDefine(linkKey);
            if (DataLinkType.DATA_LINK_TYPE_FMDM == dl.getType()) {
                IEntityAttribute attribute = entityModel.getAttribute(dl.getLinkExpression());
                queryFields.add(this.taskTree.convertZbToQueryField(entityModel, attribute, fmdmCode));
                continue;
            }
            DataField df = this.schemeService.getDataField(dl.getLinkExpression());
            if (df == null || !this.isFixed(df) || this.notSupport(df) || DataLinkType.DATA_LINK_TYPE_FORMULA == dl.getType()) continue;
            DataTable table = this.schemeService.getDataTable(df.getDataTableKey());
            queryFields.add(this.schemeTree.convertZbToQueryField(df, scheme, table, dl));
        }
        return queryFields;
    }
}

