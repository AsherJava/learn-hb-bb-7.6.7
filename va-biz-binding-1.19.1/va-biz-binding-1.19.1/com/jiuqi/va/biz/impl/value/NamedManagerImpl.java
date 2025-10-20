/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.value;

import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.biz.intf.value.NamedManager;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NamedManagerImpl<T extends NamedElement>
implements NamedManager<T>,
InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired(required=false)
    private final List<T> list = new ArrayList<T>();
    private final Map<String, T> map = new HashMap<String, T>();

    @Override
    public void afterPropertiesSet() throws Exception {
        for (NamedElement t : this.list) {
            if (this.map.putIfAbsent(t.getName(), t) == null) continue;
            this.logger.warn("\u91cd\u590d\u6ce8\u518c\uff1a" + t.getName());
        }
    }

    @Override
    public Stream<T> stream() {
        return this.map.values().stream();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public void forEachName(BiConsumer<String, T> consumer) {
        this.map.forEach(consumer);
    }

    @Override
    public T find(String name) {
        return (T)((NamedElement)this.map.get(name));
    }

    @Override
    public T get(String name) {
        NamedElement result = (NamedElement)this.map.get(name);
        if (result == null) {
            throw new MissingObjectException(BizBindingI18nUtil.getMessage("va.bizbinding.getobjfailed") + name);
        }
        return (T)result;
    }

    @Override
    public void forEachIndex(BiConsumer<Integer, T> consumer) {
        for (int i = 0; i < this.list.size(); ++i) {
            consumer.accept(i, (Integer)this.list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return (T)((NamedElement)this.list.get(index));
    }

    @Override
    public int findIndex(Predicate<T> predicate) {
        for (int i = 0; i < this.list.size(); ++i) {
            if (!predicate.test(this.list.get(i))) continue;
            return i;
        }
        return -1;
    }
}

