package ua.nure.sharmenko.finaltask.entities.db;

public enum Status {
    REGISTERED, PAID, CANCELED;

    public static Status getOrderStatus(Order order) {
        long statusId = order.getStatusId();
        return Status.values()[(int) statusId - 1];
    }

    public static Long getStatusId(Status status) {
        for (int i = 0; i < Status.values().length; i++) {
            if (Status.values()[i].equals(status)) {
                return (long) i + 1;
            }
        }
        return null;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
