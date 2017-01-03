package com.wangl.sb.web;

import com.wangl.sb.daomain.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 */
@RestController
@RequestMapping(value = "/users") // 通过这里配置使下面的映射都在/users下，可去除
public class UserController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello User";
    }

    static Map<Long, User> userMap = Collections.synchronizedMap(new HashMap<Long, User>());

    /**
     * 处理"/users/"的GET请求，用来获取用户列表
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList() {

        List<User> userList = new ArrayList<User>(userMap.values());

        return userList;
    }

    /**
     * 处理"/users/"的POST请求，用来创建User
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user) {
        userMap.put(user.getId(), user);
        return "success";
    }

    /**
     * 处理"/users/id"的Get请求,通过id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return userMap.get(id);
    }

    /**
     * 处理"/users/id"的PUT请求,用来更新id用户的信息
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
        User userUpdate;
        userUpdate = userMap.get(id);

        try{
            userUpdate.setAge(user.getAge());
            userUpdate.setName(user.getName());
            userMap.put(id, userUpdate);
        }
        catch (Exception e){
            return "User " + id + " is not exist";
        }

        return "success";
    }

    /**
     * 处理"/users/id"的DELETE请求,用来删除id用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id){
        if (userMap.remove(id) == null) {
            return "User " + id + " is not exist";
        }

        return "success";
    }


}
