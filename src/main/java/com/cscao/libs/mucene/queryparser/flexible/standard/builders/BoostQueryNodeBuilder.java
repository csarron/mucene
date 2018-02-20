/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cscao.libs.mucene.queryparser.flexible.standard.builders;

import com.cscao.libs.mucene.queryparser.flexible.core.QueryNodeException;
import com.cscao.libs.mucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import com.cscao.libs.mucene.queryparser.flexible.core.nodes.BoostQueryNode;
import com.cscao.libs.mucene.queryparser.flexible.core.nodes.QueryNode;
import com.cscao.libs.mucene.search.BoostQuery;
import com.cscao.libs.mucene.search.Query;

/**
 * This builder basically reads the {@link Query} object set on the
 * {@link BoostQueryNode} child using
 * {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} and applies the boost value
 * defined in the {@link BoostQueryNode}.
 */
public class BoostQueryNodeBuilder implements StandardQueryBuilder {

  public BoostQueryNodeBuilder() {
    // empty constructor
  }

  @Override
  public Query build(QueryNode queryNode) throws QueryNodeException {
    BoostQueryNode boostNode = (BoostQueryNode) queryNode;
    QueryNode child = boostNode.getChild();

    if (child == null) {
      return null;
    }

    Query query = (Query) child
        .getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);

    return new BoostQuery(query, boostNode.getValue());

  }

}
