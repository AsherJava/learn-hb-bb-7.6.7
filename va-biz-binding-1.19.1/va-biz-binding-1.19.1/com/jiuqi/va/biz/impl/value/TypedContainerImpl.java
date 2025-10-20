/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.value;

import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.biz.intf.value.TypedElement;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypedContainerImpl<T extends TypedElement>
implements TypedContainer<T> {
    private final List<T> list;
    private final Map<String, T> map;
    private final ConcurrentHashMap<Class<?>, Object> concurrentHashMap = new ConcurrentHashMap();

    public TypedContainerImpl(List<T> list) {
        this.list = list;
        this.map = list.stream().collect(Collectors.toMap(TypedElement::getType, Function.identity(), (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        }, LinkedHashMap::new));
    }

    public TypedContainerImpl(LinkedHashMap<String, T> map) {
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
        return (T)((TypedElement)this.map.get(name));
    }

    @Override
    public T get(String name) {
        TypedElement result = (TypedElement)this.map.get(name);
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
        return (T)((TypedElement)this.list.get(index));
    }

    @Override
    public int findIndex(Predicate<T> predicate) {
        for (int i = 0; i < this.list.size(); ++i) {
            if (!predicate.test(this.list.get(i))) continue;
            return i;
        }
        return -1;
    }

    @Override
    public <S> S find(Class<S> elementClass) {
        return (S)this.concurrentHashMap.computeIfAbsent(elementClass, key -> this.stream().filter(o -> elementClass.isInstance(o)).findFirst().orElse(null));
    }

    @Override
    public <S> S get(Class<S> elementClass) {
        S result = this.find(elementClass);
        if (result == null) {
            throw new MissingObjectException(BizBindingI18nUtil.getMessage("va.bizbinding.getobjfailed") + elementClass);
        }
        return result;
    }
}

