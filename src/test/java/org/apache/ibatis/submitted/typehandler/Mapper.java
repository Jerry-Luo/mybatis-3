/*
 *    Copyright 2009-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.submitted.typehandler;

import java.util.Map;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.submitted.typehandler.Product.ProductId;
import org.apache.ibatis.type.JdbcType;

public interface Mapper {

  @Select("select * from users where id = #{value}")
  // @formatter:off
  @Results({
      @Result(column = "id", property = "id"),
      @Result(column = "name", property = "name"),
      @Result(column = "city", property = "city", jdbcType = JdbcType.CHAR),
      @Result(column = "state", property = "state", jdbcType = JdbcType.VARCHAR)
    })
  // @formatter:on
  User getUser(Integer id);

  @Insert({ "insert into product (name) values (#{name})" })
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insertProduct(Product product);

  @Select("select id, name from product where name = #{value}")
  Product getProductByName(String name);

  Product getProductByNameXml(String name);

  @Select("select id, name from product where name = #{value}")
  // @formatter:off
  @ConstructorArgs({
      @Arg(id = true, column = "id", javaType = ProductId.class, jdbcType = JdbcType.INTEGER),
      @Arg(column = "name")
    })
  // @formatter:on
  Product getProductByNameUsingConstructor(String name);

  @Select("select id from product where name = #{value}")
  ProductId getProductIdByName(String name);

  @Select("select current_date d, current_time t, current_timestamp ts, localtime lt, localtimestamp lts from (values(0))")
  Map<String, Object> selectDateTime();

  @Select("select id, name, released_on from product where id = #{id}")
  Map<String, Object> getProductAsMap(Integer id);

  @Insert({ "insert into vague (vague) values (#{bean})" })
  int insertVague(@Param("bean") VagueBean bean);

  @Result(property = "id", column = "id")
  @Result(property = "vague", column = "vague")
  @Select("select * from vague where id = #{id}")
  VagueBean selectVague(Integer id);

  @ResultMap("vagueRM")
  @Select("select * from vague where id = #{id}")
  VagueBean selectVagueNested(Integer id);
}
