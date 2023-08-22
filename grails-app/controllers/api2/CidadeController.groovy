package api2

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class CidadeController {

    CidadeService cidadeService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", list: "GET", get: "GET"]

    /*********************************************************************************************/
    /***********************************MÉTODO LIST***********************************************/
    /*********************************************************************************************/

    def list() {
        respond cidadeService.list()
    }

    /*********************************************************************************************/
    /************************************MÉTODO GET***********************************************/
    /*********************************************************************************************/

    def get(Long id) {
        def cidade = cidadeService.get(id)

        if (!cidade) {
            respond([message: "Cidade não encontrada!"], status: 404)
            return
        }

        respond(cidade)
    }

    /*********************************************************************************************/
    /************************************MÉTODO SAVE**********************************************/
    /*********************************************************************************************/

    @Transactional
    def save(Cidade cidade) {
        if (cidade == null) {
            respond([message: "Dados inválidos!"], status: 404)
            return
        }
        if (cidade.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cidade.errors
            return
        }
        try {
            cidadeService.save(cidade)
        } catch (ValidationException e) {
            respond cidade.errors
            return
        }

        respond cidade, [status: CREATED, view:"show"]
    }

    /*********************************************************************************************/
    /***********************************MÉTODO UPDATE*********************************************/
    /*********************************************************************************************/

    @Transactional
    def update(Cidade cidade) {
        if (cidade == null || cidadeService.get(cidade.id) == null) {
            respond([message: "Cidade não encontrada!"], status: 404)
            return
        }
        if (cidade.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cidade.errors
            return
        }

        try {
            cidadeService.save(cidade)
        } catch (ValidationException e) {
            respond cidade.errors
            return
        }
        respond([message: "Cidade alterada com sucesso!"], status: 200)
    }

    /*********************************************************************************************/
    /***********************************MÉTODO DELETE*********************************************/
    /*********************************************************************************************/

    @Transactional
    def delete(Long id) {
        if (id == null || cidadeService.get(id) == null) {
            respond([message: "Não há Cidade cadastrada com este id!"], status: 404)
            return
        }

        cidadeService.delete(id)

        respond([message: "Cidade deletada com sucesso!"], status: 201)
    }
}