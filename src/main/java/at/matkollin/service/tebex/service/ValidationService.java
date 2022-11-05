package at.matkollin.service.tebex.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ValidationService {

  @Value("${TEBEX_WEBHOOK_SECRET:secret}")
  private String tebexWebhookSecret;
  // Authorized ip addresses provided by tebex
  @Value("${TEBEX_WEBHOOK_AUTHORIZED_IP_ADDRESSES:18.209.80.3,54.87.231.232}")
  private String[] authorizedIpAddresses;

  /**
   * Check if the given address is in the authorized address list.
   * Tebex provides addresses from their hosts for validation.
   *
   * @param address address to check.
   * @return whenever the address is authorized.
   */
  public boolean isIpAddressAuthorized(String address) {
    return Arrays.asList(this.authorizedIpAddresses).contains(address);
  }

}
