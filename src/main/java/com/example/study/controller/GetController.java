package com.example.study.controller;

import com.example.study.model.SearchParam;
import com.example.study.model.network.Header;
import org.springframework.web.bind.annotation.*;

import java.time.chrono.HijrahEra;

@RestController//현재 클래스를 컨트롤러로 쓰겠다는 표시
@RequestMapping("/api")//Localhost:8080/api 주소로 매칭
public class GetController {

    @RequestMapping(method= RequestMethod.GET,path="/getMethod") //localhost:8080/api/getMethod주소 호출시 사용자의 요청이 getRequest로 들어온다.
    public String getrequest(){
        return "Hi getMethod";
    }

    //?id=~~~~&password=~~~~~로 서버로 파라미터 전송하기
    @GetMapping("/getParameter") //localhost:8080/api/getparameter?id=1234&password=abcd로 매칭
    //이렇게 @RequestParam(name="값")을 해주면 입력할떄는 pwd로 입력받지만 주소창에 표시되는건 password라는 이름으로 표시된다.
    //단 그렇다고 지역변수값인 String password="test";가 표시되진 않고 이름만 password로 표시되는것뿐이다.
    public String getParameter(@RequestParam String id,@RequestParam(name="password") String pwd){
        String password="test";

        System.out.println("id" + id);
        System.out.println("password" + password);

        return id+pwd;

    }

    //근데 여럿 parameter을 받을때마다 @RequestParam을 쓰기엔 힘들다.
    //당연히 해결방법이있다.
    //1) Model 패키지를 생성한다 -> 2) Search에 쓸거니 Search 클래스 생성한다.
    //3) 필요한 요소들을 private로 생성한다. -> 4)code->generator->getter and setter로 해당 요소들을 선택한다.
    //4)우리가 생성한 SearchParam클래스로 새로운 변수를(multiparam)을 집어넣고 getter로 호출하면
    //우리가 입력한 값들이(http://localhost:8080/api/getMultiparam?account=test@gmail.com&email=test@gmail.css&page=3)
    //그대로 sysout 된다.

    @GetMapping("/getMultiparam")
    public SearchParam getMultiParameter(SearchParam searchParam){
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getPage());
        //{"account" : "", "email" : "", "page" : 0}
        //위에는 단순히 String으로 값을 받은거지만 네트워크상으로는 json형태로 받아야한다.

        //1) public String getMu~~ 에서 출력형인 String을 입력한 클래스로 변경후 return 값도 변수로 변경한다.
        // public SearchParam getMultiParameter(SearchParam multiparam)
        //3)끝 Spring의 jackson 라이브러리 에서 알아서 표시해준다.

        return searchParam;
    }

    @GetMapping("/header")
    public Header getHeader(){
        return Header.builder().resultCode("OK").description("OK").build();

    }
}

// 매핑주소가 동일할경우 에러가 발생한다
// @GetMapping("/api")
// 근데 클래스에 대해서 @RequestMapping한것은 주소가 같아도 별개의 클래스면 에러가 안난다.