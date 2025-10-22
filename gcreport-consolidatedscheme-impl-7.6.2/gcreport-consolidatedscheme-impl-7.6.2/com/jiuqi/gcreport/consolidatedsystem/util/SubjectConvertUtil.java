/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  org.apache.commons.lang3.StringUtils
 *  org.thymeleaf.util.MapUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.OrderNumUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.thymeleaf.util.MapUtils;

public class SubjectConvertUtil {
    public static BaseDataDTO convertGcSubjectEOToBaseDataDTO(ConsolidatedSubjectEO subject) {
        BaseDataDTO dto = new BaseDataDTO();
        dto.setTableName("MD_GCSUBJECT");
        dto.setObjectcode(subject.getObjectCode());
        dto.put("sharefields", (Object)CollectionUtils.newArrayList((Object[])new String[]{"systemid"}));
        dto.setId(UUIDUtils.fromString36((String)subject.getId()));
        dto.put("attri", (Object)subject.getAttri());
        dto.put("boundindexpath", (Object)subject.getBoundIndexPath());
        dto.setCode(subject.getCode());
        dto.put("consolidationflag", (Object)ConverterUtils.getAsInteger((Object)subject.getConsolidationFlag(), (Integer)0));
        dto.put("consolidationtype", (Object)subject.getConsolidationType());
        dto.setCreatetime(subject.getCreatetime());
        dto.setCreateuser(subject.getCreateuser());
        dto.put("formula", (Object)subject.getFormula());
        dto.setOrdinal(subject.getOrdinal());
        dto.put("orient", (Object)subject.getOrient());
        dto.setParentcode(subject.getParentCode());
        dto.put("systemid", (Object)subject.getSystemId());
        dto.setName(subject.getTitle());
        dto.put("globaladd", (Object)(subject.getGlobalAdd() == null || subject.getGlobalAdd() == false ? 0 : 1));
        dto.put("asstype", (Object)subject.getAsstype());
        if (!MapUtils.isEmpty(subject.getMultilingualNames())) {
            dto.putAll(subject.getMultilingualNames());
        }
        return dto;
    }

    public static BaseDataDO convertGcSubjectEOToBaseDataDO(ConsolidatedSubjectEO subject) {
        BaseDataDO bdo = new BaseDataDO();
        bdo.setTableName("MD_GCSUBJECT");
        bdo.put("sharefields", (Object)CollectionUtils.newArrayList((Object[])new String[]{"systemid"}));
        bdo.setId(UUIDUtils.fromString36((String)subject.getId()));
        bdo.put("attri", (Object)subject.getAttri());
        bdo.put("boundindexpath", (Object)subject.getBoundIndexPath());
        bdo.setCode(subject.getCode());
        bdo.put("consolidationflag", (Object)ConverterUtils.getAsInteger((Object)subject.getConsolidationFlag(), (Integer)0));
        bdo.put("consolidationtype", (Object)subject.getConsolidationType());
        bdo.setCreatetime(subject.getCreatetime());
        bdo.setCreateuser(subject.getCreateuser());
        bdo.put("formula", (Object)subject.getFormula());
        bdo.setOrdinal(subject.getOrdinal());
        bdo.put("orient", (Object)subject.getOrient());
        bdo.setParentcode(subject.getParentCode());
        bdo.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        bdo.put("systemid", (Object)subject.getSystemId());
        bdo.setName(subject.getTitle());
        bdo.setObjectcode(subject.getObjectCode());
        return bdo;
    }

    public static ConsolidatedSubjectEO convertBdoToGcSubjectEO(BaseDataDO subjectDo) {
        ConsolidatedSubjectEO subjectEO = new ConsolidatedSubjectEO();
        subjectEO.setAttri(ConverterUtils.getAsInteger((Object)subjectDo.get((Object)"attri")));
        subjectEO.setBoundIndexPath(ConverterUtils.getAsString((Object)subjectDo.get((Object)"boundindexpath")));
        subjectEO.setCode(subjectDo.getCode());
        subjectEO.setConsolidationFlag(ConverterUtils.getAsBoolean((Object)subjectDo.get((Object)"consolidationflag"), (Boolean)false));
        subjectEO.setConsolidationType(ConverterUtils.getAsInteger((Object)subjectDo.get((Object)"consolidationtype")));
        subjectEO.setCreatetime(subjectDo.getCreatetime());
        subjectEO.setCreateuser(subjectDo.getCreateuser());
        subjectEO.setFormula(ConverterUtils.getAsString((Object)subjectDo.get((Object)"formula")));
        subjectEO.setId(UUIDUtils.toString36((UUID)subjectDo.getId()));
        subjectEO.setOrdinal(subjectDo.getOrdinal());
        subjectEO.setOrient(ConverterUtils.getAsInteger((Object)subjectDo.get((Object)"orient")));
        subjectEO.setParentCode(subjectDo.getParentcode());
        subjectEO.setSystemId(ConverterUtils.getAsString((Object)subjectDo.get((Object)"systemid")));
        subjectEO.setTitle(subjectDo.getLocalizedName());
        subjectEO.setObjectCode(subjectEO.getCode() + "||" + subjectEO.getSystemId());
        subjectEO.setGlobalAdd(ConverterUtils.getAsBoolean((Object)subjectDo.get((Object)"globaladd")));
        subjectEO.setAsstype(ConverterUtils.getAsString((Object)subjectDo.get((Object)"asstype")));
        subjectEO.setParents(subjectDo.getParents());
        HashMap<String, String> multilingualNames = new HashMap<String, String>();
        for (String key : subjectDo.keySet()) {
            if (!key.startsWith("name_")) continue;
            multilingualNames.put(key, ConverterUtils.getAsString((Object)subjectDo.get((Object)key)));
        }
        subjectEO.setMultilingualNames(multilingualNames);
        return subjectEO;
    }

    public static ConsolidatedSubjectVO convertEO2VO(ConsolidatedSubjectEO eo) {
        if (eo == null) {
            return null;
        }
        ConsolidatedSubjectVO vo = new ConsolidatedSubjectVO();
        BeanUtils.copyProperties(eo, vo);
        if (!StringUtils.isEmpty((CharSequence)vo.getAsstype())) {
            DimensionService dimensionService = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
            Map asstypeMap = (Map)JsonUtils.readValue((String)vo.getAsstype(), Map.class);
            String asstypeTitles = "";
            for (String asstYype : asstypeMap.keySet()) {
                DimensionVO dimensionEO = dimensionService.getDimensionByCode(asstYype);
                if (dimensionEO == null) continue;
                asstypeTitles = asstypeTitles.concat(dimensionEO.getTitle()).concat(",");
            }
            if (asstypeTitles.length() != 0) {
                asstypeTitles = asstypeTitles.substring(0, asstypeTitles.length() - 1);
            }
            vo.setAsstypeTitles(asstypeTitles);
        }
        return vo;
    }
}

