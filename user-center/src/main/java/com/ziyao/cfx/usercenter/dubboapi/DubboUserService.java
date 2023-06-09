package com.ziyao.cfx.usercenter.dubboapi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.cfx.dubboapi.user.UserOpenApi;
import com.ziyao.cfx.dubboapi.user.vo.UserVO;
import com.ziyao.cfx.usercenter.entity.User;
import com.ziyao.cfx.usercenter.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Service
@DubboService(version = "1.0.0")
public class DubboUserService implements UserOpenApi {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO getUser(Long appid, String username) {

        User user = userMapper.selectOne(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getAppId, appid)
                        .eq(User::getAccessKey, username));

        if (!ObjectUtils.isEmpty(user)) {
            UserVO userVo = new UserVO();
            BeanUtils.copyProperties(user, userVo);
            return userVo;
        }
        return null;
    }
}
