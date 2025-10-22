/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.common.R
 */
package nr.single.para.basedata;

import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.common.R;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IBaseDataVerService {
    public void updateUseVersion(BaseDataDefineDO var1);

    public List<BaseDataVersionDO> listVersion(BaseDataVersionDTO var1);

    public BaseDataVersionDO getVersion(BaseDataVersionDTO var1);

    public R updateVersion(BaseDataVersionDTO var1);

    public R insertVersion(BaseDataVersionDTO var1);

    public BaseDataVersionDO insertYearVerion(String var1, int var2, boolean var3) throws Exception;

    public BaseDataVersionDO queryYearVerion(String var1, int var2, boolean var3) throws Exception;

    public Date[] getDateRegion(int var1) throws ParseException;
}

