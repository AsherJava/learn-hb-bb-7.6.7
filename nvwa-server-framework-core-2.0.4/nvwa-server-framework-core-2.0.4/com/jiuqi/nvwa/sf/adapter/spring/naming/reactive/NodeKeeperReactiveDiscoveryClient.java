/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.cloud.client.ServiceInstance
 *  org.springframework.cloud.client.discovery.ReactiveDiscoveryClient
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 *  reactor.core.scheduler.Schedulers
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.reactive;

import com.jiuqi.nvwa.sf.adapter.spring.naming.NodeKeeperDiscovery;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class NodeKeeperReactiveDiscoveryClient
implements ReactiveDiscoveryClient {
    private NodeKeeperDiscovery serviceDiscovery;
    public static final String DESCRIPTION = "Join-Cheer NVWA Basic Reactive Discovery Client";

    public NodeKeeperReactiveDiscoveryClient(NodeKeeperDiscovery nacosServiceDiscovery) {
        this.serviceDiscovery = nacosServiceDiscovery;
    }

    public String description() {
        return DESCRIPTION;
    }

    public Flux<ServiceInstance> getInstances(String serviceId) {
        return Mono.justOrEmpty((Object)serviceId).flatMapMany(this.loadInstancesFromNodeKeeper()).subscribeOn(Schedulers.boundedElastic());
    }

    private Function<String, Publisher<ServiceInstance>> loadInstancesFromNodeKeeper() {
        return serviceId -> Flux.fromIterable(this.serviceDiscovery.getInstances((String)serviceId));
    }

    public Flux<String> getServices() {
        return Flux.defer(() -> Flux.fromIterable(this.serviceDiscovery.getServices())).subscribeOn(Schedulers.boundedElastic());
    }

    public int getOrder() {
        return 1;
    }
}

