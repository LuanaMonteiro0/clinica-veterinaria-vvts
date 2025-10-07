package br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CRMV {

    private String crmv;

    private static final Pattern CRMV_PATTERN = Pattern.compile(
            "^(?i)CRMV/(?<uf>AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)\\s+(?<num>\\d{1,6})$"
    );

    public CRMV() {
    }

    public CRMV(String crmv) {
        this.crmv = crmv;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        Objects.requireNonNull(crmv, "CRMV não pode ser nulo");

        String normalized = crmv.trim().replaceAll("\\s+", " ");

        Matcher m = CRMV_PATTERN.matcher(normalized);
        if (!m.matches()) {
            throw new IllegalArgumentException(
                    "CRMV inválido. Formato esperado: CRMV/UF 123456 (UF válida e 1–6 dígitos)."
            );
        }

        String uf = m.group("uf").toUpperCase();
        String num = leftPadTo6(m.group("num"));

        this.crmv = "CRMV/" + uf + " " + num;
    }

    private static String leftPadTo6(String digits) {
        int len = digits.length();
        if (len == 6) return digits;
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6 - len; i++) sb.append('0');
        sb.append(digits);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CRMV crmv1 = (CRMV) o;
        return Objects.equals(crmv, crmv1.getCrmv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(crmv);
    }


}