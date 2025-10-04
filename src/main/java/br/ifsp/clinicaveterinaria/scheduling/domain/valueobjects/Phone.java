package br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects;

public final class Phone {
    private String phone;

    public Phone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {

        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty.");
        }

        String regex = "^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$";

        if (!phone.matches(regex)) {
            throw new IllegalArgumentException("Invalid phone format. Expected: (xx) xxxx-xxxx or (xx) xxxxx-xxxx");
        }

        String onlyNumbers = phone.replaceAll("\\D", "");

        if (!(onlyNumbers.length() == 10 || onlyNumbers.length() == 11)) {
            throw new IllegalArgumentException("Phone number must contain 10 or 11 digits.");
        }

        this.phone = phone;
    }
}
