/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration
 *  org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties
 *  org.springframework.cloud.client.serviceregistry.Registration
 *  org.springframework.cloud.client.serviceregistry.ServiceRegistry
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.register;

import com.jiuqi.nvwa.sf.adapter.spring.naming.register.NodeKeeperRegistration;
import com.jiuqi.nvwa.sf.adapter.spring.naming.register.NodeKeeperServiceRegistry;
import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class NodeKeeperAutoServiceRegistration
extends AbstractAutoServiceRegistration<Registration> {
    private NodeKeeperServiceRegistry registry;
    private NodeKeeperRegistration registration;

    public NodeKeeperAutoServiceRegistration(NodeKeeperServiceRegistry registry, AutoServiceRegistrationProperties properties, NodeKeeperRegistration registration) {
        super((ServiceRegistry)registry, properties);
        this.registry = registry;
        this.registration = registration;
    }

    protected Object getConfiguration() {
        return null;
    }

    protected boolean isEnabled() {
        return true;
    }

    protected Registration getRegistration() {
        return this.registration;
    }

    protected Registration getManagementRegistration() {
        return null;
    }
}

