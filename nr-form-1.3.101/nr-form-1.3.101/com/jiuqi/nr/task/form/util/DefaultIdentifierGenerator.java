/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.nr.task.form.util.IdentifierGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultIdentifierGenerator
extends IdentifierGenerator<String> {
    private final Map<String, IdentifierGenerator.IdParts> identifierMap = new HashMap<String, IdentifierGenerator.IdParts>();
    private static final Pattern SUFFIX_PATTERN = Pattern.compile("^(.*?)(_\\d+)?$");

    @Override
    IdentifierGenerator.IdParts getIdParts(String identifier) {
        Matcher matcher = SUFFIX_PATTERN.matcher(identifier);
        if (!matcher.matches()) {
            return new IdentifierGenerator.IdParts(identifier, 0);
        }
        String base = matcher.group(1);
        String suffix = matcher.group(2);
        if (suffix == null || suffix.isEmpty()) {
            return new IdentifierGenerator.IdParts(base, 0);
        }
        try {
            int num = Integer.parseInt(suffix.substring(1));
            return new IdentifierGenerator.IdParts(base, num);
        }
        catch (NumberFormatException e) {
            return new IdentifierGenerator.IdParts(identifier, 0);
        }
    }

    @Override
    boolean exist(IdentifierGenerator.IdParts idParts, String identifier) {
        if (this.identifierMap.containsKey(idParts.getBase())) {
            return true;
        }
        this.identifierMap.put(idParts.getBase(), idParts);
        return false;
    }

    @Override
    String nextIdentifier(IdentifierGenerator.IdParts idParts, String identifier, Function<IdentifierGenerator.IdParts, String> format) {
        IdentifierGenerator.IdParts oldIdParts = this.identifierMap.get(idParts.getBase());
        int newCount = Math.max(oldIdParts.getSuffixNumber(), idParts.getSuffixNumber()) + 1;
        oldIdParts.setSuffixNumber(newCount);
        return format.apply(idParts);
    }

    @Override
    Function<IdentifierGenerator.IdParts, String> formatSupplier() {
        return idParts -> String.format("%s_%d", idParts.getBase(), idParts.getSuffixNumber());
    }
}

