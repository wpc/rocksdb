// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
//  This source code is licensed under both the GPLv2 (found in the
//  COPYING file in the root directory) and Apache 2.0 License
//  (found in the LICENSE.Apache file in the root directory).

package org.rocksdb;

import java.nio.ByteBuffer;

/**
 * <p>An iterator that yields a sequence of key/value pairs from a source.
 * Multiple implementations are provided by this library.
 * In particular, iterators are provided
 * to access the contents of a Table or a DB.</p>
 *
 * <p>Multiple threads can invoke const methods on an RocksIterator without
 * external synchronization, but if any of the threads may call a
 * non-const method, all threads accessing the same RocksIterator must use
 * external synchronization.</p>
 *
 * @see org.rocksdb.RocksObject
 */
public class RocksIterator extends AbstractRocksIterator<RocksDB> {
  protected RocksIterator(RocksDB rocksDB, long nativeHandle) {
    super(rocksDB, nativeHandle);
  }

  /**
   * <p>Return the key for the current entry.  The underlying storage for
   * the returned slice is valid only until the next modification of
   * the iterator.</p>
   *
   * <p>REQUIRES: {@link #isValid()}</p>
   *
   * @return key for the current entry.
   */
  public byte[] key() {
    assert(isOwningHandle());
    return key0(nativeHandle_);
  }

  /**
   * <p>Return the value for the current entry.  The underlying storage for
   * the returned slice is valid only until the next modification of
   * the iterator.</p>
   *
   * <p>REQUIRES: !AtEnd() &amp;&amp; !AtStart()</p>
   * @return value for the current entry.
   */
  public byte[] value() {
    assert(isOwningHandle());
    return value0(nativeHandle_);
  }

  /**
    * <p>Copy value into passined direct byte buffer. RocksDBException
    * will be thrown if value is invalid or do not enough remaining
    * size </p>
    *
    * <p>REQUIRES: !AtEnd() &amp;&amp; !AtStart()</p>
    *
    * @param buffer the out-value to receive the retrieved value.
    *     It is using position and limit. Limit is set according to value size.
    *     Supports direct buffer only.
    */
  public void value(ByteBuffer buffer) {
    assert(isOwningHandle() && buffer.isDirect());
    int valueSize = valueDirect0(nativeHandle_, buffer, buffer.position(), buffer.remaining());
    buffer.limit(buffer.position() + valueSize);
  }

  /**
    * <p>Size of current value in bytes</p>
    *
    * <p>REQUIRES: !AtEnd() &amp;&amp; !AtStart()</p>
    *
    * @return size of current entry value.
    */

  public int valueSize() {
    assert(isOwningHandle());
    return valueSize0(nativeHandle_);
  }


  @Override protected final native void disposeInternal(final long handle);
  @Override final native boolean isValid0(long handle);
  @Override final native void seekToFirst0(long handle);
  @Override final native void seekToLast0(long handle);
  @Override final native void next0(long handle);
  @Override final native void prev0(long handle);
  @Override final native void seek0(long handle, byte[] target, int targetLen);
  @Override final native void seekForPrev0(long handle, byte[] target, int targetLen);
  @Override final native void status0(long handle) throws RocksDBException;

  private native byte[] key0(long handle);
  private native byte[] value0(long handle);
  private native int valueDirect0(long handle, ByteBuffer buffer, int bufferOffset, int bufferLen);
  private native int valueSize0(long handle);
}
