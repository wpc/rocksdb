// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree. An additional grant
// of patent rights can be found in the PATENTS file in the same directory.

#ifndef ROCKSDB_LITE

#pragma once

#include <string>

#include "rocksdb/compaction_filter.h"
#include "rocksdb/slice.h"

namespace rocksdb {
namespace cassandra {

class CassandraCompactionFilter : public CompactionFilter {
public:
  const char* Name() const override;
  virtual Decision FilterV2(int level,
                            const Slice& key,
                            ValueType value_type,
                            const Slice& existing_value,
                            std::string* new_value,
                            std::string* skip_until) const override;
};
}  // namespace cassandra
}  // namespace rocksdb
#endif  // !ROCKSDB_LITE
