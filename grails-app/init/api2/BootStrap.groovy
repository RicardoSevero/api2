package api2

import groovy.sql.Sql

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BootStrap {

    def CidadeService cidadeService
    def FuncionarioService funcionarioService
    def ReajusteSalarioService reajusteSalarioService

    def init = { servletContext ->
        databaseConnection()
        createMockup()
    }

    def createMockup() {

        def cidade1 = new Cidade(nome: "Sapiranga")
        def cidade2 = new Cidade(nome: "Campo Bom")
        def cidade3 = new Cidade(nome: "Novo Hamburgo")

        cidadeService.save(cidade1)
        cidadeService.save(cidade2)
        cidadeService.save(cidade3)

        log.info("Cidades criadas com sucesso!")

        def funcionario1 = new Funcionario(nome:"Ricardo", cidade: cidade1)
        def funcionario2 = new Funcionario(nome:"Luan", cidade: cidade2)
        def funcionario3 = new Funcionario(nome:"Vinícius", cidade: cidade3)

        funcionarioService.save(funcionario1)
        funcionarioService.save(funcionario2)
        funcionarioService.save(funcionario3)

        log.info("Funcionários criados com sucesso!")

        def reajuste1 = new ReajusteSalario(
                valorSalario: 5000,
                dataReajuste: LocalDate.parse("01/01/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                funcionario: funcionario1)

        def reajuste2 = new ReajusteSalario(
                valorSalario: 6000,
                dataReajuste: LocalDate.parse("24/05/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                funcionario: funcionario2)

        def reajuste3 = new ReajusteSalario(
                valorSalario: 7000,
                dataReajuste: LocalDate.parse("12/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                funcionario: funcionario3
        )

        reajusteSalarioService.save(reajuste1)
        reajusteSalarioService.save(reajuste2)
        reajusteSalarioService.save(reajuste3)

        log.info("Reajustes criados com sucesso!")
    }

    def databaseConnection() {
        def username = "aluno18"
        def password = "aluno18"
        def jdbcUrl = "jdbc:oracle:thin:@insoft-lnx4.insoft.local:1521:desenv01"

        try {
            def sql = Sql.newInstance(jdbcUrl, username, password, "oracle.jdbc.OracleDriver")

            if (sql.connection.isValid(5)){
                log.info("Conexão Efetuada com sucesso!")
            }
            else{
                log.warn("Conexão inválida!")
            }
        }catch (Exception e){
            log.error("Conexão com erro: ${e.message}")
        }
    }

    def destroy = {
    }
}