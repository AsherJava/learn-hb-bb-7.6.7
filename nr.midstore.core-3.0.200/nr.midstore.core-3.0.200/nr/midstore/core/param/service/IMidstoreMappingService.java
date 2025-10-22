/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping.web.vo.BaseDataVO
 */
package nr.midstore.core.param.service;

import com.jiuqi.nr.mapping.web.vo.BaseDataVO;
import java.util.Map;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;

public interface IMidstoreMappingService {
    public boolean checkMaping(MidstoreContext var1, MidstoreResultObject var2);

    public void initOrgMapping(MidstoreContext var1);

    public void initZbMapping(MidstoreContext var1);

    public void initPeriodMapping(MidstoreContext var1);

    public void initEnumMapping(MidstoreContext var1);

    public Map<String, BaseDataVO> getBaseDataItemMapping(MidstoreContext var1, String var2);

    public String getMapFieldCode(String var1);

    public String getMapTableCode(String var1);
}

