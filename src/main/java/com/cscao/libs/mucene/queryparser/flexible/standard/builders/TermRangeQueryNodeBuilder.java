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
import com.cscao.libs.mucene.queryparser.flexible.core.nodes.FieldQueryNode;
import com.cscao.libs.mucene.queryparser.flexible.core.nodes.QueryNode;
import com.cscao.libs.mucene.queryparser.flexible.core.util.StringUtils;
import com.cscao.libs.mucene.queryparser.flexible.standard.nodes.TermRangeQueryNode;
import com.cscao.libs.mucene.queryparser.flexible.standard.processors.MultiTermRewriteMethodProcessor;
import com.cscao.libs.mucene.search.MultiTermQuery;
import com.cscao.libs.mucene.search.TermRangeQuery;

/**
 * Builds a {@link TermRangeQuery} object from a {@link TermRangeQueryNode}
 * object.
 */
public class TermRangeQueryNodeBuilder implements StandardQueryBuilder {
  
  public TermRangeQueryNodeBuilder() {
  // empty constructor
  }
  
  @Override
  public TermRangeQuery build(QueryNode queryNode) throws QueryNodeException {
    TermRangeQueryNode rangeNode = (TermRangeQueryNode) queryNode;
    FieldQueryNode upper = rangeNode.getUpperBound();
    FieldQueryNode lower = rangeNode.getLowerBound();
    
    String field = StringUtils.toString(rangeNode.getField());
    String lowerText = lower.getTextAsString();
    String upperText = upper.getTextAsString();
    
    if (lowerText.length() == 0) {
      lowerText = null;
    }
    
    if (upperText.length() == 0) {
      upperText = null;
    }
    
    TermRangeQuery rangeQuery = TermRangeQuery.newStringRange(field, lowerText, upperText, rangeNode
        .isLowerInclusive(), rangeNode.isUpperInclusive());
    
    MultiTermQuery.RewriteMethod method = (MultiTermQuery.RewriteMethod) queryNode
        .getTag(MultiTermRewriteMethodProcessor.TAG_ID);
    if (method != null) {
      rangeQuery.setRewriteMethod(method);
    }
    
    return rangeQuery;
    
  }
  
}
