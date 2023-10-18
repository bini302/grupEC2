package grup.grupaws.Entity;

import grup.grupaws.Dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "member")
public class MemberEntity {

    @Id // pk
    @Column(unique = true, name = "memberEmail") //unique 제약조건 추가
    private String memberEmail;

    @Column(name = "memberPassword")
    private String memberPassword;
    @Column(name = "memberBirth")
    private String memberBirth;
    @Column(name = "memberPhone")
    private String memberNum;

    //처음에 회원가입할 땐 id가 자동으로 부여되는데
    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberNum(memberDTO.getMemberNum());
        return memberEntity;
    }

    //수정할 땐 이미 id가 있으니까 그걸 가져오는게 필요함
    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberNum(memberDTO.getMemberNum());
        return memberEntity;
    }
}