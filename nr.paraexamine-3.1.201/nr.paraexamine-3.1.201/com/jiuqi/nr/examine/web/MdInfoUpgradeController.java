/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.service.DesignFormGroupLinkService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.examine.web;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.service.DesignFormGroupLinkService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.examine.facade.MdInfoDataUpgradeRecordDTO;
import com.jiuqi.nr.examine.facade.MdInfoUpgradeParamsDTO;
import com.jiuqi.nr.examine.facade.MdInfoUpgradeRecordDTO;
import com.jiuqi.nr.examine.service.IMdInfoUpgradeService;
import com.jiuqi.nr.examine.web.bean.BaseDimVO;
import com.jiuqi.nr.examine.web.bean.BaseSelectItemVO;
import com.jiuqi.nr.examine.web.bean.MdInfoFieldUpgradeVO;
import com.jiuqi.nr.examine.web.bean.MdInfoFormUpgradeVO;
import com.jiuqi.nr.examine.web.bean.MdInfoUpgradeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@Api(tags={"\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7"})
@RequestMapping(value={"api/v1/paramcheck/"})
public class MdInfoUpgradeController {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignFormGroupLinkService designFormGroupLinkService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IMdInfoUpgradeService mdInfoUpgradeService;

    @ApiOperation(value="\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\u67e5\u8be2\u65b9\u6848", httpMethod="GET")
    @GetMapping(value={"upgrade/mdinfo/query/scheme/{dataSchemeKey}"})
    public MdInfoUpgradeVO queryFormSchemes(@PathVariable String dataSchemeKey) {
        List taskDefines = this.designTimeViewController.listTaskByDataScheme(dataSchemeKey);
        if (CollectionUtils.isEmpty(taskDefines)) {
            return null;
        }
        MdInfoUpgradeVO vo = new MdInfoUpgradeVO();
        vo.setDataSchemeKey(dataSchemeKey);
        this.queryDims(vo);
        this.queryFormSchemes(vo, taskDefines);
        this.restoreFromRecord(dataSchemeKey, vo);
        return vo;
    }

    private void queryDims(MdInfoUpgradeVO vo) {
        List dimensions = this.designDataSchemeService.getDataSchemeDimension(vo.getDataSchemeKey(), DimensionType.DIMENSION);
        HashMap<String, BaseDimVO> dataDims = new HashMap<String, BaseDimVO>();
        HashMap<String, String> dataDimValues = new HashMap<String, String>();
        for (DesignDataDimension dimension : dimensions) {
            if ("ADJUST".equals(dimension.getDimKey())) continue;
            dataDimValues.put(dimension.getDimKey(), null);
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimension.getDimKey());
            BaseDimVO dataDim = new BaseDimVO(iEntityDefine.getId(), iEntityDefine.getCode(), iEntityDefine.getTitle());
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
            viewDefine.setEntityId(dimension.getDimKey());
            iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            context.setOrgEntityId(dimension.getDimKey());
            try {
                IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)context);
                for (IEntityRow row : iEntityTable.getAllRows()) {
                    dataDim.getItems().put(row.getCode(), row.getTitle());
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            dataDims.put(dimension.getDimKey(), dataDim);
        }
        vo.setDataDimValues(dataDimValues);
        vo.setDataDims(dataDims);
    }

    private void queryFormSchemes(MdInfoUpgradeVO vo, List<DesignTaskDefine> taskDefines) {
        vo.setFmdmForms(new ArrayList<MdInfoFormUpgradeVO>());
        for (DesignTaskDefine taskDefine : taskDefines) {
            List schemeDefines = this.designTimeViewController.listFormSchemeByTask(taskDefine.getKey());
            for (DesignFormSchemeDefine schemeDefine : schemeDefines) {
                MdInfoFormUpgradeVO info = new MdInfoFormUpgradeVO();
                info.setTaskKey(taskDefine.getKey());
                info.setTaskTitle(taskDefine.getTitle());
                info.setFormSchemeKey(schemeDefine.getKey());
                info.setFormSchemeTitle(schemeDefine.getTitle());
                DesignFormDefine formDefine = this.designTimeViewController.listFormByFormSchemeAndType(schemeDefine.getKey(), FormType.FORM_TYPE_NEWFMDM).stream().filter(Objects::nonNull).findFirst().orElse(null);
                if (null != formDefine) {
                    info.setFormKey(formDefine.getKey());
                    info.setFormTitle(formDefine.getTitle());
                    info.setFormCode(formDefine.getFormCode());
                }
                vo.getFmdmForms().add(info);
            }
        }
    }

    private void restoreFromRecord(String dataSchemeKey, MdInfoUpgradeVO vo) {
        MdInfoUpgradeRecordDTO record = this.mdInfoUpgradeService.queryFailedUpgradeRecord(dataSchemeKey);
        if (null != record) {
            vo.setUpgradeFailed(!record.isUpgradeSucceed());
            vo.setUpgradeMessage(record.getUpgradeMessage());
            MdInfoUpgradeParamsDTO upgradeParams = record.getUpgradeParams();
            vo.setDataFieldKeys(upgradeParams.getDataFieldKeys());
            for (String dim : vo.getDataDimValues().keySet()) {
                vo.getDataDimValues().put(dim, upgradeParams.getDataDimValues().get(dim));
            }
            List<String> formKeys = upgradeParams.getFormKeys();
            Map<String, DesignFormDefine> formDefineMap = this.designTimeViewController.listForm(formKeys).stream().collect(Collectors.toMap(FormDefine::getFormScheme, v -> v));
            for (MdInfoFormUpgradeVO mdInfoForm : vo.getFmdmForms()) {
                DesignFormDefine formDefine = formDefineMap.get(mdInfoForm.getFormSchemeKey());
                if (null == formDefine) continue;
                mdInfoForm.setFormKey(formDefine.getKey());
                mdInfoForm.setFormTitle(formDefine.getTitle());
            }
        }
    }

    @ApiOperation(value="\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\u67e5\u8be2\u62a5\u8868", httpMethod="GET")
    @GetMapping(value={"upgrade/mdinfo/query/form/{formSchemeKey}"})
    public List<BaseSelectItemVO> queryForms(@PathVariable String formSchemeKey) {
        Map<String, String> groupOrders = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IBaseMetaItem::getOrder));
        List links = this.designFormGroupLinkService.getFormGroupLinksByGroups(new ArrayList<String>(groupOrders.keySet()));
        HashMap<String, String> formOrders = new HashMap<String, String>();
        for (DesignFormGroupLink link : links) {
            String groupOrder = groupOrders.get(link.getGroupKey());
            String formOrder = link.getFormOrder();
            formOrders.put(link.getFormKey(), groupOrder + formOrder);
        }
        return this.designTimeViewController.listFormByFormScheme(formSchemeKey).stream().filter(f -> FormType.FORM_TYPE_FMDM == f.getFormType() || FormType.FORM_TYPE_FIX == f.getFormType()).sorted((f1, f2) -> {
            String o1 = (String)formOrders.get(f1.getKey());
            String o2 = (String)formOrders.get(f2.getKey());
            o1 = null == o1 ? "" : o1;
            o2 = null == o2 ? "" : o2;
            return o1.compareTo(o2);
        }).map(f -> new BaseSelectItemVO(f.getKey(), f.getFormCode(), f.getTitle())).collect(Collectors.toList());
    }

    @ApiOperation(value="\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\u67e5\u8be2\u6307\u6807", httpMethod="POST")
    @PostMapping(value={"upgrade/mdinfo/query/field"})
    public List<BaseSelectItemVO> queryDataFields(@RequestBody List<String> formKeys) {
        HashSet dataFieldKeys = new HashSet();
        for (String formKey : formKeys) {
            Set keys = this.designTimeViewController.listDataLinkByForm(formKey).stream().filter(l -> DataLinkType.DATA_LINK_TYPE_FIELD == l.getType()).map(DataLinkDefine::getLinkExpression).filter(StringUtils::hasText).collect(Collectors.toSet());
            dataFieldKeys.addAll(keys);
        }
        List dataFields = this.designDataSchemeService.getDataFields(new ArrayList(dataFieldKeys));
        if (CollectionUtils.isEmpty(dataFields)) {
            return Collections.emptyList();
        }
        DesignDataTable mdInfo = this.designDataSchemeService.getDataTableForMdInfo(((DesignDataField)dataFields.get(0)).getDataSchemeKey());
        return dataFields.stream().filter(f -> this.filterField(mdInfo, (DesignDataField)f)).sorted().map(f -> new BaseSelectItemVO(f.getKey(), f.getCode(), f.getTitle())).collect(Collectors.toList());
    }

    private boolean filterField(DesignDataTable mdInfo, DesignDataField dataField) {
        if (null == dataField || DataFieldKind.FIELD_ZB != dataField.getDataFieldKind()) {
            return false;
        }
        if (null != mdInfo && mdInfo.getKey().equals(dataField.getDataTableKey())) {
            return false;
        }
        DataFieldType dataFieldType = dataField.getDataFieldType();
        return dataFieldType == DataFieldType.STRING || dataFieldType == DataFieldType.CLOB || dataFieldType == DataFieldType.INTEGER || dataFieldType == DataFieldType.BIGDECIMAL || dataFieldType == DataFieldType.BOOLEAN || dataFieldType == DataFieldType.DATE;
    }

    @ApiOperation(value="\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\u6267\u884c\u5347\u7ea7", httpMethod="POST")
    @PostMapping(value={"upgrade/mdinfo"})
    public MdInfoUpgradeVO upgrade(@RequestBody MdInfoUpgradeVO params) throws JQException {
        MdInfoUpgradeRecordDTO record = new MdInfoUpgradeRecordDTO();
        record.setDataSchemeKey(params.getDataSchemeKey());
        record.setUpgradeParams(new MdInfoUpgradeParamsDTO());
        record.getUpgradeParams().setDataDimValues(null == params.getDataDimValues() ? Collections.emptyMap() : params.getDataDimValues());
        record.getUpgradeParams().setDataFieldKeys(null == params.getDataFieldKeys() ? Collections.emptyList() : params.getDataFieldKeys());
        record.getUpgradeParams().setFormKeys(null == params.getFmdmForms() ? Collections.emptyList() : params.getFmdmForms().stream().map(MdInfoFormUpgradeVO::getFormKey).filter(Objects::nonNull).collect(Collectors.toList()));
        this.mdInfoUpgradeService.upgradeParam(record);
        return this.upgrade(params, record);
    }

    @ApiOperation(value="\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\u5931\u8d25\u540e\u91cd\u65b0\u5347\u7ea7", httpMethod="POST")
    @PostMapping(value={"re-upgrade/mdinfo"})
    public MdInfoUpgradeVO reUpgrade(@RequestBody MdInfoUpgradeVO params) {
        MdInfoUpgradeRecordDTO record = this.mdInfoUpgradeService.queryFailedUpgradeRecord(params.getDataSchemeKey());
        if (null == record) {
            return params;
        }
        record.getUpgradeParams().setDataDimValues(params.getDataDimValues());
        return this.upgrade(params, record);
    }

    private MdInfoUpgradeVO upgrade(MdInfoUpgradeVO params, MdInfoUpgradeRecordDTO record) {
        this.mdInfoUpgradeService.upgradeMdInfoTable(record);
        if (!record.isUpgradeSucceed()) {
            return this.getResult(params, record);
        }
        this.mdInfoUpgradeService.upgradeData(record);
        return this.getResult(params, record);
    }

    private MdInfoUpgradeVO getResult(MdInfoUpgradeVO params, MdInfoUpgradeRecordDTO record) {
        String errorMessage = "\u5347\u7ea7\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u540e\u91cd\u8bd5";
        String successMessage = CollectionUtils.isEmpty(record.getDataUpgradeRecords()) ? "\u5347\u7ea7\u6210\u529f" : "\u8bf7\u5728\u786e\u8ba4\u5347\u7ea7\u53c2\u6570\u53ca\u6570\u636e\u65e0\u8bef\u540e\uff0c\u624b\u52a8\u5220\u9664\u6e90\u7269\u7406\u8868\uff08\u8868\u5185\u65e0\u6709\u6548\u5b57\u6bb5\uff09\u6216\u7269\u7406\u8868\u4e2d\u7684\u5b57\u6bb5";
        params.setUpgradeFailed(!record.isUpgradeSucceed());
        params.setUpgradeMessage(record.isUpgradeSucceed() ? successMessage : errorMessage);
        if (!CollectionUtils.isEmpty(record.getDataUpgradeRecords())) {
            ArrayList<MdInfoFieldUpgradeVO> mdInfoFields = new ArrayList<MdInfoFieldUpgradeVO>();
            for (MdInfoDataUpgradeRecordDTO dataUpgradeRecord : record.getDataUpgradeRecords()) {
                String sourceTableName = dataUpgradeRecord.getSourceTableName();
                String mdInfoTableName = dataUpgradeRecord.getMdInfoTableName();
                Map<String, String> sourceFiledNameMap = dataUpgradeRecord.getSourceFiledNameMap();
                Map<String, String> mdInfoFieldNameMap = dataUpgradeRecord.getMdInfoFieldNameMap();
                if (CollectionUtils.isEmpty(sourceFiledNameMap) || CollectionUtils.isEmpty(mdInfoFieldNameMap)) continue;
                for (Map.Entry<String, String> entry : sourceFiledNameMap.entrySet()) {
                    String fieldKey = entry.getKey();
                    String fieldName = entry.getValue();
                    String mdInfoFieldName = mdInfoFieldNameMap.get(fieldKey);
                    mdInfoFields.add(new MdInfoFieldUpgradeVO(fieldName, sourceTableName, mdInfoFieldName, mdInfoTableName));
                }
            }
            params.setMdInfoFields(mdInfoFields);
        }
        return params;
    }
}

