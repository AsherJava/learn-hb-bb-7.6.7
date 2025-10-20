/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public final class FilteredObjectInputStream
extends ObjectInputStream {
    private Set<String> rejectTypes = new HashSet<String>();
    private List<String> rejectPackages = new ArrayList<String>();
    private Class<?> rootClass;

    public FilteredObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    public void addReject(String rejectName) {
        int p = rejectName.indexOf(42);
        if (p < 0) {
            this.rejectTypes.add(rejectName);
        } else if (p == rejectName.length() - 1) {
            this.rejectPackages.add(rejectName.substring(0, rejectName.length() - 1));
        } else {
            throw new IllegalArgumentException("\u65e0\u6548\u7684\u5305\u9650\u5b9a\uff1a" + rejectName);
        }
    }

    private boolean isRejected(String className) {
        if (Cache.REJECT_TYPES.contains(className) || this.rejectTypes.contains(className)) {
            return true;
        }
        return Stream.concat(Cache.REJECT_PACKAGES.stream(), this.rejectPackages.stream()).anyMatch(className::startsWith);
    }

    public void filterClass(Class<?> klass) {
        this.rootClass = Objects.requireNonNull(klass);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        if (this.isRejected(desc.getName())) {
            throw new ClassNotFoundException("\u53cd\u5e8f\u5217\u5316\u65f6\u9047\u5230\u7981\u6b62\u4f7f\u7528\u7684\u7c7b\u578b\uff1a" + desc.getName());
        }
        Class<?> klass = super.resolveClass(desc);
        if (this.rootClass != null) {
            if (!this.rootClass.isAssignableFrom(klass)) {
                throw new ClassNotFoundException("\u53cd\u5e8f\u5217\u5316\u5bf9\u8c61\u51fa\u9519\uff0c\u89e3\u6790\u7c7b\u578b\u4e0d\u662f\u671f\u5f85\u7684\u7c7b\u578b\uff1a" + desc.getName());
            }
            this.rootClass = null;
        }
        return klass;
    }

    public <T> T readObject(Class<? extends T> klass) throws IOException, ClassNotFoundException {
        this.filterClass(klass);
        return (T)this.readObject();
    }

    private static final class Cache {
        private static final Set<String> REJECT_TYPES = new HashSet<String>();
        private static final List<String> REJECT_PACKAGES;

        private Cache() {
        }

        static {
            REJECT_TYPES.add("org.apache.commons.collections.functors.InvokerTransformer");
            REJECT_TYPES.add("org.apache.commons.collections.functors.ChainedTransformer");
            REJECT_TYPES.add("org.apache.commons.collections.map.LazyMap");
            REJECT_TYPES.add("org.springframework.beans.factory.ObjectFactory");
            REJECT_TYPES.add("org.springframework.beans.factory.config.BeanExpressionResolver");
            REJECT_TYPES.add("com.mysql.cj.jdbc.admin.MiniAdmin");
            REJECT_TYPES.add("java.lang.Runtime");
            REJECT_TYPES.add("java.lang.ProcessBuilder");
            REJECT_TYPES.add("javax.naming.InitialContext");
            REJECT_TYPES.add("javax.el.ELProcessor");
            REJECT_TYPES.add("com.mchange.v2.c3p0.WrapperConnectionPoolDataSource");
            REJECT_TYPES.add("org.hibernate.property.BasicPropertyAccessor");
            REJECT_TYPES.add("sun.reflect.annotation.AnnotationInvocationHandler");
            REJECT_PACKAGES = Arrays.asList("jdk.nashorn.", "org.python.", "groovy.", "org.codehaus.groovy.");
        }
    }
}

