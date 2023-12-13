package com.example.mpdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mpdemo.entity.User;
import com.example.mpdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired // 自动注入实例对象（将UserMapper接口自动实例化一个对象，赋给userMapper）
    private UserMapper userMapper;

    // ----------查询数据----------
    // 这里users会自动转化为json格式并发送给前端
    @GetMapping("/user")
    public List<User> query() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
        return users;
    }

    // ----------插入数据----------
    @PostMapping("/user")
    public String save(User user) {
        int i = userMapper.insert(user);
        if (i > 0) {
            return "插入成功";
        } else {
            return "插入失败";
        }
    }

    // ----------多表查询----------
    @GetMapping("/user/findAll")
    public List<User> find() {
        return userMapper.selectAllUserAndOrders();
    }

    // 条件查询
    @GetMapping("/user/find")
    public List<User> findByCondition() {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", "lucy");
        return userMapper.selectList(queryWrapper);
    }

    // 分页查询(limit)
    @GetMapping("/user/findByPage")
    public IPage findByPage() {
        // 设置起始值及每页条数
        Page<User> page = new Page<>(0, 2); // 从索引0开始取数，取2条
        Page<User> userPage = userMapper.selectPage(page, null);// queryWrapper：查询条件
        return userPage;
    }
}
