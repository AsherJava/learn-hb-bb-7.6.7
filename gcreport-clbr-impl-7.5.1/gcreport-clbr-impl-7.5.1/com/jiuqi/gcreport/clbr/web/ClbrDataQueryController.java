/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.api.ClbrDataQueryClient
 *  com.jiuqi.gcreport.clbr.enums.ClbrDataQueryTypeEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 *  com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.clbr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.api.ClbrDataQueryClient;
import com.jiuqi.gcreport.clbr.enums.ClbrDataQueryTypeEnum;
import com.jiuqi.gcreport.clbr.service.ClbrDataQueryService;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClbrDataQueryController
implements ClbrDataQueryClient {
    @Autowired
    private ClbrDataQueryService clbrDataQueryService;

    public BusinessResponseEntity<ClbrOverViewVO> queryClbrOverView(ClbrDataQueryConditon clbrDataQueryConditon) {
        return BusinessResponseEntity.ok((Object)this.clbrDataQueryService.queryClbrOverView(clbrDataQueryConditon));
    }

    public BusinessResponseEntity<List<ClbrOverViewVO>> listClbrOverView(ClbrDataQueryConditon clbrDataQueryConditon) {
        return BusinessResponseEntity.ok(this.clbrDataQueryService.listClbrOverView(clbrDataQueryConditon));
    }

    public BusinessResponseEntity<PageInfo<ClbrBillVO>> queryClbrBillDetails(ClbrDataQueryConditon clbrDataQueryConditon) {
        ClbrDataQueryTypeEnum clbrDataQueryTypeEnum = ClbrDataQueryTypeEnum.getEnumByCode((String)clbrDataQueryConditon.getQueryDataType());
        if (ClbrDataQueryTypeEnum.TOTAL.equals((Object)clbrDataQueryTypeEnum)) {
            return BusinessResponseEntity.ok(this.clbrDataQueryService.queryDetailsTotal(clbrDataQueryConditon));
        }
        if (ClbrDataQueryTypeEnum.CONFIRM.equals((Object)clbrDataQueryTypeEnum)) {
            return BusinessResponseEntity.ok(this.clbrDataQueryService.queryConfirmDetails(clbrDataQueryConditon));
        }
        if (ClbrDataQueryTypeEnum.PARTCONFIRM.equals((Object)clbrDataQueryTypeEnum)) {
            return BusinessResponseEntity.ok(this.clbrDataQueryService.queryPartConfirmDetails(clbrDataQueryConditon));
        }
        if (ClbrDataQueryTypeEnum.NOTCONFIRM.equals((Object)clbrDataQueryTypeEnum)) {
            return BusinessResponseEntity.ok(this.clbrDataQueryService.queryNotConfirmDetails(clbrDataQueryConditon));
        }
        if (ClbrDataQueryTypeEnum.REJECT.equals((Object)clbrDataQueryTypeEnum)) {
            return BusinessResponseEntity.ok(this.clbrDataQueryService.queryRejectDetails(clbrDataQueryConditon));
        }
        return BusinessResponseEntity.ok();
    }
}

