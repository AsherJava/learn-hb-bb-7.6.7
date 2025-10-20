/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.register;

import com.jiuqi.nvwa.sf.adapter.spring.naming.register.NodeKeeperAutoServiceRegistration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Order(value=-2147483648)
public class NodeKeeperConfig
implements ApplicationRunner {
    private NodeKeeperAutoServiceRegistration registration;

    public NodeKeeperConfig(NodeKeeperAutoServiceRegistration nodeKeeperAutoServiceRegistration) {
        this.registration = nodeKeeperAutoServiceRegistration;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (this.registration != null) {
            this.registration.start();
        }
    }
}

