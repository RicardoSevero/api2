package api2

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Service(Cidade)
class CidadeService {

    Cidade get(Long id) {
        Cidade.findById(id)
    }

    List<Cidade> list() {
        Cidade.list()
    }

    Long count() {
        Cidade.count()
    }

    @Transactional
    Cidade delete(Long id) {
        Cidade.findById(id)?.delete()
    }

    @Transactional
    Cidade save(Cidade cidade) {
        cidade.save()
    }
}
