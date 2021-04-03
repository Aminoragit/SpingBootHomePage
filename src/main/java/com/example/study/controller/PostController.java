package com.example.study.controller;


import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {
//POST는 html의 form태그나
    //ajax 검색할때 주로 사용된다.
    //post body -> data
    //즉, 그냥 받는게 아니라 @RequestBody에 해당 값들을 받아서 처리해야하는게 GET이랑 다른점이다.
    //json,xml,multi-form

    @PostMapping(value= "/postMethod")//produces={"지원형태"<-기본값 : json}
    public SearchParam postMethod(@RequestBody SearchParam searchParam){
        return searchParam;
    }

    @PutMapping("/putMethod")
    public void put(){}

    @PatchMapping("/patchMethod")
    public void patch(){}
}