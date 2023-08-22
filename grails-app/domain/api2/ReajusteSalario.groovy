package api2

import com.fasterxml.jackson.annotation.JsonFormat

import java.time.LocalDate

class ReajusteSalario {

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataReajuste

    BigDecimal valorSalario

    static belongsTo = [funcionario: Funcionario]

    static mapping = {
        id generator: 'increment'
        version(false)
    }

    static constraints = {
        dataReajuste nullable: false
        valorSalario nullable: false, maxSize: 6.2
        funcionario nullable: false
        id unique: true
    }
}
