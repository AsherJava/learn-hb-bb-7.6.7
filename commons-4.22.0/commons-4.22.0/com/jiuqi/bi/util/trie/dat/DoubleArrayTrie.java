/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.trie.dat;

import com.jiuqi.bi.util.trie.dat.ByteArray;
import com.jiuqi.bi.util.trie.dat.ByteUtil;
import com.jiuqi.bi.util.trie.dat.IOUtil;
import com.jiuqi.bi.util.trie.dat.ITrie;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DoubleArrayTrie<V>
implements Serializable,
ITrie<V> {
    private static final long serialVersionUID = 1865946440412547225L;
    private static final int BUF_SIZE = 16384;
    private static final int UNIT_SIZE = 8;
    protected int[] check = null;
    protected int[] base = null;
    private BitSet used = new BitSet();
    protected int size = 0;
    private int allocSize = 0;
    private List<String> key;
    private int keySize;
    private int[] length;
    private int[] value;
    protected V[] v;
    private int progress;
    private int nextCheckPos;
    int error_ = 0;

    private int resize(int newSize) {
        int[] base2 = new int[newSize];
        int[] check2 = new int[newSize];
        if (this.allocSize > 0) {
            System.arraycopy(this.base, 0, base2, 0, this.allocSize);
            System.arraycopy(this.check, 0, check2, 0, this.allocSize);
        }
        this.base = base2;
        this.check = check2;
        this.allocSize = newSize;
        return this.allocSize;
    }

    private int fetch(Node parent, List<Node> siblings) {
        if (this.error_ < 0) {
            return 0;
        }
        int prev = 0;
        for (int i = parent.left; i < parent.right; ++i) {
            if ((this.length != null ? this.length[i] : this.key.get(i).length()) < parent.depth) continue;
            String tmp = this.key.get(i);
            int cur = 0;
            if ((this.length != null ? this.length[i] : tmp.length()) != parent.depth) {
                cur = tmp.charAt(parent.depth) + '\u0001';
            }
            if (prev > cur) {
                this.error_ = -3;
                return 0;
            }
            if (cur != prev || siblings.size() == 0) {
                Node tmp_node = new Node();
                tmp_node.depth = parent.depth + 1;
                tmp_node.code = cur;
                tmp_node.left = i;
                if (siblings.size() != 0) {
                    siblings.get((int)(siblings.size() - 1)).right = i;
                }
                siblings.add(tmp_node);
            }
            prev = cur;
        }
        if (siblings.size() != 0) {
            siblings.get((int)(siblings.size() - 1)).right = parent.right;
        }
        return siblings.size();
    }

    private int insert(List<Node> siblings) {
        int i;
        if (this.error_ < 0) {
            return 0;
        }
        int begin = 0;
        int pos = Math.max(siblings.get((int)0).code + 1, this.nextCheckPos) - 1;
        int nonzero_num = 0;
        boolean first = false;
        if (this.allocSize <= pos) {
            this.resize(pos + 1);
        }
        block0: while (true) {
            if (this.allocSize <= ++pos) {
                this.resize(pos + 1);
            }
            if (this.check[pos] != 0) {
                ++nonzero_num;
                continue;
            }
            if (!first) {
                this.nextCheckPos = pos;
                first = true;
            }
            if (this.allocSize <= (begin = pos - siblings.get((int)0).code) + siblings.get((int)(siblings.size() - 1)).code) {
                this.resize(begin + siblings.get((int)(siblings.size() - 1)).code + 65535);
            }
            if (this.used.get(begin)) continue;
            for (i = 1; i < siblings.size(); ++i) {
                if (this.check[begin + siblings.get((int)i).code] == 0) continue;
                continue block0;
            }
            break;
        }
        if (1.0 * (double)nonzero_num / (double)(pos - this.nextCheckPos + 1) >= 0.95) {
            this.nextCheckPos = pos;
        }
        this.used.set(begin);
        this.size = this.size > begin + siblings.get((int)(siblings.size() - 1)).code + 1 ? this.size : begin + siblings.get((int)(siblings.size() - 1)).code + 1;
        for (i = 0; i < siblings.size(); ++i) {
            this.check[begin + siblings.get((int)i).code] = begin;
        }
        for (i = 0; i < siblings.size(); ++i) {
            int h;
            ArrayList<Node> new_siblings = new ArrayList<Node>();
            if (this.fetch(siblings.get(i), new_siblings) == 0) {
                int n = this.base[begin + siblings.get((int)i).code] = this.value != null ? -this.value[siblings.get((int)i).left] - 1 : -siblings.get((int)i).left - 1;
                if (this.value != null && -this.value[siblings.get((int)i).left] - 1 >= 0) {
                    this.error_ = -2;
                    return 0;
                }
                ++this.progress;
                continue;
            }
            this.base[begin + siblings.get((int)i).code] = h = this.insert(new_siblings);
        }
        return begin;
    }

    void clear() {
        this.check = null;
        this.base = null;
        this.used = null;
        this.allocSize = 0;
        this.size = 0;
    }

    public int getUnitSize() {
        return 8;
    }

    public int getSize() {
        return this.size;
    }

    public int getTotalSize() {
        return this.size * 8;
    }

    public int getNonzeroSize() {
        int result = 0;
        for (int i = 0; i < this.check.length; ++i) {
            if (this.check[i] == 0) continue;
            ++result;
        }
        return result;
    }

    public int build(List<String> key, List<V> value) {
        assert (key.size() == value.size()) : "\u952e\u7684\u4e2a\u6570\u4e0e\u503c\u7684\u4e2a\u6570\u4e0d\u4e00\u6837\uff01";
        assert (key.size() > 0) : "\u952e\u503c\u4e2a\u6570\u4e3a0\uff01";
        this.v = value.toArray();
        return this.build(key, null, null, key.size());
    }

    public int build(List<String> key, V[] value) {
        assert (key.size() == value.length) : "\u952e\u7684\u4e2a\u6570\u4e0e\u503c\u7684\u4e2a\u6570\u4e0d\u4e00\u6837\uff01";
        assert (key.size() > 0) : "\u952e\u503c\u4e2a\u6570\u4e3a0\uff01";
        this.v = value;
        return this.build(key, null, null, key.size());
    }

    public int build(Set<Map.Entry<String, V>> entrySet) {
        ArrayList<String> keyList = new ArrayList<String>(entrySet.size());
        ArrayList<V> valueList = new ArrayList<V>(entrySet.size());
        for (Map.Entry<String, V> entry : entrySet) {
            keyList.add(entry.getKey());
            valueList.add(entry.getValue());
        }
        return this.build(keyList, valueList);
    }

    @Override
    public int build(TreeMap<String, V> keyValueMap) {
        assert (keyValueMap != null);
        Set<Map.Entry<String, V>> entrySet = keyValueMap.entrySet();
        return this.build(entrySet);
    }

    public int build(List<String> _key, int[] _length, int[] _value, int _keySize) {
        if (_key == null || _keySize > _key.size()) {
            return 0;
        }
        this.key = _key;
        this.length = _length;
        this.keySize = _keySize;
        this.value = _value;
        this.progress = 0;
        this.resize(0x200000);
        this.base[0] = 1;
        this.nextCheckPos = 0;
        Node root_node = new Node();
        root_node.left = 0;
        root_node.right = this.keySize;
        root_node.depth = 0;
        ArrayList<Node> siblings = new ArrayList<Node>();
        this.fetch(root_node, siblings);
        this.insert(siblings);
        this.used = null;
        this.key = null;
        this.length = null;
        return this.error_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void open(String fileName) throws IOException {
        File file = new File(fileName);
        this.size = (int)file.length() / 8;
        this.check = new int[this.size];
        this.base = new int[this.size];
        try (FilterInputStream is = null;){
            is = new DataInputStream(new BufferedInputStream(IOUtil.newInputStream(fileName), 16384));
            for (int i = 0; i < this.size; ++i) {
                this.base[i] = ((DataInputStream)is).readInt();
                this.check[i] = ((DataInputStream)is).readInt();
            }
        }
    }

    public boolean save(String fileName) {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(IOUtil.newOutputStream(fileName)));){
            out.writeInt(this.size);
            for (int i = 0; i < this.size; ++i) {
                out.writeInt(this.base[i]);
                out.writeInt(this.check[i]);
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean save(DataOutputStream out) {
        try {
            out.writeInt(this.size);
            for (int i = 0; i < this.size; ++i) {
                out.writeInt(this.base[i]);
                out.writeInt(this.check[i]);
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public void save(ObjectOutputStream out) throws IOException {
        out.writeObject(this.base);
        out.writeObject(this.check);
    }

    public boolean load(String path, List<V> value) {
        if (!this.loadBaseAndCheck(path)) {
            return false;
        }
        this.v = value.toArray();
        return true;
    }

    public boolean load(String path, V[] value) {
        if (!this.loadBaseAndCheckByFileChannel(path)) {
            return false;
        }
        this.v = value;
        return true;
    }

    @Override
    public boolean load(ByteArray byteArray, V[] value) {
        if (byteArray == null) {
            return false;
        }
        this.size = byteArray.nextInt();
        this.base = new int[this.size + 65535];
        this.check = new int[this.size + 65535];
        for (int i = 0; i < this.size; ++i) {
            this.base[i] = byteArray.nextInt();
            this.check[i] = byteArray.nextInt();
        }
        this.v = value;
        this.used = null;
        return true;
    }

    public boolean load(String path) {
        return this.loadBaseAndCheckByFileChannel(path);
    }

    private boolean loadBaseAndCheck(String path) {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));){
            this.size = in.readInt();
            this.base = new int[this.size + 65535];
            this.check = new int[this.size + 65535];
            for (int i = 0; i < this.size; ++i) {
                this.base[i] = in.readInt();
                this.check[i] = in.readInt();
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean loadBaseAndCheckByFileChannel(String path) {
        try (FileInputStream fis = new FileInputStream(path);){
            FileChannel channel = fis.getChannel();
            int fileSize = (int)channel.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(fileSize);
            channel.read(byteBuffer);
            byteBuffer.flip();
            byte[] bytes = byteBuffer.array();
            byteBuffer.clear();
            channel.close();
            int index = 0;
            this.size = ByteUtil.bytesHighFirstToInt(bytes, index);
            index += 4;
            this.base = new int[this.size + 65535];
            this.check = new int[this.size + 65535];
            for (int i = 0; i < this.size; ++i) {
                this.base[i] = ByteUtil.bytesHighFirstToInt(bytes, index);
                this.check[i] = ByteUtil.bytesHighFirstToInt(bytes, index += 4);
                index += 4;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean serializeTo(String path) {
        try (ObjectOutputStream out = new ObjectOutputStream(IOUtil.newOutputStream(path));){
            out.writeObject(this);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> DoubleArrayTrie<T> unSerialize(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));){
            DoubleArrayTrie doubleArrayTrie = (DoubleArrayTrie)in.readObject();
            return doubleArrayTrie;
        }
        catch (Exception e) {
            return null;
        }
    }

    public int exactMatchSearch(String key) {
        return this.exactMatchSearch(key, 0, 0, 0);
    }

    public int exactMatchSearch(String key, int pos, int len, int nodePos) {
        int p;
        if (len <= 0) {
            len = key.length();
        }
        if (nodePos <= 0) {
            nodePos = 0;
        }
        int result = -1;
        int b = this.base[nodePos];
        for (int i = pos; i < len; ++i) {
            p = b + key.charAt(i) + 1;
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

    public int exactMatchSearch(char[] keyChars, int pos, int len, int nodePos) {
        int p;
        int result = -1;
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

    public List<Integer> commonPrefixSearch(String key) {
        return this.commonPrefixSearch(key, 0, 0, 0);
    }

    public List<Integer> commonPrefixSearch(String key, int pos, int len, int nodePos) {
        if (len <= 0) {
            len = key.length();
        }
        if (nodePos <= 0) {
            nodePos = 0;
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        char[] keyChars = key.toCharArray();
        int b = this.base[nodePos];
        for (int i = pos; i < len; ++i) {
            int p = b + keyChars[i] + 1;
            if (b != this.check[p]) {
                return result;
            }
            b = this.base[p];
            p = b;
            int n = this.base[p];
            if (b != this.check[p] || n >= 0) continue;
            result.add(-n - 1);
        }
        return result;
    }

    public LinkedList<Map.Entry<String, V>> commonPrefixSearchWithValue(String key) {
        int n;
        int p;
        int len = key.length();
        LinkedList<Map.Entry<String, V>> result = new LinkedList<Map.Entry<String, V>>();
        char[] keyChars = key.toCharArray();
        int b = this.base[0];
        for (int i = 0; i < len; ++i) {
            p = b;
            n = this.base[p];
            if (b == this.check[p] && n < 0) {
                result.add(new AbstractMap.SimpleEntry<String, V>(new String(keyChars, 0, i), this.v[-n - 1]));
            }
            if (b != this.check[p = b + keyChars[i] + 1]) {
                return result;
            }
            b = this.base[p];
        }
        p = b;
        n = this.base[p];
        if (b == this.check[p] && n < 0) {
            result.add(new AbstractMap.SimpleEntry<String, V>(key, this.v[-n - 1]));
        }
        return result;
    }

    public LinkedList<Map.Entry<String, V>> commonPrefixSearchWithValue(char[] keyChars, int begin) {
        int n;
        int p;
        int len = keyChars.length;
        LinkedList<Map.Entry<String, V>> result = new LinkedList<Map.Entry<String, V>>();
        int b = this.base[0];
        for (int i = begin; i < len; ++i) {
            p = b;
            n = this.base[p];
            if (b == this.check[p] && n < 0) {
                result.add(new AbstractMap.SimpleEntry<String, V>(new String(keyChars, begin, i - begin), this.v[-n - 1]));
            }
            if (b != this.check[p = b + keyChars[i] + 1]) {
                return result;
            }
            b = this.base[p];
        }
        p = b;
        n = this.base[p];
        if (b == this.check[p] && n < 0) {
            result.add(new AbstractMap.SimpleEntry<String, V>(new String(keyChars, begin, len - begin), this.v[-n - 1]));
        }
        return result;
    }

    public String toString() {
        return "DoubleArrayTrie{size=" + this.size + ", allocSize=" + this.allocSize + ", key=" + this.key + ", keySize=" + this.keySize + ", progress=" + this.progress + ", nextCheckPos=" + this.nextCheckPos + ", error_=" + this.error_ + '}';
    }

    @Override
    public int size() {
        return this.v.length;
    }

    public int[] getCheck() {
        return this.check;
    }

    public int[] getBase() {
        return this.base;
    }

    public V getValueAt(int index) {
        return this.v[index];
    }

    @Override
    public V get(String key) {
        int index = this.exactMatchSearch(key);
        if (index >= 0) {
            return this.getValueAt(index);
        }
        return null;
    }

    @Override
    public V get(char[] key) {
        int index = this.exactMatchSearch(key, 0, key.length, 0);
        if (index >= 0) {
            return this.getValueAt(index);
        }
        return null;
    }

    @Override
    public V[] getValueArray(V[] a) {
        int size = this.v.length;
        if (a.length < size) {
            a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
        }
        System.arraycopy(this.v, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean containsKey(String key) {
        return this.exactMatchSearch(key) >= 0;
    }

    protected int transition(String path) {
        return this.transition(path.toCharArray());
    }

    protected int transition(char[] path) {
        int p;
        int b = this.base[0];
        for (int i = 0; i < path.length; ++i) {
            p = b + path[i] + 1;
            if (b != this.check[p]) {
                return -1;
            }
            b = this.base[p];
        }
        p = b;
        return p;
    }

    public int transition(String path, int from) {
        int p;
        int b = from;
        for (int i = 0; i < path.length(); ++i) {
            p = b + path.charAt(i) + 1;
            if (b != this.check[p]) {
                return -1;
            }
            b = this.base[p];
        }
        p = b;
        return p;
    }

    public int transition(char c, int from) {
        int b = from;
        int p = b + c + 1;
        if (b != this.check[p]) {
            return -1;
        }
        b = this.base[p];
        return b;
    }

    public V output(int state) {
        if (state < 0) {
            return null;
        }
        int n = this.base[state];
        if (state == this.check[state] && n < 0) {
            return this.v[-n - 1];
        }
        return null;
    }

    public Searcher getSearcher(String text, int offset) {
        return new Searcher(offset, text.toCharArray());
    }

    public Searcher getSearcher(char[] text, int offset) {
        return new Searcher(offset, text);
    }

    protected int transition(int current, char c) {
        int b = this.base[current];
        int p = b + c + 1;
        if (b != this.check[p]) {
            return -1;
        }
        b = this.base[p];
        p = b;
        return p;
    }

    public boolean set(String key, V value) {
        int index = this.exactMatchSearch(key);
        if (index >= 0) {
            this.v[index] = value;
            return true;
        }
        return false;
    }

    public V get(int index) {
        return this.v[index];
    }

    public class Searcher {
        public int begin;
        public int length;
        public int index;
        public V value;
        private char[] charArray;
        private int last;
        private int i;
        private int arrayLength;

        public Searcher(int offset, char[] charArray) {
            this.charArray = charArray;
            this.i = offset;
            this.last = DoubleArrayTrie.this.base[0];
            this.arrayLength = charArray.length;
            this.begin = this.arrayLength == 0 ? -1 : offset;
        }

        public boolean next() {
            int b = this.last;
            while (true) {
                int p;
                if (this.i == this.arrayLength) {
                    ++this.begin;
                    if (this.begin == this.arrayLength) break;
                    this.i = this.begin;
                    b = DoubleArrayTrie.this.base[0];
                }
                if (b != DoubleArrayTrie.this.check[p = b + this.charArray[this.i] + 1]) {
                    this.i = this.begin++;
                    if (this.begin == this.arrayLength) break;
                    b = DoubleArrayTrie.this.base[0];
                } else {
                    b = DoubleArrayTrie.this.base[p];
                    p = b;
                    int n = DoubleArrayTrie.this.base[p];
                    if (b == DoubleArrayTrie.this.check[p] && n < 0) {
                        this.length = this.i - this.begin + 1;
                        this.index = -n - 1;
                        this.value = DoubleArrayTrie.this.v[this.index];
                        this.last = b;
                        ++this.i;
                        return true;
                    }
                }
                ++this.i;
            }
            return false;
        }
    }

    private static class Node {
        int code;
        int depth;
        int left;
        int right;

        private Node() {
        }

        public String toString() {
            return "Node{code=" + this.code + ", depth=" + this.depth + ", left=" + this.left + ", right=" + this.right + '}';
        }
    }
}

