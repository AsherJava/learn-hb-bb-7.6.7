/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.engine.exception.DataGatherExecption;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GatherTempTableHandler {
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runtimeController;

    public static List<LogicField> getDimCols() {
        ArrayList<LogicField> res = new ArrayList<LogicField>();
        LogicField column1 = new LogicField();
        column1.setFieldName("GT_COLUM_1");
        column1.setDataType(6);
        column1.setSize(50);
        column1.setNullable(true);
        column1.setRawType(-9);
        column1.setDefaultValue("");
        res.add(column1);
        LogicField column2 = new LogicField();
        column2.setFieldName("GT_COLUM_2");
        column2.setDataType(6);
        column2.setSize(50);
        column2.setNullable(true);
        column2.setRawType(-9);
        column2.setDefaultValue("");
        res.add(column2);
        LogicField column3 = new LogicField();
        column3.setFieldName("GT_COLUM_3");
        column3.setDataType(6);
        column3.setSize(50);
        column3.setNullable(true);
        column3.setRawType(-9);
        column3.setDefaultValue("");
        res.add(column3);
        LogicField column4 = new LogicField();
        column4.setFieldName("GT_COLUM_4");
        column4.setDataType(6);
        column4.setSize(50);
        column4.setNullable(true);
        column4.setRawType(-9);
        column4.setDefaultValue("");
        res.add(column4);
        LogicField column5 = new LogicField();
        column5.setFieldName("GT_COLUM_5");
        column5.setDataType(6);
        column5.setSize(50);
        column5.setRawType(-9);
        column5.setNullable(true);
        column5.setDefaultValue("");
        res.add(column5);
        return res;
    }

    public Map<String, String> getSingleSelectDimCols(String dataSchemeKey, String taskKey) {
        LinkedHashMap<String, String> dimColsMap = new LinkedHashMap<String, String>();
        TaskDefine taskDefine = this.runtimeController.queryTaskDefine(taskKey);
        String dw = taskDefine.getDw();
        if (!StringUtils.hasLength(dw)) {
            return Collections.emptyMap();
        }
        IEntityModel entityModel = this.metaService.getEntityModel(dw);
        List dims = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        for (DataDimension dim : dims) {
            boolean multival;
            String entityKey = dim.getDimKey();
            if ("ADJUST".equals(entityKey)) continue;
            String dimAttribute = dim.getDimAttribute();
            IEntityDefine entity = this.metaService.queryEntity(entityKey);
            IEntityAttribute attribute = entityModel.getAttribute(dimAttribute);
            if (attribute == null || (multival = attribute.isMultival())) continue;
            dimColsMap.put(entity.getDimensionName(), attribute.getCode());
        }
        return dimColsMap;
    }

    public String getOrgTable(String taskKey) {
        TaskDefine taskDefine = this.runtimeController.queryTaskDefine(taskKey);
        String dw = taskDefine.getDw();
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            dw = dsContext.getContextEntityId();
        }
        TableModelDefine tableModel = this.metaService.getTableModel(dw);
        return tableModel.getName();
    }

    public DataScheme getDataScheme(String dataSchemeKey) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        if (dataScheme == null) {
            throw new DataGatherExecption("\u627e\u4e0d\u5230\u6570\u636e\u65b9\u6848\uff01");
        }
        return dataScheme;
    }

    public IRuntimeDataSchemeService getRuntimeDataService() {
        return this.runtimeDataSchemeService;
    }
}

