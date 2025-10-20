/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.cache.BaseDataCache
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.cache.BaseDataCache;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class BaseDataUtils {
    public static Set<String> getAllChildrenContainSelfByCodes(String tableName, Collection<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return Collections.emptySet();
        }
        HashSet<String> allCodes = new HashSet<String>();
        codes.stream().forEach(code -> allCodes.addAll(BaseDataUtils.getAllChildrenContainSelf(tableName, code)));
        return allCodes;
    }

    public static List<String> getAllChildrenContainSelf(String tableName, String subjectCode) {
        ArrayList<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(subjectCode)) {
            return list;
        }
        list.add(subjectCode);
        list.addAll(BaseDataUtils.queryAllCodesByParentid(tableName, subjectCode));
        return list;
    }

    public static List<String> getAllChildren(String tableName, String subjectCode) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(subjectCode)) {
            return list;
        }
        list = BaseDataUtils.queryAllCodesByParentid(tableName, subjectCode);
        return list;
    }

    private static List<String> queryAllCodesByParentid(String tableName, String entityKeyData) {
        List gcBaseDataList = GcBaseDataCenterTool.getInstance().queryBasedataItemsByParentid(tableName, entityKeyData);
        if (CollectionUtils.isEmpty(gcBaseDataList)) {
            return Collections.emptyList();
        }
        return gcBaseDataList.stream().map(GcBaseData::getCode).collect(Collectors.toList());
    }

    public static List<BaseDataVO> getBaseDataVoListCodeByJsonNode(List<String> codes, String tableName, List<BaseDataVO> allBaseDataVos) {
        if (CollectionUtils.isEmpty(codes) || CollectionUtils.isEmpty(allBaseDataVos)) {
            return Collections.emptyList();
        }
        return codes.stream().map(code -> {
            Optional<BaseDataVO> optional = allBaseDataVos.stream().filter(baseDataVo -> baseDataVo.getCode().equals(code)).findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<BaseDataVO> getBaseDataVoListContainChildByJsonNode(JsonNode jsonNode, String tableName) {
        ArrayList<BaseDataVO> baseDataVos = new ArrayList<BaseDataVO>();
        if (!jsonNode.isArray()) {
            return baseDataVos;
        }
        ArrayList<String> codes = new ArrayList<String>();
        Iterator it = jsonNode.iterator();
        while (it.hasNext()) {
            codes.add(((JsonNode)it.next()).asText());
        }
        codes.stream().forEach(code -> {
            List list;
            BaseDataVO parent = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode(tableName, code));
            if (parent != null) {
                baseDataVos.add(parent);
            }
            if (!CollectionUtils.isEmpty(list = GcBaseDataCenterTool.getInstance().convertListBaseDataVO(GcBaseDataCenterTool.getInstance().queryAllBasedataItemsByParentid(tableName, code)))) {
                baseDataVos.addAll(list);
            }
        });
        return baseDataVos;
    }

    public static List<BaseDataVO> getBaseDataVoListByJsonNode(JsonNode jsonNode, String tableName) {
        ArrayList<BaseDataVO> baseDataVos = new ArrayList<BaseDataVO>();
        if (!jsonNode.isArray()) {
            return baseDataVos;
        }
        ArrayList<String> codes = new ArrayList<String>();
        Iterator it = jsonNode.iterator();
        while (it.hasNext()) {
            codes.add(((JsonNode)it.next()).asText());
        }
        codes.stream().forEach(code -> {
            GcBaseData baseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode(tableName, code);
            if (baseData != null) {
                BaseDataVO baseDataVO = GcBaseDataCenterTool.getInstance().convertBaseDataVO(baseData);
                baseDataVos.add(baseDataVO);
            }
        });
        return baseDataVos;
    }

    public static List<String> getBaseDataCodeOnlyParent(List<BaseDataVO> baseDataVos, String tableName) {
        HashSet baseDataCodes = new HashSet();
        if (CollectionUtils.isEmpty(baseDataVos)) {
            return new ArrayList<String>(baseDataCodes);
        }
        baseDataVos.stream().forEach(baseDataVo -> baseDataCodes.add(baseDataVo.getCode()));
        ArrayList removedCodes = new ArrayList();
        baseDataVos.stream().forEach(baseDataVo -> {
            if (removedCodes.contains(baseDataVo.getCode())) {
                return;
            }
            List<String> childrenAndSelfCodes = BaseDataUtils.queryAllCodesByParentid(tableName, baseDataVo.getCode());
            ArrayList thisTimeNeedRemovedCodes = new ArrayList();
            childrenAndSelfCodes.stream().forEach(code -> {
                if (!code.equals(baseDataVo.getCode()) && baseDataCodes.contains(code)) {
                    thisTimeNeedRemovedCodes.add(code);
                }
            });
            removedCodes.addAll(thisTimeNeedRemovedCodes);
            baseDataCodes.removeAll(thisTimeNeedRemovedCodes);
        });
        return new ArrayList<String>(baseDataCodes);
    }

    public static String getDictTitle(String tableCode, String dictCode) {
        GcBaseData baseData = ((BaseDataCache)SpringContextUtils.getBean(BaseDataCache.class)).queryBaseDataByCode(tableCode, dictCode);
        return null == baseData ? null : baseData.getTitle();
    }
}

