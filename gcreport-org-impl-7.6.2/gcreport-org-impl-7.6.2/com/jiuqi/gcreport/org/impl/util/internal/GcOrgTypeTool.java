/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 */
package com.jiuqi.gcreport.org.impl.util.internal;

import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgOtherModel;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import java.util.List;
import java.util.Map;

public class GcOrgTypeTool {
    private GcOrgOtherModel model;

    private GcOrgTypeTool(GcOrgOtherModel model) {
        this.model = model;
    }

    public static GcOrgTypeTool getInstance() {
        return new GcOrgTypeTool(GcOrgModelProvider.getGcOrgOtherModel());
    }

    public void createOrgType(OrgTypeVO vo) {
        try {
            OrgCategoryDO type = OrgParse.toVaType(vo);
            this.model.publishTable(type);
            this.model.getOrgTypeDao().addOrgType(type);
            this.model.publishEvent(EventChangeTypeEnum.INSERT, vo);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void modifyOrgType(OrgTypeVO vo) {
        try {
            OrgCategoryDO type = OrgParse.toVaType(vo);
            this.model.publishTable(type);
            type.addExtInfo("onlyEditBasicInfo", (Object)true);
            this.model.getOrgTypeDao().updateOrgType(type);
            this.model.publishEvent(EventChangeTypeEnum.UPDATE, vo);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u4fee\u6539\u673a\u6784\u7c7b\u578b-" + type.getName()), (String)JsonUtils.writeValueAsString((Object)vo));
        }
        catch (Exception e) {
            LogHelper.error((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u4fee\u6539\u673a\u6784\u7c7b\u578b-" + vo.getTable() + "\u5f02\u5e38"), (String)JsonUtils.writeValueAsString((Object)vo));
            throw new RuntimeException(e);
        }
    }

    public void removeOrgType(String typeName) {
        try {
            OrgTypeVO type = this.model.getOrgTypeDao().getOrgType(typeName);
            OrgTypeVO vo = new OrgTypeVO();
            vo.setId(type.getId());
            vo.setName(type.getName());
            this.model.getOrgTypeDao().removeOrgType(OrgParse.toVaType(vo));
            this.model.publishEvent(EventChangeTypeEnum.DELETE, vo);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u5220\u9664\u673a\u6784\u7c7b\u578b" + typeName), (String)"");
        }
        catch (Exception e) {
            LogHelper.error((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u5220\u9664\u673a\u6784\u7c7b\u578b" + typeName + "\u5f02\u5e38"), (String)"");
            throw new RuntimeException(e);
        }
    }

    public List<OrgTypeVO> listOrgType() {
        List<OrgTypeVO> orgTypes = this.model.getOrgTypeDao().listOrgType();
        return orgTypes;
    }

    public OrgTypeVO getOrgTypeByName(String typeCode) {
        return this.model.getOrgTypeDao().getOrgType(typeCode);
    }

    public Map<String, ZB> getTypeExtFieldsByName(String typeName) {
        return this.model.getOrgTypeDao().getTypeExtFieldsByName(typeName);
    }
}

