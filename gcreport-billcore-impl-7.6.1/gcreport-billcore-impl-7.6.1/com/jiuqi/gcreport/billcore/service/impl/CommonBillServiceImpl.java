/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.extend.common.consts.MetaDataType
 *  com.jiuqi.va.biz.extend.i18n.MetaInfoI18nHandler
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.gcreport.billcore.service.impl;

import com.google.common.collect.Maps;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.service.CommonBillService;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.util.OrgUtil;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.extend.common.consts.MetaDataType;
import com.jiuqi.va.biz.extend.i18n.MetaInfoI18nHandler;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonBillServiceImpl
implements CommonBillService {
    private static final Logger logger = LoggerFactory.getLogger(CommonBillServiceImpl.class);
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private VaDataModelPublishedService vaDataModelPublishedService;

    @Override
    public List<Map<String, Object>> listColumns(String tableName, String defineCode) {
        LinkedHashSet<String> masterColumnCodes;
        BillInfoVo billInfoVo = BillParseTool.parseBillInfo(defineCode);
        MetaInfoI18nHandler i18nHandler = (MetaInfoI18nHandler)SpringContextUtils.getBean(MetaInfoI18nHandler.class);
        String parentId = "VA#bill#GCBILL&module#GCREPORT&group#" + defineCode + "&define#data&plugin#" + tableName + "&table";
        List i18nResources = i18nHandler.getI18nResourceList(MetaDataType.BILL.getName(), parentId, "resource");
        I18nHelper i18nHelper = (I18nHelper)SpringBeanUtils.getBean((String)"VA", I18nHelper.class);
        HashMap feildName2I18nTitleMap = new HashMap();
        if (!CollectionUtils.isEmpty((Collection)i18nResources)) {
            i18nResources.stream().forEach(i18nResource -> {
                String message = i18nHelper.getMessage(i18nResource.getUniqueName());
                feildName2I18nTitleMap.put(i18nResource.getName(), message);
            });
        }
        if (CollectionUtils.isEmpty(masterColumnCodes = billInfoVo.getMasterColumnCodes())) {
            return Collections.emptyList();
        }
        ArrayList<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
        List columnModelDefines = NrTool.queryAllColumnsInTable((String)tableName);
        Map<String, ColumnModelDefine> columnCode2FieldDefineMap = columnModelDefines.stream().collect(Collectors.toMap(IModelDefineItem::getCode, item -> item, (v1, v2) -> v2));
        for (String columnCode : masterColumnCodes) {
            HashMap<String, String> columnInfo = new HashMap<String, String>(8);
            ColumnModelDefine define = columnCode2FieldDefineMap.get(columnCode);
            if (null == define) {
                logger.info(String.format("\u5728%1s\u8868\u4e2d \u672a\u67e5\u8be2\u5230\u5b57\u6bb5\u6a21\u578b\uff1a%2s", tableName, columnCode));
                continue;
            }
            columnInfo.put("key", columnCode);
            String i18nTitle = (String)feildName2I18nTitleMap.get(columnCode);
            columnInfo.put("label", StringUtils.isEmpty((String)i18nTitle) ? define.getTitle() : i18nTitle);
            columnInfo.put("align", "left");
            columnInfo.put("columnType", define.getColumnType().name());
            if (define.getColumnType() == ColumnModelType.DOUBLE || define.getColumnType() == ColumnModelType.INTEGER || define.getColumnType() == ColumnModelType.BIGDECIMAL) {
                columnInfo.put("align", "right");
            }
            columnList.add(columnInfo);
        }
        return columnList;
    }

    @Override
    public String getOrgType(String defineCode) {
        BillContextImpl billContextImpl = new BillContextImpl();
        billContextImpl.setDisableVerify(true);
        BillModel billModel = this.billDefineService.createModel((BillContext)billContextImpl, defineCode);
        String unitTableName = ((DataFieldDefine)billModel.getMasterTable().getDefine().getFields().get("UNITCODE")).getRefTableName();
        return StringUtils.isEmpty((String)unitTableName) ? "MD_ORG_CORPORATE" : unitTableName;
    }

    @Override
    public Map checkCommonParent(String unitType, String periodStr, String mergeUnitId, String unitId, String oppUnitId) {
        if (StringUtils.isEmpty((String)unitType) || Objects.isNull(mergeUnitId) || Objects.isNull(unitId) || Objects.isNull(oppUnitId)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        HashMap resultMap = Maps.newHashMap();
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)unitType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitOrgVO = tool.getOrgByID(unitId);
        GcOrgCacheVO oppUnitVO = tool.getOrgByID(oppUnitId);
        if (Objects.isNull(unitOrgVO) || Objects.isNull(oppUnitVO)) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u6570\u636e\u6709\u8bef\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u6838\u5bf9");
        }
        boolean isParentUnit = false;
        GcOrgCacheVO commonUnit = tool.getCommonUnit(unitOrgVO, oppUnitVO);
        if (Objects.nonNull(commonUnit) && Objects.equals(commonUnit.getId(), mergeUnitId)) {
            isParentUnit = true;
        }
        resultMap.put("isParent", isParentUnit);
        resultMap.put("unitId", unitOrgVO.getId());
        resultMap.put("oppUnitId", oppUnitVO.getId());
        resultMap.put("unit", unitOrgVO);
        resultMap.put("oppUnit", oppUnitVO);
        return resultMap;
    }

    private List<VaI18nResourceItem> getMetaDataI18nPluginList(String module, String metaType, String defineCode) {
        ArrayList<VaI18nResourceItem> resourceList = new ArrayList<VaI18nResourceItem>();
        TenantDO tenant = new TenantDO();
        tenant.addExtInfo("module", (Object)module);
        tenant.addExtInfo("uniqueCode", (Object)defineCode);
        List metaTreeInfos = this.metaDataClient.getMetaInfoList(tenant);
        if (metaTreeInfos == null || metaTreeInfos.size() <= 0) {
            return resourceList;
        }
        MetaTreeInfoDTO currDefine = (MetaTreeInfoDTO)metaTreeInfos.get(0);
        ModuleDTO moduleParam = new ModuleDTO();
        moduleParam.setModuleName(module);
        moduleParam.setFunctionType(metaType);
        R r = this.metaDataClient.getModuleByName(moduleParam);
        if (r.getCode() != 0) {
            return null;
        }
        String path = r.get((Object)"path").toString();
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)r.get((Object)"server").toString(), (String)(org.springframework.util.StringUtils.hasText(path) ? path : ""));
        TenantDO param = new TenantDO();
        param.addExtInfo("modelType", (Object)currDefine.getModelName());
        param.addExtInfo("uniqueCode", (Object)defineCode);
        r = bizClient.getI18nPlugins(param);
        List i18nPlugins = (List)r.get((Object)"plugins");
        if (i18nPlugins == null || i18nPlugins.size() <= 0) {
            return null;
        }
        for (Map i18nPlugin : i18nPlugins) {
            VaI18nResourceItem pluginResourceItem = new VaI18nResourceItem();
            pluginResourceItem.setName(i18nPlugin.get("name").toString());
            pluginResourceItem.setTitle(i18nPlugin.get("title").toString());
            pluginResourceItem.setGroupFlag(Boolean.valueOf(i18nPlugin.get("groupFlag").toString()).booleanValue());
            pluginResourceItem.setCategoryFlag(Boolean.valueOf(i18nPlugin.get("categoryFlag").toString()).booleanValue());
            resourceList.add(pluginResourceItem);
        }
        return resourceList;
    }

    @Override
    public List<DataModelColumn> listAmtFileds(String tableName) {
        if (StringUtils.isEmpty((String)tableName)) {
            return null;
        }
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(tableName);
        DataModelDO dataModelDO = this.vaDataModelPublishedService.get(dataModelDTO);
        List columns = dataModelDO.getColumns();
        List<DataModelColumn> amtColumns = columns.stream().filter(item -> item != null && !DataModelType.ColumnAttr.SYSTEM.equals((Object)item.getColumnAttr()) && item.getColumnType().equals((Object)DataModelType.ColumnType.NUMERIC)).collect(Collectors.toList());
        return amtColumns;
    }

    @Override
    public List<DataModelColumn> listNotSystemFileds(String tableName) {
        if (StringUtils.isEmpty((String)tableName)) {
            return null;
        }
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(tableName);
        DataModelDO dataModelDO = this.vaDataModelPublishedService.get(dataModelDTO);
        List columns = dataModelDO.getColumns();
        List<DataModelColumn> amtColumns = columns.stream().filter(item -> item != null && !DataModelType.ColumnAttr.SYSTEM.equals((Object)item.getColumnAttr())).collect(Collectors.toList());
        return amtColumns;
    }

    @Override
    public String getOrgTypeByTableName(String tableName) {
        return OrgUtil.getOrgType(tableName);
    }
}

