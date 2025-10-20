/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.INode
 *  com.jiuqi.gcreport.basedata.api.itree.ITree
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.basedata.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.INode;
import com.jiuqi.gcreport.basedata.api.itree.ITree;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseDataApiProxyController {
    public static final String BASE_DATA_API_BASE_PATH = "/api/datacenter/v1/basedatas";

    private GcBaseDataCenterTool getTool() {
        return GcBaseDataCenterTool.getInstance();
    }

    @PostMapping(value={"/api/datacenter/v1/basedatas/levelTree"})
    public BusinessResponseEntity<Object> getBaseDataByParent(@RequestBody BaseDataParam baseDataParam) {
        List basedatas = this.getTool().lazyLoadDirectBasedataItemsByParentid(baseDataParam.getTableName(), baseDataParam.getParentCode(), baseDataParam.getAuthType());
        List nodes = basedatas.stream().map(v -> new ITree((INode)this.getTool().convertGcBaseDataVO(v))).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    @PostMapping(value={"/api/datacenter/v1/basedatas/searchKey"})
    public BusinessResponseEntity<List<GcBaseDataVO>> getBaseDataBySearch(@RequestBody BaseDataParam baseDataParam) {
        List basedatas = this.getTool().queryBasedataItemsBySearch(baseDataParam.getTableName(), baseDataParam.getSearchText(), baseDataParam.getAuthType());
        return BusinessResponseEntity.ok((Object)this.getTool().convertListGcBaseDataVO(basedatas));
    }

    @PostMapping(value={"/api/datacenter/v1/basedatas/singleData"})
    public BusinessResponseEntity<GcBaseDataVO> getBaseDataByCode(@RequestBody BaseDataParam baseDataParam) {
        GcBaseData baseData = this.getTool().queryBasedataByCode(baseDataParam.getTableName(), baseDataParam.getCode());
        return BusinessResponseEntity.ok((Object)this.getTool().convertGcBaseDataVO(baseData));
    }

    @PostMapping(value={"/api/datacenter/v1/basedatas/allLevelTree"})
    public BusinessResponseEntity<Object> listAllBaseDataByParent(@RequestBody BaseDataParam baseDataParam) {
        List basedatas = this.getTool().queryAllWithSelfItemsByParentid(baseDataParam.getTableName(), baseDataParam.getParentCode(), baseDataParam.getAuthType());
        List nodes = basedatas.stream().map(v -> new ITree((INode)this.getTool().convertGcBaseDataVO(v))).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }
}

