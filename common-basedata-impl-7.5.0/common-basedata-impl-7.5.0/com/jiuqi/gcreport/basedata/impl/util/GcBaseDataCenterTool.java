/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.INode
 *  com.jiuqi.gcreport.basedata.api.itree.ITree
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 */
package com.jiuqi.gcreport.basedata.impl.util;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.INode;
import com.jiuqi.gcreport.basedata.api.itree.ITree;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class GcBaseDataCenterTool {
    private static final GcBaseDataCenterTool tool = new GcBaseDataCenterTool();
    private GcBaseDataService basedataService;

    public GcBaseDataService getBasedataService() {
        return this.basedataService;
    }

    public void setBasedataService(GcBaseDataService basedataService) {
        this.basedataService = basedataService;
    }

    private GcBaseDataCenterTool() {
    }

    public static GcBaseDataCenterTool getInstance() {
        return tool;
    }

    public List<BaseDataVO> queryBasedataTree(String tableName) {
        return this.basedataService.treeBaseDataObj(tableName);
    }

    public List<GcBaseData> queryBasedataItems(String tableName) {
        return this.queryBasedataItems(tableName, AuthType.ACCESS);
    }

    public List<GcBaseData> queryBasedataItems(String tableName, AuthType authType) {
        return this.basedataService.queryBasedataItems(tableName, authType);
    }

    public List<GcBaseData> queryBasedataItemsBySearch(String tableName, String searchText) {
        return this.basedataService.queryBasedataItemsBySearch(tableName, searchText);
    }

    public List<GcBaseData> queryBasedataItemsBySearch(String tableName, String searchText, AuthType authType) {
        return this.basedataService.queryBasedataItemsBySearch(tableName, searchText, authType);
    }

    public List<GcBaseData> queryRootBasedataItems(String tableName) {
        return this.basedataService.queryRootBasedataItems(tableName);
    }

    public List<GcBaseData> queryAllBasedataItemsByParentid(String tableName, String code) {
        return this.basedataService.queryAllBasedataItemsByParentid(tableName, code);
    }

    public List<GcBaseData> queryAllBasedataItemsByParentid(String tableName, String code, AuthType authType) {
        return this.basedataService.queryAllBasedataItemsByParentid(tableName, code, authType);
    }

    public List<GcBaseData> queryAllWithSelfItemsByParentid(String tableName, String code) {
        return this.basedataService.queryAllWithSelfItemsByParentid(tableName, code);
    }

    public List<GcBaseData> queryAllWithSelfItemsByParentid(String tableName, String code, AuthType authType) {
        return this.basedataService.queryAllWithSelfItemsByParentid(tableName, code, authType);
    }

    public List<GcBaseData> queryBasedataItemsByParentid(String tableName, String code) {
        return this.basedataService.queryDirectBasedataItemsByParentid(tableName, code);
    }

    public List<GcBaseData> queryBasedataItemsByParentid(String tableName, String code, AuthType authType) {
        return this.basedataService.queryDirectBasedataItemsByParentid(tableName, code, authType);
    }

    public List<GcBaseData> lazyLoadDirectBasedataItemsByParentid(String tableName, String code) {
        return this.basedataService.lazyLoadDirectBasedataItemsByParentid(tableName, code);
    }

    public List<GcBaseData> lazyLoadDirectBasedataItemsByParentid(String tableName, String code, AuthType authType) {
        return this.basedataService.lazyLoadDirectBasedataItemsByParentid(tableName, code, authType);
    }

    public GcBaseData queryBaseDataSimpleItem(String tableName, String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        return this.basedataService.queryBaseDataSimpleItem(tableName, code);
    }

    public GcBaseData queryBasedataByCode(String tableName, String code) {
        return this.basedataService.queryBasedataByCode(tableName, code);
    }

    public GcBaseData queryBasedataByObjCode(String tableName, String objectCode) {
        return this.basedataService.queryBasedataByObjCode(tableName, objectCode);
    }

    public BaseDataVO queryBaseDataVoByCode(String tableName, String code) {
        return this.basedataService.queryBaseDataVoByCode(tableName, code);
    }

    public BaseDataVO convertBaseDataVO(GcBaseData iBaseData) {
        if (iBaseData == null) {
            return null;
        }
        BaseDataVO baseDataVo = new BaseDataVO();
        BeanUtils.copyProperties(iBaseData, baseDataVo);
        if (!StringUtils.isEmpty((String)iBaseData.getParents())) {
            baseDataVo.setParents(iBaseData.getParents().split("/"));
        }
        if (iBaseData.getOrdinal() != null) {
            baseDataVo.setOrdinal(iBaseData.getOrdinal().toString());
        }
        return baseDataVo;
    }

    public List<BaseDataVO> convertListBaseDataVO(List<GcBaseData> iBaseDatas) {
        if (iBaseDatas == null || iBaseDatas.size() < 1) {
            return null;
        }
        List<BaseDataVO> basedataList = iBaseDatas.stream().map(iBaseData -> this.convertBaseDataVO((GcBaseData)iBaseData)).collect(Collectors.toList());
        return basedataList;
    }

    public GcBaseDataVO convertGcBaseDataVO(GcBaseData iBaseData) {
        if (iBaseData == null) {
            return null;
        }
        GcBaseDataVO baseDataVo = new GcBaseDataVO();
        BeanUtils.copyProperties(iBaseData, baseDataVo);
        baseDataVo.setKey(iBaseData.getCode());
        if (iBaseData.isLeaf() != null) {
            baseDataVo.setLeaf(iBaseData.isLeaf().booleanValue());
        }
        if (iBaseData.getOrdinal() != null) {
            baseDataVo.setOrdinal(iBaseData.getOrdinal().toString());
        }
        return baseDataVo;
    }

    public List<GcBaseDataVO> convertListGcBaseDataVO(List<GcBaseData> iBaseDatas) {
        if (iBaseDatas == null || iBaseDatas.size() < 1) {
            return null;
        }
        List<GcBaseDataVO> basedataList = iBaseDatas.stream().map(iBaseData -> this.convertGcBaseDataVO((GcBaseData)iBaseData)).collect(Collectors.toList());
        return basedataList;
    }

    public List<BaseDataVO> queryBasedataItemsVO(String tableName) {
        return this.convertListBaseDataVO(this.queryBasedataItems(tableName));
    }

    public List<BaseDataVO> queryTreeBasedataItems(String tableCode) {
        return null;
    }

    public static String combiningObjectCode(String code, String ... isolationFields) {
        StringBuffer objectCode = new StringBuffer(code);
        if (isolationFields != null) {
            for (String isolationField : isolationFields) {
                objectCode.append("||").append(isolationField);
            }
        }
        return objectCode.toString();
    }

    public List<GcBaseData> listBaseData(BaseDataParam param) {
        return this.basedataService.listBaseData(param);
    }

    public List<GcBaseDataVO> levelBaseData(BaseDataParam param) {
        List<ITree<GcBaseDataVO>> nodes = this.listBaseData(param).stream().map(v -> new ITree((INode)this.convertGcBaseDataVO((GcBaseData)v))).collect(Collectors.toList());
        return this.getNodesAtLevel(BaseDataObjConverter.build(nodes), param.getLevel());
    }

    public List<GcBaseDataVO> getNodesAtLevel(List<ITree<GcBaseDataVO>> root, int level) {
        ArrayList<GcBaseDataVO> result = new ArrayList<GcBaseDataVO>();
        if (root == null || level < 1) {
            return result;
        }
        if (level == 1) {
            result.addAll(root.stream().map(v -> (GcBaseDataVO)v.getData()).collect(Collectors.toList()));
        } else {
            for (ITree<GcBaseDataVO> node : root) {
                result.addAll(this.getNodesAtLevel(node.getChildren(), level - 1));
            }
        }
        return result;
    }

    public Integer getBaseDataMaxDepth(BaseDataParam baseDataParam) {
        return this.basedataService.getBaseDataMaxDepth(baseDataParam);
    }
}

