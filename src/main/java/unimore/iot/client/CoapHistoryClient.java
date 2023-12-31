package unimore.iot.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;
import unimore.iot.utilities.PlanHistory;

import java.io.IOException;

public class CoapHistoryClient {
    public static String run(int serverPort) {
        String passedResource = "history";

        //  Set Endpoint with the right port
        String COAP_ENDPOINT = "coap://127.0.0.1:" + serverPort + "/";

        CoapClient coapClient = new CoapClient(COAP_ENDPOINT + passedResource);

        //  Request
        Request request = new Request(CoAP.Code.GET);
        request.setOptions(new OptionSet().setAccept(MediaTypeRegistry.APPLICATION_SENML_JSON));
        request.setConfirmable(true);

        try
        {
            //  Response
            CoapResponse coapResp = coapClient.advanced(request);

            String prettyPrint = "==[ GET (history) ]====================================================\n";
            prettyPrint += PlanHistory.getPlanCounter();
            prettyPrint += Utils.prettyPrint(coapResp);
            prettyPrint = prettyPrint.replace("},{","},\n{");
            prettyPrint = prettyPrint.replace("],[","],\n[");

            System.out.println(prettyPrint);
            return prettyPrint;
        }
        catch (ConnectorException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
