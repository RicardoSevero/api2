package api2

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?"()

        "/cidade"(controller: "cidade", action: "list")
        "/funcionario"(controller: "funcionario", action: "list")
        "/reajusteSalario"(controller: "reajusteSalario", action: "list")
    }
}
