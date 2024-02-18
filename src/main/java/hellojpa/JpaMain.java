package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //애플리케이션 로딩 시점에 데이터베이스당 딱 1개만
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// persistance.xml에 정의된 unit name
        // ex 고객의 요청이 올때마다 만들어줘야 하며 쓰레드간에 공유를 해서는 안된다.
        EntityManager em = emf.createEntityManager();
        //데이터를 저장하는 행위가 일어날떄 반드시 해줘야함
        //JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setUsername("hello");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("hello2");
            em.persist(member2);
            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.getReference(Member.class, member2.getId());
            System.out.println("(m1.getClass()== m2.getClass()) = " + (m1.getClass() == m2.getClass()));
//            Member findMember = em.find(Member.class, member.getId());
            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("findMember = " + reference.getClass());
            System.out.println("findMember.getUsername() = " + reference.getUsername());
            System.out.println("findMember.getId() = " + reference.getId());

            emf.getPersistenceUnitUtil().isLoaded(reference); // 프록시 인스턴스의 초기화 여부 확인

            reference.getClass().getName(); // 프록시 클래스 확인 방법

            Hibernate.initialize(reference); // 프록시 강제 초기화

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //자원을 다쓰면 닫아줘야함
        }

        emf.close(); // application이 종료되면서 닫아줘야 함

    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
    }

}