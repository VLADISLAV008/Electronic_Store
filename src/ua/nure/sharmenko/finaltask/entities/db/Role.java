package ua.nure.sharmenko.finaltask.entities.db;

public enum Role {
    CLIENT, ADMIN;

    public static Role getUserRole(long roleId) {
        return Role.values()[(int) roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
