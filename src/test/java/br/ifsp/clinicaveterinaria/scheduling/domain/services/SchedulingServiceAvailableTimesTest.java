package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledDate;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledTime;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SchedulingServiceAvailableTimesTest {

    static class FakeAppointment {
        private final Veterinarian vet;
        private final ScheduledDate scheduledDate;
        private final ScheduledTime scheduledTime;

        FakeAppointment(Veterinarian vet, LocalDate date, LocalTime startTime, LocalTime endTime) {
            this.vet = vet;
            this.scheduledDate = new ScheduledDate(date);
            this.scheduledTime = new ScheduledTime(startTime, endTime);
        }

        public Veterinarian getVet() { return vet; }
        public ScheduledDate getScheduledDate() { return scheduledDate; }
        public ScheduledTime getScheduledTime() { return scheduledTime; }
    }

    static class AppointmentRepoFake implements Repository<Object, Object> {
        private final List<FakeAppointment> data;

        public AppointmentRepoFake(List<FakeAppointment> data) {
            this.data = data;
        }

        public List<FakeAppointment> findByVetAndDate(Veterinarian vet, LocalDate date) {
            return data.stream()
                    .filter(a -> a.getVet().equals(vet) && a.getScheduledDate().getScheduledDate().equals(date))
                    .collect(Collectors.toList());
        }

        @Override public void add(Object aggregate) {}
        @Override public void update(Object aggregate) {}
        @Override public void deleteById(Object id) {}
        @Override public Optional<Object> findById(Object id) { return Optional.empty(); }
        @Override public boolean existsById(Object id) { return false; }
    }

    @Test
    void shouldListAvailableTimesForSelectedDay() {
        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));
        LocalDate selectedDate = LocalDate.of(2025, 10, 20);

        List<FakeAppointment> bookedAppointments = List.of(
                new FakeAppointment(vet, selectedDate, LocalTime.of(10, 0), LocalTime.of(11, 0))
        );
        AppointmentRepoFake appointmentRepo = new AppointmentRepoFake(bookedAppointments);
        SchedulingService service = new SchedulingService(null, appointmentRepo);

        List<ScheduledTime> availableTimes = service.findAvailableTimesFor(vet, new ScheduledDate(selectedDate));

        assertThat(availableTimes).hasSize(8);

        assertThat(availableTimes).doesNotContain(new ScheduledTime(LocalTime.of(10, 0), LocalTime.of(11, 0)));

        assertThat(availableTimes).containsExactly(
                new ScheduledTime(LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new ScheduledTime(LocalTime.of(11, 0), LocalTime.of(12, 0)),
                new ScheduledTime(LocalTime.of(12, 0), LocalTime.of(13, 0)),
                new ScheduledTime(LocalTime.of(13, 0), LocalTime.of(14, 0)),
                new ScheduledTime(LocalTime.of(14, 0), LocalTime.of(15, 0)),
                new ScheduledTime(LocalTime.of(15, 0), LocalTime.of(16, 0)),
                new ScheduledTime(LocalTime.of(16, 0), LocalTime.of(17, 0)),
                new ScheduledTime(LocalTime.of(17, 0), LocalTime.of(18, 0))
        );
    }
}