package com.ziyao.cfx.usercenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziyao.cfx.common.exception.ServiceException;
import com.ziyao.cfx.common.writer.Errors;
import com.ziyao.cfx.usercenter.dto.UserRoleDTO;
import com.ziyao.cfx.usercenter.entity.UserRole;
import com.ziyao.cfx.usercenter.service.UserRoleService;
import com.ziyao.cfx.web.mvc.BaseController;
import com.ziyao.cfx.web.orm.PageQuery;
import com.ziyao.cfx.web.orm.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/usercenter/user-role")
public class UserRoleController extends BaseController<UserRoleService, UserRole> {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/save")
    public void save(@RequestBody UserRoleDTO entityDTO) {
        super.iService.save(entityDTO.getInstance());
    }

    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody UserRoleDTO entityDTO) {
        super.iService.saveOrUpdate(entityDTO.getInstance());
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody UserRoleDTO entityDTO) {
        if (ObjectUtils.isEmpty(entityDTO.getId())) {
            throw new ServiceException(Errors.ILLEGAL_ARGUMENT);
        }
        super.iService.updateById(entityDTO.getInstance());
    }

    /**
     * 默认一次插入500条
     */
    @PostMapping("/saveBatch")
    public void saveBatch(@RequestBody List<UserRoleDTO> entityDTOList) {
        super.iService.saveBatch(entityDTOList.stream().map(UserRoleDTO::getInstance).collect(Collectors.toList()), 500);
    }

    /**
     * 条件分页查询
     *
     * @param pageQuery 分页参数
     * @return 返回分页查询信息
     */
    @PostMapping("/page/get")
    public Page<UserRole> getPage(@RequestBody PageQuery<UserRoleDTO> pageQuery) {
        Page<UserRole> page = PageUtils.initPage(pageQuery, UserRole.class);
        return userRoleService.page(page, pageQuery.getQuery());
    }
}
