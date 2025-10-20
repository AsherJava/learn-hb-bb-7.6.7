/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.api.enums.QueryDataType
 *  com.jiuqi.gcreport.basedata.api.enums.RangeType
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.basedata.impl.service.impl;

import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.api.enums.QueryDataType;
import com.jiuqi.gcreport.basedata.api.enums.RangeType;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDTO;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcBaseDataServiceImpl
implements GcBaseDataService {
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public List<GcBaseData> listBaseDataObj(GcBaseDataDTO baseDataObjDTO) {
        BaseDataDTO vaBaseDataDTO = BaseDataObjConverter.convert(baseDataObjDTO);
        vaBaseDataDTO.setLeafFlag(Boolean.valueOf(true));
        PageVO list = this.baseDataClient.list(vaBaseDataDTO);
        if (list == null || list.getRows().size() < 1) {
            return Collections.emptyList();
        }
        List<GcBaseData> result = list.getRows().stream().map(vabasedataDO -> BaseDataObjConverter.convert(vabasedataDO)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<BaseDataVO> treeBaseDataObj(String tableName) {
        BaseDataDTO vaBaseDataDTO = new BaseDataDTO();
        vaBaseDataDTO.setTableName(tableName);
        PageVO list = this.baseDataClient.list(vaBaseDataDTO);
        if (list == null || list.getRows().size() < 1) {
            return Collections.emptyList();
        }
        List<BaseDataVO> result = list.getRows().stream().map(vabasedataDO -> BaseDataObjConverter.convertBaseDataVO(vabasedataDO)).collect(Collectors.toList());
        return this.buildTree(result);
    }

    @Override
    public List<GcBaseData> queryBasedataItems(String tableName) {
        return this.queryBasedataItems(tableName, AuthType.ACCESS);
    }

    @Override
    public List<GcBaseData> queryBasedataItems(String tableName, AuthType authType) {
        GcBaseDataDTO gcBaseDataDTO = new GcBaseDataDTO();
        gcBaseDataDTO.setTableName(tableName);
        gcBaseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(gcBaseDataDTO);
    }

    @Override
    public List<GcBaseData> queryBasedataItemsBySearch(String tableName, String searchText) {
        return this.queryBasedataItemsBySearch(tableName, searchText, AuthType.ACCESS);
    }

    @Override
    public List<GcBaseData> queryBasedataItemsBySearch(String tableName, String searchText, AuthType authType) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setSearchKey(searchText);
        baseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(baseDataDTO);
    }

    @Override
    public List<GcBaseData> queryRootBasedataItems(String tableName) {
        return this.lazyLoadDirectBasedataItemsByParentid(tableName, null);
    }

    @Override
    public List<GcBaseData> queryAllBasedataItemsByParentid(String tableName, String code) {
        return this.queryAllBasedataItemsByParentid(tableName, code, AuthType.ACCESS);
    }

    @Override
    public List<GcBaseData> queryAllBasedataItemsByParentid(String tableName, String code, AuthType authType) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setCode(code);
        baseDataDTO.setRangeType(RangeType.ALL_CHILDREN);
        baseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(baseDataDTO);
    }

    @Override
    public List<GcBaseData> queryAllWithSelfItemsByParentid(String tableName, String code) {
        return this.queryAllWithSelfItemsByParentid(tableName, code, AuthType.ACCESS);
    }

    @Override
    public List<GcBaseData> queryAllWithSelfItemsByParentid(String tableName, String code, AuthType authType) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setCode(code);
        baseDataDTO.setRangeType(RangeType.ALL_CHILDREN_WITH_SELF);
        baseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(baseDataDTO);
    }

    @Override
    public List<GcBaseData> queryDirectBasedataItemsByParentid(String tableName, String code) {
        return this.queryDirectBasedataItemsByParentid(tableName, code, AuthType.ACCESS);
    }

    @Override
    public List<GcBaseData> queryDirectBasedataItemsByParentid(String tableName, String code, AuthType authType) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setCode(code);
        baseDataDTO.setRangeType(RangeType.DIRECT_CHILDREN);
        baseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(baseDataDTO);
    }

    @Override
    public List<GcBaseData> lazyLoadDirectBasedataItemsByParentid(String tableName, String code) {
        return this.lazyLoadDirectBasedataItemsByParentid(tableName, code, AuthType.ACCESS);
    }

    @Override
    public List<GcBaseData> lazyLoadDirectBasedataItemsByParentid(String tableName, String code, AuthType authType) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        if (null == code || code.length() == 0 || "-".equals(code)) {
            baseDataDTO.setParentid("-");
        } else {
            baseDataDTO.setParentid(code);
        }
        baseDataDTO.setLazyLoad(true);
        baseDataDTO.setAuthType(authType);
        return this.listBaseDataObj(baseDataDTO);
    }

    @Override
    public GcBaseData queryBasedataByCode(String tableName, String code) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setCode(code);
        List<GcBaseData> result = this.listBaseDataObj(baseDataDTO);
        if (result == null || result.size() < 1) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public GcBaseData queryBasedataByObjCode(String tableName, String objectCode) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setObjectCode(objectCode);
        List<GcBaseData> result = this.listBaseDataObj(baseDataDTO);
        if (result == null || result.size() < 1) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<GcBaseData> batchQueryBasedata(String tableName, List<String> codes) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setBaseDataCodes(codes);
        return this.listBaseDataObj(baseDataDTO);
    }

    @Override
    public GcBaseData queryBaseDataSimpleItem(String tableName, String code) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setCode(code);
        baseDataDTO.setQueryDataType(QueryDataType.SIMPLE);
        List<GcBaseData> result = this.listBaseDataObj(baseDataDTO);
        if (result == null || result.size() < 1) {
            return null;
        }
        return result.get(0);
    }

    public List<BaseDataVO> listBaseDataVO(GcBaseDataDTO baseDataObjDTO) {
        BaseDataDTO vaBaseDataDTO = BaseDataObjConverter.convert(baseDataObjDTO);
        PageVO list = this.baseDataClient.list(vaBaseDataDTO);
        if (list == null || list.getRows().size() < 1) {
            return Collections.emptyList();
        }
        List<BaseDataVO> result = list.getRows().stream().map(vabasedataDO -> BaseDataObjConverter.convertBaseDataVO(vabasedataDO)).collect(Collectors.toList());
        return result;
    }

    @Override
    public BaseDataVO queryBaseDataVoByCode(String tableName, String code) {
        GcBaseDataDTO baseDataDTO = new GcBaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setCode(code);
        List<BaseDataVO> result = this.listBaseDataVO(baseDataDTO);
        if (result == null || result.size() < 1) {
            return null;
        }
        return result.get(0);
    }

    private List<BaseDataVO> buildTree(List<BaseDataVO> nodes) {
        if (nodes == null) {
            return Collections.emptyList();
        }
        HashMap<String, BaseDataVO> map = new HashMap<String, BaseDataVO>();
        for (BaseDataVO node : nodes) {
            map.put(node.getCode(), node);
        }
        ArrayList<BaseDataVO> topNodes = new ArrayList<BaseDataVO>();
        String pid = null;
        BaseDataVO pNode = null;
        for (BaseDataVO node : nodes) {
            pid = node.getParentid();
            if (pid == null || "-".equals(pid) || !map.containsKey(pid)) {
                topNodes.add(node);
                continue;
            }
            if (!map.containsKey(pid)) continue;
            pNode = (BaseDataVO)map.get(pid);
            pNode.addChild(node);
            pNode.setLeaf(false);
        }
        return topNodes;
    }

    @Override
    public List<GcBaseData> listBaseData(BaseDataParam param) {
        BaseDataDTO vaBaseDataDTO = BaseDataObjConverter.convert(param);
        if (param.getSystemId() != null) {
            vaBaseDataDTO.put("systemid", (Object)param.getSystemId());
        }
        vaBaseDataDTO.setLeafFlag(Boolean.valueOf(true));
        PageVO list = this.baseDataClient.list(vaBaseDataDTO);
        if (list == null || list.getRows().size() < 1) {
            return Collections.emptyList();
        }
        List<GcBaseData> result = list.getRows().stream().map(vabasedataDO -> BaseDataObjConverter.convert(vabasedataDO)).collect(Collectors.toList());
        return result;
    }

    @Override
    public Integer getBaseDataMaxDepth(BaseDataParam param) {
        PageVO list;
        BaseDataDTO vaBaseDataDTO = BaseDataObjConverter.convert(param);
        if (param.getSystemId() != null) {
            vaBaseDataDTO.put("systemid", (Object)param.getSystemId());
        }
        if ((list = this.baseDataClient.list(vaBaseDataDTO)) == null || list.getRows().size() < 1) {
            return 0;
        }
        List<BaseDataVO> res = list.getRows().stream().map(vabasedataDO -> BaseDataObjConverter.convertBaseDataVO(vabasedataDO)).collect(Collectors.toList());
        List<BaseDataVO> baseDataVOS = this.buildTree(res);
        int maxDepth = 0;
        for (BaseDataVO baseDataVO : baseDataVOS) {
            maxDepth = Math.max(this.getDepth(baseDataVO), maxDepth);
        }
        return maxDepth;
    }

    public int getDepth(BaseDataVO baseDataVo) {
        if (baseDataVo == null) {
            return 0;
        }
        int depth = 0;
        for (BaseDataVO child : baseDataVo.getChildren()) {
            depth = Math.max(this.getDepth(child), depth);
        }
        return depth + 1;
    }
}

