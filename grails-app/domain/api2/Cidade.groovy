package api2

class Cidade {

    String nome

    static hasMany = [funcionario:Funcionario]

    static mapping = {
        id generator: 'increment'
        version(false)
    }

    static constraints = {
        nome nullable: false, maxSize:50
        id unique: true
    }

}
