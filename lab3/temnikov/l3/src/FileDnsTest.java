import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileDnsTest {


    @Test
    @DisplayName("Test Read DNS From File Not Found")
    public void testReadDNSFromFile_InvalidInput() {
        String filename = "invalid_dns.txt";
        ArrayList<DNS> result = FileDns.readDNSFromFile(filename);
        assertNull(result);
    }

    @Test
    @DisplayName("Test Search Files")
    public void testSearchFiles_MatchingFilesFound() {
        File directory = new File("testDir/testSearchFiles");
        String searchPattern = "\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}\\.txt";
        List<String> result = new ArrayList<>();

        FileDns.searchFiles(directory, searchPattern, result);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Test Is Valid DNS Server Valid Server")
    public void testIsValidDNSServer_ValidServer() {
        String dnsServer = "192.168.0.1";
        boolean result = DNS.isValidDNSServer(dnsServer);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test Is Valid DNS Server Invalid Server")
    public void testIsValidDNSServer_InvalidServer() {
        String dnsServer = "256.0.0.1";
        boolean result = DNS.isValidDNSServer(dnsServer);
        assertFalse(result);
    }

    @Test
    @DisplayName("Test Is Valid DNS Server Invalid Format")
    public void testIsValidDNSServer_InvalidFormat() {
        String dnsServer = "192.168.0.1.2";
        boolean result = DNS.isValidDNSServer(dnsServer);
        assertFalse(result);
    }

    @Test
    @DisplayName("Test Ping DNS Reachable Host")
    public void testPingDNS_ReachableHost() {
        String dnsServer = "8.8.8.8";
        Duration result = DNS.pingDNS(dnsServer);
        assertNotNull(result);
        assertTrue(result.toMillis() >= 0);
    }

}