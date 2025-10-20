/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.contexts;

import com.itextpdf.commons.actions.NamespaceConstant;
import com.itextpdf.commons.actions.contexts.GenericContext;
import com.itextpdf.commons.actions.contexts.IContext;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class ContextManager {
    private static final ContextManager INSTANCE;
    private final SortedMap<String, IContext> contextMappings = new TreeMap<String, IContext>(new LengthComparator());

    ContextManager() {
    }

    public static ContextManager getInstance() {
        return INSTANCE;
    }

    public IContext getContext(Class<?> clazz) {
        return clazz == null ? null : this.getContext(clazz.getName());
    }

    public IContext getContext(String className) {
        return this.getNamespaceMapping(this.getRecognisedNamespace(className));
    }

    String getRecognisedNamespace(String className) {
        if (className != null) {
            String normalizedClassName = ContextManager.normalize(className);
            for (String namespace : this.contextMappings.keySet()) {
                if (!normalizedClassName.startsWith(namespace)) continue;
                return namespace;
            }
        }
        return null;
    }

    void unregisterContext(Collection<String> namespaces) {
        for (String namespace : namespaces) {
            this.contextMappings.remove(ContextManager.normalize(namespace));
        }
    }

    private IContext getNamespaceMapping(String namespace) {
        if (namespace != null) {
            return (IContext)this.contextMappings.get(namespace);
        }
        return null;
    }

    void registerGenericContext(Collection<String> namespaces, Collection<String> products) {
        GenericContext context = new GenericContext(products);
        for (String namespace : namespaces) {
            this.contextMappings.put(ContextManager.normalize(namespace), context);
        }
    }

    private static String normalize(String namespace) {
        return namespace.toLowerCase();
    }

    static {
        ContextManager local = new ContextManager();
        local.registerGenericContext(NamespaceConstant.ITEXT_CORE_NAMESPACES, Collections.singleton("itext-core"));
        local.registerGenericContext(Collections.singleton("com.itextpdf.signatures"), Collections.singleton("itext-core-sign"));
        local.registerGenericContext(Collections.singletonList("com.itextpdf.html2pdf"), Collections.singleton("pdfHtml"));
        local.registerGenericContext(Collections.singletonList("com.itextpdf.pdfcleanup"), Collections.singleton("pdfSweep"));
        local.registerGenericContext(Collections.singletonList("com.itextpdf.pdfocr.tesseract4"), Collections.singleton("pdfOcr-tesseract4"));
        INSTANCE = local;
    }

    private static class LengthComparator
    implements Comparator<String> {
        private LengthComparator() {
        }

        @Override
        public int compare(String o1, String o2) {
            int lengthComparison = Integer.compare(o2.length(), o1.length());
            if (0 == lengthComparison) {
                return o1.compareTo(o2);
            }
            return lengthComparison;
        }
    }
}

