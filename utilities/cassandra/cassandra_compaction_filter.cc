// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree. An additional grant
// of patent rights can be found in the PATENTS file in the same directory.

#ifndef ROCKSDB_LITE

#include <string>

#include "rocksdb/slice.h"
#include "utilities/cassandra/cassandra_compaction_filter.h"
#include "utilities/cassandra/format.h"


namespace rocksdb {
namespace cassandra {

const char* CassandraCompactionFilter::Name() const {
  return "CassandraCompactionFilter";
}

CompactionFilter::Decision CassandraCompactionFilter::FilterV2(
  int level,
  const Slice& key,
  ValueType value_type,
  const Slice& existing_value,
  std::string* new_value,
  std::string* skip_until) const {

  bool value_changed = false;
  RowValue row_value = RowValue::Deserialize(existing_value.data(), existing_value.size());
  RowValue compacted = row_value.GC(&value_changed);

  if(compacted.Empty()) {
    return Decision::kRemove;
  }

  if (value_changed) {
    compacted.Serialize(new_value);
    return Decision::kChangeValue;        
  }

  return Decision::kKeep;
}


}  // namespace cassandra 
}  // namespace rocksdb
#endif  // !ROCKSDB_LITE
