/*
 * Copyright (c) 1998, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.dtdparser;

import java.util.Arrays;
import java.util.Enumeration;


// This could be replaced by Collections class unless we want
// to be able to run on JDK 1.1


/**
 * This class implements a special purpose hashtable.  It works like a
 * normal {@code java.util.Hashtable} except that: <OL>
 *
 * <LI> Keys to "get" are strings which are known to be interned,
 * so that "==" is used instead of "String.equals".  (Interning
 * could be document-relative instead of global.)
 *
 * <LI> It's not synchronized, since it's to be used only by
 * one thread at a time.
 *
 * <LI> The keys () enumerator allocates no memory, with live
 * updates to the data disallowed.
 *
 * <LI> It's got fewer bells and whistles:  fixed threshold and
 * load factor, no JDK 1.2 collection support, only keys can be
 * enumerated, things can't be removed, simpler inheritance; more.
 * </OL>
 *
 * <P> The overall result is that it's less expensive to use these in
 * performance-critical locations, in terms both of CPU and memory,
 * than {@code java.util.Hashtable} instances.  In this package
 * it makes a significant difference when normalizing attributes,
 * which is done for each start-element construct.
 *
 * @version $Revision: 1.2 $
 */
final class SimpleHashtable<K, V> implements Enumeration<K> {
    // entries ...
    private Entry<K, V>[] table;

    // currently enumerated key
    private Entry<K, V> current = null;
    private int currentBucket = 0;

    private int count;
    private int threshold;

    private static final float loadFactor = 0.75f;


    /**
     * Constructs a new, empty hashtable with the specified initial
     * capacity.
     *
     * @param initialCapacity the initial capacity of the hashtable.
     */
    @SuppressWarnings({"rawtypes","unchecked"})
    public SimpleHashtable(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        if (initialCapacity == 0)
            initialCapacity = 1;
        table = new Entry[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);
    }

    /**
     * Constructs a new, empty hashtable with a default capacity.
     */
    public SimpleHashtable() {
        this(11);
    }

    /**
     */
    public void clear() {
        count = 0;
        currentBucket = 0;
        current = null;
        Arrays.fill(table, null);
    }

    /**
     * Returns the number of keys in this hashtable.
     *
     * @return the number of keys in this hashtable.
     */
    public int size() {
        return count;
    }

    /**
     * Returns an enumeration of the keys in this hashtable.
     *
     * @return an enumeration of the keys in this hashtable.
     * @see Enumeration
     */
    public Enumeration<K> keys() {
        currentBucket = 0;
        current = null;
        return this;
    }

    /**
     * Used to view this as an enumeration; returns true if there
     * are more keys to be enumerated.
     */
    @Override
    public boolean hasMoreElements() {
        if (current != null)
            return true;
        while (currentBucket < table.length) {
            current = table[currentBucket++];
            if (current != null)
                return true;
        }
        return false;
    }

    /**
     * Used to view this as an enumeration; returns the next key
     * in the enumeration.
     */
    @Override
    public K nextElement() {
        K retval;

        if (current == null)
            throw new IllegalStateException();
        retval = current.key;
        current = current.next;
        return retval;
    }


    /**
     * Returns the value to which the specified key is mapped in this hashtable.
     */
    public V get(String key) {
        Entry<K, V>[] tab = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry<K, V> e = tab[index]; e != null; e = e.next) {
            if ((e.hash == hash) && (e.key == key))
                return e.value;
        }
        return null;
    }

    /**
     * Returns the value to which the specified key is mapped in this
     * hashtable ... the key isn't necessarily interned, though.
     */
    public V getNonInterned(String key) {
        Entry<K, V>[] tab = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry<K, V> e = tab[index]; e != null; e = e.next) {
            if ((e.hash == hash) && e.key.equals(key))
                return e.value;
        }
        return null;
    }

    /**
     * Increases the capacity of and internally reorganizes this
     * hashtable, in order to accommodate and access its entries more
     * efficiently.  This method is called automatically when the
     * number of keys in the hashtable exceeds this hashtable capacity
     * and load factor.
     */
    private void rehash() {
        int oldCapacity = table.length;
        Entry<K, V>[] oldMap = table;

        int newCapacity = oldCapacity * 2 + 1;
        @SuppressWarnings({"unchecked", "rawtypes"})
        Entry<K, V>[] newMap = new Entry[newCapacity];

        threshold = (int) (newCapacity * loadFactor);
        table = newMap;

        /*
        System.out.println("rehash old=" + oldCapacity
            + ", new=" + newCapacity
            + ", thresh=" + threshold
            + ", count=" + count);
        */

        for (int i = oldCapacity; i-- > 0;) {
            for (Entry<K, V> old = oldMap[i]; old != null;) {
                Entry<K, V> e = old;
                old = old.next;

                int index = (e.hash & 0x7FFFFFFF) % newCapacity;
                e.next = newMap[index];
                newMap[index] = e;
            }
        }
    }

    /**
     * Maps the specified {@code key} to the specified
     * {@code value} in this hashtable. Neither the key nor the
     * value can be {@code null}.
     *
     * <P>The value can be retrieved by calling the {@code get} method
     * with a key that is equal to the original key.
     */
    public V put(K key, V value) {
        // Make sure the value is not null
        if (value == null) {
            throw new NullPointerException();
        }

        // Makes sure the key is not already in the hashtable.
        Entry<K, V>[] tab = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry<K, V> e = tab[index]; e != null; e = e.next) {
            // if ((e.hash == hash) && e.key.equals(key)) {
            if ((e.hash == hash) && (e.key == key)) {
                V old = e.value;
                e.value = value;
                return old;
            }
        }

        if (count >= threshold) {
            // Rehash the table if the threshold is exceeded
            rehash();

            tab = table;
            index = (hash & 0x7FFFFFFF) % tab.length;
        }

        // Creates the new entry.
        Entry<K, V> e = new Entry<>(hash, key, value, tab[index]);
        tab[index] = e;
        count++;
        return null;
    }


    /**
     * Hashtable collision list.
     */
    private static class Entry<K, V> {
        int hash;
        K key;
        V value;
        Entry<K, V> next;

        protected Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
