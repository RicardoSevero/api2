package api2

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class FuncionarioController {

    FuncionarioService funcionarioService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", list: "GET", get: "GET"]

    /*********************************************************************************************/
    /***********************************MÉTODO LIST***********************************************/
    /*********************************************************************************************/

    def list() {

        List<Funcionario> funcionarios = funcionarioService.list()
        List<FuncionarioDTO> funcionariosDTO = funcionarios.collect{funcionario -> new FuncionarioDTO(funcionario)}

        respond funcionariosDTO
    }

    /*********************************************************************************************/
    /************************************MÉTODO GET***********************************************/
    /*********************************************************************************************/

    def get(Long id) {
        def funcionario = funcionarioService.get(id)

        if (funcionario){
            def funcionarioDTO = new FuncionarioDTO(funcionario)
            respond funcionarioDTO
        } else {
            respond([message: "Funcionário não encontrado!"], status: 404)
            return
        }
    }

    /*********************************************************************************************/
    /************************************MÉTODO SAVE**********************************************/
    /*********************************************************************************************/

    @Transactional
    def save(Funcionario funcionario) {
        if (funcionario == null) {
            respond([message: "Dados inválidos!"], status: 404)
            return
        }
        if (funcionario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond funcionario.errors
            return
        }

        try {
            funcionarioService.save(funcionario)
        } catch (ValidationException e) {
            respond funcionario.errors
            return
        }

        def funcionarioDTO = new FuncionarioDTO(funcionario)

        respond funcionarioDTO
    }

    /*********************************************************************************************/
    /***********************************MÉTODO UPDATE*********************************************/
    /*********************************************************************************************/

    @Transactional
    def update(Funcionario funcionario) {
        if (funcionario == null || funcionarioService.get(funcionario.id) == null) {
            respond([message: "Funcionário não encontrado!"], status: 404)
            return
        }

        if (funcionario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond funcionario.errors
            return
        }

        try {
            funcionarioService.save(funcionario)
        } catch (ValidationException e) {
            respond funcionario.errors
            return
        }
        respond([message: "Funcionário alterado com sucesso!"], status: 200)
    }

    /*********************************************************************************************/
    /************************************MÉTODO DELETE********************************************/
    /*********************************************************************************************/

    @Transactional
    def delete(Long id) {
        if (id == null || funcionarioService.get(id) == null) {
            respond([message: "Não há Funcionário cadastrado com este id!"], status: 404)
            return
        }

        funcionarioService.delete(id)

        respond([message: "Funcionário deletado com sucesso!"], status: 201)
    }
}