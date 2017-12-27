package model.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class User implements Serializable {
    private String name;
    private String secondName;
    private String middleName;
    private long userID;
    private String login;

    public User(String name, String secondName, String middleName, String login, String password, UserRole userRole){
        this.name = name;
        this.secondName = secondName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    private String password;
    private UserRole userRole;

    public User() {
    }

    public User(String name, String secondName, String middleName, long userID, String login, String password, UserRole userRole){
        this.name = name;
        this.secondName = secondName;
        this.middleName = middleName;
        this.userID = userID;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSecondName(){
        return secondName;
    }

    public void setSecondName(String secondName){
        this.secondName = secondName;
    }

    public String getMiddleName(){
        return middleName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User)) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(login, user.login)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(login)
                .toHashCode();
    }
}
