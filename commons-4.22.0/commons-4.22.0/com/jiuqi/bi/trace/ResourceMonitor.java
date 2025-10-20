/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.trace;

import com.jiuqi.bi.trace.ResourceAction;
import com.jiuqi.bi.trace.ResourceDescriptor;
import com.jiuqi.bi.trace.ResourceMonitorException;
import com.jiuqi.bi.trace.ResourceObject;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ResourceMonitor {
    private static final List<ResourceDescriptor> resources = new ArrayList<ResourceDescriptor>();
    private static volatile boolean enabled;
    private static final ConcurrentMap<Object, ResourceObject> resourceObjects;
    private static final ConcurrentMap<Long, ResourceObject> resourceFinder;
    public static final String ACTION_OPEN = "OPEN";
    public static final String ACTION_TRANSACTION = "TRANSACTION";

    private ResourceMonitor() {
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean isEnabled) {
        enabled = isEnabled;
        resourceObjects.clear();
        resourceFinder.clear();
    }

    public static synchronized void defineResource(ResourceDescriptor descriptor) {
        resources.add(descriptor);
    }

    public static void defineResource(String catagory, String type, Class<?> klass) {
        ResourceMonitor.defineResource(new ResourceDescriptor(catagory, type, klass));
    }

    public static void defineResource(String catagory, String type, Class<?> klass, String ... actions) {
        ResourceMonitor.defineResource(new ResourceDescriptor(catagory, type, klass, actions));
    }

    private static ResourceDescriptor locateResource(Object obj) {
        if (obj == null) {
            return null;
        }
        for (ResourceDescriptor resource : resources) {
            if (!resource.isInstance(obj)) continue;
            return resource;
        }
        return null;
    }

    public static void createResource(Object resource) throws ResourceMonitorException {
        if (!enabled) {
            return;
        }
        if (resourceObjects.containsKey(resource)) {
            throw new ResourceMonitorException("\u91cd\u590d\u521b\u5efa\u6216\u521d\u59cb\u5316\u5bf9\u8c61\uff1a" + resource);
        }
        ResourceDescriptor descriptor = ResourceMonitor.locateResource(resource);
        if (descriptor == null) {
            return;
        }
        ResourceObject resObject = new ResourceObject(descriptor, resource);
        resourceObjects.put(resource, resObject);
        resourceFinder.put(resObject.getId(), resObject);
    }

    public static void closeResource(Object resource) {
        if (!enabled) {
            return;
        }
        ResourceObject resObject = (ResourceObject)resourceObjects.remove(resource);
        if (resObject != null) {
            resourceFinder.remove(resObject.getId());
        }
    }

    public static void startAction(Object resource, String action) {
        if (!enabled) {
            return;
        }
        ResourceDescriptor descriptor = ResourceMonitor.locateResource(resource);
        if (descriptor == null) {
            return;
        }
        ResourceObject resObject = (ResourceObject)resourceObjects.get(resource);
        if (resObject == null) {
            return;
        }
        ResourceAction resAction = new ResourceAction(action);
        resObject.putAction(resAction);
    }

    public static void finishAction(Object resource, String action) {
        if (!enabled) {
            return;
        }
        ResourceDescriptor descriptor = ResourceMonitor.locateResource(resource);
        if (descriptor == null) {
            return;
        }
        ResourceObject resObject = (ResourceObject)resourceObjects.get(resource);
        if (resObject == null) {
            return;
        }
        resObject.removeAction(action);
    }

    public static List<String> getDefinedCatagories() {
        ArrayList<String> catagories = new ArrayList<String>();
        for (ResourceDescriptor resource : resources) {
            if (catagories.contains(resource.getCatagory())) continue;
            catagories.add(resource.getCatagory());
        }
        return catagories;
    }

    public static List<String> getDefinedTypes(String catagory) {
        ArrayList<String> types = new ArrayList<String>();
        for (ResourceDescriptor resource : resources) {
            if (catagory != null && !resource.getCatagory().equals(catagory) || types.contains(resource.getType())) continue;
            types.add(resource.getType());
        }
        return types;
    }

    public static List<String> getDefinedActions(String catagory, String type) {
        ArrayList<String> actions = new ArrayList<String>();
        for (ResourceDescriptor resource : resources) {
            if (!ResourceMonitor.filter(catagory, type, resource)) continue;
            for (String action : resource.getActions()) {
                if (actions.contains(action)) continue;
                actions.add(action);
            }
        }
        return actions;
    }

    private static boolean filter(String catagory, String type, ResourceDescriptor descriptor) {
        return !(catagory != null && !descriptor.getCatagory().equals(catagory) || type != null && !descriptor.getType().equals(type));
    }

    public static List<ResourceObject> getLivingResources(String catagory, String type) {
        ArrayList<ResourceObject> resources = new ArrayList<ResourceObject>();
        for (ResourceObject resource : resourceObjects.values()) {
            if (!ResourceMonitor.filter(catagory, type, resource.getDescriptor())) continue;
            resources.add(resource);
        }
        return resources;
    }

    public static List<ResourceObject> getLivingResource(Predicate<ResourceDescriptor> filter) {
        Objects.requireNonNull(filter);
        return resourceObjects.values().stream().filter(res -> filter.test(res.getDescriptor())).collect(Collectors.toList());
    }

    public static ResourceObject getLivingResource(long id) {
        return (ResourceObject)resourceFinder.get(id);
    }

    public static ResourceObject getLivingResource(Object resource) {
        return (ResourceObject)resourceObjects.get(resource);
    }

    public static void printTrace(String fileName) throws IOException {
        try (PrintStream s = new PrintStream(fileName);){
            ResourceMonitor.printTrace(s);
        }
    }

    public static void printTrace(PrintStream s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss(SSS)");
        s.print("Snapshot Resource Monitor at ");
        s.print(format.format(new Date()));
        if (!enabled) {
            s.println(", monitor is disabled.");
            return;
        }
        s.println(", monitor is running.");
        List<String> catagories = ResourceMonitor.getDefinedCatagories();
        for (String catagory : catagories) {
            s.println("----------------");
            List<String> types = ResourceMonitor.getDefinedTypes(catagory);
            for (String type : types) {
                List<ResourceObject> resources = ResourceMonitor.getLivingResources(catagory, type);
                s.format("%s.%-12s:%,8d objects.%n", catagory, type, resources.size());
                int num = 1;
                for (ResourceObject resource : resources) {
                    s.printf("\t------ %d.%s.%s ------%n", num++, catagory, type);
                    resource.printTrace(s, 1);
                }
            }
        }
    }

    static {
        String enabledProp = System.getProperty("jiuqi.nvwa.enableResourceMonitor");
        enabled = Boolean.parseBoolean(enabledProp);
        resourceObjects = new ConcurrentHashMap<Object, ResourceObject>();
        resourceFinder = new ConcurrentHashMap<Long, ResourceObject>();
    }
}

