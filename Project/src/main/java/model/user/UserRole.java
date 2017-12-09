package model.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserRole{
    private static final int ADMIN_ROLE_ID = 0;
    private static final int OPERATOR_ROLE_ID = 1;
    private static final String OPERATOR_ROLE_NAME = "Operator";
    private static final String ADMIN_ROLE_NAME = "Admin";

    private static UserRole adminRole = new UserRole(ADMIN_ROLE_ID, ADMIN_ROLE_NAME);
    private static UserRole operatorRole = new UserRole(OPERATOR_ROLE_ID, OPERATOR_ROLE_NAME);

    private long roleID;
    private String roleName;

    public static UserRole getAdminRole(){
        return adminRole;
    }

    public static UserRole getOperatorRole(){
        return operatorRole;
    }

    private UserRole(long roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    public long getRoleID() {
        return roleID;
    }

    private void setRoleID(long roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    private void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        return new EqualsBuilder()
                .append(roleID, userRole.roleID)
                .append(roleName, userRole.roleName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(roleID)
                .append(roleName)
                .toHashCode();
    }
}
