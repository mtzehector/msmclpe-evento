/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.evento.endpoint;

import java.util.logging.Level;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Topic;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.evento.model.Evento;

/**
 *
 * @author antonio
 */
@JMSDestinationDefinitions(
    value = {
        @JMSDestinationDefinition(
            name = "java:/topic/MCLPETopic",
            interfaceName = "javax.jms.Topic",
            destinationName = "MCLPETopic"
        )
    }
)

@ApplicationScoped
@Path("/evento")
public class EventoEndPoint extends BaseGUIEndPoint<Evento, Evento, Evento> {

  @Inject private JMSContext context;
  
  @Resource(lookup = "java:/topic/MCLPETopic")
  private Topic topic;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(Evento evento) throws BusinessException {
      log.log(Level.INFO, "Creando evento {0}", evento);
      Message<Long> mensaje = new Message<>( evento.getId(),
            evento.getEvent());
      context.createProducer().send( topic, mensaje );
      log.log(Level.INFO, "Evento creado {0}", evento);
      return Response.ok().build();
    }
    
}
