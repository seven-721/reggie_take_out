package com.seven.reggie.dao;

import com.seven.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/3
 * Time:17:55
 */
public interface ShoppingCartDao {

    ShoppingCart findByUserId(ShoppingCart shoppingCart);

    //添加
    //插入数据
    @Insert("insert into shopping_cart values(null,#{name},#{image},#{userId}," +
            "#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    //修改数据
    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void update(ShoppingCart shoppingCart1);

    @Select("select * from shopping_cart where user_id=#{userId}")
    List<ShoppingCart> findByUsersId(Long userId);

    //删除购物车
    @Delete("delete from shopping_cart where user_id=#{userId}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ")
    void deleteClean(Long userId);

    @Delete("delete from shopping_cart where id=#{userId}")
    void deleteByUserId(Long userId);
}
