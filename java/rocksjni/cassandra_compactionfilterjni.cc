// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree. An additional grant
// of patent rights can be found in the PATENTS file in the same directory.

#include <jni.h>

#include "include/org_rocksdb_CassandraCompactionFilter.h"
#include "utilities/cassandra/cassandra_compaction_filter.h"

/*
 * Class:     org_rocksdb_CassandraCompactionFilter
 * Method:    createNewCassandraCompactionFilter0
 * Signature: ()J
 */
jlong Java_org_rocksdb_CassandraCompactionFilter_createNewCassandraCompactionFilter0(
    JNIEnv* env, jclass jcls) {
  auto* compaction_filter =
      new rocksdb::cassandra::CassandraCompactionFilter();
  // set the native handle to our native compaction filter
  return reinterpret_cast<jlong>(compaction_filter);
}
