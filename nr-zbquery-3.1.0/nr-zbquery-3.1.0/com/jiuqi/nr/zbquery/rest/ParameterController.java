/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.PageCondition
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.PageCondition;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.zbquery.common.ZBQueryErrorEnum;
import com.jiuqi.nr.zbquery.model.DefaultValueMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.rest.vo.ParameterInfoVO;
import com.jiuqi.nr.zbquery.util.DimensionValueUtils;
import com.jiuqi.nr.zbquery.util.ParameterBuilder;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery"})
public class ParameterController {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormSchemeService formSchemeService;

    @PostMapping(value={"/parameter/generate"})
    @RequiresPermissions(value={"nr:zbquery:querymodel"})
    public List<String> getParameter(@RequestBody List<ParameterInfoVO> infos) throws JQException {
        Assert.notNull(infos, "conditionField must not be null");
        ArrayList<String> list = new ArrayList<String>();
        try {
            List<ParameterModel> paramModels = ParameterBuilder.build(infos);
            ParameterCalculator parameterCalculator = new ParameterCalculator(NpContextHolder.getContext().getUserName(), paramModels);
            for (ParameterModel paramModel : paramModels) {
                JSONObject json = ParameterConvertor.toJson((ParameterCalculator)parameterCalculator, (ParameterModel)paramModel, (boolean)false);
                if (paramModel.getValueConfig() != null && paramModel.getValueConfig().getCandidateValue().size() > 0) {
                    JSONObject valueConfigJSON = new JSONObject();
                    paramModel.getValueConfig().toJson(valueConfigJSON, paramModel.getDatasource());
                    json.put("valueConfig", (Object)valueConfigJSON);
                }
                list.add(json.toString());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_201, e.getMessage(), (Throwable)e);
        }
        return list;
    }

    @GetMapping(value={"/parameter/get_unitTitle"})
    public String getUnitTitle(String unitId, String entityId) throws Exception {
        unitId = Html.cleanName((String)unitId, (char[])new char[0]);
        entityId = Html.cleanName((String)entityId, (char[])new char[0]);
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        DimensionValueSet dimensionValue = new DimensionValueSet();
        String dimensionName = this.entityMetaService.getDimensionName(entityViewDefine.getEntityId());
        dimensionValue.setValue(dimensionName, (Object)unitId);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.setMasterKeys(dimensionValue);
        IEntityTable iEntityTable = iEntityQuery.executeReader(null);
        List entityRows = iEntityTable.getAllRows();
        String unitName = "";
        if (entityRows != null) {
            for (IEntityRow iEntityRow : entityRows) {
                AbstractData rowNameValue = iEntityRow.getValue("name");
                if (rowNameValue.isNull) continue;
                unitName = rowNameValue.getAsString();
            }
        }
        return unitName;
    }

    @GetMapping(value={"/parameter/get_formScheme"})
    public String getFormScheme(String period, String taskKey) throws Exception {
        SchemePeriodLinkDefine formScheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
        if (formScheme != null) {
            return formScheme.getSchemeKey();
        }
        return "";
    }

    @GetMapping(value={"/parameter/get_formSchemeAndAdjust"})
    public HashMap<String, String> get_formSchemeAndAdjust(String period, String taskKey) throws Exception {
        boolean enableAdjust;
        HashMap<String, String> map = new HashMap<String, String>();
        SchemePeriodLinkDefine formScheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
        if (formScheme != null) {
            map.put("formScheme", formScheme.getSchemeKey());
        }
        map.put("adjust", (enableAdjust = this.formSchemeService.isTaskEnableAdjustPeriod(taskKey)) ? "true" : "false");
        return map;
    }

    @GetMapping(value={"/parameter/get_dimFirstValue/{dimName}"})
    public String getDimFirstValue(@PathVariable String dimName) throws Exception {
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(dimName);
        EntityViewDefine entityViewDefine = this.entityViewController.buildEntityView(entityDefine.getId());
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(DimensionValueSet.EMPTY);
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.sorted(true);
        IEntityTable entityTable = iEntityQuery.executeReader(null);
        PageCondition pc = new PageCondition();
        pc.setPageSize(Integer.valueOf(1));
        pc.setPageIndex(Integer.valueOf(1));
        List rows = entityTable.getRootRows();
        if (!CollectionUtils.isEmpty(rows)) {
            return ((IEntityRow)rows.get(0)).getEntityKeyData();
        }
        return null;
    }

    @PostMapping(value={"/parameter/get_dimValues/{valueMode}"})
    public List<String> getDimValues(@RequestBody QueryDimension queryDim, @PathVariable String valueMode) throws Exception {
        String value = null;
        String[] values = null;
        switch (DefaultValueMode.valueOf(StringUtils.upperCase((String)valueMode))) {
            case CURRENT: {
                value = DimensionValueUtils.getCurrentPeriod(queryDim);
                break;
            }
            case PREVIOUS: {
                value = DimensionValueUtils.getCurrentPeriod(queryDim, -1);
                break;
            }
            case FIRST: {
                value = DimensionValueUtils.getFirstValue(queryDim);
                break;
            }
            case FIRST_CHILD: {
                values = DimensionValueUtils.getFirstAndChildValue(queryDim);
                break;
            }
            case FIRST_ALLCHILD: {
                values = DimensionValueUtils.getFirstAndAllChildValue(queryDim);
                break;
            }
        }
        if (StringUtils.isNotEmpty(value)) {
            return Arrays.asList(value);
        }
        if (values != null) {
            return Arrays.asList(values);
        }
        return Arrays.asList(new String[0]);
    }
}

