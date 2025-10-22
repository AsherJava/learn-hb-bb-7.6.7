/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyAuthzParam
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyParam
 */
package com.jiuqi.gcreport.listedcompanyauthz.base;

import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyAuthzEO;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyEO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyAuthzParam;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.ListedCompanyParam;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class BeanUtil {
    public static ListedCompanyAuthzVO authzParam2VO(ListedCompanyAuthzParam source) {
        ListedCompanyAuthzVO target = new ListedCompanyAuthzVO();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static ListedCompanyVO companyParam2VO(ListedCompanyParam source) {
        ListedCompanyVO target = new ListedCompanyVO();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static ListedCompanyAuthzEO authzVO2EO(ListedCompanyAuthzVO source) {
        ListedCompanyAuthzEO target = new ListedCompanyAuthzEO();
        BeanUtils.copyProperties(source, (Object)target);
        return target;
    }

    public static ListedCompanyEO companyVO2EO(ListedCompanyVO source) {
        ListedCompanyEO target = new ListedCompanyEO();
        BeanUtils.copyProperties(source, (Object)target);
        return target;
    }

    public static ListedCompanyAuthzVO authzEO2VO(ListedCompanyAuthzEO source) {
        ListedCompanyAuthzVO target = new ListedCompanyAuthzVO();
        BeanUtils.copyProperties((Object)source, target);
        return target;
    }

    public static ListedCompanyVO companyEO2VO(ListedCompanyEO source) {
        ListedCompanyVO target = new ListedCompanyVO();
        BeanUtils.copyProperties((Object)source, target);
        return target;
    }

    public static List<ListedCompanyAuthzVO> authzEO2VO(List<ListedCompanyAuthzEO> source) {
        return source.stream().map(v -> BeanUtil.authzEO2VO(v)).collect(Collectors.toList());
    }

    public static List<ListedCompanyVO> companyEO2VO(List<ListedCompanyEO> source) {
        return source.stream().map(v -> BeanUtil.companyEO2VO(v)).collect(Collectors.toList());
    }
}

