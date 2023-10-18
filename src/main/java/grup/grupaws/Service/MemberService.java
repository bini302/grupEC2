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
        // 1 dto->entity 변환(서비스 클래스에서 변환하는 메서드 추가/엔티티에서 변환/ ...
        // 2 repository의 save 메서드 호출
        memberDTO.setMemberPassword(BCrypt.hashpw(memberDTO.getMemberPassword(), BCrypt.gensalt()));
        memberRepository.save(MemberEntity.toMemberEntity(memberDTO));
        // repository의 save메서드 호출(조건. 엔티티 객체를 넘겨줘야함)
        //jpa어쩌구 상속받아 쓰는거라 repository에 save 직접 쓴게 없음
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

