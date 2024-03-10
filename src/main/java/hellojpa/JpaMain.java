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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// persistance.xml에 정의된 unit name
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity","street","10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressesHistory().add(new Address("old1","street","10000"));
            member.getAddressesHistory().add(new Address("old2","street","10000"));

            em.persist(member);

            em.flush();
            em.clear();
            System.out.println("================Start==========================");
            Member findMember = em.find(Member.class, member.getId());

            findMember.getAddressesHistory();
            for (Address address : findMember.getAddressesHistory()) {
                System.out.println("address = " + address);
            }
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().remove("한식");

            findMember.getAddressesHistory().remove(new Address("old1","street","10000"));
            findMember.getAddressesHistory().add(new Address("newCity1","street","10000"));

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close(); //자원을 다쓰면 닫아줘야함
        }

        emf.close(); // application이 종료되면서 닫아줘야 함

    }


}