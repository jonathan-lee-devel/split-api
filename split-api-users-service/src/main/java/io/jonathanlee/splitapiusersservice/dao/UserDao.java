package io.jonathanlee.splitapiusersservice.dao;

import io.jonathanlee.splitapiusersservice.domain.UserVO;
import java.util.List;

public interface UserDao {

  /**
   * Method to create a new user
   * @param userVO user data
   * @return id of newly created user (on success)
   */
  Integer createNewUser(UserVO userVO);

  /**
   * Method to obtain all users
   * @return all users
   */
  List<UserVO> getAllUsers();

}
