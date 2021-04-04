package com.example.study.controller.api;


import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api")

//일일히 CRUD를 설정해주는건 귀찮으니
//ifs->CrudInterface 인터페이스 작성
//각 Crud의 매핑설정
public class UserApiController implements CrudInterface<UserApiRequest,UserApiResponse> {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @Override
    @PostMapping("/user") // /api/user
    public Header<UserApiResponse>
        create(@RequestBody Header<UserApiRequest> request)
    {
        //System.out.println과 비슷한 동작을 한다.
        log.info("{},{}",request,"ABC");
        return userApiLogicService.create(request);
    }

    @Override
    @GetMapping("/user/{id}")
    public Header<UserApiResponse>
        read(@PathVariable(name="id") Long id) {
        log.info("read id : {}",id);
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("/user")
    public Header<UserApiResponse>
        update(@RequestBody Header<UserApiRequest> request) {

        return userApiLogicService.update(request);
    }



    @Override
    @DeleteMapping("/user/{id}")
    public Header
        delete(@PathVariable(name="id") Long id) {
        log.info("delete : {}",id);
        return userApiLogicService.delete(id);
    }

    @PostMapping("/user2") // /api/user2
    public Header<UserApiResponse>
        checkEmailCreate(@RequestBody Header<UserApiRequest> request)
    {
        //System.out.println과 비슷한 동작을 한다.
        log.info("{},{}",request,"ABC");
        return userApiLogicService.checkEmailCreate(request);
    }



}
