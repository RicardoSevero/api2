package api2

class Funcionario {

    String nome

    static belongsTo = [cidade: Cidade]
    static hasMany = [reajusteSalario:ReajusteSalario]

    static mapping = {
        id generator: 'increment'
        version(false)
    }

    static constraints = {
        nome nullable: false, maxSize:50
        cidade nullable: false
        id unique: true
    }
}
