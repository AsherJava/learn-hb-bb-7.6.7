/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.extend.BaseDataDefineDesignExtend
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.utils.VaI18nParamUtils
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller;

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
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.extend.BaseDataDefineDesignExtend;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.utils.VaI18nParamUtils;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaBaseDataDefineController")
@RequestMapping(value={"/baseData/define/binary"})
public class BaseDataDefineController {
    private static final String DEFINE_NAME = "defineName";
    private static Logger logger = LoggerFactory.getLogger(BaseDataDefineController.class);
    @Autowired
    private BaseDataDefineService baseDataService;
    @Autowired
    private BaseDataDefineCacheService baseDataCacheService;
    private BaseDataDefineTransUtil baseDataDefineTransUtil;

    @PostMapping(value={"/get"})
    Object get(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        param.setDeepClone(Boolean.valueOf(false));
        BaseDataDefineDO rs = this.baseDataService.get(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/list"})
    Object list(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        param.setDeepClone(Boolean.valueOf(false));
        PageVO<BaseDataDefineDO> rs = this.baseDataService.list(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/listSimple"})
    Object listSimple(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setDeepClone(Boolean.valueOf(false));
        ArrayList endList = new ArrayList();
        PageVO<BaseDataDefineDO> page = this.baseDataService.list(param);
        for (BaseDataDefineDO defineDO : page.getRows()) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("name", defineDO.getName());
            data.put("title", defineDO.getTitle() + " (" + defineDO.getName() + ")");
            endList.add(data);
        }
        return MonoVO.just((Object)JSONUtil.toBytes(endList));
    }

    @PostMapping(value={"/exist"})
    Object exist(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        R rs = this.baseDataService.exist(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/existData"})
    Object existData(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        R rs = this.baseDataService.existData(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/add"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object add(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        R rs = this.baseDataService.add(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object update(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        R rs = this.baseDataService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/update/{defineName}"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object update(@RequestBody byte[] binaryData, @PathVariable(value="defineName") String defineName) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        param.setName(defineName);
        R rs = this.baseDataService.update(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/remove"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object remove(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        R rs = this.baseDataService.remove(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/updown"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object updown(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        R rs = this.baseDataService.updown(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/sync/cache"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object sync(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        BaseDataDefineSyncCacheDTO bddscd = new BaseDataDefineSyncCacheDTO();
        bddscd.setTenantName(param.getTenantName());
        bddscd.setForceUpdate(true);
        this.baseDataCacheService.pushSyncMsg(bddscd);
        R rs = R.ok((String)BaseDataCoreI18nUtil.getMessage("basedata.info.bddefine.cache.sync.waiting", new Object[0]));
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/batchOperate"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object batchOperate(@RequestBody byte[] binaryData) {
        BaseDataDefineBatchOperateDTO param = (BaseDataDefineBatchOperateDTO)((Object)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineBatchOperateDTO.class));
        R rs = this.baseDataService.batchOperate(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/isolation/list"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object isolationList(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        List<DataModelColumn> rs = this.baseDataService.isolationList(param);
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/checkZbExistData"})
    Object checkZbExistData(@RequestBody byte[] binaryData) {
        BaseDataDefineDTO param = (BaseDataDefineDTO)JSONUtil.parseObject((byte[])binaryData, BaseDataDefineDTO.class);
        R rs = this.baseDataService.checkZbExistData(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }

    @PostMapping(value={"/language/trans"})
    Object defineTrans(@RequestBody byte[] binaryData) {
        try {
            if (!VaI18nParamUtils.getTranslationEnabled().booleanValue()) {
                return MonoVO.just((Object)JSONUtil.toBytes(new ArrayList()));
            }
            if (this.baseDataDefineTransUtil == null) {
                this.baseDataDefineTransUtil = (BaseDataDefineTransUtil)ApplicationContextRegister.getBean(BaseDataDefineTransUtil.class);
            }
            VaI18nResourceDTO param = (VaI18nResourceDTO)JSONUtil.parseObject((byte[])binaryData, VaI18nResourceDTO.class);
            List<String> rs = this.baseDataDefineTransUtil.transResource(param);
            return MonoVO.just((Object)JSONUtil.toBytes(rs));
        }
        catch (Exception e) {
            logger.info("\u83b7\u53d6\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u56fd\u9645\u5316\u8d44\u6e90\u5f02\u5e38", e);
            return MonoVO.just(null);
        }
    }

    @PostMapping(value={"/extend/type/list"})
    Object getMenuExtend(@RequestBody byte[] binaryData) {
        List<BaseDataDefineDesignExtend> rs = BaseDataDefineExtendUtil.getDefineDesignExtends();
        return MonoVO.just((Object)JSONUtil.toBytes(rs));
    }

    @PostMapping(value={"/dummy/checkSQLDefine"})
    @RequiresPermissions(value={"vaBasedata:define:mgr"})
    Object checkSQLDefine(@RequestBody byte[] binaryData) {
        BaseDataDummyDTO param = (BaseDataDummyDTO)((Object)JSONUtil.parseObject((byte[])binaryData, BaseDataDummyDTO.class));
        R rs = this.baseDataService.checkSQLDefine(param);
        return MonoVO.just((Object)JSONUtil.toBytes((Object)rs));
    }
}

