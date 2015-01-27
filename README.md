[1]: http://download.jboss.org/wildfly/8.2.0.Final/wildfly-8.2.0.Final.zip
[2]: https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo
[3]: http://es.wikipedia.org/wiki/M%C3%B3nada_%28programaci%C3%B3n_funcional%29
[4]: https://github.com/ftrianakast/vision_software_backend/raw/master/vision.war
[5]: https://raw.githubusercontent.com/ftrianakast/vision_software_backend/master/photos/all.png
[6]: https://raw.githubusercontent.com/ftrianakast/vision_software_backend/master/photos/some.png
[7]: https://raw.githubusercontent.com/ftrianakast/vision_software_backend/master/photos/specific.png

# Intro

Para la resolución de la prueba se utilizo __Java 8__. Es importante que se despliegue el __war__ sobre un servidor [__JBOSS Widifly 8.x__][1] que soporta precisamente __Java 8__; evidentemente de lo contrario el despliegue será erróneo. Sin embargo para más comodidad los servicios de validación de reglas se encuentran expuestos sobre la ip: __http://54.200.93.241:8080__. 

Sin embargo si quiere probarlo en un servidor local, aquí encuentra [el war de la aplicación][4].


# Herramientas utilizadas

1. [JBOSS Widifly 8.x][1]: Para el despliegue de la aplicación.
2. __Java 8__: Se utilizan __defaults__, __lambdas__ y __Optionals__ extensivamente.
3. __JEE 6__: Se utilizaron __inyecciones de dependencia__ y anotaciones propias de esta versión del __Java empresarial__.
4. [__Advanced REST Client Para Chrome__][2]: En vez de implementar un cliente front-end (teniendo en cuenta que la prueba es de __backend__) se puede utilizar un cliente existente. Más adelante se explicará como usarlo para probar los servicios __REST__.
5. __JUnit__: Para las pruebas unitarias.
6. __Maven__: Para la descripción del proyecto.
7. __REST__: Se siguió este estilo arquitectural para la implementación de los servicios __REST__ requeridos.


# Endpoints

Si bien la aplicación valida reglas; hay tres sabores para validar estas reglas:

-  __POST__ http://54.200.93.241:8080:8080/validation/rules/all:

Se prueban todas las reglas existentes para el proceso __XML__ suministrado

- __POST__ http://54.200.93.241:8080/validation/rules?names=STYLE_115,STYLE_122

Se prueban las reglas indicadas en el __Query Parameter__ names. Se deben separar por comas como está en el ejemplo.

- __POST__ http://54.200.93.241:8080/validation/rules/{ruleName}

Se prueban la regla indicada sobre el proceso.

En 2 y 3 los nombres de las reglas disponibles deben ser como se indican a continuación:

Nombre que se debe usar| Descripción
---------------------- | -----------
BPMN_102  | All flow objects other than end events and compensating activities must have an outgoing sequence flow, if the process level includes any start or end events.
STYLE_104 | Two activities in the same process should not have the same name.
STYLE_115 | A throwing intermediate event should be labeled.
STYLE_122 | A catching Message event should have incoming message flow.
STYLE_123 | A throwing Message event should have outgoing message flow


# Uso

Para consumir los servicios REST utilice [__Advanced REST Client__][2] siguiendo los ejemplos que se indican a continuación:

1. __POST__ http://54.200.93.241:8080:8080/validation/rules/all: 

Para consumir todas las reglas abra el [__Advanced REST__][2] en Chrome. Luego tendrá que hacer algo como esto:

![All][5]


Fíjarse en que se agrega un File __.xpdl__ con el nombre __file__; de lo contrario obtendrá un error. SIEMPRE UTILICE EL NOMBRE __file__ para todos los archivos que suba.

Usted obtendrá un JSON indicando los errores teniendo en cuenta todas las reglas. Esta respuesta será recurrente en los otros dos servicios por lo que se omitirá dicha respuesta en esos apartados.


    [{
        "specificErrorMessages": [{
            "elementId": "fb4d6618-fce1-491e-95ea-a61c945aa510",
            "message": "There was an error in the event because: All flow objects other than end events and compensating activities must have an outgoing sequence flow, if the process level includes any start or end events.",
            "elementName": "A"
        }, {
            "elementId": "be9fa0a8-8b6b-479e-8c60-832f8b0e469b",
            "message": "There was an error in the event because: All flow objects other than end events and compensating activities must have an outgoing sequence flow, if the process level includes any start or end events.",
            "elementName": "C"
        }, {
            "elementId": "bb6f3a3e-acff-48a8-bafa-c406b2114c2b",
            "message": "There was an error in the event because: All flow objects other than end events and compensating activities must have an outgoing sequence flow, if the process level includes any start or end events.",
            "elementName": "S"
        }, {
            "elementId": "c18b9d3a-e828-43d7-bda9-794c8fc79e7a",
            "message": "There was an error in the event because: All flow objects other than end events and compensating activities must have an outgoing sequence flow, if the process level includes any start or end events.",
            "elementName": ""
        }, {
            "elementId": "6082a312-32d1-44bd-90a0-a055fd22f1b1",
            "message": "There was an error in the event because: All flow objects other than end events and compensating activities must have an outgoing sequence flow, if the process level includes any start or end events.",
            "elementName": ""
        }]
    }, {
        "specificErrorMessages": [{
            "elementId": "c18b9d3a-e828-43d7-bda9-794c8fc79e7a",
            "message": "There was an error in the event because: A throwing intermediate event should be labeled.",
            "elementName": ""
        }, {
            "elementId": "6082a312-32d1-44bd-90a0-a055fd22f1b1",
            "message": "There was an error in the event because: A throwing intermediate event should be labeled.",
            "elementName": ""
        }]
    }, {
        "specificErrorMessages": [{
            "elementId": "c18b9d3a-e828-43d7-bda9-794c8fc79e7a",
            "message": "There was an error in the event because: A catching Message event should have incoming message flow.",
            "elementName": ""
        }, {
            "elementId": "6082a312-32d1-44bd-90a0-a055fd22f1b1",
            "message": "There was an error in the event because: A catching Message event should have incoming message flow.",
            "elementName": ""
        }]
    }, {
        "specificErrorMessages": [{
            "elementId": "c18b9d3a-e828-43d7-bda9-794c8fc79e7a",
            "message": "There was an error in the event because: A throwing Message event should have outgoing message flow",
            "elementName": ""
        }]
    }]


2. __POST__ http://54.200.93.241:8080:8080/validation/rules?names=STYLE_122,STYLE_123: 

Este servicio valida las reglas indicadas en names. En el caso del ejemplo se validan las reglas STYLE_122 y STYLE_123. Este seguro de utilizar los nombres indicados con anterioridad.

![Some][6]


3. __POST__ http://54.200.93.241:8080:8080/validation/rules/BPMN_102

Este servicio valida la regla indicada en el Path Param de la URL. En este caso se valida la regla __BPMN_102__. Reemplazarla a conveniencia.

![Specific][7]


# Decisiones de diseño de software

Usted podrá encontrar entre otras una __Factoría__ para crear las reglas, un __Agregador__ para validar múltiples reglas, expresiones __lambda__ que permiten simular mejor el comportamiento del patrón __Command__. Del mismo modo se usan __Optionals__ que son una [__Mónada__][3] (en programación funcional) que vendría a hacer el equivalente al patrón __Decorador__ pero mucho más simple.

Finalmente usted podrá encontrar que el parseador expresa buen __SRP__.



