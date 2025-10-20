/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.ReflectionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.ReflectionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.offsetitem.cache.OffSetUnSysDimensionCache;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import com.jiuqi.gcreport.offsetitem.utils.OrgPeriodUtil;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class OffsetConvertUtil {
    private static OffsetConvertUtil showOneUnitUtil;
    private static OffsetConvertUtil showTwoUnitUtil;
    private boolean showOneUnit = true;

    private OffsetConvertUtil() {
    }

    public static OffsetConvertUtil getInstance() {
        if (null == showOneUnitUtil) {
            showOneUnitUtil = new OffsetConvertUtil();
            OffsetConvertUtil.showOneUnitUtil.showOneUnit = true;
        }
        return showOneUnitUtil;
    }

    public static OffsetConvertUtil getShowTwoUnitInstance() {
        if (null == showTwoUnitUtil) {
            showTwoUnitUtil = new OffsetConvertUtil();
            OffsetConvertUtil.showTwoUnitUtil.showOneUnit = false;
        }
        return showTwoUnitUtil;
    }

    public GcOffSetVchrDTO convertVO2DTO(GcOffSetVchrVO vchrVO) {
        List itemVOs = vchrVO.getItems();
        this.checkValid(itemVOs);
        ArrayList<GcOffSetVchrItemDTO> itemDTOs = new ArrayList<GcOffSetVchrItemDTO>();
        HashSet<String> unitIdSet = new HashSet<String>();
        for (Object vo : itemVOs) {
            String unitCode = "";
            String oppUnitCode = "";
            if (!StringUtils.isEmpty((String)vo.getUnitCode())) {
                unitCode = vo.getUnitCode();
            } else if (vo.getOffSetSrcType() == OffSetSrcTypeEnum.OFFSET_ITEM_INIT.getSrcTypeValue() && vo.getUnitVo() != null && !StringUtils.isEmpty((String)vo.getUnitVo().getCode())) {
                unitCode = vo.getUnitVo().getCode();
            }
            if (!StringUtils.isEmpty((String)vo.getOppUnitCode())) {
                oppUnitCode = vo.getOppUnitCode();
            } else if (vo.getOffSetSrcType() == OffSetSrcTypeEnum.OFFSET_ITEM_INIT.getSrcTypeValue() && vo.getOppUnitVo() != null && !StringUtils.isEmpty((String)vo.getOppUnitVo().getCode())) {
                oppUnitCode = vo.getOppUnitVo().getCode();
            }
            unitIdSet.add(unitCode);
            unitIdSet.add(oppUnitCode);
        }
        unitIdSet.remove("");
        if (unitIdSet.size() != 2) {
            throw new BusinessRuntimeException("\u672c\u5bf9\u65b9\u5355\u4f4d\u6709\u4e14\u4ec5\u80fd\u6709\u4e24\u5bb6\u5355\u4f4d");
        }
        ArrayList unitIdList = new ArrayList(unitIdSet);
        for (GcOffSetVchrItemVO vo : itemVOs) {
            if (StringUtils.isEmpty((String)vo.getUnitCode())) {
                vo.setUnitCode(((String)unitIdList.get(0)).equals(vo.getOppUnitCode()) ? (String)unitIdList.get(1) : (String)unitIdList.get(0));
                continue;
            }
            if (!StringUtils.isEmpty((String)vo.getOppUnitCode())) continue;
            vo.setOppUnitCode(((String)unitIdList.get(0)).equals(vo.getUnitCode()) ? (String)unitIdList.get(1) : (String)unitIdList.get(0));
        }
        if (itemVOs != null && itemVOs.size() > 0) {
            for (GcOffSetVchrItemVO itemVO : itemVOs) {
                itemDTOs.add(this.convertVO2DTO(itemVO));
            }
        }
        GcOffSetVchrDTO dto = new GcOffSetVchrDTO();
        if (!StringUtils.isEmpty((String)vchrVO.getMrecid())) {
            dto.setMrecid(vchrVO.getMrecid());
        }
        dto.setItems(itemDTOs);
        return dto;
    }

    public GcOffSetVchrItemVO convertDTO2VO(GcOffSetVchrItemDTO itemDTO) {
        ConsolidatedSubjectEO subjectEO;
        if (itemDTO == null) {
            return null;
        }
        GcOffSetVchrItemVO itemVo = new GcOffSetVchrItemVO();
        BeanUtils.copyProperties(itemDTO, itemVo);
        itemVo.setElmModeTitle(OffsetElmModeEnum.getElmModeTitle((Integer)itemDTO.getElmMode()));
        itemVo.setOffSetSrcType(OffSetSrcTypeEnum.getEnumValue((OffSetSrcTypeEnum)itemDTO.getOffSetSrcType()));
        itemVo.setOffSetSrcTypeName(OffSetSrcTypeEnum.getEnumByValue((int)itemDTO.getOffSetSrcType().getSrcTypeValue()).getSrcTypeName());
        this.setUnit(itemDTO, itemVo);
        if (itemVo.getOffSetCredit() != null && itemVo.getOffSetCredit() == 0.0) {
            itemVo.setOffSetCredit(null);
        }
        if (itemVo.getOffSetDebit() != null && itemVo.getOffSetDebit() == 0.0) {
            itemVo.setOffSetDebit(null);
        }
        if (itemDTO.getOrient() == OrientEnum.D && itemDTO.getOffSetDebit() == 0.0) {
            itemVo.setOffSetDebit(new Double(0.0));
            itemVo.setOffSetCredit(null);
        } else if (itemDTO.getOrient() == OrientEnum.C && itemDTO.getOffSetCredit() == 0.0) {
            itemVo.setOffSetCredit(new Double(0.0));
            itemVo.setOffSetDebit(null);
        }
        AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)itemVo.getRuleId());
        if (rule != null) {
            itemVo.setRuleTitle(rule.getTitle());
            itemVo.setRuleParentId(rule.getParentId());
        }
        if ((subjectEO = ((ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).getSubjectByCode(itemVo.getSystemId(), itemVo.getSubjectCode())) == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u4ee3\u7801\u4e3a[" + itemVo.getSubjectCode() + "]\u7684\u5408\u5e76\u79d1\u76ee\u3002");
        }
        GcBaseDataVO subjVo = new GcBaseDataVO();
        BeanUtils.copyProperties(subjectEO, subjVo);
        itemVo.setSubjectName(subjVo == null ? "" : subjVo.getTitle());
        itemVo.setSubjectCode(subjVo.getCode());
        itemVo.setSubjectVo(subjVo);
        OffSetUnSysDimensionCache.load();
        List dimensionVOs = OffSetUnSysDimensionCache.allDimValue((String)itemDTO.getSystemId());
        if (!CollectionUtils.isEmpty(dimensionVOs)) {
            GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
            dimensionVOs.forEach(dimensionVO -> {
                String dictTableName = dimensionVO.getDictTableName();
                String fieldCode = dimensionVO.getCode().toUpperCase();
                Object fieldDictValue = itemDTO.getFieldValue(fieldCode);
                if (!StringUtils.isEmpty((String)dictTableName)) {
                    GcBaseData baseData;
                    if (fieldDictValue != null && !StringUtils.isEmpty((String)fieldDictValue.toString()) && null != (baseData = tool.queryBasedataByCode(dictTableName, String.valueOf(fieldDictValue)))) {
                        itemVo.addUnSysFieldValue(fieldCode, (Object)baseData);
                    }
                } else {
                    itemVo.addUnSysFieldValue(fieldCode, fieldDictValue);
                }
            });
        }
        return itemVo;
    }

    private void setUnit(GcOffSetVchrItemDTO itemDTO, GcOffSetVchrItemVO itemVo) {
        YearPeriodObject yp = new YearPeriodObject(null, OrgPeriodUtil.getQueryOrgPeriod(itemDTO.getDefaultPeriod()));
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)itemDTO.getUnitVersion(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitVo = tool.getOrgByID(itemDTO.getUnitId());
        GcOrgCacheVO oppUnitVo = tool.getOrgByID(itemDTO.getOppUnitId());
        itemVo.setUnitName(unitVo == null ? "" : unitVo.getTitle());
        itemVo.setUnitCode(unitVo == null || unitVo.getCode() == null ? "" : unitVo.getCode());
        itemVo.setUnitId(unitVo == null ? null : unitVo.getId());
        itemVo.setUnitVo(unitVo);
        itemVo.setOppUnitName(oppUnitVo == null ? "" : oppUnitVo.getTitle());
        itemVo.setOppUnitCode(oppUnitVo == null || oppUnitVo.getCode() == null ? "" : oppUnitVo.getCode());
        itemVo.setOppUnitId(oppUnitVo == null ? null : oppUnitVo.getId());
        itemVo.setOppUnitVo(oppUnitVo);
    }

    public GcOffSetVchrVO convertDTO2VO(GcOffSetVchrDTO dto) {
        List itemVOs = null;
        List itemDTOs = dto.getItems();
        if (itemDTOs != null && itemDTOs.size() > 0) {
            itemVOs = itemDTOs.stream().map(itemDTO -> this.convertDTO2VO((GcOffSetVchrItemDTO)itemDTO)).collect(Collectors.toList());
        }
        GcOffSetVchrVO vo = new GcOffSetVchrVO();
        vo.setMrecid(dto.getMrecid());
        vo.setItems(itemVOs);
        return vo;
    }

    public GcOffSetVchrItemDTO convertVO2DTO(GcOffSetVchrItemVO itemVo) {
        if (itemVo == null) {
            return null;
        }
        GcOffSetVchrItemDTO itemDTO = new GcOffSetVchrItemDTO();
        if (!StringUtils.isEmpty((String)itemVo.getUnitCode())) {
            itemVo.setUnitId(itemVo.getUnitCode());
        }
        if (!StringUtils.isEmpty((String)itemVo.getOppUnitCode())) {
            itemVo.setOppUnitId(itemVo.getOppUnitCode());
        }
        itemVo.setSubjectCode(itemVo.getSubjectVo().getCode());
        BeanUtils.copyProperties(itemVo, itemDTO);
        if (OffSetSrcTypeEnum.OFFSET_ITEM_INIT.getSrcTypeValue() == itemVo.getOffSetSrcType()) {
            if (itemVo.getOffSetCredit() == null) {
                itemDTO.setOrient(OrientEnum.D);
            } else if (itemVo.getOffSetDebit() == null) {
                itemDTO.setOrient(OrientEnum.C);
            }
        }
        Map unSysFields = itemVo.getUnSysFields();
        for (Map.Entry unSysField : unSysFields.entrySet()) {
            if (unSysField.getValue() instanceof Map) {
                itemDTO.addFieldValue((String)unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
            } else {
                itemDTO.addFieldValue((String)unSysField.getKey(), unSysField.getValue());
            }
            if (!StringUtils.isEmpty((String)ConverterUtils.getAsString((Object)itemDTO.getFieldValue((String)unSysField.getKey())))) continue;
            itemDTO.addFieldValue((String)unSysField.getKey(), null);
        }
        if (OffSetSrcTypeEnum.INVENTORY_OFFSET_ITEM_INIT.getSrcTypeValue() == itemVo.getOffSetSrcType()) {
            itemDTO.setOrgType(GCOrgTypeEnum.NONE.getCode());
        }
        itemDTO.setOffSetSrcType(OffSetSrcTypeEnum.getEnumByValue((int)itemVo.getOffSetSrcType()));
        return itemDTO;
    }

    private void checkValid(List<GcOffSetVchrItemVO> itemVOs) {
        double debitSum = 0.0;
        double creditSum = 0.0;
        for (GcOffSetVchrItemVO vo : itemVOs) {
            if (vo.getSubjectVo() == null) {
                throw new BusinessRuntimeException("\u7b2c" + vo.getSortOrder().intValue() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u79d1\u76ee\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
            }
            if (vo.getOffSetSrcType() != OffSetSrcTypeEnum.OFFSET_ITEM_INIT.getSrcTypeValue() && StringUtils.isEmpty((String)vo.getUnitCode()) && StringUtils.isEmpty((String)vo.getOppUnitCode()) || vo.getOffSetSrcType() == OffSetSrcTypeEnum.OFFSET_ITEM_INIT.getSrcTypeValue() && vo.getUnitVo() == null && vo.getOppUnitVo() == null) {
                throw new BusinessRuntimeException("\u7b2c" + vo.getSortOrder().intValue() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u672c\u5bf9\u65b9\u5355\u4f4d\u5fc5\u987b\u6709\u4e00\u65b9\u6709\u503c\u3002");
            }
            creditSum = NumberUtils.sum((double)creditSum, (double)(vo.getOffSetCredit() != null ? vo.getOffSetCredit() : 0.0));
            debitSum = NumberUtils.sum((double)debitSum, (double)(vo.getOffSetDebit() != null ? vo.getOffSetDebit() : 0.0));
        }
        if (NumberUtils.sub((double)creditSum, (double)debitSum) != 0.0) {
            throw new BusinessRuntimeException("\u501f\u8d37\u91d1\u989d\u4e0d\u76f8\u7b49\u3002");
        }
    }

    public GcOffSetVchrItemAdjustEO convertDTO2EO(GcOffSetVchrItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }
        GcOffSetVchrItemAdjustEO itemEo = new GcOffSetVchrItemAdjustEO();
        HashMap fields = new HashMap();
        fields.putAll(itemDTO.getFields());
        BeanUtils.copyProperties(itemDTO, itemEo);
        itemEo.resetFields(fields);
        if (null == itemEo.getSrcOffsetGroupId()) {
            itemEo.setSrcOffsetGroupId(itemEo.getmRecid());
        }
        Map unSysFields = itemDTO.getUnSysFields();
        for (Map.Entry unSysField : unSysFields.entrySet()) {
            if (unSysField.getValue() instanceof Map) {
                itemEo.addFieldValue((String)unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
                continue;
            }
            itemEo.addFieldValue((String)unSysField.getKey(), unSysField.getValue());
        }
        itemEo.setOffSetSrcType(Integer.valueOf(OffSetSrcTypeEnum.getEnumValue((OffSetSrcTypeEnum)itemDTO.getOffSetSrcType())));
        ReflectionUtils.setFieldValue((Object)itemEo, (String)"offSetCredit", (Object)itemDTO.getOffSetCredit());
        ReflectionUtils.setFieldValue((Object)itemEo, (String)"offSetDebit", (Object)itemDTO.getOffSetDebit());
        ReflectionUtils.setFieldValue((Object)itemEo, (String)"diffc", (Object)itemDTO.getDiffc());
        ReflectionUtils.setFieldValue((Object)itemEo, (String)"diffd", (Object)itemDTO.getDiffd());
        this.initNullValue(itemEo);
        if (itemDTO.getDisableFlag() == null) {
            itemEo.setDisableFlag(Integer.valueOf(0));
        } else {
            itemEo.setDisableFlag(Integer.valueOf(itemDTO.getDisableFlag() != false ? 1 : 0));
        }
        itemEo.setSubjectOrient(itemDTO.getSubjectOrient().getValue());
        return itemEo;
    }

    private void initNullValue(GcOffSetVchrItemAdjustEO item) {
        if (item.getOffSetDebit() == null) {
            item.setOffSetDebit(Double.valueOf(0.0));
        }
        if (item.getDiffd() == null) {
            item.setDiffd(Double.valueOf(0.0));
        }
        if (item.getOffSetCredit() == null) {
            item.setOffSetCredit(Double.valueOf(0.0));
        }
        if (item.getDiffc() == null) {
            item.setDiffc(Double.valueOf(0.0));
        }
    }

    public GcOffSetVchrItemDTO convertEO2DTO(GcOffSetVchrItemAdjustEO itemEO) {
        if (itemEO == null) {
            return null;
        }
        GcOffSetVchrItemDTO itemDTO = new GcOffSetVchrItemDTO();
        BeanUtils.copyProperties(itemEO, itemDTO);
        itemDTO.setDisableFlag(Boolean.valueOf(Objects.equals(itemEO.getDisableFlag(), 1)));
        itemDTO.resetFields(new HashMap());
        itemDTO.getFields().putAll(itemEO.getFields());
        itemDTO.setOffSetSrcType(OffSetSrcTypeEnum.getEnumByValue((int)itemEO.getOffSetSrcType()));
        itemDTO.setOffSetCredit((Double)ReflectionUtils.getFieldValue((Object)itemEO, (String)"offSetCredit"));
        itemDTO.setOffSetDebit((Double)ReflectionUtils.getFieldValue((Object)itemEO, (String)"offSetDebit"));
        itemDTO.setDiffc((Double)ReflectionUtils.getFieldValue((Object)itemEO, (String)"diffc"));
        itemDTO.setDiffd((Double)ReflectionUtils.getFieldValue((Object)itemEO, (String)"diffd"));
        if (OffSetSrcTypeEnum.OFFSET_ITEM_INIT.getSrcTypeValue() == itemEO.getOffSetSrcType().intValue()) {
            if (OrientEnum.D.getValue().equals(itemEO.getOrient())) {
                itemDTO.setOrient(OrientEnum.D);
            } else if (OrientEnum.C.getValue().equals(itemEO.getOrient())) {
                itemDTO.setOrient(OrientEnum.C);
            }
        }
        return itemDTO;
    }

    public static List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<Map<String, Object>> sortedRecords = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> oneEntryRecords = new ArrayList<Map<String, Object>>();
        String mrecid = null;
        int entryIndex = 1;
        for (Map<String, Object> record : unSortedRecords) {
            String tempMrecid = (String)record.get("MRECID");
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    OffsetItemComparatorUtil.mapSortComparator(oneEntryRecords);
                    sortedRecords.addAll(oneEntryRecords);
                    ((Map)oneEntryRecords.get(0)).put("rowspan", size);
                    ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
                    oneEntryRecords.clear();
                }
                mrecid = tempMrecid;
            }
            oneEntryRecords.add(record);
        }
        int size = oneEntryRecords.size();
        if (size > 0) {
            sortedRecords.addAll(oneEntryRecords);
            ((Map)oneEntryRecords.get(0)).put("rowspan", size);
            ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
    }

    public static List<GcOffSetVchrItemDTO> setObjectRowSpanAndSort(List<GcOffSetVchrItemDTO> unSortedRecords) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<GcOffSetVchrItemDTO> sortedRecords = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<GcOffSetVchrItemDTO> oneEntryRecords = new ArrayList<GcOffSetVchrItemDTO>();
        String mrecid = null;
        int entryIndex = 1;
        for (GcOffSetVchrItemDTO record : unSortedRecords) {
            String tempMrecid = record.getmRecid();
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    sortedRecords.addAll(oneEntryRecords);
                    ((GcOffSetVchrItemDTO)oneEntryRecords.get(0)).addFieldValue("rowspan", (Object)size);
                    ((GcOffSetVchrItemDTO)oneEntryRecords.get(0)).addFieldValue("index", (Object)entryIndex++);
                    oneEntryRecords.clear();
                }
                mrecid = tempMrecid;
            }
            oneEntryRecords.add(record);
        }
        int size = oneEntryRecords.size();
        if (size > 0) {
            sortedRecords.addAll(oneEntryRecords);
            ((GcOffSetVchrItemDTO)oneEntryRecords.get(0)).addFieldValue("rowspan", (Object)size);
            ((GcOffSetVchrItemDTO)oneEntryRecords.get(0)).addFieldValue("index", (Object)entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        unSortedRecords.addAll(sortedRecords);
        return sortedRecords;
    }
}

