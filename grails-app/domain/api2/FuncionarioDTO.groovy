package api2

class FuncionarioDTO {

    Long id
    Long cidadeId
    String nome
    String nomeCidade

    FuncionarioDTO(Funcionario funcionario){
        this.id = funcionario.id
        this.nome = funcionario.nome
        this.cidadeId = funcionario.cidade.id
        this.nomeCidade = funcionario.cidade.nome
    }

    static constraints = {
    }
}
