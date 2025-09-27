package br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects;

public final class CPF {
    private String CPF;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        if (CPF == null || CPF.isEmpty()) {
            throw new IllegalArgumentException("CPF cannot be null or empty.");
        }

        String regex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";

        if (!CPF.matches(regex)) {
            throw new IllegalArgumentException("Invalid CPF format. Expected: XXX.XXX.XXX-XX");
        }

        String onlyNumbers = CPF.replaceAll("\\D", "");
        if (onlyNumbers.length() != 11) {
            throw new IllegalArgumentException("CPF must contain exactly 11 digits.");
        }

        if (!validateCheckDigits(onlyNumbers)) {
            throw new IllegalArgumentException("Invalid CPF. Check digits do not match.");
        }

        this.CPF = CPF;
    }

    private boolean validateCheckDigits(String CPF) {
        if (CPF.matches("(\\d)\\1{10}")) return false;

        int sum1 = 0, sum2 = 0;

        for (int i = 0; i < 9; i++) {
            int num = CPF.charAt(i) - '0';
            sum1 += num * (10 - i);
            sum2 += num * (11 - i);
        }

        int digit1 = (sum1 * 10) % 11;
        if (digit1 == 10) digit1 = 0;

        sum2 += digit1 * 2;
        int digit2 = (sum2 * 10) % 11;
        if (digit2 == 10) digit2 = 0;

        return digit1 == (CPF.charAt(9) - '0') &&
                digit2 == (CPF.charAt(10) - '0');

    }
}
