package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledDate;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SchedulingServiceAvailableDaysTest {

    static class FakeAppointment {
        private final Veterinarian vet;
        private final ScheduledDate scheduledDate;
        FakeAppointment(Veterinarian vet, LocalDate date) {
            this.vet = vet;
            this.scheduledDate = new ScheduledDate(date);
        }
        public Veterinarian getVet() { return vet; }
        public ScheduledDate getScheduledDate() { return scheduledDate; }
    }

    static class AppointmentRepoFake implements Repository {
        private final List<FakeAppointment> data = new ArrayList<>();
        public void add(FakeAppointment a) { data.add(a); }

        public List<FakeAppointment> findByVetAndDateBetween(Veterinarian vet, LocalDate start, LocalDate end) {
            return data.stream()
                    .filter(a -> a.getVet().equals(vet))
                    .filter(a -> {
                        LocalDate d = a.getScheduledDate().getScheduledDate();
                        return !d.isBefore(start) && !d.isAfter(end);
                    })
                    .toList();
        }

        @Override
        public void add(Object aggregate) {

        }

        @Override
        public void update(Object aggregate) {

        }

        @Override
        public void deleteById(Object o) {

        }

        @Override
        public Optional findById(Object o) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Object o) {
            return false;
        }
    }

    @Test
    void shouldReturnDaysWithoutAppointmentsInRange() {
        Veterinarian vet = new Veterinarian("Dra. Maria", "maria@gmail.com", new CRMV("CRMV-SP 0223"), new Phone("(12) 22345-2345"));

        AppointmentRepoFake appointmentRepo = new AppointmentRepoFake();
        appointmentRepo.add(new FakeAppointment(vet, LocalDate.of(2025, 10, 10))); // ocupado
        appointmentRepo.add(new FakeAppointment(vet, LocalDate.of(2025, 10, 12))); // ocupado

        SchedulingService service = new SchedulingService(null, appointmentRepo);

        LocalDate start = LocalDate.of(2025, 10, 10);
        LocalDate end   = LocalDate.of(2025, 10, 13);

        List<ScheduledDate> obtained = service.findAvailableDaysFor(vet, start, end);

        assertThat(obtained)
                .containsExactlyInAnyOrder(
                        new ScheduledDate(LocalDate.of(2025, 10, 11)),
                        new ScheduledDate(LocalDate.of(2025, 10, 13))
                );
    }
}
