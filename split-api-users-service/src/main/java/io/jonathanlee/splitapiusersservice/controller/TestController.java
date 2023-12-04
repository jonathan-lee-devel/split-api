package io.jonathanlee.splitapiusersservice.controller;

import io.jonathanlee.splitapiusersservice.dao.UserDao;
import io.jonathanlee.splitapiusersservice.domain.UserVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class TestController {

  private final UserDao userDao;

  @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<List<UserVO>> getAllUsers() {
    return ResponseEntity.ok(this.userDao.getAllUsers());
  }

  @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Integer> createUser() {
    UserVO userVO = new UserVO();
    userVO.setFirstName("Jane");
    userVO.setLastName("Richards");
    return ResponseEntity.ok(this.userDao.createNewUser(userVO));
  }

}
