/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datacrud.impl.MetaData
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.EntityValueType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.EntityValueType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class RegionSettingUtil {
    private static final Logger logger = LoggerFactory.getLogger(RegionSettingUtil.class);

    public static void rebuildMasterKeyByRegion(RegionData regionData, DimensionValueSet masterKey) {
        List<EntityDefaultValue> regionDefalutSettings = regionData.getRegionEntityDefaultValue();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        if (CollectionUtils.isEmpty(regionDefalutSettings)) {
            return;
        }
        for (EntityDefaultValue entityDefaultValue : regionDefalutSettings) {
            String dimension;
            EntityValueType entityValueType = entityDefaultValue.getEntityValueType();
            String value = entityDefaultValue.getItemValue();
            if (EntityValueType.CUSTOM_STRING == entityValueType) {
                dimension = RegionSettingUtil.getDimensionName(entityDefaultValue.getFieldKey());
                if (dimension == null) {
                    logger.warn("\u533a\u57df\u6307\u5b9a\u7ef4\u5ea6\u6307\u6807\u6ca1\u6709\u67e5\u8be2\u5230\u7ef4\u5ea6\u540d\u79f0");
                    return;
                }
                masterKey.setValue(dimension, (Object)value);
                continue;
            }
            dimension = entityMetaService.getDimensionName(entityDefaultValue.getEntityId());
            masterKey.setValue(dimension, (Object)value);
        }
    }

    public static void rebuildMasterKeyByRegion(RegionData regionData, DimensionValueSet masterKey, AbstractRegionRelationEvn regionRelationEvn) {
        List<EntityDefaultValue> regionDefalutSettings = regionData.getRegionEntityDefaultValue();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        if (CollectionUtils.isEmpty(regionDefalutSettings)) {
            return;
        }
        for (EntityDefaultValue entityDefaultValue : regionDefalutSettings) {
            String dimension;
            EntityValueType entityValueType = entityDefaultValue.getEntityValueType();
            String value = entityDefaultValue.getItemValue();
            if (EntityValueType.CUSTOM_STRING == entityValueType) {
                dimension = RegionSettingUtil.getDimensionName(entityDefaultValue.getFieldKey());
                if (dimension == null) {
                    logger.warn("\u533a\u57df\u6307\u5b9a\u7ef4\u5ea6\u6307\u6807\u6ca1\u6709\u67e5\u8be2\u5230\u7ef4\u5ea6\u540d\u79f0");
                    return;
                }
                masterKey.setValue(dimension, (Object)value);
                continue;
            }
            if (EntityValueType.DATA_ITEM_CODE == entityValueType) {
                String fieldKey = entityDefaultValue.getFieldKey();
                LinkData linkData = regionRelationEvn.getDataLinkByFiled(fieldKey);
                if (linkData == null || !StringUtils.isNotEmpty((String)value)) continue;
                DataFormaterCache dataFormaterCache = regionRelationEvn.getDataFormaterCache();
                Object formatData = linkData.getFormatData((AbstractData)new StringData(value), dataFormaterCache);
                Map<String, EntityReturnInfo> entityDataMap = dataFormaterCache.getEntityDataMap();
                EntityReturnInfo entityReturnInfo = entityDataMap.get(entityDefaultValue.getEntityId());
                if (entityReturnInfo == null || entityReturnInfo.getEntitys() == null || entityReturnInfo.getEntitys().size() <= 0 || formatData == null || !StringUtils.isNotEmpty((String)formatData.toString())) continue;
                String dimension2 = entityMetaService.getDimensionName(entityDefaultValue.getEntityId());
                masterKey.setValue(dimension2, (Object)formatData.toString());
                continue;
            }
            dimension = entityMetaService.getDimensionName(entityDefaultValue.getEntityId());
            masterKey.setValue(dimension, (Object)value);
        }
    }

    public static void rebuildMasterKeyByRegion(RegionData regionData, JtableContext jtableContext, DimensionValueSet masterKey, RegionRelation regionRelation) {
        List<EntityDefaultValue> regionDefalutSettings = regionData.getRegionEntityDefaultValue();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        if (CollectionUtils.isEmpty(regionDefalutSettings)) {
            return;
        }
        for (EntityDefaultValue entityDefaultValue : regionDefalutSettings) {
            String dimension;
            EntityValueType entityValueType = entityDefaultValue.getEntityValueType();
            String value = entityDefaultValue.getItemValue();
            if (EntityValueType.CUSTOM_STRING == entityValueType) {
                dimension = RegionSettingUtil.getDimensionName(entityDefaultValue.getFieldKey());
                if (dimension == null) {
                    logger.warn("\u533a\u57df\u6307\u5b9a\u7ef4\u5ea6\u6307\u6807\u6ca1\u6709\u67e5\u8be2\u5230\u7ef4\u5ea6\u540d\u79f0");
                    return;
                }
                masterKey.setValue(dimension, (Object)value);
                continue;
            }
            if (EntityValueType.DATA_ITEM_CODE == entityValueType) {
                String linkKey;
                LinkData linkData;
                String fieldKey = entityDefaultValue.getFieldKey();
                MetaData metaData = regionRelation.getMetaDataByFieldKey(fieldKey);
                if (metaData == null || (linkData = jtableParamService.getLink(linkKey = metaData.getLinkKey())) == null || !StringUtils.isNotEmpty((String)value)) continue;
                DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext);
                Object formatData = linkData.getFormatData((AbstractData)new StringData(value), dataFormaterCache);
                Map<String, EntityReturnInfo> entityDataMap = dataFormaterCache.getEntityDataMap();
                EntityReturnInfo entityReturnInfo = entityDataMap.get(entityDefaultValue.getEntityId());
                if (entityReturnInfo == null || entityReturnInfo.getEntitys() == null || entityReturnInfo.getEntitys().size() <= 0 || formatData == null || !StringUtils.isNotEmpty((String)formatData.toString())) continue;
                String dimension2 = entityMetaService.getDimensionName(entityDefaultValue.getEntityId());
                masterKey.setValue(dimension2, (Object)formatData.toString());
                continue;
            }
            dimension = entityMetaService.getDimensionName(entityDefaultValue.getEntityId());
            masterKey.setValue(dimension, (Object)value);
        }
    }

    public static Object checkRegionDefaultValueGetIfAbsent(RegionData regionData, LinkData linkData) {
        List<EntityDefaultValue> regionDefalutSettings = regionData.getRegionEntityDefaultValue();
        if (CollectionUtils.isEmpty(regionDefalutSettings)) {
            return null;
        }
        if (linkData instanceof EnumLinkData) {
            EnumLinkData enumLinkData = (EnumLinkData)linkData;
            String entityKey = enumLinkData.getEntityKey();
            Optional<EntityDefaultValue> enumEntityDefault = regionDefalutSettings.stream().filter(e -> entityKey.equals(e.getEntityId()) && linkData.getZbid().equals(e.getFieldKey())).findAny();
            if (enumEntityDefault.isPresent()) {
                String defaultVal = enumEntityDefault.get().getItemValue();
                return defaultVal;
            }
        } else {
            Optional<EntityDefaultValue> enumEntityDefault = regionDefalutSettings.stream().filter(e -> linkData.getZbid().equals(e.getFieldKey())).findAny();
            if (enumEntityDefault.isPresent()) {
                String defaultVal = enumEntityDefault.get().getItemValue();
                return defaultVal;
            }
        }
        return null;
    }

    public static boolean checkRegionSettingContainDefaultVal(RegionData regionData, LinkData linkData) {
        List<EntityDefaultValue> regionDefalutSettings = regionData.getRegionEntityDefaultValue();
        if (CollectionUtils.isEmpty(regionDefalutSettings)) {
            return false;
        }
        if (linkData instanceof EnumLinkData) {
            EnumLinkData enumLinkData = (EnumLinkData)linkData;
            String entityKey = enumLinkData.getEntityKey();
            Optional<EntityDefaultValue> enumEntityDefault = regionDefalutSettings.stream().filter(e -> entityKey.equals(e.getEntityId()) && linkData.getZbid().equals(e.getFieldKey())).findAny();
            if (enumEntityDefault.isPresent()) {
                return true;
            }
        } else {
            Optional<EntityDefaultValue> enumEntityDefault = regionDefalutSettings.stream().filter(e -> linkData.getZbid().equals(e.getFieldKey())).findAny();
            if (enumEntityDefault.isPresent()) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkRegionSettingContainDefaultVal(RegionData regionData, FieldData fieldDefine) {
        Optional<EntityDefaultValue> stringDefault;
        Optional<EntityDefaultValue> enumEntityDefault;
        List<EntityDefaultValue> regionDefalutSettings = regionData.getRegionEntityDefaultValue();
        if (CollectionUtils.isEmpty(regionDefalutSettings)) {
            return false;
        }
        return StringUtils.isNotEmpty((String)fieldDefine.getEntityKey()) ? (enumEntityDefault = regionDefalutSettings.stream().filter(e -> fieldDefine.getEntityKey().equals(e.getEntityId()) && fieldDefine.getFieldKey().equals(e.getFieldKey())).findAny()).isPresent() : (stringDefault = regionDefalutSettings.stream().filter(e -> fieldDefine.getFieldKey().equals(e.getFieldKey())).findAny()).isPresent();
    }

    private static String getDimensionName(String fieldKey) {
        try {
            IDataAccessProvider provider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
            IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
            com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(definitionRuntimeController);
            IDataAssist dataAssist = provider.newDataAssist((ExecutorContext)context);
            FieldDefine field = definitionRuntimeController.queryFieldDefine(fieldKey);
            return dataAssist.getDimensionName(field);
        }
        catch (Exception e) {
            logger.error("\u533a\u57df\u6307\u5b9a\u7ef4\u5ea6\u6307\u6807\u67e5\u8be2\u9519\u8bef");
            return null;
        }
    }
}

