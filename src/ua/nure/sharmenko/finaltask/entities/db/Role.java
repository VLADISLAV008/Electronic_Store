package ua.nure.sharmenko.finaltask.entities.db;

public enum Role {
    CLIENT, ADMIN;

    public static Role getUserRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
