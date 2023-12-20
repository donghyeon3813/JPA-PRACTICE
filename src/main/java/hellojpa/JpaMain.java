package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
//            Member member = new Member(); insert
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

//            Member findMember = em.find(Member.class, 1L); // jpa를 통해 가져온 객체는 jpa에 의해 관리되며 commit이 있을 때 변경된 부분을 감지하여 update를 해준다.
//            findMember.setName("HelloJPA");

            //jpql 객체 지향 쿼리 언어 -> 엔티티 객체를 대상으로 쿼리
            List<Member> result = em.createQuery("select m from Member as m ", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)//pagination
                    .getResultList();

            for (Member member: result) {
                System.out.println("member.name() = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //자원을 다쓰면 닫아줘야함
        }

        emf.close(); // application이 종료되면서 닫아줘야 함



    }

}