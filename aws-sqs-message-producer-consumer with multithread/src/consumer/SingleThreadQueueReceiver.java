package consumer;




import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import common.AwsClientProvider;

/**
 *This class is to receive all the messages which are there in the Queue.
 *And later on after processing the messages it will empty the queue by deleting all the messages.
 *It is a Single thread class which is making a single thread to invoke and process all the messages.
 *
 * @author Manish
 *
 */
public class SingleThreadQueueReceiver {

	private static final String QUEUE = "MyQueue";

	public static void main(String[] args) {

		new SingleThreadQueueReceiver().run();

	}

	public void run() {

		long start = System.currentTimeMillis();

		AmazonSQS sqs = AwsClientProvider.createSQSClient();

		String queueUrl = sqs.createQueue(QUEUE).getQueueUrl();
		ReceiveMessageRequest request = new ReceiveMessageRequest(QUEUE)
				.withQueueUrl(queueUrl);
		request.setVisibilityTimeout(5);

		boolean queueExists = true;
		while (queueExists) {
			ReceiveMessageResult result = sqs.receiveMessage(request);
			if (result.getMessages().isEmpty()) {
				queueExists = false;
			} else {
				result.getMessages().stream().forEach(x -> {
					System.out.println("[" + x.getBody() + "]");
					deleteMessage(sqs, x);
				});
			}
		}
	
		long end = System.currentTimeMillis();
		System.out.println("All the messages have been retrived\n");
		System.out.println((end - start) + "ms");
	}

	private void deleteMessage(AmazonSQS sqs, Message message) {

		DeleteMessageRequest deleteRequest = new DeleteMessageRequest();
		deleteRequest.setQueueUrl(sqs.createQueue(QUEUE).getQueueUrl());
		deleteRequest.setReceiptHandle(message.getReceiptHandle());
		sqs.deleteMessage(deleteRequest);
	}
}
