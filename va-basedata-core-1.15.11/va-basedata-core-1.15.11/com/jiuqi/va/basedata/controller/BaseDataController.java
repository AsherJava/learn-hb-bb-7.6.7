/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.OrgContext
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.apache.shiro.authz.UnauthorizedException
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.apache.shiro.subject.Subject
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller;

import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataContextService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.OrgContext;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaBaseDataController")
@RequestMapping(value={"/baseData/data/binary"})
public class BaseDataController {
    private static final String TABLENAME = "tablename";
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private BaseDataContextService baseDataContextService;

    @PostMapping(value={"/exist"})
    Object exist(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.exist(param);
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!isfromFeign) {
            rs.remove((Object)"data");
        }
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/count"})
    Object count(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        int rs = this.baseDataService.count(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        return this.list(binaryData, isfromFeign || !this.baseDataContextService.isSensitivityEnhanced());
    }

    @PostMapping(value={"/sensitivity/list"})
    Object sensitivityList(@RequestBody byte[] binaryData) {
        return this.list(binaryData, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Object list(byte[] binaryData, boolean realDataFlag) {
        Subject subject;
        PageVO rs = null;
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!isfromFeign && (param.getAuthType() == BaseDataOption.AuthType.NONE || param.isOnlyMarkAuth()) && (subject = ShiroUtil.getSubjct()) != null) {
            try {
                subject.checkPermissions(new String[]{"vaBasedata:unauthorized.query"});
            }
            catch (UnauthorizedException e) {
                try {
                    subject.checkPermissions(new String[]{"vaBasedata:baseData:mgr"});
                }
                catch (UnauthorizedException e2) {
                    rs = new PageVO(true);
                    rs.getRs().setMsg(403, "Unsupported Operation");
                    return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
                }
            }
        }
        OrgContext.removeContext();
        BaseDataContext.removeContext();
        BaseDataContext.unbindColIndex();
        rs = this.baseDataService.list(param);
        BaseDataContext.bindRealDataFlag((Boolean)realDataFlag);
        try {
            Object object = MonoVO.just((Object)JSONUtil.toBytes(rs));
            return object;
        }
        finally {
            BaseDataContext.unbindRealDataFlag();
        }
    }

    @PostMapping(value={"/columnValue/list"})
    Object columnValueList(@RequestBody byte[] binaryData) {
        BaseDataColumnValueDTO param = (BaseDataColumnValueDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataColumnValueDTO.class);
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!isfromFeign) {
            BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
            defineParam.setName(param.getQueryParam().getTableName());
            boolean flag = this.baseDataContextService.canQuerySensitive(defineParam);
            if (!flag) {
                return MonoVO.just(null);
            }
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e", (String)"\u67e5\u770b\u654f\u611f\u4fe1\u606f", null, null, (String)JSONUtil.toJSONString((Object)param));
        }
        OrgContext.removeContext();
        BaseDataContext.removeContext();
        Map<String, Object[]> rs = this.baseDataService.columnValueList(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/batch/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchAdd(@RequestBody byte[] binaryData) {
        BaseDataBatchOptDTO param = (BaseDataBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataBatchOptDTO.class);
        R rs = this.baseDataService.batchAdd(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/batch/update"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchUpdate(@RequestBody byte[] binaryData) {
        BaseDataBatchOptDTO param = (BaseDataBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataBatchOptDTO.class);
        R rs = this.baseDataService.batchUpdate(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object remove(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.remove(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/batch/remove"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchRemove(@RequestBody byte[] binaryData) {
        BaseDataBatchOptDTO baseDataDTO = (BaseDataBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataBatchOptDTO.class);
        baseDataDTO.setTenantName(ShiroUtil.getTenantName());
        byte[] bytes = JSONUtil.toBytes((Object)this.baseDataService.batchRemove(baseDataDTO));
        return MonoVO.just((Object)bytes);
    }

    @PostMapping(value={"/stop"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object stop(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.stop(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/batch/stop"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchStop(@RequestBody byte[] binaryData) {
        BaseDataBatchOptDTO param = (BaseDataBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataBatchOptDTO.class);
        R rs = this.baseDataService.batchStop(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/recover"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object recover(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.recover(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/batch/recover"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchRecover(@RequestBody byte[] binaryData) {
        BaseDataBatchOptDTO param = (BaseDataBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataBatchOptDTO.class);
        R rs = this.baseDataService.batchRecover(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/move"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object move(@RequestBody byte[] binaryData) {
        BaseDataMoveDTO param = (BaseDataMoveDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataMoveDTO.class);
        R rs = this.baseDataService.move(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/sync/cache/{tablename}"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object forceSyncCache(@RequestBody byte[] binaryData, @PathVariable(value="tablename") String tablename) {
        BaseDataCacheDTO param = new BaseDataCacheDTO();
        param.setTableName(tablename);
        param.setForceUpdate(true);
        R rs = this.baseDataService.syncCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/init/cache"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object initCache(@RequestBody byte[] binaryData) {
        BaseDataCacheDTO param = (BaseDataCacheDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataCacheDTO.class);
        R rs = this.baseDataService.initCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object syncCache(@RequestBody byte[] binaryData) {
        BaseDataCacheDTO param = (BaseDataCacheDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataCacheDTO.class);
        R rs = this.baseDataService.syncCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/clean/cache"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object cleanCache(@RequestBody byte[] binaryData) {
        BaseDataCacheDTO param = (BaseDataCacheDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataCacheDTO.class);
        R rs = this.baseDataService.cleanCache(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/clearRecovery"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object clearRecovery(@RequestBody byte[] binaryData) {
        UserLoginDTO user = ShiroUtil.getUser();
        if (user == null || !"super".equalsIgnoreCase(user.getMgrFlag())) {
            return MonoVO.just((Object)R.error((String)"\u65e0\u6267\u884c\u6743\u9650"));
        }
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.clearRecovery(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/changeShare"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object changeShare(@RequestBody byte[] binaryData) {
        BaseDataBatchOptDTO param = (BaseDataBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataBatchOptDTO.class);
        R rs = this.baseDataService.changeShare(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/getNextCode"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object getNextCode(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.getNextCode(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/sync"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object sync(@RequestBody byte[] binaryData) {
        BaseDataBatchOptDTO param = (BaseDataBatchOptDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataBatchOptDTO.class);
        R rs = this.baseDataService.sync(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/fastupdown"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object fastUpDown(@RequestBody byte[] binaryData) {
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        R rs = this.baseDataService.fastUpDown(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/verDiff/list"})
    Object verDiffList(@RequestBody byte[] binaryData) {
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!isfromFeign) {
            return MonoVO.just((Object)JSONUtil.toBytes(null));
        }
        BaseDataDTO param = (BaseDataDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDTO.class);
        List<BaseDataDO> rs = this.baseDataService.verDiffList(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }
}

