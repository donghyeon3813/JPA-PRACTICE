package hellojpa;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String   username;

    //기간 Period
    @Embedded
    private Period workPeriod;

    //주소
    @Embedded
    private Address homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressesHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private  List<AddressEntity> addressesHistory = new ArrayList<>();
    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public Period getWorkPeriod() {

        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {

        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {

        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {

        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {

        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {

        this.favoriteFoods = favoriteFoods;
    }

//    public List<Address> getAddressesHistory() {
//
//        return addressesHistory;
//    }
//
//    public void setAddressesHistory(List<Address> addressesHistory) {
//
//        this.addressesHistory = addressesHistory;
//    }

    public List<AddressEntity> getAddressesHistory() {

        return addressesHistory;
    }

    public void setAddressesHistory(List<AddressEntity> addressesHistory) {

        this.addressesHistory = addressesHistory;
    }
}
