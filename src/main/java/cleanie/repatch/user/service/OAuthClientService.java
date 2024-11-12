package cleanie.repatch.user.service;

import cleanie.repatch.user.client.OAuthApiClient;
import cleanie.repatch.user.model.OAuthProvider;
import cleanie.repatch.user.model.request.OAuthLoginRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuthClientService {

    private Map<OAuthProvider, OAuthApiClient> clients;

    @PostConstruct
    void initClients() {
        clients = clients.values()
                .stream()
                .collect(Collectors.toMap(
                        OAuthApiClient::getProvider,
                        client -> client
                ));
    }

    public OAuthApiClient getClient(OAuthLoginRequest request) {
        return clients.get(request.getProvider());
    }
}
