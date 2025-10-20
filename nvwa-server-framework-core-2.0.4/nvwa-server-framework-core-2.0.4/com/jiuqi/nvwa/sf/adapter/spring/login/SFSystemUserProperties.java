/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.login;

import com.jiuqi.nvwa.sf.models.Users;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.np.user")
public class SFSystemUserProperties {
    private List<Users.User> system = new ArrayList<Users.User>();

    public List<Users.User> getSystem() {
        return this.system;
    }

    public void setSystem(List<Users.User> system) {
        this.system = system;
    }
}

