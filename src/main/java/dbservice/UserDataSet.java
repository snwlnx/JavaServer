package dbservice;

import base.LongId;
import user.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class UserDataSet implements Serializable {
    private static final long serialVersionUID = -1L;

    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="email")
    private String email;

    @Id
    @Column(name="login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name="hash")
    private String hash;

    @Column(name="salt")
    private String salt;

    @Column(name="banned")
    private int banned;

    @Column(name="registration_time")
    private String regTime;

    @Column(name="avatar_path")
    private String avPath;

    @Column(name="banned_end_time")
    private String banEndTime;



    public UserDataSet(long id, String name ) {
        this.setId(id);
        this.setName(name);
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }


    public LongId<User> getId() {
        LongId<User> userId = new LongId<User>(id);
        return userId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
