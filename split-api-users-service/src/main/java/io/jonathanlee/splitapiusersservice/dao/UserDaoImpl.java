package io.jonathanlee.splitapiusersservice.dao;

import io.jonathanlee.splitapiusersservice.domain.UserVO;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

  private static final String MAPPER_NAMESPACE = "io.jonathanlee.splitapiusersservice.UserMapper";
  private static final String INSERT_USER_QUERY = MAPPER_NAMESPACE + ".insertUser";
  private static final String GET_ALL_USERS_QUERY = MAPPER_NAMESPACE + ".getAllUsers";

  private final SqlSession sqlSession;

  @Override
  public Integer createNewUser(UserVO userVO) {
    try {
      int rowsAffected = this.sqlSession.insert(INSERT_USER_QUERY, userVO);
      if (rowsAffected == 0) {
        log.error("Failed to perform insert of user");
        return null;
      }
    } catch (DataAccessException dataAccessException) {
      log.error("There was an error inserting the user.", dataAccessException);
      return null;
    }

    return userVO.getId();
  }

  @Override
  public List<UserVO> getAllUsers() {
    List<UserVO> userVOs;
    try {
      userVOs = sqlSession.selectList(GET_ALL_USERS_QUERY);
    } catch (DataAccessException dataAccessException) {
      log.error("There was an error retrieving all users.", dataAccessException);
      return Collections.emptyList();
    }
    return userVOs;
  }
}
