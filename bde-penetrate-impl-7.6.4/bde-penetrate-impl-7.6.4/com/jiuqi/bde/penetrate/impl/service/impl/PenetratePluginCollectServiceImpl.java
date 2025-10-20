/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateCollectCondi
 *  com.jiuqi.bde.penetrate.client.dto.PenetratePluginDTO
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.penetrate.impl.service.impl;

import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.penetrate.client.dto.PenetrateCollectCondi;
import com.jiuqi.bde.penetrate.client.dto.PenetratePluginDTO;
import com.jiuqi.bde.penetrate.impl.pluginRegister.ApenetrationPlugin;
import com.jiuqi.bde.penetrate.impl.pluginRegister.PenetrateLevelEnum;
import com.jiuqi.bde.penetrate.impl.pluginRegister.plugin.CommonBalancePenetrate;
import com.jiuqi.bde.penetrate.impl.pluginRegister.plugin.CommonDetailLedgerPenetrate;
import com.jiuqi.bde.penetrate.impl.pluginRegister.plugin.CommonVoucherPenetrate;
import com.jiuqi.bde.penetrate.impl.service.PenetratePluginCollectService;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PenetratePluginCollectServiceImpl
implements PenetratePluginCollectService {
    @Autowired
    private List<ApenetrationPlugin> penetrationPluginList;
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;

    @Override
    public PenetratePluginDTO getPluginByCondition(PenetrateCollectCondi condi) {
        Assert.isNotEmpty((String)condi.getLevel(), (String)"\u7a7f\u900f\u63d2\u4ef6\u6536\u96c6\u51fa\u9519\uff0c\u5f53\u524d\u7a7f\u900f\u5c42\u6b21\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)condi.getBizModel(), (String)"\u7a7f\u900f\u63d2\u4ef6\u6536\u96c6\u51fa\u9519\uff0c\u5f53\u524d\u4e1a\u52a1\u6a21\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)condi.getUnitCode(), (String)"\u7a7f\u900f\u63d2\u4ef6\u6536\u96c6\u51fa\u9519\uff0c\u5f53\u524d\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        OrgMappingDTO orgMapping = this.orgMappingProvider.getByCode(condi.getBblx()).getOrgMapping(condi.getUnitCode());
        if (Objects.isNull(orgMapping)) {
            return this.getCommonPenetratePlugin(PenetrateLevelEnum.getEnumByCode(condi.getLevel()));
        }
        String dataSchemeCode = orgMapping.getDataSchemeCode();
        String pluginType = orgMapping.getPluginType();
        Assert.isNotEmpty((String)pluginType, (String)"\u7a7f\u900f\u63d2\u4ef6\u6536\u96c6\u51fa\u9519\uff0c\u6838\u7b97\u63d2\u4ef6\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        ApenetrationPlugin resultPlugin = null;
        PenetratePluginDTO penetratePluginDTO = new PenetratePluginDTO();
        for (ApenetrationPlugin plugin : this.penetrationPluginList) {
            List<ComputationModelEnum> bizModel = plugin.getBizModel();
            if (CollectionUtils.isEmpty(bizModel)) continue;
            List bizModelCodeList = plugin.getBizModel().stream().map(ComputationModelEnum::getCode).collect(Collectors.toList());
            if (!condi.getLevel().equalsIgnoreCase(plugin.getLevel().getCode()) || !bizModelCodeList.contains(condi.getBizModel().toUpperCase())) continue;
            if (StringUtils.isNotEmpty((String)plugin.getDataSchemeCode()) && StringUtils.isNotEmpty((String)dataSchemeCode)) {
                if (!dataSchemeCode.equalsIgnoreCase(plugin.getDataSchemeCode())) continue;
                penetratePluginDTO.setPluginName(plugin.getPluginName());
                penetratePluginDTO.setAppName(plugin.getAppName());
                penetratePluginDTO.setProdLine(plugin.getProdLine());
                return penetratePluginDTO;
            }
            if (!CollectionUtils.isEmpty(plugin.getAccountSoftwareType())) {
                if (!plugin.getAccountSoftwareType().contains(pluginType.toUpperCase())) continue;
                penetratePluginDTO.setPluginName(plugin.getPluginName());
                penetratePluginDTO.setAppName(plugin.getAppName());
                penetratePluginDTO.setProdLine(plugin.getProdLine());
                return penetratePluginDTO;
            }
            resultPlugin = plugin;
        }
        if (Objects.isNull(resultPlugin)) {
            penetratePluginDTO = this.getCommonPenetratePlugin(PenetrateLevelEnum.getEnumByCode(condi.getLevel()));
        } else {
            penetratePluginDTO.setPluginName(resultPlugin.getPluginName());
            penetratePluginDTO.setAppName(resultPlugin.getAppName());
            penetratePluginDTO.setProdLine(resultPlugin.getProdLine());
        }
        return penetratePluginDTO;
    }

    private PenetratePluginDTO getCommonPenetratePlugin(PenetrateLevelEnum level) {
        if (Objects.isNull((Object)level)) {
            return null;
        }
        PenetratePluginDTO penetratePluginDTO = new PenetratePluginDTO();
        switch (level) {
            case BALANCE: {
                CommonBalancePenetrate commonBalancePenetrate = new CommonBalancePenetrate();
                penetratePluginDTO.setPluginName(commonBalancePenetrate.getPluginName());
                penetratePluginDTO.setAppName(commonBalancePenetrate.getAppName());
                penetratePluginDTO.setProdLine(commonBalancePenetrate.getProdLine());
                return penetratePluginDTO;
            }
            case DETAIL_LEDGER: {
                CommonDetailLedgerPenetrate detailLedgerPenetrate = new CommonDetailLedgerPenetrate();
                penetratePluginDTO.setPluginName(detailLedgerPenetrate.getPluginName());
                penetratePluginDTO.setAppName(detailLedgerPenetrate.getAppName());
                penetratePluginDTO.setProdLine(detailLedgerPenetrate.getProdLine());
                return penetratePluginDTO;
            }
            case VOUCHER: {
                CommonVoucherPenetrate voucherPenetrate = new CommonVoucherPenetrate();
                penetratePluginDTO.setPluginName(voucherPenetrate.getPluginName());
                penetratePluginDTO.setAppName(voucherPenetrate.getAppName());
                penetratePluginDTO.setProdLine(voucherPenetrate.getProdLine());
                return penetratePluginDTO;
            }
        }
        throw new BdeRuntimeException("\u6682\u672a\u652f\u6301\u7684\u7a7f\u900f\u5c42\u7ea7");
    }
}

