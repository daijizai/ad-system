package asia.daijizai.ad.service;

import asia.daijizai.ad.exception.AdException;
import asia.daijizai.ad.vo.CreateUserRequest;
import asia.daijizai.ad.vo.CreateUserResponse;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 9:08
 * @description
 */
public interface IUserService {

    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
