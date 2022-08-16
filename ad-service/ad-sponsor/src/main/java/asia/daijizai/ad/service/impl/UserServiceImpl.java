package asia.daijizai.ad.service.impl;

import asia.daijizai.ad.constant.Constants;
import asia.daijizai.ad.dao.AdUserRepository;
import asia.daijizai.ad.entity.AdUser;
import asia.daijizai.ad.exception.AdException;
import asia.daijizai.ad.service.IUserService;
import asia.daijizai.ad.util.CommonUtil;
import asia.daijizai.ad.vo.CreateUserRequest;
import asia.daijizai.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 9:16
 * @description
 */

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if(!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if(oldUser!=null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser=userRepository.save(new AdUser(
                request.getUsername(), CommonUtil.md5(request.getUsername())
        ));

        return new CreateUserResponse(
                newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime()
        );
    }
}
