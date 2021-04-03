package com.example.study.controller.api;


import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")

//일일히 CRUD를 설정해주는건 귀찮으니
//ifs->CrudInterface 인터페이스 작성
//각 Crud의 매핑설정
public class UserApiController implements CrudInterface {

    @Override
    @PostMapping("")
    public Header create(){
        return null;
    }

    @Override
    @GetMapping("{id}")
    public Header read(@PathVariable(name="id") Long id) {
        return null;
    }

    @Override
    @PutMapping("")
    public Header update() {
        return null;
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable(name="id") Long id) {
        return null;
    }

}
