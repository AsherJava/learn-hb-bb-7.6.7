/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.basedata.api.GcBasedataFeignClient
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.INode
 *  com.jiuqi.gcreport.basedata.api.itree.ITree
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataTableVO
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.basedata.impl.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.basedata.api.GcBasedataFeignClient;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.INode;
import com.jiuqi.gcreport.basedata.api.itree.ITree;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataTableVO;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class GcBaseDataController
implements GcBasedataFeignClient {
    private GcBaseDataCenterTool getTool() {
        return GcBaseDataCenterTool.getInstance();
    }

    public BusinessResponseEntity<List<BaseDataVO>> listBaseData(@PathVariable(value="tbCode") String tbCode) {
        List<BaseDataVO> basedatas = this.getTool().queryBasedataItemsVO(tbCode);
        return BusinessResponseEntity.ok(basedatas);
    }

    public BusinessResponseEntity<List<BaseDataVO>> treeBaseData(Map<String, String> params) {
        List<BaseDataVO> basedatas = this.getTool().queryBasedataTree(params.get("tbName"));
        return BusinessResponseEntity.ok(basedatas);
    }

    public BusinessResponseEntity<List<BaseDataVO>> treeBaseData(@PathVariable(value="tbCode") String tbCode) {
        List<BaseDataVO> basedatas = this.getTool().queryBasedataTree(tbCode);
        return BusinessResponseEntity.ok(basedatas);
    }

    public BusinessResponseEntity<List<BaseDataTableVO>> queryAllBasedateTables() {
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<BaseDataVO>> listBaseDataBySearch(@PathVariable(value="tbCode") String tbCode, @PathVariable(value="searchText") String searchText) {
        List<GcBaseData> basedatas = this.getTool().queryBasedataItemsBySearch(tbCode, searchText);
        return BusinessResponseEntity.ok(this.getTool().convertListBaseDataVO(basedatas));
    }

    public BusinessResponseEntity<BaseDataVO> getBaseDataByCode(@PathVariable(value="tbCode") String tbCode, @PathVariable(value="code") String code) {
        GcBaseData baseData = this.getTool().queryBasedataByCode(tbCode, code);
        return BusinessResponseEntity.ok((Object)this.getTool().convertBaseDataVO(baseData));
    }

    public BusinessResponseEntity<List<BaseDataVO>> listRootBaseData(@PathVariable(value="tbCode") String tbCode) {
        List<GcBaseData> basedatas = this.getTool().queryRootBasedataItems(tbCode);
        return BusinessResponseEntity.ok(this.getTool().convertListBaseDataVO(basedatas));
    }

    public BusinessResponseEntity<List<BaseDataVO>> listBaseDataByParent(@PathVariable(value="tbCode") String tbCode, @PathVariable(value="parentCode") String parentCode) {
        List<GcBaseData> basedatas = this.getTool().queryBasedataItemsByParentid(tbCode, parentCode);
        return BusinessResponseEntity.ok(this.getTool().convertListBaseDataVO(basedatas));
    }

    public BusinessResponseEntity<List<BaseDataVO>> listAllBaseDataByParent(@PathVariable(value="tbCode") String tbCode, @PathVariable(value="parentCode") String parentCode) {
        List<GcBaseData> basedatas = this.getTool().queryAllBasedataItemsByParentid(tbCode, parentCode);
        return BusinessResponseEntity.ok(this.getTool().convertListBaseDataVO(basedatas));
    }

    @Transactional
    public BusinessResponseEntity<String> saveBaseDatas(@PathVariable String tableName, @RequestBody Map<String, Object> datas, @RequestHeader(value="Authorization") String token) {
        Object result = null;
        return BusinessResponseEntity.ok();
    }

    @Transactional
    public BusinessResponseEntity<List<BaseDataVO>> getBaseDataByCodes(Map<String, Object> params) {
        ObjectMapper mapper = new ObjectMapper();
        String tbCode = (String)mapper.convertValue(params.get("tableCode"), String.class);
        List codes = (List)mapper.convertValue(params.get("codes"), (TypeReference)new TypeReference<List<String>>(){});
        String filter = (String)mapper.convertValue(params.get("filter"), String.class);
        if (CollectionUtils.isEmpty(codes)) {
            return BusinessResponseEntity.ok(Collections.emptyList());
        }
        return BusinessResponseEntity.ok(this.getTool().convertListBaseDataVO(codes.stream().map(code -> this.getTool().queryBasedataByCode(tbCode, (String)code)).collect(Collectors.toList())));
    }

    public BusinessResponseEntity<Object> getBaseDataByParent(BaseDataParam baseDataParam) {
        List<GcBaseData> basedatas = this.getTool().lazyLoadDirectBasedataItemsByParentid(baseDataParam.getTableName(), baseDataParam.getParentCode(), baseDataParam.getAuthType());
        List nodes = basedatas.stream().map(v -> new ITree((INode)this.getTool().convertGcBaseDataVO((GcBaseData)v))).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<List<GcBaseDataVO>> getBaseDataBySearch(BaseDataParam baseDataParam) {
        List<GcBaseData> basedatas = this.getTool().queryBasedataItemsBySearch(baseDataParam.getTableName(), baseDataParam.getSearchText(), baseDataParam.getAuthType());
        return BusinessResponseEntity.ok(this.getTool().convertListGcBaseDataVO(basedatas));
    }

    public BusinessResponseEntity<GcBaseDataVO> getBaseDataByCode(BaseDataParam baseDataParam) {
        GcBaseData baseData = this.getTool().queryBasedataByCode(baseDataParam.getTableName(), baseDataParam.getCode());
        return BusinessResponseEntity.ok((Object)this.getTool().convertGcBaseDataVO(baseData));
    }

    public BusinessResponseEntity<Object> listAllBaseDataByParent(BaseDataParam baseDataParam) {
        List<GcBaseData> basedatas = this.getTool().queryAllWithSelfItemsByParentid(baseDataParam.getTableName(), baseDataParam.getParentCode(), baseDataParam.getAuthType());
        List nodes = basedatas.stream().map(v -> new ITree((INode)this.getTool().convertGcBaseDataVO((GcBaseData)v))).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<List<GcBaseDataVO>> listBaseData(BaseDataParam baseDataParam) {
        List<GcBaseData> basedatas = this.getTool().listBaseData(baseDataParam);
        return BusinessResponseEntity.ok(this.getTool().convertListGcBaseDataVO(basedatas));
    }

    public BusinessResponseEntity<List<GcBaseDataVO>> levelBaseData(BaseDataParam baseDataParam) {
        return BusinessResponseEntity.ok(this.getTool().levelBaseData(baseDataParam));
    }

    public BusinessResponseEntity<Integer> getBaseDataMaxDepth(BaseDataParam baseDataParam) {
        return BusinessResponseEntity.ok((Object)this.getTool().getBaseDataMaxDepth(baseDataParam));
    }
}

