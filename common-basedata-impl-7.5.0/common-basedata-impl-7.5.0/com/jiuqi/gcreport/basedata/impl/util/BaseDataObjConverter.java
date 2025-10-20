/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.api.enums.QueryDataType
 *  com.jiuqi.gcreport.basedata.api.enums.RangeType
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.ITree
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$OrderType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO
 */
package com.jiuqi.gcreport.basedata.impl.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.api.enums.QueryDataType;
import com.jiuqi.gcreport.basedata.api.enums.RangeType;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.ITree;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.util.StringUtils;

public class BaseDataObjConverter {
    public static BaseDataDTO convert(GcBaseDataDO baseDataDO) {
        BaseDataDTO vaBaseDataDTO;
        block23: {
            block25: {
                block24: {
                    AuthType authType;
                    QueryDataType queryDataType;
                    vaBaseDataDTO = new BaseDataDTO();
                    vaBaseDataDTO.setTableName(baseDataDO.getTableName());
                    vaBaseDataDTO.setId(!StringUtils.hasLength(baseDataDO.getId()) ? null : UUID.fromString(baseDataDO.getId()));
                    vaBaseDataDTO.setCode(baseDataDO.getCode());
                    vaBaseDataDTO.setName(baseDataDO.getTitle());
                    vaBaseDataDTO.setObjectcode(baseDataDO.getObjectCode());
                    vaBaseDataDTO.setShortname(baseDataDO.getShortName());
                    vaBaseDataDTO.setValidtime(DateUtils.convertLDTToDate((LocalDateTime)baseDataDO.getValidTime()));
                    vaBaseDataDTO.setInvalidtime(DateUtils.convertLDTToDate((LocalDateTime)baseDataDO.getInValidTime()));
                    vaBaseDataDTO.setVersionDate(DateUtils.convertLDTToDate((LocalDate)baseDataDO.getVersionDate()));
                    vaBaseDataDTO.setParentcode(baseDataDO.getParentid());
                    vaBaseDataDTO.setOrdinal(baseDataDO.getOrdinal());
                    vaBaseDataDTO.setCreatetime(DateUtils.convertLDTToDate((LocalDateTime)baseDataDO.getCreateTime()));
                    vaBaseDataDTO.setParents(baseDataDO.getParents());
                    vaBaseDataDTO.setUnitcode(baseDataDO.getUnitCode());
                    vaBaseDataDTO.setVer(baseDataDO.getVer());
                    vaBaseDataDTO.setRecoveryflag(Integer.valueOf(0));
                    if (baseDataDO.isStop() != null) {
                        vaBaseDataDTO.setStopflag(Integer.valueOf(baseDataDO.isStop() != false ? 1 : 0));
                    } else {
                        vaBaseDataDTO.setStopflag(Integer.valueOf(-1));
                    }
                    if (baseDataDO.getFieldValMap() != null) {
                        vaBaseDataDTO.putAll(baseDataDO.getFieldValMap());
                    }
                    if (!(baseDataDO instanceof GcBaseDataDTO)) break block23;
                    vaBaseDataDTO.setSearchKey(((GcBaseDataDTO)baseDataDO).getSearchKey());
                    vaBaseDataDTO.setBaseDataCodes(((GcBaseDataDTO)baseDataDO).getBaseDataCodes());
                    vaBaseDataDTO.setLazyLoad(Boolean.valueOf(((GcBaseDataDTO)baseDataDO).isLazyLoad()));
                    if (((GcBaseDataDTO)baseDataDO).isOrdered()) {
                        BaseDataSortDTO sortDTO = new BaseDataSortDTO("ordinal", BaseDataOption.OrderType.ASC);
                        vaBaseDataDTO.setOrderBy(Collections.singletonList(sortDTO));
                    }
                    if ((queryDataType = ((GcBaseDataDTO)baseDataDO).getQueryDataType()) != null) {
                        switch (queryDataType) {
                            case SIMPLE: {
                                vaBaseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.SIMPLE);
                                break;
                            }
                            case ALL: {
                                vaBaseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                                break;
                            }
                            case ALL_WITH_REF: {
                                vaBaseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL_WITH_REF);
                                break;
                            }
                            default: {
                                throw new BusinessRuntimeException("not support queryDataType");
                            }
                        }
                    }
                    if ((authType = ((GcBaseDataDTO)baseDataDO).getAuthType()) == null) break block24;
                    switch (authType) {
                        case NONE: {
                            vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
                            break block25;
                        }
                        case ACCESS: {
                            vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.ACCESS);
                            break block25;
                        }
                        case WRITE: {
                            vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.WRITE);
                            break block25;
                        }
                        case MANAGE: {
                            vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.MANAGE);
                            break block25;
                        }
                        default: {
                            throw new BusinessRuntimeException("not support authType");
                        }
                    }
                }
                vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
            }
            RangeType rangeType = ((GcBaseDataDTO)baseDataDO).getRangeType();
            if (rangeType != null) {
                switch (rangeType) {
                    case DIRECT_CHILDREN: {
                        vaBaseDataDTO.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
                        break;
                    }
                    case DIRECT_CHILDREN_WITH_SELF: {
                        vaBaseDataDTO.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF);
                        break;
                    }
                    case ALL_CHILDREN: {
                        vaBaseDataDTO.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
                        break;
                    }
                    case ALL_CHILDREN_WITH_SELF: {
                        vaBaseDataDTO.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
                        break;
                    }
                    default: {
                        throw new BusinessRuntimeException("not support rangeType");
                    }
                }
            }
        }
        return vaBaseDataDTO;
    }

    public static GcBaseData convert(BaseDataDO vaBaseData) {
        GcBaseDataDO baseDataDO = new GcBaseDataDO();
        baseDataDO.setTableName(vaBaseData.getTableName());
        baseDataDO.setCode(vaBaseData.getCode());
        baseDataDO.setObjectCode(vaBaseData.getObjectcode());
        baseDataDO.setTitle(vaBaseData.getLocalizedName());
        baseDataDO.setCreateTime(DateUtils.convertDateToLDT((Date)vaBaseData.getCreatetime()));
        baseDataDO.setCreatorId(vaBaseData.getCreateuser());
        baseDataDO.setId(vaBaseData.getId().toString());
        baseDataDO.setKey(vaBaseData.getId().toString());
        baseDataDO.setOrdinal(vaBaseData.getOrdinal());
        baseDataDO.setShortName(vaBaseData.getShortname());
        baseDataDO.setStop(Integer.valueOf(1).equals(vaBaseData.getStopflag()));
        baseDataDO.setParentid(vaBaseData.getParentcode());
        baseDataDO.setValidTime(DateUtils.convertDateToLDT((Date)vaBaseData.getValidtime()));
        baseDataDO.setInValidTime(DateUtils.convertDateToLDT((Date)vaBaseData.getInvalidtime()));
        baseDataDO.setUnitCode(vaBaseData.getUnitcode());
        baseDataDO.setParents(vaBaseData.getParents());
        baseDataDO.setVer(vaBaseData.getVer());
        HashMap<String, Object> fieldMap = new HashMap<String, Object>();
        for (Map.Entry entry : vaBaseData.entrySet()) {
            Object value = entry.getValue();
            if (value != null && "showTitleMap".equals(entry.getKey())) {
                Map map = (Map)value;
                HashMap newMap = new HashMap();
                for (Map.Entry tempEntry : map.entrySet()) {
                    newMap.put(((String)tempEntry.getKey()).toUpperCase(), tempEntry.getValue());
                }
                value = newMap;
            }
            fieldMap.put(((String)entry.getKey()).toUpperCase(), value);
        }
        baseDataDO.setFieldValMap(fieldMap);
        if (fieldMap.get("ISLEAF") != null) {
            baseDataDO.setLeaf(ConverterUtils.getAsBooleanValue(fieldMap.get("ISLEAF"), (boolean)false));
        } else if (fieldMap.get("HASCHILDREN") != null) {
            baseDataDO.setLeaf(!ConverterUtils.getAsBooleanValue(fieldMap.get("HASCHILDREN"), (boolean)false));
        }
        return baseDataDO;
    }

    public static BaseDataVO convertBaseDataVO(BaseDataDO vaBaseData) {
        BaseDataVO baseDataVO = new BaseDataVO();
        baseDataVO.setId(vaBaseData.getId().toString());
        baseDataVO.setCode(vaBaseData.getCode());
        baseDataVO.setTitle(vaBaseData.getLocalizedName());
        baseDataVO.setParentid(vaBaseData.getParentcode());
        baseDataVO.setParents(vaBaseData.getParents().split("/"));
        return baseDataVO;
    }

    public static BaseDataDTO convert(BaseDataParam param) {
        BaseDataDTO vaBaseDataDTO;
        block8: {
            block7: {
                AuthType authType;
                vaBaseDataDTO = new BaseDataDTO();
                vaBaseDataDTO.setTableName(param.getTableName());
                vaBaseDataDTO.setCode(param.getCode());
                vaBaseDataDTO.setParentcode(param.getParentCode());
                vaBaseDataDTO.setSearchKey(param.getSearchText());
                vaBaseDataDTO.setPagination(Boolean.valueOf(param.isPagination()));
                if (param.isPagination()) {
                    vaBaseDataDTO.setOffset(Integer.valueOf(param.getPageNum() * param.getPageSize()));
                    vaBaseDataDTO.setLimit(Integer.valueOf(param.getPageSize()));
                }
                if ((authType = param.getAuthType()) == null) break block7;
                switch (authType) {
                    case NONE: {
                        vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
                        break block8;
                    }
                    case ACCESS: {
                        vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.ACCESS);
                        break block8;
                    }
                    case WRITE: {
                        vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.WRITE);
                        break block8;
                    }
                    case MANAGE: {
                        vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.MANAGE);
                        break block8;
                    }
                    default: {
                        throw new BusinessRuntimeException("not support authType");
                    }
                }
            }
            vaBaseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        }
        return vaBaseDataDTO;
    }

    public static List<ITree<GcBaseDataVO>> build(List<ITree<GcBaseDataVO>> nodes) {
        if (nodes == null) {
            return Collections.emptyList();
        }
        HashMap<String, ITree<GcBaseDataVO>> map = new HashMap<String, ITree<GcBaseDataVO>>();
        for (ITree<GcBaseDataVO> node : nodes) {
            map.put(node.getKey(), node);
        }
        ArrayList<ITree<GcBaseDataVO>> topNodes = new ArrayList<ITree<GcBaseDataVO>>();
        String pid = null;
        ITree pNode = null;
        for (ITree<GcBaseDataVO> node : nodes) {
            pid = ((GcBaseDataVO)node.getData()).getParentid();
            if (pid == null || !map.containsKey(pid)) {
                topNodes.add(node);
                continue;
            }
            if (!map.containsKey(pid)) continue;
            pNode = (ITree)map.get(pid);
            pNode.appendChild(node);
        }
        return topNodes;
    }
}

