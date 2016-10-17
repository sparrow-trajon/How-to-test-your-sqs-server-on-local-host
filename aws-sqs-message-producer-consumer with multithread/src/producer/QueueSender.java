package producer;



import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import common.AwsClientProvider;

/**
 * This class is to make the new Queue and push all the messages in the queue.
 *  
 *
 * @author Manish
 *
 */
public class QueueSender {

	private static final String QUEUE = "MyQueue";
	private static int NumberOfMessages=0;
	
	public static int getNumberOfMessages() {
		return NumberOfMessages;
	}

	public static void setNumberOfMessages(int numberOfMessages) {
		NumberOfMessages = numberOfMessages;
	}

	public static void main(String[] args) throws InterruptedException {

		AmazonSQS sqs = AwsClientProvider.createSQSClient();

		CreateQueueRequest request = new CreateQueueRequest(QUEUE);
		String queueUrl = sqs.createQueue(request).getQueueUrl();
		long start = System.currentTimeMillis();
		NumberOfMessages=100;
		
		
		   try {
			   for (int i = 0; i < NumberOfMessages; i++) {
					sqs.sendMessage(new SendMessageRequest(queueUrl, "this is the message number"+i));
					Thread.sleep(10);
				}

	        } catch (AmazonServiceException ase) {
	            System.out.println("Caught an AmazonServiceException, which means your request made it " +
	                    "to Amazon SQS, but was rejected with an error response for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
	        } catch (AmazonClientException ace) {
	            System.out.println("Caught an AmazonClientException, which means the client encountered " +
	                    "a serious internal problem while trying to communicate with SQS, such as not " +
	                    "being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
	        }
		 new SendMessageRequest().setDelaySeconds(300);
		long end = System.currentTimeMillis();
		System.out.println("All the messages have been pushed\n");
		System.out.println((end - start) + "ms");
		sqs.shutdown();
	}
}
