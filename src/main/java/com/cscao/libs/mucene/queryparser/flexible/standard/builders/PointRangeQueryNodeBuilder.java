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

import com.cscao.libs.mucene.document.DoublePoint;
import com.cscao.libs.mucene.document.FloatPoint;
import com.cscao.libs.mucene.document.IntPoint;
import com.cscao.libs.mucene.document.LongPoint;
import com.cscao.libs.mucene.index.PointValues;
import com.cscao.libs.mucene.queryparser.flexible.core.QueryNodeException;
import com.cscao.libs.mucene.queryparser.flexible.core.messages.QueryParserMessages;
import com.cscao.libs.mucene.queryparser.flexible.core.nodes.QueryNode;
import com.cscao.libs.mucene.queryparser.flexible.core.util.StringUtils;
import com.cscao.libs.mucene.queryparser.flexible.messages.MessageImpl;
import com.cscao.libs.mucene.queryparser.flexible.standard.config.PointsConfig;
import com.cscao.libs.mucene.queryparser.flexible.standard.nodes.PointQueryNode;
import com.cscao.libs.mucene.queryparser.flexible.standard.nodes.PointRangeQueryNode;
import com.cscao.libs.mucene.search.Query;

/**
 * Builds {@link PointValues} range queries out of {@link PointRangeQueryNode}s.
 *
 * @see PointRangeQueryNode
 */
public class PointRangeQueryNodeBuilder implements StandardQueryBuilder {
  
  /**
   * Constructs a {@link PointRangeQueryNodeBuilder} object.
   */
  public PointRangeQueryNodeBuilder() {
  // empty constructor
  }
  
  @Override
  public Query build(QueryNode queryNode) throws QueryNodeException {
    PointRangeQueryNode numericRangeNode = (PointRangeQueryNode) queryNode;
    
    PointQueryNode lowerNumericNode = numericRangeNode.getLowerBound();
    PointQueryNode upperNumericNode = numericRangeNode.getUpperBound();
    
    Number lowerNumber = lowerNumericNode.getValue();
    Number upperNumber = upperNumericNode.getValue();
    
    PointsConfig pointsConfig = numericRangeNode.getPointsConfig();
    Class<? extends Number> numberType = pointsConfig.getType();
    String field = StringUtils.toString(numericRangeNode.getField());
    boolean minInclusive = numericRangeNode.isLowerInclusive();
    boolean maxInclusive = numericRangeNode.isUpperInclusive();
    
    // TODO: push down cleaning up of crazy nulls and inclusive/exclusive elsewhere
    if (Integer.class.equals(numberType)) {
      Integer lower = (Integer) lowerNumber;
      if (lower == null) {
        lower = Integer.MIN_VALUE;
      }
      if (minInclusive == false) {
        lower = lower + 1;
      }
      
      Integer upper = (Integer) upperNumber;
      if (upper == null) {
        upper = Integer.MAX_VALUE;
      }
      if (maxInclusive == false) {
        upper = upper - 1;
      }
      return IntPoint.newRangeQuery(field, lower, upper);
    } else if (Long.class.equals(numberType)) {
      Long lower = (Long) lowerNumber;
      if (lower == null) {
        lower = Long.MIN_VALUE;
      }
      if (minInclusive == false) {
        lower = lower + 1;
      }
      
      Long upper = (Long) upperNumber;
      if (upper == null) {
        upper = Long.MAX_VALUE;
      }
      if (maxInclusive == false) {
        upper = upper - 1;
      }
      return LongPoint.newRangeQuery(field, lower, upper);
    } else if (Float.class.equals(numberType)) {
      Float lower = (Float) lowerNumber;
      if (lower == null) {
        lower = Float.NEGATIVE_INFINITY;
      }
      if (minInclusive == false) {
        lower = Math.nextUp(lower);
      }
      
      Float upper = (Float) upperNumber;
      if (upper == null) {
        upper = Float.POSITIVE_INFINITY;
      }
      if (maxInclusive == false) {
        upper = Math.nextDown(upper);
      }
      return FloatPoint.newRangeQuery(field, lower, upper);
    } else if (Double.class.equals(numberType)) {
      Double lower = (Double) lowerNumber;
      if (lower == null) {
        lower = Double.NEGATIVE_INFINITY;
      }
      if (minInclusive == false) {
        lower = Math.nextUp(lower);
      }
      
      Double upper = (Double) upperNumber;
      if (upper == null) {
        upper = Double.POSITIVE_INFINITY;
      }
      if (maxInclusive == false) {
        upper = Math.nextDown(upper);
      }
      return DoublePoint.newRangeQuery(field, lower, upper);
    } else {
      throw new QueryNodeException(new MessageImpl(QueryParserMessages.UNSUPPORTED_NUMERIC_DATA_TYPE, numberType));
    }
  }
}
