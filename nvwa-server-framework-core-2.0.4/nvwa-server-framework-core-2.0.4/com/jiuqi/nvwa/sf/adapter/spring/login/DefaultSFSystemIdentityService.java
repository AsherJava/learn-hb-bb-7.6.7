/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 */
package com.jiuqi.nvwa.sf.adapter.spring.login;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.login.SFSystemUserProperties;
import com.jiuqi.nvwa.sf.models.ISFSystemIdentityService;
import com.jiuqi.nvwa.sf.models.Users;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DefaultSFSystemIdentityService
implements ISFSystemIdentityService {
    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean checkUser(String user, String pass) {
        Users.User u = null;
        List<Users.User> system = ((SFSystemUserProperties)SpringBeanUtils.getBean(SFSystemUserProperties.class)).getSystem();
        for (Users.User user1 : system) {
            if (!user1.getName().equalsIgnoreCase(user)) continue;
            u = user1;
            break;
        }
        if (u == null) {
            return false;
        }
        if (u.getEncoder() == null) {
            if (StringUtils.isEmpty((String)u.getPassword())) {
                throw new RuntimeException("\u914d\u7f6e\u6587\u4ef6\u5bc6\u7801\u7981\u6b62\u4e3a\u7a7a");
            }
            return u.getPassword().equals(pass);
        }
        if ("BCrypt".equalsIgnoreCase(u.getEncoder())) {
            BCryptPasswordEncoder bean = (BCryptPasswordEncoder)SpringBeanUtils.getBean((String)"bcryptPasswordEncoder", BCryptPasswordEncoder.class);
            if (StringUtils.isEmpty((String)u.getPassword())) {
                return user.equals(pass);
            }
            return bean.matches((CharSequence)pass, u.getPassword());
        }
        throw new RuntimeException("\u672a\u77e5\u7684\u52a0\u5bc6\u7b97\u6cd5\uff01" + u.getEncoder());
    }
}

