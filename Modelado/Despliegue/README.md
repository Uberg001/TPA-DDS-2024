# Justificaciones/Observaciones
## Propuesta monolítica
- El broker no se comunicaría con el cliente (aplica también para la otra propuesta):
  - Para el caso de los sensores, son los mismos los que -situados desde la heladera- toman el 
rol de publicadores. Los sensores publican en el broker y una región particular del sistema se suscribiría a los tópicos correspondientes para
producir ciertas acciones. El sensor de temperatura, por ejemplo, publicaría una temperatura fuera de rango. Este suceso haría que la heladera
asociada quede fuera de funcionamiento.
  - Por otra parte, para las colaboraciones que posean algún tipo de interacción con las heladeras, el cliente no tiene ninguna interacción directa
con el broker. Tomando de ejemplo la donación de una vianda, un colaborador da de alta una solicitud, lo cual supone una interacción con el controller
correspondiente y el resto de capas subyacentes. En algún instante se genera una instancia de la solicitud, entidad inmanente de nuestro dominio
que tiene asociada la responsabilidad de publicar el mensaje en el tópico correspondiente.
- Por el lado de los servicios externos, se dispone de un servicio que deduce las coordenadas a partir de una cadena y, además, el servicio
del grupo 5. Si bien este último no se despliega por una cuestión de practicidad, lo cierto es que hay lógica y vistas ad hoc, por lo
que se tendría en cuenta como si estuviera desplegado aunque no lo esté (este item tambien aplica para la otra propuesta).    

## Propuesta basada en microservicios
- Corrección: Faltan las eventuales comunicaciones entre microservicios. Serían mediante el protocolo HTTP y, eventualmente, podrían integrarse mediante gRPC.
- Se dispone de un API Gateway y un Service Registry, componentes característicos de una propuesta basada en este estilo arquitectónico.
- Asimismo, se cuenta con cuatro microservicios: Personas , Colaboraciones, Heladeras y Reportes; cada uno con su respectiva base de datos.
- Se decide "Personas" por sobre "Colaboradores" porque el microservicio no solo pretende abarcar a los colaboradores, sino también a los
técnicos y a las personas vulnerables. Si bien estas últimas están relacionadas con un tipo particular de colaboración, posiblemente
resulte más apropiado para el contexto que se sitúe en el microservicio de las personas, basicamente porque la persona vulnerable es una persona 🙃.
- Se optó por esta solución concreta porque sí se analiza el dominio inicial en conjunto con los requerimientos, resulta bastante trivial
la deducción de esos "subdominios". La gran mayoría de las entidades se encuentran relacionadas a las heladeras, las personas, o bien, las colaboraciones;
aunque hay ciertas excepciones como los reportes que son transversales a los tres contextos anteriores, por lo que se lo excluye en un microservicio nuevo.
 
  
