/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.common.BaseDataDefineExtendUtil;
import com.jiuqi.va.basedata.common.BaseDataDefineTransUtil;
import com.jiuqi.va.basedata.domain.BaseDataDefineBatchOperateDTO;
import com.jiuqi.va.basedata.domain.BaseDataDefineSyncCacheDTO;
import com.jiuqi.va.basedata.domain.BaseDataDummyDTO;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataDefineCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated
@ConditionalOnProperty(name={"nvwa.basedata.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/baseData/define"})
public class BaseDataDefineController {
    private static final String DEFINE_NAME = "defineName";
    private static Logger logger = LoggerFactory.getLogger(BaseDataDefineController.class);
    @Autowired
    private BaseDataDefineService baseDataService;
    @Autowired
    private BaseDataDefineCacheService baseDataCacheService;

    @PostMapping(value={"/get"})
    Object get(@RequestBody BaseDataDefineDTO param) {
        param.setDeepClone(Boolean.valueOf(false));
        return MonoVO.just((Object)this.baseDataService.get(param));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody BaseDataDefineDTO param) {
        param.setDeepClone(Boolean.valueOf(false));
        return MonoVO.just(this.baseDataService.list(param));
    }

    @GetMapping(value={"/listSimple"})
    Object listSimple() {
        ArrayList endList = new ArrayList();
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setDeepClone(Boolean.valueOf(false));
        PageVO<BaseDataDefineDO> page = this.baseDataService.list(param);
        for (BaseDataDefineDO defineDO : page.getRows()) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("name", defineDO.getName());
            data.put("title", defineDO.getTitle() + " (" + defineDO.getName() + ")");
            endList.add(data);
        }
        return MonoVO.just(endList);
    }

    @PostMapping(value={"/exist"})
    Object exist(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just((Object)this.baseDataService.exist(param));
    }

    @PostMapping(value={"/existData"})
    Object existData(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just((Object)this.baseDataService.existData(param));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object add(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just((Object)this.baseDataService.add(param));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object update(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just((Object)this.baseDataService.update(param));
    }

    @PostMapping(value={"/update/{defineName}"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object update(@RequestBody BaseDataDefineDTO param, @PathVariable(value="defineName") String defineName) {
        param.setName(defineName);
        return MonoVO.just((Object)this.baseDataService.update(param));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object remove(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just((Object)this.baseDataService.remove(param));
    }

    @PostMapping(value={"/updown"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object updown(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just((Object)this.baseDataService.updown(param));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object sync(@RequestBody BaseDataDefineDTO param) {
        BaseDataDefineSyncCacheDTO bddscd = new BaseDataDefineSyncCacheDTO();
        bddscd.setTenantName(param.getTenantName());
        bddscd.setForceUpdate(true);
        this.baseDataCacheService.pushSyncMsg(bddscd);
        return MonoVO.just((Object)R.ok((String)BaseDataCoreI18nUtil.getMessage("basedata.info.bddefine.cache.sync.waiting", new Object[0])));
    }

    @PostMapping(value={"/batchOperate"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object batchOperate(@RequestBody BaseDataDefineBatchOperateDTO updateParam) {
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49", (String)"\u6279\u91cf\u64cd\u4f5c", (String)"", (String)"", (String)"");
        return MonoVO.just((Object)this.baseDataService.batchOperate(updateParam));
    }

    @PostMapping(value={"/isolation/list"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object isolationList(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just(this.baseDataService.isolationList(param));
    }

    @PostMapping(value={"/checkZbExistData"})
    Object checkZbExistData(@RequestBody BaseDataDefineDTO param) {
        return MonoVO.just((Object)this.baseDataService.checkZbExistData(param));
    }

    @PostMapping(value={"/language/trans"})
    Object defineTrans(@RequestBody VaI18nResourceDTO vaDataResourceDTO) {
        try {
            BaseDataDefineTransUtil transUtil = (BaseDataDefineTransUtil)ApplicationContextRegister.getBean(BaseDataDefineTransUtil.class);
            return MonoVO.just(transUtil.transResource(vaDataResourceDTO));
        }
        catch (Exception e) {
            logger.info("\u83b7\u53d6\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u56fd\u9645\u5316\u8d44\u6e90\u5f02\u5e38", e);
            return MonoVO.just(null);
        }
    }

    @GetMapping(value={"/extend/type/list"})
    Object getMenuExtend() {
        return MonoVO.just(BaseDataDefineExtendUtil.getDefineDesignExtends());
    }

    @PostMapping(value={"/dummy/checkSQLDefine"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object checkSQLDefine(@RequestBody BaseDataDummyDTO basedata) {
        return MonoVO.just((Object)this.baseDataService.checkSQLDefine(basedata));
    }
}

