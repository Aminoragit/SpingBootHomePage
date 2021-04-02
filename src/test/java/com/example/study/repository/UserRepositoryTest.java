package com.example.study.repository;
import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String account = "Test01";
        String password = "Test01";
        String status="REGISTERED";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-2222";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
        user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);

        User newUser = userRepository.save(user);

        Assert.assertNotNull(newUser);


    }

    @Test
    @Transactional
    public void read(){

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");
        Assert.assertNotNull(user);

     }

    @Test
    public void update(){
        Optional<User> user =userRepository.findById(2L);
        user.ifPresent(selectUser->{
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("Update methood()");
            userRepository.save(selectUser);
        });
    }

    //@DeleteMapping("/api/user")
    //    public void delete(@RequestParam Long id){ }
    @Test
    @Transactional //SQL문 자체는 모두 실행하는데 실질적으로 DB에 save는 안되게하는 test기능
    public void delete(){
        //데이터 삭제하기
        Optional<User> user =userRepository.findById(5L);
        //반드시 user는 존재해야만 한다라는 코드 <- 통과못하면 에러발생하면서 코드가 중단됨
        Assert.assertTrue(user.isPresent());

        user.ifPresent(selectUser->{
           userRepository.delete(selectUser); });

        //데이터 있는지 확인하는곳이며 위에서 id=2인것을 삭제했으므로 여기선 삭제되었다고 나온다.
        Optional<User> deleteUser =userRepository.findById(5L);

        //deleteUser의 값은 반드시 false여야만한다.
        Assert.assertFalse(deleteUser.isPresent());

        //이거는 우리가 확인한다고 작성한 코드인데 쓸데없이 길다//그래서 위에처럼 Assert를 사용함
       if(deleteUser.isPresent()){
            System.out.println("데이터 존재 : "+deleteUser.get());

        System.out.println("데이터 삭제/데이터 없음 ");
        }
   }
}
