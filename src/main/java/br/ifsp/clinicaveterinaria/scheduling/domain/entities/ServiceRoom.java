package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import java.util.regex.Pattern;

public class ServiceRoom {

    // Exatamente 3 dígitos ASCII
    private static final Pattern THREE_DIGITS = Pattern.compile("^[0-9]{3}$");

    private String code; // sempre armazenado como 3 dígitos (zero-padded)

    // Construtor a partir de String (valida com regex)
    public ServiceRoom(String code) {
        setCode(code);
    }

    // Construtor a partir de int (valida faixa e aplica zero-padding)
    public ServiceRoom(int code) {
        setCode(code);
    }

    public String getCode() {
        return code;
    }

    // Setter String: valida com regex ^[0-9]{3}$
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
