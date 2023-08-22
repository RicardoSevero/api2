package api2

import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

class ReajusteSalarioDTO {

    Long id
    Long idFuncionario
    String nomeFuncionario
    String dataReajuste
    String valorSalario

    def formato = new DecimalFormat("#.00")

    ReajusteSalarioDTO(ReajusteSalario reajusteSalario){
        this.id = reajusteSalario.id
        this.valorSalario = formato.format(reajusteSalario.valorSalario)
        this.dataReajuste = reajusteSalario.dataReajuste.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        this.idFuncionario = reajusteSalario.funcionario.id
        this.nomeFuncionario = reajusteSalario.funcionario.nome
    }

    static constraints = {
    }
}
