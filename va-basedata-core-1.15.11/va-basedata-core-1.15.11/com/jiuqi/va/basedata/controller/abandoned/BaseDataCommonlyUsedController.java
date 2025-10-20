/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.MonoVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.basedata.controller.abandoned;

import com.jiuqi.va.basedata.service.BaseDataCommonlyUsedService;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.MonoVO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated
@ConditionalOnProperty(name={"nvwa.basedata.binary.compatible"}, havingValue="true", matchIfMissing=true)
@RequestMapping(value={"/baseData/commonlyUsed"})
public class BaseDataCommonlyUsedController {
    @Autowired
    private BaseDataCommonlyUsedService commonlyUsedService;
    @Autowired
    private BaseDataService baseDataService;

    @PostMapping(value={"/list"})
    Object list(@RequestBody BaseDataDTO param) {
        BaseDataDTO cmrdParam = new BaseDataDTO();
        cmrdParam.setCreateuser(ShiroUtil.getUser().getId());
        cmrdParam.setTableName(param.getTableName());
        Set<String> commonlyFilter = this.commonlyUsedService.listObjectcode(cmrdParam);
        if (commonlyFilter.isEmpty()) {
            return MonoVO.just(null);
        }
        ArrayList<String> baseDataCodes = new ArrayList<String>();
        baseDataCodes.addAll(commonlyFilter);
        param.setIgnoreShareFields(Boolean.valueOf(true));
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        param.setBaseDataObjectcodes(baseDataCodes);
        PageVO<BaseDataDO> page = this.baseDataService.list(param);
        return MonoVO.just((Object)page.getRows());
    }

    @PostMapping(value={"/changeData"})
    Object changeData(@RequestBody BaseDataDTO param) {
        return MonoVO.just((Object)this.commonlyUsedService.changeData(param));
    }

    @PostMapping(value={"/removeData"})
    Object removeData(@RequestBody BaseDataDTO param) {
        return MonoVO.just((Object)this.commonlyUsedService.removeData(param));
    }

    @PostMapping(value={"/countFlag"})
    Object countFlag(@RequestBody BaseDataDTO param) {
        return MonoVO.just((Object)this.commonlyUsedService.countFlag(param));
    }

    @PostMapping(value={"/changeFlag"})
    Object changeFlag(@RequestBody BaseDataDTO param) {
        return MonoVO.just((Object)this.commonlyUsedService.changeFlag(param));
    }
}

