package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Appointment;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Client;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledDate;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledTime;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
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

    public SchedulingService(VeterinarianRepository veterinarianRepository, AppointmentRepository appointmentRepository) {
        this.veterinarianRepository = veterinarianRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void requestAppointment(Client client, Veterinarian veterinarian, ScheduledDate date) {
        if (client == null) {
            throw new IllegalArgumentException("cliente deve ser selecionado");
        }
        if (veterinarian == null) {
            throw new IllegalArgumentException("veterinário deve ser selecionado");
        }
        if (date == null) {
            throw new IllegalArgumentException("data deve ser selecionada");
        }
        // Lógica de negócio para criar o agendamento...
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
        // Horário de funcionamento: 9h às 18h
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        long slotDurationInMinutes = 60;

        return Stream.iterate(startTime, time -> time.plusMinutes(slotDurationInMinutes))
                .limit((long) (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / (slotDurationInMinutes * 60))
                .map(time -> new ScheduledTime(time, time.plusMinutes(slotDurationInMinutes)));
    }
}