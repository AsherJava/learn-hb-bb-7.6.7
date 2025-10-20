/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.dc.penetratebill.client.common.CustomParam
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateContext
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateParam
 *  com.jiuqi.dc.penetratebill.impl.define.IPenetrateHandler
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.bde.plugin.va6.penetrate.voucher;

import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.dc.penetratebill.client.common.CustomParam;
import com.jiuqi.dc.penetratebill.client.common.PenetrateContext;
import com.jiuqi.dc.penetratebill.client.common.PenetrateParam;
import com.jiuqi.dc.penetratebill.impl.define.IPenetrateHandler;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherPenetrateBillHandler
implements IPenetrateHandler {
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    private DataSourceService dataSourceService;

    public String getCode() {
        return "XIBUJICHAGN";
    }

    public String getName() {
        return "\u897f\u90e8\u673a\u573a\u51ed\u8bc1\u7a7f\u900f\u5904\u7406\u5668";
    }

    public String getOrdinal() {
        return "10";
    }

    public List<CustomParam> getCustomParam() {
        return CollectionUtils.newArrayList((Object[])new CustomParam[]{new CustomParam("address", "\u7a7f\u900f\u5730\u5740")});
    }

    public PenetrateParam handlePenetrate(PenetrateContext context, List<CustomParam> customParams, String openWay, String billNoField) {
        OrgMappingDTO orgMapping = this.orgMappingProvider.getByCode(null).getOrgMapping(context.getUnitCode());
        String dataSourceCode = orgMapping.getDataSourceCode();
        String address = "";
        if (!CollectionUtils.isEmpty(customParams)) {
            address = customParams.get(0).getValue();
        }
        String vchrId = context.getVchrId();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT RAWTOHEX(BILLDEFINE),RAWTOHEX(BILLID) FROM GL_VOUCHER_BILLRALATION WHERE VOUCHERID = HEXTORAW('").append(vchrId).append("')");
        Map queryResult = (Map)this.dataSourceService.query(dataSourceCode, sql.toString(), null, rs -> {
            if (rs.next()) {
                HashMap<String, String> idMap = new HashMap<String, String>();
                idMap.put("defineId", rs.getString(1));
                idMap.put("billId", rs.getString(2));
                return idMap;
            }
            return null;
        });
        Assert.isNotNull((Object)queryResult, (String)"\u5f53\u524d\u51ed\u8bc1\u6ca1\u6709\u67e5\u8be2\u5230\u5355\u636e\u4fe1\u606f\uff0c\u4e0d\u652f\u6301\u8054\u67e5", (Object[])new Object[0]);
        String defineId = (String)queryResult.get("defineId");
        String billId = (String)queryResult.get("billId");
        String userName = NpContextHolder.getContext().getUserName();
        String url = address.replace("##USERNAME##", userName).replace("##DEFINEID##", defineId).replace("##DATAID##", billId);
        HashMap<String, String> extParam = new HashMap<String, String>();
        extParam.put("openWay", openWay);
        PenetrateParam penetrateParam = new PenetrateParam();
        penetrateParam.setUrl(url);
        penetrateParam.setExtParam(extParam);
        return penetrateParam;
    }
}

