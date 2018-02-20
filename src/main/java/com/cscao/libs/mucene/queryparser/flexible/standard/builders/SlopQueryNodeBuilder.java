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
import com.cscao.libs.mucene.queryparser.flexible.core.nodes.QueryNode;
import com.cscao.libs.mucene.queryparser.flexible.core.nodes.SlopQueryNode;
import com.cscao.libs.mucene.search.MultiPhraseQuery;
import com.cscao.libs.mucene.search.PhraseQuery;
import com.cscao.libs.mucene.search.Query;

/**
 * This builder basically reads the {@link Query} object set on the
 * {@link SlopQueryNode} child using
 * {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} and applies the slop value
 * defined in the {@link SlopQueryNode}.
 */
public class SlopQueryNodeBuilder implements StandardQueryBuilder {

  public SlopQueryNodeBuilder() {
    // empty constructor
  }

  @Override
  public Query build(QueryNode queryNode) throws QueryNodeException {
    SlopQueryNode phraseSlopNode = (SlopQueryNode) queryNode;

    Query query = (Query) phraseSlopNode.getChild().getTag(
        QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);

    if (query instanceof PhraseQuery) {
      PhraseQuery.Builder builder = new PhraseQuery.Builder();
      builder.setSlop(phraseSlopNode.getValue());
      PhraseQuery pq = (PhraseQuery) query;
      com.cscao.libs.mucene.index.Term[] terms = pq.getTerms();
      int[] positions = pq.getPositions();
      for (int i = 0; i < terms.length; ++i) {
        builder.add(terms[i], positions[i]);
      }
      query = builder.build();

    } else {
      MultiPhraseQuery mpq = (MultiPhraseQuery)query;
      
      int slop = phraseSlopNode.getValue();
      
      if (slop != mpq.getSlop()) {
        query = new MultiPhraseQuery.Builder(mpq).setSlop(slop).build();
      }
    }

    return query;

  }

}
