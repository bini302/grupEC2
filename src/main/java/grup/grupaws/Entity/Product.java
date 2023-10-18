package grup.grupaws.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "storeTitle")
    private String storeTitle;

    @Column(name = "storeName")
    private String storeName;

    @Column(name = "price")
    private String price;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "storeLink")
    private String storeLink;

    @Column(name = "searchName")
    private String searchName;

    public String getImgUrl() {
        return imgUrl;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public String getStoreTitle() {
        return storeTitle;
    }

    public String getPrice() {
        return price;
    }
}

