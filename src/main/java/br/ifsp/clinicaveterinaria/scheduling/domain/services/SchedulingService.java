package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.*;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.ServiceRoomRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.VeterinarianRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SchedulingService {

    private final VeterinarianRepository veterinarianRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServiceRoomRepository roomRepository;

    public SchedulingService(VeterinarianRepository veterinarianRepository, AppointmentRepository appointmentRepository, ServiceRoomRepository roomRepository) {
        this.veterinarianRepository = veterinarianRepository;
        this.appointmentRepository = appointmentRepository;
        this.roomRepository = roomRepository;
    }

    public Appointment requestAppointment(Client client, Veterinarian veterinarian, ScheduledDate date, ScheduledTime time, Animal animal) {
        if (client == null) {
            throw new IllegalArgumentException("cliente deve ser selecionado");
        }
        if (veterinarian == null) {
            throw new IllegalArgumentException("veterinário deve ser selecionado");
        }
        if (date == null) {
            throw new IllegalArgumentException("data deve ser selecionada");
        }
        if (time == null) {
            throw new IllegalArgumentException("horário deve ser selecionado");
        }
        if (animal == null) {
            throw new IllegalArgumentException("animal deve ser selecionado");
        }

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if (date.getScheduledDate().isBefore(today)) {
            throw new IllegalArgumentException("Não é possível agendar em uma data passada.");
        }

        if (date.getScheduledDate().isEqual(today) && time.getStartTime().isBefore(now)) {
            throw new IllegalArgumentException("Não é possível agendar em um horário passado.");
        }

        List<ScheduledTime> availableTimes = findAvailableTimesFor(veterinarian, date);
        if (!availableTimes.contains(time)) {
            throw new IllegalArgumentException("Horário indisponível.");
        }

        ServiceRoom availableRoom = roomRepository.findAvailable()
                .orElseThrow(() -> new IllegalStateException("Nenhuma sala de atendimento disponível."));

        Appointment newAppointment = new Appointment(client, veterinarian, date, time, availableRoom, animal);

        appointmentRepository.add(newAppointment);

        return newAppointment;
    }

    public List<Veterinarian> findAvailableVeterinarians(ScheduledDate appointmentDate) {
        List<Veterinarian> allVets = veterinarianRepository.findAll();
        List<Appointment> appointmentsOnDate = appointmentRepository.findByDate(appointmentDate.getScheduledDate());

        Set<Veterinarian> busyVets = appointmentsOnDate.stream()
                .map(Appointment::getVet)
                .collect(Collectors.toSet());

        return allVets.stream()
                .filter(vet -> !busyVets.contains(vet))
                .collect(Collectors.toList());
    }

    public List<ScheduledDate> findAvailableDaysFor(Veterinarian vet, LocalDate start, LocalDate end) {
        if (vet == null) throw new IllegalArgumentException("veterinário deve ser selecionado");
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("intervalo de datas inválido");
        }

        List<Appointment> vetAppointments = appointmentRepository.findByVetAndDateBetween(vet, start, end);
        Set<LocalDate> bookedDates = vetAppointments.stream()
                .map(appointment -> appointment.getScheduledDate().getScheduledDate())
                .collect(Collectors.toSet());

        return start.datesUntil(end.plusDays(1))
                .filter(date -> !bookedDates.contains(date))
                .map(ScheduledDate::new)
                .collect(Collectors.toList());
    }

    public List<ScheduledTime> findAvailableTimesFor(Veterinarian vet, ScheduledDate date) {
        if (vet == null) throw new IllegalArgumentException("Veterinário deve ser selecionado.");
        if (date == null) throw new IllegalArgumentException("A data deve ser selecionada.");

        List<Appointment> appointmentsOnDay = appointmentRepository.findByVetAndDate(vet, date.getScheduledDate());
        Set<ScheduledTime> bookedTimes = appointmentsOnDay.stream()
                .map(Appointment::getScheduledTime)
                .collect(Collectors.toSet());

        return generateAllPossibleTimes()
                .filter(slot -> !bookedTimes.contains(slot))
                .collect(Collectors.toList());
    }

    private Stream<ScheduledTime> generateAllPossibleTimes() {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        long slotDurationInMinutes = 60;

        return Stream.iterate(startTime, time -> time.plusMinutes(slotDurationInMinutes))
                .limit((long) (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / (slotDurationInMinutes * 60))
                .map(time -> new ScheduledTime(time, time.plusMinutes(slotDurationInMinutes)));
    }
}