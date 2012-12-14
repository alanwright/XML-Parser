import it.sauronsoftware.ftp4j.FTPClient;

public class FTPUtility
{
        public static FTPClient connect(String ipAddress, String userName, String password)
        {
                FTPClient client = new FTPClient();
                System.out.println("Connecting to " + ipAddress + " as " + userName + "/" + password);
                try
                {
                        client.setType(FTPClient.TYPE_BINARY);
                        client.connect(ipAddress);
                        client.login(userName, password);
                        return client;
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        return null;
                }
        }
}