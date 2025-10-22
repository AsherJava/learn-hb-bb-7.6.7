/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.NpApplication
 */
package com.jiuqi.np.definition.observer;

import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class NpDefinitionObserverable
implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private NpApplication npApplication;
    private Map<MessageType, List<Observer>> map;
    private Map<String, List<Observer>> unknownMap;
    private Logger logger = LoggerFactory.getLogger(NpDefinitionObserverable.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(NpDefinitionObserver.class);
        if (beans == null || beans.size() == 0) {
            return;
        }
        Collection<Object> observers = beans.values();
        for (Object obj : observers) {
            this.addObserver(obj);
        }
    }

    public Map<Boolean, List<String>> notify(MessageType type, Object[] objs) throws Exception {
        if (this.map == null || this.map.isEmpty()) {
            return Collections.emptyMap();
        }
        List<Observer> list = this.map.get((Object)type);
        if (list == null || list.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<Boolean, List<String>> resultMap = new HashMap<Boolean, List<String>>();
        for (Observer observer : list) {
            if (observer.isAsyn()) {
                this.npApplication.asyncRun(() -> {
                    try {
                        observer.excute(objs);
                        this.addSuccess(resultMap, observer);
                    }
                    catch (Exception e) {
                        this.logger.error("\u53d1\u5e03\u540e\u4e8b\u4ef6\u5931\u8d25", e);
                        this.addFailed(resultMap, observer, e);
                    }
                });
                continue;
            }
            try {
                observer.excute(objs);
                this.addSuccess(resultMap, observer);
            }
            catch (Exception e) {
                this.logger.error("\u53d1\u5e03\u540e\u4e8b\u4ef6\u5931\u8d25", e);
                this.addFailed(resultMap, observer, e);
            }
        }
        return resultMap;
    }

    private void addFailed(Map<Boolean, List<String>> resultMap, Observer observer, Exception e) {
        List<String> failList = resultMap.get(false);
        if (failList == null) {
            failList = new ArrayList<String>();
            resultMap.put(false, failList);
        }
        failList.add(observer.getName() + (e == null ? "" : ":" + e.getMessage()));
    }

    private void addSuccess(Map<Boolean, List<String>> resultMap, Observer observer) {
        List<String> successList = resultMap.get(true);
        if (successList == null) {
            successList = new ArrayList<String>();
            resultMap.put(true, successList);
        }
        successList.add(observer.getName());
    }

    public Map<Boolean, List<String>> notify(String type, Object[] objs) throws Exception {
        if (this.unknownMap == null || this.unknownMap.isEmpty()) {
            return Collections.emptyMap();
        }
        List<Observer> list = this.unknownMap.get(type);
        if (list == null || list.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<Boolean, List<String>> resultMap = new HashMap<Boolean, List<String>>();
        for (Observer observer : list) {
            if (observer.isAsyn()) {
                this.npApplication.asyncRun(() -> {
                    try {
                        observer.excute(objs);
                        this.addSuccess(resultMap, observer);
                    }
                    catch (Exception e) {
                        this.logger.error("\u53d1\u5e03\u540e\u4e8b\u4ef6\u5931\u8d25", e);
                        this.addFailed(resultMap, observer, e);
                    }
                });
                continue;
            }
            try {
                observer.excute(objs);
                this.addSuccess(resultMap, observer);
            }
            catch (Exception e) {
                this.logger.error("\u53d1\u5e03\u540e\u4e8b\u4ef6\u5931\u8d25", e);
                this.addFailed(resultMap, observer, e);
            }
        }
        return resultMap;
    }

    private void addObserver(MessageType type, Observer obj) {
        List<Observer> list;
        if (this.map == null) {
            this.map = new HashMap<MessageType, List<Observer>>();
        }
        if ((list = this.map.get((Object)type)) == null) {
            list = new ArrayList<Observer>();
            this.map.put(type, list);
        }
        for (Observer o : list) {
            if (o.getClass() != obj.getClass()) continue;
            return;
        }
        list.add(obj);
    }

    public void addObserver(Object obj) {
        Object relObj = this.getTarget(obj);
        NpDefinitionObserver[] observerAnno = (NpDefinitionObserver[])relObj.getClass().getAnnotationsByType(NpDefinitionObserver.class);
        if (observerAnno == null || observerAnno.length == 0) {
            return;
        }
        MessageType[] types = observerAnno[0].type();
        if (types == null || types.length == 0) {
            return;
        }
        block3: for (MessageType type : types) {
            switch (type) {
                case UNKNOWN: {
                    this.addObserver(observerAnno[0].name(), (Observer)obj);
                    continue block3;
                }
                default: {
                    this.addObserver(type, (Observer)obj);
                }
            }
        }
    }

    private void addObserver(String[] names, Observer obj) {
        if (names == null || names.length == 0) {
            return;
        }
        if (this.unknownMap == null) {
            this.unknownMap = new HashMap<String, List<Observer>>();
        }
        block0: for (String name : names) {
            List<Observer> list = this.unknownMap.get(name);
            if (list == null) {
                list = new ArrayList<Observer>();
                this.unknownMap.put(name, list);
            }
            for (Observer o : list) {
                if (o.getClass() != obj.getClass()) continue;
                break block0;
            }
            list.add(obj);
        }
    }

    private Object getTarget(Object beanInstance) {
        if (!AopUtils.isAopProxy(beanInstance)) {
            return beanInstance;
        }
        if (AopUtils.isCglibProxy(beanInstance)) {
            try {
                Field h = beanInstance.getClass().getDeclaredField("CGLIB$CALLBACK_0");
                ReflectionUtils.makeAccessible(h);
                Object dynamicAdvisedInterceptor = h.get(beanInstance);
                Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
                ReflectionUtils.makeAccessible(advised);
                Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
                return target;
            }
            catch (NoSuchFieldException e) {
                this.logger.error(e.getMessage(), e);
            }
            catch (IllegalAccessException e) {
                this.logger.error(e.getMessage(), e);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return beanInstance;
    }
}

