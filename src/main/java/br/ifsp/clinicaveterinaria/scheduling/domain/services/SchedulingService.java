package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Appointment;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledDate;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SchedulingService {

    private final Repository veterinarianRepository;
    private final Repository appointmentRepository;

    public SchedulingService(Repository veterinarianRepository, Repository appointmentRepository) {
        this.veterinarianRepository = veterinarianRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void requestAppointment(br.ifsp.clinicaveterinaria.scheduling.domain.entities.Client client) {
        if (client == null) {
            throw new IllegalArgumentException("cliente deve ser selecionado");
        }
        // lógica futura para criar/validar o atendimento
    }

    public List<Veterinarian> findAvailableVeterinarians(ScheduledDate appointmentDate) {
        LocalDate date = extractDate(appointmentDate);
        List<Veterinarian> all = findAllVets();
        List<Appointment> appts = findAppointmentsByDate(date);
        Set<Veterinarian> busy = appts.stream()
                .map(this::extractVet)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return all.stream()
                .filter(v -> !busy.contains(v))
                .toList();
    }

    public List<ScheduledDate> findAvailableDaysFor(
            Veterinarian vet,
            LocalDate start,
            LocalDate end
    ) {
        if (vet == null) throw new IllegalArgumentException("veterinário deve ser selecionado");
        if (start == null || end == null || end.isBefore(start)) throw new IllegalArgumentException("intervalo de datas inválido");

        List<?> appts = tryFindAppointmentsByVetAndDateBetween(this.appointmentRepository, vet, start, end);

        Set<LocalDate> booked = appts.stream()
                .map(this::extractDateFromAppointment)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<ScheduledDate> freeDays = new
                ArrayList<>();
        LocalDate d = start;
        while (!d.isAfter(end)) {
            if (!booked.contains(d)) {
                freeDays.add(new ScheduledDate(d));
            }
            d = d.plusDays(1);
        }
        return freeDays;
    }

    private
    List<?> tryFindAppointmentsByVetAndDateBetween(
            Object repository,
            Veterinarian vet,
            LocalDate start,
            LocalDate end
    ) {
        if (repository == null) return
                Collections.emptyList();
        try {
            Method m = repository.getClass().getMethod("findByVetAndDateBetween",
                    Veterinarian.class,
                    LocalDate.class,
                    LocalDate.class);
            Object r = m.invoke(repository, vet, start, end);
            if (r instanceof
                    List<?> l) return l;
        } catch (Exception ignored) {}

        try {

            Method m = repository.getClass().getMethod("findByVet",
                    Veterinarian.class);
            Object r = m.invoke(repository, vet);
            if (r instanceof java.util.List<?> l) {
                return l.stream()
                        .filter(o -> {
                            LocalDate d = extractDateFromAppointment(o);
                            return d != null && !d.isBefore(start) && !d.isAfter(end);
                        })
                        .toList();
            }
        } catch (Exception ignored) {}

        return java.util.Collections.emptyList();
    }

    private LocalDate extractDateFromAppointment(Object appt) {
        try {

            Method m = appt.getClass().getMethod("getScheduledDate");
            Object r = m.invoke(appt);
            if (r instanceof ScheduledDate sd) {
                return extractDate(sd);
            }
        } catch (Exception ignored) {}
        try {

            Method m = appt.getClass().getMethod("getDate");
            Object r = m.invoke(appt);
            if (r instanceof LocalDate d) return d;
        } catch (Exception ignored) {}
        return null;
    }

    private LocalDate extractDate(ScheduledDate sd) {
        try {
            Method m = sd.getClass().getMethod("value");
            Object r = m.invoke(sd);
            if (r instanceof LocalDate d) return d;
        } catch (Exception ignored) {}
        try {
            Method m = sd.getClass().getMethod("getValue");
            Object r = m.invoke(sd);
            if (r instanceof LocalDate d) return d;
        } catch (Exception ignored) {}
        try {
            var f = sd.getClass().getDeclaredField("date");
            f.setAccessible(true);
            Object r = f.get(sd);
            if (r instanceof LocalDate d) return d;
        } catch (Exception ignored) {}
        throw new IllegalStateException("Não consegui extrair LocalDate de ScheduledDate (esperava value()/getValue()/campo 'date').");
    }

    private List<Veterinarian> findAllVets() {
        for (String m : List.of("findAll", "listAll", "all", "list")) {
            List<Veterinarian> r = invokeListNoArg(veterinarianRepository, m, Veterinarian.class);
            if (r != null) return r;
        }
        return Collections.emptyList();
    }

    private List<Appointment> findAppointmentsByDate(LocalDate date) {
        List<Appointment> r = invokeListOneArg(appointmentRepository, "findByDate", LocalDate.class, date, Appointment.class);
        if (r != null) return r;
        return Collections.emptyList();
    }

    private Veterinarian extractVet(Appointment a) {
        try {
            Method m = a.getClass().getMethod("getVet");
            Object r = m.invoke(a);
            if (r instanceof Veterinarian v) return v;
        } catch (Exception ignored) {}
        try {
            Method m = a.getClass().getMethod("getVeterinarian");
            Object r = m.invoke(a);
            if (r instanceof Veterinarian v) return v;
        } catch (Exception ignored) {}
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> invokeListNoArg(Object target, String methodName, Class<T> itemType) {
        try {
            Method m = target.getClass().getMethod(methodName);
            Object r = m.invoke(target);
            if (r instanceof List<?> list) {
                if (list.isEmpty() || itemType.isInstance(list.get(0))) {
                    return (List<T>) list;
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <A, T> List<T> invokeListOneArg(Object target, String methodName, Class<A> argType, A arg, Class<T> itemType) {
        try {
            Method m = target.getClass().getMethod(methodName, argType);
            Object r = m.invoke(target, arg);
            if (r instanceof List<?> list) {
                if (list.isEmpty() || itemType.isInstance(list.get(0))) {
                    return (List<T>) list;
                }
            }
        } catch (Exception ignored) {}
        return null;
    }
}
