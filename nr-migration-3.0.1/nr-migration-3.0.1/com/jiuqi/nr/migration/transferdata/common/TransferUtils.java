/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.migration.transferdata.common;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.ExportParam;
import com.jiuqi.nr.migration.transferdata.bean.TransOrgInfo;
import com.jiuqi.nr.migration.transferdata.common.DataTransUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class TransferUtils {
    private static final String[] nrSYSFields = new String[]{"ID", "VER", "CODE", "OBJECTCODE", "NAME", "SHORTNAME", "UNITCODE", "VALIDTIME", "INVALIDTIME", "PARENTCODE", "STOPFLAG", "RECOVERYFLAG", "ORDINAL", "CREATEUSER", "CREATETIME", "PARENTS"};
    private static final IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
    private static final IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
    private static final IEntityViewRunTimeController entityViewController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);

    public static boolean isNrSysField(String code) {
        for (String sysField : nrSYSFields) {
            if (!sysField.equals(code)) continue;
            return true;
        }
        return false;
    }

    public static DimensionValueSet toDimensionValueSet(String periodEntityId, String periodValue, String dwEntityId, List<String> dwValues) {
        DimensionValueSet dvs = new DimensionValueSet();
        dvs.setValue(periodEntityId, (Object)periodValue);
        dvs.setValue(dwEntityId, dwValues);
        return dvs;
    }

    public static void extractDimToVo(Map<String, DimensionValue> dimensionValueSet, ExportParam vo) {
        vo.setPeriodEntityId(dimensionValueSet.get("DATATIME").getName());
        vo.setPeriodValue(dimensionValueSet.get("DATATIME").getValue());
        vo.setDwEntityId(dimensionValueSet.get("MD_ORG").getName());
        ArrayList<TransOrgInfo> infos = new ArrayList<TransOrgInfo>();
        String orgDatas = dimensionValueSet.get("MD_ORG").getValue();
        for (String orgInfo : orgDatas.split(";")) {
            infos.add(new TransOrgInfo(orgInfo.split(":")[0], orgInfo.split(":")[1]));
        }
        vo.setOrgInfos(infos);
    }

    public static List<DimInfo> toDimInfoList(Map<String, DimensionValue> dimensionValueSet) {
        ArrayList<DimInfo> result = new ArrayList<DimInfo>();
        DimensionValue periodV = dimensionValueSet.get("DATATIME");
        result.add(new DimInfo("DATATIME", periodV.getName(), periodV.getValue()));
        DimensionValue masterDimV = dimensionValueSet.get("MD_ORG");
        DimInfo masterDimInfo = new DimInfo();
        String name = masterDimV.getName();
        String entityId = "";
        if (name.contains("@")) {
            entityId = name;
            name = name.split("@")[0];
        } else {
            entityId = name.startsWith("MD_ORG") ? name + "@ORG" : name + "@BASE";
        }
        masterDimInfo.setName(name);
        masterDimInfo.setEntityId(entityId);
        String orgDatas = dimensionValueSet.get("MD_ORG").getValue();
        for (String orgInfo : orgDatas.split(";")) {
            masterDimInfo.addValue(orgInfo.split(":")[0]);
        }
        result.add(masterDimInfo);
        return result;
    }

    public static DimensionValueSet getDimensionValueSet(List<DimInfo> dimInfos) {
        DimensionValueSet valueSet = new DimensionValueSet();
        for (DimInfo dimInfo : dimInfos) {
            String name;
            if ("DATATIME".equals(dimInfo.getName())) {
                valueSet.setValue(dimInfo.getName(), (Object)dimInfo.getValue());
                continue;
            }
            String string = name = dimInfo.getEntityId().endsWith("@BASE") ? dimInfo.getEntityId().substring(0, dimInfo.getEntityId().length() - "@BASE".length()) : "MD_ORG";
            if (dimInfo.getValues().size() > 0) {
                valueSet.setValue(name, dimInfo.getValues());
                continue;
            }
            valueSet.setValue(name, (Object)dimInfo.getValue());
        }
        return valueSet;
    }

    public static DimensionCollection buildDimensionCollection(List<DimInfo> dims) {
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (DimInfo dim : dims) {
            if ("DATATIME".equals(dim.getName())) {
                builder.setEntityValue(dim.getName(), dim.getEntityId(), new Object[]{dim.getValue()});
                continue;
            }
            if (dim.getValues().size() > 0) {
                if (dim.getName().startsWith("MD_ORG")) {
                    builder.setDWValue("MD_ORG", dim.getEntityId(), new Object[]{dim.getValues()});
                    continue;
                }
                builder.setEntityValue(dim.getName(), dim.getName() + "@BASE", new Object[]{dim.getValues()});
                continue;
            }
            if (dim.getName().startsWith("MD_ORG") || "UNITID".equals(dim.getName())) {
                builder.setDWValue("MD_ORG", dim.getEntityId(), new Object[]{dim.getValue()});
                continue;
            }
            builder.setEntityValue(dim.getName(), dim.getName() + "@BASE", new Object[]{dim.getValue()});
        }
        return builder.getCollection();
    }

    public static IEntityTable getEntityTable(String entityId, String timeKey) throws Exception {
        EntityViewDefine entityViewDefine = entityViewController.buildEntityView(entityId);
        IEntityQuery iEntityQuery = entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(DimensionValueSet.EMPTY);
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.sorted(true);
        if (StringUtils.hasLength(timeKey)) {
            DimensionValueSet dvs = new DimensionValueSet();
            dvs.setValue("DATATIME", (Object)timeKey);
            iEntityQuery.setMasterKeys(dvs);
        }
        return iEntityQuery.executeReader(null);
    }

    public static Map<String, String> getBaseDataProperty(IEntityTable entityTable, String code) {
        HashMap<String, String> bdProperty = new HashMap<String, String>();
        if (StringUtils.hasLength(code)) {
            IEntityRow findByCode = entityTable.findByCode(code);
            Iterator attributes = entityTable.getEntityModel().getAttributes();
            while (attributes.hasNext()) {
                IEntityAttribute entityAttribute = (IEntityAttribute)attributes.next();
                String fieldName = entityAttribute.getCode();
                AbstractData abstractData = findByCode.getValue(fieldName);
                String commonZbValue = DataTransUtil.getFieldValue(abstractData);
                if (TransferUtils.isNrSysField(fieldName) || commonZbValue == null || commonZbValue.equals("null") || "".equals(commonZbValue)) continue;
                bdProperty.put(fieldName, commonZbValue);
            }
        }
        return bdProperty;
    }

    public static String getEntityFieldRelateID(String[] dimensionGroup) {
        if (dimensionGroup != null && dimensionGroup.length == 2) {
            String baseEntityId = dimensionGroup[0];
            String entityField = dimensionGroup[1];
            if (StringUtils.hasLength(baseEntityId) && StringUtils.hasLength(entityField)) {
                List entityRefer = entityMetaService.getEntityRefer(baseEntityId);
                for (IEntityRefer refer : entityRefer) {
                    if (!entityField.equals(refer.getOwnField())) continue;
                    return refer.getReferEntityId();
                }
            }
        }
        return null;
    }

    public static String getParentCode(IEntityTable iEntityTable, String curOrgCode) {
        IEntityRow byEntityKey;
        IEntityRow ientityRow;
        if (StringUtils.hasLength(curOrgCode) && iEntityTable != null && (ientityRow = iEntityTable.findByCode(curOrgCode)) != null && StringUtils.hasLength(ientityRow.getParentEntityKey()) && (byEntityKey = iEntityTable.findByEntityKey(ientityRow.getParentEntityKey())) != null) {
            return byEntityKey.getCode();
        }
        return "";
    }

    public static boolean isExistEntity(String entityCode) {
        IEntityDefine iEntityDefine = entityMetaService.queryEntityByCode(entityCode);
        return iEntityDefine != null;
    }

    public static String getManagement(String[] dimensionGroup, Map<String, String> bdPropertyMap) {
        if (dimensionGroup.length == 2 && bdPropertyMap.containsKey(dimensionGroup[1])) {
            return bdPropertyMap.get(dimensionGroup[1]);
        }
        return "";
    }

    public static IEntityRow getManagementEntityRow(String relatedEntityId, String management, String periodValue) throws Exception {
        IEntityTable entityTable = TransferUtils.getEntityTable(relatedEntityId, periodValue);
        return entityTable.findByCode(management);
    }

    public static List<IEntityRow> getAllOrg(String entityId, String timeKey) throws Exception {
        List rows;
        IEntityTable entityTable;
        if (StringUtils.hasLength(entityId) && StringUtils.hasLength(timeKey) && (entityTable = TransferUtils.getEntityTable(entityId, timeKey)) != null && !CollectionUtils.isEmpty(rows = entityTable.getRootRows()) && rows.size() <= 1) {
            IEntityRow parent = (IEntityRow)rows.get(0);
            return entityTable.getChildRows(parent.getEntityKeyData());
        }
        return null;
    }
}

