package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")//DTYPE에 들어가게될 값
public class Album extends Item {
    private String artist;
}
