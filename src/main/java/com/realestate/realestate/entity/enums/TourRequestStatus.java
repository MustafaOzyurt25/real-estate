package com.realestate.realestate.entity.enums;
public enum TourRequestStatus {

        PENDING(0, "Pending", "Initial value"),
        APPROVED(1, "Approved", "Can be approved by owner of property"),
        DECLINED(2, "Declined", "Can be declined by owner of property"),
        CANCELED(3, "Canceled", "Can be canceled by guest");

        private final int id;
        private final String name;
        private final String description;

        TourRequestStatus(int id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
}


