package com.ziyao.cfx.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziyao.cfx.common.exception.ServiceException;
import com.ziyao.cfx.common.writer.Errors;
import com.ziyao.cfx.usercenter.dto.UserDTO;
import com.ziyao.cfx.usercenter.entity.User;
import com.ziyao.cfx.usercenter.service.UserService;
import com.ziyao.cfx.web.context.ContextManager;
import com.ziyao.cfx.web.details.UserDetails;
import com.ziyao.cfx.web.mvc.BaseController;
import com.ziyao.cfx.web.orm.PageQuery;
import com.ziyao.cfx.web.orm.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/usercenter/user")
public class UserController extends BaseController<UserService, User> {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public void save(@RequestBody UserDTO entityDTO) {
        super.iService.save(entityDTO.getInstance());
    }

    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody UserDTO entityDTO) {
        super.iService.saveOrUpdate(entityDTO.getInstance());
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody UserDTO entityDTO) {
        if (ObjectUtils.isEmpty(entityDTO.getId())) {
            throw new ServiceException(Errors.ILLEGAL_ARGUMENT);
        }
        super.iService.updateById(entityDTO.getInstance());
    }

    /**
     * 默认一次插入500条
     */
    @PostMapping("/saveBatch")
    public void saveBatch(@RequestBody List<UserDTO> entityDTOList) {
        super.iService.saveBatch(entityDTOList.stream().map(UserDTO::getInstance).collect(Collectors.toList()), 500);
    }

    /**
     * 条件分页查询
     *
     * @param pageQuery 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<User> getPage(@RequestBody PageQuery<UserDTO> pageQuery) {
        Page<User> page = PageUtils.initPage(pageQuery, User.class);
        return userService.page(page, pageQuery.getQuery());
    }

    @GetMapping("/current")
    public UserDetails userDetails() {
        return ContextManager.getUser();
    }
}
