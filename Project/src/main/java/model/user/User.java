package model.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class User {
    private long userID;
    private String login;
    private String password;
    private UserRole userRole;

    public User(long userID, String login, String password, UserRole userRole) {
        this.userID = userID;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
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
                .append(userID, user.userID)
                .append(login, user.login)
                .append(password, user.password)
                .append(userRole, user.userRole)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userID)
                .append(login)
                .append(password)
                .append(userRole)
                .toHashCode();
    }
}
