package com.example.mpdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mpdemo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

// 有一个问题，这里传入的类名是User，而数据库的表名为t_user不一致，怎么知道去那个数据库查询呢？？？？
// 解决方法在User类加入注解： @TableName("t_user")
@Repository
public interface UserMapper extends BaseMapper<User> {
//    // 会寻找配置文件里的数据库spring.datasource.url，并执行select语句，将结果返回到 List<User>中
//    @Select("select * from user")
//    public List<User> find();
//
//    @Select("select * from user where id=#{id}")
//    public List<User> findById(int id);
//
//    // 返回值int代表成功插入了几条记录
//    @Insert("insert into user values (#{id},#{username},#{password},#{birthday})")
//    public int insert(User user);
//
//    @Update("update user set username=#{username},password=#{password},birthday=#{birthday} where id=#{id}")
//    public int update(User user);
//
//    @Delete("delete from user where id=#{id}")
//    public int delete(int id);

    @Select("select * from t_user where id = #{id}")
    User selectById(int id);

    @Select("select * from t_user")
    @Results(
            {
                    @Result(column = "id", property = "id"), // column:数据库里t_user的字段。property:User类里的字段
                    @Result(column = "username", property = "username"),
                    @Result(column = "password", property = "password"),
                    @Result(column = "birthday", property = "birthday"),
                    // 将t_user的字段id映射到User类里的orders
                    // javaType：orders的类型（一对多映射）
                    @Result(column = "id",property = "orders",javaType = List.class,
                        many = @Many(select = "com.example.mpdemo.mapper.OrderMapper.selectByUid"))
            }
    )
    List<User> selectAllUserAndOrders();
}
