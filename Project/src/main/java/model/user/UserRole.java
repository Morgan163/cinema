package model.user;

public enum UserRole{
    ADMIN(0, "Admin"),
    OPERATOR(1, "Operator");

    private long roleID;
    private String roleName;

    public static UserRole getRoleByRoleId(long id){
        UserRole[] allRoles = values();
        for (UserRole role : allRoles){
            if (role.getRoleID() == id){
                return role;
            }
        }
        throw new RuntimeException("IllegalIdForRole");
    }

    UserRole(long roleID, String roleName) {
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
}
