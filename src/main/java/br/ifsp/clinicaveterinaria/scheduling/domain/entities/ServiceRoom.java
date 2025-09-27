package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import java.util.regex.Pattern;

public class ServiceRoom {

    private static final Pattern THREE_DIGITS = Pattern.compile("^[0-9]{3}$");

    private String code; // sempre armazenado como 3 dígitos (zero-padded)

    public ServiceRoom(String code) {
        setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || !THREE_DIGITS.matcher(code).matches()) {
            throw new IllegalArgumentException("Código inválido: deve ter exatamente 3 dígitos (000–999).");
        }
        this.code = code;
    }

    // --- Utilitários de validação (podem ser usados em testes e em outros pontos do domínio) ---

    public static boolean isValid(String code) {
        return code != null && THREE_DIGITS.matcher(code).matches();
    }

    public static boolean isValid(int code) {
        return 0 <= code && code <= 999;
    }

}