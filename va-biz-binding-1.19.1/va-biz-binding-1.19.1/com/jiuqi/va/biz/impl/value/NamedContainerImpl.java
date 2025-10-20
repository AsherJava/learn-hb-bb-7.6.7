/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.value;

import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NamedContainerImpl<T extends NamedElement>
implements NamedContainer<T> {
    private final List<T> list;
    private final Map<String, T> map;

    public NamedContainerImpl(List<T> list) {
        this.list = list;
        this.map = list.stream().collect(Collectors.toMap(NamedElement::getName, Function.identity(), (u, v) -> {
            throw new IllegalStateException(BizBindingI18nUtil.getMessage("va.bizbinding.namecontainerimpl.fieldrepeat") + u.getName());
        }, LinkedHashMap::new));
    }

    public NamedContainerImpl(LinkedHashMap<String, T> map) {
        this.map = map;
        this.list = map.values().stream().collect(Collectors.toList());
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

