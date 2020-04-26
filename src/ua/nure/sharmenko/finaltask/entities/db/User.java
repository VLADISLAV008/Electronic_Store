package ua.nure.sharmenko.finaltask.entities.db;

/**
 * User entity.
 *
 * @author V.Shramenko
 *
 */
public class User extends Entity {

    private static final long serialVersionUID = -6524279778373709366L;

    /**
     * User email
     */
    private String email;

    /**
     * The password of user
     */
    private String password;

    /**
     * The id of user role
     * If roleId == 0 -> Client,
     * if roleId == 1 -> Admin
     */
    private int roleId;

    /**
     * User name
     */
    private String name;

    /**
     * User surname
     */
    private String surname;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
