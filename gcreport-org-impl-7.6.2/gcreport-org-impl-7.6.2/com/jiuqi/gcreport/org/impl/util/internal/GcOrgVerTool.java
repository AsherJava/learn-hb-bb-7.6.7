/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.util.internal;

import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgOtherModel;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.org.OrgVersionDO;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class GcOrgVerTool {
    private GcOrgOtherModel model;

    private GcOrgVerTool(GcOrgOtherModel service) {
        this.model = service;
    }

    public static GcOrgVerTool getInstance() {
        return new GcOrgVerTool(GcOrgModelProvider.getGcOrgOtherModel());
    }

    @Transactional(rollbackFor={Exception.class})
    public void createOrgVersion(OrgVersionVO vo) {
        this.model.getOrgTypeDao().addOrgVersion((OrgVersionDO)OrgParse.toVaVersion(vo));
        this.model.publishEvent(EventChangeTypeEnum.INSERT, vo.getOrgType(), Arrays.asList(vo));
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u65b0\u589e\u7248\u672c-\u673a\u6784\u7c7b\u578b" + vo.getOrgType().getName() + "-\u7248\u672c" + vo.getName()), (String)"");
    }

    public void modifyOrgVersion(OrgVersionVO vo) {
        this.model.getOrgTypeDao().updateOrgVersion((OrgVersionDO)OrgParse.toVaVersion(vo));
        this.model.publishEvent(EventChangeTypeEnum.UPDATE, vo.getOrgType(), Arrays.asList(vo));
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u4fee\u6539\u7248\u672c-\u673a\u6784\u7c7b\u578b" + vo.getOrgType().getName() + "-\u7248\u672c" + vo.getName()), (String)"");
    }

    public List<OrgVersionVO> listOrgVersion(String typeName) {
        OrgTypeVO type = this.model.getOrgTypeDao().getOrgType(typeName);
        return this.model.getOrgTypeDao().listOrgVersionByType(type);
    }

    public OrgVersionVO getOrgVersionByCode(String typeName, String verName) {
        return this.model.getOrgTypeDao().getOrgVersion(this.model.getOrgTypeDao().getOrgType(typeName), verName);
    }

    public OrgVersionVO getOrgVersionByDate(String typeName, Date date) {
        return this.model.getOrgTypeDao().getOrgVersion(this.model.getOrgTypeDao().getOrgType(typeName), date);
    }

    public boolean removeOrgVersion(OrgVersionVO vo) {
        boolean b = this.model.getOrgTypeDao().removeOrgVersion((OrgVersionDO)OrgParse.toVaVersion(vo));
        this.model.publishEvent(EventChangeTypeEnum.DELETE, vo.getOrgType(), Arrays.asList(vo));
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u5220\u9664\u7248\u672c-\u673a\u6784\u7c7b\u578b" + vo.getOrgType().getName() + "-\u7248\u672c" + vo.getName()), (String)"");
        return b;
    }

    public boolean splitOrgVersion(OrgVersionVO vo) {
        boolean b = this.model.getOrgTypeDao().splitOrgVersion((OrgVersionDO)OrgParse.toVaVersion(vo));
        this.model.publishEvent(EventChangeTypeEnum.UPDATE, vo.getOrgType(), Arrays.asList(vo));
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u62c6\u5206\u7248\u672c-\u673a\u6784\u7c7b\u578b" + vo.getOrgType().getName() + "-\u7248\u672c" + vo.getName()), (String)("\u62c6\u5206\u53c2\u6570\uff1a" + JsonUtils.writeValueAsString((Object)vo)));
        return b;
    }
}

