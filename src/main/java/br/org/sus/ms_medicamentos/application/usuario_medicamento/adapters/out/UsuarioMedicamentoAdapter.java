package br.org.sus.ms_medicamentos.application.usuario_medicamento.adapters.out;

import br.org.sus.ms_medicamentos.application.usuario_medicamento.ports.out.UsuarioMedicamentoPortOut;
import br.org.sus.ms_medicamentos.domain.Medicamento;
import br.org.sus.ms_medicamentos.domain.Usuario;
import br.org.sus.ms_medicamentos.domain.UsuarioMedicamento;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaMedicamentoRepository;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaUsuarioMedicamentoRepository;
import br.org.sus.ms_medicamentos.infra.datasource.jpa.repository.JpaUsuarioRepository;
import br.org.sus.ms_medicamentos.infra.mapper.MedicamentoMapper;
import br.org.sus.ms_medicamentos.infra.mapper.UsuarioMapper;
import br.org.sus.ms_medicamentos.infra.mapper.UsuarioMedicamentoMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsuarioMedicamentoAdapter implements UsuarioMedicamentoPortOut {

    private final JpaUsuarioRepository usuarioRepository;
    private final JpaMedicamentoRepository medicamentoRepository;
    private final JpaUsuarioMedicamentoRepository usuarioMedicamentoRepository;

    public UsuarioMedicamentoAdapter(JpaUsuarioRepository usuarioRepository, JpaMedicamentoRepository medicamentoRepository, JpaUsuarioMedicamentoRepository usuarioMedicamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioMedicamentoRepository = usuarioMedicamentoRepository;
    }

    @Override
    public Usuario buscarUsuarioPorId(UUID id) {
        return UsuarioMapper.toDomain(usuarioRepository.findById(id).orElseThrow());
    }

    @Override
    public Medicamento buscarMedicamentoPorId(UUID id) {
        return MedicamentoMapper.toDomain(
                medicamentoRepository.findById(id)
                        .orElseThrow()
        );
    }

    @Override
    public UsuarioMedicamento salvar(UsuarioMedicamento usuarioMedicamento) {
        return UsuarioMedicamentoMapper.toDomain(
                usuarioMedicamentoRepository.save(
                        UsuarioMedicamentoMapper.toEntity(usuarioMedicamento)
                )
        );
    }
}
