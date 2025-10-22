/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.SecurityLevel
 *  com.jiuqi.np.authz2.service.SecurityLevelService
 */
package com.jiuqi.nr.analysisreport.securitylevel;

import com.jiuqi.np.authz2.SecurityLevel;
import com.jiuqi.np.authz2.service.SecurityLevelService;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SecurityLevelHelper {
    @Autowired
    private SecurityLevelService securityLevelService;

    public SecurityLevel getMinSystemSecurityLevel() {
        List systemSecurityLevels = this.securityLevelService.getSystemSecurityLevels();
        if (CollectionUtils.isEmpty(systemSecurityLevels)) {
            return null;
        }
        systemSecurityLevels.sort(new Comparator<SecurityLevel>(){

            @Override
            public int compare(SecurityLevel o1, SecurityLevel o2) {
                Integer v1 = Integer.valueOf(o1.getName());
                Integer v2 = Integer.valueOf(o2.getName());
                return v1 - v2;
            }
        });
        return (SecurityLevel)systemSecurityLevels.get(0);
    }

    public SecurityLevel getMinUserSecurityLevel() {
        List userSecurityLevels = this.securityLevelService.getUserSecurityLevels();
        if (CollectionUtils.isEmpty(userSecurityLevels)) {
            return null;
        }
        userSecurityLevels.sort(new Comparator<SecurityLevel>(){

            @Override
            public int compare(SecurityLevel o1, SecurityLevel o2) {
                Integer v1 = Integer.valueOf(o1.getName());
                Integer v2 = Integer.valueOf(o2.getName());
                return v1 - v2;
            }
        });
        return (SecurityLevel)userSecurityLevels.get(0);
    }
}

