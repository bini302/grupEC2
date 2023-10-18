package grup.grupaws.Controller;

import grup.grupaws.Dto.MemberDTO;
import grup.grupaws.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Transactional
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/PlantsPlanet/save")
    @ResponseBody
    public String save(@RequestBody MemberDTO memberDTO){
        String memberEmail=memberDTO.getMemberEmail();
        try {
            if (memberService.emailCheck(memberEmail)=="ok") {
                memberService.save(memberDTO);
                return "success";
            } else {
                return "already exist";
            }
        } catch (Exception e) {
            return "failed";
        }
    }

    //로그인페이지

    @PostMapping("/PlantsPlanet/login")
    @ResponseBody
    public String login(@RequestBody MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult=memberService.login(memberDTO);
        try {
            if (loginResult!=null){
                //login success
                session.setAttribute("loginEmail", loginResult.getMemberEmail());
                String loginEmail= (String) session.getAttribute("loginEmail");
                //세션에 로그인 정보를 담는다
                return loginEmail;
            } else {
                session.invalidate();
                return "login failed";
            }
        } catch (Exception e){
            //login fail
            session.invalidate();
            return "login error";
        }
    }

    @GetMapping("/PlantsPlanet/loginCheck")
    @ResponseBody
    public String loginCheck(HttpSession session){
        String sessionEmail = (String) session.getAttribute("loginEmail");
        try{
            if(sessionEmail!=null){
                return sessionEmail;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    //로그아웃, 세션에서 지운다
    @PostMapping("/PlantsPlanet/logout")
    @ResponseBody
    public void logout(HttpSession session){
        session.invalidate();
    }

}