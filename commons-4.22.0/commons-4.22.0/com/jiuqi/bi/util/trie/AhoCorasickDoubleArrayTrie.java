/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.trie;

import com.jiuqi.bi.util.trie.State;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

public class AhoCorasickDoubleArrayTrie<V>
implements Serializable {
    private static final long serialVersionUID = -1L;
    protected int[] check;
    protected int[] base;
    protected int[] fail;
    protected int[][] output;
    protected V[] v;
    protected int[] l;
    protected int size;

    public List<Hit<V>> parseText(String text) {
        int position = 1;
        int currentState = 0;
        LinkedList<Hit<V>> collectedEmits = new LinkedList<Hit<V>>();
        for (int i = 0; i < text.length(); ++i) {
            currentState = this.getState(currentState, text.charAt(i));
            this.storeEmits(position, currentState, collectedEmits);
            ++position;
        }
        return collectedEmits;
    }

    public void parseText(String text, IHit<V> processor) {
        int position = 1;
        int currentState = 0;
        for (int i = 0; i < text.length(); ++i) {
            int[] hitArray = this.output[currentState = this.getState(currentState, text.charAt(i))];
            if (hitArray != null) {
                for (int hit : hitArray) {
                    processor.hit(position - this.l[hit], position, this.v[hit]);
                }
            }
            ++position;
        }
    }

    public void parseText(String text, IHitCancellable<V> processor) {
        int currentState = 0;
        for (int i = 0; i < text.length(); ++i) {
            int position = i + 1;
            int[] hitArray = this.output[currentState = this.getState(currentState, text.charAt(i))];
            if (hitArray == null) continue;
            for (int hit : hitArray) {
                boolean proceed = processor.hit(position - this.l[hit], position, this.v[hit]);
                if (proceed) continue;
                return;
            }
        }
    }

    public void parseText(char[] text, IHit<V> processor) {
        int position = 1;
        int currentState = 0;
        for (char c : text) {
            int[] hitArray = this.output[currentState = this.getState(currentState, c)];
            if (hitArray != null) {
                for (int hit : hitArray) {
                    processor.hit(position - this.l[hit], position, this.v[hit]);
                }
            }
            ++position;
        }
    }

    public void parseText(char[] text, IHitFull<V> processor) {
        int position = 1;
        int currentState = 0;
        for (char c : text) {
            int[] hitArray = this.output[currentState = this.getState(currentState, c)];
            if (hitArray != null) {
                for (int hit : hitArray) {
                    processor.hit(position - this.l[hit], position, this.v[hit], hit);
                }
            }
            ++position;
        }
    }

    public void save(ObjectOutputStream out) throws IOException {
        out.writeObject(this.base);
        out.writeObject(this.check);
        out.writeObject(this.fail);
        out.writeObject(this.output);
        out.writeObject(this.l);
        out.writeObject(this.v);
    }

    public void load(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.base = (int[])in.readObject();
        this.check = (int[])in.readObject();
        this.fail = (int[])in.readObject();
        this.output = (int[][])in.readObject();
        this.l = (int[])in.readObject();
        this.v = (Object[])in.readObject();
    }

    public V get(String key) {
        int index = this.exactMatchSearch(key);
        if (index >= 0) {
            return this.v[index];
        }
        return null;
    }

    public V get(int index) {
        return this.v[index];
    }

    private int getState(int currentState, char character) {
        int newCurrentState = this.transitionWithRoot(currentState, character);
        while (newCurrentState == -1) {
            currentState = this.fail[currentState];
            newCurrentState = this.transitionWithRoot(currentState, character);
        }
        return newCurrentState;
    }

    private void storeEmits(int position, int currentState, List<Hit<V>> collectedEmits) {
        int[] hitArray = this.output[currentState];
        if (hitArray != null) {
            for (int hit : hitArray) {
                collectedEmits.add(new Hit<V>(position - this.l[hit], position, this.v[hit]));
            }
        }
    }

    protected int transition(int current, char c) {
        int b = current;
        int p = b + c + 1;
        if (b != this.check[p]) {
            return -1;
        }
        b = this.base[p];
        p = b;
        return p;
    }

    protected int transitionWithRoot(int nodePos, char c) {
        int b = this.base[nodePos];
        int p = b + c + 1;
        if (b != this.check[p]) {
            if (nodePos == 0) {
                return 0;
            }
            return -1;
        }
        return p;
    }

    public void build(Map<String, V> map) {
        new Builder().build(map);
    }

    public int exactMatchSearch(String key) {
        return this.exactMatchSearch(key, 0, 0, 0);
    }

    private int exactMatchSearch(String key, int pos, int len, int nodePos) {
        int p;
        if (len <= 0) {
            len = key.length();
        }
        if (nodePos <= 0) {
            nodePos = 0;
        }
        int result = -1;
        char[] keyChars = key.toCharArray();
        int b = this.base[nodePos];
        for (int i = pos; i < len; ++i) {
            p = b + keyChars[i] + 1;
            if (b != this.check[p]) {
                return result;
            }
            b = this.base[p];
        }
        p = b;
        int n = this.base[p];
        if (b == this.check[p] && n < 0) {
            result = -n - 1;
        }
        return result;
    }

    public int size() {
        return this.v.length;
    }

    private class Builder {
        private State rootState = new State();
        private boolean[] used;
        private int allocSize;
        private int progress;
        private int nextCheckPos;
        private int keySize;

        private Builder() {
        }

        public void build(Map<String, V> map) {
            AhoCorasickDoubleArrayTrie.this.v = map.values().toArray();
            AhoCorasickDoubleArrayTrie.this.l = new int[AhoCorasickDoubleArrayTrie.this.v.length];
            Set<String> keySet = map.keySet();
            this.addAllKeyword(keySet);
            this.buildDoubleArrayTrie(keySet.size());
            this.used = null;
            this.constructFailureStates();
            this.rootState = null;
            this.loseWeight();
        }

        private int fetch(State parent, List<Map.Entry<Integer, State>> siblings) {
            if (parent.isAcceptable()) {
                State fakeNode = new State(-(parent.getDepth() + 1));
                fakeNode.addEmit(parent.getLargestValueId());
                siblings.add(new AbstractMap.SimpleEntry<Integer, State>(0, fakeNode));
            }
            for (Map.Entry<Character, State> entry : parent.getSuccess().entrySet()) {
                siblings.add(new AbstractMap.SimpleEntry<Integer, State>(entry.getKey().charValue() + '\u0001', entry.getValue()));
            }
            return siblings.size();
        }

        private void addKeyword(String keyword, int index) {
            State currentState = this.rootState;
            char[] cArray = keyword.toCharArray();
            int n = cArray.length;
            for (int i = 0; i < n; ++i) {
                Character character = Character.valueOf(cArray[i]);
                currentState = currentState.addState(character);
            }
            currentState.addEmit(index);
            AhoCorasickDoubleArrayTrie.this.l[index] = keyword.length();
        }

        private void addAllKeyword(Collection<String> keywordSet) {
            int i = 0;
            for (String keyword : keywordSet) {
                this.addKeyword(keyword, i++);
            }
        }

        private void constructFailureStates() {
            AhoCorasickDoubleArrayTrie.this.fail = new int[AhoCorasickDoubleArrayTrie.this.size + 1];
            AhoCorasickDoubleArrayTrie.this.fail[1] = AhoCorasickDoubleArrayTrie.this.base[0];
            AhoCorasickDoubleArrayTrie.this.output = new int[AhoCorasickDoubleArrayTrie.this.size + 1][];
            LinkedBlockingDeque<State> queue = new LinkedBlockingDeque<State>();
            for (State depthOneState : this.rootState.getStates()) {
                depthOneState.setFailure(this.rootState, AhoCorasickDoubleArrayTrie.this.fail);
                queue.add(depthOneState);
                this.constructOutput(depthOneState);
            }
            while (!queue.isEmpty()) {
                State currentState = (State)queue.remove();
                for (Character transition : currentState.getTransitions()) {
                    State targetState = currentState.nextState(transition);
                    queue.add(targetState);
                    State traceFailureState = currentState.failure();
                    while (traceFailureState.nextState(transition) == null) {
                        traceFailureState = traceFailureState.failure();
                    }
                    State newFailureState = traceFailureState.nextState(transition);
                    targetState.setFailure(newFailureState, AhoCorasickDoubleArrayTrie.this.fail);
                    targetState.addEmit(newFailureState.emit());
                    this.constructOutput(targetState);
                }
            }
        }

        private void constructOutput(State targetState) {
            Collection<Integer> emit = targetState.emit();
            if (emit == null || emit.size() == 0) {
                return;
            }
            int[] output = new int[emit.size()];
            Iterator<Integer> it = emit.iterator();
            for (int i = 0; i < output.length; ++i) {
                output[i] = it.next();
            }
            AhoCorasickDoubleArrayTrie.this.output[targetState.getIndex()] = output;
        }

        private void buildDoubleArrayTrie(int keySize) {
            this.progress = 0;
            this.keySize = keySize;
            this.resize(0x200000);
            AhoCorasickDoubleArrayTrie.this.base[0] = 1;
            this.nextCheckPos = 0;
            State root_node = this.rootState;
            ArrayList<Map.Entry<Integer, State>> siblings = new ArrayList<Map.Entry<Integer, State>>(root_node.getSuccess().entrySet().size());
            this.fetch(root_node, siblings);
            this.insert(siblings);
        }

        private int resize(int newSize) {
            int[] base2 = new int[newSize];
            int[] check2 = new int[newSize];
            boolean[] used2 = new boolean[newSize];
            if (this.allocSize > 0) {
                System.arraycopy(AhoCorasickDoubleArrayTrie.this.base, 0, base2, 0, this.allocSize);
                System.arraycopy(AhoCorasickDoubleArrayTrie.this.check, 0, check2, 0, this.allocSize);
                System.arraycopy(this.used, 0, used2, 0, this.allocSize);
            }
            AhoCorasickDoubleArrayTrie.this.base = base2;
            AhoCorasickDoubleArrayTrie.this.check = check2;
            this.used = used2;
            this.allocSize = newSize;
            return this.allocSize;
        }

        private int insert(List<Map.Entry<Integer, State>> siblings) {
            int begin = 0;
            int pos = Math.max(siblings.get(0).getKey() + 1, this.nextCheckPos) - 1;
            int nonzero_num = 0;
            boolean first = false;
            if (this.allocSize <= pos) {
                this.resize(pos + 1);
            }
            block0: while (true) {
                if (this.allocSize <= ++pos) {
                    this.resize(pos + 1);
                }
                if (AhoCorasickDoubleArrayTrie.this.check[pos] != 0) {
                    ++nonzero_num;
                    continue;
                }
                if (!first) {
                    this.nextCheckPos = pos;
                    first = true;
                }
                if (this.allocSize <= (begin = pos - siblings.get(0).getKey()) + siblings.get(siblings.size() - 1).getKey()) {
                    double l = 1.05 > 1.0 * (double)this.keySize / (double)(this.progress + 1) ? 1.05 : 1.0 * (double)this.keySize / (double)(this.progress + 1);
                    this.resize((int)((double)this.allocSize * l));
                }
                if (this.used[begin]) continue;
                for (int i = 1; i < siblings.size(); ++i) {
                    if (AhoCorasickDoubleArrayTrie.this.check[begin + siblings.get(i).getKey()] == 0) continue;
                    continue block0;
                }
                break;
            }
            if (1.0 * (double)nonzero_num / (double)(pos - this.nextCheckPos + 1) >= 0.95) {
                this.nextCheckPos = pos;
            }
            this.used[begin] = true;
            AhoCorasickDoubleArrayTrie.this.size = AhoCorasickDoubleArrayTrie.this.size > begin + siblings.get(siblings.size() - 1).getKey() + 1 ? AhoCorasickDoubleArrayTrie.this.size : begin + siblings.get(siblings.size() - 1).getKey() + 1;
            for (Map.Entry<Integer, State> sibling : siblings) {
                AhoCorasickDoubleArrayTrie.this.check[begin + sibling.getKey().intValue()] = begin;
            }
            for (Map.Entry<Integer, State> sibling : siblings) {
                ArrayList<Map.Entry<Integer, State>> new_siblings = new ArrayList<Map.Entry<Integer, State>>(sibling.getValue().getSuccess().entrySet().size() + 1);
                if (this.fetch(sibling.getValue(), new_siblings) == 0) {
                    AhoCorasickDoubleArrayTrie.this.base[begin + sibling.getKey().intValue()] = -sibling.getValue().getLargestValueId().intValue() - 1;
                    ++this.progress;
                } else {
                    int h;
                    AhoCorasickDoubleArrayTrie.this.base[begin + sibling.getKey().intValue()] = h = this.insert(new_siblings);
                }
                sibling.getValue().setIndex(begin + sibling.getKey());
            }
            return begin;
        }

        private void loseWeight() {
            int[] nbase = new int[AhoCorasickDoubleArrayTrie.this.size + 65535];
            System.arraycopy(AhoCorasickDoubleArrayTrie.this.base, 0, nbase, 0, AhoCorasickDoubleArrayTrie.this.size);
            AhoCorasickDoubleArrayTrie.this.base = nbase;
            int[] ncheck = new int[AhoCorasickDoubleArrayTrie.this.size + 65535];
            System.arraycopy(AhoCorasickDoubleArrayTrie.this.check, 0, ncheck, 0, AhoCorasickDoubleArrayTrie.this.size);
            AhoCorasickDoubleArrayTrie.this.check = ncheck;
        }
    }

    public class Hit<V> {
        public final int begin;
        public final int end;
        public final V value;

        public Hit(int begin, int end, V value) {
            this.begin = begin;
            this.end = end;
            this.value = value;
        }

        public String toString() {
            return String.format("[%d:%d]=%s", this.begin, this.end, this.value);
        }
    }

    public static interface IHitCancellable<V> {
        public boolean hit(int var1, int var2, V var3);
    }

    public static interface IHitFull<V> {
        public void hit(int var1, int var2, V var3, int var4);
    }

    public static interface IHit<V> {
        public void hit(int var1, int var2, V var3);
    }
}

