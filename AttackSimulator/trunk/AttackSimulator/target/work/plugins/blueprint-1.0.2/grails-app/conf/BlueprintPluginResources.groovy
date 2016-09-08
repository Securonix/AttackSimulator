// Define the resources for exposure via Resources plugin
modules = {
    blueprint {
        resource id:'main', url:[plugin:'blueprint', dir:'css/blueprint', file:'screen.css'], 
            attrs:[media:'screen, projection']
        resource id:'ie', url:[plugin:'blueprint', dir:'css/blueprint', file:'ie.css'], attrs:[media:'screen,projection'],
            wrapper: { s -> "<!--[if lt IE 8]>$s<![endif]-->" }
    }

    'blueprint-print' {
        dependsOn 'blueprint'
        resource url:[plugin:'blueprint', dir:'css/blueprint', file:'print.css'], attrs:[media:'print']
    }

    'blueprint-buttons' {
        dependsOn 'blueprint'
        resource url:[plugin:'blueprint', dir:'css/blueprint/plugins/buttons', file:'screen.css'], attrs:[media:'screen, projection']
    }

    'blueprint-fancy-type' {
        dependsOn 'blueprint'
        resource url:[plugin:'blueprint', dir:'css/blueprint/plugins/fancy-type', file:'screen.css'], attrs:[media:'screen, projection']
    }

    'blueprint-link-icons' {
        dependsOn 'blueprint'
        resource url:[plugin:'blueprint', dir:'css/blueprint/plugins/link-icons', file:'screen.css'], attrs:[media:'screen, projection']
    }

    'blueprint-rtl' {
        dependsOn 'blueprint'
        resource url:[plugin:'blueprint', dir:'css/blueprint/plugins/rtl', file:'screen.css'], attrs:[media:'screen, projection']
    }
}