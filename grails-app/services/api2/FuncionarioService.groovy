package api2

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Service(Funcionario)
class FuncionarioService {

    Funcionario get(Long id) {
        Funcionario.findById(id)
    }

    List<Funcionario> list() {
        Funcionario.list()
    }

    Long count() {
        Funcionario.count()
    }

    @Transactional
    Funcionario delete(Long id) {
        Funcionario.findById(id)?.delete()
    }

    @Transactional
    Funcionario save(Funcionario funcionario) {
        funcionario.save()
    }
}