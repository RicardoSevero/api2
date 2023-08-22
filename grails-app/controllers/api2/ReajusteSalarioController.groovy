package api2

import grails.validation.ValidationException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import static org.springframework.http.HttpStatus.OK
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ReajusteSalarioController {

    private static LocalDate parseData(String dataReajuste) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.parse(dataReajuste, dateFormatter)
    }

    ReajusteSalarioService reajusteSalarioService
    FuncionarioService funcionarioService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", list: "GET", get: "GET"]

    /*********************************************************************************************/
    /***********************************MÉTODO LIST***********************************************/
    /*********************************************************************************************/

    def list() {
        List<ReajusteSalario> reajusteSalarios = reajusteSalarioService.list()
        List<ReajusteSalarioDTO> reajusteSalarioDTO = reajusteSalarios.collect{reajusteSalario -> new ReajusteSalarioDTO(reajusteSalario)}

        respond reajusteSalarioDTO
    }

    /*********************************************************************************************/
    /************************************MÉTODO GET***********************************************/
    /*********************************************************************************************/

    def get(Long id) {
        def reajusteSalario = reajusteSalarioService.get(id)

        if (reajusteSalario){
            def reajusteSalarioDTO = new ReajusteSalarioDTO(reajusteSalario)
            respond reajusteSalarioDTO
        } else {
            respond([message: "Reajuste não encontrado!"], status: 404)
            return
        }
    }

    /*********************************************************************************************/
    /************************************MÉTODO SAVE**********************************************/
    /*********************************************************************************************/

    @Transactional
    def save() {
        def requestBody = request.JSON

        def funcionario = funcionarioService.get(requestBody.funcionario)

        if (!funcionario){
            respond([message: "Funcionário não encontrado!"], status: 404)
            return
        }

        if (!requestBody) {
            respond([message: "Dados inválidos!"], status: 404)
            return
        }

        def reajusteSalario = new ReajusteSalario(
                valorSalario: requestBody.valorSalario,
                dataReajuste: parseData(requestBody.dataReajuste),
                funcionario: requestBody.funcionario
        )

        if (reajusteSalario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond reajusteSalario.errors
            return
        }

        try {
            reajusteSalarioService.save(reajusteSalario)
        } catch (ValidationException e) {
            respond reajusteSalario.errors
            return
        }

        def reajusteSalarioDTO = new ReajusteSalarioDTO(reajusteSalario)
        respond reajusteSalarioDTO
    }

    /*********************************************************************************************/
    /***********************************MÉTODO UPDATE*********************************************/
    /*********************************************************************************************/

    @Transactional
    def update(Long id) {
        def requestBody = request.JSON

        def reajusteSalario = reajusteSalarioService.get(id)
        def dataReajuste = requestBody.dataReajuste
        def funcionarioId = requestBody.funcionario
        def valorSalario = requestBody.valorSalario

        if (id == null || !reajusteSalario) {
            respond([message: "Reajuste não encontrado!"], status: 404)
            return
        }

        if (funcionarioId){
            def funcionario = funcionarioService.get(funcionarioId)

            if (!funcionario) {
                respond([message: "Funcionário não encontrado!"], status: 404)
                return
            }
            reajusteSalario.funcionario = funcionario
        }

        if (dataReajuste){
            reajusteSalario.dataReajuste = parseData(dataReajuste)
        }

        if (valorSalario){
            reajusteSalario.valorSalario = new BigDecimal(valorSalario)
        }

        if (reajusteSalario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond reajusteSalario.errors
            return
        }

        try {
            reajusteSalarioService.save(reajusteSalario)
        } catch (ValidationException e) {
            respond reajusteSalario.errors
            return
        }

        def reajusteSalarioDTO = new ReajusteSalarioDTO(reajusteSalario)
        respond reajusteSalarioDTO, [status: OK]
    }

    /*********************************************************************************************/
    /************************************MÉTODO DELETE********************************************/
    /*********************************************************************************************/

    @Transactional
    def delete(Long id) {
        if (id == null || reajusteSalarioService.get(id) == null) {
            respond([message: "Não há Reajuste cadastrado com este id!"], status: 404)
            return
        }

        reajusteSalarioService.delete(id)

        respond([message: "Reajuste deletado com sucesso!"], status: 201)
    }
}