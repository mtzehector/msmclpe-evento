package mx.gob.imss.dpes.evento.endpoint;

import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.evento.exception.BitacoraInterfazTopicException;
import mx.gob.imss.dpes.interfaces.bitacora.model.BitacoraInterfaz;

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
import java.util.Date;
import java.util.logging.Level;

@JMSDestinationDefinitions(
    value = {
        @JMSDestinationDefinition(
            name = "java:/topic/MCLPEBitacoraInterfaz",
            interfaceName = "javax.jms.Topic",
            destinationName = "MCLPEBitacoraInterfaz"
        )
    }
)

@ApplicationScoped
@Path("/bitacora/interfaz")
public class BitacoraInterfazTopicEndPoint extends BaseGUIEndPoint<BitacoraInterfaz, BitacoraInterfaz, BitacoraInterfaz> {

  @Inject private JMSContext context;
  
  @Resource(lookup = "java:/topic/MCLPEBitacoraInterfaz")
  private Topic topic;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(BitacoraInterfaz bitacoraInterfaz) {
      try {
        if(bitacoraInterfaz.getAltaRegistro() == null)
          bitacoraInterfaz.setAltaRegistro(new Date());

        Message<BitacoraInterfaz> mensaje = new Message<BitacoraInterfaz>(bitacoraInterfaz);
        context.createProducer().send(topic, mensaje);
        return Response.ok().build();
      } catch (Exception e) {
        log.log(Level.SEVERE,
            "BitacoraInterfaceTopicEndPoint.create bitacoraInterfaz = [" + bitacoraInterfaz + "]", e);
      }
      return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, new BitacoraInterfazTopicException(), null));
    }
    
}
