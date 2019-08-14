import com.google.gson.Gson;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;

import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;


public class Server {

    static MockServerClient mock = startClientAndServer(8081);

    public static void consulta(String method,String path,int statusCode,String content,String body,long delay) {

        mock.when(
                request()
                        .withMethod(method)
                        .withPath(path)
        ).respond(
                response()
                .withStatusCode(statusCode)
                .withHeader(new Header("Content-Type",content))
                .withBody(body)
                .withDelay(new Delay(TimeUnit.MILLISECONDS,delay))
        );

    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        Site site = new Site("MLA","Argentina");

        consulta("GET","/sites/.*",200,"application/json", gson.toJson(site),5000);

    }

}
