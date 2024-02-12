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
            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbb");
            movie.setName("바람과함께사라지다.");
            movie.setPrice(10000);
            em.persist(movie);

            em.flush();
            em.clear();
            Movie findMNove = em.find(Movie.class, movie.getId());
            System.out.println("findMNove = " + findMNove);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //자원을 다쓰면 닫아줘야함
        }

        emf.close(); // application이 종료되면서 닫아줘야 함

    }

}