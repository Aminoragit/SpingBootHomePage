package com.example.study.controller.api;


import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")

//일일히 CRUD를 설정해주는건 귀찮으니
//ifs->CrudInterface 인터페이스 작성
//각 Crud의 매핑설정
public class UserApiController implements CrudInterface<UserApiRequest,UserApiResponse> {

    @Override
    @PostMapping("")
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> userApiRequest)
    {
        return null;

    }

    @Override
    @GetMapping("{id}")
    public Header<UserApiResponse>
        read(@PathVariable(name="id") Long id) {
        return null;
    }

    @Override
    @PutMapping("")
    public Header<UserApiResponse>
        update(@RequestBody Header<UserApiRequest> request) {

        return null;
    }

    @Override
    @DeleteMapping("{id}")
    public Header
        delete(@PathVariable(name="id") Long id) {

        return null;
    }

}
