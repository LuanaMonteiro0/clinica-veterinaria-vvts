package br.ifsp.clinicaveterinaria.scheduling.infra.mappers;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.VeterinarianJpaEntity;

public final class VeterinarianMapper {

    private VeterinarianMapper() {}

    public static VeterinarianJpaEntity toEntity(Veterinarian v) {
        String crmv = v.getCrmv().getCrmv();
        String phone = v.getPhone().getPhone();
        return new VeterinarianJpaEntity(crmv, v.getName(), v.getEmail(), phone);
    }

    public static Veterinarian toDomain(VeterinarianJpaEntity e) {

        Phone phone = new Phone(e.getPhone());

        return new Veterinarian(
                e.getName(),
                e.getEmail(),
                new CRMV(e.getCrmv()),
                phone
        );
    }
}
