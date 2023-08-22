package api2

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Service(ReajusteSalario)

class ReajusteSalarioService {

    ReajusteSalario get(Long id) {
        ReajusteSalario.findById(id)
    }

    List<ReajusteSalario> list() {
        ReajusteSalario.list()
    }

    Long count() {
        ReajusteSalario.count()
    }

    @Transactional
    ReajusteSalario delete(Long id) {
        ReajusteSalario.findById(id)?.delete()
    }

    @Transactional
    ReajusteSalario save(ReajusteSalario reajusteSalario) {
        reajusteSalario.save()
    }
}
