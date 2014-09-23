class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        
        
      ///  "/"(controller:"loginRegister", action:"index")
        "/"(controller:"login", action:"auth")
        "500"(view:'/error')
	}
}
