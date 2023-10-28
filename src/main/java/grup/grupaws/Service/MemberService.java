package grup.grupaws.Service;

import grup.grupaws.Dto.MemberDTO;
import grup.grupaws.Entity.MemberEntity;
import grup.grupaws.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO){
        memberDTO.setMemberPassword(BCrypt.hashpw(memberDTO.getMemberPassword(), BCrypt.gensalt()));
        memberRepository.save(MemberEntity.toMemberEntity(memberDTO));
    }
    public MemberDTO login(MemberDTO memberDTO) {
        // 1 회원이 입력한 이메일 db 조회
        // 2 db의 비민번호와 일치하는지 확인
        Optional<MemberEntity> byMemberEmail=memberRepository.findByMemberEmail((memberDTO.getMemberEmail()));
        //엔티티객체를 옵셔널로 한 번 더 감싸는거임
        if (byMemberEmail.isPresent()){
            //조회 결과가 있다
            MemberEntity memberEntity=byMemberEmail.get();
            if (BCrypt.checkpw(memberDTO.getMemberPassword(), memberEntity.getMemberPassword())) {
                //비밀번호 일치
                //entity->dto변환 후 리턴
                MemberDTO dto=MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                //비밀번호 불일치(로그인 실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(로그인 실패)
            return null;
        }
    }
    //회원가입 중복 확인
    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {
            //조회결과가 있음 => 사용x
            return null;
        } else {
            return "ok";
        }
    }
}

