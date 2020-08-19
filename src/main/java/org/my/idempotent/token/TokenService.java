package org.my.idempotent.token;

import org.my.idempotent.exception.IdempotentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class TokenService {

    @Autowired
    private RedisService redisService;

    public String createToken() {
        String uuid = UUID.randomUUID().toString();
        redisService.setEx(uuid, uuid, 1000L);
        return uuid;
    }

    public boolean checkToken(HttpServletRequest request) throws IdempotentException {
        String token = request.getHeader("token");      // 从请求头获取token
        if (StringUtils.isEmpty(token)) {       // 请求头没有token就在参数中获取
            token = request.getParameter("token");
            if (StringUtils.isEmpty(token)) {
                throw new IdempotentException("token 不存在");
            }
        }

        if (!redisService.exists(token)) {
            throw new IdempotentException("重复提交!");
        }

        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new IdempotentException("重复提交!");
        }

        return true;
    }
}
