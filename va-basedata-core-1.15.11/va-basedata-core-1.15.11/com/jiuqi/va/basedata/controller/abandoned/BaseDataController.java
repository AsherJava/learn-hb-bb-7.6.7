/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.OrgContext
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
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
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataContextService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDTO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.OrgContext;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.RequestContextUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated
@ConditionalOnProperty(name={"nvwa.basedata.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/baseData/data"})
public class BaseDataController {
    private static final String TABLENAME = "tablename";
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private BaseDataContextService baseDataContextService;

    @PostMapping(value={"/exist"})
    Object exist(@RequestBody BaseDataDTO param) {
        R rs = this.baseDataService.exist(param);
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!isfromFeign) {
            rs.remove((Object)"data");
        }
        return MonoVO.just((Object)rs);
    }

    @PostMapping(value={"/count"})
    Object count(@RequestBody BaseDataDTO param) {
        return MonoVO.just((Object)this.baseDataService.count(param));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/list"})
    Object list(@RequestBody BaseDataDTO param) {
        Subject subject;
        PageVO rs = null;
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!isfromFeign && (param.getAuthType() == BaseDataOption.AuthType.NONE || param.isOnlyMarkAuth()) && (subject = ShiroUtil.getSubjct()) != null) {
            try {
                subject.checkPermissions(new String[]{"vaBasedata:unauthorized.query"});
            }
            catch (UnauthorizedException e) {
                rs = new PageVO(true);
                rs.getRs().setMsg(403, "Unsupported Operation");
                return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
            }
        }
        OrgContext.removeContext();
        BaseDataContext.removeContext();
        BaseDataContext.unbindColIndex();
        rs = this.baseDataService.list(param);
        BaseDataContext.bindRealDataFlag((Boolean)(isfromFeign || !this.baseDataContextService.isSensitivityEnhanced() ? 1 : 0));
        try {
            Object object = MonoVO.just((Object)JSONUtil.toJSONString((Object)rs, (String)"yyyy-MM-dd HH:mm:ss"));
            return object;
        }
        finally {
            BaseDataContext.unbindRealDataFlag();
        }
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object add(@RequestBody BaseDataDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.add(baseDataDTO));
    }

    @PostMapping(value={"/batch/add"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchAdd(@RequestBody BaseDataBatchOptDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.batchAdd(baseDataDTO));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object update(@RequestBody BaseDataDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.update(baseDataDTO));
    }

    @PostMapping(value={"/batch/update"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchUpdate(@RequestBody BaseDataBatchOptDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.batchUpdate(baseDataDTO));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object remove(@RequestBody BaseDataDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.remove(baseDataDTO));
    }

    @PostMapping(value={"/batch/remove"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchRemove(@RequestBody BaseDataBatchOptDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.batchRemove(baseDataDTO));
    }

    @PostMapping(value={"/stop"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object stop(@RequestBody BaseDataDTO param) {
        return MonoVO.just((Object)this.baseDataService.stop(param));
    }

    @PostMapping(value={"/batch/stop"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchStop(@RequestBody BaseDataBatchOptDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.batchStop(baseDataDTO));
    }

    @PostMapping(value={"/recover"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object recover(@RequestBody BaseDataDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.recover(baseDataDTO));
    }

    @PostMapping(value={"/batch/recover"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object batchRecover(@RequestBody BaseDataBatchOptDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.batchRecover(baseDataDTO));
    }

    @PostMapping(value={"/move"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object move(@RequestBody BaseDataMoveDTO param) {
        return MonoVO.just((Object)this.baseDataService.move(param));
    }

    @PostMapping(value={"/sync/cache/{tablename}"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object forceSyncCache(@PathVariable(value="tablename") String tablename) {
        BaseDataCacheDTO param = new BaseDataCacheDTO();
        param.setTableName(tablename);
        param.setForceUpdate(true);
        return MonoVO.just((Object)this.baseDataService.syncCache(param));
    }

    @PostMapping(value={"/init/cache"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object initCache(@RequestBody BaseDataCacheDTO param) {
        return MonoVO.just((Object)this.baseDataService.initCache(param));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object syncCache(@RequestBody BaseDataCacheDTO param) {
        return MonoVO.just((Object)this.baseDataService.syncCache(param));
    }

    @PostMapping(value={"/clean/cache"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object cleanCache(@RequestBody BaseDataCacheDTO param) {
        return MonoVO.just((Object)this.baseDataService.cleanCache(param));
    }

    @PostMapping(value={"/clearRecovery"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object clearRecovery(@RequestBody BaseDataDTO param) {
        UserLoginDTO user = ShiroUtil.getUser();
        if (user == null || !"super".equalsIgnoreCase(user.getMgrFlag())) {
            return MonoVO.just((Object)R.error((String)"\u65e0\u6267\u884c\u6743\u9650"));
        }
        return MonoVO.just((Object)this.baseDataService.clearRecovery(param));
    }

    @PostMapping(value={"/changeShare"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object changeShare(@RequestBody BaseDataBatchOptDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.changeShare(baseDataDTO));
    }

    @PostMapping(value={"/getNextCode"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object getNextCode(@RequestBody BaseDataDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.getNextCode(baseDataDTO));
    }

    @PostMapping(value={"/sync"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object sync(@RequestBody BaseDataBatchOptDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.sync(baseDataDTO));
    }

    @PostMapping(value={"/fastupdown"})
    @RequiresPermissions(value={"vaBasedata:baseData:mgr"})
    Object fastUpDown(@RequestBody BaseDataDTO baseDataDTO) {
        return MonoVO.just((Object)this.baseDataService.fastUpDown(baseDataDTO));
    }

    @PostMapping(value={"/verDiff/list"})
    Object verDiffList(@RequestBody BaseDataDTO basedataDTO) {
        boolean isfromFeign = "true".equals(RequestContextUtil.getHeader((String)"FeignClient"));
        if (!isfromFeign) {
            return MonoVO.just(null);
        }
        return MonoVO.just(this.baseDataService.verDiffList(basedataDTO));
    }
}

