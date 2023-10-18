package grup.grupaws.Dto;

import grup.grupaws.Entity.MemberEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//save에서 받는거 이름이 똑같으면 알아서 스프링에서 객체로 관리해줌
public class MemberDTO {
    private String memberEmail;
    private String memberPassword;
    private String memberBirth;
    private String memberNum;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO=new MemberDTO();
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberNum(memberEntity.getMemberNum());
        return memberDTO;
    }
}
