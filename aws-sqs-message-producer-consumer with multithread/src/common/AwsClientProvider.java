package common;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
/**
 * 
 *This is the connection provider class.
 *which will be invoked each time we have to make a connection request to the server.
 *
 * @author Manish
 *
 */
public class AwsClientProvider {
	/*This method is to make the connections with the AWS server.
	 * It provide the connection credentials
	 * */
	

	public static AmazonSQS createSQSClient() {
		

		
		
		AmazonSQS sqs = new AmazonSQSClient(new BasicAWSCredentials("", ""));

		
		
		sqs.setEndpoint("http://localhost:9324");
		
		return sqs;
	}
}
